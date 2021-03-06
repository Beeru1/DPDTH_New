<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-Disposition", "attachment;filename=TransactionSummaryReportExcel.xls");
%>
<html:html>
<HEAD>

<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">

	<TR valign="top">

		<TD valign="top" width="100%" height="100%">

		<TABLE width="680" border="0" cellpadding="5" cellspacing="0"
			class="text">
			<TR>
				<TD colspan="4" class="text"><BR>
				<H3>Transaction Summary Report</H3>
				</TD>
			</TR>


			<TR>
				<TD colspan="4">
				<TABLE border="1" width="725" align="center" class="mLeft5">
					<logic:notEmpty name="TransactionRptA2AFormBean"
						property="displayDetails">
						
						
						<logic:match  name="TransactionRptA2AFormBean" property="transactionType" value="QUERY">
							<TR align="center" bgcolor="#104e8b">
								<TD align="center" bgcolor="#cd0504" ><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="application.sno" /></SPAN></TD>
								<TD align="center" bgcolor="#cd0504" ><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="report.transaction_type" /></SPAN></TD>
								<TD align="center" bgcolor="#cd0504" ><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="report.total_successful" /></SPAN></TD>
								<TD align="center" bgcolor="#cd0504" ><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="report.total_failed" /></SPAN></TD>
								<TD align="center" bgcolor="#cd0504" ><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="report.no_total_transaction" /></SPAN></TD>
							</TR>
															
							</logic:match>

						<logic:notMatch name="TransactionRptA2AFormBean" property="transactionType" value="QUERY">
						<TR align="center" bgcolor="#104e8b">
							<TD align="center" bgcolor="#cd0504"></TD>
							<TD align="center" bgcolor="#cd0504"></TD>
							<TD align="center" bgcolor="#cd0504" colspan="2"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.total_successful" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504" colspan="2"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.total_failed" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504"></TD>
						</TR>
						<TR align="center" bgcolor="#104e8b">
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="application.sno" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.transaction_type" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.no_transaction" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.value_transaction" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.no_transaction" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.value_transaction" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.no_total_transaction" /></SPAN></TD>
						</TR>
						</logic:notMatch>
						<logic:iterate id="transaction" name="TransactionRptA2AFormBean"
							property="displayDetails" indexId="i">
							<TR bgcolor="#fce8e6">
								<TD class="text"><SPAN class="mTop5 mBot5 black10Bold">
								<c:out value="${i+1}"></c:out></SPAN></TD>
								<TD class="text"><bean:write name="transaction"
									property="transactionType" /></TD>
								<TD class="text" align="right"><bean:write
									name="transaction" property="noSuccessfulTransaction" /></TD>
								<logic:notMatch name="transaction" property="transactionType" value="QUERY">
								<TD class="text" align="right"><bean:write
									name="transaction" property="valueSuccessfulTransaction" /></TD>
								</logic:notMatch>	
								<TD class="text" align="right"><bean:write
									name="transaction" property="noFailedTransaction" /></TD>
								<logic:notMatch name="transaction" property="transactionType" value="QUERY">	
								<TD class="text" align="right"><bean:write
									name="transaction" property="valueFailedTransaction" /></TD>
								</logic:notMatch>
								<TD class="text" align="center"><bean:write
									name="transaction" property="totalTransaction" /></TD>



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
