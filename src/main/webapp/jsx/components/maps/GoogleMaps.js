import React from 'react';
import ReactDOM from 'react-dom';

export default class GoogleMaps extends React.Component {
	
	constructor(props){
		super(props);
		this.markers = [];
		this.previousLocation = {};
	}
	
	componentDidMount() {
    	this.loadMap();
  }

	componentDidUpdate(){
	 	const center = new google.maps.LatLng(this.props.lat, this.props.lng);
	 	this.map.panTo(center);
	  this.showGeolocation();
	  this.showPlacesOfInterest();
	  this.mapLocationUpdated();
	}
	
	loadMap() {
		const node = ReactDOM.findDOMNode(this.refs.map);
    const mapConfig = Object.assign({}, {
    	center: new google.maps.LatLng(this.props.lat, this.props.lng),
    	zoom: 14
    });

    this.map = new google.maps.Map(node, mapConfig);
    this.showGeolocation();
    this.showPlacesOfInterest();
    this.mapLocationUpdated();
	}
	
	mapLocationUpdated(){
		if (!this.map.getBounds())
			return;
		let data = {
				'topRightLat': this.map.getBounds().getNorthEast().lat(),
				'topRightLng': this.map.getBounds().getNorthEast().lng(),
				'bottomLeftLat': this.map.getBounds().getSouthWest().lat(),
				'bottomLeftLng': this.map.getBounds().getSouthWest().lng(),
		};
		if (this.previousLocation.topRightLat == data.topRightLat && this.previousLocation.bottomLeftLat == data.bottomLeftLat)
			return;
		this.previousLocation = data;
		this.props.mapLocationUpdate(data);
	}
	
	showGeolocation(){
		if (this.props.html5Geolocation){
			let image = {
					url: '/resources/images/location.png'
			};
			if (this.myPos)
				this.myPos.setMap(null);
		  this.myPos = new google.maps.Marker({
			  position: {lat: this.props.lat, lng: this.props.lng},
		    map: this.map,
		    icon: image
		  });
		}
	}
	
	showPlacesOfInterest(){
		this.markers.forEach((el) => {
			el.setMap(null);
		});
		this.markers = [];
		let pois = this.props.pois;
		let image = {
				url: '/resources/images/chat.png'
		};
	  pois.forEach((el, i) => {
			let marker = new google.maps.Marker({
			  position: {lat: el.lat, lng: el.lng},
		    map: this.map,
		    icon: image
		  });
		  this.markers.push(marker);
		});
	}
	
	render() {
		return (
			<div id="map" ref='map'>
         	Loading map...
      </div>
		);
	}
}