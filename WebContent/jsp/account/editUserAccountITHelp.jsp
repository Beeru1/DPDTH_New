<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="java.util.*"%>
<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="<%=request.getContextPath()%>/theme/text.css" rel="stylesheet" type="text/css">
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/scripts/validation.js"
	type="text/javascript"></SCRIPT>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/scripts/Cal.js"
	type="text/javascript"></SCRIPT>
	
<SCRIPT language="javascript" type="text/javascript"> 

//Added by Shilpa For locator code validations

function checkLocator(str){
	var isvalid;
	var filter = /^\d\d\d.[\w]+\.[\w]+\.[\w]+\)*\s*$/;
	if (String(str).search (filter) == -1) {
		isvalid =false;
	}
	else
	{
		isvalid =true;
	}
return isvalid;

}

//Added by Shilpa For locator code validations Ends here 


	function OpenHelpWindow(url){
		document.submitForm.action=url;
		displayWindow = window.open('', "newWin", "width =700,height= 220, left=50, top=100, scrollbars=yes,resizable=yes, status=yes");
       	document.submitForm.submit();
	}
	// trim the spaces
	
		function trimAll(sString) {
		while (sString.substring(0,1) == ' '){
		sString = sString.substring(1, sString.length);
		}		
		while (sString.substring(sString.length-1, sString.length) == ' '){
		sString = sString.substring(0,sString.length-1);	
		}
		return sString;
	}

	function checkCircleChange(){
	//alert("circl ");
		var selectedCircleValues ="";
		selectedCircleValues =0;
		var accountId = document.getElementById("accountId").value;
		//
		if(parseInt(document.getElementById("accountLevel").value) == 2 || parseInt(document.getElementById("accountLevel").value) == 3 || parseInt(document.getElementById("accountLevel").value) == 4  || parseInt(document.getElementById("accountLevel").value) == 5    || parseInt(document.getElementById("accountLevel").value) == 14){
			var	Id = document.forms[0].circleIdList[0].value;
			if(Id != "" &&  document.forms[0].circleIdList.length >1){
				for (var i=1; i < document.forms[0].circleIdList.length; i++)
		  	 {
		   		 if (document.forms[0].circleIdList[i].selected)
		     	 {
		     		if(selectedCircleValues !=""){
						selectedCircleValues += ",";
		     		}
		     	var selectedValCircle = document.forms[0].circleIdList[i].value.split(",");
		     	if(parseInt(selectedValCircle[0]) == 0){
		     	if(!(parseInt(document.getElementById("accountLevel").value) == 2 || parseInt(document.getElementById("accountLevel").value) == 14)){
		     		alert("Select Valid Circle");
		     		document.getElementById("circleIdList").focus();
		     		return false;
		     		}
		     	}
		     	selectedCircleValues += selectedValCircle[0];
		     	 }
		   	}
		   	
		   	var url="initCreateAccountItHelp.do?methodName=checkCircleChange&selectedCircleList="+selectedCircleValues+"&accountId="+accountId+"&random="+new Date().toString();
			if(window.XmlHttpRequest) {
				req = new XmlHttpRequest();
			}else if(window.ActiveXObject) {
				req=new ActiveXObject("Microsoft.XMLHTTP");
			}
			if(req==null) {
				alert("Browser Doesn't Support AJAX");
				return;
			}
			req.onreadystatechange = checkCircleChangeAjax;
			req.open("POST",url,false);
			req.send();
	   
	  }
	  
	  
  }
}

		function checkCircleChangeAjax()
		{
		//alert("checkCircleChangeAjax");
		if (req.readyState==4 || req.readyState=="complete") 
	  	{
			var xmldoc = req.responseXML.documentElement;
			if(xmldoc == null) 
			{
				return;
			}
			
			// Get Conditions.
			optionValues = xmldoc.getElementsByTagName("option");						
			var strERR = optionValues[0].getAttribute("errMessage");	
			if(strERR !='NA'){
				alert(strERR);
				document.getElementById("isERR").value="Y";
			}else{
				document.getElementById("isERR").value="N";
			}							
		}
	}

	var addressStatus=false;
	// function that checks that is any field value is blank or not
	function validate() {
		checkCircleChange();
		//check if page is not submitted by state field to get city 
		var checkCircleChangeValue = document.getElementById("isERR").value;
		if(checkCircleChangeValue =="Y"){
			return false;
		}
		if(document.getElementById("flagForCityFocus").value=="true"){	
	        document.getElementById("methodName").value="getCityForEdit";
		  	return true;
		} 
		
		if(parseInt(document.getElementById("accountLevel").value)<7 )
		{		
			if(document.getElementById("accountCode").value==null || document.getElementById("accountCode").value=="")
			{
				alert('<bean:message bundle="error" key="error.account.accountcode" />');
				document.getElementById("accountCode").focus();
				return false; 
			}
			var accCode = document.getElementById("accountCode").value;
			var firstChar = isNumber(accCode.charAt(0));
			if(firstChar==true || accCode.charAt(0)==""){
				alert("User Name Cannot Begin With A Numeric Character Or Blank Space.");
				document.getElementById("accountCode").focus();
				return false;
			}
			
			
			if(document.getElementById("accountCode").value.length < 8)
			{
				alert("User Name Must Contain At Least 8 Characters.");
				document.getElementById("accountCode").focus();
				return false;
			}
			var validAccountCode = isAlphaNumeric(accCode);
			if(validAccountCode==false){
				alert("Special Characters and Blank Spaces are not allowed in Username");
				document.getElementById("accountCode").focus();
				return false;
			}
		}			
		
		
		if(document.getElementById("accountCode").value==null || document.getElementById("accountCode").value==""){
			alert('<bean:message bundle="error" key="error.account.accountcode" />');
			document.getElementById("accountCode").focus();
			return false; 
		}
		if(document.getElementById("accountName").value==null || document.getElementById("accountName").value==""){
			alert('<bean:message bundle="error" key="error.account.accountname" />');
			document.getElementById("accountName").focus();
			return false; 
		}
		var accName = document.getElementById("accountName").value;
		var firstChar = isNumber(accName.charAt(0));
		if(firstChar==true){
			alert("Account Name Cannot Begin With A Numeric Character");
			document.getElementById("accountName").focus();
			return false;
		}
		var validAccountCode = isAlphaNumericWithSpace(accName);
		if(validAccountCode==false){
			alert("Special Characters are not allowed in Account Name");
			document.getElementById("accountName").focus();
			return false;
		}
		if(document.getElementById("contactName").value==null || document.getElementById("contactName").value==""){
			alert('<bean:message bundle="error" key="error.account.contactname" />');
			document.getElementById("contactName").focus();
			return false; 
		}
		var contactName = document.getElementById("contactName").value;
		var firstChar = isNumber(contactName.charAt(0));
		if(firstChar==true){
			alert("Contact Name Cannot Begin With A Numeric Character");
			document.getElementById("contactName").focus();
			return false;
		}
		var validAccountCode = isAlphaNumericWithSpace(contactName);
		if(validAccountCode==false){
			alert("Special Characters are not allowed in Contact Name");
			document.getElementById("contactName").focus();
			return false;
		}
		if(!(document.getElementById("email").value==null || document.getElementById("email").value=="")){
		   // Validate Email
			var emailID=document.forms[0].email;
			var errorMsg ='<bean:message bundle="error" key="errors.user.email_invalid" />';
		    if (!(isEmail(emailID.value, errorMsg))){
				emailID.focus();
				return false;
			} 
		}
		if(!(document.getElementById("simNumber").value==null || document.getElementById("simNumber").value==""))
		{
			if(!(document.getElementById("simNumber").value.length==20 || document.getElementById("simNumber").value.length==19)){
				alert('<bean:message bundle="error" key="error.account.simnolength" />');
				document.getElementById("simNumber").focus();
				return false;
			} else if(isNumber(document.forms[0].simNumber.value)==false){
					alert('<bean:message bundle="error" key="error.account.simnonumeric" />');
					return false;
			}else if(document.getElementById("simNumber").value.length==20){
				if(isLastCharAlphabet(document.forms[0].simNumber.value)==false){
					alert('<bean:message bundle="error" key="error.account.simnoinvalid" />');
					return false;
				}
			}
		}
		var mobileno = document.getElementById("mobileNumber").value; 
		 if(mobileno==null || mobileno==""){
			alert('<bean:message bundle="error" key="error.account.mobilenumber" />');
			document.getElementById("mobileNumber").focus();
			return false; 
		}
		if(mobileno != null || mobileno != ""){
		var firstChar = mobileno.charAt(0);
	 if((document.getElementById("mobileNumber").value.length)<10){
			alert('<bean:message bundle="error" key="error.account.mobilenumberlength" />');
			document.getElementById("mobileNumber").focus();
			return false;
		}
		}
		if(document.getElementById("accountPinNumber").value==null || document.getElementById("accountPinNumber").value==""){
			alert('<bean:message bundle="error" key="error.account.pin" />');
			document.getElementById("accountPinNumber").focus();
			return false; 
		}
		 if((document.getElementById("accountPinNumber").value.length)<6){
			alert('<bean:message bundle="error" key="error.account.pinlength" />');
			document.getElementById("accountPinNumber").focus();
			return false; 
		}
		var pinNumber=document.getElementById("accountPinNumber").value;	
		if((pinNumber*1)==0){
		alert('<bean:message bundle="error" key="error.account.invalidpin" />');
		document.getElementById("accountPinNumber").focus();
		return false;
		}
	
	 	var pinNumber=document.getElementById("accountPinNumber").value;
		if(pinNumber.indexOf(0)==0){
		alert('<bean:message bundle="error" key="error.account.invalidpinformat" />');
		document.getElementById("accountPinNumber").focus();
		return false;
		}
		if(IsNumeric(document.getElementById("accountPinNumber").value)==false){
			alert("Pin number field takes positive integer values only");
			document.getElementById("accountPinNumber").focus();
			return false;
		}
		
	
//		**************************************
//removed && parseInt(document.getElementById("accountLevel").value) != 2
		if(parseInt(document.getElementById("accountLevel").value) != 0 && parseInt(document.getElementById("accountLevel").value) != 1)
		{
			if((document.getElementById("circleId").value == "" || document.getElementById("circleId").value == "-1" )  && (document.getElementById("circleIdList").value == "" || document.getElementById("circleIdList").value == "-1"))
			{
				alert('<bean:message bundle="error" key="error.account.circleid.empty" />');
				document.getElementById("circleId").focus();
				return false; 
			}
		if(!(parseInt(document.getElementById("accountLevel").value) == 2 || parseInt(document.getElementById("accountLevel").value) == 14)){
			if((document.getElementById("circleId").value == "0")  && (document.getElementById("circleIdList").value == "" || document.getElementById("circleIdList").value == "0")){
				alert("Select Valid Circle");
				document.getElementById("circleId").focus();
				return false; 
			}
			}
			if(parseInt(document.getElementById("accountLevel").value) != 14 && parseInt(document.getElementById("accountLevel").value) != 2)
				{
					if(document.getElementById("accountZoneId").value==null || document.getElementById("accountZoneId").value=="0"){
						alert('<bean:message bundle="error" key="error.account.zone" />');
						document.getElementById("accountZoneId").focus();
						return false; 
					}
				
				
				
				 	if(document.getElementById("accountCityId").value==null || document.getElementById("accountCityId").value=="0" || document.getElementById("accountCityId").value==""){
						alert('<bean:message bundle="error" key="error.account.city" />');
						document.getElementById("accountCityId").focus();
						return false; 
					} 
				}
		}
		
		//Added for Transaction
		//if block added by aman for restricting alert for finance user
			//if(parseInt(document.getElementById("accountLevel").value) != 14 || parseInt(document.getElementById("accountLevel").value) != 2)
				//{
								//	alert("Accountlevel"+document.getElementById("accountLevel").value);
								//	if(document.getElementById("transactionId").value == "" || document.getElementById("transactionId").value == "-1")
								//	{
								//	alert("Please select Transaction type for TSM. ");
							//		document.getElementById("transactionId").focus();
							//		return false; 
						//	}
			//	}
							
//		***********************************************
		//Commented by SHilpa
		//if(!(document.getElementById("circleId").value==0 && document.getElementById("accountLevelId").value=="3" || document.getElementById("accountLevelId").value=="2")){
	//	 if(document.getElementById("accountZoneId").value==null || document.getElementById("accountZoneId").value=="0"){
		//	alert('<bean:message bundle="error" key="error.account.zone" />');
		//	document.getElementById("accountZoneId").focus();
		//	return false; 
	//	}
		
		
		
		 //if(document.getElementById("accountCityId").value==null || document.getElementById("accountCityId").value=="0" || document.getElementById("accountCityId").value==""){
		//	alert('<bean:message bundle="error" key="error.account.city" />');
		//	document.getElementById("accountCityId").focus();
		//	return false; 
		//} 
	//}
	
	// Commented by SHilpa ends here
		
		 if(document.getElementById("accountAddress").value==null || document.getElementById("accountAddress").value.length=="0"){
			alert('<bean:message bundle="error" key="error.account.accountaddress" />');
			document.getElementById("accountAddress").focus();
			return false; 
		}
  
  		var validAccountAdd = specialCharCheck(document.getElementById("accountAddress").value);
		
		//alert(validAccountAdd);
		
		if(validAccountAdd==false)
		{
			alert("Special Characters are not allowed in Account Address  ");
			document.getElementById("accountAddress").focus();
			return false;
		}
		if(document.getElementById("accountAddress2").value !=null && trimAll(document.getElementById("accountAddress2").value) !=""){
			var validAccountAdd2 = specialCharCheck(document.getElementById("accountAddress2").value);
			if(validAccountAdd2==false){
			alert("Special Characters are not allowed in Alternate Address ");
			document.getElementById("accountAddress2").focus();
			return false;
			}
		}
		if(document.getElementById("status").value==null || document.getElementById("status").value=="0"){
			alert('<bean:message bundle="error" key="errors.user.status_invalid" />');
		    document.getElementById("status").focus();
		    return false; 
		} if(document.getElementById("groupId").value==null || document.getElementById("groupId").value=="0"){
			alert('<bean:message bundle="error" key="errors.user.groupid_required" />');
		    document.getElementById("groupId").focus();
		    return false; 
		}
	if(parseInt(document.getElementById("accountLevel").value) != 14 && parseInt(document.getElementById("accountLevel").value) != 2 ){	
	 if(parseInt(document.getElementById("accountLevel").value) != 0 ){
		if(document.getElementById("parentAccount").value==null || document.getElementById("parentAccount").value==""){
				alert('<bean:message bundle="error" key="error.account.parentAccount" />');
				document.getElementById("searchbutton").focus();
				return false; 
			}  	
		}
		}
       if(document.getElementById("srNumber").value==null || document.getElementById("srNumber").value==""){
			alert("Please Enter SR Number.");
			document.getElementById("srNumber").focus();
			return false;
		}
		var remarks = document.getElementById('remarks').value;
		if(remarks == "" || remarks == null || trimAll(remarks) == ""){
			alert("Please Enter Remarks");
			document.getElementById("remarks").focus();
		 	return false;
		}else if(remarks.length>200){
			alert("Please Enter Remarks less than 200 characters.");
			document.getElementById("remarks").focus();
		 	return false;
		}
	
		if(document.getElementById("approval1").value==null || document.getElementById("approval1").value==""){
			alert("Please Enter Approver 1.");
			document.getElementById("approval1").focus();
			return false;
		}
		   
		   
		var approval1 = document.getElementById("approval1").value;
		var firstCharApp1 = isNumber(approval1.charAt(0));
			if(firstCharApp1==true || approval1.charAt(0)==""){
				alert("Approver 1 Cannot Begin With A Numeric Character Or Blank Space.");
				document.getElementById("approval1").focus();
				return false;
			}
			if(document.getElementById("approval1").value.length < 8)
			{
				alert("Approver 1 Must Contain At Least 8 Characters.");
				document.getElementById("approval1").focus();
				return false;
			}
			var validApproval1 = isAlphaNumeric(approval1);
			if(validApproval1==false){
				alert("Special Characters and Blank Spaces are not allowed in Approver 1");
				document.getElementById("approval1").focus();
				return false;
			}
			if(document.getElementById("approval2").value!=null && document.getElementById("approval2").value!=""){
				var approval2 = document.getElementById("approval2").value;
				var firstCharApp2 = isNumber(approval2.charAt(0));
				if(firstCharApp2==true || approval2.charAt(0)==""){
					alert("Approver 2 Cannot Begin With A Numeric Character Or Blank Space.");
					document.getElementById("approval2").focus();
					return false;
				}
				if(document.getElementById("approval2").value.length < 8)
				{
					alert("Approver 2 Must Contain At Least 8 Characters.");
					document.getElementById("approval2").focus();
					return false;
				}
				var validApproval2 = isAlphaNumeric(approval2);
				if(validApproval2==false){
					alert("Special Characters and Blank Spaces are not allowed in Approver 2");
					document.getElementById("approval2").focus();
					return false;
				}
				
			}
			
		
		var selectedCircleValues ="";
		selectedCircleValues =0;
		//
		if(parseInt(document.getElementById("accountLevel").value) == 2 || parseInt(document.getElementById("accountLevel").value) == 3 || parseInt(document.getElementById("accountLevel").value) == 4  || parseInt(document.getElementById("accountLevel").value) == 5 || parseInt(document.getElementById("accountLevel").value) == 14)
		{
			var	Id = document.forms[0].circleIdList[0].value;
			if(Id != "" &&  document.forms[0].circleIdList.length >1)
			{
			var panfound=false;
			var count=0;
			for (var i=1; i < document.forms[0].circleIdList.length; i++)
		  	 {
		   		 if (document.forms[0].circleIdList[i].selected)
		     	 {
		     	 	count=count+1;
		     		if(selectedCircleValues !=""){
						selectedCircleValues += ",";
		     		}
				     	var selectedValCircle = document.forms[0].circleIdList[i].value.split(",");
				     	if(parseInt(selectedValCircle[0]) == 0)
				     	{
				     		if(!(parseInt(document.getElementById("accountLevel").value) == 2 || parseInt(document.getElementById("accountLevel").value) == 14)){
				     		alert("Select Valid Circle");
				     		document.getElementById("circleIdList").focus();
				     		return false;
				     		}
				     	}
				     	selectedCircleValues += selectedValCircle[0];
				     	//alert("sele is "+selectedValCircle[0]);
				    if(selectedValCircle[0]==0)
			     	{
					     	panfound=true;
					     	
					 }
					     	if(count>1 && panfound==true)
					     	{
					     			alert("Please select either PAN India or Particular Circle.");
				 					document.getElementById("circleIdList").value=-1;
				 					return false;
					     	}
			     	
				     	
		     	 }	
		   	}
		  }
	  }
  // } deleted by aman
		document.forms[0].hiddenCircleIdList.value  = selectedCircleValues;
	    document.forms[0].accountAddress.value=trimAll(document.forms[0].accountAddress.value);
		document.getElementById("flagForCityFocus").value=false;
		document.getElementById("isbillable").value="Y";
		document.getElementById("methodName").value="updateAccount";
		document.forms[0].action ="initCreateAccountItHelp.do?methodName=updateAccount"+"&random="+new Date().toString();
		document.forms[0].submit();
        return true;
        //added by sugandha
        
			var seltdTransactValues ="";
		var	Id = document.forms[0].transactionId[0].value;
		if(Id != "" &&  document.forms[0].transactionId.length >1){
			for (var i=0; i < document.forms[0].transactionId.length; i++)
	  	 {
	   		 if (document.forms[0].transactionId[i].selected)
	     	 {
	     		if(seltdTransactValues !=""){
					seltdTransactValues += ",";
	     		}
	     	var selectedTran = document.forms[0].transactionId[i].value.split(",");
	     	if(parseInt(selectedTran[0]) == -1){
	     		alert("Select Valid Transaction");
	     		document.getElementById("transactionId").focus();
	     		return false;
	     	}
	     	seltdTransactValues += selectedTran[0];
	     	 }
	   		}
  		}
  		
	   	document.forms[0].hiddenTranctionId.value  = seltdTransactValues;
	    document.forms[0].accountAddress.value=trimAll(document.forms[0].accountAddress.value);
		document.getElementById("flagForCityFocus").value=false;
		document.getElementById("methodName").value="updateAccount";
        return true;
	 }
	 
	
	function goBack(){
	 document.getElementById("methodName").value="callSearchAccount";
	}
	function checkKeyPressed(){
		if(addressStatus==false){ 
			if(window.event.keyCode=="13"){
				if(validate()){
			 		document.forms[0].submit();
				}else{
					return false;
				}
			}
		}
	}
	function setAddressStatus(){
 		addressStatus=true;
	}
	function ResetAddressStatus(){
 		addressStatus=false;
	}
	
	function getCity(){
		document.getElementById("methodName").value="getCityForEdit";
		document.getElementById("flagForCityFocus").value=true;
		document.forms[0].submit();
	}
	
	// this function open child window to search and select distributor code 
	function OpenBillableWindow(){
		if(document.getElementById("circleId").value=="0"){
			alert('<bean:message bundle="error" key="error.account.circleid" />');
			document.getElementById("circleId").focus();
			return false; 
		}
		else if(document.getElementById("accountLevel").value=="0"){
			alert('<bean:message bundle="error" key="error.account.accountlevel" />');
			document.getElementById("accountLevel").focus();
			return false; 
		}
		document.submitForm.action="./initCreateAccount.do?methodName=openSearchBillableAccountPage";
		document.submitForm.circleId.value=document.getElementById("circleId").value;
		document.submitForm.parent.value=document.getElementById("parentAccount").value;
		document.submitForm.accountLevel.value=document.getElementById("accountLevel").value;
		document.submitForm.accountId.value=document.getElementById("accountId").value;
	
		displayWindow = window.open('', "newWin", "width =700,height= 220, left=50, top=100, scrollbars=yes,resizable=yes, status=yes");
       	document.submitForm.submit();
	}
	
	
	
	function checkBillable()
	{	
	 	// Add by harbans
		if(parseInt(document.getElementById("accountLevelId").value) == 1 || parseInt(document.getElementById("accountLevelId").value) == 2 || parseInt(document.getElementById("accountLevelId").value) == 3)
		{

		  document.getElementById("showZoneCityId").style.display="none";
		  	if(parseInt(document.getElementById("accountLevelId").value) == 1 || parseInt(document.getElementById("accountLevelId").value) == 2){
			  	document.getElementById("divSearchButton").style.display="none";
			}else{
				document.getElementById("divSearchButton").style.display="inline";
			}
		}
		//Added by sugandha for to get preselected values of circle of circleadmin BFR-61 to DP-Phase-3 parseInt(document.getElementById("accountLevelId").value) == 3
		if(parseInt(document.getElementById("accountLevelId").value) == 3 ||parseInt(document.getElementById("accountLevelId").value) == 4 || parseInt(document.getElementById("accountLevelId").value) == 5 || parseInt(document.getElementById("accountLevelId").value) == 6 || parseInt(document.getElementById("accountLevelId").value) == 15 )
		{
		
			var selectedValCircle = document.getElementById("hiddenCircleIdList").value.split(",");
			for(var j=0; j < selectedValCircle.length; j++){
				for (var i=1; i < document.forms[0].circleIdList.length; i++)
	  	 		{
	  	 			if (document.forms[0].circleIdList[i].value == selectedValCircle[j])
	     	 		{
	     				document.forms[0].circleIdList.options[i].selected = true;
	     			}
	     		
	     		}
	   		}
	   		//alert(document.getElementById("accountZoneId").value);
		getChangeList("zone");
		document.getElementById("accountZoneId").value = document.getElementById("hiddenZoneId").value;
		}
		//added by sugandha
	if(document.getElementById("accountLevel").value == 5 ){

			document.getElementById("transactionTypeLable").style.display="inline";
			document.getElementById("transactionType").style.display="inline";
			
		}else{

			document.getElementById("transactionTypeLable").style.display="none";
			document.getElementById("transactionType").style.display="none";
		}
		
	//added by aman for finance user	
	
	if(document.getElementById("accountLevel").value == 14 )
	
	{	
			
	
		//document.getElementById("parentAccountStyle").style.display="none";
		//document.getElementById("parentColumn").style.display="none";	
		//document.getElementById("showTSMDepositeRow").style.display="none";
		//document.getElementById("depositeTypeTSMLabel").style.display="none";
		//document.getElementById("searchbuton").style.display="none";
		//document.getElementById("searchSalesTSM").style.display="none";
		//document.getElementById("salesTypeTSMLabel").style.display="none";
		document.getElementById("spnsimnotxt").style.display="none";
		document.getElementById("spnsimnofld").style.display="none";
	
		//to hide parent account
		//document.getElementById("parentColumn").style.display="none";
		//document.getElementById("parentAccount").style.display="none";
		document.getElementById("divSearchButton").style.display="none";
		document.getElementById("parentSrch").style.display="none";
		document.getElementById("parentSrchBtn").style.display="none";
		//document.getElementById("remarksCol").style.display="none";
		//document.getElementById("chBtn").style.display="none";
		document.getElementById("showZoneCityId").style.display="none";
		document.getElementById("zone").style.display="none";
		document.getElementById("zone1").style.display="none";
		document.getElementById("city").style.display="none";
		document.getElementById("city1").style.display="none";
		}
		else
		{
		    document.getElementById("parentSrch").style.display="inline";
		    document.getElementById("parentSrchBtn").style.display="inline";
		    document.getElementById("divSearchButton").style.display="inline";
			document.getElementById("spnsimnotxt").style.display="inline";
			document.getElementById("spnsimnofld").style.display="inline";
		}
	//end of changes by aman
	//Added by sugndha to edit circle Admin to make multicircle and hide zone and city
	if(document.getElementById("accountLevel").value == 2 )
	{
		document.getElementById("spnsimnotxt").style.display="inline";
		document.getElementById("spnsimnofld").style.display="inline";
		document.getElementById("showZoneCityId").style.display="none";
		document.getElementById("zone").style.display="none";
		document.getElementById("zone1").style.display="none";
		document.getElementById("city").style.display="none";
		document.getElementById("city1").style.display="none";
		
		//alert("circle admin");
		document.getElementById("parentSrch").style.display="none";
		document.getElementById("parentAccount").style.display="none";
		document.getElementById("divSearchButton").style.display="none";
		document.getElementById("parentSrchBtn").style.display="none";
		
	}
	else
	{
		
	}
	// end of changes by sugandha 
}
	
	
	
	function setCityFlag(){
	 document.getElementById("flagForCityFocus").value=true;
	}
	function setCategoryFlag(){
	 document.getElementById("flagForCategoryFocus").value=true;
	}
	function getCategory(){
			document.getElementById("methodName").value="getCategory";
			document.getElementById("flagForCategoryFocus").value=true;
			document.forms[0].submit();
		} 
	
