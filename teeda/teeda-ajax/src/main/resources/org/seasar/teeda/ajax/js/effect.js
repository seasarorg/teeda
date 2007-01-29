
if (typeof(Kumu) == 'undefined') {
  Kumu = {}
}else{
  Kumu.dynamicLoad('dhtml');
}

if (typeof(Kumu.Effect) == 'undefined') {
  Kumu.Effect = {};
}

Kumu.Effect.makeEaseIn = function(a){
  return function(state){
    return Math.pow(state, a * 2);
  }
};

Kumu.Effect.makeEaseOut = function(a){
  return function(state){
    return 1 - Math.pow(1 - state, a * 2);
  }
};

Kumu.Effect.lfos = {
    pulse: function (pos) {
        return Kumu.Effect.lfos.easeInOut(1 - Math.abs(0.5-pos) * 2);
    },
    linear : function(x){return x;},
    easeIn : Kumu.Effect.makeEaseIn(1.5),
    easeOut : Kumu.Effect.makeEaseOut(1.5),
    makeEaseIn : Kumu.Effect.makeEaseIn,
    makeEaseOut : Kumu.Effect.makeEaseOut,
    cubicIn: function(t){
        return t*t*t;
    },
    cubicOut: function(t){
        return ((t-1)*t*t + 1);
    },
    cubicInOut: function(t){
        if ((t*=2) < 1) return 0.5*t*t*t;
        return 0.5*((t-=2)*t*t + 2);
    },
    sineIn: function(t){
        return - Math.cos(t/ (Math.PI/2)) + 1;
    },
    sineOut: function(t){
        return  Math.sin(t/ (Math.PI/2));
    },
    sineInOut: function(t){
        return -0.5 * (Math.cos(Math.PI*t) - 1);
    }
};

Kumu.Effect.apply = function(el, style, options){
  return Kumu.Effect.cssAnimation(style, options, el);
}

Kumu.Effect._getDefaultValue = function(element, property, defaultValue){
  if(defaultValue){
    return defaultValue;
  }
  if(property == 'opacity'){
    return Kumu.DHTML.getOpacity(element);
  }else{
    return Kumu.DHTML.getStyle(element, property) || 0;
  }
};


Kumu.Effect._parseStyleArguments = function(elements, property, fromDefaultValue, toDefaultValue){
  var self = Kumu.Effect;
  var arg;
  elements = Kumu.toArray(elements);
  return elements.map(function(node){
    var from = self._getDefaultValue(node, property, fromDefaultValue);
    var to = self._getDefaultValue(node, property, toDefaultValue);
    return [from, to];
  });
};

Kumu.Effect.Effector = Kumu.create();

