<template>
	<div>
		<div class="container">
			<Puzzle :puzzle="puzzle" />

			<div v-if="puzzle">
				<PuzzleAnswerForm :puzzle="puzzle" :allowHints="allowHints" v-if="!puzzle.end"/>

				<Hints :hints="puzzle.hints" />

				<PreviousGuesses :guesses="puzzle.guesses" v-if="false" />
			</div>
		</div>
		<footer class="footer">
			<div class="container">
				<div class="footer-content">
					This is my footer content. I'll show current total score, possible score remaining for the current puzzle, etc.
				</div>
				<div class="puzzle-progress" :style="{width: (scorePerc * 100) + '%'}">&nbsp;</div>
			</div>
		</footer>
	</div>
</template>

<script>
	import axios from 'axios';

	import auth from '../auth';

	import Puzzle from './puzzle/Puzzle';
	import PreviousGuesses from './puzzle/PreviousGuesses';
	import PuzzleAnswerForm from './puzzle/PuzzleAnswerForm';
	import Hints from './puzzle/Hints';

	export default {
		data() {
			return {
				answer: '',
				allowHints: true,
				puzzle: {},
				scorePerc: 1.0
			}
		},
		components: {
			Puzzle, PreviousGuesses, PuzzleAnswerForm, Hints
		},
		methods: {
			loadCurrent() {
				axios.get('/', { headers: auth.getAuthHeader() })
					.then((response) => {
						this.puzzle = response.data.currentPuzzle;
						if (this.puzzle) {
							if (this.puzzle.guesses && this.puzzle.guesses.length == 0) {
								this.puzzle.guesses = null;
							}
							this.scorePerc = (this.puzzle.currentScore / this.puzzle.maxScore);

							if (this.scorePerc < 0.3) {
								$(".puzzle-progress").addClass("puzzle-progress-red").removeClass("puzzle-progress-amber");
							} else if (this.scorePerc < 0.7) {
								$(".puzzle-progress").addClass("puzzle-progress-amber").removeClass("puzzle-progress-red");
							} else {
								$(".puzzle-progress").removeClass("puzzle-progress-red").removeClass("puzzle-progress-amber");
							}
						}
						$('#answer').focus();
					})
					.catch((error) => {
						console.log(error);
						auth.logout();
					});
			}
		},
		mounted() {
			this.loadCurrent();
		},
		updated() {
			this.allowHints = $(".shown-hint").length < this.puzzle.hints.length;
		}
	}
</script>

<style>
	.puzzle-progress {
		height: 2px;
		background-color: #009900;
		width: 100%;
		line-height: 2px;
	}

	.puzzle-progress-red {
		background-color: #990000;
	}
	.puzzle-progress-amber {
		background-color: #996600;
	}
	.footer-content {
		line-height: 48px;
	}
</style>

