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
<LINK href="<%=request.getContextPath()%>/theme/Master.css" rel="stylesheet" type="text/css">
<LINK href="<%=request.getContextPath()%>/theme/CalendarControl.css" rel="stylesheet" type="text/css">
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/scripts/Cal.js" type="text/javascript"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>

<TITLE><bean:message bundle="view" key="application.title" /></TITLE>

<script>
	history.forward();
</script>

<SCRIPT language="javascript" type="text/javascript"> 
	
	function validate(){
		date1=document.getElementById("startDt").value;
		date2=document.getElementById("endDt").value;
		if(document.getElementById("startDt").value=="" ) {
			alert('<bean:message bundle="error" key="error.report.invalidstartdate" />');
			document.forms[0].startDt.focus();
			return false; 
		}else if(document.getElementById("endDt").value=="" ) {
			alert('<bean:message bundle="error" key="error.report.invalidenddate" />');
			document.forms[0].endDt.focus();
			return false; 
		} else  if (!(isDate2Greater(date1, date2))){
			return false;
		}			
		if(document.getElementById("searchFieldName").value=="0" ){
			alert('<bean:message bundle="error" key="error.report.invalidoption" />');
			document.forms[0].searchFieldName.focus();
			return false; 
		}else if(!(document.getElementById("searchFieldName").value=="PC") && isNull('document.forms[0]','searchFieldValue')){
			alert('<bean:message bundle="error" key="error.report.invalidaccodevalue" />');	  
			document.forms[0].searchFieldValue.focus();
			return false; 
		}else if(!(document.getElementById("searchFieldName").value=="AC") && isNull('document.forms[0]','searchFieldValue')){
			alert('<bean:message bundle="error" key="error.report.invalidparentcodevalue" />');	  
			document.forms[0].searchFieldValue.focus();
			return false; 
		}  
		document.getElementById("methodName").value="searchReportData";	
		return true;
	} // Validate Form Ends

	function setSearchControlDisabled(){
		if(document.getElementById("searchFieldName").value=="0"){ 
			document.getElementById("searchFieldValue").disabled=true;
		}else{
			document.getElementById("searchFieldValue").disabled=false;
		}
	}

	function checkKeyPressed(){
		if(window.event.keyCode=="13"){
			if(validate()){
				document.forms[0].submit();
			}else{
				return false;
			}
		}
	}

	function exportDataToExcel(){
		document.getElementById("methodName").value="exportData";
		document.forms[0].submit();
	}
	function resetAllData(){
		resetAll();
		document.getElementById("methodName").value="init";	
		document.forms[0].submit();
	}
</SCRIPT>
</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onkeypress="return checkKeyPressed();"
	onload="setSearchControlDisabled()">

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
			action="TransactionSummaryRptAction" focus="searchFieldName">
			<html:hidden property="methodName" styleId="methodName" />
			<TABLE width="680" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="5" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/TransSummaryReport.gif" width="505" height="22"
						alt=""></TD>
				</TR>

				<TR>
					<TD colspan="5" align="center"><FONT color="#ff0000" size="-2"><html:errors
						bundle="error" /></FONT></TD>
				</TR>
			</TABLE>
			<TABLE width="90%" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD class="text pLeft15"><FONT color="#000000"><STRONG><bean:message
						bundle="view" key="application.start_date" /></STRONG></FONT><FONT color="RED">*</FONT>&nbsp;
					: <html:text property="startDt" styleId="startDt" styleClass="box"
						readonly="true" size="15" maxlength="10" /><script language="JavaScript">
new tcal ({
// form name
'formname': 'TransactionRptA2AFormBean',
// input name
'controlname': 'startDt'
});

</script></TD>
					<TD class="text pLeft15" nowrap><FONT color="#000000"><STRONG><bean:message
						bundle="view" key="application.end_date" /></STRONG></FONT><FONT color="RED">*</FONT>&nbsp;:

					<html:text property="endDt" styleId="endDt" styleClass="box"
						readonly="true" size="15" maxlength="10" /><script language="JavaScript">
new tcal ({
// form name
'formname': 'TransactionRptA2AFormBean',
// input name
'controlname': 'endDt'
});

