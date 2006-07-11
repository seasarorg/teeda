function removeDateSeparate(obj,sep) {
  if (!obj.value) {
    return;
  }
  if (sep == '') {
    return;
  }
  obj.value = obj.value.replace(new RegExp(sep,'g'), '');
  if (document.all) {
    tRNG = obj.createTextRange();
    tRNG.select();
  }
}
function addDateSeparate(obj,sep,pos) {
  obj.value = obj.value.replace(new RegExp(sep,'g'), '');
  if (!obj.value) {
    return;
  }
  if (pos == '' || sep == '') {
    return;
  }
  var v = obj.value;
  var posarr = pos.split(",");
  var count = 0;
  for(i = 0; i < posarr.length; i++) {
   var p = eval(posarr[i]);
   if(v.length > p) {
     count++;
     v = v.substr(0,p) + sep + v.substr(p,v.length);
   }
  }
  obj.value = v;
  //decide what browser is.
  var id = obj.id;
  var o = document.getElementById(id);
  o.setAttribute("maxlength", obj.value.length + count);
  obj.maxlength = v.length;
}
function convertByKey(o) {
  if(o.value.match(/([^0-9\-\.])/)!=null) {
  o.value=o.value.replace(/([^0-9\-\.])/g,"")
  return true;
 }
}
function keycheckForDate(e) {
 return isNumber(e) || isBackspace(e) || isMovable(e) || isEnter(e);
}

function getKeyCode(e) {
 var c = e.keyCode;
 if(c <= 0) {
   c = e.which;
 }
 return c;
}

function isNumber(e) {
 var c = getKeyCode(e);
 return (c >= 48 && c <= 57) || (c >= 96 && c <= 105);
}

function isBackspace(e) {
 var c = getKeyCode(e);
 return (c == 8);
}

function isMovable(e) {
 var c = getKeyCode(e);
 return (c >= 37 && c <= 40) || (c == 9);
}

function isEnter(e) {
 var c = getKeyCode(e);
 return (c == 13);
}
