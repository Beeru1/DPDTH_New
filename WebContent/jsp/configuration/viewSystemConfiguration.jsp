<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>
<HEAD>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<LINK href="<%=request.getContextPath()%>/theme/text.css" rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/theme/chromestyle2.css" />
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/chrome.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/JScriptLib.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>

<SCRIPT language="javascript" type="text/javascript">
 
function goBack(){
		document.getElementById("methodName").value="searchSysConfig";
	}

</SCRIPT>
</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" >

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
			action="/SysConfigAction" name="SysConfigFormBean"
			type="com.ibm.virtualization.recharge.beans.SysConfigFormBean">
			<html:hidden property="sysConfigId" styleId="sysConfigId" name="SysConfigFormBean" />
			<html:hidden property="methodName" styleId="methodName" name="SysConfigFormBean" />
			

			<TABLE  border="0" cellpadding="7" cellspacing="0"
				class="text">
				
				<TR>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/viewSysConfigDetails.gif" width="505" height="22"
						alt=""></TD>
				</TR>
				<TR>
					<TD colspan="4"><FONT color="#ff0000" size="2"><B><html:errors
						bundle="error" /> </B></FONT></TD>
				</TR>
				<TR>
								
					<TD class="text" ><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="sysconfig.circle_name" /></FONT> :</STRONG></TD>
						<TD width="250"><FONT color=#3C3C3C> <bean:write
						name="SysConfigFormBean" property="circleName" /> </FONT></TD>
				</TR>
				
				
				<TR>
					<TD class="text"><STRONG><bean:message
						bundle="view" key="sysconfig.channel_name" />
					:</STRONG></TD>
					
						<TD width="250"><FONT color=#3C3C3C> <bean:write
						name="SysConfigFormBean" property="channelName" /> </FONT></TD>
									
				</TR>
				<TR>
											
					<TD class="text" ><STRONG><bean:message
						bundle="view" key="sysconfig.transaction_type" /> :</STRONG></TD>
					<TD width="250"><FONT color=#3C3C3C> <bean:write
						name="SysConfigFormBean" property="transactionType" /> </FONT></TD>
			
				</TR>
				<TR>
					<TD class="text" ><STRONG><bean:message
						bundle="view" key="sysconfig.min_amount" />
					:</STRONG></TD>
						<TD width="250"><FONT color=#3C3C3C> <bean:write
						name="SysConfigFormBean" property="minAmount" /> </FONT></TD>
				</TR>
				<TR>
					<TD class="text" ><STRONG><bean:message
						bundle="view" key="sysconfig.max_amount" />
					:</STRONG></TD>
					<TD width="250"><FONT color=#3C3C3C> <bean:write
						name="SysConfigFormBean" property="maxAmount" /> </FONT></TD>
				</TR>
				<TR>
				
					<TD align="right">
					<!-- <input type="button" onclick="history.back()"  value="Back" class="submit" > -->
					<INPUT class="submit" type="submit" tabindex="13"
								name="BACK" value="Back" onclick="goBack()">
					</td>
					<td width="250">			
					<html:hidden property="selectedCircleId" name="SysConfigFormBean" />
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
