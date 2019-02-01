<template>
	<div>
		<nav class="navbar navbar-expand-lg fixed-top" v-if="auth.user.authenticated">
			<div class="container">
				<div class="collapse navbar-collapse" id="navbarText">
					<ul class="navbar-nav mr-auto">
						<li class="nav-item active">
							<router-link to="/" class="nav-link">Home</router-link>
						</li>
						<li class="nav-item">
							<router-link to="/instructions" class="nav-link">Instructions</router-link>
						</li>
					</ul>
					<span>
						<a href="/logout" @click.prevent="logout()" class="nav-link">Logout</a>
    				</span>
				</div>
			</div>
		</nav>
		<div class="main-content">
			<router-view></router-view>
		</div>
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
	.nav-link {
		color: #9d9d9d;
	}
	.nav-link:hover {
		color: #737373;
	}
	nav {
		border-bottom: 1px solid #737373;
	}
	.main-content {
		padding-top: 15px;
	}
</style>
