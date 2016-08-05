<%@include file="/jsp/_inc.jsp" %>
<!DOCTYPE html>
<html lang="en">
 	<head prefix="og: http://ogp.me/ns# fb: http://ogp.me/ns/fb# on-aire: http://ogp.me/ns/fb/on-aire#">
   	<meta charset="utf-8">
    <meta name="keywords" content="">
		<meta name="description" content="">
		<meta name="author" content="Christopher Rivera">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

		<link rel="apple-touch-icon" sizes="57x57" href="/resources/images/fav/apple-icon-57x57.png">
		<link rel="apple-touch-icon" sizes="60x60" href="/resources/images/fav/apple-icon-60x60.png">
		<link rel="apple-touch-icon" sizes="72x72" href="/resources/images/fav/apple-icon-72x72.png">
		<link rel="apple-touch-icon" sizes="76x76" href="/resources/images/fav/apple-icon-76x76.png">
		<link rel="apple-touch-icon" sizes="114x114" href="/resources/images/fav/apple-icon-114x114.png">
		<link rel="apple-touch-icon" sizes="120x120" href="/resources/images/fav/apple-icon-120x120.png">
		<link rel="apple-touch-icon" sizes="144x144" href="/resources/images/fav/apple-icon-144x144.png">
		<link rel="apple-touch-icon" sizes="152x152" href="/resources/images/fav/apple-icon-152x152.png">
		<link rel="apple-touch-icon" sizes="180x180" href="/resources/images/fav/apple-icon-180x180.png">
		<link rel="icon" type="image/png" sizes="192x192"  href="/resources/images/fav/android-icon-192x192.png">
		<link rel="icon" type="image/png" sizes="32x32" href="/resources/images/fav/favicon-32x32.png">
		<link rel="icon" type="image/png" sizes="96x96" href="/resources/images/fav/favicon-96x96.png">
		<link rel="icon" type="image/png" sizes="16x16" href="/resources/images/fav/favicon-16x16.png">
		<link rel="manifest" href="/resources/images/fav/manifest.json">
		<meta name="msapplication-TileColor" content="#ffffff">
		<meta name="msapplication-TileImage" content="/resources/images/fav/ms-icon-144x144.png">
		<meta name="theme-color" content="#ffffff">

    <title><sitemesh:write property="title"/></title>

		<sitemesh:write property="head"/>
		
		<!-- Bootstrap -->
	  <!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		
		<!-- Default CSS -->
		<link rel="stylesheet" href="/resources/css/style.css">
		
	  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	  <!--[if lt IE 9]>
	      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	  <![endif]-->
	    
	</head>
	<body>
			<sitemesh:write property="body"/>
	  	<script src="https://code.jquery.com/jquery-3.1.0.min.js"></script>		
			<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
			<script src="https://use.fontawesome.com/0a0888c20b.js"></script>
			<sitemesh:write property="page.javascript"/>
	</body>
</html>