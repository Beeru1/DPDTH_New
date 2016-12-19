function daysBetween(date1, date2) 
{
	    var DSTAdjust = 0;
	    // constants used for our calculations below
	    oneMinute = 1000 * 60;
	    var oneDay = oneMinute * 60 * 24;
	    // equalize times in case date objects have them
	    date1.setHours(0);
	    date1.setMinutes(0);
	    date1.setSeconds(0);
	    date2.setHours(0);
	    date2.setMinutes(0);
	    date2.setSeconds(0);
	    // take care of spans across Daylight Saving Time changes
	    if (date2 > date1) {
	        DSTAdjust = (date2.getTimezoneOffset() - date1.getTimezoneOffset()) * oneMinute;
	    } else {
	        DSTAdjust = (date1.getTimezoneOffset() - date2.getTimezoneOffset()) * oneMinute;    
	    }
	    var diff = Math.abs(date2.getTime() - date1.getTime()) - DSTAdjust;
	    return Math.ceil(diff/oneDay);
}
	
	
	
function enableDate(){
var msgValidation= document.getElementById("msgValidation").value;
		
	if(msgValidation=="-1")
	   {
	   document.getElementById("displaySchTitle").style.display = 'block';
	    	
	  }
		if(typeof(document.forms[0].dateOption)!="undefined")
		{var dateOption =document.getElementById("dateOption").value;
	
			if(dateOption == "-1"){
				document.forms[0].fromDate.disabled =true;
				document.forms[0].toDate.disabled =true;
				document.getElementById("fromDate").value ="";
				document.getElementById("toDate").value ="";
				
				document.getElementById("tcalico_1").style.display = 'none';
				document.getElementById("tcalico_0").style.display = 'none';
				document.getElementById("req").style.display = 'none';
				document.getElementById("req1").style.display = 'none';
				
				
				
			}else{
				document.forms[0].fromDate.disabled =false;
				document.forms[0].toDate.disabled =false;
				
				document.getElementById("tcalico_1").style.display = 'inline';
				document.getElementById("tcalico_0").style.display = 'inline';
				document.getElementById("req").style.display = 'inline';
				document.getElementById("req1").style.display = 'inline';
				
			}
			}
			
			if(typeof(document.forms[0].searchOption)!="undefined"){
			var searchOption =document.getElementById("searchOption").value;
			if(searchOption == "-1"){
				document.forms[0].searchCriteria.disabled =true;
				document.getElementById("searchCriteria").value ="";
				document.getElementById("reqsearch").style.display = 'none';	
			}
			else{
				document.forms[0].searchCriteria.disabled =false;
				document.getElementById("reqsearch").style.display = 'inline';
				
			}
			
			}
}

function enableActivity()

{
	document.forms[0].fromDate.disabled =false;
	document.forms[0].toDate.disabled =false;
	
	document.getElementById("tcalico_1").style.display = 'inline';
	document.getElementById("tcalico_0").style.display = 'inline';
}

function trimAll(sString) {
	while (sString.substring(0,1) == ' '){
	sString = sString.substring(1, sString.length);
	}		
	while (sString.substring(sString.length-1, sString.length) == ' '){
	sString = sString.substring(0,sString.length-1);	
	}
	return sString;
}
function showHideMandatory() {
	var dcNo = document.getElementById("dcNo").value;
	dcNo = trimAll(dcNo);
	var dcNoLength = dcNo.length;
	
	if(typeof(document.forms[0].circleId)!="undefined" ) {
		if(dcNoLength != 0) {
			document.getElementById("cirstar").style.display = 'none';
		} else {
			document.getElementById("cirstar").style.display = 'inline';
		}
	}

	if(typeof(document.forms[0].tsmId)!="undefined" ) {
		if(dcNoLength != 0) {
			document.getElementById("tsmstar").style.display = 'none';		
		} else {
			document.getElementById("tsmstar").style.display = 'inline';		
		}
	}
	if(typeof(document.forms[0].distId)!="undefined" ) {
		if(dcNoLength != 0) {
			document.getElementById("diststar").style.display = 'none';		
		} else {
			document.getElementById("diststar").style.display = 'inline';		
		}
	}
	if(typeof(document.forms[0].dcStatus)!="undefined" ) {
		if(dcNoLength != 0) {
			document.getElementById("dcstar").style.display = 'none';		
		} else {
			document.getElementById("dcstar").style.display = 'inline';		
		}
	}
	/*if(typeof(document.forms[0].fromDate)!="undefined" ) {
		if(dcNoLength != 0) {
			document.getElementById("req").style.display = 'none';		
		} else {
			document.getElementById("req").style.display = 'inline';		
		}
	}
	if(typeof(document.forms[0].toDate)!="undefined" ) {
		if(dcNoLength != 0) {
			document.getElementById("req1").style.display = 'none';		
		} else {
			document.getElementById("req1").style.display = 'inline';		
		}
	}*/
	if(typeof(document.forms[0].productId)!="undefined" ) {
		if(dcNoLength != 0) {
			document.getElementById("stbstar").style.display = 'none';		
		} else {
			document.getElementById("stbstar").style.display = 'inline';		
		}
	}
}

function toggleMandatoryDate() {
	
	var dcNo;
	var dcNoLength = 0;
	if(typeof(document.forms[0].dcNo)!="undefined" ) {
		dcNo = document.getElementById("dcNo").value;
		dcNo = trimAll(dcNo);
		dcNoLength = dcNo.length;
	}

	var dateOption =document.getElementById("dateOption").value;
	
	if(dateOption == "-1"){
		document.getElementById("req").style.display = 'none';
		document.getElementById("req1").style.display = 'none';
	} else {

		if(typeof(document.forms[0].fromDate)!="undefined" ) {
			if(dcNoLength != 0) {
				document.getElementById("req").style.display = 'none';		
			} else {
				document.getElementById("req").style.display = 'inline';		
			}
		}
		if(typeof(document.forms[0].toDate)!="undefined" ) {
			if(dcNoLength != 0) {
				document.getElementById("req1").style.display = 'none';		
			} else {
				document.getElementById("req1").style.display = 'inline';		
			}
		}
	}
}

function toggleMandatory() {
	var selectedIndex = document.getElementById("searchOption").selectedIndex;
	
	if(typeof(document.forms[0].circleId)!="undefined" ) {
		if(selectedIndex != '0') {
			document.getElementById("cirstar").style.display = 'none';
		} else {
			document.getElementById("cirstar").style.display = 'inline';
		}
	}

	if(typeof(document.forms[0].tsmId)!="undefined" ) {
		if(selectedIndex != '0') {
			document.getElementById("tsmstar").style.display = 'none';		
		} else {
			document.getElementById("tsmstar").style.display = 'inline';		
		}
	}
	if(typeof(document.forms[0].distId)!="undefined" ) {
		if(selectedIndex != '0') {
			document.getElementById("diststar").style.display = 'none';		
		} else {
			document.getElementById("diststar").style.display = 'inline';		
		}
	}
	if(typeof(document.forms[0].poStatus)!="undefined" ) {
		if(selectedIndex != '0') {
			document.getElementById("postar").style.display = 'none';
		} else {
			document.getElementById("postar").style.display = 'inline';		
		}

	}
	if(typeof(document.forms[0].status)!="undefined" ) {
		if(selectedIndex != '0') {
			document.getElementById("statstar").style.display = 'none';
		} else {
			document.getElementById("statstar").style.display = 'inline';
		}
	}
	if(typeof(document.forms[0].accountType)!="undefined" ) {		
		if(selectedIndex != '0') {
			document.getElementById("acctypestar").style.display = 'none';
		} else {
			document.getElementById("acctypestar").style.display = 'inline';
		}
	}
	
}

