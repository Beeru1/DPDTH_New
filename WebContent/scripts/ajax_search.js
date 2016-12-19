
//Gets the browser specific XmlHttpRequest Object
function getXmlHttpRequestObject() {
	if (window.XMLHttpRequest) {
		return new XMLHttpRequest();
	} else if(window.ActiveXObject) {
		return new ActiveXObject("Microsoft.XMLHTTP");
	} else {
		alert("Browser Doesn't Support AJAX");
	}
}

//Our XmlHttpRequest object to get the auto suggest   
var searchReq = getXmlHttpRequestObject();
var logoutRequest = getXmlHttpRequestObject();

//Called from keyup on the search textbox.   
//Starts the AJAX request.
function searchSuggest() {  
var frm = document.forms["TelemarketerSearchBean"];  
var str=frm.name.value;
str=str.trim();  
if(str.length > 2){     
document.getElementById("search_suggest").style.display="block";
}if(str.length == 0){
document.getElementById("search_suggest").style.display="none"; 
}

if(str.length>2){   
 
if(window.XmlHttpRequest) {
		searchReq = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		searchReq=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if(searchReq==null) {     
		alert("Browser Doesn't Support AJAX");
		return;
	}
		var str = document.forms[0].name.value;
		url="initTelemarketerSearch.do?search=" + str;
		searchReq.onreadystatechange = handleSearchSuggest;
		searchReq.open("POST",url,true);
		searchReq.send();
	}	
	
}

//Called when the AJAX response is returned. 

function handleSearchSuggest() {
	if (searchReq.readyState == 4) {
		
		var ss = document.getElementById('search_suggest'); 
		
		ss.innerHTML =''; 
	 	var str = searchReq.responseText.split("#"); 
	 	
		  
		for(i=0; i < str.length-1;i++) {       
		
		 var nOpt = document.createElement("option");
       	 var nText = document.createTextNode(str[i]);
       	 nOpt.value = str[i];
       	 nOpt.appendChild(nText);
       	 ss.appendChild(nOpt);
    
			
			}
	
	}
}

//Mouse over function
function suggestOver(div_value) {
	div_value.className = 'suggest_link_over';  
}
//Mouse out function
function suggestOut(div_value) {
	div_value.className = 'suggest_link';
}
//Click function
function setSearch(obj) {

	document.forms[0].name.value = obj.value;
	document.getElementById('search_suggest').innerHTML = '';
	document.forms[0].name.focus(); 
	hideSelect();
	
}
function hideSelect(){

	if(document.getElementById('search_suggest').value==''){
	
		document.getElementById("search_suggest").style.display="none";
		
	}
	 
}
function setSearchEnter(obj) { 

if(event.keyCode==13){
		document.forms[0].name.value = obj.value;
		document.getElementById('search_suggest').innerHTML = '';
		document.getElementById("search_suggest").style.display="none"; 
		document.forms[0].name.focus(); 
	}
}
function logoutonBrowserClose() 
{
	if(window.XmlHttpRequest) {
		logoutRequest = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		logoutRequest=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if(logoutRequest==null) {
		alert("Browser Doesn't Support AJAX");
		return;
	}
		url="logoutonBrowserClose.do";
		searchReq.open("GET",url,true);
		searchReq.send();
}

String.prototype.trim = function() {
a = this.replace(/^\s+/, '');
return a.replace(/\s+$/, '');
};

