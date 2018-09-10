<template>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h1>Completed Puzzles</h1>
			</div>
			<div class="panel-body">
				<table class="table table-sm" v-if="puzzles">
					<thead class="thead-light">
					<tr>
						<th scope="col"></th>
						<th scope="col">Title</th>
						<th scope="col">Correct Answer</th>
						<th scope="col">Completed Time</th>
					</tr>
					</thead>
					<tbody>
					<tr v-for="puzzle in puzzles">
						<td scope="row">{{ puzzle.sequenceNum }}</td>
						<td>{{ puzzle.title }}</td>
						<td>{{ puzzle.mostRecentGuess.value | uppercase }}</td>
						<td>{{ formatDate(puzzle.mostRecentGuess.guessTime) }}</td>
					</tr>
					</tbody>
				</table>
				<div v-if="!puzzles">
					<p>No puzzles have been completed yet!</p>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import axios from 'axios';
	import moment from 'moment';

	import auth from '../../auth';

	export default {
		data() {
			return {
				puzzles: null
			}
		},
		components: {
		},
		methods: {
			formatDate(value) {
				return moment(value).format('YYYY/MM/DD HH:mm:ss');
			}
		},
		mounted() {
			axios.get('/completed', { headers: auth.getAuthHeader() })
				.then((response) => {
					this.puzzles = response.data;
					if (this.puzzles && this.puzzles.length == 0) {
						this.puzzles = null;
					}
				})
				.catch((error) => {
					auth.logout();
				});
		}
	}
</script>

<style>
</style>
