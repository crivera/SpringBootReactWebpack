import React from 'react';
import ReactDOM from 'react-dom';

export default class Chat extends React.Component {
	
	constructor(props){
		super(props);
	}

	componentDidMount(){
		
	}
	
	componentDidUpdate(){
		
	}
	
	render(){
		return(
				<div className="col-md-6" id="chatModal">
					<div className="card card-nav-tabs">
						<div className="header header-success">
							<div className="nav-tabs-navigation">
								<div className="nav-tabs-wrapper">
									<ul className="nav nav-tabs" data-tabs="tabs">
									</ul>
								</div>
							</div>
						</div>
						<div className="content">
							<div className="tab-pane" id="chat">
								<p>I think that’s a responsibility that I have, to push possibilities, to show people, this is the level that things could be at. So when you get something that has the name Kanye West on it, it’s supposed to be pushing the furthest possibilities. I will be the leader of a company that ends up being worth billions of dollars, because I got the answers. I understand culture. I am the nucleus.</p>
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
					