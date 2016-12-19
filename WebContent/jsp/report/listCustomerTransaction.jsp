<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-Disposition",
			"attachment;filename=listCustomerTransaction.xls");
%>
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

</HEAD>


<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onkeypress="return checkKeyPressed();"
	onload="setSearchControlDisabled()">
<TABLE cellspacing="0" cellpadding="0" border="1" width="100%"
	height="100%" valign="top">
	<TR>
		<TD colspan="4" class="text"><BR>
		<H3>Customer Transaction Report</H3>
		</TD>
	</TR>

	<html:form action="AccountAction" focus="circleName">


		<logic:notEmpty name="CustomerTransaction" property="displayDetails">


			<TR>
				<TD align="center" bgcolor="#cd0504"><SPAN
					class="white10heading mLeft5 mTop5"><bean:message
					key="application.sno" bundle="view" /></SPAN></TD>
				<TD align="center" bgcolor="#cd0504"><SPAN
					class="white10heading mLeft5 mTop5"><bean:message
					key="transaction.transaction_id" bundle="view" /></SPAN></TD>
				<TD align="center" bgcolor="#cd0504"><SPAN
					class="white10heading mLeft5 mTop5"><bean:message
					key="sysconfig.transaction_type" bundle="view" /></SPAN></TD>
				<TD align="center" bgcolor="#cd0504"><SPAN
					class="white10heading mLeft5 mTop5"><bean:message
					key="transaction.account_code" bundle="view" /></SPAN></TD>
				<TD align="center" bgcolor="#cd0504"><SPAN
					class="white10heading mLeft5 mTop5"> <logic:equal
					name="CustomerTransaction" property="transactionType"
					value="RECHARGE">
					<bean:message bundle="view"
						key="report.customertransaction.mobileno" />
				</logic:equal> <logic:equal name="CustomerTransaction" property="transactionType"
					value="VAS">
					<bean:message bundle="view"
						key="report.customertransaction.mobileno" />
				</logic:equal> <logic:equal name="CustomerTransaction" property="transactionType"
					value="POSTPAID_MOBILE">
					<bean:message bundle="view"
						key="report.customertransaction.mobileno" />
				</logic:equal> <logic:equal name="CustomerTransaction" property="transactionType"
					value="POSTPAID_ABTS">
					<bean:message bundle="view"
						key="report.customertransaction.LandlineNo" />
				</logic:equal> <logic:equal name="CustomerTransaction" property="transactionType"
					value="POSTPAID_DTH">
					<bean:message bundle="view" key="report.customertransaction.DTHNo" />
				</logic:equal> </SPAN></TD>
				<TD align="center" bgcolor="#cd0504"><SPAN
					class="white10heading mLeft5 mTop5"><bean:message
					key="transactiontype.transaction_amount" bundle="view" /></SPAN></TD>
				<c:if
					test='${CustomerTransaction.transationType eq "RECHARGE" or CustomerTransaction.transationType eq "VAS"}'>
					<TD align="center" bgcolor="#cd0504"><SPAN
						class="white10heading mLeft5 mTop5"><bean:message
						key="topup.service_tax" bundle="view" /></SPAN></TD>

					<TD align="center" bgcolor="#cd0504"><SPAN
						class="white10heading mLeft5 mTop5"><bean:message
						bundle="view" key="topup.processing_fee" /></SPAN></TD>
				</c:if>
				<TD align="center" bgcolor="#cd0504"><SPAN
					class="white10heading mLeft5 mTop5"><bean:message
					bundle="view" key="report.customertransaction.commission" /></SPAN></TD>
					<c:if
						test='${CustomerTransaction.transationType eq "RECHARGE" or CustomerTransaction.transationType eq "VAS"}'>
				<logic:equal name="CustomerTransaction" property="transactionType"
					value="RECHARGE">
					
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"> <bean:message
							bundle="view" key="report.customertransaction.validity" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="report.talktime" /></SPAN></TD>
					
				</logic:equal></c:if>
				<TD align="center" bgcolor="#cd0504"><SPAN
					class="white10heading mLeft5 mTop5"><bean:message
					bundle="view" key="transaction.list.transactiondate" /></SPAN></TD>
				<TD align="center" bgcolor="#cd0504"><SPAN
					class="white10heading mLeft5 mTop5"><bean:message
					bundle="view" key="account.status" /></SPAN></TD>
				<TD align="center" bgcolor="#cd0504"><SPAN
					class="white10heading mLeft5 mTop5"> 
					<logic:equal name="CustomerTransaction" property="transactionType" 
					value="RECHARGE"><bean:message bundle="view" key="report.inStatus" />
				</logic:equal> <logic:equal name="CustomerTransaction" property="transactionType"
					value="POSTPAID_MOBILE"><bean:message bundle="view" key="report.selfcareStatus" />
				</logic:equal> <logic:equal name="CustomerTransaction" property="transactionType"
					value="POSTPAID_ABTS"><bean:message bundle="view" key="report.selfcareStatus" />
				</logic:equal> <logic:equal name="CustomerTransaction" property="transactionType"
					value="POSTPAID_DTH"><bean:message bundle="view" key="report.selfcareStatus" />
				</logic:equal> <logic:equal name="CustomerTransaction" property="transactionType"
					value="VAS"><bean:message bundle="view" key="report.inStatus" />
				</logic:equal> </SPAN></TD>
				<TD align="center" bgcolor="#cd0504"><SPAN
					class="white10heading mLeft5 mTop5"><bean:message
					bundle="view" key="report.customertransaction.reason" /></SPAN></TD>
				<c:if test='${CustomerTransaction.transationType eq "POSTPAID_ABTS" or CustomerTransaction.transationType eq "POSTPAID_DTH"}'>
					<TD align="center" bgcolor="#cd0504"><SPAN
						class="white10heading mLeft5 mTop5"><bean:message
						bundle="view" key="report.customertransaction.confirmMobileno" /></SPAN></TD>
				</c:if>
			</TR>

			<logic:iterate id="transBean" name="CustomerTransaction"
				property="displayDetails" indexId="i">
				<TR align="center" bgcolor="#fce8e6">
					<TD class="text" align="center"><SPAN
						class="mTop5 mBot5 black10Bold"> <c:out value="${i+1}"></c:out></SPAN></TD>
					<TD class="height19" align="center"><SPAN
						class="mLeft5 mTop5 mBot5 black10"><bean:write
						name="transBean" property="transactionId" /></SPAN></TD>

					<TD class=" height19" align="center"><SPAN
						class="mLeft5 mTop5 mBot5 black10"> <c:if
						test='${transBean.transationType eq "RECHARGE"}'>
						<bean:message bundle="view"
							key="report.customertransaction.transactiontype.recharge" />
					</c:if> <c:if test='${transBean.transationType eq "VAS"}'>
						<bean:message bundle="view"
							key="report.customertransaction.transactiontype.vas" />
					</c:if> <c:if test='${transBean.transationType eq "POSTPAID_MOBILE"}'>
						<bean:message bundle="view"
							key="report.transaction.postpaid.mobile" />
					</c:if> <c:if test='${transBean.transationType eq "POSTPAID_ABTS"}'>
						<bean:message bundle="view" key="report.transaction.postpaid.abts" />
					</c:if><c:if test='${transBean.transationType eq "POSTPAID_DTH"}'>
						<bean:message bundle="view" key="report.transaction.postpaid.dth" />
					</c:if> </SPAN></TD>

					<TD class="height19" align="center"><SPAN
						class="mLeft5 mTop5 mBot5 black10"><bean:write
						name="transBean" property="sourceAccountCode" /></SPAN></TD>
					<TD class="height19" align="center"><SPAN
						class="mLeft5 mTop5 mBot5 black10"><bean:write
						name="transBean" property="customerMobileNo" /></SPAN></TD>
					<TD class="height19" align="center"><SPAN
						class="mLeft5 mTop5 mBot5 black10"><bean:write
						name="transBean" format="#0.00" property="transactionAmount" /></SPAN></TD>
					<c:if
						test='${transBean.transationType eq "RECHARGE" or transBean.transationType eq "VAS"}'>
						<logic:notEqual name="transBean" property="transationType"
							value="RECHARGE">
							<TD class="height19" align="center"><SPAN
								class="mLeft5 mTop5 mBot5 black10"> -- </SPAN></TD>
						</logic:notEqual>

						<logic:equal name="transBean" property="transationType"
							value="RECHARGE">
							<TD class="height19" align="center"><SPAN
								class="mLeft5 mTop5 mBot5 black10"> <bean:write
								bundle="view" format="#0.00" name="transBean"
								property="serviceTax" /> </SPAN></TD>
						</logic:equal>

						<logic:notEqual name="transBean" property="transationType"
							value="RECHARGE">
							<TD class="height19" align="center"><SPAN
								class="mLeft5 mTop5 mBot5 black10"> -- </SPAN></TD>
						</logic:notEqual>
						<logic:equal name="transBean" property="transationType"
							value="RECHARGE">
							<TD class="height19" align="center"><SPAN
								class="mLeft5 mTop5 mBot5 black10"> <bean:write
								bundle="view" format="#0.00" name="transBean"
								property="processingFee" /> </SPAN></TD>
						</logic:equal>
					</c:if>

					<TD class="height19" align="center"><SPAN
						class="mLeft5 mTop5 mBot5 black10"><bean:write
						name="transBean" format="#0.00" property="espCommission" /></SPAN></TD>
					<c:if
						test='${transBean.transationType eq "RECHARGE" or transBean.transationType eq "VAS"}'>
						<logic:equal name="transBean" property="transationType"
							value="RECHARGE">
							<logic:empty name="transBean" property="validity">
								<TD class="text" align="center"><bean:message bundle="view"
									key="report.novalidity" /></TD>
							</logic:empty>
						</logic:equal>

						<logic:notEmpty name="transBean" property="validity">
							<logic:equal name="transBean" property="validity" value="null">
								<TD class="text" align="center"><bean:message bundle="view"
									key="report.novalidity" /></TD>
							</logic:equal>

							<logic:notEqual name="transBean" property="validity" value="null">
								<TD class="text" align="center"><bean:write
									name="transBean" property="validity" formatKey="formatDateTime" /></TD>
							</logic:notEqual>
						</logic:notEmpty>

						<logic:equal name="transBean" property="transationType"
							value="RECHARGE">
							<TD class="height19" align="center"><SPAN
								class="mLeft5 mTop5 mBot5 black10"> <bean:write
								bundle="view" format="#0.00" name="transBean"
								property="talkTime" /> </SPAN></TD>
						</logic:equal>
						<logic:equal name="transBean" property="transationType"
							value="POSTPAID">
							<TD class="height19" align="center"><SPAN
								class="mLeft5 mTop5 mBot5 black10"> <bean:write
								bundle="view" format="#0.00" name="transBean"
								property="talkTime" /> </SPAN></TD>
						</logic:equal>

						<logic:equal name="transBean" property="transationType"
							value="VAS">
							<TD class="height19" align="center"><SPAN
								class="mLeft5 mTop5 mBot5 black10"> -- </SPAN></TD>
						</logic:equal>
					</c:if>
					<TD class="height19" align="center"><SPAN
						class="mLeft5 mTop5 mBot5 black10"><bean:write
						name="transBean" property="transactionDate"
						formatKey="application.date.format" bundle="view" /></SPAN></TD>
					<TD class="height19" align="center"><SPAN
						class="mLeft5 mTop5 mBot5 black10"> <c:if
						test='${transBean.status eq "0"}'>
						<bean:message bundle="view"
							key="report.customertransaction.status.success" />
					</c:if> <c:if test='${transBean.status eq "1"}'>
						<bean:message bundle="view" key="report.transaction.failure" />
					</c:if><c:if test='${transBean.status eq "2"}'>
						<bean:message bundle="view" key="report.transaction.pending" />
					</c:if><c:if test='${transBean.status eq "3"}'>
						<bean:message bundle="view" key="report.transaction.failure" />
					</c:if><c:if test='${transBean.status eq "4"}'>
						<bean:message bundle="view" key="report.transaction.failure" />
					</c:if><c:if test='${transBean.status eq "5"}'>
						<bean:message bundle="view" key="report.transaction.failure" />
					</c:if> </SPAN></TD>

					<TD class="height19" align="center" nowrap><SPAN
						class="mLeft5 mTop5 mBot5 black10"><c:if test='${transBean.inStatus == "null"}'></c:if>
							<c:if test='${transBean.inStatus != "null"}'>
							<bean:write name="transBean" property="inStatus" /></c:if></SPAN></TD>
					<TD class="height19" align="center"><SPAN
						class="mLeft5 mTop5 mBot5 black10"> <bean:write
						name="transBean" property="reason" /> </SPAN></TD>
					<c:if test='${transBean.transationType eq "POSTPAID_ABTS" or transBean.transationType eq "POSTPAID_DTH"}'>
						<TD class="height19" align="center"><SPAN
							class="mLeft5 mTop5 mBot5 black10"><c:if test='${transBean.inStatus == "null"}'></c:if>
							<c:if test='${transBean.inStatus != "null"}'> <bean:write
							name="transBean" property="confirmMobileNo" /></c:if> </SPAN></TD>
					</c:if>

				</TR>

			</logic:iterate>
		</logic:notEmpty>
	</html:form>
</TABLE>
</BODY>
</html:html>
