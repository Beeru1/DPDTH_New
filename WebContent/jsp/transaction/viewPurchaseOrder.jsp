
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html:html>

<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="theme/text.css" type="text/css">

<TITLE></TITLE>

</HEAD>

<BODY BACKGROUND="images/bg_main.gif" >
<SCRIPT language="JavaScript" type="text/javascript">


function formSubmit(employeeID)
{

document.forms[0].editEmployId.value= employeeID;
document.forms[0].methodName.value= "editEmpDetails";
document.forms[0].submit();

}


</SCRIPT>
<html:form action="/empDetail" >
	<html:hidden property="methodName" />
	<html:hidden property="editEmployId" />
	<table width="75%" class="mTop30" align="center" border="1">

		<tr align="center">
			<td colspan="9" bgcolor="#15317E" align="center"><FONT color="white" class="text" ><span class="heading"><H1></H1></span></td>
		</tr>
		
		
		<tr class="text white">
			<td bgcolor="s4100" align="center"><FONT color="white">Serial No.</FONT></td>
			<td bgcolor="s4100" align="center"><FONT color="white">Employee Id.</FONT></td>
			<td bgcolor="s4100" align="center"><FONT color="white">Employee Name</FONT></td>
			<td bgcolor="s4100" align="center"><FONT color="white">Employee Phone</FONT></td>
		</tr>

		<logic:empty name="empFormBean" property="userList">
			<TR class="lightBg">
				<TD class="text" align="center">EMPLOYEE DETAILS NOT FOUND</TD>
			</TR>
		</logic:empty>
		
		
		<logic:notEmpty name="empFormBean" property="userList">
			<logic:iterate name="empFormBean" id="empInfo" indexId="i"
				property="userList">
				
				<TR class="lightBg">
					<TD bgcolor="#6D7B8D" align="center"><FONT color="white" class="text" align="center"><%=(i.intValue() + 1)%>.</TD>
					<TD align="center" bgcolor="#6D7B8D" align="center"><FONT color="white" class="text"><bean:write name="empInfo"
						property="employeeID" /></TD>
						
					<TD align="center" bgcolor="#6D7B8D" align="center"><FONT color="white" class="text"><bean:write name="empInfo"
						property="firstName" /> <bean:write name="empInfo"
						property="middleName" /> <bean:write name="empInfo"
						property="lastName" /></TD>
						
					<TD align="center" bgcolor="#6D7B8D" align="center"><FONT color="white" class="text"><bean:write name="empInfo"
						property="phone" /></TD>
					
					<TD class="txt" align="center"><A
						href="javascript: formSubmit('<bean:write name="empInfo" property="employeeID"/>');" ><FONT color="red">Edit</FONT></A></TD>
				</TR>
			</logic:iterate>
		</logic:notEmpty>
	
	</table>
</html:form>
</body>

</html:html>
