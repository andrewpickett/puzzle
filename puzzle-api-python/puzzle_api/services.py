import datetime

import bcrypt

import re

import puzzle_api.database
from puzzle_api.jwt_utils import generate_jwt_token
from puzzle_api.models import AnswerGuess


def perform_login(name, password):
    user = puzzle_api.database.find_user_by_name(name)
    if not user or not bcrypt.checkpw(password, user.password.encode("utf-8")):
        raise PermissionError("Invalid credentials")

    return generate_jwt_token(user.id, 'ROLE_ADMIN' if user.admin else 'ROLE_USER', user.name)


def load_puzzle_for_user(user_id):
    user = puzzle_api.database.find_user_by_id(user_id)
    if user:
        current_puzzle = puzzle_api.database.find_current_puzzle_for_user(user.id)
        if current_puzzle:
            if not current_puzzle.startTime:
                current_puzzle.startTime = datetime.datetime.now().timestamp()
                puzzle_api.database.update_puzzle(current_puzzle)
            user.currentPuzzle = current_puzzle
            current_puzzle.hints = puzzle_api.database.find_hints_for_puzzle(current_puzzle.id)
            current_puzzle.guesses = puzzle_api.database.find_answer_guesses_for_puzzle(current_puzzle.id)
            """
            # TODO: Set end		--	CollectionUtils.isEmpty(correctAnswerRepo.findByPuzzleId(currentPuzzle.getId()))
            """
            current_puzzle.calculate_current_score()
        user.summary = puzzle_api.database.find_user_summary_for_user(user.id)
        user.password = None
    return user


def get_hint(puzzle_id):
    next_available = None
    if puzzle_id:
        hints = puzzle_api.database.find_hints_for_puzzle(puzzle_id)
        if hints:
            for hint in hints:
                if hint.available:
                    next_available = hint
                    break

        if next_available:
            next_available.available = False
            next_available.hintTime = datetime.datetime.now().timestamp()
            puzzle_api.database.update_hint(next_available)

    return next_available


def submit_answer(answer, earned_score):
    now = datetime.datetime.now().timestamp()
    answer['puzzle_id'] = answer.pop('puzzleId')
    answer['guess_time'] = now
    answer['correct'] = False
    guess = AnswerGuess(**answer)

    puzzle = puzzle_api.database.find_puzzle_by_id(guess.puzzleId)

    if puzzle:
        correct_answers = puzzle_api.database.find_correct_answers_for_puzzle(puzzle.id)
        if correct_answers:
            puzzle.correctAnswers = correct_answers
            for ca in correct_answers:
                official_answer = _get_official_answer(guess.value, ca.normalized)
                if re.match(ca.answer + '$', official_answer):
                    guess.correct = True
                    puzzle.completeTime = now
                    puzzle.nextPuzzleId = ca.nextPuzzleId
                    puzzle.earnedScore = earned_score
                    puzzle_api.database.update_puzzle(puzzle)
                    break

    puzzle_api.database.insert_answer_guess(guess)
    return guess.correct


def _get_official_answer(answer, normalize):
    if normalize:
        return re.sub('\W', '', answer).upper()

    return answer
