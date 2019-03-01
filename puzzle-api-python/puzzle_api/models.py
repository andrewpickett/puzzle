class ApplicationUser:
    def __init__(self, user_id=None, name=None, password=None, admin=False):
        self.user_id = int(user_id)
        self.name = name
        self.password = password
        self.admin = bool(admin)
        self.summary = None
        self.current_puzzle = None

    def __str__(self):
        return "ApplicationUser(id=%s, name=%s, admin=%s)" % (self.user_id, self.name, self.admin)


class AnswerGuess:
    def __init__(self, answer_id=None, puzzle_id=None, value=None, guess_time=None, correct=False):
        self.answer_id = answer_id
        self.puzzle_id = puzzle_id
        self.value = value
        self.guess_time = guess_time
        self.correct = bool(correct)

    def __str__(self):
        return "AnswerGuess(id=%s, puzzle_id=%s, value=%s, time=%s, correct=%s)" % (
            self.answer_id, self.puzzle_id, self.value, self.guess_time, self.correct)


class Puzzle:
    def __init__(self, puzzle_id=None, next_puzzle_id=None, description=None, start_time=None, complete_time=None,
                 user_id=None, max_score=None, earned_score=None, points_per_day=None, points_per_hint=None,
                 points_per_incorrect=None):
        self.puzzle_id = puzzle_id
        self.next_puzzle_id = next_puzzle_id
        self.description = description
        if start_time:
            self.start_time = start_time.timestamp()
        else:
            self.start_time = None
        self.complete_time = complete_time
        self.user_id = user_id
        self.max_score = max_score
        self.earned_score = earned_score
        self.points_per_day = points_per_day
        self.points_per_hint = points_per_hint
        self.points_per_incorrect = points_per_incorrect

    def __str__(self):
        return "Puzzle(id=%s, user_id=%s, description=%s)" % (self.puzzle_id, self.user_id, self.description)
