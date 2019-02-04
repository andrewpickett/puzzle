import Vue from 'vue';
import VueRouter from 'vue-router';
import auth from './auth';

Vue.use(VueRouter);

function checkUserAuth(to, from, next) {
	auth.checkAuth();
	if (auth.user.authenticated) {
		next();
	} else {
		next('/login');
	}
}

export default new VueRouter({
	routes: [
		{
			path: '/',
			name: 'default',
			component: require('./components/Home.vue').default,
			beforeEnter: checkUserAuth
		},
		{
			path: '/completed',
			name: 'completed',
			component: require('./components/completed/CompletedPuzzles.vue').default,
			beforeEnter: checkUserAuth
		},
		{
			path: '/admin',
			name: 'admin',
			component: require('./components/admin/Admin.vue').default,
			beforeEnter: checkUserAuth
		},
		{
			path: '/progress',
			name: 'progress',
			component: require('./components/Progress.vue').default,
			beforeEnter: checkUserAuth
		},
		{
			path: '/instructions',
			name: 'instructions',
			component: require('./components/Instructions.vue').default,
			beforeEnter: checkUserAuth
		},
		{
			path: '/login',
			name: 'login',
			component: require('./components/login/Login.vue').default
		},
		{
			path: '*',
			redirect: '/'
		}
	]
});
