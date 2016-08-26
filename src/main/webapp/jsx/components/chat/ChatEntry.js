import React from 'react';
import ReactDOM from 'react-dom';

export default class ChatEntry extends React.Component {
	
	constructor(props){
		super(props);
		this.state = {
			message: ''
		};
	}

	componentDidMount(){
		ReactDOM.findDOMNode(this.refs.textInput).focus();
	}
	
	handleSubmit(e) {
		e.preventDefault();
		if (this.state.message.length == 0)
			return;
		this.props.onMessageSubmit(this.state.message); 
	  this.setState({ message: '' });
	}

	handleEnterKey(e) {
		if (e.key == 'Enter'){
			this.handleSubmit(e);
		}
	}

	handleInput(event) {
		this.setState({message: event.target.value});
	}
	
	render(){
		return (
				<div className="messageForm">
						<div className="input-group">	
							<span className="input-group-addon">
								<i className="material-icons">message</i>
							</span>
							<div className="form-group is-empty">
								<input type="text" className="form-control" placeholder="Write your message here..." 
										value={this.state.message} 
										onKeyPress={this.handleEnterKey.bind(this)} 
										onChange={this.handleInput.bind(this)} 
										ref="textInput"/>
							</div>
							<span className="input-group-btn" style={{'paddingTop': '27px'}}>
								<button type="submit" className="btn btn-default btn-sm" onClick={this.handleSubmit.bind(this)}>Send</button>
							</span>
						</div>
				</div>
		);
	}
}

ChatEntry.propTypes = { 
		onMessageSubmit: React.PropTypes.func.isRequired
};      