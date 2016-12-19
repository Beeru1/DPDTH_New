<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="<%=request.getContextPath()%>/theme/text.css" rel="stylesheet" type="text/css">
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<script type="text/javascript">
function validate()
{
	if(document.getElementById("subject").value=="0")
	{
		alert("Please Select The Subject Of Mail.");
		document.getElementById("subject").focus();
		return false;
	}else if (document.getElementById("message_Sent").value=="")
	{
		alert("Please Enter The Message.");
		document.getElementById("message_Sent").focus();
		return false;
	}
	return true;
}
function reset()
{
	document.getElementById("subject").value = "0";
	document.getElementById("message_Sent").value = "";
}

</script>

</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF">

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
		<html:form action="/sendEmail.do?methodName=sendEmailMethod">

			<TABLE border="0" cellspacing="0" cellpadding="0" class="text">
				<TR>
					<TD width="521" valign="top">
					<TABLE width="545" border="0" cellpadding="5" cellspacing="0"
						class="text">
						<TR align="center" valign="middle">
              	<td colspan="3" align="center">
						<logic:messagesPresent message="true">
							<html:messages id="msg" property="MESSAGE_SENT_SUCCESS" bundle="view" message="true">
								<font color="red" size="2"><strong><bean:write name="msg"/></strong></font>
							</html:messages>
							<html:messages id="msg" property="ERROR_OCCURED" bundle="view" message="true">
								<font color="red" size="2"><strong><bean:write name="msg"/></strong></font>
							</html:messages>
						</logic:messagesPresent>
						<html:errors property="error" />
						</td>
				</TR>
						<TR>
							<TD colspan="4" class="text"><BR>
							 <IMG src="<%=request.getContextPath()%>/images/send-email.gif" width="505" height="22" alt="">  
							</TD>
						</TR>
              <tr>
                <td class="text pLeft15"><font color="#4b0013"><strong><bean:message bundle="view" key="sendEmail.from"/> <span class="orange8"><font color="red">*</font> </span> :</strong></font></td>
                <td><font color="#003399">
                <bean:write name="HBOSendEmailForm" property="senderId"/>
			    </font></td>
              </tr>
              <tr>
                <td class="text pLeft15"><font color="#4b0013"><strong><bean:message bundle="view" key="sendEmail.to"/> <span class="orange8"><font color="red">*</font> </span> :</strong></font></td>
                <td><font color="#003399">
                	 <bean:write name="HBOSendEmailForm" property="receiverId"/>
              </font></td>
              </tr>
              <tr>
                <td class="text pLeft15"><font color="#4b0013"><strong><bean:message bundle="view" key="sendEmail.Cc"/> <span class="orange8"><font color="red">*</font> </span> :</strong></font></td>
                <td><font color="#003399">
					<bean:write name="HBOSendEmailForm" property="ccReceiverId"/>
                   </font></td>
              </tr>
              <tr>
                <td class="text pLeft15"><font color="#4b0013"><strong><bean:message bundle="view" key="sendEmail.subject"/> <span class="orange8"><font color="red">*</font> </span> :</strong></font></td>
                <td><font color="#003399">
                     <html:select property="subject" name="HBOSendEmailForm">
                     <html:option value="0">--Select--</html:option>
                     <html:options collection="subjectList" labelProperty="subject" property="subject"/>
                     </html:select>
              </font></td>
              </tr>
              <tr>
                <td valign="top" class="text pLeft15"><font color="#4b0013"><strong><bean:message bundle="view" key="sendEmail.textmessage" /><span class="orange8"><font color="red">*</font> </span>:</strong></font></td>
                <td colspan="3" valign="top"><font color="#003399">
                <html:textarea property="message_Sent" name="HBOSendEmailForm" rows="10" cols="60"></html:textarea>
                 </font></td>
              </tr>
              <tr>
                <td class="text pLeft15"><font color="#4b0013">&nbsp;</font></td>
                <td colspan="3"><table width="140" border="0" cellspacing="0" cellpadding="5">
                    <tr align="center">
                    <td>
                    <html:submit value="Submit" property="Submit" onclick="return validate();"></html:submit>
                    </td>
                      <td width="70">
                       <html:reset value="Reset" property="Reset" onclick="reset();"></html:reset>		
                      </td>
                    </tr>
                </table>
				</td>
              </tr>				
			</TABLE>
		</TD>
	</TR>
</TABLE>
</html:form>
</TD></TR>
<TR align="center" bgcolor="4477bb" valign="top">
		<TD colspan="5" height="17" align="center" width="100%"><jsp:include
			page="../common/footer.jsp" flush="" /></TD>
	</TR>
</TABLE>
</BODY>
</html:html>