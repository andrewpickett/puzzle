from contextlib import contextmanager

from mysql.connector import pooling, DatabaseError

import puzzle_api.queries
from config import config
from puzzle_api.models import ApplicationUser, Puzzle, UserSummary

dbconfig = {
    "database": config['database']['schema'],
    "user": config['database']['username'],
    "password": config['database']['password'],
    "host": config['database']['host']
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


def find_user_summary_for_user(user_id):
    summary = None
    with open_db_connection() as cur:
        cur.execute(puzzle_api.queries.GET_SUMMARY_FOR_USER, (user_id,))
        summary_data = cur.fetchone()
        if summary_data:
            summary = UserSummary(*summary_data)

    return summary
