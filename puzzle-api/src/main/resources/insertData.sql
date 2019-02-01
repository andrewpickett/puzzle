-- ------------------------------------------------ --
-- DELETE OLD DATA TO RESET ----------------------- --
-- ------------------------------------------------ --
truncate puzzle.answer_guess;
truncate puzzle.hint;
truncate puzzle.correct_answer;
truncate puzzle.puzzle;
truncate puzzle.user;

-- ------------------------------------------------ --
-- INSERT USER DATA ------------------------------- --
-- ------------------------------------------------ --
insert into puzzle.user (id, name, password) values (1, 'Andrew', 'darkstar');

-- ------------------------------------------------ --
-- INSERT PUZZLES AND HINTS ----------------------- --
-- ------------------------------------------------ --
insert into puzzle.puzzle (id, user_id, title, description)
values (1, 1, null, 'Hello.');

insert into puzzle.correct_answer (puzzle_id, next_puzzle_id, answer, normalized_ind)
values (1, 2, 'HELLO', 1);

insert into puzzle.hint (puzzle_id, seq_num, description)
values (1, 1, 'It is only polite to say hello back...');

insert into puzzle.puzzle (id, user_id, title, description)
values (2, 1, null, 'Welcome to your journey, Ava.\n\nBefore we begin, please tell me a 4 letter word.');

insert into puzzle.correct_answer (puzzle_id, next_puzzle_id, answer, normalized_ind)
values (2, 3, '\\w{4}', 0);

insert into puzzle.hint (puzzle_id, seq_num, description)
values (2, 1, 'Seriously, any 4-letter word will work.');

insert into puzzle.puzzle (id, user_id, title, description)
values (3, 1, null, 'Excellent! Let''s begin!\n\nKnock, knock...');

insert into puzzle.correct_answer (puzzle_id, next_puzzle_id, answer, normalized_ind)
values (3, 4, 'WHOSTHERE', 1);

insert into puzzle.correct_answer (puzzle_id, next_puzzle_id, answer, normalized_ind)
values (3, 5, 'abc123', 0);

insert into puzzle.hint (puzzle_id, seq_num, description)
values (3, 1, 'I said "Knock, knock..."');

insert into puzzle.hint (puzzle_id, seq_num, description)
values (3, 2, 'Can''t you take a joke?');

insert into puzzle.puzzle (id, user_id, title, description)
values (4, 1, null, 'Ha. Good answer! But maybe you should actually check your door.');

insert into puzzle.correct_answer (puzzle_id, next_puzzle_id, answer, normalized_ind)
values (4, 5, 'abc123', 0);

insert into puzzle.hint (puzzle_id, seq_num, description)
values (4, 1, 'Have you looked ALL over your door for anything "unusual"?');

insert into puzzle.puzzle (id, user_id, title, description)
values (5, 1, null, 'Now you''re getting the idea!\n\nAsk your dad for a dollar and tell me the serial number');




-- ------------------------------------------------ --
-- THIS IS THE QUERY TO GET NEXT PUZZLE FOR USER -- --
-- ------------------------------------------------ --
SELECT p.*, ca.*
  FROM puzzle p
       JOIN correct_answer ca ON p.id = ca.puzzle_id
 WHERE p.id = ifnull((SELECT next_puzzle_id
                        FROM puzzle
					   WHERE complete_time = (SELECT MAX(complete_time)
                                                FROM puzzle
											   WHERE user_id = 1)),
					 (SELECT MIN(id) FROM puzzle WHERE user_id = 1));

