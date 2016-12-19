<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="<%=request.getContextPath()%>/theme/CalendarControl.css"
	rel="stylesheet" type="text/css">
<LINK href="<%=request.getContextPath()%>/theme/Master.css"
	rel="stylesheet" type="text/css">
<SCRIPT language="JavaScript"
	src="<%=request.getContextPath()%>/scripts/Cal.js"
	type="text/javascript"></SCRIPT>
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/GeneralValidation.js"></SCRIPT>
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<script>
	history.forward();
</script>

<SCRIPT language="javascript" type="text/javascript">
	function back(){
		document.forms[0].submit();
	}

</SCRIPT>
</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">
	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include
			page="../common/dpheader.jsp" flush="" /></TD>
	</TR>
	<TR valign="top">
		<TD width="167" align="left" bgcolor="b4d2e7" valign="top"
			height="100%"><jsp:include page="../common/leftHeader.jsp"
			flush="" /></TD>
		<TD valign="top" width="100%" height="100%"><html:form
			action="TransactionRptAction">
			<html:hidden property="methodName" styleId="methodName"
				value="searchRechargeTransactionReportData" />
			<html:hidden name="TransactionRptA2AFormBean"
				property="searchFieldName" />
			<html:hidden name="TransactionRptA2AFormBean"
				property="searchFieldValue" />
			<html:hidden name="TransactionRptA2AFormBean" property="status" />
			<html:hidden name="TransactionRptA2AFormBean" property="startDt" />
			<html:hidden name="TransactionRptA2AFormBean" property="endDt" />
			<html:hidden name="TransactionRptA2AFormBean"
				property="transactionType" />
			<html:hidden name="TransactionRptA2AFormBean" property="circleId" />
			<html:hidden property="page" styleId="page" name="TransactionRptA2AFormBean" />
			<TABLE width="600" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					<img
						src="<%=request.getContextPath()%>/images/circleRechargeReport.gif"
						width="505" height="22"></TD>
				</TR>

				<logic:iterate id="transaction" name="TransactionRptA2AFormBean"
					property="displayDetails" indexId="i">
					<TR>
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.transaction_id" />&nbsp;:</FONT></STRONG></TD>
						<TD width="155"><bean:write name="transaction"
							property="transactionId" /></TD>
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.transaction_type" />&nbsp;:</FONT></STRONG></TD>
						<TD width="155"><bean:write name="transaction"
							property="transactionType" /></TD>
					</TR>
					<TR>
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.mtpcode" />&nbsp;:</FONT></STRONG></TD>
						<TD width="155"><bean:write name="transaction"
							property="mtpAccountCode" /></TD>
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.customertransaction.requesterMobile" />&nbsp;:</FONT></STRONG></TD>
						<TD width="155"><bean:write name="transaction"
							property="requesterMobile" /></TD>
					</TR>




					<TR>

						<TD width="155" nowrap class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.requestercircleId" />&nbsp;:</FONT></STRONG></TD>
						<TD width="155"><bean:write name="transaction"
							property="requesterCircleId" /></TD>
				
				<c:if  test='${transaction.transactionType eq "RECHARGE" or 
				transaction.transactionType eq "POSTPAID_MOBILE" or
				 transaction.transactionType eq "POSTPAID_ABTS"}'>
				 		<c:if  test='${transaction.transactionType eq "RECHARGE" or 
							transaction.transactionType eq "POSTPAID_MOBILE"}'>
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.customer_mobile" />&nbsp;:</FONT></STRONG></TD>
						</c:if>
						
						<c:if  test='${transaction.transactionType eq "POSTPAID_ABTS"}'>
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.customer_landline" />&nbsp;:</FONT></STRONG></TD>
						</c:if>
						<TD width="155"><bean:write name="transaction"
							property="customerMobile" /></TD>
				</c:if>							
					
			
				
					<c:if  test='${transaction.transactionType eq "POSTPAID_DTH"}'>									
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.customertransaction.accountId" />&nbsp;:</FONT></STRONG></TD>
						<TD width="155"><bean:write name="transaction"
							property="receiverAccountId" /></TD>
				</c:if>										

					</TR>




					<TR>
						<TD width="155" nowrap class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.subscribercircleId" />&nbsp;:</FONT></STRONG></TD>
						<TD width="163"><bean:write name="transaction"
							property="subscriberCircleId" /></TD>
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.transactionamount" />&nbsp;:</FONT></STRONG></TD>
						<TD width="155"><bean:write name="transaction" format="#0.00"
							property="transactionAmount" /></TD>

					</TR>
					<TR>
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="topup.service_tax" />&nbsp;:</FONT></STRONG></TD>
						<TD width="155"><bean:write bundle="view" format="#0.00"
							name="transaction" property="serviceTax" /></TD>
							
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.customertransaction.commission" />&nbsp;:</FONT></STRONG></TD>
						<logic:empty name="transaction" property="commission">
							<TD width="155">--</TD>
						</logic:empty>
						<logic:notEmpty name="transaction" property="commission">
							<TD width="155"><bean:write name="transaction"
								property="commission" format="#0.00" /></TD>
						</logic:notEmpty>	
							
					
					</TR>
					<TR>
					
						<logic:match  name="transaction" property="transactionType" value="RECHARGE">	
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="topup.processing_fee" />&nbsp;:</FONT></STRONG></TD>

						<logic:empty name="transaction" property="processingFee">
							<TD width="155">--</TD>
						</logic:empty>
						<logic:notEmpty name="transaction" property="processingFee">
							<TD width="155"><bean:write name="transaction"
								property="processingFee" format="#0.00" /></TD>
						</logic:notEmpty>

						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.customertransaction.cardGroup" />&nbsp;:</FONT></STRONG></TD>
						<logic:empty name="transaction" property="cardGroup">
							<TD width="155">--</TD>
						</logic:empty>
						<logic:notEmpty name="transaction" property="cardGroup">
							<TD width="155"><bean:write name="transaction"
								property="cardGroup" /></TD>
						</logic:notEmpty>
						</logic:match>



					</TR>
					<TR>
						 <TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.customertransaction.debitamount" />&nbsp;:</FONT></STRONG></TD>
						<TD width="155"><logic:equal name="transaction"
							property="status" value="0">
							<bean:write name="transaction" property="debitAmount" />
						</logic:equal> <logic:equal name="transaction" property="status" value="2">
							<bean:write name="transaction" property="debitAmount" />
						</logic:equal> <logic:equal name="transaction" property="status" value="1">
							<bean:message bundle="view"
								key="report.customertransaction.debitamount.hide" />
						</logic:equal> <logic:greaterEqual name="transaction" property="status"
							value="3">
							<bean:message bundle="view"
								key="report.customertransaction.debitamount.hide" />
						</logic:greaterEqual></TD> 

						<logic:match  name="transaction" property="transactionType" value="RECHARGE">	
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.talktime" />&nbsp;:</FONT></STRONG></TD>
						</logic:match>	
						<logic:notMatch name="transaction" property="transactionType" value="RECHARGE">
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.customertransaction.creditamount" />&nbsp;:</FONT></STRONG></TD>
						</logic:notMatch>
							
						<logic:empty name="transaction" property="talkTime">
							<TD width="155">--</TD>
						</logic:empty>
						<logic:notEmpty name="transaction" property="talkTime">
							<TD width="155"><bean:write name="transaction"
								property="talkTime" format="#0.00" /></TD>
						</logic:notEmpty>

					</TR>

					<TR>

						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.status" />&nbsp;:</FONT></STRONG></TD>
						<TD width="155"><logic:match name="transaction"
							property="status" value="0">
							<bean:message bundle="view" key="report.transaction.success" />
						</logic:match> <logic:match name="transaction" property="status" value="2">
							<bean:message bundle="view" key="report.transaction.pending" />
						</logic:match> <logic:match name="transaction" property="status" value="1">
							<bean:message bundle="view" key="report.transaction.failure" />
						</logic:match><logic:match name="transaction" property="status" value="4">
							<bean:message bundle="view" key="report.transaction.failure" />
						</logic:match><logic:match name="transaction" property="status" value="5">
							<bean:message bundle="view" key="report.transaction.failure" />
						</logic:match></TD>


						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.transactiondate" />&nbsp;:</FONT></STRONG></TD>
						<TD width="210"><bean:write name="transaction"
							property="transactionDate" formatKey="application.date.format" bundle="view"/></TD>

					</TR>
					<TR>
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.parent_name" />&nbsp;:</FONT></STRONG></TD>
						<TD width="155"><bean:write name="transaction"
							property="parentName" bundle="view"/></TD>	
							
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.parent_name1" />&nbsp;:</FONT></STRONG></TD>
						<TD width="155"><bean:write name="transaction"
							property="parentName1" bundle="view"/></TD>	
					</TR>	
					
					<TR>
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.parent_name2" />&nbsp;:</FONT></STRONG></TD>
						<TD width="155"><bean:write name="transaction"
							property="parentName2" bundle="view"/></TD>	
							
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.parent_name3" />&nbsp;:</FONT></STRONG></TD>
						<TD width="155"><bean:write name="transaction"
							property="parentName3" bundle="view"/></TD>	
					</TR>		
						
					
					
					<TR>
						

						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.reason" />&nbsp;:</FONT></STRONG></TD>
						<TD width="155"><bean:write name="transaction"
							property="reason" /></TD>
							
						<logic:match  name="transaction" property="transactionType" value="RECHARGE">
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.customertransaction.inStatus" />&nbsp;:</FONT></STRONG></TD>
						<TD width="155" colspan="3"><bean:write name="transaction"
							property="inStatus" /></TD>
						</logic:match>
						<logic:notMatch name="transaction" property="transactionType" value="RECHARGE">
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000">
						<bean:message bundle="view" key="report.selfcareStatus" />&nbsp;:
						</FONT></STRONG></TD>
						<TD width="155" colspan="3">
						<logic:notMatch name="transaction" property="selfcareStatus" value="null">
						<bean:write name="transaction" property="selfcareStatus" />
						</logic:notMatch>
						</TD>
						</logic:notMatch>
						
							
					</TR>
					
					
				
					<TR>
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.openingAvailableBalance" />&nbsp;:</FONT></STRONG></TD>

						<logic:equal value="0" name="transaction"
							property="sourceAvailBalBeforeRecharge">
							<logic:equal name="transaction" property="status" value="0">
								<TD width="163"><bean:write name="transaction"
									property="sourceAvailBalBeforeRecharge" format="#0.00" /></TD>
							</logic:equal>
							<logic:notEqual name="transaction" property="status" value="0">
								<TD width="163">--</TD>
							</logic:notEqual>
						</logic:equal>
						<logic:notEqual value="0" name="transaction"
							property="sourceAvailBalBeforeRecharge">
							<TD width="163"><bean:write name="transaction"
								property="sourceAvailBalBeforeRecharge" format="#0.00" /></TD>
						</logic:notEqual>

						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.closingAvailableBalance" />&nbsp;:</FONT></STRONG></TD>
						<logic:equal value="0" name="transaction"
							property="sourceAvailBalAfterRecharge">
							<logic:equal name="transaction" property="status" value="0">
								<TD width="163"><bean:write name="transaction"
									property="sourceAvailBalAfterRecharge" format="#0.00" /></TD>
							</logic:equal>
							<logic:notEqual name="transaction" property="status" value="0">
								<TD width="163">--</TD>
							</logic:notEqual>
						</logic:equal>
						<logic:notEqual value="0" name="transaction"
							property="sourceAvailBalAfterRecharge">
							<TD width="163"><bean:write name="transaction"
								property="sourceAvailBalAfterRecharge" format="#0.00" /></TD>
						</logic:notEqual>
					</TR>
				<logic:match  name="transaction" property="transactionType" value="RECHARGE">
					<TR>

					<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.customertransaction.validity" />&nbsp;:</FONT></STRONG></TD>

						<logic:empty name="transaction" property="validity">
							<TD width="155"><bean:message bundle="view"
								key="report.novalidity" /></TD>
						</logic:empty>

						<logic:notEmpty name="transaction" property="validity">
							<logic:equal name="transaction" property="validity" value="null">
								<TD width="155"><bean:message bundle="view"
									key="report.novalidity" /></TD>
							</logic:equal>
							<logic:notEqual name="transaction" property="validity"
								value="null">
								<TD width="155"><bean:write name="transaction"
									property="validity" formatKey="formatDateTime" /></TD>
							</logic:notEqual>
						</logic:notEmpty>
					

					</TR>
					</logic:match>



					<TR>
						<TD class="text pLeft15"><FONT color="#000000">&nbsp;</FONT></TD>
						<TD colspan="2">
						<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
							<TR align="center" valign="top">

								<TD><INPUT class="submit" type="button" onclick="back();"
									name="Back" value="Back" tabindex="1"></TD>
								<TD></TD>
							</TR>
						</TABLE>
						</TD>
					</TR>
				</logic:iterate>
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
