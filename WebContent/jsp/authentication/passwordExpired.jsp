
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
		<TD valign="top" width="100%" height="100%">
		<TABLE width="545" border="0" cellpadding="5" cellspacing="0"
			class="text">
			<TR>
				<TD colspan="4" class="text"><BR>
				<H3><U><bean:message bundle="view"
					key="application.change_password.heading" /></U></H3>
				<HR color="#ff0000">
				</TD>
			</TR>
			<TR>
				<TD align="left" class="txt" colspan="4"><FONT color="#ff0000">
				<bean:message bundle="view"
					key="application.change_password.message" /></FONT> <A href="<%=request.getContextPath()%>/jsp/authentication/login.jsp"><FONT
					color="#0000ff"><bean:message bundle="view"
					key="application.logout.redirect" /></FONT></A></TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
	<TR align="center" bgcolor="4477bb" valign="top">
		<TD colspan="2" height="17" align="center"><jsp:include
			page="../common/footer.jsp" flush="" /></TD>
	</TR>
</TABLE>
</BODY>
</html:html>
