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
		<logic:iterate id="headerData" name="DPStbInBulkFormBean" property="headersOne">
		<td class="colhead" align="center">
			<b>
			<font color="blue"><bean:write name="headerData"/></font>
			</b>
		</td>
		</logic:iterate>														
		</tr>
		<tr>
		<logic:iterate id="headerDataTwo" name="DPStbInBulkFormBean" property="headersTwo">
		<td class="colhead" align="center">
			<b>
			<bean:write name="headerDataTwo"/>
			</b>
		</td>
		</logic:iterate>														
		</tr>
		
		<logic:notEmpty property="availableSerialNosList" name="DPStbInBulkFormBean">
  											
								<logic:iterate id="serialNos" property="availableSerialNosList" name="DPStbInBulkFormBean" indexId="i">
								<tr>
								<td>&nbsp;<b><bean:write name="serialNos" property="serial_no"/></b> &nbsp;</td>
								<td>&nbsp;<bean:write name="serialNos" property="product_name"/>&nbsp;</td>
								<td>&nbsp;<font color="blue"><bean:write name="serialNos" property="status"/></font>&nbsp;</td>
								<td>&nbsp;<bean:write name="serialNos" property="distId"/>&nbsp;</td>
								<td>&nbsp;<bean:write name="serialNos" property="distributor"/>&nbsp;</td>
								<td>&nbsp;<bean:write name="serialNos" property="circle"/>&nbsp;</td>
								<td>&nbsp;<bean:write name="serialNos" property="pono"/>&nbsp;</td>
								<td>&nbsp;<bean:write name="serialNos" property="poDate"/>&nbsp;</td>
								<td>&nbsp;<bean:write name="serialNos" property="dcno"/>&nbsp;</td>
								<td>&nbsp;<bean:write name="serialNos" property="dcDate"/>&nbsp;</td>
								<td>&nbsp;<bean:write name="serialNos" property="accept_date"/>&nbsp;</td>
								<td>&nbsp;<bean:write name="serialNos" property="fsc"/>&nbsp;</td>
								<td>&nbsp;<bean:write name="serialNos" property="retailer"/>&nbsp;</td>
								<td>&nbsp;<font color="blue"><bean:write name="serialNos" property="invoice_status"/></font>&nbsp;</td>
								<td>&nbsp;<bean:write name="serialNos" property="activationDate"/>&nbsp;</td>
								<td>&nbsp;<bean:write name="serialNos" property="customer"/>&nbsp;</td>
								<td>&nbsp;<bean:write name="serialNos" property="distIdRev"/>&nbsp;</td>
								<td>&nbsp;<bean:write name="serialNos" property="distributorRev"/>&nbsp;</td>
								<td>&nbsp;<bean:write name="serialNos" property="circleRev"/>&nbsp;</td>
								<td>&nbsp;<bean:write name="serialNos" property="inv_change_date"/>&nbsp;</td>
								<td>&nbsp;<bean:write name="serialNos" property="inv_change_type"/>&nbsp;</td>
								<td>&nbsp;<bean:write name="serialNos" property="defect_type"/>&nbsp;</td>
								<td>&nbsp;<bean:write name="serialNos" property="rev_dcno"/>&nbsp;</td>
								<td>&nbsp;<bean:write name="serialNos" property="rev_dc_date"/>&nbsp;</td>
								<td>&nbsp;<font color="blue"><bean:write name="serialNos" property="rev_dc_status"/></font>&nbsp;</td>
								<td>&nbsp;<font color="blue"><bean:write name="serialNos" property="rev_stock_in_status"/></font>&nbsp;</td>
								</tr>
								</logic:iterate>
												
											
								 </logic:notEmpty>	
		
		
 	</table>
 
<P><BR>
</P>
</BODY>
</html:html>
<!-- Added by Neetika 13Aug BFR 16 -->
<%
try{
			
			session.setAttribute("ReportSTB", "done");
    }
catch (Exception e) 
	{
   				System.out.println("Error: "+e);
    }
 %>
