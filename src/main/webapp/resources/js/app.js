var app = {
	doPost: function(obj){
		$.ajax({
			type:"POST",
	        beforeSend: function (request) {
	        	request.setRequestHeader("Content-Type", "application/json");
	        },
	        url: obj.url,
	        dataType: 'json',
	        data: JSON.stringify(obj.data),
	        success: function(result) {
	        	if (obj.callback)
	        		obj.callback('SUCCESS', result);
	        }, 
	        error: function (result) {
	        	handleError(result, obj);
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
	        error: function (result) {
	        	handleError(result, obj);
	        }
	    });
	}
}

function handleError(result, obj){
	if (result.responseJSON){
		if (obj.callback)
    		obj.callback('ERROR', {'code': result.responseJSON.status, 'error': result.responseJSON.message});
		return;
	}
	if (obj.callback)
		obj.callback('ERROR', result);
}