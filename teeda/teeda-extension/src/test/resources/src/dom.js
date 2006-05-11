if (typeof(Kumu.DOM) == 'undefined') {
    Kumu.DOM = {};
};

Kumu.extend(Kumu.DOM, {

    createDOM : function (name, attrs) {
        var element;
        var self = Kumu.DOM;
        if (typeof(name) == 'string') {
            if (attrs && "name" in attrs) {
                name = '<' + name + ' name="' + self.escapeHTML(attrs.name) + '">';
            }
            element = document.createElement(name);
        } else {
            element = name;
        }
        if (attrs) {
            self.updateNodeAttributes(element, attrs);
        }
        if (arguments.length <= 2) {
            return element;
        } else {
            var args = Kumu.add([element], arguments, 2);
            return self.appendChildNodes.apply(this, args);
        }
        
    },
    
    appendChildNodes: function (element) {
        var args = arguments;
        for(var i = 1; i < args.length; i++){            
            var node = args[i];
            if(node instanceof Array){
                for(var j = 0; j < node.length; j++){
                    if(typeof(node.nodeType) != 'undefined' && node.nodeType > 0){
         	            element.appendChild(node);            
                    }else if(typeof(node) == 'string'){
                        var textN = document.createTextNode(node);
                        element.appendChild(textN);
                    }
                }
            }else{
                if(typeof(node.nodeType) != 'undefined' && node.nodeType > 0){
         	        element.appendChild(node);            
                }else if(typeof(node) == 'string'){
                    var textN = document.createTextNode(node);
                    element.appendChild(textN);
                }
           }
        }
        return element;
    },
    
    updateNodeAttributes : function (node, attrs) {
        var element = $i(node);
        if (attrs) {
            for (var k in attrs) {
                var v = attrs[k];
                if (k.substring(0, 2) == "on") {
                    if (typeof(v) == "string") {
                        v = new Function(v);
                    }
                    element[k] = v;
                } else {
                   element.setAttribute(k, v);
                }
           }  
        }
        return element;
    },
    
    insertBefore : function(node, ref){
        if((!node)||(!ref)){ 
	        return false; 
        }
        node = $i(node);
        ref = $i(ref);
	    var parent = ref.parentNode;
	    parent.insertBefore(node, ref);
	    return true;
    },

    insertAfter : function(node, ref){
        if((!node)||(!ref)){ 
	        return false; 
	    }
	    var self = Kumu.DOM;
	    node = $i(node);
        ref = $i(ref);
	    var pn = ref.parentNode;
	    if(ref == pn.lastChild){
		    pn.appendChild(node);
	    }else{
		    return self.insertBefore(node, ref.nextSibling);
	    } 
	    return true;
    },

    insert : function(node, ref, position){
	    if((!node)||(!ref)||(!position)){ 
	        return false; 
	    }
	    var self = Kumu.DOM;
	    node = $i(node);
        ref = $i(ref);
	    switch(position.toLowerCase()){
		    case "before":
			    return self.insertBefore(node, ref);
		    case "after":
			    return self.insertAfter(node, ref);
		    case "first":
			    if(ref.firstChild){
				    return self.insertBefore(node, ref.firstChild);
			    }else{
				    ref.appendChild(node);
				    return true;
			    }
		        break;
		    default: 
			    ref.appendChild(node);
			    return true;
	    }
    },
    
    update:function(node, html){
        $i(node).innerHTML = html;
    },
    
    escapeHTML: function (s) {
        return s.replace(/&/g, "&amp;"
            ).replace(/"/g, "&quot;"
            ).replace(/</g, "&lt;"
            ).replace(/>/g, "&gt;");
    }
    
});

var UL = Kumu.bind(Kumu.DOM.createDOM)("ul");
var OL = Kumu.bind(Kumu.DOM.createDOM)("ol");
var LI = Kumu.bind(Kumu.DOM.createDOM)("li");
var TD = Kumu.bind(Kumu.DOM.createDOM)("td");
var TR = Kumu.bind(Kumu.DOM.createDOM)("tr");
var TBODY = Kumu.bind(Kumu.DOM.createDOM)("tbody");
var THEAD = Kumu.bind(Kumu.DOM.createDOM)("thead");
var TFOOT = Kumu.bind(Kumu.DOM.createDOM)("tfoot");
var TABLE = Kumu.bind(Kumu.DOM.createDOM)("table");
var TH = Kumu.bind(Kumu.DOM.createDOM)("th");
var INPUT = Kumu.bind(Kumu.DOM.createDOM)("input");
var SPAN = Kumu.bind(Kumu.DOM.createDOM)("span");
var A = Kumu.bind(Kumu.DOM.createDOM)("a");
var DIV = Kumu.bind(Kumu.DOM.createDOM)("div");
var IMG = Kumu.bind(Kumu.DOM.createDOM)("img");
var BUTTON = Kumu.bind(Kumu.DOM.createDOM)("button");
var TT = Kumu.bind(Kumu.DOM.createDOM)("tt");
var PRE = Kumu.bind(Kumu.DOM.createDOM)("pre");
var H1 = Kumu.bind(Kumu.DOM.createDOM)("h1");
var H2 = Kumu.bind(Kumu.DOM.createDOM)("h2");
var H3 = Kumu.bind(Kumu.DOM.createDOM)("h3");
var BR = Kumu.bind(Kumu.DOM.createDOM)("br");
var HR = Kumu.bind(Kumu.DOM.createDOM)("hr");
var LABEL = Kumu.bind(Kumu.DOM.createDOM)("label");
var TEXTAREA = Kumu.bind(Kumu.DOM.createDOM)("textarea");
var FORM = Kumu.bind(Kumu.DOM.createDOM)("form");
var P = Kumu.bind(Kumu.DOM.createDOM)("p");
var SELECT = Kumu.bind(Kumu.DOM.createDOM)("select");
var OPTION = Kumu.bind(Kumu.DOM.createDOM)("option");
var OPTGROUP = Kumu.bind(Kumu.DOM.createDOM)("optgroup");
var LEGEND = Kumu.bind(Kumu.DOM.createDOM)("legend");
var FIELDSET = Kumu.bind(Kumu.DOM.createDOM)("fieldset");
var STRONG = Kumu.bind(Kumu.DOM.createDOM)("strong");
var CANVAS = Kumu.bind(Kumu.DOM.createDOM)("canvas");
