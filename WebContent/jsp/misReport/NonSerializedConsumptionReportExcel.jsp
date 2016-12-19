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
	response.setHeader("content-disposition","attachment;filename=NonSerializedConsumptionReport.xls");
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
					<td class="colhead" align="center"><b>Oracle Locator Code</b></td>
					<td class="colhead" align="center"><b>Date</b></td>
					<td class="colhead" align="center"><b>Batch Id</b></td>
					<td class="colhead" align="center"><b>STB Type</b></td>
					<td class="colhead" align="center"><b>Item Code</b></td>
					<td class="colhead" align="center"><b>Quantity</b></td>
					<td class="colhead" align="center"><b>Installation Date</b></td>
					<td class="colhead" align="center"><b>Status</b></td>
					<td class="colhead" align="center"><b>Created By</b></td>
					<td class="colhead" align="center"><b>Last Update By</b></td>
					<td class="colhead" align="center"><b>Creation Date</b></td>
					<td class="colhead" align="center"><b>Last Update Date</b></td>
		</tr>
		<logic:notEmpty name="NonSerializedConsumptionReportBean" property="reportList">
 		<logic:iterate id="reportData" name="NonSerializedConsumptionReportBean" property="reportList" indexId="m">
				<tr>
				
					<td align="center"><%=(m.intValue()+1)%></td>
					<td align="center"><bean:write name="reportData" property="distId"/></td>
					<td align="center"><bean:write name="reportData" property="distName"/></td>
					<td align="center"><bean:write name="reportData" property="tsmName"/></td>
					<td align="center"><bean:write name="reportData" property="circleName"/></td>
					<td align="center"><bean:write name="reportData" property="oracleLocatorCode"/></td>
					<td align="center"><bean:write name="reportData" property="date"/></td>
					<td align="center"><bean:write name="reportData" property="batchId"/></td>
					<td align="center"><bean:write name="reportData" property="stbType"/></td>
					<td align="center"><bean:write name="reportData" property="itemcode"/></td>
					<td align="center"><bean:write name="reportData" property="quantity"/></td>
					<td align="center"><bean:write name="reportData" property="installationDate"/></td>
					<td align="center"><bean:write name="reportData" property="status"/></td>
					<td align="center"><bean:write name="reportData" property="createdBy"/></td>
					<td align="center"><bean:write name="reportData" property="lastUpdatedBy"/></td>
					<td align="center"><bean:write name="reportData" property="creationDate"/></td>
					<td align="center"><bean:write name="reportData" property="lastUpdateDate"/></td>
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
 <!-- Neetika BFR 16 16 Aug -->
  <%
try{
			
			session.setAttribute("NonSerializedConReport", "done");
    }
catch (Exception e) 
	{
   				System.out.println("Error: "+e);
    }
 %>