function getRetName(){

var distId = document.getElementById("hiddendistIds").value;
    	var fseId = document.getElementById("fseId").value;
	var selectedFseValues ="";
		
	if(fseId ==""){
		selectedFseValues =0;
	  getAllAccountsUnderMultipleAccounts(selectedFseValues,'retId');
		return false;
	 }
	 if(document.forms[0].fseId.length ==1){
			alert("No Dist exists ");
		return false;
	   }
 	else{
	 	if (document.forms[0].fseId[0].selected){
			selectedFseValues=document.forms[0].fseId[0].value;
		}	
		else{
			for (var i=1; i < document.forms[0].fseId.length; i++)
	  	 {
	   		 if (document.forms[0].fseId[i].selected)
	     	 {
	     		if(selectedFseValues !=""){
					selectedFseValues += ",";
     			}
	     	var selectedValFse = document.forms[0].fseId[i].value.split(",");
     		selectedFseValues += selectedValFse[0];
	     	 }
	   	}
   }
   		document.getElementById("hiddenFseSelectIds").value =selectedFseValues;
   		 getAllRetailer(selectedFseValues,distId,'retId');
 }

}

function resetCircle()
{
	for (var i=0; i < document.forms[0].circleId.length; i++)
  	 {
   		 document.forms[0].circleId[i].selected = false;
     }
}
function resetTSM()
{

	if(typeof(document.forms[0].transactionType)!="undefined" )
		{ 
			if(document.getElementById("transactionType").disabled==false){
				if(document.getElementById("transactionType").value ==""){
					alert("Please Select atleast one Business Category");
					return false;
		   		}
	  		}
	  	}
	  	
	  	
	for (var i=0; i < document.forms[0].tsmId.length; i++)
  	 {
   		 document.forms[0].tsmId[i].selected = false;
     }
     
       var circleId = document.forms[0].circleId[0].value;
	   var selectedCircleValues ="";
		selectedCircleValues =0;
		
		if(circleId ==""){
			alert("Please Select atleast one Circle");
			return false;
		 }
	 	else{
		 	
			for (var i=0; i < document.forms[0].circleId.length; i++)
		  	 {
		   		 if (document.forms[0].circleId[i].selected)
		     	 {
		     		if(selectedCircleValues !=""){
						selectedCircleValues += ",";
		     		}
			     	var selectedValCircle = document.forms[0].circleId[i].value.split(",");
			     	selectedCircleValues += selectedValCircle[0];
		     	 }
		   	}
	   
	   	 	document.getElementById("hiddenCircleSelectIds").value =selectedCircleValues;
	   		 //getAllAccountsMultipleCircles(accountLevel,selectedCircleValues,'tsmId');
	   	  //	 getAllTSM(selectedCircleValues,'tsmId');
	 }
     
}
function getDistName(accountlevel){
	var tsmId = document.getElementById("tsmId").value;
	var selectedTsmValues ="";
		
	if(tsmId ==""){
		alert("Please Select atleast one TSM");
		return false;
	 }
		
		if(accountlevel != 5 )
		{
			if(document.forms[0].tsmId.length ==1) {
					alert("No TSM exists ");
					return false;
			   }
		}
 	
			if (document.forms[0].tsmId[0].selected){
				
					selectedTsmValues=document.forms[0].tsmId[0].value;
				}	
				else{
					for (var i=0; i < document.forms[0].tsmId.length; i++)
			  	 {
			   		 if (document.forms[0].tsmId[i].selected)
			     	 {
			     		if(selectedTsmValues !=""){
							selectedTsmValues += ",";
		     			}
			     	var selectedValTSM = document.forms[0].tsmId[i].value.split(",");
		     		selectedTsmValues += selectedValTSM[0];
			     	 }
			   	}
		  }
		   		 document.getElementById("hiddenTsmSelectIds").value =selectedTsmValues;
		   		// getAllAccountsUnderMultipleAccounts(selectedTsmValues,'distId');
		   		
		   		//alert(document.getElementById("hiddenCircleSelectIds").value);
		   		
		   		
		   		 var selectedTransTypeValues="";
		   		 
		   		 
		   		 
if(typeof(document.forms[0].transactionType)!="undefined" )
		{	 		 
   		 		 for (var i=0; i < document.forms[0].transactionType.length; i++)
			  	 {
			   		 if (document.forms[0].transactionType[i].selected)
			     	 {
			     		if(selectedTransTypeValues !=""){
							selectedTransTypeValues += ",";
			     		}
					     	var selectedValTrans = document.forms[0].transactionType[i].value.split(",");
					     	selectedTransTypeValues += selectedValTrans[0];
			     	 }
			   	}
		}   		
				if(document.getElementById("hiddenCircleSelectIds").value!=null && document.getElementById("hiddenCircleSelectIds").value!="")
				{
		   			//alert(document.getElementById("hiddenCircleSelectIds").value);
					getAllDist((document.getElementById("hiddenCircleSelectIds").value),selectedTsmValues,'distId',selectedTransTypeValues);
				}
		   		else
		   		{	//alert(document.forms[0].circleId[0].value+","+document.forms[0].circleId[1].value);	
		   			//Added by neetika to handle multi circle users 
		   			var allcircles="";
		   			for(var c=0; c < document.forms[0].circleId.length; c++)
		   			{
		   				if(c==(document.forms[0].circleId.length-1))
		   					allcircles=	allcircles+""+document.forms[0].circleId[c].value +"";
		   				else
		   					allcircles=	allcircles+""+document.forms[0].circleId[c].value +",";
		   				//alert("allcircles"+allcircles);
		   				
		   			}
		   			getAllDist(allcircles,selectedTsmValues,'distId',selectedTransTypeValues);
		   			
		   		}
}
function getProductListAjax(businessCat, target)
{
	var reportId = document.getElementById("reportId").value;
	var url= "dropDownUtilityAjaxAction.do?methodName=getProductList&businessCat="+businessCat+"&reportId="+reportId;
	//alert(url);
	var elementId = target;
	var txt = false;
	makeAjaxRequest(url,elementId,txt);
}
function getProductList()
{
	
	 if(typeof(document.forms[0].productId)!="undefined" )
		{
			if(document.getElementById("productId").disabled==false){
			
					var selectedtransactionTypeValues="";
					 for (var i=0; i < document.forms[0].transactionType.length; i++)
					  	 {
					   			 if (document.forms[0].transactionType[i].selected)
						     	 {
						     		if(selectedtransactionTypeValues !=""){
										selectedtransactionTypeValues += ",";
						     		}
							     	var selectedTransTypeCircle = document.forms[0].transactionType[i].value.split(",");
							     	selectedtransactionTypeValues += selectedTransTypeCircle[0];
						     	 }
					    }
						getProductListAjax(selectedtransactionTypeValues,"productId");
			}
			else
			{
				//alert("444"); 
			}
	  	}
	  	else
	  	{
	  		//alert("3333"); 
	  		return false;
	  	}

}

