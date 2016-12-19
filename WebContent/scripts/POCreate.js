 
var xmlHttp;

function showProduct()
{ 
document.forms[0].quantity.value = "";
var selected = document.getElementById("businessCategory").value
xmlHttp=GetXmlHttpObject();
if (xmlHttp==null)
  {
  alert ("Your Browser Does Not Support AJAX!");
 return;
  } 
var url="getProductList.do";
url=url+"?methodName=getProductList&selected="+selected;

xmlHttp.onreadystatechange=stateChanged;
xmlHttp.open("POST",url,true);
xmlHttp.send();
}

function stateChanged() 
{ 
if (xmlHttp.readyState==4)
{ 
	var xmldoc = xmlHttp.responseXML.documentElement;
		if(xmldoc == null){		
			return;
		}
		optionValues = xmldoc.getElementsByTagName("option");
		var selectObj = document.getElementById("productName");
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("-Select A Product-","");
			for(var i=0; i<optionValues.length; i++){
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
		}
}
}

function GetXmlHttpObject()
{
	var xmlHttp=null;
	try
	  {
  // Firefox, Opera 8.0+, Safari
	  xmlHttp=new XMLHttpRequest();
	  }
	catch (e)
	  {
	  // Internet Explorer
	  try
	    {
	    xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
	    }
	  catch (e)
	    {
	    xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
	    }
	  }
	return xmlHttp;
}

