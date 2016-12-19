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
<LINK href="<%=request.getContextPath()%>/theme/CalendarControl.css"
	rel="stylesheet" type="text/css">
<LINK href="<%=request.getContextPath()%>/theme/Master.css"
	rel="stylesheet" type="text/css">
<SCRIPT language="JavaScript"
	src="<%=request.getContextPath()%>/scripts/Cal.js"
	type="text/javascript"></SCRIPT>
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/GeneralValidation.js"></SCRIPT>
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<script>
	history.forward();
</script>

<SCRIPT language="javascript" type="text/javascript"><!--
	
	function submitData(nextPage,searchType,searchValue,transactionType,status,startDate,endDate){
		document.forms[0].action="TransactionRptAction.do?methodName=searchReportData&page="
				+ nextPage + "&searchType=" + searchType + "&searchValue="
				+ searchValue+ "&transactionType=" + transactionType
				+ "&status=" + status + "&startDate=" 
				+ startDate + "&endDate=" + endDate;
		document.getElementById("page").value="";	
		document.forms[0].method="post";
		document.forms[0].submit();
	}
	
	function validate(){
		date1=document.getElementById("startDt").value;
		date2=document.getElementById("endDt").value;
		if(document.getElementById("startDt").value=="" ) {
			alert('<bean:message bundle="error" key="error.report.invalidstartdate" />');
			document.forms[0].startDt.focus();
			return false; 
		} else if(document.getElementById("endDt").value=="" ) {
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
	  	document.getElementById("page").value="";	
		document.getElementById("methodName").value="searchReportData";
		return true;
	}  // Validate Form Ends

	function viewDetails(transactionId){
	      
		   var form = document.forms[0];
	       document.getElementById("methodName").value="getViewTransactionReport";
		   document.getElementById("transactionId").value=transactionId;
	       form.submit(); 
	       
	}//viewDetails ends here
	
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

--></SCRIPT>
</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onkeypress="return checkKeyPressed();"
	onload="setSearchControlDisabled();">

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
			action="/TransactionRptAction" focus="searchFieldName">
			<html:hidden property="methodName" styleId="methodName" />
			<html:hidden property="transactionId" styleId="transactionId" />
			<html:hidden property="page" styleId="page" />
			<TABLE width="680" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="6" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/TransReport.gif"
						width="505" height="22" alt=""><BR>
					</TD>
				</TR>

				<TR>
					<TD class="text pLeft15" nowrap><FONT color="#000000"><STRONG><bean:message
						bundle="view" key="application.start_date" /></STRONG></FONT><FONT color="RED">*</FONT>&nbsp;:

					<html:text property="startDt" styleId="startDt" styleClass="box"
						readonly="true" size="15" maxlength="10" /> <script language="JavaScript">
new tcal ({
// form name
'formname': 'TransactionRptA2AFormBean',
// input name
'controlname': 'startDt'
});

</script>

					<TD class="text pLeft15" nowrap><FONT color="#000000"><STRONG><bean:message
						bundle="view" key="application.end_date" /></STRONG></FONT><FONT color="RED">*</FONT>&nbsp;:

					<html:text property="endDt" styleId="endDt" styleClass="box"
						readonly="true" size="15" maxlength="10" /> <script language="JavaScript">
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

						<html:option value="RECHARGE">
							<bean:message key="report.transaction.recharge" bundle="view" />
						</html:option>
						<html:option value="VAS">
							<bean:message key="report.transaction.vas" bundle="view" />
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
							<bean:message key="report.transaction.postpaid.dth" bundle="view" />
						</html:option>
					</html:select></TD>
					<TD class="text pLeft15" nowrap><FONT color="#000000"><STRONG><bean:message
						bundle="view" key="report.status" /></STRONG></FONT><FONT color="RED">*</FONT>&nbsp;:

					<html:select property="status" name="TransactionRptA2AFormBean"
						style="width:85" styleClass="w130" tabindex="4">
						<html:option value="0">
							<bean:message key="report.transaction.success" bundle="view" />
						</html:option>
						<html:option value="2">
							<bean:message key="report.transaction.pending" bundle="view" />
						</html:option>
						<html:option value="1">
							<bean:message key="report.transaction.failure" bundle="view" />
						</html:option>
					</html:select></TD>

				</TR>
			</TABLE>
			<TABLE width="680" border="0" cellpadding="5" cellspacing="0"
				class="text">

				<TR>
					<TD class="text pLeft15" nowrap><FONT color="#000000"><STRONG><bean:message
						bundle="view" key="report.param" /></STRONG></FONT><FONT color="RED">*</FONT>&nbsp;:

					<html:select property="searchFieldName" styleId="searchFieldName"
						onchange="setSearchControlDisabled()" tabindex="5"
						styleClass="w130">
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
						styleId="searchFieldValue" size="15" maxlength="20" tabindex="6" />
					&nbsp;&nbsp;&nbsp;&nbsp; <INPUT class="submit"
						onclick="return validate();" type="submit" tabindex="7"
						name="Submit" value="Submit">&nbsp;&nbsp;&nbsp;&nbsp; <INPUT
						class="submit" type="button" onclick="resetAllData();"
						name="Submit2" value="Reset" tabindex="8"></TD>
				</TR>
			</TABLE>

			<TABLE width="680" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="6" align="center"><FONT color="#ff0000"><html:errors
						bundle="error" /></FONT></TD>
				</TR>
				<TR>
					<TD colspan="6">
					<TABLE width="725" align="center" class="mLeft5">
						<logic:notEmpty name="TransactionRptA2AFormBean"
							property="displayDetails">
							<TR align="center">
								<TD align="left" colspan="13"><INPUT class="submit"
									onclick="exportDataToExcel();" type="submit" tabindex="7"
									name="export" value="Export to Excel" tabindex="9"></TD>
							</TR>

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
							<c:if  test='${transactionType eq "RECHARGE" or 
									transactionType eq "POSTPAID_MOBILE" or
									transactionType eq "POSTPAID_ABTS" }'>
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
								<TD align="center" bgcolor="#cd0504"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="report.transactiondate" /></SPAN></TD>
								<TD align="center" bgcolor="#cd0504"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="report.status" /></SPAN></TD>
								<TD align="center" bgcolor="#cd0504"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="report.reason" /></SPAN></TD>

							<c:if  test='${transactionType eq "RECHARGE"}'>
								<TD align="center" bgcolor="#cd0504"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="report.validity" /></SPAN></TD>
								<TD align="center" bgcolor="#cd0504"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="report.inStatus" /></SPAN></TD>	
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
									bundle="view" key="application.detail" /></SPAN></TD>

							</TR>

							<logic:iterate id="transaction" name="TransactionRptA2AFormBean"
								property="displayDetails" indexId="i">
								<TR align="center" bgcolor="#fce8e6">
									<TD class=" height19" align="center"><SPAN
										class="mLeft5 mTop5 mBot5 black10"><bean:write
										name="transaction" property="rowNum" /></SPAN></TD>
									<TD class=" height19" align="center"><SPAN
										class="mLeft5 mTop5 mBot5 black10"> <bean:write
										name="transaction" property="transactionId" /></SPAN></TD>
									<TD class="text" align="center"><SPAN
										class="mLeft5 mTop5 mBot5 black10"> <bean:write
										name="transaction" property="transactionType" /></SPAN></TD>
									<TD class=" height19" align="center"><SPAN
										class="mLeft5 mTop5 mBot5 black10"> <bean:write
										name="transaction" property="mtpAccountCode" /></SPAN></TD>
								<c:if  test='${transactionType eq "RECHARGE" or 
									transactionType eq "POSTPAID_MOBILE" or
									transactionType eq "POSTPAID_ABTS"}'>
									<TD class=" height19" align="center"><SPAN
										class="mLeft5 mTop5 mBot5 black10"> <bean:write
										name="transaction" property="customerMobile" /></SPAN></TD>
								</c:if>	
								<c:if  test='${transactionType eq "POSTPAID_DTH"}'>
									<TD class=" height19" align="center"><SPAN
										class="mLeft5 mTop5 mBot5 black10"> <bean:write
										name="transaction" property="receiverAccountId" /></SPAN></TD>
								</c:if>		
									<TD class=" height19" align="right"><SPAN
										class="mLeft5 mTop5 mBot5 black10"> <bean:write
										name="transaction" format="#0.00" property="transactionAmount" /></SPAN></TD>


									<TD class="text" align="center" nowrap><bean:write
										name="transaction" property="transactionDate"
										formatKey="application.date.format" bundle="view"/></TD>

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

							
								<c:if  test='${transactionType eq "RECHARGE"}'>
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
									<TD class="text" align="center"><bean:write
										name="transaction" property="inStatus" /> </TD>
								</c:if>	
								<c:if  test='${transactionType eq "POSTPAID_MOBILE" or 
									transactionType eq "POSTPAID_ABTS" or
									transactionType eq "POSTPAID_DTH"}'>										
									<TD class="text" align="center">
									<logic:notMatch name="transaction" property="selfcareStatus" value="null">
									<bean:write	name="transaction" property="selfcareStatus" /> 
									</logic:notMatch>
									</TD>
								</c:if>

									<TD class="text" align="left" nowrap><input type="button"
										class="submit" value="Details"
										onclick="return viewDetails('${transaction.transactionId }')" />
									</TD>

								</TR>


							</logic:iterate>
							<TR>
								<TD width="20" colspan="13" align="left">&nbsp;&nbsp;&nbsp;</TD>
							</TR>

							<TR align="center">
								<TD align="center" bgcolor="#daeefc" colspan="13"><FONT
									face="verdana" size="2"><c:if test="${PAGES!=''}">
									<c:if test="${PAGES>1}">

								    	Page:
								       <c:if test="${PRE>=1}">
											<a href="#"
												onclick="submitData('<c:out value='${PRE}' />','<c:out value='${searchType}' />','<c:out value='${searchValue}' />','<c:out value='${transactionType}' />','<c:out value='${status}' />','<c:out value='${startDate}' />','<c:out value='${endDate}' />');"><
											Prev</a>
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
														<a href="#"
															onclick="submitData('<c:out value="${item}"/>','<c:out value='${searchType}' />','<c:out value='${searchValue}' />','<c:out value='${transactionType}' />','<c:out value='${status}' />','<c:out value='${startDate}' />','<c:out value='${endDate}' />');"><c:out
															value="${item}" /></a>
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
														<a href="#"
															onclick="submitData('<c:out value="${item}"/>','<c:out value='${searchType}' />','<c:out value='${searchValue}' />','<c:out value='${transactionType}' />','<c:out value='${status}' />','<c:out value='${startDate}' />','<c:out value='${endDate}' />');"><c:out
															value="${item}" /></a>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:if>

										<c:if test="${NEXT<=PAGES}">
											<a href="#"
												onclick="submitData('<c:out value='${NEXT}' />','<c:out value='${searchType}' />','<c:out value='${searchValue}' />','<c:out value='${transactionType}' />','<c:out value='${status}' />','<c:out value='${startDate}' />','<c:out value='${endDate}' />');">Next></a>
										</c:if>

									</c:if>
								</c:if> </FONT></TD>
							</TR>
						</logic:notEmpty>
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
