// Begin -- Functions Created By parul
// Method for Open a New Window for the Batch Details from View Assign Stock
function openWindow(batchno,AssQTY) {
		window.open("viewBatchDetails.do?methodName=getBatchDetails&Batch_No="+batchno+"&AssignedQTY="+AssQTY);
		
	}	

function IsNumeric(strString)
   //  check for valid numeric strings	
   {
   var strValidChars = "0123456789";
   var strChar;
   var blnResult = true;

   if (strString.length == 0) return false;

   //  test strString consists of valid characters listed above
   for (i = 0; i < strString.length && blnResult == true; i++)
      {
      strChar = strString.charAt(i);
      if (strValidChars.indexOf(strChar) == -1)
         {
         blnResult = false;
         }
      }
   return blnResult;
   }	

function closeWindow() {
		window.close();
		
	}
function viewBundleDetailsOpenWindow(reqId,BundledQTY) {
	
		window.open("viewBundledStockDetails.do?methodName=getBundledStockDetails&RequestId="+reqId+"&BundledQTY="+BundledQTY);
		
	}
// Function used in AssignSImStock.jsp
function validateAssignSimStock()
{
	
if(parseInt(document.HBOAssignSimStockForm.avlSimStock.value) < parseInt(document.HBOAssignSimStockForm.assignedSimQty.value)) 
{	
	alert("SIM quantity assigned must be less than SIM quantity available");
	return false;
}
if (document.HBOAssignSimStockForm.assignedSimQty.value <=0)
{
	alert("Assigned SIM quantity can not be negative or zero.");
	return false;
}
else if(IsNumeric(document.HBOAssignSimStockForm.assignedSimQty.value)==false){
	alert("Assigned SIM quantity takes positive integer values only");
	return false;
}
}
	
//End - parul
// Method for Calling AJAX
function getChange()
{
	var Id = document.getElementById("distributor").value;
	var url="initMinReorder.do?method=getChange&Id="+Id;
	if(window.XmlHttpRequest) {
		req = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		req=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if(req==null) {
		alert("Browser Doesnt Support AJAX");
		return;
	}
	req.onreadystatechange = getOnChange;
	
	req.open("POST",url,false);
	req.send();
}
function getOnChange()
{
	if (req.readyState==4 || req.readyState=="complete") 
  	{
		
		var xmldoc = req.responseXML.documentElement;
		if(xmldoc == null){		
			return;
		}
		optionValues = xmldoc.getElementsByTagName("option");
		var selectObj = document.getElementById("product");
		selectObj.options.length = 0;
		//selectObj.options[selectObj.options.length] = new Option(" -Select- ","0");
		for(var i=0; i<optionValues.length; i++){		
			selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
		}

	}
}