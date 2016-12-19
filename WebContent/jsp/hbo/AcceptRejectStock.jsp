<%try{%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.ArrayList,com.ibm.hbo.dto.HBOStockDTO" %>
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
</HEAD>

<script>function getDetails(Id,cond) 
{
//	var url="viewBatch.do?methodName=viewBatchDetails&Id="+Id+"&cond="+cond;
	var url = "viewStockBatchDetails.do?methodName=viewBatchDetails&Id="+Id+"&cond="+cond;
	window.open(url,"","width=850,scrollbars");
}

</script>
<BODY>
<table>
<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/acceptRejectStock.gif"
		width="505" height="22" alt="">
	</TD>
</tr>
</table>
<html:form action = "/acceptRejectStock.do?methodName=acceptRejectStock"> 
<logic:notEmpty property="arrAssignedProdList" name="HBOAssignStockForm">
<TABLE border="0" class="border" width="100%" align="center">
	<TBODY align="center">

		<TR>
			<TD   class = "colhead">Batch Number</TD>
			<TD   class = "colhead">Assign Date</TD>
			<TD   class = "colhead">Quantity Assigned</TD>
			<TD   class = "colhead">Accept/Reject</TD>

		</TR>

		<%int j = 0;%>
		<% 
		   String rowDark ="#FFE4E1";
 		   String rowLight = "#FFFFFF";
		%>
		<logic:notEmpty name="HBOAssignStockForm" property="arrAssignedProdList">
		<logic:iterate id="prodstocklist" property="arrAssignedProdList"
						name="HBOAssignStockForm" indexId="m">
						<TR BGCOLOR='<%=((m.intValue()+1) % 2==0 ? rowDark : rowLight) %>'>																				
							<%ArrayList arrSimStockList = (ArrayList) request.getAttribute("unverifiedBatch");
								
							%>
							
							<TD  class="txt" align="center"><A
								href="javascript:getDetails('<bean:write name="prodstocklist" property="batch_no" />','I')">
							<bean:write name="prodstocklist" property="batch_no" /></A> 
							<html:hidden name="HBOAssignStockForm" property="arrBatch"
								value="<%=((HBOStockDTO) arrSimStockList.get(j)).getBatch_no()%>" />
							</td>

							<TD class="txt" align="center"><bean:write name="prodstocklist"
								property="assign_date" /></TD>
							<TD class="txt" align="center"><bean:write name="prodstocklist"
								property="assignedProdQty" /></TD>
							<TD class="txt" align="center"><html:select name="HBOAssignStockForm"
								property="arrStatus">
								<html:option value="I">In-Transit</html:option>
								<html:option value="A">Accept</html:option>
								<html:option value="R">Reject</html:option>
							</html:select></TD>
						</TR>
						<%j++;%>
					</logic:iterate>
			</logic:notEmpty>		
		<TR>
			<TD colspan="4" align="center"><html:submit value="Save" styleClass="medium"/></TD>
		</TR>
		
	</TBODY>
</TABLE>
</logic:notEmpty>
<logic:empty property="arrAssignedProdList" name="HBOAssignStockForm">
  <table align="center">
  	<tr align="center">
  		<td align="center">
  	 		<logic:messagesPresent message="true">
				<html:messages id="msg" property="NO_TRANSIT_RECORD" bundle="hboView" message="true">
					<font color="red" size="2"><strong><bean:write name="msg"/></strong></font>
				</html:messages>
			</logic:messagesPresent>
  	 	</td>	
   </tr>
  </table>	
</logic:empty>

<br><br><br>
<h3 align="center" class="colhead">Assigned Stock</h3>
<logic:notEmpty property="acceptRejectProdList" name="HBOAssignStockForm">
<TABLE border="0" width="100%" class="border" align="center">
<TBODY align="center">

		<TR>
			<TD   class = "colhead">Batch Number</TD>
			<TD   class = "colhead">Assign Date</TD>
			<TD   class = "colhead">Quantity Assigned</TD>
			<TD   class = "colhead">Status</TD>
		</TR>
		
		<% 
		   String rowDark1 ="#FFE4E1";
 		   String rowLight1 = "#FFFFFF";
		%>
				
		<logic:iterate id="i" name="HBOAssignStockForm" property="acceptRejectProdList" indexId="m">
		<TR BGCOLOR='<%=((m.intValue()+1) % 2==0 ? rowDark1 : rowLight1) %>'>
			<TD  class="txt" align="center"><a href='javascript:getDetails("<bean:write name="i" property="batch_no"/>","<bean:write name="i" property="status"/>")'><bean:write name="i" property="batch_no"/></a></TD>
			<TD  class="txt" align="center"><bean:write name="i" property="assign_date"/></TD>
			<TD  class="txt" align="center"><bean:write name="i" property="assignedProdQty"/></TD>
			<TD  class="txt" align="center"><bean:write name="i" property="status"/></TD>
		</TR>
		</logic:iterate>
</TBODY>
</TABLE>
</logic:notEmpty>
<logic:empty property="acceptRejectProdList" name="HBOAssignStockForm">
  <table align="center">
  	<tr align="center">
  		<td align="center">
			<html:messages id="msg1" property="NO_RECORD" bundle="hboView" message="true">
				<font color="red" size="2"><strong><bean:write name="msg1"/></strong></font>
			</html:messages>
  	 	</td>	
   </tr>
  </table>
</logic:empty>
</html:form>
</BODY>
</html:html>
<%}catch(Exception e){ e.printStackTrace();}%>