import React from 'react';
import ReactDOM from 'react-dom';
import ChatEntry from './ChatEntry.js';
import ChatWindow from './ChatWindow.js';
import ConnectedUsersWindow from './ConnectedUsersWindow.js';

export default class Chat extends React.Component {
	
	constructor(props){
		super(props);
		this.state = {
			messages: [],
			users: []
		}
		this.id = -1;
	}
	
	componentDidUpdate(){
		if (this.props.id > 0 && this.props.id != this.id){
			this.id = this.props.id;
			const chatModal = ReactDOM.findDOMNode(this.refs.chatModal);
			$(chatModal).modal('show');
			let ws = new WebSocket("ws://localhost:8080/socket");
			this.stompClient = Stomp.over(ws);
			this.subscribeEndpoint = "/chat/" + this.props.id;
			this.publishEndpoint = "/app" + this.subscribeEndpoint;
			
			this.stompClient.connect({}, (frame)=>{
				
				// subscribe to all the stuff we need
				this.stompClient.subscribe(this.subscribeEndpoint + "/join", (data) => {
					let user = JSON.parse(data.body);
					let users = this.state.users;
					users.push(user);
					this.setState({
						users: users
					});
				});
				
				this.stompClient.subscribe(this.subscribeEndpoint + "/leave", (data) => {
					let user = JSON.parse(data.body);
					let users = this.state.users;
					users = users.filter((e) => e.id !== user.id);
					this.setState({
						users: users
					});
				});
				
				this.stompClient.subscribe(this.subscribeEndpoint + "/message", (data) => {
					let message = JSON.parse(data.body);
					let messages = this.state.messages;
					messages.push(message);
					this.setState({
						messages: messages
					});
				});
				
				// send that we joined
				this.stompClient.send(this.publishEndpoint + "/join", {}, {});
				
			});
		}
	}
	
	leaveChat(){
		const chatModal = ReactDOM.findDOMNode(this.refs.chatModal);
		$(chatModal).modal('hide');
		
		if (this.stompClient){
			// send that we left
			this.stompClient.send(this.publishEndpoint + "/leave", {}, {});
			this.stompClient.disconnect();
		}
		this.props.leaveChat(this.props.id);
	}
	
	componentWillUnmount() {
		if (this.stompClient){
			// send that we left
			this.stompClient.send(this.publishEndpoint + "/leave", {}, {});
			this.stompClient.disconnect();
		}
	}
	
	sendMessage(message) {
		this.stompClient.send(this.publishEndpoint + "/message", {}, JSON.stringify({'message': message}));
	}
	
	render(){
		return(
				<div className="modal fade" id="chatModal" role="dialog" ref="chatModal" data-backdrop="static">
					<div className="modal-dialog large">
						<div className="modal-content">
							<div className="card card-nav-tabs">
								<div className="header header-success">
									<div className="nav-tabs-navigation">
										<div className="nav-tabs-wrapper">
											<ul className="nav nav-tabs" data-tabs="tabs">
												<li className="active">
													<a href="#chat" data-toggle="tab" aria-expanded="false">
														<i className="material-icons">chat</i>
														Chat
														<div className="ripple-container"></div>
													</a>
												</li>
												<li>
													<a href="#users" data-toggle="tab" aria-expanded="true">
														<i className="material-icons">faces</i>
														Users
														<div className="ripple-container"></div>
													</a>
												</li>
												<li className="pull-right">
													<a>
														<button type="button" className="close" onClick={this.leaveChat.bind(this)}>
															<i className="material-icons">clear</i>
														</button>
													</a>
												</li>
											</ul>
										</div>
									</div>
								</div>
								<div className="content" style={{'minHeight': '60vh'}}>
									<div className="tab-content text-center">
										<div className="tab-pane active" id="chat">
											<ChatWindow messages={this.state.messages} />
											<ChatEntry onMessageSubmit={this.sendMessage.bind(this)} />
										</div>
										<div className="tab-pane" id="users">
											<ConnectedUsersWindow users={this.state.users} />
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
		);
	}
}

Chat.propTypes = { 
		id: React.PropTypes.number.isRequired,
		leaveChat: React.PropTypes.func.isRequired,
};          
					