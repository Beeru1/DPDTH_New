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
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>

<SCRIPT language="javascript" type="text/javascript"> 
	function setBack(){
		document.getElementById("methodName").value="getAllDistributorTransactionList";
	}
</SCRIPT>

</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%" height="100%" valign="top">
	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include page="../common/dpheader.jsp" flush="" /></TD>
	</TR>
	<TR valign="top">
		<TD width="167" align="left" bgcolor="b4d2e7" valign="top"
			height="100%"><jsp:include page="../common/leftHeader.jsp" flush="" /></TD>
		<TD valign="top" width="100%" height="100%"><html:form action="DistributorTransaction">
			<TABLE border="0" cellspacing="0" cellpadding="0" class="text">
				<TR>
					<TD width="521" valign="top">
					<TABLE width="545" border="0" cellpadding="5" cellspacing="0"
						class="text">
						<TR>
							<TD colspan="4" class="text"><BR>
						 	   <IMG src="<%=request.getContextPath()%>/images/viewTransDetails.gif" width="505" height="22" alt=""> 
							</TD>
						</TR>
						<TR>
							<TD colspan="4" class="text"><FONT color="#ff0000" size="-2"><html:errors
								bundle="error" /><html:errors /></FONT></TD>
						</TR>

						<!-- ---------Transaction Id and Account Name  -->
						<TR>
							<TD class="text pLeft15" nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="transaction.transactionid" /></FONT> :</STRONG></TD>
							<TD> <bean:write name="DistributorTransactionFormBean" property="transactionId"/></TD>

							<TD class="text "><STRONG><FONT color="#000000">
							<bean:message bundle="view" key="transaction.accountname" /></FONT> :</STRONG></TD>
							<TD><bean:write name="DistributorTransactionFormBean" property="accountName"/></TD>

						</TR>

						<!-- ---------Transaction amount and Account Code  -->
						<TR>
							<TD class="text pLeft15" nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="transaction.account_code" /></FONT> :</STRONG></TD>
							<TD><bean:write name="DistributorTransactionFormBean" property="accountCode"/></TD>

							<TD class="text " nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="transaction.bankname" /></FONT> :</STRONG></TD>
							<TD><bean:write name="DistributorTransactionFormBean" property="bankName"/></TD>
						</TR>

						<!-- ---------Cheque date and Cheque No.---------  -->
						<TR>
							<TD class="text pLeft15" nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="transaction.paymentMode" /></FONT> :</STRONG></TD>
							<TD><bean:write name="DistributorTransactionFormBean" property="paymentMode"/></TD>
							<TD class="text " nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="transaction.cheq_cardno" /></FONT> :</STRONG></TD>
							<TD><bean:write name="DistributorTransactionFormBean" property="chqccNumber"/></TD>
						</TR>

						<!-- ---------Created By Name Date AND Credited Amount---------  -->
						<TR>
							<TD class="text pLeft15" nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="transactiontype.transaction_amount" /></FONT> :</STRONG></TD>
							<TD><bean:write name="DistributorTransactionFormBean" property="transactionAmount"/></TD>

							<TD class="text " nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="transactiontype.amount_credit" /></FONT> :</STRONG></TD>
							<TD><bean:write name="DistributorTransactionFormBean" property="creditedAmount" /></TD>
						</TR>

						<!-- ---------transaction Type-and bank name--------  -->
						<TR>
							<TD class="text pLeft15" nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="transaction.chequedate" /></FONT> :</STRONG></TD>
							<TD><bean:write name="DistributorTransactionFormBean" property="chequeDate" formatKey="formatDate" /></TD>

							<TD class="text" nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="transaction.carddate" /></FONT> :</STRONG></TD>
							<TD><bean:write name="DistributorTransactionFormBean" property="ccvalidDate" /></TD>
						</TR>

						<TR>
							<TD class="text pLeft15" nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="transaction.ecsno" /></FONT> :</STRONG></TD>
							<TD><bean:write name="DistributorTransactionFormBean" property="ecsno" /></TD>

							<TD class="text" nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="transaction.purchaseorderno" /></FONT> :</STRONG></TD>
							<TD><bean:write name="DistributorTransactionFormBean" property="purchaseOrderNo" /></TD>
						</TR>


						<TR>
							<TD class="text pLeft15" nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="transaction.purchasedate" /></FONT> :</STRONG></TD>
							<TD><bean:write name="DistributorTransactionFormBean" property="purchaseOrderDate" /></TD>
						</TR>

						<logic:notEqual name="DistributorTransactionFormBean" property="reviewStatus" value="P">
							<TR>
								<TD class="text pLeft15" nowrap><STRONG><FONT
									color="#000000"><bean:message bundle="view"
									key="transaction.comment" /></FONT> :</STRONG></TD>
								<TD colspan="3"><pre><bean:write name="DistributorTransactionFormBean" property="reviewComment" /></pre></TD>
							</TR>
						</logic:notEqual>

						<!-- ---------Review Status and Comments.---------  -->
						<logic:match name="DistributorTransactionFormBean" property="reviewStatus" value="P">
							<TR>
								<TD class="text pLeft15" nowrap><STRONG><FONT
									color="#000000"><bean:message bundle="view"
									key="transaction.comment" /></FONT> :</STRONG></TD>
								<TD colspan="3"><pre><bean:write name="DistributorTransactionFormBean" property="reviewComment" /></pre></TD>
							</TR>

							<TR>
								<TD class="text pLeft15"><FONT color="#000000">&nbsp;</FONT></TD>
								<TD colspan="3">
								<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
									<TR align="center">
										<TD><input class="submit" type="submit" name="Submit2" value="Back" onclick="setBack();"></TD>
									</TR>
								</TABLE>
								</TD>
							</TR>
						</logic:match>

						<logic:notMatch name="DistributorTransactionFormBean" property="reviewStatus" value="P">
							<TR>
								<TD class="text pLeft15"><FONT color="#000000">&nbsp;</FONT></TD>
								<TD colspan="3">
								<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
									<TR align="center">
										<TD width="70"><INPUT class="submit" type="submit" name="Submit3" value="Back" onclick="setBack();"></TD>
									</TR>
								</TABLE>
								</TD>
							</TR>
						</logic:notMatch>

					</TABLE>
					</TD>
				</TR>
				<TR>
					<TD colspan="4" align="center">&nbsp;</TD>

					<!-- hidden  use to set dispatch action method name -->
					<html:hidden property="methodName" styleId="methodName" />
					<!-- hidden  use to set accountId -->
					<html:hidden property="accountId" styleId="accountId" />
					<!-- hidden  use to set Selected  Transaction ID from list -->
					<html:hidden property="transactionId" styleId="transactionId" />
					<!-- hidden  use to set  Transaction type selected From search list page and after update to get updated list -->
					<html:hidden property="transactionListType"
						styleId="transactionListType" />
					<html:hidden property="reviewStatus" styleId="reviewStatus" />
					<html:hidden property="circleId" styleId="circleId" />
					<html:hidden property="startDate" styleId="startDate" />
					<html:hidden property="endDate" styleId="endDate" />
					<html:hidden property="page" styleId="page" />
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
