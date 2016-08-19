import React from 'react';
import ReactDOM from 'react-dom';

export default class UpdateProfile extends React.Component {
	
	constructor(props){
		super(props);
		this.state = {
				email: this.props.user.email ? this.props.user.email : '',
				userName: this.props.user.userName ? this.props.user.userName : '',
				errorClass: ''
		}
	}
	
	componentDidMount(){
		const updateProfileModal = ReactDOM.findDOMNode(this.refs.updateProfileModal);
		$(updateProfileModal).modal('show');
	}
	
	handleInput(field, event){
		let change = {};
	  change[field] = event.target.value;
	  change['errorClass'] = '';
		this.setState(change);
	}
	
	updateProfile(){
		if (this.state.userName.length == 0){
			this.setState({errorClass: 'has-error'});
			return;
		}
		app.doPost({
			url: '/profile/update',
			data: {
				'userName': this.state.userName,
				'email': this.state.email
			}, 
			callback: (status, data) => {
				if (status == 'SUCCESS'){
					const updateProfileModal = ReactDOM.findDOMNode(this.refs.updateProfileModal);
					$(updateProfileModal).modal('hide');
					this.props.updateUser(data);
				}
			}
		});
	}
	
	render(){
		let inputClassName = 'input-group ' + this.state.errorClass;
	
		return(
				<div className="modal fade" id="updateProfileModal" role="dialog" ref="updateProfileModal" data-backdrop="static" data-keyboard="false"> 
					<div className="modal-dialog">
						<div className="modal-content">
							<div className="modal-header">
								<h4 className="modal-title">Update Profile</h4>
							</div>
							<div className="modal-body">
								<p>We need to know a little more about you before we start:</p>
								<div className={inputClassName}>
									<span className="input-group-addon">
										<i className="material-icons">face</i>
									</span>
									<div className="form-group is-empty">
										<input type="text" value={this.state.userName} onChange={this.handleInput.bind(this, 'userName')} placeholder="* Username..." className="form-control" required/>
										<span className="material-input"></span>
									</div>
								</div>
								<div className="input-group">
									<span className="input-group-addon">
										<i className="material-icons">email</i>
									</span>
									<div className="form-group is-empty">
										<input type="text" value={this.state.email} onChange={this.handleInput.bind(this, 'email')} placeholder="Email..." className="form-control" />
										<span className="material-input"></span>
									</div>
								</div>
							</div>
							<div className="modal-footer">
								<button onClick={this.updateProfile.bind(this)} type="button" className="btn btn-default">Update<div className="ripple-container"></div></button>
							</div>
					</div>
				</div>
			</div>
		);
	}
}

UpdateProfile.propTypes = { 
		user: React.PropTypes.object.isRequired,
		updateUser: React.PropTypes.func.isRequired
};