import React from 'react';
import ReactDOM from 'react-dom';

export default class ChatWindow extends React.Component {
	
	constructor(props){
		super(props);
	}

	componentWillUpdate(nextProps, nextState) {
		this.shouldScrollToBot = chatHolder.scrollTop + chatHolder.clientHeight == chatHolder.scrollHeight;
	}

	imageLoadCallback (){
		const chatHolder = ReactDOM.findDOMNode(this.refs.chatHolder);
		// only scroll if we are at the bottom
		if (this.shouldScrollToBot){
			const scrollTo = chatHolder.scrollHeight - chatHolder.clientHeight;
			chatHolder.scrollTop = scrollTo;
		}
	}

	render(){
		return (
			<div id="chatHolder" ref="chatHolder">  	
			{
				this.props.messages.map((message, i) => {
					return (
						<div className="row" key={i} style={{'marginBottom':'10px'}}>
							<div className="card card-raised vertical-align">
								<div className="col-sm-1 text-center center-block  addSpace">
									<img src="/resources/images/face.png" onLoad={this.imageLoadCallback.bind(this)} className="img-circle" />
									<div>{message.user.userName}</div>
		             			</div>
								<div className="col-sm-10 text-left addSpace">
										{message.message}
								</div>
								<div className="col-sm-1 text-center addSpace">
										{message.createDate.hour}:{message.createDate.minute < 10 ? '0' + message.createDate.minute : message.createDate.minute}:{message.createDate.second}
								</div>
							</div>
            			</div>
          			)
        		})
      		}
			</div>	
		);
	} 
}

ChatWindow.propTypes = { 
	messages: React.PropTypes.array.isRequired
};      