export default {
	BASE_URL: buildBaseUrl(),
	JWT_STORAGE_KEY: "puzzle.jwt",
	AXIOS_CONFIG: {
	},
	allowedAnonymousRoutes: ['login'],
	GAMIFICATION: true
}

function buildBaseUrl() {
	let url = process.env.SERVER_SIDE.baseUrl;
	if (process.env.SERVER_SIDE.port) {
		url += ':' + process.env.SERVER_SIDE.port;
	}
	return url;
}
