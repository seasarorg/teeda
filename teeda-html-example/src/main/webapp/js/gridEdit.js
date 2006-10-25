function doLinkAction(aElem, buttonId) {
    var form        = document.forms[0];
    var hrefValue   = aElem.href.replace("#", "");
	var queryString = hrefValue.substring(aElem.href.indexOf("?") + 1, aElem.href.length);
    var querys      = queryString.split("&");

	// create input form
	var query;
	for (var i = 0; query = querys[i]; i++) {
		var splitIndex = query.indexOf("=");
		var elemId;
		var elemValue;
		if (splitIndex > 0) {
			elemId    = query.substring(0,              splitIndex);
			elemValue = query.substring(splitIndex + 1, query.length);

			var elem = document.getElementById(elemId + "Select");
			if(elem != null) {
				elem.value = elemValue;
			}
		}
	}

	aElem.href = "#";

	var submitButton = document.getElementById(buttonId);
	if(submitButton != null) {
		submitButton.click();
	} else {
		return false;
	}
}

//-----------------------------------------------------------------------------
// Teedaのグリッドのセルを変更したことを保持する。 
//
// cell : セル（divタグであることを想定）
/*
function gridCellOnChange(cell) {
    var EDIT_ADD    = 1;
    var EDIT_UPDATE = 2;
    
    // find tr tag
    var node = cell.parentNode;
    while (node != null || node != "undefined") {
        var currentTagName = node.tagName;
        if(currentTagName == "TR") {
            break;
        }
        node = node.parentNode;
    }
    
    if (node == null || node == "undefined") {
        return;
    }
    
    // change editStatus to update
    // if editStatus is add, not change
    var inputNodeArray = node.getElementsByTagName("input");
    var inputNode;
    for (var i = 0; inputNode = inputNodeArray[i]; i++) {
        if(inputNode.id == "editStatus") {
            var status = inputNode.value;
            if (status != EDIT_ADD) {
                inputNode.value = EDIT_UPDATE;
            }
        }
    }
}
*/

//-----------------------------------------------------------------------------
// foreachで作成したグリッド用に、スクロール連動を行う。
// IDの命名規則によって、縦横のスクロールが連動する。
// 
// param layerId : スクロール操作を行うレイヤーのID
//-----------------------------------------------------------------------------
function scroll(layerId) {
    var rightHeaderId = layerId.replace("Body", "Header");
    var rightBodyId   = layerId;
    var leftBodyId    = layerId.replace("Right", "Left");
    
    var rightHeaderLayer = document.getElementById(rightHeaderId);
    var rightBodyLayer   = document.getElementById(rightBodyId);
    var leftBodyLayer    = document.getElementById(leftBodyId);
    
    rightHeaderLayer.scrollLeft = rightBodyLayer.scrollLeft;
    leftBodyLayer.scrollTop     = rightHeaderLayer.scrollTop;
}


//-----------------------------------------------------------------------------
// foreachで作成したグリッド用に、フォーカスイン／フォーカスアウト時にスタイルシートを切り替える。
//-----------------------------------------------------------------------------
/*
var cssChange = {
    
    focus: function(e) {
        e.className = e.className.replace("Blur", "Focus");
    },
    
    blur: function(e) {
        e.className = e.className.replace("Focus", "Blur");
    }
}
*/

//-----------------------------------------------------------------------------
// グリッド用に、変更状態を管理する。
// グリッド中のinputフィールドが更新された際に呼び出される。
// 通常のグリッド、およびForeachで生成したグリッドの両方に対応する。
// 
// target tag    : table(for gird)
// specify class : gridTable
//   ex) <table class="gridTable">
// specify class : eGridTable
//   ex) <table class="eGridTable">
//
// 関連タグ
//   <input type="hidden" id="editStatus" />
//     : 行（もしくは列）単位の変更状態を保持するプロパティ
//   <input type="hidden" id="xxxEditStatus" />
//     : セル単位の変更状態を保持するプロパティ（変更対象のプロパティ毎に用意する）
//-----------------------------------------------------------------------------
var fdGrid = {
    
    GRID_CSS_CLASS          : "gridTable",
    FOREACHE_GRID_CSS_CLASS : "eGridTable",
    EDIT_STATUS_ID          : "editStatus",
    EDIT_STATUS_ID_SUFFIX   : "EditStatus",
    EDIT_ADD                : 1,
    EDIT_UPDATE             : 2,
    
    initGrid: function() {
        
        if (!document.getElementsByTagName) {
            return;
        }
        
        var grids = getElementsByClassName(fdGrid.GRID_CSS_CLASS);
        var grid; 
        for (var i = 0; grid = grids[i]; i++) {
            var inputElems = grid.getElementsByTagName("input");
            var inputElem;
            for (var j = 0; inputElem = inputElems[j]; j++) {
                if(inputElem.type == "text") {         
                    inputElem.onchange = fdGrid.onChangeGrid;
                }
            }
        }
    },
    
    onChangeGrid: function(e) {
        fdGrid.onChange(this, "tr");
    },
    
    initForeachGrid: function() {
        
        if (!document.getElementsByTagName) {
            return;
        }
        
        var grids = getElementsByClassName(fdGrid.FOREACHE_GRID_CSS_CLASS);
        var grid; 
        for (var i = 0; grid = grids[i]; i++) {
            var inputElems = grid.getElementsByTagName("input");
            var inputElem;
            for (var j = 0; inputElem = inputElems[j]; j++) {
                if(inputElem.type == "text") {         
                    inputElem.onchange = fdGrid.onChangeForeachGrid;
                }
            }
        }
    },
    
    onChangeForeachGrid: function(e) {
        fdGrid.onChange(this, "td");
    },
    
    onChange: function(elem, parentTagName) {    
        // change editStatus to update
        // if editStatus is add, not change
        var inputNodeArray = findNodesInParent(elem, parentTagName, "input");
        var inputNode;
        for (var i = 0; inputNode = inputNodeArray[i]; i++) {
            if( (inputNode.id == fdGrid.EDIT_STATUS_ID)
             || (inputNode.id == elem.id + fdGrid.EDIT_STATUS_ID_SUFFIX) ) {
                var status = inputNode.value;
                if (status != fdGrid.EDIT_ADD) {
                    inputNode.value = fdGrid.EDIT_UPDATE;
                }
            }
        }
    }
    
}

addEvent(window, "load", fdGrid.initGrid);
addEvent(window, "load", fdGrid.initForeachGrid);


//-----------------------------------------------------------------------------
// Utility function.
// 親ノード中に含まれる指定された要素の配列を返す。
// 親ノードおよび検索対象のノードは、タグ名で判断される。
// 
// param childNode     : 検索を行う子ノード
// param parentTagName : 検索のルートとなる親ノードのタグ名
// param targetTagName : 取得したいタグ名
// return 要素の配列
//-----------------------------------------------------------------------------
function findNodesInParent(childNode, parentTagName, targetTagName) {
    
    parentTagName = parentTagName.toUpperCase();
    targetTagName = targetTagName.toUpperCase();
    
        var nodeArray;
        var node = childNode.parentNode;
        while (node != null || node != "undefined") {
            var currentTagName = node.tagName;
            if(currentTagName == parentTagName) {
                break;
            }
            node = node.parentNode;
        }
        
        if (node == null || node == "undefined") {
            return nodeArray;
        }
        
        var nodeArray = node.getElementsByTagName(targetTagName);
        
        return nodeArray;
}

