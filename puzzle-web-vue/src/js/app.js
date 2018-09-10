import Vue from 'vue';
import Vue2Filters from 'vue2-filters';

import router from './routes';

import App from './components/App.vue';

require('bootstrap/dist/css/bootstrap.min.css');
require('../css/main.css');

Vue.use(Vue2Filters);

new Vue({
	el: '#app',
	template: '<App />',
	components: {App},
	router: router
});
