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
<LINK href="<%=request.getContextPath()%>/theme/text.css" rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/theme/chromestyle2.css" />
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/chrome.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/Cal.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>

<SCRIPT type="text/javascript">
	function submitData(nextPage,startDate,endDate,status){
		document.forms[0].action="CircleAction.do?methodName=getCircleList&page="
				+ nextPage  + "&startDate=" 
				+ startDate + "&endDate=" + endDate
				+ "&status=" + status;
		document.getElementById("page").value="";	
		document.forms[0].method="post";
		document.forms[0].submit();
	}

	function editDetails(circleId){
		   var form = document.forms[0];
	       document.getElementById("methodName").value="getEditCircle";
		   document.getElementById("circleId").value=circleId;
	       form.submit();
	} // function editDeatils ends
	
	function viewDetails(circleId){
		   var form = document.forms[0];
	       document.getElementById("methodName").value="getViewCircle";
		   document.getElementById("circleId").value=circleId;
	       form.submit();
	} // function viewDeatils ends
	
	function validate(){
		var date1 = document.forms[0].startDate.value;
		var date2 = document.forms[0].endDate.value;
		if(document.getElementById("circleStatus").value==0){
			alert('<bean:message bundle="error" key="errors.user.status_required" />');	  
			document.forms[0].circleStatus.focus();
			return false; 
		}  	 
		if (!(isDate2Greater(date1, date2))){
			return false;
		}else {
			document.getElementById("page").value="";	
			document.getElementById("methodName").value="getCircleList";
			return true;
		}
	} // function validate ends

	/* This function lets the user to dowanload the displayed rows. */
	function exportDataToExcel(){
		document.getElementById("methodName").value="downloadCircleList";
		document.forms[0].submit();
	} // function exportDataToExcel ends

	// set focus on status control  
	function doLoad(){
	document.forms[0].circleStatus.focus();
	}
	/* check if enter key is pressed */
	function checkKeyPressed(){
		if(window.event.keyCode=="13"){
			if(validate()){
				document.getElementById("methodName").value="getCircleList";
				return true;
			}
			return false;	
		}
	}

</SCRIPT>
</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onload="javascript:doLoad();"
	onkeypress="return checkKeyPressed();">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">
	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include
			page="../common/dpheader.jsp" /></TD>
	</TR>
	<TR valign="top">
		<TD width="167" align="left" bgcolor="b4d2e7" valign="top"
			height="100%"><jsp:include page="../common/leftHeader.jsp" /></TD>
		<TD valign="top" width="100%" height="100%"><html:form
			action="/CircleAction" name="CircleAction"
			type="com.ibm.virtualization.recharge.beans.CircleFormBean">
			<TABLE border="0" cellpadding="5" cellspacing="0" class="text"
				width="700">
				<TR>
					<TD colspan="4" class="text" c><BR>
					<IMG src="<%=request.getContextPath()%>/images/viewEditCircle.gif" width="505" height="22"><BR>
					</TD>
				</TR>
				<TR>
					<TD width="192"><html:hidden property="methodName"
						styleId="methodName" /> 
									
						<STRONG><FONT color="#000000"><bean:message
						bundle="view" key="report.status" /></FONT></STRONG> <FONT color="RED">*</FONT>&nbsp;<FONT
						color="#003399"><html:select property="circleStatus"
						styleClass="w130" tabindex="1" styleId="circleStatus" name="CircleFormBean"
						>
						<html:option value="0">
							<bean:message key="application.option.select" bundle="view" />
						</html:option>
						<html:option value="A">
							<bean:message key="status.active" bundle="view" />
						</html:option>
						<html:option value="D">
							<bean:message key="status.inactive" bundle="view" />
						</html:option>
					</html:select></FONT></TD>
					<TD width="193"><STRONG><FONT color="#000000"><bean:message
						bundle="view" key="application.start_date" /></FONT></STRONG>&nbsp;<html:text
						property="startDate" styleId="startDate" styleClass="box"
						readonly="true" size="15" maxlength="10" name="CircleFormBean" />
					<script language="JavaScript">
