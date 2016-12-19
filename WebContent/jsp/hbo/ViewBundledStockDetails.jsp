
<%try { %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ibm.hbo.common.HBOUser,com.ibm.hbo.dto.HBOStockDTO,java.util.ArrayList,com.ibm.virtualization.recharge.dto.UserSessionContext,com.ibm.virtualization.recharge.common.Constants" %>
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
<script>
	function damagedWindow(imeiNo,simNo,bundle,role) {
	window.showModalDialog("viewMarkDamaged.do?methodName=setNewIMEI&imeiNo="+imeiNo+"&simNo="+simNo+"&flag=imeiNo",null,"dialogWidth:300px;dialogHeight:300px");
	window.location.reload(true);
}
</script>
</HEAD>
<BODY>
<%
UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
HBOUser userBean=new HBOUser(sessionContext);
String role=userBean.getActorId();
int warehouseId = Integer.parseInt(userBean.getWarehouseID());
%>
<html:form action="initVerifySimStock.do?methodName=getSimStockList">
<TABLE cellpadding="4" cellspacing="3" align="center" width="100%" align="center">
<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/viewBundleStockDetails.gif"
		width="505" height="22" alt="">
	</TD>
</tr>
<TR align="center">
	 	<TD class="border">
			<TABLE cellpadding="6" cellspacing="0" align="center" width="100%">			

					<TR>
							<TD class="txt" align="center"><bean:message bundle="hboView" key="viewBundleStockDetails.reqId" /></TD>
							<TD>
							<html:text name="HBOViewBundledStockForm" readonly="true" property="requestId">
								</html:text>
							</TD>
						</TR>
						<TR>
							<TD class="txt" align="center"><bean:message bundle="hboView" key="viewBundleStockDetails.bundleQty" /></TD>
							<TD>
							<html:text name="HBOViewBundledStockForm" readonly="true" property="bundledQTY"></html:text>
							</TD>
						</TR>
				
				</TABLE>
				</TD>
			</TR>
			
			<logic:notEmpty name="HBOViewBundledStockForm" property="bundledBatchDetails">
			<TR>
				<TD>
				<% ArrayList bundledBatchDetails = (ArrayList) request.getAttribute("bundledList");
				   int x=0;
				%>
				<TABLE cellpadding="6" cellspacing="0" align="center" width="100%">
				<TBODY>			
					<TR>
						<TD height="27" class="colhead" align="center"><bean:message bundle="hboView" key="viewBundleStockDetails.imeiNo" /></TD>
						<TD height="27" class="colhead" align="center"><bean:message bundle="hboView" key="viewBundleStockDetails.simNo" /></TD>
						<TD height="27" class="colhead" align="center"><bean:message bundle="hboView" key="viewBundleStockDetails.msidnNo" /></TD>
						<TD height="27" class="colhead" align="center"><bean:message bundle="hboView" key="viewBundleStockDetails.markdamage" /></TD>
					</TR>
				
						
						<logic:iterate id="i" property="bundledBatchDetails" name="HBOViewBundledStockForm" indexId ="j">
						<%
						   HBOStockDTO stockDTO = (HBOStockDTO) bundledBatchDetails.get(x);
						%>
						<TR >
						<TD align="center"><bean:write name="i" property="imeiNo" /> </TD>
							<TD class="txt" align="center"><bean:write name="i" property="simNo" /> </TD>
							<TD class="txt" align="center"><bean:write name="i" property="msidnNo" /></TD>
							<%if(stockDTO.getStockStatus().equalsIgnoreCase("I") || stockDTO.getWarehouseId()!= warehouseId){%>
							<td align="center">-</td>
							<%}else{%>
							<TD class="txt" align="center">	<!--<bean:write name="i" property="damageFlag"/>-->						
							<logic:equal value="N" name="i" property="damageFlag">
								<a href="javascript:damagedWindow('<bean:write name="i" property="imeiNo"/>','<bean:write name="i" property="simNo"/>','Y',<%=role%>)">Mark As Damaged</a>
							</logic:equal>	
							<logic:equal value="Y" name="i" property="damageFlag">
								Damaged
							</logic:equal>
						</TD>
						<%}
						x++;%>
						</TR>
						</logic:iterate>
					</TBODY>
				</TABLE>
				</td>
				</tr>
		</logic:notEmpty>
				

			<tr>
				<td colspan=2></td>
			</tr>
			<tr>
				<td colspan=2 align="center"><html:button property = "" value="Close"   styleClass="medium" onclick="javascript:closeWindow();"></html:button></td>
			</tr>
	
	</TABLE>

</html:form>
</BODY>

</html:html>
<%}catch (Exception e){e.printStackTrace();} %>