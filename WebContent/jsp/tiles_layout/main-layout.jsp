<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<html:html>
<HEAD> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href=${pageContext.request.contextPath}/jsp/tiles_layout/theme/text.css rel="stylesheet" type="text/css">
<TITLE><tiles:getAsString name="title" /></TITLE>
</HEAD>

<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellpadding="0" cellspacing="0" height="100%">
	<tr>
		<TD bgcolor="#FFFFFF" colspan="2" valign="top" align="left" height="17%" width="100%"><tiles:insert attribute="header" /></TD>
	</tr>
	<tr>
		<TD valign="top" align="left" width="16%"><tiles:insert attribute="leftmenu" /></TD>
		<td valign="top" align="left" width="84%" background="<%=request.getContextPath()%>/images/bg_main.gif"><tiles:insert attribute="body"/></TD>
	</tr>
	<tr align="left">
		<td valign="top" align="left" colspan="2" bgcolor="#FFFFFF" width="100%" height="25"><tiles:insert attribute="footer" /></TD>
	</tr>
</table>
</body>
</html:html>
