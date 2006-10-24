if (typeof(Teeda) == 'undefined') {
  Teeda = {};
}
if (typeof(Teeda.THtmlTree) == 'undefined') {
  Teeda.THtmlTree = {};
}
Teeda.THtmlTree = {

  treeNavClick : function(spanId, navImageId, image1, image2, nodeImgId, expandImg, collapseImg, cookieName, nodeId, contextPath) {
    var navSpan = document.getElementById(spanId);
    var displayStyle = navSpan.style.display;
    if (displayStyle == 'none') {
      displayStyle = 'block'
    } else {
      displayStyle = 'none';
    }
    navSpan.style.display = displayStyle;
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
  }
};
