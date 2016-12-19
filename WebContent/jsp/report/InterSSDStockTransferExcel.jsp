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
	response.setHeader("content-disposition","attachment;filename=InterSSDStockTransferReport.xls");
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
					<td rowspan="2"  class="colhead" align="center"><b>S.NO.</b></td>	
					<td colspan="3" class="colhead" align="center"><b>From Distributor</b></td>
					<td colspan="3" class="colhead" align="center"><b>To Distributor</b></td>
					
					<td  rowspan="2" class="colhead" align="center"><b>ZBM Name</b></td>
					<td  rowspan="2" class="colhead" align="center"><b>Circle</b></td>
					<td rowspan="2" class="colhead" align="center"><b>DC No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Serial No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Initiation Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Transfer Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Acceptance Date</b></td>
					
		</tr>
		<tr>
					<td class="colhead" align="center"><b>Distributor Login ID</b></td>
					<td class="colhead" align="center"><b>Distributor Name</b></td>
					<td class="colhead" align="center"><b>TSM Name</b></td>
					<td class="colhead" align="center"><b>Distributor Login ID</b></td>
					<td class="colhead" align="center"><b>Distributor Name</b></td>
					<td class="colhead" align="center"><b>TSM Name</b></td>
		</tr>
		<logic:notEmpty name="DPInterSSDStockTransferReportFormBean" property="reportList">
 		<logic:iterate id="reportData" name="DPInterSSDStockTransferReportFormBean" property="reportList" indexId="m">
				<tr>
				
					<td align="center"><%=(m.intValue()+1)%></td>
					<td align="center"><bean:write name="reportData" property="distLoginId"/></td>
					<td align="center"><bean:write name="reportData" property="distName"/></td>
					<td align="center"><bean:write name="reportData" property="tsmName"/></td>
					<td align="center"><bean:write name="reportData" property="toDistLoginId"/></td>
					<td align="center"><bean:write name="reportData" property="toDistName"/></td>
					<td align="center"><bean:write name="reportData" property="toTsmName"/></td>
					<td align="center"><bean:write name="reportData" property="zbmName"/></td>
					<td align="center"><bean:write name="reportData" property="circleName"/></td>
					<td align="center"><bean:write name="reportData" property="dcNo"/></td>
					<td align="center"><bean:write name="reportData" property="stbType"/></td>
					<td align="center"><bean:write name="reportData" property="stbSerialNo"/></td>
					<td align="center"><bean:write name="reportData" property="initiationDate"/></td>
					<td align="center"><bean:write name="reportData" property="transferDate"/></td>
					<td align="center"><bean:write name="reportData" property="acceptanceDate"/></td>				
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