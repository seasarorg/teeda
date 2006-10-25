// - - - - - - - - - - JavaScript Document - - - - - - - - - - - - 
//
// Title : Js file	
// Author : Cody Lindley
// URL : 
//

//add event function
function addEvent( obj, type, fn ) {
	if (obj.addEventListener) {
		obj.addEventListener( type, fn, false );
		EventCache.add(obj, type, fn);
	}
	else if (obj.attachEvent) {
		obj["e"+type+fn] = fn;
		obj[type+fn] = function() { obj["e"+type+fn]( window.event ); }
		obj.attachEvent( "on"+type, obj[type+fn] );
		EventCache.add(obj, type, fn);
	}
	else {
		obj["on"+type] = obj["e"+type+fn];
	}
}

//remove event function
function removeEvent( obj, type, fn )
{
EventCache.remove(obj, type, fn);
}

//flush all events on page unload
var EventCache = function()
{
var listEvents = [];
return {
listEvents : listEvents,
add : function(node, sEventName, fHandler)
{
listEvents.push(arguments);
},
remove : function(node, sEventName, fHandler)
{
var i, item;
for(i = listEvents.length - 1; i >= 0; i = i - 1) {
if(node == listEvents[i][0] && sEventName == listEvents[i][1] && fHandler == listEvents[i][2]) {
item = listEvents[i];
if(item[0].removeEventListener) {
item[0].removeEventListener(item[1], item[2], item[3]);
}
if(item[1].substring(0, 2) != "on") {
item[1] = "on" + item[1];
}
if(item[0].detachEvent) {
item[0].detachEvent(item[1], item[0][sEventName+fHandler]);

}
item[0][item[1]] = null;
}
}
},
flush : function()
{
var i, item, eventtype;
for(i = listEvents.length - 1; i >= 0; i = i - 1) {
item = listEvents[i];
if(item[0].removeEventListener) {
item[0].removeEventListener(item[1], item[2], item[3]);
}
eventtype = item[1];
if(item[1].substring(0, 2) != "on") {
item[1] = 'on' + item[1];
}
if(item[0].detachEvent) {
item[0].detachEvent(item[1], item[2]);
item[0].detachEvent(item[1], item[0][eventtype+item[2]]);
}
item[0][item[1]] = null;
}
}
}
}();

//stripe a table with default css and add mouseover effect. 
var stripe = function(tablesName) {
		var tables = getElementsByClassName(tablesName);	

		for(var x=0;x!=tables.length;x++){
			var table = tables[x];
			if (! table) { return; }
			
			var tbodies = table.getElementsByTagName("tbody");
			
			for (var h = 0; h < tbodies.length; h++) {
				var even = false;
				var trs = tbodies[h].getElementsByTagName("tr");
				
				for (var i = 0; i < trs.length; i++) {
					trs[i].onmouseover=function(){
						this.className += " ruled"; return false
					}
					trs[i].onmouseout=function(){
						this.className = this.className.replace("ruled", ""); return false
					}
					
					if(even)
						trs[i].className += " even";
					
					even = !even;
				}
			}
		}
	}

//event added using the addEvent() function above
addEvent(window, 'load', function(){stripe('defaulttablestyle');});
addEvent(window, 'load', function(){stripe('scrolltablestyle');});


//find html element by class function
function getElementsByClassName(classname){
        var rl = new Array();
        var re = new RegExp('(^| )'+classname+'( |$)');
        var ael = document.getElementsByTagName('*');
        var op = (navigator.userAgent.indexOf("Opera") != -1) ? true : false;
        if (document.all && !op) ael = document.all;
        for(i=0, j=0 ; i<ael.length ; i++) {
                if(re.test(ael[i].className)) {
                        rl[j]=ael[i];
                        j++;
                }
        }
        return rl;
}



function cleanUpJs(){
	EventCache.flush;
}

addEvent(window,'unload',cleanUpJs);

