function calc(arg1, arg2){
    return arg1 + arg2;
}

function catString(arg1, arg2){
    return "Hello " + arg1 + " " + arg2;
}

function isNull(arg1) {
    return (arg1 == null);
}

function splitTest(arg1){
    return arg1.split(",");
}

if (typeof(Kumu) == 'undefined') {
    Kumu = {};
}
if (typeof(Kumu.MockAjax) == 'undefined') {
    Kumu.MockAjax = {};
};
Kumu.MockAjax = {
    AJAX_COMPONENT_NAME : "AjaxComponent"
};

var mock = Kumu.MockAjax;
var mockName = mock.AJAX_COMPONENT_NAME;