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
insert into puzzle.puzzle (id, user_id, description, max_score, points_per_day, points_per_hint, points_per_incorrect)
values (1, 1, 'Hello.', 10, 1, 5, 2);

insert into puzzle.correct_answer (puzzle_id, next_puzzle_id, answer, normalized_ind)
values (1, 2, 'HELLO', 1);

insert into puzzle.correct_answer (puzzle_id, next_puzzle_id, answer, normalized_ind)
values (1, 2, 'HI', 1);

insert into puzzle.hint (puzzle_id, seq_num, description)
values (1, 1, 'It is only polite to say hello back...');



insert into puzzle.puzzle (id, user_id, description, max_score, points_per_day, points_per_hint, points_per_incorrect)
values (2, 1, 'Welcome to your journey, Ava.\n\nBefore we begin, please tell me a 4 letter word.', 10, 1, 5, 2);

insert into puzzle.correct_answer (puzzle_id, next_puzzle_id, answer, normalized_ind)
values (2, 3, '\\w{4}', 0);

insert into puzzle.hint (puzzle_id, seq_num, description)
values (2, 1, 'Seriously, any 4-letter word will work.');



insert into puzzle.puzzle (id, user_id, description, max_score, points_per_day, points_per_hint, points_per_incorrect)
values (3, 1, 'Excellent! Let''s begin!\n\nKnock, knock...', 15, 1, 5, 2);

insert into puzzle.correct_answer (puzzle_id, next_puzzle_id, answer, normalized_ind)
values (3, 4, 'WHOSTHERE', 1);

insert into puzzle.correct_answer (puzzle_id, next_puzzle_id, answer, normalized_ind)
values (3, 5, 'abc123', 0);

insert into puzzle.hint (puzzle_id, seq_num, description)
values (3, 1, 'I said "Knock, knock..."');

insert into puzzle.hint (puzzle_id, seq_num, description)
values (3, 2, 'Can''t you take a joke?');



insert into puzzle.puzzle (id, user_id, description, max_score, points_per_day, points_per_hint, points_per_incorrect)
values (4, 1, 'Ha. Good answer! But maybe you should actually check your door.', 15, 1, 5, 5);

insert into puzzle.correct_answer (puzzle_id, next_puzzle_id, answer, normalized_ind)
values (4, 5, 'abc123', 0);

insert into puzzle.hint (puzzle_id, seq_num, description)
values (4, 1, 'Have you looked ALL over your door for anything "unusual"?');



insert into puzzle.puzzle (id, user_id, description, max_score, points_per_day, points_per_hint, points_per_incorrect)
values (5, 1, 'Now you''re getting the idea!\n\nYou may be asked questions or riddles; you may need to decipher clues; you may need to find items all over; sometimes you may be completely stuck and not know what to do! I know you''ll figure it out though...eventually.\n\nSometimes, you may have a choice to make, and that choice could completely change the path you take on this journey. For example: Do you want to go left or right?', 10, 5, 5, 5);

insert into puzzle.correct_answer (puzzle_id, next_puzzle_id, answer, normalized_ind)
values (5, 6, 'LEFT', 1);

insert into puzzle.correct_answer (puzzle_id, next_puzzle_id, answer, normalized_ind)
values (5, 7, 'RIGHT', 1);

insert into puzzle.hint (puzzle_id, seq_num, description)
values (5, 1, 'The only valid answers are either "left" or "right". You must make a choice.');



insert into puzzle.puzzle (id, user_id, description, max_score, points_per_day, points_per_hint, points_per_incorrect)
values (6, 1, 'Interesting that you chose "left". I guess you''ll never know what was down the "right" path now, because you must learn to live with your decisions...you can''t go back and change them now.\n\nLet''s try one more thing: <span style="color:#202020;">Pa$sW0rd</span>', 20, 1, 1, 3);

insert into puzzle.correct_answer(puzzle_id, next_puzzle_id, answer, normalized_ind)
values (6, 8, 'Pa\\$sW0rd', 0);

insert into puzzle.puzzle (id, user_id, description, max_score, points_per_day, points_per_hint, points_per_incorrect)
values (7, 1, 'Interesting that you chose "right". I guess you''ll never know what was down the "left" path now, because you must learn to live with your decisions...you can''t go back and change them now.\n\nLet''s try one more thing:  <span style="color:#202020;">Pa$sW0rd</span>', 20, 1, 1, 3);

insert into puzzle.correct_answer(puzzle_id, next_puzzle_id, answer, normalized_ind)
values (7, 8, 'Pa\\$sW0rd', 0);



insert into puzzle.puzzle (id, user_id, description)
values (8, 1, 'That''s all I have for now!');
