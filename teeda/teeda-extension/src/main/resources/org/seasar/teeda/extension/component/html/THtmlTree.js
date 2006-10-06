if (typeof(Teeda) == 'undefined') {
  Teeda = {};
}
if (typeof(Teeda.THtmlTree) == 'undefined') {
  Teeda.THtmlTree = {};
}
Teeda.THtmlTree = {

  Teeda.THtmlTree.COOKIE_DELIM  : ';',
  Teeda.THtmlTree.COOKIE_KEYVAL : '=',
  Teeda.THtmlTree.ATTRIB_DELIM  : ';',
  Teeda.THtmlTree.ATTRIB_KEYVAL : '=',

  treeNavClick : function(spanId, navImageId, image1, image2, nodeImgId, expandImg, collapseImg, cookieName, nodeId) {
    var navSpan = document.getElementById(spanId);
    var displayStyle = navSpan.style.display;
    if (displayStyle == 'none') {
      displayStyle = 'block'
      Teeda.THtmlTree.setCookieAttrib(cookieName, nodeId, 'x');
    } else {
      displayStyle = 'none';
      Teeda.THtmlTree.setCookieAttrib(cookieName, nodeId, 'c');
    }
    navSpan.style.display = displayStyle;
    if (navImageId != '') {
      var navImage = document.getElementById(navImageId);
      if (navImage.src.indexOf(image1) >= 0) {
        navImage.src = image2;
      } else {
        navImage.src = image1;
      }
    }
    if (nodeImgId != '') {
      var nodeImg = document.getElementById(nodeImgId);
      if (nodeImg.src.indexOf(expandImg) >=0) {
        nodeImg.src = collapseImg;
      } else {
        nodeImg.src = expandImg;
      }
    }
  },
  getRawCookie : function (name) {
    var search = name + Teeda.THtmlTree.COOKIE_KEYVAL;
    if (document.cookie){
      if (document.cookie.length > 0) {
        offset = document.cookie.indexOf(search);
        if (offset != -1) {
          offset += search.length;
          end = document.cookie.indexOf(Teeda.THtmlTree.COOKIE_DELIM, offset);
          if (end == -1) {
            end = document.cookie.length;
          }
          return unescape(document.cookie.substring(offset, end));
        }
      }
    }
    return null;
  },
  setCookieAttrib : function (cookieName, attribName, attribValue){
    var attribMap = Teeda.THtmlTree.getCookie(cookieName);
    attribMap[attribName] = attribValue;
    Teeda.THtmlTree.setCookie(cookieName,attribMap);
  },
  getCookieAttrib : function (cookieName, attribName){
    var attribMap = Teeda.THtmlTree.getCookie(cookieName);
    return attribMap[attribName];
  },
  getCookie : function (cookieName){
    var attribMap = new Array();
    var cookie = Teeda.THtmlTree.getRawCookie(cookieName);
    if (typeof( cookie ) != 'undefined'  && cookie != null) {
      var attribArray = cookie.split(Teeda.THtmlTree.ATTRIB_DELIM);
      for (var i=0;i<attribArray.length;i++) {
          var index = attribArray[i].indexOf(Teeda.THtmlTree.ATTRIB_KEYVAL);
          var name =  attribArray[i].substring(0,index);
          var value = attribArray[i].substring(index+1);
          attribMap[name] = value;
      }
    }
    return attribMap;
  },
  setCookie : function (cookieName, attribMap){
    var attrib = '';
    for (var name in attribMap) {
        var value = attribMap[name];
        if (typeof( value ) != 'undefined' && value != null && value != '' && typeof(value) != 'function') {
            if (name.indexOf(Teeda.THtmlTree.ATTRIB_KEYVAL) < 0 && value.indexOf(Teeda.THtmlTree.ATTRIB_KEYVAL) < 0 &&
                name.indexOf(Teeda.THtmlTree.ATTRIB_DELIM) < 0 && value.indexOf(Teeda.THtmlTree.ATTRIB_DELIM) < 0) {
                attrib += ((attrib == '') ? '' : Teeda.THtmlTree.ATTRIB_DELIM);
                attrib += (name + Teeda.THtmlTree.ATTRIB_KEYVAL + value);
             }
        }
    }
    document.cookie = cookieName + Teeda.THtmlTree.COOKIE_KEYVAL + escape(attrib);
  }

};
