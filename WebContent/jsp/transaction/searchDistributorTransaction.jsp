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
<LINK href="<%=request.getContextPath()%>/theme/text.css" rel="stylesheet" type="text/css">
<LINK href="<%=request.getContextPath()%>/theme/Master.css" rel="stylesheet" type="text/css">
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<SCRIPT language=JavaScript src="<%=request.getContextPath()%>/scripts/Cal.js" type=text/javascript></SCRIPT>
<SCRIPT language=JavaScript src="<%=request.getContextPath()%>/scripts/validation.js" type=text/javascript></SCRIPT>
<SCRIPT language="javascript"> 

	function submitData(nextPage,circleId,transactionListType,startDate,endDate){
		document.forms[0].action="DistributorTransaction.do?methodName=getAllDistributorTransactionList&page="
				+ nextPage + "&circleId=" + circleId + "&transactionListType="
				+ transactionListType + "&startDate=" 
				+ startDate + "&endDate=" + endDate;
		document.getElementById("page").value="";							
		document.forms[0].method="post";
		document.forms[0].submit();
	}	

	// set  the action class method name which call when we click on submit button 
	function setFunctionName(){
		var form = document.forms[0];
		date1=document.getElementById("startDate").value;
		date2=document.getElementById("endDate").value;
		if(form.circleId.value=='0' && document.getElementById("flagForCircleDisplay").value!="true"){
			alert('<bean:message bundle="error" key="errors.transaction.circleid" />');
			form.circleId.focus();
			return false;
		}else if(document.getElementById("startDate").value==""){
			alert('<bean:message bundle="error" key="error.report.invalidstartdate" />');	  
			form.startDate.focus();
			return false; 
		}else if(document.getElementById("endDate").value==""){
			alert('<bean:message bundle="error" key="error.report.invalidenddate" />');	  
			form.endDate.focus();
			return false; 
		}else if (!(isDate2Greater(date1, date2))){
			return false;
		}else{
			document.getElementById("page").value="";
			document.getElementById("methodName").value="getAllDistributorTransactionList";
			return true;
		}
	}
   
	function getVerifyTransactionId(transactionId,status){
		var form = document.forms[0];
		document.getElementById("transactionId").value=transactionId;
		document.getElementById("transactionListType").value=status;
		document.getElementById("methodName").value="getVerifyTransactionId"; 
		form.submit();
	} //function getVerifyTransactionId() ends
	
	function getViewTransactionId(transactionId,status){
		var form = document.forms[0];
		document.getElementById("transactionId").value=transactionId;
		document.getElementById("transactionListType").value=status;
		document.getElementById("methodName").value="getViewTransactionId"; 
		form.submit();
	} // function getViewTransactionId() ends

	function checkKeyPressed(){
		if(window.event.keyCode=="13"){
			document.forms[0].submit();
		}
	}

</SCRIPT>

</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onkeypress="return checkKeyPressed();">