function getChange(condition)
{	
	var Id = "";
	if(condition == "category"){
		Id = document.getElementById("tradeId").value;
	}
	if(condition == "beat"){
		Id = document.getElementById("accountCityId").value;
	}else
	if(condition == "zone"){
		Id = document.getElementById("circleId").value;
	}else
	if(condition == "city"){
		Id = document.getElementById("accountZoneId").value;
	}
	 var url="initCreateAccountItHelp.do?methodName=getParentCategory&OBJECT_ID="+Id+"&condition="+condition+"&random="+new Date().toString();
	if(window.XmlHttpRequest) {
		req = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		req=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if(req==null) {
		alert("Browser Doesn't Support AJAX");
		return;
	}
	req.onreadystatechange = function(){getOnChange(condition);};
	req.open("POST",url,false);
	req.send();
}
function getOnChange(condition)
{
	if (req.readyState==4 || req.readyState=="complete") 
  	{
		var xmldoc = req.responseXML.documentElement;
		if(xmldoc == null){		
			return;
		}
		optionValues = xmldoc.getElementsByTagName("option");
		var selectObj = "";
		if(condition == "category"){
			selectObj = document.forms[0].tradeCategoryId;
		} 
		if(condition == "beat"){
			selectObj = document.forms[0].beatId;
		}else
		if(condition == "zone"){
			selectObj = document.forms[0].accountZoneId;
		}else
		if(condition == "city"){
			selectObj = document.forms[0].accountCityId;
		}
		selectObj.options.length = optionValues.length + 1;
		for(var i=0; i < optionValues.length; i++)
		{	
			selectObj.options[(i+1)].text = optionValues[i].getAttribute("text");
			selectObj.options[(i+1)].value = optionValues[i].getAttribute("value");
		}
		
		selectObj.value="0";
	}
}


function getChangeList(condition)
{	
	
	var Id = "";
	var selectedCircleValues ="";
		selectedCircleValues =0;
	if(condition == "zone"){
		Id = document.forms[0].circleIdList[0].value;
		
	if(Id != "" && document.forms[0].circleIdList.length >1){
			var panfound=false;
			var count=0;
			for (var i=1; i < document.forms[0].circleIdList.length; i++)
	  	 {
	  	 	
	   		 if (document.forms[0].circleIdList[i].selected)
	     	 {
	     	 count=count+1;
	     		if(selectedCircleValues !=""){
					selectedCircleValues += ",";
	     		}
	     	var selectedValCircle = document.forms[0].circleIdList[i].value.split(",");
	     	if(parseInt(selectedValCircle[0]) == 0 ){
	     	if(!(parseInt(document.getElementById("accountLevel").value) == 2 || parseInt(document.getElementById("accountLevel").value) == 14)){
	     		alert("Select Valid Circle");
	     		}
	     	}
	     	selectedCircleValues += selectedValCircle[0];
	     	//alert(selectedValCircle[0]);
	     	if(selectedValCircle[0]==0)
			     	{
					     	panfound=true;
					     	
					 }
					     	if(count>1 && panfound==true)
					     	{
					     			alert("Please select either PAN India or Particular Circle..");
				 					document.getElementById("circleIdList").value=-1;
				 					return false;
					     	}
			     	
	     	 }
	   	}
   
  }
   
}
	 var url="initCreateAccountItHelp.do?methodName=getParentCategoryList&OBJECT_ID="+selectedCircleValues+"&condition="+condition+"&random="+new Date().toString();
	if(window.XmlHttpRequest) {
		req = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		req=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if(req==null) {
		alert("Browser Doesn't Support AJAX");
		return;
	}
	req.onreadystatechange = function(){getOnChangeList(condition);};
	req.open("POST",url,false);
	req.send();
}
function getOnChangeList(condition)
{
	if (req.readyState==4 || req.readyState=="complete") 
  	{
		var xmldoc = req.responseXML.documentElement;
		if(xmldoc == null){		
			return;
		}
		optionValues = xmldoc.getElementsByTagName("option");
		var selectObj = "";
		
		if(condition == "zone"){
			selectObj = document.forms[0].accountZoneId;
		}else
		if(condition == "state"){
			selectObj = document.forms[0].accountStateId;
		} if(condition == "warehouse"){
			selectObj = document.forms[0].accountWarehouseCode;
		}else
		if(condition == "city"){
			selectObj = document.forms[0].accountCityId;
		}
		selectObj.options.length = optionValues.length + 1;
		for(var i=0; i < optionValues.length; i++)
		{	
			selectObj.options[(i+1)].text = optionValues[i].getAttribute("text");
			selectObj.options[(i+1)].value = optionValues[i].getAttribute("value");
		}
	}
}	

function maxlength(param) {
	var size=100;
	if(param=="1"){
	var address = document.getElementById("accountAddress").value;
    	if (address.length > size) {
    	address = address.substring(0, size);
	    alert ("Account Address  Can Contain 100 Characters only.");
    	document.getElementById("accountAddress").focus();
        return false;
    	}
	}else if (param=="2"){
		var address2 = document.getElementById("accountAddress2").value;
		if (address2.length > size) {
		address2 = address2.substring(0, size);
	    alert ("Altrenate Address Can Contain 100 Characters Only.");
    	document.getElementById("accountAddress2").focus();
    	return false;
	    }
	}
}
	// Code for State by Digvijay Singh start


	// Code for State by Digvijay Singh Ends		

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
  
	// this function open child window to search and select distributor code 
	function OpenParentWindow(){
		if(document.getElementById("accountLevelId") != "7")
		{  //.options[document.getElementById("accountLevel").selectedIndex].text=="Airtel Distributor"){
			document.getElementById("roleName").value="Y";
		}
		else
		{
			document.getElementById("roleName").value="N";
		}
		if(document.getElementById("circleId").value=="" && document.getElementById("circleIdList").value=="")
		{
			alert('<bean:message bundle="error" key="error.account.circleid" />');
			//document.getElementById("circleId").focus();
			return false; 
		}
		else if(document.getElementById("accountLevel").value=="0")
		{
			alert('<bean:message bundle="error" key="error.account.accountlevel" />');
			document.getElementById("accountLevel").focus();
			return false; 
		}
		
			var selectedCircleValues ="";
			selectedCircleValues =0;
		if(document.getElementById("accountLevelId").value== "3"||document.getElementById("accountLevelId").value== "4" || document.getElementById("accountLevelId").value== "5" || document.getElementById("accountLevelId").value== "6" || document.getElementById("accountLevelId").value== "15" )	{
			var	Id = document.forms[0].circleIdList[0].value;
			if(Id != "" &&  document.forms[0].circleIdList.length >1){
				for (var i=1; i < document.forms[0].circleIdList.length; i++)
		  	 {
		   		 if (document.forms[0].circleIdList[i].selected)
		     	 {
		     		if(selectedCircleValues !=""){
						selectedCircleValues += ",";
		     		}
		     	var selectedValCircle = document.forms[0].circleIdList[i].value.split(",");
		     	selectedCircleValues += selectedValCircle[0];
		     	 }
		   	}
	   }
  }
		document.submitForm.action="./initCreateAccountItHelp.do?methodName=openSearchParentAccountPage";
		document.submitForm.circleId.value=document.getElementById("circleId").value;
		document.submitForm.circleIdList.value=selectedCircleValues;
		document.submitForm.parent.value=document.getElementById("parentAccount").value;
		
		//window.open(url,"SerialNo","width=900,height=500,scrollbars=yes,toolbar=no");
		window.open('', "newWin", "width=900,height=450,scrollbars=yes,toolbar=no");
		//displayWindow = window.open('', "newWin", "width=900,height=500,scrollbars=yes,toolbar=no");
       	document.submitForm.submit();
	}   
	
function changeParentText()
{
	document.getElementById("parentAccount").value ="";
	document.forms[0].accountZoneId.options[0].selected = true;
}
function transactionChange()
{
	var acctID = document.getElementById("accountId").value;
	var oldTransID = document.getElementById("hiddenTranctionId").value;
	var transID = document.getElementById("transactionId").value;
	
	if(transID=="-1")
	{
		if(document.getElementById("accountLevelId").value!= 15)
		{
			
			document.getElementById("parentAccount").value ="";
			document.forms[0].accountZoneId.options[0].selected = true;
		}
	}
	else
	{	
		var url="initCreateAccountItHelp.do?methodName=validateTSMTransChange&accountID="+acctID+"&random="+new Date().toString();
		
		if(window.XmlHttpRequest) {
			req = new XmlHttpRequest();
		}else if(window.ActiveXObject) {
			req=new ActiveXObject("Microsoft.XMLHTTP");
		}
		if(req==null) {
			alert("Browser Doesn't Support AJAX");
			return;
		}
		var bolValidate = "false";
		
		req.onreadystatechange = function(){ bolValidate = validateTSMTransChange();};
		req.open("POST",url,false);
		req.send();
		
		if(bolValidate=="false")
		{
			alert("Transaction Type can not be changed as this user is having some distributors attached with him.");
			document.getElementById("transactionId").value = oldTransID;
			return false;
		}
	}
}
function validateTSMTransChange()
{
	if (req.readyState==4 || req.readyState=="complete") 
  	{
		var xmldoc = req.responseXML.documentElement;
		if(xmldoc == null){		
			return "false";
		}
		optionValues = xmldoc.getElementsByTagName("option");
		return optionValues[0].getAttribute("text");
	}
}
</SCRIPT>

</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onkeypress="return checkKeyPressed();" onload="checkBillable();">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">
	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include
			page="../common/dpheader.jsp" flush="" /></TD>
	</TR>
	<TR valign="top">
		<TD width="167" align="left" bgcolor="b4d2e7" valign="top"
			height="100%"><jsp:include page="../common/leftHeader.jsp" flush="" /></TD>
		<TD valign="top" width="100%" height="100%"><html:form action="/initCreateAccountItHelp"
			 focus="accountCode">
			<TABLE border="0" cellspacing="0" cellpadding="0" class="text">
				<TR>
					<TD width="521" valign="top">
					<TABLE width="545" border="0" cellpadding="5" cellspacing="0"
						class="text">
						<TR>
							<TD colspan="4" class="text"><BR>
							<IMG src="<%=request.getContextPath()%>/images/editAccount.gif" width="505" height="22" alt=""></TD>
						</TR>
					<TR>
						<TD colspan="4">
							<FONT color="RED"><STRONG> <html:errors
							bundle="view" property="message.account" /> <html:errors
							bundle="error" property="error.account" /> </STRONG></FONT>
						</TD>
					</TR>
						<TR>
							<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.accountCode" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="155"><FONT color="#003399"> <html:text
								property="accountCode" styleClass="box" styleId="accountCode"
								size="19" maxlength="9" name="DPCreateAccountITHelpFormBean" tabindex="1"
								readonly="false" /> </FONT><FONT color="#ff0000" size="-2"></FONT></TD>
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.accountName" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="171"><FONT color="#3C3C3C"> <html:text
								property="accountName" styleClass="box" styleId="accountName"
								size="19" maxlength="100" tabindex="2" onkeypress="alphaNumWithSpace()"/> </FONT></TD>
						</TR>
						<TR>		
							<TD width="121" class="text" nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.contactName" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="176"><FONT color="#3C3C3C"> <html:text
								property="contactName" styleClass="box" styleId="contactName"
								onkeypress="alphaNumWithSpace()" size="19" maxlength="100"
								tabindex="3" /> </FONT></TD>
							<TD width="121" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.email" /></FONT>:</STRONG></TD>
							<TD width="171"><FONT color="#3C3C3C"> <html:text
								property="email" styleClass="box" styleId="email" size="19"
								tabindex="4" maxlength="64" name="DPCreateAccountITHelpFormBean" /> </FONT></TD>		
						</TR>
						<TR>
							<TD style="display: none;inline;" width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><span id="spnsimnotxt" name="spnsimnotxt">
								<bean:message bundle="view" key="account.simNumber" /></span></FONT> :</STRONG></TD>
							<TD style="display: none;" width="155"><span id="spnsimnofld" name="spnsimnofld"><FONT color="#003399"> <html:text
								property="simNumber" styleClass="box" styleId="simNumber"
								size="20" maxlength="20" tabindex="5"
								name="DPCreateAccountITHelpFormBean" /> </FONT><FONT
								color="#ff0000" size="-2"></FONT></span></TD>
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.mobileNumber" /></FONT><FONT color="RED">*</FONT> :<br>(<bean:message key="application.digits.mobile_number" bundle="view" />)</br></STRONG></TD>
							<TD width="171"><FONT color="#3C3C3C"> <html:text
								property="mobileNumber" styleClass="box" styleId="mobileNumber"
								onkeypress="isValidNumber()" tabindex="6" size="19"
								maxlength="10" name="DPCreateAccountITHelpFormBean" /> </FONT></TD>
						</TR>
						<TR>
							
                             <TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.pin" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="171"><FONT color="#3C3C3C"> <html:text
								property="accountPinNumber" tabindex="7" styleClass="box" styleId="accountPinNumber"
								size="19" maxlength="6" onkeypress="isValidNumber()"/> </FONT></TD>  
						
						<TD width="126" class="text pLeft15">
						<STRONG><FONT color="#000000"><bean:message bundle="view" key="account.circleId" /></FONT>
						<FONT color="RED">*</FONT> :</STRONG>
						</TD>
						
						
						
			<!--  			<TD>
							
							<html:select property="circleIdList" tabindex="9" styleClass="w130" multiple="true" style="width:175; height:80px;"
										onchange="changeParentText();getChangeList('zone');">
										<html:option value="-1">
											<bean:message key="application.option.select" bundle="view" />
										</html:option>
										<html:options collection="circleList"
											labelProperty="circleName" property="circleId" />
							</html:select>
						</TD>         -->	
						
	
					<TD>
							<logic:match name="DPCreateAccountITHelpFormBean" property="showCircle" value="A">
								<html:text property="circleName" styleClass="box" styleId="circleName" readonly="true" size="19" maxlength="20" />
							</logic:match>
							<logic:match name="DPCreateAccountITHelpFormBean" property="showCircle" value="N">
							<html:select property="circleIdList" styleId="circleIdList"  tabindex="9" styleClass="w130" multiple="true" style="width:175; height:80px;"
										onchange="changeParentText();getChangeList('zone');"> 
										<html:option value="-1">
											<bean:message key="application.option.select" bundle="view" />
										</html:option>
										<html:options collection="circleList"
											labelProperty="circleName" property="circleId" />
							</html:select>
							</logic:match>
						</TD>
					</TR>
					
						<TR id="showZoneCityId" style="display: inline;">			
							<TD id="zone" width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.zone" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD id="zone1" width="155"><FONT color="#003399">
									<html:select property="accountZoneId" tabindex="8" disabled="false"
										styleClass="w130" onchange="getChange('city')">
										<html:option value="0">
											<bean:message key="application.option.select" bundle="view" />
										</html:option>
										<html:options collection="zoneList"
											labelProperty="accountZoneName" property="accountZoneId" />
									</html:select>
									</FONT></TD>
							<TD id="city" width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.city" /></FONT> :</STRONG></TD>
							<TD id="city1" width="155"><FONT color="#003399">
									<html:select property="accountCityId" tabindex="9" disabled="false"
										styleClass="w130">
										<html:option value="0">
											<bean:message key="application.option.select" bundle="view" />
										</html:option>
										<html:options collection="cityList"
											labelProperty="accountCityName" property="accountCityId" />
									</html:select></FONT>
							</TD>		
						</TR>
						
						<TR>
							<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.accountAddress" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="175"><FONT color="#003399"> <html:textarea
								property="accountAddress" styleId="accountAddress" tabindex="10"
								onfocus="setAddressStatus()" onblur="ResetAddressStatus()"  
								cols="17" rows="3" onkeyup="maxlength('1');"></html:textarea> </FONT><FONT color="#ff0000"
								size="-2"></FONT></TD>
							<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.accountAddress2" /> :</STRONG></TD>
							<TD  width="176"><FONT color="#003399"> <html:textarea onkeyup="maxlength('2');"  
								property="accountAddress2" styleId="accountAddress2" tabindex="11"
								cols="17" rows="3"></html:textarea> </FONT><FONT color="#ff0000"
								size="-2"></FONT></TD>
						</TR>
						
						<tr id="showStatusParent" style="display: inline;">
							<TD width="120" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.status" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="163"><FONT color="#003399"> <html:select
								property="status" styleClass="w130" tabindex="20">
								<html:option value="A">
									<bean:message bundle="view" key="application.option.active" />
								</html:option>
								<html:option value="I">
									<bean:message bundle="view" key="application.option.suspend" />
								</html:option>
							</html:select> </FONT></TD>
							<TD width="126" class="text pLeft15"><STRONG><FONT
									color="#000000"><bean:message key="user.groupid"
									bundle="view" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
								<TD width="175"><FONT color="#003399"> <html:text
									property="groupName" styleClass="w130" readonly="true"/>
									</FONT><a href="#"
									onClick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.groupid" />','Information','width=500,height=100,resizable=yes')">
								<b> ? </b> </a></TD>
						</TR>
						
						
						<TR>
							<TD id="parentSrch" width="126" class="text pLeft15" nowrap style="display: none;"> <STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.parentAccount" /></FONT><FONT color="RED">*</FONT>:</STRONG></TD>
							<TD width="163" id="parentSrchBtn" style="display: none;"><FONT color="#003399"> 
							<html:text 	property="parentAccount" styleClass="box" size="19" maxlength="20" readonly="true"/> </FONT>
								<div id="divSearchButton" style="display:none;">
								<INPUT type="button" tabindex="18" name="searchbutton" 	id="searchbutton" class="submit" value='Find'
									onclick="OpenParentWindow()" />
									</div></TD>
									<TD width="126" class="text pLeft15" id="transactionTypeLable" style="display: none;"><STRONG><FONT
								color="#000000">Transaction Type</FONT><FONT color="RED">*</FONT> :</STRONG>
							</TD>
							<TD width="175" id="transactionType" style="display: none;"><FONT color="#003399" >
								<html:select name="DPCreateAccountITHelpFormBean" property="transactionId" onchange="transactionChange();" tabindex="11" styleClass="w130" style="width:175; height:50px;">
								<html:option value="-1">
								<bean:message key="application.option.select" bundle="view" />
								</html:option>
								<html:options collection="transactionTypeList" labelProperty="strText" property="strValue" />
								</html:select></FONT>
							</TD>
						</TR>

						
						<tr id="showCategory" style="display: inline;">
						
							<TD width="121" class="text"><STRONG><FONT
								color="#000000">
								<div id="employeeid">
								<bean:message key="account.EmployeeCode" bundle="view"/>
								<FONT color="RED">*</FONT>
								</div>
								</FONT> :
							</STRONG></TD>
							<TD width="176"><FONT color="#3C3C3C"> <html:text
								property="code" styleClass="box" styleId="code" tabindex="22"
								onkeypress="isValidNumber()" size="19"
								name="DPCreateAccountITHelpFormBean" maxlength="10" readonly="true"/> </FONT> <a href="#"
								onClick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.code" />','Information','width=500,location=no,height=100,resizable=yes')">
							<b> ? </b> </a></TD>
						</TR>
					  <tr id="SRFireld">
							<TD width="126" class="text pLeft15" nowrap="nowrap"><STRONG> <FONT	color="#000000"> 
								SR Number</FONT><font color="red">*</FONT>:</STRONG></TD>
							<TD width="175" nowrap="nowrap"><FONT color="#003399"> <html:text
								property="srNumber" styleClass="box" styleId="srNumber"
								size="16" maxlength="50" tabindex="41" /> </FONT></TD>
							<TD id="remarksCol" width="121" class="text"><STRONG> <FONT
								color="#000000"> Remarks</FONT><FONT color="RED">*</FONT>:</STRONG></TD>

							<TD id="remarksBox" width="176"><FONT color="#003399"> </FONT><html:textarea
								property="remarks" styleClass="box" styleId="remarks"/></TD>
							</tr>
					  <tr id="approval">
							<TD width="126" class="text pLeft15" nowrap="nowrap"><STRONG> <FONT	color="#000000"> 
								Approver 1</FONT><font color="red">*</FONT>:</STRONG></TD>
							<TD width="175" nowrap="nowrap"><FONT color="#003399"> <html:text
								property="approval1" styleClass="box" styleId="approval1"
								size="16"  tabindex="42"  maxlength="9" /> </FONT></TD>
							<TD width="121" class="text"><STRONG> <FONT	color="#000000"> 
								Approver 2</FONT>:</STRONG></TD>
							<TD width="176"><FONT color="#003399"> <html:text
								property="approval2" styleClass="box" styleId="approval2"
								size="19"  tabindex="43"  maxlength="9" /> </FONT></TD>
								
						</tr>	
							 
					  
						<tr>
						 <td>
							<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
								<TR align="center">
									<TD width="70"><INPUT class="submit" type="button"
										tabindex="25" name="Submit1" value="Submit"
										onclick="return validate(); "></TD>
									<TD><INPUT class="submit" type="submit" tabindex="26"
										name="BACK" value="Back" onclick="goBack()"></TD>
								</TR>
							</TABLE>
							</TD>
						</TR>
						
						<TR>
							<TD colspan="4" align="center">&nbsp;</TD>
							<html:hidden property="methodName" value="updateAccount" />
							<html:hidden property="accountId" />
							<html:hidden property="searchFieldName" styleId="searchFieldName" />
							<html:hidden property="circleId" styleId="circleId" />
							<html:hidden property="startDt" styleId="startDt" />
							<html:hidden property="accountLevel" styleId="accountLevel" />
							<html:hidden property="accountLevelId" styleId="accountLevelId" />
							<html:hidden property="endDt" styleId="endDt" />
							<html:hidden property="showBillableCode" styleId="showBillableCode" />
							<html:hidden property="parentAccountId" styleId="parentAccountId" />
							<html:hidden property="billableCodeId" styleId="billableCodeId" />
							<html:hidden styleId="flagForCityFocus" property="flagForCityFocus" />
							<html:hidden styleId="flagForCategoryFocus" property="flagForCategoryFocus" />
							<html:hidden property="userType" styleId="userType" />
							<html:hidden property="page" styleId="page" />
 							<!--<html:hidden property="tradeCategoryId" styleId="tradeategoryId" />	 -->
							<html:hidden property="tradeId" styleId="tradeId" /> 
							<html:hidden property="searchFieldValue" styleId="searchFieldValue" />
							<html:hidden property="accountStatus" styleId="accountStatus" />
							<html:hidden property="groupId" styleId="groupId" />
							<html:hidden property="heirarchyId" styleId="heirarchyId" />
							<html:hidden property="isbillable" styleId="isbillable"/> 
							<html:hidden property="hiddenZoneId" styleId="hiddenZoneId"/> 
							<html:hidden property="isERR" styleId="isERR"/> 
							<!-- <html:hidden property="accountCityId" styleId="accountCityId"/> -->
							<html:hidden property="hiddenCircleIdList" styleId="hiddenCircleIdList" name="DPCreateAccountITHelpFormBean" />
				
							<html:hidden property="beatId" styleId="beatId"/>
							<html:hidden property="roleName" value="N"/>
							<html:hidden property="parentLoginName" name="DPCreateAccountITHelpFormBean"/>
							<html:hidden property="billableLoginName" name="DPCreateAccountITHelpFormBean"/>
							<html:hidden property="parentAccountName" name="DPCreateAccountITHelpFormBean"/>
							<html:hidden property="billableAccountName" name="DPCreateAccountITHelpFormBean"/>
							<html:hidden property="hiddenTranctionId" styleId="hiddenTranctionId" name="DPCreateAccountITHelpFormBean" />
						</TR>
					</TABLE>
					</TD>
				</TR>
			</TABLE>
		</html:form>
		<div id="submitFormDiv" style="display:none;">
			<form name="submitForm" method="post" target="newWin">
			  <input type="hidden" name="circleId">
			  <input type="hidden" name="parent">
			  <input type="hidden" name="accountId">
			  <input type="hidden" name="accountLevel">
			  <input type="hidden" name="accountLevelId">
			  <input type="hidden" name="circleIdList">
			</form>	
		</div>
		</TD>
	</TR>
	<TR align="center" bgcolor="4477bb" valign="top">
		<TD colspan="2" height="17" align="center"><jsp:include
			page="../common/footer.jsp" flush="" /></TD>
	</TR>
</TABLE>
<SCRIPT type="text/javascript">

</SCRIPT>
</BODY>
</html:html>