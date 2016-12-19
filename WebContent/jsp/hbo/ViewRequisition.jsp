
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<html:html>
<HEAD>
<%@ page language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="<%=request.getContextPath()%>/theme/Master.css" rel="stylesheet"
	type="text/css">
<TITLE></TITLE>
<script>
function uploadFile()
{
	var reqId = document.getElementById("requisition_id").value;
}
function setHidden(a){
	document.forms[0].requisition_id.value = a;
	var b = document.forms[0].requisition_id.value;
	alert(b);
}
function getDetails(invoice_no){
	var url = "viewAssignedStock.do?methodName=viewAssignedStock";
	document.forms[0].action = url;
	document.forms[0].method = "post" ;
	document.forms[0].submit();
}
</script>
</HEAD>
<BODY>
<h2 align="center" ></h2>
<BR>
<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>
<html:form action="/initViewRequisition.do">
<table>
<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/viewHandsetRequisition.gif"
		width="505" height="22" alt="">
	</TD>
</tr>
</table>
	<TABLE cellpadding="4" cellspacing="3" align="center" width="75%">
		<logic:empty name="HBORequisitionForm" property="arrRequisitionList">
			<tr><td align="center"><font color="#CD0504" size="2"><strong><bean:message bundle="hboView" key="viewRequisition.noRecordFound"/></strong></font></td></tr>
		</logic:empty>
		
<logic:notEmpty name="HBORequisitionForm" property="arrRequisitionList">
		<tr >
		<TD align="center" colspan="10" height="5"></TD>
		</tr>
	<logic:equal name="HBORequisitionForm" property="userType" value="ROLE_ND">
			<TR bgcolor="#CD0504">
				<TD align="center"  class="colhead"><FONT color="white"><bean:message bundle="hboView" key="viewRequisition.requisitionNo"/></FONT></TD>
				<TD align="center"  class="colhead"><FONT color="white"><bean:message bundle="hboView" key="viewRequisition.requisitionDate"/></FONT></TD>
				<TD align="center"  class="colhead"><FONT color="white"><bean:message bundle="hboView" key="viewRequisition.modelCode"/></FONT></TD>
				<TD align="center"  class="colhead"><FONT color="white"><bean:message bundle="hboView" key="viewRequisition.forMonth"/></FONT></TD>
				<TD align="center"  class="colhead"><FONT color="white"><bean:message bundle="hboView" key="viewRequisition.qtyRequisitioned"/></FONT></TD>
				<TD align="center"  class="colhead"><FONT color="white"><bean:message bundle="hboView" key="viewRequisition.invoiceNo"/></FONT></TD>
				<TD align="center"  class="colhead"><FONT color="white"><bean:message bundle="hboView" key="viewRequisition.invoiceDt"/></FONT></TD>
				<TD align="center"  class="colhead"><FONT color="white"><bean:message bundle="hboView" key="viewRequisition.qtyUploaded"/></FONT></TD>
			</TR>
		<bean:define id="oldVal" value="" />
		<logic:iterate id="i" name="HBORequisitionForm" property="arrRequisitionList" indexId="m">
			<TR BGCOLOR='<%=((m.intValue()+1) % 2==0 ? rowDark : rowLight) %>'>
			<logic:notEqual name="i" property="requisitionId" value='${oldVal}' >	
				<TD align="center"  class="txt"><bean:write name="i" property="requisitionId"/></TD>
				<TD align="center"  class="txt" ><bean:write name="i" property="requisitionDate"/></TD>
			 	<TD align="center"  class="txt"><bean:write name="i" property="modelCode"/></TD>
				<TD align="center"  class="txt"><bean:write name="i" property="month"/></TD>
				<TD align="center"  class="txt"><bean:write name="i" property="qtyRequisition"/></TD>
			</logic:notEqual>	
			<logic:equal name="i" property="requisitionId" value='${oldVal}'>	
				<TD align="center"  class="txt">.</TD>
				<TD align="center"  class="txt">.</TD>
			 	<TD align="center"  class="txt">.</TD>
				<TD align="center"  class="txt">.</TD>
				<TD align="center"  class="txt">.</TD>
			</logic:equal>
				
    		<logic:notEmpty name="i" property="invoice_no">
				<TD align="center"  class="txt"><bean:write name="i" property="invoice_no"/></TD>
				<TD align="center"  class="txt"><bean:write name="i" property="invoice_dt"/></TD>
				<TD align="center"  class="txt"><bean:write name="i" property="qtyuploaded"/></TD>
			</logic:notEmpty>
			<logic:empty name="i" property="invoice_no">
				<TD align="center"  class="txt">.</td>
				<TD align="center"  class="txt">.</td>
				<TD align="center"  class="txt">.</td>
			</logic:empty>
			</TR>
				<c:set var="oldVal" ><bean:write name="i" property="requisitionId"/></c:set>
		</logic:iterate>
	</logic:equal>
	<logic:equal name="HBORequisitionForm" property="userType" value="ROLE_MA">		
			<TR bgcolor="#CD0504">
				<TD align="center"  class="colhead"><FONT color="white"><bean:message bundle="hboView" key="viewRequisition.requisitionNo"/></FONT></TD>
				<TD align="center"  class="colhead"><FONT color="white"><bean:message bundle="hboView" key="viewRequisition.requisitionDate"/></FONT></TD>
				<TD align="center"  class = "colhead"><FONT color="white"><bean:message bundle="hboView" key="viewRequisition.assignedTo"/></FONT></TD>
				<TD align="center"  class="colhead"><FONT color="white"><bean:message bundle="hboView" key="viewRequisition.modelCode"/></FONT></TD>
				<TD align="center"  class="colhead"><FONT color="white"><bean:message bundle="hboView" key="viewRequisition.forMonth"/></FONT></TD>
				<TD align="center"  class="colhead"><FONT color="white"><bean:message bundle="hboView" key="viewRequisition.qtyRequisitioned"/></FONT></TD>
			</TR>
		<logic:iterate id="i" name="HBORequisitionForm" property="arrRequisitionList" indexId="m">
			<TR BGCOLOR='<%=((m.intValue()+1) % 2==0 ? rowDark : rowLight) %>'>
				<TD align="center"  class="txt" ><bean:write name="i" property="requisitionId"/></TD>
				<TD align="center"  class="txt" ><bean:write name="i" property="requisitionDate"/></TD>
				<TD align="center"  class="txt"><bean:write name="i" property="warehouse_to_name"/></TD>
				<TD align="center"  class="txt"><bean:write name="i" property="modelCode"/></TD>
				<TD align="center"  class="txt"><bean:write name="i" property="month"/></TD>
				<TD align="center"  class="txt"><bean:write name="i" property="qtyRequisition"/></TD>
			</TR>
		</logic:iterate>
	</logic:equal>		
</logic:notEmpty>	
	</TABLE>
</html:form>
</BODY>
</html:html>
