// Utilities.js

function dispCalendar(targetid) {
alert("disp cvalS");
var targetobj=document.getElementById(targetid);
calendar1(targetobj);
var d= new Date();
cal_popup1(d.getDate()+ "-" + (d.getMonth()+1) + "-" +d.getFullYear(),true);
}

