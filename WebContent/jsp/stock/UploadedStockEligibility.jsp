<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>
	<!--  <head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>View Stock Eligibility</title>
	</head> -->
	<HEAD>
		<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
			pageEncoding="ISO-8859-1"%>
		<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<META name="GENERATOR" content="IBM Software Development Platform">
		<META http-equiv="Content-Style-Type" content="text/css">
		<LINK href="<%=request.getContextPath()%>/theme/Master.css"
			rel="stylesheet" type="text/css">
	
		<title>ViewStockEligibility</title>
	</HEAD>

	<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
		bgcolor="#FFFFFF" marginheight="0" onload="doInitials()">
	
		<TABLE cellspacing="0" cellpadding="0" border="0" height="100%" valign="top">
			<TR>
				<TD colspan="2" valign="top" width="100%">
					<jsp:include page="../common/dpheader.jsp" flush="" />
				</TD>
			</TR>
		
			<TR valign="top">
				<TD width="167" align="left" bgcolor="b4d2e7" valign="top" height="100%">
					<jsp:include page="../common/leftHeader.jsp" flush="" />
				</TD>
	
			<TD valign="top" width="100%" height="100%">
				<html:form action="/ViewStockEligibility" method="post"
				type="com.ibm.dp.beans.ViewStockEligibilityBean"
				name="ViewStockEligibilityBean" scope="request">
				
				<html:hidden property="methodName" />
				<html:hidden property="intUserAcctLevel" styleId="intUserAcctLevel" />
				<html:hidden property="distOlmId" styleId="distOlmId" />
	
				<TABLE border="0" cellpadding="5" cellspacing="0" class="text">
					<TR>
						<TD colspan="4" class="text"><BR>
							<IMG
								src="<%=request.getContextPath()%>/images/viewStockEligibility.jpg"
								width="505" height="22" alt="">
						</TD>
					</TR>
				</TABLE>
				
				<TABLE align="center" class="mLeft5" width="50%">
						<tr id="balanceCol" align="center" style='display:inline'>
							<td class="txt" align="left"><strong>Swap Products</strong></td>
							<td align="left" >&nbsp;</td>
						</tr>
	
						<tr id="balanceCol" align="center" style='display:inline'>
							<td class="txt" align="left">
								<strong>Security Eligibility Balance</strong>
							</td>
							<td align="left">
								<html:text name="ViewStockEligibilityBean"
										   property="securityEligibilityBalanceSwap" 
										   styleId="balance" 
										   readonly="true" />
							</td>
							<td align="left" >&nbsp;</td>
						</tr>
				</TABLE>
	
				<TABLE align="center" class="mLeft5" width="100%">
	
					
						<TR align="center" bgcolor="#CD0504" style="color: #ffffff !important;">
							<TD align="center" bgcolor="#CD0504">
								<SPAN class="white10heading mLeft5 mTop5">Security Deposit Available  Qty</SPAN>
							</TD>
							<TD align="center" bgcolor="#CD0504">
								<SPAN class="white10heading mLeft5 mTop5"> SWAP SD Eligibility </SPAN>
							</TD>
							<TD align="center" bgcolor="#CD0504">
								<SPAN class="white10heading mLeft5 mTop5"> SWAP HD Eligibility </SPAN>
							</TD>
							<TD align="center" bgcolor="#CD0504">
								<SPAN class="white10heading mLeft5 mTop5">SWAP PVR Eligibility  <br>Product Qty</SPAN>
							</TD>
							<TD align="center" bgcolor="#CD0504">
								<SPAN class="white10heading mLeft5 mTop5"> SWAP HD DVR Eligibility</SPAN>
							</TD>
							<TD align="center" bgcolor="#CD0504">
								<SPAN class="white10heading mLeft5 mTop5"> SWAP  SD Plus Eligibility</SPAN>
							</TD>
							<TD align="center" bgcolor="#CD0504">
								<SPAN class="white10heading mLeft5 mTop5"> CAM Eligibility</SPAN>
							</TD>
						</TR>
	
						<TR bgcolor="#FCE8E6">
							<TD class="text" align="center">
								<bean:write name="ViewStockEligibilityBean" property="securityDepositAvailableQty" />
							</TD>
							<TD class="text" align="center">
								<bean:write name="ViewStockEligibilityBean" property="swapSdEligibility" />
							</TD>
							<TD class="text" align="center">
								<bean:write name="ViewStockEligibilityBean" property="swapHdEligibility" />
							</TD>
							<TD class="text" align="center">
								<bean:write name="ViewStockEligibilityBean" property="swapPVREligibilityProductQty" />
							</TD>
							<TD class="text" align="center">
								<bean:write name="ViewStockEligibilityBean" property="swapHdDvrEligibility" />
							</TD>
							<TD class="text" align="center">
								<bean:write name="ViewStockEligibilityBean" property="swapHdPlusEligibility" />
							</TD>
							<TD class="text" align="center">
								<bean:write name="ViewStockEligibilityBean" property="camEligibilitySwap" />
							</TD>
						</TR>
							
				</TABLE>
				<br><br>
				<TABLE align="center" class="mLeft5" width="50%">
						<tr id="balanceCol" align="center" style='display:inline'>
							<td class="txt" align="left"><strong>Commercial Products</strong></td>
							<td align="left" >
								&nbsp;
							</td>
						</tr>
	
					<tr id="balanceCol" align="center" style='display:inline'>
							<td class="txt" align="left"><strong>Security Eligibility Balance</strong></td>
							<td align="left"><html:text name="ViewStockEligibilityBean"
								property="securityEligibilityBalanceCommercial" styleId="balance" readonly="true" /></td>
							<td align="left" >
								&nbsp;
							</td>
						</tr>
				</TABLE>
	
				<TABLE align="center" class="mLeft5" width="100%">
					<TR align="center" bgcolor="#CD0504" style="color: #ffffff !important;">
						<TD align="center" bgcolor="#CD0504">
							<SPAN class="white10heading mLeft5 mTop5">Security Deposit Eligibility</SPAN>
						</TD>
						<TD align="center" bgcolor="#CD0504">
							<SPAN class="white10heading mLeft5 mTop5">SD Box/ SD plus Eligibility </SPAN>
						</TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"> HD Box Eligibility </SPAN></TD>
						<TD align="center" bgcolor="#CD0504">
							<SPAN class="white10heading mLeft5 mTop5">HD DVR Box Eligibility  <br>Product Qty</SPAN>
						</TD>
						<TD align="center" bgcolor="#CD0504">
							<SPAN class="white10heading mLeft5 mTop5"> CAM Eligibility</SPAN>
						</TD>
					</TR>
					<TR bgcolor="#FCE8E6">
							<TD class="text" align="center">
								<bean:write name="ViewStockEligibilityBean" property="securityDepositEligibility" />
							</TD>
							<TD class="text" align="center">
								<bean:write name="ViewStockEligibilityBean" property="sdBoxSdPlusEligibility" />
							</TD>
							<TD class="text" align="center">
								<bean:write name="ViewStockEligibilityBean" property="hdBoxEligibility" />
							</TD>
							<TD class="text" align="center">
								<bean:write name="ViewStockEligibilityBean" property="hdDvrBoxEligibility" />
							</TD>
							<TD class="text" align="center">
								<bean:write name="ViewStockEligibilityBean" property="camEligibilityCommerical" />
							</TD>
						</TR>
				</TABLE>
				
			</html:form>
			</TD>
		</TR>
		<TR align="center" bgcolor="4477bb" valign="top">
			<TD colspan="2" height="17" align="center"><jsp:include
				page="../common/footer.jsp" flush="" /></TD>
		</TR>
	</TABLE>
	
</BODY>
</html:html>