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
	function validate(){
		if((document.forms[0].receivingAccount.value=="")||(document.forms[0].receivingAccount.value==null)){
			alert('<bean:message bundle="error" key="errors.transaction.receivingAccount.required" />');
			// document.forms[0].receivingAccount.value="";
			document.forms[0].receivingAccount.focus();
			return false;	
		}
		if(isNumericNumber(document.getElementById("transactionAmount").value)==false){
	    	 alert('<bean:message bundle="error" key="errors.transaction.amount_invalid" />')
			document.forms[0].transactionAmount.focus();
			return false;	
		}
		
		return true;
	}
	function checkKeyPressed(){
		if(window.event.keyCode=="13"){
			if(validate()){
				document.getElementById("methodName").click();
				return false;
			//	document.forms[0].action="AccountTransaction.do?methodName="+document.forms[0].methodName.value;
				return true;
			}
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
		<TD valign="top" width="100%" height="100%"><html:form
			action="/AccountTransaction" method="post"
			type="com.ibm.virtualization.recharge.beans.AccountTransactionBean"
			name="AccountTransactionBean" scope="request" focus="transactionAmount">
			<TABLE border="0" cellspacing="0" cellpadding="0" class="text">
				<TR>
					<TD width="521" valign="top">

					<DIV id="transactionDetsDivId">
					<TABLE width="545" border="0" cellpadding="5" cellspacing="0"
						class="text">
						<TR>
							<TD colspan="4" class="text"><BR>
							<IMG src="<%=request.getContextPath()%>/images/createAccToAcc.gif" width="505" height="22"
								alt=""></TD>
						</TR>
						<TR>
							<TD class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="label.transaction.transaction_amount" /></FONT><FONT color="RED">*</FONT>
							:</STRONG></TD>
							<TD><html:text property="transactionAmount"
								styleId="transactionAmount" styleClass="box" tabindex="1"
								size="19" maxlength="16" onkeypress="isValidRate()"/></TD>
							<TD width="3" class="text">&nbsp;</TD>
							<TD width="156">&nbsp;</TD>
						</TR>
						<TR>
							<TD class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="label.transaction.received_account" /></FONT><FONT color="RED">*</FONT>
							:</STRONG></TD>
							<TD><html:select property="receivingAccount"
								name="AccountTransactionBean" styleClass="w130" tabindex="2">
								<logic:notEmpty name="AccountTransactionBean"
									property="childAccountList">
									<html:optionsCollection property="childAccountList"
										name="AccountTransactionBean" label="accountName"
										value="mobileNumber" />
								</logic:notEmpty>
							</html:select></TD>
							<TD width="3" class="text">&nbsp;</TD>
							<TD width="156">&nbsp;</TD>
						</TR>

						<TR>
							<TD class="text pLeft15"><FONT color="#000000">&nbsp;</FONT></TD>
							<TD colspan="3">
							<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
								<TR align="center">
									<TD width="70"><html:submit property="methodName"
										styleClass="submit" tabindex="3" onclick="return validate();">
										<bean:message bundle="view" key="button.account_transfer" />
									</html:submit></TD>
									<TD><INPUT class="submit" type="button" onclick="resetAll();"
										name="Submit2" value="Reset" tabindex="4"></TD>
								</TR>
							</TABLE>
							</TD>

						</TR>
						<TR>
							<TD colspan="4" align="center">&nbsp;</TD>
						</TR>
						<TR>
							<TD colspan="4" class="text pLeft15"><FONT color="RED"><STRONG><html:errors
								property="genericError" bundle="error" /> <html:errors
								property="name" bundle="error" /></STRONG></FONT></TD>
						</TR>
					</TABLE>
					</DIV>
					</TD>
				
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
