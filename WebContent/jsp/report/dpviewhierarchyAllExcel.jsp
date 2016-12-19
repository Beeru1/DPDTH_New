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
	response.setHeader("content-disposition","attachment;filename=ViewHierarchyAll.xls");
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
	
					<tr class="noScroll">
					<td class="colhead" align="center"><b>Login Name</b></td>
					<td class="colhead" align="center"><b>Account Name</b></td>
					<td class="colhead" align="center"><b>Lapu Mobile No.</b></td>
					<td class="colhead" align="center"><b>FSE</b></td>
					<td class="colhead" align="center"><b>Lapu Mobile No.</b></td>
					<td class="colhead" align="center"><b>Retailer</b></td>
					<td class="colhead" align="center"><b>Lapu Mobile No.</b></td>
				</tr>
	
			<logic:iterate id="reportData" name="DPViewHierarchyFormBean" property="hierarchyList">
				<tr>
				<td align="center">
								<bean:write name="reportData" property="login_name"/>					
							</TD>
							<td align="center">
								<bean:write name="reportData" property="account_name"/>					
							</TD>
							<td align="center">
								<bean:write name="reportData" property="lapu_number"/>					
							</TD>
							<td align="center">
								<bean:write name="reportData" property="fse"/>					
							</TD>
							<td align="center">
								<bean:write name="reportData" property="fse_lapu_number"/>					
							</TD>
							<td align="center">
								<bean:write name="reportData" property="retailer"/>					
							</TD>
							<td align="center">
								<bean:write name="reportData" property="retailer_lapu_number"/>					
							</TD>
			</tr>
			</logic:iterate>
 	</table>
 
<P><BR>
</P>
</BODY>
</html:html>
 <%}
 catch(Exception e){e.printStackTrace();}%>