
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		if(document.forms[0].loginName.value.length==0 || document.forms[0].loginName.value==""){
			alert('<bean:message bundle="error" key="errors.changepassword.required_login_name" />');
			document.forms[0].loginName.focus();
			return false;
		}
		if(document.forms[0].oldPassword.value.length==0 || document.forms[0].oldPassword.value==""){
			alert('<bean:message bundle="error" key="errors.changepassword.required_oldpassword" />');
			document.forms[0].oldPassword.focus();
			return false;
		}
		if(document.forms[0].newPassword.value.length==0 || document.forms[0].newPassword.value==""){
			alert('<bean:message bundle="error" key="errors.changepassword.required_newpassword" />');
			document.forms[0].newPassword.focus();
			return false;
		}
		if(document.forms[0].confirmPassword.value.length==0 || document.forms[0].confirmPassword.value==""){
			alert('<bean:message bundle="error" key="errors.changepassword.required_confirmpassword" />');
			document.forms[0].confirmPassword.focus();
			return false;
		}
		if(document.forms[0].newPassword.value != document.forms[0].confirmPassword.value){
			alert('<bean:message bundle="error" key="errors.changepassword.mismatch" />');
			document.forms[0].oldPassword.value="";
			document.forms[0].newPassword.value="";
			document.forms[0].confirmPassword.value="";
			document.forms[0].oldPassword.focus();
			return false;
		}
		if(document.forms[0].newPassword.value == document.forms[0].oldPassword.value){
			alert('<bean:message bundle="error" key="errors.changepassword.samepassword" />');
			document.forms[0].oldPassword.value="";
            document.forms[0].newPassword.value="";
            document.forms[0].confirmPassword.value="";
            document.forms[0].oldPassword.focus();
            return false;
         }
		document.getElementById("methodName").value="changePasswordNonContextUser";
   		return true;
	}

	function checkKeyPressed(){
		if(window.event.keyCode=="13"){
			return validate();
		}
	}
var a = "0";	
	function OpenHelpWindow(url){
		url = "<%=request.getContextPath()%>" + url;
		document.submitForm.action=url;
		if(a=="0"){
		a = "1";
		displayWindow = window.open('', "newWin", "width =650,height= 300, left=50, top=100, scrollbars=yes,resizable=yes, status=yes");
       	document.submitForm.submit();
       	}
	}
	
function OpenHelpWindow1(url){
		url = "<%=request.getContextPath()%>" + url;
		document.submitForm.action=url;
		displayWindow = window.open('', "newWin", "width =650,height= 300, left=50, top=100, scrollbars=yes,resizable=yes, status=yes");
       	document.submitForm.submit();
	}	
	function goBack(){
		document.forms[0].action="ChangePasswordAction.do?methodName=displayLogin";
		document.forms[0].method="post";
	}
	
	function goToBack(){
		if(window.event.keyCode=="13"){
			return goBack();
		}
	}
</SCRIPT>
</HEAD>


<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">
	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include
			page="loginHeader.jsp" flush="" /></TD>
	</TR>
	<TR valign="top">
		<TD width="167" align="left" bgcolor="b4d2e7" valign="top"
			height="100%"><jsp:include page="loginLeftHeader.jsp" flush="" /></TD>
		<TD valign="top" width="100%" height="100%"><html:form
			name="ChangePasswordAction" action="/ChangePasswordAction"
			type="com.ibm.virtualization.recharge.beans.ChangePasswordFormBean"
			focus="loginName">
			<html:hidden property="methodName" styleId="methodName" />
			<TABLE width="78%" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/changePassword.gif" width="505" height="22" alt=""></TD>
				</TR>
				<TR>
					<TD align="left" class="txt" colspan="4"><FONT color="red">
					<html:errors property="validationerror" /><c:out value="${sessionScope.message}"></c:out><BR>
					<html:errors bundle="error" /></FONT></TD>
				</TR>
				<TR>
					<TD width="25%" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="changepassword.login_name" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
					<TD><html:text property="loginName" styleId="loginName"
						size="19" styleClass="box" tabindex="1" /></TD>
					<TD class="text">&nbsp;</TD>
					<TD>&nbsp;</TD>
				</TR>
				<TR>
					<TD width="25%" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="changepassword.password_old" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
					<TD><html:password property="oldPassword" size="19"
						styleClass="box" maxlength="20" tabindex="2" redisplay="false" /></TD>
					<TD class="text">&nbsp;</TD>
					<TD>&nbsp;</TD>
				</TR>
				<TR>
					<TD class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="changepassword.password_new" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
					<TD><html:password property="newPassword" size="19"
						styleClass="box" maxlength="20" tabindex="3" redisplay="false"
						onfocus="OpenHelpWindow('/jsp/authentication/helpforpassword.jsp');" />
						<a href="#"
								title="<bean:message bundle="view" key="detail.iPassword" />"
								onclick="OpenHelpWindow1('/jsp/authentication/helpforpassword.jsp')">
							<b> <font color="red">Help</font></b></a>
						</TD>
					<TD class="text">&nbsp;</TD>
					<TD>&nbsp;</TD>
				</TR>
				<TR>
					<TD class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="changepassword.password_confirm" /></FONT><FONT color="RED">*</FONT>
					:</STRONG></TD>
					<TD><html:password property="confirmPassword" size="19"
						styleClass="box" maxlength="20" tabindex="4" redisplay="false" />
						</TD>
					<TD class="text">&nbsp;</TD>
					<TD>&nbsp;</TD>
				</TR>
				<TR>
					<TD class="text pLeft15"><FONT color="#000000">&nbsp;</FONT></TD>
					<TD colspan="3">
					<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
						<TR>
							<TD class=" height19" nowrap align="center">
								<html:submit value="Submit"
									styleClass="submit" property="submit" tabindex="5"
									onclick="return validate();" />
								<INPUT class="submit" type="button" onclick="resetAll();"
									name="Submit2" value="Reset" tabindex="6">
								<INPUT class="submit" type="submit" onclick="goBack();"
										name="BACK" value="Back" tabindex="7" onkeypress="return goToBack();">
							</TD>
						</TR>
						<TR align="center">
							<TD colspan="4" align="left"><FONT color="#ff0000" size="-1">
							<html:errors bundle="view" property="message" /> </FONT></TD>
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
