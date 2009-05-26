var	jscalendarCrossFrameobj, jscalendarObj, jscalendarCrossobj, jscalendarCrossMonthObj, jscalendarCrossYearObj,
    jscalendarMonthSelected, jscalendarYearSelected, jscalendarDateSelected,
    jscalendarOmonthSelected, jscalendarOyearSelected, jscalendarOdateSelected,
    jscalendarMonthConstructed, jscalendarYearConstructed, jscalendarIntervalID1, jscalendarIntervalID2,
    jscalendarTimeoutID1, jscalendarTimeoutID2, jscalendarCtlToPlaceValue, jscalendarCtlNow, jscalendarDateFormat, jscalendarNStartingYear

var	jscalendarBPageLoaded=false
var	jscalendarIe=document.all
var	jscalendarIe6=navigator.userAgent.indexOf("MSIE 5.5") >= 0 || navigator.userAgent.indexOf("MSIE 6") >= 0

var	jscalendarToday =	new	Date()
var	jscalendarDateNow	 = jscalendarToday.getDate()
var	jscalendarMonthNow = jscalendarToday.getMonth()
var	jscalendarYearNow	 = jscalendarToday.getYear()

var jscalendarBShow = false;

if (jscalendarIe6) {
	document.write ("<iframe src='javascript:false;' scrolling='no' frameborder='0' id='calendarFrame' style='z-index:10; position:absolute; display:none;'></iframe>");
}
document.write ("<div onclick='jscalendarBShow=true' id='calendar'	class='"+jscalendarThemePrefix+"-div-style'><table	width='220' class='"+jscalendarThemePrefix+"-table-style'><tr class='"+jscalendarThemePrefix+"-title-background-style'><td><table width='218'><tr><td class='"+jscalendarThemePrefix+"-title-style'><span id='caption'></span></td><td align=right><a href='javascript:jscalendarHideCalendar()'><span id='jscalendarCloseButton'></span></a></td></tr></table></td></tr><tr><td class='"+jscalendarThemePrefix+"-body-style'><span id='popupcalendar_content'></span></td></tr>")

if (jscalendarShowToday==1)
	document.write ("<tr class='"+jscalendarThemePrefix+"-today-style'><td class='"+jscalendarThemePrefix+"-today-lbl-style'><span id='lblToday'></span></td></tr>")

document.write ("</table></div><div id='selectMonth' class='"+jscalendarThemePrefix+"-div-style'></div><div id='selectYear' class='"+jscalendarThemePrefix+"-div-style'></div>");

function jscalendarSwapImage(srcImg, destImg){
	document.getElementById(srcImg).setAttribute("src",jscalendarImgDir + destImg);
}

