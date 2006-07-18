function editOn(span) {
	var input = span.nextSibling;
	input.value = span.innerHTML;
	span.style.display = "none";
	input.style.display = "inline";
	input.focus();
}
function editOff(input) {
	var span = input.previousSibling;
	span.innerHTML = input.value;
	input.style.display = "none";
	span.style.display = "inline";
}
