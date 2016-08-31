<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/_inc.jsp"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta name="decorator" content="/jsp/templates/main.jsp">
	</head>

	<content tag="javascript"> 
		<app:facebook /> 
		<script type="text/javascript" src="/resources/js/loginPage.js"></script>
	</content>

	<body>
		<div class="row">
			<div class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3">
				<div class="card card-signup">
					<form class="form" method="post" action="/loginWithAccountKit">
						<input type="hidden" id="code" name="code" value=""/>
						<div class="content text-center">
							<h2>Login</h2>
							<div class="space">
								ChatterBox uses a new system that does not require you to have a password. 
							</div>
							<div class="togglebutton">
								<label>
									<input id="terms" type="checkbox" name="acceptTermsAndConditions">
									Accept <a href="">Terms And Conditions</a>
								</label>
							</div>
						</div>
						<div class="footer text-center">
							<button id="loginViewTextButton" class="btn btn-round btn-info">Login via Text</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>