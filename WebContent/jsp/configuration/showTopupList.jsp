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
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<SCRIPT language=JavaScript
	src="<%=request.getContextPath()%>/scripts/Cal.js" type=text/javascript></SCRIPT>
<SCRIPT language="JavaScript" type="text/javascript">

	function submitData(nextPage,startDate,endDate,circleId,listStatus){
		document.forms[0].action="TopupSlabAction.do?methodName=showTopupConfig&page="
				+ nextPage + "&startDate=" 
				+ startDate + "&endDate=" + endDate
				+ "&circleId=" + circleId + "&listStatus=" + listStatus;
		document.getElementById("page").value="";	
		document.forms[0].method="post";
		document.forms[0].submit();
	}

	function validateDetails(){
		var form = document.forms[0];
		date1=document.getElementById("startDate").value;
		date2=document.getElementById("endDate").value;
		//Validating Circle
		
		if(document.getElementById("circleId").disabled==false){
			if(form.circleId.value=='0'){
			alert('<bean:message bundle="error" key="errors.topup.circleid" />');
			form.circleId.focus();
			return false;
			}
		}
		 if(document.getElementById("listStatus").value==0){
			alert('<bean:message bundle="error" key="errors.user.status_required" />');	  
			document.forms[0].listStatus.focus();
			return false; 
		}  	 
		 if (!(isDate2Greater(date1, date2))){
			return false;
		}
		document.getElementById("page").value="";	
		document.getElementById("methodName").value="showTopupConfig";
		return true;
	}// function validateDetails ends
	
	function editDetails(topupID){
		var form = document.forms[0];
		document.getElementById("methodName").value="getEditTopupConfig";
		document.getElementById("topupConfigId").value=topupID;
		form.submit();
	}// function editDetails ends
	
	function viewDetails(topupID){
		var form = document.forms[0];
		document.getElementById("methodName").value="getViewTopupConfig";
		document.getElementById("topupConfigId").value=topupID;
		form.submit();
	}// function viewDetails ends
	
	function checkKeyPressed(){
		if(window.event.keyCode=="13"){
			if(validateDetails()){
				document.forms[0].submit();
			}else{
				return false;
			}
		}
	}

	/* This function lets the user to dowanload the displayed rows. */
	function exportDataToExcel(){
		document.getElementById("methodName").value="downloadTopupConfigList";
		document.forms[0].submit();
	}
	
	// set focus on status  
	function doLoad(){
	if(document.getElementById("circleId").disabled==false){
	document.forms[0].circleId.focus();
		}
	else{
		document.forms[0].listStatus.focus();
		}
	
	}

</SCRIPT>
</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onkeypress="return checkKeyPressed();"
	onload="doLoad()">
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
			name="topupSlabAction" action="/TopupSlabAction"
			type="com.ibm.virtualization.recharge.beans.TopupSlabBean">

			<TABLE border="0" cellspacing="0" cellpadding="5" class="text"
				width="845">
				<TR>
					<TD colspan="4" class="text"><BR>
					<IMG
						src="<%=request.getContextPath()%>/images/viewEdit_SlabConfig.gif"
						width="505" height="22" alt=""></TD>
				</TR>
				<TR>
					<TD align="left" nowrap><STRONG><FONT color="#000000">
					<bean:message bundle="view" key="topup.circle_name" /></FONT></STRONG><FONT
						color="RED">*</FONT><FONT color="#003399"> <html:select
						property="circleId" name="topupSlabBean" styleId="circleId"
						tabindex="1" styleClass="w130">
						<html:option value="0">
							<bean:message bundle="view" key="topup.select_circle" />
						</html:option>
						<html:optionsCollection name="topupSlabBean" property="circleList"
							label="circleName" value="circleId" />
					</html:select></FONT></TD>
					<TD align="left" nowrap><STRONG><FONT color="#000000">
					<bean:message bundle="view" key="topup.status" /></FONT></STRONG><FONT color="RED">*</FONT>
					<FONT color="#003399"><html:select property="listStatus"
						name="topupSlabBean" styleId="listStatus" tabindex="2" style="width:85"
						styleClass="w130">
						<html:option value="0">
							<bean:message key="application.option.select" bundle="view" />
						</html:option>
						<html:option value="A">
							<bean:message key="status.active" bundle="view" />
						</html:option>
						<html:option value="D">
							<bean:message key="status.inactive" bundle="view" />
						</html:option>
					</html:select> </FONT></TD>
					<TD align="left" nowrap><STRONG><FONT color="#000000">
					<bean:message bundle="view" key="application.start_date" /></FONT><FONT
						color="RED"></FONT></STRONG><html:text property="startDate"
						styleId="startDate" styleClass="box" readonly="true" size="10"
						maxlength="10" name="topupSlabBean" /> 
						<!--  	<img src="<%=request.getContextPath()%>/images/cal.gif" Alt="Show Calendar"
										onclick="displayDatePicker('startDate', false, 'mdy', '/',<bean:message key="application.days.available_data" bundle="view" />);"></img>
										--> <script language="JavaScript">
