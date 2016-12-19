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
		// validate groupId
		var groupIdField=document.forms[0].groupId;
		if ((groupIdField.value== "0")){
			alert('<bean:message bundle="error" key="errors.user.groupid_required" />');
			groupIdField.focus();
			return false;
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
		 document.getElementById("methodName").value="editUser";
	}

	function setBack(){
	   document.getElementById("methodName").value="getUserList";
	}
	function checkKeyPressed(){
		if(window.event.keyCode=="13"){
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
		<TD valign="top" width="100%" height="100%"><html:form
			name="UserFormBean" action="/UserAction"
			type="com.ibm.virtualization.recharge.beans.UserFormBean"
			scope="request" focus="emailId">
			<html:hidden property="methodName" styleId="methodName" />
			<html:hidden property="flagForCircleDisplay"
						styleId="flagForCircleDisplay" />
			<html:hidden property="loginId" styleId="loginId" />
			<html:hidden property="password" styleId="password" />
			<html:hidden property="type" styleId="type" />
			<html:hidden property="selectedCircleId" styleId="selectedCircleId" />
			<html:hidden property="selectedStatus" styleId="selectedStatus" />
			<html:hidden property="page" styleId="page" />
			
			<logic:notMatch name="UserFormBean" property="userAuthStatus"
				value="A">
				<html:hidden property="groupId" styleId="groupId" />

			</logic:notMatch>




			<TABLE width="545" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/editUser.gif" width="505" height="22" alt=""></TD>
				</TR>
				<TR>
					<TD colspan="4" class="text"><FONT color="#ff0000" size="-2"><html:errors
						bundle="error" /><html:errors /></FONT></TD>
				</TR>
				<TR>
					<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message key="user.username"
						bundle="view" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
					<TD width="163"><FONT color="#003399"> <html:text
						property="loginName" styleClass="box" styleId="loginName"
						size="19" readonly="true" name="UserFormBean" /> </FONT></TD>
					<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message key="user.emailid"
						bundle="view" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
					<TD width="163"><FONT color="#003399"> <html:text
						property="emailId" styleClass="box" styleId="emailId" size="19"
						name="UserFormBean" tabindex="1" maxlength="64" /> </FONT></TD>
				</TR>

				<TR>
					<!-- if user authorized to change or update the group then display the group -->
					<logic:match name="UserFormBean" property="userAuthStatus"
						value="A">
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message key="user.groupid"
							bundle="view" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
						<TD width="163"><FONT color="#003399"> <html:select
							property="groupId" name="UserFormBean" tabindex="2" styleClass="w130">
							<html:option value="0">
								<bean:message key="application.option.select" bundle="view" />
							</html:option>
							<html:options collection="groupList" labelProperty="groupName"
								property="groupId" />
						</html:select> </FONT></TD>
					</logic:match>

					<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message key="user.circleid"
						bundle="view" /></FONT> :</STRONG></TD>
					<TD width="171"><FONT color="#3C3C3C"> <html:select
						property="circleId" name="UserFormBean" styleClass="w130"
						tabindex="3">
						<html:option value="0">
							<bean:message key="application.option.select" bundle="view" />
						</html:option>
						<html:options collection="circleList" labelProperty="circleName"
							property="circleId" />
					</html:select> </FONT></TD>
				</TR>
				<TR>
					<TD width="256" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message key="user.contactno"
						bundle="view" /></FONT><FONT color="RED">*</FONT> :<br>(<bean:message key="application.digits.mobile_number" bundle="view" />)</br></STRONG></TD>
					<TD width="163"><FONT color="#003399"> <html:text
						property="contactNumber" styleClass="box" size="19" maxlength="10"
						name="UserFormBean" tabindex="4" onkeypress="isValidNumber()" /> </FONT></TD>
					<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="user.status" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
					<TD width="171"><FONT color="#3C3C3C"><html:select
						property="status" name="UserFormBean" styleClass="w130"
						tabindex="5">
						<html:option value="A">
							<bean:message key="application.option.active" bundle="view" />
						</html:option>
						<html:option value="D">
							<bean:message key="application.option.inactive" bundle="view" />
						</html:option>
					</html:select></FONT></TD>
				</TR>
				<TR>
					<html:hidden property="startDt" styleId="startDt"
						name="UserFormBean" />
					<html:hidden property="endDt" styleId="endDt" name="UserFormBean" />
					<TD class="text pLeft15"><FONT color="#000000">&nbsp;</FONT></TD>
					<TD colspan="2">
					<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
						<TR align="center" valign="top">
							<!-- <td ><html:submit styleClass="submit" /></td>  -->
							<TD width="70"><INPUT class="submit" type="submit"
								tabindex="6" name="Submit" value="Submit"
								onclick="return validateForm();"></TD>
							<TD width="70"><INPUT class="submit" type="submit"
								tabindex="7" name="Submit2" value="Back" onclick="setBack();"></TD>
						</TR>
					</TABLE>
					</TD>
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
