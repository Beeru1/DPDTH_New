<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>
<head>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<link rel="stylesheet" href="<%=request.getContextPath()%>/theme/Master.css" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">

<script type="text/javascript">
	function goBack(){
		document.getElementById("methodName").value="getGroupList";
	}

</script>
</head>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%" height="100%" valign="top">
	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include
			page="../common/dpheader.jsp" flush="" /></TD>
	</TR>
	<TR valign="top">
		<TD width="167" align="left" bgcolor="b4d2e7" valign="top"
			height="100%"><jsp:include page="../common/leftHeader.jsp" flush="" /></TD>
		<TD valign="top" width="100%" height="100%"><html:form
			name="GroupFormBean" action="/GroupAction"
			type="com.ibm.virtualization.recharge.beans.GroupFormBean">
					<html:hidden property="methodName" styleId="methodName" />
			<html:hidden property="groupId" styleId="groupId" />

			<TABLE width="545" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/viewGroup.gif" width="505" height="22" alt=""></TD>
				</TR>
				<TR>
					<TD colspan="4" class="text"><FONT color="#ff0000" size="-2"><html:errors
						bundle="error" /><html:errors /></FONT></TD>
				</TR>
				<TR>
					<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message key="group.name"
						bundle="view" /></FONT>:</STRONG></TD>
					<TD width="155"><bean:write property="groupName"  name="GroupFormBean"/></TD>
					<TD width="121" class="text">&nbsp;</TD>
					<TD width="171">&nbsp;</TD>
				</TR>

				<TR>
					<TD width="126" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message key="group.type"
						bundle="view" /></FONT> :</STRONG></TD>

					<TD width="155"><logic:equal
						value="I" name="GroupFormBean" property="type">
						<bean:message key="group.internal" bundle="view" />
					</logic:equal> <logic:equal value="E" name="GroupFormBean" property="type">
						<bean:message key="group.external" bundle="view" />
					</logic:equal>
										
					<FONT color="#ff0000" size="-2"></FONT></TD>
				</TR>
				<TR>
					<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message key="group.description"
						bundle="view" /></FONT> :</STRONG></TD>
					<TD width="163"><bean:write property="description" name="GroupFormBean" /></TD>
					<TD width="121" class="text">&nbsp;</TD>
					<TD width="171">&nbsp;</TD>
				</TR>
				<TR>
					<TD class="text pLeft15"><FONT color="#000000">&nbsp;</FONT></TD>
					<TD colspan="2">
					<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
						<TR align="center" valign="top">
							<td><INPUT class="submit" type="submit" name="BACK" value="Back" onclick="goBack();"></td>
						</TR>
					</TABLE>
					</TD>
				</TR>
				<TR>
					<TD colspan="4" align="center">&nbsp;</TD>
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
