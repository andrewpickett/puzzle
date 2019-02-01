<template>
	<div class="panel">
		<!--<div class="panel-heading">
			<h1 v-if="puzzle">{{ puzzle.title }}</h1>
			<h1 v-else>&nbsp;</h1>
		</div>-->
		<div class="panel-body">
			<div v-html="puzzleText()"></div>
		</div>
	</div>
</template>

<script>
	import showdown from 'showdown';

	export default {
		props: ['puzzle'],
		methods: {
			puzzleText() {
				if (this.puzzle) {
					return new showdown.Converter().makeHtml(this.puzzle.description);
				} else {
					return "<p>No current puzzle to work.</p>";
				}
			}
		},
		computed: {
			showHints: function () {
				return (this.puzzle && this.puzzle.hints && this.puzzle.hints.length > 0);
			}
		}
	}
</script>

<style>
</style>
