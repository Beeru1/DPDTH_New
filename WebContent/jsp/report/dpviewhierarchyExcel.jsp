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
	response.setHeader("content-disposition","attachment;filename=ViewHierarchy.xls");
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
					<td class="colhead" align="center"><b>Name</b></td>
					<td class="colhead" align="center"><b>Oracle Code</b></td>
					<td class="colhead" align="center"><b>Lapu Number</b></td>
					<td class="colhead" align="center"><b>Parent Login Name</b></td>
					<td class="colhead" align="center"><b>Parent Name</b></td>
					<td class="colhead" align="center"><b>Circle</b></td>
					<td class="colhead" align="center"><b>Address</b></td>
					<td class="colhead" align="center"><b>City</b></td>
					<td class="colhead" align="center"><b>Pin Code</b></td>
					<td class="colhead" align="center"><b>BoTree Code</b></td>
					<td class="colhead" align="center"><b>Email Id</b></td>
					<td class="colhead" align="center"><b>Mobile Number</b></td>
				</tr>
	
			<logic:iterate id="reportData" name="DPViewHierarchyFormBean" property="getHierarchyList">
				<tr>
				<td align="center">
								<bean:write name="reportData" property="login_name"/>					
							</TD>
							<td align="center">
								<bean:write name="reportData" property="account_name"/>					
							</TD>
							<td align="center">
								<bean:write name="reportData" property="contact_name"/>					
							</TD>
							<td align="center">
								<bean:write name="reportData" property="oracle_code"/>					
							</TD>
							<td align="center">
								<bean:write name="reportData" property="lapu_number"/>					
							</TD>
							<td align="center">
								<bean:write name="reportData" property="parent_login_name"/>					
							</TD>
							<td align="center">
								<bean:write name="reportData" property="parent_name"/>					
							</TD>
							<td align="center">
								<bean:write name="reportData" property="circle_name"/>					
							</TD>
							<td align="center">
								<bean:write name="reportData" property="address"/>					
							</TD>
							<td align="center">
								<bean:write name="reportData" property="city_name"/>					
							</TD>
							<td align="center">
								<bean:write name="reportData" property="pin_code"/>					
							</TD>
							
							<td align="center">
								<bean:write name="reportData" property="botree_code"/>					
							</TD>
							<td align="center">
								<bean:write name="reportData" property="email_id"/>					
							</TD>
							<td align="center">
								<bean:write name="reportData" property="mobile_number"/>					
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