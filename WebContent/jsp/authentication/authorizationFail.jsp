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
<TITLE></TITLE>
</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">

<table cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">
	<tr>
		<td colspan="2" valign="top" width="100%"><jsp:include
			page="../common/dpheader.jsp" /></td>
	</tr>
	<tr valign="top">
		<td width="167" align="left" bgcolor="b4d2e7" valign="top"
			height="100%"><jsp:include page="../common/leftHeader.jsp" /></td>
		<td valign="top" width="100%" height="100%">
		<table width="700" border="0" class="text" align="left">
			<tr>
				<td width="680" valign="top">
				<table width="680" border="0" cellpadding="5" cellspacing="0"
					class="text">
					<tr height="100%" valign="top">
						<td valign="top"><STRONG><FONT
						color="#000000"><h3><bean:message key="errors.usernotautherized" bundle="error" /></h3></FONT></STRONG>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
				</table>
				</td></tr>


		</table>
		</td></tr><tr align="center" bgcolor="4477bb" valign="top">
	<td colspan="2" height="17" align="center"><jsp:include
		page="../common/footer.jsp" /></td>
</tr></table>
</body>
</html:html>
