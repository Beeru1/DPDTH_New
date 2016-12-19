<%try{%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>	
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import = "java.util.ArrayList,java.util.HashMap"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import = "java.util.*" %>
<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-disposition","attachment;filename=DistEligibilitystock.xls");
	response.setHeader("Cache-Control", "must-revalidate");
	response.setHeader( "Pragma", "public" );
%>
<html:html>
<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE>View Stock Eligibility</TITLE>
</HEAD>																		
<BODY>

<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>
	<table border="1">
	<tr>
			<td>&nbsp;<b>Distributor OLM ID&nbsp;</b></td>
			<td>&nbsp;<b><bean:write name="ViewStockEligibilityBean" property="strOLMID"/>&nbsp;</b></td>
			<td>&nbsp;<b>Distributor Name&nbsp;</b></td>
			<td>&nbsp;<b><bean:write name="ViewStockEligibilityBean" property="strDistName"/>&nbsp;</b></td>
			<td>&nbsp;<b>Security&nbsp;</b></td>
			<td>&nbsp;<b><bean:write name="ViewStockEligibilityBean" property="security"/>&nbsp;</b></td>
			<td>&nbsp;<b>Loan&nbsp;</b></td>
			<td>&nbsp;<b><bean:write name="ViewStockEligibilityBean" property="loan"/>&nbsp;</b></td>
			<td>&nbsp;<b>Balance&nbsp;</b></td>
			<td>&nbsp;<b><bean:write name="ViewStockEligibilityBean" property="balance"/>&nbsp;</b></td>
		</tr>
		
		<tr>
					
					<td class="colhead" align="center"><b>Product Name</b></td>
					<td class="colhead" align="center"><b>Available Stock</b></td>
					<td class="colhead" align="center"><b>Min Days</b></td>
					<td class="colhead" align="center"><b>Min Stock</b></td>
					<td class="colhead" align="center"><b>Max Days</b></td>
					<td class="colhead" align="center"><b>Max Stock</b></td>
					<td class="colhead" align="center"><b>Max PO Product Qty</b></td>
					<td class="colhead" align="center"><b>Product Wise Security</b></td>
					<td class="colhead" align="center"><b>Balance Security Qty</b></td>
					<td class="colhead" align="center"><b>Eligiblity</b></td>
		</tr>
		
			<logic:notEmpty property="viewStockEligibilityList" name="ViewStockEligibilityBean">
				<logic:iterate id="account" property="viewStockEligibilityList"
						name="ViewStockEligibilityBean" indexId="i">
						<logic:notEqual name="account" property="productName" value="">
						<TR>
							<TD align="center"><bean:write name="account" property="productName" /></TD>
							<TD align="center"><bean:write name="account" property="availableStock" /></TD>
							<TD align="center"><bean:write name="account" property="minDays" /></TD>
							<TD align="center"><bean:write name="account" property="minStock" /></TD>
							<TD align="center"><bean:write name="account" property="maxDays" /></TD>
							<TD align="center"><bean:write name="account" property="maxStock" /></TD>
							<TD align="center"><bean:write name="account" property="maxPoProductQty" /></TD>
							<TD align="center"><bean:write name="account" property="productWiseSecurity" /></TD>
							<TD align="center"><bean:write name="account" property="balanceSecurityQty" /></TD>
							<TD align="center"><bean:write name="account" property="eligibilty" /></TD>
						</TR>
						</logic:notEqual>
					</logic:iterate>
			 	</logic:notEmpty>  
			 
 	</table>
<P><BR>
</P>
</BODY>
</html:html>
 <%}
 catch(Exception e){e.printStackTrace();}%>