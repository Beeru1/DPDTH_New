
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
</script>
<SCRIPT type="text/javascript">

	// This function will enable/Disabale the transactionId & Mobile No textboxes.
	function checkSearchCriteria(){
		var form = document.forms[0];
		if(document.getElementById("searchCriteria").value==0){
			form.searchCriteria.focus();
			document.getElementById("mobileNoDiv").style.display="block";
			document.getElementById("transactionIdDiv").style.display="none";
			document.getElementById("abtsNoDiv").style.display="none";
			document.getElementById("dthNoDiv").style.display="none";
			document.getElementById("div1").style.display="block";
			document.getElementById("mobileNo").readOnly = "readonly";		
			document.getElementById("mobileNo").value="";	
			document.getElementById("transactionId").value="";
			return false; 
		}	
		else if(document.getElementById("searchCriteria").value=="MOBILE"){
			document.getElementById("mobileNoDiv").style.display="block";
			document.getElementById("transactionIdDiv").style.display="none";
			document.getElementById("abtsNoDiv").style.display="none";
			document.getElementById("dthNoDiv").style.display="none";
			document.getElementById("div1").style.display="block";
			document.getElementById("mobileNo").readOnly = false;
			document.getElementById("transactionId").value="";
			if(document.getElementById("transactionType").value=="POSTPAID_ABTS" || document.getElementById("transactionType").value=="POSTPAID_DTH"){
				alert('<bean:message bundle="error" key="error.customertransaction.correct_transactiontype" />');  
				document.getElementById("transactionType").focus();
				return false; 
			}
		}
		else if(document.getElementById("searchCriteria").value=="ABTS"){
			document.getElementById("mobileNoDiv").style.display="none";
			document.getElementById("transactionIdDiv").style.display="none";
			document.getElementById("abtsNoDiv").style.display="block";
			document.getElementById("dthNoDiv").style.display="none";
			document.getElementById("div1").style.display="block";
			document.getElementById("mobileNo").readOnly = false;
			document.getElementById("transactionId").value="";
			if(document.getElementById("transactionType").value=="RECHARGE" || document.getElementById("transactionType").value=="POSTPAID_DTH" || document.getElementById("transactionType").value=="VAS" || document.getElementById("transactionType").value=="POSTPAID_MOBILE"){
				alert('<bean:message bundle="error" key="error.customertransaction.correct_transactiontype" />');  
				document.getElementById("transactionType").focus();
				return false; 
			}
		}
		else if(document.getElementById("searchCriteria").value=="DTH"){
			document.getElementById("mobileNoDiv").style.display="none";
			document.getElementById("transactionIdDiv").style.display="none";
			document.getElementById("abtsNoDiv").style.display="none";
			document.getElementById("dthNoDiv").style.display="block";
			document.getElementById("div1").style.display="block";
			document.getElementById("mobileNo").readOnly = false;
			document.getElementById("transactionId").value="";
			if(document.getElementById("transactionType").value=="RECHARGE" || document.getElementById("transactionType").value=="POSTPAID_ABTS"|| document.getElementById("transactionType").value=="VAS" || document.getElementById("transactionType").value=="POSTPAID_MOBILE"){
				alert('<bean:message bundle="error" key="error.customertransaction.correct_transactiontype" />');  
				document.getElementById("transactionType").focus();
				return false; 
			}
		}
		else{
			document.getElementById("mobileNoDiv").style.display="none";
			document.getElementById("transactionIdDiv").style.display="block";
			document.getElementById("abtsNoDiv").style.display="none";
			document.getElementById("dthNoDiv").style.display="none";
			document.getElementById("div1").style.display="none";
			document.getElementById("transactionType").value="0";
			document.getElementById("mobileNo").readOnly = "readonly";
			document.getElementById("mobileNo").value="";
		}	
		//checkTransactionId();	
	
	}

	function findForTransId(transId){
	
		document.getElementById("transDetailId").value=transId;
		document.getElementById("methodName").value="searchCustomerTransWithId";
		document.forms[0].action="CustomerTransaction.do?methodName=searchCustomerTransWithId";
		document.forms[0].method="post";
		document.forms[0].submit();
		return true;
	}

	function submitData(nextPage,transactionId,transactionType,startDate,endDate){
		document.forms[0].action="CustomerTransaction.do?methodName=searchCustomerTrans&page="
				+ nextPage + "&transactionId=" + transactionId + "&transactionType="
				+ transactionType  + "&startDate=" 
				+ startDate + "&endDate=" + endDate;
		document.getElementById("page").value="";	
		document.forms[0].method="post";
		document.forms[0].submit();
	}
	
	function validate(){ 
		var form = document.forms[0];
		var date1 = document.forms[0].startDate.value;
		var date2 = document.forms[0].endDate.value;
		if(document.getElementById("searchCriteria").value==0){
			if(document.getElementById("transactionType").value==0){
				alert('<bean:message bundle="error" key="error.customertransaction.transactiontype_required" />');  
				document.getElementById("transactionType").focus();
				return false; 
			}else if(document.getElementById("startDate").value==""){
				alert('<bean:message bundle="error" key="error.report.invalidstartdate" />');	  
				form.startDate.focus();
				return false; 
			}else if(document.getElementById("endDate").value==""){
				alert('<bean:message bundle="error" key="error.report.invalidenddate" />');	  
				form.endDate.focus();
				return false; 	 
			} 
		}else if(document.getElementById("searchCriteria").value=="MOBILE"){
			document.getElementById("mobileNo").value = trim(document.getElementById("mobileNo").value);
			if(document.getElementById("transactionType").value==0){
				alert('<bean:message bundle="error" key="error.customertransaction.transactiontype_required" />');  
				document.getElementById("transactionType").focus();
				return false; 
			}else if(document.getElementById("transactionType").value=="POSTPAID_ABTS" || document.getElementById("transactionType").value=="POSTPAID_DTH"){
				alert('<bean:message bundle="error" key="error.customertransaction.correct_transactiontype_required" />');  
				document.getElementById("searchCriteria").focus();
				return false; 
			}else if(document.getElementById("startDate").value==""){
				alert('<bean:message bundle="error" key="error.report.invalidstartdate" />');	  
				form.startDate.focus();
				return false; 
			}else if(document.getElementById("endDate").value==""){
				alert('<bean:message bundle="error" key="error.report.invalidenddate" />');	  
				form.endDate.focus();
				return false; 	 
			}else if(document.getElementById("mobileNo").value==""){
				alert('<bean:message bundle="error" key="errors.transaction.customerMobileNo" />');	 
				document.getElementById("mobileNo").focus();
				return false;  
			}else if((document.getElementById("mobileNo").value.length)<10){
				alert('<bean:message bundle="error" key="error.account.mobilenumberlength" />');
				document.getElementById("mobileNo").focus();
				return false;
			}
			
		}else if(document.getElementById("searchCriteria").value=="ABTS"){
			document.getElementById("abtsNo").value = trim(document.getElementById("abtsNo").value);
			if(document.getElementById("transactionType").value==0){
				alert('<bean:message bundle="error" key="error.customertransaction.transactiontype_required" />');  
				document.getElementById("transactionType").focus();
				return false; 
			}else if(document.getElementById("transactionType").value=="RECHARGE" || document.getElementById("transactionType").value=="POSTPAID_DTH" || document.getElementById("transactionType").value=="VAS" || document.getElementById("transactionType").value=="POSTPAID_MOBILE"){
				alert('<bean:message bundle="error" key="error.customertransaction.correct_transactiontype_required" />');  
				document.getElementById("searchCriteria").focus();
				return false; 
			}else if(document.getElementById("startDate").value==""){
				alert('<bean:message bundle="error" key="error.report.invalidstartdate" />');	  
				form.startDate.focus();
				return false; 
			}else if(document.getElementById("endDate").value==""){
				alert('<bean:message bundle="error" key="error.report.invalidenddate" />');	  
				form.endDate.focus();
				return false; 	 
			}else if(document.getElementById("abtsNo").value==""){
				alert('<bean:message bundle="error" key="errors.transaction.AbtsNo" />');	 
				document.getElementById("abtsNo").focus();
				return false;  
			}			
		}else if(document.getElementById("searchCriteria").value=="DTH"){
			document.getElementById("abtsNo").value = trim(document.getElementById("abtsNo").value);
			if(document.getElementById("transactionType").value==0){
				alert('<bean:message bundle="error" key="error.customertransaction.transactiontype_required" />');  
				document.getElementById("transactionType").focus();
				return false; 
			}else if(document.getElementById("transactionType").value=="RECHARGE" || document.getElementById("transactionType").value=="POSTPAID_ABTS" || document.getElementById("transactionType").value=="VAS" || document.getElementById("transactionType").value=="POSTPAID_MOBILE"){
				alert('<bean:message bundle="error" key="error.customertransaction.correct_transactiontype_required" />');  
				document.getElementById("searchCriteria").focus();
				return false; 
			}else if(document.getElementById("startDate").value==""){
				alert('<bean:message bundle="error" key="error.report.invalidstartdate" />');	  
				form.startDate.focus();
				return false; 
			}else if(document.getElementById("endDate").value==""){
				alert('<bean:message bundle="error" key="error.report.invalidenddate" />');	  
				form.endDate.focus();
				return false; 	 
			}else if(document.getElementById("dthNo").value==""){
				alert('<bean:message bundle="error" key="errors.transaction.DthNo" />');	 
				document.getElementById("dthNo").focus();
				return false;  
			}			
		}else{
			document.getElementById("transactionId").value = trim(document.getElementById("transactionId").value);
			if(document.getElementById("transactionId").value==""){
				alert('<bean:message bundle="error" key="errors.transaction.transactionId" />');	
				document.getElementById("mobileNo").focus();
				return false;   
			}
		}
		if (!(isDate2Greater(date1, date2))){
				return false;
		}else {
			document.getElementById("page").value="";	
			document.getElementById("methodName").value="searchCustomerTrans";	
			return true;
			}
	} // function validate ends

	function checkTransactionId(){
		if(document.getElementById("transactionId").value!=""){
			// hide div
			document.getElementById("div1").style.display="none";
			//document.getElementById("transactionType").value="0";
		}else{
			// un-hide div
			document.getElementById("div1").style.display="block";
		}
	}

	function checkTransactionType(){
		if(document.getElementById("transactionType").value!="0"){
			document.getElementById("transactionId").value ="";
			document.getElementById("transactionId").readonly = true;
		}else{
			document.getElementById("transactionId").readonly = false;
		}
	}

	/* This function lets the user to dowanload the displayed rows. */
	function exportDataToExcel(){
		document.getElementById("methodName").value="downloadCustomerTransList";
		document.forms[0].submit();
	}
	
	function checkKeyPressed(){
		if(window.event.keyCode=="13"){
			if(validate()){
				document.forms[0].submit();
				return true;
			}
			return false;	
		}
	}

	// set focus on status control  
	function doLoad(){
		document.forms[0].transactionId.focus();
		checkSearchCriteria();
	}
	function resetAllData(){
		resetAll();
		document.getElementById("methodName").value="callCustomerTrans";	
		document.forms[0].submit();
	}

