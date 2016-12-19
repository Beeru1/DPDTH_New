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

function getTotOpen(rowNum){
	var partnerRowOpen = document.getElementById("tableOpen").rows[rowNum].cells;
	var partnerRowRcvd = document.getElementById("tableRcvd").rows[rowNum].cells;
	var partnerRowRet = document.getElementById("tableRet").rows[rowNum].cells;
	var partnerRowAct = document.getElementById("tableActvn").rows[rowNum].cells;
	var partnerRowClosng = document.getElementById("tableClosing").rows[rowNum].cells;
	var partnerRowSummry = document.getElementById("tableSummry").rows[rowNum].cells;
	
	var sysRowOpen = document.getElementById("tableOpen").rows[parseInt(rowNum)-1].cells;
	var sysRowRcvd = document.getElementById("tableRcvd").rows[parseInt(rowNum)-1].cells;
	var sysRowRet = document.getElementById("tableRet").rows[parseInt(rowNum)-1].cells;
	var sysRowAct = document.getElementById("tableActvn").rows[parseInt(rowNum)-1].cells;
	var sysRowClosng = document.getElementById("tableClosing").rows[parseInt(rowNum)-1].cells;
	var sysRowSummry = document.getElementById("tableSummry").rows[parseInt(rowNum)-1].cells;
	
	var varRowOpen = document.getElementById("tableOpen").rows[parseInt(rowNum)+1].cells;
	var varRowRcvd = document.getElementById("tableRcvd").rows[parseInt(rowNum)+1].cells;
	var varRowRet = document.getElementById("tableRet").rows[parseInt(rowNum)+1].cells;
	var varRowAct = document.getElementById("tableActvn").rows[parseInt(rowNum)+1].cells;
	var varRowClosng = document.getElementById("tableClosing").rows[parseInt(rowNum)+1].cells;
	var varRowSummry = document.getElementById("tableSummry").rows[parseInt(rowNum)+1].cells;
	
	var totalOpenPart = 0;
	var totalOpenSys = 0;	
	var totalOpenVar = 0;
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
	
		/*  // Pending po and pending dc exluded in closing stock as it should not be considerd
			// pending dc included in closing stock as it should be considerd
		if(chckBlank(pendingPOOpening) ==true  && !(partnerRowOpen[2].getElementsByTagName("input")[0].disabled)){
			totalOpenPart = parseInt(totalOpenPart) + parseInt(pendingPOOpening);
		}
		*/
		if(chckBlank(pendingDCOpen) ==true  && !(partnerRowOpen[3].getElementsByTagName("input")[0].disabled)){
			totalOpenPart = parseInt(totalOpenPart) + parseInt(pendingDCOpen);
		}
		if(chckBlank(serOpen) ==true  && !(partnerRowOpen[4].getElementsByTagName("input")[0].disabled)){
			totalOpenPart = parseInt(totalOpenPart) + parseInt(serOpen);
		}
		if(chckBlank(nsrOpen) ==true  && !(partnerRowOpen[5].getElementsByTagName("input")[0].disabled)){
			totalOpenPart = parseInt(totalOpenPart) + parseInt(nsrOpen);
		}
		if(chckBlank(defOpen) ==true  && !(partnerRowOpen[6].getElementsByTagName("input")[0].disabled)){
			totalOpenPart = parseInt(totalOpenPart) + parseInt(defOpen);
		}
		if(chckBlank(upgaradeOpen) ==true  && !(partnerRowOpen[7].getElementsByTagName("input")[0].disabled)){
			totalOpenPart = parseInt(totalOpenPart) + parseInt(upgaradeOpen);
		}
		if(chckBlank(churnOpen) ==true  && !(partnerRowOpen[8].getElementsByTagName("input")[0].disabled)){
			totalOpenPart = parseInt(totalOpenPart) + parseInt(churnOpen);
		}
		
		if(document.all){
		 	totalOpenSys = sysRowRcvd[2].getElementsByTagName("div")[0].value ;
		} else {
	    	totalOpenSys = sysRowRcvd[2].getElementsByTagName("div")[0].value.textContent;
		}
		
		totalOpenVar = parseInt(totalOpenPart) - parseInt(totalOpenSys) ;
		
		
		partnerRowRcvd[2].innerHTML = "<div value=\"" + totalOpenPart +  "\">" + totalOpenPart + "</div>";
		varRowRcvd[2].innerHTML = "<div value=\"" + totalOpenVar +  "\">" + totalOpenVar + "</div>";
		//varRowRcvd[2].innerHTML = "<input type=\"textfield\" id=\"totalOpenVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalOpenVar + "\"  />";
		
		partnerRowRet[2].innerHTML = "<div value=\"" + totalOpenPart +  "\">" + totalOpenPart + "</div>";
		varRowRet[2].innerHTML = "<div value=\"" + totalOpenVar +  "\">" + totalOpenVar + "</div>";
		//varRowRet[2].innerHTML = "<input type=\"textfield\" id=\"totalOpenVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalOpenVar + "\"  />";
		
		partnerRowAct[2].innerHTML = "<div value=\"" + totalOpenPart +  "\">" + totalOpenPart + "</div>";
		varRowAct[2].innerHTML = "<div value=\"" + totalOpenVar +  "\">" + totalOpenVar + "</div>";
		//varRowAct[2].innerHTML = "<input type=\"textfield\" id=\"totalOpenVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalOpenVar + "\"  />";
		
		partnerRowClosng[2].innerHTML = "<div value=\"" + totalOpenPart +  "\">" + totalOpenPart + "</div>";
		varRowClosng[2].innerHTML = "<div value=\"" + totalOpenVar +  "\">" + totalOpenVar + "</div>";
		//varRowClosng[2].innerHTML = "<input type=\"textfield\" id=\"totalOpenVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalOpenVar + "\"  />";
		
		partnerRowSummry[2].innerHTML = "<div value=\"" + totalOpenPart +  "\">" + totalOpenPart + "</div>";
		varRowSummry[2].innerHTML = "<div value=\"" + totalOpenVar +  "\">" + totalOpenVar + "</div>";
		//varRowSummry[2].innerHTML = "<input type=\"textfield\" id=\"totalOpenVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalOpenVar + "\"  />";
		
	
}


