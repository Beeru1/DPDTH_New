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
	response.setHeader("content-disposition","attachment;filename=RecoSummaryReport.xls");
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
					<td class="colhead" align="center"><b>Distributor Code</b></td>					
					<td class="colhead" align="center"><b>Distributor Name</b></td>
					<td class="colhead" align="center"><b>Start Date</b></td>
					<td class="colhead" align="center"><b>End Date</b></td>
					<td class="colhead" align="center"><b>Product</b></td>
					<td class="colhead" align="center"><b>Status</b></td>				
		</tr>
		<logic:notEmpty name="RecoSummaryFormBean" property="recoSummaryList">
 		<logic:iterate id="reportData" name="RecoSummaryFormBean" property="recoSummaryList">
				<tr>
					<td align="center"><bean:write name="reportData" property="distCode"/></td>
					<td align="center"><bean:write name="reportData" property="distName"/></td>
					<td align="center"><bean:write name="reportData" property="recoStartDate"/></td>
					<td align="center"><bean:write name="reportData" property="recoEndDate"/></td>
					<td align="center"><bean:write name="reportData" property="productName"/></td>
					<td align="center"><bean:write name="reportData" property="recoStatus"/></td>
			</logic:iterate>
			</logic:notEmpty>
		
 	</table>
 
<P><BR>
</P>
</BODY>
</html:html>
 <%}
 catch(Exception e){e.printStackTrace();}%>
 <!-- Neetika BFR 16 on 16 Aug -->
  <%
try{
			
			session.setAttribute("recoSummaryReport", "done");
    }
catch (Exception e) 
	{
   				System.out.println("Error: "+e);
    }
 %>