new tcal ({
// form name
'formname': 'CircleFormBean',
// input name
'controlname': 'startDate'
});

</script></TD>

					<TD width="185"><STRONG><FONT color="#000000"><bean:message
						bundle="view" key="application.end_date" /></FONT></STRONG>&nbsp;<html:text
						property="endDate" styleId="endDate" styleClass="box"
						readonly="true" size="15" maxlength="10" name="CircleFormBean" />
					<script language="JavaScript">
new tcal ({
// form name
'formname': 'CircleFormBean',
// input name
'controlname': 'endDate'
});

</script></TD>
					<TD align="left" width="84"><html:submit
						onclick="return validate();" styleClass="submit" value="Search"
						tabindex="4" /></TD>
				</TR>
				<TR align="left">
					<TD colspan="4" class="text height19"><FONT color="black"><html:errors
						bundle="error" /><html:errors bundle="view" /></FONT></TD>
				</TR>
			</TABLE>
			<TABLE width="90%" align="center" class="mLeft5" border="0">
				<logic:notEmpty name="CircleFormBean" property="displayDetails">
					<TR align="center">
						<TD align="left" colspan="14"><INPUT class="submit"
							onclick="exportDataToExcel();" tabindex="5" type="button"
							name="export" value="Export to Excel"></TD>
					</TR>
					<TR align="center" bgcolor="#104e8b">
						<TD align="center"  bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><font color="white"><bean:message
							bundle="view" key="application.sno" /></font></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><font color="white"><bean:message
							bundle="view" key="circle.circlecode" /></font></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><font color="white"><bean:message
							bundle="view" key="circle.circlename" /></font></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><font color="white"><bean:message
							bundle="view" key="circle.description" /></font></SPAN></TD>
						<TD align="center"  bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><font color="white"><bean:message
							bundle="view" key="circle.regionname" /></font></SPAN></TD>
		<%-- 				<TD align="center" bgcolor="#CD0504" colspan="3"><SPAN
							class="white10heading mLeft5 mTop5"><font color="white"><bean:message
							bundle="view" key="circle.balance" /> (<bean:message
							bundle="view" key="application.currency" />)</font></SPAN></TD>
						<TD align="center" rowspan="2" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><font color="white"><bean:message
							bundle="view" key="circle.rate" /></font></SPAN></TD>
						<TD align="center" rowspan="2" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><font color="white"><bean:message
							bundle="view" key="circle.threshold" /></font></SPAN></TD>	--%>
						<TD align="center"  bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><font color="white"><bean:message
							bundle="view" key="user.createddate" /></font></SPAN></TD>

<!-- 						
						<TD align="center" rowspan="2" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="user.createdby" /></SPAN></TD>

-->						

						<TD align="center"  bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><font color="white"><bean:message
							bundle="view" key="circle.status" /></font></SPAN></TD>
						<TD align="center"  bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><font color="white"><bean:message
							bundle="view" key="application.edit" />&nbsp;|&nbsp;<bean:message
							bundle="view" key="application.view" /></font></SPAN></TD>
					</TR>