function getTotRec(rowNum){
	var partnerRowOpen = document.getElementById("tableOpen").rows[rowNum].cells;
	var partnerRowRcvd = document.getElementById("tableRcvd").rows[rowNum].cells;
	var partnerRowRet = document.getElementById("tableRet").rows[rowNum].cells;
	var partnerRowAct = document.getElementById("tableActvn").rows[rowNum].cells;
	var partnerRowClosng = document.getElementById("tableClosing").rows[rowNum].cells;
	var partnerRowSummry = document.getElementById("tableSummry").rows[rowNum].cells;
	
	var sysRowOpen = document.getElementById("tableOpen").rows[parseInt(rowNum)-1].cells;
	var sysRowRcvd = document.getElementById("tableRcvd").rows[parseInt(rowNum)-1].cells;
	var sysRowRet = document.getElementById("tableRet").rows[parseInt(rowNum)-1].cells;
	var sysRowAct = document.getElementById("tableActvn").rows[parseInt(rowNum)-1].cells;
	var sysRowClosng = document.getElementById("tableClosing").rows[parseInt(rowNum)-1].cells;
	var sysRowSummry = document.getElementById("tableSummry").rows[parseInt(rowNum)-1].cells;
	
	var varRowOpen = document.getElementById("tableOpen").rows[parseInt(rowNum)+1].cells;
	var varRowRcvd = document.getElementById("tableRcvd").rows[parseInt(rowNum)+1].cells;
	var varRowRet = document.getElementById("tableRet").rows[parseInt(rowNum)+1].cells;
	var varRowAct = document.getElementById("tableActvn").rows[parseInt(rowNum)+1].cells;
	var varRowClosng = document.getElementById("tableClosing").rows[parseInt(rowNum)+1].cells;
	var varRowSummry = document.getElementById("tableSummry").rows[parseInt(rowNum)+1].cells;
	
	var totalRecPart = 0;
	var totalRecSys = 0;	
	var totalRecVar = 0;
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
		
		if(chckBlank(rcvdWH) ==true  && !(partnerRowRcvd[3].getElementsByTagName("input")[0].disabled)){
			totalRecPart = parseInt(totalRecPart) + parseInt(rcvdWH);
		}
		if(chckBlank(recInterSSdOK) ==true  && !(partnerRowRcvd[4].getElementsByTagName("input")[0].disabled)){
			totalRecPart = parseInt(totalRecPart) + parseInt(recInterSSdOK);
		}
		if(chckBlank(recInterSSDDef) ==true  && !(partnerRowRcvd[5].getElementsByTagName("input")[0].disabled)){
			totalRecPart = parseInt(totalRecPart) + parseInt(recInterSSDDef);
		}
		if(chckBlank(recUpgrade) ==true  && !(partnerRowRcvd[6].getElementsByTagName("input")[0].disabled)){
			totalRecPart = parseInt(totalRecPart) + parseInt(recUpgrade);
		}
		if(chckBlank(recChurn) ==true  && !(partnerRowRcvd[7].getElementsByTagName("input")[0].disabled)){
			totalRecPart = parseInt(totalRecPart) + parseInt(recChurn);
		}
		
		
		if(document.all){
		 	totalRecSys = sysRowOpen[9].getElementsByTagName("div")[0].value ;
		} else {
	    	totalRecSys = sysRowOpen[9].getElementsByTagName("div")[0].value.textContent;
		}
		
		totalRecVar =  parseInt(totalRecPart) - parseInt(totalRecSys);
		
		
		partnerRowOpen[9].innerHTML = "<div value=\"" + totalRecPart +  "\">" + totalRecPart + "</div>";
		varRowOpen[9].innerHTML = "<div value=\"" + totalRecVar +  "\">" + totalRecVar + "</div>";
		//varRowOpen[9].innerHTML = "<input type=\"textfield\" id=\"totalRecVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalRecVar + "\"  />";
		
		partnerRowRet[3].innerHTML = "<div value=\"" + totalRecPart +  "\">" + totalRecPart + "</div>";
		varRowRet[3].innerHTML = "<div value=\"" + totalRecVar +  "\">" + totalRecVar + "</div>";
		//varRowRet[3].innerHTML = "<input type=\"textfield\" id=\"totalRecVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalRecVar + "\"  />";
		
		partnerRowAct[3].innerHTML = "<div value=\"" + totalRecPart +  "\">" + totalRecPart + "</div>";
		varRowAct[3].innerHTML = "<div value=\"" + totalRecVar +  "\">" + totalRecVar + "</div>";
		//varRowAct[3].innerHTML = "<input type=\"textfield\" id=\"totalRecVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalRecVar + "\"  />";
		
		partnerRowClosng[3].innerHTML = "<div value=\"" + totalRecPart +  "\">" + totalRecPart + "</div>";
		varRowClosng[3].innerHTML = "<div value=\"" + totalRecVar +  "\">" + totalRecVar + "</div>";
		//varRowClosng[3].innerHTML = "<input type=\"textfield\" id=\"totalRecVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalRecVar + "\"  />";
		
		partnerRowSummry[3].innerHTML = "<div value=\"" + totalRecPart +  "\">" + totalRecPart + "</div>";
		varRowSummry[3].innerHTML = "<div value=\"" + totalRecVar +  "\">" + totalRecVar + "</div>";
		//varRowSummry[3].innerHTML = "<input type=\"textfield\" id=\"totalRecVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalRecVar + "\"  />";
		
	
}

