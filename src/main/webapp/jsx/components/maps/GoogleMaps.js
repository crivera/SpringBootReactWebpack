import React from 'react';
import ReactDOM from 'react-dom';

export default class GoogleMaps extends React.Component {
	
	constructor(props){
		super(props);
		this.markers = [];
	}
	
	componentDidMount() {
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

	componentDidUpdate(){
		this.showPlacesOfInterest();
	}
	
	showCurrentUserPos(lat, lng){
		this.lat = lat;
		this.lng = lng;
		const center = new google.maps.LatLng(lat, lng);
	 	this.map.panTo(center);
	  this.showGeolocation();
	  this.props.onDragend(this.map);
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
				this.e = e;
				if (timeout) {
	    		clearTimeout(timeout);
	    		timeout = null;
	  		}
	  		timeout = setTimeout(() => {
	    		if (this.props[handlerName]) {
	      			this.props[handlerName](this.map, this.e);
	    		}
	  		}, 0);
		}
	}
	
	showGeolocation(){
		if (!this.lat)
			return;
		let image = {
				url: '/resources/images/location.png'
		};
		if (this.myPos)
			this.myPos.setMap(null);
	  this.myPos = new google.maps.Marker({
		 	position: {lat: this.lat, lng: this.lng},
	   	map: this.map,
	   	icon: image
	  });
	}
	
	showPlacesOfInterest(){
		// lets do some optimization here so that it doesnt flicker
		let poisId = this.props.pois.map((e) => {return e.id});
		let markersId = this.markers.map((e) => {return e.id});
		this.markers.forEach((el) => {
			if (poisId.indexOf(el.id) == -1)
				el.setMap(null);
		});
		this.markers = [];
		this.infoWindows = [];
		let pois = this.props.pois;
		let image = {
				url: '/resources/images/chat.png'
		};
	  pois.forEach((el, i) => {
		  if (markersId.indexOf(el.id) > -1)
			  return;
		  let marker = new google.maps.Marker({
			  	position: {lat: el.lat, lng: el.lng},
			    map: this.map,
			    icon: image,
			    id: el.id
			});
		  let infowindow = new google.maps.InfoWindow({
			  content: '<span style="text-transform: uppercase;">'+el.name+'</span>'
		  });
		  
		  marker.addListener('mouseover', () => {
			    infowindow.open(this.map, marker);
			});

			// assuming you also want to hide the infowindow when user mouses-out
			marker.addListener('mouseout', () => {
			    infowindow.close();
			});
			
			marker.addListener('click', () => {
					this.props.onJoinChat(el.id);
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
	pois: React.PropTypes.array.isRequired,
	onClick: React.PropTypes.func,
	onDragend: React.PropTypes.func,
	onJoinChat: React.PropTypes.func
};
