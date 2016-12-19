
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>	
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import = "java.util.*" %>
<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-disposition","attachment;filename=Report.xls");
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
<TITLE>STB REPORTS</TITLE>
</HEAD>																		
<BODY>
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
		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</tr>
		<tr>
			<TD>&nbsp;Product Name&nbsp;</TD>
			<TD>&nbsp;Available Stock&nbsp;</TD>
			<TD>&nbsp;Min Days&nbsp;</TD>
			<TD>&nbsp;Min Stock&nbsp;</TD>
			<TD>&nbsp;Max Days&nbsp;</TD>
			<TD>&nbsp;Max Stock&nbsp;</TD>
			<TD>&nbsp;Max PO&nbsp;</TD>
			<TD>&nbsp;Product Wise Security&nbsp;</TD>
			<TD>&nbsp;Balance Security Qty&nbsp;</TD>
			<TD>&nbsp;Eligiblity&nbsp;</TD>
		</tr>
		<logic:notEmpty property="stkEligExcelDetailList" name="ViewStockEligibilityBean">
			<logic:iterate id="eligibDetail" property="stkEligExcelDetailList" name="ViewStockEligibilityBean" indexId="i">
				<tr>
					<td>&nbsp;<bean:write name="eligibDetail" property="productName"/>&nbsp;</td>
					<td>&nbsp;<bean:write name="eligibDetail" property="availableStock"/>&nbsp;</td>
					<td>&nbsp;<bean:write name="eligibDetail" property="minDays"/>&nbsp;</td>
					<td>&nbsp;<bean:write name="eligibDetail" property="minStock"/>&nbsp;</td>
					<td>&nbsp;<bean:write name="eligibDetail" property="maxDays"/>&nbsp;</td>
					<td>&nbsp;<bean:write name="eligibDetail" property="maxStock"/>&nbsp;</td>
					<td>&nbsp;<bean:write name="eligibDetail" property="maxPoProductQty"/>&nbsp;</td>
					<td>&nbsp;<bean:write name="eligibDetail" property="productWiseSecurity"/>&nbsp;</td>
					<td>&nbsp;<bean:write name="eligibDetail" property="balanceSecurityQty"/>&nbsp;</td>
					<td>&nbsp;<bean:write name="eligibDetail" property="eligibilty"/>&nbsp;</td>
				</tr>
			</logic:iterate>
		</logic:notEmpty>	
 	</table>
<P><BR>
</P>
</BODY>
</html:html>