function getTsmName() 
{   
		//alert("in genericreports.js---alert1");
		if(typeof(document.forms[0].transactionType)!="undefined" )
		{ 
			if(document.getElementById("transactionType").disabled==false){
				if(document.getElementById("transactionType").value ==""){
					alert("Please Select atleast one Business Category");
					return false;
		   		}
	  		}
	  	}
		//alert("in genericreports.js---alert2");
		 var accountLevel =  5;
		var circleId = document.forms[0].circleId[0].value;
		var selectedCircleValues ="";
		selectedCircleValues =0;
		//getAllAccountsUnderMultipleAccounts(selectedCircleValues,'distId');
	   
	if(circleId ==""){
		alert("Please Select atleast one Circle");
		return false;
	 }
	// if(document.forms[0].circleId.length ==1){
	//		alert("No Circle exists");
	//		return false;
	//   }
 	//else{
	 	if (document.forms[0].circleId[0].selected && document.forms[0].circleId[0].value=="0"){
		
			for (var i=1; i < document.forms[0].circleId.length; i++)
  	 		{
   		
     			if(selectedCircleValues !=""){
					selectedCircleValues += ",";
     			}
     		var selectedValCircle = document.forms[0].circleId[i].value.split(",");
     		selectedCircleValues += selectedValCircle[0];
   			}
   			
		}	
		else{
		  for (var i=0; i < document.forms[0].circleId.length; i++)
	  	 {
	   		 if(document.forms[0].circleId[i].value != "0") {	 
		   		 if (document.forms[0].circleId[i].selected)
		     	 {
		     		if(selectedCircleValues !=""){
						selectedCircleValues += ",";
		     		}
			     	var selectedValCircle = document.forms[0].circleId[i].value.split(",");
			     	selectedCircleValues += selectedValCircle[0];
		     	 }
	     	 }
	   	}
   }
   	 	document.getElementById("hiddenCircleSelectIds").value =selectedCircleValues;
   		 //getAllAccountsMultipleCircles(accountLevel,selectedCircleValues,'tsmId');
   		 
   		 
   	 //alert("in genericreports.js---alert3");
   var selectedTransTypeValues="";
   	if(typeof(document.forms[0].transactionType)!="undefined" )
		{	 
		   		 for (var i=0; i < document.forms[0].transactionType.length; i++)
			  	 {
			   		 if (document.forms[0].transactionType[i].selected)
			     	 {
			     		if(selectedTransTypeValues !=""){
							selectedTransTypeValues += ",";
			     		}
			     	var selectedValTrans = document.forms[0].transactionType[i].value.split(",");
			     	selectedTransTypeValues += selectedValTrans[0];
			     	 }
			   	}
		   		 	document.getElementById("hiddenBusinesscatSelectIds").value =selectedTransTypeValues;
		}
   	//alert("in genericreports.js---alert4");
   		 	//alert(document.getElementById("hiddenBusinesscatSelectIds").value);
   		 	getAllTSM(selectedCircleValues,'tsmId',selectedTransTypeValues);
 //}
		
}
function selectedCount(ctrl){
	var count = 0;
	for(i=1; i < ctrl.length; i++){
		if(ctrl[i].selected){
			count++;
		}
	}
	return count;
}

function selectAllTSM(){
		var ctrl = document.forms[0].tsmId;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		
		
		if (document.forms[0].tsmId[0].selected){
			for (var i=1; i < document.forms[0].tsmId.length; i++){
  	 			document.forms[0].tsmId[i].selected =true;
     		}
   		}
}

function selectAllDist(){
		var ctrl = document.forms[0].distId;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		
		if (document.forms[0].distId[0].selected){
			for (var i=1; i < document.forms[0].distId.length; i++){
  	 			document.forms[0].distId[i].selected =true;
     		}
   		}
}


function selectAllProducts(){
	var ctrl = document.forms[0].productId;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		var reportId = document.getElementById("reportId").value;
		
		
		if(reportId=="47" && document.forms[0].productId[1].selected == true)
   		{
   			for (var i=2; i < document.forms[0].productId.length; i++){
   				if(document.forms[0].productId[i].selected == true)
   				{
   					alert("Please do not Select any product after selecting All option");
					
   					for (var j=2; j < document.forms[0].productId.length; j++){
   						document.forms[0].productId[j].selected = false;
   					}
   					return false;
   				}
   			}
   		}
   		
   		
		if (document.forms[0].productId[0].selected ){
		
			if(reportId=="47")
			{	
				for (var i=2; i < document.forms[0].productId.length; i++){
	  	 			document.forms[0].productId[i].selected =true;
	     		}
			}
			else
			{
				for (var i=1; i < document.forms[0].productId.length; i++){
	  	 			document.forms[0].productId[i].selected =true;
	     		}
	     	}
   		}
   		
   		
   		
}


function selectAllCollectionTypes(){
		var ctrl = document.forms[0].collectionName;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		
		
		
		if (document.forms[0].collectionName[0].selected){
			for (var i=1; i < document.forms[0].collectionName.length; i++){
  	 			document.forms[0].collectionName[i].selected =true;
     		}
   		}
   	
}

function selectAllStatus(){
		var ctrl = document.forms[0].status;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		
		
		if (document.forms[0].status[0].selected){
			for (var i=1; i < document.forms[0].status.length; i++){
  	 			document.forms[0].status[i].selected =true;
     		}
   		}
   	
}

