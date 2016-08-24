import React from 'react';
import ReactDOM from 'react-dom';
import NewChat from './components/chat/NewChat.js';
import GoogleMaps from './components/maps/GoogleMaps.js';
import UpdateProfile from './components/profile/UpdateProfile.js';

export default class AroundMe extends React.Component {
  
 constructor(props) {
    super(props);
    // init default state
    this.state = {
    		lat: 37.774929,
        lng: -122.419416,
        html5Geolocation: false,
        availableChats: []
    };
    
    // get current location
    if (navigator.geolocation) {
    	navigator.geolocation.getCurrentPosition((position) => {
    		const lat = position.coords.latitude;
    		const lng = position.coords.longitude;
    		this.setState({
    			lat: lat,
    			lng: lng,
    			html5Geolocation: true
    		});
    	}, (error) => {
    		alert(error);
    	}, 
    	{maximumAge:60000, timeout:5000, enableHighAccuracy:true});
    }
    
    this.getCurrentUser();
  }
  
	getCurrentUser(){
		var self = this;
		app.doGet({
    		url: '/profile/currentUser',
     		callback: (status, data) => {
     			if (status == 'SUCCESS'){
     				self.setState({currentUser: data});
     			}
     		}
    	});
	}
  
	loadChatsInArea(map){
  		if (!map.getBounds())
			return;
		let data = {
			'topRightLat': map.getBounds().getNorthEast().lat(),
			'topRightLng': map.getBounds().getNorthEast().lng(),
			'bottomLeftLat': map.getBounds().getSouthWest().lat(),
			'bottomLeftLng': map.getBounds().getSouthWest().lng()
		};
		if (this.previousBounds && this.previousBounds.topRightLat == data.topRightLat && this.previousBounds.topRightLng == data.topRightLng)
			return;
		this.previousBounds = data;
	  	app.doGet({
	    	url: '/chat/list',
	    	data: data,
	    	callback: (status, data) => {
	  			if (status == 'SUCCESS'){
	  				this.setState({availableChats: data});
	  			} 
	  			else {
	  				// do something
	  			}
	    	}
	    });
  	}
  
  	newChatOnLocation(map){
  		
  	}
  
	submitNewChat(chatName, chatModal){
		app.doPost({
		 	url: '/chat/new',
		 	data: {
		 		'name': chatName,
		 		'lat': this.state.lat,
		 		'lng': this.state.lng
		 	}, 
		 	callback: (status, data) => {
		 		if (status == 'SUCCESS'){
		 			$(chatModal).modal('hide');
		 			let chats = self.state.availableChats;
		 			chats.push(data);
		 			self.setState({availableChats: chats});
		 		} 
		 	}
		  });
	}
  
 	updateUser(user){
		this.setState({currentUser: user});
  	}
  
  render () {
	let needsUpdateProfile = (this.state.currentUser && !this.state.currentUser.userName) ? <UpdateProfile user={this.state.currentUser} updateUser={this.updateUser.bind(this)}/> : '';
	return (
  		<div>
  			<button className="btn btn-success btn-fab btn-fab-mini btn-round" id="newChatButton" data-toggle="modal" data-target="#newChatModal">
				<i className="material-icons">add</i>
				<div className="ripple-container"></div>
			</button>
				
			<NewChat submitNewChat={this.submitNewChat.bind(this) }/>
			
			{needsUpdateProfile}
			
			<GoogleMaps 
  				lat={this.state.lat} 
  				lng={this.state.lng} 
  				html5Geolocation={this.state.html5Geolocation} 
  				pois={this.state.availableChats}
  				onDragend={this.loadChatsInArea.bind(this)}
  				onClick={this.newChatOnLocation.bind(this)} />
  		</div>
  	);
  }
}

ReactDOM.render(<AroundMe />, document.getElementById('aroundMeDiv'));