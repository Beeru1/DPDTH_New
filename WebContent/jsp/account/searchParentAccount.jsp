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
	//var Id = document.getElementById("businessCategory").value;   
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
		document.forms[0].action="initCreateAccount.do?methodName=getParentAccountList&page="
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
	// this function open child window to search and select distributor code 
	function getAccount(){
		if(window.opener.document.forms[0].circleId.value=="0"){
			alert('<bean:message bundle="error" key="error.account.circleid" />');
			window.opener.document.forms[0].circleId.focus();
			return false; 
		}
	    var circleid=window.opener.document.forms[0].circleId.value;
	    document.getElementById("circleId").value=window.opener.document.forms[0].circleId.value;
	    document.getElementById("accountLevel").value=window.opener.document.forms[0].accountLevel.value;
	    document.getElementById("methodName").value="getParentAccountList";
	    var name="getParentAccountList";
	    var send = "methodName="+name+"&circleid="+circleid+"&parent="+parent;
		return true;
	}
	function getCode(accountCode,tradeName,tradeCategoryName,parentAccount,outletType,loginName,billableName){
		window.opener.document.forms[0].parentAccount.value=accountCode;
		window.opener.document.forms[0].parentAccountName.value=accountCode;
		window.opener.document.forms[0].parentLoginName.value=loginName;
		if(window.opener.document.getElementById("roleName").value!="Y"){
			getChange(tradeName);
			window.opener.document.forms[0].tradeId.value=tradeName;
			window.opener.document.forms[0].tradeCategoryId.value = tradeCategoryName;
	    	window.opener.document.forms[0].billableCode.value=accountCode;
	    	window.opener.document.forms[0].outletType.value = outletType;
	    	window.opener.document.forms[0].billableLoginName.value=loginName;
	    	window.opener.document.forms[0].billableAccountName.value=accountCode;
	    	if(window.opener.document.getElementById("accountLevel").value!=""){
		    	if(window.opener.document.getElementById("accountLevel").value=="8"){
	    	window.opener.document.forms[0].billableCode.value=parentAccount;
			window.opener.document.forms[0].billableLoginName.value=billableName;
	    	window.opener.document.forms[0].billableAccountName.value=parentAccount;	    	
	    	}
	    }	
	    }
    	window.close();
		return;
	}
</SCRIPT>
</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onkeypress="return checkKeyPressed();"
	onload="setSearchControlDisabled()">
<html:form action="initCreateAccount" focus="searchFieldName">

<html:hidden property="methodName" value="getParentAccountList" />
	<TABLE cellspacing="0" cellpadding="0" border="0" bordercolor='GREEN' width="100%" height="100%" valign="top">
		<TR valign="top" height="20%">
			<TD width="100%" align="left" valign="top">
				<TABLE width="100%" border="0" cellpadding="5" cellspacing="0" bordercolor='RED' class="text">
					<TR>
						<TD colspan="2" class="text"><BR>
							<IMG src="<%=request.getContextPath()%>/images/searchAccount.gif" width="505" height="22" alt="">
						</TD>
					</TR>
					<TR>
						<TD colspan="2" align="center"><FONT color="#ff0000" size="-2"><html:errors
							bundle="error" property="error.account" /></FONT> <FONT color="#ff0000"
							size="-2"><html:errors bundle="view"
							property="message.account" /></FONT>
						</TD>
					</TR>
					<TR>
						<TD class="text pLeft15" width='30%'><FONT color="#000000"><STRONG><bean:message
							bundle="view" key="account.param" /></STRONG></FONT><FONT color="RED">*</FONT>
						</TD>
						<TD width='70%'>
							<html:select property="searchFieldName" styleId="searchFieldName" onchange="setSearchControlDisabled()" tabindex="1" styleClass="w130">
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
							</html:select> 
						
							<html:text property="searchFieldValue" styleClass="box" styleId="searchFieldValue" size="19" maxlength="20" tabindex="2" />
							<INPUT class="submit" onclick="return validate();" type="submit"
							tabindex="3" name="Submit" value="Search">
						</TD>
					</TR>
				</TABLE>
			</td>
		</TR>
		<TR>
			<TD width="100%" align="left" valign="top">
				<div style="DISPLAY: block">
					<DIV style="height: 300px; overflow: auto;" width="100%">
						<TABLE width="100%" align="left" class="mLeft5">
							<logic:notEmpty name="DPCreateAccountForm" property="displayDetails">
							<thead>
								<TR align="center" bgcolor="#CD0504"  style="position: relative; 
				  					top:expression(this.offsetParent.scrollTop-2); color:#ffffff !important; ">
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

								</TR>
							</thead>
							<tbody>
								<logic:iterate id="account" name="DPCreateAccountForm" property="displayDetails" indexId="i">
								<TR align="center" bgcolor="#FCE8E6">
									<TD class="text" align="left"><html:button
										property="accountid" styleId="accountid" value="Select"
										onclick="getCode('${account.accountName}','${account.tradeId}','${account.tradeCategoryId}','${account.parentAccount}','${account.outletType}','${account.accountCode}','${account.billableLoginName}')"
										styleClass="submit" /></TD>
									<TD class="text"><bean:write name="account"
										property="accountCode" /></TD>
									<TD class="text"><bean:write name="account"
										property="accountName" /></TD>
									<TD class="text" align="right"><bean:write
										name="account" property="mobileNumber" /></TD>
									<TD class="text"><bean:write name="account"
										property="parentAccount" /></TD>
									<TD class="text"><bean:write name="account"
										property="email" /></TD>
										<html:hidden property="tradeId" styleId="tradeId" name="account"/>
										<html:hidden property="tradeCategoryId" styleId="tradeCategoryId" name="account"/>
										<html:hidden property="tradeName" styleId="tradeName" name="account"/>
										<html:hidden property="tradeCategoryName" styleId="tradeCategoryName" name="account"/>
										<html:hidden property="outletType" styleId="outletType" name="account"/>
										<html:hidden property="billableLoginName" name="account"/>
										<html:hidden property="billableAccountName" name="account"/>
								</TR>
								</logic:iterate>
							</tbody>
							</logic:notEmpty>
						
							<html:hidden styleId="methodName" property="methodName" value="getParentAccountList" />
							<html:hidden styleId="accountId" property="accountId" />
							<html:hidden property="circleId" styleId="circleId" />
							<html:hidden property="accountLevel" styleId="accountLevel" />
						<!-- TODO: bhanu,	sep	10,	2007 implement Pagination	  -->
						</TABLE>
					</DIV>
				</div>
			</TD>
		</TR>
		<TR align="center" bgcolor="4477bb" valign="top">
			<TD colspan="2" height="17" align="center"><jsp:include
				page="../common/footer.jsp" flush="" /></TD>
		</TR>
	</TABLE>
</html:form>
</BODY>
</html:html>