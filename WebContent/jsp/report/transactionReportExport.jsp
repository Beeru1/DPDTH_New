<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-Disposition",
			"attachment;filename=TransactionReportExcel.xls");
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
				<H3>Transaction Report</H3>
				</TD>
			</TR>

			<TR>
				<TD colspan="4">
				<TABLE border="1" width="725" align="center" class="mLeft5">
					<logic:notEmpty  name="TransactionRptA2AFormBean"
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
								bundle="view" key="report.transactionamount" /></SPAN></TD>
							<!--	
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.service_tax" /></SPAN></TD>
-->

							<!-- 
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.commission" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.talktime_transfered" /></SPAN></TD>
-->
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.transactiondate" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.status" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.reason" /></SPAN></TD>

							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.service_tax" /></SPAN></TD>
							

							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.commission" /></SPAN></TD>
								
							<c:if  test='${transactionType eq "RECHARGE"}'>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.processing_fee" /></SPAN></TD>	

							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.validity" /></SPAN></TD>
							
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.inStatus" /></SPAN></TD>
								
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.customertransaction.cardGroup" /></SPAN></TD>	
							</c:if>
								
							<c:if  test='${transactionType eq "POSTPAID_MOBILE" or 
									transactionType eq "POSTPAID_ABTS" or
									transactionType eq "POSTPAID_DTH"}'>	
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.selfcareStatus" /></SPAN></TD>	
							</c:if>		

							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.requestercircleId" /></SPAN></TD>

							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.subscribercircleId" /></SPAN></TD>

								
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.debitAmount" /></SPAN></TD>
							
							<logic:match  name="TransactionRptA2AFormBean" property="transactionType" value="RECHARGE">	
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.talktime" /></SPAN></TD>
							</logic:match>
							<logic:notMatch name="TransactionRptA2AFormBean" property="transactionType" value="RECHARGE">
							<TD width="126" class="text pLeft15" bgcolor="#cd0504"><STRONG><SPAN
								class="white10heading mLeft5 mTop5"><bean:message bundle="view"
							key="report.customertransaction.creditamount" /></SPAN></TD>
							</logic:notMatch>		
				
						
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
								<TD class=" height19" align="center"><SPAN
										class="mLeft5 mTop5 mBot5 black10"> <bean:write
										name="transaction" property="parentName" /></SPAN></TD>
								<TD class=" height19" align="center"><SPAN
										class="mLeft5 mTop5 mBot5 black10"> <bean:write
										name="transaction" property="parentName1" /></SPAN></TD>	
								<TD class=" height19" align="center"><SPAN
										class="mLeft5 mTop5 mBot5 black10"> <bean:write
										name="transaction" property="parentName2" /></SPAN></TD>											
								<TD class=" height19" align="center"><SPAN
										class="mLeft5 mTop5 mBot5 black10"> <bean:write
										name="transaction" property="parentName3" /></SPAN></TD>						
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
								<TD class="text" align="right"><bean:write
									name="transaction" property="transactionAmount" /></TD>
								<!--
								<TD class="text" align="center"><logic:notEmpty
									name="transaction" property="transactionType">
									<logic:match name="transaction" property="transactionType"
										value="RECHARGE">
										<bean:write name="transaction" property="serviceTax" />
									</logic:match>
									<logic:notMatch name="transaction" property="transactionType"
										value="RECHARGE">
										--
									</logic:notMatch>
								</logic:notEmpty></TD>
-->

								<!--	
								<TD class="text" align="right"><bean:write
									name="transaction" property="commission" /></TD>
								<TD class="text" align="center"><logic:notEmpty
									name="transaction" property="transactionType">
									<logic:match name="transaction" property="transactionType"
										value="RECHARGE">
										<bean:write name="transaction" property="talkTime" />
									</logic:match>
									<logic:notMatch name="transaction" property="transactionType"
										value="RECHARGE">
										--
									</logic:notMatch>
								</logic:notEmpty></TD>
-->
								<TD class="text" align="center"><bean:write
									name="transaction" property="transactionDate"
									formatKey="application.date.format" bundle="view" /></TD>

								<TD class="text" align="center"><logic:match
									name="transaction" property="status" value="0">
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

								<TD class="text" align="left"><bean:write
									name="transaction" property="reason" /></TD>

								<TD class="text" align="left"><bean:write
									name="transaction" property="serviceTax" /></TD>

								

								<TD class="text" align="left"><bean:write
									name="transaction" property="commission" /></TD>
									
							<c:if  test='${transactionType eq "RECHARGE"}'>		
								
								<TD class="text" align="left"><bean:write
									name="transaction" property="processingFee" /></TD>	

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
								
								<TD class="text" align="center" ><bean:write
									name="transaction" property="inStatus" /></TD>
									
								<TD class="text" align="center"><bean:write
									name="transaction" property="cardGroup" /></TD>	
							</c:if>		
									
								<c:if  test='${transactionType eq "POSTPAID_MOBILE" or 
									transactionType eq "POSTPAID_ABTS" or
									transactionType eq "POSTPAID_DTH"}'>		
								<TD class="text" align="center" >
								<logic:notMatch name="transaction" property="selfcareStatus" value="null">
								<bean:write	name="transaction" property="selfcareStatus" />
								</logic:notMatch>
								</TD>
								</c:if>		

								<TD class="text" align="left"><bean:write
									name="transaction" property="requesterCircleId" /></TD>

								<TD class="text" align="left"><bean:write
									name="transaction" property="subscriberCircleId" /></TD>

								<TD class="text" align="left"><bean:write
									name="transaction" property="debitAmount" /></TD>
									
									<TD class="text" align="left"><bean:write
									name="transaction" property="talkTime" /></TD>		
							
							
					
							
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
