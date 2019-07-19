var RESPONSE_MODEL;

function getServiceUrl(serviceName, params) {
	fullUrl = SERVER_URL + serviceName;
	fullUrl = formatParams(fullUrl, params);

	return fullUrl;
}

function sendAuthenticationRequest(serviceName, params) {
	url = getServiceUrl(serviceName, params);
	Ext.Ajax.request({
        url: url,
        method: 'GET',
        async: false,
        success: function(conn, response, options, eOpts) {
        	var responseObj = Ext.JSON.decode(conn.responseText);
        	if (responseObj.error == undefined) {
        		RESPONSE_MODEL = {
        			status:RESPONSE_STATUS_SUCCESS,
                	error:'',
                	data:responseObj
            	};
        	} else {
        		RESPONSE_MODEL = {
        			status:RESPONSE_STATUS_ERROR,
                	error:responseObj.error_description,
                	data:responseObj
            	};
        	}
        },
        failure: function(conn, response, options, eOpts) {
        	var responseObj = Ext.JSON.decode(conn.responseText);
        	RESPONSE_MODEL = {
    			status:RESPONSE_STATUS_ERROR,
            	error:responseObj.error_description,
            	data:''
        	};
        }
    });
}

function sendGetRequest(serviceName, params, asyncType, callbackMethod) {
	sendRequest(serviceName, params, 'GET', asyncType, callbackMethod)
}

function sendPostRequest(url, params, asyncType, callbackMethod) {
	sendRequest(serviceName, params, 'POST', asyncType, callbackMethod)
}

function sendRequest(serviceName, params, requestType, asyncType, callbackMethod) {
	url = getServiceUrl(serviceName, params);
	Ext.Ajax.request({
        url: url,
        method: requestType,
        async: asyncType,
        success: function(conn, response, options, eOpts) {
        	RESPONSE_MODEL = Ext.JSON.decode(conn.responseText);
        },
        failure: function(conn, response, options, eOpts) {
        	RESPONSE_MODEL = {
    			status:RESPONSE_STATUS_ERROR,
    			error:conn.responseText,
            	data:''
        	};
        },
        callback: callbackMethod
    });
}

function sendRequestToServer(url, param, method){
    var urlApi = url.replace("{0}", ACCESS_TOKEN).replace("{1}", param);
    var result;
    Ext.Ajax.request({
        url: urlApi,
        method: method,
        async: false,
        success: function(conn, response, options, eOpts) {
            var jsonData = Ext.JSON.decode(conn.responseText);
            if(jsonData.error == ""){
                result = jsonData.data;
            }else{
                Ext.Msg.show({
                    title:'Error!',
                    msg: jsonData.error,
                    icon: Ext.Msg.ERROR,
                    buttons: Ext.Msg.OK
                });
            }               
        },
        failure: function(conn, response, options, eOpts) {
            var jsonData = Ext.JSON.decode(conn.responseText);
            Ext.Msg.show({
                title:'Error!',
                msg: jsonData.error,
                icon: Ext.Msg.ERROR,
                buttons: Ext.Msg.OK
            });
        }
    });

    return result;
}