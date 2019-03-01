import datetime

import puzzle_api.database


def get_user_from_jwt_token(jwt_token):
    if jwt_token:
        user = puzzle_api.database.find_by_id(jwt_token["userId"])
        if user:
            current_puzzle = puzzle_api.database.find_current_puzzle_for_user(user.user_id)
            if current_puzzle:
                if not current_puzzle.start_time:
                    current_puzzle.start_time = int(datetime.datetime.now().timestamp() * 1000)
                    # TODO: Save back to DB
                user.current_puzzle = current_puzzle
                """
                # TODO: Set hints	--	hintRepo.findByIdPuzzleId(currentPuzzle.getId())
                # TODO: Set guesses	--	answerGuessRepo.findByPuzzleIdOrderByGuessTimeDesc(currentPuzzle.getId())
                # TODO: Set end		--	CollectionUtils.isEmpty(correctAnswerRepo.findByPuzzleId(currentPuzzle.getId()))

                # TODO: Set User summary	--	new UserSummary(userRepo.getUserSummaryInfo(user.getId()))
                """
        user.password = None
        return user
    else:
        raise PermissionError("You are not allowed to view this page")
