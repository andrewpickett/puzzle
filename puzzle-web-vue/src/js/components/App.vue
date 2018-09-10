<template>
	<div>
		<nav class="navbar navbar-default navbar-fixed-top" v-if="auth.user.authenticated">
			<div class="container">
				<ul class="nav navbar-nav">
					<li>
						<router-link to="/">Home</router-link>
					</li>
					<li>
						<router-link to="/completed">Completed</router-link>
					</li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li v-if="auth.user.admin">
						<router-link to="/admin">Admin</router-link>
					</li>
					<li>
						<a href="/logout" @click.prevent="logout()">Logout</a>
					</li>
				</ul>
			</div>
		</nav>
		<router-view></router-view>
	</div>
</template>

<script>
	import auth from '../auth';

	export default {
		data() {
			return {
				auth: auth
			}
		},
		methods: {
			logout() {
				auth.logout();
			}
		}
	}
</script>

<style>
</style>
