import React from 'react';
import ReactDOM from 'react-dom';

export default class GoogleMaps extends React.Component {
	
	constructor(props){
		super(props);
		this.markers = [];
	}
	
	componentDidMount() {
    	this.loadMap();
  	}

	componentDidUpdate(){
	 	const center = new google.maps.LatLng(this.props.lat, this.props.lng);
	 	this.map.panTo(center);
	  	this.showGeolocation();
	  	this.showPlacesOfInterest();
	  	this.props.onDragend(this.map);
	}
	
	loadMap() {
		const node = ReactDOM.findDOMNode(this.refs.map);
    	const mapConfig = Object.assign({}, {
	    	center: new google.maps.LatLng(this.props.lat, this.props.lng),
	    	zoom: 14
	    });
	
	    this.map = new google.maps.Map(node, mapConfig);
	    
	    const evtNames = ['click', 'dragend'];
	    evtNames.forEach(e => {
        	this.map.addListener(e, this.handleEvent(e));
      	});
	    
	    this.showGeolocation();
	    this.showPlacesOfInterest();
	}
	
	handleEvent(evtName) {
    	const camelize = function(str) {
	  		return str.split(' ').map(function(word){
	    		return word.charAt(0).toUpperCase() + word.slice(1);
	  		}).join('');
		}
	
    	let timeout;
    	const handlerName = 'on' + camelize(evtName);

    	return (e) => {
      		if (timeout) {
        		clearTimeout(timeout);
        		timeout = null;
      		}
      		timeout = setTimeout(() => {
        		if (this.props[handlerName]) {
          			this.props[handlerName](this.map);
        		}
      		}, 0);
    	}
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

GoogleMaps.propTypes = { 
	lat: React.PropTypes.number.isRequired,
	lng: React.PropTypes.number.isRequired,
	html5Geolocation: React.PropTypes.bool.isRequired, 
	pois: React.PropTypes.array.isRequired,
	onClick: React.PropTypes.func,
	onDragend: React.PropTypes.func
};
