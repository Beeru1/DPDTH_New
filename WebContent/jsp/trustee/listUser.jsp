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
<LINK href="<%=request.getContextPath()%>/theme/Master.css" rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/theme/chromestyle2.css" />
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/chrome.js"></SCRIPT>
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/scripts/Cal.js" type="text/javascript"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>

<SCRIPT type="text/javascript">


function submitData(nextPage,startDate,endDate,circleId,status)
{

 document.forms[0].action="UserAction.do?methodName=getUserList&page="
										+ nextPage   + "&startDate=" 
										+ startDate + "&endDate=" + endDate
										+ "&circleId=" + circleId
										+ "&status=" + status;
document.getElementById("page").value="";	
									
document.forms[0].method="post";
document.forms[0].submit();
}

	function editDetails(loginId){
	    var form = document.forms[0];
       document.getElementById("methodName").value="getEditUser";
	   document.getElementById("loginId").value=loginId;
       form.submit();
	} // function editDetails ends

	function viewDetails(loginId){
	    var form = document.forms[0];
       document.getElementById("methodName").value="getViewUser";
	   document.getElementById("loginId").value=loginId;
	
       form.submit();
	} // function viewDetails ends

	function unlockUser(loginId){
	  var unlock=confirm('<bean:message bundle="view" key="user.confirmunlockuser" />');
	 if(unlock){ 
	       var form = document.forms[0];
	       document.getElementById("methodName").value="UnlockUser";
		   document.getElementById("loginId").value=loginId;
	       document.forms[0].method="post";
	       document.forms[0].submit();
        }
     } // function unlockUser ends
      
     function resetPassword(loginId){
		var unlock=confirm('<bean:message bundle="view" key="user.confirmresetpassword" />');
	   if(unlock){ 
	    var form = document.forms[0];
       document.getElementById("methodName").value="resetIPassword";
	   document.getElementById("loginId").value=loginId;
       form.submit();
       }
     } // function resetPassword ends

	function validate(){
		 date1=document.getElementById("startDt").value;
		 date2=document.getElementById("endDt").value; 
	  
		 if(document.getElementById("selectedStatus").value==0){
   		       alert('<bean:message bundle="error" key="errors.user.status_required" />');	  
   		       document.forms[0].selectedStatus.focus();
	           return false; 
	  	 }  	 
	  	 else if (!(isDate2Greater(date1, date2))){
			  return false;
		  }
	   
	   document.getElementById("page").value="";
	   document.getElementById("methodName").value="getUserList";
		return true;
	} // Validate Form Ends


function getLoginId(loginId){
	   document.getElementById("loginId").value=loginId;
       document.getElementById("methodName").value="getUser"; 
	}
	
/* This function lets the user to dowanload the displayed rows. */
function exportDataToExcel(){
	document.getElementById("methodName").value="downloadUserList";
	document.forms[0].submit();
}
	
// set focus on status control  
function doLoad()
{
document.forms[0].selectedStatus.focus();
}

