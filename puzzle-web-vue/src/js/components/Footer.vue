<template>
	<footer class="footer">
		<div class="container" v-if="showGamification">
			<div class="footer-content">
				<div class="row">
					<div class="col-sm text-left">
						#{{summary.puzzleNum}}
					</div>
					<div class="col-sm text-center">
						Total Score: {{summary.currentScore}}
					</div>
					<div class="col-sm text-right">
						{{summary.scorePerc}}%
					</div>
				</div>
			</div>
			<div class="puzzle-progress" :style="{width: (puzzlePerc * 100) + '%'}">&nbsp;</div>
		</div>
	</footer>
</template>

<script>
	import config from '../config';

	export default {
		data() {
			return {
				showGamification: config.GAMIFICATION
			}
		},
		props: ['summary', 'puzzlePerc'],
		updated() {
			if (this.puzzlePerc < 0.3) {
				$(".puzzle-progress").addClass("puzzle-progress-red").removeClass("puzzle-progress-amber");
			} else if (this.puzzlePerc < 0.7) {
				$(".puzzle-progress").addClass("puzzle-progress-amber").removeClass("puzzle-progress-red");
			} else {
				$(".puzzle-progress").removeClass("puzzle-progress-red").removeClass("puzzle-progress-amber");
			}
		}
	}
</script>

<style>
	.footer-content {
		line-height: 48px;
		color: #444444;
	}
	.puzzle-progress {
		height: 2px;
		background-color: #009900;
		width: 100%;
		line-height: 2px;
	}
	.puzzle-progress-red {
		background-color: #990000;
	}
	.puzzle-progress-amber {
		background-color: #996600;
	}
</style>
