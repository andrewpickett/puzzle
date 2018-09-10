const path = require('path');
const webpack = require('webpack');
const CopyWebpackPlugin = require('copy-webpack-plugin');

module.exports = {
	entry: {
		app: './src/js/app.js',
		vendor: ['vue', 'vue-router', 'vue2-filters', 'axios', 'jquery', 'bootstrap', 'showdown', 'moment']
	},
	output: {
		path: path.resolve(__dirname, './public'),
		publicPath: '/public/',
		filename: 'js/[name].js'
	},
	module: {
		rules: [
			{ test: /\.css$/, loader: 'style-loader!css-loader' },
			{ test: /\.vue$/, loader: 'vue-loader' },
			{ test: /\.js$/, loader: 'babel-loader', exclude: /node_modules/ },
			{ test: /\.(woff|woff2)(\?v=\d+\.\d+\.\d+)?$/, loader: 'url-loader?limit=10000&mimetype=application/font-woff' },
			{ test: /\.ttf(\?v=\d+\.\d+\.\d+)?$/, loader: 'url-loader?limit=10000&mimetype=application/octet-stream' },
			{ test: /\.eot(\?v=\d+\.\d+\.\d+)?$/, loader: 'file-loader' },
			{ test: /\.svg(\?v=\d+\.\d+\.\d+)?$/, loader: 'url-loader?limit=10000&mimetype=image/svg+xml' }
		]
	},
	resolve: {
		alias: {
			'vue$': 'vue/dist/vue.esm.js'
		},
		extensions: ['*', '.js', '.vue', '.json']
	},

	plugins: [
		new webpack.ProvidePlugin({
			$: "jquery",
			jQuery: "jquery",
			'window.jQuery': 'jquery',
			ssr: false,
			Popper: ['popper.js', 'default']
		}),
		new webpack.optimize.CommonsChunkPlugin({
			names: ['vendor']
		}),
		new CopyWebpackPlugin([
			{from: 'src/images', to: 'images'},
			{from: 'src/css', to: 'css'}
		])
	]
}
