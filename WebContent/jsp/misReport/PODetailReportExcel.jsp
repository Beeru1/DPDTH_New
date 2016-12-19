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
	response.setHeader("content-disposition","attachment;filename=PODetailReport.xls");
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
					<td class="colhead" align="center"><b>S.NO.</b></td>
					<td class="colhead" align="center"><b>Distributor Login ID</b></td>
					<td class="colhead" align="center"><b>Distributor Name</b></td>
					<td class="colhead" align="center"><b>TSM Name</b></td>
					<td class="colhead" align="center"><b>Circle</b></td>
					<td class="colhead" align="center"><b>PR No.</b></td>
					<td class="colhead" align="center"><b>PR Date</b></td>
					<td class="colhead" align="center"><b>PO No.</b></td>
					<td class="colhead" align="center"><b>PO Date</b></td>
					<td class="colhead" align="center"><b>DC No.</b></td>
					<td class="colhead" align="center"><b>DC Date</b></td>
					<td class="colhead" align="center"><b>PO Status</b></td>
					<td class="colhead" align="center"><b>STB Type</b></td>
					<td class="colhead" align="center"><b>STB Serial No</b></td>
					<td class="colhead" align="center"><b>Status</b></td>
					<td class="colhead" align="center"><b>Last PO Action Date</b></td>
					
		</tr>
		<logic:notEmpty name="PODetailReportBean" property="reportList">
 		<logic:iterate id="reportData" name="PODetailReportBean" property="reportList" indexId="m">
				<tr>
				
					<td align="center"><%=(m.intValue()+1)%></td>
					<td align="center"><bean:write name="reportData" property="distLoginId"/></td>
					<td align="center"><bean:write name="reportData" property="distName"/></td>
					<td align="center"><bean:write name="reportData" property="tsmName"/></td>
					<td align="center"><bean:write name="reportData" property="circleName"/></td>
					<td align="center"><bean:write name="reportData" property="prNo"/></td>
					<td align="center"><bean:write name="reportData" property="prDate"/></td>
					<td align="center"><bean:write name="reportData" property="poNo"/></td>
					<td align="center"><bean:write name="reportData" property="poDate"/></td>
					<td align="center"><bean:write name="reportData" property="dcNo"/></td>
					<td align="center"><bean:write name="reportData" property="dcDate"/></td>
					<td align="center"><bean:write name="reportData" property="poStatusReport"/></td>
					<td align="center"><bean:write name="reportData" property="stbType"/></td>
					<td align="center"><bean:write name="reportData" property="stbSerialNo"/></td>
					<td align="center"><bean:write name="reportData" property="latestStatus"/></td>
					<td align="center"><bean:write name="reportData" property="lastPoActionDate"/></td>
					
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