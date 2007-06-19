//-----------------------------------------------------------------------------
// Change values which it is related to select tag.
// 
// target tag    : select
// specify class : selectExtension
//   ex) <select class="selectExtension">
//  
// Naming rule of id
//   select          : xxxItems
//   foreach         : xxxRelItems-xxxName
//   foreach element : name-xxxName
//   label span : xxxLabel , hidden : xxxLabel-hidden
//   name  span : xxxName  , hidden : xxxName-hidden
//-----------------------------------------------------------------------------
var fdSelectName = {
    
    init: function() {
        
        if (!document.getElementsByTagName) {
            return;
        }

        var selectors = document.getElementsByTagName("select");
        var selector;
        for (var i = 0; selector = selectors[i]; i++) {
            if (selector.className.match("selectExtension")) {
                // dispatch the defined event
                addEvent(selector, "change", selector.onchange);
                
				fdSelectName.change(selector);
                selector.onchange = fdSelectName.onchangeInvoke;
            }
        }
    },

    onchangeInvoke: function(e) {
		fdSelectName.change(this);
    },

	change: function(selectTag) {
        var selectorIndex  = selectTag.selectedIndex;
        var selectorId     = selectTag.id;
		var selectorProp   = selectorId.substring(0, selectorId.indexOf("Items"));
		
		// change label value
		var selectLabelValue = selectTag.options[selectorIndex].innerHTML;
		
		fdSelectName.changeValue(selectorProp + "Label", selectLabelValue);
        
        // change name value
		var selectEachId = "name-" + selectorProp + "Name";
        var selectName   = document.forms[0].id
                         + ":" + selectorProp + "RelItems-" + selectorProp + "Name"
                         + ":" + (selectorIndex -1)
                         + ":" + selectEachId
        var selectNameElem  = document.getElementsByName(selectName);
		if (selectNameElem != "undefined" && selectNameElem.length > 0) {
	        var selectNameValue = selectNameElem[0].value;
			fdSelectName.changeValue(selectorProp + "Name", selectNameValue);
		} else {
			fdSelectName.changeValue(selectorProp + "Name", "");
		}
	},
	
	changeValue: function(id, value) {
		// change span value
		var spanElem = document.getElementById(id);
		if(spanElem != null) {
			spanElem.innerHTML = value;
		}
		
		// change hidden value
		var hiddenElem = document.getElementById(id + "-hidden");
		if(hiddenElem != null) {
			hiddenElem.value = value;
		}
	}
}

addEvent(window, "load", fdSelectName.init);
