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
<SCRIPT language="javascript" type="text/javascript"><!-- 

	function submitData(nextPage,searchType,searchValue,circleId,userRole,listStatus,startDate,endDate){
	    document.forms[0].action="initCreateAccountItHelp.do?methodName=searchAccount&page="
				+ nextPage + "&searchType=" + searchType + "&searchValue="
				+ searchValue + "&circleId="
				+ circleId +  "&listStatus=" + listStatus + "&startDate=" 
				+ startDate + "&endDate=" + endDate+ "&userRole=" +userRole;
		document.getElementById("page").value="";
		document.forms[0].method="post";
		document.forms[0].submit();
		document.getElementById("listStatus").value="0";
		document.getElementById("searchFieldName").value="0";
	}

	function validate()
	{
		var selectedSearchType= document.getElementById("searchFieldName").value;
		if (selectedSearchType!="A")
		{
			if(selectedSearchType =="0")
			{
				alert('<bean:message bundle="error" key="error.account.invalidsearch.search" />');
				document.forms[0].searchFieldName.focus();
				return false; 
			}
		var txtBoxValue= document.getElementById("searchFieldValue").value; 
		    if(txtBoxValue=="")
			{
				document.forms[0].searchFieldValue.focus();
				return false; 
			}
			else
			{
				document.getElementById("circleId").value="";
				document.getElementById("listStatus").value="0";
				document.getElementById("startDt").value="";
				document.getElementById("endDt").value="";
				document.getElementById("userRole").value="";
				document.getElementById("methodName").value="searchAccount";
			    document.getElementById("page").value="";	    
				document.forms[0].submit();
			}
		}
		else
		{		
			date1=document.getElementById("startDt").value;
			date2=document.getElementById("endDt").value;
			if(document.getElementById("circleName").value==""){
	   			alert('<bean:message bundle="error" key="error.account.invalidsearch.circle" />');	  
				document.forms[0].circleName.focus();
				return false; 
			}
			else if(document.getElementById("userRole").value==""){
	   			alert('<bean:message bundle="error" key="error.account.invalidsearch.role" />');	  
				document.forms[0].userRole.focus();
				return false; 
			}
			else if(document.getElementById("listStatus").value==0){
				alert('<bean:message bundle="error" key="error.account.invalidsearch.status" />');	  
				document.forms[0].listStatus.focus();
				return false; 
			}else if(document.getElementById("searchFieldName").value=="0" ){
				alert('<bean:message bundle="error" key="error.account.invalidsearch.search" />');
				document.forms[0].searchFieldName.focus();
				return false; 
			}else if(!(document.getElementById("searchFieldName").value=="A") && isNull('document.forms[0]','searchFieldValue')){
				alert('<bean:message bundle="error" key="error.account.invalidsearch.textbox" />');	  
				document.forms[0].searchFieldValue.focus();
				return false; 
			}
			else if(date1 != "" && date2 == "")
			{
				alert("Please enter To Date");
				return false;
			}
			else if(date2 != "" && date1 == "")
			{
				alert("Please enter From Date");
				return false;
			}
			else if (!(isDate2Greater(date1, date2))){	  
				return false;
			}

			document.getElementById("methodName").value="searchAccount";
		    document.getElementById("page").value="";
			document.forms[0].submit();
		}
	}
	
	function checkDistAccountUpdateBoTree()
	{
		if (req.readyState==4 || req.readyState=="complete") 
 		{
			var xmldoc = req.responseXML.documentElement;
			if(xmldoc == null) 
				return;
			optionValues = xmldoc.getElementsByTagName("option");
			var msg = optionValues[0].getAttribute("text");
			return msg;
		}
	}//Added sugandha ends here
	
	
	function editAccount(accountId,groupName)
	{
	var flag ="false";
	if(groupName=="Distributor") 
	{
		var message= "SUCCESS";
		var accountlevel = document.getElementById("userRole").value;
		if(groupName=="Distributor")
		{
			var url="initCreateAccountItHelp.do?methodName=checkDistAccountUpdateBoTree&accountId="+accountId;
			if(window.XmlHttpRequest) 
				{
					req = new XmlHttpRequest();
				}
					else if(window.ActiveXObject) 
				{
					req=new ActiveXObject("Microsoft.XMLHTTP");
				}
					if(req==null) 
						{
							alert("Browser Doesn't Support AJAX");
							return;
						}
						req.onreadystatechange = function()
						{ 
								message = checkDistAccountUpdateBoTree();
								if(message != "SUCCESS")
									{	
										flag = "true";
									}
									else
									{
										flag = "false";
									}
						};
						req.open("POST",url,false);
						req.send();
					}
	}	
	if (flag == "false")
	{
		document.getElementById("accountId").value=accountId;
		document.getElementById("methodName").value="getAccountInfoEdit";
		document.forms[0].submit();
	}
	else
	{
		alert(message);
		return false;
	}
	
	
	
	
	
	}// function editAccount ends
	
	function viewAccount(accountId){
		document.getElementById("accountId").value=accountId;
		document.getElementById("methodName").value="getAccountInfoView"; 
		document.forms[0].submit();
	}// function viewAccount ends

	function setSearchControlDisabled(){
		if((document.getElementById("searchFieldName").value=="0") || (document.getElementById("searchFieldName").value=="A")){ 
			document.getElementById("searchFieldValue").disabled=true;
		}else{
			document.getElementById("searchFieldValue").disabled=false;
		}
	document.getElementById("searchFieldValue").value="";	
	}
