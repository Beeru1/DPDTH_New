<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	response.setContentType("application/vnd.ms-excel");
	response
			.setHeader("content-Disposition",
			"attachment;filename=CustomerRechargeTransactionReportExcel.xls");
%>
<html:html>
<HEAD>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">
<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">

	<TR valign="top">

		<TD valign="top" width="100%" height="100%">

		<TABLE width="680" border="0" cellpadding="5" cellspacing="0"
			class="text">
			<TR>
				<TD colspan="4" class="text"><BR>
				<H3>Circle Recharge Transaction Report</H3>
				</TD>
			</TR>

			<TR>
				<TD colspan="4">
				<TABLE border="1" width="725" align="center" class="mLeft5">
					<logic:notEmpty name="TransactionRptA2AFormBean"
						property="displayDetails">
						<TR align="center" bgcolor="#104e8b">
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="application.sno" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.transaction_id" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.transaction_type" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.mtpcode" /></SPAN></TD>

							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.customertransaction.requesterMobile" /></SPAN></TD>

							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								key="report.requestercircleId" bundle="view" /></SPAN></TD>

							<c:if  test='${transactionType eq "RECHARGE" or 
								transactionType eq "POSTPAID_MOBILE" or
								 transactionType eq "POSTPAID_ABTS"}'>
				 				<c:if  test='${transactionType eq "RECHARGE" or 
								transactionType eq "POSTPAID_MOBILE"}'>
									<TD align="center" bgcolor="#cd0504"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="report.customer_mobile" /></SPAN></TD>
								</c:if>
								<c:if  test='${transactionType eq "POSTPAID_ABTS"}'>
											<TD align="center" bgcolor="#cd0504"><SPAN
											class="white10heading mLeft5 mTop5"><bean:message
											bundle="view" key="report.customer_landline" /></SPAN></TD>
								</c:if>
							</c:if>
							<c:if  test='${transactionType eq "POSTPAID_DTH"}'>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.customertransaction.accountId" /></SPAN></TD>
							</c:if>					

							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								key="report.subscribercircleId" bundle="view" /></SPAN></TD>

							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.transactionamount" /></SPAN></TD>

							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								key="topup.service_tax" bundle="view" /></SPAN></TD>
							<logic:match  name="TransactionRptA2AFormBean" property="transactionType" value="RECHARGE">		
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="topup.processing_fee" /></SPAN></TD>
							</logic:match>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.customertransaction.commission" /></SPAN></TD>
							<logic:match  name="TransactionRptA2AFormBean" property="transactionType" value="RECHARGE">		
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.customertransaction.cardGroup" /></SPAN></TD>
							</logic:match>	
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.customertransaction.debitamount" /></SPAN></TD>
							<logic:match  name="TransactionRptA2AFormBean" property="transactionType" value="RECHARGE">		
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.talktime" /></SPAN></TD>
							</logic:match>	
							<logic:notMatch name="TransactionRptA2AFormBean" property="transactionType" value="RECHARGE">
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message bundle="view"
							key="report.customertransaction.creditamount" />&nbsp;</SPAN></TD>
							</logic:notMatch>
							
							<logic:match  name="TransactionRptA2AFormBean" property="transactionType" value="RECHARGE">		
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.customertransaction.validity" /></SPAN></TD>
							</logic:match>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.parent_name" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.parent_name1" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.parent_name2" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.parent_name3" /></SPAN></TD>		
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.transactiondate" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.status" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.reason" /></SPAN></TD>

							<logic:notMatch name="TransactionRptA2AFormBean" property="transactionType" value="RECHARGE">
						 	<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.selfcareStatus" /></SPAN></TD>
							</logic:notMatch>		
							<logic:match  name="TransactionRptA2AFormBean" property="transactionType" value="RECHARGE">		
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.inStatus" /></SPAN></TD>
							</logic:match>	
								
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.openingAvailableBalance" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.closingAvailableBalance" /></SPAN></TD>
							
						

						</TR>
						<logic:iterate id="transaction" name="TransactionRptA2AFormBean"
							property="displayDetails" indexId="i">
							<TR bgcolor="#fce8e6">
								<TD class="text" align="center"><SPAN
									class="mTop5 mBot5 black10Bold"> <c:out value="${i+1}"></c:out></SPAN></TD>
								<TD class="text" align="center"><bean:write
									name="transaction" property="transactionId" /></TD>
								<TD class="text" align="center"><bean:write
									name="transaction" property="transactionType" /></TD>
								<TD class="text" align="center"><bean:write
									name="transaction" property="mtpAccountCode" /></TD>
								<TD class="text" align="center" ><bean:write
									name="transaction" property="requesterMobile" /></TD>
								<TD class="text" align="center"><bean:write
									name="transaction" property="requesterCircleId" /></TD>

								<c:if  test='${transactionType eq "RECHARGE" or 
									transactionType eq "POSTPAID_MOBILE" or
									transactionType eq "POSTPAID_ABTS"}'>

								<TD class="text" align="center"><bean:write
									name="transaction" property="customerMobile" /></TD>
								</c:if>
								
								<c:if  test='${transactionType eq "POSTPAID_DTH"}'>
								<TD class="text" align="center"><bean:write
									name="transaction" property="receiverAccountId" /></TD>			
								</c:if>			
								<TD class="text" align="center"><bean:write name="transaction"
										property="subscriberCircleId" /></TD>
								<TD class="text" align="center"><bean:write
									name="transaction" property="transactionAmount" /></TD>
								<TD class="text" align="center"><bean:write format="#0.00"
									name="transaction" property="serviceTax" /></TD>
								<logic:match  name="transaction" property="transactionType" value="RECHARGE">		
								<TD class="text" align="center"><bean:write format="#0.00"
									name="transaction" property="processingFee" /></TD>
								</logic:match>	
								<TD class="text" align="center"><bean:write
									name="transaction" format="#0.00" property="commission" /></TD>
								<logic:match  name="transaction" property="transactionType" value="RECHARGE">		
								<TD class="text" align="center"><bean:write
									name="transaction" property="cardGroup" /></TD>
								</logic:match>	
								<TD class="text" align="center" ><logic:equal
									name="transaction" property="status" value="0">
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
								
								<TD class="text" align="center"><bean:write
									name="transaction" property="talkTime" /></TD>
								<logic:match  name="transaction" property="transactionType" value="RECHARGE">
								<logic:empty name="transaction" property="validity">
									<TD class="text" align="center"><bean:message
										bundle="view" key="report.novalidity" /></TD>
								</logic:empty>
		
								<logic:notEmpty name="transaction" property="validity">
									<logic:equal name="transaction" property="validity"
										value="null">
										<TD class="text" align="center"><bean:message
											bundle="view" key="report.novalidity" /></TD>
									</logic:equal>
									<logic:notEqual name="transaction" property="validity"
										value="null">
										<TD class="text" align="center"><bean:write
											name="transaction" property="validity"
											formatKey="formatDateTime" /></TD>
									</logic:notEqual>
								</logic:notEmpty>
							</logic:match>	
								
								<TD class="text" align="center" ><bean:write
									name="transaction" property="parentName" /></TD>
								<TD class="text" align="center" ><bean:write
									name="transaction" property="parentName1" /></TD>
								<TD class="text" align="center" ><bean:write
									name="transaction" property="parentName2" /></TD>
								<TD class="text" align="center" ><bean:write
									name="transaction" property="parentName3" /></TD>		
								<TD class="text" align="center"><bean:write
									name="transaction" property="transactionDate"
									formatKey="application.date.format" bundle="view" /></TD>

								<TD class="text" align="center"><logic:match
									name="transaction" property="status" value="0">
									<bean:message bundle="view"
										key="report.customertransaction.status.success" />
								</logic:match> <logic:match name="transaction" property="status" value="1">
									<bean:message bundle="view" key="report.transaction.failure" />
								</logic:match> <logic:match name="transaction" property="status" value="2">
									<bean:message bundle="view"
										key="report.customertransaction.status.pending" />
								</logic:match> <logic:match name="transaction" property="status" value="3">
									<bean:message bundle="view"
										key="report.transaction.failure" />
								</logic:match> <logic:match name="transaction" property="status" value="4">
									<bean:message bundle="view"
										key="report.transaction.failure" />
								</logic:match> <logic:match name="transaction" property="status" value="5">
									<bean:message bundle="view"
										key="report.transaction.failure" />
								</logic:match></TD>
								<TD class="text" align="left"><bean:write
									name="transaction" property="reason" /></TD>
									
								<logic:notMatch name="transaction" property="transactionType" value="RECHARGE">									
									<TD class="text" align="center" >
									 <logic:notMatch name="transaction" property="selfcareStatus" value="null">
									<bean:write name="transaction" property="selfcareStatus" />
									</logic:notMatch>
									</TD>
								</logic:notMatch>	
							
								<logic:match  name="transaction" property="transactionType" value="RECHARGE">		
								<TD class="text" align="center" ><bean:write
									name="transaction" property="inStatus" /></TD>
						
								</logic:match>
								<logic:equal value="0" name="transaction"
									property="sourceAvailBalBeforeRecharge">
									<logic:equal name="transaction" property="status" value="0">
										<TD class="text" align="center" ><bean:write
											name="transaction" property="sourceAvailBalBeforeRecharge"
											format="#0.00" /></TD>
									</logic:equal>
									<logic:notEqual name="transaction" property="status" value="0">
										<TD class="text" align="center" >--</TD>
									</logic:notEqual>
								</logic:equal>
								<logic:notEqual value="0" name="transaction"
									property="sourceAvailBalBeforeRecharge">
									<TD class="text" align="center" ><bean:write
										name="transaction" property="sourceAvailBalBeforeRecharge"
										format="#0.00" /></TD>
								</logic:notEqual>


								<logic:equal value="0" name="transaction"
									property="sourceAvailBalAfterRecharge">
									<logic:equal name="transaction" property="status" value="0">
										<TD class="text" align="center" ><bean:write
											name="transaction" property="sourceAvailBalAfterRecharge"
											format="#0.00" /></TD>
									</logic:equal>
									<logic:notEqual name="transaction" property="status" value="0">
										<TD class="text" align="center">--</TD>
									</logic:notEqual>
								</logic:equal>
								<logic:notEqual value="0" name="transaction"
									property="sourceAvailBalAfterRecharge">
									<TD class="text" align="center"><bean:write
										name="transaction" property="sourceAvailBalAfterRecharge"
										format="#0.00" /></TD>
								</logic:notEqual>
								
				
								
							</TR>
						</logic:iterate>
					</logic:notEmpty>

				</TABLE>
				</TD>

			</TR>
		</TABLE>

		</TD>
	</TR>

</TABLE>

</BODY>
</html:html>
