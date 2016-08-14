// initialize Account Kit with CSRF protection
AccountKit_OnInteractive = function() {
	AccountKit.init({
		appId : 521804444687157,
		state : "123",
		version : "v1.0"
	});
};

// login callback
function loginCallback(response) {
	if (response.status === "PARTIALLY_AUTHENTICATED") {
		$("#code").val(response.code);
		$("form").submit();
	} else if (response.status === "NOT_AUTHENTICATED") {
		// handle authentication failure
	} else if (response.status === "BAD_PARAMS") {
		// handle bad parameters
		console.log(response);
	}
}

$("#loginViewTextButton").click(function() {
	if ($("#terms").is(':checked'))
		AccountKit.login('PHONE', {}, loginCallback);
	return false;
});

$("#loginViewEmailButton").click(function() {
	if ($("#terms").is(':checked'))
		AccountKit.login('EMAIL', {}, loginCallback);
	return false;
});
