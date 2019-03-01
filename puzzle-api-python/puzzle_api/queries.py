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

GET_SUMMARY_FOR_USER = """
    SELECT count(1) + 1 AS puzzle_num, sum(max_score) AS total_possible_score, sum(earned_score) AS current_score 
      FROM puzzle 
     WHERE complete_time IS NOT NULL AND user_id = %s
"""