function selectAllDCStatus(){
		var ctrl = document.forms[0].dcStatus;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		
		
		if (document.forms[0].dcStatus[0].selected){
			for (var i=1; i < document.forms[0].dcStatus.length; i++){
  	 			document.forms[0].dcStatus[i].selected =true;
     		}
   		}
   	
}
function selectAllAccountTypes(){
		var ctrl = document.forms[0].accountType;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		
		
		if (document.forms[0].accountType[0].selected){
			for (var i=1; i < document.forms[0].accountType.length; i++){
  	 			document.forms[0].accountType[i].selected =true;
     		}
   		}
   	
}
function selectAllAccountName(){
		var ctrl = document.forms[0].accountName;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		
		
		if (document.forms[0].accountName[0].selected){
			for (var i=1; i < document.forms[0].accountName.length; i++){
  	 			document.forms[0].accountName[i].selected =true;
     		}
   		}
   	
}
function selectAllPendingAt(){
		var ctrl = document.forms[0].pendingAt;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		
		
		if (document.forms[0].pendingAt[0].selected){
			for (var i=1; i < document.forms[0].pendingAt.length; i++){
  	 			document.forms[0].pendingAt[i].selected =true;
     		}
   		}
   	
}
function selectAllPOStatus(){
		var ctrl = document.forms[0].poStatus;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		
		if (document.forms[0].poStatus[0].selected){
			for (var i=1; i < document.forms[0].poStatus.length; i++){
  	 			document.forms[0].poStatus[i].selected =true;
     		}
   		}
   	
}


function selectAllSTBStatus(){

		var ctrl = document.forms[0].stbStatus;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		
		
		if (document.forms[0].stbStatus[0].selected){
			for (var i=1; i < document.forms[0].stbStatus.length; i++){
  	 			document.forms[0].stbStatus[i].selected =true;
     		}
   		}
   	
}


function selectAllStockType(){

		var ctrl = document.forms[0].stockType;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		
		
		if (document.forms[0].stockType[0].selected){
			for (var i=1; i < document.forms[0].stockType.length; i++){
  	 			document.forms[0].stockType[i].selected =true;
     		}
   		}
   	
}
function selectAllCircles(){
		
		var ctrl = document.forms[0].circleId;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		
		//alert(document.forms[0].circleId[0].value);
		if (document.forms[0].circleId[0].selected && document.forms[0].circleId[0].value =="0") {
			for (var i=1; i < document.forms[0].circleId.length; i++){
  	 			document.forms[0].circleId[i].selected =true;
     		}
   		}
   		
   		
   		
   	
}


function initZBM()
{
//alert("initZBM");
	for (var i=0; i < document.forms[0].circleId.length; i++)
  	 {
   		 document.forms[0].circleId[i].selected = true;
     }
	
    // alert("circle selected");
     var circleId = document.forms[0].circleId[0].value;
	   var selectedCircleValues ="";
		selectedCircleValues =0;
		
		if(circleId ==""){
			alert("Please Select atleast one Circle");
			return false;
		 }
	 	else{
		 	//alert(" else loop of circle selected");
			for (var i=0; i < document.forms[0].circleId.length; i++)
		  	 {
		   		 if (document.forms[0].circleId[i].selected)
		     	 {
		     		if(selectedCircleValues !=""){
						selectedCircleValues += ",";
		     		}
			     	var selectedValCircle = document.forms[0].circleId[i].value.split(",");
			     	selectedCircleValues += selectedValCircle[0];
		     	 }
		   	}
	   
	   	 	document.getElementById("hiddenCircleSelectIds").value =selectedCircleValues;
	 }

}