</SCRIPT>

</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onkeypress="return checkKeyPressed();"
	onload="javascript:doLoad();">

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
			<html:hidden property="page" styleId="page" />
			<TABLE border="0" cellpadding="5" cellspacing="0" class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					<IMG
						src="<%=request.getContextPath()%>/images/viewCustTransReport.gif"
						width="505" height="22" alt=""></TD>
				</TR>

				<TR>
					<TD>
					<DIV id="div1" style="display:none">
					<TABLE class="text" border="0">
						<TR>
							<TD width="267"><STRONG><FONT color="#000000"><bean:message
								bundle="view" key="sysconfig.transaction_type" /></FONT></STRONG> <FONT
								color="RED">*</FONT> <html:select property="transactionType"
								styleClass="w130" tabindex="1" styleId="transactionType"
								onchange="checkTransactionType();">
								<html:option  value="0" >
									<bean:message key="application.option.select" bundle="view" />
								</html:option>
								<html:option value="RECHARGE">
									<bean:message bundle="view"
										key="sysconfig.transaction_type.recharge" />
								</html:option>
								<html:option value="VAS">
									<bean:message bundle="view"
										key="sysconfig.transaction_type.vas" />
								</html:option>
								<html:option value="POSTPAID_MOBILE">
									<bean:message key="report.transaction.postpaid.mobile"
										bundle="view" />
								</html:option>
								<html:option value="POSTPAID_ABTS">
									<bean:message key="report.transaction.postpaid.abts"
										bundle="view" />
								</html:option>
								<html:option value="POSTPAID_DTH">
									<bean:message key="report.transaction.postpaid.dth"
										bundle="view" />
								</html:option>
							</html:select></TD>

							<td><STRONG><FONT color="#000000"><bean:message
								bundle="view" key="application.start_date" /></FONT></STRONG> <FONT color="RED">*</FONT></td>
							<TD><html:text property="startDate" styleId="startDate"
								styleClass="box" readonly="true" size="15" maxlength="10" /> <script language="JavaScript">