function jscalendarInit(){
	if (jscalendarYearNow < 2000) {
		jscalendarYearNow += 1900;
	}

	if (jscalendarIe6) {
		jscalendarCrossFrameobj=document.getElementById("calendarFrame").style;
	}
	jscalendarObj=document.getElementById("calendar");
	jscalendarCrossobj=document.getElementById("calendar").style;
	jscalendarCrossMonthObj=document.getElementById("selectMonth").style;
	jscalendarCrossYearObj=document.getElementById("selectYear").style;
	jscalendarHideCalendar();
	jscalendarMonthConstructed=false;
	jscalendarYearConstructed=false;

	if (jscalendarShowToday==1) {
		document.getElementById("lblToday").innerHTML =	jscalendarTodayString + " <a id='popupcalendar_today' onmousemove='window.status=\""+jscalendarGotoString+"\"' onmouseout='window.status=\"\"' title='"+jscalendarGotoString+"' class='"+jscalendarThemePrefix+"-today-style' href='javascript:jscalendarMonthSelected=jscalendarMonthNow;jscalendarYearSelected=jscalendarYearNow;jscalendarConstructCalendar();'>"+ jscalendarConstructDate(jscalendarDateNow, jscalendarMonthNow, jscalendarYearNow) + "</a>";
	}
	var sHTML1 ="<span id='spanLeft'  class='"+jscalendarThemePrefix+"-title-control-normal-style' onmouseover='jscalendarSwapImage(\"changeLeft\",\"left2.gif\");  this.className=\""+jscalendarThemePrefix+"-title-control-select-style\"; window.status=\""+jscalendarScrollLeftMessage+"\"' onclick='javascript:jscalendarDecMonth()' onmouseout='clearInterval(jscalendarIntervalID1);jscalendarSwapImage(\"changeLeft\",\"left1.gif\"); this.className=\""+jscalendarThemePrefix+"-title-control-normal-style\"; window.status=\"\"' onmousedown='clearTimeout(jscalendarTimeoutID1);jscalendarTimeoutID1=setTimeout(\"jscalendarStartDecMonth()\",500)'	onmouseup='clearTimeout(jscalendarTimeoutID1);clearInterval(jscalendarIntervalID1)'>&nbsp<IMG id='changeLeft' SRC='"+jscalendarImgDir+"left1.gif' width=10 height=11 BORDER=0>&nbsp</span>&#160;"
	sHTML1+="<span id='spanRight' class='"+jscalendarThemePrefix+"-title-control-normal-style' onmouseover='jscalendarSwapImage(\"changeRight\",\"right2.gif\");this.className=\""+jscalendarThemePrefix+"-title-control-select-style\"; window.status=\""+jscalendarScrollRightMessage+"\"' onmouseout='clearInterval(jscalendarIntervalID1);jscalendarSwapImage(\"changeRight\",\"right1.gif\"); this.className=\""+jscalendarThemePrefix+"-title-control-normal-style\"; window.status=\"\"' onclick='jscalendarIncMonth()' onmousedown='clearTimeout(jscalendarTimeoutID1);jscalendarTimeoutID1=setTimeout(\"jscalendarStartIncMonth()\",500)'	onmouseup='clearTimeout(jscalendarTimeoutID1);clearInterval(jscalendarIntervalID1)'>&nbsp<IMG id='changeRight' SRC='"+jscalendarImgDir+"right1.gif'	width=10 height=11 BORDER=0>&nbsp</span>&nbsp"
	sHTML1+="<span id='spanMonth' class='"+jscalendarThemePrefix+"-title-control-normal-style' onmouseover='jscalendarSwapImage(\"changeMonth\",\"drop2.gif\"); this.className=\""+jscalendarThemePrefix+"-title-control-select-style\"; window.status=\""+jscalendarSelectMonthMessage+"\"' onmouseout='jscalendarSwapImage(\"changeMonth\",\"drop1.gif\"); this.className=\""+jscalendarThemePrefix+"-title-control-normal-style\"; window.status=\"\"' onclick='jscalendarPopUpMonth()'></span>&#160;"
	sHTML1+="<span id='spanYear'  class='"+jscalendarThemePrefix+"-title-control-normal-style' onmouseover='jscalendarSwapImage(\"changeYear\",\"drop2.gif\");  this.className=\""+jscalendarThemePrefix+"-title-control-select-style\"; window.status=\""+jscalendarSelectYearMessage+"\"'	onmouseout='jscalendarSwapImage(\"changeYear\",\"drop1.gif\"); this.className=\""+jscalendarThemePrefix+"-title-control-normal-style\"; window.status=\"\"'	onclick='jscalendarPopUpYear()'></span>&#160;"

	document.getElementById("caption").innerHTML = sHTML1;

	jscalendarBPageLoaded = true;
}

function jscalendarHideCalendar(){
	jscalendarCrossobj.visibility="hidden";
	jscalendarCrossMonthObj.visibility="hidden";
	jscalendarCrossYearObj.visibility="hidden";

	if (jscalendarIe6) {
		jscalendarCrossFrameobj.display="none";
	}
}

function jscalendarPadZero(num){
	return (num	< 10)? '0' + num : num ;
}

function jscalendarConstructDate(d,m,y){
	var sTmp = jscalendarDateFormat
	sTmp = sTmp.replace	("dd","<e>")
	sTmp = sTmp.replace	("d","<d>")
	sTmp = sTmp.replace	("<e>",jscalendarPadZero(d))
	sTmp = sTmp.replace	("<d>",d)
	sTmp = sTmp.replace	("mmmm","<p>")
	sTmp = sTmp.replace	("MMMM","<p>")
	sTmp = sTmp.replace	("mmm","<o>")
	sTmp = sTmp.replace	("MMM","<o>")
	sTmp = sTmp.replace	("mm","<n>")
	sTmp = sTmp.replace	("MM","<n>")
	sTmp = sTmp.replace	("m","<m>")
	sTmp = sTmp.replace	("M","<m>")
	sTmp = sTmp.replace	("<m>",m+1)
	sTmp = sTmp.replace	("<n>",jscalendarPadZero(m+1))
	sTmp = sTmp.replace	("<o>",jscalendarMonthName[m])
	sTmp = sTmp.replace	("<p>",jscalendarMonthName2[m])
	sTmp = sTmp.replace	("yyyy",y)
	return sTmp.replace ("yy",jscalendarPadZero(y%100))
}