function init()
{
	for (var i=0; i < document.forms[0].circleId.length; i++)
  	 {
   		 document.forms[0].circleId[i].selected = true;
     }
	for (var i=0; i < document.forms[0].tsmId.length; i++)
  	 {
   		 document.forms[0].tsmId[i].selected = true;
     }
     
     var circleId = document.forms[0].circleId[0].value;
	   var selectedCircleValues ="";
		selectedCircleValues =0;
		
		if(circleId ==""){
			alert("Please Select atleast one Circle");
			return false;
		 }
	 	else{
		 	
			for (var i=0; i < document.forms[0].circleId.length; i++)
		  	 {
		   		 if (document.forms[0].circleId[i].selected)
		     	 {
		     		if(selectedCircleValues !=""){
						selectedCircleValues += ",";
		     		}
			     	var selectedValCircle = document.forms[0].circleId[i].value.split(",");
			     	selectedCircleValues += selectedValCircle[0];
		     	 }
		   	}
	   
	   	 	document.getElementById("hiddenCircleSelectIds").value =selectedCircleValues;
	 }
	 
				 
	 
	 			for (var i=0; i < document.forms[0].tsmId.length; i++)
			  	 {
			   		 if (document.forms[0].tsmId[i].selected)
			     	 {
			     		if(selectedTsmValues !=""){
							selectedTsmValues += ",";
		     			}
			     	var selectedValTSM = document.forms[0].tsmId[i].value.split(",");
		     		selectedTsmValues += selectedValTSM[0];
			     	 }
			   	}
				document.getElementById("hiddenTsmSelectIds").value =selectedTsmValues;
}
function exportToExcel(accountLevel) {
 //alert(" genericreports.jsp  exportToExcel :"+accountLevel);
	
	var groupId = document.getElementById("groupId").value;
	var reportId = document.getElementById("reportId").value;
	var skipValidation = false;
	

		if(typeof(document.forms[0].dcNo)!="undefined") {
			var dcNo = document.getElementById("dcNo").value;
			if(dcNo != "") {
				skipValidation = true; 
			} 
		}

		if(typeof(document.forms[0].searchOption)!="undefined") {
			var selectedIndex = document.getElementById("searchOption").selectedIndex;
			if(selectedIndex != '0') {
				skipValidation = true; 
			} 
		}
		if(typeof(document.forms[0].transactionType)!="undefined" && !skipValidation )
		{ 
			if(document.getElementById("transactionType").disabled==false){
				if(document.getElementById("transactionType").value ==""){
					alert("Please Select atleast one Business Category");
					return false;
		   		}
		   		
		   		var selectedTransTypeValues="";
   		 
		   		 for (var i=0; i < document.forms[0].transactionType.length; i++)
			  	 {
			   		 if (document.forms[0].transactionType[i].selected)
			     	 {
			     		if(selectedTransTypeValues !=""){
							selectedTransTypeValues += ",";
			     		}
			     	var selectedValTrans = document.forms[0].transactionType[i].value.split(",");
			     	selectedTransTypeValues += selectedValTrans[0];
			     	 }
			   	}
		   		 	document.getElementById("hiddenBusinesscatSelectIds").value =selectedTransTypeValues;
		   	}
	  	}
		if(typeof(document.forms[0].circleId)!="undefined" && !skipValidation)
		{ 
			if(document.getElementById("circleId").disabled==false){
				if(document.getElementById("circleId").value ==""){
					alert("Please Select atleast one Circle");
					return false;
		   		}
	  		}
	  	}
	  	
		if(typeof(document.forms[0].tsmId)!="undefined" && !skipValidation)
		{
			if(document.getElementById("tsmId").disabled==false){
				if(document.getElementById("tsmId").value ==""){
					alert("Please Select atleast one TSM");
					return false;
		   		}
		   		if(accountLevel != 5) {
			  		if(document.forms[0].tsmId.length ==1){
						alert("No TSM exists under the Cirlce");
						return false;
			   		}  
			   	}
			}
		}
		
		
		if(typeof(document.forms[0].distId)!="undefined" && !skipValidation) 
		{	
			if(document.getElementById("distId").disabled==false){
				if(document.getElementById("distId").value ==""){
					alert("Please Select atleast one Distributor");
					return false;
	   			}
	   			}
	   	}
		
		if(typeof(document.forms[0].distId)!="undefined" && document.forms[0].distId.length!=0) 
		{	
			if(document.getElementById("distId").disabled==false){			
		   		var selectedDistValues = "";
		   		if (document.forms[0].distId[0].selected){
			
				selectedDistValues=document.forms[0].distId[0].value;
				}	
			else{
				for (var i=0; i < document.forms[0].distId.length; i++)
		  	 {
		   		 if (document.forms[0].distId[i].selected)
		     	 {
		     		if(selectedDistValues !=""){
						selectedDistValues += ",";
	     			}
		     	var selectedValDist = document.forms[0].distId[i].value.split(",");
	     		selectedDistValues += selectedValDist[0];
		     	 }
		   	}
   			}
   		 	document.getElementById("hiddenDistSelectIds").value =selectedDistValues;
	  	}
	  }
	 
	 if(typeof(document.forms[0].collectionName)!="undefined" && !skipValidation)
	 {
	 	 if(document.getElementById("collectionName").disabled==false){
			if(document.getElementById("collectionName").value ==""){
					alert("Please Select atleast one collection type");
					return false;
		   	}
		 }
	 }	   
	 if(typeof(document.forms[0].collectionName)!="undefined")
	 {
	 	 if(document.getElementById("collectionName").disabled==false){
		
		   		var selectedcollectionTypeValues = "";
		   		if (document.forms[0].collectionName[0].selected){
			
				for (var i=1; i < document.forms[0].collectionName.length; i++)
  	 		{
   		
     			if(selectedcollectionTypeValues !=""){
					selectedcollectionTypeValues += ",";
     			}
     		var selectedVal = document.forms[0].collectionName[i].value.split(",");
     		selectedcollectionTypeValues += selectedVal[0];
   			}
				}	
			else{
				for (var i=0; i < document.forms[0].collectionName.length; i++)
		  	 	{
		   		 if (document.forms[0].collectionName[i].selected)
		     	 {
		     		if(selectedcollectionTypeValues !=""){
						selectedcollectionTypeValues += ",";
	     			}
		     	var selectedVal = document.forms[0].collectionName[i].value.split(",");
	     		selectedcollectionTypeValues += selectedVal[0];
		     	 }
		   		}
   			}
   		 	document.getElementById("hiddenCollectionTypeSelectIds").value =selectedcollectionTypeValues;
   		
   		}
   		}
   		
   		if(typeof(document.forms[0].transferType)!="undefined" && !skipValidation)
   		{
   			if(document.getElementById("transferType").value ==""){
				alert("Please Select atleast one Transfer Type");
				return false;
	   		}
	   	}
	   		
   		
   		if(typeof(document.forms[0].transferType)!="undefined")
   		{
	   		var selectedtrnsfrTypeValues = "";
	   		if (document.forms[0].transferType[0].selected){
		
			for (var i=1; i < document.forms[0].transferType.length; i++)
  	 		{
   		
     			if(selectedtrnsfrTypeValues !=""){
					selectedtrnsfrTypeValues += ",";
     			}
     		var selectedVal = document.forms[0].transferType[i].value.split(",");
     		selectedtrnsfrTypeValues += selectedVal[0];
   			}
			}	
		else{
			for (var i=1; i < document.forms[0].transferType.length; i++)
	  	 	{
	   		 if (document.forms[0].transferType[i].selected)
	     	 {
	     		if(selectedtrnsfrTypeValues !=""){
					selectedtrnsfrTypeValues += ",";
     			}
	     	var selectedVal = document.forms[0].transferType[i].value.split(",");
     		selectedtrnsfrTypeValues += selectedVal[0];
	     	 }
	   		}
   		}
   		 document.getElementById("hiddenTrnsfrTypeSelec").value =selectedtrnsfrTypeValues;
   		 
   		 }
   		 
   		if(typeof(document.forms[0].status)!="undefined" && !skipValidation )
   		{
   		   if(document.getElementById("status").disabled==false){

   		   if(reportId != 7 ) 
   		   {
		 		if(document.getElementById("status").value ==""){
					alert("Please Select atleast one status");
					return false;
		   		}
		   	}
		   }
		}
   		 
   		if(typeof(document.forms[0].status)!="undefined")
   		{
   		   if(document.getElementById("status").disabled==false){

	   			var selectedStatusValues = "";
		   		if (document.forms[0].status[0].selected){
			
				for (var i=1; i < document.forms[0].status.length; i++)
	  	 		{
	   		
	     			if(selectedStatusValues !=""){
						selectedStatusValues += ",";
	     			}
	     		var selectedVal = document.forms[0].status[i].value.split(",");
	     		selectedStatusValues += selectedVal[0];
	   			}
				}	
			else{
				for (var i=0; i < document.forms[0].status.length; i++)
		  	 	{
		   		 if (document.forms[0].status[i].selected)
		     	 {
		     		if(selectedStatusValues !=""){
						selectedStatusValues += ",";
	     			}
		     	var selectedVal = document.forms[0].status[i].value.split(",");
	     		selectedStatusValues += selectedVal[0];
		     	 }
		   		}
   			}
   		 document.getElementById("hiddenStatusSelectIds").value =selectedStatusValues;
	  	}}
	  	
		  if(typeof(document.forms[0].productId)!="undefined" && !skipValidation)
			{ 
			   	if(document.getElementById("productId").disabled==false){
			 		 if(document.getElementById("productId").value ==""){
			 		 		alert("Please Select atleast one Product/STB Type");
							return false;
					}
				}
			}		
	 	
	  if(typeof(document.forms[0].productId)!="undefined")
	   { if(document.getElementById("productId").disabled==false){
			
	   		var selectedSTBTypeValues = "";
	   		if (document.forms[0].productId[0].selected){
		
			for (var i=1; i < document.forms[0].productId.length; i++)
  	 		{
   		
     			if(selectedSTBTypeValues !=""){
					selectedSTBTypeValues += ",";
     			}
     		var selectedVal = document.forms[0].productId[i].value.split(",");
     		selectedSTBTypeValues += selectedVal[0];
   			}
			}	
		else{
			for (var i=1; i < document.forms[0].productId.length; i++)
	  	 	{
	   		 if (document.forms[0].productId[i].selected)
	     	 {
	     		if(selectedSTBTypeValues !=""){
					selectedSTBTypeValues += ",";
     			}
	     	var selectedVal = document.forms[0].productId[i].value.split(",");
     		selectedSTBTypeValues += selectedVal[0];
	     	 }
	   		}
   		}
   		 document.getElementById("hiddenProductTypeSelectIds").value =selectedSTBTypeValues;
	  }}	
	  	
	  	
	  	
		
		if(typeof(document.forms[0].recoPeriod)!="undefined" )
   		{
   		   if(document.getElementById("recoPeriod").disabled==false){
				if(document.getElementById("recoPeriod").value ==""){
					alert("Please Select reco Period");
					return false;
		   		}
		   }
		}
	  	
	  if(typeof(document.forms[0].accountType)!="undefined" && !skipValidation)
		{ if(document.getElementById("accountType").disabled==false){
				if(document.getElementById("accountType").value ==""){
				alert("Please Select atleast one Account Type");
				return false;
	   		}
	   		}
	   	}
	  	if(typeof(document.forms[0].accountType)!="undefined" )
		{ if(document.getElementById("accountType").disabled==false){

	   		var selectedAccountTypeValues = "";
	   		if (document.forms[0].accountType[0].selected){
		
			for (var i=1; i < document.forms[0].accountType.length; i++)
  	 		{
   		
     			if(selectedAccountTypeValues !=""){
					selectedAccountTypeValues += ",";
     			}
     		var selectedValAccountType = document.forms[0].accountType[i].value.split(",");
     		selectedAccountTypeValues += selectedValAccountType[0];
   			}
			}	
		else{
			for (var i=0; i < document.forms[0].accountType.length; i++)
	  	 {
	   		 if (document.forms[0].accountType[i].selected)
	     	 {
	     		if(selectedAccountTypeValues !=""){
					selectedAccountTypeValues += ",";
     			}
	     	var selectedValAccountType = document.forms[0].accountType[i].value.split(",");
     		selectedAccountTypeValues += selectedValAccountType[0];
	     	 }
	   	}
   		}
   		 document.getElementById("hiddenAccountTypeSelectIds").value =selectedAccountTypeValues;
	  	}
	  	}
	  	
	  		if(typeof(document.forms[0].accountName)!="undefined" && !skipValidation) 
	  		{ 
	  			if(document.getElementById("accountName").disabled==false){
				if(document.getElementById("accountName").value ==""){
				alert("Please Select atleast one Account Name");
				return false;
	   			}
	   			}
	   		}	
	  	
	  		if(typeof(document.forms[0].accountName)!="undefined") 
	  		{ 
	  			if(document.getElementById("accountName").disabled==false){

		   		var selectedAccountNameValues = "";
		   		if (document.forms[0].accountName[0].selected){
			
				for (var i=1; i < document.forms[0].accountName.length; i++)
	  	 		{
	   		
	     			if(selectedAccountNameValues !=""){
						selectedAccountNameValues += ",";
	     			}
	     		var selectedValAccountName = document.forms[0].accountName[i].value.split(",");
	     		selectedAccountNameValues += selectedValAccountName[0];
	   			}
				}	
			else{
				for (var i=0; i < document.forms[0].accountName.length; i++)
		  	 {
		   		 if (document.forms[0].accountName[i].selected)
		     	 {
		     		if(selectedAccountNameValues !=""){
						selectedAccountNameValues += ",";
	     			}
		     	var selectedValAccountName = document.forms[0].accountName[i].value.split(",");
	     		selectedAccountNameValues += selectedValAccountName[0];
		     	 }
		   	}
	   		}
   		 document.getElementById("hiddenAccountNameSelectIds").value =selectedAccountNameValues;
	  	}
	  	}
	  	
	  		if(typeof(document.forms[0].poStatus)!="undefined" && !skipValidation)
	  		{	
	  			if(document.getElementById("poStatus").disabled==false){
					if(document.getElementById("poStatus").value ==""){
					alert("Please Select atleast one PO Status");
					return false;
		   		}
		   		}
		   	}	
	  		if(typeof(document.forms[0].poStatus)!="undefined" )
	  		{	
	  			if(document.getElementById("poStatus").disabled==false){

		   		var selectedPOStatusValues = "";
		   	if (document.forms[0].poStatus[0].selected){
			
				for (var i=1; i < document.forms[0].poStatus.length; i++)
	  	 		{
	   		
	     			if(selectedPOStatusValues !=""){
						selectedPOStatusValues += ",";
	     			}
	     		var selectedValPOStatus = document.forms[0].poStatus[i].value.split(",");
	     		selectedPOStatusValues += selectedValPOStatus[0];
	   			}
				}	
			else{
				for (var i=0; i < document.forms[0].poStatus.length; i++)
		  	 {
		   		 if (document.forms[0].poStatus[i].selected)
		     	 {
		     		if(selectedPOStatusValues !=""){
						selectedPOStatusValues += ",";
	     			}
		     	var selectedValPOStatus = document.forms[0].poStatus[i].value.split(",");
	     		selectedPOStatusValues += selectedValPOStatus[0];
		     	 }
		   	}
	   		}
	   		 document.getElementById("hiddenPOStatusSelectIds").value =selectedPOStatusValues;
	  	}
	  	}
	  	 
	  		if(typeof(document.forms[0].pendingAt)!="undefined" && !skipValidation) 
	  		 { 
	  		 if(document.getElementById("pendingAt").disabled==false){
				if(document.getElementById("pendingAt").value ==""){
				alert("Please Select atleast one Pending At");
				return false;
	   			}
	   			}
	   		}	
	  	
	  		if(typeof(document.forms[0].pendingAt)!="undefined") 
	  		 { 
	  		 if(document.getElementById("pendingAt").disabled==false){

		   		var selectedPendingAtValues = "";
		   		if (document.forms[0].pendingAt[0].selected){
			
				for (var i=1; i < document.forms[0].pendingAt.length; i++)
	  	 		{
	   		
	     			if(selectedPendingAtValues !=""){
						selectedPendingAtValues += ",";
	     			}
	     		var selectedValPendingAt = document.forms[0].pendingAt[i].value.split(",");
	     		selectedPendingAtValues += selectedValPendingAt[0];
	   			}
				}	
			else{
				for (var i=0; i < document.forms[0].pendingAt.length; i++)
		  	 {
		   		 if (document.forms[0].pendingAt[i].selected)
		     	 {
		     		if(selectedPendingAtValues !=""){
						selectedPendingAtValues += ",";
	     			}
		     	var selectedValPendingAt = document.forms[0].pendingAt[i].value.split(",");
	     		selectedPendingAtValues += selectedValPendingAt[0];
		     	 }
		   	}
	   		}
   		 document.getElementById("hiddenPendingAtSelectIds").value =selectedPendingAtValues;
	  	}
	  	}
	  	
	  		if(typeof(document.forms[0].dcStatus)!="undefined" && !skipValidation) 
	  		{	if(document.getElementById("dcStatus").disabled==false)
	  			{
					if(document.getElementById("dcStatus").value ==""){
					alert("Please Select atleast one DC Status");
					return false;
		   			}
		   		}
		   	}		
	  		if(typeof(document.forms[0].dcStatus)!="undefined" ) 
	  		{	if(document.getElementById("dcStatus").disabled==false)
	  			{
		   		var selectedDCStatusValues = "";
		   		if (document.forms[0].dcStatus[0].selected){
			
				for (var i=1; i < document.forms[0].dcStatus.length; i++)
	  	 		{
	   		
	     			if(selectedDCStatusValues !=""){
						selectedDCStatusValues += ",";
	     			}
	     		var selectedValDCStatus = document.forms[0].dcStatus[i].value.split(",");
	     		selectedDCStatusValues += selectedValDCStatus[0];
	   			}
				}	
			else{
				for (var i=0; i < document.forms[0].dcStatus.length; i++)
		  	 {
		   		 if (document.forms[0].dcStatus[i].selected)
		     	 {
		     		if(selectedDCStatusValues !=""){
						selectedDCStatusValues += ",";
	     			}
		     	var selectedValDCStatus = document.forms[0].dcStatus[i].value.split(",");
	     		selectedDCStatusValues += selectedValDCStatus[0];
		     	 }
		   	}
   			}
   		 	document.getElementById("hiddenDCStatusSelectIds").value =selectedDCStatusValues;
	  	}}
	  	
	  	if(typeof(document.forms[0].searchOption)!="undefined"){
			var searchOption =document.getElementById("searchOption").value;
			if(searchOption != "-1"){
				var searchCriteria = document.getElementById("searchCriteria").value;
					if ((searchCriteria==null)||(searchCriteria=="0") || searchCriteria==""){
					alert('Search Criteria is Required');
					return false;
				}	
			}	
		}
			
	  if(typeof(document.forms[0].stbStatus)!="undefined" && !skipValidation)
	   { 
   		 	if(document.getElementById("stbStatus").value =="") {
				alert("Please Select atleast one STB Status");
				return false;
	   		}
	   	}			
	  if(typeof(document.forms[0].stbStatus)!="undefined")
	   { 

	   		var selectedStatusValues = "";
	   		if (document.forms[0].stbStatus[0].selected){
		
				for (var i=1; i < document.forms[0].stbStatus.length; i++)
	  	 		{
	   		
	     			if(selectedStatusValues !=""){
						selectedStatusValues += ",";
	     			}
		     		var selectedStatusValuesArr = document.forms[0].stbStatus[i].value.split(",");
		     		selectedStatusValues += selectedStatusValuesArr[0];
	   			}
			}	
		else{
			for (var i=1; i < document.forms[0].stbStatus.length; i++)
	  	 	{
	   		 if (document.forms[0].stbStatus[i].selected)
	     	 {
	     		if(selectedStatusValues !=""){
					selectedStatusValues += ",";
     			}
	     	var selectedStatusValuesArr = document.forms[0].stbStatus[i].value.split(",");
     		selectedStatusValues += selectedStatusValuesArr[0];
	     	 }
	   		}
   		}
   		 document.getElementById("hiddenStbStatus").value =selectedStatusValues;
   		 
   	
   	}
   	
   	
   	if(typeof(document.forms[0].stockType)!="undefined")
	   { 
   		 	if(document.getElementById("stockType").value =="") {
				alert("Please Select atleast one Stock Type");
				return false;
	   		}
	   	}			
	  if(typeof(document.forms[0].stockType)!="undefined")
	   { 

	   		var selectedStatusValues = "";
	   		if (document.forms[0].stockType[0].selected){
		
				for (var i=1; i < document.forms[0].stockType.length; i++)
	  	 		{
	   		
	     			if(selectedStatusValues !=""){
						selectedStatusValues += ",";
	     			}
		     		var selectedStatusValuesArr = document.forms[0].stockType[i].value.split(",");
		     		selectedStatusValues += selectedStatusValuesArr[0];
	   			}
			}	
		else{
			for (var i=1; i < document.forms[0].stockType.length; i++)
	  	 	{
	   		 if (document.forms[0].stockType[i].selected)
	     	 {
	     		if(selectedStatusValues !=""){
					selectedStatusValues += ",";
     			}
	     	var selectedStatusValuesArr = document.forms[0].stockType[i].value.split(",");
     		selectedStatusValues += selectedStatusValuesArr[0];
	     	 }
	   		}
   		}
   		 document.getElementById("hiddenStockTypeSelectIds").value =selectedStatusValues;
   		 
   	
   	}
   	
   	//aman
		  if(typeof(document.forms[0].activity)!="undefined" && !skipValidation) 
	  		{if(document.getElementById("activity").disabled==false)
	  			{
					if(document.getElementById("activity").value ==-1){
					alert("Please Select atleast one activity ");
					return false;
		   			}
		   		}
	  		
	  		var fromDate = document.getElementById("fromDate").value;
	  		var toDate = document.getElementById("toDate").value;
	  		var startDate = new Date(fromDate);
	    	var endDate = new Date(toDate);
	  		if(daysBetween(startDate,endDate) >= 31)
			{
	  		
	  			alert('Please select dates having only max 31 days difference');
			  return false;
			}
	  		
	  		return true;
	  		
	  		
	  	
	  		}	
	  //
   	
 	 if(typeof(document.forms[0].dateOption)!="undefined") 
  		{	
			var dateOption =document.getElementById("dateOption").value;
			if(dateOption =="-1" && (document.getElementById("reportId").value == 45 ||document.getElementById("reportId").value == 46 ) )
			{
				alert("Please select any date option");
				return false;
			}
			
			
			
			if(dateOption !="-1"){
			var fromDate = document.getElementById("fromDate").value;
			if ((fromDate==null)||(fromDate=="0") || fromDate==""){
				alert('From Date is Required');
				return false;
			}
			
			var toDate = document.getElementById("toDate").value;
		    if ((toDate==null)||(toDate=="0") || toDate==""){
				alert('To Date is Required');
				return false;
			}
			
	var dateValidation= document.getElementById("dateValidation").value;
			var startDate = new Date(fromDate);
	    	var endDate = new Date(toDate);
	    	var currentDate=null;
	    	if(dateValidation=="-1")
	    	{
	    	var dt=new Date();
	    		 currentDate = new Date((dt.getMonth()+1)+"/"+(dt.getDate()-1)+"/"+dt.getYear())
	    	
	    	}else
	    	 currentDate = new Date();
	    	
	    	
	    	if(startDate > endDate)
	    	{
	    		 alert('From date can not be greater than To Date');
	  		  	 return false;
	    	}
	    	if(endDate > currentDate)
	    	{
	    	if(dateValidation=="-1")
	    	{
	    	alert('To date should be smaller than current Date');
	  		  	 return false;
	    	}else{
	    		 alert('To date can not be greater than todays Date');
	  		  	 return false;
	    	}
	    	}
	    	if(daysBetween(startDate,currentDate) > 365)
	    	{
	    		 alert('From date can not be more than 365 days old from today');
	  		  	 return false;
	    	}
	    	
	    	if(daysBetween(startDate,endDate) >= 45)
			{
	    		alert('Please select dates having only max 45 days difference');
			  return false;
			}
	   }
	   }
	   else{
	   if(typeof(document.forms[0].fromDate)!="undefined" && typeof(document.forms[0].toDate)!="undefined" && !skipValidation ) 
	  		{
			var fromDate = document.getElementById("fromDate").value;
			if ((fromDate==null)||(fromDate=="0") || fromDate==""){
				alert('From Date is Required');
				return false;
			}
			
			var toDate = document.getElementById("toDate").value;
		    if ((toDate==null)||(toDate=="0") || toDate==""){
				alert('To Date is Required');
				return false;
			}
			
			var dateValidation= document.getElementById("dateValidation").value;
			var currentDate=null;
			var startDate = new Date(fromDate);
	    	var endDate = new Date(toDate);
	    	if(dateValidation=="-1")
	    	{
	    		var dt=new Date();
	    		currentDate = new Date((dt.getMonth()+1)+"/"+(dt.getDate()-1)+"/"+dt.getYear())
	    	
	    	} else {
	    	 	currentDate = new Date();
	    	}
	    	
	    	if(startDate > endDate)
	    	{
	    		 alert('From date can not be greater than To Date');
	  		  	 return false;
	    	}
	    	if(endDate > currentDate) {
	    		if(dateValidation=="-1") {
	    			alert('To date can not be greater than Yesterday Date');
	  		  	 	return false;
	    		}else{
	    		 	alert('To date can not be greater than todays Date');
	  		  	 	return false;
	    		}
	    	}
	    	
	    	if(daysBetween(startDate,currentDate) > 365)
	    	{
	    		 alert('From date can not be more than 365 days old from today');
	  		  	 return false;
	    	}
	    	
	    	if(daysBetween(startDate,endDate) >= 45)
			{
	    		alert('Please select dates having only max 45 days difference');
			  return false;
			}
	   	}
	   }
	   	if(typeof(document.forms[0].date)!="undefined" && !skipValidation)
		{ 
			if(document.getElementById("date").disabled==false){
				if(document.getElementById("date").value ==""){
					alert("Please Select Date");
					return false;
		   		}
	  		}
	  	}
	   	//alert("==== submitting page======"+document.getElementById("excelBtn").value+ " "+document.getElementById("excelBtn").disabled);
	   	document.getElementById("excelBtn").disabled=true;
	   	var url = "GenericReportsAction.do?methodName=exportToExcel";
		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();
		document.getElementById("excelBtn").disabled=false;
		
	}
	
	function getAccountNames() {
		var selectedAccount = document.getElementById("accountType");
		var selectedAccountTypeId = selectedAccount.options[selectedAccount.selectedIndex].value;
		
		if(document.getElementById("distId").options.length == 0){
			alert("No Distributor exists ");
			return false;
		}
		var selectedDistIndex = document.getElementById("distId").selectedIndex;
		
		if((selectedAccountTypeId == 2 || selectedAccountTypeId == 3 ) && selectedDistIndex == -1){
			alert("Please Select atleast one Distributor");
			return false;
		}
		
		var selectedDistValues ="";
		
		if (document.forms[0].distId[0].selected){
			
			for (var i=0; i < document.forms[0].distId.length; i++) {
	     		if(selectedDistValues !="") {
					selectedDistValues += ",";
	    		}
	     		var selectedValDist = document.forms[0].distId[i].value.split(",");
	    		selectedDistValues += selectedValDist[0];
	   		}
	   		
		} else {
			for (var i=0; i < document.forms[0].distId.length; i++) {
		   		 if (document.forms[0].distId[i].selected) {
		     		if(selectedDistValues !="") {
						selectedDistValues += ",";
	     			}
		     		var selectedValDist = document.forms[0].distId[i].value.split(",");
	     			selectedDistValues += selectedValDist[0];
		     	 }
	   		}
  		}
  		
  		if(selectedAccountTypeId == 1) {
	   		var accountNameObj = document.getElementById("accountName");
			accountNameObj.options.length = 0;
			for(var i=0; i < document.forms[0].distId.length; i++) 
			{
			   accountNameObj.options[i] = new Option(document.forms[0].distId[i].text,document.forms[0].distId[i].value);
			}
  		} else {
  			getAjaxAccountNames(selectedDistValues, selectedAccountTypeId, 'accountName');
  		}

	}
	
	function getFseName(){
var groupId = document.getElementById("groupId").value;

    	var distId = document.getElementById("distId").value;
	var selectedDistValues ="";
		
	if(distId ==""){
		selectedDistValues =0;
	  getAllAccountsUnderMultipleAccounts(selectedDistValues,'accountName');
		return false;
	 }
	 if(document.forms[0].distId.length ==1){
			alert("No Dist exists ");
		return false;
	   }
 	else{
	 	if (document.forms[0].distId[0].selected){
		
			for (var i=1; i < document.forms[0].distId.length; i++)
  	 		{
   		
     			if(selectedDistValues !=""){
					selectedDistValues += ",";
     			}
     		var selectedValDist = document.forms[0].distId[i].value.split(",");
     		selectedDistValues += selectedValDist[0];
   			}
		}	
		else{
			for (var i=1; i < document.forms[0].distId.length; i++)
	  	 {
	   		 if (document.forms[0].distId[i].selected)
	     	 {
	     		if(selectedDistValues !=""){
					selectedDistValues += ",";
     			}
	     	var selectedValDist = document.forms[0].distId[i].value.split(",");
     		selectedDistValues += selectedValDist[0];
	     	 }
	   	}
   } 
   //alert(selectedDistValues);
   document.getElementById("hiddendistIds").value =selectedDistValues;

   getAllAccountsUnderMultipleAccounts(selectedDistValues,'accountName');


 }
}