new tcal ({
// form name
'formname': 'topupSlabBean',
// input name
'controlname': 'startDate'
});

</script></TD>
					<TD align="left" nowrap><STRONG><FONT color="#000000">
					<bean:message bundle="view" key="application.end_date" /></FONT><FONT
						color="RED"></FONT></STRONG> <html:text property="endDate"
						styleId="endDate" styleClass="box" readonly="true" size="10"
						maxlength="10" name="topupSlabBean" /> <!--  <img src="<%=request.getContextPath()%>/images/cal.gif" Alt="Show Calendar"
										onclick="displayDatePicker('endDate', false, 'mdy', '/',<bean:message key="application.days.available_data" bundle="view" />);"></img>-->
					<script language="JavaScript">
new tcal ({
// form name
'formname': 'topupSlabBean',
// input name
'controlname': 'endDate'
});

</script></TD>

					<TD align="left" colspan="4"><STRONG><FONT
						color="#000000"> <INPUT type="submit"
						value="Search" class="submit" align="center"
						onclick="return validateDetails();" tabindex="5" /> </FONT></STRONG></TD>
				</TR>

				<TR align="left">
					<TD colspan="4" class="text height19"><FONT color="#ff0000"><html:errors
						bundle="view" /><html:errors bundle="error" /> </FONT></TD>
				</TR>
			</TABLE>
			<TABLE width="90%" align="center" class="mLeft5" border="0">
				<logic:notEmpty name="topupSlabBean" property="topupList">
					<TR align="center">
						<TD align="left" colspan="12"><INPUT class="submit"
							onclick="exportDataToExcel();" type="button" tabindex="6"
							name="export" value="Export to Excel"></TD>
					</TR>
					<TR align="center" bgcolor="#104e8b">
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="application.sno" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="topup.config.id" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="topup.circle_name" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="topup.transaction_type" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="topup.start_amount" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="topup.end_amount" /></SPAN></TD>

						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="topup.esp_commission" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="topup.psp_commission" /></SPAN></TD>

						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="topup.service_tax" /> </SPAN></TD>

						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="topup.processing_fee" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="topup.incard_group" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="topup.validity" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="topup.status" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="topup.created.date" /> </SPAN></TD>

						<!-- 
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="topup.created.by" /> </SPAN></TD>

