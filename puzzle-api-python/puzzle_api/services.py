import datetime

import bcrypt

import puzzle_api.database
from puzzle_api.jwt_utils import generate_jwt_token


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
                current_puzzle.startTime = datetime.datetime.now()
                # TODO: Save back to DB
            user.currentPuzzle = current_puzzle
            """
            # TODO: Set hints	--	hintRepo.findByIdPuzzleId(currentPuzzle.getId())
            # TODO: Set guesses	--	answerGuessRepo.findByPuzzleIdOrderByGuessTimeDesc(currentPuzzle.getId())
            # TODO: Set end		--	CollectionUtils.isEmpty(correctAnswerRepo.findByPuzzleId(currentPuzzle.getId()))
            """
        user.summary = puzzle_api.database.find_user_summary_for_user(user.id)
        user.password = None
    return user
