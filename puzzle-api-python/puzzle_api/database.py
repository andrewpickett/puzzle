from mysql.connector import pooling

from config import config
from puzzle_api.models import ApplicationUser, Puzzle

dbconfig = {
    "database": config['database']['schema'],
    "user": config['database']['username'],
    "password": config['database']['password'],
    "host": config['database']['host']
}

db_pool = pooling.MySQLConnectionPool(pool_name=config['database']['pool_name'],
                                      pool_size=config['database']['pool_size'],
                                      **dbconfig)


def create_connection():
    con = db_pool.get_connection()
    cur = con.cursor(prepared=True)
    return con, cur


def close_connection(cur, con):
    if cur:
        cur.close()
    if con:
        con.close()


def find_by_id(user_id):
    user = None
    try:
        con, cur = create_connection()
        cur.execute("SELECT id, name, password, admin FROM user WHERE id = %s", (user_id,))
        user_data = cur.fetchone()
        if user_data:
            user = ApplicationUser(*user_data)
    finally:
        close_connection(cur, con)

    return user


def find_current_puzzle_for_user(user_id):
    query = """
		SELECT * 
			FROM puzzle 
		   WHERE id = IFNULL((SELECT next_puzzle_id 
								FROM puzzle 
							   WHERE complete_time = (SELECT MAX(complete_time) 
														FROM puzzle 
													   WHERE user_id = %s)), 
							 (SELECT MIN(id) FROM puzzle WHERE user_id = %s))
			 AND complete_time IS NULL
	"""
    try:
        con, cur = create_connection()
        cur.execute(query, (user_id, user_id))
        puzzle_data = cur.fetchone()
        if puzzle_data:
            puzzle = Puzzle(*puzzle_data)
    finally:
        close_connection(cur, con)

    return puzzle


if __name__ == '__main__':
    find_by_id(1)
