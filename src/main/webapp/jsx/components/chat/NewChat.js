import React from 'react';
import ReactDOM from 'react-dom';

export default class NewChat extends React.Component {
	
	constructor(props){
		super(props);
		this.state = {
	    		chatName: ''
		};
	}

	handleInput(event) {
	   this.setState({chatName: event.target.value.substr(0, 30)});
	}
	  
	submitNewChat(){
		let chatName = this.state.chatName;
	 	if (chatName.length == 0) 
	 		return;
	 	const newChatModal = ReactDOM.findDOMNode(this.refs.newChatModal);
	 	this.props.submitNewChat(chatName, newChatModal);
	}
	
	render () {
	  return (
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
			);
	  }
}

NewChat.propTypes = { 
		submitNewChat: React.PropTypes.func.isRequired
};