//-----------------------------------------------------------------------------
// グリッドのソートを行う。CSSの指定により、ソート対象を自動で判別する。
//  
// target tag    : th
// specify class : sorttable
//   ex) <th class="sorttable">
// 
//   ※ Teedaが実際に出力するタグは以下のようになっているため、
//      指定時はthであっても、処理対象はdivとなる。
//
// 関連タグ
//   <input type="submit" id="doSort" />    : ソート時のサブミットボタン
//   <input type="hidden" id="sortId" />    : 前回ソートを行った列のID
//   <input type="hidden" id="sortOrder" /> : 前回ソートオーダ
//-----------------------------------------------------------------------------
var fdTableSort = {
    
    SORT_CSS          : "sorttable",
    SORT_REVERSE_CSS  : "reverseSort",
    SORT_TAGID_ANCHOR : "sorttableAnchor",
    SORT_TAGID_SPAN   : "sorttableSpan",
    SORT_TAGID_ID     : "sortId",
    SORT_TAGID_ORDER  : "sortOrder",
    DESC_MARK         : " \u25bd",
    DESC_VALUE        : "desc",
    ASC_MARK          : " \u25b3",
    ASC_VALUE         : "asc",
    SUMIT_ID          : "doSort",

    init: function() {
      
        if (!document.getElementsByTagName) {
            return;
        }
        
        var sortId    = document.getElementById(fdTableSort.SORT_TAGID_ID).value;
        var sortOrder = document.getElementById(fdTableSort.SORT_TAGID_ORDER).value;

        
        var headers = document.getElementsByTagName("th");
        var sortHeader;
        for (var i = 0; sortHeader = headers[i]; i++) {
            
            // th/div
            var divTag = sortHeader.getElementsByTagName("div")[0];
            if ( divTag != null
              && divTag != "undefined"
              && divTag.className.match(fdTableSort.SORT_CSS)) {
            
                fdTableSort.setupSort(divTag);
                sortHeader.onclick = fdTableSort.doSort;
                
                if(sortId == getPrimitiveId(sortHeader.id)) {
                    fdTableSort.initSort(sortHeader, sortOrder);
                }
            }
        }
    },
    
    setupSort: function(sortTag) {
        // th/div/nobr
        var sortTag = sortTag.getElementsByTagName("nobr")[0];

        // Insert ancher to text.
        var anchor = sortTag.appendChild(document.createElement("a"));
        anchor.id   = fdTableSort.SORT_TAGID_ANCHOR;
        anchor.href = "#";

        var orgChild = sortTag.childNodes;
        while(orgChild.length > 1) {
            // If append child, sortTag's child is removed.
            anchor.appendChild(orgChild[0]);
            
            if(orgChild[0].id == anchor.id) {
                break;
            }
        }

        // Insert span for sort.
        var span = sortTag.appendChild(document.createElement("span"));
        span.id      = fdTableSort.SORT_TAGID_SPAN;
        span.onclick = "return false";
    },
    
    initSort: function(sortHeader, sortOrder) {
        
        if(sortOrder == fdTableSort.ASC_VALUE) {
            sortHeader.className =  sortHeader.className + " " + fdTableSort.SORT_REVERSE_CSS;
        }
        
        fdTableSort.insertSortView(sortHeader, false);
    },

    doSort: function(e) {

        var curr = this;
        var pos = 0;

        while (curr.previousSibling) {
            if (curr.previousSibling.nodeType != 3) {
                pos++;
            }
            curr = curr.previousSibling;
        }

        // Remove reverse class
        var thCollection = curr.parentNode.getElementsByTagName("th");
        var th;
        for (var i = 0; th = thCollection[i]; i++) {
            if (i != pos) {
                th.className = th.className.replace(fdTableSort.SORT_REVERSE_CSS, "");
                var span = th.getElementsByTagName("span");
                if (span.length > 0) {
                    var span = th.getElementsByTagName("span")[span.length - 1];
                    if (span.firstChild && span.id == fdTableSort.SORT_TAGID_SPAN) {
                        span.removeChild(span.firstChild);
                    }
                    span.appendChild(document.createTextNode(""));
                }
            }
        }

        fdTableSort.insertSortView(this, true);
        
        // submit
        var button = document.getElementById(fdTableSort.SUMIT_ID);
        if(button != null && button != "undefined") {
            button.click();
        } else {
            document.forms[0].submit();
        }
    },
    
    insertSortView: function(sortHeader, isSave) {
        
        var thClassName = sortHeader.className;
        
        var arrow;
        var order;
        if (thClassName.match(fdTableSort.SORT_REVERSE_CSS)) {
            sortHeader.className = thClassName.replace(fdTableSort.SORT_REVERSE_CSS, "");
            arrow = fdTableSort.ASC_MARK;
            order = fdTableSort.ASC_VALUE;
            
        } else {
            sortHeader.className = thClassName + " " + fdTableSort.SORT_REVERSE_CSS;
            arrow = fdTableSort.DESC_MARK;
            order = fdTableSort.DESC_VALUE;
        }

        var span = sortHeader.getElementsByTagName("span");
        if (span.length > 0) {
            var span = sortHeader.getElementsByTagName("span")[span.length - 1];
            if (span.firstChild && span.id == fdTableSort.SORT_TAGID_SPAN) {
                span.removeChild(span.firstChild);
            }
            span.appendChild(document.createTextNode(arrow));
        }
        
        var sortId    = getPrimitiveId(sortHeader.id);
		var sortOrder = order;
		
        if(isSave) {
            fdTableSort.saveStatus(sortId, sortOrder);
        }
    },
    
    saveStatus: function(id, order) {
        var sortId    = document.getElementById(fdTableSort.SORT_TAGID_ID);
        if(sortId != null && sortId != "undefined") {
            sortId.value = id;
        }
        
        var sortOrder = document.getElementById(fdTableSort.SORT_TAGID_ORDER);
        if(sortOrder != null && sortOrder != "undefined") {
            sortOrder.value = order;
        }
    }

}

addEvent(window, "load", fdTableSort.init);


//-----------------------------------------------------------------------------
// Utility function.
// idに"-"（ハイフン）が含まれていれば、それ以降を削除した値を返す（Teeda用）。 
// 
// param id : 元のID
// return ハイフン以降を削除したID
//-----------------------------------------------------------------------------
function getPrimitiveId(id) {
    var primitiveId;
    var splitIndex = id.indexOf("-");
    if (splitIndex > 0) {
        primitiveId = id.substring(0, splitIndex);
    } else {
        primitiveId = id;
    }
    
    return primitiveId
}


