<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title><bean:message key="Forgot.title" bundle="view"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/theme/text.css" rel="stylesheet" type="text/css">
<script>
function Trim(sInString) 
   	{
   		sInString = sInString.replace( /^\s+/g, "" );// strip leading
   		return sInString.replace( /\s+$/g, "" );// strip trailing
   	}
 function trim(stringToTrim) {
		return stringToTrim.replace(/^\s+|\s+$/g,"");
	} 
function isBlank(fieldName)
{	
	
	if (Trim(fieldName)=='')
	{		
		return true;
	}
	else
	{			
	 	return false; 
	}
} 	
function validate()
{
	var uname=trim(document.getElementById("loginName").value);
	if(uname == null || uname == "")
	{ 
		alert("Please enter User name");
		return false;
	}
}
</script>
</head>
<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">
<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">

	<TR>
		<TD colspan="2" valign="top"><jsp:include
			page="/jsp/authentication/loginHeader.jsp" flush="" /></TD>
	</TR>
	<TR valign="top">
		<TD align="left" bgcolor="567FC0" valign="top"
			height="100%" width="168"><jsp:include page="/jsp/authentication/loginLeftHeader.jsp" flush="" /></TD>
<TD valign="top" width="100%" height="100%">
<html:form action="/forgotPassword.do">
	<table width="300" border="0" cellpadding="0" cellspacing="1" align="center">
		
		<tr>			<td colspan="2" align="center"><span class="heading"><bean:message
								key="forgotpassword.loginDetails" bundle="view"/></span></td>
						</tr>
						<tr>
			<td colspan="2" class="text" align="center"><br>
			<strong> <bean:write name="AuthenticationFormBean"
				property="message" /></strong></td>
		</tr>

						<tr>
							<td class="text pLeft15" width="25%"><font color="#000000"
								class="pLeft10"><strong><bean:message
								key="forgotpassword.loginId" bundle="view"/></strong></font></td>
							<td><font color="#003399"> <html:text
								name="AuthenticationFormBean" property="loginName" styleId="loginName" /></font></td>
						</tr>
						<tr>
			<td class="text pLeft15" colspan="2" align="CENTER" ><BR>
			<html:submit property="submit" value="Get Password" onclick="return validate();" />
			&nbsp;&nbsp;<html:button property="back" value="Back" onclick="javascript:history.back(-1);"/>
					</td>
				</tr>
					
	</table>
	</html:form>				
</td>
</TR>
<tr>
	<td align="center" colspan="3" ><jsp:include
			page="../common/footer.jsp" flush="" /></td>
</tr>
</table>

</body>
</html>
