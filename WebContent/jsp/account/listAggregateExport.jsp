<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-Disposition",
			"attachment;filename=listAggregate.xls");
	response.setHeader("Cache-Control", "must-revalidate");
	response.setHeader( "Pragma", "public" );		
%>
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<title><bean:message bundle="view" key="application.title" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

</HEAD>


<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onkeypress="return checkKeyPressed();" onload="setSearchControlDisabled()">
	<table cellspacing="0" cellpadding="0" border="1" width="100%" height="100%" valign="top">
		
    <html:form action="initCreateAccount.do?methodName=downloadAggregateList">
    
			<tr>
			<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.CircleName" /></SPAN></TD>
			
			<TD class="text" align="center"><bean:write property="circleName" name="DPCreateAccountForm"/></TD>
			</tr>
			<logic:empty name="DPCreateAccountForm" property="aggList">
				<tr>
				<td colspan="3">
				<font color="red" size="2"><bean:message bundle="view" key="account.NoRecord"/> </font>
				</td>
				</tr>
				
				</logic:empty>
					<logic:notEmpty name="DPCreateAccountForm" property="aggList">
					<TR align="center" bgcolor="#CD0504" style="color:#ffffff !important;">
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.SNo" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.dist" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.lapuMobileNo" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.fse" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.lapuMobileNo" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.retailer" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.lapuMobileNo" /></SPAN>
						</TD>
					</TR>

						<logic:iterate id="account" name="DPCreateAccountForm"
							property="aggList" indexId="i">
							<tr bgcolor="#FCE8E6">
								<td class="height19"><span class="mTop5 mBot5 black10Bold"> <c:out
									value="${i+1}"></c:out></span></td>
								<TD class="text" align="center"><bean:write name="account"
								property="distName" /></TD>
							<TD class="text" align="center"><bean:write name="account"
								property="distLapuNo" /></TD>
							<TD class="text" align="center"><bean:write name="account"
								property="fseName" /></TD>
							
							<TD class="text" align="center"><bean:write name="account"
								property="fseLapuNo" /></TD>

							<TD class="text" align="center"><bean:write name="account"
								property="retailerName" /></TD>

							<TD class="text" align="center"><bean:write name="account" 
								property="retailerLapuNo" /></TD>
							</tr>
						</logic:iterate>
						</logic:notEmpty>


						</html:form>
					

					</table>
	
							
		
</BODY>
</html:html>
