var webpack = require('webpack');
var path = require('path');

var BUILD_DIR = path.resolve(__dirname, 'src/main/webapp/bundle');
var APP_DIR = path.resolve(__dirname, 'src/main/webapp/jsx');

var config = {
	entry : {
		'main': APP_DIR + '/main.js',
		'main2': APP_DIR + '/main2.js',
	},
	output : {
		path : BUILD_DIR,
		filename : '[name].bundle.js'
	},
	module : {
		loaders : [ {
			test : /\.js?/,
			include : APP_DIR,
			loader : 'babel'
		} ]
	}
};

module.exports = config;