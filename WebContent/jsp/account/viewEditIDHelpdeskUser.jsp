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
<script language="javascript" src="scripts/calendar1.js">
</script>
<script language="javascript" src="scripts/Utilities.js">
</script>
<script language="javascript" src="scripts/cal2.js">
</script>
<script language="javascript" src="scripts/cal_conf2.js">
</script>
<SCRIPT language="javascript" type="text/javascript"> 

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
		
	    document.getElementById("methodName").value="searchIdHelpUserAccount";
	    document.forms[0].submit();
		return true;
	} 
	function submitData(searchType,searchValue){
	    document.forms[0].action="initCreateAccountItHelp.do?methodName=searchIdHelpUserAccount&searchType=" + searchType + "&searchValue="
				+ searchValue ;
		document.forms[0].method="post";
		document.forms[0].submit();
		document.getElementById("searchFieldName").value="0";
	}

	function editAccount(accountId){
	//	 getChange(accountId);
		document.getElementById("accountId").value=accountId;
		document.getElementById("methodName").value="getAccountInfoEdit"; 
		document.forms[0].submit();
	}// function editAccount ends
	
	

	function onload()
	{
	document.getElementById("searchFieldName").value="0";
	document.getElementById("searchFieldValue").value="";
	document.getElementById("circleId").value="";
	document.getElementById("listStatus").value="";
	document.getElementById("startDt").value="";
	document.getElementById("endDt").value="";
	document.getElementById("userRole").value="";
	setSearchControlDisabled()
	}
	function inactiveAccount(accountId,accountCode){
		var unlock=confirm( 'Are you sure to In-activate the user account?' );
	   if(unlock){ 
	    var form = document.forms[0];
       document.getElementById("methodName").value="inActiveIdHelpdeskUser";
	   document.getElementById("accountId").value=accountId;
       form.submit();
       }
     }
        
	function activeAccount(accountId,accountCode){
		var unlock=confirm( 'Are you sure to Activate the user account?' );
	   	if(unlock){ 
	    var form = document.forms[0];
       document.getElementById("methodName").value="activeIdHelpdeskUser";
	   document.getElementById("accountId").value=accountId;
	   document.getElementById("accountCode").value=accountCode;
	   
       form.submit();
       }
     } 
	


	
</SCRIPT>
</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" >

<TABLE cellspacing="0" cellpadding="0" border="0" height="100%"
	valign="top">
	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include
			page="../common/dpheader.jsp" flush="" /></TD>
	</TR>
	<TR valign="top">
		<TD align="left" bgcolor="b4d2e7" valign="top" height="100%"><jsp:include
			page="../common/leftHeader.jsp" flush="" /></TD>

		<TD valign="top" width="100%" height="100%"><html:form
			action="initCreateAccountItHelp" name="DPCreateAccountITHelpFormBean"
			type="com.ibm.dp.beans.DPCreateAccountITHelpFormBean">


			<TABLE border="0" cellpadding="5" cellspacing="0" class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/searchAccount.gif"
						width="505" height="22" alt=""></TD>
				</TR>

				<TR>
				</TR>

				<TR>
					<TD colspan="4" align="center"><FONT color="#ff0000" size="-2"><html:errors
						bundle="error" property="error.account" /></FONT> <FONT color="#ff0000"
						size="-2"><html:errors bundle="view"
						property="message.account" /></FONT></TD>
					
					<html:hidden property="methodName" styleId="methodName" />
				</TR>

				
			</TABLE>
			<TABLE class="text" border="0">
				<TR>
					<TD><FONT color="#000000"><STRONG><bean:message
						bundle="view" key="account.param" /></STRONG></FONT><FONT color="RED">*</FONT> :
					</TD>
					<TD><html:select property="searchFieldName"
						styleId="searchFieldName" onchange="setSearchControlDisabled()"
						tabindex="5" styleClass="w130">
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
						<html:option value="E">
							<bean:message bundle="view" key="account.accountemail" />
						</html:option>
					</html:select> <html:text property="searchFieldValue" styleClass="box"
						styleId="searchFieldValue" size="19" maxlength="20" tabindex="6" />
					</TD>
					<TD align="center"><INPUT class="submit"
						onclick="return validate();" type="button" tabindex="7"
						name="Submit" value="Search"></TD>
				</TR>
			</TABLE>
			
			<TABLE align="center" class="mLeft5" width="100%">
				<logic:notEmpty name="DPCreateAccountITHelpFormBean" property="displayDetails">
					<thead>
				  		<TR align="center" bgcolor="#CD0504"  style="position: relative; 
				  		top:expression(this.offsetParent.scrollTop-2); color:#ffffff !important; ">
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="application.sno" /></SPAN></TD>
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
							bundle="view" key="account.accountAddress" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.email" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"> <bean:message
							bundle="view" key="account.status" /></SPAN></TD>

						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.createddate" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.createdby" /></SPAN></TD>
						<TD align="left" bgcolor="#CD0504" nowrap><SPAN	class="white10heading mLeft5 mTop5">
						Activate/In-Activate
						</SPAN></TD>
					</TR>
					</thead>
					<tbody>
					<logic:iterate id="account" property="displayDetails"
						name="DPCreateAccountITHelpFormBean" indexId="i">
						<TR bgcolor="#FCE8E6">
							<TD class="text" align="center">
							<bean:write name="account" property="rowNum" /></TD>
							<TD class="text" align="center"> 
							<bean:write name="account" property="accountCode" /></TD>
							<TD class="text" align="center">
							<bean:write name="account" property="accountName" /></TD>
							<TD class="text" align="center">
							<bean:write name="account" property="mobileNumber" /></TD>
							<TD class="text" align="left">
							<bean:write name="account" property="accountAddress" /></TD>
							<TD class="text" align="center">
							<bean:write name="account" property="email" /></TD>
							<TD class="text" align="center"><logic:match name="account"
								property="status" value="A">
								<bean:message bundle="view" key="application.option.active" />
							</logic:match> <logic:notMatch name="account" property="status" value="A">
								<bean:message bundle="view" key="application.option.inactive" />
							</logic:notMatch></TD>
							

							<TD class="text" align="center"><bean:write name="account"
								property="createdDt" formatKey="formatDate" bundle="view" /></TD>
							<TD class="text" align="center"><bean:write name="account"
								property="createdByName" /></TD>
							<TD class="text" align="left" nowrap>
							<logic:match name="account" property="unlockStatus"	value="Y">
								<INPUT type="button" class="submit" value='In-Activate'
										onclick="return inactiveAccount('${account.accountId}','${account.accountCode}')" />&nbsp;
								
							</logic:match>
							<logic:match name="account" property="unlockStatus"	value="N">
								<INPUT type="button" class="submit" value='Activate'
										onclick="return activeAccount('${account.accountId}','${account.accountCode}')" />&nbsp;
								
							</logic:match>
							</TD>
						</TR>
					</logic:iterate>
					</tbody>
					<html:hidden styleId="methodName" property="methodName"
						value="getAccountInfo" />
					<html:hidden styleId="accountId" property="accountId" />
					<html:hidden styleId="accountCode" property="accountCode" />
					
				</logic:notEmpty>
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
