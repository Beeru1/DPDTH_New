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


	function OpenHelpWindow(url)
	{
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
	var isSales=false;
	// function that checks that is any field value is blank or not
	function validate(){
		
		var	accountId = document.getElementById("accountId").value;
		var levelID = document.getElementById("accountLevelId").value;
		
		if(document.getElementById("terminationId")!=null)
		var terminationID= document.getElementById("terminationId").value;
		var distTrancID= document.getElementById("distTranctionId").value;
		
		//alert("accountId  ::  "+accountId+"  terminationID  ::  "+terminationID+"  distTrancID  ::  "+distTrancID+" levelID  ::  "+levelID);
		//if(document.getElementById("terminationId")!=null){
		if(document.getElementById("terminationId")!=null && terminationID!=-1 && levelID==7) // Termination of Distributor
		{
			
			var r=confirm("Do you want to Terminate this Distributor ? ");
			if (r==true)
  			{
  			  document.getElementById("methodName").value="terminateDist";
  			  return true;
  			}
	      
		}
		
		else{
	
		//check if page is not submitted by state field to get city 	
	    if(document.getElementById("flagForCityFocus").value=="true"){	
	        document.getElementById("methodName").value="getCityForEdit";
		  	return true;
		} 
		
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
		var mobileno = document.getElementById("mobileNumber").value; 
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
			if(isSales == false){
				alert('<bean:message bundle="error" key="error.account.warehouse" />');
				document.getElementById("accountWareHouseCode").focus();
				return false; 
			}
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
		
/*		if(document.getElementById("accountAddress2").value==null || document.getElementById("accountAddress2").value==""){
			alert('<bean:message bundle="error" key="error.account.accountaddressAlternate" />');
			document.getElementById("accountAddress2").focus();
			return false; 
		} 	*/
	//alert(document.getElementById("accountLevelId").value);
	//alert(document.getElementById("heirarchyId").value);
	
     if(parseInt(document.getElementById("accountLevelId").value) > 6 && document.getElementById("heirarchyId").value=="1")
     {
    // alert("if");
 		//alert('Level ID : ' +  parseInt(document.getElementById("accountLevelId").value));
        if(parseInt(document.getElementById("accountLevelId").value) != 9)
        {


		
		if(document.forms[0].distTranctionId.length >1){
			for (var i=1; i < document.forms[0].distTranctionId.length; i++)
	  		 {
		   		 if (document.forms[0].distTranctionId[i].selected)
		     	 {
		     		var selectedTran = document.forms[0].distTranctionId[i].value.split(",");
		     		if(selectedTran[0] == "1") {
		     			/* Added By Parnika */
		     			if(document.getElementById("salesTypeTSM").value == null || document.getElementById("salesTypeTSM").value == ""){
		     				alert("Please select Sales Type TSM, as distributor has Sales transaction type.");
		     				return false;
		     			}
		     			/* End of changes By Parnika */
		     			if(document.getElementById("lapuMobileNo1").value == "" || document.getElementById("lapuMobileNo1").value == null){
		     				alert("Please enter Lapu Mobile No. 2, as distributor has Sales transaction type.");
		     				document.getElementById("lapuMobileNo1").focus();
		     				return false;
		     			}
		     		}
		   			/* Added By Parnika */
		     		if(selectedTran[0] == "2") {
		     			if(document.getElementById("lapuMobileNo").value==null || document.getElementById("lapuMobileNo").value=="")
	 					{
						alert('<bean:message bundle="error" key="error.account.lapuMobileNo" />');
						document.getElementById("lapuMobileNo").focus();
						return false; 
						}
		     			if(document.getElementById("depositeTypeTSM").value == null || document.getElementById("depositeTypeTSM").value == ""){
		     				alert("Please select Deposit Type TSM, as distributor has Deposit transaction type.");
		     				return false;
		     			}
		     			
		     		}
		     		/* End of changes By Parnika */
		     	 }
	   		}
	 	 }
	 	 if(document.getElementById("lapuMobileNo").value != "" && document.getElementById("lapuMobileNo").value != null)
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
		}// end account check
		
		if(parseInt(document.getElementById("accountLevelId").value) == 9 &&
			(document.getElementById("altChannelType").value==null 
			|| document.getElementById("altChannelType").value=="0"))
		{
			alert('Please select alternate channel.');
		    document.getElementById("altChannelType").focus();
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
//	alert("after if");
		if(document.getElementById("status").value==null || document.getElementById("status").value=="0"){
			alert('<bean:message bundle="error" key="errors.user.status_invalid" />');
		    document.getElementById("status").focus();
		    return false; 
		} if(document.getElementById("groupId").value==null || document.getElementById("groupId").value=="0"){
			alert('<bean:message bundle="error" key="errors.user.groupid_required" />');
		    document.getElementById("groupId").focus();
		    return false; 
		} 
		//Added for Transaction
		if(document.getElementById("distTranctionId").value == "" || document.getElementById("distTranctionId").value == "-1"){
				alert("Please select Transaction type for Distributor. ");
				document.getElementById("distTranctionId").focus();
				return false; 
		}
		
		
		
		if(document.getElementById("parentAccountId").value!="0"){
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
	
			if(document.getElementById("tinNo").value == null || document.getElementById("tinNo").value == ""){
				if(isSales == false){
					alert("Enter Tin No.");
					document.getElementById("tinNo").focus();
					return false;
				} 
			}
			if(document.getElementById("tinDt").value == null || document.getElementById("tinDt").value == ""){
				if(isSales == false){
						alert("Please enter TIN Date");
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
			if(document.getElementById("cstDt").value == null || document.getElementById("cstDt").value == ""){
				alert("Please enter CST Date");
				document.getElementById("cstDt").focus();
				return false; 
			}
			
		if(document.getElementById("accountLevelId").value=='7'){
		if(isSales == false){
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
		}	
		//added by sugandha for User termination BFR-10 DP-Phase-3
	if(document.getElementById("accountLevel").value == '6' )
	{
			//alert("accountLevel!!@###")
			if(document.getElementById("terminationId")!=null){
			document.getElementById("terminationType").style.display="inline";
			document.getElementById("terminationLable").style.display="inline";
			document.getElementById("terminationValue").style.display="inline";
			}
			
		}
		else
		{
			if(document.getElementById("terminationId")!=null){
			document.getElementById("terminationType").style.display="none";
			document.getElementById("terminationLable").style.display="none";
			document.getElementById("terminationValue").style.display="none";
			}
		}
		//added by sugandha ends here for User termination BFR-10 DP-Phase-3	
      	}    
      	    
		if(document.getElementById("octroiCharge").disable==false){
			if(document.getElementById("octroiCharge").value == null || document.getElementById("octroiCharge").value == ""){
				alert("Select Octroi Applied On");
				document.getElementById("octroiCharge").focus();
			}
		}	
		

		if(document.getElementById("srNumber").value == null || document.getElementById("srNumber").value == ""){
				alert("Please enter SR Number");
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
		if(document.getElementById("approval1").value == null || document.getElementById("approval1").value == ""){
				alert("Please enter Approver 1");
				document.getElementById("Approval1").focus();
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
		
		
			var seltdTransactValues ="";
		var	Id = document.forms[0].distTranctionId[0].value;
		if(Id != "" &&  document.forms[0].distTranctionId.length >1){
			for (var i=0; i < document.forms[0].distTranctionId.length; i++)
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
		
	   	document.forms[0].hiddenTranctionIds.value  = seltdTransactValues;
	    document.forms[0].accountAddress.value=trimAll(document.forms[0].accountAddress.value);
		document.getElementById("flagForCityFocus").value=false;
		document.getElementById("methodName").value="updateAccount";
        return true;
        }
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
	
	function transactionChange(){
		var selectedValTrans = document.getElementById("hiddenTranctionIds").value.split(",");
	     	var isRemoved =0;
			for(var j=0; j < selectedValTrans.length; j++){
				for (var i=1; i < document.forms[0].distTranctionId.length; i++)
	  	 		{
	  	 			if (document.forms[0].distTranctionId[i].value == selectedValTrans[j])
	     	 		{
	     				if(!document.forms[0].distTranctionId.options[i].selected){
	     					isRemoved =1;
	     					}
	     			}
	     		}
	   		}
	   		if(isRemoved == 1){
	   			alert("Transaction type can not be de-selected, once created!");
	   			for(var j=0; j < selectedValTrans.length; j++){
					for (var i=1; i < document.forms[0].distTranctionId.length; i++)
		  	 		{
		  	 			if (document.forms[0].distTranctionId[i].value == selectedValTrans[j])
		     	 		{
		     				document.forms[0].distTranctionId.options[i].selected =true;
		     			}
		     		}
	   			}
	     		return false;
	     	}
	}
	//Added by sugandha For BFR-10 (User termination)DP-Phase3
		/*function terminateUser()
	{
		var	accountId = document.getElementById("accountId").value;
		var levelID = document.getElementById("accountLevelId").value;
		var terminationID= document.getElementById("terminationId").value;
		var distTrancID= document.getElementById("distTranctionId").value;
		
		alert("accountId  ::  "+accountId+"  terminationID  ::  "+terminationId+"  distTrancID  ::  "+distTrancID+" levelID  ::  "+levelID);
			var url = "initCreateAccount.do?methodName=terminateDist&accountId="+accountId+"&levelID="+levelID+"&distTrancID="+distTrancID+"&terminationID="+terminationID;
			alert("passed1111111111111");
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
			req.onreadystatechange = function(){terminateUserStatus();};
			req.open("POST",url,false);
			req.send();
		
		
	}*/
	
	function validateUserStatusDB()
	{
		if (req.readyState==4 || req.readyState=="complete") 
  		{
  			var xmldoc = req.responseXML.documentElement;
			if(xmldoc == null)
			{		
				return;
			}
			
			optionValues = xmldoc.getElementsByTagName("option");
			var msg = optionValues[0].getAttribute("message");
			alert(msg);
			
			if(msg!="OK")
			{
				if(document.getElementById("accountLevelId").value=="7")
					alert("Distributor cannot be terminated as they have child attached to them");
				else
					alert("Distributor cannot be terminated as this distributor is having stock with him.");
				return false;
			}
  		}
  		
  	}
	//Added By Sugandha for BFR-10 Dp-Phase3 ends here
	
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
	
	//this function is added open child window to search and select Deposite type TSM  
			/* Changes by Parnika */
	function OpenDepositeTypeTSMWindow()
	{

		if(parseInt(document.getElementById("accountLevelId").value) == 7){
			document.getElementById("roleName").value="Y";
		}
		else{
			document.getElementById("roleName").value="N";
		}
			
			
		if(document.getElementById("circleId").value==""){
			alert('<bean:message bundle="error" key="error.account.circleid" />');
			//document.getElementById("circleId").focus();
			return false; 
		}else if(document.getElementById("accountLevel").value=="0"){
			alert('<bean:message bundle="error" key="error.account.accountlevel" />');
			document.getElementById("accountLevel").focus();
			return false; 
		}

		document.submitForm.circleId.value=document.getElementById("circleId").value;
		//document.submitForm.circleIdList.value=selectedCircleValues;
		document.getElementById("distTypeId").value = "2";		
		document.submitForm.action="./initCreateAccountItHelp.do?methodName=OpenTransactionTypeTSMPage&distTranctionId=2";
		displayWindow = window.open('', "newWin", "width =700,height= 220, left=50, top=100, scrollbars=yes,resizable=yes, status=yes");
       	document.submitForm.submit();
	}
	
	function OpenSalesTypeTSMWindow()
	{
		if(parseInt(document.getElementById("accountLevelId").value) == 7){
			document.getElementById("roleName").value="Y";
		}
		else{
			document.getElementById("roleName").value="N";
		}
		
		if(document.getElementById("circleId").value==""){
			alert('<bean:message bundle="error" key="error.account.circleid" />');
			//document.getElementById("circleId").focus();
			return false; 
		}else if(document.getElementById("accountLevel").value=="0"){
			alert('<bean:message bundle="error" key="error.account.accountlevel" />');
			document.getElementById("accountLevel").focus();
			return false; 
		}

		document.submitForm.circleId.value=document.getElementById("circleId").value;
		//document.submitForm.circleIdList.value=selectedCircleValues;
		document.getElementById("distTypeId").value = "1";		
		document.submitForm.action="./initCreateAccountItHelp.do?methodName=OpenTransactionTypeTSMPage&distTranctionId=1";
		displayWindow = window.open('', "newWin", "width =700,height= 220, left=50, top=100, scrollbars=yes,resizable=yes, status=yes");
       	document.submitForm.submit();
	}
	/* End of changes by Parnika */
	
	function checkBillable()
	{
	
/*	if(parseInt(document.getElementById("accountLevelId").value)==2){
		document.getElementById("searchbutton").disabled=true;
	}
		else{
		document.getElementById("searchbutton").disabled=false;
		}	*/
	    if(document.getElementById("showBillableCode").value=="Y" ){
	    	document.getElementById("isBillable").disabled=false;
	    	document.getElementById("tradeId").disabled=false;
	    	document.getElementById("tradeCategoryId").disabled=false;
		} else{
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
			document.getElementById("transactionLable").style.display="inline";
			document.getElementById("transactionValue").style.display="inline";	
			document.getElementById("showTSMDepositeRow").style.display="inline";
			document.getElementById("parentAccountTd1").style.display="none";
			document.getElementById("parentAccountTd2").style.display="none";
		//	document.getElementById("terminationLable").style.display="inline";
			//document.getElementById("terminationValue").style.display="inline";	
			//document.getElementById("terminationId").style.display="inline";	
	   		
		}
		else
		{
		 	document.getElementById("distLocatorBox").style.display="none";
		 	document.getElementById("transactionLable").style.display="none";
			document.getElementById("transactionValue").style.display="none";
			document.getElementById("showTSMDepositeRow").style.display="none";	
			document.getElementById("parentAccountTd1").style.display="inline";
			document.getElementById("parentAccountTd2").style.display="inline";
	//document.getElementById("terminationLable").style.display="none";
//document.getElementById("terminationValue").style.display="none";
//document.getElementById("terminationId").style.display="none";
		}
		
		if(document.getElementById("accountLevelId").value != 7)
		{
			document.getElementById("tinNo").readOnly="readonly";
			document.getElementById("serviceTax").readOnly="readonly";
			document.getElementById("panNo").readOnly="readonly";
			document.getElementById("cstNo").readOnly="readonly";
//			document.getElementById("reportingEmail").readOnly="readonly";
			document.getElementById("octroiCharge").disabled=true;
		}else{
			document.getElementById("tinNo").readOnly=false;
			document.getElementById("serviceTax").readOnly=false;
			document.getElementById("panNo").readOnly=false;
			document.getElementById("cstNo").readOnly=false;
//			document.getElementById("reportingEmail").readOnly=false;
			document.getElementById("octroiCharge").disabled=false;
		}
		if(document.getElementById("accountLevelId").value != 9){
			//document.getElementById("altChannelType").disabled=true;
			//document.getElementById("retailerType").disabled=true;
			//document.getElementById("channelType").disabled=true;
			//document.getElementById("category").disabled=true;
			document.getElementById("searchbutton").disabled=false;
		}else{
//			document.getElementById("altChannelType").disabled=false;
//			document.getElementById("retailerType").disabled=false;
//			document.getElementById("channelType").disabled=false;
	//		document.getElementById("category").disabled=false;
			document.getElementById("searchbutton").disabled=false;
		}
		
		
		// Add by harbans
		if(parseInt(document.getElementById("accountLevelId").value) == 2)
		{
		  document.getElementById("showCSTDate").style.display="none";
		  document.getElementById("showServiceTax").style.display="none";
		  document.getElementById("showTinDate").style.display="none";
		  document.getElementById("showChannelType").style.display="none";
		  document.getElementById("showRetailerType").style.display="none";
		  document.getElementById("showBeat").style.display="none";
		  document.getElementById("showCategory").style.display="none";
		          //document.getElementById("showStatusParent").style.display="none";
		  document.getElementById("showTradeCategory").style.display="none";
		  document.getElementById("showFTANumber").style.display="none";
		  document.getElementById("showLapuNumber").style.display="none";
          document.getElementById("showZoneCityId").style.display="none";
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
	 var url="initCreateAccountItHelp.do?methodName=getParentCategory&OBJECT_ID="+Id+"&condition="+condition;
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
	    alert ("Account Address  Can Contain 100 Characters Only.");
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
		var selectedValTrans = document.getElementById("hiddenTranctionIds").value.split(",");
	     	for(var j=0; j < selectedValTrans.length; j++){
				for (var i=1; i < document.forms[0].distTranctionId.length; i++)
	  	 		{
	  	 			if (document.forms[0].distTranctionId[i].value == selectedValTrans[j])
	     	 		{
	     				document.forms[0].distTranctionId.options[i].selected = true;
	     			}
	     		}
	   		}
	}
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
		document.submitForm.action="./initCreateAccountItHelp.do?methodName=openSearchParentAccountPage";
		document.submitForm.circleId.value=document.getElementById("circleId").value;
		document.submitForm.parent.value=document.getElementById("parentAccount").value;
		//displayWindow = window.open('', "newWin", "width =700,height= 220, left=50, top=100, scrollbars=yes,resizable=yes, status=yes");
		window.open('', "newWin", "width=900,height=450,scrollbars=yes,toolbar=no");
       	document.submitForm.submit();
	}   
</SCRIPT>

</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onkeypress="return checkKeyPressed();" onload="validateState(); checkBillable();" >

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
			action="/initCreateAccountItHelp" focus="accountCode">
							
				<!-- Added By Parnika -->
				<html:hidden property="depositeTypeTSMLoginName" name="DPCreateAccountITHelpFormBean"/>
				<html:hidden property="depositeTypeTSMAccountName" name="DPCreateAccountITHelpFormBean"/>
				<html:hidden property="salesTypeTSMAccountName" name="DPCreateAccountITHelpFormBean"/>
				<html:hidden property="salesTypeTSMLoginName" name="DPCreateAccountITHelpFormBean"/>
				<html:hidden property="distTypeId" name="DPCreateAccountITHelpFormBean" styleId="distTypeId" value=""/>

				
				<!-- End of changes By Parnika -->
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
							<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.simNumber" /></FONT> :</STRONG></TD>
							<TD width="155"><FONT color="#003399"> <html:text
								property="simNumber" styleClass="box" styleId="simNumber"
								size="20" maxlength="20" tabindex="5"
								name="DPCreateAccountITHelpFormBean" /> </FONT><FONT
								color="#ff0000" size="-2"></FONT></TD>
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
									<html:select property="accountZoneId" tabindex="8" 
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
									<html:select property="accountCityId" tabindex="9" 
										styleClass="w130" >
										<html:option value="0">
											<bean:message key="application.option.select" bundle="view" />
										</html:option>
										<html:options collection="cityList"
											labelProperty="accountCityName" property="accountCityId" />
									</html:select></FONT>
							</TD>	
							<!-- Changes for distributor type ( riju ) -->
							
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><B>Type</B></FONT> :</STRONG></TD>
							<TD id="city1" width="155"><FONT color="#003399">
									<html:select property="disttype" tabindex="9" 
										styleClass="w130">
										<html:option value="">
											<bean:message key="application.option.select" bundle="view" />
										</html:option>
										<html:option value="SF">SF</html:option>
									<html:option value="SSD">SSD</html:option>
									</html:select></FONT>
							</TD>	
							<!-- Changes for distributor type ( riju ) -->
							
							
								
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
									<logic:notEmpty name="DPCreateAccountITHelpFormBean" property="stateList">
										<html:options collection="stateList"
											labelProperty="accountStateName" property="accountStateId" />
									</logic:notEmpty>
								</html:select> </FONT></TD>
								<TD width="121" class="text pLeft15"><STRONG><FONT
									color="#000000"><bean:message bundle="view"
									key="account.warehouse" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
								<TD width="175"><FONT color="#003399"> <html:select
									property="accountWarehouseCode" tabindex="10" styleClass="w130">
									<html:option value="">
										<bean:message key="application.option.select" bundle="view" />
									</html:option>
									<logic:notEmpty name="DPCreateAccountITHelpFormBean" property="wareHouseList">
										<html:options collection="wareHouseList"
											labelProperty="accountWarehouseName" property="accountWarehouseCode" />
									</logic:notEmpty>
								</html:select> </FONT></TD>
								<TD width="176"></TD>
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
						<tr id="showLapuNumber" style="display: inline;">
							<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.lapu" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="155"><FONT color="#3C3C3C">
							<html:text property="lapuMobileNo" name="DPCreateAccountITHelpFormBean" maxlength="10" tabindex="12"/>
							</FONT></TD>
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.lapu1" /></FONT> :</STRONG></TD>
							<TD width="171"><FONT color="#3C3C3C">
							<html:text property="lapuMobileNo1" name="DPCreateAccountITHelpFormBean" maxlength="10" tabindex="13"/></FONT></TD>
						</tr>
						<tr id="showFTANumber" style="display: inline;">
							<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.FTA" /></FONT> :</STRONG></TD>
							<TD width="155"><FONT color="#3C3C3C">
							<html:text property="FTAMobileNo" name="DPCreateAccountITHelpFormBean" maxlength="10" tabindex="14"/>
							</FONT></TD>
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.FTA1" /></FONT> :</STRONG></TD>
							<TD width="171"><FONT color="#3C3C3C">
							<html:text property="FTAMobileNo1" name="DPCreateAccountITHelpFormBean" maxlength="10" tabindex="15"/>
							 </FONT></TD>
						</tr>

						<tr id="showTradeCategory" style="display: inline;">
							<TD width="126" class="text pLeft15"><STRONG><FONT color="#000000">
								<bean:message
									bundle="view" key="account.trade" /><FONT color="RED">*</FONT> :</TD>
							<TD width="175" nowrap="nowrap"><FONT color="#003399"> <html:select
								property="tradeId" tabindex="18" styleClass="w130"  disabled="true"
								onchange="getChange('category');">
								<html:option value="0">
									<bean:message key="application.option.select" bundle="view" />
								</html:option>
								<logic:notEmpty  name="DPCreateAccountITHelpFormBean" property="tradeList">
								<bean:define id="tradeList" name="DPCreateAccountITHelpFormBean" property="tradeList"/>
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
								property="tradeCategoryId" tabindex="19" styleClass="w130">
								<html:option value="0">
									<bean:message key="application.option.select" bundle="view" />
								</html:option>
								<logic:notEmpty  name="DPCreateAccountITHelpFormBean" property="tradeCategoryList">
								<bean:define id="tradeCategoryList" name="DPCreateAccountITHelpFormBean" property="tradeCategoryList"/>
								<html:options collection="tradeCategoryList"
									labelProperty="tradeCategoryName" property="tradeCategoryId" /></logic:notEmpty>
							</html:select> </FONT></TD>
						</tr>
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
							<TD width="126" class="text pLeft15" nowrap id="parentAccountTd1"> <STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.parentAccount" /></FONT><FONT color="RED">*</FONT>:</STRONG></TD>
							<TD id="parentAccountTd2"><FONT color="#003399"> 
							<html:text 	property="parentAccount" styleClass="box" size="19" maxlength="20"/> </FONT>
								<INPUT type="button" tabindex="18" name="searchbutton" 	id="searchbutton" class="submit" value='Find'
									onclick="OpenParentWindow()" />
							</TD>
							
							<TD width="126" class="text pLeft15" id="transactionLable"><STRONG><FONT
								color="#000000">Distributor Transaction</FONT><FONT color="RED">*</FONT> :</STRONG>
							</TD>
							<TD width="175" id="transactionValue"><FONT color="#003399" >
							<html:select property="distTranctionId" onchange="transactionChange();" tabindex="10" styleClass="w130" multiple="true" style="width:175; height:80px;">
								<html:option value="-1">
									<bean:message key="application.option.select" bundle="view" />
								</html:option>
								<html:options collection="distTranctionList" labelProperty="strText" property="strValue" />
								</html:select></FONT></TD>
						</TR>
						
					<TR  id="showTSMDepositeRow" style="display: none;">
						<TD width="126" class="text pLeft15" id="depositeTypeTSMLabel" nowrap="nowrap"><STRONG> <FONT color="#000000"> 
								CPE TSM </FONT><font color="red">*</FONT>:</STRONG></TD>
								<TD width="175"><FONT color="#003399"><html:text
									property="depositeTypeTSM" styleId="depositeTypeTSM" styleClass="box" tabindex="15"
									 disabled="true" size="16" maxlength="20" onmouseover="ShowLoginName(this);"
									onmouseout="ShowAccountName(this);" onmousemove="move_Area;" onkeypress="isValidNumAlpha()" /> </FONT>
	 								<INPUT type="button"
									tabindex="18" name="depositbutton"
									id="searchbuton" class="submit" value='Find'
									onclick="OpenDepositeTypeTSMWindow()"/>  
									<a href="#"
									onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.depositeTypeTSM" />','Information','width=500,height=100,resizable=yes')">
								<b> ? </b> </a></TD>
						<TD width="126" class="text pLeft15" id="salesTypeTSMLabel" nowrap="nowrap"><STRONG> <FONT color="#000000"> 
								Sales TSM </FONT><font color="red">*</FONT>:</STRONG></TD>
								<TD width="175"><FONT color="#003399"><html:text
									property="salesTypeTSM"  styleId="salesTypeTSM" styleClass="box" tabindex="15"
									 disabled="true" size="16" maxlength="20"/> </FONT>
	 								<INPUT type="button"
									tabindex="29" name="salesbutton"
									id="searchSalesTSM" class="submit" value='Find'
									onclick="OpenSalesTypeTSMWindow()" />  
									<a href="#"
									onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.salesTypeTSM" />','Information','width=500,height=100,resizable=yes')">
								<b> ? </b> </a></TD>
						</TR>
					<!-- Added by sugandha for BFR-10 DP Phase 3 -->
						
						<logic:notEmpty property="terminationList" name="DPCreateAccountITHelpFormBean">
							
							<TR>
								<TD width="126" class="text pLeft15" id="terminationLable" >						
								 <STRONG><FONT color="#000000">Termination Type</FONT> :</STRONG>
								</TD>
								<TD width="175" id="terminationValue" ><FONT color="#003399" >
								<html:select property="terminationId" name="DPCreateAccountITHelpFormBean" tabindex="20" styleClass="w130" >
									<html:option value="-1">
										<bean:message key="application.option.select" bundle="view" />
									</html:option>
									<html:options collection="terminationList" labelProperty="terminationType" property="terminationId"/>
								</html:select>
								</FONT>
						 	</TD>								
						</TR>
						</logic:notEmpty>
						<!-- Added by sugandha for BFR-10 DP Phase 3 ends here -->
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
						<TD width="121" class="text"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="account.outletType" /></FONT><font color="red">*</font> :</STRONG></TD>
						<TD width="176"><FONT color="#003399"> <html:select
							property="outletType" tabindex="18" styleClass="w130">
							<html:option value="0">
								<bean:message key="application.option.select" bundle="view" />
							</html:option>	
							<logic:notEmpty name="DPCreateAccountITHelpFormBean" property="outletList">
							<bean:define id="outletList" name="DPCreateAccountITHelpFormBean" property="outletList"/>
							<html:options collection="outletList"
								labelProperty="outletName" property="outletType" />
							</logic:notEmpty>	
						</html:select></FONT><FONT color="#ff0000" size="-2"></FONT><a href="#"
							onClick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
							bundle="view" key="detail.category" />','Information','width=500,height=100,resizable=yes')">
						<b> ? </b> </a></TD>
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
								property="code" styleClass="box" styleId="code" tabindex="22"
								onkeypress="isValidNumber()" size="19"
								name="DPCreateAccountITHelpFormBean" maxlength="10" readonly="true"/> </FONT> <a href="#"
								onClick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.code" />','Information','width=500,location=no,height=100,resizable=yes')">
							<b> ? </b> </a></TD>
						</TR>
				
			
				
						
<tr id="showTinDate" style="display: inline;">
	<TD class="text pLeft15"><STRONG><FONT
		color="#000000"><bean:message bundle="view"
		key="account.tinNo" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
	<TD width="175"><FONT color="#003399"><html:text
		property="tinNo" tabindex="19" styleClass="box"
		styleId="tinNo" size="19" maxlength="30"
		name="DPCreateAccountITHelpFormBean" readonly="true"/>
	</FONT><FONT color="#ff0000" size="-2"></FONT></TD>
	<TD class="text" ><STRONG><FONT	color="#000000">
	<bean:message bundle="view" key="account.tinDate" /></FONT> :</STRONG></TD>
	<TD width="155"><FONT color="#003399"> <html:text
	property="tinDt" styleClass="box" styleId="tinDt" 
	size="19" maxlength="10" name="DPCreateAccountITHelpFormBean" readonly="true" />
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

<tr id="showServiceTax" style="display: inline;">
	<TD width="121" class="text" nowrap><STRONG><FONT
		color="#000000"><bean:message bundle="view"
		key="account.serviceTax" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
	<TD width="176"><FONT color="#3C3C3C"> <html:text
		property="serviceTax" styleClass="box" styleId="serviceTax"
		size="19" maxlength="30" tabindex="20"  readonly="true"/> </FONT>
	</TD>
	<TD class="text pLeft15"><STRONG><FONT
		color="#000000"><bean:message bundle="view"
		key="account.PAN" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
	<TD width="175"><FONT color="#003399"><html:text
		property="panNo" tabindex="21" styleClass="box"
		styleId="panNo" size="19" maxlength="10"
		name="DPCreateAccountITHelpFormBean" readonly="true" onkeypress="alphaNumWithoutSpace();"/>
	</FONT><FONT color="#ff0000" size="-2"></FONT></TD>
</TR>

<tr id="showCSTDate" style="display: inline;">
<TD width="121" class="text" nowrap><STRONG>
	<FONT color="#000000"><bean:message bundle="view"	key="account.CST" /></FONT>
	<FONT color="RED">*</FONT> :</STRONG></TD>
	<TD width="176"><FONT color="#3C3C3C"> <html:text	property="cstNo" styleClass="box" styleId="cstNo"	
	size="19" maxlength="30" tabindex="22"  readonly="true"/> </FONT>
	</TD>

	<TD class="text" ><STRONG><FONT	color="#000000">
	<bean:message bundle="view" key="account.CSTDATE" /></FONT> :</STRONG></TD>
	<TD width="155"><FONT color="#003399"> <html:text
	property="cstDt" styleClass="box" styleId="cstDt" 
	size="19" maxlength="10" name="DPCreateAccountITHelpFormBean" readonly="true" />
	<script language="JavaScript">
new tcal ({
// form name
'formname': 'DPCreateAccountITHelpFormBean',
// input name
'controlname': 'cstDt'
});

</script>
	</FONT>
</TD>	
<TR>
						
						
						
						<tr>

							<TD width="126" class="text">
						<STRONG>
							<FONT color="#000000"> <bean:message bundle="view" key="account.octroiCharge" /></FONT>
							<FONT color="RED">*</FONT>:
						</STRONG>
						</TD>	
						<TD width="175"><FONT color="#003399"> 
						<html:select property="octroiCharge" tabindex="24" styleClass="w130" disabled="true">
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
						
						
							<tr id="distLocatorBox" style="display: none;">
							<TD width="121" class="text"><STRONG> <FONT
								color="#000000"> <bean:message bundle="view"
								key="account.distLocator" /></FONT>:</STRONG></TD>
							<TD width="176"><FONT color="#003399"> </FONT><html:text
								property="distLocator" styleClass="box" styleId="distLocator"
								size="30" maxlength="50" /></TD>
					<!--by saumya for repair facility -->
								<TD width="121"  class="text"><STRONG> <FONT
								color="#000000"> <bean:message bundle="view"
								key="account.repFacility" /></FONT>:</STRONG></TD>

							    <TD width="176"><FONT color="#003399"> 
						<html:select property="repairFacility" tabindex="40" styleClass="w130" styleId="repairFacility" >
							<html:option value="N">
											<bean:message key="application.option.no" bundle="view" />
							</html:option>
										<html:option value="Y">
											<bean:message key="application.option.yes" bundle="view" />
										</html:option>
										
						  </html:select> </FONT></TD>
						  
					<!-- ended by saumya  -->	
												</tr>
					<tr style="display: inline;">
							<TD width="121" class="text"><STRONG> <FONT
								color="#000000"> <bean:message bundle="view"
								key="account.srnumber" /></FONT><FONT color="RED">*</FONT>:</STRONG></TD>
							<TD width="176"><FONT color="#003399"> </FONT><html:text
								property="srNumber" styleClass="box" styleId="srNumber"
								size="30" maxlength="20" /></TD>
								
					<TD width="121" class="text"><STRONG> <FONT
								color="#000000"> Remarks</FONT><FONT color="RED">*</FONT>:</STRONG></TD>

					<TD width="176"><FONT color="#003399"> </FONT><html:textarea
								property="remarks" styleClass="box" styleId="remarks"/></TD>
					</tr>
					
					<tr style="display: inline;">
							<TD width="121" class="text"><STRONG> <FONT
								color="#000000"> <bean:message bundle="view"
								key="account.approval1" /></FONT><FONT color="RED">*</FONT>:</STRONG></TD>
							<TD width="176"><FONT color="#003399"> </FONT><html:text
								property="approval1" styleClass="box" styleId="approval1"
								size="30" maxlength="9" /></TD>
					<!--by saumya for repair facility -->
								<TD width="121"  class="text"><STRONG> <FONT
								color="#000000"> <bean:message bundle="view"
								key="account.approval2" /></FONT>:</STRONG></TD>

					<TD width="176"><FONT color="#003399"> </FONT><html:text
								property="approval2" styleClass="box" styleId="approval2"
								size="30" maxlength="9" /></TD>
					</tr>
					
					
					
					
					
						<tr>
						 <td>
							<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
								<TR align="center">
									<TD width="70"><INPUT class="submit" type="submit"
										tabindex="25" name="Submit" value="Submit"
										onclick="return validate();"></TD>
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
							<html:hidden property="isbillable" styleId="isbillable"/> 
							<html:hidden property="searchFieldValue" styleId="searchFieldValue" />
							<html:hidden property="accountStatus" styleId="accountStatus" />
							<html:hidden property="groupId" styleId="groupId" />
							<html:hidden property="heirarchyId" styleId="heirarchyId" />
							<!-- <html:hidden property="accountCityId" styleId="accountCityId"/> -->
							<html:hidden property="accountZoneId" styleId="accountZoneId"/>
							<html:hidden property="beatId" styleId="beatId"/>
							<html:hidden property="roleName" value="N"/>
							<html:hidden property="parentLoginName" name="DPCreateAccountITHelpFormBean"/>
							<html:hidden property="billableLoginName" name="DPCreateAccountITHelpFormBean"/>
							<html:hidden property="parentAccountName" name="DPCreateAccountITHelpFormBean"/>
							<html:hidden property="billableAccountName" name="DPCreateAccountITHelpFormBean"/>
							<html:hidden property="hiddenTranctionIds" styleId="hiddenTranctionIds" name="DPCreateAccountITHelpFormBean" />
				
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