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
		
function checkReq()
{
	return false;
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
	var isSales=false;
	// function that checks that is any field value is blank or not
	function validate()
	{	
				
		/* Added by Parnika */
		if(parseInt(document.getElementById("accountLevel").value)== 6){
			if(document.forms[0].distTranctionId.length >1){
				for (var i=1; i < document.forms[0].distTranctionId.length; i++){
			   		 if (document.forms[0].distTranctionId[i].selected){
			     		var selectedTran = document.forms[0].distTranctionId[i].value.split(",");
			     		if(selectedTran[0] == "1"){
			     			isSales = true;
			     		}
			     	}
			     }
		    }
		   }
	
			/* End of changes by Parnika */
			
			//Changes for distributor type ( riju )
			if(parseInt(document.getElementById("accountLevel").value)== 6)
			{
				if(document.getElementById("disttype").value==null || document.getElementById("disttype").value=="")
				{
					alert("Please select type");
					document.getElementById("disttype").focus();
					return false; 
				}	
			}
			//Changes for distributor type ( riju )	
	
		document.getElementById("accountLevel").value = document.getElementById("hiddenAccountLevel").value;
	   if(document.getElementById("accountLevel").value==null || document.getElementById("accountLevel").value=="-1"){
			alert('<bean:message bundle="error" key="error.account.invalidlevel" />');
			document.getElementById("accountLevel").focus();
			return false; 
		}
		
		 if(parseInt(document.getElementById("accountLevel").value)<7 ||parseInt(document.getElementById("accountLevel").value)==14)
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
		firstChar = isNumber(contactName.charAt(0));
		if(firstChar==true){
			alert("Contact Name Cannot Begin With A Numeric Character.");
			document.getElementById("contactName").focus();
			return false;
		}
		validAccountCode = isAlphaNumericWithSpace(contactName);
		if(validAccountCode==false){
			alert("Special Characters are not allowed in Contact Name");
			document.getElementById("contactName").focus();
			return false;
		}
		// Validate Email
		if(parseInt(document.getElementById("accountLevel").value) <7 || parseInt(document.getElementById("accountLevel").value) ==14){
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
		var mobileno = document.getElementById("mobileNumber").value;
		if(mobileno==null || mobileno==""){
			alert('<bean:message bundle="error" key="error.account.mobilenumber" />');
			document.getElementById("mobileNumber").focus();
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
		
		if(parseInt(document.getElementById("accountLevel").value) != 0 && parseInt(document.getElementById("accountLevel").value) != 1){
			if((document.getElementById("circleId").value == "" || document.getElementById("circleId").value == "-1" )  && (document.getElementById("circleIdList").value == "" || document.getElementById("circleIdList").value == "-1")){
				alert('<bean:message bundle="error" key="error.account.circleid.empty" />');
				document.getElementById("circleId").focus();
				return false; 
			}
			// added by pratap document.getElementById("accountLevel").value != 2 in this condition
			//alert("hi.."+document.getElementById("accountLevel").value);
			if((document.getElementById("circleId").value == "0")  && (document.getElementById("circleIdList").value == "" || document.getElementById("circleIdList").value == "0") && (document.getElementById("accountLevel").value != 2 && document.getElementById("accountLevel").value != 14) ){
				alert("Select Valid Circle");
				document.getElementById("circleId").focus();
				return false; 
			}
			// adding end by pratap
		}
		if((document.getElementById("accountStateId").value==null || document.getElementById("accountStateId").value=="") && parseInt(document.getElementById("accountLevel").value) == 6){
			alert('<bean:message bundle="error" key="error.account.state" />');
			document.getElementById("accountStateId").focus();
			return false; 
		}
		
		//added by ARTI for warehaouse list box BFR -11 release2
		if((document.getElementById("accountWareHouseCode").value==null || document.getElementById("accountWareHouseCode").value=="") && parseInt(document.getElementById("accountLevel").value) == 6){
		if(isSales == false){
			alert('<bean:message bundle="error" key="error.account.warehouse" />');
			document.getElementById("accountWareHouseCode").focus();
			return false; 
		}
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

		if(document.getElementById("accountLevel").value==null || document.getElementById("accountLevel").value=="-1"){
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
			
		if(document.getElementById("heirarchyId").value=="1" && parseInt(document.getElementById("accountLevel").value) != 14 && parseInt(document.getElementById("accountLevel").value) != 2)
		{
			if((!document.getElementById("parentAccount").disabled)&& (document.getElementById("parentAccount").value==null||document.getElementById("parentAccount").value==""))
			{

				alert('<bean:message bundle="error" key="error.account.parentAccount" />');
				document.getElementById("searchbutton").focus();
				return false; 
			}  
		}	
		
		if(parseInt(document.getElementById("accountLevel").value)== 6){

			if(document.getElementById("distTranctionId").value == "" || document.getElementById("distTranctionId").value == "-1"){
				alert("Please select Transaction type for Distributor. ");
				document.getElementById("distTranctionId").focus();
				return false; 
			} 
			//riju
			//if(document.getElementById("disttype").value == "" /** || document.getElementById("disttype").value == "-1"**/){
			//	alert("Please select Distributor type. ");
			//	document.getElementById("disttype").focus();
			//	return false;  
			
			//riju

		}
		if(parseInt(document.getElementById("accountLevel").value)== 5){
			if(document.getElementById("transactionId").value == "" || document.getElementById("transactionId").value == "-1"){
				alert("Please select Transaction type for TSM. ");
				document.getElementById("transactionId").focus();
				return false; 
			}
		}
		if(document.getElementById("showNumbers").value=="Y")
		{
			var lapu = document.getElementById("lapuMobileNo").value;
			/*
			if((parseInt(document.getElementById("accountLevel").value)!=8 )&&(lapu==null|| lapu=="")){
				alert('<bean:message bundle="error" key="error.account.lapuMobileNo" />');
				document.getElementById("lapuMobileNo").focus();
				return false; 
			}

			if(lapu != "" && lapu.length>0 ){
			
				if((document.getElementById("lapuMobileNo").value.length)<10){
					alert('<bean:message bundle="error" key="error.account.mobilenumberlength" />');
					document.getElementById("lapuMobileNo").focus();
					return false;
				}
			}
			*/
			//Added by Shilpa to check whether the distributor is sales type
			
		if(document.forms[0].distTranctionId.length >1)
		{
			for (var i=1; i < document.forms[0].distTranctionId.length; i++)
	  		 {
		   		 if (document.forms[0].distTranctionId[i].selected)
		     	 {
		     		var selectedTran = document.forms[0].distTranctionId[i].value.split(",");
		     		if(selectedTran[0] == "1") {
		     		/* Added By Parnika */
		     			if(document.getElementById("salesTypeTSM").value == null 
		     			|| document.getElementById("salesTypeTSM").value == "")
		     			{
		     				alert("Please select Sales Type TSM, as distributor has Sales transaction type.");
		     				return false;
		     			}
		     			/* End of changes By Parnika */
		     			
		     			var lapu2 = document.getElementById("lapuMobileNo1").value;
		     			if( lapu2== "" || lapu2 == null)
		     			{
		     				alert("Please enter Lapu Mobile No. 2, as distributor has Sales transaction type.");
		     				document.getElementById("lapuMobileNo1").focus();
		     				return false;
		     			}
		     			
		     			else if(lapu2.length<10)
		     			{
							alert('<bean:message bundle="error" key="error.account.mobilenumberlength" />');
							document.getElementById("lapuMobileNo1").focus();
							return false;
						}
		     		}
		     		/* Added By Parnika */
		     		if(selectedTran[0] == "2") 
		     		{
		     			if(document.getElementById("depositeTypeTSM").value == null 
		     			|| document.getElementById("depositeTypeTSM").value == "")
		     			{
		     				alert("Please select Deposit Type TSM, as distributor has Deposit transaction type.");
		     				return false;
		     			}
		     			
		     			if(lapu == "" || lapu == null)
		     			{
		     				alert("Please enter Lapu Mobile No1, as distributor has Deposit transaction type.");
		     				document.getElementById("lapuMobileNo").focus();
		     				return false;
		     			}
		     			else if(lapu.length>0 )
		     			{
							if(lapu.length<10)
							{
								alert('<bean:message bundle="error" key="error.account.mobilenumberlength" />');
								document.getElementById("lapuMobileNo").focus();
								return false;
							}
						}
		     		}
		     		/* End of changes By Parnika */
		     	 }
	   		}
	 	 }
			// Ends here
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
		if((document.getElementById("FTAMobileNo").value.length)<10){
			alert('<bean:message bundle="error" key="error.account.mobilenumberlength" />');
			document.getElementById("FTAMobileNo").focus();
			return false;
		}
		}
		if(!(document.getElementById("FTAMobileNo1").value == "" || document.getElementById("FTAMobileNo1").value == null)){
		var firstChar = document.getElementById("FTAMobileNo1").value.charAt(0);
		if((document.getElementById("FTAMobileNo1").value.length)<10){
			alert('<bean:message bundle="error" key="error.account.mobilenumberlength" />');
			document.getElementById("FTAMobileNo1").focus();
			return false;
		}
		}

		if( parseInt(document.getElementById("accountLevel").value)!=14)
		{
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
			if(isSales == false){
					alert("Enter Tin No.");
					document.getElementById("tinNo").focus();
					return false;
			}
		}
			
			if(document.getElementById("tinDt").value==null || document.getElementById("tinDt").value==""){
				if(isSales == false){	
					alert('<bean:message bundle="error" key="error.tin.date" />');
					document.getElementById("tinDt").focus();
					return false;
				}
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
			   
	
		if(document.getElementById("accountLevelId").value=='7'){
		if( isSales == false){
			if(document.getElementById("distLocator").value == null || document.getElementById("distLocator").value == "")
			{
				if(isSales == false){
				alert("Enter Distributor Locator Code.");
				document.getElementById("distLocator").focus();
				return false;
				}
			}
			var strString = document.getElementById("distLocator").value;
						var isValidLocator = 	checkLocator(strString);
			if(!isValidLocator){
				alert("Invalid Distributor Locator Code.");
				return false;
			}
			//Shilpa
			}
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
	//Shilpa Error
		if(document.getElementById("srNumber").value==null || document.getElementById("srNumber").value==""){
			alert("Please Enter SR Number.");
			document.getElementById("srNumber").focus();
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
			
		
		document.forms[0].accountAddress.value=trimAll(document.forms[0].accountAddress.value);
		if(parseInt(document.getElementById("accountLevel").value) >= 6)
		{
		document.getElementById("tradeIdInBack").value = document.getElementById("tradeId").value;
		document.getElementById("tradeCategoryIdInBack").value = document.getElementById("tradeCategoryId").value;
		document.getElementById("isBillableInBack").value = document.getElementById("isBillable").value;
		}
		document.getElementById("methodName").value="createAccount";
		if(parseInt(document.getElementById("accountLevel").value)>6 && parseInt(document.getElementById("accountLevel").value) !=14)
		{
			document.getElementById("hiddenZoneId").value=document.getElementById("accountZoneId").value;
			
			document.getElementById("hiddenCircleId").value=document.getElementById("circleId").value;
			document.getElementById("hiddenCityId").value=document.getElementById("accountCityId").value;
			document.getElementById("hiddenOutletType").value=document.getElementById("outletType").value;
		}
		if( parseInt(document.getElementById("accountLevel").value) < 6 || parseInt(document.getElementById("accountLevel").value)==14)
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
		
		var selectedCircleValues ="";
		selectedCircleValues =0;
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
	     	if(parseInt(selectedValCircle[0]) == 0 && (document.getElementById("accountLevel").value != 2 && document.getElementById("accountLevel").value != 14)){ //added by neetika
		     		alert("Select Valid Circle");
		     		document.getElementById("circleIdList").focus();
		     		return false;
	     		}
	     	selectedCircleValues += selectedValCircle[0];
	     	 }
	   	}
   
  }
 		var seltdTransactValues ="";
		var	Id = document.forms[0].distTranctionId[0].value;
		if(Id != "" &&  document.forms[0].distTranctionId.length >1){
			for (var i=1; i < document.forms[0].distTranctionId.length; i++)
	  	 {
	   		 if (document.forms[0].distTranctionId[i].selected)
	     	 {
	     		if(seltdTransactValues !=""){
					seltdTransactValues += ",";
	     		}
	     	var selectedTran = document.forms[0].distTranctionId[i].value.split(",");
	     	if(parseInt(selectedTran[0]) == -1){
	     		alert("Select Valid Transaction");
	     		document.getElementById("distTranctionId").focus();
	     		return false;
	     	}
	     	seltdTransactValues += selectedTran[0];
	     	 }
	   	}
   
  }
  if(seltdTransactValues == "1,2" || seltdTransactValues == "2,1"){
  	if(document.getElementById("lapuMobileNo").value == document.getElementById("lapuMobileNo1").value){
  	 	alert("Lapu Mobile no. 1 and Lapu Mobile no. 2 can not be equal if distributor has both Transaction types.");
  	 	document.getElementById("lapuMobileNo1").focus();
  	 	return false;
  	 }
  }
  		document.getElementById("accountLevel").disabled = false;
		document.forms[0].hiddenCircleIdList.value  = selectedCircleValues;
		document.forms[0].hiddenTranctionIds.value  = seltdTransactValues;
		
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
	function parentCheck(pageReturn)
	{
	var len = document.forms[0].length;
	var controlType = null;
	if(pageReturn == "true" )  {
		document.getElementById("hiddenAccountLevel").value = document.getElementById("accountLevel").value;
		document.getElementById("accountLevel").disabled = false;
		for(i = 0; i < len; i++){
			controlType = document.forms[0].elements[i].type;
			if(controlType == "text"){
				
				if(document.forms[0].elements[i].name != "openingDt" && document.forms[0].elements[i].name != "parentAccount" )
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
	}else{
		document.getElementById("hiddenAccountLevel").value = document.getElementById("accountLevel").value;
		document.getElementById("accountLevel").disabled = true;
	}
	
	document.getElementById("userName").style.display="inline";
	document.getElementById("userNameValue").style.display="inline";
	if(document.getElementById("accountLevel").value == 0 || document.getElementById("accountLevel").value == 1 ||document.getElementById("accountLevel").value == 2 || document.getElementById("accountLevel").value == 14)
	{
		
		if(document.getElementById("accountLevel").value == 0 || document.getElementById("accountLevel").value == 1)
		{
			
			if(pageReturn == "true" ) 
			 {
			document.getElementById("parentAccount").value="";
			document.getElementById("parentAccountName").value="";
			document.getElementById("parentLoginName").value="";
			}
			/* Added by Parnika */
			document.getElementById("parentAccountStyle").style.display="block";
			document.getElementById("divSearchButton").style.display="block";
			document.getElementById("parentColumn").style.display="block";			
			/* End of changes by Parnika */
			document.getElementById("parentAccount").disabled=true;
			document.getElementById("searchbutton").disabled=true;
			document.getElementById("divParentNotMand").style.display="none";
			document.getElementById("divParentMand").style.display="block";
			
			document.getElementById("showTSMDepositeRow").style.display="none";
			document.getElementById("depositeTypeTSMLabel").style.display="none";
			document.getElementById("searchbuton").style.display="none";
			document.getElementById("searchSalesTSM").style.display="none";
			document.getElementById("salesTypeTSMLabel").style.display="none";
			//Changes for distributor type ( riju )
			document.getElementById("distributortype").style.display="none";
			//Changes for distributor type ( riju )
		}
		else{
			
				if(document.getElementById("accountLevel").value == 2 )
					{	
						//alert("circle admin:if");
						if(pageReturn == "true" )  {
							document.getElementById("parentAccount").value="none";
							document.getElementById("parentAccountName").value="none";
							document.getElementById("parentLoginName").value="none";
						}
						//alert("circle admin");
						document.getElementById("parentColumn").style.display="none";
						document.getElementById("parentAccount").style.display="none";
						document.getElementById("divSearchButton").style.display="none";
						//document.getElementById("parentAccount").disabled=false;
						//document.getElementById("parentAccount").readOnly="readonly";
						//document.getElementById("searchbutton").disabled=false;
						//document.getElementById("divParentNotMand").style.display="block";
						document.getElementById("divParentNotMand").style.display="none";
						document.getElementById("divParentMand").style.display="none";
					}
				//added by aman
				else if(document.getElementById("accountLevel").value == 14 )
				{
						document.getElementById("showCircleId").style.display="none";
						document.getElementById("showCircleListId").style.display="inline";
   						//document.getElementById("showZoneCity").value="A";
						//document.getElementById("showZoneCityId").style.display="inline";
						document.getElementById("parentColumn").style.display="none";
						document.getElementById("parentAccount").style.display="none";
						document.getElementById("divSearchButton").style.display="none";
						document.getElementById("sim").style.display="none";
						document.getElementById("sim1").style.display="none";
						document.getElementById("dist").style.display="none";
						document.getElementById("dist1").style.display="none";
						document.getElementById("distcat").style.display="none";
						document.getElementById("distcat1").style.display="none";
						document.getElementById("market").style.display="none";
						document.getElementById("market1").style.display="none";
						document.getElementById("employeeid").style.display="inline";
						document.getElementById("showTSMDepositeRow").style.display="none";
						document.getElementById("showLapuNumber").style.display="none";
						document.getElementById("showFTANumber").style.display="none";
						document.getElementById("showBillableTags").style.display="none";
						document.getElementById("uniqueCode").style.display="none";
						document.getElementById("tinNservice").style.display="none";
						document.getElementById("panNcst").style.display="none";
						document.getElementById("cstBox").style.display="none";
						document.getElementById("emailNoctroi").style.display="none";
						document.getElementById("locatorFields").style.display="none";
						document.getElementById("showZoneCity").value="N";
						document.getElementById("showZoneCityId").style.display="none";
						//Changes for distributor type ( riju )
						document.getElementById("distributortype").style.display="none";
						//Changes for distributor type ( riju )
				
						
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
				}		
		}
		if(document.getElementById("accountLevel").value == 1 )
			{
				document.getElementById("showCircleListId").style.display="none";
				document.getElementById("showCircleId").style.display="inline";
			}	
		if(document.getElementById("accountLevel").value == 2 )
		{
		//niki
		document.getElementById("circleId").disabled=false;
		if(pageReturn == "true" )  {
			//alert("niki");
			document.getElementById("circleId").value="";
			document.getElementById("showCircleId").style.display="none";			//added by aman
			document.getElementById("showCircleListId").style.display="inline";		//added by aman
			//document.getElementById("parentAccountStyle").style.display="block";
			//document.getElementById("divSearchButton").style.display="block";
			//document.getElementById("parentColumn").style.display="block";			
			
			//document.getElementById("parentAccount").disabled=false;
			//document.getElementById("searchbutton").disabled=false ;
			//document.getElementById("divParentNotMand").style.display="inline";
			//document.getElementById("divParentMand").style.display="none";
			
			document.getElementById("showTSMDepositeRow").style.display="none";
			document.getElementById("depositeTypeTSMLabel").style.display="none";
			document.getElementById("searchbuton").style.display="none";
			document.getElementById("searchSalesTSM").style.display="none";
			document.getElementById("salesTypeTSMLabel").style.display="none";
			//Changes for distributor type ( riju )
			document.getElementById("distributortype").style.display="none";
			//Changes for distributor type ( riju )
			
			// Added by sugandha ends here
		}
		}else{
			document.getElementById("circleId").disabled=true;
			if(pageReturn == "true" )  {
				document.getElementById("circleId").value="0";
			}
		}
		
		if(document.getElementById("accountLevel").value != 14){
		document.getElementById("showZoneCity").value="N";
		document.getElementById("showZoneCityId").style.display="none";
		
		document.getElementById("transactionLable").style.display="none";
		document.getElementById("transactionValue").style.display="none";
		if(pageReturn == "true" )  {
			document.getElementById("distTranctionId").value="-1";
		}
		}
		
	}else{
		// Added by Parnika for distributors
		if(document.getElementById("accountLevel").value == 6){

			if(pageReturn == "true" )  {
			document.getElementById("parentAccount").value="";
			document.getElementById("parentAccountName").value="";
			document.getElementById("parentLoginName").value="";
			}
			document.getElementById("parentAccountStyle").style.display="none";
			document.getElementById("divSearchButton").style.display="none";
			document.getElementById("parentColumn").style.display="none";
			document.getElementById("displayColumn").style.display="block";
			//riju
			//document.getElementById("distributortype").style.display="inline";
			
		
		}
		else{
			if(pageReturn == "true" )  {
			document.getElementById("parentAccount").value="";
			document.getElementById("parentAccountName").value="";
			document.getElementById("parentLoginName").value="";
			
		}
		document.getElementById("parentAccount").disabled=false;
		document.getElementById("parentAccount").readOnly="readonly";
		document.getElementById("searchbutton").disabled=false;
		document.getElementById("divParentNotMand").style.display="block";
		document.getElementById("divParentMand").style.display="none";
		//riju
		//document.getElementById("distributortype").style.display="inline";
		//riju
		}

	
	if(document.getElementById("accountLevel").value == 3 ||document.getElementById("accountLevel").value == 4 ||document.getElementById("accountLevel").value == 5 ){
		document.getElementById("showCircleId").style.display="none";
		document.getElementById("showCircleListId").style.display="inline";
		
			document.getElementById("parentAccountStyle").style.display="block";
			document.getElementById("divSearchButton").style.display="block";
			document.getElementById("parentColumn").style.display="block";	
			document.getElementById("showTSMDepositeRow").style.display="none";
			document.getElementById("depositeTypeTSMLabel").style.display="none";
			document.getElementById("searchbuton").style.display="none";
			document.getElementById("searchSalesTSM").style.display="none";
			document.getElementById("salesTypeTSMLabel").style.display="none";
			//Changes for distributor type ( riju )
			document.getElementById("distributortype").style.display="none";
			//Changes for distributor type ( riju )
		
	}else{
		document.getElementById("showCircleId").style.display="inline";
		document.getElementById("circleId").disabled=false;
		document.getElementById("showCircleListId").style.display="none";
		}
		
		
		document.getElementById("showZoneCity").value="A";
		document.getElementById("showZoneCityId").style.display="inline";
		
				if(pageReturn != "true" )  {
						if(parseInt(document.getElementById("accountLevel").value) == 3 || parseInt(document.getElementById("accountLevel").value) == 4 || parseInt(document.getElementById("accountLevel").value) == 5){
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
								getChangeList("zone");
								document.getElementById("accountZoneId").value = document.getElementById("hiddenZoneId").value;
								getChange("city");
								document.getElementById("accountCityId").value = document.getElementById("hiddenCityId").value;
						}else if(parseInt(document.getElementById("accountLevel").value) == 6){
							getChange("zone");
							document.getElementById("accountZoneId").value = document.getElementById("hiddenZoneId").value;
							getChange("city");
							document.getElementById("accountCityId").value = document.getElementById("hiddenCityId").value;
							var stateId = document.getElementById("hiddenStateId").value;
							var warehouseCode = document.getElementById("hiddenWarehouseId").value;
							getChange("state");
							getChange("warehouse");
							
							document.getElementById("accountStateId").value = stateId;
							document.getElementById("accountWarehouseCode").value =warehouseCode;
							
							var selectedValTransac = document.getElementById("hiddenTranctionIds").value.split(",");
					     	for(var j=0; j < selectedValTransac.length; j++){
								for (var i=1; i < document.forms[0].distTranctionId.length; i++)
					  	 		{
					  	 			if (document.forms[0].distTranctionId[i].value == selectedValTransac[j])
					     	 		{
					     				document.forms[0].distTranctionId.options[i].selected = true;
					     			}
					     		
					     		}
					   		}
					}
				}else if(pageReturn == "true" ) {
				 		if(parseInt(document.getElementById("accountLevel").value) == 2 ||parseInt(document.getElementById("accountLevel").value) == 3 || parseInt(document.getElementById("accountLevel").value) == 4 || parseInt(document.getElementById("accountLevel").value) == 5){
							for (var i=1; i < document.forms[0].circleIdList.length; i++)
					  	 		{
					  	 				document.forms[0].circleIdList.options[i].selected = false;
					     		}
					     	}
				}
				
		if(document.getElementById("accountLevel").value == 6 ){
			document.getElementById("transactionLable").style.display="inline";
			document.getElementById("transactionValue").style.display="inline";
			if(pageReturn == "true" )  {
				document.getElementById("distTranctionId").value="-1";
			}
		}else{
			document.getElementById("transactionLable").style.display="none";
			document.getElementById("transactionValue").style.display="none";
			if(pageReturn == "true" )  {
				document.getElementById("distTranctionId").value="-1";
			}
		}
		if(document.getElementById("accountLevel").value == 6 ){
		
			document.getElementById("showTSMDepositeRow").style.display="inline";
			document.getElementById("depositeTypeTSMLabel").style.display="inline";
			document.getElementById("searchbuton").style.display="inline";
			document.getElementById("searchSalesTSM").style.display="inline";
			document.getElementById("showParentRow").style.display="none";
			document.getElementById("parentAccount").disabled=true;
			document.getElementById("divParentNotMand").style.display="block";
			document.getElementById("salesTypeTSMLabel").style.display="inline";
			//Changes for distributor type ( riju )
			document.getElementById("distributortype").style.display="inline";
			//Changes for distributor type ( riju )
			
		}else{
			document.getElementById("showTSMDepositeRow").style.display="none";
			document.getElementById("depositeTypeTSMLabel").style.display="none";
			document.getElementById("searchbuton").style.display="none";
			document.getElementById("searchSalesTSM").style.display="none";
			document.getElementById("parentAccount").disabled=false;
			document.getElementById("divParentNotMand").style.display="inline";
			document.getElementById("salesTypeTSMLabel").style.display="none";
			document.getElementById("showParentRow").style.display="inline";
			//Changes for distributor type ( riju )
			document.getElementById("distributortype").style.display="none";
			//Changes for distributor type ( riju )
		}
		if(document.getElementById("accountLevel").value == 5 ){
			document.getElementById("transactionTypeLable").style.display="inline";
			document.getElementById("transactionType").style.display="inline";
			if(pageReturn == "true" )  {
				document.getElementById("transactionId").value="-1";
			}
		}else {
			document.getElementById("transactionTypeLable").style.display="none";
			document.getElementById("transactionType").style.display="none";
			
			if(pageReturn == "true" )  {
				document.getElementById("transactionId").value="-1";
			}
			
		}
	}
		
		
		var sn;
		if(document.getElementById("heirarchyId").value=="1")
		{
			document.getElementById("accountLevelStatus").value= document.getElementById("accountLevel").value;
			if(document.getElementById("accountLevel").value != null && document.getElementById("accountLevel").value != "-1"){
				var acclevelId=document.getElementById("accountLevel").value;
	            document.getElementById("accountLevelStatus").value=acclevelId;
	            document.getElementById("accountLevelId").value=document.getElementById("accountLevelStatus").options[document.getElementById("accountLevelStatus").selectedIndex].text;
            }
            if(document.getElementById("accountLevel").value == 0){
            	document.getElementById("accountLevelId").value= "1";
            }
		}  
		// to check the hierarchy id of the selected accountlevel.
		accountLevel();
		
			if(parseInt(document.getElementById("accountLevel").value)==6)
			{	
					
					document.getElementById("showBillableTags").style.display="inline";
			 	   
			 	    
			 	    if(pageReturn == "true" )  {
				 	    document.getElementById("openingBalance").value="0.00";
						document.getElementById("billableCode").value="";
					}
					document.getElementById("isbillable").value="Y";
					document.getElementById("showNumbers").value="Y";
					
					document.getElementById("isbillable").disabled=true;
					
					
					
					document.getElementById("div12").style.display="none";
				 	document.getElementById("showBeat").style.display="none";
				 	document.getElementById("tinNservice").style.display="inline";
				 	document.getElementById("panNcst").style.display="inline";
				 	document.getElementById("cstBox").style.display="inline";
				 	document.getElementById("emailNoctroi").style.display="inline";
				 	document.getElementById("locatorFields").style.display="inline";
	 			 	document.getElementById("uniqueCode").style.display="inline";
				 	document.getElementById("employeeid").style.display="none";
					document.getElementById("hideForFnR").style.display="inline";
					document.getElementById("showLapuNumber").style.display="inline";
					document.getElementById("showFTANumber").style.display="inline";
					document.getElementById("emailMand").style.display="inline";
					document.getElementById("emailMand").style.display="inline";				 				 		 	
			}
			else if(parseInt(document.getElementById("accountLevel").value)<6 )
			{
					document.getElementById("showBillableCode").value="N";
					
					document.getElementById("showBillableTags").style.display="none";
				 	document.getElementById("showBeat").style.display="none";
				 	document.getElementById("tinNservice").style.display="none";
				 	document.getElementById("panNcst").style.display="none";
				 	document.getElementById("cstBox").style.display="none";
				 	document.getElementById("emailNoctroi").style.display="none";
				 	document.getElementById("locatorFields").style.display="none";
	 				document.getElementById("uniqueCode").style.display="none";
				 	document.getElementById("employeeid").style.display="inline";
					document.getElementById("hideForFnR").style.display="inline";
				 	document.getElementById("showLapuNumber").style.display="none";
					document.getElementById("showFTANumber").style.display="none";
					document.getElementById("emailMand").style.display="inline";
			}
			
			if(document.getElementById("showBillableCode").value=="Y")
			{
				  document.getElementById("billableCode").value=="";
			}
			document.getElementById("showParentRow").style.display="inline";
		
		if(parseInt(document.getElementById("accountLevel").value) >= 6 && document.getElementById("accountLevelFlag").value == "1")
		{	
			sn="Y";
			document.getElementById("ShowNumbers").value="Y";
			document.getElementById("channelTypeId").style.display="inline";
		}else{
			sn="N";
			document.getElementById("ShowNumbers").value="N";
			document.getElementById("channelTypeId").style.display="none";			
		}
		//alert("accountLevel == "+document.getElementById("accountLevel").value);
		
			document.getElementById("retailerType").style.display="none";
			document.getElementById("altChannel").style.display = "none";
			document.getElementById("altChannel1").style.display = "none";
			document.getElementById("categoryCode").style.display="none";
			document.getElementById("categoryName").style.display="none";
		
		
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
		var frm = document.forms[DPCreateAccountITHelpFormBean];
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
			
		if(document.getElementById("circleId").value=="" && document.getElementById("circleIdList").value==""){
			alert('<bean:message bundle="error" key="error.account.circleid" />');
			//document.getElementById("circleId").focus();
			return false; 
		}else if(document.getElementById("accountLevel").value=="0"){
			alert('<bean:message bundle="error" key="error.account.accountlevel" />');
			document.getElementById("accountLevel").focus();
			return false; 
		}
	
	var selectedCircleValues ="";
		selectedCircleValues =0;
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
		document.submitForm.action="./initCreateAccountItHelp.do?methodName=openSearchParentAccountPage";
		document.submitForm.circleid.value=document.getElementById("circleId").value;
		document.submitForm.circleIdList.value=selectedCircleValues;
		document.submitForm.parent.value=document.getElementById("parentAccount").value;
		displayWindow = window.open('', "newWin", "width =700,height= 220, left=50, top=100, scrollbars=yes,resizable=yes, status=yes");
       	document.submitForm.submit();
	}
	//this function is added open child window to search and select Deposite type TSM  
	function OpenDepositeTypeTSMWindow()
	{
	//alert("OpenDepositeTypeTSMWindow");
		if(document.getElementById("accountLevel").options[document.getElementById("accountLevel").selectedIndex].text=="Airtel Distributor"){
			document.getElementById("roleName").value="Y";
		}
		else{
			document.getElementById("roleName").value="N";
		}
			
			
		if(document.getElementById("circleId").value=="" && document.getElementById("circleIdList").value==""){
			alert('<bean:message bundle="error" key="error.account.circleid" />');
			//document.getElementById("circleId").focus();
			return false; 
		}else if(document.getElementById("accountLevel").value=="0"){
			alert('<bean:message bundle="error" key="error.account.accountlevel" />');
			document.getElementById("accountLevel").focus();
			return false; 
		}
	
	var selectedCircleValues ="";
		selectedCircleValues =0;
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
		
		document.submitForm.circleid.value=document.getElementById("circleId").value;
		document.submitForm.circleIdList.value=selectedCircleValues;
		/* Changes by Parnika */
		//alert("document.submitForm.distTranctionId.value : " +document.submitForm.distTranctionId.value);	
		//document.submitForm.distTranctionId.value=document.getElementById("distTranctionId").value;
		//document.getElementById("distTypeId").value = "2";
		//document.submitForm.distTypeId.value=document.getElementById("distTypeId").value;
		//alert("document.submitForm.distTypeId.value  : "+document.getElementById("distTypeId").value);
		document.getElementById("distTypeId").value = "2";

		/* End of changes by Parnika */
		document.submitForm.action="./initCreateAccountItHelp.do?methodName=OpenTransactionTypeTSMPage&distTranctionId=2";
		displayWindow = window.open('', "newWin", "width =700,height= 220, left=50, top=100, scrollbars=yes,resizable=yes, status=yes");
       	document.submitForm.submit();
	}
	
	function OpenSalesTypeTSMWindow()
	{
	//alert("OpenSalesTypeTSMWindow()");
		if(document.getElementById("accountLevel").options[document.getElementById("accountLevel").selectedIndex].text=="Airtel Distributor"){
			document.getElementById("roleName").value="Y";
		}
		else
			document.getElementById("roleName").value="N";
			
		if(document.getElementById("circleId").value=="" && document.getElementById("circleIdList").value==""){
			alert('<bean:message bundle="error" key="error.account.circleid" />');
			//document.getElementById("circleId").focus();
			return false; 
		}else if(document.getElementById("accountLevel").value=="0"){
			alert('<bean:message bundle="error" key="error.account.accountlevel" />');
			document.getElementById("accountLevel").focus();
			return false; 
		}
	
	var selectedCircleValues ="";
		selectedCircleValues =0;
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
		
		document.submitForm.circleid.value=document.getElementById("circleId").value;
		document.submitForm.circleIdList.value=selectedCircleValues;
		/* Changes by Parnika */	
		//alert("document.submitForm.distTranctionId.value : " +document.submitForm.distTranctionId.value);
		//alert(document.getElementById("distTranctionId").value + " : " +document.getElementById("distTranctionId").value);
		//document.submitForm.distTranctionId.value=document.getElementById("distTranctionId").value;
		//document.submitForm.distTypeId = "1";
		document.getElementById("distTypeId").value = "1";
		//alert("value::::::"+document.getElementById("distTypeId").value);

		/* End of changes by Parnika */
		document.submitForm.action="./initCreateAccountItHelp.do?methodName=OpenTransactionTypeTSMPage&distTranctionId=1";
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
		document.submitForm.action="./initCreateAccountItHelp.do?methodName=openSearchBillableAccountPage";
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
var url= "initCreateAccountItHelp.do?methodName=getAccountLevel&cond1="+accountLevel;
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
	
	var url="initCreateAccountItHelp.do?methodName=getParentCategory&OBJECT_ID="+circleId+"&condition="+condition;
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


</script>
<script type="text/javascript" language="ecmascript">

	function ShowLoginName(obj)
	{
	
		if(document.getElementById("parentAccount").value !="" && document.getElementById("parentAccount").value != null){
			document.getElementById("parentAccount").value = document.getElementById("parentLoginName").value;
			if(parseInt(document.getElementById("accountLevelId").value) >=8){
				document.getElementById("billableCode").value=document.getElementById("billableLoginName").value;
			}
		}
	}
	
	function ShowAccountName(obj)
	{
		if(document.getElementById("parentAccount").value !="" && document.getElementById("parentAccount").value != null){
			document.getElementById("parentAccount").value = document.getElementById("parentAccountName").value;
			if(parseInt(document.getElementById("accountLevelId").value) >=8){
				document.getElementById("billableCode").value=document.getElementById("billableAccountName").value;
			}
		}
	}
	
	function showDistParentLoginName(transType)
	{
		if(transType == 'CPE')
		{
			if(document.getElementById("depositeTypeTSM").value !="" 
				&& document.getElementById("depositeTypeTSM").value != null)
			{
				document.getElementById("depositeTypeTSM").value 
					= document.getElementById("depositeTypeTSMLoginName").value;
			}
		}
		else
		{
			if(document.getElementById("salesTypeTSM").value !="" 
				&& document.getElementById("salesTypeTSM").value != null)
			{
				document.getElementById("salesTypeTSM").value 
					= document.getElementById("salesTypeTSMLoginName").value;
			}
		}
	}
	
	function showDistParent(transType)
	{
		if(transType == 'CPE')
		{
			if(document.getElementById("depositeTypeTSM").value !="" 
				&& document.getElementById("depositeTypeTSM").value != null)
			{
				document.getElementById("depositeTypeTSM").value 
					= document.getElementById("depositeTypeTSMAccountName").value;
			}
		}
		else
		{
			if(document.getElementById("salesTypeTSM").value !="" 
				&& document.getElementById("salesTypeTSM").value != null)
			{
				document.getElementById("salesTypeTSM").value 
					= document.getElementById("salesTypeTSMAccountName").value;
			}
		}
	}
	
	function move_Area(event)
	{
		event = event || window.event;
		var parent = document.getElementById("parentAccount").value;
		if(document.getElementById("parentAccount").value !="" && document.getElementById("parentAccount").value != null){
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
	marginheight="0" >

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
			action="/initCreateAccountItHelp">
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
							<TD width="176"><FONT color="#003399"> 
							<html:select property="accountLevel" styleId="accountLevel" tabindex="1"
								styleClass="w130" onchange="parentCheck('true')">
								<html:option value="-1">
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
								name="DPCreateAccountITHelpFormBean" onkeypress="alphaNumWithoutSpace()" />
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
								name="DPCreateAccountITHelpFormBean" size="19" maxlength="64" /> </FONT></TD>
							
						</TR>
						<TR>
							<TD id="sim" width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.simNumber" /></FONT> :</STRONG></TD>
							<TD id="sim1" width="175"><FONT color="#003399"> <html:text
								property="simNumber" styleClass="box" styleId="simNumber"
								size="20" maxlength="20" tabindex="6" name="DPCreateAccountITHelpFormBean" />
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
								name="DPCreateAccountITHelpFormBean" maxlength="10" /> </FONT></TD>
						</TR>
						<TR>
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.pin" /></FONT><FONT color="RED">*</FONT> :</STRONG>
							</TD>
							<TD width="176"><FONT color="#3C3C3C"> <html:text
								name="DPCreateAccountITHelpFormBean" property="accountPinNumber"
								tabindex="8" styleClass="box" styleId="accountPinNumber"
								size="19" maxlength="6" onkeypress="isValidNumber()" /> </FONT>
							</TD>
							<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.circle" /></FONT><FONT color="RED">*</FONT> :</STRONG>
							</TD>
							<TD width="175"><FONT color="#003399" >
							<div id="showCircleId" style="display: inline;">
									<html:select property="circleId" tabindex="9" styleClass="w130"
										onchange="getChange('zone');getChange('state');getChange('warehouse');">
										<html:option value="">
											<bean:message key="application.option.select" bundle="view" />
										</html:option>
										<html:options collection="circleList"
											labelProperty="circleName" property="circleId" />
									</html:select>
							</div>
							<div id="showCircleListId" style="display: none;">
								<html:select property="circleIdList" tabindex="10" styleClass="w130" multiple="true" style="width:175; height:80px;"
										onchange="getChangeList('zone');">
										<html:option value="-1">
											<bean:message key="application.option.select" bundle="view" />
										</html:option>
										<html:options collection="circleList"
											labelProperty="circleName" property="circleId" />
									</html:select>
							</div>
							</FONT><FONT color="#ff0000" size="-2"></FONT><a href="#"
								onClick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.circleId" />','Information','width=500,height=100,resizable=yes')">
							<b> ? </b> </a>
							</TD>
							
							
							<!-- Changes for distributor type ( riju )  -->
							 	
							<TD width="126" class="text pLeft15">
							<div id="distributortype" style="display: none;">
							<STRONG><FONT color="#000000"><B>Type</B></FONT><FONT color="RED">*</FONT> :</STRONG>
								<FONT color="#003399"> <html:select
									property="disttype" tabindex="13" styleClass="w130">
									<html:option value="">
										<bean:message key="application.option.select" bundle="view" />
									</html:option>
									<html:option value="SF">SF</html:option>
									<html:option value="SSD">SSD</html:option>
																
									
									
								</html:select> </FONT>
							</div>	
							</TD>
							
							
							
							<!-- Changes for distributor type ( riju )  -->	 
							
						</TR>
						<TR id="showZoneCityId" style="display: inline;">
 							<logic:match name="DPCreateAccountITHelpFormBean" property="showZoneCity"
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
									<logic:notEmpty name="DPCreateAccountITHelpFormBean" property="zoneList">
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
									<logic:notEmpty name="DPCreateAccountITHelpFormBean" property="cityList">
										<html:options collection="cityList"
											labelProperty="accountCityName" property="accountCityId" />
									</logic:notEmpty>
								</html:select> </FONT></TD>
							</logic:match>
						</TR>	





						<TR id="showStateId" style="display: inline;">
								<TD width="126" class="text pLeft15"><STRONG><FONT
									color="#000000"><bean:message bundle="view"
									key="account.state" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
								<TD width="175"><FONT color="#003399"> <html:select
									property="accountStateId" tabindex="13" styleClass="w130">
									<html:option value="">
										<bean:message key="application.option.select" bundle="view" />
									</html:option>
									<logic:notEmpty name="DPCreateAccountITHelpFormBean" property="stateList">
										<html:options collection="stateList"
											labelProperty="accountStateName" property="accountStateId" />
									</logic:notEmpty>
								</html:select> </FONT></TD>
								
								<!--  added by ARTI for warehaouse list box BFR -11 release2 -->
								
								<TD width="121" class="text pLeft15"><STRONG><FONT
									color="#000000"><bean:message bundle="view"
									key="account.warehouse" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
								<TD width="175"><FONT color="#003399"> <html:select
									property="accountWarehouseCode" tabindex="14" styleClass="w130">
									<html:option value="">
										<bean:message key="application.option.select" bundle="view" />
									</html:option>
									<logic:notEmpty name="DPCreateAccountITHelpFormBean" property="wareHouseList">
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
								property="accountAddress" styleId="accountAddress" tabindex="15"
								onfocus="setAddressStatus()" onblur="ResetAddressStatus()" onkeypress="alphaNumWithSpace()" 
								cols="17" rows="3" onkeyup="return maxlength('1');"></html:textarea> </FONT>
							</TD>
							<TD width="126" class="text pLeft15"><STRONG> <FONT
								color="#000000"><bean:message bundle="view"
								key="account.accountAddress2" /></FONT> :</STRONG>
							</TD>
							<TD width="176"><FONT color="#003399"> <html:textarea onkeyup="return maxlength('2');" onkeypress="alphaNumWithSpace()" 
								property="accountAddress2" styleId="accountAddress2"
								tabindex="16" cols="17" rows="3"></html:textarea> </FONT>
							</TD>
						</TR>
						<tr id="flag" style="display: none;">
							<td><html:text property="accountLevelFlag" value="" tabindex="17"></html:text>
							</td>
						</tr>
						
						<TR id="showParentRow" style="display: inline;">
								<TD width="126" class="text pLeft15" id="parentColumn"><STRONG><FONT
									color="#000000">
								<div id="divParentMand" style="display:block;"><bean:message
									bundle="view" key="account.parentAccount" />:</div>
								</FONT>
								<div id="divParentNotMand" style="display:none;"><bean:message
									bundle="view" key="account.parentAccount" /><FONT color="RED">*</FONT>:</div>
								</STRONG></TD>
								
								<TD width="175"><FONT color="#003399"><html:text
									property="parentAccount" styleClass="box" tabindex="18"
									 disabled="true" size="16" maxlength="30" onmouseover="ShowLoginName(this);"
									onmouseout="ShowAccountName(this);" onmousemove="move_Area;" onkeypress="isValidNumAlpha()" styleId="parentAccountStyle"/> </FONT>
	 						<div id="divSearchButton">
	 								<INPUT type="button"
									tabindex="19" disabled="true" name="searchbutton"
									id="searchbutton" class="submit" value='Find'
									onclick="OpenParentWindow()" />  
									<a href="#"
									onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.parentAccount" />','Information','width=500,height=100,resizable=yes')">
								<b> ? </b> </a></TD>
								
								<TD width="126" class="text pLeft15" id="displayColumn" style="display: none;"></TD>
								
							<TD width="126" class="text pLeft15" id="transactionLable" style="display: none;" ><STRONG><FONT
								color="#000000">Distributor Transaction</FONT><FONT color="RED">*</FONT> :</STRONG>
							</TD>
							<TD width="175" id="transactionValue" style="display: none;"><FONT color="#003399" >
							<html:select property="distTranctionId" styleId="distTranctionId" tabindex="20" styleClass="w130" multiple="true" style="width:175; height:50px;">
								<html:option value="-1">
									<bean:message key="application.option.select" bundle="view" />
								</html:option>
								<html:options collection="distTranctionList" labelProperty="strText" property="strValue" />
								</html:select></FONT>
								</TD>
								<TD width="126" class="text pLeft15" id="transactionTypeLable" style="display: none;"><STRONG><FONT
								color="#000000">Transaction Type</FONT><FONT color="RED">*</FONT> :</STRONG>
							</TD>
							<TD width="175" id="transactionType" style="display: none;"><FONT color="#003399" >
							<html:select property="transactionId" tabindex="21" styleClass="w130" style="width:175; height:50px;">
								<html:option value="-1">
									<bean:message key="application.option.select" bundle="view" />
								</html:option>
								<html:options collection="transactionTypeList" labelProperty="strText" property="strValue" />
								</html:select></FONT></TD>
						</TR>
						
						
						<TR  id="showTSMDepositeRow" style="display: none;">
						<TD width="126" class="text pLeft15" id="depositeTypeTSMLabel" nowrap="nowrap"><STRONG> <FONT color="#000000"> 
								CPE TSM </FONT><font color="red">*</FONT>:</STRONG></TD>
								<TD width="175"><FONT color="#003399"><html:text
									property="depositeTypeTSM" name="DPCreateAccountITHelpFormBean" styleId="depositeTypeTSM" styleClass="box" tabindex="22"
									 disabled="true" size="16" maxlength="20" onmouseover="showDistParentLoginName('CPE');"
									onmouseout="showDistParent('CPE');" onmousemove="move_Area;" /> </FONT>
	 								<INPUT type="button"
									tabindex="23" name="depositbutton"
									id="searchbuton" class="submit" value='Find'
									onclick="OpenDepositeTypeTSMWindow()"/>  
									<a href="#"
									onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.depositeTypeTSM" />','Information','width=500,height=100,resizable=yes')">
								<b> ? </b> </a></TD>
						<TD width="126" class="text pLeft15" id="salesTypeTSMLabel" nowrap="nowrap"><STRONG> <FONT color="#000000"> 
								Sales TSM </FONT><font color="red">*</FONT>:</STRONG></TD>
								<TD width="175"><FONT color="#003399"><html:text
									property="salesTypeTSM"  styleId="salesTypeTSM" styleClass="box" tabindex="24"
									 disabled="true" size="16" maxlength="20" name="DPCreateAccountITHelpFormBean" onmouseover="showDistParentLoginName('SALES');"
									onmouseout="showDistParent('SALES');" onmousemove="move_Area;"/> </FONT>
	 								<INPUT type="button"
									tabindex="25" name="salesbutton"
									id="searchSalesTSM" class="submit" value='Find'
									onclick="OpenSalesTypeTSMWindow()" />  
									<a href="#"
									onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.salesTypeTSM" />','Information','width=500,height=100,resizable=yes')">
								<b> ? </b> </a></TD>
						</TR>
						<tr id="showLapuNumber" style="display: none;">
							<TD width="126" class="text pLeft15" nowrap="nowrap"><STRONG>
							<FONT color="#000000"> <bean:message bundle="view"
								key="account.lapu" />  </FONT><FONT color="RED">*</FONT> : </STRONG></TD>
							<TD width="175" nowrap="nowrap"><FONT color="#003399">
							<html:text property="lapuMobileNo" styleClass="box" tabindex="26"
								size="16" maxlength="10" onkeypress="isValidNumber()" /> </FONT><a
								href="#"
								onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="account.lapuMobileNo" />','Information','width=500,height=100,resizable=yes')">
							<b> ? </b> </a></TD>
							<TD width="126" class="text pLeft15" nowrap="nowrap"><STRONG>
							<FONT color="#000000"> <bean:message bundle="view"
								key="account.FTA" /> : </FONT></STRONG></TD>
							<TD width="175" nowrap="nowrap"><FONT color="#003399">
							<html:text property="FTAMobileNo" styleClass="box" tabindex="27"
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
								tabindex="28" size="16" maxlength="10"
								onkeypress="isValidNumber()" /> </FONT><a href="#"
								onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="account.lapuMobileNo1" />','Information','width=500,height=100,resizable=yes')">
							<b> ? </b> </a></TD>
							<TD width="126" class="text pLeft15" nowrap="nowrap"><STRONG>
							<FONT color="#000000"> <bean:message bundle="view"
								key="account.FTA1" /> : </FONT> </STRONG></TD>
							<TD width="175" nowrap="nowrap"><FONT color="#003399">
							<html:text property="FTAMobileNo1" styleClass="box" tabindex="29"
								size="16" maxlength="10" onkeypress="isValidNumber()" /> </FONT><a
								href="#"
								onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="account.FTAMobileNo1" />','Information','width=500,height=100,resizable=yes')">
							<b> ? </b> </a></TD>
						</tr>

						
						<tr id="tradeCategory" style="display: none;">
							<TD id="dist" width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"> <bean:message bundle="view"
								key="account.trade" /></FONT> <FONT color="RED">*</FONT></STRONG> :</TD>
							<TD id="dist1" width="175" nowrap="nowrap"><FONT color="#003399">
							<html:select property="tradeId" tabindex="30" styleClass="w130"
								onchange="getChange('category')">
								<html:option value="0">
									<bean:message key="application.option.select" bundle="view" />
								</html:option>
								<html:options collection="tradeList" labelProperty="tradeName"
									property="tradeId" />
							</html:select> </FONT></TD>
							<TD id="distcat" width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"> <bean:message bundle="view"
								key="account.distcategory" /></FONT> <FONT color="RED">*</FONT></STRONG> : </TD>
							<TD id="distcat1" width="175"><FONT color="#003399"> <html:select
								property="tradeCategoryId" tabindex="31" styleClass="w130">
								<html:option value="0">
									<bean:message key="application.option.select" bundle="view" />
								</html:option>
								<logic:notEmpty name="DPCreateAccountITHelpFormBean"
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
									<html:select property="isbillable" tabindex="32" styleClass="w130"
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
									property="billableCode" styleClass="box" tabindex="33"
									size="16" readonly="true" maxlength="30" onmouseover="ShowLoginName(this);"
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
							size="19" maxlength="10" name="DPCreateAccountITHelpFormBean" tabindex="34"/>
							<script language="JavaScript">
							new tcal ({
							// form name
							'formname': 'DPCreateAccountITHelpFormBean',
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
								property="code" styleClass="box" styleId="code" tabindex="35"
								onkeypress="alphaNumWithoutSpace()" size="19"
								name="DPCreateAccountITHelpFormBean" maxlength="10" /></FONT> <a href="#"
								onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.code" />','Information','width=500,location=no,height=100,resizable=yes')">
							<b> ? </b> </a></div></TD>
						</TR>
	
						<tr id="showBeat" style="display: none;">
						<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"> <bean:message bundle="view"
								key="beat.beatname" /></FONT></STRONG> : 
						</TD>
						<TD width="175"><FONT color="#003399"> <html:select
							property="beatId" tabindex="36" styleClass="w130">
							<html:option value="0">
								<bean:message key="application.option.select" bundle="view" />
							</html:option>
							<logic:notEmpty name="DPCreateAccountITHelpFormBean" property="beatList">
							<bean:define id="beatList" name="DPCreateAccountITHelpFormBean" property="beatList"/>
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
						 <html:select property="altChannelType" tabindex="37" styleClass="w130">
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
							property="retailerType" tabindex="38" styleClass="w130">
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
							property="channelType" tabindex="39" styleClass="w130">
						<html:option value="0">
								<bean:message key="application.option.select" bundle="view" />
							</html:option>	
						<html:options collection="channelTypeList"
								labelProperty="channelName" property="channelType" />
						</html:select></FONT><FONT color="#ff0000" size="-2"></FONT>
						</TD>
						</tr>
						<tr id="channelTypeId" style="display: none">
						<TD id="market" width="121" class="text"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="account.outletType" /></FONT><font color="red">*</font> :</STRONG></TD>
						<TD id="market1" width="176"><FONT color="#003399"> <html:select
							property="outletType" tabindex="40" styleClass="w130">
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
								property="category" tabindex="41" styleClass="w130">
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
								property="tinNo" tabindex="42" styleClass="box"
								styleId="tinNo" size="19" maxlength="25"
								name="DPCreateAccountITHelpFormBean"/>
							</FONT><FONT color="#ff0000" size="-2"></FONT></TD>
							<TD class="text" ><STRONG><FONT	color="#000000">
							<bean:message bundle="view" key="account.tinDate" /></FONT><font color="red">*</FONT> :</STRONG></TD>
						<TD width="155"><FONT color="#003399"> <html:text
						property="tinDt" styleClass="box" styleId="tinDt" readonly="true" 
						size="19" maxlength="10" name="DPCreateAccountITHelpFormBean"  />
						<script language="JavaScript">
new tcal ({
// form name
'formname': 'DPCreateAccountITHelpFormBean',
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
								property="serviceTax" tabindex="43" styleClass="box"
								styleId="serviceTax" size="19" maxlength="25"
								name="DPCreateAccountITHelpFormBean"/>
							</FONT><FONT color="#ff0000" size="-2"></FONT></TD>
							<TD width="121" class="text" nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.PAN" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="175"><FONT color="#003399"><html:text
								property="panNo" tabindex="44" styleClass="box"
								styleId="panNo" size="19" maxlength="10"
								name="DPCreateAccountITHelpFormBean" onkeypress="alphaNumWithoutSpace();"/>
							</FONT></TD>
						</TR>
						
						<TR id="cstBox" style="display: none;">
							<TD class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.CST" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="175"><FONT color="#003399"><html:text
								property="cstNo" tabindex="45" styleClass="box"
								styleId="cstNo" size="19" maxlength="10" name="DPCreateAccountITHelpFormBean" />
							</FONT><FONT color="#ff0000" size="-2"></FONT></TD>
							
							<TD class="text" ><STRONG><FONT	color="#000000">
							<bean:message bundle="view" key="account.CSTDATE" /></FONT> <font color="red">*</FONT>:</STRONG></TD>
							<TD width="155"><FONT color="#003399"> <html:text
							property="cstDt" styleClass="box" styleId="cstDt" readonly="true" 
							size="19" maxlength="10" name="DPCreateAccountITHelpFormBean" />
							<script language="JavaScript">
new tcal ({
// form name
'formname': 'DPCreateAccountITHelpFormBean',
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
								property="reportingEmail" tabindex="46" styleClass="box"
								styleId="reportingEmail" size="19" maxlength="30"
								name="DPCreateAccountITHelpFormBean" onkeypress="alphaNumWithoutSpace()" /> 
							</FONT><FONT color="#ff0000" size="-2"></FONT></TD>--%>
							<TD width="126" class="text">
						<STRONG>
							<FONT color="#000000"> <bean:message bundle="view" key="account.octroiCharge" /></FONT>
							<FONT color="RED">*</FONT>:
						</STRONG>
						</TD>
						<TD width="175"><FONT color="#003399"> 
						<html:select property="octroiCharge" tabindex="47" styleClass="w130">
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
								size="30" maxlength="50" tabindex="48" /> </FONT></TD>
					<!--by saumya for repair facility -->
								<TD width="121"><STRONG> <FONT
								color="#000000"> <bean:message bundle="view"
								key="account.repFacility" /></FONT>:</STRONG></TD>

							    <TD width="176"><FONT color="#003399"> 
						<html:select property="repairFacility" tabindex="49" styleClass="w130" styleId="repairFacility" >
							<html:option value="N">
											<bean:message key="application.option.no" bundle="view" />
							</html:option>
										<html:option value="Y">
											<bean:message key="application.option.yes" bundle="view" />
										</html:option>
										
						  </html:select> </FONT></TD>
						  <!-- ended by saumya  -->	
						</tr>
						
						
						<tr id="SRFireld">
							<TD width="126" class="text pLeft15" nowrap="nowrap"><STRONG> <FONT
								color="#000000"> 
								SR Number</FONT><font color="red">*</FONT>:</STRONG></TD>
							<TD width="175" nowrap="nowrap"><FONT color="#003399"> <html:text
								property="srNumber" styleClass="box" styleId="srNumber"
								size="16" maxlength="50" tabindex="50" /> </FONT></TD>
							</tr>
					  <tr id="approval">
							<TD width="126" class="text pLeft15" nowrap="nowrap"><STRONG> <FONT
								color="#000000"> 
								Approver 1</FONT><font color="red">*</FONT>:</STRONG></TD>
							<TD width="175" nowrap="nowrap"><FONT color="#003399"> <html:text
								property="approval1" styleClass="box" styleId="approval1"
								size="16"  tabindex="51" maxlength="9" /> </FONT></TD>
							<TD width="121" class="text"><STRONG> <FONT	color="#000000"> 
								Approver 2</FONT>:</STRONG></TD>
							<TD width="176"><FONT color="#003399"> <html:text
								property="approval2" styleClass="box" styleId="approval2"
								size="19"  tabindex="52"  maxlength="9"/> </FONT></TD>
								
						</tr>			
									
						
						<TR>
							<TD class="text"><STRONG><FONT color="#000000">&nbsp;</FONT></STRONG></TD>
							<TD colspan="3">
							<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
								<TR align="center">
									<TD width="70"><INPUT class="submit" type="button"
										name="Submit" value="Submit" tabindex="53"
										onclick="submitAccount();">
									</TD>
									<TD width="70"><INPUT class="submit" type="button"
										onclick="resetForm();" name="Submit2" value="Reset"
										tabindex="54"></TD>
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
					name="DPCreateAccountITHelpFormBean" />
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
				<html:hidden property="parentLoginName" name="DPCreateAccountITHelpFormBean"/>
				<html:hidden property="parentAccountName" name="DPCreateAccountITHelpFormBean"/>
				<html:hidden property="billableLoginName" name="DPCreateAccountITHelpFormBean"/>
				<html:hidden property="billableAccountName" name="DPCreateAccountITHelpFormBean"/>
				<html:hidden property="hiddenCircleIdList" styleId="hiddenCircleIdList" name="DPCreateAccountITHelpFormBean" />
				<html:hidden property="hiddenTranctionIds" styleId="hiddenTranctionIds" name="DPCreateAccountITHelpFormBean" />
				<html:hidden styleId="hiddenStateId" property="hiddenStateId" />
				<html:hidden styleId="hiddenWarehouseId" property="hiddenWarehouseId" />
				<html:hidden styleId="hiddenAccountLevel" property="hiddenAccountLevel" />
				
				<!-- Added By Parnika -->
				<html:hidden property="depositeTypeTSMLoginName" name="DPCreateAccountITHelpFormBean"/>
				<html:hidden property="depositeTypeTSMAccountName" name="DPCreateAccountITHelpFormBean"/>
				<html:hidden property="salesTypeTSMAccountName" name="DPCreateAccountITHelpFormBean"/>
				<html:hidden property="salesTypeTSMLoginName" name="DPCreateAccountITHelpFormBean"/>
				<html:hidden property="distTypeId" name="DPCreateAccountITHelpFormBean" styleId="distTypeId" value=""/>

				
				<!-- End of changes By Parnika -->
				
				
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
				<TR id="showPasswordRow" style="display:block;">
							<div id="passwordDiv"  style="display:block;">
							<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.iPassword" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="175"><FONT color="#003399"> <html:password
								property="IPassword" styleClass="box" styleId="IPassword" value="@@@@@@@@"
								size="19" maxlength="20" tabindex="55" readonly="true" /> </FONT><FONT color="#ff0000"
								size="-2"></FONT></TD>
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.checkIPassword" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="176"><FONT color="#3C3C3C"> <html:password
								property="checkIPassword" styleClass="box" value="@@@@@@@@"
								styleId="checkIPassword" size="19" maxlength="20" tabindex="56" readonly="true" />
							</FONT></TD>
						</TR>
			</TABLE>
			</DIV>
			<logic:equal value="true" name="DPCreateAccountITHelpFormBean" property="errorFlag">
			<script>
				parentCheck('false');
				getChange('category');
			</script>
			</logic:equal>
			
		</html:form>
		<div id="submitFormDiv" style="display:none;">
		<form name="submitForm" method="post" target="newWin">
		<input type="hidden" name="circleid">
		<input type="hidden" name="circleIdList">
		<input type="hidden" name="parent">
		<input type="hidden" name="distTranctionId">
		
		
		</form>
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