function getRetName(){

    	var fseId = document.getElementById("fseId").value;
	var selectedFseValues ="";
		
	if(fseId ==""){
		selectedFseValues =0;
	  getAllAccountsUnderMultipleAccounts(selectedFseValues,'retId');
		return false;
	 }
	 if(document.forms[0].fseId.length ==1){
			alert("No Dist exists ");
		return false;
	   }
 	else{
	 	if (document.forms[0].fseId[0].selected){
		
			for (var i=1; i < document.forms[0].fseId.length; i++)
  	 		{
   		
     			if(selectedFseValues !=""){
					selectedFseValues += ",";
     			}
     		var selectedValFse = document.forms[0].fseId[i].value.split(",");
     		selectedFseValues += selectedValFse[0];
   			}
		}	
		else{
			for (var i=1; i < document.forms[0].fseId.length; i++)
	  	 {
	   		 if (document.forms[0].fseId[i].selected)
	     	 {
	     		if(selectedFseValues !=""){
					selectedFseValues += ",";
     			}
	     	var selectedValFse = document.forms[0].fseId[i].value.split(",");
     		selectedFseValues += selectedValFse[0];
	     	 }
	   	}
   }
   		document.getElementById("hiddenFseSelecIds").value =selectedFseValues;
   		 getAllAccountsUnderMultipleAccounts(selectedFseValues,'retId');
 }
 selectAllFSE();
}
	
	