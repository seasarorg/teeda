var	jscalendarFixedX = -1			// x position (-1 if to appear below control)
var	jscalendarFixedY = -1			// y position (-1 if to appear below control)
var jscalendarStartAt = 1			// 0 - sunday ; 1 - monday
var jscalendarShowToday = 1		// 0 - don't show; 1 - show
var jscalendarImgDir = "not available"			// directory for images ... e.g. var jscalendarImgDir="/img/"
var jscalendarThemePrefix = "jscalendar"

var jscalendarGotoString = "Go To Current Month"
var jscalendarTodayString = "Today is"
var jscalendarScrollLeftMessage = "Click to scroll to previous month. Hold mouse button to scroll automatically."
var jscalendarScrollRightMessage = "Click to scroll to next month. Hold mouse button to scroll automatically."
var jscalendarSelectMonthMessage = "Click to select a month."
var jscalendarSelectYearMessage = "Click to select a year."
var jscalendarSelectDateMessage = "Select [date] as date." // do not replace [date], it will be replaced by date.

var	jscalendarMonthName = new Array("January","February","March","April","May","June","July","August","September","October","November","December");
var	jscalendarMonthName2 = new Array("JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC");
var	jscalendarDayName = jscalendarStartAt==0 ? new Array("Sun","Mon","Tue","Wed","Thu","Fri","Sat") : new Array("Mon","Tue","Wed","Thu","Fri","Sat","Sun");
var jscalendarDateFormat = "yyyy/mm/dd"

function jscalendarSetImageDirectory(dir){
    jscalendarImgDir = dir;
}