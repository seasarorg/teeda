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

DisabledConf = false;

Kumu.extend(Kumu.Html.Disabled, {

  time : 1000,

  anchor : false,
  
  button : false,

  submitMessage : false,

  anchorMessage : false,
  
  exDisabledAnchor : false,

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
    }.delay(this.time))();
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

  disableAction : function(){
    document.body.onclick = function(e){
      var element = e.target || e.srcElement;
      if(element){
    	if(this.exDisabledAnchor){
    		for(var i = 0, len = this.exDisabledAnchor.length; i < len; i++){
    			if(element == this.exDisabledAnchor[i]) {
    				return;
    			}
    		}
    	}
        if(this.anchor && element.id){
          for(var i = 0, len = this.anchor.length; i < len; i++){
            if(element.id == this.anchor[i]){
              return;
            }
          }
        }
        var name = element.nodeName.toUpperCase();
        if(name == 'A'){
			return this.disable(e, element, this.anchorMessage);
        }
        if(this.button && element.id){
          for(var i = 0, len = this.button.length; i < len; i++){
            if(element.id == this.button[i] || 0 < element.id.indexOf(":" + this.button[i])){
              var name = element.getAttribute('type').toUpperCase();
              if(name == 'SUBMIT' || name == 'BUTTON' || name == 'IMAGE'){
		    	return this.disable(e, element, this.submitMessage);
              }
            }
          }
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
      if(DisabledConf.includeButton){
        this.button = DisabledConf.includeButton;
      }else{
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
    }
    // excludeDisabled check
    var elements = this.getElementsByClassName('excludeDisabled');
    var elem;
    for (var i = 0; elem = elements[i]; i++){
      var nodeName = elem.nodeName.toUpperCase();
      if (nodeName === 'A') {
        if(this.exDisabledAnchor) this.exDisabledAnchor.push(elem);
        else this.exDisabledAnchor = [elem];
      }
    }
    this.disableAction();
    if(!DisabledConf.includeButton){
      this.disableForms(forms);
    }
  },
  
  getElementsByClassName : function(classname){
    var rl = new Array();
    var re = new RegExp('(^| )'+classname+'( |$)');
    var ael = document.getElementsByTagName('*');
    if(navigator.userAgent){
        op = (navigator.userAgent.indexOf("Opera") != -1) ? true : false;
    }
    if (document.all && !op) ael = document.all;
    for(i=0, j=0 ; i<ael.length ; i++) {
      if(re.test(ael[i].className)) rl[j++]=ael[i];
    }
    return rl;
  }
});
Kumu.Event.addOnLoadEvent(Kumu.Html.Disabled.loadDisabled.bindScope(Kumu.Html.Disabled));
}