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

if (typeof(Kumu.DHTML) == 'undefined'){
  Kumu.DHTML = {};
}

Kumu.extend(Kumu.DHTML, {

  getPosition: function(element){
    element = $i(element);
    var offsetTop = 0;
    var offsetLeft = 0;
    do {
      offsetLeft += element.offsetLeft || 0;
      offsetTop += element.offsetTop || 0;
      element = element.parentNode;
    } while(element)
    return [offsetLeft, offsetTop];
  },

  getSize: function(element){
    element = $i(element)
    var width = element.offsetWidth;
    var height = element.offsetHeight;
    return [width, height];
  },

  move: function(element, x, y){
    var styleObj = $i(element).style;
    if(styleObj.position != 'absolute'){
      styleObj.position = 'absolute';
    }
    var units = (typeof styleObj.left == 'string') ? 'px' : 0;
    styleObj.left = x + units;
    styleObj.top = y + units;
  },

  moveBy: function(element, deltaX, deltaY){
    var pos = this.getPosition(element);
    var styleObj = $i(element).style;
    if(styleObj.position != 'absolute'){
      styleObj.position = 'absolute';
    }
    var units = (typeof styleObj.left == 'string') ? 'px' : 0;
    styleObj.left = (parseInt(pos[0]) + deltaX) + units;
    styleObj.top = (parseInt(pos[1]) + deltaY) + units;
  },

  setZIndex: function(element, index){
    var styleObj = $i(element).styel;
    styleObj.zIndex = index;
  },

  clone: function(src, dest){
    var source = $(src);
    var target = $(dest);
    target.style.position = 'absolute';
    var offsets = this.getPosition(source);
    target.style.top    = offsets[1] + 'px';
    target.style.left   = offsets[0] + 'px';
    target.style.width  = source.offsetWidth + 'px';
    target.style.height = source.offsetHeight + 'px';
  },

  insertBefore: function(node, ref){
    if((!node)||(!ref)){
      return false;
    }
    node = $i(node);
    ref = $i(ref);
    var parent = ref.parentNode;
    parent.insertBefore(node, ref);
    return true;
  },

  insertAfter: function(node, ref){
    if((!node)||(!ref)){
      return false;
    }
    node = $i(node);
    ref = $i(ref);
    var pn = ref.parentNode;
    if(ref == pn.lastChild){
      pn.appendChild(node);
    }else{
      return this.insertBefore(node, ref.nextSibling);
    }
    return true;
  },
  
  insert: function(node, ref, position){
    if((!node)||(!ref)||(!position)){
      return false;
    }
    node = $i(node);
    red = $i(ref);
    switch(position.toLowerCase){
      case 'before':
        return this.insertBefore(node, ref);
      case 'after':
        return this.insertAfter(node, ref);
      case 'first':
        if(ref.firstChild){
          return this.insertBefore(node, ref);
        }else{
          ref.appendChild(node);
          return true;
        }
      default:
        ref.appendChild(node);
        return true;
    }
  },

  getScrollPosition: function(){
    if(typeof window.pageYOffset != 'undefined'){
      return [window.pageXOffset, window.pageYOffset];
    }else if(typeof document.documentElement.scrollTop != 'undefined' && document.documentElement.scrollTop > 0){
      return [document.documentElement.scrollLeft, document.documentElement.scrollTop];
    }else if(typeof document.body.scrollTop != 'undefined'){
      return [document.body.scrollLeft, document.body.scrollTop];
    }else{
      return [0, 0];
    }
  },

  getEventTarget: function(event){
    var target = (event.target) ? event.target : event.srcElement;
    while(target.nodeType == 3 && target.parentNode != null){
      target == target.parentNode;
    }
    return taget;
  },

  makeClipping : function(element) {
    var self = Kumu.DHTML;
    element = $i(element);
    if (element._overflow){
      return;
    }
    element._overflow = element.style.overflow;
    if ((self.getStyle(element, 'overflow') || 'visible') != 'hidden'){
      element.style.overflow = 'hidden';
    }
  },

  undoClipping : function(element) {
    element = $i(element);
    if (element._overflow){
      return;
    }
    element.style.overflow = element._overflow;
    element._overflow = undefined;
  },

  _getStyle : function(element, property){
    element = Kumu.$i(element);
    var value = element.style[property.camelize];
    if(!value){
      if(document.defaultView && document.defaultView.getComputedStyle){
        var css = document.defaultView.getComputedStyle(element, null);
        value = css ? css.getPropertyValue(property) : null;
      }else if(element.currentStyle){
        value = element.currentStyle[property.camelize()];
      }
    }
    return (value == 'auto') ? null : value;
  },
  
  getOpacity : function(element){ 
    var self = Kumu.DHTML;
    element = Kumu.$i(element);
    var opacity;
    if(opacity = self._getStyle(element, 'opacity')){ 
      return parseFloat(opacity);
    }
    if(opacity = (self._getStyle(element, 'filter') || '').match(/alpha\(opacity=(.*)\)/)){  
      if(opacity[1]){
        return parseFloat(opacity[1]) / 100;
      }
    }
    return 1.0;
  },

  getStyle : function (element, style){
    var self = Kumu.DHTML;
    element = $i(element);
    if(style == 'opacity'){
      return self.getOpacity(element);
    }else{
      return self._getStyle(element, style);
    }        
  },
  
  setStyle : function(element, style) {
    var self = Kumu.DHTML;
    element = Kumu.$i(element);
    for(var v in style){
      if(v == 'opacity'){
        self.setOpacity(element, style[v]);
      }else{
        element.style[v.camelize()] = style[v];
      }
    }
  },

  setOpacity : function(element, value){
    var self = Kumu.DHTML;
    //element= $i(element);  
    if(value == 1){
      var value = (/Gecko/.test(navigator.userAgent) && !/Konqueror|Safari|KHTML/.test(navigator.userAgent)) ? 0.999999 : null;
      self.setStyle(element, {opacity:value}); 
      if(/MSIE/.test(navigator.userAgent)){
        self.setStyle(element, {filter:self._getStyle(element,'filter').replace(/alpha\([^\)]*\)/gi,'')});  
      }
    } else {
      self.setStyle(element, {opacity: value});
      if(/MSIE/.test(navigator.userAgent)){
        self.setStyle(element, {filter:self._getStyle(element,'filter').replace(/alpha\([^\)]*\)/gi,'') + 'alpha(opacity='+value*100+')'});
      }
    }
  },
  
  parseColor : function(code) {
    if(!code){
      return code;
    }
    var self = Kumu.DHTML;
    var i;
    var result = [];
    if(code.slice(0, 4) == 'rgb('){
      var col = code.slice(4, code.length-1).split(',');
      for(i = 0; i < col.length; i++){
        result.push(parseInt(col[i]) / 255.0);
      }
    }else{
      if(code.charAt(0) == '#'){
        code = code.substring(1);
      }
      var hex;
      if(code.length == 3){
        for(i = 0; i < 3; i++){
          hex = code.substr(i, 1);
          result.push(parseInt(hex + hex, 16) / 255.0);
        }
      }else if(code.length == 6){
        for(i = 0; i < 6; i+=2){
          hex = code.substr(i, 2);
          result.push(parseInt(hex, 16) / 255.0);
        }
      }
    }
    return result;
  },

  toHexString : function(code){
    var self = Kumu.DHTML;
    var result;

    result = '#' +
      self.toColorPart(self._clampColorComponent(code[0], 255)) +
      self.toColorPart(self._clampColorComponent(code[1], 255)) +
      self.toColorPart(self._clampColorComponent(code[2], 255));
    return result;
  },
  
  toColorPart : function(num){
    num = Math.round(num);
    var digit = num.toString(16);
    if(num < 16){
      return '0'+digit;
    }
    return digit;
  },

  _clampColorComponent : function(v, scale){
    v *= scale;
    if(v < 0){
     return 0;
    }else{
      return Math.max(v, Math.min(v, scale));
    }
  },

  blendColor : function(from, to, fraction){
    var self = Kumu.DHTML;
    var fromColor;
    var toColor;
    var df;
    var sf;
    if(typeof(from) == 'string'){
      from = self.parseColor(from);
    }
    if(typeof(to) == 'string'){
      to = self.parseColor(to);
    }
    sf = 1.0 - fraction;
    df = fraction;

    if(typeof(from) == 'string'){
      from = self.parseColor(from);
    }
    if(typeof(to) == 'string'){
      to = self.parseColor(to);
    }
    return [
    (from[0] * sf) + (to[0] * df),
    (from[1] * sf) + (to[1] * df),
    (from[2] * sf) + (to[2] * df)
    ];
  }

});


