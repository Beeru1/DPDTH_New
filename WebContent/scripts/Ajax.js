
function makeAjaxRequest(url,elementId,txt) {
        var httpRequest;
        if (window.XMLHttpRequest) { // Mozilla, Safari, ...
            httpRequest = new XMLHttpRequest();
            if (httpRequest.overrideMimeType) {
                httpRequest.overrideMimeType('text/xml');
            }
        } 
        else if (window.ActiveXObject) { // IE
            try {
                httpRequest = new ActiveXObject("Msxml2.XMLHTTP");
            } 
            catch (e) {
                try {
                    httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
                } 
                catch (e) {}
            }
        }
        if (!httpRequest) {
            alert('Giving up :( Cannot create an XMLHTTP instance');
            return false;
        }
        httpRequest.onreadystatechange = function() {
        	if (txt==false) {
        		getAjaxRequestValues(httpRequest,elementId);
        	} else if (txt==true) {
        		getAjaxRequestTxtValue(httpRequest,elementId);
        	}
        };
        httpRequest.open('POST', url, false);
        httpRequest.send(null);
    }

    function getAjaxRequestValues(httpRequest,elementId) 
    {
        if ((httpRequest.readyState == 4) || (httpRequest.readyState=="complete")) 
        {
            if (httpRequest.status == 200) 
            {
            	var xmldoc = httpRequest.responseXML.documentElement;
				if(xmldoc == null) 
				{	
					var selectObj = document.getElementById(elementId);
					selectObj.options.length = 0;
					selectObj.options[selectObj.options.length] = new Option(" -Select "+ elementId + "-", "");
					return;
				}
				optionValues = xmldoc.getElementsByTagName("option");
				var selectObj = document.getElementById(elementId);
				 selectObj.options.length = 0;
				for(var i=0; i<optionValues.length; i++) 
				{
				   selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
				}
            } 
            else if (httpRequest.status == 404) 
            {
         		alert("Requested URL does not exist.");
         	} 
         	else 
         	{
                var selectObj = document.getElementById(elementId);
				selectObj.options.length = 0;
				selectObj.options[selectObj.options.length] = new Option("Exception During Transaction....");
				alert("Error: status code is " + httpRequest.status);
				return;
            }
        }
    }

    function getAjaxRequestTxtValue(httpRequest,elementId) {
        if ((httpRequest.readyState == 4) || (httpRequest.readyState=="complete")) {
            if (httpRequest.status == 200) {
            	var txtValue = httpRequest.responseText;
				if(txtValue != null) {
					document.getElementById(elementId).value = txtValue;
				} else {
					document.getElementById(elementId).value = "0" ;
				}
            } else if (httpRequest.status == 404) {
         		alert("Requested URL does not exist.");
         	} else {
                document.getElementById(elementId).value = "Exception During Transaction" ;
                alert("Error: status code is " + httpRequest.status);
				return;
            }
        }
    }

// with select option...















































function makeAjaxRequestSelect(url,elementId,txt) {
        var httpRequest;
        if (window.XMLHttpRequest) { // Mozilla, Safari, ...
            httpRequest = new XMLHttpRequest();
            if (httpRequest.overrideMimeType) {
                httpRequest.overrideMimeType('text/xml');
            }
        } 
        else if (window.ActiveXObject) { // IE
            try {
                httpRequest = new ActiveXObject("Msxml2.XMLHTTP");
            } 
            catch (e) {
                try {
                    httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
                } 
                catch (e) {}
            }
        }
        if (!httpRequest) {
            alert('Giving up :( Cannot create an XMLHTTP instance');
            return false;
        }
        httpRequest.onreadystatechange = function() {
        	if (txt==false) {
        		getAjaxRequestValuesSelect(httpRequest,elementId);
        	} else if (txt==true) {
        		getAjaxRequestTxtValueSelect(httpRequest,elementId);
        	}
        };
        httpRequest.open('POST', url, false);
        httpRequest.send(null);
    }

    function getAjaxRequestValuesSelect(httpRequest,elementId) {
        if ((httpRequest.readyState == 4) || (httpRequest.readyState=="complete")) {
            if (httpRequest.status == 200) {
            	var xmldoc = httpRequest.responseXML.documentElement;
				if(xmldoc == null) {	
					var selectObj = document.getElementById(elementId);
					selectObj.options.length = 0;
					selectObj.options[selectObj.options.length] = new Option(" -Select "+ elementId + "-", "");
					return;
				}
				optionValues = xmldoc.getElementsByTagName("option");
				var selectObj = document.getElementById(elementId);
				selectObj.options.length = 0;
				selectObj.options[selectObj.options.length] = new Option("--Select--", "-1");
				for(var i=0; i<optionValues.length; i++) {
				   selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
				}
            } else if (httpRequest.status == 404) {
         		alert("Requested URL does not exist.");
         	} else {
                var selectObj = document.getElementById(elementId);
				selectObj.options.length = 0;
				selectObj.options[selectObj.options.length] = new Option("Exception During Transaction....");
				alert("Error: status code is " + httpRequest.status);
				return;
            }
        }
    }

    function getAjaxRequestTxtValueSelect(httpRequest,elementId) {
        if ((httpRequest.readyState == 4) || (httpRequest.readyState=="complete")) {
            if (httpRequest.status == 200) {
            	var txtValue = httpRequest.responseText;
				if(txtValue != null) {
					document.getElementById(elementId).value = txtValue;
				} else {
					document.getElementById(elementId).value = "-1" ;
				}
            } else if (httpRequest.status == 404) {
         		alert("Requested URL does not exist.");
         	} else {
                document.getElementById(elementId).value = "Exception During Transaction" ;
                alert("Error: status code is " + httpRequest.status);
				return;
            }
        }
    }


	