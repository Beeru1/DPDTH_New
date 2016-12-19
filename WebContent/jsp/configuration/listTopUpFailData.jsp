<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-Disposition",
			"attachment;filename=listTopUpSlab.xls");
%>
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">


</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onkeypress="return checkKeyPressed();">

<TABLE cellspacing="0" cellpadding="0" border="1" width="100%"
	height="100%" valign="top">
	<html:form action="/TopupSlabAction" method="post">
		<logic:notEmpty name="topupSlabBean" property="topupList">
			<TR align="center" bgcolor="#104e8b">
				<TD align="center" bgcolor="#cd0504"><SPAN
					class="white10heading mLeft5 mTop5"><bean:message
					bundle="view" key="application.sno" /></SPAN></TD>
				<TD align="center" bgcolor="#cd0504"><SPAN
					class="white10heading mLeft5 mTop5"><bean:message
					bundle="view" key="topup.start_amount" /></SPAN></TD>
				<TD align="center" bgcolor="#cd0504"><SPAN
					class="white10heading mLeft5 mTop5"><bean:message
					bundle="view" key="topup.end_amount" /></SPAN></TD>
				<TD align="center" bgcolor="#cd0504"><SPAN
					class="white10heading mLeft5 mTop5"><bean:message
					bundle="view" key="topup.esp_commission" /></SPAN></TD>
				<TD align="center" bgcolor="#cd0504"><SPAN
					class="white10heading mLeft5 mTop5"><bean:message
					bundle="view" key="topup.psp_commission" /></SPAN></TD>
				<TD align="center" bgcolor="#cd0504"><SPAN
					class="white10heading mLeft5 mTop5"><bean:message
					bundle="view" key="topup.service_tax" /> </SPAN></TD>
				<TD align="center" bgcolor="#cd0504"><SPAN
					class="white10heading mLeft5 mTop5"><bean:message
					bundle="view" key="topup.processing_fee" /></SPAN></TD>
				<TD align="center" bgcolor="#cd0504"><SPAN
					class="white10heading mLeft5 mTop5"><bean:message
					bundle="view" key="topup.incard_group" /></SPAN></TD>
				<TD align="center" bgcolor="#cd0504"><SPAN
					class="white10heading mLeft5 mTop5"><bean:message
					bundle="view" key="topup.validity" /></SPAN></TD>
			
				</TR>


			
			<logic:iterate id="topup" name="topupSlabBean" property="topupList"
				scope="request" indexId="i">
				<TR align="center"
					bgcolor="#fce8e6">
					<TD class=" height19"><SPAN class="mLeft5 mTop5 mBot5 black10"><c:out
						value="${i+1}"></c:out></SPAN></TD>
					<TD class=" height19"><SPAN class="mLeft5 mTop5 mBot5 black10"><bean:write
						name="topup" property="startAmount" format="#0.00" /></SPAN></TD>
					<TD class=" height19"><SPAN class="mLeft5 mTop5 mBot5 black10"><bean:write
						name="topup" property="tillAmount" format="#0.00" /></SPAN></TD>
					<TD class=" height19"><SPAN class="mLeft5 mTop5 mBot5 black10"><bean:write
						name="topup" property="espCommission" format="#0.00" /></SPAN></TD>
					<TD class=" height19"><SPAN class="mLeft5 mTop5 mBot5 black10"><bean:write
						name="topup" property="pspCommission" format="#0.00" /></SPAN></TD>
					<TD class=" height19"><SPAN class="mLeft5 mTop5 mBot5 black10"><bean:write
						name="topup" property="serviceTax" format="#0.00" /></SPAN></TD>
					<TD class=" height19"><SPAN class="mLeft5 mTop5 mBot5 black10"><bean:write
						name="topup" property="processingFee" format="#0.00" /></SPAN></TD>
					<TD class="height19"><SPAN class="mLeft5 mTop5 mBot5 black10"><bean:write
						name="topup" property="inCardGroup" /></SPAN></TD>
					<TD class=" height19"><SPAN
						class="mLeft5 mTop5 mBot5 black10"><bean:write
						name="topup" property="validity" /></SPAN></TD>
				</TR>
				
			</logic:iterate>
		</logic:notEmpty>
	</html:form>
</TABLE>

</BODY>
</html:html>
