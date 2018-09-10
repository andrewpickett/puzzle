<template>
	<div id="previousGuesses" class="panel panel-danger" v-if="guesses">
		<div class="panel-heading">Previous Guesses</div>
		<div class="panel-body">
			<table class="table table-sm">
				<thead class="thead-light">
					<tr>
						<th scope="col">Time</th>
						<th scope="col">Guess</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="guess in guesses">
						<td scope="row">{{ formatDate(guess.guessTime) }}</td>
						<td>{{ guess.value }}</td>
					</tr>
				</tbody>
			</table>
			<div v-if="!guesses">
				<p>No previous guesses for this puzzle.</p>
			</div>
		</div>
	</div>
</template>

<script>
	import moment from 'moment';

	export default {
		methods: {
			formatDate(value) {
				return moment(value).format('YYYY/MM/DD HH:mm:ss');
			}
		},
		props: {
			guesses: {
				type: Array,
				default: function () {
					return null;
				}
			}
		}
	}
</script>

<style>
</style>