<TABLE cellspacing="0" cellpadding="0" border="0" height="100%" width="100%"
	valign="top">
	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include
			page="../common/dpheader.jsp" /></TD>
	</TR>
	<TR valign="top">
		<TD width="167" align="left" bgcolor="b4d2e7" valign="top"
			height="100%"><jsp:include page="../common/leftHeader.jsp" /></TD>


		<TD valign="top" width="100%" height="100%"><html:form action="DistributorTransaction"
			method="post">
			<TABLE border="0" cellspacing="0" cellpadding="5" class="text">
				<TR>
					<TD colspan="5" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/searchTransaction.gif" width="505" height="22"><BR>
					</TD>
				</TR>
				<html:hidden property="methodName" styleId="methodName" />
				<html:hidden property="flagForCircleDisplay"
					styleId="flagForCircleDisplay" />
					<html:hidden property="page" styleId="page" />
				<TR>
					<TD nowrap ><STRONG><FONT color="#000000"><bean:message
						bundle="view" key="transaction.typestatus" /> </FONT></STRONG> <html:select
						property="transactionListType" styleId="transactionListType"
						styleClass="w130" style="width:85" tabindex="1">
						<html:option value="P">
							<bean:message bundle="view" key="transaction.pending" />
						</html:option>
						<html:option value="R">
							<bean:message bundle="view" key="transaction.reject" />
						</html:option>
						<html:option value="A">
							<bean:message bundle="view" key="transaction.approve" />
						</html:option>
					</html:select></TD>
					<TD nowrap><STRONG><FONT color="#000000"> <bean:message
						bundle="view" key="topup.circle_name" /></FONT><FONT color="RED">*</FONT>
					</STRONG> <FONT color="#3C3C3C"> <html:select property="circleId"
						name="DistributorTransactionFormBean" styleClass="w130"
						tabindex="2">
						<html:option value="0">
							<bean:message key="application.option.select" bundle="view" />
						</html:option>
						<html:options collection="circleList" labelProperty="circleName"
							property="circleId" />
					</html:select></FONT></TD>
					<TD nowrap><STRONG><FONT color="#000000"> <bean:message
						bundle="view" key="application.start_date" /></FONT><FONT color="RED">*</FONT></STRONG><html:text
						property="startDate" styleId="startDate" styleClass="box"
						readonly="true" size="10" maxlength="10"
						name="DistributorTransactionFormBean" /> <!--<img src="<%=request.getContextPath()%>/images/cal.gif" Alt="Show Calendar" tabindex="3"
						onclick="displayDatePicker('startDate', false, 'mdy', '/',<bean:message key="application.days.available_data" bundle="view" />);"></img>-->
					<script language="JavaScript">
new tcal ({
// form name
'formname': 'DistributorTransactionFormBean',
// input name
'controlname': 'startDate'
});

</script></TD>
					<TD nowrap><STRONG><FONT color="#000000"> <bean:message
						bundle="view" key="application.end_date" /></FONT><FONT color="RED">*</FONT>
					</STRONG> <html:text property="endDate" styleId="endDate" styleClass="box"
						readonly="true" size="10" maxlength="10"
						name="DistributorTransactionFormBean" /><!-- <img src="<%=request.getContextPath()%>/images/cal.gif" Alt="Show Calendar"
						onclick="displayDatePicker('endDate', false, 'mdy', '/',<bean:message key="application.days.available_data" bundle="view" />);"></img>-->
					<script language="JavaScript">
new tcal ({
// form name
'formname': 'DistributorTransactionFormBean',
// input name
'controlname': 'endDate'
});

</script></TD>
					<TD><html:submit styleClass="submit"
						onclick="return setFunctionName();" value="Search"></html:submit>
					</TD>
				</TR>
			</TABLE>



			<TABLE align="center" class="mLeft5" width="100%">
				<TR align="left">
					<TD colspan="5" class="text height19"><FONT color="red">
					<STRONG><html:errors  bundle="error" /></STRONG></FONT></TD>
				</TR>

				<logic:notEmpty name="DistributorTransactionFormBean"
					property="transactionList">
					<TR>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="transaction.list.srno" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="transaction.MTPAccountCode" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="transaction.purchaseorderno" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="transaction.purchasedate" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="transaction.list.transactionamount" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="transaction.amountcredited" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="transaction.datetime" /></SPAN></TD>
						<%-- <td align="center" bgcolor="#cd0504"><span
											class="white10heading mLeft5 mTop5"><bean:message
											bundle="view" key="transaction.type" /></span></td> --%>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="transaction.list.chqcardno" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="transaction.bankname" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="transaction.chequedate" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="transaction.carddate" /></SPAN></TD>
						<%-- <td align="center" bgcolor="#cd0504"><span
											class="white10heading mLeft5 mTop5"><bean:message
											bundle="view" key="transaction.remarks" /></span></td> --%>

					
