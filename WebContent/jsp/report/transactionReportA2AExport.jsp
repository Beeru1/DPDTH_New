<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	response.setContentType("application/vnd.ms-excel");
	response
			.setHeader("content-Disposition",
			"attachment;filename=Account2AccountTransactionReportExcel.xls");
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
				<H3>Account to Account Transaction Report</H3>
				</TD>
			</TR>

			<TR>
				<TD colspan="4">
				<TABLE border="1" width="725" align="center" class="mLeft5">
					<logic:notEmpty name="TransactionRptA2AFormBean"
						property="displayDetails">
						
						<TR align="center">
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="application.sno" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.transaction_id" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.transferingcode" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.recievingcode" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.transactionamount" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.transaction" /> <bean:message
								bundle="view" key="report.transactiondate" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.status" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.reason" /></SPAN></TD>

						</TR>
						<logic:iterate id="transaction" name="TransactionRptA2AFormBean"
							property="displayDetails" indexId="i">
							<TR>
								<TD class="text" align="center"><SPAN
									class="mTop5 mBot5 black10Bold"> <c:out value="${i+1}"></c:out></SPAN></TD>
								<TD class="text" align="center"><bean:write
									name="transaction" property="transactionId" /></TD>
								<TD class="text" align="center"><bean:write
									name="transaction" property="transferringAccountCode" /></TD>
								<TD class="text" align="center"><bean:write
									name="transaction" property="receivingAccountCode" /></TD>
								<TD class="text" align="center"><bean:write
									name="transaction" property="transactionAmount" /></TD>
								<TD class="text" align="center" width="500" nowrap><bean:write
									name="transaction" property="transactionDate"
									formatKey="application.date.format" bundle="view" /></TD>

								<TD class="text" align="center"><logic:match
									name="transaction" property="status" value="0">
									<bean:message bundle="view" key="report.transaction.success" />
								</logic:match> <logic:match name="transaction" property="status" value="2">
									<bean:message bundle="view" key="report.transaction.pending" />
								</logic:match> <logic:match name="transaction" property="status" value="1">
									<bean:message bundle="view" key="report.transaction.failure" />
								</logic:match> <logic:match name="transaction" property="status" value="3">
									<bean:message bundle="view" key="report.transaction.failure" />
								</logic:match> <logic:match name="transaction" property="status" value="4">
									<bean:message bundle="view" key="report.transaction.failure" />
								</logic:match> <logic:match name="transaction" property="status" value="5">
									<bean:message bundle="view" key="report.transaction.failure" />
								</logic:match></TD>
								<TD class="text" align="center"><bean:write
									name="transaction" property="reason" /></TD>

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
