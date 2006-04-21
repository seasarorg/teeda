var HTTP_STATUS_OK = 200;
var HTTP_STATUS_NOT_FOUND = 404;

var XML_HTTP_REQUEST_STATUS_UNINITIALIZED = 0;
var XML_HTTP_REQUEST_STATUS_LOADING = 1;
var XML_HTTP_REQUEST_STATUS_LOADED = 2;
var XML_HTTP_REQUEST_STATUS_INTERACTIVE = 3;
var XML_HTTP_REQUEST_STATUS_COMPLETE = 4;

var AJAX_RESPONSE_TYPE_XML = 1;

var axo = new Array(
    "Microsoft.XMLHTTP",
    "Msxml2.XMLHTTP.4.0",
    "Msxml2.XMLHTTP.3.0",
    "Msxml2.XMLHTTP"
);

var AJAX_COMPONENT_NAME = "AjaxComponent";

var DEBUG = true;

function getS2AjaxComponent() {
    var ajax = new AjaxComponent();
    return ajax;
}

function AjaxComponent() {
    this.name = AJAX_COMPONENT_NAME;

	this.responseType = null;
	
    this.url = "teeda.ajax";

    this.params = null;

    this.doAction = function(ajaxResponse){
    };
}

function createXmlHttp(){
    var xmlHttp = false;
    /*@cc_on
    @if (@_jscript_version >= 5)
    for (var i = 0; !xmlHttp && i < axo.length; i++) {
        try {
            xmlHttp = new ActiveXObject(axo[i]);
        } catch(e) {
        }
    }
    @else
        xmlHttp = false;
    @end @*/
    if (!xmlHttp && typeof XMLHttpRequest != "undefined") {
        try{
            // for Firefox, safari
            xmlHttp = new XMLHttpRequest();
            //xmlHttp.overrideMimeType("text/xml");
        } catch(e) {
            xmlHttp = false;
        }
    }
    return xmlHttp;
}

function debugPrint(message, errorFlg) {
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
}

function checkComponent(component) {
    var name;
    try {
        name = component.name;
    } catch(e) {
        return false;
    }
    if (AJAX_COMPONENT_NAME != name || !component.doAction || !component.url) {
        return false;
    }

    return true;
}

function executeAjax(ajaxComponent) {
    if (!checkComponent(ajaxComponent)) {
        debugPrint("IllegalArgument. argument object is not AjaxComponent. implements url or doAction!", true);
        return;
    }
    var xmlHttp = createXmlHttp();
    if (!xmlHttp || !document.getElementById) {
        debugPrint("This browser does not support Ajax.", true);
        return;
    }
    var sysdate = new String(new Date());
    var url = ajaxComponent.url + "?time=" + encodeURL(sysdate);
    var parameters = "";
    if(null != ajaxComponent.params){
        for(var key in ajaxComponent.params){
            parameters += "&" + key + "=" + encodeURL(ajaxComponent.params[key]);
        }
    }
    url += parameters;
    if(xmlHttp){
        registAjaxListener(xmlHttp, ajaxComponent);

        xmlHttp.open("GET", url, true);
        xmlHttp.setRequestHeader("If-Modified-Since", sysdate);
        xmlHttp.send(null);
    }
}

function registAjaxListener(req, ajaxComponent) {
    req.onreadystatechange = function() {
        if (XML_HTTP_REQUEST_STATUS_COMPLETE == req.readyState) { 
            if (HTTP_STATUS_OK == req.status) {
                if (DEBUG) debugPrint(req.responseText);
                if (ajaxComponent.responseType) {
                    ajaxComponent.doAction(req.responseXML);
                } else {
   	            	ajaxComponent.doAction(req.responseText);
   	            }
			} else {
        		debugPrint("AjaxError! status["+req.status+"] message["+req.responseText+"]", true);
			}
        }
    };
}

function encodeURL(val) {
    if (encodeURI) {
        return encodeURI(val);
    }
    if (encodeURIComponent) {
        return encodeURIComponent(val);
    }
    if (escape) {
        return escape(val);
    }        
}