function getTotRet(rowNum){
	var partnerRowOpen = document.getElementById("tableOpen").rows[rowNum].cells;
	var partnerRowRcvd = document.getElementById("tableRcvd").rows[rowNum].cells;
	var partnerRowRet = document.getElementById("tableRet").rows[rowNum].cells;
	var partnerRowAct = document.getElementById("tableActvn").rows[rowNum].cells;
	var partnerRowClosng = document.getElementById("tableClosing").rows[rowNum].cells;
	var partnerRowSummry = document.getElementById("tableSummry").rows[rowNum].cells;
	
	var sysRowOpen = document.getElementById("tableOpen").rows[parseInt(rowNum)-1].cells;
	var sysRowRcvd = document.getElementById("tableRcvd").rows[parseInt(rowNum)-1].cells;
	var sysRowRet = document.getElementById("tableRet").rows[parseInt(rowNum)-1].cells;
	var sysRowAct = document.getElementById("tableActvn").rows[parseInt(rowNum)-1].cells;
	var sysRowClosng = document.getElementById("tableClosing").rows[parseInt(rowNum)-1].cells;
	var sysRowSummry = document.getElementById("tableSummry").rows[parseInt(rowNum)-1].cells;
	
	var varRowOpen = document.getElementById("tableOpen").rows[parseInt(rowNum)+1].cells;
	var varRowRcvd = document.getElementById("tableRcvd").rows[parseInt(rowNum)+1].cells;
	var varRowRet = document.getElementById("tableRet").rows[parseInt(rowNum)+1].cells;
	var varRowAct = document.getElementById("tableActvn").rows[parseInt(rowNum)+1].cells;
	var varRowClosng = document.getElementById("tableClosing").rows[parseInt(rowNum)+1].cells;
	var varRowSummry = document.getElementById("tableSummry").rows[parseInt(rowNum)+1].cells;
	
	var totalRetPart = 0;
	var totalRetSys = 0;	
	var totalRetVar = 0;
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
		
		if(chckBlank(returnedOK) ==true  && !(partnerRowRet[4].getElementsByTagName("input")[0].disabled)){
			totalRetPart = parseInt(totalRetPart) + parseInt(returnedOK);
		}
		if(chckBlank(retInterssdOk) ==true  && !(partnerRowRet[5].getElementsByTagName("input")[0].disabled)){
			totalRetPart = parseInt(totalRetPart) + parseInt(retInterssdOk);
		}
		if(chckBlank(retInterssdDef) ==true  && !(partnerRowRet[6].getElementsByTagName("input")[0].disabled)){
			totalRetPart = parseInt(totalRetPart) + parseInt(retInterssdDef);
		}
		if(chckBlank(retDoaBI) ==true  && !(partnerRowRet[7].getElementsByTagName("input")[0].disabled)){
			totalRetPart = parseInt(totalRetPart) + parseInt(retDoaBI);
		}
		if(chckBlank(retDoaAi) ==true  && !(partnerRowRet[8].getElementsByTagName("input")[0].disabled)){
			totalRetPart = parseInt(totalRetPart) + parseInt(retDoaAi);
		}
		if(chckBlank(retC2s) ==true  && !(partnerRowRet[9].getElementsByTagName("input")[0].disabled)){
			totalRetPart = parseInt(totalRetPart) + parseInt(retC2s);
		}
		if(chckBlank(retChurn) ==true  && !(partnerRowRet[10].getElementsByTagName("input")[0].disabled)){
			totalRetPart = parseInt(totalRetPart) + parseInt(retChurn);
		}
		if(chckBlank(retDefSwap) ==true  && !(partnerRowRet[11].getElementsByTagName("input")[0].disabled)){
			totalRetPart = parseInt(totalRetPart) + parseInt(retDefSwap);
		}
		if(document.all){
		 	totalRetSys = sysRowOpen[10].getElementsByTagName("div")[0].value ;
		} else {
	    	totalRetSys = sysRowOpen[10].getElementsByTagName("div")[0].value.textContent;
		}
		
		totalRetVar =  parseInt(totalRetPart) - parseInt(totalRetSys);
		
		partnerRowOpen[10].innerHTML = "<div value=\"" + totalRetPart +  "\">" + totalRetPart + "</div>";
		varRowOpen[10].innerHTML = "<div value=\"" + totalRetVar +  "\">" + totalRetVar + "</div>";
		//varRowOpen[10].innerHTML = "<input type=\"textfield\" id=\"totalRetVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalRetVar + "\"  />";
		
		partnerRowRcvd[8].innerHTML = "<div value=\"" + totalRetPart +  "\">" + totalRetPart + "</div>";
		varRowRcvd[8].innerHTML = "<div value=\"" + totalRetVar +  "\">" + totalRetVar + "</div>";
		//varRowRcvd[8].innerHTML = "<input type=\"textfield\" id=\"totalRetVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalRetVar + "\"  />";
		
		partnerRowAct[4].innerHTML = "<div value=\"" + totalRetPart +  "\">" + totalRetPart + "</div>";
		varRowAct[4].innerHTML = "<div value=\"" + totalRetVar +  "\">" + totalRetVar + "</div>";
		//varRowAct[4].innerHTML = "<input type=\"textfield\" id=\"totalRetVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalRetVar + "\"  />";
		
		partnerRowClosng[4].innerHTML = "<div value=\"" + totalRetPart +  "\">" + totalRetPart + "</div>";
		varRowClosng[4].innerHTML = "<div value=\"" + totalRetVar +  "\">" + totalRetVar + "</div>";
		//varRowClosng[4].innerHTML = "<input type=\"textfield\" id=\"totalRetVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalRetVar + "\"  />";
		
		partnerRowSummry[4].innerHTML = "<div value=\"" + totalRetPart +  "\">" + totalRetPart + "</div>";
		varRowSummry[4].innerHTML = "<div value=\"" + totalRetVar +  "\">" + totalRetVar + "</div>";
		//varRowSummry[4].innerHTML = "<input type=\"textfield\" id=\"totalRetVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalRetVar + "\"  />";
		
	
}

