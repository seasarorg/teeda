if (typeof(Teeda) == 'undefined') {
  Teeda = {};
}
if (typeof(Teeda.THtmlInputDateText) == 'undefined') {
  Teeda.THtmlInputDateText = {};
}
Teeda.THtmlInputDateText = {
  removeDelimeter : function(obj, delimeter, worklen) {
    var o = obj.value;
    if (!o) {
      return;
    }
    reg = new RegExp(delimeter, 'g');
    o = o.replace(reg, '');
    if(o.length > worklen) {
      o = o.substring(o.length - worklen);
    }
    obj.value = o;
    if (document.all) {
      tRNG = obj.createTextRange();
      tRNG.select();
    }
  },
  addDelimeter : function(obj, format, worklen, threshold, delim) {
    var o = obj.value;
    var length = o.length;
    if(length != worklen) {
      return o;
    }
    var isYYYY = (format.indexOf('yyyy') != -1);
    if(isYYYY) {
      var yy = o.substr(0, 2);
      var remain = o.substr(2, length);
      if(yy >= threshold) {
        yy = "19" + yy;
      } else {
        yy = "20" + yy;
      }
      o = yy + remain;
    }
    var isDD = (format.indexOf('dd') != -1);
    var convert = '6,4';
    if(!isDD) {
      convert = '4';
    }
    o = Teeda.THtmlInputDateText.addDateSeparate(o, delim, convert);
    obj.value = o
  },
  addDateSeparate : function (obj, sep, pos) {
    obj = obj.replace(new RegExp(sep,'g'), '');
    if (!obj) {
      return;
    }
    if (pos == '' || sep == '') {
      return;
    }
    var v = obj;
    posarr = pos.split(",");
    for(i = 0; i < posarr.length; i++) {
      p = eval(posarr[i]);
      if(v.length > p) {
        v = v.substr(0,p) + sep + v.substr(p,v.length);
      }
    }
    return v;
  },
  checkFormat : function(obj, format, decimalSeparator) {
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
  },
  convertByKey : function(o) {
    if(o.value.match(/([^0-9])/)!=null) {
     o.value=o.value.replace(/([^0-9])/g,'');
     return true;
    }
  },
  keycheckForNumber : function(e, obj, format, worklen, threshold, delim) {
    var self = Teeda.THtmlInputDateText;
    var b = self.checkEnter(e);
    if(b) {
      self.addDelimeter(obj, format, worklen, threshold, delim);
    }
    return self.isNumber(e) || self.isSlash(e) || self.isHiphen(e) || self.isBackspace(e) || self.isMovable(e) || self.isEnter(e);
  },
  getKeyCode : function(e) {
    var c = e.keyCode;
    if(c <= 0) {
    c = e.which;
    }
    return c;
  },
  isNumber : function(e) {
    var c = Teeda.THtmlInputDateText.getKeyCode(e);
    return (c >= 48 && c <= 57) || (c >= 96 && c <= 105);
  },
  isSlash : function(e) {
    var c = Teeda.THtmlInputDateText.getKeyCode(e);
    return (c == 47) || (c == 191);
  },
  isHiphen : function(e) {
    var c = Teeda.THtmlInputDateText.getKeyCode(e);
    return (c == 45) || (c == 189);
  },
  isBackspace : function(e) {
    var c = Teeda.THtmlInputDateText.getKeyCode(e);
    return (c == 8);
  },
  isMovable : function(e) {
    var c = Teeda.THtmlInputDateText.getKeyCode(e);
    return (c >= 35 && c <= 40) || (c == 9);
  },
  isEnter : function(e) {
    var c = Teeda.THtmlInputDateText.getKeyCode(e);
    return (c == 13);
  },
  checkEnter : function(e) {
    if(Teeda.THtmlInputDateText.isEnter(e)) {
      return true;
    }
    return false;
  }
};
