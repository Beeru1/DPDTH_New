<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>


<%try{ %>

<%@page import="java.util.Map"%>
<%@ page import = "java.util.*" %>
<%@page import="com.ibm.dp.common.Constants"%>
<%@page import="com.ibm.dp.reports.common.*"%>
<%@page import="com.ibm.virtualization.recharge.dto.UserSessionContext"%>

<html>
<head>
<link rel="stylesheet" href="../../theme/Master.css" type="text/css">
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</HEAD>


<BODY background="${pageContext.request.contextPath}/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" >

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%" 	height="100%" valign="top">
<td align="center">
<font color="red"><B>
<%=request.getAttribute("errMsg") %>
</B></font>
	
</td>

	
</TABLE>

</BODY>
</html>

<%}catch(Exception e){
e.printStackTrace();
}%>