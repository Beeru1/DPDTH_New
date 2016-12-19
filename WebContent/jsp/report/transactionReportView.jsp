<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html:html>
<head>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/theme/Master.css" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">

<script type="text/javascript">
	function goBack(){
	
		document.getElementById("status").value=document.getElementById("viewstatus").value;
		document.getElementById("methodName").value="searchReportData";
		
	}

</script>
</head>

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
			<html:hidden property="methodName" styleId="methodName" />
			<html:hidden property="status" name="TransactionRptA2AFormBean" styleId="status"/>
			<html:hidden property="viewstatus" name="TransactionRptA2AFormBean" styleId="viewstatus"/>
			<html:hidden property="searchFieldName" styleId="searchFieldName"
				name="TransactionRptA2AFormBean" />
			<html:hidden property="searchFieldValue" styleId="searchFieldValue"
				name="TransactionRptA2AFormBean" />
				<html:hidden property="page" styleId="page" name="TransactionRptA2AFormBean" />
			<TABLE width="545" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					<h4>View TransactionReport Detail</h4>
					</TD>
				</TR>
				<TR>
					<TD colspan="4" class="text"><FONT color="#ff0000" size="-2"><html:errors
						bundle="error" /><html:errors /></FONT></TD>
				</TR>
				<TR>
					<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message
						key="transaction.transaction_id" bundle="view" />&nbsp;:</FONT> </STRONG></TD>
					<TD width="155"><bean:write name="TransactionRptA2AFormBean"
						property="transactionId" /></TD>
					<TD width="120" nowrap><STRONG><FONT color="#000000">
					<bean:message key="sysconfig.transaction_type" bundle="view" />&nbsp;:</FONT>
					</STRONG></TD>
					<TD width="163" nowrap><bean:write
						name="TransactionRptA2AFormBean" property="transactionType" /></TD>
				</TR>
				<TR>
					<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="report.mtpcode" /></FONT>:</STRONG></TD>
					<TD width="155"><bean:write name="TransactionRptA2AFormBean"
						property="mtpAccountCode" /></TD>
					<c:if  test='${TransactionRptA2AFormBean.transactionType eq "RECHARGE" or 
									TransactionRptA2AFormBean.transactionType eq "POSTPAID_MOBILE" or
									TransactionRptA2AFormBean.transactionType eq "POSTPAID_ABTS"}'>
				 		<c:if  test='${TransactionRptA2AFormBean.transactionType eq "RECHARGE" or 
							TransactionRptA2AFormBean.transactionType eq "POSTPAID_MOBILE"}'>
					<TD class="text"><STRONG><FONT color="#000000"><bean:message
						bundle="view" key="report.customer_mobile" /></FONT>:</STRONG></TD>
						</c:if>
						<c:if  test='${TransactionRptA2AFormBean.transactionType eq "POSTPAID_ABTS"}'>
						<TD class="text"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.customer_landline" />&nbsp;:</FONT></STRONG></TD>
						</c:if>	
						<TD width="163"><bean:write name="TransactionRptA2AFormBean"
						property="customerMobile" /></TD>
					</c:if>	
					<c:if  test='${TransactionRptA2AFormBean.transactionType eq "POSTPAID_DTH"}'>									
						<TD class="text"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.customertransaction.accountId" />&nbsp;:</FONT></STRONG></TD>
						<TD width="155"><bean:write name="TransactionRptA2AFormBean"
							property="receiverAccountId" /></TD>
					</c:if>				
				</TR>
				<TR>
					<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="report.transactionamount" /></FONT>:</STRONG></TD>
					<TD width="163"><bean:write name="TransactionRptA2AFormBean"
						format="#0.00" property="transactionAmount" /></TD>
					<TD><STRONG><FONT color="#000000"><bean:message
						bundle="view" key="report.transactiondate" /></FONT>:</STRONG></TD>
					<TD width="171"><bean:write name="TransactionRptA2AFormBean" 
						formatKey="application.date.format" bundle="view" property="transactionDate" /></TD>
				</TR>
				
				<TR>
					<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="report.parent_name" /></FONT>:</STRONG></TD>
					<TD width="163"><bean:write name="TransactionRptA2AFormBean"
						 property="parentName" /></TD>
					<TD><STRONG><FONT color="#000000"><bean:message
						bundle="view" key="report.parent_name1" /></FONT>:</STRONG></TD>
					<TD width="171"><bean:write name="TransactionRptA2AFormBean"
						property="parentName1" /></TD>
				</TR>
					<TR>
					<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="report.parent_name2" /></FONT>:</STRONG></TD>
					<TD width="163"><bean:write name="TransactionRptA2AFormBean"
						 property="parentName2" /></TD>
					<TD><STRONG><FONT color="#000000"><bean:message
						bundle="view" key="report.parent_name3" /></FONT>:</STRONG></TD>
					<TD width="171"><bean:write name="TransactionRptA2AFormBean"
						property="parentName3" /></TD>
				</TR>
				<TR>
					<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"> <bean:message bundle="view"
						key="report.status" /> </FONT>:</STRONG></TD>
					<TD width="171"><logic:match name="TransactionRptA2AFormBean"
						property="status" value="0">
						<bean:message bundle="view" key="report.transaction.success" />
					</logic:match> <logic:match name="TransactionRptA2AFormBean" property="status"
						value="2">
						<bean:message bundle="view" key="report.transaction.pending" />
					</logic:match> <logic:match name="TransactionRptA2AFormBean" property="status"
						value="1">
						<bean:message bundle="view" key="report.transaction.failure" />
					</logic:match><logic:match name="TransactionRptA2AFormBean" property="status"
						value="4">
						<bean:message bundle="view" key="report.transaction.failure" />
					</logic:match><logic:match name="TransactionRptA2AFormBean" property="status"
						value="5">
						<bean:message bundle="view" key="report.transaction.failure" />
					</logic:match></TD>
					<TD><STRONG><FONT color="#000000"><bean:message
						bundle="view" key="report.reason" /></FONT>&nbsp;:</STRONG></TD>
					<TD width="171"><bean:write name="TransactionRptA2AFormBean"
						property="reason" /></TD>
				</TR>
				<TR>
					<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message key="report.service_tax"
						bundle="view" /></FONT>&nbsp;:</STRONG></TD>
					<TD width="171"><bean:write bundle="view" format="#0.00"
						name="TransactionRptA2AFormBean" property="serviceTax" /></TD>
					
					<TD><STRONG><FONT color="#000000"><STRONG><FONT
						color="#000000"><bean:message key="report.commission"
						bundle="view" /></FONT>&nbsp;:</STRONG></TD>
					<TD width="171"><bean:write bundle="view" format="#0.00"
						name="TransactionRptA2AFormBean" property="espCommission" /></TD>
					
				</TR>

				<c:if  test='${TransactionRptA2AFormBean.transactionType eq "RECHARGE"}'>

				<TR>					
					<TD width="126" class="text pLeft15"><STRONG><FONT color="#000000"><bean:message
						bundle="view" key="report.processing_fee" /></FONT>&nbsp;:</STRONG></TD>
					<TD width="171"><bean:write bundle="view" format="#0.00"
						name="TransactionRptA2AFormBean" property="processingFee" /></TD>
					
					
					<TD><STRONG><FONT color="#000000"><bean:message
						bundle="view" key="report.validity" /></FONT>&nbsp;:</STRONG></TD>
					<logic:empty name="TransactionRptA2AFormBean" property="validity">
						<TD width="171"><bean:message bundle="view"
							key="report.novalidity" /></TD>
					</logic:empty>

					<logic:notEmpty name="TransactionRptA2AFormBean"
						property="validity">
						<logic:equal name="TransactionRptA2AFormBean" property="validity"
							value="null">
							<TD width="171"><bean:message bundle="view"
								key="report.novalidity" /></TD>
						</logic:equal>
						<logic:notEqual name="TransactionRptA2AFormBean"
							property="validity" value="null">
							<TD width="171"><bean:write name="TransactionRptA2AFormBean"
								property="validity" formatKey="formatDateTime" /></TD>
						</logic:notEqual>
					</logic:notEmpty>

				</TR>
				</c:if>
				<tr>
					<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
							key="report.requestercircleId" /></FONT>&nbsp;:</STRONG></TD>
						<TD width="171"><bean:write name="TransactionRptA2AFormBean"
							property="requesterCircleId" /></TD>


					<TD><STRONG><FONT color="#000000"><bean:message bundle="view"
							key="report.subscribercircleId" />&nbsp;:</FONT></STRONG></TD>
						<TD width="171"><bean:write name="TransactionRptA2AFormBean"
							property="subscriberCircleId" /></TD>
				</tr>
				
				
					<TR>
						<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
							key="report.openingAvailableBalance" /></FONT>&nbsp;:</STRONG></TD>

						<logic:equal value="0" name="TransactionRptA2AFormBean"
							property="sourceAvailBalBeforeRecharge">
							<logic:equal name="TransactionRptA2AFormBean" property="status" value="0">
								<TD width="171"><bean:write name="TransactionRptA2AFormBean"
									property="sourceAvailBalBeforeRecharge" format="#0.00" /></TD>
							</logic:equal>
							<logic:notEqual name="TransactionRptA2AFormBean" property="status" value="0">
								<TD width="171">--</TD>
							</logic:notEqual>
						</logic:equal>
						<logic:notEqual value="0" name="TransactionRptA2AFormBean"
							property="sourceAvailBalBeforeRecharge">
							<TD width="171"><bean:write name="TransactionRptA2AFormBean"
								property="sourceAvailBalBeforeRecharge" format="#0.00" /></TD>
						</logic:notEqual>

						<TD><STRONG><FONT color="#000000"><bean:message bundle="view"
							key="report.closingAvailableBalance" />&nbsp;:</FONT></STRONG></TD>
						<logic:equal value="0" name="TransactionRptA2AFormBean"
							property="sourceAvailBalAfterRecharge">
							<logic:equal name="TransactionRptA2AFormBean" property="status" value="0">
								<TD width="171"><bean:write name="TransactionRptA2AFormBean"
									property="sourceAvailBalAfterRecharge" format="#0.00" /></TD>
							</logic:equal>
							<logic:notEqual name="TransactionRptA2AFormBean" property="status" value="0">
								<TD width="171">--</TD>
							</logic:notEqual>
						</logic:equal>
						<logic:notEqual value="0" name="TransactionRptA2AFormBean"
							property="sourceAvailBalAfterRecharge">
							<TD width="171"><bean:write name="TransactionRptA2AFormBean"
								property="sourceAvailBalAfterRecharge" format="#0.00" /></TD>
						</logic:notEqual>


					</TR>

				
				
				<TR>

					<logic:match  name="TransactionRptA2AFormBean" property="transactionType" value="RECHARGE">	
					<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="report.talktime" /></FONT> :</STRONG></TD>
					</logic:match>	
					<logic:notMatch name="TransactionRptA2AFormBean" property="transactionType" value="RECHARGE">
						<TD width="126" class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.customertransaction.creditamount" />&nbsp;:</FONT></STRONG></TD>
					</logic:notMatch>
					<TD width="171"><bean:write bundle="view" format="#0.00"
						name="TransactionRptA2AFormBean" property="talkTime" /></TD>
					<TD><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="report.debitAmount" /></FONT> :</STRONG></TD>
					<TD width="171"><bean:write bundle="view" format="#0.00"
						name="TransactionRptA2AFormBean" property="debitAmount" /></TD>
				</TR>
				<TR>
					<c:if  test='${TransactionRptA2AFormBean.transactionType eq "RECHARGE"}'>
						<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view" key="report.inStatus" 
						/></FONT></STRONG></TD>	
						<TD width="171"><bean:write
							name="TransactionRptA2AFormBean" property="inStatus" /> </TD>
						<TD  class="text"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="report.customertransaction.cardGroup" />&nbsp;:</FONT></STRONG></TD>
						<logic:empty name="TransactionRptA2AFormBean" property="cardGroup">
							<TD width="155">--</TD>
						</logic:empty>
						<logic:notEmpty name="TransactionRptA2AFormBean" property="cardGroup">
							<TD width="155"><bean:write name="TransactionRptA2AFormBean"
								property="cardGroup" /></TD>
						</logic:notEmpty>						
					</c:if>	
					<c:if  test='${TransactionRptA2AFormBean.transactionType eq "POSTPAID_MOBILE" or 
									TransactionRptA2AFormBean.transactionType eq "POSTPAID_ABTS" or
									TransactionRptA2AFormBean.transactionType eq "POSTPAID_DTH"}'>
						<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message
							bundle="view" key="report.selfcareStatus" /></FONT></STRONG></TD>
						<TD width="171">
						<logic:notMatch name="TransactionRptA2AFormBean" property="selfcareStatus" value="null">
						<bean:write name="TransactionRptA2AFormBean" property="selfcareStatus" />
						</logic:notMatch>
						 </TD>								
										
					</c:if>		
				</TR>	
				<TR>
					<TD width="171"></TD>
				</TR>
				<TR>
					<TD class="text pLeft15"><FONT color="#000000">&nbsp;</FONT></TD>
					<TD colspan="2">
					<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
						<TR align="center" valign="top">

							<TD><INPUT class="submit" type="submit" tabindex="10"
								name="BACK" value="Back" onclick="goBack()"></TD>
							<TD></TD>
						</TR>
					</TABLE>
					</TD>
				</TR>

				<TR>
					<TD colspan="4" align="center">&nbsp; <html:hidden
						property="startDt" styleId="startDt"
						name="TransactionRptA2AFormBean" /> <html:hidden property="endDt"
						styleId="endDt" name="TransactionRptA2AFormBean" /> <html:hidden
						property="transactionType" name="TransactionRptA2AFormBean" /></TD>


				</TR>

				<TR>
					<TD colspan="4" align="center">&nbsp;</TD>
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
