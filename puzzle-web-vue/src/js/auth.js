import config from './config';
import router from './routes';
import axios from 'axios';

axios.defaults.baseURL = config.BASE_URL;
axios.defaults.withCredentials = true;

export default {
	user: {
		authenticated: false,
		admin: false
	},

	login(creds, redirect) {
		axios.post('/login', creds, config.AXIOS_CONFIG)
			.then(response => {
				sessionStorage.setItem(config.JWT_STORAGE_KEY, response.headers.authorization);
				this.user.authenticated = true;
				if (response.headers["x-user-admin"]) {
					this.user.admin = true;
				}

				if (redirect) {
					router.push(redirect);
				}
			})
			.catch(error => {
				console.log(error);
			});
	},

	logout() {
		sessionStorage.removeItem(config.JWT_STORAGE_KEY);
		this.user.authenticated = false;
		this.user.admin = false;
		router.push('/login');
	},

	checkAuth() {
		var jwt = sessionStorage.getItem(config.JWT_STORAGE_KEY);

		if (jwt) {
			this.user.authenticated = true;
		} else {
			this.user.authenticated = false;
		}
	},

	getAuthHeader() {
		return {
			'Authorization': 'Bearer ' + sessionStorage.getItem(config.JWT_STORAGE_KEY)
		}
	}
}
