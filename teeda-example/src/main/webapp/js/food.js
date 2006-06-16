function getSelectFood() {
    var f = document.frm;
    for (var i = 0; i < f.food.length; i++) {
        var food = f.food[i];
        if (food.checked) {
            return food.value;
        }
    }
}
function createOptionsFromOrg(data, opt) {
    clearOptions(opt);
    if (!data) return;
    var rows = data.split("\n");
    for (var i = 0; i < rows.length - 1; i++) {
        var tmp = rows[i].split("\t");
        var value = tmp[0];
        var text = tmp[1];
        opt.options[i] = new Option(text, value);
    }
}

function createOptionsFromJSON(data, opt) {
    clearOptions(opt);
    if (!data) return;
    var json = eval('(' + data + ')');
    createOptionsFromJson(opt, json);
}

function createOptionsFromXml(data, opt) {
    clearOptions(opt);
    if (!data) return;
    var doc = data.documentElement;
    var itemTag = data.documentElement.getElementsByTagName("item");
    
    for (var i = 0; i < itemTag.length; i++) {
    	var item = itemTag.item(i);
    	var menuName = item.childNodes.item(0).childNodes.item(0).data;
    	var price = item.childNodes.item(1).childNodes.item(0).data;
    	opt.options[i] = new Option(menuName, price);
    }
}

function clearOptions(opt) {
    var listcnt = opt.length;
    for (var i = listcnt - 1; i >= 0; i--) {
        opt.options[i] = null;
    }
}

function createOptionsFromJson(opt, json) {
    var dataCnt = json.detail.length;
	for (var i = 0; i < json.detail.length; i++) {
	    var detail = json.detail[i];
	    opt.options[i] = new Option(detail.menuName, detail.price);
	}
}


// parse JSON to xml
function XmlToJson(responseXml) {
    var parser = new ParseXML();
    var json = parser.parse(responseXml.documentElement);
    return json;
}

// xml to json parser ----------------------------------------------------------
ParseXML = function () {
    return this;
};

ParseXML.prototype.parse = function(root) {
    if (!root) {
        return;
    }
    var ret = this.parseElement(root);
    if (this.usearray == true) {                  // always into array
        ret = [ ret ];
    } else if (this.usearray == false) {          // always into scalar
    } else if (this.usearray == null) {           // automatic
    } else if (this.usearray[root.nodeName]) {    // specified tag
        ret = [ret];
    }
    var json = {};
    json[root.nodeName] = ret;                    // root nodeName
    return json;
};

ParseXML.prototype.parseElement = function(elem) {
    //  COMMENT_NODE
    if (elem.nodeType == 7) {
        return;
    }

    //  TEXT_NODE CDATA_SECTION_NODE
    if (elem.nodeType == 3 || elem.nodeType == 4) {
        var bool = elem.nodeValue.match( /[^\x00-\x20]/ ); // for Safari
        if (bool == null) {
            return;     // ignore white spaces
        }
        return elem.nodeValue;
    }
    var retval;
    var cnt = {};
    //  parse attributes
    if (elem.attributes && elem.attributes.length) {
        retval = {};
        for (var i = 0; i < elem.attributes.length; i++) {
            var key = elem.attributes[i].nodeName;
            //if (typeof(key) != "string") continue;
            if (!isTypeOfString(key)) continue;
            var val = elem.attributes[i].nodeValue;
            if (!val) continue;
            //if (typeof(cnt[key]) == "undefined") cnt[key] = 0;
            if (isTypeOfUndef(cnt[key])) cnt[key] = 0;
            cnt[key] ++;
            this.addNode(retval, key, cnt[key], val);
        }
    }

    //  parse child nodes (recursive)
    if (elem.childNodes && elem.childNodes.length) {
        var textonly = true;
        if (retval) textonly = false;        // some attributes exists
        for (var i = 0; i < elem.childNodes.length && textonly; i++) {
            var ntype = elem.childNodes[i].nodeType;
            if (ntype == 3 || ntype == 4) continue;
            textonly = false;
        }
        if (textonly) {
            if (!retval) retval = "";
            for (var i = 0; i < elem.childNodes.length; i++) {
                retval += elem.childNodes[i].nodeValue;
            }
        } else {
            if (!retval) retval = {};
            for (var i = 0; i < elem.childNodes.length; i++) {
                var key = elem.childNodes[i].nodeName;
                //if (typeof(key) != "string") continue;
                if (!isTypeOfString(key)) continue;
                var val = this.parseElement( elem.childNodes[i] );
                if (!val) continue;
                //if (typeof(cnt[key]) == "undefined") cnt[key] = 0;
                if (isTypeOfUndef(cnt[key])) cnt[key] = 0;
                cnt[key] ++;
                this.addNode( retval, key, cnt[key], val );
            }
        }
    }
    return retval;
};

ParseXML.prototype.addNode = function(hash, key, cnts, val) {
    if (this.usearray == true) {              // into array
        if (cnts == 1) hash[key] = [];
        hash[key][hash[key].length] = val;    // push
    } else if (this.usearray == false) {      // into scalar
        if (cnts == 1) hash[key] = val;       // only 1st sibling
    } else if (this.usearray == null) {
        if (cnts == 1) {                      // 1st sibling
            hash[key] = val;
        } else if (cnts == 2) {               // 2nd sibling
            hash[key] = [ hash[key], val ];
        } else {                              // 3rd sibling and more
            hash[key][hash[key].length] = val;
        }
    } else if (this.usearray[key]) {
        if (cnts == 1) hash[key] = [];
        hash[key][hash[key].length] = val;    // push
    } else {
        if (cnts == 1) hash[key] = val;       // only 1st sibling
    }
};

function isTypeOfString(str) {
    return (typeof(str) == "string");
}

function isTypeOfUndef(obj) {
    return (typeof(obj) == "undefined");
}
