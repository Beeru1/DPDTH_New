
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page isErrorPage="true"%>
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
<SCRIPT language="javascript" type="text/javascript">
 	function forgotPass(){
 	var url="<%=request.getContextPath()%>/initForgotPassword.do";
 	document.forms[0].action=url;
	document.forms[0].method="post";
	document.forms[0].submit();
	}

	// use to check blank values of user name or password
	function doValidate(){
		var loginName = document.forms[0].loginName;
		var iPassword = document.forms[0].password;
		if((loginName.value=="")||(loginName.value.length==0)||(loginName.value==null)){
			alert('<bean:message bundle="error" key="errors.login.username_required"/>');
			loginName.focus();
			return false;
		}
		if((iPassword.value=="")||(iPassword.value.length==0)||(iPassword.value==null)){
			alert('<bean:message bundle="error" key="errors.login.password_required"/>');
			iPassword.focus();
			return false;
		}
		return true;
	}
	// set focus on user name control  
	function doLoad(){
		document.forms[0].loginName.focus();
	}

</SCRIPT>

</HEAD>



<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onload="javascript:doLoad();">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">

	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include
			page="/jsp/authentication/loginHeader.jsp" flush="" /></TD>
	</TR>
	<TR valign="top">
		<TD width="167" align="left" bgcolor="567FC0" valign="top"
			height="100%"><jsp:include page="/jsp/authentication/loginLeftHeader.jsp" flush="" /></TD>
		<TD valign="top" width="100%" height="100%">
		<html:form action="/LoginAction" onsubmit="javascript:return doValidate()">
			<TABLE width="545" border="0" cellpadding="5" cellspacing="0"
				class="text">

				<TR>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/memberLogin.gif" width="505" height="22" alt=""></TD>
				</TR>
				<TR>
					<TD align="left" class="txt" colspan="4"><bean:write name="AuthenticationFormBean" property="message"/></TD>
				</TR>
				<TR>
					<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="login.loginusername" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
					<TD width="133"><html:text property="loginName" size="19" 
						styleClass="box" /></TD>
					<TD width="121" class="text">&nbsp;</TD>
					<TD width="171">&nbsp;</TD>
				</TR>
				<TR>
					<TD class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="login.loginpassword" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
					<TD>
					<input type="password" size="19" class="box" redisplay="false" name="password"  />
					<!--<html:password property="password" size="19" styleClass="box" redisplay="false"  /> -->
					
					</TD>
					<TD width="121" class="text">&nbsp;</TD>
					<TD width="171">&nbsp;</TD>
				</TR>
				<TR> 
					<TD class="text pLeft15"><FONT color="#000000">&nbsp;</FONT></TD>
					<TD colspan="3">
					<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
						<TR align="center">
							<TD width="70"><html:submit value="Login"
								styleClass="submit" /></TD>
							<TD width="70">&nbsp;</TD>
							<TD width="70"><INPUT class="submit" type="button" onclick="resetAll();"
										name="Submit2" value="Cancel"></TD>
							<TD width="70">&nbsp;</TD>
						</TR>
						<tr>
						<td colspan="3">
							<a href="javascript:forgotPass()"><font color="red">Forgot Password ?</font></a>
						</td>
						</tr>
					</TABLE>
					</TD>
					
				</TR>
				<TR>
					<TD colspan="4" align="center"><FONT color="RED"><STRONG><html:errors
						bundle="error" /><html:errors /></STRONG></FONT></TD>
				</TR>
			</TABLE>
		</html:form></TD>
	</TR>
	<TR align="center" valign="top">
		<TD colspan="2" height="17" align="center"><jsp:include
			page="../common/footer.jsp" flush="" /></TD>
	</TR>
</TABLE>

</BODY>
</html:html>
