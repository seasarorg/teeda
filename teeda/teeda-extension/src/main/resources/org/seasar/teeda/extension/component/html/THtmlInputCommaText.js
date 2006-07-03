function removeComma(obj, groupingSeparator) {
  if (!obj.value) {
    return;
  }
  reg = new RegExp(groupingSeparator, "g");
  obj.value = obj.value.replace(reg, '');
  if (document.all) {
    tRNG = obj.createTextRange();
    tRNG.select();
  }
}
function addComma(obj, length, groupingSeparator, decimalSeparator) {
  var o = obj;
  if(length > 0) {
    o = checkFraction(obj, length, decimalSeparator);
  }

  var regGroup = new RegExp(groupingSeparator, "g");
  var regDecimal = new RegExp(decimalSeparator, "g");
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
  } else {
    o.value = sign + val1 + decimalSeparator + vals[1];
  }
}

function checkFraction(obj, length, decimalSeparator) {
  var vals = obj.value.split(decimalSeparator);
  if(vals.length == 2) {
    var integer = vals[0];
    var fraction = vals[1];
    if(fraction.length > length) {
      fraction = fraction.substring(0, length);
      obj.value = integer + decimalSeparator + fraction;
    }
  }
  return obj;
}

function convertByKey(o) {
  if(o.value.match(/[m]/) != null) {
   o.value=o.value.replace(/[m]/g, "000");
  }
  if(o.value.match(/[b]/) != null) {
   o.value=o.value.replace(/[b]/g, "000000");
  }
  if(o.value.match(/[t]/) != null) {
   o.value=o.value.replace(/[t]/g, "000000000");
  }
  if(o.value.match(/([^0-9mbt\-\.\,])/)!=null) {
   o.value=o.value.replace(/([^0-9mbt\-\.\,]|[^mbtMBT])/g,"");
   return true;
  }
}
function keycheckForNumber(e) {
 return isAllowedUnit(e) || isNumber(e) || isBackspace(e) || isPeriod(e) || isMovable(e) || isMinus(e) || isEnter(e) || isComma(e);
}

function getKeyCode(e) {
 var c = e.keyCode;
 if(c <= 0) {
   c = e.which;
 }
 return c;
}
function isAllowedUnit(e) {
 var c = getKeyCode(e);
 return (c == 66 ||c == 77 ||c == 84) || (c == 98 ||c == 109 ||c == 116);
}

function isNumber(e) {
 var c = getKeyCode(e);
 return (c >= 48 && c <= 57) || (c >= 96 && c <= 105);
}

function isBackspace(e) {
 var c = getKeyCode(e);
 return (c == 8);
}

function isPeriod(e) {
 var c = getKeyCode(e);
 return (c == 46 ||c == 110||c == 190);
}

function isMovable(e) {
 var c = getKeyCode(e);
 return (c >= 37 && c <= 40) || (c == 9);
}

function isMinus(e) {
 var c = getKeyCode(e);
 return (c == 45 ||c == 189);
}

function isEnter(e) {
 var c = getKeyCode(e);
 return (c == 13);
}

function isComma(e) {
 var c = getKeyCode(e);
 return (c == 44 ||c == 188);
}
