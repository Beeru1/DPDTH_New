
<%try { %>
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
<LINK href="./theme/Master.css" rel="stylesheet" type="text/css">
<TITLE></TITLE>
<script language="javascript" src="jScripts/Common.js">
</script>
</HEAD>
<BODY>
<html:form action="/VerifySimStock.do?method=VerifySimStock">

<TABLE cellpadding="4" cellspacing="3" class="border" align="center" width="100%">
<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/viewAssignSIMStock.gif"
		width="505" height="22" alt="">
	</TD>
</tr>
<TR align="center">
	 	<TD class="border">
			<logic:empty  name="HBOVerifySimStockForm" property="assignedBatch"> 
				<B><bean:message bundle="hboView" key="viewAssignSImStock.noAssignStock" /></B>						
			</logic:empty>	 	
	 		<logic:notEmpty name="HBOVerifySimStockForm" property="assignedBatch" >
			<TABLE cellpadding="6" cellspacing="0" align="center" width="100%">			

					<TR>
						<TD height="27" class="colhead" align="center"><STRONG><bean:message bundle="hboView" key="viewAssignSImStock.batchNo" /></Strong></TD>
						<TD height="27" class="colhead" align="center"><strong><bean:message bundle="hboView" key="viewAssignSImStock.assignTo" /></Strong></TD>
						<TD height="27" class="colhead" align="center"><strong><bean:message bundle="hboView" key="viewAssignSImStock.simQty" /></strong></TD>						
						<TD height="27" class="colhead" align="center"><Strong><bean:message bundle="hboView" key="viewAssignSImStock.assignedDT"/></Strong></TD>
						<TD height="27" class="colhead" align="center"><strong><bean:message bundle="hboView" key="viewAssignSImStock.status" /></strong></TD>
					</TR>
				
					<logic:iterate id="simstocklistV" property="assignedBatch" 	name="HBOVerifySimStockForm" >
						<TR>
							<TD class="txt" align="center"><A href="javascript:openWindow('<bean:write name="simstocklistV" property="batch_no" />','<bean:write name="simstocklistV" property="assignedSimQty" />')">
							<bean:write	name="simstocklistV" property="batch_no" /></A></td>
							<TD class="txt" align="center"><bean:write name="simstocklistV" property="warehouseTo_Name" /></TD>
							<TD class="txt" align="center"><bean:write name="simstocklistV" property="assignedSimQty" /></TD>
							<TD class="txt" align="center"><bean:write name="simstocklistV" property="created_dt" /></TD>
							<TD class="txt" align="center"><bean:write name="simstocklistV" property="status" /></TD>
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
<%}catch (Exception e){e.printStackTrace();} %>