-->

						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="application.edit" />&nbsp;|&nbsp;<bean:message
							bundle="view" key="application.view" /> </SPAN></TD>
					</TR>



					<logic:iterate id="topup" name="topupSlabBean" property="topupList"
						scope="request" indexId="i">
						<TR align="center" bgcolor="#fce8e6">
							<TD class=" height19" align="center"><SPAN
								class="mLeft5 mTop5 mBot5 black10"> <bean:write
								name="topup" property="rowNum" /> </SPAN></TD>
							<TD class="text" align="center"><bean:write name="topup"
								property="topupConfigId" /></TD>
							<TD class="text" align="left"><bean:write name="topup"
								property="circleName" /></TD>
							<TD class="text" align="left"><bean:write name="topup"
								property="transactionType" /></TD>
							<TD class=" height19" align="right"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="topup" property="startAmount" format="#0.00" /></SPAN></TD>
							<TD class=" height19" align="right"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="topup" property="endAmount" format="#0.00" /></SPAN></TD>

							<TD class=" height19" align="right"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="topup" property="espCommission" format="#0.00" /></SPAN></TD>
							<TD class=" height19" align="right"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="topup" property="pspCommission" format="#0.00" /></SPAN></TD>

							<TD class=" height19" align="right"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="topup" property="serviceTax" format="#0.00" /></SPAN></TD>

							<TD class=" height19" align="right"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="topup" property="processingFee" format="#0.00##" /></SPAN></TD>
							<TD class="height19" align="center"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="topup" property="inCardGroup" /></SPAN></TD>
							<c:if test='${topup.validity eq "0"}'>
								<TD class=" height19"><SPAN
									class="mLeft5 mTop5 mBot5 black10">--</SPAN></TD>
							</c:if>
							<c:if test='${topup.validity gt "0"}'>
								<TD class=" height19" align="right"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><bean:write
									name="topup" property="validity" /></SPAN></TD>
							</c:if>
							<c:if test='${topup.status eq "A"}'>
								<TD class="height19"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><bean:message
									bundle="view" key="application.option.active" /></SPAN></TD>
							</c:if>
							<c:if test='${topup.status eq "D"}'>
								<TD class="height19"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><bean:message
									bundle="view" key="application.option.inactive" /></SPAN></TD>
							</c:if>
							<TD class=" height19" align="center"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="topup" property="createdDt" formatKey="formatDate" /></SPAN></TD>
							<!-- 
							<TD class=" height19" align="left"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="topup" property="loginName" /></SPAN></TD>
-->
							<html:hidden name="topup" property="createdBy" />
							<%-- %><td class="text" nowrap align="center"><html:link
										action="/TopupSlabAction.do?methodName=initEditTopupConfig"
										paramName="topup" paramProperty="topupConfigId"
										paramId="topupConfigId">EDIT</html:link></td>--%>


							<TD class=" height19" nowrap align="center"><logic:equal
								name="topupSlabBean" property="editStatus" value="Y">
								<INPUT type="button" class="submit" value="Edit"
									onclick="return editDetails('${topup.topupConfigId}')" />&nbsp;|
								</logic:equal> &nbsp; <INPUT type="button" class="submit" value="View"
								onclick="return viewDetails('${topup.topupConfigId}')" /></TD>
						</TR>

					</logic:iterate>
					<TR align="center">
						<TD align="center" bgcolor="#daeefc" colspan="16"><FONT
							face="verdana" size="2"> <c:if test="${PAGES!=''}">
							<c:if test="${PAGES>1}">
								
								    	Page:
							   <c:if test="${PRE>=1}">
									<A href="#"
										onclick="submitData('<c:out value='${PRE}' />','<c:out value='${startDate}' />','<c:out value='${endDate}' />','<c:out value='${circleId}' />','<c:out value='${listStatus}' />');"><
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
													onclick="submitData('<c:out value="${item}"/>','<c:out value='${startDate}' />','<c:out value='${endDate}' />','<c:out value='${circleId}' />','<c:out value='${listStatus}' />');"><c:out
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
													onclick="submitData('<c:out value="${item}"/>','<c:out value='${startDate}' />','<c:out value='${endDate}' />','<c:out value='${circleId}' />','<c:out value='${listStatus}' />');"><c:out
													value="${item}" /></A>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:if>
								
								<c:if test="${NEXT<=PAGES}">
									<A href="#"
										onclick="submitData('<c:out value='${NEXT}' />','<c:out value='${startDate}' />','<c:out value='${endDate}' />','<c:out value='${circleId}' />','<c:out value='${listStatus}' />');">Next></A>
								</c:if>

								
							</c:if>
						</c:if> </FONT></TD>
					</TR>

				</logic:notEmpty>

				<TR>
					<TD><html:hidden property="methodName" styleId="methodName" />
					<html:hidden property="topupConfigId" styleId="topupConfigId" /></TD>
					<html:hidden property="flagForCircleDisplay"
						styleId="flagForCircleDisplay" name="topupSlabBean" />
					<html:hidden property="page" styleId="page" name="topupSlabBean" />
				</TR>
			</TABLE>

		</html:form></TD>
	</TR>

	<TR align="center" bgcolor="4477bb" valign="top" width="100%">
		<TD colspan="2" height="17" align="center"><jsp:include
			page="../common/footer.jsp" flush="" /></TD>
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
