<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>	
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import = "java.util.*" %>
<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-disposition","attachment;filename=closeDistributorRecoExportToExcel.xls");
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
<TITLE>DP Reports</TITLE>
</HEAD>																		
<BODY>
	<table border="1">
	
		<tr>
					<td rowspan="2" class="colhead" align="center"><b>S.No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor OLM</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Account Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Circle Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>TSM Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Product Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Type of Stock</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Serial Number</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor Flag</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor Remarks</b></td>
					<td rowspan="2" class="colhead" align="center"><b>FlushOut Flag</b></td>
					<td rowspan="2" class="colhead" align="center"><b>DTH Remarks</b></td>
		</tr>
		<tr></tr>
		<%
		int i=0;
		 %>
		 <logic:notEmpty name="CloseDistributorRecoBean" property="detailsList">
 		<logic:iterate id="reportData" name="CloseDistributorRecoBean" property="detailsList">
 		<%i++; %>
 		
 					<tr>
					<td align="center" class="text"><%=i %></td>					
					<td align="center" class="text"><bean:write name="reportData" property="distOlmId"/></td>
					<td align="center" class="text"><bean:write name="reportData" property="accountName"/></td>
					<td align="center" class="text"><bean:write name="reportData" property="circleName"/></td>
					<td align="center" class="text"><bean:write name="reportData" property="tsmName"/></td>
					<td align="center" class="text"><bean:write name="reportData" property="productName"/></td>
					<td align="center" class="text"><bean:write name="reportData" property="typeOfStock"/></td>
					<td align="center" class="text"><bean:write name="reportData" property="serialNo" /></td>
					<td align="center" class="text"><bean:write name="reportData" property="distFlag"/></td>
					<td align="center" class="text"><bean:write name="reportData" property="distRemarks"/></td>
					<td align="center" class="text"><bean:write name="reportData" property="flushOutFlag"/></td>
					<td align="center" class="text"><bean:write name="reportData" property="dthRemarks"/></td>
				</tr>
		
			</logic:iterate>
			</logic:notEmpty>
			<logic:empty name="CloseDistributorRecoBean" property="detailsList">
			<tr><td colspan="10" align="center">No Record Found</td></tr>
			</logic:empty>
		 	</table>	
</BODY>
</html:html>
