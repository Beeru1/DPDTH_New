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
			"attachment;filename=listUser.xls");
%>
<META name="GENERATOR" content="IBM Software Development Platform">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title><bean:message bundle="view" key="application.title" /></title>

</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">
<table width="725" align="center" class="mLeft5" border="1">
	<html:form action="UserAction" method="post">
		<logic:notEmpty name="UserFormBean" property="userList">

			<tr align="center" bgcolor="#104e8b">
				<td align="center" bgcolor="#cd0504"><span
					class="white10heading mLeft5 mTop5"><bean:message
					key="application.sno" bundle="view" /></span></td>
				<td align="center" bgcolor="#cd0504"><span
					class="white10heading mLeft5 mTop5"><bean:message
					key="user.username" bundle="view" /></span></td>
				<td align="center" bgcolor="#cd0504"><span
					class="white10heading mLeft5 mTop5"><bean:message
					key="user.groupid" bundle="view" /></span></td>
				<td align="center" bgcolor="#cd0504"><span
					class="white10heading mLeft5 mTop5"><bean:message
					key="user.circleid" bundle="view" /></span></td>
				<td align="center" bgcolor="#cd0504"><span
					class="white10heading mLeft5 mTop5"><bean:message
					key="user.contactno" bundle="view" /></span></td>
				<td align="center" bgcolor="#cd0504"><span
					class="white10heading mLeft5 mTop5"><bean:message
					key="user.emailid" bundle="view" /></span></td>
				<td align="center" bgcolor="#cd0504"><span
					class="white10heading mLeft5 mTop5"><bean:message
					key="user.status" bundle="view" /></span></td>
				<td align="center" bgcolor="#cd0504"><span
					class="white10heading mLeft5 mTop5"><bean:message
					bundle="view" key="user.createddate" /></span></td>
<!-- 
				<td align="center" bgcolor="#cd0504"><span
					class="white10heading mLeft5 mTop5"><bean:message
					bundle="view" key="user.createdby" /></span></td>
-->			
			</tr>
			
			<logic:iterate id="user" name="UserFormBean" property="userList"
				indexId="i">
				<tr align="center"
					bgcolor="#fce8e6">
					<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><c:out
						value="${i+1}"></c:out></span></td>
					<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write
						name="user" property="loginName" /></span></td>
					<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write
						name="user" property="groupName" /></span></td>
					<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write
						name="user" property="circleName" /></span></td>
					<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write
						name="user" property="contactNumber" /></span></td>
					<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write
						name="user" property="emailId" /></span></td>
					<td class="height19"><span class="mLeft5 mTop5 mBot5 black10">
					<c:if test='${user.status eq "A"}'>
						<bean:message bundle="view" key="application.option.active" />
					</c:if> <c:if test='${user.status eq "D"}'>
						<bean:message bundle="view" key="application.option.inactive" />
					</c:if> </span></td>
					<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write
						name="user" property="createdDt" formatKey="formatDate"/></span></td>
<!-- 
					<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write
						name="user" property="loginName" /></span></td>
-->						
					<html:hidden property="loginId" styleId="loginId" />
					<html:hidden property="loginId" styleId="createdBy" />
					<html:hidden property="methodName" styleId="methodName" />
				</tr>
				
			</logic:iterate>
		</logic:notEmpty>
	</html:form>
</table>
</body>
</html:html>