function jscalendarCloseCalendar() {
    jscalendarHideCalendar();    
    jscalendarCtlToPlaceValue.value = jscalendarConstructDate(jscalendarDateSelected,jscalendarMonthSelected,jscalendarYearSelected)
    var onchange=jscalendarCtlToPlaceValue.getAttribute("onchange");
    if(onchange){
        eval(onchange);
    }
}

function jscalendarStartDecMonth(){
	jscalendarIntervalID1=setInterval("jscalendarDecMonth()",80);
}

function jscalendarStartIncMonth(){
	jscalendarIntervalID1=setInterval("jscalendarIncMonth()",80);
}

function jscalendarIncMonth(){
	jscalendarMonthSelected++;
	if (jscalendarMonthSelected>11) {
		jscalendarMonthSelected=0;
		jscalendarYearSelected++;
	}
	jscalendarConstructCalendar();
}

function jscalendarDecMonth () {
	jscalendarMonthSelected--
	if (jscalendarMonthSelected<0) {
		jscalendarMonthSelected=11
		jscalendarYearSelected--
	}
	jscalendarConstructCalendar()
}

function jscalendarConstructMonth(){
	jscalendarPopDownYear();
	if (!jscalendarMonthConstructed) {
		var sHTML =	"";
		for	(i=0; i<12;	i++) {
			var sName = jscalendarMonthName[i];
			if (i==jscalendarMonthSelected)
				sName =	"<b>" +	sName +	"</b>";
			sHTML += "<tr><td id='m" + i + "' onmouseover='this.className=\""+jscalendarThemePrefix+"-dropdown-select-style\"' onmouseout='this.className=\""+jscalendarThemePrefix+"-dropdown-normal-style\"' onclick='jscalendarMonthConstructed=false;jscalendarMonthSelected=" + i + ";jscalendarConstructCalendar();jscalendarPopDownMonth();event.cancelBubble=true'>&#160;" + sName + "&#160;</td></tr>";
		}

		document.getElementById("selectMonth").innerHTML = "<table width='70' class='"+jscalendarThemePrefix+"-dropdown-style'  cellspacing=0 onmouseover='clearTimeout(jscalendarTimeoutID1)'	onmouseout='clearTimeout(jscalendarTimeoutID1);jscalendarTimeoutID1=setTimeout(\"jscalendarPopDownMonth()\",100);event.cancelBubble=true'>" +	sHTML +	"</table>";

		jscalendarMonthConstructed=true;
	}
}

function jscalendarPopUpMonth() {
	jscalendarConstructMonth()
	jscalendarCrossMonthObj.visibility = "visible";
	jscalendarCrossMonthObj.left = parseInt(formatInt(jscalendarCrossobj.left),10) + 50 + "px";
	jscalendarCrossMonthObj.top =	parseInt( formatInt(jscalendarCrossobj.top),10) + 26 + "px";
}

function jscalendarPopDownMonth()	{
	jscalendarCrossMonthObj.visibility= "hidden"
}

/*** Year Pulldown ***/

function jscalendarIncYear() {
	for	(i=0; i<7; i++){
		newYear	= (i+jscalendarNStartingYear)+1
		if (newYear==jscalendarYearSelected)
		{ txtYear =	"&#160;<B>"	+ newYear +	"</B>&#160;" }
		else
		{ txtYear =	"&#160;" + newYear + "&#160;" }
		document.getElementById("y"+i).innerHTML = txtYear
	}
	jscalendarNStartingYear++;
	jscalendarBShow=true;
}

function jscalendarDecYear() {
	for	(i=0; i<7; i++){
		newYear	= (i+jscalendarNStartingYear)-1
		if (newYear==jscalendarYearSelected)
		{ txtYear =	"&#160;<B>"	+ newYear +	"</B>&#160;" }
		else
		{ txtYear =	"&#160;" + newYear + "&#160;" }
		document.getElementById("y"+i).innerHTML = txtYear
	}
	jscalendarNStartingYear--;
	jscalendarBShow=true;
}

