FIND_USER_BY_NAME = """
    SELECT id, name, password, admin FROM user WHERE name = %s
"""

FIND_USER_BY_ID = """
    SELECT id, name, password, admin FROM user WHERE id = %s
"""

FIND_CURRENT_PUZZLE_FOR_USER = """
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

FIND_PUZZLE_BY_ID = """
    SELECT *
      FROM puzzle
     WHERE id = %s
"""

UPDATE_PUZZLE = """
    UPDATE puzzle 
       SET start_time = %s, complete_time = %s, next_puzzle_id = %s, earned_score = %s
     WHERE id = %s
"""

GET_SUMMARY_FOR_USER = """
    SELECT count(1) + 1 AS puzzle_num, sum(max_score) AS total_possible_score, sum(earned_score) AS current_score 
      FROM puzzle 
     WHERE complete_time IS NOT NULL AND user_id = %s
"""

FIND_HINTS_FOR_PUZZLE = """
      SELECT *
        FROM hint
       WHERE puzzle_id = %s
    ORDER BY seq_num
"""

FIND_CORRECT_ANSWERS_FOR_PUZZLE = """
      SELECT *
        FROM correct_answer
       WHERE puzzle_id = %s
"""

UPDATE_HINT = """
    UPDATE hint 
       SET description = %s, available_ind = %s, hint_time = %s
     WHERE puzzle_id = %s AND seq_num = %s
"""

INSERT_ANSWER_GUESS = """
    INSERT INTO answer_guess (puzzle_id, value, guess_time, correct_ind)
         VALUES (%s, %s, %s, %s)
"""

FIND_ANSWER_GUESSES_FOR_PUZZLE = """
      SELECT * 
        FROM answer_guess
       WHERE puzzle_id = %s
    ORDER BY guess_time DESC
"""
