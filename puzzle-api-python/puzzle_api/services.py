from .models import ApplicationUser


class UserService:
	def __init__(self):
		pass

	def get_user(self):
		user = ApplicationUser()

		if user is None:
			raise PermissionError('You are not allowed to view this page.')

		user.name = 'Andrew'
		user.id = 1
		user.password = 'foobar'

		return user
		# Get the JWT token from the request
		# use the id from the token to look up the user in the DB
		# load the current puzzle for the given user from the DB
		# load the summary on the user
		# if user is null throw error and don't give them access (assumes JWT is invalid)
		# return the ApplicationUser

		# ApplicationUser user = null;
		#
		# JwtAuthenticationToken auth = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		# if (auth != null) {
		# user = userRepo.findById(auth.getId()).get();
		# if (user != null && includePuzzles) {
		# user.setCurrentPuzzle(puzzleRepo.findCurrentPuzzleForUser(user.getId()));
		# Puzzle currentPuzzle = user.getCurrentPuzzle();
		# if (currentPuzzle != null) {
		# if (currentPuzzle.getStartTime() == null) {
		# currentPuzzle.setStartTime(new Date());
		# puzzleRepo.save(currentPuzzle);
		# }
		# currentPuzzle.setHints(hintRepo.findByIdPuzzleId(currentPuzzle.getId()));
		# currentPuzzle.setGuesses(answerGuessRepo.findByPuzzleIdOrderByGuessTimeDesc(currentPuzzle.getId()));
		# currentPuzzle.setEnd(CollectionUtils.isEmpty(correctAnswerRepo.findByPuzzleId(currentPuzzle.getId())));
		# }
		#
		# user.setSummary(new UserSummary(userRepo.getUserSummaryInfo(user.getId())));
		# }
		# }
		# if (user == null) {
		# throw new AccessDeniedException("You are not allowed to view this page.");
		# } else {
		# return user;
		# }
