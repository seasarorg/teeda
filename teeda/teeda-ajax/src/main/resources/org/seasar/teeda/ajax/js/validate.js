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

if (typeof(Kumu.Validator) == 'undefined') {
  Kumu.Validator = {};
}

var KumuValidatorConf;

Kumu.Validator = Kumu.extend(Kumu.Validator, {
  _type : 'name',
  
  _validators : {},

  _resultObj : [],
    
  _createValidator : function(type){
    if(type.indexOf(':') > 1){
      var obj;
      var arr = type.split(':');
      eval('obj = new Kumu.Validator.'+arr[0]+'Validator("'+arr[1].toString()+'");');
      return obj;                  
    }else{
      var obj;
      eval('obj = new Kumu.Validator.'+type+'Validator();');
      return obj;
    }
  },
  
  modeId : function(type){
    if(type){
      this._type = 'id';
    }
  },
  
  _getElements : function(id){
    if(this._type == 'name'){
      return $n(id);
    }else if(this._type == 'id'){
      return [$i(id)];
    }
  },
  
  _createChain : function(ids){
    var chain = this._validators[ids];
    var e = this._getElements(ids);
    if(!chain && e){
      chain = new Kumu.Validator.ValidatorChain(e[0]);
      this._validators[ids] = chain;
    }
    return chain;
  },

  _parse : function(validateConf){
    if(validateConf){
      for(var v in validateConf){
        var conf = validateConf[v];
        if(conf instanceof Array){
          //chain
          var chain = this._createChain(v);
          if(chain){
            for(var i = 0; i < conf.length; i++){
              var validator = this._createValidator(conf[i]);
              chain.add(validator);
            }
          }
        }else if(typeof(conf) == 'string'){
          if(conf == 'result'){
            var e = this._getElements(v);
            if(e){
              this._resultObj.push(e[0]);
            }
          }
        }else if(conf instanceof Object){
          var chain = this._createChain(v);
          if(chain){
            for(var ids in conf){
              var validator = this._createValidator(ids);
              validator.showResult = conf[ids];
              chain.add(validator);
            }
          }
        }
      }
    }      
  },

  loadValidator : function(){
    if(KumuValidatorConf){
      this._regist(KumuValidatorConf);
      this._disabled();
    }
  },
    
  _disabled : function(){
    for(var i = 0; i < this._resultObj.length; i++){
      this._resultObj[i].disabled = true;
    }
  },

  _enabled : function(){
    for(var i = 0; i < this._resultObj.length; i++){
      this._resultObj[i].disabled = false;
    }
  },

  _validate : function(){
    var result = true;
    for(var v in this._validators){
      var validator = this._validators[v];
      if(!validator.validate()){
        this._disabled();
        result = false;
      }
    }
    if(result){
      this._enabled();
    }
  },
    
  _regist : function(validateConf){
    this._parse(validateConf);
    for(var v in this._validators){
      var elements = this._getElements(v);
      elements.map(function(element){
        if (element.type) {
          switch (element.type.toLowerCase()) {
            case 'checkbox':
            case 'radio':
              Kumu.Event.addEvent(element, 'click', this._validate.bindScopeAsEventListener(this));
              break;
            case 'password':
            case 'text':
            case 'textarea':
              Kumu.Event.addEvent(element, 'keyup', this._validate.bindScopeAsEventListener(this));
              break;
            case 'select-one':
              case 'select-multiple':
              Kumu.Event.addEvent(element, 'change', this._validate.bindScopeAsEventListener(this));
              break;
            }
        }
      }.bindScope(this));
    }
  }
});

Kumu.Validator.ValidatorBase = function(){
  this.result = false;
  this.showResult = function(element, result){
    this.result = result;
    if(result){
      element.style.background = '#ffffff';
    }else{
      element.style.background = '#ff4500';
    }
  }
};

Kumu.Validator.ValidatorChain = function(element){
    
  this.element = element;
  
  this.validators = [];
  
  this.add = function(validator){
    this.validators.push(validator);
  };
  
  this.validate = function(evt){
    this.result = false;
    for(var i = 0; i < this.validators.length; i++){
      var validator = this.validators[i];
      var result = validator.validate(this.element.value);
      validator.showResult(element, result);
      if(!result){
        return false;
      }
    }
    this.result = true;
    return true
  }
}

Kumu.Validator.RequiredValidator = function(){
  this.validate = function(v){
    if(v == null || v.length == 0){
      return false;
    }else{
      return true;
    } 
  }
}
Kumu.Validator.RequiredValidator.prototype =  new Kumu.Validator.ValidatorBase();

Kumu.Validator.RegularExpressionValidator = function(pattern){
  this.pattern = pattern;
  this.validate = function(v){
    var re = new RegExp(this.pattern);
    var v = v.toString();
    if(!v.match(re)){
      return false;      
    }else{
      return true;
    }
  }
}
Kumu.Validator.RegularExpressionValidator.prototype =  new Kumu.Validator.ValidatorBase();
   
Kumu.Validator.IntegerValidator = function(){
  this.validate = function(v){
    var re = /^[-]?([1-9]\d*)?\d$/;
    var v = v.toString();
    if(!v.match(re)){
      return false;      
    }else{
      return true;
    }
  }
}
Kumu.Validator.IntegerValidator.prototype = new Kumu.Validator.ValidatorBase();

Kumu.Validator.MinRangeValidator = function(value){
  this.value = value;
  this.validate = function(v){
    var v = v.toString();
    if(Number(v) > Number(this.value)){
      return true;
    }else{
      return false;
    }
  }
}
Kumu.Validator.MinRangeValidator.prototype = new Kumu.Validator.ValidatorBase();

Kumu.Validator.MaxRangeValidator = function(value){
  this.value = value;
  this.validate = function(v){
    var v = v.toString();
    if(Number(v) < Number(this.value)){
      return true;
    }else{
      return false;
    }
  }
}
Kumu.Validator.MaxRangeValidator.prototype = new Kumu.Validator.ValidatorBase();

Kumu.Validator.DoubleValidator = function(){
  this.validate = function(v){
    var re = /^[-]?([1-9]\d*)?\d(\.\d*)?$/;
    var v = v.toString();
    if(!v.match(re)){
      return false;      
    }else{
      return true;
    }
  }
}
Kumu.Validator.DoubleValidator.prototype = new Kumu.Validator.ValidatorBase();

Kumu.Validator.MinLengthValidator = function(value){
  this.value = value;
  this.validate = function(v){
    var v = v.toString();
    if(v.length > this.value){
      return true;
    }else{
      return false;
    }
  }
}
Kumu.Validator.MinLengthValidator.prototype = new Kumu.Validator.ValidatorBase();

Kumu.Validator.MaxLengthValidator = function(value){
  this.value = value;
  this.validate = function(v){
    var v = v.toString();
    if(v.length < this.value){
      return true;      
    }else{
      return false;      
    }
  }
}
Kumu.Validator.MaxLengthValidator.prototype = new Kumu.Validator.ValidatorBase();
Kumu.Event.addOnLoadEvent(Kumu.Validator.loadValidator.bindScope(Kumu.Validator));
