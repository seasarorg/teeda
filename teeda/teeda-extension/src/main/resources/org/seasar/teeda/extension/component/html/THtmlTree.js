if (typeof(Teeda) == 'undefined') {
  Teeda = {};
}
if (typeof(Teeda.THtmlTree) == 'undefined') {
  Teeda.THtmlTree = {};
}
Teeda.THtmlTree = {

  COOKIE_DELIM  : ';',
  COOKIE_KEYVAL : '=',
  ATTRIB_DELIM  : ';',
  ATTRIB_KEYVAL : '=',

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
    var self = Teeda.THtmlTree;
    var search = name + self.COOKIE_KEYVAL;
    if (document.cookie){
      if (document.cookie.length > 0) {
        offset = document.cookie.indexOf(search);
        if (offset != -1) {
          offset += search.length;
          end = document.cookie.indexOf(self.COOKIE_DELIM, offset);
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
    var self = Teeda.THtmlTree;
    var attribMap = Teeda.THtmlTree.getCookie(cookieName);
    attribMap[attribName] = attribValue;
    self.setCookie(cookieName,attribMap);
  },
  getCookieAttrib : function (cookieName, attribName){
    var self = Teeda.THtmlTree;
    var attribMap = self.getCookie(cookieName);
    return attribMap[attribName];
  },
  getCookie : function (cookieName){
    var self = Teeda.THtmlTree;
    var attribMap = new Array();
    var cookie = self.getRawCookie(cookieName);
    if (typeof( cookie ) != 'undefined'  && cookie != null) {
      var attribArray = cookie.split(self.ATTRIB_DELIM);
      for (var i=0;i<attribArray.length;i++) {
          var index = attribArray[i].indexOf(self.ATTRIB_KEYVAL);
          var name =  attribArray[i].substring(0,index);
          var value = attribArray[i].substring(index+1);
          attribMap[name] = value;
      }
    }
    return attribMap;
  },
  setCookie : function (cookieName, attribMap){
    var self = Teeda.THtmlTree;
    var attrib = '';
    for (var name in attribMap) {
        var value = attribMap[name];
        if (typeof( value ) != 'undefined' && value != null && value != '' && typeof(value) != 'function') {
            if (name.indexOf(self.ATTRIB_KEYVAL) < 0 && value.indexOf(self.ATTRIB_KEYVAL) < 0 &&
                name.indexOf(self.ATTRIB_DELIM) < 0 && value.indexOf(self.ATTRIB_DELIM) < 0) {
                attrib += ((attrib == '') ? '' : self.ATTRIB_DELIM);
                attrib += (name + self.ATTRIB_KEYVAL + value);
             }
        }
    }
    document.cookie = cookieName + self.COOKIE_KEYVAL + escape(attrib);
  }

};
