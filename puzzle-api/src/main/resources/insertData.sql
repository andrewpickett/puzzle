-- ------------------------------------------------ --
-- DELETE OLD DATA TO RESET ----------------------- --
-- ------------------------------------------------ --
truncate puzzle.answer_guess;
truncate puzzle.hint;
truncate puzzle.puzzle;
truncate puzzle.user;

-- ------------------------------------------------ --
-- INSERT USER DATA ------------------------------- --
-- ------------------------------------------------ --
insert into puzzle.user (id, name, password) values (1, 'Andrew', 'darkstar');

-- ------------------------------------------------ --
-- INSERT PUZZLES AND HINTS ----------------------- --
-- ------------------------------------------------ --
insert into puzzle.puzzle (id, user_id, prev_puzzle_id, title, description, answer, normalized_ind)
values (1, 1, 0, null, 'Hello.', 'HELLO', 1);

insert into puzzle.hint (puzzle_id, seq_num, description)
values (1, 1, 'It is only polite to say hello back...');

insert into puzzle.puzzle (id, user_id, prev_puzzle_id, title, description, answer, normalized_ind)
values (2, 1, 1, null, 'Welcome to your journey, Ava. Before we begin, please tell me a 4 letter word.', '\\w{4}', 0);

insert into puzzle.hint (puzzle_id, seq_num, description)
values (2, 1, 'Seriously, any 4-letter word will work.');

insert into puzzle.puzzle (id, user_id, prev_puzzle_id, title, description, answer, normalized_ind)
values (3, 1, 2, null, 'Excellent! Let''s begin! Knock, knock...', 'WHOSTHERE', 1);

insert into puzzle.hint (puzzle_id, seq_num, description)
values (3, 1, 'I said "Knock, knock..."');

insert into puzzle.hint (puzzle_id, seq_num, description)
values (3, 2, 'Can''t you take a joke?');

insert into puzzle.puzzle (id, user_id, prev_puzzle_id, title, description, answer, normalized_ind)
values (4, 1, 3, null, 'Ha. Good answer! But maybe you should actually check your door.', 'ABC123', 1);

insert into puzzle.hint (puzzle_id, seq_num, description)
values (4, 1, 'Have you looked ALL over your door for anything "unusual"?');

insert into puzzle.puzzle (id, user_id, prev_puzzle_id, title, description, answer, normalized_ind)
values (5, 1, 4, null, 'Now you''re getting the idea!', '', 0);



-- ------------------------------------------------ --
-- THIS IS THE QUERY TO GET NEXT PUZZLE FOR USER -- --
-- ------------------------------------------------ --
  SELECT p.*, h.seq_num, h.description, h.available_ind, h.hint_time 
    FROM puzzle.hint h 
         JOIN puzzle.puzzle p ON h.puzzle_id = p.id 
   WHERE p.prev_puzzle_id = (SELECT IF(tmp.ct IS NULL, 0, tmp.id) 
                               FROM (SELECT id, MAX(complete_time) AS ct 
                                       FROM puzzle.puzzle p WHERE user_id = 1) tmp)
     AND p.complete_time IS NULL
ORDER BY p.id, h.seq_num;
