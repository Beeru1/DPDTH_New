function extendSession(){
	if(window.XmlHttpRequest) {
		req = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		req=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if(req==null) { 
		alert("Browser Doesnt Support AJAX");
		return;
	}
	req.onreadystatechange = function(){
	  getAjaxTextValues();
	}
	var url= "DistReco.do?methodName=extendSession";
	req.open("POST",url,false);
	req.send();
}
function getAjaxTextValues()
{
	if (req.readyState==4 || req.readyState=="complete") 
	{
		var msg=req.responseText;			
	}
}
function deleteTable(tableName){
	var selectObj = document.getElementById(tableName);
		var rowCount2 = selectObj.rows.length;
		if(rowCount2 >2){
			var rowdelete =rowCount2-1;
			var loopCondition=rowCount2-1;
			 for(var i=0;i< loopCondition;i++){
				document.getElementById(tableName).deleteRow(rowdelete);
				rowdelete --;
				}
			}
}
function getProductRecoList()
	{
	 var stbType = document.getElementById("stbTypeId").value;
	 var recoPeriod = document.getElementById("recoPeriodId").value;
	 if(recoPeriod == "" || recoPeriod == "-1"){
		alert("Please select Reco Period");
		deleteTable('tableOpen');
		deleteTable('tableRcvd');
		deleteTable('tableRet');
		deleteTable('tableActvn');
		deleteTable('tableClosing');
		deleteTable('tableSummry');
		document.getElementById("recoPeriodId").focus();
		return false;
	}
   	var url = "DistReco.do?methodName=viewRecoProducts&stbType="+stbType+"&recoPeriod="+recoPeriod;
	document.forms[0].action =url;
	document.forms[0].submit();
	}
function setVariance(rowNo,cellNo,tablename){
	var sysRowNo = parseInt(rowNo) -1;
	var partnerRowNo = rowNo;
	var varianceRowNo = parseInt(rowNo) +1;
	var sysRow = document.getElementById(tablename).rows[sysRowNo].cells;
	var partnerRow = document.getElementById(tablename).rows[partnerRowNo].cells;
	var varianceRow = document.getElementById(tablename).rows[varianceRowNo].cells;
	var sysValue;
	var partnerValue;
	var varianceValue;
	if(document.all){
     	sysValue = sysRow[cellNo].getElementsByTagName("div")[0].value;
	} else {
    	sysValue = sysRow[cellNo].getElementsByTagName("div")[0].value.textContent;
	}
	if(document.all){
     	partnerValue = partnerRow[cellNo].getElementsByTagName("input")[0].value;
	} else {
    	partnerValue = partnerRow[cellNo].getElementsByTagName("input")[0].value.textContent;
	}
	var validity = IsNumeric(partnerValue);
	if (validity == false) 
      {
     	 alert("Please Check - Non Numeric Value!");
     	 if(document.all){
	     	partnerRow[cellNo].getElementsByTagName("input")[0].value = "";
	     	varianceRow[cellNo].getElementsByTagName("input")[0].value ="";
		} else {
	    	partnerRow[cellNo].getElementsByTagName("input")[0].value.textContent = "";
	    	varianceRow[cellNo].getElementsByTagName("input")[0].value.textContent = "";
		}
      }else{
      //Calculating variance
         varianceValue =  parseInt(partnerValue) - parseInt(sysValue); 
		if(document.all){
	     	varianceRow[cellNo].innerHTML = "<div value=\"" + varianceValue +  "\">" + varianceValue + "</div>";
	     	//varianceRow[cellNo].getElementsByTagName("input")[0].value = varianceValue;
		} else {
	    	varianceRow[cellNo].innerHTML = "<div value=\"" + varianceValue +  "\">" + varianceValue + "</div>";
	     	//varianceRow[cellNo].getElementsByTagName("input")[0].value.textContent = varianceValue;
		}
      }
}
function IsNumeric(strString)
   {
   var strValidChars = "0123456789";
   var strChar;
   var blnResult = true;
   if (strString.length == 0) return false;
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
function getVariance(rowNo,cellNo,tablename){
	var sysRowNo = rowNo -2;
	var partnerRowNo = rowNo -1;
	var variance = 0;
	var sysRow = document.getElementById(tablename).rows[sysRowNo].cells;
	var partnerRow = document.getElementById(tablename).rows[partnerRowNo].cells;
	var varianceRow = document.getElementById(tablename).rows[rowNo].cells;
	var sysValue;
	var partnerValue;
	if(partnerValue = partnerRow[cellNo].getElementsByTagName("div")[0] != null &&  partnerRow[cellNo].getElementsByTagName("div")[0].value != "undefined"){
		if(document.all){
	     	sysValue = sysRow[cellNo].getElementsByTagName("div")[0].value;
		} else {
	    	sysValue = sysRow[cellNo].getElementsByTagName("div")[0].value.textContent;
		}
		if(document.all){
	     	partnerValue = partnerRow[cellNo].getElementsByTagName("div")[0].value;
		} else {
	    	partnerValue = partnerRow[cellNo].getElementsByTagName("div")[0].value.textContent;
		}
		//Calculating variance
		variance =parseInt(partnerValue) - parseInt(sysValue)   ;
	}
	if(document.all){
			varianceRow[cellNo].innerHTML = "<div value=\"" + variance +  "\">" + variance + "</div>";
		} else {
			varianceRow[cellNo].innerHTML = "<div value=\"" + variance +  "\">" + variance + "</div>";
		}
	return variance;
	
}
function getSummVariance(rowNo,cellNo,tablename){
	var sysRowNo = rowNo -2;
	var partnerRowNo = rowNo -1;
	var variance = 0;
	var sysRow = document.getElementById(tablename).rows[sysRowNo].cells;
	var partnerRow = document.getElementById(tablename).rows[partnerRowNo].cells;
	var varianceRow = document.getElementById(tablename).rows[rowNo].cells;
	var sysValue;
	var partnerValue;
	if(partnerValue = partnerRow[cellNo].getElementsByTagName("div")[0] != null &&  partnerRow[cellNo].getElementsByTagName("div")[0].value != "undefined"){
			if(document.all){
	     	sysValue = sysRow[cellNo].getElementsByTagName("div")[0].value;
		} else {
	    	sysValue = sysRow[cellNo].getElementsByTagName("div")[0].value.textContent;
		}
		if(document.all){
	     	partnerValue = partnerRow[cellNo].getElementsByTagName("div")[0].value;
		} else {
	    	partnerValue = partnerRow[cellNo].getElementsByTagName("div")[0].value.textContent;
		}
		//Calculating variance
		variance = parseInt(partnerValue) - parseInt(sysValue);
   }
	if(document.all){
	     	varianceRow[cellNo].innerHTML = "<div value=\"" + variance +  "\">" + variance + "</div>";
	     	//varianceRow[cellNo].getElementsByTagName("input")[0].value = variance;
		} else {
	    	varianceRow[cellNo].innerHTML = "<div value=\"" + variance +  "\">" + variance + "</div>";
	    	//varianceRow[cellNo].getElementsByTagName("input")[0].value.textContent = variance;
		}
	return variance;
}
function submitData(rowNum,productId){
// Start of changing by RAM
var msg="";
 var recoPeriod = document.getElementById("recoPeriodId").value;
if(document.forms[0].oldOrNewReco.value == 'N')
{
	if(window.XmlHttpRequest) {
		req = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		req=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if(req==null) { 
		alert("Browser Doesnt Support AJAX");
		return;
	}
	req.onreadystatechange = function()
 	 {
	 if (req.readyState==4 || req.readyState=="complete") 
		{
			 msg=req.responseText;			
		}	
	}
	var url= "DistReco.do?methodName=findNotOkStbs&recoPeriodId="+recoPeriod+"&productId="+productId;
	req.open("POST",url,false);
	req.send();
//alert("msg  :"+msg);
 
if(' '!=msg)
{

if(msg==1)
{
alert("Please upload serialized stock");
return false;
}
if(msg==2)
{
alert("Please upload defective stock");
return false;
}
if(msg==3)
{
alert("Please upload upgrade stock");
return false;
}
if(msg==4)
{
alert("Please upload churn stock");
return false;
}
}
if(typeof String.prototype.trim !== 'function') {
	  String.prototype.trim = function() {
	    return this.replace(/^\s+|\s+$/g, ''); 
	  }
	}
if(msg.trim() != 'norecord')
{
	if(!confirm("You have marked "+msg+" STBs ,Does Not exist at your end ,Please confirm !"))
	{
	return false;
	}
}
	
}
// End of changing by RAM
		 var stbType = document.getElementById("stbTypeId").value;
		 if(recoPeriod == "" || recoPeriod == "-1"){
			alert("Please select Reco Period");
			document.getElementById("recoPeriodId").focus();
			return false;
		}
		var partnerRowOpen = document.getElementById("tableOpen").rows[rowNum].cells;
		var partnerRowRcvd = document.getElementById("tableRcvd").rows[rowNum].cells;
		var partnerRowRet = document.getElementById("tableRet").rows[rowNum].cells;
		var partnerRowAct = document.getElementById("tableActvn").rows[rowNum].cells;
		var partnerRowClosng = document.getElementById("tableClosing").rows[rowNum].cells;
		if(document.all){
			if(typeof(partnerRowOpen[1].getElementsByTagName("input")[0])!="undefined")
     			var productId = partnerRowOpen[1].getElementsByTagName("input")[0].value;
     		else
     			var productId = partnerRowOpen[1].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowOpen[1].getElementsByTagName("input")[0])!="undefined")
     			var productId = partnerRowOpen[1].getElementsByTagName("input")[0].value.textContent;
     		else
     			var productId = partnerRowOpen[1].getElementsByTagName("div")[0].value.textContent;
		}	
		if(document.all){
			if(typeof(partnerRowOpen[2].getElementsByTagName("input")[0])!="undefined")
     			var pendingPOOpening = partnerRowOpen[2].getElementsByTagName("input")[0].value;
     		else
     			var pendingPOOpening = partnerRowOpen[2].getElementsByTagName("div")[0].value;
		} else {
			if(typeof(partnerRowOpen[2].getElementsByTagName("input")[0])!="undefined")
     			var pendingPOOpening = partnerRowOpen[2].getElementsByTagName("input")[0].value.textContent;
     		else
     			var pendingPOOpening = partnerRowOpen[2].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(pendingPOOpening) ==false  && !(partnerRowOpen[2].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Pending PO Opening Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRowOpen[3].getElementsByTagName("input")[0])!="undefined")
     			var pendingDCOpen = partnerRowOpen[3].getElementsByTagName("input")[0].value;
     		else
     			var pendingDCOpen = partnerRowOpen[3].getElementsByTagName("div")[0].value;
		} else {
			if(typeof(partnerRowOpen[3].getElementsByTagName("input")[0])!="undefined")
     			var pendingDCOpen = partnerRowOpen[3].getElementsByTagName("input")[0].value.textContent;
     		else
     		 var pendingDCOpen = partnerRowOpen[3].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(pendingDCOpen) ==false  && !(partnerRowOpen[3].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Pending DC Open Stock");
			return false;
		}
		if(document.all){
			if(typeof(partnerRowOpen[4].getElementsByTagName("input")[0])!="undefined")
     			var serOpen = partnerRowOpen[4].getElementsByTagName("input")[0].value;
     		else
     			var serOpen = partnerRowOpen[4].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowOpen[4].getElementsByTagName("input")[0])!="undefined")
     			var serOpen = partnerRowOpen[4].getElementsByTagName("input")[0].value.textContent;
     		else
     			var serOpen = partnerRowOpen[4].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(serOpen) ==false  && !(partnerRowOpen[4].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Serialized Open Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRowOpen[5].getElementsByTagName("input")[0])!="undefined")
     			var nsrOpen = partnerRowOpen[5].getElementsByTagName("input")[0].value;
     		else
     			var nsrOpen = partnerRowOpen[5].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowOpen[5].getElementsByTagName("input")[0])!="undefined")
     			var nsrOpen = partnerRowOpen[5].getElementsByTagName("input")[0].value.textContent;
     		else
     			var nsrOpen = partnerRowOpen[5].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(nsrOpen) ==false  && !(partnerRowOpen[5].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Non Serialized Open Stock");
			return false;
		}
		if(document.all){
			if(typeof(partnerRowOpen[6].getElementsByTagName("input")[0])!="undefined")
     			var defOpen = partnerRowOpen[6].getElementsByTagName("input")[0].value;
     		else
     			var defOpen = partnerRowOpen[6].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowOpen[6].getElementsByTagName("input")[0])!="undefined")
     			var defOpen = partnerRowOpen[6].getElementsByTagName("input")[0].value.textContent;
     		else
     			var defOpen = partnerRowOpen[6].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(defOpen) ==false && !(partnerRowOpen[6].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Defective Open Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRowOpen[7].getElementsByTagName("input")[0])!="undefined")
     			var upgaradeOpen = partnerRowOpen[7].getElementsByTagName("input")[0].value;
     		else
     			var upgaradeOpen = partnerRowOpen[7].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowOpen[7].getElementsByTagName("input")[0])!="undefined")
     			var upgaradeOpen = partnerRowOpen[7].getElementsByTagName("input")[0].value.textContent;
     		else
     			var upgaradeOpen = partnerRowOpen[7].getElementsByTagName("div")[0].value.textContent;
		}
		if(chckBlank(upgaradeOpen) ==false  && !(partnerRowOpen[7].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Upgrade Open Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRowOpen[8].getElementsByTagName("input")[0])!="undefined")
     			var churnOpen = partnerRowOpen[8].getElementsByTagName("input")[0].value;
     		else
     			var churnOpen = partnerRowOpen[8].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowOpen[8].getElementsByTagName("input")[0])!="undefined")
     			var churnOpen = partnerRowOpen[8].getElementsByTagName("input")[0].value.textContent;
     		else
     			var churnOpen = partnerRowOpen[8].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(churnOpen) ==false  && !(partnerRowOpen[8].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Churn Open Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRowRcvd[3].getElementsByTagName("input")[0])!="undefined")
     			var rcvdWH = partnerRowRcvd[3].getElementsByTagName("input")[0].value;
     		else
     			var rcvdWH = partnerRowRcvd[3].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowRcvd[3].getElementsByTagName("input")[0])!="undefined")
     			var rcvdWH = partnerRowRcvd[3].getElementsByTagName("input")[0].value.textContent;
     		else
     			var rcvdWH = partnerRowRcvd[3].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(rcvdWH) ==false  && !(partnerRowRcvd[3].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Received from Warehouse Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRowRcvd[4].getElementsByTagName("input")[0])!="undefined")
     			var recInterSSdOK = partnerRowRcvd[4].getElementsByTagName("input")[0].value;
     		else
     			var recInterSSdOK = partnerRowRcvd[4].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowRcvd[4].getElementsByTagName("input")[0])!="undefined")
     			var recInterSSdOK = partnerRowRcvd[4].getElementsByTagName("input")[0].value.textContent;
     		else
     			var recInterSSdOK = partnerRowRcvd[4].getElementsByTagName("div")[0].value.textContent;
		}
		if(chckBlank(recInterSSdOK) ==false  && !(partnerRowRcvd[4].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Received Inter SSD OK Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRowRcvd[5].getElementsByTagName("input")[0])!="undefined")
     			var recInterSSDDef = partnerRowRcvd[5].getElementsByTagName("input")[0].value;
     		else
     			var recInterSSDDef = partnerRowRcvd[5].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowRcvd[5].getElementsByTagName("input")[0])!="undefined")
     			var recInterSSDDef = partnerRowRcvd[5].getElementsByTagName("input")[0].value.textContent;
     		else
     			var recInterSSDDef = partnerRowRcvd[5].getElementsByTagName("div")[0].value.textContent;
		}
		if(chckBlank(recInterSSDDef) ==false  && !(partnerRowRcvd[5].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Received Inter SSD Defective Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRowRcvd[6].getElementsByTagName("input")[0])!="undefined")
     			var recUpgrade = partnerRowRcvd[6].getElementsByTagName("input")[0].value;
     		else
     			var recUpgrade = partnerRowRcvd[6].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowRcvd[6].getElementsByTagName("input")[0])!="undefined")
     			var recUpgrade = partnerRowRcvd[6].getElementsByTagName("input")[0].value.textContent;
     		else
     			var recUpgrade = partnerRowRcvd[6].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(recUpgrade) ==false  && !(partnerRowRcvd[6].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Received Upgrade Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRowRcvd[7].getElementsByTagName("input")[0])!="undefined")
     			var recChurn = partnerRowRcvd[7].getElementsByTagName("input")[0].value;
     		else
     			var recChurn = partnerRowRcvd[7].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowRcvd[7].getElementsByTagName("input")[0])!="undefined")
     			var recChurn = partnerRowRcvd[7].getElementsByTagName("input")[0].value.textContent;
     		else
     			var recChurn = partnerRowRcvd[7].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(recChurn) ==false  && !(partnerRowRcvd[7].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Received Churn Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRowRet[4].getElementsByTagName("input")[0])!="undefined")
     			var returnedOK = partnerRowRet[4].getElementsByTagName("input")[0].value;
     		else
     			var returnedOK = partnerRowRet[4].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowRet[4].getElementsByTagName("input")[0])!="undefined")
     			var returnedOK = partnerRowRet[4].getElementsByTagName("input")[0].value.textContent;
     		else
     			var returnedOK = partnerRowRet[4].getElementsByTagName("div")[0].value.textContent;	
		}	
		if(chckBlank(returnedOK) ==false  && !(partnerRowRet[4].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Return OK Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRowRet[5].getElementsByTagName("input")[0])!="undefined")
     			var retInterssdOk = partnerRowRet[5].getElementsByTagName("input")[0].value;
     		else
     			var retInterssdOk = partnerRowRet[5].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowRet[5].getElementsByTagName("input")[0])!="undefined")
     			var retInterssdOk = partnerRowRet[5].getElementsByTagName("input")[0].value.textContent;
     		else
     			var retInterssdOk = partnerRowRet[5].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(retInterssdOk) ==false  && !(partnerRowRet[5].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Return Inter SSD OK Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRowRet[6].getElementsByTagName("input")[0])!="undefined")
     			var retInterssdDef = partnerRowRet[6].getElementsByTagName("input")[0].value;
     		else
     			var retInterssdDef = partnerRowRet[6].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowRet[6].getElementsByTagName("input")[0])!="undefined")
     			var retInterssdDef = partnerRowRet[6].getElementsByTagName("input")[0].value.textContent;
     		else
     			var retInterssdDef = partnerRowRet[6].getElementsByTagName("div")[0].value;
		}	
		if(chckBlank(retInterssdDef) ==false  && !(partnerRowRet[6].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Returned Inter SSD Defective Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRowRet[7].getElementsByTagName("input")[0])!="undefined")
     			var retDoaBI = partnerRowRet[7].getElementsByTagName("input")[0].value;
     		else
     			var retDoaBI = partnerRowRet[7].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowRet[7].getElementsByTagName("input")[0])!="undefined")
     			var retDoaBI = partnerRowRet[7].getElementsByTagName("input")[0].value.textContent;
     		else
     			var retDoaBI = partnerRowRet[7].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(retDoaBI) ==false  && !(partnerRowRet[7].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Return Dead On Arrival Before Installation Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRowRet[8].getElementsByTagName("input")[0])!="undefined")
     			var retDoaAi = partnerRowRet[8].getElementsByTagName("input")[0].value;
     		else
     			var retDoaAi = partnerRowRet[8].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowRet[8].getElementsByTagName("input")[0])!="undefined")
     			var retDoaAi = partnerRowRet[8].getElementsByTagName("input")[0].value.textContent;
     		else
     		var retDoaAi = partnerRowRet[8].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(retDoaAi) ==false && !(partnerRowRet[8].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Return Dead On Arrival After Installation Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRowRet[9].getElementsByTagName("input")[0])!="undefined")
     			var retC2s = partnerRowRet[9].getElementsByTagName("input")[0].value;
     		else
     			var retC2s = partnerRowRet[9].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowRet[9].getElementsByTagName("input")[0])!="undefined")
     			var retC2s = partnerRowRet[9].getElementsByTagName("input")[0].value.textContent;
     		else
     		var retC2s = partnerRowRet[9].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(retC2s) ==false && !(partnerRowRet[9].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Return C2S Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRowRet[10].getElementsByTagName("input")[0])!="undefined")
     			var retChurn = partnerRowRet[10].getElementsByTagName("input")[0].value;
     		else
     			var retChurn = partnerRowRet[10].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowRet[10].getElementsByTagName("input")[0])!="undefined")
     			var retChurn = partnerRowRet[10].getElementsByTagName("input")[0].value.textContent;
     		else
     		var retChurn = partnerRowRet[10].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(retChurn) ==false && !(partnerRowRet[10].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Retutn Churn Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRowRet[11].getElementsByTagName("input")[0])!="undefined")
     			var retDefSwap = partnerRowRet[11].getElementsByTagName("input")[0].value;
     		else
     			var retDefSwap = partnerRowRet[11].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowRet[11].getElementsByTagName("input")[0])!="undefined")
     			var retDefSwap = partnerRowRet[11].getElementsByTagName("input")[0].value.textContent;
     		else
     		var retDefSwap = partnerRowRet[11].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(retDefSwap) ==false && !(partnerRowRet[11].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Return Defective Swap");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRowAct[5].getElementsByTagName("input")[0])!="undefined")
     			var activation = partnerRowAct[5].getElementsByTagName("input")[0].value;
     		else
     			var activation = partnerRowAct[5].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowAct[5].getElementsByTagName("input")[0])!="undefined")
     			var activation = partnerRowAct[5].getElementsByTagName("input")[0].value.textContent;
     		else
     		var activation = partnerRowAct[5].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(activation) ==false && !(partnerRowAct[5].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Activation Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRowAct[6].getElementsByTagName("div")[0])!="undefined")
     			var invChange = partnerRowAct[6].getElementsByTagName("div")[0].value;
     		else
     			var invChange = partnerRowAct[6].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowAct[6].getElementsByTagName("div")[0])!="undefined")
     			var invChange = partnerRowAct[6].getElementsByTagName("div")[0].value.textContent;
     		else
     		var invChange = partnerRowAct[6].getElementsByTagName("div")[0].value.textContent;
		}	
		if(document.all){
     		if(typeof(partnerRowClosng[8].getElementsByTagName("input")[0])!="undefined")
     			var serClosing = partnerRowClosng[8].getElementsByTagName("input")[0].value;
     		else
     			var serClosing = partnerRowClosng[8].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowClosng[8].getElementsByTagName("input")[0])!="undefined")
     			var serClosing = partnerRowClosng[8].getElementsByTagName("input")[0].value.textContent;
     		else
     		var serClosing = partnerRowClosng[8].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(serClosing) ==false && !(partnerRowClosng[8].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Serialized Closing Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRowClosng[9].getElementsByTagName("input")[0])!="undefined")
     			var nserClosing = partnerRowClosng[9].getElementsByTagName("input")[0].value;
     		else
     			var nserClosing = partnerRowClosng[9].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowClosng[9].getElementsByTagName("input")[0])!="undefined")
     			var nserClosing = partnerRowClosng[9].getElementsByTagName("input")[0].value.textContent;
     		else
     		var nserClosing = partnerRowClosng[9].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(nserClosing) ==false && !(partnerRowClosng[9].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Non Serialized Closing Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRowClosng[10].getElementsByTagName("input")[0])!="undefined")
     			var defClosing = partnerRowClosng[10].getElementsByTagName("input")[0].value;
     		else
     			var defClosing = partnerRowClosng[10].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowClosng[10].getElementsByTagName("input")[0])!="undefined")
     			var defClosing = partnerRowClosng[10].getElementsByTagName("input")[0].value.textContent;
     		else
     		var defClosing = partnerRowClosng[10].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(defClosing) ==false && !(partnerRowClosng[10].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Defecting Closing Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRowClosng[11].getElementsByTagName("input")[0])!="undefined")
     			var upgradeClosing = partnerRowClosng[11].getElementsByTagName("input")[0].value;
     		else
     			var upgradeClosing = partnerRowClosng[11].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowClosng[11].getElementsByTagName("input")[0])!="undefined")
     			var upgradeClosing = partnerRowClosng[11].getElementsByTagName("input")[0].value.textContent;
     		else
     		var upgradeClosing = partnerRowClosng[11].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(upgradeClosing) ==false && !(partnerRowClosng[11].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Upgrade Closing Stock Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRowClosng[12].getElementsByTagName("input")[0])!="undefined")
     			var churnClosing = partnerRowClosng[12].getElementsByTagName("input")[0].value;
     		else
     			var churnClosing = partnerRowClosng[12].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowClosng[12].getElementsByTagName("input")[0])!="undefined")
     			var churnClosing = partnerRowClosng[12].getElementsByTagName("input")[0].value.textContent;
     		else
     		var churnClosing = partnerRowClosng[12].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(churnClosing) ==false && !(partnerRowClosng[12].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Churn Closing Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRowClosng[13].getElementsByTagName("input")[0])!="undefined")
     			var penPOClosing = partnerRowClosng[13].getElementsByTagName("input")[0].value;
     		else
     			var penPOClosing = partnerRowClosng[13].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowClosng[13].getElementsByTagName("input")[0])!="undefined")
     			var penPOClosing = partnerRowClosng[13].getElementsByTagName("input")[0].value.textContent;
     		else
     		var penPOClosing = partnerRowClosng[13].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(penPOClosing) ==false && !(partnerRowClosng[13].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Pending PO Closing Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRowClosng[14].getElementsByTagName("input")[0])!="undefined")
     			var penDCClosing = partnerRowClosng[14].getElementsByTagName("input")[0].value;
     		else
     			var penDCClosing = partnerRowClosng[14].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowClosng[14].getElementsByTagName("input")[0])!="undefined")
     			var penDCClosing = partnerRowClosng[14].getElementsByTagName("input")[0].value.textContent;
     		else
     		var penDCClosing = partnerRowClosng[14].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(penDCClosing) ==false && !(partnerRowClosng[14].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Pending DC Closing Stock");
			return false;
		}
		if(pendingPOOpening=="")
			pendingPOOpening="0";
		if(pendingDCOpen=="")
			pendingDCOpen="0";		
		if(serOpen=="")
			serOpen="0";		
		if(nsrOpen=="")
			nsrOpen="0";	
		if(defOpen=="")
			defOpen="0";	
		if(upgaradeOpen=="")
			upgaradeOpen="0";	
		if(churnOpen=="")
			churnOpen="0";	
		if(rcvdWH=="")
			rcvdWH="0";	
		if(recInterSSdOK=="")
			recInterSSdOK="0";
		if(recInterSSDDef=="")
			recInterSSDDef="0";
		if(recUpgrade=="")
			recUpgrade="0";
		if(recChurn=="")
			recChurn="0";
		if(returnedOK=="")
			returnedOK="0";
		if(retInterssdOk=="")
			retInterssdOk="0";
		if(retInterssdDef=="")
			retInterssdDef="0";
		if(retDoaBI=="")
			retDoaBI="0";
		if(retDoaAi=="")
			retDoaAi="0";
		if(retC2s=="")
			retC2s="0";
		if(retChurn=="")
			retChurn="0";
		if(retDefSwap=="")
			retDefSwap="0";
		if(activation=="")
			activation="0";
		if(invChange=="")
			invChange="0";
		if(serClosing=="")
			serClosing="0";
		if(nserClosing=="")
			nserClosing="0";
		if(defClosing=="")
			defClosing="0";
		if(upgradeClosing=="")
			upgradeClosing="0";
		if(churnClosing=="")
			churnClosing="0";
		if(penPOClosing=="")
			penPOClosing="0";
		if(penDCClosing=="")
			penDCClosing="0";
		var totalPartClosing = parseInt(serClosing) + parseInt(nserClosing) + parseInt(defClosing) + parseInt(upgradeClosing) +parseInt(churnClosing) + parseInt(penDCClosing); 
		var totalOpen = parseInt(serOpen) + parseInt(nsrOpen) +parseInt(defOpen) +parseInt(upgaradeOpen) +parseInt(churnOpen) + parseInt(pendingDCOpen); 
		var totRcvd =  parseInt(rcvdWH) + parseInt(recInterSSdOK)+ parseInt(recInterSSDDef)+ parseInt(recUpgrade)+ parseInt(recChurn);
		var totRet = parseInt(returnedOK) +parseInt(retInterssdOk)+parseInt(retInterssdDef)+parseInt(retDoaBI)+parseInt(retDoaAi)+ parseInt(retC2s)+parseInt(retChurn) +parseInt(retDefSwap)+parseInt(activation);
		var totalSysClosing = parseInt(totalOpen) + parseInt(totRcvd) - parseInt(totRet)
		var oldOrNewReco = document.getElementById("oldOrNewReco").value;
		
		
		var remarks=document.getElementById("remarks").value;
		 if(confirm("Are you Sure to enter this reco detail ?")){
			var url = "DistReco.do?methodName=submitDetail&stbType="+stbType+"&recoPeriod="+recoPeriod+"&productId="+productId+"&pendingPOOpening="+pendingPOOpening+"&pendingDCOpen="+pendingDCOpen+"&serOpen="+serOpen+"&nsrOpen="+nsrOpen+"&defOpen="+defOpen+"&upgaradeOpen="+upgaradeOpen;
			url += "&churnOpen="+churnOpen+"&rcvdWH="+rcvdWH+"&recInterSSdOK="+recInterSSdOK+"&recInterSSDDef="+recInterSSDDef+"&recUpgrade="+recUpgrade+"&recChurn="+recChurn+"&returnedOK="+returnedOK+"&retInterssdOk="+retInterssdOk+"&retInterssdDef="+retInterssdDef;
			url += "&retDoaBI="+retDoaBI+"&retDoaAi="+retDoaAi+"&retC2s="+retC2s+"&retChurn="+retChurn+"&retDefSwap="+retDefSwap+"&activation="+activation+"&invChange="+invChange;
			url += "&serClosing="+serClosing+"&nserClosing="+nserClosing+"&defClosing="+defClosing+"&upgradeClosing="+upgradeClosing+"&churnClosing="+churnClosing+"&penPOClosing="+penPOClosing;
			url += "&penDCClosing="+penDCClosing;
			url += "&remarks="+remarks;     // Added By Sadiqua
			url += "&oldOrNewReco="+oldOrNewReco;
			document.forms[0].action =url;
			document.forms[0].submit();
	}else{
		return null;
		}
}

