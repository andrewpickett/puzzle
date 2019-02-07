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

		<Footer :summary="summary" :puzzlePerc="puzzlePerc"></Footer>
	</div>
</template>

<script>
	import axios from 'axios';

	import auth from '../auth';

	import Puzzle from './puzzle/Puzzle';
	import PreviousGuesses from './puzzle/PreviousGuesses';
	import PuzzleAnswerForm from './puzzle/PuzzleAnswerForm';
	import Hints from './puzzle/Hints';
	import Footer from './Footer';

	export default {
		data() {
			return {
				answer: '',
				allowHints: true,
				puzzle: {},
				puzzlePerc: 1.0,
				summary: {}
			}
		},
		components: {
			Puzzle, PreviousGuesses, PuzzleAnswerForm, Hints, Footer
		},
		methods: {
			loadCurrent() {
				axios.get('/', { headers: auth.getAuthHeader() })
					.then((response) => {
						this.puzzle = response.data.currentPuzzle;
						this.summary = response.data.summary;
						if (this.puzzle) {
							if (this.puzzle.guesses && this.puzzle.guesses.length == 0) {
								this.puzzle.guesses = null;
							}
							this.puzzlePerc = (this.puzzle.currentScore / this.puzzle.maxScore);
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
</style>

