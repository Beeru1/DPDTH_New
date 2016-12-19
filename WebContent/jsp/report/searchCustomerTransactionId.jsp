
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<LINK href="<%=request.getContextPath()%>/theme/text.css"
	rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/theme/chromestyle2.css" />
<LINK href="<%=request.getContextPath()%>/theme/Master.css"
	rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/chrome.js"></SCRIPT>
<SCRIPT language="JavaScript"
	src="<%=request.getContextPath()%>/scripts/Cal.js"
	type="text/javascript"></SCRIPT>
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<script>
	history.forward();
	
	

</SCRIPT>
<SCRIPT language="javascript" type="text/javascript">
	function back(){
		if(document.getElementById("transFlag").value=="true"){
			document.forms[0].action="CustomerTransaction.do?methodName=initAsync";
			document.getElementById("transFlag").value = false;
			document.forms[0].method="post";
			document.forms[0].submit();
			return true;
		}else if (document.getElementById("flagPostPaid").value=="true"){
		    document.forms[0].action="BillPaymentAction.do?methodName=init";
			document.getElementById("flagPostPaid").value = false;
			document.forms[0].method="post";
			document.forms[0].submit();
			return true;
		
		}else{
			//document.getElementById("methodName").value="searchCustomerTrans";
			document.forms[0].action="CustomerTransaction.do?methodName=searchCustomerTrans";
			document.forms[0].submit();
		}
		
	}
//	function hideBack(){
	//	if(document.getElementById("transFlag").value=="true"){
	//		document.getElementById("disableBackDiv").style.display="none";
	//		document.getElementById("transFlag").value = false;
	//	}else{
	//		document.getElementById("disableBackDiv").style.display="block";
	//	}
	
//	}
</script>

