import datetime
from contextlib import contextmanager

from mysql.connector import pooling, DatabaseError

import puzzle_api.queries
from config import config
from puzzle_api.models import ApplicationUser, Puzzle, UserSummary, Hint, CorrectAnswer, AnswerGuess

dbconfig = {
    "database": config['database']['schema'],
    "user": config['database']['username'],
    "password": config['database']['password'],
    "host": config['database']['host'],
    "autocommit": True
}

db_pool = pooling.MySQLConnectionPool(pool_name=config['database']['pool_name'],
                                      pool_size=config['database']['pool_size'],
                                      **dbconfig)


@contextmanager
def open_db_connection():
    connection = db_pool.get_connection()
    cursor = connection.cursor(prepared=True)
    try:
        yield cursor
    except DatabaseError as err:
        cursor.execute("ROLLBACK")
        raise err
    finally:
        if cursor:
            cursor.close()
        if connection:
            connection.close()


def find_user_by_id(user_id):
    user = None
    with open_db_connection() as cur:
        cur.execute(puzzle_api.queries.FIND_USER_BY_ID, (user_id,))
        user_data = cur.fetchone()
        if user_data:
            user = ApplicationUser(*user_data)

    return user


def find_user_by_name(name):
    user = None
    with open_db_connection() as cur:
        cur.execute(puzzle_api.queries.FIND_USER_BY_NAME, (name,))
        user_data = cur.fetchone()
        if user_data:
            user = ApplicationUser(*user_data)

    return user


def find_current_puzzle_for_user(user_id):
    puzzle = None
    with open_db_connection() as cur:
        cur.execute(puzzle_api.queries.FIND_CURRENT_PUZZLE_FOR_USER, (user_id, user_id))
        puzzle_data = cur.fetchone()
        if puzzle_data:
            puzzle = Puzzle(*puzzle_data)

    return puzzle


def find_puzzle_by_id(puzzle_id):
    puzzle = None
    with open_db_connection() as cur:
        cur.execute(puzzle_api.queries.FIND_PUZZLE_BY_ID, (puzzle_id,))
        puzzle_data = cur.fetchone()
        if puzzle_data:
            puzzle = Puzzle(*puzzle_data)

    return puzzle


def update_puzzle(puzzle):
    with open_db_connection() as cur:
        start_time = None if not puzzle.startTime else datetime.datetime.fromtimestamp(puzzle.startTime)
        complete_time = None if not puzzle.completeTime else datetime.datetime.fromtimestamp(puzzle.completeTime)
        return cur.execute(puzzle_api.queries.UPDATE_PUZZLE, (start_time, complete_time, puzzle.nextPuzzleId, puzzle.earnedScore, puzzle.id))


def find_user_summary_for_user(user_id):
    summary = None
    with open_db_connection() as cur:
        cur.execute(puzzle_api.queries.GET_SUMMARY_FOR_USER, (user_id,))
        summary_data = cur.fetchone()
        if summary_data:
            summary = UserSummary(*summary_data)

    return summary


def find_hints_for_puzzle(puzzle_id):
    hints = []
    with open_db_connection() as cur:
        cur.execute(puzzle_api.queries.FIND_HINTS_FOR_PUZZLE, (puzzle_id,))

        all_hint_data = cur.fetchall()
        if all_hint_data:
            for hint_data in all_hint_data:
                hints.append(Hint(*hint_data))

    return hints


def update_hint(hint):
    with open_db_connection() as cur:
        hint_time = datetime.datetime.fromtimestamp(hint.hintTime)
        return cur.execute(puzzle_api.queries.UPDATE_HINT, (hint.description, 1 if hint.available else 0, hint_time, hint.puzzleId, hint.seqNum))


def find_correct_answers_for_puzzle(puzzle_id):
    correct_answers = []
    with open_db_connection() as cur:
        cur.execute(puzzle_api.queries.FIND_CORRECT_ANSWERS_FOR_PUZZLE, (puzzle_id,))

        all_answer_data = cur.fetchall()
        if all_answer_data:
            for answer_data in all_answer_data:
                correct_answers.append(CorrectAnswer(*answer_data))

    return correct_answers


def insert_answer_guess(answer):
    with open_db_connection() as cur:
        guess_time = datetime.datetime.fromtimestamp(answer.guessTime)
        return cur.execute(puzzle_api.queries.INSERT_ANSWER_GUESS, (answer.puzzleId, answer.value, guess_time, 1 if answer.correct else 0))


def find_answer_guesses_for_puzzle(puzzle_id):
    guesses = []
    with open_db_connection() as cur:
        cur.execute(puzzle_api.queries.FIND_ANSWER_GUESSES_FOR_PUZZLE, (puzzle_id,))

        all_guess_data = cur.fetchall()
        if all_guess_data:
            for guess_data in all_guess_data:
                guesses.append(AnswerGuess(*guess_data))

    return guesses
