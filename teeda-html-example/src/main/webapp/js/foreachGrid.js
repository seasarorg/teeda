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
var cssChange = {
    
    focus: function(e) {
        e.className = e.className.replace("Blur", "Focus");
    },
    
    blur: function(e) {
        e.className = e.className.replace("Focus", "Blur");
    }
}


//-----------------------------------------------------------------------------
// foreachで作成したグリッド用に、変更状態を管理する。
// グリッド中のinputフィールドが更新された際に呼び出される。
// 
// target tag    : table(for gird)
// specify class : eGridTable
//   ex) <table class="eGridTable">
//
// 関連タグ
//   <input type="hidden" id="xxxEditStatus" /> : 変更状態を保持するプロパティ
//                                                （変更対象のプロパティ毎に用意する）
//-----------------------------------------------------------------------------
var fdGrid = {
    
    initForeachGrid: function() {
        
        if (!document.getElementsByTagName) {
            return;
        }
        
        var grids = getElementsByClassName("eGridTable");
        var grid; 
        for (var i = 0; grid = grids[i]; i++) {
            var inputElems = grid.getElementsByTagName("input");
            var inputElem;
            for (var j = 0; inputElem = inputElems[j]; j++) {
                if(inputElem.type == "text") {         
                    inputElem.onchange = fdGrid.onChange;
                }
            }
        }
    },
    
    onChange: function(e) {
        var EDIT_ADD    = 1;
        var EDIT_UPDATE = 2;
    
        // change editStatus to update
        // if editStatus is add, not change
        var inputNodeArray = findNodesInParent(this, "td", "input");
        var inputNode;
        for (var i = 0; inputNode = inputNodeArray[i]; i++) {
            if(inputNode.id == this.id + "EditStatus") {
                var status = inputNode.value;
                if (status != EDIT_ADD) {
                    inputNode.value = EDIT_UPDATE;
                }
            }
        }
    }
    
}

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

