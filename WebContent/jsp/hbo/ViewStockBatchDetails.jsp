<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ibm.virtualization.recharge.dto.UserSessionContext,com.ibm.hbo.common.HBOUser,com.ibm.hbo.dto.HBOStockDTO,java.util.ArrayList"%>
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
<script>
	function damageWindow(imeiNo,simNo,bundle,role) {
	var batch=document.forms[0].batchNo.value;
	imeiNo=imeiNo.trim();
	if (bundle=="Y" && (role=="ROLE_ND"||role=="ROLE_LD"))
	{	window.showModalDialog("viewMarkDamaged.do?methodName=setNewIMEI&imeiNo="+imeiNo+"&simNo="+simNo+"&flag=imeiNo",null,"dialogWidth:300px;dialogHeight:300px");
		window.location.reload(true);
	}
	else if (bundle=="N" || (bundle=="Y" && (role=="ROLE_AD"||role=="ROLE_SS")))
	{
		var url="initViewMarkDamaged.do?methodName=updtDmgFlag&imeiNo="+imeiNo+"&simNo="+simNo+"&batch="+batch;
		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();
	}

}
String.prototype.trim = function () {
    return this.replace(/^\s*/, "").replace(/\s*$/, "");
}

</script>
<TITLE></TITLE>
</HEAD>

<BODY>
<%
	UserSessionContext userSessionContext=(UserSessionContext)session.getAttribute("USER_INFO");
	HBOUser userBean = new HBOUser(userSessionContext);
	String role = (String)request.getAttribute("role");
	int warehouseId = Integer.parseInt(userBean.getWarehouseID());
%>
<html:form action="/viewStockBatchDetails.do">
<html:hidden property="batchNo"/>	
<html:hidden property="warehouseIdHidden"/>
	<TABLE border="0" align="center">
		<TBODY>
		<logic:equal name="HBOAssignStockForm" property="cond" value="A">
<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/viewBatchDetails.gif"
		width="505" height="22" alt="">
	</TD>
</tr>
		</logic:equal>
		<logic:notEqual name="HBOAssignStockForm" property="cond" value="A">
<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/viewBatchDetails.gif"
		width="505" height="22" alt="">
	</TD>
</tr>
		</logic:notEqual>
		<logic:notEmpty name="HBOAssignStockForm" property="arrBatchDetailsList">
		<%// ArrayList batchDetailsList = (ArrayList) request.getAttribute("batchDetailsList");
			int x=0;
		%>
			<TR>
				<TD width="117" align="center" class="colhead">INVOICE NO.</TD>
				<TD width="127" align="center" class="colhead">INVOICE DATE</TD>
				<TD width="124" align="center" class="colhead">IMEI NO.</TD>
				<TD width="124" align="center" class="colhead">SIM No.</TD>
				<TD width="124" align="center" class="colhead">MSIDN No.</TD>
				<TD width="124" align="center" class="colhead">ISSUANCE DATE</TD>
				<TD width="109" align="center" class="colhead">RECEIVING DATE</TD>
				<TD width="120" align="center" class="colhead">COMPANY NAME</TD>
				<TD width="103" align="center" class="colhead">PRODUCT NAME</TD>
				<logic:equal name="HBOAssignStockForm" property="cond" value="A">
			<%--	<logic:equal value="N" name="HBOAssignStockForm" property="damageFlag">--%>
				<TD width="103" align="center" class="colhead">MARK AS DAMAGED</TD>	
				<%--</logic:equal>--%>
				</logic:equal>			
			</TR><bean:define id="userIdHid" name="USER_INFO" property="id"></bean:define>
   <% 
		   String rowDark ="#FFE4E1";
 		   String rowLight = "#FFFFFF";
   %>
   		<logic:iterate id="id" name="HBOAssignStockForm" property="arrBatchDetailsList" indexId="m">
			<TR BGCOLOR='<%=((m.intValue()+1) % 2==0 ? rowDark : rowLight) %>'>
				<TD width="117" align="center"><bean:write name="id" property="invoice_no" /></TD>
				<TD width="127" align="center"><bean:write name="id" property="invoice_date" /></TD>
				<TD width="124" align="center"><bean:write name="id" property="imeiNo" /></TD>
				<%//if(role.equalsIgnoreCase("6")||role.equalsIgnoreCase("7")){%>
				<TD width="124" align="center"><bean:write name="id" property="simNo" /></TD>
				<TD width="124" align="center"><bean:write name="id" property="msidnNo" /></TD>
				<%//}%>
				<TD width="124" align="center"><bean:write name="id" property="issuance_date" /></TD>
				<TD width="109" align="center"><bean:write name="id" property="receive_date" /></TD>
				<TD width="120" align="center"><bean:write name="id" property="company_name" /></TD>
				<TD width="103" align="center"><bean:write name="id" property="model_name" /></TD>
				<logic:equal name="HBOAssignStockForm" property="cond" value="A">
				
				<%--<%if(stockDTO.getStockStatus().equalsIgnoreCase("I") || stockDTO.getWarehouseId()!= warehouseId){%>
				<td align="center">-</td>
				<%}%>--%>
				
				
				
				<TD align="center">
				<logic:equal name="id" property="warehouseId" value="${userIdHid}">
					<logic:equal value="N" name="id" property="damageFlag">
						<a href="javascript:damageWindow('<bean:write name="id" property="imeiNo"/>','<bean:write name="id" property="simNo"/>','<bean:write name="id" property="bundle"/>','<%=role%>')">Mark As Damaged</A>
					</logic:equal>
					<logic:equal value="Y" name="id" property="damageFlag">
						Damaged
					</logic:equal>
				</logic:equal>
				<logic:notEqual name="id" property="warehouseId" value="${userIdHid}">
					-
				</logic:notEqual>
				</TD>
				</logic:equal>
				<%x++;%>
			</TR>
		 </logic:iterate>
		</logic:notEmpty>	
			<tr>
				<td colspan="8" align="center"><html:button value="Close" property="btn" onclick="window.close()" styleClass="medium"></html:button></td>
			</tr>
		</TBODY>
	</TABLE>
</html:form>
</BODY>
</html:html>