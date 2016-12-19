<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>
<HEAD>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<LINK href="<%=request.getContextPath()%>/theme/text.css" rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/theme/chromestyle2.css" />
<LINK href="<%=request.getContextPath()%>/theme/CalendarControl.css" rel="stylesheet" type="text/css">
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/scripts/Cal.js"
	type="text/javascript"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/chrome.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/JScriptLib.js"></SCRIPT>

<SCRIPT language="Javascript" type="text/javascript">
	
	function goBack(){
		document.getElementById("methodName").value="showTopupConfig";
	}
	
</SCRIPT>

</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >

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
			name="topupSlabAction" action="/TopupSlabAction"
			type="com.ibm.virtualization.recharge.beans.TopupSlabBean">
			<html:hidden property="methodName" styleId="methodName" />
			<html:hidden property="topupConfigId" name="topupSlabBean" />
			<html:hidden property="circleId" name="topupSlabBean" />
			<html:hidden property="page" styleId="page" name="topupSlabBean" />

			<TABLE width="545" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/viewSlabConfigDetails.gif" width="505" height="22"
						alt=""></TD>
				</TR>
				<TR>

					<TD><FONT color="#ff0000" size="-2"><html:errors
						bundle="error" /></FONT></TD>
				</TR>
				<TR>
				
					<TD width="126" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="topup.circle_name" /></FONT> :</STRONG>
					</TD>
					<TD width="155"><FONT color="#3C3C3C">
						<bean:write name="topupSlabBean" property="circleName" /> 
					</FONT>
					</TD>


					<TD width="120" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="topup.transaction_type" /></FONT> :</STRONG>
					</TD>
					<TD width="163"><FONT color="#3C3C3C">
					 
						<logic:equal value="RECHARGE" name="topupSlabBean" property="transactionType" >
						<bean:message bundle="view" key="sysconfig.transaction_type.recharge" />
						</logic:equal>
					
						<logic:equal value="VAS" name="topupSlabBean" property="transactionType" >
						<bean:message bundle="view" key="sysconfig.transaction_type.vas" />
						</logic:equal>
					
						<logic:equal value="POSTPAID_ABTS" name="topupSlabBean" property="transactionType" >
						<bean:message bundle="view" key="sysconfig.transaction_type.postpaid_abts" />
						</logic:equal>
						
						<logic:equal value="POSTPAID_MOBILE" name="topupSlabBean" property="transactionType" >
						<bean:message bundle="view" key="sysconfig.transaction_type.postpaid" />
						</logic:equal>
						
						<logic:equal value="POSTPAID_DTH" name="topupSlabBean" property="transactionType" >
						<bean:message bundle="view" key="sysconfig.transaction_type.postpaid_dth" />
						</logic:equal>
						
						<logic:equal value="ACCOUNT" name="topupSlabBean" property="transactionType" >
						<bean:message bundle="view" key="sysconfig.transaction_type.account" />
						</logic:equal>
					
						</FONT>
					</TD>
						
				</TR>
				<TR>
					<TD width="126" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="topup.start_amount" /></FONT> :</STRONG>
					</TD>
					<TD width="171"><FONT color="#3C3C3C"> 
						<bean:write name="topupSlabBean" property="startAmount"/>
						</FONT>
					</TD>
					
					
					<TD width="120" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="topup.till_amount" /></FONT> :</STRONG></TD>
					<TD width="163"><FONT color="#3C3C3C"> 
						<bean:write name="topupSlabBean" property="tillAmount"/>
						</FONT>
					</TD>
				</TR>
				<TR>
					<TD width="126" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="topup.esp_commission" /></FONT> :</STRONG>
					</TD>
					<TD width="171"><FONT color="#3C3C3C"> 
						<bean:write name="topupSlabBean" property="espCommission"/>
						</FONT>
					</TD>
						
					<TD width="120" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="topup.psp_commission" /></FONT> :</STRONG></TD>
					<TD width="163"><FONT color="#3C3C3C"> 
						<bean:write name="topupSlabBean" property="pspCommission"/>
						</FONT>
					</TD>
				</TR>
				<TR>
					<TD width="126" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="topup.service_tax" /></FONT> :</STRONG>
					</TD>
					<TD width="171"><FONT color="#3C3C3C"> 
						<bean:write name="topupSlabBean" property="serviceTax"/>
						</FONT>
					</TD>
					
					<TD width="120" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="topup.processing_fee" /></FONT> :</STRONG>
					</TD>
					<TD width="163"><FONT color="#3C3C3C"> 
						<bean:write name="topupSlabBean" property="processingFee"/>
						</FONT>
					</TD>
				</TR>
				<TR>
					<TD width="126" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="topup.processing_code" /></FONT> :</STRONG>
					</TD>
					<TD width="171"><FONT color="#3C3C3C">
						<bean:write name="topupSlabBean" property="processingCode"/>
						</FONT>
					</TD>
					
					<TD width="120" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="topup.incard_group" /></FONT> :</STRONG>
					</TD>
					<TD width="163"><FONT color="#3C3C3C"> 
						<bean:write name="topupSlabBean" property="inCardGroup"/>
						</FONT>
					</TD>
				</TR>
				<TR>
					<TD width="126" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="topup.validity" /></FONT> :</STRONG></TD>
					<TD width="171"><FONT color="#3C3C3C">
						<bean:write name="topupSlabBean" property="validity"/>
						</FONT>
					</TD>
					
					<TD width="120" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="topup.status" /></FONT> :</STRONG>
					</TD>
					<TD width="163">
						<FONT color="#3C3C3C"> 
						<logic:equal value="A" name="topupSlabBean" property="status">
							<bean:message key="application.option.active" bundle="view" />
						</logic:equal>
						<logic:equal value="D" name="topupSlabBean" property="status">
							<bean:message key="application.option.inactive" bundle="view" />
						</logic:equal>
						</FONT>
					</TD>
				</TR>
				<TR>
					<TD class="text pLeft15" nowrap><FONT color="#000000">&nbsp;</FONT></TD>
					<TD colspan="2">
					<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
						<TR align="center" valign="top">
							
							<TD><INPUT class="submit" type="submit" tabindex="13"
								name="BACK" value="Back" onclick="goBack()"></TD>
							
						</TR>
					</TABLE>
					</TD>
				</TR>
				<TR>
					<TD colspan="4" align="center">&nbsp;</TD>
					<html:hidden property="listStatus" styleId="listStatus" name="topupSlabBean" />
					<html:hidden property="startDate" name="topupSlabBean" />
					<html:hidden property="endDate" name="topupSlabBean" />
					<html:hidden property="flagForCircleDisplay"
						styleId="flagForCircleDisplay" name="topupSlabBean" />
				</TR>

			</TABLE>

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