new tcal ({
// form name
'formname': 'CustomerTransaction',
// input name
'controlname': 'startDate'
});

</script></TD>

							<td><STRONG><FONT color="#000000"><bean:message
								bundle="view" key="application.end_date" /></FONT></STRONG> <FONT color="RED">*</FONT>
							</td>
							<TD><html:text property="endDate" styleId="endDate"
								styleClass="box" readonly="true" size="15" maxlength="10" /> <script language="JavaScript">
new tcal ({
// form name
'formname': 'CustomerTransaction',
// input name
'controlname': 'endDate'
});

</script></TD>
						</TR>


					</TABLE>
					</DIV>


					</TD>





				</TR>

			</TABLE>

			<TABLE>
				<TR>

					<td>

					<TABLE class="text" border="0">
						<TR>
							<TD width="250"><STRONG><FONT color="#000000"><bean:message
								bundle="view" key="report.customertransaction.Search" /></FONT></STRONG> <html:select
								property="searchCriteria"  name="CustomerTransaction"  styleClass="w130" tabindex="4"
								styleId="searchCriteria" onchange="return checkSearchCriteria();" >
								<html:option value="0">
									<bean:message key="application.option.select_all" bundle="view" />
								</html:option>
								<html:option value="MOBILE">
									<bean:message bundle="view"
										key="report.customertransaction.mobileno" />
								</html:option>
								
								<html:option value="ABTS">
									<bean:message bundle="view"
										key="report.customertransaction.LandlineNo" />
								</html:option>
								
								<html:option value="DTH">
									<bean:message bundle="view"
										key="report.customertransaction.DTHNo" />
								</html:option>
								<html:option value="TRANSACTIONID">
									<bean:message bundle="view"
										key="report.customertransaction.transactionID" />
								</html:option>
							</html:select></TD>
						</TR>
					</TABLE>

					</td>
					<TD>
					<div id="transactionIdDiv" style="display:block" class="text">
					<STRONG><FONT color="#000000">&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message
						bundle="view" key="transaction.transaction_id" /></FONT></STRONG> <FONT
						color="#003399"><html:text property="transactionId"
						styleClass="box" styleId="transactionId" size="20" maxlength="15"
						name="CustomerTransaction" tabindex="5"
						onchange="checkTransactionId();" onkeypress="isValidNumber()"
						onblur="checkTransactionId();" /></FONT></div>
					<div id="mobileNoDiv" style="display:none" class="text"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="report.customertransaction.mobileno" /></FONT></STRONG>&nbsp;<FONT
						color="#003399"><html:text property="mobileNo"
						styleClass="box" styleId="mobileNo" size="20" maxlength="10"
						name="CustomerTransaction" tabindex="5"
						onkeypress="isValidNumber()" /></FONT></div>
					<div id="abtsNoDiv" style="display:none" class="text"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="report.customertransaction.LandlineNo" /></FONT></STRONG>&nbsp;<FONT
						color="#003399"><html:text property="abtsNo"
						styleClass="box" styleId="abtsNo" size="20" maxlength="11"
						name="CustomerTransaction" tabindex="5"
						onkeypress="isValidNumber()" /></FONT></div>
					<div id="dthNoDiv" style="display:none" class="text"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="report.customertransaction.DTHNo" /></FONT></STRONG>&nbsp;<FONT
						color="#003399"><html:text property="dthNo"
						styleClass="box" styleId="dthNo" size="20" maxlength="10"
						name="CustomerTransaction" tabindex="5"
						onkeypress="isValidNumber()" /></FONT></div>
					</TD>
					<TD width="234"><INPUT class="submit"
						onclick="return validate();" type="submit" tabindex="6"
						id="Submit" name="Submit" value="Submit">&nbsp;&nbsp;&nbsp;<INPUT
						class="submit" type="button" onclick="resetAllData();"
						name="Submit2" value="Reset" tabindex="7"></TD>

				</TR>
				<TR>
					<TD colspan="4"><FONT color="#ff0000" size="-2"><html:errors
						bundle="error" /> </FONT> <FONT color="#ff0000"><html:errors
						bundle="view" /> </FONT></TD>
				</TR>

			</TABLE>

			<TABLE width="100%" align="center" class="mLeft5">
				<logic:notEmpty name="CustomerTransaction" property="displayDetails">

					<TR align="center">
						<TD align="left" colspan="12"><INPUT class="submit"
							onclick="exportDataToExcel();" type="button" name="export"
							value="Export to Excel" tabindex="8"></TD>
					</TR>
					<TR align="center" bgcolor="#104e8b">
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
							class="white10heading mLeft5 mTop5">
							<c:if  test='${CustomerTransaction.transationType eq "RECHARGE" or CustomerTransaction.transationType eq "POSTPAID_MOBILE" or CustomerTransaction.transationType eq "VAS"}'>
							<bean:message
							key="report.customertransaction.mobileno" bundle="view" /></c:if>
							<c:if  test='${CustomerTransaction.transationType eq "POSTPAID_ABTS"}'>
							<bean:message
							key="report.customertransaction.LandlineNo" bundle="view" /></c:if>
							<c:if  test='${CustomerTransaction.transationType eq "POSTPAID_DTH"}'>
							<bean:message
							key="report.customertransaction.DTHNo" bundle="view" /></c:if>
							</SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							key="transactiontype.transaction_amount" bundle="view" /></SPAN></TD>
						<logic:notEmpty name="CustomerTransaction"
							property="transactionId">
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								key="topup.service_tax" bundle="view" /></SPAN></TD>

							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="topup.processing_fee" /></SPAN></TD>

							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.customertransaction.commission" /></SPAN></TD>
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="report.talktime" /></SPAN></TD>

						</logic:notEmpty>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="report.customertransaction.transactiondate" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.status" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5">
							<logic:equal name="CustomerTransaction" property="transactionType" value="RECHARGE"><bean:message bundle="view"
									key="report.inStatus" /></logic:equal>
							<logic:equal name="CustomerTransaction" property="transactionType" value="POSTPAID_MOBILE"><bean:message bundle="view"
									key="report.selfcareStatus" /></logic:equal>
							<logic:equal name="CustomerTransaction" property="transactionType" value="POSTPAID_ABTS"><bean:message bundle="view"
									key="report.selfcareStatus" /></logic:equal>
							<logic:equal name="CustomerTransaction" property="transactionType" value="POSTPAID_DTH"><bean:message bundle="view"
									key="report.selfcareStatus" /></logic:equal>
							<logic:equal name="CustomerTransaction" property="transactionType" value="VAS"><bean:message bundle="view"
									key="report.inStatus" /></logic:equal>
							</SPAN></TD>
						
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="report.customertransaction.reason" /></SPAN></TD>
						<c:if  test='${CustomerTransaction.transationType eq "RECHARGE" or CustomerTransaction.transationType eq "VAS"}'>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="report.customertransaction.validity" /></SPAN></TD></c:if>
						<logic:empty name="CustomerTransaction" property="transactionId">
							<TD align="center" bgcolor="#cd0504"><SPAN
								class="white10heading mLeft5 mTop5"><bean:message
								bundle="view" key="transaction.list.detail" /></SPAN></TD>
						</logic:empty>
					</TR>

					<logic:iterate id="transBean" name="CustomerTransaction"
						property="displayDetails" indexId="i">
						<TR align="center" bgcolor="#fce8e6">
							<TD class="text" align="center"><bean:write name="transBean"
								property="rowNum" /></TD>
							<TD class="height19" align="center"><SPAN	class="mLeft5 mTop5 mBot5 black10"> <bean:write
								name="transBean" property="transactionId" />
							<TD class=" height19" align="left"><SPAN
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
								<bean:message bundle="view"
									key="report.transaction.postpaid.abts" />
							</c:if><c:if test='${transBean.transationType eq "POSTPAID_DTH"}'>
								<bean:message bundle="view"
									key="report.transaction.postpaid.dth" />
							</c:if></SPAN></TD>

							<TD class="height19" align="center"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="transBean" property="sourceAccountCode" /></SPAN></TD>
							<TD class="height19" align="center"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="transBean" property="customerMobileNo" /></SPAN></TD>
							<TD class="height19" align="right"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="transBean" format="#0.00" property="transactionAmount" /></SPAN></TD>
							<logic:notEmpty name="CustomerTransaction"
								property="transactionId">
								<logic:notEqual name="transBean" property="transationType"
									value="RECHARGE">
									<TD class="height19" align="center"><SPAN
										class="mLeft5 mTop5 mBot5 black10"> -- </SPAN></TD>
								</logic:notEqual>
								<logic:equal name="transBean" property="transationType"
									value="RECHARGE">
									<TD class="height19" align="right"><SPAN
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
									<TD class="height19" align="right"><SPAN
										class="mLeft5 mTop5 mBot5 black10"> <bean:write
										bundle="view" format="#0.00" name="transBean"
										property="processingFee" /> </SPAN></TD>
								</logic:equal>

								<TD class="height19" align="right"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><bean:write
									name="transBean" format="#0.00" property="espCommission" /></SPAN></TD>

								<logic:equal name="transBean" property="transationType"
									value="RECHARGE">
									<TD class="height19" align="right"><SPAN
										class="mLeft5 mTop5 mBot5 black10"> <bean:write
										bundle="view" format="#0.00" name="transBean"
										property="talkTime" /> </SPAN></TD>
								</logic:equal>
								<logic:equal name="transBean" property="transationType"
									value="POSTPAID">
									<TD class="height19" align="right"><SPAN
										class="mLeft5 mTop5 mBot5 black10"> <bean:write
										bundle="view" format="#0.00" name="transBean"
										property="talkTime" /> </SPAN></TD>
								</logic:equal>
								<logic:equal name="transBean" property="transationType"
									value="VAS">
									<TD class="height19" align="center"><SPAN
										class="mLeft5 mTop5 mBot5 black10"> -- </SPAN></TD>
								</logic:equal>
								<logic:notEqual name="transBean" property="transationType"
									value="RECHARGE">
									<TD class="height19" align="center"><SPAN
										class="mLeft5 mTop5 mBot5 black10"> -- </SPAN></TD>
								</logic:notEqual>


							</logic:notEmpty>
							<TD class="height19" align="center" nowrap><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="transBean" property="transactionDate"
								formatKey="application.date.format" bundle="view" /></SPAN></TD>
							<TD class="height19" align="center"><SPAN
								class="mLeft5 mTop5 mBot5 black10"> <c:if
								test='${transBean.status eq "0"}'>
								<bean:message bundle="view" key="report.transaction.success" />
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

							<TD class="text" align="center" nowrap><c:if test='${transBean.inStatus == "null"}'></c:if>
							<c:if test='${transBean.inStatus != "null"}'><bean:write
								name="transBean" property="inStatus" /></c:if>
							</TD>

							<TD class="height19" align="left"><SPAN
								class="mLeft5 mTop5 mBot5 black10"> <bean:write
								name="transBean" property="reason" /> </SPAN></TD>
							<c:if  test='${CustomerTransaction.transationType eq "RECHARGE" or CustomerTransaction.transationType eq "VAS"}'>
							<logic:empty name="transBean" property="validity">
							
								<TD class="text" align="center"><bean:message bundle="view"
									key="report.novalidity" /></TD>
							</logic:empty>

							<logic:notEmpty name="transBean" property="validity">
								<logic:equal name="transBean" property="validity" value="null">
									<TD class="text" align="center"><bean:message
										bundle="view" key="report.novalidity" /></TD>
								</logic:equal>
								<logic:notEqual name="transBean" property="validity"
									value="null">
									<TD class="text" align="center"><bean:write
										name="transBean" property="validity"
										formatKey="formatDateTime" /></TD>
								</logic:notEqual>
							</logic:notEmpty></c:if>
		
							<logic:empty name="CustomerTransaction" property="transactionId">
								<TD class="height19" align="center"><input type="button"
									onclick="return findForTransId('${transBean.transactionId}')"
									value="<bean:message bundle="view"
									key="application.detail" />"
									class="submit" /></TD>
							</logic:empty>
						</TR>

					</logic:iterate>

					<TR align="center">

						<TD align="center" bgcolor="#daeefc" colspan="13"><FONT
							face="verdana" size="2"> <c:if test="${PAGES!=''}">
							<c:if test="${PAGES>1}">

								    	Page:
					    	   <c:if test="${PRE>=1}">
									<A href="#"
										onclick="submitData('<c:out value='${PRE}' />','<c:out value='${transactionId}' />','<c:out value='${transactionType}' />','<c:out value='${startDate}' />','<c:out value='${endDate}' />');"><
									Prev</A>
								</c:if>

								<c:if test="${PAGES>LinksPerPage}">
									<c:set var="start_page" value="1" scope="request" />
									<c:if test="${SELECTED_PAGE+1>LinksPerPage}">
										<c:set var="start_page"
											value="${SELECTED_PAGE-(LinksPerPage/2)}" scope="request" />
									</c:if>
									<c:if test="${SELECTED_PAGE+(LinksPerPage/2)-1>=PAGES}">
										<c:set var="start_page" value="${PAGES-LinksPerPage+1}"
											scope="request" />
									</c:if>
									<c:set var="end_page" value="${start_page+LinksPerPage-1}"
										scope="request" />
									<c:forEach var="item" step="1" begin="${start_page}"
										end="${end_page}">
										<c:choose>
											<c:when test="${item==(NEXT-1)}">
												<c:out value="${item}"></c:out>
											</c:when>
											<c:otherwise>
												<A href="#"
													onclick="submitData('<c:out value="${item}"/>','<c:out value='${transactionId}' />','<c:out value='${transactionType}' />','<c:out value='${startDate}' />','<c:out value='${endDate}' />');"><c:out
													value="${item}" /></A>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:if>

								<c:if test="${PAGES<=LinksPerPage}">
									<c:forEach var="item" step="1" begin="1" end="${PAGES}">
										<c:choose>
											<c:when test="${item==(NEXT-1)}">
												<c:out value="${item}"></c:out>
											</c:when>
											<c:otherwise>
												<A href="#"
													onclick="submitData('<c:out value="${item}"/>','<c:out value='${transactionId}' />','<c:out value='${transactionType}' />','<c:out value='${startDate}' />','<c:out value='${endDate}' />');"><c:out
													value="${item}" /></A>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:if>

								<c:if test="${NEXT<=PAGES}">
									<A href="#"
										onclick="submitData('<c:out value='${NEXT}' />','<c:out value='${transactionId}' />','<c:out value='${transactionType}' />','<c:out value='${startDate}' />','<c:out value='${endDate}' />');">Next></A>
								</c:if>

							</c:if>
						</c:if></FONT></TD>

					</TR>
				</logic:notEmpty>
				<html:hidden styleId="transDetailId" property="transDetailId" />
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

