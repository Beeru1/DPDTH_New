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
		
		
	
function resetForm()
{
	var len = document.forms[0].length;
	var controlType = null;

	for(i = 0; i < len; i++){
		controlType = document.forms[0].elements[i].type;
		if(controlType == "text"){
				document.forms[0].elements[i].value="";
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
function trimAll(sString) {
		while (sString.substring(0,1) == ' '){
		sString = sString.substring(1, sString.length);
		}		
		while (sString.substring(sString.length-1, sString.length) == ' '){
		sString = sString.substring(0,sString.length-1);	
		}
		return sString;
	}


	// function that checks that is any field value is blank or not
	function validate()
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
		
			if(document.getElementById("code").value==null || document.getElementById("code").value==""){
				alert("Enter Employee Id");
				document.getElementById("code").focus();
				return false; 
			}
	
		var mobileno = document.getElementById("mobileNumber").value;
		if(mobileno==null || mobileno==""){
			alert('<bean:message bundle="error" key="error.account.mobilenumber" />');
			document.getElementById("mobileNumber").focus();
			return false; 
		}
		if(mobileno != null || mobileno != ""){
		if((document.getElementById("mobileNumber").value.length)<10){
			alert('<bean:message bundle="error" key="error.account.mobilenumberlength" />');
			document.getElementById("mobileNumber").focus();
			return false;
		}
		}
		
		if(document.getElementById("accountAddress").value==null || document.getElementById("accountAddress").value==""){
			alert('<bean:message bundle="error" key="error.account.accountaddress" />');
			document.getElementById("accountAddress").focus();
			return false; 
		}

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
		
	
	//Shilpa Error
		if(document.getElementById("srNumber").value==null || document.getElementById("srNumber").value==""){
			alert("Please Enter SR Number.");
			document.getElementById("srNumber").focus();
			return false;
		}
		
		
		if(document.getElementById("approval1").value==null || document.getElementById("approval1").value==""){
			alert("Please Enter Approval 1.");
			document.getElementById("approval1").focus();
			return false;
		}
		
		var approval1 = document.getElementById("approval1").value;
		var firstCharApp1 = isNumber(approval1.charAt(0));
			if(firstCharApp1==true || approval1.charAt(0)==""){
				alert("Approval 1 Cannot Begin With A Numeric Character Or Blank Space.");
				document.getElementById("approval1").focus();
				return false;
			}
			if(document.getElementById("approval1").value.length < 8)
			{
				alert("Approval 1 Must Contain At Least 8 Characters.");
				document.getElementById("approval1").focus();
				return false;
			}
			var validApproval1 = isAlphaNumeric(approval1);
			if(validApproval1==false){
				alert("Special Characters and Blank Spaces are not allowed in Apprtoval 1");
				document.getElementById("approval1").focus();
				return false;
			}
			if(document.getElementById("approval2").value!=null && document.getElementById("approval2").value!=""){
				var approval2 = document.getElementById("approval2").value;
				var firstCharApp2 = isNumber(approval2.charAt(0));
				if(firstCharApp2==true || approval2.charAt(0)==""){
					alert("Approval 2 Cannot Begin With A Numeric Character Or Blank Space.");
					document.getElementById("approval2").focus();
					return false;
				}
				if(document.getElementById("approval2").value.length < 8)
				{
					alert("Approval 2 Must Contain At Least 8 Characters.");
					document.getElementById("approval2").focus();
					return false;
				}
				var validApproval2 = isAlphaNumeric(approval2);
				if(validApproval2==false){
					alert("Special Characters and Blank Spaces are not allowed in Apprtoval 2");
					document.getElementById("approval2").focus();
					return false;
				}
				
			}
			
		
		document.forms[0].accountAddress.value=trimAll(document.forms[0].accountAddress.value);
		
		document.getElementById("methodName").value="createIdHelpdeskAccount";
		
	
	
  	return true;
	}



	function submitAccount()
	{	
		if(validate()){
			document.getElementById("Submit").disabled=true; 
				document.forms[0].submit();
		}
			return true;
	}
	

function OpenHelpWindow(url){
		document.submitForm.action=url;
		displayWindow = window.open('', "newWin", "width =700,height= 220, left=50, top=100, scrollbars=yes,resizable=yes, status=yes");
       	document.submitForm.submit();
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
						
						<TR>

							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.accountName" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="176"><FONT color="#3C3C3C"> <html:text
								property="accountName" styleClass="box" styleId="accountName"
								onkeypress="alphaNumWithSpace()" size="19" maxlength="100"
								tabindex="3" /> </FONT><a href="#"
								onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.accountName" />','Information','width=500,height=100,resizable=yes')">
							<b> ? </b> </a></TD>
							
							<TD class="text pLeft15" id="userName"><STRONG><FONT 
								color="#000000"><bean:message bundle="view"
								key="account.accountCode" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="175" id="userNameValue"><FONT color="#003399"><html:text
								property="accountCode" tabindex="4" styleClass="box" 
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
								tabindex="5" /> </FONT><a href="#"
								onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.contactName" />','Information','width=500,height=100,resizable=yes')">
							<b> ? </b> </a></TD>
							<TD class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.email" /></FONT>
								<div id="emailMand" style="display: inline;">
								<font color="red">*</font></div> :</STRONG></TD>
							<TD width="175"><FONT color="#3C3C3C"> <html:text
								property="email" tabindex="6" styleClass="box" styleId="email"
								name="DPCreateAccountITHelpFormBean" size="19" maxlength="64" /> </FONT></TD>
							
						</TR>
						<TR>
							<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message key="account.EmployeeCode" bundle="view"/>
									<font color="red">*:</FONT>
							</TD>
							<TD width="175"><FONT color="#003399"> <html:text
								property="code" styleClass="box" styleId="code" tabindex="21"
								onkeypress="alphaNumWithoutSpace()" size="19"
								name="DPCreateAccountITHelpFormBean" maxlength="10" /></FONT> <a href="#"
								onclick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.code" />','Information','width=500,location=no,height=100,resizable=yes')">
							<b> ? </b> </a></TD>
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.mobileNumber" /></FONT><FONT color="RED">*</FONT> :<br>
							(<bean:message key="application.digits.mobile_number"
								bundle="view" />)</br>
							</STRONG></TD>

							<TD width="176"><FONT color="#3C3C3C"> <html:text
								property="mobileNumber" styleClass="box" styleId="mobileNumber"
								tabindex="8" onkeypress="isValidNumber()" size="19"
								name="DPCreateAccountITHelpFormBean" maxlength="10" /> </FONT></TD>
						</TR>
						
						<TR>
							<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.accountAddress" /></FONT><FONT color="RED">*</FONT> :</STRONG>
							</TD>
							<TD width="175"><FONT color="#003399"> <html:textarea
								property="accountAddress" styleId="accountAddress" tabindex="10"
								onfocus="setAddressStatus()" onblur="ResetAddressStatus()" onkeypress="alphaNumWithSpace()" 
								cols="17" rows="3" onkeyup="return maxlength('1');"></html:textarea> </FONT>
							</TD>
							<TD width="126" class="text pLeft15"><STRONG> <FONT
								color="#000000"><bean:message bundle="view"
								key="account.accountAddress2" /></FONT> :</STRONG>
							</TD>
							<TD width="176"><FONT color="#003399"> <html:textarea onkeyup="return maxlength('2');" onkeypress="alphaNumWithSpace()" 
								property="accountAddress2" styleId="accountAddress2"
								tabindex="11" cols="17" rows="3"></html:textarea> </FONT>
							</TD>
						</TR>
						
						<tr id="flag" style="display: none;">
							<td><html:text property="accountLevelFlag" value="" tabindex="13"></html:text>
							</td>
						</tr>
						
						
					
						<tr id="SRFireld">
							<TD width="126" class="text pLeft15" nowrap="nowrap"><STRONG> <FONT
								color="#000000"> 
								SR Number</FONT><font color="red">*</FONT>:</STRONG></TD>
							<TD width="175" nowrap="nowrap"><FONT color="#003399"> <html:text
								property="srNumber" styleClass="box" styleId="srNumber"
								size="16" maxlength="50" tabindex="41" /> </FONT></TD>
							</tr>
					  <tr id="approval">
							<TD width="126" class="text pLeft15" nowrap="nowrap"><STRONG> <FONT
								color="#000000"> 
								Approver 1</FONT><font color="red">*</FONT>:</STRONG></TD>
							<TD width="175" nowrap="nowrap"><FONT color="#003399"> <html:text
								property="approval1" styleClass="box" styleId="approval1"
								size="16"  tabindex="42" maxlength="9" /> </FONT></TD>
							<TD width="121" class="text"><STRONG> <FONT	color="#000000"> 
								Approver 2</FONT>:</STRONG></TD>
							<TD width="176"><FONT color="#003399"> <html:text
								property="approval2" styleClass="box" styleId="approval2"
								size="19"  tabindex="43"  maxlength="9"/> </FONT></TD>
								
						</tr>			
									
						
						<TR>
							<TD class="text"><STRONG><FONT color="#000000">&nbsp;</FONT></STRONG></TD>
							<TD colspan="3">
							<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
								<TR align="center">
									<TD width="70"><INPUT class="submit" type="button"
										name="Submit" value="Submit" tabindex="41"
										onclick="submitAccount();">
									</TD>
									<TD width="70"><INPUT class="submit" type="button"
										onclick="resetForm();" name="Submit2" value="Reset"
										tabindex="42"></TD>
								</TR>
							</TABLE>
							</TD>
						</TR>
						<TR>
							<TD colspan="4" align="center">&nbsp;</TD>
							<html:hidden property="methodName" value="createAccount" />
							<!-- Active -->
							
						</TR>
					</TABLE>
					</TD>
				</TR>
					
				
				
			</TABLE>
			
			<div id="passwordDiv"  style="display:none;">
							<table>
			<TR id="showPasswordRow" style="display:block;">
							<TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.iPassword" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="175"><FONT color="#003399"> <html:password
								property="IPassword" styleClass="box" styleId="IPassword" value="@@@@@@@@"
								size="19" maxlength="20" tabindex="15" readonly="true" /> </FONT><FONT color="#ff0000"
								size="-2"></FONT></TD>
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.checkIPassword" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
							<TD width="176"><FONT color="#3C3C3C"> <html:password
								property="checkIPassword" styleClass="box" value="@@@@@@@@"
								styleId="checkIPassword" size="19" maxlength="20" tabindex="16" readonly="true" />
							</FONT></TD>
						</TR>
						</table>
						</div>
			<logic:equal value="true" name="DPCreateAccountITHelpFormBean" property="errorFlag">
			<script>
				parentCheck('false');
				getChange('category');
			</script>
			</logic:equal>
			
		</html:form>
		
		</TD>
	</TR>
	<TR align="center" bgcolor="4477bb" valign="top">
		<TD colspan="2" height="17" align="center"><jsp:include
			page="../common/footer.jsp" flush="" /></TD>
	</TR>
</TABLE>
</BODY>
</html:html>