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
  Kumu.dynamicLoad('event');
  Kumu.dynamicLoad('ajax');
}

if (typeof(Kumu.Html) == 'undefined') {
  Kumu.Html = {};
}

if (typeof(Kumu.Html.AutoComplete) == 'undefined') {
  Kumu.Html.AutoComplete = Kumu.create();
}


Kumu.extend(Kumu.Html.AutoComplete.prototype, {
  
  initialize : function( element, target, options){
    this.element = $i(element);
    this.target = $i(target);
    this.target.style.display = 'none';
    this.url = 'teeda.ajax';
    this.options = options;
    this.index = 0;
    this.entryCount = 0;
    this.wordCount = 3;
    this.active = true;

    if(options.wordCount){
      this.wordCount = options.wordCount;
    }
    if(options.url){
      this.url = options.url;
    }
    
    this.keypress_event.registEventToElement(this.element, this);
    this.blur_event.registEventToElement(this.element, this);
  },

  renderChoice : function(json){
    this.index = 0;
    var ele = this.target;
    if(ele.firstChild){
        ele.removeChild(ele.firstChild);
    }
    if(!json) return;
      
    ele.style.display = 'block';
    ele.style.position = 'absolute';
    var data = json.data;
    var len = json.data.length;
    this.entryCount = len;
    var ul = document.createElement('ul');
	for(var i = 0; i < len; i++){
      var li = document.createElement('li');
      li.innerHTML = data[i].toString();
      li.index = i;
      ul.appendChild(li);
      this.bindEvent(li);
	}
    ele.appendChild(ul);
    this.render();
  },
  
  bindEvent : function(li){
    this.click_li.registEventToElement(li, this);
    this.mouseover_li.registEventToElement(li, this);
  },
  
  render: function() {
    if(this.entryCount > 0) {
      for (var i = 0; i < this.entryCount; i++){
        if(this.index == i){
          this.getEntry(i).className = 'selected'; 
        }else{
          this.getEntry(i).className = '';
        }
      }
    }
    this.active = true;
  },
  
  callRemote : function(value){
    if(value.length  >= this.wordCount){
      var renderChoice = this.renderChoice;
      var callback = function(object){
        var x = renderChoice;
        return function(response) {
          return x.call(object, response);
        };
      };
      var ajax = Kumu.Ajax.getS2AjaxComponent();
      ajax.url = this.url;
      ajax.params = {"component" : this.options.component, "action" : this.options.action, "value" : value};
      ajax.doAction = callback(this);
      ajax.responseType = Kumu.Ajax.RESPONSE_TYPE_JSON;
      Kumu.Ajax.executeAjax(ajax);
    }else{
      this.hide();
    }
  },

  getEntry: function(index) {
    return this.target.firstChild.childNodes[index];
  },
  
  getCurrentEntry: function() {
    return this.getEntry(this.index);
  },
  
  selectEntry: function(){
    var li = this.getCurrentEntry();
    var v = li.innerHTML;
    this.element.value = v;
    this.hide();
  },
  
  markPrevious : function(){
    if(this.index > 0){
      this.index--;
    }else{
       this.index = this.entryCount-1;
    }
  },

  markNext : function(){
    if(this.index < this.entryCount-1){
      this.index++
    }else{
      this.index = 0;
    }
  },
  
  hide : function(){
    var disp = this.target.style.display;
    if(disp != 'none'){
      this.target.style.display = 'none';
    }
    this.active = false;
  },
  
  keypress_event : function keypress_event(event, e){
    if(this.active){
      switch(event.keyCode) {
        case Kumu.Event.KEY_TAB:
        case Kumu.Event.KEY_RETURN:
           this.selectEntry();
           Kumu.Event.stopEvent(event);
           return;
        case Kumu.Event.KEY_ESC:
        case Kumu.Event.KEY_LEFT:
        case Kumu.Event.KEY_RIGHT:
        case Kumu.Event.KEY_UP:
          this.markPrevious();
          this.render();
          return;
        case Kumu.Event.KEY_DOWN:
          this.markNext();
          this.render();
          return;
      }
    }
    var v = this.element.value;
    this.callRemote(v);
  },
  
  blur_event : function blur_event(event, e) {
    this.hide.bindScope(this).delay(250)();
  },
      
  click_li : function click_li(event, e){
    var v = e.innerHTML;
    this.element.value = v;
    this.hide();
  },

  mouseover_li : function mouseover_li(event, e){
    e.className = "selected";
    this.index = e.index;
    this.render();    
  }
});