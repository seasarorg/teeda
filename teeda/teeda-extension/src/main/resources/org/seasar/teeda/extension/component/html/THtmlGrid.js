function editOn(div) {
	var span = getFirstChildByTagName(div, "span");
	var input = getFirstChildByTagName(div, "input");
	input.value = span.innerHTML;
	span.style.display = "none";
	input.style.display = "inline";
	input.focus();
}
function editOff(input) {
	var span = getFirstChildByTagName(input.parentNode, "span");
	span.innerHTML = input.value;
	input.style.display = "none";
	span.style.display = "inline";
}
function getFirstChildByTagName(fromNode, elemName) {
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
		var ret = getFirstChildByTagName(child, elemName);
		if (ret != null) {
			return ret;
		}
	}
}