</script></TD>
					<TD class="text pLeft15" nowrap><FONT color="#000000"><STRONG><bean:message
						bundle="view" key="report.transaction_type" /></STRONG></FONT><FONT color="RED">*</FONT>&nbsp;:

					<html:select property="transactionType"
						name="TransactionRptA2AFormBean" styleClass="w130" tabindex="3">
						<!--
								<html:option value="-1"><bean:message key="report.transaction.all" bundle="view" /></html:option>
							-->
						<html:option value="RECHARGE">
							<bean:message key="report.transaction.recharge" bundle="view" />
						</html:option>
						<html:option value="VAS">
							<bean:message key="report.transaction.vas" bundle="view" />
						</html:option>

						<html:option value="POSTPAID_MOBILE">
							<bean:message key="report.transaction.postpaid.mobile" bundle="view" />
						</html:option>
							<html:option value="POSTPAID_ABTS">
							<bean:message key="report.transaction.postpaid.abts" bundle="view" />
						</html:option>
							<html:option value="POSTPAID_DTH">
							<bean:message key="report.transaction.postpaid.dth" bundle="view" />
						</html:option>
						<html:option value="QUERY">
							<bean:message key="report.transaction.query" bundle="view" />
						</html:option>
						
					</html:select></TD>
				</TR>
			</TABLE>
			<TABLE width="90%" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD class="text pLeft15" nowrap><FONT color="#000000"><STRONG><bean:message
						bundle="view" key="report.param" /></STRONG></FONT><FONT color="RED">*</FONT>&nbsp;:

					<html:select property="searchFieldName" styleId="searchFieldName"
						onchange="setSearchControlDisabled()" tabindex="4"  styleClass="w130">
						<html:option value="0">
							<bean:message bundle="view" key="application.option.select" />
						</html:option>
						<html:option value="AC">
							<bean:message bundle="view" key="account.accountCode" />
						</html:option>
						<html:option value="PC">
							<bean:message bundle="view" key="account.parentAccount" />
						</html:option>
					</html:select> <html:text property="searchFieldValue" styleClass="box"
						styleId="searchFieldValue" size="19" maxlength="20" tabindex="5" />
					&nbsp;&nbsp;&nbsp;&nbsp; <INPUT class="submit"
						onclick="return validate();" type="submit" tabindex="6"
						name="Submit" value="Submit">&nbsp; &nbsp;&nbsp; <input
						class="submit" type="button" onclick="resetAllData();" name="Submit2"
						value="Reset" tabindex="7"></TD>

				</TR>
			</TABLE>
			<TABLE width="680" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="5">
					<TABLE width="725" align="center" class="mLeft5">
						<logic:notEmpty name="TransactionRptA2AFormBean"
							property="displayDetails">
							<TR align="center">
								<TD align="left" colspan="7"><INPUT class="submit"
									onclick="exportDataToExcel();" type="submit" tabindex="8"
									name="export" value="Export to Excel"></TD>
							</TR>
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
								<TD align="center" bgcolor="#cd0504" rowspan="2"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="application.sno" /></SPAN></TD>
								<TD align="center" bgcolor="#cd0504" rowspan="2"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="report.transaction_type" /></SPAN></TD>
								<TD align="center" bgcolor="#cd0504" colspan="2"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="report.total_successful" /></SPAN></TD>
								<TD align="center" bgcolor="#cd0504" colspan="2"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="report.total_failed" /></SPAN></TD>
								<TD align="center" bgcolor="#cd0504" rowspan="2"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="report.no_total_transaction" /></SPAN></TD>
							</TR>
							<TR align="center" bgcolor="#104e8b">								
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
							</TR>
							</logic:notMatch>
							
							<logic:iterate id="transaction" name="TransactionRptA2AFormBean"
								property="displayDetails" indexId="i">
								<TR bgcolor="#fce8e6">
									<TD class="text" nowrap>1.</TD>
									<TD class="text"><bean:write name="transaction"
										property="transactionType" /></TD>
									<TD class="text" align="right"><bean:write
										name="transaction" property="noSuccessfulTransaction" /></TD>
									
									<logic:notMatch name="transaction" property="transactionType" value="QUERY">										
									<TD class="text" align="right"><bean:write
										name="transaction" property="valueSuccessfulTransaction"
										format="#0.00" /></TD>
									</logic:notMatch>	
									
									<TD class="text" align="right"><bean:write
										name="transaction" property="noFailedTransaction" /></TD>
									
									<logic:notMatch name="transaction" property="transactionType" value="QUERY">	
									<TD class="text" align="right"><bean:write
										name="transaction" property="valueFailedTransaction"
										format="#0.00" /></TD>
									</logic:notMatch>
									
									<TD class="text" align="center"><bean:write
										name="transaction" property="totalTransaction" /></TD>
						

								</TR>
							</logic:iterate>
						</logic:notEmpty>
						
						<TR>
							<TD width="20" align="left">&nbsp;&nbsp;&nbsp;</TD>
						</TR>


					</TABLE>
					</TD>

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
