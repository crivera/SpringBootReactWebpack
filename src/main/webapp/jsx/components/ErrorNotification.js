import React from 'react';
import ReactDOM from 'react-dom';

export default class ErrorNotification extends React.Component {
	
	constructor(props){
		super(props);
	}

	componentDidMount(){
		setTimeout(this.closeErrorMessage.bind(this), 5000);
	}
	
	closeErrorMessage(){
		this.props.clear();
	}
	
	render(){
		return (
				<div className="alert alert-danger">
					<div className="container-fluid">
						<div className="alert-icon">
							<i className="material-icons">error_outline</i>
						</div>
						<button ref="closeButton" type="button" className="close" data-dismiss="alert" aria-label="Close" onClick={this.closeErrorMessage.bind(this)}>
							<span aria-hidden="true"><i className="material-icons">clear</i></span>
						</button>
			      <b>Error:</b> {this.props.errorMessage}
			    </div>
			  </div>
		);
	}
}


ErrorNotification.propTypes = { 
		errorMessage: React.PropTypes.string.isRequired,
		clear: React.PropTypes.func.isRequired,
};          
					 
						
					