function textareaMaxlength() {
	var size=500;
	var address = document.getElementById("remarks").value;
   	if (address.length >= size) {
	   	address = address.substring(0, size-1);
	   	document.getElementById("remarks").value = address;
	    alert ("Remarks field  can contain 500 characters only.");
	   	document.getElementById("remarks").focus();
		return false;
		
   	}
}
		
function chckBlank(value){
	var blnResult = true;
	if(value == null || value == "null" || value == ""){
		blnResult = false; 
	}
	return blnResult;
}
function printtPage()
{				var certId=document.getElementById("certId").value;
 				var url="printRecoDetails.do?methodName=printRecoDetail&CERT_ID="+certId;
 			    window.open(url,"SerialNo","width=750,height=650,scrollbars=yes,toolbar=no");
}
function getprintpageButton(){
if(document.forms[0].oldOrNewReco.value == 'N')
		{
var e1=document.getElementById("closingStockUploadScreen");
e1.style.visibility='hidden';
}
	var certtId = document.getElementById("certId").value;
	var certId = document.getElementById("certificateId").value;
		
	if(certId != "" && certId != "-1"){
   		document.getElementById("printPage").disabled = false;
  		document.getElementById("certId").value=certId; 
    	//printPage(certId);
    	enableLink();
    }
	if(certtId != "" && certtId != "-1"){
  	 	document.getElementById("printPage").disabled = false;
  	 	document.getElementById("certId").value=certtId;
    }
		    
    if(certId=="-1" && certtId=="-1"){
     	document.getElementById("printPage").disabled = true;
    } 
    if(certId=="" && certtId==""){
     	document.getElementById("printPage").disabled = true;
    } 
  	if(document.getElementById("isValidToEnter").value=="0"){
			alert("Note: Selected reco cannot be updated unless previous reco is complete !!");
			//return false;
	}
}
function disableLink()
	{
		var dls = document.getElementById('chromemenu').getElementsByTagName("li");
		 alert("hi");
		for (i=0;i<dls.length;i++) {
		dls[i].setAttribute("className", "defaultCursor");
			var dds = dls[i].getElementsByTagName("a");
			for(j=0;j<dds.length;j++){
				document.getElementById(dds[j].getAttribute("rel")).style.visibility="hidden";
				document.getElementById(dds[j].getAttribute("rel")).style.display="none";
			}
		}
	}	
	function enableLink()
	{
		var dls = document.getElementById('chromemenu').getElementsByTagName("li");
		
		for (i=0;i<dls.length;i++) {
		dls[i].setAttribute("className", "pointerCursor");
			var dds = dls[i].getElementsByTagName("a");
			for(j=0;j<dds.length;j++){
				document.getElementById(dds[j].getAttribute("rel")).style.display="block";
			}
		}
	}	
	function checkIsPswdExpiring()
	{
		var pswdExpireDays = '<%=session.getAttribute("pswdExpireDays")%>';	
		var recodownloadMsg = document.getElementById("strmsgId").value;
 		if(pswdExpireDays>0 && pswdExpireDays!=""){
	 		var ans = confirm("Your password will expire in "+pswdExpireDays+" days. Do you like to change your password now?");
	 		if(ans == true){
	 		window.location.href = "./jsp/authentication/changePassword.jsp";
	 		}
 		}
 		if(recodownloadMsg !=null && recodownloadMsg != '') {alert(recodownloadMsg);}
 		
	}	