function getTotAct(rowNum){
	var partnerRowOpen = document.getElementById("tableOpen").rows[rowNum].cells;
	var partnerRowRcvd = document.getElementById("tableRcvd").rows[rowNum].cells;
	var partnerRowRet = document.getElementById("tableRet").rows[rowNum].cells;
	var partnerRowAct = document.getElementById("tableActvn").rows[rowNum].cells;
	var partnerRowClosng = document.getElementById("tableClosing").rows[rowNum].cells;
	var partnerRowSummry = document.getElementById("tableSummry").rows[rowNum].cells;
	
	var sysRowOpen = document.getElementById("tableOpen").rows[parseInt(rowNum)-1].cells;
	var sysRowRcvd = document.getElementById("tableRcvd").rows[parseInt(rowNum)-1].cells;
	var sysRowRet = document.getElementById("tableRet").rows[parseInt(rowNum)-1].cells;
	var sysRowAct = document.getElementById("tableActvn").rows[parseInt(rowNum)-1].cells;
	var sysRowClosng = document.getElementById("tableClosing").rows[parseInt(rowNum)-1].cells;
	var sysRowSummry = document.getElementById("tableSummry").rows[parseInt(rowNum)-1].cells;
	
	var varRowOpen = document.getElementById("tableOpen").rows[parseInt(rowNum)+1].cells;
	var varRowRcvd = document.getElementById("tableRcvd").rows[parseInt(rowNum)+1].cells;
	var varRowRet = document.getElementById("tableRet").rows[parseInt(rowNum)+1].cells;
	var varRowAct = document.getElementById("tableActvn").rows[parseInt(rowNum)+1].cells;
	var varRowClosng = document.getElementById("tableClosing").rows[parseInt(rowNum)+1].cells;
	var varRowSummry = document.getElementById("tableSummry").rows[parseInt(rowNum)+1].cells;
	
	var totalActPart = 0;
	var totalActSys = 0;	
	var totalActVar = 0;
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
		if(chckBlank(activation) ==true  && !(partnerRowAct[5].getElementsByTagName("input")[0].disabled)){
			totalActPart = parseInt(totalActPart) + parseInt(activation);
		}
		if(document.all){
		 	totalActSys = sysRowAct[5].getElementsByTagName("div")[0].value ;
		} else {
	    	totalActSys = sysRowAct[5].getElementsByTagName("div")[0].value.textContent;
		}
		
		totalActVar =  parseInt(totalActPart) - parseInt(totalActSys) ;
		
		partnerRowOpen[11].innerHTML = "<div value=\"" + totalActPart +  "\">" + totalActPart + "</div>";
		varRowOpen[11].innerHTML = "<div value=\"" + totalActVar +  "\">" + totalActVar + "</div>";
		//varRowOpen[11].innerHTML = "<input type=\"textfield\" id=\"totalActVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalActVar + "\"  />";
		
		partnerRowRcvd[9].innerHTML = "<div value=\"" + totalActPart +  "\">" + totalActPart + "</div>";
		varRowRcvd[9].innerHTML = "<div value=\"" + totalActVar +  "\">" + totalActVar + "</div>";
		//varRowRcvd[9].innerHTML = "<input type=\"textfield\" id=\"totalActVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalActVar + "\"  />";
		
		partnerRowRet[12].innerHTML = "<div value=\"" + totalActPart +  "\">" + totalActPart + "</div>";
		varRowRet[12].innerHTML = "<div value=\"" + totalActVar +  "\">" + totalActVar + "</div>";
		//varRowRet[12].innerHTML = "<input type=\"textfield\" id=\"totalActVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalActVar + "\"  />";
		
		partnerRowClosng[5].innerHTML = "<div value=\"" + totalActPart +  "\">" + totalActPart + "</div>";
		varRowClosng[5].innerHTML = "<div value=\"" + totalActVar +  "\">" + totalActVar + "</div>";
		//varRowClosng[5].innerHTML = "<input type=\"textfield\" id=\"totalActVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalActVar + "\"  />";
		
		partnerRowSummry[5].innerHTML = "<div value=\"" + totalActPart +  "\">" + totalActPart + "</div>";
		varRowSummry[5].innerHTML = "<div value=\"" + totalActVar +  "\">" + totalActVar + "</div>";
		//varRowSummry[5].innerHTML = "<input type=\"textfield\" id=\"totalActVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalActVar + "\"  />";
	
}

