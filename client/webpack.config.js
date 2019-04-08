const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
	mode: 'development',
  entry: {
		app: './src/index.js',
	},
  output: {
    filename: '[name].bundle.js',
    path: path.resolve(__dirname, 'dist')
	},
	devtool: 'inline-source-map',
	devServer: {
		contentBase: './dist'
	},
	plugins: [
		new HtmlWebpackPlugin({
			title: 'wordgame'
		})
	],
	module: {
	  rules: [
	    {
	      test: /\.css$/,
	      use: [
	        'style-loader',
	        'css-loader'
	      ]
			},
			{
			  test: /\.(png|svg|jpg|gif)$/,
			  use: [
			    'file-loader'
			  ]
			},
			{
			  test: /\.(woff|woff2|eot|ttf|otf)$/,
			  use: [
			    'file-loader'
			  ]
			}
	  ]
	}
};
