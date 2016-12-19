
<%try {%>
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
<LINK href="../theme/Master.css" rel="stylesheet" type="text/css">
<TITLE></TITLE>
<script language="javascript" src="jScripts/Common.js">
</script>
</HEAD>
<BODY>
<html:form action="/viewBundledStock.do?method=getBundledStock">
	<TABLE cellpadding="4" cellspacing="3" align="center" width="100%">
<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/viewBundleStock.gif"
		width="505" height="22" alt="">
	</TD>
</tr>		<TR align="center">
			<TD class="border">
			<logic:empty name="HBOViewBundledStockForm" property="bundledBatch">
				<bean:message bundle="hboView" key="viewBundleStock.noBundleStock" />	
			</logic:empty>
			<logic:notEmpty name="HBOViewBundledStockForm" property="bundledBatch" >
			<TABLE cellpadding="6" cellspacing="0" align="center" width="100%">
				<tr>
				<TD height="27" class="colhead" align="center"><b><bean:message bundle="hboView"
					key="viewBundleStock.bundledReqId" /></b></TD>
				<TD height="27" class="colhead" align="center"><b><bean:message bundle="hboView"
					key="viewBundleStock.bundledQty" /></b></TD>
				<TD height="27" class="colhead" align="center"><b><bean:message bundle="hboView"
					key="viewBundleStock.modelCode" /></b></TD>
				<TD height="27" class="colhead" align="center"><b><bean:message bundle="hboView"
					key="viewBundleStock.modelName" /></b></TD>
				<TD height="27" class="colhead" align="center"><b><bean:message bundle="hboView"
					key="viewBundleStock.circle" /></b></TD>
				<TD height="27" class="colhead" align="center"><b><bean:message bundle="hboView"
					key="viewBundleStock.bundledDT" /></b></TD>
				</TR>
				<logic:iterate id="i" property="bundledBatch"
					name="HBOViewBundledStockForm" indexId="j">
					<TR>
						<TD class="txt" align="center"><A
							href="javascript:viewBundleDetailsOpenWindow('<bean:write name="i" property="requestId" />','<bean:write name="i" property="bundledQty" />')">
						<bean:write name="i" property="requestId" /></A></td>
						<td align="center"><bean:write name="i" property="bundledQty" /></td>
						<TD class="txt" align="center"><bean:write name="i"
							property="modelCode" /></TD>
						<TD class="txt" align="center"><bean:write name="i"
							property="modelName" /></TD>						
						<TD class="txt" align="center"><bean:write name="i"
							property="circleName" /></TD>
						<TD class="txt" align="center"><bean:write name="i"
							property="bundledDt" /></TD>
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
<%} catch (Exception e) {
	e.printStackTrace();
}%>
