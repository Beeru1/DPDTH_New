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
			"attachment;filename=listCircle.xls");
%>
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<title><bean:message bundle="view" key="application.title" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">
<table border="1" cellpadding="5" cellspacing="0" class="text"
	width="100%">
	<html:form action="/CircleAction" name="CircleAction"
		type="com.ibm.virtualization.recharge.beans.CircleFormBean">
		<logic:notEmpty name="CircleFormBean" property="displayDetails">
			<tr align="center" bgcolor="#104e8b">
				<td align="center" rowspan="2" bgcolor="#CD0504"><span
					class="white10heading mLeft5 mTop5"><font color="white"><bean:message
					bundle="view" key="application.sno" /></font></span></td>
				<td align="center" rowspan="2" bgcolor="#CD0504"><span
					class="white10heading mLeft5 mTop5"><font color="white"><bean:message
					bundle="view" key="circle.circleid" /></font></span></td>
				<td align="center" rowspan="2" bgcolor="#CD0504"><span
					class="white10heading mLeft5 mTop5"><font color="white"><bean:message
					bundle="view" key="circle.circlename" /></font></span></td>
				<td align="center" rowspan="2" bgcolor="#CD0504"><span
					class="white10heading mLeft5 mTop5"><font color="white"><bean:message
					bundle="view" key="circle.description" /></font></span></td>
				<td align="center" rowspan="2" bgcolor="#CD0504"><span
					class="white10heading mLeft5 mTop5"><font color="white"><bean:message
					bundle="view" key="circle.regionname" /></font></span></td>
	<%-- 			<td align="center" bgcolor="#CD0504" colspan="3"><span
					class="white10heading mLeft5 mTop5"><font color="white"><bean:message
					bundle="view" key="circle.balance" /> (<bean:message bundle="view"
					key="application.currency" />)</font></span></td>
				<td align="center" rowspan="2" bgcolor="#CD0504"><span
					class="white10heading mLeft5 mTop5"><bean:message
					bundle="view" key="circle.rate" /></span></td>
				<td align="center" rowspan="2" bgcolor="#CD0504"><span
					class="white10heading mLeft5 mTop5"><font color="white"><bean:message
					bundle="view" key="circle.threshold" /></font></span></td>	--%>
				<td align="center" rowspan="2" bgcolor="#CD0504"><span
					class="white10heading mLeft5 mTop5"><font color="white"><bean:message
					bundle="view" key="user.createddate" /></font></span></td>
<!-- 
				<td align="center" rowspan="2" bgcolor="#CD0504"><span
					class="white10heading mLeft5 mTop5"><bean:message
					bundle="view" key="user.createdby" /></span></td>
-->				
				<td align="center" rowspan="2" bgcolor="#CD0504"><span
					class="white10heading mLeft5 mTop5"><font color="white"><bean:message
					bundle="view" key="circle.status" /></font></span></td>
			</tr>
	<%-- 		<tr align="center">
				<td align="center" bgcolor="#CD0504"><span
					class="white10heading mLeft5 mTop5"><font color="white"><bean:message
					bundle="view" key="circle.openingbalance" /></font></span></td>
				<td align="center" bgcolor="#CD0504"><span
					class="white10heading mLeft5 mTop5"><font color="white"><bean:message
					bundle="view" key="circle.availablebalance" /></font></span></td>
				<td align="center" bgcolor="#CD0504"><span
					class="white10heading mLeft5 mTop5"><font color="white"><bean:message
					bundle="view" key="circle.operatingbalance" /></font></span></td>
			</tr> --%>
			
			<logic:iterate id="circle" name="CircleFormBean"
				property="displayDetails" indexId="i">
				<c:if test='${circle.operatingBalance gt circle.threshold}'>
					<tr align="center"
						bgcolor="#FCE8E6">
						<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><c:out
							value="${i+1}"></c:out></span></td>
						<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write
							name="circle" property="circleId" /></span></td>
						<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write
							name="circle" property="circleName" /></span></td>
						<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write
							name="circle" property="description" /></span></td>
						<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write
							name="circle" property="regionName" /></span></td>
		<%-- 			<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write
							name="circle" format="#0.00" property="openingBalance" /></span></td>
						<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write
							name="circle" format="#0.00" property="availableBalance" /></span></td>
						<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write
							name="circle" format="#0.00" property="operatingBalance" /></span></td>
						<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write
							name="circle" format="#0.00" property="rate" /></span></td>
						<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write
							name="circle" format="#0.00" property="threshold" /></span></td>	--%>
						<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write
							name="circle" formatKey="formatDate" property="createdDt" /></span></td>
<!-- 
						<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write
							name="circle"  property="loginName" /></span></td>
-->						
						<c:if test='${circle.status eq "A"}'>
							<td class="height19"><span
								class="mLeft5 mTop5 mBot5 black10"><bean:message
								bundle="view" key="application.option.active" /></span></td>
						</c:if>
						<c:if test='${circle.status eq "D"}'>
							<td class="height19"><span
								class="mLeft5 mTop5 mBot5 black10"> <bean:message
								bundle="view" key="application.option.inactive" /></span></td>
						</c:if>
					</tr>
				</c:if>
				<c:if test='${circle.operatingBalance le circle.threshold}'>
					<tr align="center"
						bgcolor="#FCE8E6">
						<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><font
							color="black"><c:out value="${i+1}"></c:out></font></span></td>
						<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><font
							color="black"><bean:write name="circle"
							property="circleId" /></font></span></td>
						<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><font
							color="black"><bean:write name="circle"
							property="circleName" /></font></span></td>
						<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><font
							color="black"><bean:write name="circle" property="description" /></font></span></td>
						<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><font
							color="black"><bean:write name="circle"
							property="regionName" /></font></span></td>
			<%-- 		<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><font
							color="black"><bean:write name="circle" format="#0.00"
							property="openingBalance" /></font></span></td>
						<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><font
							color="black"><bean:write name="circle" format="#0.00"
							property="availableBalance" /></font></span></td>
						<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><font
							color="black"><bean:write name="circle" format="#0.00"
							property="operatingBalance" /></font></span></td>
						<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><font
							color="black"><bean:write name="circle" format="#0.00"
							property="rate" /></font></span></td>
						<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><font
							color="black"><bean:write name="circle" format="#0.00"
							property="threshold" /></font></span></td>	--%>
						<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><font
							color="black"><bean:write name="circle" formatKey="formatDate" property="createdDt" /></font></span></td>
							
<!-- 					
							<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write
							name="circle" format="#0.00" property="loginName" /></span></td>
 -->
						
							
						<c:if test='${circle.status eq "A"}'>
							<td class="height19"><span
								class="mLeft5 mTop5 mBot5 black10"><font color="black"><bean:message
								bundle="view" key="application.option.active" /></font></span></td>
						</c:if>
						<c:if test='${circle.status eq "D"}'>
							<td class="height19"><span
								class="mLeft5 mTop5 mBot5 black10"><font color="black"><bean:message
								bundle="view" key="application.option.inactive" /></font></span></td>
						</c:if>
					</tr>
				</c:if>
				
			</logic:iterate>
		</logic:notEmpty>
		<tr><td><html:hidden name="circle"  property="status" styleId="status" /></td></tr>
	</html:form>
</table>

</body>
</html:html>
