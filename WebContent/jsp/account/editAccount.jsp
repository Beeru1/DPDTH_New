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


	var addressStatus=false;
	// function that checks that is any field value is blank or not
	function validate(){
		//check if page is not submitted by state field to get city 	
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
		if(!(document.getElementById("simNumber").value==null || document.getElementById("simNumber").value=="")){
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
		if(parseInt(document.getElementById("accountLevelId").value) != 9)
        {
		var mobileno = document.getElementById("mobileNumber").value; //neetika
		 if(mobileno==null || mobileno==""){
			alert('<bean:message bundle="error" key="error.account.mobilenumber" />');
			document.getElementById("mobileNumber").focus();
			return false; 
		}
		if(mobileno != null || mobileno != ""){
		var firstChar = mobileno.charAt(0);
		/*
		if(firstChar != "9"){
			alert("Mobile Number must start with '9'");
			document.getElementById("mobileNumber").focus();
			return false;
		}*/
		 if((document.getElementById("mobileNumber").value.length)<10){
			alert('<bean:message bundle="error" key="error.account.mobilenumberlength" />');
			document.getElementById("mobileNumber").focus();
			return false;
		}
		}
		}
		 if(parseInt(document.getElementById("accountLevelId").value) == 9)
        {
        var status = document.getElementById("status").value;
        //alert("Neetika"+status);
        if(status=="L")//Neetika
		{
		
			var no = document.getElementById("retailerMobileNumber").value; 
			//alert("Neetika"+status+"no"+no);
			 if(!(no==null || no=="")){
			alert('Please make Lapu Number as blank');
			document.getElementById("retailerMobileNumber").focus();
			return false; 
			}
		}
		else
		{
		var rmobileno = document.getElementById("retailerMobileNumber").value; //neetika
		 if(rmobileno==null || rmobileno==""){
			alert('<bean:message bundle="error" key="error.account.lapuMobileNo" />');
			document.getElementById("retailerMobileNumber").focus();
			return false; 
		}
		if(rmobileno != null || rmobileno != ""){
		var firstChar = rmobileno.charAt(0);
		/*
		if(firstChar != "9"){
			alert("Mobile Number must start with '9'");
			document.getElementById("mobileNumber").focus();
			return false;
		}*/
		 if((document.getElementById("retailerMobileNumber").value.length)<10){
			alert('<bean:message bundle="error" key="error.account.lapumobilenumberlength" />');
			document.getElementById("retailerMobileNumber").focus();
			return false;
		}
		}
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
		
		if((document.getElementById("accountStateId").value==null || document.getElementById("accountStateId").value=="") && parseInt(document.getElementById("accountLevel").value) == 6){
			alert('<bean:message bundle="error" key="error.account.state" />');
			document.getElementById("accountStateId").focus();
			return false; 
		}
		
		
		if(!(document.getElementById("circleId").value==0 && document.getElementById("accountLevelId").value=="3" || document.getElementById("accountLevelId").value=="2")){
		 if(document.getElementById("accountZoneId").value==null || document.getElementById("accountZoneId").value=="0"){
			alert('<bean:message bundle="error" key="error.account.zone" />');
			document.getElementById("accountZoneId").focus();
			return false; 
		}
		
		//added by ARTI for warehaouse list box BFR -11 release2
		if((document.getElementById("accountWareHouseCode").value==null || document.getElementById("accountWareHouseCode").value=="") && parseInt(document.getElementById("accountLevel").value) == 6){
			alert('<bean:message bundle="error" key="error.account.warehouse" />');
			document.getElementById("accountWareHouseCode").focus();
			return false; 
		}
		//ended by ARTI for warehaouse list box BFR -11 release2
		
		 if(document.getElementById("accountCityId").value==null || document.getElementById("accountCityId").value=="0" || document.getElementById("accountCityId").value==""){
			alert('<bean:message bundle="error" key="error.account.city" />');
			document.getElementById("accountCityId").focus();
			return false; 
		} 
	}
		
		 if(document.getElementById("accountAddress").value==null || document.getElementById("accountAddress").value.length=="0"){
			alert('<bean:message bundle="error" key="error.account.accountaddress" />');
			document.getElementById("accountAddress").focus();
			return false; 
		}
/*		if(document.getElementById("accountAddress2").value==null || document.getElementById("accountAddress2").value==""){
			alert('<bean:message bundle="error" key="error.account.accountaddressAlternate" />');
			document.getElementById("accountAddress2").focus();
			return false; 
		} 	*/
     if(parseInt(document.getElementById("accountLevelId").value) > 6 && document.getElementById("heirarchyId").value=="1")
     {
 		//alert('Level ID : ' +  parseInt(document.getElementById("accountLevelId").value));
        if(parseInt(document.getElementById("accountLevelId").value) != 9)
        {
	 		if(document.getElementById("lapuMobileNo").value==null|| document.getElementById("lapuMobileNo").value=="")
	 		{
				alert('<bean:message bundle="error" key="error.account.lapuMobileNo" />');
				document.getElementById("lapuMobileNo").focus();
				return false; 
			}
			if(document.getElementById("lapuMobileNo").value != "" || document.getElementById("lapuMobileNo").value != null)
			{
				var lapu = document.getElementById("lapuMobileNo").value;
				var firstChar = lapu.charAt(0);
				/*
				if(firstChar != "9"){
					alert("Lapu Mobile Number must start with '9'");
					document.getElementById("lapuMobileNo").focus();
					return false;
				}*/
				if((document.getElementById("lapuMobileNo").value.length)<10)
				{
					alert('<bean:message bundle="error" key="error.account.lapumobilenumberlength" />');
					document.getElementById("lapuMobileNo").focus();
					return false;
				}
			}
		
		
			if(!(document.getElementById("lapuMobileNo1").value == "" || document.getElementById("lapuMobileNo1").value == null))
			{
				var firstChar = document.getElementById("lapuMobileNo1").value.charAt(0);
				/*
				if(firstChar != "9"){
					alert("Lapu Mobile Number must start with '9'");
					document.getElementById("lapuMobileNo1").focus();
					return false;
				}*/
				if((document.getElementById("lapuMobileNo1").value.length)<10){
					alert('<bean:message bundle="error" key="error.account.altlapumobilenumberlength" />');
					document.getElementById("lapuMobileNo1").focus();
					return false;
				}
			}
		
			if(!(document.getElementById("FTAMobileNo").value == "" || document.getElementById("FTAMobileNo").value == null))
			{
				var firstChar = document.getElementById("FTAMobileNo").value.charAt(0);
				/*
				if(firstChar != "9"){
					alert("FTA Mobile Number must start with '9'");
					document.getElementById("FTAMobileNo").focus();
					return false;
				}*/		
				if((document.getElementById("FTAMobileNo").value.length)<10)
				{
					alert('<bean:message bundle="error" key="error.account.ftamobilenumberlength" />');
					document.getElementById("FTAMobileNo").focus();
					return false;
				}
			}
			if(!(document.getElementById("FTAMobileNo1").value == "" || document.getElementById("FTAMobileNo1").value == null)){
			var firstChar = document.getElementById("FTAMobileNo1").value.charAt(0);
			/*
			if(firstChar != "9"){
				alert("FTA Mobile Number must start with '9'");
				document.getElementById("FTAMobileNo1").focus();
				return false;
			}*/
			if((document.getElementById("FTAMobileNo1").value.length)<10){
				alert('<bean:message bundle="error" key="error.account.altftamobilenumberlength" />');
				document.getElementById("FTAMobileNo1").focus();
				return false;
			}
			}
		}
		//Neetika
		else   if(parseInt(document.getElementById("accountLevelId").value) == 9)
        {
	 		//alert("Mobile 1 "+document.getElementById("mobile1").value);
			if(!(document.getElementById("mobile1").value == "" || document.getElementById("mobile1").value == null))
			{
				var mobile1 = document.getElementById("mobile1").value;
				var firstChar = mobile1.charAt(0);
				/*
				if(firstChar != "9"){
					alert("Lapu Mobile Number must start with '9'");
					document.getElementById("lapuMobileNo").focus();
					return false;
				}*/
				if((document.getElementById("mobile1").value.length)<10)
				{
					alert('<bean:message bundle="error" key="error.account.mobile1length" />');
					document.getElementById("mobile1").focus();
					return false;
				}
			}
		
		
			if(!(document.getElementById("mobile2").value == "" || document.getElementById("mobile2").value == null))
			{
				var firstChar = document.getElementById("mobile2").value.charAt(0);
				/*
				if(firstChar != "9"){
					alert("Lapu Mobile Number must start with '9'");
					document.getElementById("lapuMobileNo1").focus();
					return false;
				}*/
				if((document.getElementById("mobile2").value.length)<10){
					alert('<bean:message bundle="error" key="error.account.mobile2length" />');
					document.getElementById("mobile2").focus();
					return false;
				}
			}
		
			if(!(document.getElementById("mobile3").value == "" || document.getElementById("mobile3").value == null))
			{
				var firstChar = document.getElementById("FTAMobileNo").value.charAt(0);
				/*
				if(firstChar != "9"){
					alert("FTA Mobile Number must start with '9'");
					document.getElementById("FTAMobileNo").focus();
					return false;
				}*/		
				if((document.getElementById("mobile3").value.length)<10)
				{
					alert('<bean:message bundle="error" key="error.account.mobile3length" />');
					document.getElementById("mobile3").focus();
					return false;
				}
			}
			if(!(document.getElementById("mobile4").value == "" || document.getElementById("mobile4").value == null)){
			var firstChar = document.getElementById("mobile4").value.charAt(0);
			/*
			if(firstChar != "9"){
				alert("FTA Mobile Number must start with '9'");
				document.getElementById("FTAMobileNo1").focus();
				return false;
			}*/
			if((document.getElementById("mobile4").value.length)<10){
				alert('<bean:message bundle="error" key="error.account.mobile4length" />');
				document.getElementById("mobile4").focus();
				return false;
			}
			}
		}
		
		
		// end account check
/*		if(document.getElementById("rate").value==null || document.getElementById("rate").value==""){
			alert('<bean:message bundle="error" key="error.account.rate" />');
			document.getElementById("rate").focus();
		    return false; 
		}
		if(isAmount(document.forms[0].rate.value,'<bean:message bundle="view" key="account.rate" />',true)==false){
			document.forms[0].rate.focus();
			return false;	
		}
		
		if(parseFloat(document.getElementById("rate").value)==0){
			alert('<bean:message bundle="error" key="error.account.invalidrate" />');
			document.getElementById("rate").focus();
			return false;
		}
	    if(parseFloat(document.getElementById("rate").value)>100){
			alert('<bean:message bundle="error" key="error.account.invalidrate" />');
			document.getElementById("rate").focus();
			return false;
		}
		if(isAmount(document.forms[0].threshold.value,'<bean:message bundle="view" key="account.threshold" />',false)==false){
			document.forms[0].threshold.focus();
			return false;	
		}	*/
		// harbans 
		//Added by nazim hussain as for edit account it is not required for retailers only
		
		if(parseInt(document.getElementById("accountLevelId").value) == 9 &&
			(document.getElementById("altChannelType").value==null 
			|| document.getElementById("altChannelType").value=="0"))
		{
			alert('Please select alternate channel.');
		    document.getElementById("altChannelType").focus();
		    return false; 
		}
		
		if(parseInt(document.getElementById("accountLevelId").value) == 8 &&
			(document.getElementById("transactionId").value==null 
			|| document.getElementById("transactionId").value=="-1"))
		{
			alert('Please select Transaction type for retailer.');
		    document.getElementById("transactionId").focus();
		    return false; 
		}
		if(document.getElementById("outletType").value==null || document.getElementById("outletType").value=="0"){
			alert('Please select market.');
		    document.getElementById("outletType").focus();
		    return false; 
		}
		
		if(document.getElementById("tradeId").value==null || document.getElementById("tradeId").value=="0"){
			alert('<bean:message bundle="error" key="error.account.tradeId" />');
			document.getElementById("tradeId").focus();
			return false; 
		}
		if(document.getElementById("tradeCategoryId").value==null || document.getElementById("tradeCategoryId").value=="0"){
			alert('<bean:message bundle="error" key="error.account.tradeCategoryId" />');
			document.getElementById("tradeCategoryId").focus();
			return false; 
		}
	}
		if(document.getElementById("status").value==null || document.getElementById("status").value=="0"){
			alert('<bean:message bundle="error" key="errors.user.status_invalid" />');
		    document.getElementById("status").focus();
		    return false; 
		} if(document.getElementById("category").value==null || document.getElementById("category").value==""){
			alert('<bean:message bundle="error" key="error.account.category" />');
			document.getElementById("category").focus();
			return false; 
		} if(document.getElementById("groupId").value==null || document.getElementById("groupId").value=="0"){
			alert('<bean:message bundle="error" key="errors.user.groupid_required" />');
		    document.getElementById("groupId").focus();
		    return false; 
		} if(document.getElementById("parentAccountId").value!="0"){
			if(document.getElementById("isbillable").value==null || document.getElementById("isbillable").value=="0"){  
				 if(!document.getElementById("isbillable").disabled){
						alert('<bean:message bundle="error" key="error.account.billable" />');
						document.getElementById("isbillable").focus();
						return false; 
					}
					else{
					 	document.getElementById("isbillable").value="Y";
					}
		       }else if(document.getElementById("isbillable").value=="N"){  
					  if(document.getElementById("showBillableCode").value=="Y"){
					        if(document.getElementById("billableCode").value==null || document.getElementById("billableCode").value==""){
								alert('<bean:message bundle="error" key="error.account.billablecode" />');
								document.getElementById("searchBillablebutton").focus();
								return false; 
							}
						}
					}	
		}
/*		if(document.getElementById("tinNo").disable==false){
			if(document.getElementById("tinNo").value == null || document.getElementById("tinNo").value == ""){
				alert("Enter TIN No.");
				document.getElementById("tinNo").focus();
			}
		} */
		if(document.getElementById("serviceTax").disable==false){
			if(document.getElementById("serviceTax").value == null || document.getElementById("serviceTax").value == ""){
				alert("Enter Service Tax No.");
				document.getElementById("serviceTax").focus();
			}
		}
		if(document.getElementById("panNo").disable==false){
			if(document.getElementById("panNo").value == null || document.getElementById("panNo").value == ""){
				alert("Enter PAN No.");
				document.getElementById("panNo").focus();
			}
		}
		if(document.getElementById("cstNo").disable==false){
			if(document.getElementById("cstNo").value == null || document.getElementById("cstNo").value == ""){
				alert("Enter CST No.");
				document.getElementById("cstNo").focus();
			}
		}
/*		if(document.getElementById("reportingEmail").disable==false){
			if(document.getElementById("reportingEmail").value == null || document.getElementById("reportingEmail").value == ""){
				alert("Enter Reporting Email Id");
				document.getElementById("reportingEmail").focus();
			}
		}	
*/			
		if(document.getElementById("accountLevelId").value=='7'){
			 if(document.getElementById("distLocator").value == null || document.getElementById("distLocator").value == "")
			{
				alert("Enter Distributor Locator Code.");
				document.getElementById("distLocator").focus();
				return false;
			}
			//Added by Shilpa
			var strString = document.getElementById("distLocator").value;
			var isValidLocator = 	checkLocator(strString);
			if(!isValidLocator){
				alert("Invalid Distributor Locator Code.");
				return false;
			}
		
		
			//Added by Shilpa Ends here
			
			//Commented by Shilpa
			/*
			var strString = document.getElementById("distLocator").value;
			var strArray = strString.split(".");
		    if(strArray[0].length!=3){
				alert("Invalid Distributor Locator Code.");
				document.getElementById("distLocator").focus();
				return false;
   			}
   			
   			var strChar;
			var dotCount =0;
   			if (strString.length != 0) 
   			{
	     		for (var i = 0; i < strString.length; i++) 
	     		{
		      		 strChar = strString.charAt(i);
		      		 if(strChar == ".")
		      		 {
		      			dotCount ++;
		      		 }
		  		}
			  	if(dotCount !=3)
			  	{
					alert("Invalid Distributor Locator Code.");
					document.getElementById("distLocator").focus();
					return false;
			  	}
      	    } */
      	}    
      	    
		   
			
/*		if(document.getElementById("accountLevelId").value=='7'){
		var emailID1=document.forms[0].reportingEmail;
		var errorMsg ='<bean:message bundle="error" key="errors.user.email_invalid" />'; 
	    if(!(document.getElementById("reportingEmail").value==null || document.getElementById("reportingEmail").value=="")){
			if (!(isEmail(emailID1.value, errorMsg))){
				emailID1.focus();
				return false;
			}
		}
		}   */
		
			if(document.getElementById("octroiCharge").disable==false){
			if(document.getElementById("octroiCharge").value == null || document.getElementById("octroiCharge").value == ""){
				alert("Select Octroi Applied On");
				document.getElementById("octroiCharge").focus();
			}
		}	
		
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
	
	function setBillableDiv(){
		 // if user type internal 
		 if(document.getElementById("userType").value=="I"){
			    if(document.getElementById("isBillable").value=="Y"){ 
				 	 document.getElementById("div12").style.display="block";
				 	 document.getElementById("div11").style.display="none";
			 	}else{
				 	 document.getElementById("div12").style.display="none";
			 	 	 document.getElementById("div11").style.display="block";
			 	}
		  }	
	}
	
	function checkBillable()
	{
	    if(document.getElementById("showBillableCode").value=="Y" )
	    {
	    	document.getElementById("isBillable").disabled=false;
	    	document.getElementById("tradeId").disabled=false;
	    	document.getElementById("tradeCategoryId").disabled=false;
		} 
		else
		{
			 document.getElementById("tradeCategoryId").disabled=true;
			 document.getElementById("tradeId").disabled=true;
			 document.getElementById("isBillable").disabled=true;
		}
		
		if(parseInt(document.getElementById("accountLevelId").value) >= 7)
		{
			document.getElementById("outletType").disabled=false;
			document.getElementById("lapuMobileNo").readOnly=false;
			document.getElementById("lapuMobileNo1").readOnly=false;
			document.getElementById("FTAMobileNo").readOnly=false;
			document.getElementById("FTAMobileNo1").readOnly=false;
		 	document.getElementById("uniqueCode").style.display="inline";
		 	document.getElementById("employeeid").style.display="none";
		 	
		 	if(parseInt(document.getElementById("accountLevelId").value) == 9)
		 	{
		 		
		 		document.getElementById("showStatusParent").style.display="inline"; //Neetika 25 Feb
		 		document.getElementById("showStatusParentOther").style.display="none"; //Neetika 25 Feb
		 		document.getElementById("retailerNumber").style.display="inline";
		 		document.getElementById("showRetailerNumber1").style.display="inline";
		 		document.getElementById("showRetailerNumber2").style.display="inline";
		 		document.getElementById("fseNumber").style.display="none";
		 		document.getElementById("showLapuNumber").style.display="none";
		 		document.getElementById("showFTANumber").style.display="none";
		 	}
		 	else
		 	{
		 		document.getElementById("showStatusParentOther").style.display="inline"; //Neetika 25 Feb
		 		document.getElementById("showStatusParent").style.display="none"; //Neetika 25 Feb
		 		document.getElementById("fseNumber").style.display="inline";
		 		document.getElementById("showLapuNumber").style.display="inline";
		 		document.getElementById("showFTANumber").style.display="inline";
		 		document.getElementById("retailerNumber").style.display="none";
		 		document.getElementById("showRetailerNumber1").style.display="none";
		 		document.getElementById("showRetailerNumber2").style.display="none";
		 	}
		}else{
			document.getElementById("outletType").disabled=true;
			document.getElementById("lapuMobileNo").readOnly="readonly";
			document.getElementById("lapuMobileNo1").readOnly="readonly";
			document.getElementById("FTAMobileNo").readOnly="readonly";
			document.getElementById("FTAMobileNo1").readOnly="readonly";
		 	document.getElementById("uniqueCode").style.display="none";
		 	document.getElementById("employeeid").style.display="inline";			 		 	
		}
		if(parseInt(document.getElementById("accountLevelId").value) > 7)
		{
			document.getElementById("beatId").disabled=false;
		}else{
			document.getElementById("beatId").disabled=true;
		}
				
		if(parseInt(document.getElementById("accountLevelId").value) == 7)
		{
			document.getElementById("distLocatorBox").style.display="inline";	
		}else{
		 	document.getElementById("distLocatorBox").style.display="none";
		}
		
		if(document.getElementById("accountLevelId").value != 7)
		{
			document.getElementById("tinNo").readOnly="readonly";
			document.getElementById("serviceTax").readOnly="readonly";
			document.getElementById("panNo").readOnly="readonly";
			document.getElementById("cstNo").readOnly="readonly";
//			document.getElementById("reportingEmail").readOnly="readonly";
			document.getElementById("octroiCharge").disabled=true;
		}
		else
		{
			document.getElementById("tinNo").readOnly=false;
			document.getElementById("serviceTax").readOnly=false;
			document.getElementById("panNo").readOnly=false;
			document.getElementById("cstNo").readOnly=false;
//			document.getElementById("reportingEmail").readOnly=false;
			document.getElementById("octroiCharge").disabled=false;
		}
		if(document.getElementById("accountLevelId").value != 9){
			document.getElementById("altChannelType").disabled=true;
			document.getElementById("retailerType").disabled=true;
			document.getElementById("channelType").disabled=true;
			document.getElementById("category").disabled=true;
			document.getElementById("searchbutton").disabled=false;
		}else{
			document.getElementById("altChannelType").disabled=false;
			document.getElementById("retailerType").disabled=false;
			document.getElementById("channelType").disabled=false;
			document.getElementById("category").disabled=false;
			document.getElementById("searchbutton").disabled=false;
		}
		
		
		// Add by Sugandha
		if(parseInt(document.getElementById("accountLevelId").value) == 9)
		{
		  document.getElementById("showDistrict").style.display="inline";
  		  document.getElementById("showDemoId").style.display="inline";
  		  document.getElementById("showDemoId2").style.display="inline";
		}
		else
		{
			document.getElementById("showDistrict").style.display="none";
  		  document.getElementById("showDemoId").style.display="none";
  		  document.getElementById("showDemoId2").style.display="none";
		}
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
	 var url="initCreateAccount.do?methodName=getParentCategory&OBJECT_ID="+Id+"&condition="+condition;
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
	}
}
function maxlength(param) {
	var size=100;
	if(param=="1"){
	var address = document.getElementById("accountAddress").value;
    	if (address.length > size) {
    	address = address.substring(0, size);
	    alert ("Account Address  Can Contain 100 Characters Oonly.");
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

function validateState(){
	if(parseInt(document.getElementById("accountLevel").value) == 6)
		{
			document.getElementById("showStateId").style.display="inline";
		}
		else
		{
			document.getElementById("showStateId").style.display="none";
		}
	}
	// Code for State by Digvijay Singh Ends
	// code added by sugandha to change status if and only if no stock is left with the distributor		
	function validateUserStatus()
	{
		var status = document.getElementById("status").value;
		var	accountId = document.getElementById("accountId").value;
		var levelID = document.getElementById("accountLevelId").value;
		//alert("accountId  ::  "+accountId+"  status  ::  "+status+"  levelID  ::  "+levelID);
		
		if(status=="L")//Neetika
		{
		
			var mobileno = document.getElementById("retailerMobileNumber").value; 
			 if(!(mobileno==null || mobileno=="")){
			alert('Please make Lapu Number as blank');
			document.getElementById("retailerMobileNumber").focus();
			return false; 
			}
		}
		
		if(status=="I" || status=="L"){
			var url = "initCreateAccount.do?methodName=getStockStatus&accountId="+accountId+"&levelID="+levelID;
			if(window.XmlHttpRequest) 
			{
				req = new XmlHttpRequest();
			}else if(window.ActiveXObject) 
			{
				req=new ActiveXObject("Microsoft.XMLHTTP");
			}
			if(req==null) 
			{
				alert("Browser Doesnt Support AJAX");
				return;
			}
			req.onreadystatechange = function(){validateUserStatusDB();};
			req.open("POST",url,false);
			req.send();
		}
		
	}
	
	function validateUserStatusDB()
	{
		if (req.readyState==4 || req.readyState=="complete") 
  		{
  			var xmldoc = req.responseXML.documentElement;
			if(xmldoc == null){		
				return;
			}
			
			optionValues = xmldoc.getElementsByTagName("option");
			
			var msg = optionValues[0].getAttribute("message");
			
			//alert(msg);
			
			if(msg!="OK")
			{
				if(document.getElementById("accountLevelId").value=="8")
					alert("FSE cannot be deactivated as this FSE is having stock with him.");
				else
					alert("Retailer cannot be deactivated as this retailer is having stock with him.");
				
				document.getElementById("status").value='A';
				
				return false;
			}
  		}
  		
  	}
	//code added by sugandha ends here
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
		if(document.getElementById("accountLevelId") != "7"){  //.options[document.getElementById("accountLevel").selectedIndex].text=="Airtel Distributor"){
			document.getElementById("roleName").value="Y";
		}
		else
			document.getElementById("roleName").value="N";
		if(document.getElementById("circleId").value==""){
			alert('<bean:message bundle="error" key="error.account.circleid" />');
			document.getElementById("circleId").focus();
			return false; 
		}else if(document.getElementById("accountLevel").value=="0"){
			alert('<bean:message bundle="error" key="error.account.accountlevel" />');
			document.getElementById("accountLevel").focus();
			return false; 
		}
		document.submitForm.action="./initCreateAccount.do?methodName=openSearchParentAccountPage";
		document.submitForm.circleId.value=document.getElementById("circleId").value;
		document.submitForm.parent.value=document.getElementById("parentAccount").value;
		
		window.open('', "newWin", "width=900,height=450,scrollbars=yes,toolbar=no");
		
		//displayWindow = window.open('', "newWin", "width =700,height= 220, left=50, top=100, scrollbars=yes,resizable=yes, status=yes");
       	
       	document.submitForm.submit();
	} 
	
</SCRIPT>

</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onkeypress="return checkKeyPressed();" onload="checkBillable();validateState();">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">
	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include
			page="../common/dpheader.jsp" flush="" /></TD>
	</TR>
	<TR valign="top">
		<TD width="167" align="left" bgcolor="b4d2e7" valign="top"
			height="100%"><jsp:include page="../common/leftHeader.jsp" flush="" /></TD>
		<TD valign="top" width="100%" height="100%"><html:form
			action="/initCreateAccount" focus="accountCode">
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
								size="19" maxlength="9" name="DPCreateAccountForm" tabindex="1"
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
								tabindex="4" maxlength="64" name="DPCreateAccountForm" /> </FONT></TD>		
						</TR>
						<TR id="fseNumber">
							<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.simNumber" /></FONT> :</STRONG></TD>
							<TD width="155"><FONT color="#003399"> <html:text
								property="simNumber" styleClass="box" styleId="simNumber"
								size="20" maxlength="20" tabindex="5"
								name="DPCreateAccountForm" /> </FONT><FONT
								color="#ff0000" size="-2"></FONT></TD>
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.mobileNumber" /></FONT><FONT color="RED">*</FONT> :<br>(<bean:message key="application.digits.mobile_number" bundle="view" />)</br></STRONG></TD>
							<TD width="171"><FONT color="#3C3C3C"> <html:text
								property="mobileNumber" styleClass="box" styleId="mobileNumber"
								onkeypress="isValidNumber()" tabindex="6" size="19"
								maxlength="10" name="DPCreateAccountForm" /> </FONT></TD>
						</TR>
						
						<TR id="retailerNumber">
							<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.simNumber" /></FONT> :</STRONG></TD>
							<TD width="155"><FONT color="#003399"> <html:text
								property="simNumber" styleClass="box" styleId="simNumber"
								size="20" maxlength="20" tabindex="5"
								name="DPCreateAccountForm" /> </FONT><FONT
								color="#ff0000" size="-2"></FONT></TD>
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.retailer.mobileNumber" /></FONT><FONT color="RED">*</FONT> :<br>(<bean:message key="application.digits.mobile_number" bundle="view" />)</br></STRONG></TD>
							<TD width="171"><FONT color="#3C3C3C"> <html:text
								property="retailerMobileNumber" styleClass="box" styleId="retailerMobileNumber"
								onkeypress="isValidNumber()" tabindex="6" size="19"
								maxlength="10" name="DPCreateAccountForm" /> </FONT></TD>
						</TR>
						
						<TR>
							
                             <TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.pin" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="171"><FONT color="#3C3C3C"> <html:text
								property="accountPinNumber" tabindex="7" styleClass="box" styleId="accountPinNumber"
								size="19" maxlength="6" onkeypress="isValidNumber()"/> </FONT></TD>  
						
						<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.circleId" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD><html:text property="circleName" styleClass="box"
								styleId="circleName" readonly="true" size="19" maxlength="20" /></TD>
					</TR>
					
						<TR id="showZoneCityId" style="display: inline;">			
							<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.zone" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="155"><FONT color="#003399">
									<html:select property="accountZoneId" tabindex="8" disabled="true"
										styleClass="w130" onchange="getChange('city')">
										<html:option value="0">
											<bean:message key="application.option.select" bundle="view" />
										</html:option>
										<html:options collection="zoneList"
											labelProperty="accountZoneName" property="accountZoneId" />
									</html:select>
									</FONT></TD>
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.city" /></FONT> :</STRONG></TD>
							<TD width="155"><FONT color="#003399">
									<html:select property="accountCityId" tabindex="9" disabled="true"
										styleClass="w130" onchange="getChange('beat')">
										<html:option value="0">
											<bean:message key="application.option.select" bundle="view" />
										</html:option>
										<html:options collection="cityList"
											labelProperty="accountCityName" property="accountCityId" />
									</html:select></FONT>
							</TD>		
						</TR>
						<TR id="showStateId" style="display: inline;">
								<TD width="126" class="text pLeft15"><STRONG><FONT
									color="#000000"><bean:message bundle="view"
									key="account.state" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
								<TD width="175"><FONT color="#003399"> <html:select
									property="accountStateId" tabindex="10" styleClass="w130">
									<html:option value="">
										<bean:message key="application.option.select" bundle="view" />
									</html:option>
									<logic:notEmpty name="DPCreateAccountForm" property="stateList">
										<html:options collection="stateList"
											labelProperty="accountStateName" property="accountStateId" />
									</logic:notEmpty>
								</html:select> </FONT></TD>
								<TD width="121" class="text pLeft15"><STRONG><FONT
									color="#000000"><bean:message bundle="view"
									key="account.warehouse" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
								<TD width="175"><FONT color="#003399"> <html:select
									property="accountWarehouseCode" tabindex="11" styleClass="w130">
									<html:option value="">
										<bean:message key="application.option.select" bundle="view" />
									</html:option>
									<logic:notEmpty name="DPCreateAccountForm" property="wareHouseList">
										<html:options collection="wareHouseList"
											labelProperty="accountWarehouseName" property="accountWarehouseCode" />
									</logic:notEmpty>
								</html:select> </FONT></TD>
								<TD width="176"></TD>
						</TR>
						
						<TR id="showDistrict" style="display: none;">
						<TD class="text pLeft15"><STRONG><FONT color="#000000"><bean:message bundle="view"
									key="account.district" /><FONT color="RED">*</FONT> :</STRONG>
							</TD>
							<TD width="175" id="District"><FONT color="#003399" >
							<html:select property="accountDistrictId" name="DPCreateAccountForm" tabindex="12" styleClass="w130" style="width:175; height:50px;">
								<html:option value="-1">
									<bean:message key="application.option.select" bundle="view" />
								</html:option>
								</html:select></FONT></TD>
						<TD class="text pLeft15"><STRONG><FONT color="#000000"><bean:message bundle="view" key="account.transaction" /><FONT color="RED">*</FONT> :</STRONG>
							</TD>
							<TD width="175" id="transactionType"><FONT color="#003399" >
							<html:select property="transactionId"  name="DPCreateAccountForm" tabindex="13" styleClass="w130" style="width:175; height:50px;">
								<html:option value="-1">
									<bean:message key="application.option.select" bundle="view" />
								</html:option>
								<html:options collection="retailerTransList" labelProperty="retailerTypeName" property="retailerTransId" />
								</html:select></FONT></TD>
						</TR>
						<TR>
							<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.accountAddress" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="175"><FONT color="#003399"> <html:textarea
								property="accountAddress" styleId="accountAddress" tabindex="14"
								onfocus="setAddressStatus()" onblur="ResetAddressStatus()"  
								cols="17" rows="3" onkeyup="maxlength('1');"></html:textarea> </FONT><FONT color="#ff0000"
								size="-2"></FONT></TD>
							<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.accountAddress2" /> :</STRONG></TD>
							<TD  width="176"><FONT color="#003399"> <html:textarea onkeypress="maxlength('2');"  
								property="accountAddress2" styleId="accountAddress2" tabindex="15"
								cols="17" rows="3"></html:textarea> </FONT><FONT color="#ff0000"
								size="-2"></FONT></TD>
						</TR>
						<tr id="showLapuNumber" style="display: inline;">
							<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.lapu" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="155"><FONT color="#3C3C3C">
							<html:text property="lapuMobileNo" name="DPCreateAccountForm" maxlength="10" tabindex="16"/>
							</FONT></TD>
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.lapu1" /></FONT> :</STRONG></TD>
							<TD width="171"><FONT color="#3C3C3C">
							<html:text property="lapuMobileNo1" name="DPCreateAccountForm" maxlength="10" tabindex="17"/></FONT></TD>
						</tr>
						<tr id="showFTANumber" style="display: inline;">
							<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.FTA" /></FONT> :</STRONG></TD>
							<TD width="155"><FONT color="#3C3C3C">
							<html:text property="FTAMobileNo" name="DPCreateAccountForm" maxlength="10" tabindex="18"/>
							</FONT></TD>
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.FTA1" /></FONT> :</STRONG></TD>
							<TD width="171"><FONT color="#3C3C3C">
							<html:text property="FTAMobileNo1" name="DPCreateAccountForm" maxlength="10" tabindex="19"/>
							 </FONT></TD>
						</tr>
						
						<tr id="showRetailerNumber1" style="display: none;">
							<TD width="126" class="text pLeft15" nowrap="nowrap"><STRONG>
							<FONT color="#000000"> <bean:message bundle="view"
								key="account.retailer.mobile1" /> : </FONT> </STRONG></TD>
							<TD width="175" nowrap="nowrap"><FONT color="#003399">
							<html:text property="mobile1" styleClass="box" tabindex="22"
								size="16" maxlength="10" onkeypress="isValidNumber()" /> </FONT><a
								href="#"
								onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="account.lapuMobileNo" />','Information','width=500,height=100,resizable=yes')">
							<b> ? </b> </a></TD>
							<TD width="126" class="text pLeft15" nowrap="nowrap"><STRONG>
							<FONT color="#000000"> <bean:message bundle="view"
								key="account.retailer.mobile2" /> : </FONT></STRONG></TD>
							<TD width="175" nowrap="nowrap"><FONT color="#003399">
							<html:text property="mobile2" styleClass="box" tabindex="23"
								size="16" maxlength="10" onkeypress="isValidNumber()" /> </FONT><a
								href="#"
								onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="account.FTAMobileNo" />','Information','width=500,height=100,resizable=yes')">
							<b> ? </b> </a></TD>
						</tr>
						
						<tr id="showRetailerNumber2" style="display: none;">
							<TD width="126" class="text pLeft15" nowrap="nowrap"><STRONG>
							<FONT color="#000000"> <bean:message bundle="view"
								key="account.retailer.mobile3" /> : </FONT> </STRONG></TD>
							<TD width="175" nowrap="nowrap"><FONT color="#003399">
							<html:text property="mobile3" styleClass="box" tabindex="22"
								size="16" maxlength="10" onkeypress="isValidNumber()" /> </FONT><a
								href="#"
								onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="account.lapuMobileNo" />','Information','width=500,height=100,resizable=yes')">
							<b> ? </b> </a></TD>
							<TD width="126" class="text pLeft15" nowrap="nowrap"><STRONG>
							<FONT color="#000000"> <bean:message bundle="view"
								key="account.retailer.mobile4" /> : </FONT></STRONG></TD>
							<TD width="175" nowrap="nowrap"><FONT color="#003399">
							<html:text property="mobile4" styleClass="box" tabindex="23"
								size="16" maxlength="10" onkeypress="isValidNumber()" /> </FONT><a
								href="#"
								onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="account.FTAMobileNo" />','Information','width=500,height=100,resizable=yes')">
							<b> ? </b> </a></TD>
						</tr>
						
						
<%-- 						<TR>
							<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.rate" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="171"><FONT color="#3C3C3C"> <html:text
								property="rate" styleClass="box" size="19" maxlength="5"
								tabindex="20"  onkeypress="isValidRate()"/> </FONT></TD>
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.threshold" /></FONT> :</STRONG></TD>
							<TD width="171"><FONT color="#3C3C3C"> <html:text
								property="threshold" styleClass="box" size="19" tabindex="21"
								maxlength="10" onkeypress="isValidRate()"/> </FONT></TD>
						</TR>	--%>
						<tr id="showTradeCategory" style="display: inline;">
							<TD width="126" class="text pLeft15"><STRONG><FONT color="#000000">
								<bean:message
									bundle="view" key="account.trade" /><FONT color="RED">*</FONT> :</TD>
							<TD width="175" nowrap="nowrap"><FONT color="#003399"> <html:select
								property="tradeId" tabindex="22" styleClass="w130"  disabled="true"
								onchange="getChange('category');">
								<html:option value="0">
									<bean:message key="application.option.select" bundle="view" />
								</html:option>
								<logic:notEmpty  name="DPCreateAccountForm" property="tradeList">
								<bean:define id="tradeList" name="DPCreateAccountForm" property="tradeList"/>
								<html:options collection="tradeList"
									labelProperty="tradeName" property="tradeId"/>
									</logic:notEmpty>
							</html:select> </FONT></TD>
							
							<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000">
								<bean:message
									bundle="view" key="account.distcategory" /><FONT color="RED">*</FONT> :
								</TD>
							<TD width="175"><FONT color="#003399"> <html:select disabled="true"
								property="tradeCategoryId" tabindex="23" styleClass="w130">
								<html:option value="0">
									<bean:message key="application.option.select" bundle="view" />
								</html:option>
								<logic:notEmpty  name="DPCreateAccountForm" property="tradeCategoryList">
								<bean:define id="tradeCategoryList" name="DPCreateAccountForm" property="tradeCategoryList"/>
								<html:options collection="tradeCategoryList"
									labelProperty="tradeCategoryName" property="tradeCategoryId" /></logic:notEmpty>
							</html:select> </FONT></TD>
						</tr>
						<tr id="showStatusParent" style="display: none;">
							<TD width="120" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.status" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="163"><FONT color="#003399"> <html:select onchange="validateUserStatus();"
								property="status" styleClass="w130" tabindex="24">
								<html:option value="A">
									<bean:message bundle="view" key="application.option.active" />
								</html:option>
								<html:option value="I">
									<bean:message bundle="view" key="application.option.inactive" />
								</html:option>
								
								<html:option value="L">
									<bean:message bundle="view" key="application.option.lapuinactive" />
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
						
						
						
						<tr id="showStatusParentOther" style="display: none;">
							<TD width="120" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.status" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="163"><FONT color="#003399"> <html:select onchange="validateUserStatus();"
								property="status" styleClass="w130" tabindex="24">
								<html:option value="A">
									<bean:message bundle="view" key="application.option.active" />
								</html:option>
								<html:option value="I">
									<bean:message bundle="view" key="application.option.inactive" />
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
							<TD width="126" class="text pLeft15" nowrap> <STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.parentAccount" /></FONT><FONT color="RED">*</FONT>:</STRONG></TD>
							<TD width="163"><FONT color="#003399"> 
							<html:text 	property="parentAccount" styleClass="box" size="19" maxlength="20" readonly="true"/> </FONT>
								<INPUT type="button" tabindex="25" name="searchbutton" 	id="searchbutton" class="submit" value='Find'
									onclick="OpenParentWindow()" />
							</TD>
						</TR>
<%-- 							<TD width="121" class="text" nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.openingBalance" /></FONT><FONT color="RED">*</FONT>:</STRONG></TD>
							<TD width="171"><FONT color="#3C3C3C"> <html:text
								property="openingBalance" styleClass="box" size="19"
								maxlength="20" readonly="true" /> </FONT></TD>
						
						<TR>
							<TD width="126" class="text pLeft15" nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.operatingbalance" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="163"><FONT color="#003399"> <html:text
								property="operatingBalance" styleClass="box" size="19"
								maxlength="20" readonly="true" /> </FONT></TD>
							<TD width="121" class="text" nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.availablebalance" /></FONT>:<FONT color="RED">*</FONT></STRONG></TD>
							<TD width="171"><FONT color="#3C3C3C"> <html:text
								property="availableBalance" readonly="true" styleClass="box"
								size="19" maxlength="20" /> </FONT></TD>
						</TR>	--%>
						<TR>
								<TD width="126" class="text pLeft15"><STRONG><FONT
									color="#000000"><bean:message bundle="view"
									key="account.billable" /></FONT><FONT color="RED">*</FONT>:</STRONG></TD>
								<TD width="163" nowrap><FONT color="#003399">
								 <html:select property="isbillable" disabled="true"
										styleClass="w130" onchange="checkBillable(),setBillableDiv()">
								<html:option value="0">
									<bean:message key="application.option.select" bundle="view" />
								</html:option>
										<html:option   value="Y">
											<bean:message key="application.option.yes" bundle="view" />
										</html:option>
										<html:option value="N">
											<bean:message key="application.option.no" bundle="view" />
										</html:option>
									</html:select>	
									</FONT></TD>
							    <TD width="121" class="text"><STRONG><FONT
									color="#000000"><div id="div12" style="display:none;"><bean:message bundle="view"
									key="account.billablecode" />:</div></FONT><div id="div11" style="display:block;"><bean:message bundle="view"
									key="account.billablecode" /><FONT color="RED">*</FONT>:</div></STRONG></TD>
								<TD width="163" nowrap><FONT color="#003399"> <html:text
									property="billableCode" styleClass="box" 
									 size="16" readonly="true" maxlength="30"
									onkeypress="isValidNumAlpha()" /> </FONT>
								</TD>
						</TR>
		<tr id="showCategory" style="display: inline;">
						<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.category" /></FONT>:</STRONG></TD>
							<TD width="155"><FONT color="#003399"> <html:select disabled="true"
								property="category" styleClass="w130" tabindex="26">
								<html:option value="0">
									<bean:message key="application.option.select" bundle="view" />
								</html:option>
								<html:option value="G">
									<bean:message key="account.category.option_gold" bundle="view" />
								</html:option>
								<html:option value="S">
									<bean:message key="account.category.option_silver"
										bundle="view" />
								</html:option>
								<html:option value="P">
									<bean:message key="account.category.option_platinum"
										bundle="view" />
								</html:option>
							</html:select> </FONT><FONT color="#ff0000" size="-2"></FONT></TD>
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><div id="uniqueCode" style="display: none;">
								<bean:message bundle="view"
								key="account.Code" /></div>
								<div id="employeeid" style="display: none;">
								<bean:message key="account.EmployeeCode" bundle="view"/>
								<FONT color="RED">*</FONT>
								</div>
								</FONT> :
							</STRONG></TD>
							<TD width="176"><FONT color="#3C3C3C"> <html:text
								property="code" styleClass="box" styleId="code" tabindex="27"
								onkeypress="isValidNumber()" size="19"
								name="DPCreateAccountForm" maxlength="10" readonly="true"/> </FONT> <a href="#"
								onClick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.code" />','Information','width=500,location=no,height=100,resizable=yes')">
							<b> ? </b> </a></TD>
						</TR>
				<tr id="showBeat" style="display: inline;">
						<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"> <bean:message bundle="view"
								key="beat.beatname" /></FONT> : </STRONG>
						</TD>
						<TD width="175"><FONT color="#003399"> <html:select 
							property="beatId" tabindex="28" styleClass="w130" disabled="true">
							<html:option value="0">
								<bean:message key="application.option.select" bundle="view" />
							</html:option>
							<html:options collection="beatList" property="beatId" labelProperty="beatName"/>
						</html:select> </FONT>
						</TD>
						<TD width="121" class="text"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="account.alternateChannel"/></FONT><font color="red">*</font> :</STRONG></TD>
						<TD width="176"><FONT color="#003399"> <html:select
							property="altChannelType" tabindex="29" styleClass="w130">
							<html:option value="0">
								<bean:message key="application.option.select" bundle="view" />
							</html:option>	
							<logic:notEmpty name="DPCreateAccountForm" property="altChannelTypeList">
							<bean:define id="altChannelTypeList" name="DPCreateAccountForm" property="altChannelTypeList"/>
							<html:options collection="altChannelTypeList"
								labelProperty="altChannelName" property="altChannelType" />
							</logic:notEmpty>	
						</html:select></FONT><FONT color="#ff0000" size="-2"></FONT><a href="#"
							onClick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
							bundle="view" key="detail.category" />','Information','width=500,height=100,resizable=yes')">
						<b> ? </b> </a>
						</TD>
					</tr>
			<tr id="showRetailerType" style="display: inline;">
						<TD width="121" class="text"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="account.retailerType" /></FONT> :</STRONG></TD>
						<TD width="176"><FONT color="#003399"> <html:select
							property="retailerType" tabindex="30" styleClass="w130">
						<html:option value="0">
								<bean:message key="application.option.select" bundle="view" />
							</html:option>
						<logic:notEmpty name="DPCreateAccountForm" property="retailerCatList">
						<bean:define id="retailerCatList" name="DPCreateAccountForm" property="retailerCatList"/>
						<html:options collection="retailerCatList"
								labelProperty="retailerTypeName" property="retailerType" />
						</logic:notEmpty>		
						</html:select></FONT><FONT color="#ff0000" size="-2"></FONT><a href="#"
							onClick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
							bundle="view" key="detail.retailerCategory" />','Information','width=500,height=100,resizable=yes')">
						<b> ? </b> </a></TD>
						<TD width="121" class="text"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="account.outletType" /></FONT><font color="red">*</font> :</STRONG></TD>
						<TD width="176"><FONT color="#003399"> <html:select
							property="outletType" tabindex="31" styleClass="w130">
							<html:option value="0">
								<bean:message key="application.option.select" bundle="view" />
							</html:option>	
							<logic:notEmpty name="DPCreateAccountForm" property="outletList">
							<bean:define id="outletList" name="DPCreateAccountForm" property="outletList"/>
							<html:options collection="outletList"
								labelProperty="outletName" property="outletType" />
							</logic:notEmpty>	
						</html:select></FONT><FONT color="#ff0000" size="-2"></FONT><a href="#"
							onClick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
							bundle="view" key="detail.category" />','Information','width=500,height=100,resizable=yes')">
						<b> ? </b> </a></TD>
						</tr>
				<tr id="showChannelType" style="display: inline;">
						<TD width="121" class="text"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="account.channelType" /></FONT> :</STRONG></TD>
						<TD width="176"><FONT color="#003399"> <html:select
							property="channelType" tabindex="32" styleClass="w130">
						<html:option value="0">
								<bean:message key="application.option.select" bundle="view" />
							</html:option>
							<logic:notEmpty name="DPCreateAccountForm" property="channelTypeList">
							<bean:define id="channelTypeList" name="DPCreateAccountForm" property="channelTypeList"/>
							<html:options collection="channelTypeList"
								labelProperty="channelName" property="channelType" />
							</logic:notEmpty>	
						</html:select></FONT><FONT color="#ff0000" size="-2"></FONT><a href="#"
							onClick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
							bundle="view" key="detail.retailerCategory" />','Information','width=500,height=100,resizable=yes')">
						<b> ? </b> </a></TD>
						</tr>
						
												<!-- Started by sugandha for Retailer demo Id  -->
							<tr id="showDemoId" style="display: none;">
							<TD width="80" class="text pLeft15"><STRONG><FONT
									color="#000000"><bean:message bundle="view"
									key="account.demoId1" /></FONT> :</STRONG></TD>
							<TD width="80"><FONT color="#003399"><html:text
								property="demoId1" tabindex="33" styleClass="box" 
								styleId="demoId" size="19" maxlength="15"
								name="DPCreateAccountForm" onkeypress="alphaNumWithoutSpace()" />
							<TD width="80" class="text pLeft15"><STRONG><FONT
									color="#000000"><bean:message bundle="view"
									key="account.demoId2" /></FONT> :</STRONG></TD>
							<TD width="80"><FONT color="#003399"><html:text
								property="demoId2" tabindex="34" styleClass="box" 
								styleId="demoId" size="19" maxlength="15"
								name="DPCreateAccountForm" onkeypress="alphaNumWithoutSpace()" />
							</TR>
							<tr id="showDemoId2" style="display: none;">
							<TD width="80" class="text pLeft15"><STRONG><FONT
									color="#000000"><bean:message bundle="view"
									key="account.demoId3" /></FONT> :</STRONG></TD>
							<TD width="80"><FONT color="#003399"><html:text
								property="demoId3" tabindex="35" styleClass="box" 
								styleId="demoId" size="19" maxlength="15"
								name="DPCreateAccountForm" onkeypress="alphaNumWithoutSpace()" />
							<TD width="80" class="text pLeft15"><STRONG><FONT
									color="#000000"><bean:message bundle="view"
									key="account.demoId4" /></FONT> :</STRONG></TD>
							<TD width="80"><FONT color="#003399"><html:text
								property="demoId4" tabindex="36" styleClass="box" 
								styleId="demoId" size="19" maxlength="15"
								name="DPCreateAccountForm" onkeypress="alphaNumWithoutSpace()" />
							</tr>	
					<!-- ended by sugandha for Retailer demo Id  -->
<tr id="showTinDate" style="display: inline;">
	<TD class="text pLeft15"><STRONG><FONT
		color="#000000"><bean:message bundle="view"
		key="account.tinNo" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
	<TD width="175"><FONT color="#003399"><html:text
		property="tinNo" tabindex="37" styleClass="box"
		styleId="tinNo" size="19" maxlength="30"
		name="DPCreateAccountForm" readonly="true"/>
	</FONT><FONT color="#ff0000" size="-2"></FONT></TD>
	<TD class="text" ><STRONG><FONT	color="#000000">
	<bean:message bundle="view" key="account.tinDate" /></FONT> :</STRONG></TD>
	<TD width="155"><FONT color="#003399"> <html:text
	property="tinDt" styleClass="box" styleId="tinDt" 
	size="19" maxlength="10" name="DPCreateAccountForm" />
	<script language="JavaScript">
new tcal ({
// form name
'formname': 'DPCreateAccountForm',
// input name
'controlname': 'tinDt'
});

</script>
	 </FONT></TD>
</TR>

<tr id="showServiceTax" style="display: inline;">
	<TD width="121" class="text" nowrap><STRONG><FONT
		color="#000000"><bean:message bundle="view"
		key="account.serviceTax" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
	<TD width="176"><FONT color="#3C3C3C"> <html:text
		property="serviceTax" styleClass="box" styleId="serviceTax"
		size="19" maxlength="30" tabindex="38"  readonly="true"/> </FONT>
	</TD>
	<TD class="text pLeft15"><STRONG><FONT
		color="#000000"><bean:message bundle="view"
		key="account.PAN" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
	<TD width="175"><FONT color="#003399"><html:text
		property="panNo" tabindex="39" styleClass="box"
		styleId="panNo" size="19" maxlength="10"
		name="DPCreateAccountForm" readonly="true" onkeypress="alphaNumWithoutSpace();"/>
	</FONT><FONT color="#ff0000" size="-2"></FONT></TD>
</TR>

<tr id="showCSTDate" style="display: inline;">
<TD width="121" class="text" nowrap><STRONG>
	<FONT color="#000000"><bean:message bundle="view"	key="account.CST" /></FONT>
	<FONT color="RED">*</FONT> :</STRONG></TD>
	<TD width="176"><FONT color="#3C3C3C"> <html:text	property="cstNo" styleClass="box" styleId="cstNo"	
	size="19" maxlength="30" tabindex="40"  readonly="true"/> </FONT>
	</TD>

	<TD class="text" ><STRONG><FONT	color="#000000">
	<bean:message bundle="view" key="account.CSTDATE" /></FONT> :</STRONG></TD>
	<TD width="155"><FONT color="#003399"> <html:text
	property="cstDt" styleClass="box" styleId="cstDt" 
	size="19" maxlength="10" name="DPCreateAccountForm" />
	<script language="JavaScript">
new tcal ({
// form name
'formname': 'DPCreateAccountForm',
// input name
'controlname': 'cstDt'
});

</script>
	</FONT>
</TD>	
<TR>
						
						<%-- 
						<TR>
							<TD class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.tinNo" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="175"><FONT color="#003399"><html:text
								property="tinNo" tabindex="41" styleClass="box"
								styleId="tinNo" size="19" maxlength="30"
								name="DPCreateAccountForm" readonly="true"/>
							</FONT><FONT color="#ff0000" size="-2"></FONT></TD>
							<TD width="121" class="text" nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.serviceTax" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="176"><FONT color="#3C3C3C"> <html:text
								property="serviceTax" styleClass="box" styleId="serviceTax"
								size="19" maxlength="30" tabindex="42"  readonly="true"/> </FONT>
							</TD>
						</TR>
						<TR>
							<TD class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.PAN" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="175"><FONT color="#003399"><html:text
								property="panNo" tabindex="43" styleClass="box"
								styleId="panNo" size="19" maxlength="10"
								name="DPCreateAccountForm" readonly="true" onkeypress="alphaNumWithoutSpace();"/>
							</FONT><FONT color="#ff0000" size="-2"></FONT></TD>
							<TD width="121" class="text" nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.CST" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="176"><FONT color="#3C3C3C"> <html:text
								property="cstNo" styleClass="box" styleId="cstNo"
								size="19" maxlength="30" tabindex="44"  readonly="true"/> </FONT>
							</TD>
						</TR>
						--%>
						
						<tr>
<%-- 							<TD class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.ReportingEmail" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="175"><FONT color="#003399"><html:text
								property="reportingEmail" tabindex="45" styleClass="box"
								styleId="reportingEmail" size="19" maxlength="30"
								name="DPCreateAccountForm" readonly="true"/>
							</FONT><FONT color="#ff0000" size="-2"></FONT>
							</TD>	--%>
							<TD width="126" class="text">
						<STRONG>
							<FONT color="#000000"> <bean:message bundle="view" key="account.octroiCharge" /></FONT>
							<FONT color="RED">*</FONT>:
						</STRONG>
						</TD>	
						<TD width="175"><FONT color="#003399"> 
						<html:select property="octroiCharge" tabindex="46" styleClass="w130" disabled="true">
							<html:option value="0">
								<bean:message key="application.option.none" bundle="view" />
							</html:option>
							<html:option value="1">
								<bean:message key="application.option.mrp" bundle="view" />
							</html:option>
							<html:option value="2">
								<bean:message key="application.option.CostPrice" bundle="view" />
							</html:option>
							<html:option value="3">
								<bean:message key="application.option.Octroi" bundle="view" />
							</html:option>
						</html:select> </FONT>
							</TD>
						</tr>
						<TR>
						
						
							<tr id="distLocatorBox" style="display: none;">
							<TD width="121" class="text"><STRONG> <FONT
								color="#000000"> <bean:message bundle="view"
								key="account.distLocator" /></FONT>:</STRONG></TD>
							<TD width="176"><FONT color="#003399"> </FONT><html:text
								property="distLocator" styleClass="box" styleId="distLocator"
								size="30" maxlength="50" /></TD>
					<!--by saumya for repair facility -->
								<TD width="121"><STRONG> <FONT
								color="#000000"> <bean:message bundle="view"
								key="account.repFacility" /></FONT>:</STRONG></TD>

							    <TD width="176"><FONT color="#003399"> 
						<html:select property="repairFacility" tabindex="47" styleClass="w130" styleId="repairFacility" >
							<html:option value="N">
											<bean:message key="application.option.no" bundle="view" />
							</html:option>
										<html:option value="Y">
											<bean:message key="application.option.yes" bundle="view" />
										</html:option>
										
						  </html:select> </FONT></TD>
					<!-- ended by saumya  -->	
												</tr>
							
							
					
						<tr>
						 <td>
							<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
								<TR align="center">
									<TD width="70"><INPUT class="submit" type="submit"
										tabindex="48" name="Submit" value="Submit"
										onclick="return validate();"></TD>
									<TD><INPUT class="submit" type="submit" tabindex="49"
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
							<html:hidden property="isbillable" styleId="isbillable"/> 
							<html:hidden property="searchFieldValue" styleId="searchFieldValue" />
							<html:hidden property="accountStatus" styleId="accountStatus" />
							<html:hidden property="groupId" styleId="groupId" />
							<html:hidden property="heirarchyId" styleId="heirarchyId" />
							<html:hidden property="accountCityId" styleId="accountCityId"/>
							<html:hidden property="accountZoneId" styleId="accountZoneId"/>
							<html:hidden property="beatId" styleId="beatId"/>
							<html:hidden property="roleName" value="N"/>
							<html:hidden property="parentLoginName" name="DPCreateAccountForm"/>
							<html:hidden property="billableLoginName" name="DPCreateAccountForm"/>
							<html:hidden property="parentAccountName" name="DPCreateAccountForm"/>
							<html:hidden property="billableAccountName" name="DPCreateAccountForm"/>
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
/* {
	if(document.getElementById("flagForCityFocus").value=="true")
	{
		document.getElementById("accountCityId").focus();
		document.getElementById("flagForCityFocus").value=false;
	}
	if(document.getElementById("flagForCategoryFocus").value=="true")
	{
		document.getElementById("tradeCategoryId").focus();
		document.getElementById("flagForCategoryFocus").value=false;
	}
} */
</SCRIPT>
</BODY>
</html:html>