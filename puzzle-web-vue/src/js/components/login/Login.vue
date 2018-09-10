<template>
	<div class="container">
		<form class="form-signin" @submit.prevent="submit()">
			<div class="form-group row">
				<input type="text" class="form-control form-control-lg" placeholder="Name" required="required"
						 autofocus="autofocus" autocomplete="off" v-model="creds.name"/>
				<input type="password" class="form-control form-control-lg" placeholder="Password" required="required"
						 v-model="creds.password"/>
			</div>

			<div class="form-group row">
				<button type="submit" class="btn btn-lg btn-primary btn-block">Sign in</button>
			</div>
		</form>
	</div>
</template>

<script>
	import auth from '../../auth';

	export default {
		data() {
			return {
				creds: {
					name: '',
					password: ''
				}
			}
		},
		methods: {
			submit() {
				auth.login(this.$data.creds, '/');
			}
		}
	}
</script>

<style>
	.form-signin {
		max-width: 350px;
		margin: 0 auto;
	}
</style>