<!-- 					<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="transaction.createdby" /></SPAN></TD>
-->					

						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="transaction.approverRemarks" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="transaction.verifyDateTime" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="transaction.verifiedBy" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="transaction.status" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="application.verify" />&nbsp;|&nbsp;<bean:message
							bundle="view" key="application.view" /></SPAN></TD>
					</TR>

					
					<logic:iterate id="transInfo" name="DistributorTransactionFormBean"
						property="transactionList" indexId="i">
						<TR align="center"
							bgcolor="#fce8e6">
							<TD class="text"><bean:write name="transInfo"
								property="rowNum" /></TD>
							<TD class="height19" align="left"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="transInfo" property="accountCode" /></SPAN></TD>
							<TD class="height19" align="left"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="transInfo" property="purchaseOrderNo" /></SPAN></TD>
							<TD class="height19" align="center"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="transInfo" property="purchaseOrderDate"
								formatKey="formatDate" /></SPAN></TD>
							<TD class="height19" align="right"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="transInfo" property="transactionAmount" format="#0.00" /></SPAN></TD>
							<TD class="height19" align="right"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="transInfo" property="creditedAmount" format="#0.00" /></SPAN></TD>
							<TD class="height19" align="center" nowrap><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="transInfo" property="transactionDate"
								formatKey="application.date.format" bundle="view"/></SPAN></TD>

														
							
							<logic:notEqual name="transInfo" property="chqccNumber" value="0">
							<TD class="height19" align="center" nowrap><SPAN
									class="mLeft5 mTop5 mBot5 black10">
							<logic:equal name="transInfo" property="paymentMode" value="CREDIT">
							<bean:message bundle="view" key="transaction.append"/>
							</logic:equal><bean:write name="transInfo" property="chqccNumber" /></SPAN></TD>
							</logic:notEqual>
							
							
							<logic:equal name="transInfo" property="chqccNumber" value="0">
								<TD class="height19" align="left"><SPAN
									class="mLeft5 mTop5 mBot5 black10"></SPAN></TD>
							</logic:equal>

							<logic:notEqual name="transInfo" property="bankName" value="0">
								<TD class="height19" align="left"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><bean:write
									name="transInfo" property="bankName" /></SPAN></TD>
							</logic:notEqual>
							<logic:equal name="transInfo" property="bankName" value="0">
								<TD class="height19"><SPAN
									class="mLeft5 mTop5 mBot5 black10"></SPAN></TD>
							</logic:equal>
							<!-- Checque Date -->
							<logic:notEqual name="transInfo" property="chequeDate"
								value="null">
								<TD class="height19" align="center"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><bean:write
									name="transInfo" property="chequeDate" formatKey="formatDate" /></SPAN></TD>
							</logic:notEqual>
							<logic:equal name="transInfo" property="chequeDate" value="null">
								<TD class="height19"><SPAN
									class="mLeft5 mTop5 mBot5 black10"></SPAN></TD>
							</logic:equal>
							<!--  CHeck\Card Validate Date -->
							<logic:notEqual name="transInfo" property="ccvalidDate"
								value="null">
								<TD class="height19" align="center"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><bean:write
									name="transInfo" property="ccvalidDate" formatKey="formatDate" /></SPAN></TD>
							</logic:notEqual>
							<logic:equal name="transInfo" property="ccvalidDate" value="null">
								<TD class="height19"><SPAN
									class="mLeft5 mTop5 mBot5 black10"></SPAN></TD>
							</logic:equal>

							<!-- Created By Name -->
<!-- 	
							<TD class="height19" align="left"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="transInfo" property="createdByName" /></SPAN></TD>