function jscalendarSelectYear(nYear) {
	jscalendarYearSelected=parseInt( formatInt(nYear+jscalendarNStartingYear),10);
	jscalendarYearConstructed=false;
	jscalendarConstructCalendar();
	jscalendarPopDownYear();
}

function jscalendarConstructYear() {
	jscalendarPopDownMonth();
	var sHTML =	"";
	if (!jscalendarYearConstructed) {

		sHTML =	"<tr><td align='center'	onmouseover='this.className=\""+jscalendarThemePrefix+"-dropdown-select-style\"' onmouseout='clearInterval(jscalendarIntervalID1); this.className=\""+jscalendarThemePrefix+"-dropdown-normal-style\"' onmousedown='clearInterval(jscalendarIntervalID1);jscalendarIntervalID1=setInterval(\"jscalendarDecYear()\",30)' onmouseup='clearInterval(jscalendarIntervalID1)'>-</td></tr>";

		var j =	0;
		jscalendarNStartingYear = jscalendarYearSelected-3;
		for	(i=jscalendarYearSelected-3; i<=(jscalendarYearSelected+3); i++) {
			var sName =	i;
			if (i==jscalendarYearSelected)
				sName =	"<b>"+sName+"</b>";

			sHTML += "<tr><td id='y"+j+"' onmouseover='this.className=\""+jscalendarThemePrefix+"-dropdown-select-style\"' onmouseout='this.className=\""+jscalendarThemePrefix+"-dropdown-normal-style\"' onclick='jscalendarSelectYear("+j+");event.cancelBubble=true'>&#160;"+sName+"&#160;</td></tr>";
			j++;
		}

		sHTML += "<tr><td align='center' onmouseover='this.className=\""+jscalendarThemePrefix+"-dropdown-select-style\"' onmouseout='clearInterval(jscalendarIntervalID2); this.className=\""+jscalendarThemePrefix+"-dropdown-normal-style\"' onmousedown='clearInterval(jscalendarIntervalID2);jscalendarIntervalID2=setInterval(\"jscalendarIncYear()\",30)'	onmouseup='clearInterval(jscalendarIntervalID2)'>+</td></tr>";

		document.getElementById("selectYear").innerHTML	= "<table width='44' class='"+jscalendarThemePrefix+"-dropdown-style' onmouseover='clearTimeout(jscalendarTimeoutID2)' onmouseout='clearTimeout(jscalendarTimeoutID2);jscalendarTimeoutID2=setTimeout(\"jscalendarPopDownYear()\",100)' cellspacing='0'>"+sHTML+"</table>";

		jscalendarYearConstructed = true;
	}
}

function jscalendarPopDownYear() {
	clearInterval(jscalendarIntervalID1);
	clearTimeout(jscalendarTimeoutID1);
	clearInterval(jscalendarIntervalID2);
	clearTimeout(jscalendarTimeoutID2);
	jscalendarCrossYearObj.visibility= "hidden";
}

function jscalendarPopUpYear() {
	var	leftOffset;

	jscalendarConstructYear();
	jscalendarCrossYearObj.visibility = "visible";
	leftOffset = parseInt( formatInt(jscalendarCrossobj.left),10) + document.getElementById("spanYear").offsetLeft;
	if (jscalendarIe)
		leftOffset += 6;
	jscalendarCrossYearObj.left =	leftOffset + "px";
	jscalendarCrossYearObj.top = parseInt( formatInt(jscalendarCrossobj.top),10) +	26 + "px";
}

