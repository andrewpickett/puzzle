<template>
	<div class="container">
		<Puzzle :puzzle="puzzle" />

		<div v-if="puzzle">
			<PuzzleAnswerForm :puzzle-id="puzzle.id" />

			<PreviousGuesses :guesses="puzzle.guesses" />

			<Hints :hints="puzzle.hints" />
		</div>
	</div>
</template>

<script>
	import axios from 'axios';

	import auth from '../auth';
	import config from '../config';

	import Puzzle from './puzzle/Puzzle';
	import PreviousGuesses from './puzzle/PreviousGuesses';
	import PuzzleAnswerForm from './puzzle/PuzzleAnswerForm';
	import Hints from './puzzle/Hints';

	export default {
		data() {
			return {
				answer: '',
				puzzle: {}
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
						if (this.puzzle && this.puzzle.guesses && this.puzzle.guesses.length == 0) {
							this.puzzle.guesses = null;
						}
						$('#answer').focus();
					})
					.catch((error) => {
						console.log(error);
						auth.logout();
					});
			},

			showHint() {
				for (let hint in this.puzzle.hints) {
					if (hint.available) {
						hint.available = false;
						break;
					}
				}
			}
		},
		mounted() {
			this.loadCurrent();
		}
	}
</script>

<style>
</style>
