import React from 'react';
import ReactDOM from 'react-dom';

export default class GoogleMaps extends React.Component {
	
	componentDidMount() {
    	this.loadMap();
  	}

	loadMap() {
		const mapRef = this.refs.map;
      	const node = ReactDOM.findDOMNode(mapRef);

      	let zoom = 14;
      	let lat = 37.774929;
      	let lng = -122.419416;
      	const center = new google.maps.LatLng(lat, lng);
      	const mapConfig = Object.assign({}, {
 	    	center: center,
    	    zoom: zoom
      	});
      	this.map = new google.maps.Map(node, mapConfig);
	}
	
	render() {
		return (
			<div id="map" ref='map'>
            	Loading map...
            </div>
		);
	}
}