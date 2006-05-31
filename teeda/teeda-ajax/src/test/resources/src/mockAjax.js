if (typeof(Kumu) == 'undefined') {
    Kumu = {};
}
if (typeof(Kumu.MockAjax) == 'undefined') {
    Kumu.MockAjax = {};
};
Kumu.MockAjax = {

    AJAX_COMPONENT_NAME : "AjaxComponent",
    HTTP_STATUS_OK : 200,
    HTTP_STATUS_NOT_FOUND : 404,

    XML_HTTP_REQUEST_STATUS_UNINITIALIZED : 0,
    XML_HTTP_REQUEST_STATUS_LOADING : 1,
    XML_HTTP_REQUEST_STATUS_LOADED : 2,
    XML_HTTP_REQUEST_STATUS_INTERACTIVE : 3,
    XML_HTTP_REQUEST_STATUS_COMPLETE : 4,
    
    AJAX_RESPONSE_TYPE_XML : 1,

    DEBUG : true,
    
    MOCK_RESPONSE_XML : '<?xml version="1.0" encoding="UTF-8"?>\n<mock>\nmock\n</mock>',
    
    MOCK_RESPONSE_JSON : '{"mock":"mockvalue"}',
    
    getS2AjaxComponent : function() {
    	return new this.AjaxComponent();
    },
    
    AjaxComponent : function () {
    	var self = Kumu.MockAjax;
        this.name = self.AJAX_COMPONENT_NAME;
	    this.responseType = null;
        this.url = "teeda.ajax";
        this.params = null;
        this.doAction = function(ajaxResponse){}
    },
    
    _createXmlHttp : function(){
        var xmlHttp = true;
        return xmlHttp
    },

    debugPrint : function(message, errorFlg) {
        if (errorFlg) {
            try {
                var div = document.createElement("div");
                document.body.appendChild(div);
                div.setAttribute("id", "ajax_msg");
                message = "<font color='red'>" + message + "</font>";
                document.getElementById("ajax_msg").innerHTML = "<br/>" + message;
            } catch (e) {
            }
        } else {
            try {
                var br = document.createElement("br");
                var span = document.createElement("span");
                document.body.appendChild(br);
                document.body.appendChild(span.appendChild(document.createTextNode(message)));
            } catch (e) {
            }
        }
    },
    
    _checkComponent : function(component) {
        return true;
    },

    executeAjax : function(ajaxComponent) {
    	var self = Kumu.MockAjax;
        if (!self._checkComponent(ajaxComponent)) {
            self.debugPrint("IllegalArgument. argument object is not AjaxComponent. implements url or doAction!", true);
            return;
        }
        
        var xmlHttp = self._createXmlHttp();
        var url = ajaxComponent.url;
        var sysdate = new String(new Date());
        var parameters = "";
        var params = ajaxComponent.params;
        var method = 'GET';
        if(params.method){
        	method = params.method.toUpperCase();
        	if(method != 'GET' && method != 'POST'){
        		method = 'GET';
        	}
        	delete params.method;
        }
		if(method == 'GET'){
            url += "?time=" + self.encodeURL(sysdate);
            if(null != params){
                for(var key in params){
                    parameters += "&" + key + "=" + self.encodeURL(params[key]);
                }
            }
            url += parameters;
            if(xmlHttp){
                self.debugPrint("url["+url+"]");
                self._registAjaxListener(xmlHttp, ajaxComponent);
            }
		}else{
			params['time'] = self.encodeURL(sysdate);
            if(params){
                var array = new Array();
                for(var v in params) {
                    array.push(v + "=" + encodeURIComponent(params[v]));
                }
                parameters = array.join("&");
            }
            if(xmlHttp){
                self.debugPrint("parameter["+parameters+"]");
                self._registAjaxListener(xmlHttp, ajaxComponent);
            }
		}
    },

    _registAjaxListener : function(req, ajaxComponent) {
    	var self = Kumu.MockAjax;
        if (ajaxComponent.responseType) {
            ajaxComponent.doAction(self.MOCK_RESPONSE_XML);
        } else {
   	        ajaxComponent.doAction(self.MOCK_RESPONSE_JSON);
   	    }
    },

    encodeURL : function encodeURL(val) {
        if (encodeURI) {
            return encodeURI(val);
        }
        if (encodeURIComponent) {
            return encodeURIComponent(val);
        }
        if (escape) {
            return escape(val);
        }        
    },    
    
    _getComponentName : function(func){
        var str = func.toString();
        var ret = str.match(/[0-9A-Za-z_]+\(/).toString();
        ret = ret.substring(0,ret.length-1); 
        var arr = ret.split('_');
        return arr;
    },
    
    executeTeedaAjax : function(callback, param, responseType){
    	var self = Kumu.MockAjax;
        var ajax = self.getS2AjaxComponent();
        var components = self._getComponentName(callback);
        ajax.params = param;
        if(!("component" in param) && !("action" in param) && (components.length == 2) ){
            //callback name bind
            ajax.params["component"] = components[0];
            ajax.params["action"] = components[1];
        }
        self.debugPrint("call Component[" + ajax.params["component"] + "]");
        self.debugPrint("call Component action[" + ajax.params["action"] + "]");
        ajax.doAction = callback;
        if(responseType != 'undefined'){
            ajax.responseType = responseType;
        }
        self.executeAjax(ajax);
    }
    
};