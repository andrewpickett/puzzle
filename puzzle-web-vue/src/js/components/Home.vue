<template>
	<div class="container">
		<Puzzle :puzzle="puzzle" />

		<div v-if="puzzle">
			<PuzzleAnswerForm :puzzle-id="puzzle.id" />

			<PreviousGuesses :guesses="puzzle.guesses" />
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

	export default {
		data() {
			return {
				answer: '',
				puzzle: {}
			}
		},
		components: {
			Puzzle, PreviousGuesses, PuzzleAnswerForm
		},
		methods: {
			loadCurrent() {
				axios.get('/', { headers: auth.getAuthHeader() })
					.then((response) => {
						this.puzzle = response.data.currentPuzzle;
						if (this.puzzle && this.puzzle.guesses && this.puzzle.guesses.length == 0) {
							this.puzzle.guesses = null;
						}
					})
					.catch((error) => {
						console.log(error);
						auth.logout();
					});
			}
		},
		mounted() {
			this.loadCurrent();
		}
	}
</script>

<style>
</style>
