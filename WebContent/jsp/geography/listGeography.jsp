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
<LINK href="${pageContext.request.contextPath}/theme/text.css" rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/theme/chromestyle2.css" />
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/scripts/chrome.js"></SCRIPT>
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/scripts/Cal.js"></SCRIPT>
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/scripts/validation.js"></SCRIPT>

<SCRIPT type="text/javascript"><!--
	function submitData(nextPage,startDate,endDate,status,level){
		document.forms[0].action="GeographyAction.do?methodName=getGeographyList&page="
				+ nextPage  + "&startDate=" 
				+ startDate + "&endDate=" + endDate
				+ "&status=" + status
				+"&level="+level;
		document.getElementById("page").value="";	
		document.forms[0].method="post";
		document.getElementById("level").value=level;	
		document.forms[0].submit();
	} 

	function editDetails(geographyId,level){
		   var form = document.forms[0];
	       document.getElementById("methodName").value="getEditGeography";
		   document.getElementById("geographyId").value=geographyId;
   		   document.getElementById("level").value=level;				
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
	if(document.getElementById("geographyStatus").value==0){
			alert('<bean:message bundle="error" key="errors.user.status_required" />');	  
			document.forms[0].geographyStatus.focus();
			return false; 
		}  	 
		if (!(isDate2Greater(date1, date2))){
			return false;
		}else {
			document.getElementById("page").value="";	
			document.getElementById("methodName").value="getGeographyList";			
			document.getElementById("level").value='<bean:write name="GeographyFormBean" property="level"/>';
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
	document.forms[0].geographyStatus.focus();
	}
	/* check if enter key is pressed */
	function checkKeyPressed(){
		if(window.event.keyCode=="13"){
			if(validate()){
				document.getElementById("methodName").value="getGeographyList";
				return true;
			}
			return false;	
		}
	}

--></SCRIPT>
</HEAD>

<BODY background="${pageContext.request.contextPath}/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
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
		<TD valign="top" width="100%" height="100%">
		<html:form action="/GeographyAction" name="GeographyAction" type="com.ibm.dp.beans.GeographyFormBean">
		<html:hidden property="level" value="" name="GeographyFormBean"/>
			<TABLE border="0" cellpadding="5" cellspacing="0" class="text" width="700">
				<TR>
		
					<TD colspan="4" class="text"><BR>
					<logic:equal value="0" property="level" name="GeographyFormBean">
					<IMG src="${pageContext.request.contextPath}/images/viewEditCountry.gif" width="505" height="22" alt="">
					</logic:equal>
					<logic:equal value="1" property="level" name="GeographyFormBean">
					<H1>View/Edit HUB</H1>
					</logic:equal>
					<logic:equal value="2" property="level" name="GeographyFormBean">
					<IMG src="${pageContext.request.contextPath}/images/viewEditCircle.gif" width="505" height="22"><BR>
					</logic:equal>
					<logic:equal value="3" property="level" name="GeographyFormBean">
					<IMG src="${pageContext.request.contextPath}/images/viewEditZone.gif" width="505" height="22" alt="">
					</logic:equal>
					<logic:equal value="4" property="level" name="GeographyFormBean">
					<IMG src="${pageContext.request.contextPath}/images/viewEditCitytown.gif" width="505" height="22" alt="">
					</logic:equal>	
					</TD>						
				
				</TR>
					<TR>
					<TD width="192">									
						<STRONG><FONT color="#000000"><bean:message
						bundle="view" key="report.status" /></FONT></STRONG> <FONT color="RED">*</FONT>&nbsp;<FONT
						color="#003399"><html:select property="geographyStatus"
						styleClass="w130" tabindex="1" styleId="geographyStatus" name="GeographyFormBean"
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
						readonly="true" size="15" maxlength="10" name="GeographyFormBean" />
					<script language="JavaScript">
new tcal ({
// form name
'formname': 'GeographyAction',
// input name
'controlname': 'startDate'
});

</script></TD>

					<TD width="185"><STRONG><FONT color="#000000"><bean:message
						bundle="view" key="application.end_date" /></FONT></STRONG>&nbsp;<html:text
						property="endDate" styleId="endDate" styleClass="box"
						readonly="true" size="15" maxlength="10" name="GeographyFormBean" />
					<script language="JavaScript">
new tcal ({
// form name
'formname': 'GeographyAction',
// input name
'controlname': 'endDate'
});

</script></TD>
					<TD align="left" width="84"><html:submit
						onclick="return validate();" styleClass="submit" value="Search"
						tabindex="4" /></TD>
				</TR>
				<TR>
					<TD width="192"><html:hidden property="methodName" styleId="methodName" /> 									
					<TR align="left">
					<TD colspan="4" class="text height19"><FONT color="red"><STRONG>
					<html:errors bundle="error" /><html:errors bundle="view" /></STRONG></FONT></TD>
				</TR>
			</TABLE>
			<TABLE width="90%" align="center" class="mLeft5" border="0">
				<logic:notEmpty name="GeographyFormBean" property="geographyList">
			<!-- 	<TR align="center">
						<TD align="left" colspan="14"><INPUT class="submit"
							onclick="exportDataToExcel();" tabindex="5" type="button"
							name="export" value="Export to Excel"></TD>
					</TR> 
			 -->		
					<TR align="center" bgcolor="#104e8b">
						<TD align="center"  bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><font color="white"><bean:message
							bundle="view" key="application.sno" /></font></SPAN></TD>
						<TD align="center"  bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><font color="white"><bean:message
							bundle="view" key="geography.geographycode" /></font></SPAN></TD>
						<TD align="center"  bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><font color="white"><bean:message
							bundle="view" key="geography.geographyname" /></font></SPAN></TD>
						<TD align="center"  bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><font color="white"><bean:message
							bundle="view" key="geography.status" /></font></SPAN></TD>
							<td align="center"  bgcolor="#CD0504"><font color="white"><bean:message
							bundle="view" key="application.edit" /></td>
					</TR>
					<logic:iterate id="geography" name="GeographyFormBean" property="geographyList" indexId="i">
							<TR align="center" bgcolor="#FCE8E6">
								<TD class="height19" align="right">
								<SPAN class="mLeft5 mTop5 mBot5 black10"> 
								 <bean:write name="geography" property="geographyId" /></SPAN>
								 <html:hidden property="geographyId"/></TD>
								<TD class="height19" align="left"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><bean:write
									name="geography" property="geographyCode" /> </SPAN></TD>
								<TD class="height19" align="left"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><bean:write
									name="geography" property="geographyName" /></SPAN></TD>
									
								<TD class="height19" align="left"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><bean:write
									name="geography" property="status" /></SPAN></TD>
								<td><logic:equal
									name="GeographyFormBean" property="editStatus" value="Y">
									<INPUT type="button" class="submit" value='Edit'
										onclick="return editDetails('${geography.geographyId}','<bean:write name="GeographyFormBean" property="level"/>')" />
								</logic:equal></td>
							</TR>		
					</logic:iterate>	
								<TR align="center">

						<TD align="center" bgcolor="#daeefc" colspan="14"><FONT
							face="verdana" size="2"> <c:if test="${PAGES!=''}">

							<c:if test="${PAGES>1}">

								    	Page:
								<c:if test="${PRE>=1}">
									<A href="#"
										onclick="submitData('<c:out value='${PRE}' />','<c:out value='${startDt}' />','<c:out value='${endDt}' />','<c:out value='${circleStatus}' />','<bean:write name="GeographyFormBean" property="level"/>','<bean:write name="GeographyFormBean" property="level"/>');"><
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
												onclick="submitData('<c:out value="${item}"/>','<c:out value='${startDt}' />','<c:out value='${endDt}' />','<c:out value='${circleStatus}' />','<bean:write name="GeographyFormBean" property="level"/>');"><c:out
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
												onclick="submitData('<c:out value="${item}"/>','<c:out value='${startDt}' />','<c:out value='${endDt}' />','<c:out value='${circleStatus}' />','<bean:write name="GeographyFormBean" property="level"/>');"><c:out
												value="${item}" /></A>
										</c:otherwise>
									</c:choose>
								 </c:forEach>
								</c:if>
								
								<c:if test="${NEXT<=PAGES}">
									<A href="#" 
										onclick="submitData('<c:out value='${NEXT}' />','<c:out value='${startDt}' />','<c:out value='${endDt}' />','<c:out value='${circleStatus}' />','<bean:write name="GeographyFormBean" property="level"/>');">Next></A>
								</c:if>
							</c:if>
						</c:if> </FONT></TD>
					</TR>
					
				</logic:notEmpty>	
				<html:hidden styleId="editStatus" property="editStatus"
					name="GeographyFormBean" />
				<html:hidden property="page" styleId="page" name="GeographyFormBean" />			
			</TABLE>
		</html:form>	
</td>
</TR>		
<TR align="center" bgcolor="4477bb" valign="top">
		<TD colspan="2" height="17" align="center"><jsp:include
			page="../common/footer.jsp" flush="" /></TD>
	</TR>
</TABLE>
</BODY>
</html:html>