var webpack = require('webpack');
var path = require('path');

var BUILD_DIR = path.resolve(__dirname, 'src/main/webapp/bundle');
var APP_DIR = path.resolve(__dirname, 'src/main/webapp/jsx');

var config = {
	entry : {
		'aroundMe': APP_DIR + '/aroundMe.js'
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