<%-- 					<TR align="center">
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><font color="white"><bean:message
							bundle="view" key="circle.openingbalance" /></font></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><font color="white"><bean:message
							bundle="view" key="circle.availablebalance" /></font></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><font color="white"><bean:message
							bundle="view" key="circle.operatingbalance" /></font></SPAN></TD>
					</TR> --%>
					
					<logic:iterate id="circle" name="CircleFormBean"
						property="displayDetails" indexId="i">
						<c:if test='${circle.operatingBalance gt circle.threshold}'>
							<TR align="center"
								bgcolor="#FCE8E6">
								<TD class="height19" align="right"><SPAN
									class="mLeft5 mTop5 mBot5 black10"> <bean:write
									name="circle" property="rowNum" /> </SPAN></TD>
								<TD class="height19" align="left"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><bean:write
									name="circle" property="circleCode" /> </SPAN></TD>
								<TD class="height19" align="left"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><bean:write
									name="circle" property="circleName" /></SPAN></TD>
								<TD class="height19" align="left"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><bean:write
									name="circle" property="description" /></SPAN></TD>
								<TD class="height19" align="left"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><bean:write
									name="circle" property="regionName" /></SPAN></TD>
					<%-- 		<TD class="height19" align="right"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><bean:write
									name="circle" format="#0.00" property="openingBalance" /></SPAN></TD>
								<TD class="height19" align="right"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><bean:write
									name="circle" format="#0.00" property="availableBalance" /></SPAN></TD>
								<TD class="height19" align="right"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><bean:write
									name="circle" format="#0.00" property="operatingBalance" /></SPAN></TD>
								<TD class="height19" align="right"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><bean:write
									name="circle" format="#0.00" property="rate" /></SPAN></TD>
								<TD class="height19" align="right"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><bean:write
									name="circle" format="#0.00" property="threshold" /></SPAN></TD>	--%>
								<TD class="height19" align="center"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><bean:write
									name="circle" formatKey="formatDate" property="createdDt" /></SPAN></TD>
<!-- 
								<TD class="height19" align="left"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><bean:write
									name="circle" property="loginName" /></SPAN></TD>
-->							
								<html:hidden name="circle" property="createdBy" />
								<c:if test='${circle.status eq "A"}'>
									<TD class="height19"><SPAN
										class="mLeft5 mTop5 mBot5 black10"><bean:message
										bundle="view" key="application.option.active" /></SPAN></TD>
								</c:if>
								<c:if test='${circle.status eq "D"}'>
									<TD class="height19"><SPAN
										class="mLeft5 mTop5 mBot5 black10"> <bean:message
										bundle="view" key="application.option.inactive" /></SPAN></TD>
								</c:if>
								<html:hidden property="circleName" styleId="circleName" />
								<html:hidden property="circleId" styleId="circleId"  />
								<TD class=" height19" nowrap align="center"><logic:equal
									name="CircleFormBean" property="editStatus" value="Y">
									<INPUT type="button" class="submit" value='Edit'
										onclick="return editDetails('${circle.circleId}')" />&nbsp;|
								</logic:equal> &nbsp;<INPUT type="button" class="submit" value='View'
									onclick="return viewDetails('${circle.circleId}')" /></TD>
							</TR>
						</c:if>
						<c:if test='${circle.operatingBalance le circle.threshold}'>
							<TR align="center"
								bgcolor="#FCE8E6">
								<TD class="height19" align="right"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><FONT color="black">
								<bean:write name="circle" property="rowNum" /> </FONT></SPAN></TD>
								<TD class="height19" align="left"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><FONT color="black"><bean:write
									name="circle" property="circleCode" /></FONT></SPAN></TD>
								<TD class="height19" align="left"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><FONT color="black"><bean:write
									name="circle" property="circleName" /></FONT></SPAN></TD>
								<TD class="height19" align="left"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><FONT color="black"><bean:write
									name="circle" property="description" /></FONT></SPAN></TD>
								<TD class="height19" align="left"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><FONT color="black"><bean:write
									name="circle" property="regionName" /></FONT></SPAN></TD>
				<%-- 				<TD class="height19" align="right"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><FONT color="black"><bean:write
									name="circle" format="#0.00" property="openingBalance" /></FONT></SPAN></TD>
								<TD class="height19" align="right"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><FONT color="black"><bean:write
									name="circle" format="#0.00" property="availableBalance" /></FONT></SPAN></TD>
								<TD class="height19" align="right"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><FONT color="black"><bean:write
									name="circle" format="#0.00" property="operatingBalance" /></FONT></SPAN></TD>
								<TD class="height19" align="right"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><FONT color="black"><bean:write
									name="circle" format="#0.00" property="rate" /></FONT></SPAN></TD>
								<TD class="height19" align="right"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><FONT color="black"><bean:write
									name="circle" format="#0.00" property="threshold" /></FONT></SPAN></TD> --%>
								<TD class="height19" align="center"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><FONT color="black"><bean:write
									name="circle" property="createdDt" formatKey="formatDate" /></FONT></SPAN></TD>
