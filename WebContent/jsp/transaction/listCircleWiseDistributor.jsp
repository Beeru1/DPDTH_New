<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--  <%@ taglib uri="/tags/pager" prefix="pg" --%>
<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="<%=request.getContextPath()%>/theme/text.css" rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/chrome.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<TITLE></TITLE>
<SCRIPT language="javascript" type="text/javascript"> 
 
	// function that checks that is any field value is blank or not
	function validate(){
		if(isNull('document.forms[0]','circleId')|| document.getElementById("circleId").value==""){
			alert('<bean:message bundle="error" key="error.account.circleid" />')
			return false; 
		}
 		if( document.getElementById("circleId").value=="0"){
			alert('<bean:message bundle="error" key="error.account.circleid" />')
	  		return false; 
	 	}
	 	getAccount();
		return true;
	}

	function getCode(accountCode,accountId){
		window.opener.document.forms[0].accountCode.value=accountCode;
		window.opener.document.forms[0].accountId.value=accountId;
		window.close();
		return;
	}
	
    function getAccount(){
		document.getElementById("methodName").value="getDistributorList";
		return;
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

	function setFocus(){
		if(document.getElementById("disableCircle").value=="Y"){
			document.getElementById("Submit2").focus();
		}else{
			document.getElementById("circleId").focus();
		}
	}

</SCRIPT>

</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  onkeypress="return checkKeyPressed();" onload="setFocus()">
<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%">

	<TR height="70%" valign="top">

		<TD width="23%" align="left" valign="top"></TD>
		<TD valign="top"><html:form action="DistributorTransaction"
			method="post">
			<TABLE width="680" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/distributorList.gif" width="505" height="22"
						alt=""></TD>
				</TR>
				<TR>
					<TD><FONT color="RED"><STRONG><html:errors
						bundle="error" property="errors.transaction.distribtor" /></STRONG></FONT></TD>
				</TR>

				<TR>
					<TD nowrap class="text pLeft15"><STRONG><bean:message
						bundle="view" key="account.circleId" /></STRONG><FONT color="RED">*</FONT>
					<SPAN class="pLeft15"></SPAN> <logic:equal
						name="DistributorTransactionFormBean" property="disableCircle"
						value="Y">
						<html:select property="circleId" tabindex="1" styleClass="w130"
							disabled="true">
							<html:option value="0">
								<bean:message key="application.option.select" bundle="view" />
							</html:option>

							<html:options collection="circleList" labelProperty="circleName"
								property="circleId" />
						</html:select>
					</logic:equal> <logic:notEqual name="DistributorTransactionFormBean"
						property="disableCircle" value="Y">
						<html:select property="circleId" tabindex="1" styleClass="w130">
							<html:option value="0">
								<bean:message key="application.option.select" bundle="view" />
							</html:option>

							<html:options collection="circleList" labelProperty="circleName"
								property="circleId" />
						</html:select>
					</logic:notEqual> <SPAN class="pLeft15"></SPAN> <INPUT class="submit" type="submit"
						id="Submit2" name="Submit2" value="Search"
						onclick="return validate();"></TD>
				</TR>
				<TR>

					<TD colspan="4"><logic:notEmpty
						name="DistributorTransactionFormBean" property="accountList">
						<TABLE width="500" align="left" class="mLeft5">
							<TR align="left" bgcolor="#104e8b">
								<TD align="left" bgcolor="#cd0504" width="20%"><SPAN
									class="white10heading mLeft5 mTop5">Select</SPAN></TD>
								<TD align="left" bgcolor="#cd0504" width="20%"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="account.accountCode" /></SPAN></TD>
								<TD align="left" bgcolor="#cd0504" width="60%"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="account.accountName" /></SPAN></TD>
								<TD align="left" bgcolor="#cd0504" width="60%"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="account.mobileNumber" /></SPAN></TD>
								<TD align="left" bgcolor="#cd0504" width="60%"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="account.rate" /></SPAN></TD>
								<TD align="left" bgcolor="#cd0504" width="60%"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="account.operatingbalance" /></SPAN></TD>
								<TD align="left" bgcolor="#cd0504" width="60%"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="account.availablebalance" /></SPAN></TD>



							</TR>
						
							<logic:iterate id="account" name="DistributorTransactionFormBean"
								property="accountList" indexId="i">

								<TR align="center"
									bgcolor="#fce8e6">
									<TD class="text" nowrap align="left"><html:button
										property="accountid" styleId="accountid" value="Select" styleClass="submit"
										onclick="getCode('${account.accountCode}','${account.accountId}')" /></TD>
									<TD class=" height19" align="left"><SPAN
										class="mLeft5 mTop5 mBot5 black10"><bean:write
										name="account" property="accountCode" /></SPAN></TD>
									<TD class=" height19" align="left"><SPAN
										class="mLeft5 mTop5 mBot5 black10"><bean:write
										name="account" property="accountName" /></SPAN></TD>
									<TD class=" height19" align="center"><SPAN
										class="mLeft5 mTop5 mBot5 black10"><bean:write
										name="account" property="mobileNumber" /></SPAN></TD>
									<TD class=" height19" align="right"><SPAN
										class="mLeft5 mTop5 mBot5 black10"><bean:write
										name="account" property="rate" format="#0.00" /></SPAN></TD>
									<TD class=" height19" align="right"><SPAN
										class="mLeft5 mTop5 mBot5 black10"><bean:write
										name="account" property="operatingBalance" format="#0.00" /></SPAN></TD>
									<TD class=" height19" align="right"><SPAN
										class="mLeft5 mTop5 mBot5 black10"><bean:write
										name="account" property="availableBalance" format="#0.00" /></SPAN></TD>
								</TR>
								
							</logic:iterate>

						</TABLE>
					</logic:notEmpty></TD>


				</TR>
				<html:hidden property="methodName" styleId="methodName" />
				<html:hidden property="disableCircle" styleId="disableCircle" />
			</TABLE>
		</html:form></TD>
	</TR>
	<TR valign="bottom">
		<TD colspan="2"><jsp:include page="../common/footer.jsp" flush="" /></TD>
	</TR>
</TABLE>


</BODY>
</html:html>
