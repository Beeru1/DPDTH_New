<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="<%=request.getContextPath()%>/theme/text.css" rel="stylesheet" type="text/css">
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<SCRIPT type="text/javascript">
	function validate(){
		if(document.forms[0].oldPassword.value.length==0 || document.forms[0].oldPassword.value==""){
         	alert('<bean:message bundle="error" key="errors.changempassword.required_oldpassword" />');
         	document.forms[0].oldPassword.focus();
         	return false;
		}
		if(document.forms[0].newPassword.value.length==0 || document.forms[0].newPassword.value==""){
			alert('<bean:message bundle="error" key="errors.changempassword.required_newpassword" />');
			document.forms[0].newPassword.focus();
			return false;
		}
		
		if((document.getElementById("newPassword").value.length)<4){
			alert('<bean:message bundle="error" key="error.account.mpassword_minlength" />');  
			document.getElementById("newPassword").value="";
			document.getElementById("newPassword").focus();
			return false;
		}
		
		if(document.forms[0].confirmPassword.value.length==0 || document.forms[0].confirmPassword.value==""){
			alert('<bean:message bundle="error" key="errors.changempassword.required_confirmpassword" />');
			document.forms[0].confirmPassword.focus();
			return false;
		}
		if(document.forms[0].newPassword.value != document.forms[0].confirmPassword.value){
			alert('<bean:message bundle="error" key="errors.changempassword.mismatch" />');
			document.forms[0].oldPassword.value="";
			document.forms[0].newPassword.value="";
			document.forms[0].confirmPassword.value="";
			document.forms[0].oldPassword.focus();
			return false;
		}
		if(document.forms[0].newPassword.value == document.forms[0].oldPassword.value){
			alert('<bean:message bundle="error" key="errors.changempassword.samepassword" />');
			document.forms[0].oldPassword.value="";
			document.forms[0].newPassword.value="";
			document.forms[0].confirmPassword.value="";
			document.forms[0].oldPassword.focus();
			return false;
		}
		document.getElementById("methodName").value="changeMPassword";
   		return true;
	}
	function OpenHelpWindow(url){
		document.submitForm.action=url;
		displayWindow = window.open('', "newWin", "width =700,height= 220, left=50, top=100, scrollbars=yes,resizable=yes, status=yes");
       	document.submitForm.submit();
	}
	function checkKeyPressed()
	{
		if(window.event.keyCode=="13")
		{
			return validate();
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
			name="ChangePasswordAction" action="/ChangePasswordAction"
			type="com.ibm.virtualization.recharge.beans.ChangePasswordFormBean"
			focus="oldPassword">
			<html:hidden property="methodName" styleId="methodName" />

			<TABLE width="78%" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<td colspan="4" class="text"><br>
					<IMG src="<%=request.getContextPath()%>/images/changeMPassword.gif" width="505" height="22" alt=""></TD>
				</TR>

				<TR>
					<TD align="left" class="txt" colspan="4"><FONT color="red">
					<html:errors property="validationerror" /><BR>
					<html:errors bundle="error" property="error" /><BR>
					<html:errors bundle="view" property="message" /></FONT></TD>
				</TR>

				<TR>
					<TD width="25%" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="changempassword.password_old" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
					<TD><html:password property="oldPassword" size="19"
						styleId="oldPassword" styleClass="box" maxlength="8" onkeypress="isValidNumber()"
						redisplay="false" tabindex="1" /></TD>
					<TD class="text">&nbsp;</TD>
					<TD>&nbsp;</TD>
				</TR>
				<TR>
					<TD class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="changempassword.password_new" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
					<TD><html:password property="newPassword" size="19"
						styleClass="box" maxlength="8" redisplay="false" tabindex="2" onkeypress="isValidNumber()" />
						<a href="#" title="<bean:message bundle="view" key="help.title.mpassword" />"
								onClick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.accountmpasword" />','Information','width=500,height=100,resizable=yes')">
							<b> ? </b> </a></TD>
					<TD class="text">&nbsp;</TD>
					<TD>&nbsp;</TD>
				</TR>
				<TR>
					<TD class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="changempassword.password_confirm" /></FONT><FONT color="RED">*</FONT>
					:</STRONG></TD>
					<TD><html:password property="confirmPassword" size="19"
						styleClass="box" maxlength="8" redisplay="false" tabindex="3" onkeypress="isValidNumber()" />
						<a href="#" title="<bean:message bundle="view" key="help.title.mpassword" />"
								onClick="OpenHelpWindow('jsp/account/detailInformationPage.jsp?InformationDetails=<bean:message
									bundle="view" key="detail.accountmpasword" />','Information','width=500,height=100,resizable=yes')">
							<b> ? </b> </a></TD>
					<TD class="text">&nbsp;</TD>
					<TD>&nbsp;</TD>
				</TR>
				<TR>
					<TD class="text pLeft15"><FONT color="#000000">&nbsp;</FONT></TD>
					<TD colspan="3">
					<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
						<TR align="center">
							<TD width="70"><html:submit value="Submit"
								styleClass="submit" property="submit" tabindex="4"
								onclick="return validate();" /></TD>
							<TD width="70">&nbsp;</TD>
							<TD width="70"><INPUT class="submit" type="button" onclick="resetAll();"
										name="Submit2" value="Reset" tabindex="5"></TD>
							<TD width="70">&nbsp;</TD>
						</TR>
					</TABLE>
					</TD>
				</TR>
			</TABLE>
		</html:form>
		<div id="submitFormDiv" style="display:none;">
			<form name="submitForm" method="post" target="newWin">
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


