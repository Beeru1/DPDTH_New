<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>
<HEAD>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<LINK href="<%=request.getContextPath()%>/theme/text.css" rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/theme/chromestyle2.css" />
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/chrome.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/JScriptLib.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>

<SCRIPT language="Javascript" type="text/javascript">
	function setBack(){
	   document.getElementById("methodName").value="getUserList";
	}
</SCRIPT>
</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">
	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include
			page="../common/dpheader.jsp" flush="" /></TD>
	</TR>
	<TR valign="top">
		<TD width="167" align="left" bgcolor="b4d2e7" valign="top"
			height="100%"><jsp:include page="../common/leftHeader.jsp" flush="" /></TD>
		<TD valign="top" width="100%" height="100%"><html:form
			name="UserFormBean" action="/UserAction"
			type="com.ibm.virtualization.recharge.beans.UserFormBean"
			scope="request">
			<html:hidden property="methodName" styleId="methodName" />
			<html:hidden property="loginId" styleId="loginId" />
			<html:hidden property="password" styleId="password" />
			<html:hidden property="type" styleId="type" />
			<html:hidden property="circleId" styleId="circleId" />
			<html:hidden property="status" styleId="status" />
			<html:hidden property="selectedCircleId" styleId="selectedCircleId" />
			<html:hidden property="selectedStatus" styleId="selectedStatus" />
			<html:hidden property="page" styleId="page" />
			
			
			<logic:notMatch name="UserFormBean" property="userAuthStatus"
				value="A">
				<html:hidden property="groupId" styleId="groupId" />

			</logic:notMatch>

			<TABLE width="545" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					
						<IMG src="<%=request.getContextPath()%>/images/viewUserDetails.gif" width="505" height="22" alt="">
					</TD>
				</TR>
				<TR>
					<TD colspan="4" class="text"><FONT color="#ff0000" size="-2"><html:errors
						bundle="error" /><html:errors /></FONT></TD>
				</TR>
				<TR>
					<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message key="user.username"
						bundle="view" /></FONT>:</STRONG></TD>
					<TD width="163"><bean:write
						name="UserFormBean" property="loginName" /></TD>
					<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message key="user.emailid"
						bundle="view" /></FONT>:</STRONG></TD>
					<TD width="163"><bean:write
						name="UserFormBean" property="emailId" />
					</TD>
				</TR>
				<TR>
					<!-- if user authorized to change or update the group then display the group -->
					<logic:match name="UserFormBean" property="userAuthStatus"
						value="A">
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message key="user.groupid"
							bundle="view" /></FONT>:</STRONG></TD>
						<TD width="163"><logic:iterate
							id="group" name="groupList">
							<logic:equal name="UserFormBean" property="groupId"
								value="${group.groupId}">
								<bean:write name="group" property="groupName" />
							</logic:equal>
						</logic:iterate> </TD>
					</logic:match>

					<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message key="user.circleid"
						bundle="view" /></FONT>:</STRONG></TD>
					<TD width="171"> <logic:iterate
						id="group" name="circleList">
						<logic:equal name="UserFormBean" property="circleId"
							value="${group.circleId}">
							<bean:write name="group" property="circleName" />
						</logic:equal>
					</logic:iterate> </TD>
				</TR>
				<TR>
					<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message key="user.contactno"
						bundle="view" /></FONT>:</STRONG></TD>
					<TD width="163"> <bean:write
						name="UserFormBean" property="contactNumber" /></TD>
					<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="user.status" /></FONT>:</STRONG></TD>
					<TD width="171"><logic:equal
						value="A" name="UserFormBean" property="status">
						<bean:message key="application.option.active" bundle="view" />
					</logic:equal> <logic:equal value="D" name="UserFormBean" property="status">
						<bean:message key="application.option.inactive" bundle="view" />
					</logic:equal></TD>
				</TR>
				<TR>
				<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message key="user.createddate"
						bundle="view" /></FONT>:</STRONG></TD>
					<TD width="163"> <bean:write
						name="UserFormBean" property="createdDt" formatKey="formatDate" /></TD>
				</TR>
				
				<TR>
					<html:hidden property="startDt" styleId="startDt"
						name="UserFormBean" />
					<html:hidden property="endDt" styleId="endDt" name="UserFormBean" />
					<TD class="text pLeft15"><FONT color="#000000">&nbsp;</FONT></TD>
					<TD colspan="2">
					<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
						<TR align="center" valign="top">
							<TD></TD>
							<TD width="70"><INPUT class="submit" type="submit"
								tabindex="7" name="Submit2" value="Back" onclick="setBack();"></TD>
						</TR>
					</TABLE>
					</TD>
				</TR>
			</TABLE>

		</html:form></TD>
	</TR>
	<TR align="center" bgcolor="4477bb" valign="top">
		<TD colspan="2" height="17" align="center"><jsp:include
			page="../common/footer.jsp" flush="" /></TD>
	</TR>
</TABLE>

</BODY>
</html:html>
