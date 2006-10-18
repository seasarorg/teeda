if (typeof(Teeda) == 'undefined') {
  Teeda = {};
}
if (typeof(Teeda.THtmlInputCommaText) == 'undefined') {
  Teeda.THtmlInputCommaText = {};
}
Teeda.THtmlInputCommaText = {
  removeComma : function(obj, groupingSeparator) {
    if (!obj.value) {
      return;
    }
    reg = new RegExp(groupingSeparator, 'g');
    obj.value = obj.value.replace(reg, '');
    if (document.all) {
      tRNG = obj.createTextRange();
      tRNG.select();
    }
  },
  addComma : function(obj, length, groupingSeparator, decimalSeparator) {
    var o = obj;
    if(length >= 0) {
      o = Teeda.THtmlInputCommaText.checkFraction(obj, length, decimalSeparator);
    }
    var regGroup = new RegExp(groupingSeparator, 'g');
    var regDecimal = new RegExp(decimalSeparator, 'g');
    o.value = o.value.replace(regGroup, '');
    if(o.value.match(/^-/)){
      o.value = o.value.replace(/^-/, '');
      sign = '-';
    } else {
      sign = '';
    }
    if (!o.value) {
      o.value = sign;
      return;
    }
    var vals = o.value.split(decimalSeparator);
    var val1 = vals[0].match(regDecimal).reverse().join('');
    val1 = val1.replace(/(\d{3})/g, '$1' + groupingSeparator);
    val1 = val1.match(regDecimal).reverse().join('');
    if(val1.length > 0 && val1.charAt(0) == groupingSeparator) {
      val1 = val1.substring(1, val1.length);
    }
    if (vals.length == 1) {
      o.value = sign + val1;
    } else if (length == 0) {
      o.value = sign + val1;
    } else {
      o.value = sign + val1 + decimalSeparator + vals[1];
    }
  },
  checkFraction : function(obj, length, decimalSeparator) {
    var vals = obj.value.split(decimalSeparator);
    var len = vals.length;
    if(len == 2) {
      var integer = vals[0];
      var fraction = vals[1];
      if(fraction.length > length) {
        fraction = fraction.substring(0, length);
        if(fraction != "") {
          obj.value = integer + decimalSeparator + fraction;
        } else {
          obj.value = integer;
        }
      }
    }
    return obj;
  },
  convertByKey : function(o) {
    if(o.value.match(/[mM]/) != null) {
     o.value=o.value.replace(/[mM]/g, '000');
    }
    if(o.value.match(/[bB]/) != null) {
     o.value=o.value.replace(/[bB]/g, '000000');
    }
    if(o.value.match(/[tT]/) != null) {
     o.value=o.value.replace(/[tT]/g, '000000000');
    }
    if(o.value.match(/([^0-9mbtMBT\-\.\,])/)!=null) {
     o.value=o.value.replace(/([^0-9mbt\-\.\,]|[^mbtMBT])/g,'');
     return true;
    }
  },
  keycheckForNumber : function(e, obj, length, decimalSeparator) {
    var b = Teeda.THtmlInputCommaText.checkEnter(e);
    if(b) {
      Teeda.THtmlInputCommaText.checkFraction(obj, length, decimalSeparator);
    }
    return Teeda.THtmlInputCommaText.isAllowedUnit(e) || Teeda.THtmlInputCommaText.isNumber(e) || Teeda.THtmlInputCommaText.isBackspace(e) || Teeda.THtmlInputCommaText.isPeriod(e) || Teeda.THtmlInputCommaText.isMovable(e) || Teeda.THtmlInputCommaText.isMinus(e) || Teeda.THtmlInputCommaText.isEnter(e) || Teeda.THtmlInputCommaText.isComma(e);
  },
  getKeyCode : function(e) {
    var c = e.keyCode;
    if(c <= 0) {
    c = e.which;
    }
    return c;
  },
  isAllowedUnit : function(e) {
    var c = Teeda.THtmlInputCommaText.getKeyCode(e);
    return (c == 66 ||c == 77 ||c == 84) || (c == 98 ||c == 109 ||c == 116);
  },
  isNumber : function(e) {
    var c = Teeda.THtmlInputCommaText.getKeyCode(e);
    return (c >= 48 && c <= 57) || (c >= 96 && c <= 105);
  },
  isBackspace : function(e) {
    var c = Teeda.THtmlInputCommaText.getKeyCode(e);
    return (c == 8);
  },
  isPeriod : function(e) {
    var c = Teeda.THtmlInputCommaText.getKeyCode(e);
    return (c == 46 ||c == 110||c == 190);
  },
  isMovable : function(e) {
    var c = Teeda.THtmlInputCommaText.getKeyCode(e);
    return (c >= 37 && c <= 40) || (c == 9);
  },
  isMinus : function(e) {
    var c = Teeda.THtmlInputCommaText.getKeyCode(e);
    return (c == 45 ||c == 189);
  },
  isEnter : function(e) {
    var c = Teeda.THtmlInputCommaText.getKeyCode(e);
    return (c == 13);
  },
  isComma : function(e) {
    var c = Teeda.THtmlInputCommaText.getKeyCode(e);
    return (c == 44 ||c == 188);
  },
  checkEnter : function(e) {
    if(Teeda.THtmlInputCommaText.isEnter(e)) {
      return true;
    }
    return false;
  }
};
