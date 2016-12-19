<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@page import="java.text.SimpleDateFormat"%>
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
	<LINK href=<%=request.getContextPath()%>/jsp/hbo/theme/text.css rel="stylesheet" type="text/css">
<TITLE></TITLE>
<script>
function closeWindow()
{
 window.close();
}
</script>
</HEAD>
<BODY>
<h2 align="center" ></h2>
<BR>
<html:form action="/stockAcceptTransfer.do">

	
<%try{

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	String date = sdf.format(new java.util.Date()) ;


%>

<table border="0" width="100%" >

<tr>
<td align="left" width="50%">Ref NO.:<bean:write name="DistRecoBean" property="refNo" /></td>

<td align="right" width="50%">DATE: <%=date%> </td>

</tr>

<tr><td align="left"></td></tr><tr><td align="left"></td></tr><tr><td></td></tr><tr><td></td></tr>
<tr>
<td align="left">To,</td>
</tr>
<tr>
<td align="left">M/S Bharti Telemedia Limited,</td>
</tr>
<tr>
<td align="left">Plot No. 16,</td>
</tr>
<tr>
<td align="left">Udyog Vihar Phase-IV,</td>
</tr>
<tr>
<td align="left"> Gurgaon-122015,</td>
</tr>
<tr>
<td align="left">Haryana</td>
</tr>
<tr><td align="left"></td></tr><tr><td align="left"></td></tr><tr><td></td></tr><tr><td></td></tr>
<tr>
<td align="left"> <strong>Subject:Stock Confirmation Letter.</strong></td>
</tr>
<tr><td align="left"></td></tr><tr><td align="left"></td></tr><tr><td></td></tr><tr><td></td></tr>

<tr>
<td align="left"> Dear Sir/Madam,</td>
</tr>
<tr>
<td align="left" colspan="2">Please find below the details of the stock held by us on the date <bean:write name="DistRecoBean" property="certDate" />.</td>
</tr>


<tr>
<td>&nbsp;</td>
</tr>
</table>

<table width="100%" bordercolor="black" cellspacing="0" cellpadding="0" border='1'>
<tr>
	<td width="10%" align="center" style="border-right:1px solid;border-bottom:1px solid;"><strong>Sl. No</strong></td>
	<td width="15%" align="center" style="border-right:1px solid;border-bottom:1px solid;"><strong>Item</strong></td>
	<td width="20%" align="center" style="border-right:1px solid;border-bottom:1px solid;"><strong>Quantity</strong></td>
</tr>
<logic:notEmpty property="printRecoList"
			name="DistRecoBean">
<logic:iterate id="recodetails" property="printRecoList"
				name="DistRecoBean" indexId="i">
<tr>
	<td width="10%" align="center" style="border-right:1px solid;"><font size=2><%=(i.intValue()+1)%></td>
	<td width="15%" align="left" style="border-right:1px solid;"><font size=2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<bean:write name="recodetails" property="productName" /></td>
	<td width="20%" align="center" style="border-right:1px solid;"><font size=2><bean:write name="recodetails" property="quantity" /></td>
</tr>
</logic:iterate>
</logic:notEmpty>
</table>
<BR>

<tr>
<td>

	*Note- All swap category stock is including C2S, DOA & Upgrade.

</td>
</tr>

<BR>
<table border="0" width="100%" >

<tr>
<td>
	<!-- I hereby certify that the above mentioned quantities are true to the best of my knowledge.-->
	I hereby certify that the above mentioned quantities are true and matching with records kept by me.
</td>
</tr>
<tr>
<td></td>
</tr>
<tr>
<td></td>
</tr>
<tr>
<td></td>
</tr>

<tr>
<td>With Best Regards,</td>
</tr>
<tr>
<td> <bean:write name="DistRecoBean" property="distName" /> </td>
</tr>
</table>
<br/><br/><br/>

<table border="0" width="100%">
	<TR>
	<td align="center" width="100%">
		This is system generated certificate thus does not require any signature.
		</td>
	</TR>	
</table>
<!--<table border="0" width="100%">
	<TR>
	<td align="center" width="100%">
		<html:button property="btn" value="Close" onclick="closeWindow();" />
		<html:button property="btn" value="Print" onclick="javascript:window.print();" />
		</td>
	</TR>	
</table>

--><%
}catch(Exception ex){ex.printStackTrace();} %>	
<br>
</html:form>
<script>
	window.print();
</script>
</BODY>
</html:html>
