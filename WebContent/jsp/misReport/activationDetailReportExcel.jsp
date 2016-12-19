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
	response.setHeader("content-disposition","attachment;filename=ActivationDetailReport.xls");
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

<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>
	<table border="1">
		<tr>
					<td class="colhead" align="center"><b>S.NO.</b></td>
					<td class="colhead" align="center"><b>Distributor Login ID</b></td>
					<td class="colhead" align="center"><b>Distributor Name</b></td>
					<td class="colhead" align="center"><b>TSM Name</b></td>
					<td class="colhead" align="center"><b>Circle</b></td>					
					<td class="colhead" align="center"><b>Fse Name</b></td>
					<td class="colhead" align="center"><b>Retailer Name</b></td>
					<td class="colhead" align="center"><b>STB Type</b></td>
					<td class="colhead" align="center"><b>STB Serial No</b></td>
					<td class="colhead" align="center"><b>STB Status</b></td>
					<td class="colhead" align="center"><b>PR NO.</b></td>
					<td class="colhead" align="center"><b>PO NO.</b></td>
					<td class="colhead" align="center"><b>Stock Acceptance Date</b></td>
					<td class="colhead" align="center"><b>Activation Date</b></td>
					<td class="colhead" align="center"><b>Customer ID</b></td>								
					
		</tr>
		
		<logic:notEmpty name="ActivationDetailReportFormBean" property="reportStockDataList">
 		<logic:iterate id="reportData" name="ActivationDetailReportFormBean" property="reportStockDataList" indexId="m">
				<tr>					
					<td align="center"><%=(m.intValue()+1)%></td>
					<td align="center"><bean:write name="reportData" property="distLoginId"/></td>
					<td align="center"><bean:write name="reportData" property="distName"/></td>
					<td align="center"><bean:write name="reportData" property="tsmName"/></td>
					<td align="center"><bean:write name="reportData" property="circleName"/></td>
					<td align="center"><bean:write name="reportData" property="fseName"/></td>
					<td align="center"><bean:write name="reportData" property="retName"/></td>
					<td align="center"><bean:write name="reportData" property="stbType"/></td>
					<td align="center"><bean:write name="reportData" property="serial"/></td>
					<td align="center"><bean:write name="reportData" property="status"/></td>		
					<td align="center"><bean:write name="reportData" property="prNO"/></td>
					<td align="center"><bean:write name="reportData" property="poNO"/></td>		
					<td align="center"><bean:write name="reportData" property="stockAcceptanceDate"/></td>		
					<td align="center"><bean:write name="reportData" property="activationDate"/></td>
					<td align="center"><bean:write name="reportData" property="customerId"/></td>					
				<br>
			</tr>
			</logic:iterate>
			</logic:notEmpty>
		
 	</table>
 
<P><BR>
</P>
</BODY>
</html:html>
 <%}
 catch(Exception e){e.printStackTrace();}%>