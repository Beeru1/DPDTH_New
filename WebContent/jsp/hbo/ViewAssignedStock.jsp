
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
<LINK href="../theme/Master.css" rel="stylesheet"
	type="text/css">
<TITLE></TITLE>
<script>function getDetails(Id,cond) 
{
	var url="viewStockBatchDetails.do?methodName=viewBatchDetails&Id="+Id+"&cond="+cond;
	window.open(url);
}

</script>
</HEAD>

<BODY>
<logic:messagesPresent message="true">
	<p align="center"><html:messages id="msg" message="true">
	   <b><font color="red" size="2"><bean:write name="msg" filter="false" /></font></b>
   	</html:messages></p>
</logic:messagesPresent>
<TABLE border="0" align="center" width="100%">
	<TBODY>
<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/viewAssignedHandsetStock.gif"
		width="505" height="22" alt="">
	</TD>
</tr>
	 <logic:notEmpty name="HBOAssignStockForm" property="arrAssignedProdList">	
		<TR>
			<TD width="150" align="center" class="colhead"><b>Batch No</b></TD>
			<TD width="150" align="center" class="colhead"><b>Assigning Date</b></TD>
			<TD width="150" align="center" class="colhead"><b>Quantity Assigned</b></TD>
			<TD width="150" align="center" class="colhead"><b>Assigned To</b></TD>
			<TD width="150" align="center" class="colhead"><b>Status</b></TD>
		</TR>
   <% 
		   String rowDark ="#FFE4E1";
 		   String rowLight = "#FFFFFF";
   %>

		<logic:iterate id="i" name="HBOAssignStockForm" property="arrAssignedProdList" indexId="m">
		<TR BGCOLOR='<%=((m.intValue()+1) % 2==0 ? rowDark : rowLight) %>'>
			<TD width="150" height="26" class="txt"  align="center"><a href='javascript:getDetails("<bean:write name="i" property="batch_no"/>","A/R")'><bean:write name="i" property="batch_no"/></a></TD>
			<TD width="150" height="26" class="txt" align="center"><bean:write name="i" property="assign_date" /></TD>
			<TD width="150" height="26" class="txt"  align="center"><bean:write name="i" property="assignedProdQty"/></TD>
			<TD width="150" height="26" class="txt"  align="center"><bean:write name="i" property="warehouseTo_Name"/></TD>
			<TD width="150" height="59" class="txt"  align="center"><bean:write name="i" property="status"/></TD>
		</TR>
		</logic:iterate>
	</logic:notEmpty>
	</TBODY>
</TABLE>
<p align="center"><b><font color="red" size="2"><html:errors/></font></b></p>
</BODY>
</html:html>
