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
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/chrome.js"></SCRIPT>
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/Cal.js"></SCRIPT>
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>

<SCRIPT type="text/javascript">
	function submitData(nextPage,cityId,status){
		document.forms[0].action="createBeatAction.do?methodName=viewBeatList&page="
				+ nextPage  + "&cityId" 
				+ cityId+ "&beatStatus=" + status;
		document.getElementById("page").value="";	
		document.forms[0].method="post";
		document.forms[0].submit();
	}

	function editDetails(beatId){
		   var form = document.forms[0];
	       document.getElementById("methodName").value="callEditBeat";
		   document.getElementById("beatId").value=beatId;
	       form.submit();
	} // function editDeatils ends
	
	function viewDetails(circleId){
		   var form = document.forms[0];
	       document.getElementById("methodName").value="getViewCircle";
		   document.getElementById("circleId").value=circleId;
	       form.submit();
	} // function viewDeatils ends
	
	function validate(){
/*		if(document.getElementById("circleId").value==""){
			alert('<bean:message bundle="error" key="errors.beat.circle" />');	  
			document.forms[0].circleId.focus();
			return false; 
		}
		if(document.getElementById("accountZoneId").value==0){
			alert('<bean:message bundle="error" key="errors.beat.zone" />');	  
			document.forms[0].accountZoneId.focus();
			return false; 
		}
		if(document.getElementById("accountCityId").value==0){
			alert('<bean:message bundle="error" key="errors.beat.city" />');	  
			document.forms[0].accountCityId.focus();
			return false; 
		}	*/
		if(document.getElementById("beatStatus").value==0){
			alert('<bean:message bundle="error" key="errors.beat.status_required" />');	  
			document.forms[0].beatStatus.focus();
			return false; 
		}  	 
		else {

			document.getElementById("page").value="";	
			document.getElementById("methodName").value="viewBeatList";
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
	document.getElementById("beatStatus").focus();
	}
	/* check if enter key is pressed */
	function checkKeyPressed(){
		if(window.event.keyCode=="13"){
			if(validate()){
				document.getElementById("methodName").value="callViewBeatList";
				return true;
			}
			return false;	
		}
	}
function getChange(condition)
{	
	var Id = "";
	if(condition == "zone"){
		Id = document.getElementById("circleId").value;
	}else
	if(condition == "city"){
		Id = document.getElementById("accountZoneId").value;
	}
	 var url="createBeatAction.do?methodName=getParentCategory&OBJECT_ID="+Id+"&condition="+condition;
	if(window.XmlHttpRequest) {
		req = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		req=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if(req==null) {
		alert("Browser Doesn't Support AJAX");
		return;
	}
	req.onreadystatechange = function(){getOnChange(condition);};
	req.open("POST",url,false);
	req.send();
}
function getOnChange(condition)
{	
	if (req.readyState==4 || req.readyState=="complete") 
  	{
		var xmldoc = req.responseXML.documentElement;
		if(xmldoc == null){		
			return;
		}
		optionValues = xmldoc.getElementsByTagName("option");
		var selectObj = "";
		if(condition == "zone"){
			selectObj = document.getElementById("accountZoneId");
		}else
		if(condition == "city"){
			selectObj = document.getElementById("accountCityId");
		}
		selectObj.options.length = optionValues.length + 1;
		for(var i=0; i < optionValues.length; i++)
		{	
			selectObj.options[(i+1)].text = optionValues[i].getAttribute("text");
			selectObj.options[(i+1)].value = optionValues[i].getAttribute("value");
		}
	}
}

</SCRIPT>
</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onload="doLoad();"
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
			action="/createBeatAction" name="createBeatAction"
			type="com.ibm.dp.beans.DPCreateBeatFormBean">
			<TABLE border="0" cellpadding="5" cellspacing="0" class="text"
				width="700">
				<TR>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/view-Beat.gif"
						width="505" height="22"><BR>
					</TD>
					<td><html:errors property="message.account" /></td>
				</TR>
				<%-- 				<TR>
				<td class="text pLeft15">
						<STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="beat.circlename" /><FONT color="RED">*</FONT>:</FONT></STRONG>
					</td>	
					<td><FONT color="#003399">
					<html:select
						property="circleId" tabindex="1" styleClass="w130" onchange="getChange('zone');">
						<html:option value="0">
							<bean:message key="application.option.select" bundle="view" />
						</html:option>
						<logic:notEmpty name="DPCreateBeatForm" property="circleList">
						<bean:define id="circle"
						name="DPCreateBeatForm" property="circleList" />
						<html:options collection="circle" labelProperty="circleName"
							property="circleId" />
						</logic:notEmpty>
					</html:select></FONT>
					</td>	
				<td class="text pLeft15">
						<STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="beat.zonename" /><FONT color="RED">*</FONT>:</FONT></STRONG>
					</td>	
					<td ><FONT color="#003399">
					<html:select
						property="accountZoneId" tabindex="2" styleClass="w130" onchange="getChange('city');">
						<html:option value="0">
							<bean:message key="application.option.select" bundle="view" />
						</html:option>
					</html:select></FONT>
					</td>	
				<td class="text pLeft15">
						<STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="beat.cityname" /><FONT color="RED">*</FONT>:</FONT></STRONG>
					</td>	
					<td><FONT color="#003399">
					<html:select
						property="accountCityId" tabindex="3" styleClass="w130">
						<html:option value="0">
							<bean:message key="application.option.select" bundle="view" />
						</html:option>
					</html:select></FONT>
					</td>	--%>
				<tr>
					<td><STRONG><FONT color="#000000"><bean:message
						bundle="view" key="beat.cityname" />:</FONT></STRONG></td>
					<td><html:text property="accountCityName"
						name="DPCreateBeatForm" readonly="true"/></td>
					<TD><html:hidden property="methodName" styleId="methodName" />

					<STRONG><FONT color="#000000"><bean:message
						bundle="view" key="report.status" /></FONT></STRONG> <FONT color="RED">*</FONT>&nbsp;:<FONT
						color="#003399"></TD>
					<TD><html:select property="beatStatus" styleClass="w130"
						tabindex="4" styleId="beatStatus" name="DPCreateBeatForm">
						<html:option value="0">
							<bean:message key="application.option.select" bundle="view" />
						</html:option>
						<html:option value="A">
							<bean:message key="status.active" bundle="view" />
						</html:option>
						<html:option value="D">
							<bean:message key="status.inactive" bundle="view" />
						</html:option>
					</html:select></TD>
				</TR>
				<tr>
					<TD align="center" width="84" colspan="4"><html:submit
						onclick="return validate();" styleClass="submit" value="Search"
						tabindex="5" /></TD>
				</tr>
				<TR align="left">
					<TD colspan="4" class="text height19"><FONT color="#ff0000"><html:errors
						bundle="error" /><html:errors bundle="view" /></FONT></TD>
				</TR>
			</TABLE>
			<TABLE width="90%" align="center" class="mLeft5" border="0">
				<logic:notEmpty name="DPCreateBeatForm" property="beatDetails">
					<%-- 	<TR align="center">
						<TD align="left" colspan="8"><INPUT class="submit"
							onclick="exportDataToExcel();" tabindex="5" type="button"
							name="export" value="Export to Excel"></TD>
					</TR>	--%>
					<TR align="center" bgcolor="#104e8b">
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="application.sno" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="beat.beatname" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="beat.circlename" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="beat.zonename" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="beat.cityname" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="beat.description" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="beat.status" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="application.edit" /></SPAN> <%-- 	&nbsp;|&nbsp;<bean:message
							bundle="view" key="application.view" /></SPAN>  --%></TD>
					</TR>
					<logic:iterate id="beat" name="DPCreateBeatForm"
						property="beatDetails" indexId="i">
						<TR align="center" bgcolor="#FCE8E6">
							<TD align="center" class="height19"><SPAN
								class="mLeft5 mTop5 mBot5 black10"> <bean:write
								name="beat" property="rowNum" /> </SPAN></TD>
							<html:hidden property="beatId" name="beat" />
							<TD align="center" class="height19"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="beat" property="beatName" /> </SPAN></TD>
							<TD align="center" class="height19"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="beat" property="circleName" /></SPAN></TD>
							<TD align="center" class="height19"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="beat" property="accountZoneName" /></SPAN></TD>
							<TD align="center" class="height19"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="beat" property="accountCityName" /></SPAN></TD>
							<TD align="center" class="height19"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="beat" property="description" /></SPAN></TD>
							<logic:match value="A" property="beatStatus" name="beat">
								<TD align="center" class="height19"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><bean:message
									bundle="view" key="application.option.active" /></SPAN></TD>
							</logic:match>
							<logic:match value="D" property="beatStatus" name="beat">
								<TD align="center" class="height19"><SPAN
									class="mLeft5 mTop5 mBot5 black10"> <bean:message
									bundle="view" key="application.option.inactive" /></SPAN></TD>
							</logic:match>
							<html:hidden property="circleName" styleId="circleName" />
							<html:hidden property="circleId" styleId="circleId" />
							<html:hidden property="accountZoneName" />
							<html:hidden property="accountZoneId" />
							<html:hidden property="accountCityName" styleId="accountCityName" />
							<html:hidden property="accountCityId" styleId="accountCityId" />
							<TD class=" height19" align="center"><logic:equal
								name="DPCreateBeatForm" property="editStatus" value="Y">
								<INPUT type="button" class="submit" value='Edit'
									onclick="return editDetails('${beat.beatId}')" />
							</logic:equal> <%-- 	 			&nbsp;|
								 &nbsp;<INPUT type="button" class="submit" value='View'
									onclick="return viewDetails('${beat.beatId}')" /> 	--%></TD>
						</TR>
					</logic:iterate>
					<TR align="center">

						<TD align="center" bgcolor="#daeefc" colspan="14"><FONT
							face="verdana" size="2"> <c:if test="${PAGES!=''}">

							<c:if test="${PAGES>1}">

								    	Page:
								<c:if test="${PRE>=1}">
									<A href="#"
										onclick="submitData('<c:out value='${PRE}' />','<c:out value='${accountCityId}' />','<c:out value='${beatStatus}' />');"><
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
													onclick="submitData('<c:out value="${item}"/>','<c:out value='${accountCityId}' />','<c:out value='${beatStatus}' />');"><c:out
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
													onclick="submitData('<c:out value="${item}"/>','<c:out value='${accountCityId}' />','<c:out value='${beatStatus}' />');"><c:out
													value="${item}" /></A>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:if>

								<c:if test="${NEXT<=PAGES}">
									<A href="#"
										onclick="submitData('<c:out value='${NEXT}' />','<c:out value='${accountCityId}' />','<c:out value='${beatStatus}' />');">Next></A>
								</c:if>
							</c:if>
						</c:if> </FONT></TD>
					</TR>

				</logic:notEmpty>
				<html:hidden property="page" styleId="page" name="DPCreateBeatForm" />
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
