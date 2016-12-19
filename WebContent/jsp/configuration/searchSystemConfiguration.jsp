<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html:html>
<HEAD>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<LINK href="<%=request.getContextPath()%>/theme/text.css" rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/theme/chromestyle2.css" />
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/chrome.js"></SCRIPT>
<SCRIPT language="javascript" type="text/javascript">

	function redirect(){
	document.getElementById("methodName").value="searchSysConfig";
	
	
	}

	function editDetails(sysConfigId){
		   var form = document.forms[0];
		    document.getElementById("sysConfigId").value=sysConfigId;
	       document.getElementById("methodName").value="getEditSysConfig";
		  
		   
	       form.submit();
	} // function editDeatils ends
	
	function viewDetails(sysConfigId){
		   var form = document.forms[0];
		    document.getElementById("sysConfigId").value=sysConfigId;
	       document.getElementById("methodName").value="getViewSysConfig";
		  
	       form.submit();
	} // function viewDeatils ends


	function checkKeyPressed(){
		if(window.event.keyCode=="13"){
			document.forms[0].submit();
		}
	}
	function checkIsCircleAdmin(){
		var  userType = document.getElementById("isCircleAdmin").value;
		if(userType == "Y"){
			document.getElementById("selectedCircleId").disabled = true;
			document.getElementById("selectedCircleId").value = document.getElementById("circleId").value;
			//	document.forms[0].submit();
		}
	}
</SCRIPT>
</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onkeypress="return checkKeyPressed();"
	onload="checkIsCircleAdmin();">

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
			action="SysConfigAction" focus="circleId">
			<html:hidden property="isCircleAdmin" styleId="isCircleAdmin"
				name="SysConfigFormBean" />
			<html:hidden property="disabledCircle" styleId="disabledCircle"
				name="SysConfigFormBean" />
			<html:hidden property="circleId" styleId="circleId"
				name="SysConfigFormBean" />
			<TABLE width="545" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/searchSysConfig.gif" width="505" height="22"
						alt=""></TD>
				</TR>
				<TR>
					<TD width="155" class="text pLeft15" align="center"><STRONG><bean:message
						bundle="view" key="sysconfig.circle_select" /></STRONG>&nbsp;<FONT
						color="RED">*</FONT></TD>
					<TD><FONT color="#003399"> <html:select
						property="selectedCircleId" styleClass="w130" tabindex="1">
						
						<html:option value="0">
							<bean:message key="application.option.select_all" bundle="view" />
						</html:option>
						
						<html:options property="circleId" labelProperty="circleName"
							collection="circleList" />
					</html:select>&nbsp;&nbsp;&nbsp;<INPUT class="submit" type="submit" tabindex="2"
						name="Submit" value="Search" onclick="redirect()"> </FONT></TD>
				</TR>
				
				<TR>
					<TD colspan="4" align="center">
					<FONT color="#ff0000" size="-2">
						<html:errors bundle="error" />
						<html:errors bundle="view" property="message.sysconfig.update_success" />
					</FONT>
					<FONT color="#ff0000" size="-2">
						<html:errors bundle="error" property="errors.sys.norecord_found"/>
					</FONT>
					</TD>
					<html:hidden property="methodName" styleId="methodName"  />
					<html:hidden styleId="sysConfigId" property="sysConfigId" />
				</TR>
			</TABLE>
			
			
		
			<TABLE width="725" align="left" class="mLeft5">
			<logic:notEmpty name="SysConfigFormBean" property="sysConfigList">
			
							<TR align="center" bgcolor="#104e8b">
								<TD rowspan="2" align="center" bgcolor="#cd0504" nowrap><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="application.sno" /></SPAN></TD>
								<TD rowspan="2" align="center" bgcolor="#cd0504" nowrap><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="sysconfig.circle_name" /></SPAN></TD>
								<TD rowspan="2" align="center" bgcolor="#cd0504" nowrap><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="sysconfig.channel_name" /></SPAN></TD>
								<TD rowspan="2" align="center" bgcolor="#cd0504" nowrap><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="sysconfig.transaction_type" /></SPAN></TD>
								<TD align="center" bgcolor="#cd0504" nowrap colspan="2"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="sysconfig.amount" />&nbsp;(<bean:message
									bundle="view" key="application.currency" />)</SPAN></TD>
								<TD rowspan="2" align="center" bgcolor="#cd0504"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="application.edit" />&nbsp;|&nbsp;<bean:message
							bundle="view" key="application.view" /></SPAN></TD>
							</TR>
							<TR align="center">
								<TD align="center" bgcolor="#cd0504"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="sysconfig.minimum" /></SPAN></TD>
								<TD align="center" bgcolor="#cd0504"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="sysconfig.maximum" /></SPAN></TD>
							</TR>
							
							<logic:iterate id="SysBean" name="SysConfigFormBean"
								property="sysConfigList" indexId="i">
								<TR align="center"
									bgcolor="#fce8e6">
									<TD class=" height19" align="center"><SPAN
										class="mLeft5 mTop5 mBot5 black10"><c:out
										value="${i+1}"></c:out></SPAN></TD>
									<TD class=" height19" align="left"><SPAN
										class="mLeft5 mTop5 mBot5 black10"><bean:write
										name="SysBean" property="circleName" /></SPAN></TD>
									<TD class=" height19" align="left"><SPAN
										class="mLeft5 mTop5 mBot5 black10"> <bean:write
						name="SysBean" property="channelName" /> </SPAN></TD>
									<TD class=" height19" align="left"><SPAN
										class="mLeft5 mTop5 mBot5 black10"> <bean:write
						name="SysBean" property="transactionType" /> </SPAN></TD>
									<TD class=" height19" align="right"><SPAN
										class="mLeft5 mTop5 mBot5 black10"><bean:write
										name="SysBean" format="#0.00" property="minAmount" /></SPAN></TD>
									<TD class=" height19" align="right"><SPAN
										class="mLeft5 mTop5 mBot5 black10"><bean:write
										name="SysBean" format="#0.00" property="maxAmount" /></SPAN></TD>
									<TD>								
									<logic:equal name="SysConfigFormBean" property="editStatus" value="Y">
									<INPUT type="button" class="submit" value='Edit'
										onclick="return editDetails('${SysBean.sysConfigId}')" />&nbsp;|
							 &nbsp;</logic:equal><INPUT type="button" class="submit" value='View'
									onclick="return viewDetails('${SysBean.sysConfigId}')" /></TD>										
									
									</TR>
								
							</logic:iterate>
							<TR>
								<TD colspan="4"><FONT color="RED"><STRONG>
								<html:errors bundle="view" property="message.sysconfig" /> </STRONG></FONT></TD>
								<TD colspan="4"><FONT color="RED"><STRONG>
								<html:errors bundle="error" /> </STRONG></FONT></TD>
							</TR>
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
