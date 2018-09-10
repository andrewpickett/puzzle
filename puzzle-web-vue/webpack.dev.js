const webpack = require('webpack');
const merge = require('webpack-merge');
const common = require('./webpack.common.js');

module.exports = merge(common, {
	devServer: {
		historyApiFallback: true,
		noInfo: true,
		overlay: true,
		inline: true,
		hot: true,
		watchOptions: { aggregateTimeout: 300, poll: 1000 }
	},
	plugins: [
		new webpack.HotModuleReplacementPlugin()
	],
	devtool: 'inline-source-map',
	performance: {
		hints: false
	}
});
