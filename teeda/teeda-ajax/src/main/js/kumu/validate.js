if (typeof(Kumu) == 'undefined') {
    Kumu = {};
}

if (typeof(Kumu.Validator) == 'undefined') {
    Kumu.Validator = {};
}

var KumuValidatorConf;

Kumu.Validator = Kumu.extend(Kumu.Validator, {
    _type : 'name',
    
    _validators : {},

    _resultObj : [],
        
    createValidator : function(type){
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
    
    getElements : function(id){
        if(this._type == 'name'){
            return $n(id);
        }else if(this._type == 'id'){
            return [$i(id)];
        }
    },
    
    createChain : function(ids){
        var chain = this._validators[ids];
        var e = this.getElements(ids);
        if(!chain && e){
            chain = new Kumu.Validator.ValidatorChain(e[0]);
            this._validators[ids] = chain;
        }
        return chain;
    }, 
       
    parse : function(validateConf){
        if(validateConf){
            for(var v in validateConf){
                var conf = validateConf[v];
                if(conf instanceof Array){
                    //chain
                    var chain = this.createChain(v);
                    if(chain){
                           for(var i = 0; i < conf.length; i++){
                           var validator = this.createValidator(conf[i]);
                           chain.add(validator);
                        }
                    }
                }else if(typeof(conf) == 'string'){
                    if(conf == 'result'){
                        var e = this.getElements(v);
                        if(e){
                            this._resultObj.push(e[0]);
                        }
                    }
                }else if(conf instanceof Object){
                    var chain = this.createChain(v);
                       if(chain){
                          for(var ids in conf){
                            var validator = this.createValidator(ids);
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
            this.regist(KumuValidatorConf);
            this.disabled();
        }
    },
        
    disabled : function(){
        for(var i = 0; i < this._resultObj.length; i++){
            this._resultObj[i].disabled = true;
        }
    },

    enabled : function(){
        for(var i = 0; i < this._resultObj.length; i++){
            this._resultObj[i].disabled = false;
        }
    },

    validate : function(){
        var result = true;
        for(var v in this._validators){
            var validator = this._validators[v];
            if(!validator.validate()){
                this.disabled();
                result = false;
            }
        }
        if(result){
            this.enabled();
        }
    },
        
    regist : function(validateConf){
        this.parse(validateConf);
        for(var v in this._validators){
            var elements = this.getElements(v);
            elements.map(function(element){
	            if (element.type) {
    	            switch (element.type.toLowerCase()) {
        	        	case 'checkbox':
            	    	case 'radio':
                	    	Kumu.Event.addEvent(element, 'click', this.validate.bindScopeAsEventListener(this));
                 		break;
                		case 'password':
                		case 'text':
                		case 'textarea':
                    		Kumu.Event.addEvent(element, 'keyup', this.validate.bindScopeAsEventListener(this));
                 		break;
                		case 'select-one':
               			case 'select-multiple':
                    		Kumu.Event.addEvent(element, 'change', this.validate.bindScopeAsEventListener(this));
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
        var re = /^[-]?\d*$/;
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
        if(v > this.value){
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
        if(v < this.value){
            return true;            
        }else{
            return false;            
        }
    }
}
Kumu.Validator.MaxRangeValidator.prototype = new Kumu.Validator.ValidatorBase();

Kumu.Validator.DoubleValidator = function(){
    this.validate = function(v){
        var re = /^[-]?\d*.?\d$/;
        var v = v.toString();
        if(!v.match(re)){
            //TODO errorMessage
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


Kumu.Event.addEvent(window, 'load', Kumu.Validator.loadValidator.bindScope(Kumu.Validator), false);

