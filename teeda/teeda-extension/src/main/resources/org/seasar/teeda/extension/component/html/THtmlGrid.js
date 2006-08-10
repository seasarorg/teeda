if (typeof(Teeda) == 'undefined') {
  Teeda = {};
}
if (typeof(Teeda.THtmlGrid) == 'undefined') {
  Teeda.THtmlGrid = {};
}
Teeda.THtmlGrid = {
  getFirstChildByTagName: function (fromNode, elemName) {
    var self = Teeda.THtmlGrid;
    if (!fromNode.hasChildNodes()) {
      return null;
    }
    var children = fromNode.childNodes;
    var len = children.length;
    for (var i = 0; i < len; i++) {
      var child = children.item(i);
      if (!child.tagName) {
        continue;
      }
      if (elemName == child.tagName.toLowerCase()) {
        return child;
      }
      var ret = self.getFirstChildByTagName(child, elemName);
      if (ret) {
        return ret;
      }
    }
  },
  editOn : function(div) {
    var self = Teeda.THtmlGrid;
    var span = self.getFirstChildByTagName(div, 'span');
    var input = self.getFirstChildByTagName(div, 'input');
    input.value = span.innerHTML;
    span.style.display = 'none';
    input.style.display = 'inline';
    input.focus();
  },
  editOff: function (input) {
    var self = Teeda.THtmlGrid;
    var span = self.getFirstChildByTagName(input.parentNode, 'span');
    span.innerHTML = input.value;
    input.style.display = 'none';
    span.style.display = 'inline';
  }
};
