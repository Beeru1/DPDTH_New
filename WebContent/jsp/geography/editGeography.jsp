<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

function validateForm()
{
	// Validate Geography Code
		var GeographyCode=document.forms[0].geographyCode;
		if ((GeographyCode.value==null)||(GeographyCode.value=="")){
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
		
		// Validate Geography Name
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
		document.getElementById("methodName").value="editGeography";
		document.getElementById("level").value='<bean:write name="GeographyFormBean" property="level"/>';
}
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

	
	function checkKeyPressed(){
		if(window.event.keyCode=="13"){
			return validateForm();
		}
	}
	function goBack(){
		 document.getElementById("methodName").value="getGeographyList";
		}
	function checkIsGeographyAdmin(){
		var  userType = document.getElementById("isGeographyAdmin").value;
		if(userType == "Y"){
			document.getElementById("geographyCode").readOnly = "readonly";
			document.getElementById("geographyName").readOnly = "readonly";
			document.getElementById("parentId").disabled = true;
		}
	}
</SCRIPT>

</HEAD>
<BODY background="${pageContext.request.contextPath}/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onload="checkIsGeographyAdmin();" onkeypress="return checkKeyPressed();">

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
		<html:form 	name="GeographyAction" action="/GeographyAction" type="com.ibm.dp.beans.GeographyFormBean" 	focus="geographyName">
			<html:hidden property="methodName" styleId="methodName" name="GeographyFormBean" />
			<html:hidden property="level" name="GeographyFormBean"/>
			<html:hidden property="geographyStatus" styleId="geographyStatus" name="GeographyFormBean"/>
			<html:hidden property="geographyId" styleId="geographyId" name="GeographyFormBean" />
			<html:hidden property="isGeographyAdmin" styleId="isGeographyAdmin"
				name="GeographyFormBean" />
			<html:hidden property="disabledRegion" styleId="disabledRegion" name="GeographyFormBean" />
			<html:hidden property="page" styleId="page" name="GeographyFormBean" />
			<TABLE width="545" border="0" cellpadding="5" cellspacing="0"
				class="text">
				
				<TR>
				<TD colspan="4" class="text"><BR>
					<logic:equal value="0" property="level" name="GeographyFormBean">
					<IMG src="${pageContext.request.contextPath}/images/editcountry.gif" width="505" height="22" alt="">
					</logic:equal>
					<logic:equal value="1" property="level" name="GeographyFormBean">
					<H1>Edit HUB</H1>
					</logic:equal>
					<logic:equal value="2" property="level" name="GeographyFormBean">
					<IMG src="${pageContext.request.contextPath}/images/editCircle.gif" width="505" height="22" alt="">
					</logic:equal>
					<logic:equal value="3" property="level" name="GeographyFormBean">					
					<IMG src="${pageContext.request.contextPath}/images/editZone.gif" width="505" height="22" alt="">
					</logic:equal>
					<logic:equal value="4" property="level" name="GeographyFormBean">
					<IMG src="${pageContext.request.contextPath}/images/editCityTown.gif" width="505" height="22" alt="">
					</logic:equal>	
					</TD>	
					</TR>
					<Tr>		
					<TD colspan="4" class="text"><FONT color="#ff0000"><STRONG><html:errors
						bundle="error" /><html:errors /></STRONG></FONT></TD>
				</TR>
				<TR>
					<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="dp"
						key="geography.geographycode" />:</FONT></STRONG></TD>
					<TD width="155"><FONT color="#003399"> <html:text
						property="geographyCode" styleClass="box" styleId="geographyCode"
						size="19" tabindex="1" maxlength="64" name="GeographyFormBean"
						readonly="true" /> </FONT></TD>
					<TD width="120" class="text pLeft15" nowrap></TD>
					<TD width="163"></TD>
				</TR>
				<TR>
					<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="dp"
						key="geography.geographyname" /></FONT><FONT color="RED">*</FONT>:</STRONG></TD>
					<TD width="155"><FONT color="#003399"> <html:text
						property="geographyName" styleClass="box" styleId="geographyName"
						size="19" maxlength="64" name="GeographyFormBean" tabindex="2" /> </FONT><FONT
						color="#ff0000" size="-2"></FONT></TD>

					<TD class="text"><STRONG><FONT color="#000000"><bean:message
						bundle="dp" key="geography.regionid" /></FONT><FONT color="RED">*</FONT>
					:</STRONG></TD>
					<TD width="163"><FONT color="#003399"> <html:select property="parentId" name="GeographyFormBean" tabindex="3" 	styleClass="w130">
						<html:option value="-1">
							<bean:message key="application.option.select" bundle="view" />
						</html:option>
						<logic:notEmpty property="regionList" name="GeographyFormBean">
							<bean:define id="i" name="GeographyFormBean" property="regionList" />
							<html:options collection="i" labelProperty="geographyName" property="geographyId" />
						</logic:notEmpty>								
					</html:select> </FONT></TD>
				</TR>
				
				<TR>
					<TD><STRONG><FONT color="#000000"><bean:message bundle="dp" key="geography.status" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
					<TD width="171"><FONT color="#3C3C3C"> 
					<html:select property="status" name="GeographyFormBean" styleClass="w130" tabindex="7">
						<html:option value="A">
							<bean:message key="application.option.active" bundle="view" />
						</html:option>
						<html:option value="D">
							<bean:message key="application.option.inactive" bundle="view" />
						</html:option>
					</html:select> </FONT></TD>
				</TR>		
				<TR>
					<TD class="text pLeft15"><FONT color="#000000">&nbsp;</FONT></TD>
					<TD colspan="2">
					<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
						<TR align="center" valign="top">
							<TD><INPUT class="submit" type="submit" tabindex="9" 
								name="Submit" value="Submit" onclick="return validateForm();"></TD>
							<TD><INPUT class="submit" type="submit" tabindex="10"
								name="BACK" value="Back" onclick="goBack()"></TD>
							<TD></TD>
						</TR>
					</TABLE>
					</TD>
				</TR>
				<TR>
					<TD colspan="4" align="center">&nbsp; <html:hidden
						property="startDate" name="GeographyFormBean" /> <html:hidden
						property="endDate" name="GeographyFormBean" />
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
