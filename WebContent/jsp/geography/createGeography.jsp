<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>
<HEAD>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<LINK href="${pageContext.request.contextPath}/theme/text.css" rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/theme/chromestyle2.css" />
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/scripts/chrome.js"></SCRIPT>
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/scripts/JScriptLib.js"></SCRIPT>
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/scripts/validation.js"></SCRIPT>

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
		// Validate Circle Code
		var geographyCode=document.forms[0].geographyCode;
		if ((geographyCode.value==null)||(geographyCode.value=="")){
			alert('<bean:message bundle="error" key="errors.geography.geographycode_required" />');
			geographyCode.focus();
			return false;
		}
		// Validate Spaces
		if (isNull('document.forms[0]','geographyCode')){
			alert('<bean:message bundle="error" key="errors.geography.geographycode_required" />');
			geographyCode.value="";
			geographyCode.focus();
			return false;
		}
		// Validate geography Name
		var geographyName=document.forms[0].geographyName;
		if ((geographyName.value==null)||(geographyName.value=="")){
			alert('<bean:message bundle="error" key="errors.geography.geographyname_required" />');
			geographyName.focus();
			return false;
		}
		// Validate Spaces
		if (isNull('document.forms[0]','geographyName')){
			alert('<bean:message bundle="error" key="errors.geography.geographyname_required" />');
			geographyName.value="";
			geographyName.focus();
			return false;
		}
		// validate regionId
		var geographyRegion=document.forms[0].parentId;
		var level ='<bean:write name="GeographyFormBean" property="level"/>';
		
		if((level>0)&&(geographyRegion.value == "-1")){
			alert('<bean:message bundle="error" key="errors.geography.region_invalid" />');
			flag='error';
			geographyRegion.focus();
			return false;
		}
		document.getElementById("methodName").value="createGeography";
		document.getElementById("level").value='<bean:write name="GeographyFormBean" property="level"/>';
	}
	function checkKeyPressed(){
		if(window.event.keyCode=="13"){
			return validateForm();
		}
	}
</SCRIPT>
</HEAD>
<BODY background="${pageContext.request.contextPath}/images/bg_main.gif"  background="${pageContext.request.contextPath}/images/bg_main.gif" bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
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
		<TD valign="top" width="100%" height="100%">
		<html:form 	name="GeographyAction" action="/GeographyAction.do"
			type="com.ibm.dp.beans.GeographyFormBean"
			focus="geographyCode">
			<html:hidden property="methodName" styleId="methodName" />
			<html:hidden property="level" value="" name="GeographyFormBean"/>
			
			<TABLE width="545" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					<logic:equal value="0" property="level" name="GeographyFormBean">
					<IMG src="${pageContext.request.contextPath}/images/createCountry.gif" width="505" height="22" alt="">
					</logic:equal>
					<logic:equal value="1" property="level" name="GeographyFormBean">
					<H1>Create HUB</H1>
					</logic:equal>
					<logic:equal value="2" property="level" name="GeographyFormBean">
					<IMG src="${pageContext.request.contextPath}/images/createCircle.gif" width="505" height="22" alt="">
					</logic:equal>
					<logic:equal value="3" property="level" name="GeographyFormBean">
					<IMG src="${pageContext.request.contextPath}/images/createZone.gif" width="505" height="22" alt="">
					</logic:equal>
					<logic:equal value="4" property="level" name="GeographyFormBean">
					<IMG src="${pageContext.request.contextPath}/images/create-city-town.gif" width="505" height="22" alt="">
					</logic:equal>	
					</TD>										
				</TR>
				<TR>
					<TD colspan="4" class="text"><FONT color="#ff0000"><STRONG><html:errors
						bundle="error" /> <html:errors bundle="view" /></STRONG> </FONT></TD>
				</TR>
				<TR>
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000">Code<span class="orange8">*</span>:</FONT></STRONG></TD>
					<TD width="155"><FONT color="#003399"> <html:text
						property="geographyCode" styleClass="box" styleId="geographyCode"
						size="19" tabindex="1" maxlength="64" name="GeographyFormBean" /> </FONT></TD>
					<TD width="120" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000">Name<FONT color="RED">*</FONT>:</FONT></STRONG></TD>
					<TD width="163"> <html:text
						property="geographyName" styleClass="box" styleId="geographyName"
						size="19" tabindex="2" maxlength="64" name="GeographyFormBean" /> </TD>
				</TR>
				<TR>
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000">Select Parent</FONT></STRONG><FONT color="RED">*</FONT>:</TD>
					<TD width="155"><FONT color="#003399"><FONT color="#003399">
					 <html:select property="parentId" name="GeographyFormBean" tabindex="3" 	styleClass="w130">
						<html:option value="-1">
							<bean:message key="application.option.select" bundle="view" />
						</html:option>
						<logic:notEmpty property="regionList" name="GeographyFormBean">
							<bean:define id="i" name="GeographyFormBean" property="regionList" />
							<html:options collection="i" labelProperty="geographyName" property="geographyId" />
						</logic:notEmpty>								
					</html:select> </FONT></TD>

					<TD width="120" class="text pLeft15" nowrap>Status</TD>
					<TD width="163"><html:select property ="status" name ="GeographyFormBean" tabindex="4" 	styleClass="w130">
					<html:option value="A" >Active</html:option>
					<html:option value="D">De-Active</html:option>
					</html:select></TD>
				</TR>
				<TR>
					<TD class="text pLeft15" nowrap><FONT color="#000000">&nbsp;</FONT></TD>
					<TD colspan="2">
					<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
						<TR align="center" valign="top">
							<TD><INPUT class="submit" type="submit" tabindex="8"
								name="Submit" value="Submit" onclick="return validateForm();"></TD>
							<TD width="70"><INPUT class="submit" type="button" onclick="resetAll();" name="Submit2" value="Reset" tabindex="9"></TD>
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
