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
}else{
  if (typeof(Kumu.Event) == 'undefined') {
    Kumu.dynamicLoad('event');
  }
}

if (typeof(Kumu.Html) == 'undefined') {
  Kumu.Html = {};
}

if (typeof(Kumu.Html.Disabled) == 'undefined') {
  Kumu.Html.Disabled = {};
}

DisabledConf = false;

Kumu.extend(Kumu.Html.Disabled, {

  time : 1000,

  anchor : false,

  submitMessage : false,

  anchorMessage : false,

  lock : function(element) {
    if(!element.__lock){
      element.__lock = true;
    }
  },

  unlock : function(element) {
    (function(){
      if(element.__lock){
        element.__lock = false;
      }
    }.deferred(this.time))();
  },

  disable : function(evt, element, func){
    if(element.__lock){
	  if(func){
        func(element);
      }
	  Kumu.Event.stopEvent(evt);
	  return false;
    }else{
      this.lock(element);
      this.unlock(element);
	  return true;
    }
  },

  disableAnchor : function(){
    document.body.onclick = function(e){
      var element = e.target || e.srcElement;
      if(element){
        if(this.anchor && element.id){
          for(var i = 0; i < this.anchor.length; i++){
            if(element.id == this.anchor[i]){
              return;
            }
          }
        }
        var name = element.nodeName.toUpperCase();
        if(name == 'A'){
			return this.disable(e, element, this.anchorMessage);
        }
      }
    }.bindScopeAsEventListener(this);
  },

  disableForms: function(forms) {
    Kumu.map(function(f){
      if(f.onsubmit){
      }else{
	  	f.onsubmit = function(e){
		  return this.disable(e, f, this.submitMessage);
	  	}.bindScopeAsEventListener(this);
      }
    }.bindScope(this), Kumu.toArray(forms));
  },

  loadDisabled : function(){
    var forms = Kumu.toArray(document.forms);
    if(DisabledConf){
      if(DisabledConf.time){
        this.time = DisabledConf.time;
      }
      if(DisabledConf.excludeAnchor){
        this.anchor = DisabledConf.excludeAnchor;
      }
      if(DisabledConf.submitMessage){
        this.submitMessage = DisabledConf.submitMessage;
      }
      if(DisabledConf.anchorMessage){
        this.anchorMessage = DisabledConf.anchorMessage;
      }
      if(DisabledConf.excludeForm){
        var ids  = DisabledConf.excludeForm;
        forms = Kumu.filter(function(f){
          if(f.id){
            for(var i = 0; i < ids.length; i++){
              if(f.id == ids[i]){
                return false;
              }
            }
          }
          return true;
        }, forms);
      }
    }
    this.disableForms(forms);
    this.disableAnchor();
  }
});
Kumu.Event.addOnLoadEvent(Kumu.Html.Disabled.loadDisabled.bindScope(Kumu.Html.Disabled));