function jscalendarConstructCalendar () {
	var aNumDays = Array (31,0,31,30,31,30,31,31,30,31,30,31);

	var dateMessage;
	var	startDate =	new	Date (jscalendarYearSelected,jscalendarMonthSelected,1);
	var endDate;

	if (jscalendarMonthSelected==1){
		endDate	= new Date (jscalendarYearSelected,jscalendarMonthSelected+1,1);
		endDate	= new Date (endDate	- (24*60*60*1000));
		numDaysInMonth = endDate.getDate();
	}else{
		numDaysInMonth = aNumDays[jscalendarMonthSelected];
	}

	datePointer	= 0;
	dayPointer = startDate.getDay() - jscalendarStartAt;

	if (dayPointer<0)
		dayPointer = 6;

	var sHTML = "<table border=0 class='"+jscalendarThemePrefix+"-body-style'><tr>"

	for	(i=0; i<7; i++)
		sHTML += "<td width='27' align='right'><B>"+ jscalendarDayName[i]+"</B></td>";

	sHTML +="</tr><tr>";

	for	( var i=1; i<=dayPointer;i++ )
		sHTML += "<td>&#160;</td>";

	for	( datePointer=1; datePointer<=numDaysInMonth; datePointer++ ){
		dayPointer++;
		sHTML += "<td align=right>";

		var sStyle=jscalendarThemePrefix+"-normal-day-style"; //regular day

		if ((datePointer==jscalendarDateNow)&&(jscalendarMonthSelected==jscalendarMonthNow)&&(jscalendarYearSelected==jscalendarYearNow)) //today
		{ sStyle = jscalendarThemePrefix+"-current-day-style"; }
		else if	(dayPointer % 7 == (jscalendarStartAt * -1) +1) //end-of-the-week day
		{ sStyle = jscalendarThemePrefix+"-end-of-weekday-style"; }

		//selected day
		if ((datePointer==jscalendarOdateSelected) &&	(jscalendarMonthSelected==jscalendarOmonthSelected)	&& (jscalendarYearSelected==jscalendarOyearSelected))
		{ sStyle += " "+jscalendarThemePrefix+"-selected-day-style"; }

		sHint = ""

		var regexp= /\"/g
		sHint=sHint.replace(regexp,"&quot;");

		sSelectStyle = sStyle+" "+jscalendarThemePrefix+"-would-be-selected-day-style";
		sNormalStyle = sStyle;

		dateMessage = "onmousemove='window.status=\""+jscalendarSelectDateMessage.replace("[date]",jscalendarConstructDate(datePointer,jscalendarMonthSelected,jscalendarYearSelected))+"\"' onmouseout='this.className=\""+sNormalStyle+"\"; window.status=\"\"' "

		sHTML += "<a class='"+sStyle+"' "+dateMessage+" title=\"" + sHint + "\" href='javascript:jscalendarDateSelected="+datePointer+";jscalendarCloseCalendar();' onmouseover='this.className=\""+sSelectStyle+"\";' >&#160;" + datePointer + "&#160;</a>";

		if ((dayPointer+jscalendarStartAt) % 7 == jscalendarStartAt) {
			sHTML += "</tr><tr>";
		}
	}

	document.getElementById("popupcalendar_content").innerHTML = sHTML;
	document.getElementById("spanMonth").innerHTML = "&#160;" +	jscalendarMonthName[jscalendarMonthSelected] + "&#160;<IMG id='changeMonth' SRC='"+jscalendarImgDir+"drop1.gif' WIDTH='12' HEIGHT='10' BORDER=0>";
	document.getElementById("spanYear").innerHTML =	"&#160;" + jscalendarYearSelected	+ "&#160;<IMG id='changeYear' SRC='"+jscalendarImgDir+"drop1.gif' WIDTH='12' HEIGHT='10' BORDER=0>";
	document.getElementById("jscalendarCloseButton").innerHTML = "<img src='"+jscalendarImgDir+"close.gif' width='15' height='13' border='0' alt='Close the Calendar'>";
}

