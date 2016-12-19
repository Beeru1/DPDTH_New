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

<TITLE><tiles:getAsString name="title" /></TITLE>
</HEAD>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" background="<%=request.getContextPath()%>/images/bg_main.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="1">

	<tr>
		<td valign="top" ><tiles:insert attribute="body" /></TD>
	</tr>
	
</table>
</body>
</html:html>
