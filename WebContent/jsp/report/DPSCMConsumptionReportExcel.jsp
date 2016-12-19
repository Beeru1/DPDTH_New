<%try{%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import = "java.util.ArrayList,java.util.HashMap"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import = "java.util.*" %>
<html:html>
<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-disposition","attachment;filename=SCMConsumptionReport.xls");
	response.setHeader("Cache-Control", "must-revalidate");
	response.setHeader( "Pragma", "public" );
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<TITLE>DP Reports</TITLE>
</HEAD>																		
<BODY>

<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>
	<table border="1">
		<tr>
					<td class="colhead" align="center"><b>Batch_ID</b></td>
					<td class="colhead" align="center"><b>Source</b></td>
					<td class="colhead" align="center"><b>Process</b></td>
					<td class="colhead" align="center"><b>Login ID</b></td>
					<td class="colhead" align="center"><b>Company_Code</b></td>
					<td class="colhead" align="center"><b>Area</b></td>
					<td class="colhead" align="center"><b>Sub_Area</b></td>
					<td class="colhead" align="center"><b>Source_Type</b></td>
					<td class="colhead" align="center"><b>Product_Code</b></td>
					<td class="colhead" align="center"><b>Quantity</b></td>
					<td class="colhead" align="center"><b>Status</b></td>
					<td class="colhead" align="center"><b>Error Desciption</b></td>
					<td class="colhead" align="center"><b>Request_ID</b></td>
					<td class="colhead" align="center"><b>CREATED_BY</b></td>
					<td class="colhead" align="center"><b>LAST_UPDATED_BY</b></td>
					<td class="colhead" align="center"><b>CREATION_DATE</b></td>
					<td class="colhead" align="center"><b>LAST_UPDATE_DATE</b></td>
					
		</tr>
 		<logic:iterate id="reportData" name="DPReverseLogisticReportFormBean" property="dpscmReportList">
			<tr>
					<td align="center"><bean:write name="reportData" property="batchId"/></td>
					<td align="center"><bean:write name="reportData" property="source"/></td>
					<td align="center"><bean:write name="reportData" property="process"/></td>
					<td align="center"><bean:write name="reportData" property="loginId"/></td>
					<td align="center"><bean:write name="reportData" property="companyCode"/></td>
					<td align="center"><bean:write name="reportData" property="area"/></td>
					<td align="center"><bean:write name="reportData" property="subArea"/></td>
					<td align="center"><bean:write name="reportData" property="sourceType"/></td>
					<td align="center"><bean:write name="reportData" property="productCode"/></td>
					<td align="center"><bean:write name="reportData" property="quantity"/></td>
					<td align="center"><bean:write name="reportData" property="status"/></td>
					<td align="center"><bean:write name="reportData" property="errorDesc"/></td>
					<td align="center"><bean:write name="reportData" property="requestId"/></td>
					<td align="center"><bean:write name="reportData" property="createdBy"/></td>
					<td align="center"><bean:write name="reportData" property="lastUpdatedBy"/></td>
					<td align="center"><bean:write name="reportData" property="createdDate"/></td>
					<td align="center"><bean:write name="reportData" property="lastUpdatedDate"/></td>
			</tr>
			</logic:iterate>
 	</table>
 
<P><BR>
</P>
</BODY>
</html:html>
 <%}catch(Exception e){e.printStackTrace();}%>