import React from 'react';
import ReactDOM from 'react-dom';
import ErrorNotification from './components/ErrorNotification.js';
import NewChat from './components/chat/NewChat.js';
import GoogleMaps from './components/maps/GoogleMaps.js';
import UpdateProfile from './components/profile/UpdateProfile.js';
import Chat from './components/chat/Chat.js';

export default class AroundMe extends React.Component {
  
 constructor(props) {
    super(props);
    // init default state
    this.state = {
    		lat: 37.774929,
        lng: -122.419416,
        html5Geolocation: false,
        availableChats: [],
        showNewChatModal: false,
        errorMessage: null,
        chatId: -1
    };
    let self = this;
    // get current location
    if (navigator.geolocation) {
    	navigator.geolocation.getCurrentPosition((position) => {
    		const lat = position.coords.latitude;
    		const lng = position.coords.longitude;
    		self.refs.googleMaps.showCurrentUserPos(lat, lng)
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
		if (this.previousBounds && this.previousBounds.topRightLat == data.topRightLat)
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
  
  newChatOnLocation(map, e){
	  this.setState({
		  lat: e.latLng.lat(),
		  lng: e.latLng.lng(),
		  showNewChatModal: true
	  })
  }
  
  showNewChat(){
	  if (!this.state.currentUser){
		  window.location.replace("/loginPage");
		  return;
	  }
	  this.setState({
		  lat: this.state.userLat,
		  lng: this.state.userLng,
		  showNewChatModal: true
	  })
  }
  
	submitNewChat(chatName, lat, lng){
		var self = this;
		app.doPost({
		 	url: '/chat/new',
		 	data: {
		 		'name': chatName,
		 		'lat': lat,
		 		'lng': lng
		 	}, 
		 	callback: (status, data) => {
		 		if (status == 'SUCCESS'){
		 			let chats = self.state.availableChats;
		 			chats.push(data);
		 			self.setState({
		 				availableChats: chats, 
		 				showNewChatModal: false
		 			});
		 		} else {
		 			if (data.code == 403){
		 				self.setState({
		 					  errorMessage: 'You need to log in first!'
		 				 });
		 			}
		 		}
		 	}
		});
	}
  
	joinChat(chatId){
		this.setState({
			chatId: chatId
		});
	}
	
	leaveChat(){
		this.setState({
			chatId: -1
		});
	}
	
 	updateUser(user){
		this.setState({currentUser: user});
  }
  
 	clearErrorMessage(){
 		this.setState({errorMessage: null});
 	}
 	
  render () {
	  let showError = (this.state.errorMessage) ? <ErrorNotification errorMessage={this.state.errorMessage} clear={this.clearErrorMessage.bind(this)}/> : '';
	  let needsUpdateProfile = (this.state.currentUser && !this.state.currentUser.userName) ? <UpdateProfile user={this.state.currentUser} updateUser={this.updateUser.bind(this)}/> : '';
	  let showNewChatModal = (this.state.showNewChatModal) ? <NewChat submitNewChat={this.submitNewChat.bind(this)} lat={this.state.lat} lng={this.state.lng} /> : ''; 
	  return (
  		<div>
  			<button className="btn btn-success btn-fab btn-fab-mini btn-round" id="newChatButton" data-toggle="modal" onClick={this.showNewChat.bind(this)}>
					<i className="material-icons">add</i>
					<div className="ripple-container"></div>
				</button>
				
				<GoogleMaps ref="googleMaps"
	  				lat={37.774929} 
	  				lng={-122.419416} 
	  				pois={this.state.availableChats}
	  				onDragend={this.loadChatsInArea.bind(this)}
	  				onClick={this.newChatOnLocation.bind(this)} 
						onJoinChat={this.joinChat.bind(this)} />
				
				<Chat id={this.state.chatId} leaveChat={this.leaveChat.bind(this)}/>
				
				{needsUpdateProfile}
				{showNewChatModal}
				{showError}
			</div>
  	);
  }
}

ReactDOM.render(<AroundMe />, document.getElementById('aroundMeDiv'));