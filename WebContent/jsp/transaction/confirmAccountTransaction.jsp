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
<LINK href="<%=request.getContextPath()%>/theme/CalendarControl.css" rel="stylesheet" type="text/css">
<LINK href="<%=request.getContextPath()%>/theme/text.css" rel="stylesheet" type="text/css">

<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/JScriptLib.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<SCRIPT language="javascript" type="text/javascript"> 

  // function that checks that is any field value blank or not

var buttonStatus = "false";
	
	function setButtonStatus(){
		buttonStatus = "true"; 
	}
	
	function checkKeyPressed(){
		if((buttonStatus=="false") && (window.event.keyCode=="13")){
				document.getElementById("methodName").click();
				return false;
		}
		else{
			confirmCancel();
		}
	}
	
	function ResetButtonStatus(){
		buttonStatus="false";
	}
	
	function confirmTransfer(){
		document.getElementById("flag").value="false";
		document.forms[0].submit();
	}
	
	function confirmCancel()
	{
		document.forms[0].action="AccountTransaction.do?methodName="+'<bean:message bundle="view" key="button.confirm_cancel" />';
		document.forms[0].submit();
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
		<TD valign="top" width="100%" height="100%"><html:form
			action="/AccountTransaction" method="post"
			type="com.ibm.virtualization.recharge.beans.AccountTransactionBean"
			name="AccountTransactionBean" scope="request"
			focus="transactionAmount" >
			<TABLE border="0" cellspacing="0" cellpadding="0" class="text">
				
				<TR>
					<TD width="521" valign="top">
					<TABLE width="545" border="0" cellpadding="5" cellspacing="0"
						class="text">
						<TR>
							<TD colspan="2" class="text"><BR>
							<IMG src="<%=request.getContextPath()%>/images/createAccToAcc.gif" width="505" height="22"
								alt=""></TD>
						</TR>
						<TR>
							<TD class="text" colspan="2"><FONT color="BLACK"><STRONG>
							<html:errors property="ConfirmError" bundle="error" /> </STRONG></FONT></TD>
						</TR>
						<TR align="center">
							<TD align="right"><html:submit property="methodName"
								styleClass="submit" tabindex="5" onclick="confirmTransfer();">
								<bean:message bundle="view" key="button.confirm_transfer" />
							</html:submit></TD>
							<TD align="left"><INPUT type="button" class="submit"
								tabindex="6" styleId="cancel" name="cancel"
								value='<bean:message bundle="view" key="button.confirm_cancel" />'
								onclick="confirmCancel();" onfocus="setButtonStatus();" onblur="ResetButtonStatus();"/></TD>
						</TR>
					</TABLE>
					</TD>
				</TR>
				<TR>
					<html:hidden property="sourceId" name="AccountTransactionBean"
						styleId="sourceId"></html:hidden>
					<html:hidden property="destinationId" name="AccountTransactionBean"
						styleId="destinationId"></html:hidden>
					<html:hidden property="transactionId" name="AccountTransactionBean"
						styleId="transactionId"></html:hidden>
					<html:hidden property="creditedAmount"
						name="AccountTransactionBean" styleId="creditedAmount"></html:hidden>
					<html:hidden property="contextSourceId"
						name="AccountTransactionBean" styleId="contextSourceId"></html:hidden>
					<html:hidden property="flag" name="AccountTransactionBean"
						styleId="flag"></html:hidden>
					<html:hidden property="rate" name="AccountTransactionBean"
						styleId="rate"></html:hidden>
					<html:hidden property="transactionAmount" name="AccountTransactionBean"
								styleId="transactionAmount" />
					<html:hidden property="receivingAccount"
								name="AccountTransactionBean" styleId="receivingAccount" />		
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
