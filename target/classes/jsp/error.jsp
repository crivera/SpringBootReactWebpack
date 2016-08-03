<%@include file="/WEB-INF/jsp/_inc.jsp" %>
<!DOCTYPE html>
<html lang="en">
 	<head>
    	<meta name="decorator" content="/jsp/templates/main.jsp">
	</head>
	<body>
		<div class="container">
	  		<div class="row">
	  			<h1>Ooops! An Error occured!</h1>
		
				<div class="alert alert-danger">
					${error.message}
				</div>
				<!-- 
					${error.printStackTrace()}
		 		-->
		 	</div>
		 </div>
    </body>
</html>