
<%try { %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html:html>
<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="../theme/Master.css" rel="stylesheet" type="text/css">
<TITLE></TITLE>
<script language="javascript" src="jScripts/Common.js">
</script>
</HEAD>
<BODY>
<html:form action="initVerifySimStock.do?method=getSimStockList">
<TABLE cellpadding="4" cellspacing="3" align="center" width="100%">
<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/viewBatchDetails.gif"
		width="505" height="22" alt="">
	</TD>
</tr>
<TR align="center">
	 	<TD class="border">
			<TABLE cellpadding="6" cellspacing="0" align="center" width="100%">		

					<TR>
							<TD class="txt" align="center"><bean:message bundle="hboView" key="viewBatchDetails.batchNo" /></TD>
							<TD>
							<html:text name="HBOViewSimStockForm" readonly="true" property="batchNo">
								</html:text>
							</TD>
						</TR>
						<TR>
							<TD class="txt" align="center"><bean:message bundle="hboView" key="viewBatchDetails.assignedQty" /></TD>
							<TD>
							<html:text name="HBOViewSimStockForm" readonly="true" property="simQty"></html:text>
							</TD>
						</TR>
				
				</TABLE>
				</TD>
			</TR>
			<tr>
				<td colspan=2></td>
			</tr>
			<TR>
				<TD>
				<TABLE cellpadding="6" cellspacing="0" align="center" width="100%">			

					<TR>
							<TD height="27" class="colhead" align="center"><bean:message bundle="hboView" key="viewBatchDetails.simNo" /></TD>
							<TD height="27" class="colhead" align="center"><bean:message bundle="hboView" key="viewBatchDetails.msidnNo" /></TD>
						</TR>
						<logic:iterate id="simlist" property="simList" name="HBOViewSimStockForm">
							<TR>
							<TD class="txt" align="center"><bean:write name="simlist" property="simNo" /> </TD>
							<TD class="txt" align="center"><bean:write name="simlist" property="msidnNo" /></TD>
						</TR>
						</logic:iterate>
				
				</TABLE>

				</TD>

			</TR>

			<tr>
				<td colspan=2></td>
			</tr>
			<tr>
				<td colspan=2 align="center"><html:button property = "" styleClass = "medium" value="Close" onclick="javascript:closeWindow();"></html:button></td>
			</tr>
		
	</TABLE>

</html:form>
</BODY>

</html:html>
<%}catch (Exception e){e.printStackTrace();} %>