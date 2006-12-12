if (typeof(Teeda) == 'undefined') {
  Teeda = {};
}
if (typeof(Teeda.THtmlTree) == 'undefined') {
  Teeda.THtmlTree = {};
}
Teeda.THtmlTree = {

  KEYCODE_LEFT  : 37,
  KEYCODE_UP    : 38,
  KEYCODE_RIGHT : 39,
  KEYCODE_DOWN  : 40,

  treeNavClick : function(divId, navImageId, image1, image2, nodeImgId, expandImg, collapseImg, cookieName, nodeId, contextPath) {
    if(!document.getElementById) {
      return;
    }
    var navDiv = document.getElementById(divId);
    var hiddenId = divId + ':' + 'treeExpanded';
    var hidden = document.getElementById(hiddenId);
    var displayStyle = navDiv.style.display;
    if (displayStyle == 'none') {
      displayStyle = 'block'
      hidden.value = true;
    } else {
      displayStyle = 'none';
      hidden.value = false;
    }
    navDiv.style.display = displayStyle;
    if (navImageId != '') {
      var navImage = document.getElementById(navImageId);
      if (navImage.src.indexOf(image1) >= 0) {
        navImage.src = contextPath + '/' + image2;
      } else {
        navImage.src = contextPath + '/' + image1;
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
  walkTreeNode : function (e, nodeId, me, rootId, nextDivId, navImageId, navImage, nodeImageId, nodeImage) {
    if(!document.getElementById || !document.getElementsByTagName) {
      return;
    }
    var self = Teeda.THtmlTree;
    var keyCode = self._getKeyCode(e);
    if(keyCode == self.KEYCODE_LEFT) {
    
      //TODO : hide all nodes.
      
    } else if(keyCode == self.KEYCODE_UP) {
    
      //TODO : up to sibling or parent node.
      
    } else if(keyCode == self.KEYCODE_RIGHT) {
      var node = document.getElementById(nodeId);
      node.onclick();
    } else if(keyCode == self.KEYCODE_DOWN) {
      var root = document.getElementById(rootId);
      var divs = root.getElementsByTagName('DIV');
      var firstDiv = null;
      if(typeof(firstDiv) != 'function') {
        return;
      }
      if(divs.length == 1) {
        firstDiv = divs;
      } else {
        firstDiv = divs[0];
      }
      if(firstDiv = null || firstDiv.tagName == 'undefined') {
        return;
      }
      var a = firstDiv.getElementsByTagName('A');
      if(a == null || a.tagName == 'undefined') {
        return;
      }
      a[0].focus();
    } else {
    }
  },
  _getKeyCode : function(e) {
    var c = e.keyCode;
    if(c <= 0) {
    c = e.which;
    }
    return c;
  }
  
};
