<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<title><bean:message bundle="view" key="application.title" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/theme/text.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/theme/chromestyle2.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/chrome.js"></script>
<script language="javascript">

function editDetails(groupId){
		   var form = document.forms[0];
		   document.getElementById("methodName").value="getEditGroup";
	  	   document.getElementById("groupId").value=groupId; 
	       form.submit();
	} // function editDeatils ends
	
function viewDetails(groupId){
		   var form = document.forms[0];
	       document.getElementById("methodName").value="getViewGroup";
		   document.getElementById("groupId").value=groupId;
		   form.submit();
	} // function viewDeatils ends

</script>

</HEAD>


<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
	<html:form action="/GroupAction" name="GroupAction" type="com.ibm.virtualization.recharge.beans.GroupFormBean">
	<table cellspacing="0" cellpadding="0" border="0" width="100%" height="100%" valign="top">
		<tr>
			<td colspan="2" valign="top" width="100%"><jsp:include page="../common/dpheader.jsp" /></td>
		</tr>
		<tr valign="top">
			<td width="167" align="left" bgcolor="b4d2e7" valign="top" height="100%"><jsp:include page="../common/leftHeader.jsp" /></td>
			<td valign="top" width="100%" height="100%">
		<table width="700" border="0" cellspacing="0" cellpadding="0"
			class="text" align="left">
			<tr>
				<td width="20" align="left"><img src="<%=request.getContextPath()%>/images/spacer.gif" width="15"
					height="20"></td>
				<td width="680" valign="top">
				<table width="680" border="0" cellpadding="5" cellspacing="0"
					class="text">
					 <tr> 
                <td colspan="4" class="text"><br> <img src="<%=request.getContextPath()%>/images/viewEditGroup.gif" width="505" height="22"><br>
						<font color="#ff0000"><html:errors bundle="view" /> </font></td>
              </tr>
					<tr>
						<td colspan="4">
						<table width="725" align="center" class="mLeft5">
							<tr align="center" bgcolor="#104e8b">

								<td align="center" bgcolor="#cd0504"><span
									class="white10heading mLeft5 mTop5"><bean:message
									key="application.sno" bundle="view" /></span></td>
								<td align="center" bgcolor="#cd0504"><span
									class="white10heading mLeft5 mTop5"><bean:message
									key="group.name" bundle="view" /></span></td>
								<td align="center" bgcolor="#cd0504"><span
									class="white10heading mLeft5 mTop5"><bean:message
									key="group.type" bundle="view" /></span></td>
								<td align="center" bgcolor="#cd0504"><span
									class="white10heading mLeft5 mTop5"><bean:message
									key="group.description" bundle="view" /></span></td>
								<td align="center" bgcolor="#cd0504"><span
									class="white10heading mLeft5 mTop5"><bean:message
									key="application.edit" bundle="view" />&nbsp;|&nbsp;<bean:message
									bundle="view" key="application.view" /></span></td>
							</tr>
							
							<logic:iterate id="group" name="GroupFormBean"
								property="displayDetails" indexId="i">
								
								<tr align="center" bgcolor="#fce8e6">

								
									<td class=" height19" align="center"><span class="mLeft5 mTop5 mBot5 black10">
										<c:out value="${i+1}"></c:out></span></td>
									<td  class=" height19" align="left"><span class="mLeft5 mTop5 mBot5 black10"><bean:write name="group" property="groupName" /></span></td>
									<td  class=" height19" align="left"><span class="mLeft5 mTop5 mBot5 black10">
										<logic:equal value="I" name="group" property="type">
										<bean:message key="group.internal" bundle="view" />
										</logic:equal> <logic:equal value="E" name="group" property="type">
										<bean:message key="group.external" bundle="view" />
										</logic:equal>
									</span></td>
									<td  class=" height19" align="left"><span class="mLeft5 mTop5 mBot5 black10"><bean:write name="group" property="description" /></span></td>
									<html:hidden property="methodName" styleId="methodName" name="GroupFormBean" /> 
									<html:hidden property="groupName" styleId="groupName" name="GroupFormBean" />
									<html:hidden property="groupId" styleId="groupId" name="GroupFormBean" />
									<td class=" height19" nowrap align="center"><logic:equal value="Y" name="GroupFormBean" property="editStatus">
										<INPUT type="button" class="submit" value='Edit'
											   onclick="return editDetails('${group.groupId}')" />&nbsp;|
									</logic:equal> &nbsp;<INPUT type="button" class="submit" value='View'
											   onclick="return viewDetails('${group.groupId}')" />
									
									</td>
									
								</tr>
								
							</logic:iterate>



						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	</html:form>
	<tr align="center" bgcolor="4477bb" valign="top"> 
          <td colspan="2" height="17" align="center"><jsp:include page="../common/footer.jsp" /></td>
		</tr>
	</table>

</BODY>
</html:html>
