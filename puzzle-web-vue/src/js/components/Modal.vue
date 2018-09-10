<template>
	<div class="modal fade" :id="id" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<span class="modal-title">{{ title }}</span>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<p>
						<slot></slot>
					</p>
				</div>
				<div class="modal-footer">
					<button id="closeModal" type="button" class="btn btn-primary" data-dismiss="modal">{{ closeText }}</button>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	export default {
		props: ['id', 'title', 'closeText']
	}
</script>

<style>
	.modal-title {
		font-weight: bolder;
	}

	.modal-header {
		background-color: #5cb85c;
	}

	.modal-footer {
		padding: 10px;
	}

	.modal-dialog {
		margin: 120px auto;
	}
</style>
