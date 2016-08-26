import React from 'react';
import ReactDOM from 'react-dom';

export default class ChatWindow extends React.Component {
	
	constructor(props){
		super(props);
	}

	render(){
		return (
			<div id="chatHolder">	
			{
				this.props.messages.map((message, i) => {
					return (
						<div className="row" key={i} style={{'marginBottom':'10px'}}>
							<div className="card card-raised vertical-align">
								<div className="col-sm-1 text-center center-block  addSpace">
									<img src="/resources/images/face.png" className="img-circle" />
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