// Anju 	
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
	function unlockAccount(accountId){
		var unlock=confirm( '<bean:message bundle="view" key="user.confirmunlockuser" />' );
	   if(unlock){ 
	    var form = document.forms[0];
       document.getElementById("methodName").value="UnlockUser";
	   document.getElementById("accountId").value=accountId;
       form.submit();
       }
     }
        
    function resetPassword(accountId){
		var unlock=confirm('<bean:message bundle="view" key="user.confirmresetpassword" />');
	   if(unlock){ 
	    var form = document.forms[0];
       document.getElementById("methodName").value="resetIPassword";
	   document.getElementById("accountId").value=accountId;
       form.submit();
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

	/* This function lets the user to dowanload the displayed rows. */
	function exportDataToExcel(){
		document.getElementById("methodName").value="downloadAccountList";
		document.forms[0].submit();
	}
	function getAllRoles(){
		var url = "initCreateAccountItHelp.do?methodName=getRole";
			if(window.XmlHttpRequest) 
			{
				req = new XmlHttpRequest();
			}else if(window.ActiveXObject) 
			{
				req=new ActiveXObject("Microsoft.XMLHTTP");
			}
			if(req==null) 
			{
				alert("Browser Doesnt Support AJAX");
				return;
			}
			req.onreadystatechange = getUserRole;
			req.open("POST",url,false);
			req.send();
	}
	
	function getUserRole() 
	{
	if (req.readyState==4 || req.readyState=="complete") 
	  	{
			var xmldoc = req.responseXML.documentElement;
			if(xmldoc == null) 
			{
				return;
			}
			
			// Get Conditions.
			optionValues = xmldoc.getElementsByTagName("option");			
			var selectObj2 = document.getElementById("userRole");							
			selectObj2.options.length = 0;
			selectObj2.options[selectObj2.options.length] = new Option("Select User Role","");
		
					
            		for(var i=0; i<optionValues.length; i++)
					{
					selectObj2.options[selectObj2.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));					
					}
			
  		}
	}
--></SCRIPT>
</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onkeypress="return checkKeyPressed();"
	onload=getAllRoles();>
	
<input type="hidden" name="strMessage" id="strMessage" />
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
			action="initCreateAccountItHelp" focus="circleName"
			name="DPCreateAccountITHelpFormBean"
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
					<html:hidden property="flagForCircleDisplay"
						styleId="flagForCircleDisplay" />
					<html:hidden property="methodName" styleId="methodName" />
					<html:hidden property="page" styleId="page" />
				</TR>

				<TR>
					<TD align="left"><STRONG><FONT color="#000000"><bean:message
						bundle="view" key="account.circleId" /></FONT></STRONG><FONT color="RED">*</FONT>
					:</TD>
					<TD align="left"><html:select property="circleId" tabindex="1"
						styleClass="w130" styleId="circleName">
						<html:option value="">
							<bean:message key="application.option.select" bundle="view" />
						</html:option>
						<html:options collection="circleList" labelProperty="circleName"
							property="circleId" />
					</html:select></TD>

					<TD align="left"><STRONG><FONT color="#000000">User Role</FONT></STRONG><FONT color="RED">*</FONT> :</TD>
					<td><html:select property="userRole" style="width:150px">
						<html:option value="">--Select User Role--</html:option>
					</html:select></td>
                      <%--  <!-- riju  -->	
                       <TD width="126" class="text pLeft15"><STRONG><FONT
								color="#000000"><B>Type</B></FONT><FONT color="RED">*</FONT> :</STRONG>
							</TD>
							<TD width="175"><FONT color="#003399"> <html:select
									property="disttype" tabindex="13" styleClass="w130">
									<html:option value="">
										<bean:message key="application.option.select" bundle="view" />
									</html:option>
									<html:option value="ssf">SSF</html:option>
									<html:option value="ssd">SSD</html:option>
																
									
									
								</html:select> </FONT></TD>
                     <!-- riju  -->	 --%>
					<TD align="left"><STRONG><FONT color="#000000"><bean:message
						bundle="view" key="account.status" /></FONT></STRONG> <FONT color="RED">*</FONT>
					:</TD>
					<TD align="left"><html:select property="listStatus"
						styleClass="w130" tabindex="2" styleId="listStatus">
						<html:option value="0">
							<bean:message key="application.option.select" bundle="view" />
						</html:option>
						<html:option value="A">
							<bean:message bundle="view" key="application.option.active" />
						</html:option>
						<html:option value="I">
							<bean:message bundle="view" key="application.option.suspend" />
						</html:option>
					</html:select></TD>


					<TD align="left"><STRONG><FONT color="#000000"><bean:message
						bundle="view" key="application.start_date" /></FONT></STRONG></TD>
					<TD align="left"><html:text property="startDt"
						styleId="startDt" styleClass="box" readonly="true" size="15"
						maxlength="10" /> <a onclick="javascript:showCal('CalendarUp11')">
					<img src="images/calendar.gif" width='16' height='16'
						alt="Click here to choose the date"
						title="Click here to choose the date" border='0'></a></TD>



					<TD align="left"><STRONG><FONT color="#000000"><bean:message
						bundle="view" key="application.end_date" /></FONT></STRONG></TD>
					<TD align="left"><html:text property="endDt" styleId="endDt"
						styleClass="box" readonly="true" size="15" maxlength="10" /> <a
						onclick="javascript:showCal('CalendarUp12')"> <img
						src="images/calendar.gif" width='16' height='16'
						alt="Click here to choose the date"
						title="Click here to choose the date" border='0'></a></TD>
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
						<html:option value="P">
							<bean:message bundle="view" key="account.parentAccount" />
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
			<logic:notEmpty name="DPCreateAccountITHelpFormBean"
					property="displayDetails">
					<TR align="center">
						<TD align="left" colspan="12"><INPUT class="submit"
							onclick="exportDataToExcel();" type="button" name="export"
							value="Export to Excel" tabindex="8"></TD>
					</TR>
			</logic:notEmpty>
			</TABLE>
			
			<DIV style="height: 300px; overflow: auto;" width="100%">
			<DIV>
			<TABLE align="center" class="mLeft5" width="100%">
				<logic:notEmpty name="DPCreateAccountITHelpFormBean"
					property="displayDetails">
					
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
							bundle="view" key="account.simNumber" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.parentAccount" /></SPAN></TD>
							
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.parentAccountStatus" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.accountAddress" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.email" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.category" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="user.groupid" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.circleId" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"> <bean:message
							bundle="view" key="account.status" /></SPAN></TD>

						
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.createddate" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="account.createdby" /></SPAN></TD>
							
							
							
														
						<TD align="left" bgcolor="#CD0504" nowrap><SPAN
							class="white10heading mLeft5 mTop5"><logic:equal
							name="DPCreateAccountITHelpFormBean" property="editStatus"
							value="Y">
							<bean:message bundle="view" key="application.edit" />&nbsp;</logic:equal>
							<logic:equal
							name="DPCreateAccountITHelpFormBean" property="unlockStatus"
							value="Y">&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;<bean:message
								bundle="view" key="application.unlock" />
						</logic:equal><logic:equal name="DPCreateAccountITHelpFormBean"
							property="resetStatus" value="Y">&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;<bean:message
								bundle="view" key="application.reset" />
						</logic:equal></SPAN></TD>
					</TR>
					</thead>
					<tbody>
					<logic:iterate id="account" property="displayDetails"
						name="DPCreateAccountITHelpFormBean" indexId="i">
						<TR bgcolor="#FCE8E6">
							<TD class="text" align="center"><bean:write name="account"
								property="rowNum" /></TD>
							<TD class="text" align="center"><bean:write name="account"
								property="accountCode" /></TD>
							<TD class="text" align="center"><bean:write name="account"
								property="accountName" /></TD>
							<TD class="text" align="center"><bean:write name="account"
								property="mobileNumber" /></TD>
							<TD class="text" align="center"><bean:write name="account"
								property="simNumber" /></TD>
							<TD class="text" align="center"><bean:write name="account"
								property="parentAccount" /></TD>
								
 							<TD class="text" align="center"><bean:write name="account"
								property="parentAccountStatus" /></TD>
							<TD class="text" align="center"><bean:write name="account"
								property="accountAddress" /></TD>
							<TD class="text" align="center"><bean:write name="account"
								property="email" /></TD>
							<TD class="text" align="center"><logic:match name="account"
								property="category" value="G">
								<bean:message bundle="view" key="account.category.option_gold" />
							</logic:match> <logic:match name="account" property="category" value="S">
								<bean:message bundle="view" key="account.category.option_silver" />
							</logic:match> <logic:match name="account" property="category" value="P">
								<bean:message bundle="view"
									key="account.category.option_platinum" />
							</logic:match></TD>
							<TD class="text" align="center"><bean:write name="account"
								property="groupName" /></TD>
							<TD class="text" align="center"><bean:write name="account"
								property="circleName" /></TD>
							<TD class="text" align="center"><logic:match name="account"
								property="status" value="A">
								<bean:message bundle="view" key="application.option.active" />
							</logic:match> <logic:notMatch name="account" property="status" value="A">
								<bean:message bundle="view" key="application.option.suspend" />
							</logic:notMatch></TD>
							

							<TD class="text" align="center"><bean:write name="account"
								property="createdDt" formatKey="formatDate" bundle="view" /></TD>
							<TD class="text" align="center"><bean:write name="account"
								property="createdByName" /></TD>
							<TD class="text" align="left" nowrap><logic:equal
								name="DPCreateAccountITHelpFormBean" property="editStatus"
								value="Y">
								<INPUT type="button" class="submit" value="Edit"
									onclick="return editAccount('${account.accountId}','${account.groupName}')" />&nbsp;
								</logic:equal> &nbsp;<logic:equal
								name="DPCreateAccountITHelpFormBean" property="unlockStatus"
								value="Y">
								<logic:match name="account" property="locked" value="true">|
								<INPUT type="button" class="submit" value='Unlock'
										onclick="return unlockAccount('${account.accountId}')" />&nbsp;
								</logic:match>
								<logic:match name="account" property="locked" value="false">|
								<INPUT type="button" class="submit" value='Unlock' disabled />
								</logic:match>
							</logic:equal> <logic:equal name="DPCreateAccountITHelpFormBean"
								property="resetStatus" value="Y"> | 
								<INPUT type="button" class="submit" value='Reset'
									onclick="return resetPassword('${account.accountId}')" />
							</logic:equal></TD>
						</TR>
					</logic:iterate>
					</tbody>
					<html:hidden styleId="methodName" property="methodName"
						value="getAccountInfo" />
					<html:hidden styleId="accountId" property="accountId" />
					
				</logic:notEmpty>
				
			</TABLE>
			</DIV>
			</DIV>
			<html:hidden property="circleId" />
			<html:hidden property="accessLevelId" styleId="accessLevelId" />
		</html:form></TD>
	</TR>
	<TR align="center" bgcolor="4477bb" valign="top">
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