function getTotInvChng(rowNum){
	var partnerRowOpen = document.getElementById("tableOpen").rows[rowNum].cells;
	var partnerRowRcvd = document.getElementById("tableRcvd").rows[rowNum].cells;
	var partnerRowRet = document.getElementById("tableRet").rows[rowNum].cells;
	var partnerRowAct = document.getElementById("tableActvn").rows[rowNum].cells;
	var partnerRowClosng = document.getElementById("tableClosing").rows[rowNum].cells;
	var partnerRowSummry = document.getElementById("tableSummry").rows[rowNum].cells;
	
	var sysRowOpen = document.getElementById("tableOpen").rows[parseInt(rowNum)-1].cells;
	var sysRowRcvd = document.getElementById("tableRcvd").rows[parseInt(rowNum)-1].cells;
	var sysRowRet = document.getElementById("tableRet").rows[parseInt(rowNum)-1].cells;
	var sysRowAct = document.getElementById("tableActvn").rows[parseInt(rowNum)-1].cells;
	var sysRowClosng = document.getElementById("tableClosing").rows[parseInt(rowNum)-1].cells;
	var sysRowSummry = document.getElementById("tableSummry").rows[parseInt(rowNum)-1].cells;
	
	var varRowOpen = document.getElementById("tableOpen").rows[parseInt(rowNum)+1].cells;
	var varRowRcvd = document.getElementById("tableRcvd").rows[parseInt(rowNum)+1].cells;
	var varRowRet = document.getElementById("tableRet").rows[parseInt(rowNum)+1].cells;
	var varRowAct = document.getElementById("tableActvn").rows[parseInt(rowNum)+1].cells;
	var varRowClosng = document.getElementById("tableClosing").rows[parseInt(rowNum)+1].cells;
	var varRowSummry = document.getElementById("tableSummry").rows[parseInt(rowNum)+1].cells;
	
	var totalInvPart = 0;
	var totalInvSys = 0;	
	var totalInvVar = 0;
		
		if(document.all){
     		if(typeof(partnerRowAct[6].getElementsByTagName("input")[0])!="undefined")
     			var invChange = partnerRowAct[6].getElementsByTagName("input")[0].value;
     		else
     			var invChange = partnerRowAct[6].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRowAct[6].getElementsByTagName("input")[0])!="undefined")
     			var invChange = partnerRowAct[6].getElementsByTagName("input")[0].value.textContent;
     		else
     		var invChange = partnerRowAct[6].getElementsByTagName("div")[0].value.textContent;
		}
		
		if(chckBlank(invChange) ==true  && !(partnerRowAct[6].getElementsByTagName("input")[0].disabled)){
			totalInvPart = parseInt(totalInvPart) + parseInt(invChange);
		}
		if(document.all){
		 	totalInvSys = sysRowAct[6].getElementsByTagName("div")[0].value ;
		} else {
	    	totalInvSys = sysRowAct[6].getElementsByTagName("div")[0].value.textContent;
		}
			
		totalInvVar =  parseInt(totalInvPart) - parseInt(totalInvSys);
		
		partnerRowOpen[12].innerHTML = "<div value=\"" + totalInvPart +  "\">" + totalInvPart + "</div>";
		varRowOpen[12].innerHTML = "<div value=\"" + totalInvVar +  "\">" + totalInvVar + "</div>";
		//varRowOpen[12].innerHTML = "<input type=\"textfield\" id=\"totalInvVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalInvVar + "\"  />";
		
		partnerRowRcvd[10].innerHTML = "<div value=\"" + totalInvPart +  "\">" + totalInvPart + "</div>";
		varRowRcvd[10].innerHTML = "<div value=\"" + totalInvVar +  "\">" + totalInvVar + "</div>";
		//varRowRcvd[10].innerHTML = "<input type=\"textfield\" id=\"totalInvVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalInvVar + "\"  />";
		
		partnerRowRet[13].innerHTML = "<div value=\"" + totalInvPart +  "\">" + totalInvPart + "</div>";
		varRowRet[13].innerHTML = "<div value=\"" + totalInvVar +  "\">" + totalInvVar + "</div>";
		//varRowRet[13].innerHTML = "<input type=\"textfield\" id=\"totalInvVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalInvVar + "\"  />";
		
		partnerRowClosng[6].innerHTML = "<div value=\"" + totalInvPart +  "\">" + totalInvPart + "</div>";
		varRowClosng[6].innerHTML = "<div value=\"" + totalInvVar +  "\">" + totalInvVar + "</div>";
		//varRowClosng[6].innerHTML = "<input type=\"textfield\" id=\"totalInvVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalInvVar + "\"  />";
		
		partnerRowSummry[6].innerHTML = "<div value=\"" + totalInvPart +  "\">" + totalInvPart + "</div>";
		varRowSummry[6].innerHTML = "<div value=\"" + totalInvVar +  "\">" + totalInvVar + "</div>";
		//varRowSummry[6].innerHTML = "<input type=\"textfield\" id=\"totalInvVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalInvVar + "\"  />";
	
}