Kumu.extend(Kumu.Effect.Effector.prototype, {
  
  options : {
    interval : 30,
    duration : 400, 
    onComplete : function(){},
    onForward : function(){},
    onBackward : function(){},
    onStep : function(){},
    mod : Kumu.Effect.lfos.cubicInOut
  },

  initialize : function(options){
    Kumu.extend(this.options, options);
    this.target = 0;
    this.state = 0;
    this.play = this._play.bindScope(this);
    this.effects = [];
    this._chainForward = null;
    this._chainBackward = null;

  },
  
  seekTo : function(to){
    this.seekFromTo(this.state, to);
  },
  
  seekFromTo : function(from, to){
    this.target = Math.max(0, Math.min(1, to));
    this.state = Math.max(0, Math.min(1, from));
    if(this.target != 1 && this.state == 1 && this._chainForward && this._propagated){
      return this._chainForward(this._chainProtocol.PROPAGATE);
    }else if(this.target != 0 && this.state == 0 && this._chainBackward && this._rev_propagated){
      return this._chainBackward(this._chainProtocol.PROPAGATE);
    }
    if(!this.intervalId){
      this.intervalId = window.setInterval(this.play, this.options.interval);
    }
  },
  
  jumpTo : function(to){
    this.target = this.state = Math.max(0, Math.min(1,to));
    this._play();
  },
  
  play : function(){
    this.seekFromTo(0, 1);
  }, 

  reverse : function(){
    this.seekFromTo(0, 0);
  }, 

  toggle : function(){
    this.seekTo(1 - this.target);
  },

  _chainProtocol : {
    PROPAGATE : -1,
    DISCONNECT : 0, 
    PLAY : 1
  },
/* 
  chain : function(animation){
    var self = this;
    var cp = this._chainProtocol;

    animation._rev_propagated = true;
    self._propagated = true;
    if(this._chainForward){
      this._chainForward(cp.DISCONNECT);
    }
    self._chainForward = function(signal){
      if(signal == cp.PROPAGATE){
        if(animation._chainForward && animation._propagated){
           animation.target = 0;
           animation._chainForward(cp.PROPAGATE);
        }else{
           animation.seekTo(0);
        }
      }else if(signal == cp.DISCONNECT){
        animation._chainBackward = null;
      }else if(signal == cp.PLAY){
         self._propagated = true;
         animation._rev_propagated = true;
         animation.seekFromTo(animation.target == 0 ? 1 : animation.target); 
      }
    }
    animation._chainBackward = function(signal){
      if(sginal == cp.PROPAGATE){
        if(self._chainBackward & self._rev_propagated){
          self.target = 1;
          self._chainBackward(cp.PROPAGATE);
        }else{
          self.seekTo(1);
        }
      }else if(signal == cp.DISCONNECT){
        throw new Error("");
      }else{
        animation._rev_propagated = true;
        self._propagated = false;
        self.seekTo(self.target == 1 ? 0 : self.target);
      }
    }
    return this;
  },
*/

  addEffect : function(effect){
    this.effects[this.effects.length] = effect;
    return this;
  },

  removeEffect : function(effect){
    this.effects = this.effects.reject(function(item){return item == effect;});
  },

  clearEffect : function(){
    this.effects = [];
  },
  
  _propagateEffect : function(){
    var unclampedValue = this.options.mod(this.state);
    var value = Math.max(0, Math.min(1, unclampedValue));
    for(var i = 0; i < this.effects.length; i++){
      var effect = this.effects[i]
      if(effect.setState){
        effect.setState(value, unclampedValue, this.state);
      }else{
        effect(value, unclampedValue, this.state);
      }
    }
  },

  _play : function(){
    var movement = (this.options.interval / this.options.duration) * (this.state < this.target ? 1 : -1);
    if(Math.abs(movement) >= Math.abs(this.state - this.target)){
      this.state = this.target;
    }else{
      this.state += movement;
    }
    this._propagateEffect();
    this.options.onStep.call(this);
    if(this.target == this.state){
      window.clearInterval(this.intervalId);
      this.intervalId = null;
      if(this.state == 1 && movement > 0){
        this.options.onForward.call(this, this);
        if(this._chainForward){
          this._chainForward(this._chainProtocol.PLAY);
        }
      }else if(this.state == 0 && movement < 0){
        this.options.onForward.call(this,this);
        if(this._chainBackward){
          this._chainBackward(this._chainProtocol.PLAY);
        }  
      }
      this.options.onComplete.call(this, this);
    }
  }

});


Kumu.Effect.CSSEffect = Kumu.create();

Kumu.extend(Kumu.Effect.CSSEffect.prototype, {

  initialize : function(elements, style1, style2){
    var self = Kumu.Effect;
    var fromStyle;
    var toStyle;
    elements = Kumu.toArray($i(elements));
    if(style2){
      fromStyle = this.parseStyle(style1);
      toStyle = this.parseStyle(style2);
    }else{
      toStyle = this.parseStyle(style1);
      var fromStyle = {};
      Kumu.keys(toStyle).map(function(key){
        fromStyle[key] = null;
      });
    }
    this.effects = Kumu.keys(toStyle).map(function(property){
      return self._createStyleEffect(elements, property, fromStyle[property], toStyle[property]);
    });

  },

  parseStyle : function(style){
    var styles = style.split(';');
    var re = /^\s*([a-zA-Z\-_0-9]+)\s*:\s*(\S(.+\S)?)\s*$/;
    var obj = {};
    for(var i = 0; i < styles.length; i++){
      var parts = re.exec(styles[i]);
      if(parts){
        obj[parts[1]] = parts[2];
      }
    }
    return obj;
  },
  
  setState : function(state, unclampedState, rawState) {
    if(this.transition){
      unclampedState = this.transition(rawState);
      state = Math.max(0, Math.min(1, unclampedState));
    }
    for(var i = 0; i < this.effects.length; i++){
      this.effects[i].setState(state, unclampedState, rawState);
    }
  }

});

Kumu.Effect.PropertyEffect = Kumu.create();

Kumu.extend(Kumu.Effect.PropertyEffect.prototype, {
  
  initialize : function(elements, propertyObj){
    var self = Kumu.Effect;
    elements = Kumu.toArray($i(elements));
    this.effects = Kumu.keys(propertyObj).map(function(property){
      return self._createStyleEffect(elements, property, propertyObj[property][0], propertyObj[property][1], 
      propertyObj[property][2]);
    });
  },

  setState : function(state, unclampedState, rawState){
    if(this.transition){
      unclampedState = this.transition(rawState);
      state = Math.max(0, Math.min(1, unclampedState));
    }
    for(var i = 0; i < this.effects.length; i++){
      this.effects[i].setState(state, unclampedState, rawState);
    }
  }
});


Kumu.Effect.StyleNumericEffect = Kumu.create();

