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
		

	function validateForm()
	{
		// Validate Circle Name
		var beatName=document.forms[0].beatName;
		if ((beatName.value==null)||(beatName.value=="")){
			alert('<bean:message bundle="error" key="errors.beat.beatname" />');
			beatName.focus();
			return false;
		}
/*		if(document.getElementById("accountCityId").value == null || document.getElementById("accountCityId").value == "0"){
			alert('<bean:message bundle="error" key="errors.beat.cityname" />');
			document.getElementById("accountCityId").focus();
			return false;
		}	*/
	   // Validate Description Length
		var circleDescription=document.forms[0].description;
		if(circleDescription.value.length > 200){
			alert('<bean:message bundle="error" key="errors.circle.description_maxlimit" />');
			circleDescription.focus();
			return false;
		}
		
		document.forms[0].description.value=trimAll(document.forms[0].description.value);
		document.getElementById("methodName").value="updateBeat";
	}
	
	//Validity of Amount
	function isValidAmount(evt, source){
		evt =(evt)?evt :window.event;
		var charCode =(evt.which)?evt.which :evt.keyCode;
		var amt = source.value;
		var len = parseInt(amt.length);
		var ind = parseInt(amt.indexOf('.'));
		if(evt.keyCode == 13){
			return (evt.keyCode) ;
		}
		if(isNaN(amt)){
			evt.keyCode = 0;
			source.value = ".00";
		}
		if(charCode == 46){
			if(ind>-1)
			evt.keyCode = 0;
		}else if(charCode < 48 || charCode >57){
			evt.keyCode = 0;
		}else{
			if((len - ind)>4 && ind > -1)
			evt.keyCode = 0;
		}
	}
	function checkKeyPressed(){
		if(window.event.keyCode=="13"){
			return validateForm();
		}
	}
	function resetthis()
	{
		document.getElementById("beatName").value="";
		document.getElementById("description").value="";		
	}
</SCRIPT>
</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  background="<%=request.getContextPath()%>/images/bg_main.gif" bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
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
		<TD valign="top" width="100%" height="100%"><html:form
			name="createBeatAction" action="/createBeatAction"
			type="com.ibm.dp.beans.DPCreateBeatFormBean"
			focus="beatName">
			<html:hidden property="methodName" styleId="methodName" value="updateBeat"/>
			<TABLE width="545" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/editBeat.gif" width="505" height="22" alt=""></TD>
				</TR>
				<TR>
					<TD colspan="4" class="text"><FONT color="#ff0000" size="2"><html:errors
						bundle="error" /> <html:errors bundle="view" /> </FONT></TD>
				</TR>
				<TR>
				<html:hidden property="beatId" name="DPCreateBeatForm"/>
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="beat.beatname" /><FONT color="RED">*</FONT>:</FONT></STRONG></TD>
					<TD width="155"><FONT color="#003399"> <html:text
						property="beatName" styleClass="box" styleId="beatName"
						size="19" tabindex="1" maxlength="64" name="DPCreateBeatForm" onkeypress="alphaNumWithSpace()"/> </FONT><FONT
						color="#ff0000" size="-2"></FONT></TD>
					<td width="199" class="text pLeft15">
						<STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="beat.cityname" /><FONT color="RED">*</FONT>:</FONT></STRONG>
					</td>	
					<td width="155"><FONT color="#003399">
					<html:text property="accountCityName" name="DPCreateBeatForm" readonly="true"></html:text>
					</FONT>
					</td>	
				</tr>
				<tr>
					<TD width="126" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="beat.description" />:</FONT></STRONG></TD>
					<TD width="171" colspan="3"><FONT color="#3C3C3C"> <html:textarea
						property="description" cols="65" rows="3" tabindex="3"
						styleClass="box" name="DPCreateBeatForm" /> </FONT></TD>
				</TR>
				<tr>
				<TD width="120" class="text"><STRONG><FONT
					color="#000000"><bean:message bundle="view"
					key="account.status" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
				<TD width="163"><FONT color="#003399"> <html:select
					property="beatStatus" styleClass="w130" tabindex="20">
					<html:option value="A">
						<bean:message bundle="view" key="application.option.active" />
					</html:option>
					<html:option value="D">
						<bean:message bundle="view" key="application.option.inactive" />
					</html:option>
				</html:select> </FONT></TD>
				</tr>
				<TR>
					<TD class="text pLeft15" nowrap><FONT color="#000000">&nbsp;</FONT></TD>
					<TD colspan="2">
					<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
						<TR align="center" valign="top">
							<TD><INPUT class="submit" type="submit" tabindex="4"
								name="Submit" value="Submit" onclick="return validateForm();"></TD>
							<TD width="70"><INPUT class="submit" type="button" onclick="resetthis();"
										name="Submit2" value="Reset" tabindex="5"></TD>
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
