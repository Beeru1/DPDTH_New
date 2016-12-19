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
	response.setHeader("content-disposition","attachment;filename=AccountManagementActivityReport.xls");
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
	<logic:notEqual name="AccountManagementActivityReportBean" property="reportName" value="updateReport">
		<tr>
					<td class="colhead" align="center"><b>Login ID</b></td>
					<td class="colhead" align="center"><b>Account Type</b></td>
					<td class="colhead" align="center"><b>Name</b></td>
					<td class="colhead" align="center"><b>Lapu No.1</b></td>
					<td class="colhead" align="center"><b>Lapu No 2.</b></td>
					<td class="colhead" align="center"><b>FTA No.1</b></td>
					<td class="colhead" align="center"><b>FTA No 2.</b></td>
					<td class="colhead" align="center"><b>Parent Account Type</b></td>
					<td class="colhead" align="center"><b>Parent Account Name</b></td>															
					<td class="colhead" align="center"><b>Parent Login ID</b></td>
					<td class="colhead" align="center"><b>Circle</b></td>
					<td class="colhead" align="center"><b>City</b></td>
					<td class="colhead" align="center"><b>WareHouse Name</b></td>
					<td class="colhead" align="center"><b>Email ID</b></td>
					<td class="colhead" align="center"><b>Mobile No.</b></td>
					<td class="colhead" align="center"><b>Address</b></td>
					<td class="colhead" align="center"><b>Pin</b></td>
					<td class="colhead" align="center"><b>Zone</b></td>
					<td class="colhead" align="center"><b>BoTree Code</b></td>
					<td class="colhead" align="center"><b>Oracle Locator Code</b></td>
					<td class="colhead" align="center"><b>Status</b></td>
					<td class="colhead" align="center"><b>Account Created Date</b></td>
					<td class="colhead" align="center"><b>Account Created By</b></td>
					<td class="colhead" align="center"><b>Action Date</b></td>
					<td class="colhead" align="center"><b>Action By</b></td>
					<td class="colhead" align="center"><b>Last Login Date</b></td>
					<td class="colhead" align="center"><b>Last Password Change Date</b></td>
					<!-- <td class="colhead" align="center"><b>History Date</b></td> -->
					<td class="colhead" align="center"><b>SR No</b></td>
					<td class="colhead" align="center"><b>Approval 1</b></td>
					<td class="colhead" align="center"><b>Approval 2</b></td>
					<td class="colhead" align="center"><b>Remarks</b></td>
					
		</tr>
		</logic:notEqual>
		<!--<logic:notEqual name="AccountManagementActivityReportBean" property="reportName" value="updateReport">
		<tr>
					<td class="colhead" align="center"><b>Login ID</b></td>
					<td class="colhead" align="center"><b>Account Type</b></td>
					<td class="colhead" align="center"><b>Name</b></td>
					<td class="colhead" align="center"><b>Lapu No.1</b></td>
					<td class="colhead" align="center"><b>Lapu No 2.</b></td>
					<td class="colhead" align="center"><b>FTA No.1</b></td>
					<td class="colhead" align="center"><b>FTA No 2.</b></td>
					<td class="colhead" align="center"><b>Parent Account Type</b></td>
					<td class="colhead" align="center"><b>Parent Account Name</b></td>															
					<td class="colhead" align="center"><b>Parent Login ID</b></td>
					<td class="colhead" align="center"><b>Circle</b></td>
					<td class="colhead" align="center"><b>City</b></td>
					<td class="colhead" align="center"><b>WareHouse Name</b></td>
					<td class="colhead" align="center"><b>Email ID</b></td>
					<td class="colhead" align="center"><b>Mobile No.</b></td>
					<td class="colhead" align="center"><b>Address</b></td>
					<td class="colhead" align="center"><b>Pin</b></td>
					<td class="colhead" align="center"><b>Zone</b></td>
					<td class="colhead" align="center"><b>BoTree Code</b></td>
					<td class="colhead" align="center"><b>Oracle Locator Code</b></td>
					<td class="colhead" align="center"><b>Status</b></td>
					<td class="colhead" align="center"><b>Created Date</b></td>
					<td class="colhead" align="center"><b>Created By</b></td>
					<td class="colhead" align="center"><b>Last Updated Date</b></td>
					<td class="colhead" align="center"><b>Last Updated By</b></td>
					<td class="colhead" align="center"><b>Last Login Date</b></td>
					<td class="colhead" align="center"><b>Last Password Change Date</b></td>
					
		</tr>
		</logic:notEqual>-->
		
		<logic:notEmpty name="AccountManagementActivityReportBean" property="reportList">
		<logic:notEqual name="AccountManagementActivityReportBean" property="reportName" value="updateReport">
		<logic:iterate id="reportData" name="AccountManagementActivityReportBean" property="reportList" indexId="m">
				<tr>
					<td align="center"><bean:write name="reportData" property="distLoginId"/></td>
					<td align="center"><bean:write name="reportData" property="accountType"/></td>
					<td align="center"><bean:write name="reportData" property="accountName"/></td>
					<td align="center"><bean:write name="reportData" property="lapuNo"/></td>
					<td align="center"><bean:write name="reportData" property="lapuNo2"/></td>
					<td align="center"><bean:write name="reportData" property="ftaNo"/></td>
					<td align="center"><bean:write name="reportData" property="ftaNo2"/></td>
					<td align="center"><bean:write name="reportData" property="parentAccountType"/></td>
					<td align="center"><bean:write name="reportData" property="parentAccountName"/></td>															
					<td align="center"><bean:write name="reportData" property="parentLoginId"/></td>
					<td align="center"><bean:write name="reportData" property="circle"/></td>
					<td align="center"><bean:write name="reportData" property="city"/></td>
					<td align="center"><bean:write name="reportData" property="whName"/></td>
					<td align="center"><bean:write name="reportData" property="emailId"/></td>
					<td align="center"><bean:write name="reportData" property="mobileNo"/></td>
					<td align="center"><bean:write name="reportData" property="address"/></td>
					<td align="center"><bean:write name="reportData" property="pin"/></td>
					<td align="center"><bean:write name="reportData" property="zone"/></td>
					<td align="center"><bean:write name="reportData" property="botreeCode"/></td>
					<td align="center"><bean:write name="reportData" property="oracleLocatorCode"/></td>
					<td align="center"><bean:write name="reportData" property="status"/></td>
					<td align="center"><bean:write name="reportData" property="createdDate"/></td>
					<td align="center"><bean:write name="reportData" property="createdBy"/></td>
					<td align="center"><bean:write name="reportData" property="lastUpdatedDate"/></td>
					<td align="center"><bean:write name="reportData" property="lastUpdatedBy"/></td>
					<td align="center"><bean:write name="reportData" property="lastLoginDate"/></td>
					<td align="center"><bean:write name="reportData" property="passChangeDT"/></td>
					
					<!-- <td align="center"><bean:write name="reportData" property="hisDate"/></td> -->
					<td align="center"><bean:write name="reportData" property="srNo"/></td>
					<td align="center"><bean:write name="reportData" property="approval1"/></td>
					<td align="center"><bean:write name="reportData" property="approval2"/></td>
					<td align="center"><bean:write name="reportData" property="remarks"/></td>
				</tr>
			</logic:iterate>
			</logic:notEqual>
			</logic:notEmpty>
		
		
		
		<!--<logic:notEmpty name="AccountManagementActivityReportBean" property="reportList">
		<logic:notEqual name="AccountManagementActivityReportBean" property="reportName" value="updateReport">
		<logic:iterate id="reportData" name="AccountManagementActivityReportBean" property="reportList" indexId="m">
				<tr>
				
					
					<td align="center"><bean:write name="reportData" property="distLoginId"/></td>
					<td align="center"><bean:write name="reportData" property="accountType"/></td>
					<td align="center"><bean:write name="reportData" property="accountName"/></td>
					<td align="center"><bean:write name="reportData" property="lapuNo"/></td>
					<td align="center"><bean:write name="reportData" property="lapuNo2"/></td>
					<td align="center"><bean:write name="reportData" property="ftaNo"/></td>
					<td align="center"><bean:write name="reportData" property="ftaNo2"/></td>
					<td align="center"><bean:write name="reportData" property="parentAccountType"/></td>
					<td align="center"><bean:write name="reportData" property="parentAccountName"/></td>															
					<td align="center"><bean:write name="reportData" property="parentLoginId"/></td>
					<td align="center"><bean:write name="reportData" property="circle"/></td>
					<td align="center"><bean:write name="reportData" property="city"/></td>
					<td align="center"><bean:write name="reportData" property="whName"/></td>
					<td align="center"><bean:write name="reportData" property="emailId"/></td>
					<td align="center"><bean:write name="reportData" property="mobileNo"/></td>
					<td align="center"><bean:write name="reportData" property="address"/></td>
					<td align="center"><bean:write name="reportData" property="pin"/></td>
					<td align="center"><bean:write name="reportData" property="zone"/></td>
					<td align="center"><bean:write name="reportData" property="botreeCode"/></td>
					<td align="center"><bean:write name="reportData" property="oracleLocatorCode"/></td>
					<td align="center"><bean:write name="reportData" property="status"/></td>
					<td align="center"><bean:write name="reportData" property="createdDate"/></td>
					<td align="center"><bean:write name="reportData" property="createdBy"/></td>
					<td align="center"><bean:write name="reportData" property="lastUpdatedDate"/></td>
					<td align="center"><bean:write name="reportData" property="lastUpdatedBy"/></td>
					<td align="center"><bean:write name="reportData" property="lastLoginDate"/></td>
					<td align="center"><bean:write name="reportData" property="passChangeDT"/></td>
				</tr>
				
			</logic:iterate>
			</logic:notEqual>
			</logic:notEmpty>-->
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
			
			session.setAttribute("AccountManagementActivity", "done");
    }
catch (Exception e) 
	{
   				System.out.println("Error: "+e);
    }
 %>