Kumu.extend(Kumu.Effect.StyleNumericEffect.prototype, {
  
  initialize : function(elements, property, from, to, unit){
    var self = Kumu.Effect;
    var argLists = self._parseStyleArguments(Kumu.toArray(elements), property, from, to);
    this.elements = Kumu.toArray(elements);
    this.property = property;
    this.unit = unit;
    this.argList = argLists.map(function(arg){
      var from = this._parseUnits(arg[0], unit);
      var to = this._parseUnits(arg[1], unit);
      return [from, to];
    }.bindScope(this));

  },

  _parseUnits : function(value, preUnits){
    if(typeof(value) == 'number'){
      return [value, preUnits];
    }
    var unit = /em|ex|px|in|cm|mm|pt|pc|%/.exec(value.toLowerCase());
    unit = unit ? unit[0] : preUnits;
    var floatValue = parseFloat(value);
    var intValue = parseInt(value);
    if(!isNaN(intValue) && (intValue == floatValue)){
      return [intValue, unit];
    }else{
      return [floatValue, unit];
    }
  },
  
  setState : function(state, unclampedState, rawState){
    var value;
    if(this.lfo){
      unclampedState = this.lfo(rawState);
      state = Math.max(0, Math.min(1, unclampedState));
    }
    var dh = Kumu.DHTML; 
    for(var i = 0; i < this.elements.length; i++){
      var node = this.elements[i];
      var from = this.argList[i][0][0]
      var fromUnit = this.argList[i][0][1];
      var to = this.argList[i][1][0];
      var toUnit = this.argList[i][1][1];
      var value;
      if(this.property == 'opacity'){
        value = from + ((to -from) * state);
      }else{
        var calc = from + ((to - from) * unclampedState);
        value = Math.round(calc) + (toUnit ? toUnit : 0);
      }
      if(this.property == 'opacity'){
        dh.setOpacity(node, value);
      }else{
        node.style[this.property.camelize()] = value;
      }
    }
  }

});

Kumu.Effect.StyleColorEffect = Kumu.create();

Kumu.extend(Kumu.Effect.StyleColorEffect.prototype, {
  initialize : function(elements, property, from, to){
    var self = Kumu.Effect;
    var dh = Kumu.DHTML;
    this.elements = Kumu.toArray(elements);
    this.property = property;
    this.argList = self._parseStyleArguments(Kumu.toArray(elements), property, from, to);
  },

  setState : function(state, unclampedState, rawState){
    var color;
    if(this.lfo){
      unclampedState = this.lfo(rawState);
      state = Math.max(0, Math.min(1, unclampedState));
    }
    
    for(var i = 0; i < this.elements.length; i++){
      var node = this.elements[i];
      var from = this.argList[i][0]
      var to = this.argList[i][1];
      color = Kumu.DHTML.blendColor(from, to, state);
      color = Kumu.DHTML.toHexString(color);
      node.style[this.property.camelize()] = color;
    }
  }

});


Kumu.Effect._createStyleEffect = function(elements, property, from, to, unit){
  var afx;
  var match;
  var units;
  var dh = Kumu.DHTML;
  var numericalRe = /^[0-9.]+(%|em|ex|px|in|cm|mm|pt|pc)?$/i;
  if(match = numericalRe.exec(to)){
    afx = Kumu.Effect.StyleNumericEffect;
    var frommatch = numericalRe.exec(from);
    frommatch = frommatch ? frommatch[1] : null;
    units = unit || match[1] || frommatch;
  }else if(typeof(to) == 'string' && dh.parseColor(to).length == 3){
    afx = Kumu.Effect.StyleColorEffect;
    units = null;
  }else{
    throw new TypeError("Unrecognised format for value of " + prop + ": '" + to + "'");
  }
  return new afx(elements, property, from, to, units);
}

Kumu.Effect._createCSSEFX = function(effector, style, option, elem){
  var options = arguments[2];
  var elements;
  if(options != null && !(typeof(options.length) == 'undefined' && typeof(options.nodeType) == 'undefined')){
    elements = options;
    options = null;
  }
  if(typeof(style) == 'string'){
    if(effector){
      return new Kumu.Effect.Effector(options).addEffect(new Kumu.Effect.CSSEffect(elements, style));
    }else{
      return new Kumu.Effect.CSSEffect(elements, style);
    }
  }else if(style != null){
    if(effector){
      return new Kumu.Effect.Effector(options).addEffect(new Kumu.Effect.PropertyEffect(elements, style));
    }else{
      return new Kumu.Effect.PropertyEffect(elements, style);
    }
  }    
}

Kumu.Effect.createCSSEffector = function(){
  var arg = Kumu.toArray(arguments);
  arg.unshift(true);
  return Kumu.Effect._createCSSEFX.apply(this, arg); 
}

Kumu.Effect.createCSSEffect = function(){
  var arg = Kumu.toArray(arguments);
  arg.unshift(false);
  return Kumu.Effect._createCSSEFX.apply(this, arg); 
}

