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
		
	function submitForm(obj)
	{
			var frm = obj.form;
			if(document.getElementById('configValue').value == "")
			{
				document.getElementById("configValue").focus();
			  alert("Please Enter The Configuration Value..");
				return null;
			}
			if(document.getElementById('configDesc').value.length < 1)
			{
				document.getElementById("configDesc").focus();
			  alert("Please Enter the configuration description..");
				return null;
			}
			if(document.getElementById('configName').value == "")
			{
				document.getElementById("configName").focus();
			  alert("Please Enter the configuration name..");
				return null;
			}
			

		var  isEdit = document.getElementById("isEdit").value;
		if(isEdit == "Y")
		{
			frm.methodName.value = "updateConfigDetail";
			frm.submit();
		
		}
		else{		
			frm.methodName.value = "insertConfigDetail";
			frm.submit();
		}	}


</SCRIPT>
</HEAD>
<BODY BACKGROUND="images/bg_main.gif">
		<html:form  action="/ConfigurationMaster">	
			<html:hidden property="isEdit" styleId="isEdit" name="ConfigurationMasterBean" />
			
			
			<TABLE width="622" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
						<H1>Configuration</H1>
				</TD>
				</TR>
				<TR>
					<TD colspan="4" class="text"><FONT color="#ff0000"><STRONG><html:errors
						bundle="error" /> <html:errors bundle="view" /></STRONG> </FONT></TD>
				</TR>
				<TR> 
		
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000">Configuration Id<FONT color="RED">*</FONT>:</FONT></STRONG></TD>
					<TD width="155"><FONT color="#003399">
						<bean:message bundle="dp"
						key="configuration.configId" />
						<html:hidden
						property="configId" name="ConfigurationMasterBean" />
						</FONT></TD>


				</TR>
				<TR>	
					
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000">Configuration Name<FONT color="RED">*</FONT>:</FONT></STRONG></TD>
					<TD width="155"><FONT color="#003399"> <html:text
						property="configName" styleClass="box" styleId="configName"
						size="19" tabindex="1" maxlength="64"
						name="ConfigurationMasterBean" /> </FONT></TD>
							
				</TR>
				
				<TR>
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000">Configration Value</FONT></STRONG><FONT color="RED">*</FONT>:</TD>
					<TD width="155"><html:text property="configValue"
						styleClass="box" styleId="configValue" size="19" tabindex="1" 
						maxlength="64" name="ConfigurationMasterBean" /> </FONT></TD>
					
				</TR>
				
				<TR>
					<TD class="text pLeft15" nowrap width="185"><STRONG><FONT
						color="#000000">Configuration Description<FONT color="RED">*</FONT>:</FONT></STRONG></TD>
					<TD width="78"><html:textarea property="configDesc" styleClass="box"
						styleId="configDesc" tabindex="2" 
						name="ConfigurationMasterBean" /></TD>
									</TR>
				
				<TR>
					<TD class="text pLeft15" nowrap><FONT color="#000000">&nbsp;</FONT></TD>
					<TD colspan="2">
					<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
						<TR align="center" valign="top">
							<TD><INPUT class="submit" type="submit" tabindex="8"
								name="Submit" value="Submit" onclick="submitForm(this);"></TD>
							<TD width="70"></TD>
						</TR>
					</TABLE>
					</TD>
				</TR>

			</TABLE>
	<html:hidden property="methodName"/>

		</html:form>
</BODY>
</html:html>
