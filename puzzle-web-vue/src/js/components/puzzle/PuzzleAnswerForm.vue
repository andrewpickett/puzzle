<template>
	<form class="form-answer" @submit.prevent="submit()">
		<div class="row">
			<div class="col-xs-12">
				<div class="input-group input-group-lg answer-form">
					<input id="answer" type="text" class="form-control answer-input" autofocus="autofocus" autocomplete="off" v-model="answer"/>
					<div class="input-group-btn">
						<button type="submit" class="btn btn-answer-form">Answer</button>
						<button class="btn btn-answer-form" @click.prevent="getHint()">Hint</button>
					</div>
				</div>
			</div>
		</div>
	</form>
</template>

<script>
	import axios from 'axios';
	import auth from '../../auth';

	import Modal from '../Modal';

	export default {
		data() {
			return {
				answer: '',
			}
		},
		props: ['puzzleId'],
		methods: {
			submit() {
				axios.post('/answer', { value: this.answer, puzzleId: this.puzzleId }, { headers: auth.getAuthHeader() })
					.then((response) => {
						this.answer = '';
						this.$parent.loadCurrent();
						$('#answer').focus();
					})
					.catch((error) => {
						auth.logout();
					});
			},
			getHint() {
				axios.post('/hint', {}, { headers: auth.getAuthHeader(), params: { puzzleId: this.puzzleId }})
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
		border: 3px dashed #4cae4c;
		background-color: #202020;
		color: #4cae4c;
		border-bottom: 0px;
		font-family: Consolas, "Courier New", monospace;
	}
	.btn-answer-form:hover {
		background-color: #4cae4c;
		color: #ffffff;
	}
</style>
