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
	response.setHeader("content-disposition","attachment;filename=CollectionDetailReport.xls");
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
					<td class="colhead" align="center"><b>Collection Type</b></td>
					<td class="colhead" align="center"><b>STB Type</b></td>
					<td class="colhead" align="center"><b>Inventory Change Date</b></td>
					<td class="colhead" align="center"><b>Recovered STB</b></td>
					<td class="colhead" align="center"><b>Installed STB</b></td>
					<td class="colhead" align="center"><b>DC No</b></td>
					<td class="colhead" align="center"><b>Status</b></td>
					
		</tr>
		<logic:notEmpty name="CollectionReportBean" property="reportList">
 		<logic:iterate id="reportData" name="CollectionReportBean" property="reportList" indexId="m">
				<tr>
				
					<td align="center"><%=(m.intValue()+1)%></td>
					<td align="center"><bean:write name="reportData" property="distLoginId"/></td>
					<td align="center"><bean:write name="reportData" property="distName"/></td>
					<td align="center"><bean:write name="reportData" property="tsmName"/></td>
					<td align="center"><bean:write name="reportData" property="circleName"/></td>
					<td align="center"><bean:write name="reportData" property="collectionType"/></td>
					<td align="center"><bean:write name="reportData" property="stbType"/></td>
					<td align="center"><bean:write name="reportData" property="inventoryChangeDate"/></td>
					<td align="center"><bean:write name="reportData" property="recoveredSTB"/></td>
					<td align="center"><bean:write name="reportData" property="installedSTB"/></td>
					<td align="center"><bean:write name="reportData" property="dcNo"/></td>
					<td align="center"><bean:write name="reportData" property="status"/></td>
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