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
<LINK rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/theme/chromestyle2.css" />
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/chrome.js"></SCRIPT>
</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">
	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include
			page="../common/dpheader.jsp" flush="" /></TD>
	</TR>
	<TR valign="top">
		<TD width="167" align="left" bgcolor="b4d2e7" valign="top"
			height="100%"><jsp:include page="../common/leftHeader.jsp" flush="" /></TD>
		<TD valign="top" width="100%" height="100%">
		<TABLE width="700" border="0" cellspacing="0" cellpadding="0"
			class="text" align="left">
			<TR>
				<TD width="20" align="left"><IMG src="<%=request.getContextPath()%>/images/spacer.gif"
					width="15" height="20" alt=""></TD>
				<TD width="680" valign="top">
				<TABLE width="680" border="0" cellpadding="5" cellspacing="0"
					class="text">
					<TR>
						<TD colspan="4" class="text"><BR>
						<IMG src="<%=request.getContextPath()%>/images/viewEditSystemConfig.gif" width="505" height="22"
							alt=""></TD>
					</TR>
					<TR>
						<TD colspan="4">
						<TABLE width="725" align="center" class="mLeft5">
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
									bundle="view" key="application.edit" /></SPAN></TD>
							</TR>
							<TR align="center">
								<TD align="center" class="darkBlueBg height19"><SPAN
									class="white10heading mLeft5 mTop5"><bean:message
									bundle="view" key="sysconfig.minimum" /></SPAN></TD>
								<TD align="center" class="darkBlueBg height19"><SPAN
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
										class="mLeft5 mTop5 mBot5 black10"> <c:if
										test='${SysBean.channelName eq "SMSC"}'>
										<bean:message bundle="view" key="sysconfig.channel_smsc" />
									</c:if> <c:if test='${SysBean.channelName eq "WEB"}'>
										<bean:message bundle="view" key="sysconfig.channel_web" />
									</c:if> <c:if test='${SysBean.channelName eq "SELFCARE"}'>
										<bean:message bundle="view" key="sysconfig.channel_selfcare" />
									</c:if> <c:if test='${SysBean.channelName eq "USSD"}'>
										<bean:message bundle="view" key="sysconfig.channel_ussd" />
									</c:if> </SPAN></TD>
									<TD class=" height19" align="left"><SPAN
										class="mLeft5 mTop5 mBot5 black10"> <c:if
										test='${SysBean.transactionType eq "RECHARGE"}'>
										<bean:message bundle="view"
											key="sysconfig.transaction_type.recharge" />
									</c:if> <c:if test='${SysBean.transactionType eq "VAS"}'>
										<bean:message bundle="view"
											key="sysconfig.transaction_type.vas" />
									</c:if> <c:if test='${SysBean.transactionType eq "POSTPAID"}'>
										<bean:message bundle="view"
											key="sysconfig.transaction_type.postpaid" />
									</c:if><c:if test='${SysBean.transactionType eq "ACCOUNT"}'>
										<bean:message bundle="view"
											key="sysconfig.transaction_type.account" />
									</c:if> </SPAN></TD>
									<TD class=" height19" align="right"><SPAN
										class="mLeft5 mTop5 mBot5 black10"><bean:write
										name="SysBean" format="#0.00" property="minAmount" /></SPAN></TD>
									<TD class=" height19" align="right"><SPAN
										class="mLeft5 mTop5 mBot5 black10"><bean:write
										name="SysBean" format="#0.00" property="maxAmount" /></SPAN></TD>
									<TD class="text"><html:link
										action="/SysConfigAction.do?methodName=getSysConfigData"
										paramName="SysBean" paramProperty="sysConfigId"
										paramId="sysConfigId" styleClass="orange10">
										<bean:message bundle="view" key="application.edit" />
									</html:link></TD>
								</TR>
								
							</logic:iterate>
							<TR>
								<TD colspan="4"><FONT color="RED"><STRONG>
								<html:errors bundle="view" property="message.sysconfig" /> </STRONG></FONT></TD>
								<TD colspan="4"><FONT color="RED"><STRONG>
								<html:errors bundle="error" /> </STRONG></FONT></TD>
							</TR>
						</TABLE>
						</TD>
					</TR>
				</TABLE>
				</TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
	<TR align="center" bgcolor="4477bb" valign="top">
		<TD colspan="2" height="17" align="center"><jsp:include
			page="../common/footer.jsp" flush="" /></TD>
	</TR>
</TABLE>

</BODY>
</html:html>
