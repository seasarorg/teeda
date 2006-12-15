if (typeof(Teeda) == 'undefined') {
  Teeda = {};
}
if (typeof(Teeda.THtmlGrid) == 'undefined') {
  Teeda.THtmlGrid = {};
}
Teeda.THtmlGrid = {
  _trim : function (s) {
    return s.replace(/^\s+|\s+$/g, '');
  },
  _getFirstChildByTagName : function (fromNode, elemName) {
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
      var ret = self._getFirstChildByTagName(child, elemName);
      if (ret) {
        return ret;
      }
    }
  },
  editOn : function(div) {
    var self = Teeda.THtmlGrid;
    var span = self._getFirstChildByTagName(div, 'span');
    if (span.style.display == 'none') {
      return;
    }
    var ascendantDiv = div.parentNode.parentNode;
    ascendantDiv.style.width = ascendantDiv.offsetWidth;
    var input = self._getFirstChildByTagName(div, 'input');
    input.value = self._trim(span.innerHTML);
    span.style.display = 'none';
    input.style.display = 'inline';
    input.focus();
  },
  editOff : function (input) {
    var self = Teeda.THtmlGrid;
    var span = self._getFirstChildByTagName(input.parentNode, 'span');
    span.innerHTML = input.value;
    input.style.display = 'none';
    span.style.display = 'inline';
  },
  _adjustWidth : function (headerTable, bodyTable) {
    var self = Teeda.THtmlGrid;
    var headerRowLength = headerTable.rows.length;
    var bodyRowLength = bodyTable.rows.length;
    if (headerRowLength <= 0 || bodyRowLength <= 0 || bodyRowLength < headerRowLength) {
      return;
    }
    for (var rowCount = 0; rowCount < headerRowLength; rowCount++) {
      var headerCells = headerTable.rows[rowCount % headerRowLength].cells;
      var bodyRow;
      for (var bodyRowCount = 0; bodyRowCount < bodyRowLength; bodyRowCount++) {
        if (bodyTable.rows[rowCount].style.display != 'none') {
          bodyRow = bodyTable.rows[rowCount];
          break;
        }
      }
      if (!bodyRow) {
        return;
      }
      var bodyCells = bodyTable.rows[rowCount].cells;
      var headerCellsLength = headerCells.length;
      var bodyCellsLength = bodyCells.length;
      if (headerCellsLength != bodyCellsLength) {
        return;
      }
      for (var cellCount = 0; cellCount < headerCellsLength; cellCount++) {
        var headerDiv = headerCells[cellCount].firstChild;
        var bodyDiv = bodyCells[cellCount].firstChild;
        var headerCellWidth = headerDiv.offsetWidth;
        var bodyCellWidth = bodyDiv.offsetWidth;
        var longer = Math.max(headerCellWidth, bodyCellWidth);
        var originalWidth = headerDiv.style.width;
        if (originalWidth) {
          originalWidth = headerDiv.style.width.replace(/px/,"");
          longer = Math.max(longer, originalWidth);
        }
        headerDiv.style.width = longer;
        bodyDiv.style.width = longer;
      }
    }
  },
  adjustGridSize : function (gridId) {
    var self = Teeda.THtmlGrid;
    var leftHeaderTable = document.getElementById(gridId + 'LeftHeaderTable');
    var leftBodyTable = document.getElementById(gridId + 'LeftBodyTable');
    var rightHeaderTable = document.getElementById(gridId + 'RightHeaderTable');
    var rightBodyTable = document.getElementById(gridId + 'RightBodyTable');
    if (leftHeaderTable && leftBodyTable) {
      self._adjustWidth(leftHeaderTable, leftBodyTable);
    }
    if (rightHeaderTable && rightBodyTable) {
      self._adjustWidth(rightHeaderTable, rightBodyTable);
    }
  },
  _loadGridRowsFor : function (srcTable, destTable) {
    var srcTbody = srcTable.tBodies[0];
    destTable.appendChild(srcTbody);
    srcTable.parentNode.removeChild(srcTable);
  },
  loadGridRows : function (gridId) {
    var self = Teeda.THtmlGrid;
    var leftSrcTable = document.getElementById(gridId + 'LeftBodySource');
    var leftDestTable = document.getElementById(gridId + 'LeftBodyTable');
    var rightSrcTable = document.getElementById(gridId + 'RightBodySource');
    var rightDestTable = document.getElementById(gridId + 'RightBodyTable');
    if (leftSrcTable && leftDestTable) {
      self._loadGridRowsFor(leftSrcTable, leftDestTable);
    }
    if (rightSrcTable && rightDestTable) {
      self._loadGridRowsFor(rightSrcTable, rightDestTable);
    }
  }
};