</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" >

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
			action="/CustomerTransaction">
			<html:hidden property="methodName" styleId="methodName" />
			<html:hidden name="CustomerTransaction" property="startDate" />
			<html:hidden name="CustomerTransaction" property="endDate" />
			<html:hidden name="CustomerTransaction" property="transactionType" />
			<html:hidden name="CustomerTransaction" property="searchCriteria" />
			<html:hidden name="CustomerTransaction" property="mobileNo" />
			<html:hidden name="CustomerTransaction" property="transFlag" styleId="transFlag" />
			<html:hidden property="page" styleId="page" name="CustomerTransaction" />
			<TABLE width="545" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<logic:iterate id="CustomerTransaction" name="CustomerTransaction"
					property="displayDetails" indexId="i">
					<TR>
						<TD colspan="4" class="text"><BR>
						<IMG
							src="<%=request.getContextPath()%>/images/viewCustTransReport.gif"
							width="505" height="22" alt=""></TD>
					</TR>
					<TR>
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="transaction.transaction_id" />&nbsp;:</FONT></STRONG></TD>
						<TD width="155"><bean:write name="CustomerTransaction"
							property="transactionId" /></TD>
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="sysconfig.transaction_type" />&nbsp;:</FONT></STRONG></TD>
						<td><c:if
							test='${CustomerTransaction.transationType eq "RECHARGE"}'>
							<bean:message bundle="view"
								key="report.customertransaction.transactiontype.recharge" />
						</c:if> <c:if test='${CustomerTransaction.transationType eq "VAS"}'>
							<bean:message bundle="view"
								key="report.customertransaction.transactiontype.vas" />
						</c:if> 
						<c:if test='${CustomerTransaction.transationType eq "POSTPAID_MOBILE"}'>
								<bean:message bundle="view"
									key="report.transaction.postpaid.mobile" />
							</c:if> <c:if test='${CustomerTransaction.transationType eq "POSTPAID_ABTS"}'>
								<bean:message bundle="view"
									key="report.transaction.postpaid.abts" />
							</c:if><c:if test='${CustomerTransaction.transationType eq "POSTPAID_DTH"}'>
								<bean:message bundle="view"
									key="report.transaction.postpaid.dth" />
							</c:if>
						
						
						
						</TD>

					</TR>
					<TR>
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="transaction.account_code" />&nbsp;:</FONT></STRONG></TD>
						<TD width="155"><bean:write name="CustomerTransaction"
							property="sourceAccountCode" /></TD>
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><c:if  test='${CustomerTransaction.transationType eq "RECHARGE" or CustomerTransaction.transationType eq "POSTPAID_MOBILE" or CustomerTransaction.transationType eq "VAS"}'>
							<bean:message key="report.customertransaction.mobileno" bundle="view" />
							</c:if>
							<c:if  test='${CustomerTransaction.transationType eq "POSTPAID_ABTS"}'>
							<bean:message key="report.customertransaction.LandlineNo" bundle="view" />
							</c:if>
							<c:if  test='${CustomerTransaction.transationType eq "POSTPAID_DTH"}'>
							<bean:message key="report.customertransaction.DTHNo" bundle="view" />
							</c:if>&nbsp;:</FONT></STRONG></TD>
						<TD width="163"><bean:write name="CustomerTransaction"
							property="customerMobileNo" /></TD>
					</TR>
					<TR>
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="transactiontype.transaction_amount" />&nbsp;:</FONT></STRONG></TD>
						<TD width="155"><bean:write name="CustomerTransaction"
							format="#0.00" property="transactionAmount" /></TD>
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.customertransaction.commission" />&nbsp;:</FONT></STRONG></TD>
						<TD width="163"><bean:write bundle="view" format="#0.00"
							name="CustomerTransaction" property="espCommission" /></TD>
					</TR>
					<TR><c:if  test='${CustomerTransaction.transationType eq "RECHARGE" or CustomerTransaction.transationType eq "VAS"}'>
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="topup.service_tax" />&nbsp;:</FONT></STRONG></TD>
						<TD width="163"><bean:write bundle="view" format="#0.00"
							name="CustomerTransaction" property="serviceTax" /></TD>
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="topup.processing_fee" />&nbsp;:</FONT></STRONG></TD>
						<TD width="155"><bean:write bundle="view" format="#0.00"
							name="CustomerTransaction" property="processingFee" /></TD></c:if>
						
						
					</TR>
					<TR><c:if  test='${CustomerTransaction.transationType eq "RECHARGE" or CustomerTransaction.transationType eq "VAS"}'>
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.customertransaction.validity" />&nbsp;:</FONT></STRONG></TD>



						<logic:empty name="CustomerTransaction" property="validity">
							<TD width="126"><bean:message bundle="view"
								key="report.novalidity" /></TD>
						</logic:empty>

						<logic:notEmpty name="CustomerTransaction" property="validity">
							<logic:equal name="CustomerTransaction" property="validity"
								value="null">
								<TD width="126"><bean:message bundle="view"
									key="report.novalidity" /></TD>
							</logic:equal>
							<logic:notEqual name="CustomerTransaction" property="validity"
								value="null">
								<TD width="126"><bean:write name="CustomerTransaction"
									property="validity" formatKey="formatDateTime" /></TD>
							</logic:notEqual>
						</logic:notEmpty>

						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.talktime" />&nbsp;:</FONT></STRONG></TD>
						<TD width="163"><bean:write bundle="view" format="#0.00"
							name="CustomerTransaction" property="talkTime" /></TD>
					
					</c:if>
					

						
						
					</TR>
					<TR>
						<TD width="126" class="text pLeft15" nowrap><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.customertransaction.transactiondate" />&nbsp;:</FONT></STRONG></TD>
						<TD width="195" nowrap><bean:write name="CustomerTransaction"
							property="transactionDate" formatKey="application.date.format" bundle="view"/></TD>
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="account.status" />&nbsp;:</FONT></STRONG></TD>
						<TD width="163"><c:if
							test='${CustomerTransaction.status eq "0"}'>
							<bean:message bundle="view"
								key="report.customertransaction.status.success" />
						</c:if> <c:if test='${CustomerTransaction.status eq "1"}'>
							<bean:message bundle="view"
								key="report.transaction.failure" />
						</c:if><c:if test='${CustomerTransaction.status eq "2"}'>
							<bean:message bundle="view"
								key="report.transaction.pending" />
						</c:if><c:if test='${CustomerTransaction.status eq "3"}'>
							<bean:message bundle="view"
								key="report.transaction.failure" />
						</c:if><c:if test='${CustomerTransaction.status eq "4"}'>
							<bean:message bundle="view"
								key="report.transaction.failure" />
						</c:if><c:if test='${CustomerTransaction.status eq "5"}'>
							<bean:message bundle="view"
								key="report.transaction.failure" />
						</c:if></TD>
					</TR>
					<TR>					
						<TD width="119"  class="text pLeft15"><STRONG><FONT
							color="#000000">
							<c:if
							test='${CustomerTransaction.transationType eq "RECHARGE"}'>
							<bean:message bundle="view"
								key="report.inStatus" />
						</c:if> <c:if test='${CustomerTransaction.transationType eq "VAS"}'>
							<bean:message bundle="view"
								key="report.inStatus" />
						</c:if> 
						<c:if test='${CustomerTransaction.transationType eq "POSTPAID_MOBILE"}'>
								<bean:message bundle="view"
									key="report.selfcareStatus" />
							</c:if> <c:if test='${CustomerTransaction.transationType eq "POSTPAID_ABTS"}'>
								<bean:message bundle="view"
									key="report.selfcareStatus" />
							</c:if><c:if test='${CustomerTransaction.transationType eq "POSTPAID_DTH"}'>
								<bean:message bundle="view"
									key="report.selfcareStatus" />
							</c:if>&nbsp;:</FONT></STRONG></TD>
						<TD width="155"><c:if test='${CustomerTransaction.inStatus == "null"}'></c:if>
							<c:if test='${CustomerTransaction.inStatus != "null"}'><bean:write
								name="CustomerTransaction" property="inStatus" /></c:if></TD>
					
						<TD width="155" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.customertransaction.reason" />&nbsp;:</FONT></STRONG></TD>
						<TD width="155" ><bean:write
							name="CustomerTransaction" property="reason" /></TD>
					</TR>
					<TR><c:if  test='${CustomerTransaction.transationType eq "POSTPAID_ABTS" or CustomerTransaction.transationType eq "POSTPAID_DTH"}'>
						<TD width="155" class="text pLeft15">
						<STRONG><FONT
							color="#000000"><bean:message bundle="view" key="report.customertransaction.confirmMobileno"/>&nbsp;:</FONT></STRONG>
							</TD><TD><c:if test='${CustomerTransaction.inStatus == "null"}'></c:if>
							<c:if test='${CustomerTransaction.inStatus != "null"}'>
						<bean:write name="CustomerTransaction" property="confirmMobileNo"/></c:if>
						</TD>
						</c:if>
					</TR>
					<TR>
						<TD class="text pLeft15"><FONT color="#000000">&nbsp;</FONT></TD>
						<TD colspan="2">
						
						<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
							<TR align="center" valign="top">
							
								<TD><INPUT class="submit" type="button" onclick="back();"
									name="Back" value="Back" tabindex="1" /></TD>
								<TD></TD>
								<html:hidden property="flagPostPaid" styleId="flagPostPaid"  />
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

