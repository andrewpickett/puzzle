<template>
	<form class="form-answer" @submit.prevent="submit()">
		<div class="row">
			<div class="col-xs-12">
				<div class="input-group input-group-lg">
					<input id="answer" type="text" class="form-control" placeholder="Answer" required="required" autofocus="autofocus" autocomplete="off" v-model="answer"/>
					<div class="input-group-btn">
						<button type="submit" class="btn btn-primary">Answer</button>
						<button class="btn btn-info" @click.prevent="getHint()">Hint</button>
					</div>
				</div>
			</div>
		</div>

		<modal id="answerSuccess" title="Congratulations!" closeText="Next &gt;&gt;">
			<p>You answered correctly! Move onto the next puzzle!</p>
		</modal>
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
		components: { Modal },
		methods: {
			submit() {
				axios.post('/answer', { value: this.answer, puzzleId: this.puzzleId }, { headers: auth.getAuthHeader() })
					.then((response) => {
						this.answer = '';
						var parent = this.$parent;
						if (response.data) {
							$('#answerSuccess').modal({}).on('shown.bs.modal', function(e) {
								$("#closeModal").focus();
							}).on('hidden.bs.modal', function(e) {
								parent.loadCurrent();
								$('#answer').focus();
							});
						} else {
							parent.loadCurrent();
						}
					})
					.catch((error) => {
						auth.logout();
					});
			},
			getHint() {
				console.log('Getting hint.');
			}
		}
	}
</script>

<style>
	.form-answer {
		margin-top: 40px;
	}
</style>
