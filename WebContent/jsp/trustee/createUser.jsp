<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>
<HEAD>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<LINK href="<%=request.getContextPath()%>/theme/text.css" rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/theme/chromestyle2.css" />
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/chrome.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/JScriptLib.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<SCRIPT language="Javascript" type="text/javascript">
	function validateForm(){
		var flag='error';
		var userName=document.forms[0].loginName;
		// validate userName
		if ((userName.value==null)||(userName.value=="")){
			alert('<bean:message bundle="error" key="errors.user.username_required" />');
			userName.focus();
			return false;
		}
		var lstr=userName.value.length
		if(lstr==0 || lstr<8){
			alert('<bean:message bundle="error" key="errors.user.username_minlength" />');
			userName.focus();
			return false;
		}
		// validate emailId
		var emailID=document.forms[0].emailId;
		if ((emailID.value==null)||(emailID.value=="")){
			alert('<bean:message bundle="error" key="errors.user.emailid_required" />');
			emailID.focus();
			return false;
		}
		var errorMsg = '<bean:message bundle="error" key="errors.user.email_invalid" />';
		if (isEmail(emailID.value, errorMsg)==false){
			emailID.focus();
			return false;
		}
		// validate password
		var passwordField=document.forms[0].password;
		if ((passwordField.value==null)||(passwordField.value=="")){
			alert('<bean:message bundle="error" key="errors.user.password_required" />');
			passwordField.focus();
			return false;
		}
		var pfstr=passwordField.value.length

		if(pfstr==0 || pfstr<8){
			alert('<bean:message bundle="error" key="errors.user.username_minlength" />');
			passwordField.focus();
			return false;
		}
		// validate checkPassword
		var checkpasswordField=document.forms[0].checkPassword;
		if (!(passwordField.value==checkpasswordField.value)||(checkpasswordField.value=="")){
			alert('<bean:message bundle="error" key="errors.user.password_mismatch" />');
			passwordField.value="";
			checkpasswordField.value="";
			passwordField.focus();
			return false;
		}
		// validate groupId
		if(document.getElementById("userAuthStatus").value=="A"){
			var groupIdField=document.forms[0].groupId;
			if ((groupIdField.value== "0")){
				alert('<bean:message bundle="error" key="errors.user.groupid_required" />');
				groupIdField.focus();
				return false;
			}
		}
		// validate Contact Number
		var contactNo=document.forms[0].contactNumber;
		if ((contactNo.value==null)||(contactNo.value=="")){
			alert('<bean:message bundle="error" key="errors.user.contactno_required" />');
			contactNo.focus();
			return false;
		}
		var lstr=contactNo.value.length
		if(lstr==0 || lstr<10){
			alert('<bean:message bundle="error" key="errors.user.contactno_minlength" />');
			contactNo.focus();
			return false;
		}
		if(!(isNumeric(contactNo.value))){
			alert('<bean:message bundle="error" key="errors.user.contactno_invalid" />');
			contactNo.focus();
			return false;
		}
		document.getElementById("methodName").value = "createUser";
	}

	function checkKeyPressed()
	{
		if(window.event.keyCode=="13")
		{
			return validateForm();
		}
	}
</SCRIPT>

