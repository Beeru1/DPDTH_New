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
<LINK href="<%=request.getContextPath()%>/theme/Master.css"
	rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<SCRIPT language="JavaScript"
	src="<%=request.getContextPath()%>/scripts/Cal.js"
	type="text/javascript"></SCRIPT>

<SCRIPT language="javascript" type="text/javascript"> 

	function submitData(nextPage,circleId){
		document.forms[0].action="initCreateAccount.do?methodName=aggregateView&page="
				+ nextPage + "&circleId="
				+ circleId ;
	//	document.getElementById("page").value="";
		document.forms[0].method="post";
		document.forms[0].submit();
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

	/* This function lets the user to dowanload the displayed rows. */
	function exportDataToExcel(){
		document.getElementById("methodName").value="downloadAggregateList";
		document.forms[0].submit();
	}
	
</SCRIPT>
</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"	bgcolor="#FFFFFF" 
	marginheight="0">

<TABLE cellspacing="0" cellpadding="0" border="0" height="100%"
	valign="top">
	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include
			page="../common/dpheader.jsp" flush="" /></TD>
	</TR>
	<TR valign="top">
		<TD width="167" align="left" bgcolor="b4d2e7" valign="top"
			height="100%"><jsp:include page="../common/leftHeader.jsp"
			flush="" /></TD>

		<TD valign="top" width="100%" height="100%"><html:form
			action="initCreateAccount">


			<TABLE border="0" cellpadding="5" cellspacing="0" class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/aggregateView.gif"
						width="505" height="22" alt=""></TD>
				</TR>
			</TABLE>

			<TABLE align="center" class="mLeft5" width="100%">

				<logic:empty name="DPCreateAccountForm" property="aggList">
				<tr>
				<td colspan="3">
				<font color="red" size="2"><bean:message bundle="view" key="account.NoRecord"/> </font>
				</td>
				</tr>
				
				</logic:empty>
<%--<div id="aggregateViewOfAll"> --%>				
				<logic:notEmpty property="aggList" name="DPCreateAccountForm">
					<TR align="center">
						<TD align="left" colspan="12"><INPUT class="submit"
							onclick="exportDataToExcel();" type="button" name="export"
							value="Export to Excel"></TD>
					</TR>
					<TR align="center" bgcolor="#CD0504" style="color:#ffffff !important;">
					<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.SNo" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.dist" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.lapuMobileNo" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.fse" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.lapuMobileNo" /></SPAN></TD>

						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.retailer" /></SPAN></TD>
			
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.lapuMobileNo" /></SPAN></TD>
						
					</TR>
		<logic:iterate id="account" property="aggList" name="DPCreateAccountForm" 
					indexId="i">
						<TR bgcolor="#FCE8E6">
							<TD class="text" align="center"><bean:write name="account"
								property="rowNum" /></TD>
							<TD class="text" align="center"><bean:write name="account"
								property="distName" /></TD>
							<TD class="text" align="center"><bean:write name="account"
								property="distLapuNo" /></TD>
							<TD class="text" align="center"><bean:write name="account"
								property="fseName" /></TD>
							
							<TD class="text" align="center"><bean:write name="account"
								property="fseLapuNo" /></TD>

							<TD class="text" align="center"><bean:write name="account"
								property="retailerName" /></TD>

							<TD class="text" align="center"><bean:write name="account" 
								property="retailerLapuNo" /></TD>

						</TR>
					</logic:iterate>
					
	<%--- 			</div>	
	<div id="aggregateViewOfDist">				
<logic:notEmpty property="aggList" name="DPCreateAccountForm">
					<TR align="center">
						<TD align="left" colspan="12"><INPUT class="submit"
							onclick="exportDataToExcel();" type="button" name="export"
							value="Export to Excel"></TD>
					</TR>
					<TR align="center" bgcolor="#CD0504" style="color:#ffffff !important;">
					<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.SNo" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.fse" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.lapuMobileNo" /></SPAN></TD>

						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.retailer" /></SPAN></TD>
			
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.lapuMobileNo" /></SPAN></TD>
						
					</TR>
					<logic:iterate id="account" property="aggList" name="DPCreateAccountForm" 
					indexId="i">
						<TR bgcolor="#FCE8E6">
							<TD class="text" align="center"><bean:write name="account"
								property="rowNum" /></TD>
							<TD class="text" align="center"><bean:write name="account"
								property="fseName" /></TD>
							
							<TD class="text" align="center"><bean:write name="account"
								property="fseLapuNo" /></TD>

							<TD class="text" align="center"><bean:write name="account"
								property="retailerName" /></TD>

							<TD class="text" align="center"><bean:write name="account" 
								property="retailerLapuNo" /></TD>
					</TR>
					</logic:iterate>
					</logic:notEmpty>
				</div>	--%>
					<html:hidden styleId="methodName" property="methodName"
						value="aggregateView" />
					<html:hidden styleId="accountId" property="accountId" />
					<TR align="center">
					<TD align="right" bgcolor="#daeefc" colspan="15"><FONT
							face="verdana" size="2"><c:if test="${PAGES!=''}">
							<c:if test="${PAGES>1}">

								    	Page:
								<c:if test="${PRE>=1}">
									<a href="#"
										onclick="submitData('<c:out value='${PRE}' />','<c:out value='${circleId}' />');"><
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
													onclick="submitData('<c:out value="${item}"/>','<c:out value='${circleId}' />');"><c:out
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
													onclick="submitData('<c:out value="${item}"/>','<c:out value='${circleId}' />');"><c:out
													value="${item}" /></a>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:if>
								
								<c:if test="${NEXT<=PAGES}">
									<a href="#"
										onclick="submitData('<c:out value='${NEXT}' />','<c:out value='${circleId}' />');">Next></a>
								</c:if>
								
							</c:if>
						</c:if> </FONT></TD>
					</TR>
	</logic:notEmpty>
			</TABLE>
			<html:hidden property="circleId" />
			<html:hidden property="showAttributes" styleId="showAttributes" name="DPCreateAccountForm"/>
		</html:form>
		</TD>
	</TR>
	<TR align="center" bgcolor="4477bb" valign="top">
		<TD colspan="2" height="17" align="center"><jsp:include
			page="../common/footer.jsp" flush="" /></TD>
	</TR>
</TABLE>

</BODY>
</html:html>