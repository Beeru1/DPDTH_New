<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<html:html>
<HEAD>
<%@ page language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="<%=request.getContextPath()%>/theme/Master.css" rel="stylesheet"
	type="text/css">
	<LINK href=./theme/text.css rel="stylesheet" type="text/css">
<TITLE></TITLE>
<script>
function closeWindow()
{
 window.close();
}

</script>
</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif">
<h2 align="center" ></h2>
<BR>
<html:form action="/DeliveryChallanAccept.do">
<IMG src="<%=request.getContextPath()%>/images/serialNos.gif" width="450" height="22" alt="">
	
<%try{%>
<logic:notEmpty property="deliveryChallanList" name="DPDeliveryChallanAcceptBean">
<div style="width:100%; height:500px; overflow:scroll;" >
<table border="2" width="100%" >
<tr><td>
<table border="0" width="100%" >
<tr>
<td align="right"><h3>Serials Numbers&nbsp;&nbsp;:&nbsp;&nbsp;</h3></td>
<td align="left"><h3>Product Name</h3></td>
</tr>

	<logic:iterate id="serialNos" property="deliveryChallanList" name="DPDeliveryChallanAcceptBean">
	<tr>
	<td align="right"><b><bean:write name="serialNos" property="serialNo"/></b>&nbsp;&nbsp;<b>:</b>&nbsp;&nbsp;</td>
	<td align="left"><b><bean:write name="serialNos" property="strProductName"/></b></td>
	</tr>
	</logic:iterate>

</table>
</td></tr>
</table>
</div>

<table border="1" width="100%">
	<TR>
	<td align="center" width="100%">
		<html:button property="btn" value="Close" onclick="closeWindow();" /></td>
	</TR>	
</table>
</logic:notEmpty>	
<%}catch(Exception ex){ex.printStackTrace();} %>	
<br>
</html:form>
</BODY>
</html:html>
