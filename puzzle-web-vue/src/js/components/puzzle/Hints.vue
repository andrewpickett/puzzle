<template>
	<div v-if="showHints" class="hint-wrapper">
   	<ul>
			<li v-for="hint in hints" v-if="!hint.available">{{ hint.description }}</li>
		</ul>
	</div>
</template>

<script>
	export default {
		props: ['hints'],
		computed: {
			showHints: function () {
				return (this.hints && this.hints.length > 0);
			}
		}
	}
</script>

<style>
	.hint-wrapper {
		padding: 45px 0px;
		color: #4cae4c;
		font-family: Consolas, "Courier New", monospace;
		font-size: 20px;
	}

	.hint-wrapper ul {
		list-style-type: none;
		margin: 0px;
		padding: 0px;
	}
</style>
