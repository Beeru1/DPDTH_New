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
<LINK href="<%=request.getContextPath()%>/theme/text.css"
	rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/theme/chromestyle2.css" />
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/chrome.js"></SCRIPT>
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/JScriptLib.js"></SCRIPT>
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<script>
function getChange(Id)
{
	 var url="initCreateAccount.do?methodName=getParentCategory&OBJECT_ID="+Id+"&condition=category";
	if(window.XmlHttpRequest) {
		req = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		req=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if(req==null) {
		alert("Browser Doesn't Support AJAX");
		return;
	}
	req.onreadystatechange = getOnChange;
	req.open("POST",url,false);
	req.send();
}
function getOnChange()
{
	if (req.readyState==4 || req.readyState=="complete") 
  	{
		var xmldoc = req.responseXML.documentElement;
		if(xmldoc == null){		
			return;
		}
		optionValues = xmldoc.getElementsByTagName("option");
		var selectObj = window.opener.document.forms[0].tradeCategoryId;
		
		selectObj.options.length = optionValues.length + 1;
		for(var i=0; i < optionValues.length; i++)
		{
			selectObj.options[(i+1)].text = optionValues[i].getAttribute("text");
			selectObj.options[(i+1)].value = optionValues[i].getAttribute("value");
		}
	}
}
</script>
<SCRIPT language="javascript" type="text/javascript"> 
		function submitData(nextPage,searchType,searchValue,circleId,accountLevel){
		alert("circleId...."+circleId)
		document.forms[0].action="initCreateAccount.do?methodName=getSearchParentAccountList&page="
				+ nextPage + "&searchType=" + searchType + "&searchValue="
				+ searchValue + "&circleId=" + circleId + "&accountLevel=" 
				+ accountLevel;
		document.forms[0].method="post";
		document.forms[0].submit();
	}
	function validate(){
		if(document.getElementById("searchFieldName").value=="0" ){
			alert('<bean:message bundle="error" key="error.account.invalidsearch.search" />');
		    document.forms[0].searchFieldName.focus();
		    return false; 
		}else if(!(document.getElementById("searchFieldName").value=="A") && isNull('document.forms[0]','searchFieldValue')){
			alert('<bean:message bundle="error" key="error.account.invalidsearch.textbox" />');	  
			document.forms[0].searchFieldValue.focus();
			return false; 
		}  	 
	    getAccount();	 
		return true;
	} // Validate Form Ends
	function getAccountId(accountId){
		document.getElementById("accountId").value=accountId;
		document.getElementById("methodName").value="getAccountInfo"; 
		document.forms[0].submit();
	}
	function setSearchControlDisabled(){
		if((document.getElementById("searchFieldName").value=="0") || (document.getElementById("searchFieldName").value=="A")){ 
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
	
	function getCode(distributorName,distID){
	//alert("inside getcode !!!!!");
	document.forms[0].action="LoginAction.do?&distName="+ distributorName +"&distID="+distID;
	//alert(document.forms[0].action);
	document.forms[0].method="post";
	document.forms[0].submit();
	}
	
	// this function open window to search and select distributor code 
	//function  {
	  //  var circleid=window.opener.document.forms[0].circleId.value;
	  //	document.getElementById("accountLevel").value= 5;
	   // document.getElementById("methodName").value="getSearchParentAccountList";
	    // var name="getSearchParentAccountList";
	    //var send = "methodName="+name+"&circleid="+circleid+"&accountLevel=7";
		//return true;
	//}
	
</SCRIPT>
</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onkeypress="return checkKeyPressed();"
	onload="setSearchControlDisabled()">
<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">
	<TR>
		<TD colspan="2" valign="top" width="100%"></TD>
	</TR>
	<TR valign="top">
		<TD width="167" align="left" valign="top" height="100%"><html:form
			action="initCreateAccount" focus="searchFieldName">
			<TABLE width="680" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/proxyLogin.gif"
						width="505" height="22" alt=""></TD>
				</TR>
				<TR>
				</TR>
				<TR>
					<TD colspan="4" align="center"><FONT color="#ff0000" size="-2"><html:errors
						bundle="error" property="error.account" /></FONT> <FONT color="#ff0000"
						size="-2"><html:errors bundle="view"
						property="message.account" /></FONT></TD>
						
						
					<html:hidden property="methodName" value="getSearchParentAccountList" />
					
				</TR>
				<TR>
					<TD class="text pLeft15"><FONT color="#000000"><STRONG><bean:message
						bundle="view" key="account.param" /></STRONG></FONT><FONT color="RED">*</FONT></TD>
					<TD><html:select property="searchFieldName"
						styleId="searchFieldName" onchange="setSearchControlDisabled()"
						tabindex="1" styleClass="w130">
						<html:option value="0">
							<bean:message bundle="view" key="application.option.select" />
						</html:option>
						<html:option value="A">
							<bean:message bundle="view" key="application.option.select_all" />
						</html:option>
						<html:option value="M">
							<bean:message bundle="view" key="account.mobileNumber" />
						</html:option>
						<html:option value="C">
							<bean:message bundle="view" key="account.accountCode" />
						</html:option>
						<html:option value="N">
							<bean:message bundle="view" key="account.accountName" />
						</html:option>
						<html:option value="P">
							<bean:message bundle="view" key="account.parentAccount" />
						</html:option>
						<html:option value="E">
							<bean:message bundle="view" key="account.accountemail" />
						</html:option>
					</html:select> <html:text property="searchFieldValue" styleClass="box"
						styleId="searchFieldValue" size="19" maxlength="20" tabindex="2" />
					<INPUT class="submit" onclick="return validate();" type="submit"
						tabindex="3" name="Submit" value="Search"></TD>
				</TR>
				<TR>
					<TD width="70"></TD>
				</TR>
				<TR>
					<TD colspan="4">
					<TABLE width="500" align="left" class="mLeft5">
						<logic:notEmpty name="DPCreateAccountForm" property="displayDetails">
							<TR align="center" bgcolor="#104e8b">
								<TD align="left" bgcolor="#CD0504" width="20%"><SPAN
									class="white10heading mLeft5 mTop5">Select</SPAN></TD>
								<TD align="center" bgcolor="#CD0504"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="account.accountCode" /></SPAN></TD>
								<TD align="center" bgcolor="#CD0504"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="account.accountName" /></SPAN></TD>
								<TD align="center" bgcolor="#CD0504"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="account.mobileNumber" /></SPAN></TD>
								<TD align="center" bgcolor="#CD0504"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="account.parentAccount" /></SPAN></TD>
								<TD align="center" bgcolor="#CD0504"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="account.email" /></SPAN></TD>
								<TD align="center" bgcolor="#CD0504"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="account.availablebalance" /></SPAN></TD>
							</TR>
							<logic:iterate id="account" name="DPCreateAccountForm"
								property="displayDetails" indexId="i">
								<TR align="center" bgcolor="#FCE8E6">
									<TD class="text" align="left"><html:button
										property="accountid" styleId="accountid" value="Login to: "
										onclick="getCode('${account.accountCode}','${account.accountId}')"
										styleClass="submit" /></TD>
									<TD class="text" nowrap><bean:write name="account" 
										property="accountCode" /></TD>
									<TD class="text" nowrap><bean:write name="account"
										property="accountName" /></TD>
									<TD class="text" nowrap align="right"><bean:write
										name="account" property="mobileNumber" /></TD>
									<TD class="text" nowrap><bean:write name="account"
										property="parentAccount" /></TD>
									<TD class="text" nowrap><bean:write name="account"
										property="email" /></TD>
									<TD class="text" nowrap align="right"><bean:write
										name="account" property="availableBalance" format="#0.00" /></TD>
										<html:hidden property="tradeId" styleId="tradeId" name="account"/>
										<html:hidden property="tradeCategoryId" styleId="tradeCategoryId" name="account"/>
										<html:hidden property="tradeName" styleId="tradeName" name="account"/>
										
										<html:hidden property="tradeCategoryName" styleId="tradeCategoryName" name="account"/>
													
							</logic:iterate>
						</logic:notEmpty>
						<html:hidden styleId="methodName" property="methodName"
							value="getSearchParentAccountList" />
						<html:hidden styleId="accountId" property="accountId" />
						<html:hidden property="circleId" styleId="circleId" value =""/>
						<html:hidden property="accountLevel" styleId="accountLevel" value="7" />
						<TR>
							<TD width="20" align="left">&nbsp;&nbsp;&nbsp;</TD>
						</TR>
						<TR align="center">
							<TD align="center" bgcolor="#daeefc" colspan="7"><FONT
								face="verdana" size="2"> <c:if test="${PAGES!=''}">
								<c:if test="${PAGES>1}">
								    	Page:
								    <c:if test="${PRE>=1}">
										<A href="#"
											onclick="submitData('<c:out value='${PRE}' />','<c:out value='${searchType}'/>',
											'<c:out value='${searchValue}' />','<c:out value='${circleId}' />',
											'<c:out value='${accountLevel}' />');">
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
														onclick="submitData('<c:out value='${item}' />','<c:out value='${searchType}' />','<c:out value='${searchValue}' />','<c:out value='${circleId}' />','<c:out value='${accountLevel}' />');"><c:out
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
														onclick="submitData('<c:out value='${item}' />','<c:out value='${searchType}' />','<c:out value='${searchValue}' />','<c:out value='${circleId}' />','<c:out value='${accountLevel}' />');"><c:out
														value="${item}" /></A>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:if>
									<c:if test="${NEXT<=PAGES}">
										<A href="#"
											onclick="submitData('<c:out value='${NEXT}' />','<c:out value='${searchType}' />','<c:out value='${searchValue}' />','<c:out value='${circleId}' />','<c:out value='${accountLevel}' />');">Next></A>
									</c:if>
								</c:if>
							</c:if> </FONT></TD>
						</TR>
						<!-- TODO: bhanu,	sep	10,	2007 implement Pagination	  -->
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