</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onkeypress="return checkKeyPressed();">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">
	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include
			page="../common/dpheader.jsp" flush="" /></TD>
	</TR>
	<TR valign="top">
		<TD width="167" align="left" bgcolor="b4d2e7" valign="top"
			height="100%"><jsp:include page="../common/leftHeader.jsp" flush="" /></TD>
		<TD valign="top" width="100%" height="100%"><html:form action="/UserAction"	type="com.ibm.virtualization.recharge.beans.UserFormBean" focus="loginName">
			<html:hidden property="methodName" styleId="methodName"
				name="UserFormBean" />
			<html:hidden property="flagForCircleDisplay"
						styleId="flagForCircleDisplay" />
			<html:hidden property="loginId" styleId="loginId"  />
			<html:hidden property="type" styleId="type"  />

			<!-- Default Status Active -->
			<html:hidden property="status" value="A" name="UserFormBean" />

			<TABLE width="545" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/createUser.gif" width="505" height="22" alt=""></TD>
				</TR>
				<TR>
					<TD colspan="4" class="text"><FONT color="#ff0000" size="-2"><html:errors
						bundle="error" /><html:errors bundle="view" /><html:errors /></FONT></TD>
				</TR>
				<TR>
					<TD width="126" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message key="user.username"
						bundle="view" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
					<TD width="133"><FONT color="#003399"> <html:text
						property="loginName" styleClass="box" styleId="loginName"
						size="19" tabindex="1" maxlength="20"  /> </FONT></TD>

					<TD width="121" class="text"><STRONG><FONT
						color="#000000"><bean:message key="user.emailid"
						bundle="view" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
					<TD width="171"><FONT color="#003399"> <html:text
						property="emailId" styleClass="box" styleId="emailId" size="19"
						tabindex="2" maxlength="64"  /> </FONT></TD>
				</TR>
				<TR>
					<TD class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message key="user.password"
						bundle="view" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
					<TD><FONT color="#003399"> <html:password
						property="password" styleClass="box" size="19" maxlength="20"
						tabindex="3" /> </FONT></TD>
					<TD class="text"><STRONG><FONT color="#000000"><bean:message
						key="user.checkpassword" bundle="view" /></FONT><FONT color="RED">*</FONT>
					:</STRONG></TD>
					<TD><FONT color="#3C3C3C"> <html:password
						property="checkPassword" styleClass="box" size="19" tabindex="4"
						maxlength="20" /> </FONT></TD>
				</TR>
				<TR>
					<!-- if user authorized to change or update the group then display the group -->
					<logic:match name="UserFormBean" property="userAuthStatus"
						value="A">
						<TD class="text pLeft15"><STRONG><FONT
							color="#000000"> <bean:message key="user.groupid"
							bundle="view" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
						<TD><FONT color="#003399"> <html:select
							property="groupId"  styleClass="w130"
							tabindex="5">
							<html:option value="0">
								<bean:message key="application.option.select" bundle="view" />
							</html:option>
							<html:options collection="groupList" labelProperty="groupName"
								property="groupId" />
						</html:select> </FONT></TD>
					</logic:match>
					<TD width="121" class="text"><STRONG><FONT
						color="#000000"><bean:message key="user.circleid"
						bundle="view" /></FONT>:</STRONG></TD>
					<TD><FONT color="#3C3C3C"> <html:select
						property="circleId" styleClass="w130"
						tabindex="6">
						<html:option value="0">
							<bean:message key="application.option.select" bundle="view" />
						</html:option>
						<html:options collection="circleList" labelProperty="circleName"
							property="circleId" />
					</html:select> </FONT></TD>
				</TR>
				<TR>
					<TD class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message key="user.contactno"
						bundle="view" /></FONT><FONT color="RED">*</FONT> :<br>(<bean:message key="application.digits.mobile_number" bundle="view" />)</br></STRONG></TD>
					<TD><FONT color="#003399"> <html:text
						property="contactNumber" styleClass="box" styleId="contactNumber"
						tabindex="7" size="19" maxlength="10" onkeypress="isValidNumber()"  /> </FONT></TD>
					<TD></TD>
					<TD width="171"><FONT color="#003399"> </FONT><FONT
						color="#ff0000" size="-2"></FONT></TD>
				</TR>
				<TR>
					<TD class="text pLeft15"><FONT color="#000000">&nbsp;</FONT></TD>
					<TD colspan="2">
					<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
						<TR align="center" valign="top">
							<TD><INPUT class="submit" type="submit" tabindex="8"
								name="Submit" value="Submit" onclick="return validateForm();"></TD>
							<TD width="70"><INPUT class="submit" type="button" onclick="resetAll();"
										name="Submit2" value="Reset" tabindex="21"></TD>

						</TR>
					</TABLE>
					</TD>
				</TR>
				<TR>
					<TD colspan="4" align="center">&nbsp;</TD>
					<html:hidden styleId="userAuthStatus" property="userAuthStatus"
						 />
				</TR>
			</TABLE>

		</html:form></TD>
	</TR>
	<TR align="center" bgcolor="4477bb" valign="top">
		<TD colspan="2" height="17" align="center"><jsp:include
			page="../common/footer.jsp" flush="" /></TD>
	</TR>
</TABLE>
<SCRIPT type="text/javascript">
{
	if(document.getElementById("flagForCircleDisplay").value=="true")
	{
		document.getElementById("circleId").disabled = true;
	}
}
</SCRIPT>
</BODY>
</html:html>
