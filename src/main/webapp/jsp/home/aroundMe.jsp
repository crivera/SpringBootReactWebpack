<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/jsp/_inc.jsp" %>
<!DOCTYPE html>
<html lang="en">
  	<head>
  		<meta name="decorator" content="/jsp/templates/main.jsp">
  		<style type="text/css">
  			.container-fluid {
  				padding: 0;
  			}
  			
  		</style>
  	</head>
  
 	<content tag="javascript">
 		<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD4HLGQyT5oBahb1m0CKg_iLWqVFOujS70"></script>
 		<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
 		<script type="text/javascript" src="/bundle/aroundMe.bundle.js"></script>
 	</content>
	
	<body>
		<div id="aroundMeDiv"></div>
	</body>
</html>