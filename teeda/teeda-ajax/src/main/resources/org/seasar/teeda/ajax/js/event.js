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

KumuEventConf = false;


Kumu.extend(Kumu.Event, {
    
  KEY_BACKSPACE: 8,
  KEY_TAB:       9,
  KEY_RETURN:   13,
  KEY_ESC:      27,
  KEY_LEFT:     37,
  KEY_UP:       38,
  KEY_RIGHT:    39,
  KEY_DOWN:     40,
  KEY_DELETE:   46,

  process : {},  
  
  _nodeCache : {},
  
  _elementsById : function(id){
    if(!(id instanceof String) && typeof id != "string"){
      return [id];
    }

    var nodes = [];
    var elem = $i(id);
    if(!this._nodeCache[id]){
      this._nodeCache[id] = []; 
    }
    while(elem){
      if(!elem["cached"]){
        nodes.push(elem);
      }
      elem.id = "";
      elem = $i(id);
    }
    this._nodeCache[id] = this._nodeCache[id].concat(nodes);
    this._nodeCache[id].map(function(n){
      n.id = id;
      n.cached = true;
    } );
    return this._nodeCache[id];
  },
  
  stopEvent : function(event) {
    if (event.preventDefault) { 
      event.preventDefault(); 
      event.stopPropagation(); 
    } else {
      event.returnValue = false;
      event.cancelBubble = true;
    }
  },
  
  unloadEvent: function() {
    if (!this.process){
      return;
    }
    var p = this.process;
    for (var v in p){
      if(p[v]){
        for(var z in p[v]){
          if(p[v][z]){
            for(var i = 0; i < p[v][z].length; i++){
              p[v][z][i].remove();
            }
          }
        }
      }
    }
    this.p = {};
  },

  addEvent : function(element, name, observer, useCapture) {
    var nodes = this._elementsById(element);
    useCapture = useCapture || false;
    if (name == 'keypress' && (navigator.appVersion.match(/Konqueror|Safari|KHTML/) 
     || element.attachevent)){
      name = 'keydown';
    }
    var processList = [];
    nodes.map(function(node){
      var nodeFunc = observer.state();
      var p = {
        node : node,
        eventName : name,
        command : nodeFunc,
        add : function(){
          if (node.addEventListener) {
            node.addEventListener(name, nodeFunc, useCapture);
          } else if (node.attachEvent) {
            node.attachEvent('on' + name, nodeFunc);
          }
        },
        remove : function(){
          if (node.removeEventListener) {
  	        node.removeEventListener(name, nodeFunc, useCapture);
    	  } else if (node.detachEvent) {
            node.detachEvent('on' + name, nodeFunc);
          }
        },
        cancel : function(c){
          nodeFunc.cancel(c);
		},
		clear : function(){
		  this.remove();
		  Kumu.Event.process[node][name] = null;
		}
      };
      if(this.process[node]){
        if(this.process[node][name]){
      	  this.process[node][name].push(p);
        }else{
      	  this.process[node][name] = [];
      	  this.process[node][name].push(p);
        }
      }else{
        this.process[node] = {};
        this.process[node][name] = [];
        this.process[node][name].push(p);
      }
      p.add();
      processList.push(p);
    }.bindScope(this));
    return processList;
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
  },
  
  addOnUnLoadEvent : function(func){
    this.addEvent(window, 'unload', func, false);
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
    ret = this.getName();
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
    ret = this.getName();
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

Kumu.Event.addOnLoadEvent(Kumu.Event.loadEvent.bindScope(Kumu.Event));
Kumu.Event.addOnUnLoadEvent(Kumu.Event.unloadEvent.bindScope(Kumu.Event));
}