function jscalendarPopUpCalendar(ctl, ctl2, format){
	if (jscalendarBPageLoaded){
		if ( jscalendarCrossobj.visibility == "hidden" ) {
			jscalendarCtlToPlaceValue = ctl2;
			jscalendarDateFormat=format;

			var formatChar = " ";
			aFormat	= jscalendarDateFormat.split(formatChar)
			if (aFormat.length<3){
				formatChar = "/";
				aFormat	= jscalendarDateFormat.split(formatChar)
				if (aFormat.length<3){
					formatChar = ".";
					aFormat	= jscalendarDateFormat.split(formatChar)
					if (aFormat.length<3){
						formatChar = "-";
						aFormat	= jscalendarDateFormat.split(formatChar)
						if (aFormat.length<3){
							// invalid date	format
							formatChar="";
						}
					}
				}
			}

			var tokensChanged =	0;
			if ( formatChar	!= "" ){
				// use user's date
				aData =	ctl2.value.split(formatChar)

				for	(i=0;i<3;i++){
					if ((aFormat[i]=="d") || (aFormat[i]=="dd")){
						jscalendarDateSelected = parseInt( formatInt(aData[i]),10);
						tokensChanged++;
					}else if ((aFormat[i]=="m") || (aFormat[i]=="mm") || (aFormat[i]=="M") || (aFormat[i]=="MM")){
						jscalendarMonthSelected = parseInt( formatInt(aData[i]),10) - 1;
						tokensChanged++;
					}else if (aFormat[i]=="yyyy"){
						jscalendarYearSelected = parseInt( formatInt(aData[i]),10);
						tokensChanged++;
					}else if (aFormat[i]=="yy"){
					    newYear = parseInt( formatInt(aData[i]),10);

					    if(newYear>50)
						    jscalendarYearSelected = 1900+newYear;
						else
						    jscalendarYearSelected = 2000+newYear;

						tokensChanged++
					}else if (aFormat[i]=="mmm" || aFormat[i]=="MMM"){
						for	(j=0; j<12;	j++){
							if (aData[i]==jscalendarMonthName[j]){
								jscalendarMonthSelected=j;
								tokensChanged++;
							}
						}
					}else if (aFormat[i]=="mmmm" || aFormat[i]=="MMMM"){
						for	(j=0; j<12;	j++){
							if (aData[i]==jscalendarMonthName2[j]){
								jscalendarMonthSelected=j;
								tokensChanged++;
							}
						}
					}
				}
			}

			if ((tokensChanged!=3)||isNaN(jscalendarDateSelected)||isNaN(jscalendarMonthSelected)||isNaN(jscalendarYearSelected)){
				jscalendarDateSelected = jscalendarDateNow;
				jscalendarMonthSelected =	jscalendarMonthNow;
				jscalendarYearSelected = jscalendarYearNow;
			}

			jscalendarPopUpCalendar_Show(ctl);
		}else{
			jscalendarHideCalendar();
			if (jscalendarCtlNow!=ctl)
				jscalendarPopUpCalendar(ctl, ctl2, format);
		}
		jscalendarCtlNow = ctl;
	}
}

function jscalendarPopUpCalendar_Show(ctl){
	jscalendarOdateSelected = jscalendarDateSelected;
	jscalendarOmonthSelected = jscalendarMonthSelected;
	jscalendarOyearSelected = jscalendarYearSelected;

	var	leftpos = 0;
	var	toppos = 0;

	var aTag = ctl;
	do {
		aTag = aTag.offsetParent;
		leftpos	+= aTag.offsetLeft;
		toppos += aTag.offsetTop;
	} while(aTag.tagName!="BODY" && aTag.tagName!="HTML");

	jscalendarCrossobj.left = jscalendarFixedX==-1 ? ctl.offsetLeft	+ leftpos + "px": jscalendarFixedX;
	jscalendarCrossobj.top = jscalendarFixedY==-1 ?	ctl.offsetTop +	toppos + ctl.offsetHeight +	2 + "px": jscalendarFixedY;
	jscalendarConstructCalendar (1, jscalendarMonthSelected, jscalendarYearSelected);
	jscalendarCrossobj.visibility = "visible";

	jscalendarBShow = true;

	if (jscalendarIe6) {
		jscalendarCrossFrameobj.display = "block";
		jscalendarCrossFrameobj.left = jscalendarCrossobj.left;
		jscalendarCrossFrameobj.top = jscalendarCrossobj.top;
		jscalendarCrossFrameobj.width = jscalendarObj.offsetWidth;
		jscalendarCrossFrameobj.height = jscalendarObj.offsetHeight;
	}
}

document.onkeypress = function jscalendarHidecal1 () {
    try
    {
	    if (event && event.keyCode==27)
		    jscalendarHideCalendar();
    }
    catch(ex)
    {
    }
}
document.onclick = function jscalendarHidecal2 () {
	if (!jscalendarBShow)
		jscalendarHideCalendar();
	jscalendarBShow = false;
}

function loadPopupScript() {
	if(jscalendarIe)
		jscalendarInit();
	else
	{
	  var jscalendarOnloadBackup = window.onload;
	
	  function jscalendarOnload()
	  {
	    if(jscalendarOnloadBackup!=null)
	        jscalendarOnloadBackup();
	    jscalendarInit();
	  }
	
	  window.onload=jscalendarOnload;
	}
}


function formatInt(str){

    if(typeof str == 'string'){

	  //truncate 0 for number less than 10
      if (str.charAt(0)=="0"){
         return str.charAt(1);
      }

    }
	  return str;

}
