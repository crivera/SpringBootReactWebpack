import React from 'react';
import ReactDOM from 'react-dom';
import GoogleMaps from './components/maps/GoogleMaps.js';


export default class AroundMe extends React.Component {
  
  constructor(props) {
    super(props);
    this.state = {
    		chatName: '', 
    		lat: 37.774929,
        lng: -122.419416,
        html5Geolocation: false,
        availableChats: []
  	};
    
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
    
  }
  
  handleInput(event) {
    this.setState({chatName: event.target.value.substr(0, 30)});
  }
  
  submitNewChat(){
  	let chatName = this.state.chatName;
  	if (chatName.length == 0) 
  		return;
  	const newChatModal = ReactDOM.findDOMNode(this.refs.newChatModal);
  	let self = this;
  	app.doAuthenicatedPost({
  		url: '/chat/new',
  		data: {
  			'name': chatName,
  			'lat': this.state.lat,
  			'lng': this.state.lng
  		}, 
  		callback: (status, data) => {
  			if (status == 'SUCCESS'){
  				$(newChatModal).modal('hide');
  				let chats = self.state.availableChats;
  				chats.push(data);
  				self.setState({availableChats: chats});
  			} 
  			else {
  				if (data.code == 100){
  					window.location.replace("/loginPage");
  				}
  			}
  		}
  	});
  }
  
  loadChatsInArea(data){
	  let self = this;
	  app.doGet({
	    	url: '/chat/list',
	    	data: data,
	    	callback: (status, data) => {
	  			if (status == 'SUCCESS'){
	  				self.setState({availableChats: data});
	  			} 
	  			else {
	  				// do something
	  			}
	    	}
	    });
  }
  
  render () {
  	return (
  		<div>
  			<button className="btn btn-success btn-fab btn-fab-mini btn-round" id="newChatButton" data-toggle="modal" data-target="#newChatModal">
					<i className="material-icons">add</i>
					<div className="ripple-container"></div>
				</button>
				<div className="modal fade" id="newChatModal" role="dialog" style={{display: 'none'}} ref="newChatModal">
					<div className="modal-dialog">
						<div className="modal-content">
							<div className="modal-header">
								<button type="button" className="close" data-dismiss="modal">
									<i className="material-icons">clear</i>
								</button>
								<h4 className="modal-title">New Chat</h4>
							</div>
							<div className="modal-body">
								<p>All we need is a name for your chat:</p>
								<div className="form-group is-empty">
									<input type="text" value={this.state.chatName} onChange={this.handleInput.bind(this)} placeholder="Name" className="form-control"/>
			            <span className="material-input"></span>
			          </div> 
							</div>
							<div className="modal-footer">
								<button onClick={this.submitNewChat.bind(this)} type="button" className="btn btn-default btn-simple">Create<div className="ripple-container"></div></button>
								<button type="button" className="btn btn-danger btn-simple" data-dismiss="modal">Cancel<div className="ripple-container"><div className="ripple ripple-on ripple-out"></div></div></button>
							</div>
						</div>
					</div>
				</div>
  			<GoogleMaps 
  				lat={this.state.lat} 
  				lng={this.state.lng} 
  				html5Geolocation={this.state.html5Geolocation} 
  				pois={this.state.availableChats}
  				mapLocationUpdate={this.loadChatsInArea.bind(this)}/>
  		</div>);
  }
}

ReactDOM.render(<AroundMe />, document.getElementById('aroundMeDiv'));