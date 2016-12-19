<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>	
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="<%=request.getContextPath()%>/theme/text.css"
	rel="stylesheet" type="text/css">
<LINK href="<%=request.getContextPath()%>/theme/CalendarControl.css" rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/Cal.js"></SCRIPT>
<script language="javascript" src="scripts/calendar1.js">
</script>
<script language="javascript" src="scripts/Utilities.js">
</script>
<script language="javascript" src="scripts/cal2.js">
</script>
<script language="javascript" src="scripts/cal_conf2.js">
</script>
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
 <script language="JavaScript" src="<%=request.getContextPath()%>/jScripts/AccountMaster.js"></script> 
<script type="text/javascript">
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
	var addressStatus=false;
	// function that checks that is any field value is blank or not
	function validate()
	{	
	   if(document.getElementById("accountLevel").value==null || document.getElementById("accountLevel").value=="0"){
			alert('<bean:message bundle="error" key="error.account.invalidlevel" />');
			document.getElementById("accountLevel").focus();
			return false; 
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
		if(document.getElementById("accountName").value==null || document.getElementById("accountName").value==""){
			alert('<bean:message bundle="error" key="error.account.accountname" />');
			document.getElementById("accountName").focus();
			return false; 
		}
		var accName = document.getElementById("accountName").value;
		var firstChar = isNumber(accName.charAt(0));
		if(firstChar==true){
			alert("Account Name Cannot Begin With A Numeric Character.");
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
			alert("Contact Name Cannot Begin With A Numeric Character.");
			document.getElementById("contactName").focus();
			return false;
		}
		var validAccountCode = isAlphaNumericWithSpace(contactName);
		if(validAccountCode==false){
			alert("Special Characters are not allowed in Contact Name");
			document.getElementById("contactName").focus();
			return false;
		}
		// Validate Email
		if(parseInt(document.getElementById("accountLevel").value) <7 ){
		var emailID=document.forms[0].email;
		if(document.getElementById("email").value == "" || document.getElementById("email").value == null){
			alert("Please Enter The Email Id");
			emailID.focus();
			return false;
		}
		var errorMsg ='<bean:message bundle="error" key="errors.user.email_invalid" />'; 
	    if(!(document.getElementById("email").value==null || document.getElementById("email").value=="")){
			if (!(isEmail(emailID.value, errorMsg))){
				emailID.focus();
				return false;
			}
		}
		}
		
		if(!(document.getElementById("simNumber").value==null|| document.getElementById("simNumber").value=="")){
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
		//var mobileno = document.getElementById("mobileNumber").value;
		var mobileno = "";
		
		/*if(parseInt(document.getElementById("accountLevel").value) == 8 )
		{
			mobileno = document.getElementById("retailerMobileNumber").value;
		}
		else */
		if(parseInt(document.getElementById("accountLevel").value) != 8 )
		{
			mobileno = document.getElementById("mobileNumber").value;
		
		
		if(mobileno==null || mobileno==""){
			alert('<bean:message bundle="error" key="error.account.mobilenumber" />');
			//document.getElementById("mobileNumber").focus();
			return false; 
		}
		if(mobileno != null || mobileno != ""){
		/*
		var firstChar = mobileno.charAt(0);
		if(firstChar != "9"){
			alert("Mobile Number must start with '9'");
			document.getElementById("mobileNumber").focus();
			return false;
		}*/
		if(mobileno.length<10){
			alert('<bean:message bundle="error" key="error.account.mobilenumberlength" />');
			//document.getElementById("mobileNumber").focus();
			return false;
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
		if(document.getElementById("circleId").value == ""){
			alert('<bean:message bundle="error" key="error.account.circleid.empty" />');
			document.getElementById("circleId").focus();
			return false; 
		}
		
		if((document.getElementById("accountStateId").value==null || document.getElementById("accountStateId").value=="") && parseInt(document.getElementById("accountLevel").value) == 6){
			alert('<bean:message bundle="error" key="error.account.state" />');
			document.getElementById("accountStateId").focus();
			return false; 
		}
		
		//added by ARTI for warehaouse list box BFR -11 release2
		if((document.getElementById("accountWareHouseCode").value==null || document.getElementById("accountWareHouseCode").value=="") && parseInt(document.getElementById("accountLevel").value) == 6){
			alert('<bean:message bundle="error" key="error.account.warehouse" />');
			document.getElementById("accountWareHouseCode").focus();
			return false; 
		}
		//ended by ARTI for warehaouse list box BFR -11 release2
		
		if(document.getElementById("circleId").value != "0" && document.getElementById("showZoneCity").value=="A"){
			if(document.getElementById("accountZoneId").value==null || document.getElementById("accountZoneId").value==""){
				alert('<bean:message bundle="error" key="error.account.zone" />');
				document.getElementById("accountZoneId").focus();
				return false; 
			}
			if(document.getElementById("accountCityId").value==null || document.getElementById("accountCityId").value==""){
				alert('<bean:message bundle="error" key="error.account.city" />');
				document.getElementById("accountCityId").focus();
				return false; 
			}

/*	Here............................................................
	if(parseInt(document.getElementById("accountLevel").value) == 8){
			if (!(document.getElementById("accountCityId").value=="")){
				if(document.getElementById("beatId").value==null || document.getElementById("beatId").value=="0"){
					alert('<bean:message bundle="error" key="error.account.beat" />');
					document.getElementById("beatId").focus();
					return false; 
				}
			}
		}	*/

		}
		if(document.getElementById("accountAddress").value==null || document.getElementById("accountAddress").value==""){
			alert('<bean:message bundle="error" key="error.account.accountaddress" />');
			document.getElementById("accountAddress").focus();
			return false; 
		}
		
		var validAccountAdd = specialCharCheck(document.getElementById("accountAddress").value);
		if(validAccountAdd==false){
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
/*		if(document.getElementById("accountAddress2").value==null || document.getElementById("accountAddress2").value==""){
			alert('<bean:message bundle="error" key="error.account.accountaddressAlternate" />');
			document.getElementById("accountAddress2").focus();
			return false; 
		}	*/

		if(document.getElementById("accountLevel").value==null || document.getElementById("accountLevel").value=="0"){
			alert('<bean:message bundle="error" key="error.account.invalidlevel" />');
			document.getElementById("accountLevel").focus();
			return false; 
		}
		if(parseInt(document.getElementById("accountLevel").value) < 7){
		if(document.getElementById("IPassword").value==null || document.getElementById("IPassword").value==""){
			alert('<bean:message bundle="error" key="error.account.IPassword" />');
			document.getElementById("IPassword").focus();
			return false; 
		}
		if(document.getElementById("checkIPassword").value==null || document.getElementById("checkIPassword").value==""){
			alert('<bean:message bundle="error" key="error.account.checkipassword" />');
			document.getElementById("checkIPassword").focus();
			return false; 
		}
		if((document.getElementById("IPassword").value.length)<8){
			alert('<bean:message bundle="error" key="error.account.passwordlength" />');  
			document.getElementById("IPassword").value='';
			document.getElementById("checkIPassword").value='';
			document.getElementById("IPassword").focus();
			return false;
		}
		if(document.getElementById("IPassword").value!=document.getElementById("checkIPassword").value){
			alert('<bean:message bundle="error" key="error.account.passwordmatch" />');
			document.getElementById("IPassword").value='';
			document.getElementById("checkIPassword").value='';
			document.getElementById("IPassword").focus();
			return false; 
		}
	}		
		if(document.getElementById("heirarchyId").value=="1"){

			if((!document.getElementById("parentAccount").disabled)&& (document.getElementById("parentAccount").value==null||document.getElementById("parentAccount").value=="")){

				alert('<bean:message bundle="error" key="error.account.parentAccount" />');
				document.getElementById("searchbutton").focus();
				return false; 
			}  
		}	
		
		if(parseInt(document.getElementById("accountLevel").value)== 8){
			if(document.getElementById("transactionId").value == "" || document.getElementById("transactionId").value == "-1"){
				alert("Please select Transaction type for Retailer. ");
				document.getElementById("transactionId").focus();
				return false; 
			}
		}
		if(document.getElementById("showNumbers").value=="Y")
		{
		if(parseInt(document.getElementById("accountLevel").value)!= 8){	
			var lapu = document.getElementById("lapuMobileNo").value;
			
			if((parseInt(document.getElementById("accountLevel").value)!=8 )&&(lapu==null|| lapu=="")){
				alert('<bean:message bundle="error" key="error.account.lapuMobileNo" />');
				document.getElementById("lapuMobileNo").focus();
				return false; 
			}

			if(lapu != "" && lapu.length>0 ){
			/*
				var firstChar = lapu.charAt(0);
				if(firstChar != "9"){
					alert("Lapu Mobile Number must start with '9'");
					document.getElementById("lapuMobileNo").focus();
					return false;
				}*/
				if((document.getElementById("lapuMobileNo").value.length)<10){
					alert('<bean:message bundle="error" key="error.account.mobilenumberlength" />');
					document.getElementById("lapuMobileNo").focus();
					return false;
				}
			}
		
			if(!(document.getElementById("lapuMobileNo1").value == "" || document.getElementById("lapuMobileNo1").value == null)){
				var firstChar = document.getElementById("lapuMobileNo1").value.charAt(0);
				/*
				if(firstChar != "9"){
					alert("Lapu Mobile2 Number must start with '9'");
					document.getElementById("lapuMobileNo1").focus();
					return false;
				}*/
				if((document.getElementById("lapuMobileNo1").value.length)<10){
					alert('<bean:message bundle="error" key="error.account.mobilenumberlength" />');
					document.getElementById("lapuMobileNo1").focus();
					return false;
				}
			}
		if(!(document.getElementById("FTAMobileNo").value == "" || document.getElementById("FTAMobileNo").value == null)){
		var firstChar = document.getElementById("FTAMobileNo").value.charAt(0);
		/*
		if(firstChar != "9"){
			alert("FTA Mobile Number must start with '9'");
			document.getElementById("FTAMobileNo").focus();
			return false;
		}*/
		if((document.getElementById("FTAMobileNo").value.length)<10){
			alert('<bean:message bundle="error" key="error.account.mobilenumberlength" />');
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
			alert('<bean:message bundle="error" key="error.account.mobilenumberlength" />');
			document.getElementById("FTAMobileNo1").focus();
			return false;
		}
		}
		}
		else if(parseInt(document.getElementById("accountLevel").value)== 8){	
			var lapu1 = document.getElementById("retailerMobileNumber").value;
			
			if((parseInt(document.getElementById("accountLevel").value)==8 )&&(lapu1==null|| lapu1=="")){
				alert('<bean:message bundle="error" key="error.account.lapuMobileNo" />');
				document.getElementById("retailerMobileNumber").focus();
				return false; 
			}

			if(lapu1 != "" && lapu1.length>0 ){
			/*
				var firstChar = lapu.charAt(0);
				if(firstChar != "9"){
					alert("Lapu Mobile Number must start with '9'");
					document.getElementById("lapuMobileNo").focus();
					return false;
				}*/
				if((document.getElementById("retailerMobileNumber").value.length)<10){
					alert('<bean:message bundle="error" key="error.account.lapumobilenumberlength" />');
					document.getElementById("retailerMobileNumber").focus();
					return false;
				}
			}
		
			if(!(document.getElementById("mobile1").value == "" || document.getElementById("mobile1").value == null)){
				var firstChar = document.getElementById("mobile1").value.charAt(0);
				/*
				if(firstChar != "9"){
					alert("Lapu Mobile2 Number must start with '9'");
					document.getElementById("lapuMobileNo1").focus();
					return false;
				}*/
				if((document.getElementById("mobile1").value.length)<10){
					alert('<bean:message bundle="error" key="error.account.mobile1length" />');
					document.getElementById("mobile1").focus();
					return false;
				}
			}
		if(!(document.getElementById("mobile2").value == "" || document.getElementById("mobile2").value == null)){
		var firstChar = document.getElementById("mobile2").value.charAt(0);
		/*
		if(firstChar != "9"){
			alert("FTA Mobile Number must start with '9'");
			document.getElementById("FTAMobileNo").focus();
			return false;
		}*/
		if((document.getElementById("mobile2").value.length)<10){
			alert('<bean:message bundle="error" key="error.account.mobile2length" />');
			document.getElementById("mobile2").focus();
			return false;
		}
		}
		if(!(document.getElementById("mobile3").value == "" || document.getElementById("mobile3").value == null)){
		var firstChar = document.getElementById("mobile3").value.charAt(0);
		/*
		if(firstChar != "9"){
			alert("FTA Mobile Number must start with '9'");
			document.getElementById("FTAMobileNo1").focus();
			return false;
		}*/
		if((document.getElementById("mobile3").value.length)<10){
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
/*		if(document.getElementById("rate").value==null || document.getElementById("rate").value=="" || parseInt(document.getElementById("rate").value)==0){

			alert('<bean:message bundle="error" key="error.account.rate" />');
			document.getElementById("rate").focus();
		    return false; 
		}
		if(isAmount(document.forms[0].rate.value,'<bean:message bundle="view" key="account.rate" />',true)==false){
			alert("Enter A Valid Value For Rate.");
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
			alert('<bean:message bundle="error" key="error.account.invalidthreshold" />');
			document.forms[0].threshold.focus();
			return false;	
		}	*/
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
		if(document.getElementById("outletType").value==null || document.getElementById("outletType").value=="0"){
			alert('<bean:message bundle="error" key="error.account.outletType" />');
			document.getElementById("outletType").focus();
			return false; 
		}
	}   
	

	  if(parseInt(document.getElementById("accountLevel").value) == 6)
	  {
		if(document.getElementById("code").value==null || document.getElementById("code").value=="")
		{
			alert('<bean:message bundle="error" key="error.account.code" />');
			document.getElementById("code").focus();
			return false; 
		}
		
				
		if(document.getElementById("tinNo").value == null || document.getElementById("tinNo").value == ""){
				alert("Enter Tin No.");
				document.getElementById("tinNo").focus();
				return false;
			}
			
			if(document.getElementById("tinDt").value==null || document.getElementById("tinDt").value==""){	
				alert('<bean:message bundle="error" key="error.tin.date" />');
				document.getElementById("tinDt").focus();
				return false;
			}
		
				
			if(document.getElementById("serviceTax").value == null || document.getElementById("serviceTax").value == ""){
				alert("Enter Service Tax No.");
				document.getElementById("serviceTax").focus();
				return false;
			}
					   
			if(document.getElementById("panNo").value == null || document.getElementById("panNo").value == ""){
				alert("Enter PAN No.");
				document.getElementById("panNo").focus();
				return false;
			}
				
		   if(document.getElementById("cstNo").value == null || document.getElementById("cstNo").value == ""){
				alert("Enter CST No.");
				document.getElementById("cstNo").focus();
				return false;
			}
			if(document.getElementById("cstDt").value==null || document.getElementById("cstDt").value==""){	
				alert('<bean:message bundle="error" key="error.cst.date" />');
				document.getElementById("cstDt").focus();
				return false;
			}
			   

		   
				
/*		if(document.getElementById("code").value != null){
		if((document.getElementById("code").value.length)<8){
			alert('<bean:message bundle="error" key="error.account.code.length" />');
			document.getElementById("code").focus();
			return false;
		}
		}	*/
		
		if(document.getElementById("accountLevelId").value=='7'){
			if(document.getElementById("distLocator").value == null || document.getElementById("distLocator").value == "")
			{
				alert("Enter Distributor Locator Code.");
				document.getElementById("distLocator").focus();
				return false;
			}
			var strString = document.getElementById("distLocator").value;
						var isValidLocator = 	checkLocator(strString);
			if(!isValidLocator){
				alert("Invalid Distributor Locator Code.");
				return false;
			}
			//Shilpa
      	}    
      	
		if(!(document.getElementById("openingDt").value==null || document.getElementById("openingDt").value=="")){	
			if(!(isDateLessToday(document.getElementById("openingDt").value))){
				alert('<bean:message bundle="error" key="error.account.date" />');
				document.getElementById("openingDt").focus();
				return false;
			}
		}
		if(document.getElementById("retailerType").style.display=="inline")
		{
			if(document.getElementById("channelType").value==null || document.getElementById("channelType").value=="0"){
				alert('<bean:message bundle="error" key="error.account.channelType" />');
				document.getElementById("channelType").focus();
				return false; 
			}
		}
		
	}
		document.forms[0].accountAddress.value=trimAll(document.forms[0].accountAddress.value);
		if(parseInt(document.getElementById("accountLevel").value) >= 6)
		{
		document.getElementById("tradeIdInBack").value = document.getElementById("tradeId").value;
		document.getElementById("tradeCategoryIdInBack").value = document.getElementById("tradeCategoryId").value;
		document.getElementById("isBillableInBack").value = document.getElementById("isBillable").value;
		}
/*		var emailID1=document.forms[0].reportingEmail;
		var errorMsg ='<bean:message bundle="error" key="errors.user.email_invalid" />'; 
	    if(!(document.getElementById("reportingEmail").value==null || document.getElementById("reportingEmail").value=="")){
			if (!(isEmail(reportingEmail.value, errorMsg))){
				reportingEmail.focus();
				return false;
			}
		} */
		document.getElementById("methodName").value="createAccount";
		if(parseInt(document.getElementById("accountLevel").value)>6)
		{
			document.getElementById("hiddenZoneId").value=document.getElementById("accountZoneId").value;
			
			document.getElementById("hiddenCircleId").value=document.getElementById("circleId").value;
			document.getElementById("hiddenCityId").value=document.getElementById("accountCityId").value;
			document.getElementById("hiddenOutletType").value=document.getElementById("outletType").value;
		}
		if( parseInt(document.getElementById("accountLevel").value) < 6 )
		{
			if(document.getElementById("code").value==null || document.getElementById("code").value==""){
				alert("Enter Employee Id");
				document.getElementById("code").focus();
				return false; 
			}
		}
		
		if( parseInt(document.getElementById("accountLevel").value) == 7 )
		{
			if(document.getElementById("billableCode").value==null || document.getElementById("billableCode").value=="" || document.getElementById("billableCode").value=="0"){
				alert("Enter Billable Code");
				document.getElementById("billableCode").focus();
				return false; 
			}
		}
		
		if( parseInt(document.getElementById("accountLevel").value) == 8 )
		{
			if(document.getElementById("channelType").value==null || document.getElementById("channelType").value=="0"){
				alert("Select Chanel Type");
				document.getElementById("channelType").focus();
				return false; 
			}
			if(document.getElementById("outletType").value==null || document.getElementById("outletType").value=="0"){
				alert("Select market");
				document.getElementById("outletType").focus();
				return false; 
			}
		}
		return true;
	}

function resetForm()
{
	var len = document.forms[0].length;
	var controlType = null;

	for(i = 0; i < len; i++){
		controlType = document.forms[0].elements[i].type;
		if(controlType == "text"){
			if(document.forms[0].elements[i].name != "openingDt")
			{
				document.forms[0].elements[i].value="";
			}
		}else if(controlType == "textarea"){
			document.forms[0].elements[i].value="";
		}else if(controlType == "password"){
			if(document.forms[0].elements[i].name != "IPassword" && document.forms[0].elements[i].name != "checkIPassword" )
			{
				document.forms[0].elements[i].value="";
			}
		}else if(controlType == "radio"){
			document.forms[0].elements[i].checked=false;
		}else if(controlType == "checkbox") {
			document.forms[0].elements[i].checked=false;
		}else if((controlType == "select-one")&& (document.forms[0].elements[i].disabled != true)){
			if(document.forms[0].elements[i].name != "accountLevel")
			{
				document.forms[0].elements[i].selectedIndex=0;
			}
		}
	}
}


	// disable if distributor 	
	function parentCheck()
	{
		if(document.getElementById("accountLevel").value == 0)
		{
				document.forms[0].action="./initCreateAccount.do?methodName=init";
				document.forms[0].method="post";
				document.forms[0].submit();
				//onLoadparentCheck();
				return;		
		}

	
	var len = document.forms[0].length;
	var controlType = null;

	for(i = 0; i < len; i++){
		controlType = document.forms[0].elements[i].type;
		if(controlType == "text"){
			
			if(document.forms[0].elements[i].name != "openingDt" && document.forms[0].elements[i].name != "parentAccount" )
			{
				//alert(document.forms[0].elements[i].name);
				document.forms[0].elements[i].value="";
			}
		}else if(controlType == "textarea"){
			document.forms[0].elements[i].value="";
		}else if(controlType == "password"){
			if(document.forms[0].elements[i].name != "IPassword" && document.forms[0].elements[i].name != "checkIPassword" )
			{
				document.forms[0].elements[i].value="";
			}
		}else if(controlType == "radio"){
			document.forms[0].elements[i].checked=false;
		}else if(controlType == "checkbox") {
			document.forms[0].elements[i].checked=false;
		}else if((controlType == "select-one")&& (document.forms[0].elements[i].disabled != true)){
			if(document.forms[0].elements[i].name != "accountLevel")
			{
				document.forms[0].elements[i].selectedIndex=0;
			}
		}
	}
	
	
	document.getElementById("userName").style.display="inline";
	document.getElementById("userNameValue").style.display="inline";
					
					
		document.getElementById("parentAccount").value="";
		document.getElementById("parentAccountName").value="";
		document.getElementById("parentLoginName").value="";
		var sn;
		if(document.getElementById("heirarchyId").value=="1")
		{
			document.getElementById("accountLevelStatus").value= document.getElementById("accountLevel").value;
			var acclevelId=document.getElementById("accountLevel").value;
            document.getElementById("accountLevelStatus").value=acclevelId;
            document.getElementById("accountLevelId").value=document.getElementById("accountLevelStatus").options[document.getElementById("accountLevelStatus").selectedIndex].text;
		}  
		// to check the hierarchy id of the selected accountlevel.
		accountLevel();
		//alert(parseInt(document.getElementById("accountLevel").value)+" %$$$$$$$$$$$$ account level");
		if(document.getElementById("accountLevelFlag").value == "2")
		{
			document.getElementById("showParentRow").style.display="none";
			document.getElementById("showBillableTags").style.display="none";
		}
		
		else
		if(document.getElementById("accountLevelFlag").value=="1")
		{
			if(document.getElementById("accountLevelId").value == (parseInt(document.getElementById("accessLevelId").value)+1)){	//parseInt(document.getElementById("accountLevel").value)<=2 ||  
				document.getElementById("parentAccount").disabled=true;
//				document.getElementById("parentAccount").value="";
				document.getElementById("searchbutton").disabled=true;
				document.getElementById("divParentNotMand").style.display="none";
				document.getElementById("divParentMand").style.display="block";	
			}else{
				document.getElementById("parentAccount").disabled=false;
				document.getElementById("parentAccount").readOnly="readonly";
				document.getElementById("searchbutton").disabled=false;
//				document.getElementById("parentAccount").value="";
				document.getElementById("divParentNotMand").style.display="block";
				document.getElementById("divParentMand").style.display="none";
				}
			if(document.getElementById("accountLevel").value=="2"){
				document.getElementById("showZoneCity").value="N";
				document.getElementById("showZoneCityId").style.display="none";
			}else{
				document.getElementById("showZoneCity").value="A";
				document.getElementById("showZoneCityId").style.display="inline";			
			}
			
			if(parseInt(document.getElementById("accountLevel").value)==6)
			{	
					document.getElementById("showNumbers").value="Y";
					document.getElementById("showBillableTags").style.display="inline";
			 	    document.getElementById("isbillable").value="Y";
					document.getElementById("billableCode").value="";
					document.getElementById("isbillable").disabled=true;
					document.getElementById("openingBalance").value="0.00";
					document.getElementById("div12").style.display="none";
			 //	 	document.getElementById("distManagers").style.display="inline";
				 	document.getElementById("showBeat").style.display="none";
				 	document.getElementById("tinNservice").style.display="inline";
				 	document.getElementById("panNcst").style.display="inline";
				 	document.getElementById("cstBox").style.display="inline";
				 	document.getElementById("emailNoctroi").style.display="inline";
				 	document.getElementById("locatorFields").style.display="inline";
	 				//document.getElementById("swLocatorField").style.display="inline";
				 	document.getElementById("uniqueCode").style.display="inline";
				 	document.getElementById("employeeid").style.display="none";
					document.getElementById("hideForFnR").style.display="inline";
					document.getElementById("showLapuNumber").style.display="inline";
					document.getElementById("showFTANumber").style.display="inline";
					document.getElementById("emailMand").style.display="inline";
					document.getElementById("emailMand").style.display="inline";
					document.getElementById("showRetailerNumber1").style.display="none";
					document.getElementById("showRetailerNumber2").style.display="none";
					document.getElementById("retailerNumber").style.display="none";
					document.getElementById("fseNumber").style.display="none";
			}
			else if(parseInt(document.getElementById("accountLevel").value)>6)
			{
					if(parseInt(document.getElementById("accountLevel").value)==8)
					{
						document.getElementById("showBillableTags").style.display="none";
						document.getElementById("showRetailerNumber1").style.display="inline";
						document.getElementById("showRetailerNumber2").style.display="inline";
						document.getElementById("showLapuNumber").style.display="none";
						document.getElementById("showFTANumber").style.display="none";
						document.getElementById("retailerNumber").style.display="inline";
						document.getElementById("fseNumber").style.display="none";
					}
					else
					{
						//document.getElementById("userName").style.display="inline";
						//document.getElementById("userNameValue").style.display="inline";
						document.getElementById("showBillableTags").style.display="inline";
						document.getElementById("showRetailerNumber1").style.display="none";
						document.getElementById("showRetailerNumber2").style.display="none";
						document.getElementById("showLapuNumber").style.display="inline";
						document.getElementById("showFTANumber").style.display="inline"; //neetika
						document.getElementById("retailerNumber").style.display="none";
						document.getElementById("fseNumber").style.display="inline";
					}
					
					document.getElementById("userName").style.display="none";
					document.getElementById("userNameValue").style.display="none";
				    document.getElementById("showBillableCode").value="Y";
				    
				    document.getElementById("isbillable").value="N";
				    document.getElementById("isbillable").disabled=true;
				    document.getElementById("openingBalance").value="0.00";
				    document.getElementById("div12").style.display="inline";
		//    	 	document.getElementById("distManagers").style.display="none";
				 	document.getElementById("showBeat").style.display="inline";
		 		 	document.getElementById("tinNservice").style.display="none";
		 		 	document.getElementById("panNcst").style.display="none";
		 		 	document.getElementById("locatorFields").style.display="none";
	 				//document.getElementById("swLocatorField").style.display="none";
		 		 	document.getElementById("cstBox").style.display="none";
		 		 	document.getElementById("emailNoctroi").style.display="none";
				 	document.getElementById("uniqueCode").style.display="none";
				 	document.getElementById("employeeid").style.display="none";
				 	document.getElementById("hideForFnR").style.display="none";
				 	//document.getElementById("showLapuNumber").style.display="none";
					//document.getElementById("showFTANumber").style.display="none";
					document.getElementById("emailMand").style.display="none";			 		 	
			}
			else if(parseInt(document.getElementById("accountLevel").value)<6)
			{
					document.getElementById("showBillableCode").value="N";
					document.getElementById("showBillableTags").style.display="none";
		//		 	document.getElementById("distManagers").style.display="none";
				 	document.getElementById("showBeat").style.display="none";
				 	document.getElementById("tinNservice").style.display="none";
				 	document.getElementById("panNcst").style.display="none";
				 	document.getElementById("cstBox").style.display="none";
				 	document.getElementById("emailNoctroi").style.display="none";
				 	document.getElementById("locatorFields").style.display="none";
	 				//document.getElementById("swLocatorField").style.display="none";
				 	document.getElementById("uniqueCode").style.display="none";
				 	document.getElementById("employeeid").style.display="inline";
					document.getElementById("hideForFnR").style.display="inline";
				 	document.getElementById("showLapuNumber").style.display="none";
					document.getElementById("showFTANumber").style.display="none";
					document.getElementById("emailMand").style.display="inline";
					document.getElementById("showRetailerNumber1").style.display="none";
					document.getElementById("showRetailerNumber2").style.display="none";
			}
			if(document.getElementById("showBillableCode").value=="Y")
			{
				  document.getElementById("billableCode").value=="";
			}
			document.getElementById("showParentRow").style.display="inline";
		}
		
		//alert("accountLevel == &&&&&&&&&&&&&&&&&&&"+document.getElementById("accountLevel").value);
		if(parseInt(document.getElementById("accountLevel").value) >= 6 && document.getElementById("accountLevelFlag").value == "1")
		{	
			sn="Y";
			document.getElementById("ShowNumbers").value="Y";
//			document.getElementById("showRate").style.display="inline";
			document.getElementById("channelTypeId").style.display="inline";
		}else{
			sn="N";
			document.getElementById("ShowNumbers").value="N";
//			document.getElementById("showRate").style.display="none";
			document.getElementById("channelTypeId").style.display="none";			
		}
		//alert("accountLevel == "+document.getElementById("accountLevel").value);
		if(parseInt(document.getElementById("accountLevel").value) == 8){
			document.getElementById("retailerType").style.display="inline";
			document.getElementById("altChannel").style.display="inline";
			document.getElementById("altChannel1").style.display = "inline";
			document.getElementById("categoryCode").style.display="inline";
			document.getElementById("categoryName").style.display="inline";
			document.getElementById("showTransactionType").style.display="inline";
			document.getElementById("transactionType").style.display="inline";
			document.getElementById("showDistrict").style.display="inline";
			document.getElementById("showDemoId").style.display="inline";
			document.getElementById("showDemoId2").style.display="inline";	
			document.getElementById("District").style.display="inline";	
			document.getElementById("divSearchButton").style.display="inline";	
			document.getElementById("showRetailerNumber1").style.display="inline";
			document.getElementById("showRetailerNumber2").style.display="inline";
		}else{
			document.getElementById("retailerType").style.display="none";
			document.getElementById("altChannel").style.display = "none";
			document.getElementById("altChannel1").style.display = "none";
			document.getElementById("categoryCode").style.display="none";
			document.getElementById("categoryName").style.display="none";
			document.getElementById("showTransactionType").style.display="none";
			document.getElementById("transactionType").style.display="none";
			document.getElementById("showDistrict").style.display="none";
			document.getElementById("showDemoId").style.display="none";
			document.getElementById("showDemoId2").style.display="none";
			document.getElementById("District").style.display="none";
			document.getElementById("showRetailerNumber1").style.display="none";
			document.getElementById("showRetailerNumber2").style.display="none";
		}
		if(parseInt(document.getElementById("accountLevel").value) == 7)
		{
			document.getElementById("divSearchButton").style.display="inline";
		}
		
		// Add by harbans on RetailerInfo enhancement
		if(parseInt(document.getElementById("accountLevel").value) == 8)
		{
			//document.getElementById("showFTANumber").style.display="inline";
		}
		//alert(parseInt(document.getElementById("accountLevel").value)+" account level");
		if(parseInt(document.getElementById("accountLevel").value) == 6)
		{
			document.getElementById("showStateId").style.display="inline";
		}
		else
		{
			document.getElementById("showStateId").style.display="none";
		}
		
		
//		document.getElementById()
		showNumber(sn);
		onChangePassword(parseInt(document.getElementById("accountLevel").value),document.getElementById("accountLevelFlag").value);
	}

	function showNumber(sn)
	{
		var frm = document.forms[DPCreateAccountForm];
		if(sn == "Y")
		{
		document.getElementById("tradeCategory").style.display="inline";
		}	else if(sn == "N")
		{
		document.getElementById("tradeCategory").style.display="none";
		}
	}
	
	function onChangePassword(al,flag)
	{
	if(al>6 && flag == "1")
	{
	document.getElementById("showPasswordRow").style.display="none";
	document.getElementById("tradeId").disabled = true;
	document.getElementById("tradeCategoryId").disabled = true;
	}
	else
	{
		document.getElementById("showPasswordRow").style.display="block";
		document.getElementById("tradeId").disabled = false;
		document.getElementById("tradeCategoryId").disabled = false;
	}
}
	
	// disable if distributor 	
	function onLoadparentCheck(){
	//alert(document.getElementById("heirarchyId").value);
			if(document.getElementById("heirarchyId").value=="1"){
//			document.getElementById("accountLevel").value="0";
			document.getElementById("divParentMand").style.display="none";
			document.getElementById("divParentNotMand").style.display="block";
			document.getElementById("divSearchButton").style.display="block";
//			if((document.getElementById("accountLevelId").value=="2") || (document.getElementById("accountLevel").value=="1") || (document.getElementById("accountLevelId").value=="1")){
			if(parseInt(document.getElementById("accountLevelId").value) == (parseInt(document.getElementById("accessLevelId").value)+1)){
					document.getElementById("parentAccount").disabled=true;
					document.getElementById("searchbutton").disabled=true;
					document.getElementById("divParentNotMand").style.display="block";
					document.getElementById("divParentMand").style.display="none";
				}
				else{
					document.getElementById("parentAccount").disabled=false;
				}
/*			}else{
				document.getElementById("parentAccount").disabled=false;
				document.getElementById("parentAccount").readOnly="readonly";
				document.getElementById("searchbutton").disabled=false;
				document.getElementById("divParentNotMand").style.display="block";
				document.getElementById("divParentMand").style.display="none";
			} */
			if (document.getElementById("accessLevelId").value=="7"){
				document.getElementById("isbillable").disabled=true;
				document.getElementById("isbillable").value="N";
				document.getElementById("accountZoneId").disabled=true;
				document.getElementById("accountStateId").disabled=true;
				
				//added by ARTI for warehaouse list box BFR -11 release2
				document.getElementById("accountWareHouseCode").disabled=true;
				
				//ended by ARTI for warehaouse list box BFR -11 release2
				
				document.getElementById("accountCityId").disabled=true;
				document.getElementById("showPasswordRow").style.display="none";
				document.getElementById("tradeId").disabled = true;
				document.getElementById("tradeCategoryId").disabled = true;
				document.getElementById("showBeat").style.display="inline";
				document.getElementById("channelTypeId").style.display="inline";
				document.getElementById("outletType").disabled=true;
				document.getElementById("categoryCode").style.display="inline";
				document.getElementById("categoryName").style.display="inline";
			 	document.getElementById("uniqueCode").style.display="none";
			 	document.getElementById("employeeid").style.display="none";
			 	document.getElementById("hideForFnR").style.display="none";
				document.getElementById("categoryCode").style.display="none";
				document.getElementById("categoryName").style.display="none";
				document.getElementById("showLapuNumber").style.display="inline";
				document.getElementById("showFTANumber").style.display="inline";								 	
				document.getElementById("emailMand").style.display="none";
				document.getElementById("showRetailerNumber1").style.display="none";
				document.getElementById("showRetailerNumber2").style.display="none";
				document.getElementById("retailerNumber").style.display="none";
				document.getElementById("fseNumber").style.display="inline";	 	
			}else {
				document.getElementById("showBeat").style.display="none";
				document.getElementById("showPasswordRow").style.display="inline";
				document.getElementById("channelTypeId").style.display="none";
				document.getElementById("outletType").disabled=false;
				document.getElementById("categoryCode").style.display="none";
				document.getElementById("categoryName").style.display="none";
			 	document.getElementById("uniqueCode").style.display="none";
			 	document.getElementById("employeeid").style.display="inline";
			 	document.getElementById("showLapuNumber").style.display="none";
				document.getElementById("showFTANumber").style.display="none";
				document.getElementById("emailMand").style.display="inline";
				document.getElementById("showRetailerNumber1").style.display="inline";
				document.getElementById("showRetailerNumber2").style.display="inline";
				document.getElementById("retailerNumber").style.display="inline";
				document.getElementById("fseNumber").style.display="none";
			}
		}else if (document.getElementById("heirarchyId").value=="2"){
			document.getElementById("divParentMand").style.display="inline";
			document.getElementById("divParentNotMand").style.display="none";
			document.getElementById("divSearchButton").style.display="none";
			document.getElementById("showBeat").style.display="none";
			document.getElementById("showPasswordRow").style.display="inline";
		}
		accountLevel();
		//alert(document.getElementById("accountLevelNameHidden").value);
		if(document.getElementById("accountLevelNameHidden").value=="Airtel Distributor"){
		document.getElementById("showNumbers").value="Y";
		document.getElementById("showBillableTags").style.display="inline";
 	    document.getElementById("isbillable").value="Y";
		document.getElementById("billableCode").value="";
		document.getElementById("isbillable").disabled=true;
		document.getElementById("div12").style.display="none";
 //	 	document.getElementById("distManagers").style.display="inline";
	 	document.getElementById("showBeat").style.display="none";
	 	document.getElementById("tinNservice").style.display="inline";
	 	document.getElementById("panNcst").style.display="inline";
	 	document.getElementById("locatorFields").style.display="inline";
	 	//document.getElementById("swLocatorField").style.display="inline";
	 	document.getElementById("cstBox").style.display="inline";
	 	document.getElementById("emailNoctroi").style.display="inline";
		document.getElementById("channelTypeId").style.display="inline";
		document.getElementById("hideForFnR").style.display="inline";
		document.getElementById("showLapuNumber").style.display="inline";
		document.getElementById("showFTANumber").style.display="inline";
		document.getElementById("emailMand").style.display="inline";				 				 		 	
		}
		if(document.getElementById("accountLevelNameHidden").value=="FSE"){
			document.getElementById("showBeat").style.display="inline";
			document.getElementById("showPasswordRow").style.display="none";
			document.getElementById("categoryCode").style.display="none";
			document.getElementById("categoryName").style.display="none";
			document.getElementById("channelTypeId").style.display="inline";
			document.getElementById("showLapuNumber").style.display="inline";
			document.getElementById("showFTANumber").style.display="none";
			document.getElementById("showBillableTags").style.display="inline";						
			document.getElementById("emailMand").style.display="none";				 				 		 	
		}
		if(document.getElementById("accountLevelNameHidden").value=="Retailer"){
			//Neetika for error msg after display
			document.getElementById("showTransactionType").style.display="inline";
			document.getElementById("transactionType").style.display="inline";
			document.getElementById("showDistrict").style.display="inline";
			document.getElementById("District").style.display="inline";	
			document.getElementById("showStateId").style.display="none";
		

			document.getElementById("showDemoId").style.display="inline";
			document.getElementById("showDemoId2").style.display="inline";	
			//end neetika
			document.getElementById("showBeat").style.display="inline";
			document.getElementById("showPasswordRow").style.display="none";
			document.getElementById("categoryCode").style.display="inline";
			document.getElementById("categoryName").style.display="inline";
			document.getElementById("retailerType").style.display="inline";
			document.getElementById("altChannel").style.display="inline";
			document.getElementById("altChannel1").style.display = "inline";
			document.getElementById("showLapuNumber").style.display="none";
			document.getElementById("showFTANumber").style.display="none";// Update by harbans for Retailer Info		
			document.getElementById("showBillableTags").style.display="inline";	
			document.getElementById("emailMand").style.display="none";			
			document.getElementById("tradeCategory").style.display="none";//added by Sugandha for Retailer.info
			document.getElementById("userName").style.display="none";
			document.getElementById("userNameValue").style.display="none";
			document.getElementById("showRetailerNumber1").style.display="inline";
			document.getElementById("showRetailerNumber2").style.display="inline";
			document.getElementById("retailerNumber").style.display="inline";
			document.getElementById("fseNumber").style.display="none";
		}
		if(document.getElementById("accountLevelNameHidden").value=="CircleAdmin"){
			document.getElementById("showZoneCity").value="N";
			document.getElementById("showZoneCityId").style.display="none";
			document.getElementById("emailMand").style.display="inline";			
		}
		showNumber(document.getElementById("showNumbers").value);
		showPassword(parseInt(document.getElementById("accountLevelId").value));
	} 
function showPassword(al)
{
if(al==7)
{
document.getElementById("showBillableTags").style.display="inline";
//document.getElementById("showRate").style.display="inline";

}
else
{
document.getElementById("showPasswordRow").style.display="block";
document.getElementById("showBillableTags").style.display="none";
//document.getElementById("showRate").style.display="none";
}
}
	function checkKeyPressed(){
		if(addressStatus==false){ 
	    	 if(window.event && window.event.keyCode=="13")
	    	{
				if(validate()){
					document.getElementById("Submit").disabled=true; 
					if(document.getElementById("submitStatus").value=="1"){  
						document.getElementById("submitStatus").value="0";
						document.forms[0].submit();
					}
					return true;
				}else{	
					return false;
				}
			}	
		}
	}

	function submitAccount()
	{	
		if(validate()){
			document.getElementById("Submit").disabled=true; 
			//alert('submit now');
			document.forms[0].submit();
		}
			return true;
	}
	function setAddressStatus(){
		addressStatus=true;
	}
	
	function ResetAddressStatus(){
		addressStatus=false;
	}

	// this function open child window to search and select distributor code 
	function OpenParentWindow(){
		if(document.getElementById("accountLevel").options[document.getElementById("accountLevel").selectedIndex].text=="Airtel Distributor"){
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
		document.submitForm.circleid.value=document.getElementById("circleId").value;
		document.submitForm.parent.value=document.getElementById("parentAccount").value;
		displayWindow = window.open('', "newWin", "width =700,height= 220, left=50, top=100, scrollbars=yes,resizable=yes, status=yes");
       	document.submitForm.submit();
	}
	function resetParent(){
		document.getElementById("parentAccount").value="";
		document.getElementById("lapuMobileNo").value="";
		document.getElementById("lapuMobileNo1").value="";
		document.getElementById("FTAMobileNo").value="";
		document.getElementById("FTAMobileNo1").value="";
		document.getElementById("tradeId").value="0";
		document.getElementById("tradeCategoryId").value="0";
	}
	
	function isDateLessToday(date1){
		var startDt=new Date();
		var start = new Date(date1);
		if(start > startDt){
			return true;
		}else if(start.getDate()==startDt.getDate() && start.getMonth()==startDt.getMonth() && start.getDay()==startDt.getDay()){
			return true;
		}
		return false;
	}
	
	function checkBillable(){
	     if(document.getElementById("showBillableCode").value=="Y" ){
	     	if(document.getElementById("isBillable").value=="Y"){
		//	 		document.getElementById("searchBillablebutton").disabled=true;
			 		document.getElementById("billableCode").value="";
			}else{
		 //	 		document.getElementById("searchBillablebutton").disabled=false;
			}
		} 	
	
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
		document.submitForm.circleid.value=document.getElementById("circleId").value;
		document.submitForm.parent.value=document.getElementById("parentAccount").value;
		displayWindow = window.open('', "newWin", "width =700,height= 220, left=50, top=100, scrollbars=yes,resizable=yes, status=yes");
       	document.submitForm.submit();
	}
	function OpenHelpWindow(url){
		document.submitForm.action=url;
		displayWindow = window.open('', "newWin", "width =700,height= 220, left=50, top=100, scrollbars=yes,resizable=yes, status=yes");
       	document.submitForm.submit();
	}
	
	function setBillableDiv(){
		 // if user type internal 
		 
		 if(document.getElementById("heirarchyId").value=="1"){
			    if(document.getElementById("isBillable").value=="Y"){ 
				 	 document.getElementById("div12").style.display="none";
			 	}else{
			 	 	 document.getElementById("div12").style.display="block";
			 	}
		  }	
	}
function accountLevel(){
var Index = document.getElementById("accountLevel").selectedIndex;
var accountLevel=document.getElementById("accountLevel").options[Index].value;
var url= "initCreateAccount.do?methodName=getAccountLevel&cond1="+accountLevel;
var elementId = "accountLevelFlag" ;
ajaxText(url,elementId,"text");
}
function ajaxText(url,elementId,text){
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
		getAjaxTextValues(elementId);
	}
	req.open("POST",url,false);
	req.send();
}
function getAjaxTextValues(elementId)
{
	if (req.readyState==4 || req.readyState=="complete") 
	{
		var text=req.responseText;
		 if(text!=null){
    		var accountName = text.substring(0,text.indexOf("@"));
			var hierarchyId = text.substring(text.indexOf("@")+1,text.length);
			document.getElementById(elementId).value = hierarchyId;
			document.getElementById("accountLevelNameHidden").value=accountName;
		}
		else
		document.getElementById(elementId).value = "0" ; 
	}
}
function maxlength(param) {
	var size=100;
	if(param=="1"){
	var address = document.getElementById("accountAddress").value;
    	if (address.length >= size) {
    	address = address.substring(0, size-1);
    	document.getElementById("accountAddress").value = address;
	    alert ("Account Address  Can Contain 100 Characters Only.");
    	document.getElementById("accountAddress").focus();
 	    return false;
    	}
	}else if (param=="2"){
		var address2 = document.getElementById("accountAddress2").value;
		if (address2.length >= size) {
		address2 = address2.substring(0, size-1);
    	document.getElementById("accountAddress2").value = address2;		
	    alert ("Altrenate Address Can Contain 100 Characters Only.");
    	document.getElementById("accountAddress2").focus();
	    return false;
	    }
	}
}
function stateChange(){
	stateId = document.getElementById("accountStateId").value;
	circleId = document.getElementById("circleId").value;
	condition = "state";
	
	var url="initCreateAccount.do?methodName=getParentCategory&OBJECT_ID="+circleId+"&condition="+condition;
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
/*
function getChange(condition)
{
	alert("aaaaaaaaaa");	
	var Id = "";
	if(condition == "category"){
		Id = document.getElementById("tradeId").value;
	}
	if(condition == "beat"){
		Id = document.getElementById("accountCityId").value;
	}else
	if(condition == "zone"){
		Id = document.getElementById("circleId").value;
	}
	if(condition == "state"){
		Id = document.getElementById("circleId").value;
	}else
	if(condition == "city"){
		Id = document.getElementById("accountZoneId").value;
	}
	alert("22222222");
	 var url="initCreateAccount.do?methodName=getParentCategory&OBJECT_ID="+Id+"&condition="+condition;
	 alert("3333333333");
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
  		alert("66666666");
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
		if(condition == "state"){
			selectObj = document.forms[0].accountStateId;
		}else
		if(condition == "city"){
			selectObj = document.forms[0].accountCityId;
		}
		alert("555555555555555555");
		selectObj.options.length = optionValues.length + 1;
		for(var i=0; i < optionValues.length; i++)
		{	
			selectObj.options[(i+1)].text = optionValues[i].getAttribute("text");
			selectObj.options[(i+1)].value = optionValues[i].getAttribute("value");
		}
		//getChange('state');
	}
}
*/

</script>
<script type="text/javascript" language="ecmascript">

	function ShowLoginName(obj)
	{
		if(document.getElementById("parentAccount").value !="" || document.getElementById("parentAccount").value != null){
			document.getElementById("parentAccount").value = document.getElementById("parentLoginName").value;
			if(parseInt(document.getElementById("accountLevelId").value) >=8){
				document.getElementById("billableCode").value=document.getElementById("billableLoginName").value;
			}
		}
	}
	function ShowAccountName(obj)
	{
		if(document.getElementById("parentAccount").value !="" || document.getElementById("parentAccount").value != null){
			document.getElementById("parentAccount").value = document.getElementById("parentAccountName").value;
			if(parseInt(document.getElementById("accountLevelId").value) >=8){
				document.getElementById("billableCode").value=document.getElementById("billableAccountName").value;
			}
		}
	}
	function move_Area(event)
	{
		event = event || window.event;
		var parent = document.getElementById("parentAccount").value;
		if(document.getElementById("parentAccount").value !="" || document.getElementById("parentAccount").value != null){
			parent.style.left=event.clientX+document.body.scrollLeft+10;
			parent.style.top=event.clientY+document.body.scrollTop+10;
		}
		if(parseInt(document.getElementById("accountLevelId").value) >=8){
			billableCode.style.left=event.clientX+document.body.scrollLeft+10;
			billableCode.style.top=event.clientY+document.body.scrollTop+10;
		}		
	}

</script>
</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onload="onLoadparentCheck();"
	>

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">
	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include
			page="../common/dpheader.jsp" flush="" /></TD>
	</TR>
	<TR valign="top">
		<TD width="167" align="left" bgcolor="b4d2e7" valign="top"
			height="100%"><jsp:include page="../common/leftHeader.jsp"
			flush="" /></TD>
		<TD valign="top" width="100%" height="100%"><html:form
			action="/initCreateAccount" focus="accountLevel">
			<TABLE border="0" cellspacing="0" cellpadding="0" class="text">
				<TR>
					<TD width="521" valign="top">
					<TABLE width="570" border="0" cellpadding="5" cellspacing="0"
						class="text">
						<TR>
							<TD colspan="4" class="text"><BR>
							<IMG src="<%=request.getContextPath()%>/images/createAccount.gif"
								width="505" height="22" alt=""></TD>
						</TR>
						<TR>
							<TD colspan="4"><FONT color="RED"><STRONG> <html:errors
								bundle="view" property="message.account" /><html:errors
								bundle="error" property="errors.account" /><html:errors /></STRONG></FONT></TD>
						</TR>
						<tr>
							<TD width="126" class="text"><STRONG><FONT
								color="#000000"><bean:message key="account.accountlevel"
								bundle="view" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="176"><FONT color="#003399"> <html:select
								property="accountLevel" styleId="accountLevel" tabindex="1"
								styleClass="w130" onchange="parentCheck()">
								<html:option value="0">
									<bean:message key="application.option.select" bundle="view" />
								</html:option>
								<html:options collection="accountLevelList"
									labelProperty="accountLevelName" property="accessLevelId" />
							</html:select> </FONT><FONT color="#ff0000" size="-2"></FONT><a href="#"
								onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.accountlevel" />','Information','width=500,height=100,resizable=yes')">
							<b> ? </b> </a></TD>
						</tr>
						<TR>

							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.accountName" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="176"><FONT color="#3C3C3C"> <html:text
								property="accountName" styleClass="box" styleId="accountName"
								onkeypress="alphaNumWithSpace()" size="19" maxlength="100"
								tabindex="2" /> </FONT><a href="#"
								onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.accountName" />','Information','width=500,height=100,resizable=yes')">
							<b> ? </b> </a></TD>
							
							<TD class="text pLeft15" id="userName"><STRONG><FONT 
								color="#000000"><bean:message bundle="view"
								key="account.accountCode" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="175" id="userNameValue"><FONT color="#003399"><html:text
								property="accountCode" tabindex="3" styleClass="box" 
								styleId="accountCode" size="19" maxlength="9"
								name="DPCreateAccountForm" onkeypress="alphaNumWithoutSpace()" />
							</FONT><FONT color="#ff0000" size="-2"></FONT><a href="#"
								onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.accountCode" />','Information','width=500,height=100,resizable=yes')">
							<b> ? </b> </a></TD>
							</TR>
						<tr>	
							<TD width="121" class="text" nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.contactName" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="176"><FONT color="#3C3C3C"> <html:text
								property="contactName" styleClass="box" styleId="accountName"
								onkeypress="alphaNumWithSpace()" size="19" maxlength="100"
								tabindex="4" /> </FONT><a href="#"
								onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.contactName" />','Information','width=500,height=100,resizable=yes')">
							<b> ? </b> </a></TD>
							<TD class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.email" /></FONT>
								<div id="emailMand" style="display: inline;">
								<font color="red">*</font></div> :</STRONG></TD>
							<TD width="175"><FONT color="#3C3C3C"> <html:text
								property="email" tabindex="5" styleClass="box" styleId="email"
								name="DPCreateAccountForm" size="19" maxlength="64" /> </FONT></TD>
							
						</TR>
						<TR id="fseNumber">
							<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.simNumber" /></FONT> :</STRONG></TD>
							<TD width="175"><FONT color="#003399"> <html:text
								property="simNumber" styleClass="box" styleId="simNumber"
								size="20" maxlength="20" tabindex="6" name="DPCreateAccountForm" />
							</FONT><FONT color="#ff0000" size="-2"></FONT></TD>
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.mobileNumber" /></FONT><FONT color="RED">*</FONT> :<br>
							(<bean:message key="application.digits.mobile_number"
								bundle="view" />)</br>
							</STRONG></TD>

							<TD width="176"><FONT color="#3C3C3C"> <html:text
								property="mobileNumber" styleClass="box" styleId="mobileNumber"
								tabindex="7" onkeypress="isValidNumber()" size="19"
								name="DPCreateAccountForm" maxlength="10" /> </FONT></TD>
						</TR>
						
						<TR id="retailerNumber">
							<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.simNumber" /></FONT> :</STRONG></TD>
							<TD width="175"><FONT color="#003399"> <html:text
								property="simNumber" styleClass="box" styleId="simNumber"
								size="20" maxlength="20" tabindex="6" name="DPCreateAccountForm" />
							</FONT><FONT color="#ff0000" size="-2"></FONT></TD>
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.retailer.mobileNumber" /></FONT><FONT color="RED">*</FONT> :<br>
							(<bean:message key="application.digits.mobile_number"
								bundle="view" />)</br>
							</STRONG></TD>

							<TD width="176"><FONT color="#3C3C3C"> <html:text
								property="retailerMobileNumber" styleClass="box" styleId="retailerMobileNumber"
								tabindex="7" onkeypress="isValidNumber()" size="19"
								name="DPCreateAccountForm" maxlength="10" /> </FONT>
							</TD>
						</TR>
						
						<TR>
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.pin" /></FONT><FONT color="RED">*</FONT> :</STRONG>
							</TD>
							<TD width="176"><FONT color="#3C3C3C"> <html:text
								name="DPCreateAccountForm" property="accountPinNumber"
								tabindex="8" styleClass="box" styleId="accountPinNumber"
								size="19" maxlength="6" onkeypress="isValidNumber()" /> </FONT>
							</TD>
							<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.circle" /></FONT><FONT color="RED">*</FONT> :</STRONG>
							</TD>
							<TD width="175"><FONT color="#003399"><logic:match
								name="DPCreateAccountForm" property="showCircle" value="A">
								<logic:match name="DPCreateAccountForm" property="userType"
									value="E">
									<html:select property="circleId" tabindex="9" styleClass="w130"
										onchange="getChange('zone');getChange('state');getChange('warehouse');">
										<html:option value="">
											<bean:message key="application.option.select" bundle="view" />
										</html:option>
										<html:options collection="circleList"
											labelProperty="circleName" property="circleId" />
									</html:select>
								</logic:match>
							</logic:match> <logic:match name="DPCreateAccountForm" property="showCircle"
								value="N">
								<logic:match name="DPCreateAccountForm" property="userType"
									value="E">
									<html:select property="circleId" tabindex="10" disabled="true"
										styleClass="w130">
										<html:options collection="circleList"
											labelProperty="circleName" property="circleId" />
									</html:select>
								</logic:match>
							</logic:match></FONT><FONT color="#ff0000" size="-2"></FONT><a href="#"
								onClick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.circleId" />','Information','width=500,height=100,resizable=yes')">
							<b> ? </b> </a>
							</TD>
						</TR>
						<TR id="showZoneCityId" style="display: inline;">
 							<logic:match name="DPCreateAccountForm" property="showZoneCity"
								value="A">
								<TD width="126" class="text pLeft15"><STRONG><FONT
									color="#000000"><bean:message bundle="view"
									key="account.zone" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
								<TD width="175"><FONT color="#003399"> <html:select
									property="accountZoneId" tabindex="11" styleClass="w130"
									onchange="getChange('city')">
									<html:option value="">
										<bean:message key="application.option.select" bundle="view" />
									</html:option>
									<logic:notEmpty name="DPCreateAccountForm" property="zoneList">
										<html:options collection="zoneList"
											labelProperty="accountZoneName" property="accountZoneId" />
									</logic:notEmpty>
								</html:select> </FONT></TD>
								<TD width="121" class="text"><STRONG><FONT
									color="#000000"><bean:message bundle="view"
									key="account.city" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
								<TD width="176"><FONT color="#003399"> <html:select onchange="getChange('beat')"
									property="accountCityId" tabindex="12" styleClass="w130">
									<html:option value="">
										<bean:message key="application.option.select" bundle="view" />
									</html:option>
									<logic:notEmpty name="DPCreateAccountForm" property="cityList">
										<html:options collection="cityList"
											labelProperty="accountCityName" property="accountCityId" />
									</logic:notEmpty>
								</html:select> </FONT></TD>
							</logic:match>
						</TR>	
						<TR id="showTransactionType" style="display: none;">
						<TD id="showDistrict" style="display: none;" class="text"><STRONG><FONT color="#000000"><bean:message bundle="view"
									key="account.district" />:</STRONG>
							</TD>
							<TD width="175" id="District" style="display: none;"><FONT color="#003399" >
							<html:select property="accountDistrictId" name="DPCreateAccountForm" tabindex="13" styleClass="w130" style="width:175; height:50px;">
								<html:option value="-1">
									<bean:message key="application.option.select" bundle="view" />
								</html:option>
								</html:select></FONT></TD>
						<TD class="text"><STRONG><FONT color="#000000"><bean:message bundle="view"
									key="account.transaction" /><FONT color="RED">*</FONT> :</STRONG>
							</TD>
							<TD width="175" id="transactionType" style="display: none;"><FONT color="#003399" >
							<html:select property="transactionId" name="DPCreateAccountForm" tabindex="14" styleClass="w130" style="width:175; height:50px;">
								<html:option value="-1">
									<bean:message key="application.option.select" bundle="view" />
								</html:option>
								<html:options collection="retailerTransList" labelProperty="retailerTypeName" property="retailerTransId" />
								</html:select></FONT></TD>
						</TR>



						<TR id="showStateId" style="display: inline;">
								<TD width="126" class="text pLeft15"><STRONG><FONT
									color="#000000"><bean:message bundle="view"
									key="account.state" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
								<TD width="175"><FONT color="#003399"> <html:select
									property="accountStateId" tabindex="15" styleClass="w130">
									<html:option value="">
										<bean:message key="application.option.select" bundle="view" />
									</html:option>
									<logic:notEmpty name="DPCreateAccountForm" property="stateList">
										<html:options collection="stateList"
											labelProperty="accountStateName" property="accountStateId" />
									</logic:notEmpty>
								</html:select> </FONT></TD>
								
								<!--  added by ARTI for warehaouse list box BFR -11 release2 -->
								
								<TD width="121" class="text pLeft15"><STRONG><FONT
									color="#000000"><bean:message bundle="view"
									key="account.warehouse" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
								<TD width="175"><FONT color="#003399"> <html:select
									property="accountWarehouseCode" tabindex="16" styleClass="w130">
									<html:option value="">
										<bean:message key="application.option.select" bundle="view" />
									</html:option>
									<logic:notEmpty name="DPCreateAccountForm" property="wareHouseList">
										<html:options collection="wareHouseList"
											labelProperty="accountWarehouseName" property="accountWarehouseCode" />
									</logic:notEmpty>
								</html:select> </FONT></TD>
								<TD width="176"></TD>
								<!--  ended by ARTI for warehaouse list box BFR -11 release2  -->
						</TR>
								

						<TR>
							<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.accountAddress" /></FONT><FONT color="RED">*</FONT> :</STRONG>
							</TD>
							<TD width="175"><FONT color="#003399"> <html:textarea
								property="accountAddress" styleId="accountAddress" tabindex="17"
								onfocus="setAddressStatus()" onblur="ResetAddressStatus()" onkeypress="alphaNumWithSpace()" 
								cols="17" rows="3" onkeyup="return maxlength('1');"></html:textarea> </FONT>
							</TD>
							<TD width="126" class="text pLeft15"><STRONG> <FONT
								color="#000000"><bean:message bundle="view"
								key="account.accountAddress2" /></FONT> :</STRONG>
							</TD>
							<TD width="176"><FONT color="#003399"> <html:textarea onkeyup="return maxlength('2');" onkeypress="alphaNumWithSpace()" 
								property="accountAddress2" styleId="accountAddress2"
								tabindex="18" cols="17" rows="3"></html:textarea> </FONT>
							</TD>
						</TR>
						
						
						<tr id="flag" style="display: none;">
							<td><html:text property="accountLevelFlag" value="" tabindex="19"></html:text>
							</td>
						</tr>
						<TR id="showParentRow" style="display: inline;">
								<TD width="126" class="text pLeft15"><STRONG><FONT
									color="#000000">
								<div id="divParentMand" style="display:block;"><bean:message
									bundle="view" key="account.parentAccount" />:</div>
								</FONT>
								<div id="divParentNotMand" style="display:none;"><bean:message
									bundle="view" key="account.parentAccount" /><FONT color="RED">*</FONT>:</div>
								</STRONG></TD>
								<TD width="175"><FONT color="#003399"><html:text
									property="parentAccount" styleClass="box" tabindex="20"
									 disabled="true" size="16" maxlength="30" onmouseover="ShowLoginName(this);"
									onmouseout="ShowAccountName(this);" onmousemove="move_Area;" onkeypress="isValidNumAlpha()" /> </FONT>
	 						<div id="divSearchButton">
	 								<INPUT type="button"
									tabindex="21" disabled="true" name="searchbutton"
									id="searchbutton" class="submit" value='Find'
									onclick="OpenParentWindow()" />  
									<a href="#"
									onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.parentAccount" />','Information','width=500,height=100,resizable=yes')">
								<b> ? </b> </a></TD>
						</TR>
						<tr id="showLapuNumber" style="display: none;">
							<TD width="126" class="text pLeft15" nowrap="nowrap"><STRONG>
							<FONT color="#000000"> <bean:message bundle="view"
								key="account.lapu" /> : </FONT> </STRONG></TD>
							<TD width="175" nowrap="nowrap"><FONT color="#003399">
							<html:text property="lapuMobileNo" styleClass="box" tabindex="22"
								size="16" maxlength="10" onkeypress="isValidNumber()" /> </FONT><a
								href="#"
								onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="account.lapuMobileNo" />','Information','width=500,height=100,resizable=yes')">
							<b> ? </b> </a></TD>
							<TD width="126" class="text pLeft15" nowrap="nowrap"><STRONG>
							<FONT color="#000000"> <bean:message bundle="view"
								key="account.FTA" /> : </FONT></STRONG></TD>
							<TD width="175" nowrap="nowrap"><FONT color="#003399">
							<html:text property="FTAMobileNo" styleClass="box" tabindex="23"
								size="16" maxlength="10" onkeypress="isValidNumber()" /> </FONT><a
								href="#"
								onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="account.FTAMobileNo" />','Information','width=500,height=100,resizable=yes')">
							<b> ? </b> </a></TD>
						</tr>
												
						<tr id="showFTANumber" style="display: none;">
													<TD width="126" class="text pLeft15" nowrap="nowrap"><STRONG>
							<FONT color="#000000"> <bean:message bundle="view"
								key="account.lapu1" /> : </FONT> </STRONG></TD>
							<TD width="175" nowrap="nowrap"><FONT color="#003399">
							<html:text property="lapuMobileNo1" styleClass="box"
								tabindex="24" size="16" maxlength="10"
								onkeypress="isValidNumber()" /> </FONT><a href="#"
								onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="account.lapuMobileNo1" />','Information','width=500,height=100,resizable=yes')">
							<b> ? </b> </a></TD>
							<TD width="126" class="text pLeft15" nowrap="nowrap"><STRONG>
							<FONT color="#000000"> <bean:message bundle="view"
								key="account.FTA1" /> : </FONT> </STRONG></TD>
							<TD width="175" nowrap="nowrap"><FONT color="#003399">
							<html:text property="FTAMobileNo1" styleClass="box" tabindex="25"
								size="16" maxlength="10" onkeypress="isValidNumber()" /> </FONT><a
								href="#"
								onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="account.FTAMobileNo1" />','Information','width=500,height=100,resizable=yes')">
							<b> ? </b> </a></TD>
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
						
						
<%-- 						<TR id="showRate" style="display: none">
							<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.rate" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="175"><FONT color="#3C3C3C"> <html:text
								property="rate" styleClass="box" size="19" 
								tabindex="26" maxlength="3" onkeypress="isValidRate()" /> </FONT><a
								href="#"
								onClick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.rate" />','Information','width=500,height=100,resizable=yes')">
							<b> ? </b> </a></TD>
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.threshold" /></FONT>:</STRONG></TD>
							<TD width="176"><FONT color="#3C3C3C"> <html:text
								property="threshold" tabindex="27" styleClass="box" size="19"
								maxlength="10" onkeypress="isValidRate()" /> </FONT><a href="#"
								onClick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.threshold" />','Information','width=500,height=100,resizable=yes')">
							<b> ? </b> </a></TD>
						</TR>	--%>
						
						<tr id="tradeCategory" style="display: none;">
							<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"> <bean:message bundle="view"
								key="account.trade" /></FONT> <FONT color="RED">*</FONT></STRONG> :</TD>
							<TD width="175" nowrap="nowrap"><FONT color="#003399">
							<html:select property="tradeId" tabindex="28" styleClass="w130"
								onchange="getChange('category')">
								<html:option value="0">
									<bean:message key="application.option.select" bundle="view" />
								</html:option>
								<html:options collection="tradeList" labelProperty="tradeName"
									property="tradeId" />
							</html:select> </FONT></TD>
							<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"> <bean:message bundle="view"
								key="account.distcategory" /></FONT> <FONT color="RED">*</FONT></STRONG> : </TD>
							<TD width="175"><FONT color="#003399"> <html:select
								property="tradeCategoryId" tabindex="29" styleClass="w130">
								<html:option value="0">
									<bean:message key="application.option.select" bundle="view" />
								</html:option>
								<logic:notEmpty name="DPCreateAccountForm"
									property="tradeCategoryList">
									<html:options collection="tradeCategoryList"
										labelProperty="tradeCategoryName" property="tradeCategoryId" />
								</logic:notEmpty>
							</html:select> </FONT></TD>
						</tr>
					<TR id="showBillableTags" style="display: none;">
								<TD width="126" class="text">
								<STRONG>
									<FONT color="#000000"> <bean:message bundle="view" key="account.billable" /></FONT>
									<FONT color="RED">*</FONT>:
								</STRONG>
								</TD>
								<TD width="175"><FONT color="#003399"> 
									<html:select property="isbillable" tabindex="30" styleClass="w130"
									onchange="checkBillable(),setBillableDiv()">
										<html:option value="0">
											<bean:message key="application.option.select" bundle="view" />
										</html:option>
										<html:option value="Y">
											<bean:message key="application.option.yes" bundle="view" />
										</html:option>
										<html:option value="N">
											<bean:message key="application.option.no" bundle="view" />
										</html:option>
									</html:select> </FONT>
									<a href="#"
									onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.billable" />','Information','width=500,height=100,resizable=yes')">
									<b> ? </b></a></TD>
									<TD width="126" class="text pLeft15"><STRONG><FONT
										color="#000000"> <bean:message bundle="view"
										key="account.billablecode" /> </FONT>
										<div id="div12" align="left" style="display:none;align:left">
											<FONT color="RED">*</FONT></div>:
										</STRONG>
									</TD>
									<TD width="176"><FONT color="#003399"> <html:text
									property="billableCode" styleClass="box" tabindex="31"
									size="16" readonly="true" maxlength="30"onmouseover="ShowLoginName(this);"
									onmouseout="ShowAccountName(this);" onmousemove="move_Area;"
									onkeypress="isValidNumAlpha()" /> </FONT>
									<a href="#"
									onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.billablecode" />','Information','width=500,location=no,height=100,resizable=yes')">
								<b> ? </b> </a></TD>
							</TR>
						<TR>
							<TD class="text"><STRONG><FONT	color="#000000">
							<bean:message bundle="view" key="account.openingdate" /></FONT> :</STRONG></TD>
							<TD width="155"><FONT color="#003399"> <html:text
							property="openingDt" styleClass="box" styleId="openingDt" readonly="true" 
							size="19" maxlength="10" name="DPCreateAccountForm" tabindex="32"/>
							<script language="JavaScript">
							new tcal ({
							// form name
							'formname': 'DPCreateAccountForm',
							// input name
							'controlname': 'openingDt'
							});
							
							</script>
							</FONT></TD>
							<TD width="121" class="text"><STRONG><FONT	color="#000000">
								<div id="uniqueCode" style="display: none;">
									<bean:message bundle="view" key="account.Code" /> 
									<font color="red">*:</font>
								</div>
								<div id="employeeid" style="display: none;">
									<bean:message key="account.EmployeeCode" bundle="view"/>
									<font color="red">*:</FONT>
								  </div>
							</STRONG></TD>
							<TD width="176"><div id="hideForFnR" style="display: inline;">
								<FONT color="#3C3C3C"> <html:text
								property="code" styleClass="box" styleId="code" tabindex="33"
								onkeypress="alphaNumWithoutSpace()" size="19"
								name="DPCreateAccountForm" maxlength="10" /></FONT> <a href="#"
								onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.code" />','Information','width=500,location=no,height=100,resizable=yes')">
							<b> ? </b> </a></div></TD>
						</TR>
		<%-- 			<tr id="distManagers" style="display: none;">
						<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"> <bean:message bundle="view"
								key="account.finance" /></FONT> <FONT color="RED">*</FONT> : 
						</TD>
						<TD width="175"><FONT color="#003399"> <html:select
							property="financeId" tabindex="34" styleClass="w130">
							<html:option value="0">
								<bean:message key="application.option.select" bundle="view" />
							</html:option>
							<logic:notEmpty name="DPCreateAccountForm" property="financeList">
							<bean:define id="finance" property="financeList" name="DPCreateAccountForm"/>
							<html:options collection="finance"
								labelProperty="financeName" property="financeId" />
							</logic:notEmpty>
						</html:select> </FONT>
						</TD>
						<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"> <bean:message bundle="view"
								key="account.commerce" /></FONT> <FONT color="RED">*</FONT> : 
						</TD>
						<TD width="175"><FONT color="#003399"> <html:select
							property="commerceId" tabindex="35" styleClass="w130">
							<html:option value="0">
								<bean:message key="application.option.select" bundle="view" />
							</html:option>
							<logic:notEmpty name="DPCreateAccountForm" property="commerceList">
							<bean:define id="commerce" property="commerceList" name="DPCreateAccountForm"/>
							<html:options collection="commerce"
								labelProperty="commerceName" property="commerceId" />
							</logic:notEmpty>
						</html:select> </FONT>
						</TD> 
						<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"> <bean:message bundle="view"
								key="account.sales" /></FONT> <FONT color="RED">*</FONT> : 
						</TD>
						<TD width="175"><FONT color="#003399"> <html:select
							property="salesId" tabindex="36" styleClass="w130">
							<html:option value="0">
								<bean:message key="application.option.select" bundle="view" />
							</html:option>
							<logic:notEmpty name="DPCreateAccountForm" property="salesList">
							<bean:define id="sales" property="salesList" name="DPCreateAccountForm"/>
							<html:options collection="sales"
								labelProperty="salesName" property="salesId" />
							</logic:notEmpty>
						</html:select> </FONT>
						</TD>
						</tr> --%>
						<tr id="showBeat" style="display: none;">
						<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"> <bean:message bundle="view"
								key="beat.beatname" /></FONT></STRONG> : 
						</TD>
						<TD width="175"><FONT color="#003399"> <html:select
							property="beatId" tabindex="37" styleClass="w130">
							<html:option value="0">
								<bean:message key="application.option.select" bundle="view" />
							</html:option>
							<logic:notEmpty name="DPCreateAccountForm" property="beatList">
							<bean:define id="beatList" name="DPCreateAccountForm" property="beatList"/>
							<html:options collection="beatList" labelProperty="beatName" property="beatId"/>
							</logic:notEmpty>
						</html:select> </FONT>
						</TD>
							
						<TD width="121" class="text"><div id="altChannel" style="display: none"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="account.alternateChannel"/></FONT>:</STRONG>
							</div>
						</TD>
						<TD width="176"><div id="altChannel1" style="display: none"><FONT color="#003399">
						 <html:select property="altChannelType" tabindex="38" styleClass="w130">
							<html:option value="0">
								<bean:message key="application.option.select" bundle="view" />
							</html:option>	
							<html:options collection="altChannelTypeList"
								labelProperty="altChannelName" property="altChannelType" />
						</html:select></FONT><FONT color="#ff0000" size="-2"></FONT>
						</TD>
						</tr>
						<tr  id="retailerType" style="display: none;">
						<TD width="121" class="text"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="account.retailerType" /></FONT> :</STRONG></TD>
						<TD width="176"><FONT color="#003399"> <html:select
							property="retailerType" tabindex="39" styleClass="w130">
						<html:option value="0">
								<bean:message key="application.option.select" bundle="view" />
							</html:option>	
						<html:options collection="retailerCatList"
								labelProperty="retailerTypeName" property="retailerType" />
						</html:select></FONT><FONT color="#ff0000" size="-2"></FONT>
						</TD>
						<TD width="121" class="text"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="account.channelType" /></FONT><font color="red">*</font> :</STRONG></TD>
						<TD width="176"><FONT color="#003399"> <html:select
							property="channelType" tabindex="40" styleClass="w130">
						<html:option value="0">
								<bean:message key="application.option.select" bundle="view" />
							</html:option>	
						<html:options collection="channelTypeList"
								labelProperty="channelName" property="channelType" />
						</html:select></FONT><FONT color="#ff0000" size="-2"></FONT>
						</TD>
						</tr>
						<tr id="channelTypeId" style="display: none">
						<TD width="121" class="text"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="account.outletType" /></FONT><font color="red">*</font> :</STRONG></TD>
						<TD width="176"><FONT color="#003399"> <html:select
							property="outletType" tabindex="41" styleClass="w130">
							<html:option value="0">
								<bean:message key="application.option.select" bundle="view" />
							</html:option>	
							<html:options collection="outletList"
								labelProperty="outletName" property="outletType" />
						</html:select></FONT><FONT color="#ff0000" size="-2"></FONT>
						</TD>
												<TD width="121" class="text">
							<div id="categoryCode" style="display: none;">
						<STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.category" /></FONT> :</STRONG>
							</div>	
						</TD>
						<TD width="176">
						<div id="categoryName" style="display: none;">
							<FONT color="#003399"> <html:select
								property="category" tabindex="42" styleClass="w130">
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
							</html:select></FONT><FONT color="#ff0000" size="-2"></FONT><a href="#"
									onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.category" />','Information','width=500,height=100,resizable=yes')">
								<b> ? </b> </a>
								</div>
								</TD>
						</tr>
						
						<TR id="tinNservice" style="display: none;">
							<TD class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.tinNo" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="175"><FONT color="#003399"><html:text
								property="tinNo" tabindex="43" styleClass="box"
								styleId="tinNo" size="19" maxlength="50"
								name="DPCreateAccountForm"/>
							</FONT><FONT color="#ff0000" size="-2"></FONT></TD>
							<TD class="text" ><STRONG><FONT	color="#000000">
							<bean:message bundle="view" key="account.tinDate" /></FONT><font color="red">*</FONT> :</STRONG></TD>
						<TD width="155"><FONT color="#003399"> <html:text
						property="tinDt" styleClass="box" styleId="tinDt" readonly="true" 
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
						
						<TR id="panNcst" style="display: none;">
							<TD class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.serviceTax" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="175"><FONT color="#003399"><html:text
								property="serviceTax" tabindex="44" styleClass="box"
								styleId="serviceTax" size="19" maxlength="50"
								name="DPCreateAccountForm"/>
							</FONT><FONT color="#ff0000" size="-2"></FONT></TD>
							<TD width="121" class="text" nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.PAN" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="175"><FONT color="#003399"><html:text
								property="panNo" tabindex="45" styleClass="box"
								styleId="panNo" size="19" maxlength="10"
								name="DPCreateAccountForm" onkeypress="alphaNumWithoutSpace();"/>
							</FONT></TD>
						</TR>
						
						<TR id="cstBox" style="display: none;">
							<TD class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.CST" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="175"><FONT color="#003399"><html:text
								property="cstNo" tabindex="46" styleClass="box"
								styleId="cstNo" size="19" maxlength="10" name="DPCreateAccountForm" />
							</FONT><FONT color="#ff0000" size="-2"></FONT></TD>
							
							<TD class="text" ><STRONG><FONT	color="#000000">
							<bean:message bundle="view" key="account.CSTDATE" /></FONT> <font color="red">*</FONT>:</STRONG></TD>
							<TD width="155"><FONT color="#003399"> <html:text
							property="cstDt" styleClass="box" styleId="cstDt" readonly="true" 
							size="19" maxlength="10" name="DPCreateAccountForm" />
							<script language="JavaScript">
new tcal ({
// form name
'formname': 'DPCreateAccountForm',
// input name
'controlname': 'cstDt'
});

</script>
						 	</FONT></TD>	
						</TR>
						
						<tr id="emailNoctroi" style="display: none;">
							<%-- <TD class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.ReportingEmail" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="175"><FONT color="#003399"><html:text
								property="reportingEmail" tabindex="47" styleClass="box"
								styleId="reportingEmail" size="19" maxlength="30"
								name="DPCreateAccountForm" onkeypress="alphaNumWithoutSpace()" /> 
							</FONT><FONT color="#ff0000" size="-2"></FONT></TD>--%>
							<TD width="126" class="text">
						<STRONG>
							<FONT color="#000000"> <bean:message bundle="view" key="account.octroiCharge" /></FONT>
							<FONT color="RED">*</FONT>:
						</STRONG>
						</TD>
						<TD width="175"><FONT color="#003399"> 
						<html:select property="octroiCharge" tabindex="48" styleClass="w130">
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
						
						<tr id="locatorFields" style="display: none;">
							<TD width="121" class="text"><STRONG> <FONT
								color="#000000"> <bean:message bundle="view"
								key="account.distLocator" /></FONT><font color="red">*</FONT>:</STRONG></TD>
							<TD width="176"><FONT color="#003399"> <html:text
								property="distLocator" styleClass="box" styleId="distLocator"
								size="30" maxlength="50" tabindex="49" /> </FONT></TD>
					<!--by saumya for repair facility -->
								<TD width="121"><STRONG> <FONT
								color="#000000"> <bean:message bundle="view"
								key="account.repFacility" /></FONT>:</STRONG></TD>

							    <TD width="176"><FONT color="#003399"> 
						<html:select property="repairFacility" tabindex="50" styleClass="w130" styleId="repairFacility" >
							<html:option value="N">
											<bean:message key="application.option.no" bundle="view" />
							</html:option>
										<html:option value="Y">
											<bean:message key="application.option.yes" bundle="view" />
										</html:option>
										
						  </html:select> </FONT></TD>
						  <!-- ended by saumya  -->	
						</tr>
						
							<!--  added by sugandha for demoID for retailers  -->
							<tr id="showDemoId" style="display: none;">
							<TD width="80" class="text pLeft15"><STRONG><FONT
									color="#000000"><bean:message bundle="view"
									key="account.demoId1" /></FONT> :</STRONG></TD>
							<TD width="80"><FONT color="#003399"><html:text
								property="demoId1" tabindex="51" styleClass="box" 
								styleId="demoId" size="19" maxlength="15"
								name="DPCreateAccountForm" onkeypress="alphaNumWithoutSpace()" />
							<TD width="80" class="text pLeft15"><STRONG><FONT
									color="#000000"><bean:message bundle="view"
									key="account.demoId2" /></FONT> :</STRONG></TD>
							<TD width="80"><FONT color="#003399"><html:text
								property="demoId2" tabindex="52" styleClass="box" 
								styleId="demoId" size="19" maxlength="15"
								name="DPCreateAccountForm" onkeypress="alphaNumWithoutSpace()" />
						</tr>
						<tr id="showDemoId2" style="display: none;">
							<TD width="80" class="text pLeft15"><STRONG><FONT
									color="#000000"><bean:message bundle="view"
									key="account.demoId3" /></FONT> :</STRONG></TD>
							<TD width="80"><FONT color="#003399"><html:text
								property="demoId3" tabindex="53" styleClass="box" 
								styleId="demoId" size="19" maxlength="15"
								name="DPCreateAccountForm" onkeypress="alphaNumWithoutSpace()" />
							<TD width="80" class="text pLeft15"><STRONG><FONT
									color="#000000"><bean:message bundle="view"
									key="account.demoId4" /></FONT> :</STRONG></TD>
							<TD width="80"><FONT color="#003399"><html:text
								property="demoId4" tabindex="54" styleClass="box" 
								styleId="demoId" size="19" maxlength="15"
								name="DPCreateAccountForm" onkeypress="alphaNumWithoutSpace()" />
								
							</tr>		
						
						<TR>
							<TD class="text"><STRONG><FONT color="#000000">&nbsp;</FONT></STRONG></TD>
							<TD colspan="3">
							<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
								<TR align="center">
									<TD width="70"><INPUT class="submit" type="button"
										name="Submit" value="Submit" tabindex="55"
										onclick="submitAccount();">
									</TD>
									<TD width="70"><INPUT class="submit" type="button"
										onclick="resetForm();" name="Submit2" value="Reset"
										tabindex="56"></TD>
								</TR>
							</TABLE>
							</TD>
						</TR>
						<TR>
							<TD colspan="4" align="center">&nbsp;</TD>
							<html:hidden property="methodName" value="createAccount" />
							<!-- Active -->
							<html:hidden property="status" value="A" />
							<html:hidden property="showCircle" styleId="showCircle" />
							<html:hidden property="roleName" value="N"/>
						</TR>
					</TABLE>
					</TD>
				</TR>
				<html:hidden property="accountLevelNameHidden" />
				<html:hidden property="showNumbers" styleId="showNumbers" />
				<html:hidden styleId="userType" property="userType"
					name="DPCreateAccountForm" />
				<html:hidden styleId="accountStatus" property="accountStatus"
					value="HH" />
				<html:hidden styleId="submitStatus" property="submitStatus" />
				<html:hidden styleId="accountLevelId" property="accountLevelId" />
				<html:hidden styleId="showBillableCode" property="showBillableCode" />
				<html:hidden styleId="openingBalance" property="openingBalance"
					value="0.00" />
				<html:hidden property="heirarchyId" styleId="heirarchyId"/>
				<html:hidden styleId="accessLevelId" property="accessLevelId" />
				<html:hidden property="accountLevelFlag" styleId="accountLevelFlag" />
				<html:hidden styleId="parentAccountId" property="parentAccountId" />
				<html:hidden styleId="billableCodeId" property="billableCodeId" />
				<html:hidden styleId="tradeIdInBack" property="tradeIdInBack" />
				<html:hidden styleId="tradeCategoryIdInBack" property="tradeCategoryIdInBack" />
				<html:hidden styleId="isBillableInBack" property="isBillableInBack" />
				<html:hidden styleId="hiddenCityId" property="hiddenCityId" />
				<html:hidden styleId="hiddenZoneId" property="hiddenZoneId" />
				<html:hidden styleId="hiddenCircleId" property="hiddenCircleId" />
				<html:hidden styleId="hiddenOutletType" property="hiddenOutletType"/>
				<html:hidden style="showZoneCity" property="showZoneCity"/>
				<html:hidden property="parentLoginName" name="DPCreateAccountForm"/>
				<html:hidden property="parentAccountName" name="DPCreateAccountForm"/>
				<html:hidden property="billableLoginName" name="DPCreateAccountForm"/>
				<html:hidden property="billableAccountName" name="DPCreateAccountForm"/>
			</TABLE>
			<DIV id="accountLeveldiv" style="display:none">
			<TABLE>
				<TR>
					<TD><html:select property="accountLevelStatus"
						styleId="accountLevelStatus">
						<html:option value="0">
						</html:option>
						<html:options collection="accountLevelList"
							labelProperty="accountAccessLevel" property="accessLevelId" />
					</html:select></TD>
				</TR>
				<div id="passwordDiv" style="display:block;">
					<TR id="showPasswordRow" style="display:block;">
							
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="account.iPassword" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
						<TD width="175"><FONT color="#003399"> <html:password
							property="IPassword" styleClass="box" styleId="IPassword" value="@@@@@@@@"
							size="19" maxlength="20" tabindex="57" readonly="true" /> </FONT><FONT color="#ff0000"
							size="-2"></FONT></TD>
						<TD width="121" class="text"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="account.checkIPassword" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
						<TD width="176"><FONT color="#3C3C3C"> <html:password
							property="checkIPassword" styleClass="box" value="@@@@@@@@"
							styleId="checkIPassword" size="19" maxlength="20" tabindex="58" readonly="true" />
						</FONT></TD>
						
					</TR>
			</TABLE>
			</DIV>
		</html:form>
		<div id="submitFormDiv" style="display:none;">
		<form name="submitForm" method="post" target="newWin"><input
			type="hidden" name="circleid"><input type="hidden"
			name="parent"></form>
		</div>
		</TD>
	</TR>
	<TR align="center" bgcolor="4477bb" valign="top">
		<TD colspan="2" height="17" align="center"><jsp:include
			page="../common/footer.jsp" flush="" /></TD>
	</TR>
</TABLE>
</BODY>
</html:html>