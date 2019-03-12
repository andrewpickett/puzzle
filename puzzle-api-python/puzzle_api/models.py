import datetime


class ApplicationUser:
    """Class representing a user (or player) of the application.

    Attributes:
        id: The id of the user
        name: The name of the user
        password: The password of the user
        admin: Boolean determining if the user is an admin or not
        summary: A UserSummary object containing information about the user's gameplay
        currentPuzzle: The puzzle that the user is currently working on
    """

    def __init__(self, user_id=None, name=None, password=None, admin=False):
        self.id = int(user_id)
        self.name = name
        self.password = password
        self.admin = bool(admin)
        self.summary = None
        self.currentPuzzle = None

    def __str__(self):
        return "ApplicationUser(id=%s, name=%s, admin=%s)" % (self.id, self.name, self.admin)


class UserSummary:
    def __init__(self, puzzle_num=None, total_possible_score=None, current_score=0):
        self.puzzleNum = puzzle_num
        self.totalPossibleScore = int(total_possible_score) if total_possible_score else 0
        self.currentScore = int(current_score) if current_score else 0
        self.scorePerc = self.calculate_score_perc()

    def calculate_score_perc(self):
        if self.totalPossibleScore == 0:
            self.scorePerc = 0
        else:
            self.scorePerc = int((100 * self.currentScore) / self.totalPossibleScore)
        return self.scorePerc

    def __str__(self):
        return "UserSummary(puzzleNum=%s, totalPossibleScore=%s, currentScore=%s)" % (self.puzzleNum, self.totalPossibleScore, self.currentScore)


class AnswerGuess:
    def __init__(self, answer_id=None, puzzle_id=None, value=None, guess_time=None, correct=False):
        self.id = answer_id
        self.puzzleId = puzzle_id
        self.value = value
        self.guessTime = guess_time.timestamp() if guess_time else None
        self.correct = bool(correct)

    def __str__(self):
        return "AnswerGuess(id=%s, puzzle_id=%s, value=%s, time=%s, correct=%s)" % (
            self.id, self.puzzleId, self.value, self.guessTime, self.correct)


class Puzzle:
    def __init__(self, puzzle_id=None, next_puzzle_id=None, description=None, start_time=None, complete_time=None,
                 user_id=None, max_score=None, earned_score=None, points_per_day=None, points_per_hint=None,
                 points_per_incorrect=None):
        self.id = puzzle_id
        self.nextPuzzleId = next_puzzle_id
        self.description = description
        self.startTime = start_time.timestamp() if start_time else None
        self.completeTime = complete_time.timestamp() if complete_time else None
        self.userId = user_id
        self.maxScore = max_score
        self.earnedScore = earned_score
        self.pointsPerDay = points_per_day
        self.pointsPerHint = points_per_hint
        self.pointsPerIncorrect = points_per_incorrect
        self.end = None
        self.correctAnswers = []
        self.guesses = []
        self.hints =[]
        self.currentScore = self.calculate_current_score()

    def __str__(self):
        return "Puzzle(id=%s, user_id=%s, description=%s)" % (self.id, self.userId, self.description)

    def calculate_current_score(self):
        self.currentScore = self.maxScore
        if self.startTime:
            self.currentScore -= ((datetime.datetime.now() - datetime.datetime.fromtimestamp(self.startTime)).days * self.pointsPerDay)

        used_hints = 0
        if self.hints:
            for hint in self.hints:
                if not hint.available:
                    used_hints += 1
            self.currentScore -= (used_hints * self.pointsPerHint)

        wrong_guesses = 0
        if self.guesses:
            for guess in self.guesses:
                if not guess.correct:
                    wrong_guesses += 1
            self.currentScore -= (wrong_guesses * self.pointsPerIncorrect)

        if self.currentScore < 0:
            self.currentScore = 0

        return self.currentScore


class Hint:
    """ Class representing a hint for a specific puzzle.

    Attributes:
        puzzleId: The id of the puzzle this hint is tied to.
        seqNum: The order of the hint to display.
        description: The actual hint for the puzzle.
        available: An indicator for whether this hint is available still or not. [DEFAULT: True]
        hintTime: The time that this hint was given to the user.
    """

    def __init__(self, puzzle_id=None, seq_num=None, description=None, available=True, hint_time=None):
        self.puzzleId = puzzle_id
        self.seqNum = seq_num
        self.description = description
        self.available = bool(available)
        self.hintTime = hint_time.timestamp() if hint_time else None

    def __str__(self):
        return "Hint(puzzleId=%s, seqNum=%s, description=%s, available=%s)" % (self.puzzleId, self.seqNum, self.description, self.available)


class CorrectAnswer:
    """ Class representing a correct answer for a puzzle.

    Attributes:
        id: The id of the answer.
        puzzleId: The id of the puzzle this answer is tied to.
        nextPuzzleId: The id of the next puzzle this answer leads to.
        answer: The correct answer to the puzzle.
        normalized: An indicator for whether the answer should be normalized when validating. [DEFAULT: false]
    """

    def __init__(self, answer_id=None, puzzle_id=None, next_puzzle_id=None, answer=None, normalized=False):
        self.id = answer_id
        self.puzzleId = puzzle_id
        self.nextPuzzleId = next_puzzle_id
        self.answer = answer
        self.normalized = bool(normalized)

    def __str__(self):
        return 'CorrectAnswer(id=%s, puzzleId=%s, nextPuzzleId=%s, answer=%s, normalized=%s)' % (self.id, self.puzzleId, self.nextPuzzleId, self.answer, self.normalized)


class AnswerGuess:
    """ Class representing a guess from the user.

    Attributes:
        id: The id of the guess.
        puzzleId: The id of the puzzle this guess is tied to.
        value: The guess the user made for the puzzle.
        guessTime: The time that the guess was made.
        correct: An indicator for whether the guess was a correct answer or not. [DEFAULT: False]
    """

    def __init__(self, guess_id=None, puzzle_id=None, value=None, guess_time=None, correct=False):
        self.id = guess_id
        self.puzzleId = puzzle_id
        self.value = value
        self.guessTime = guess_time.timestamp() if guess_time else None
        self.correct = bool(correct)

    def __str__(self):
        return 'AnswerGuess(id=%s, puzzleId=%s, value=%s, guessTime=%s, correct=%s)' % (self.id, self.puzzleId, self.value, self.guessTime, self.correct)
