/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
 
if (typeof(Kumu) == 'undefined') {
    Kumu = {};
}

if (typeof(Kumu.Event) == 'undefined') {
    Kumu.Event = {};
}

var KumuEventConf;

Kumu.extend(Kumu.Event, {
		
	stopEvent : function(event) {
    	if (event.preventDefault) { 
      		event.preventDefault(); 
      		event.stopPropagation(); 
    	} else {
      		event.returnValue = false;
	        event.cancelBubble = true;
    	}
  	},

  	caches: false,
  
  	_cached: function(element, name, observer, useCapture) {
    	if (!this.caches){
    		 this.caches = [];
    	}
    	if (element.addEventListener) {
      		this.caches.push([element, name, observer, useCapture]);
      		element.addEventListener(name, observer, useCapture);
    	} else if (element.attachEvent) {
      		this.caches.push([element, name, observer, useCapture]);
      		element.attachEvent('on' + name, observer);
    	}
	},
  
	unloadEvent: function() {
    	if (!this.caches){
    		 return;
    	}
    	for (var i = 0; i < this.caches.length; i++) {
      		this.removeEvent.apply(this, this.caches[i]);
      		this.caches[i][0] = null;
    	}
    	this.caches = false;
  	},

	addEvent : function(element, name, observer, useCapture) {
    	var ele = $i(element);
    	useCapture = useCapture || false;
	    if (name == 'keypress' && (navigator.appVersion.match(/Konqueror|Safari|KHTML/) 
    	 || ele.attachevent)){
		      name = 'keydown';
    	}
    	this._cached(ele, name, observer, useCapture);
  	},

 	removeEvent : function(element, name, observer, useCapture) {
    	var element = $i(element);
    	useCapture = useCapture || false;
    
    	if (name == 'keypress' && ((navigator.appVersion.indexOf('AppleWebKit') > 0) || element.detachevent)){
      		name = 'keydown';
    	}
    
	    if (element.removeEventListener) {
    	  	element.removeEventListener(name, observer, useCapture);
    	} else if (element.detachEvent) {
      		element.detachEvent('on' + name, observer);
    	}
  	},
  	
  	regist : function(o){
		for(var v in o){
			o[v].__name = v;
			o[v].registEvent();
    	}
  	},
  	  	
  	loadEvent : function(){
  		if(KumuEventConf){
			this.regist(KumuEventConf);
		}
  	},
	
  	addOnLoadEvent : function(func){
		this.addEvent(window, 'load', func, false);
  	}
	  	
});

Function.prototype.registEventToElement = function(element, scope){
    if(!scope){
       	scope = {};
    }
    var ret;
    if(this.__name){
    	ret = this.__name;
    }else{
		var str = this.toString();
    	var ret = str.match(/[0-9A-Za-z_]+\(/).toString();
    	ret = ret.substring(0,ret.length-1);
    }
    var arr = Kumu.separate(ret);
	var callback = this.bindScopeAsEventListener(scope, element);
 	Kumu.Event.addEvent(element, arr[0], callback, false);
}

Function.prototype.registEvent = function(scope){
    if(!scope){
       	scope = {};
    }
    
    var ret;
    if(this.__name){
    	ret = this.__name;
    }else{
		var str = this.toString();
    	var ret = str.match(/[0-9A-Za-z_]+\(/).toString();
    	ret = ret.substring(0,ret.length-1);
    }
    var arr = Kumu.separate(ret);
    var callback = this.bindScopeAsEventListener(scope, arr[1]);
 	Kumu.Event.addEvent(arr[1],  arr[0], callback, false);
}

Function.prototype.registOnLoad = function(scope){
    this.__name = 'window_load';
    this.registEvent(scope);
}

Function.prototype.registOnUnLoad = function(scope){
    this.__name = 'window_unload';
    this.registEvent(scope);
}


Kumu.Event.addEvent(window, 'load', Kumu.Event.loadEvent.bindScope(Kumu.Event), false);
Kumu.Event.addEvent(window, 'unload', Kumu.Event.unloadEvent.bindScope(Kumu.Event), false);
