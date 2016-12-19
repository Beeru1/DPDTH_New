

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="theme/Master.css" rel="stylesheet" type="text/css">
<TITLE></TITLE>
<script language="javascript" src="jScripts/Common.js">
</script>
</HEAD>
<BODY>
<html:form action="/viewDistStockSummary">

<TABLE cellpadding="4" cellspacing="3" class="border" align="center" width="100%">

<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/distibutorStockSummar.gif"
		width="505" height="22" alt="">
	</TD>
</tr>
<TR align="center">
	 	<TD class="border">
		<logic:notEmpty name="DPDistStockSummaryForm" property="distStockSumm" >
			<TABLE cellpadding="6" cellspacing="0" align="center" width="100%" height="100%" valign="top" border="1">			
					<TR bgcolor="#CD0504">
					<TD height="27" align="center"><font color="white"><strong><bean:message bundle="hboView" key="viewDistStockSumm.Distname" /></strong></font></TD>	
						<TD height="27" align="center"><font color="white"><strong><bean:message bundle="hboView" key="viewDistStockSumm.Product" /></strong></font></TD>						
						<TD height="27" align="center"><font color="white"><strong><bean:message bundle="hboView" key="viewDistStockSumm.openingbalence" /></strong></font></TD>						
						<TD height="27" align="center"><font color="white"><Strong><bean:message bundle="hboView" key="viewDistStockSumm.ReceiptFrBotree"/></Strong></font></TD>
						<TD height="27" align="center"><font color="white"><strong><bean:message bundle="hboView" key="viewDistStockSumm.ReceiptFrFSE" /></strong></font></TD>
						<TD height="27" align="center"><font color="white"><strong><bean:message bundle="hboView" key="viewDistStockSumm.SalesToFSE" /></strong></font></TD>
						<TD height="27" align="center"><font color="white"><strong><bean:message bundle="hboView" key="viewDistStockSumm.cosingbalence" /></strong></font></TD>
						<TD height="27" align="center"><font color="white"><strong><bean:message bundle="hboView" key="viewDistStockSumm.damagedstock" /></strong></font></TD>
					</TR>
				
					<logic:iterate id="i" property="distStockSumm" 	name="DPDistStockSummaryForm" >
						<TR bgcolor="#FCE8E6">
							<TD class="txt" align="center"><bean:write name="i" property="distName" /></TD>
							<TD class="txt" align="center"><bean:write name="i" property="prodname" /></TD>
							<TD class="txt" align="center"><bean:write name="i" property="openingBalence" /></TD>
							<TD class="txt" align="center"><bean:write name="i" property="receiptFrBotree" /></TD>
							<TD class="txt" align="center"><bean:write name="i" property="receiptFrFSE" /></TD>
							<TD class="txt" align="center"><bean:write name="i" property="salesToFSE" /></TD>
							<TD class="txt" align="center"><bean:write name="i" property="closingbalence" /></TD>
							<TD class="txt" align="center"><bean:write name="i" property="damagedStock" /></TD>
					</TR>
					</logic:iterate>	
				</TABLE>
				</logic:notEmpty>
				</td>
			</TR>
			
	</TABLE>
</html:form>
</BODY>
</html:html>
