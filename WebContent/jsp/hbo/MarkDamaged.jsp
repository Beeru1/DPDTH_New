<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ibm.hbo.dto.HBOUserBean,java.util.ArrayList,com.ibm.hbo.dto.HBOStockDTO"%>
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
<LINK href="./theme/Master.css" rel="stylesheet"
	type="text/css">
<TITLE></TITLE>
<script>
	function openWindow(imeiNo,simnoList,bundle,role) {
	
	if (bundle=="Y" && (role=="3"||role=="4"))
	{
	window.showModalDialog("viewMarkDamaged.do?method=setNewIMEI&imeiNo="+imeiNo+"&simnoList="+simnoList+"&flag=imeiNo",null,"dialogWidth:300px;dialogHeight:300px");
	window.location.reload(true);
	}
	else if (bundle=="N" || (bundle=="Y" && (role=="5"||role=="6"||role=="7")))
	{
	var url="initMarkDamaged.do?method=updtDmgFlag&imeiNo="+imeiNo+"&simnoList="+simnoList+"&flag=imeiNo";
	document.forms[0].action=url;
	document.forms[0].method="post";
	document.forms[0].submit();
	}

}

</script>
</HEAD>

<BODY>
<%
HBOUserBean userBean=(HBOUserBean)session.getAttribute("USER_INFO");
String role=userBean.getActorId();
int warehouseId = Integer.parseInt(userBean.getWarehouseID()); 
%>
<html:form action="/initMarkDamaged.do?method=getValues">
<html:hidden name="HBOMarkDamagedForm" property="invoice"/>
<logic:notEmpty name="HBOMarkDamagedForm" property="arrImeino">
<table>
<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/markAsDamaged.gif"
		width="505" height="22" alt="">
	</TD>
</tr>
</table>
<TABLE border="0" width="87%" align="center" class ="border">
	<TBODY>
		<TR align="center">
			<TD class="border"><logic:equal value="0"
				name="HBOMarkDamagedForm" property="arrImeino">
				<CENTER>Stock not available !!!!</CENTER>
			</logic:equal></TD>
		</TR>
		<TR>
			<TD class="colhead" align="center">Sno</TD>

			<TD class="colhead" align="center">IMEI</TD>

			<TD class="colhead" align="center">SIM NO</TD>
			
			<TD  class="colhead" align="center">Damage Flag</TD>

			<TD class="colhead" align="center">Bundling Status</TD>
				
			<TD class="colhead" align="center">Mark as Damage</TD>

			
			</TR>
			
			<%
			int j=1; 
			String rowDark ="#FFE4E1";
 		   String rowLight = "#FFFFFF";
			%>
			<%
			  ArrayList imeiList = (ArrayList) request.getAttribute("imeiList");
			  int x=0;
			  HBOStockDTO stockDTO=null;
			%>
			
			<logic:iterate id="i" name="HBOMarkDamagedForm" property="arrImeino" indexId="p">
			
		<TR BGCOLOR='<%=((p.intValue()+1) % 2==0 ? rowDark : rowLight) %>'>
			<TD align="center"><%=j%></TD>
			<TD align="center"><div id="No"><bean:write name="i" property="imeiNo"/></div></TD>
			<TD align="center"><bean:write name="i" property="simnoList"/></TD>
			<TD align="center"><bean:write name="i" property="status"/></TD>
			<TD align="center"><bean:write name="i" property="bundle"/></TD>
			<% 
			   stockDTO = (HBOStockDTO) imeiList.get(x);
			   if(stockDTO.getStockStatus().equalsIgnoreCase("I") || stockDTO.getWarehouseId()!= warehouseId){
			%>
			<td align="center">-</td>
			<%}else{%>
			<TD align="center">
			<logic:equal value="N" name="i" property="status">
				<a href="javascript:openWindow('<bean:write name="i" property="imeiNo"/>','<bean:write name="i" property="simnoList"/>','<bean:write name="i" property="bundle"/>',<%=role%>)">Mark As damage</A>
			</logic:equal>	
			<logic:equal value="Y" name="i" property="status">
			Damaged
			</logic:equal>
			</TD>
 				
		</TR>
		<%}
		x++;
		j=j+1;
		%>
		</logic:iterate>

	</TBODY>
</TABLE>
</logic:notEmpty>
</html:form>
</BODY>
</html:html>
