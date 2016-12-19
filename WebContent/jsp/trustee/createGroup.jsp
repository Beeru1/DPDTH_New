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

	// trim the spaces
	function trimAll(sString) {
		while (sString.substring(0,1) == ' '){
		sString = sString.substring(1, sString.length);
		}		
		while (sString.substring(sString.length-1, sString.length) == ' '){
		sString = sString.substring(0,sString.length-1);	
		}
		return sString;
	}

	function validateForm(){
		var flag='error';
		var groupName=document.forms[0].groupName;
		var type=document.forms[0].type;
		// validate groupName as not blank
		if ((groupName.value==null)||(groupName.value=="")){
			alert('<bean:message bundle="error" key="errors.group.groupname_required" />');
			groupName.focus();
			return false;
		}
		// validate groupName as Alpha-Numeric
		if (!(isAlphaNumeric(groupName.value))){
			alert('<bean:message bundle="error" key="errors.group.groupname_alphanumeric" />');
			groupName.focus();
			return false;
		}
	    if ((type.value==null)||(type.value=="0")){
			alert('<bean:message bundle="error" key="errors.group.type" />');
			type.focus();
			return false;
		}
		
		document.forms[0].description.value=trimAll(document.forms[0].description.value);
		
 		document.getElementById("methodName").value="createGroup";
	}
	function checkKeyPressed(){
		if(window.event.keyCode=="13"){
			return validateForm();
		}
		// Validate Description Length
		var groupDescription=document.forms[0].description;
		if(groupDescription.value.length > 200){
			alert('<bean:message bundle="error" key="errors.group.description_maxlimit" />');
			groupDescription.focus();
			return false;
		} 
	}

</SCRIPT>

</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onkeypress="return checkKeyPressed();">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">
	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include
			page="../common/dpheader.jsp" flush="" /></TD>
	</TR>
	<TR valign="top">
		<TD width="167" align="left" bgcolor="b4d2e7" valign="top"
			height="100%"><jsp:include page="../common/leftHeader.jsp" flush="" /></TD>
		<TD valign="top" width="100%" height="100%"><html:form	 action="/GroupAction"	focus="groupName">
			<html:hidden property="methodName" styleId="methodName" />
			<html:hidden property="groupId" styleId="groupId" />

			<TABLE width="545" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/createGroup.gif" width="505" height="22" alt=""></TD>
				</TR>
				<TR>
					<TD colspan="4" class="text"><FONT color="#ff0000" size="-2"><html:errors
						bundle="error" /><html:errors /><html:errors bundle="view" /> </FONT></TD>
				</TR>
				<TR>
					<TD width="126" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message key="group.name"
						bundle="view" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
					<TD width="155"><FONT color="#003399"> <html:text
						property="groupName" styleClass="box" styleId="groupName"
						size="30" maxlength="30" name="GroupFormBean" tabindex="1" /> </FONT></TD>
					<TD>&nbsp;</TD>
					<TD>&nbsp;</TD>
				</TR>
				<TR>
					<TD width="126" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message key="group.type"
						bundle="view" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>

					<TD width="155"><FONT color="#003399"> <html:select
						property="type" tabindex="2" styleClass="w130">
						<html:option value="0">
							<bean:message key="application.option.select" bundle="view" />
						</html:option>
						<html:option value="I">
							<bean:message key="group.internal" bundle="view" />
						</html:option>
						<html:option value="E">
							<bean:message key="group.external" bundle="view" />
						</html:option>

					</html:select> </FONT><FONT color="#ff0000" size="-2"></FONT></TD>
				</TR>

				<TR>
					<TD width="126" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message key="group.description"
						bundle="view" /></FONT>:</STRONG></TD>
					<TD width="163"><FONT color="#003399"> <html:textarea
						property="description" styleClass="box" cols="60" rows="3"
						styleId="description" name="GroupFormBean" tabindex="3" /> </FONT></TD>
					<TD>&nbsp;</TD>
					<TD>&nbsp;</TD>
				</TR>
				<TR>
					<TD class="text pLeft15"><FONT color="#000000">&nbsp;</FONT></TD>
					<TD colspan="2">
					<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
						<TR align="center" valign="top">
							<TD><INPUT class="submit" type="submit" tabindex="4"
								name="Submit" value="Submit" onclick="return validateForm();"></TD>
							<TD width="70"><INPUT class="submit" type="button" onclick="resetAll();"
										name="Submit2" value="Reset" tabindex="5"></TD>
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
