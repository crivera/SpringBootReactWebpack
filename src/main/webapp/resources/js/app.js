var app = {
	doAuthenicatedPost: function(obj){
		if (!token){
			if (obj.callback)
				obj.callback('ERROR', {'code': 100, 'message': 'Need to log in.'});
			return;
		}
		$.ajax({
			type:"POST",
	        beforeSend: function (request) {
	        	request.setRequestHeader("Authorization", "Bearer " + token);
	        	request.setRequestHeader("Content-Type", "application/json");
	        },
	        url: obj.url,
	        dataType: 'json',
	        data: JSON.stringify(obj.data),
	        success: function(result) {
	        	if (obj.callback)
	        		obj.callback('SUCCESS', result);
	        }, 
	        error: function(result){
	        	if (obj.callback)
	        		obj.callback('ERROR', result);
	        }
	    });
		
	},
	doAuthenticatedGet: function(obj){
		if (!token){
			if (obj.callback)
				obj.callback('ERROR', {'code': 100, 'message': 'Need to log in.'});
			return;
		}
		$.ajax({
			type:"GET",
	        beforeSend: function (request) {
	        	request.setRequestHeader("Authorization", "Bearer " + token);
	        	request.setRequestHeader("Content-Type", "application/json");
	        },
	        url: obj.url,
	        dataType: 'json',
	        data: obj.data,
	        success: function(result) {
	        	if (obj.callback)
	        		obj.callback('SUCCESS', result);
	        }, 
	        error: function(result){
	        	if (obj.callback)
	        		obj.callback('ERROR', result);
	        }
	    });
	}, 
	doGet: function(obj){
		$.ajax({
			type:"GET",
	        beforeSend: function (request) {
	        	request.setRequestHeader("Content-Type", "application/json");
	        },
	        url: obj.url,
	        dataType: 'json',
	        data: obj.data,
	        success: function(result) {
	        	if (obj.callback)
	        		obj.callback('SUCCESS', result);
	        }, 
	        error: function(result){
	        	if (obj.callback)
	        		obj.callback('ERROR', result);
	        }
	    });
	}
}
