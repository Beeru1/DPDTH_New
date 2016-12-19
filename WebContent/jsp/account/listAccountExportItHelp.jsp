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
	response.setHeader("content-disposition","attachment;filename=listAccount.xls");
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
		
    <html:form action="initCreateAccountItHelp" focus="circleName">
			

					<logic:notEmpty name="DPCreateAccountITHelpFormBean" property="displayDetails">
					
						<tr align="center" bgcolor="#104e8b">
								<td align="center" bgcolor="#CD0504"><span
									class="white10heading mLeft5 mTop5"><bean:message bundle="view"
									key="application.sno" /></span></td>
								<td align="center" bgcolor="#CD0504"><span
									class="white10heading mLeft5 mTop5"><bean:message bundle="view"
									key="account.accountCode" /></span></td>
								<td align="center" bgcolor="#CD0504"><span
									class="white10heading mLeft5 mTop5"><bean:message bundle="view"
									key="account.accountName" /></span></td>
								<td align="center" bgcolor="#CD0504"><span
									class="white10heading mLeft5 mTop5"><bean:message bundle="view"
									key="account.mobileNumber" /></span></td>
								<td align="center" bgcolor="#CD0504"><span
									class="white10heading mLeft5 mTop5"><bean:message bundle="view"
									key="account.simNumber" /></span></td>
								<td align="center" bgcolor="#CD0504"><span
									class="white10heading mLeft5 mTop5"><bean:message bundle="view"
									key="account.parentAccount" /></span></td>
								<TD align="center" bgcolor="#CD0504"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="account.parentAccountStatus" /></SPAN></TD>
								<td align="center" bgcolor="#CD0504"><span
									class="white10heading mLeft5 mTop5"><bean:message bundle="view"
									key="account.accountAddress" /></span></td>
								<td align="center" bgcolor="#CD0504"><span
									class="white10heading mLeft5 mTop5"><bean:message bundle="view"
									key="account.email" /></span></td>				
								<td align="center" bgcolor="#CD0504"><span
									class="white10heading mLeft5 mTop5"><bean:message bundle="view"
									key="account.category" /></span></td>									
								<td align="center" bgcolor="#CD0504"><span
									class="white10heading mLeft5 mTop5"><bean:message bundle="view"
									key="user.groupid" /></span></td>	
								<td align="center" bgcolor="#CD0504"><span
									class="white10heading mLeft5 mTop5"><bean:message bundle="view"
									key="account.circleId" /></span></td>
								<td align="center" bgcolor="#CD0504"><span
									class="white10heading mLeft5 mTop5"> <bean:message bundle="view"
									key="account.status" /></span></td>                             
								<td align="center" bgcolor="#CD0504"><span
									class="white10heading mLeft5 mTop5"><bean:message bundle="view"
									key="account.createddate" /></span></td>
								<td align="center" bgcolor="#CD0504"><span
									class="white10heading mLeft5 mTop5"><bean:message bundle="view"
									key="account.createdby" /></span></td>
		
						</tr>

						<logic:iterate id="account" name="DPCreateAccountITHelpFormBean"
							property="displayDetails" indexId="i">
							<tr bgcolor="#FCE8E6">
								<td class="height19"><span class="mTop5 mBot5 black10Bold"> <c:out
									value="${i+1}"></c:out></span></td>
								<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write name="account"
									property="accountCode" /></span></td>
								<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write name="account"
									property="accountName" /></span></td>
								<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write name="account"
									property="mobileNumber" /></span></td>
								<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write name="account"
									property="simNumber" /></span></td>
								<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write name="account"
									property="parentAccount" /></span></td>
								<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write name="account"
									property="parentAccountStatus" /></span></td>
								<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write name="account"
									property="accountAddress" /></span></td>			
								<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write name="account"
									property="email" /></span></td>	
								<td class="height19"><span class="mLeft5 mTop5 mBot5 black10">
									<logic:match name="account"	property="category" value="G">
	                               		<bean:message bundle="view"
									key="account.category.option_gold" />
								   </logic:match>
								   <logic:match name="account"	property="category" value="S">
	                               		<bean:message bundle="view"
									key="account.category.option_silver" />
								  </logic:match>		
								   <logic:match name="account"	property="category" value="P">
	                               		<bean:message bundle="view"
									key="account.category.option_platinum" />
								  </logic:match>	
										</span></td>	
								<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write name="account"
									property="groupName" /></span></td>									
								<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write name="account"
									property="circleName" /></span></td>	
								<td class="text" >
									<logic:match name="account"	property="status" value="A">
	                               		<bean:message bundle="view"
									key="application.option.active" />
								   </logic:match>
								   <logic:notMatch name="account"	property="status" value="A">
	                               		<bean:message bundle="view"
									key="application.option.inactive" />
								  </logic:notMatch>							   		
							    </td> 
								<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write name="account"
									property="createdDt" formatKey="formatDate"/></span></td>	
								<td class="height19"><span class="mLeft5 mTop5 mBot5 black10"><bean:write name="account"
									property="createdByName" /></span></td>	
						
							</tr>
						</logic:iterate>
						</logic:notEmpty>


						</html:form>
					

					</table>
	
							
		
</BODY>
</html:html>
