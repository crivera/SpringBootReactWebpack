import React from 'react';
import ReactDOM from 'react-dom';

export default class ConnectedUsersWindow extends React.Component {
	
	constructor(props){
		super(props);
	}

	render() {
		return (
			<div className="row" id="userHolder" ref="userHolder">
				{
				this.props.users.map((user, i) => {
					let margin = {'marginTop': i < 3 ? '0px': '20px'};
					return (
						<div key={i} className="col-md-4" style={margin}>
							<div className="card card-raised vertical-align" style={{'padding': '10px'}}>
								<img src="/resources/images/face.png" className="img-circle" />
								<div>{user.userName}</div>
							</div>
            			</div>
          			)
        		})
      		}
			</div>
		);
	}
}

ConnectedUsersWindow.propTypes = { 
	users: React.PropTypes.array.isRequired
};      