/* Adding by pratap*/
function downloadDetails(productId,tabId,columnId ,prodectName)
	{
	/********tabId*********
	1 opening stock
	2 received stock
	3 returned stock
	4 activation stock
	************************/
	
	var recoPeriodId = document.getElementById("recoPeriodId").value;
	//alert("tabId :"+tabId+"  columnId :"+columnId+"  recoPeriodId :"+recoPeriodId);
	if(window.XmlHttpRequest) {
		req = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		req=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if(req==null) { 
		alert("Browser Doesnt Support AJAX");
		return;
	}
	if(tabId == "5")
		var url= "DistReco.do?methodName=downloadClosingStockDetails&productId="+productId+"&tabId="+tabId+"&columnId="+columnId+"&prodectName="+prodectName+"&recoPeriodId="+recoPeriodId;
	else
		var url= "DistReco.do?methodName=downloadDetails&productId="+productId+"&tabId="+tabId+"&columnId="+columnId+"&prodectName="+prodectName+"&recoPeriodId="+recoPeriodId;
	 document.forms[0].action=url;
	document.forms[0].method="post";
	document.forms[0].submit();
}	
function uploadDetails(productId,tabId,columnId)
	{
	var fileTypeAlert = "Only Excel file can be uploaded.";
	/********tabId*********
	1 opening stock
	2 received stock
	3 returned stock
	4 activation stock
	************************/
	//var recoPeriodId = document.getElementById("recoPeriodId").value;
	if(document.getElementById("serializedStock").value == "")
     {
		alert("Please Select a file");
		return false;
	}
    else if(!(/^.*\.xls$/.test(document.getElementById("serializedStock").value)) && !(/^.*\.xlsx$/.test(document.getElementById("serializedStock").value)) ) {
		alert(fileTypeAlert);
		return false;
	}else if(navigator.userAgent.indexOf("MSIE") != -1) {
		if(document.getElementById("serializedStock").value.indexOf(":\\") == -1 ) {
			alert("Please Select a proper file.")
			return false;
		}
	}
	 var stbType = document.getElementById("stbTypeId").value;
	 var recoPeriod = document.getElementById("recoPeriodId").value;
	alert("uploadDetails :::::tabId :"+tabId+"  columnId :"+columnId+"  productId :"+productId+" recoPeriod:"+recoPeriod+" stbType:"+stbType);
	var recoPeriodId = document.forms[0].recoPeriodId.value;
	document.forms[0].action="DistReco.do?methodName=uploadClosingStockDetails&productId="+productId+"&tabId="+tabId+"&columnId="+columnId+"&recoPeriod="+recoPeriodId+"&stbType="+stbType;
	document.forms[0].method="POST";
	//loadSubmit();
	document.forms[0].submit();
	}
function openModelDialog(tabId,columnId,browseButtonName,productId)
	{
		 var recoPeriodId = document.getElementById("recoPeriodId").value;
	alert("****************productId:"+productId+" columnId:"+columnId+" recoPeriodId:"+recoPeriodId);
	//alert("columnId :"+columnId+" productId:"+);
		if(productId == '-1')
		{
		alert("Please Select product");
		return false;
		}
		if(columnId == '-1') 
		{
		alert("Please Select stock type");
		return false;
		}
		document.forms[0].productsLists.disabled='true';
		 var win = window.showModalDialog("DistReco.do?methodName=showUploadClosingStockDetailsWindow&tabId="+tabId+"&columnId="+columnId+"&browseButtonName="+browseButtonName+"&productId="+productId+"&recoPeriodId="+recoPeriodId,"uploadExl","dialogWidth:300px;dialogHeight:150px");
		return true;
	}
	
function uploadExcel(tabId,columnId,browseButtonName,productId)
{

	var recoPeriodId = document.getElementById("recoPeriodId").value;
	var stbType = document.getElementById("stbTypeId").value;
	var fileTypeAlert = "Only Excel file can be uploaded.";
	
		if(productId == '-1')
		{
		alert("Please Select product");
		return false;
		}
		if(columnId == '-1') 
		{
		alert("Please Select stock type");
		return false;
		}
	if(document.getElementById("uploadedFile").value == "")
	{
		alert("Please Select File");
		return false;
	}
    else if(!(/^.*\.xls$/.test(document.getElementById("uploadedFile").value)) && !(/^.*\.xlsx$/.test(document.getElementById("uploadedFile").value)) )
     {
		alert(fileTypeAlert);
		return false;
	}
	else if(navigator.userAgent.indexOf("MSIE") != -1) 
	{
		if(document.getElementById("uploadedFile").value.indexOf(":\\") == -1 )
		 {
			alert("Please Select a proper file.");
			return false;
		}
	}
	/***************changing by RAM 8 Apr 2014********************/
	var productsLists = document.getElementById("productsLists");
	var stockType = document.getElementById("stockType");
	var productsListsText = productsLists.options[productsLists.options.selectedIndex].text;
	var stockTypeText = stockType.options[stockType.options.selectedIndex].text;
	/***********************************/
	var url= "DistReco.do?methodName=uploadClosingStockDetailsXls&productId="+productId+"&tabId="+tabId+"&columnId="+columnId+"&recoPeriodId="+recoPeriodId+"&stbType="+stbType+"&browseButtonName="+browseButtonName+"&productsListsText="+productsListsText+"&stockTypeText="+stockTypeText;
	 document.forms[0].action=url;
	document.forms[0].method="post";
	document.forms[0].submit()
}
function refreshPage(stockType,productId)
{
	var stbType = document.getElementById("stbTypeId").value;
    var recoPeriodId = document.getElementById("recoPeriodId").value;
	if(productId == '-1')
	{
		alert("Please Select product");
		return false;
	}
	if(stockType == '-1') 
	{
		alert("Please Select stock type");
		return false;
	}
	var url = "DistReco.do?methodName=viewRecoProducts&stockType="+stockType+"&productId="+productId+"&stbType="+stbType+"&recoPeriod="+recoPeriodId;
	document.forms[0].action =url;
	document.forms[0].submit();
}
function disableUpload()
{
	
	document.forms[0].uploadedFile.disabled= "diabled";
    document.forms[0].upload.disabled= "diabled";
}
/**********************************/
function confirmFirst(stockType,productId)
{
	var recoPeriodId = document.getElementById("recoPeriodId").value;
	if(productId == '-1')
	{
		alert("Please Select product");
		return false;
	}
	if(stockType == '-1') 
	{
		alert("Please Select stock type");
		return false;
	}
	var result=confirm("Are you ok with system generated stock ? Please confirm, if Not then Click Cancel and Upload ");
	if(result == false)
	{
	document.forms[0].uploadedFile.disabled= false;
	document.forms[0].upload.disabled= false;
	}else {
		var url= "DistReco.do?methodName=uploadClosingStockDetailsXlscheck&productId="+productId+"&columnId="+stockType+"&recoPeriodId="+recoPeriodId+"&okWithSystemStock=5";
		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit()
		document.forms[0].uploadedFile.disabled= "disabled";
		document.forms[0].upload.disabled= "disabled";
	}
	return result;
	}

//added by vishwas for excel remove indata base calling dunction

function uploadExceldataremoval(tabId,columnId,browseButtonName,productId)
{
//alert("vishwas");
	var recoPeriodId = document.getElementById("recoPeriodId").value;
	var stbType = document.getElementById("stbTypeId").value;
	var fileTypeAlert = "Only Excel file can be uploaded.";
	
		if(productId == '-1')
		{
		alert("Please Select product");
		return false;
		}
		if(columnId == '-1') 
		{
		alert("Please Select stock type");
		return false;
		}
	if(document.getElementById("uploadedFile").value == "")
	{
		alert("Please Select File");
		return false;
	}
    else if(!(/^.*\.xls$/.test(document.getElementById("uploadedFile").value)) && !(/^.*\.xlsx$/.test(document.getElementById("uploadedFile").value)) )
     {
		alert(fileTypeAlert);
		return false;
	}
	else if(navigator.userAgent.indexOf("MSIE") != -1) 
	{
		if(document.getElementById("uploadedFile").value.indexOf(":\\") == -1 )
		 {
			alert("Please Select a proper file.");
			return false;
		}
	}
	
	/***************changing by Beeru 15 Apr 2014********************/
	var productsLists = document.getElementById("productsLists");
	var stockType = document.getElementById("stockType");
	var productsListsText = productsLists.options[productsLists.options.selectedIndex].text;
	var stockTypeText = stockType.options[stockType.options.selectedIndex].text;
	/***********************************/
	
	var url= "DistReco.do?methodName=uploadClosingStockDetailsXlsagain&productId="+productId+"&tabId="+tabId+"&columnId="+columnId+"&recoPeriodId="+recoPeriodId+"&stbType="+stbType+"&browseButtonName="+browseButtonName+"&productsListsText="+productsListsText+"&stockTypeText="+stockTypeText;
	 document.forms[0].action=url;
	document.forms[0].method="post";
	document.forms[0].submit()
}
//added by vishwas on sunday
function uploadExcelcheck(tabId,columnId,browseButtonName,productId)
{
	var recoPeriodId = document.getElementById("recoPeriodId").value;
	var stbType = document.getElementById("stbTypeId").value;

	if(window.XmlHttpRequest) {
		req = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		req=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if(req==null) { 
		alert("Browser Doesnt Support AJAX");
		return;
	}
	req.onreadystatechange = function(){
	  updateCount(tabId,columnId,browseButtonName,productId);

	}

		var url= "DistReco.do?methodName=uploadClosingStockDetailsXlscheck&productId="+productId+"&tabId="+tabId+"&columnId="+columnId+"&recoPeriodId="+recoPeriodId+"&stbType="+stbType;
				
	req.open("POST",url,false);
	req.send();
}
function updateCount(tabId,columnId,browseButtonName,productId)
{
	if (req.readyState==4 || req.readyState=="complete") 
	{
		var msg=req.responseText;	
		
		if(msg!="empty")
		{
		/***************changing by RAM 9 Apr 2014********************/
	var productsLists = document.getElementById("productsLists");
	var stockType = document.getElementById("stockType");
	var productsListsText = productsLists.options[productsLists.options.selectedIndex].text;
	var stockTypeText = stockType.options[stockType.options.selectedIndex].text;
	/***********************************/
		var result=confirm("You have already uploaded/confirmed "+stockTypeText +" for "+productsListsText+",Are you sure to update the records.");
		if(result==true)
		{
		uploadExceldataremoval(tabId,columnId,browseButtonName,productId);
		}
		else
		{
		return false;
		}
		}
		else
		{
		
		uploadExcel(tabId,columnId,browseButtonName,productId);
		}
			
	}

}
function showSwap() {
	var productId = document.getElementById("productId").value;
	var swapproductId = document.getElementById("swapproductId").value;
	
	if(swapproductId.indexOf(productId) !="-1") {
		//document.getElementById("showswapId").style.display = 'none';
		//document.getElementById("hideswapId").style.display = 'block';
		document.getElementById("showswapId").innerHTML='<select id="stockType" onchange="disableUpload()">'+
		'<option value="-1">--select--</option><option value="1">Serialized Stock</option><option value="2">Defective Stock</option><option value="3">Upgrade Stock</option><option value="4">Churned Stock</option></select>';
	
	}else {
		document.getElementById("showswapId").innerHTML ='<select id="stockType" onchange="disableUpload()" >'+
		'<option value="-1">--select--</option>'+
		'<option value="1">Serialized Stock</option>'+
		'<option value="2">Defective Stock</option></select>';
		//document.getElementById("showswapId").style.display = 'block';
		//document.getElementById("hideswapId").style.display = 'none';
	}
}


/*********************************/