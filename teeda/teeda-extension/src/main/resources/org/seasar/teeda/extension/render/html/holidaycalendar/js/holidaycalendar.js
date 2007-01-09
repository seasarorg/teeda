if (typeof(Teeda) == 'undefined') {
  Teeda = {};
}
if (typeof(Teeda.THolidayCalendar) == 'undefined') {
  Teeda.THolidayCalendar = {};
}
Teeda.THolidayCalendar = {
  toggleCell : function (tdId, hiddenId, selectedClass, unselectedClass) {
    var td = document.getElementById(tdId);
    var hidden = document.getElementById(hiddenId);
    if (hidden.value == "true") {
      hidden.value = "false";
      td.className = unselectedClass;
    } else {
      hidden.value = "true";
      td.className = selectedClass;
    }
  }
}