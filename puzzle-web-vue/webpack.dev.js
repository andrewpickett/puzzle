const webpack = require('webpack');
const merge = require('webpack-merge');
const common = require('./webpack.common.js');

module.exports = merge(common, {
	mode: 'development',
	devServer: {
		historyApiFallback: true,
		noInfo: true,
		overlay: true,
		inline: true,
		hot: true,
		watchOptions: { aggregateTimeout: 300, poll: 1000 }
	},
	plugins: [
		new webpack.HotModuleReplacementPlugin(),
		new webpack.DefinePlugin({
			'process.env': {
				NODE_ENV: '"development"',
				SERVER_SIDE: {
					baseUrl: '"http://localhost"',
					port: JSON.stringify(process.env.SERVER_PORT)
				}
			}
		}),
	],
	devtool: 'inline-source-map',
	performance: {
		hints: false
	}
});