-->						
							<!-- Review comment -->
							<TD class="height19" align="left"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="transInfo" property="reviewComment" /></SPAN></TD>
							<!--  Verify Date and Time -->
							<TD class="height19" align="center"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="transInfo" property="reviewedDate" formatKey="formatDateTime" /></SPAN></TD>
							<!--  Upadated By -->
							<!-- <td class="height19"><span
												class="mLeft5 mTop5 mBot5 black10"><bean:write
												name="transInfo" property="updatedBy" /></span></td>-->
							<TD class="height19" align="left"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="transInfo" property="reviewedBy" /></SPAN></TD>

							<!-- review status -->
							<TD class="height19"><logic:equal name="transInfo"
								property="reviewStatus" value="A">
								<SPAN class="mLeft5 mTop5 mBot5 black10"><bean:message
									bundle="view" key="transaction.approve"></bean:message> </SPAN>
							</logic:equal> <logic:equal name="transInfo" property="reviewStatus" value="P">
								<SPAN class="mLeft5 mTop5 mBot5 black10"><bean:message
									bundle="view" key="transaction.pending"></bean:message> </SPAN>
							</logic:equal> <logic:equal name="transInfo" property="reviewStatus" value="R">
								<SPAN class="mLeft5 mTop5 mBot5 black10"><bean:message
									bundle="view" key="transaction.reject"></bean:message> </SPAN>
							</logic:equal></TD>
							<TD class="height19" nowrap >
							<logic:equal
									name="DistributorTransactionFormBean" property="editStatus" value="Y">
									<INPUT type="button" class="submit" value='Verify'
										onclick="return getVerifyTransactionId('${transInfo.transactionId}','${transInfo.reviewStatus}')" />&nbsp;|
								</logic:equal> &nbsp;<INPUT type="button" class="submit" value='View'
									onclick="return getViewTransactionId('${transInfo.transactionId}','${transInfo.reviewStatus}')" />	
							</TD>
						</TR>
					</logic:iterate>
					
					<html:hidden property="transactionListType" styleId="transactionListType" />
					<html:hidden property="transactionId" styleId="transactionId" />
					<TR align="center">
						<TD align="center" bgcolor="#daeefc" colspan="17"><FONT
							face="verdana" size="2"> <c:if test="${PAGES!=''}">
									<c:if test="${PAGES>1}">

								    	Page:
								 <c:if test="${PRE>=1}">
									<a href="#"
												onclick="submitData('<c:out value='${PRE}' />','<c:out value='${circleId}' />','<c:out value='${transactionListType}' />','<c:out value='${startDate}' />','<c:out value='${endDate}' />');"><
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
												<A href="#"
												onclick="submitData('<c:out value="${item}"/>','<c:out value='${circleId}' />','<c:out value='${transactionListType}' />','<c:out value='${startDate}' />','<c:out value='${endDate}' />');"><c:out
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
												onclick="submitData('<c:out value="${item}"/>','<c:out value='${circleId}' />','<c:out value='${transactionListType}' />','<c:out value='${startDate}' />','<c:out value='${endDate}' />');"><c:out
												value="${item}" /></A>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								</c:if>
								
								<c:if test="${NEXT<=PAGES}">
									<A href="#"
										onclick="submitData('<c:out value='${NEXT}' />','<c:out value='${circleId}' />','<c:out value='${transactionListType}' />','<c:out value='${startDate}' />','<c:out value='${endDate}' />');">Next></A>
								</c:if>
								
							</c:if>
						</c:if> </FONT></TD>
					</TR>
				</logic:notEmpty>
				<html:hidden styleId="editStatus" property="editStatus" name="DistributorTransactionFormBean" />
			</TABLE>
		</html:form></TD>
	</TR>

	<TR align="center" bgcolor="4477bb" valign="top">
		<TD colspan="2" height="17" align="center"><jsp:include
			page="../common/footer.jsp" /></TD>
	</TR>
</TABLE>
<SCRIPT type="text/javascript">
{
	if(document.getElementById("flagForCircleDisplay").value=="true")
	{
		document.getElementById("circleId").disabled = true;
	}
}
</SCRIPT>


</BODY>
</html:html>



