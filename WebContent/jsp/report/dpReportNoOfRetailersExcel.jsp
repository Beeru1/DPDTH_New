<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-Disposition",
			"attachment;filename=NoOfRetailerReport.xls");
%>
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<title><bean:message bundle="view" key="application.title" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

</HEAD>


<BODY background="${pageContext.request.contextPath}/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onkeypress="return checkKeyPressed();" onload="setSearchControlDisabled()">
<table>
<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/noOfRetailersReport.gif"
		width="505" height="22" alt="">
	</TD>
</tr>
</table>
	<table cellspacing="0" cellpadding="0" border="1" width="100%" height="100%" valign="top">
<% 
    String rowDark ="#FFE4E1";
    String rowLight = "#FFFFFF";
%>		
    <html:form action="reportAction">
					<logic:notEmpty name="DPReportForm" property="reportDataList">
					
						<tr align="center" bgcolor="#104e8b">
							<td align="center" bgcolor="#cd0504">
								<bean:message bundle="view"
								key="report.hub" /></td>
							<td align="center" bgcolor="#cd0504">
								<bean:message bundle="view"
								key="report.circle" /></td>
							<td align="center" bgcolor="#cd0504">
								<bean:message bundle="view"
								key="report.zone" /></td>
							<td align="center" bgcolor="#cd0504">
								<bean:message bundle="view"
								key="report.distributor" /></td>
							<td align="center" bgcolor="#cd0504">
								<bean:message bundle="view"
								key="report.productCat" /></td>		
							<td align="center" bgcolor="#cd0504">
								<bean:message bundle="view"
								key="report.productType" /></td>				
							<td align="center" bgcolor="#cd0504">
								<bean:message bundle="view"
								key="report.bucketName" /></td>									
							<td align="center" bgcolor="#cd0504">
								<bean:message bundle="view"
								key="report.noOfRetailers" /></td>	
					</tr>
						<logic:iterate id="report" name="DPReportForm"
							property="reportDataList" indexId="i">
						<logic:equal value="ZZZTOTAL" name="report" property="prodCat">	
							<tr bgcolor="gray">
								<td colspan="7" align="center"><b>Total</b></td>
								<td align="center"><bean:write name="report" property="noOfRetailers"/></td>
							</tr>
							</logic:equal>
						<logic:notEqual value="ZZZTOTAL" name="report" property="prodCat">	
						<TR>
								<td><bean:write name="report" property="hub" /></td>
								<td><bean:write name="report" property="circle" /></td>
								<td><bean:write name="report" property="zone" /></td>
								<td><bean:write name="report" property="distributorName" /></td>
								<td><bean:write name="report" property="prodCat" /></td>	
								<td><bean:write name="report" property="prodType" /></td>	
								<td><bean:write name="report" property="bucketName" /></td>									
								<td><bean:write name="report"
									property="noOfRetailers" /></td>
							</tr>
							</logic:notEqual>
						</logic:iterate>
						</logic:notEmpty>
						</html:form>
					</table>
</BODY>
</html:html>