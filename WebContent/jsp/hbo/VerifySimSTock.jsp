
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.ArrayList,com.ibm.hbo.dto.HBOStockDTO" %>
<html:html>

<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="../theme/Master.css" rel="stylesheet" type="text/css">
<TITLE></TITLE>
<script>
	function openWindow(batchno,AssQTY) {
	
		window.open("viewBatchDetails.do?methodName=getBatchDetails&Batch_No="+batchno+"&AssignedQTY="+AssQTY);
		
	}
	
</script>
</HEAD>
<BODY>
<%String rowDark = "#FFE4E1";
String rowLight = "#FFFFFF";%>
<html:form action="/VerifySimStock.do?methodName=VerifySimStock">
	<TABLE cellpadding="4" cellspacing="3" align="center" width="100%">
<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/verifySimStock.gif"
		width="505" height="22" alt="">
	</TD>
</tr>	
			<logic:empty name="HBOVerifySimStockForm" property="unverifiedBatch" > 
			<b>There is no Batch to Verify!!</b>
			</logic:empty>
			<logic:notEmpty name="HBOVerifySimStockForm" property="unverifiedBatch" > 
			<TR align="center">
				<TD class="border"><%int i = 0;%>
			<TABLE cellpadding="6" cellspacing="0" align="center" width="100%">
				<TR>
					<TD height="27" class="colhead" align="center"><B><bean:message bundle="hboView"
						key="verifySimStock.batchNo" /></B></TD>
					<TD height="27" class="colhead" align="center"><B><bean:message bundle="hboView"
						key="verifySimStock.simQty" /></B></TD>
					<TD height="27" class="colhead" align="center"><B><bean:message bundle="hboView"
						key="verifySimStock.assignDT" /></B></TD>
					<TD height="27" class="colhead" align="center"><B><bean:message bundle="hboView"
						key="verifySimStock.Status" /></B></td>
				</TR>

				<logic:iterate id="simstocklist" property="unverifiedBatch"
					name="HBOVerifySimStockForm">
					<TR>
						<%ArrayList arrSimStockList = (ArrayList) request.getAttribute("unverifiedBatch");
						int tempSize1 = arrSimStockList.size();
							%>

						<TD class="txt" align="center"><A
							href="javascript:openWindow('<bean:write name="simstocklist" property="batch_no" />','<bean:write name="simstocklist" property="assignedSimQty" />')">
						<bean:write name="simstocklist" property="batch_no" /></A>
					 <html:hidden name="HBOVerifySimStockForm" property="arrBatch"	value="<%=((HBOStockDTO) arrSimStockList.get(i)).getBatch_no()%>" />
						</td>

						<TD class="txt" align="center"><bean:write name="simstocklist"
							property="assignedSimQty" /></TD>
						<TD class="txt" align="center"><bean:write name="simstocklist"
							property="created_dt" /></TD>
						<TD class="txt" align="center">
						<html:select name="HBOVerifySimStockForm" property="arrStatus"> 						
							<html:option value ="I">In-Transit</html:option>
							<html:option value="A">Accept</html:option>
							<html:option value="R">Reject</html:option>

						</html:select></TD>
					</TR>
					<%i++;%>
				</logic:iterate>
			</TABLE>
			
			</TD>

		</TR>
		<TR>
			<TD align='center'><html:submit styleClass="medium"  value="Save"></html:submit></td>
		</TR>
	</logic:notEmpty>	
	</table>
	<TABLE cellpadding="4" cellspacing="3" align="center" width="100%">
		<TR align="center">
			<td height="23" class="colhead"><bean:message bundle="hboView"
				key="verifySimStock.Verifytitle" /></TD>
		</TR>
		<TR align="center">
			<TD class="border">
			<logic:empty name="HBOVerifySimStockForm" property="verifiedBatch" > 
				<b>There is no Verified Batch!!!</b>
			</logic:empty>
			<logic:notEmpty name="HBOVerifySimStockForm" property="verifiedBatch">
			<TABLE cellpadding="6" cellspacing="0" align="center" width="100%">
				<tr>
				<TD height="27" class="colhead" align="center"><B><bean:message bundle="hboView"
					key="verifySimStock.batchNo" /></B></TD>
				<TD height="27" class="colhead" align="center"><B><bean:message bundle="hboView"
					key="verifySimStock.simQty" /></B></TD>
				<TD height="27" class="colhead" align="center"><B><bean:message bundle="hboView"
					key="verifySimStock.assignDT" /></B></TD>
				<TD height="27" class="colhead" align="center"><B><bean:message bundle="hboView"
					key="verifySimStock.Status" /></B></td>
				</TR>
				<logic:iterate id="simstocklistV" property="verifiedBatch"
					name="HBOVerifySimStockForm" >
										<TR>
						<TD class="txt" align="center"><A
							href="javascript:openWindow('<bean:write name="simstocklistV" property="batch_no" />','<bean:write name="simstocklistV" property="assignedSimQty" />')">
						<bean:write name="simstocklistV" property="batch_no" /></A></td>
						<TD class="txt" align="center"><bean:write name="simstocklistV"
							property="assignedSimQty" /></TD>
						<TD class="txt" align="center"><bean:write name="simstocklistV"
							property="created_dt" /></TD>
						<TD class="txt" align="center"><bean:write name="simstocklistV"
							property="status" /></TD>
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