/* check if enter key is pressed */
function checkKeyPressed()
{

	if(window.event.keyCode=="13")
	{
		if(validate())
		{
			document.forms[0].submit();
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
			page="../common/dpheader.jsp" flush="" /></TD>
	</TR>
	<TR valign="top">
		<TD width="167" align="left" bgcolor="b4d2e7" valign="top"
			height="100%"><jsp:include page="../common/leftHeader.jsp" flush="" /></TD>
		<TD valign="top" width="100%" height="100%"><html:form
			action="UserAction" method="post">
			<TABLE border="0" cellspacing="0" cellpadding="5" class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/viewEdit.gif" width="505" height="22" alt=""><BR>
					</TD>
				</TR>
				<TR>
					<TD colspan="4"><FONT color="#ff0000"><html:errors
						bundle="error" /> </FONT> <FONT color="#ff0000"><html:errors
						bundle="view" /> </FONT></TD>
					<html:hidden property="flagForCircleDisplay"
						styleId="flagForCircleDisplay" />
					<html:hidden property="loginId" styleId="loginId" />
					<html:hidden property="methodName" styleId="methodName" />
					<html:hidden property="page" styleId="page" />
				</TR>

				<TR>
					<TD><STRONG><FONT color="#000000"><bean:message
						key="user.circleid" bundle="view" /> </FONT></STRONG><FONT
						color="#3C3C3C"><STRONG>:</STRONG> <html:select property="selectedCircleId"
						name="UserFormBean" styleClass="w130" tabindex="1"
						styleId="circleName">
						<html:option value="0">
							<bean:message key="application.option.select" bundle="view" />
						</html:option>
						<html:options collection="circleList" labelProperty="circleName"
							property="circleId" />
					</html:select></FONT></TD>

					<TD><STRONG><FONT color="#000000"><bean:message
						bundle="view" key="user.status" /></FONT></STRONG><FONT color="RED">*</FONT><STRONG>:</STRONG> <FONT
						color="#003399"> <html:select property="selectedStatus"
						styleClass="w130" style="width:100" tabindex="2" styleId="selectedStatus" >
						<html:option value="0">
							<bean:message key="application.option.select" bundle="view" />
						</html:option>
						<html:option value="A">
							<bean:message bundle="view" key="application.option.active" />
						</html:option>
						<html:option value="D">
							<bean:message bundle="view" key="application.option.inactive" />
						</html:option>
					</html:select> </FONT></TD>

					<TD class="text pLeft15"><STRONG><FONT color="#000000"><bean:message
						bundle="view" key="application.start_date" /></FONT></STRONG> <html:text
						property="startDt" styleId="startDt" styleClass="box"
						name="UserFormBean" readonly="true" size="10" maxlength="10" /><script language="JavaScript">
new tcal ({
// form name
'formname': 'UserFormBean',
// input name
'controlname': 'startDt'
});

</script></TD>


					<TD><STRONG><FONT color="#000000"><bean:message
						bundle="view" key="application.end_date" /></FONT></STRONG> <html:text
						property="endDt" styleId="endDt" styleClass="box"
						name="UserFormBean" readonly="true" size="10" maxlength="10" /><script language="JavaScript">
new tcal ({
// form name
'formname': 'UserFormBean',
// input name
'controlname': 'endDt'
});

</script></TD>

					<TD><INPUT class="submit" onclick="return validate();"
						type="submit" tabindex="5" name="Submit" value="Search"></TD>
				</TR>
			</TABLE>


			<TABLE width="830" align="left" class="mLeft5">
				<logic:notEmpty name="UserFormBean" property="userList">
					<TR align="center">
						<TD align="left" colspan="12"><INPUT class="submit"
							onclick="exportDataToExcel();" type="button" tabindex="6"
							name="export" value="Export to Excel"></TD>
					</TR>

					<TR align="center" bgcolor="#104e8b">
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							key="application.sno" bundle="view" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							key="user.username" bundle="view" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							key="user.groupid" bundle="view" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							key="user.circleid" bundle="view" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							key="user.contactno" bundle="view" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							key="user.emailid" bundle="view" /></SPAN></TD>
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							key="user.status" bundle="view" /></SPAN></TD>
<!-- 						
						<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="user.createddate" /></SPAN></TD>
	
							<TD align="center" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="user.createdby" /></SPAN></TD>
-->						
						<TD align="left" bgcolor="#cd0504"><SPAN
							class="white10heading mLeft5 mTop5">
							<logic:equal name="UserFormBean" property="editStatus" value="Y">
							<bean:message key="application.edit" bundle="view" />&nbsp;&nbsp;|&nbsp;&nbsp;</logic:equal><bean:message
							bundle="view" key="application.view" /><logic:equal name="UserFormBean"
								property="unlockStatus" value="Y">&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;<bean:message
							bundle="view" key="application.unlock" /></logic:equal><logic:equal name="UserFormBean"
								property="resetStatus" value="Y">&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;<bean:message
							bundle="view" key="application.reset" /></logic:equal></SPAN></TD>
					</TR>
					
					<logic:iterate id="user" name="UserFormBean" property="userList"
						indexId="i">
						<TR align="center" bgcolor="#fce8e6">
							<TD class="height19" align="center"><SPAN
								class="mLeft5 mTop5 mBot5 black10"> <bean:write
								name="user" property="rowNum" /> </SPAN></TD>
							<TD class="height19" align="left"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="user" property="loginName" /></SPAN></TD>
							<TD class="height19" align="left"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="user" property="groupName" /></SPAN></TD>
							<TD class="height19" align="center"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="user" property="circleName" /></SPAN></TD>
							<TD class="height19" align="center"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="user" property="contactNumber" /></SPAN></TD>
							<TD class="height19" align="left"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="user" property="emailId" /></SPAN></TD>
							<TD class="height19"><SPAN
								class="mLeft5 mTop5 mBot5 black10"> <c:if
								test='${user.status eq "A"}'>
								<bean:message bundle="view" key="application.option.active" />
							</c:if> <c:if test='${user.status eq "D"}'>
								<bean:message bundle="view" key="application.option.inactive" />
							</c:if> </SPAN></TD>
							
<!-- 							
							<TD class="height19" align="center"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="user" property="createdDt" formatKey="formatDate" /></SPAN></TD>

							<TD class="height19" align="left"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="user" property="createdByName" /></SPAN></TD>
-->								
							<html:hidden property="createdBy" styleId="createdBy" name="user" />
							<html:hidden property="loginId" styleId="loginId" />
							<html:hidden property="methodName" styleId="methodName" />

							<TD class=" height19" nowrap align="center"><logic:equal
								name="UserFormBean" property="editStatus" value="Y">
								<INPUT type="button" class="submit" value='Edit'
									onclick="return editDetails('${user.loginId}')" /> |
								</logic:equal> <INPUT type="button" class="submit" value='View'
								onclick="return viewDetails('${user.loginId}')" /> <logic:equal
								name="UserFormBean" property="unlockStatus" value="Y">
								<logic:match name="user"
								property="locked" value="true">|
								<INPUT type="button" class="submit" value='Unlock' 
								onclick="return unlockUser('${user.loginId}')" />
								</logic:match>
								<logic:match name="user"
								property="locked" value="false">|
								<INPUT type="button" class="submit" value='Unlock' disabled onclick="return unlockUser('${user.loginId}')"  />
								</logic:match>
								</logic:equal>
								<logic:equal name="UserFormBean" property="resetStatus" value="Y"> | 
								<INPUT type="button" class="submit" value='Reset'
								onclick="return resetPassword('${user.loginId}')" /></logic:equal></TD>
								
						</TR>
						
					</logic:iterate>
					<TR align="center">
						<TD align="center" bgcolor="#daeefc" colspan="12"><FONT
							face="verdana" size="2"> <c:if test="${PAGES!=''}">
							<c:if test="${PAGES>1}">

								    	Page:
							    <c:if test="${PRE>=1}">
									<A href="#"
										onclick="submitData('<c:out value='${PRE}' />','<c:out value='${startDt}' />','<c:out value='${endDt}' />','<c:out value='${circleId}' />','<c:out value='${status}' />');"><
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
												onclick="submitData('<c:out value="${item}"/>','<c:out value='${startDt}' />','<c:out value='${endDt}' />','<c:out value='${circleId}' />','<c:out value='${status}' />');"><c:out
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
												onclick="submitData('<c:out value="${item}"/>','<c:out value='${startDt}' />','<c:out value='${endDt}' />','<c:out value='${circleId}' />','<c:out value='${status}' />');"><c:out
												value="${item}" /></A>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:if>	
							
								<c:if test="${NEXT<=PAGES}">
									<A href="#"
										onclick="submitData('<c:out value='${NEXT}' />','<c:out value='${startDt}' />','<c:out value='${endDt}' />','<c:out value='${circleId}' />','<c:out value='${status}' />');">Next></A>
								</c:if>
								
							</c:if>
						</c:if></TD>
					</TR>
				</logic:notEmpty>
					<html:hidden styleId="editStatus" property="editStatus" name="UserFormBean" />
			</TABLE>


		</html:form></TD>
	</TR>
	<TR align="center" bgcolor="4477bb" valign="top">
		<TD colspan="2" height="17" align="center"><jsp:include
			page="../common/footer.jsp" /></TD>
	</TR>
</TABLE>
<SCRIPT type="text/javascript">
{
	if(document.getElementById("flagForCircleDisplay").value=="true")
	{
		document.getElementById("circleName").disabled = true;
	}
}
</SCRIPT>
</BODY>
</html:html>



