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
	response.setHeader("content-disposition","attachment;filename=AllWareHouseDistributorMapping.xls");
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
					
					<td class="colhead" align="center"><b>Circle Name</b></td>
					<td class="colhead" align="center"><b>WareHouse Code</b></td>
					<td class="colhead" align="center"><b>Warehouse Name</b></td>
					<td class="colhead" align="center"><b>Warehouse Address</b></td>
					
		</tr>
		
		<logic:notEmpty name="WHdistmappbulkBean" property="whDistMapList">
 		<logic:iterate id="reportData" name="WHdistmappbulkBean" property="whDistMapList">
				<tr>					
					
					<td align="center"><bean:write name="reportData" property="circleName"/></td>
					<td align="center"><bean:write name="reportData" property="wareHouseId"/></td>
					<td align="center"><bean:write name="reportData" property="wareName"/></td>
					<td align="center"><bean:write name="reportData" property="warehouseAddress"/></td>
						
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