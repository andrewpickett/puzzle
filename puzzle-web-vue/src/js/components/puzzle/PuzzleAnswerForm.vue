<template>
	<form class="form-answer" @submit.prevent="submit()">
		<div class="row justify-content-center">
			<div class="col-9">
				<div class="input-group input-group-lg answer-form">
					<input id="answer" type="text" class="form-control answer-input" autofocus="autofocus" autocomplete="off" v-model="answer" />
					<div class="input-group-btn">
						<button type="submit" class="btn btn-answer-form">Answer</button>
						<button class="btn btn-answer-form" @click.prevent="getHint()" v-if="allowHints">Hint</button>
					</div>
				</div>
			</div>
		</div>
	</form>
</template>

<script>
	import axios from 'axios';
	import auth from '../../auth';

	export default {
		data() {
			return {
				answer: ''
			}
		},
		props: ['puzzle', 'allowHints'],
		methods: {
			submit() {
				if (this.answer != '') {
					axios.post('/answer?earnedScore=' + this.puzzle.currentScore, {value: this.answer, puzzleId: this.puzzle.id}, {headers: auth.getAuthHeader()})
						.then((response) => {
							this.answer = '';
							this.$parent.loadCurrent();
							$('#answer').focus();
						})
						.catch((error) => {
							auth.logout();
						});
				}
			},
			getHint() {
				axios.post('/hint', {}, { headers: auth.getAuthHeader(), params: { puzzleId: this.puzzle.id }})
					.then((response) => {
						this.$parent.loadCurrent();
						$('#answer').focus();
					})
					.catch((error) => {
						auth.logout();
					});
			}
		}
	}
</script>

<style>
	.form-answer {
		margin-top: 40px;
	}

	.btn-answer-form {
		background-color: #202020;
		color: #4cae4c;
		font-family: Consolas, "Courier New", monospace;
	}
	.btn-answer-form:hover {
		background-color: #4cae4c;
		color: #ffffff;
	}
</style>