<!-- 
								<TD class="height19" align="left"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><FONT color="black"><bean:write
									name="circle" property="loginName" /></FONT></SPAN></TD>
-->							
								<html:hidden name="circle" property="createdBy" />
								<c:if test='${circle.status eq "A"}'>
									<TD class="height19"><SPAN
										class="mLeft5 mTop5 mBot5 black10"><FONT
										color="black"><bean:message bundle="view"
										key="application.option.active" /></FONT></SPAN></TD>
								</c:if>
								<c:if test='${circle.status eq "D"}'>
									<TD class="height19"><SPAN
										class="mLeft5 mTop5 mBot5 black10"><FONT
										color="black"><bean:message bundle="view"
										key="application.option.inactive" /></FONT></SPAN></TD>
								</c:if>

								<html:hidden property="circleName" styleId="circleName" />
									<html:hidden property="circleId" styleId="circleId"/>
								<TD class=" height19" nowrap align="center"><logic:equal
									name="CircleFormBean" property="editStatus" value="Y">
									<INPUT type="button" class="submit" value='Edit'
										onclick="return editDetails('${circle.circleId}')" />&nbsp;|
								</logic:equal> &nbsp;<INPUT type="button" class="submit" value='View'
									onclick="return viewDetails('${circle.circleId}')" /></TD>
							</TR>
						</c:if>
						
					</logic:iterate>
					<TR align="center">

						<TD align="center" bgcolor="#daeefc" colspan="14"><FONT
							face="verdana" size="2"> <c:if test="${PAGES!=''}">

							<c:if test="${PAGES>1}">

								    	Page:
								<c:if test="${PRE>=1}">
									<A href="#"
										onclick="submitData('<c:out value='${PRE}' />','<c:out value='${startDt}' />','<c:out value='${endDt}' />','<c:out value='${circleStatus}' />');"><
									Prev</A>
								</c:if>

								<c:if test="${PAGES>LinksPerPage}">
									<c:set var="start_page" value="1" scope="request" />
									<c:if test="${SELECTED_PAGE+1>LinksPerPage}">
										<c:set var="start_page" value="${SELECTED_PAGE-(LinksPerPage/2)}"
											scope="request" />
									</c:if>
									<c:if test="${SELECTED_PAGE+(LinksPerPage/2)-1>=PAGES}">
										<c:set var="start_page" value="${PAGES-LinksPerPage+1}" scope="request" />
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
												onclick="submitData('<c:out value="${item}"/>','<c:out value='${startDt}' />','<c:out value='${endDt}' />','<c:out value='${circleStatus}' />');"><c:out
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
												onclick="submitData('<c:out value="${item}"/>','<c:out value='${startDt}' />','<c:out value='${endDt}' />','<c:out value='${circleStatus}' />');"><c:out
												value="${item}" /></A>
										</c:otherwise>
									</c:choose>
								 </c:forEach>
								</c:if>
								
								<c:if test="${NEXT<=PAGES}">
									<A href="#" 
										onclick="submitData('<c:out value='${NEXT}' />','<c:out value='${startDt}' />','<c:out value='${endDt}' />','<c:out value='${circleStatus}' />');">Next></A>
								</c:if>
							</c:if>
						</c:if> </FONT></TD>
					</TR>

				</logic:notEmpty>
				<html:hidden styleId="editStatus" property="editStatus"
					name="CircleFormBean" />
				<html:hidden property="page" styleId="page" name="CircleFormBean" />				
			</TABLE>
		</html:form></TD>
	</TR>

	<TR align="center" bgcolor="4477bb" valign="top" width="100%">
		<TD colspan="2" height="17" align="center"><jsp:include
			page="../common/footer.jsp" /></TD>
	</TR>
</TABLE>
</BODY>
</html:html>