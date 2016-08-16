var app = {
	doAuthenicatedPost: function(obj){
		var token = $("#token").val();
		if (!token){
			if (data.callback)
        		callback('ERROR', {'code': 100, 'message': 'Need to log in.'});
			return;
		}
		$.ajax({
			type:"POST",
	        beforeSend: function (request) {
	        	request.setRequestHeader("Authorization", "Bearer " + token);
	        },
	        url: obj.url,
	        data: obj.data,
	        success: function(result) {
	        	if (data.callback)
	        		callback('SUCCESS', result);
	        }, 
	        error: function(result){
	        	if (data.callback)
	        		callback('ERROR', result);
	        }
	    });
		
	},
	doAuthenticatedGet: function(data){
		var token = $("#token").val();
		if (!token){
			if (data.callback)
        		callback('ERROR', {'code': 100, 'message': 'Need to log in.'});
			return;
		}
		$.ajax({
			type:"GET",
	        beforeSend: function (request) {
	        	request.setRequestHeader("Authorization", "Bearer " + token);
	        },
	        url: obj.url,
	        data: obj.data,
	        success: function(result) {
	        	if (data.callback)
	        		callback('SUCCESS', result);
	        }, 
	        error: function(result){
	        	if (data.callback)
	        		callback('ERROR', result);
	        }
	    });
	}, 
	doGet: function(data){
		$.ajax({
			type:"GET",
	        beforeSend: function (request) {
	        	request.setRequestHeader("Authorization", "Bearer " + token);
	        },
	        url: obj.url,
	        data: obj.data,
	        success: function(result) {
	        	if (data.callback)
	        		callback('SUCCESS', result);
	        }, 
	        error: function(result){
	        	if (data.callback)
	        		callback('ERROR', result);
	        }
	    });
	}
}