function getTotClos(rowNum){
	var partnerRowOpen = document.getElementById("tableOpen").rows[rowNum].cells;
	var partnerRowRcvd = document.getElementById("tableRcvd").rows[rowNum].cells;
	var partnerRowRet = document.getElementById("tableRet").rows[rowNum].cells;
	var partnerRowAct = document.getElementById("tableActvn").rows[rowNum].cells;
	var partnerRowClosng = document.getElementById("tableClosing").rows[rowNum].cells;
	var partnerRowSummry = document.getElementById("tableSummry").rows[rowNum].cells;
	
	var sysRowOpen = document.getElementById("tableOpen").rows[parseInt(rowNum)-1].cells;
	var sysRowRcvd = document.getElementById("tableRcvd").rows[parseInt(rowNum)-1].cells;
	var sysRowRet = document.getElementById("tableRet").rows[parseInt(rowNum)-1].cells;
	var sysRowAct = document.getElementById("tableActvn").rows[parseInt(rowNum)-1].cells;
	var sysRowClosng = document.getElementById("tableClosing").rows[parseInt(rowNum)-1].cells;
	var sysRowSummry = document.getElementById("tableSummry").rows[parseInt(rowNum)-1].cells;
	
	var varRowOpen = document.getElementById("tableOpen").rows[parseInt(rowNum)+1].cells;
	var varRowRcvd = document.getElementById("tableRcvd").rows[parseInt(rowNum)+1].cells;
	var varRowRet = document.getElementById("tableRet").rows[parseInt(rowNum)+1].cells;
	var varRowAct = document.getElementById("tableActvn").rows[parseInt(rowNum)+1].cells;
	var varRowClosng = document.getElementById("tableClosing").rows[parseInt(rowNum)+1].cells;
	var varRowSummry = document.getElementById("tableSummry").rows[parseInt(rowNum)+1].cells;
	
	var totalClsPart = 0;
	var totalClsSys = 0;	
	var totalClsVar = 0;
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
		
		if(chckBlank(serClosing) ==true  && !(partnerRowClosng[8].getElementsByTagName("input")[0].disabled)){
			totalClsPart = parseInt(totalClsPart) + parseInt(serClosing);
		}
		if(chckBlank(nserClosing) ==true  && !(partnerRowClosng[9].getElementsByTagName("input")[0].disabled)){
			totalClsPart = parseInt(totalClsPart) + parseInt(nserClosing);
		}
		if(chckBlank(defClosing) ==true  && !(partnerRowClosng[10].getElementsByTagName("input")[0].disabled)){
			totalClsPart = parseInt(totalClsPart) + parseInt(defClosing);
		}
		if(chckBlank(upgradeClosing) ==true  && !(partnerRowClosng[11].getElementsByTagName("input")[0].disabled)){
			totalClsPart = parseInt(totalClsPart) + parseInt(upgradeClosing);
		}
		if(chckBlank(churnClosing) ==true  && !(partnerRowClosng[12].getElementsByTagName("input")[0].disabled)){
			totalClsPart = parseInt(totalClsPart) + parseInt(churnClosing);
		}
		/*// Pending po and pending dc exluded in closing stock as it should not be considerd
		if(chckBlank(penPOClosing) ==true  && !(partnerRowClosng[13].getElementsByTagName("input")[0].disabled)){
			totalClsPart = parseInt(totalClsPart) + parseInt(penPOClosing);
		}*/
		if(chckBlank(penDCClosing) ==true  && !(partnerRowClosng[14].getElementsByTagName("input")[0].disabled)){
			totalClsPart = parseInt(totalClsPart) + parseInt(penDCClosing); //corrected by niki
		}
		
		//alert("totalClsPart"+totalClsPart);
		if(document.all){
		 	totalClsSys = sysRowOpen[14].getElementsByTagName("div")[0].value ;
		} else {
	    	totalClsSys = sysRowOpen[14].getElementsByTagName("div")[0].value.textContent;
		}
		
		totalClsVar =  parseInt(totalClsPart) - parseInt(totalClsSys);
		
		partnerRowOpen[14].innerHTML = "<div value=\"" + totalClsPart +  "\">" + totalClsPart + "</div>";
		varRowOpen[14].innerHTML = "<div value=\"" + totalClsVar +  "\">" + totalClsVar + "</div>";
		//varRowOpen[14].innerHTML = "<input type=\"textfield\" id=\"totalClsVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalClsVar + "\"  />";
		
		partnerRowRcvd[12].innerHTML = "<div value=\"" + totalClsPart +  "\">" + totalClsPart + "</div>";
		varRowRcvd[12].innerHTML = "<div value=\"" + totalClsVar +  "\">" + totalClsVar + "</div>";
		//varRowRcvd[12].innerHTML = "<input type=\"textfield\" id=\"totalClsVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalClsVar + "\"  />";
		
		partnerRowRet[15].innerHTML = "<div value=\"" + totalClsPart +  "\">" + totalClsPart + "</div>";
		varRowRet[15].innerHTML = "<div value=\"" + totalClsVar +  "\">" + totalClsVar + "</div>";
		//varRowRet[15].innerHTML = "<input type=\"textfield\" id=\"totalClsVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalClsVar + "\"  />";
		
		partnerRowAct[8].innerHTML = "<div value=\"" + totalClsPart +  "\">" + totalClsPart + "</div>";
		varRowAct[8].innerHTML = "<div value=\"" + totalClsVar +  "\">" + totalClsVar + "</div>";
		//varRowAct[8].innerHTML = "<input type=\"textfield\" id=\"totalClsVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalClsVar + "\"  />";
		
		partnerRowSummry[8].innerHTML = "<div value=\"" + totalClsPart +  "\">" + totalClsPart + "</div>";
		varRowSummry[8].innerHTML = "<div value=\"" + totalClsVar +  "\">" + totalClsVar + "</div>";
		//varRowSummry[8].innerHTML = "<input type=\"textfield\" id=\"totalClsVar\" style=\"width: 50px\" readonly=\"true\" value=\"" + totalClsVar + "\"  />";
		
	
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