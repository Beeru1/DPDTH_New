<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<html:html>
<HEAD>
<%@ page language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="${pageContext.request.contextPath}/theme/text.css" type="text/css">
<TITLE></TITLE>
<script>
function closeWindow()
{
 window.close();
}

</script>
</HEAD>
<BODY background="${pageContext.request.contextPath}/images/bg_main.gif">
<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>
<h2 align="center" ></h2>
<BR>
<html:form action="/ErrorPO.do">
<IMG src="${pageContext.request.contextPath}/images/poDetails.jpg" width="542" height="26" alt="PO Details">
	
<%try{%>
<logic:notEmpty property="duplicateSTBList" name="ErrorPOBean">
<div style="width:100%; height:470px; overflow:scroll;" >
      <table width="100%" align="left" border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse;" >
			<thead>
				<tr style="position: relative; top:expression(this.offsetParent.scrollTop-2);" class="text white"> 																												
					<td bgcolor="#CD0504" class="colhead" align="center" width="10%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.Circle"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="5%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.DISTOLMID"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="10%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.DISTName"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="8%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.TSMName"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="8%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.PONO"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="8%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.DCNO"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="8%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.DCDate"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="5%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.SRNO"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="10%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.ProdName"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="10%"><FONT color="white"><bean:message  bundle="dp" key="label.Old.STB.Status"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="20%"><FONT color="white"><bean:message  bundle="dp" key="label.DCA.ERROR_MSG"/></FONT></td>
				</tr>
			</thead>
      <tbody>
	   <logic:iterate id="serialNos" property="duplicateSTBList" indexId="i"  name="ErrorPOBean">
		<TR BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>' >
					<td align="center" class="text"><bean:write name="serialNos" property="strCircleName"/></td>
					<td align="center" class="text"><bean:write name="serialNos" property="strDistOLMId"/></td>
					<td align="center" class="text"><bean:write name="serialNos" property="strDistName"/></td>
					<td align="center" class="text"><bean:write name="serialNos" property="strTSMName"/></td>
					<td align="center" class="text"><bean:write name="serialNos" property="strPONumber"/></td>
					<td align="center" class="text"><bean:write name="serialNos" property="strDCNo"/></td>
					<td align="center" class="text"><bean:write name="serialNos" property="strDCDate"/></td>
					<td align="center" class="text"><bean:write name="serialNos" property="strSerialNo"/></td>
					<td align="center" class="text"><bean:write name="serialNos" property="strProductName"/></td>
					<td align="center" class="text"><bean:write name="serialNos" property="strSTBStatus"/></td>
					<td align="center" class="text"><bean:write name="serialNos" property="strErrorMsg"/></td>
		</tr>
	  </logic:iterate>
	</tbody>
  </table>
</div>
<table border="0" width="100%">
	<TR><td align="center" width="100%"><html:button property="btn" value="Close" onclick="closeWindow();" /></td></TR>	
</table>
</logic:notEmpty>	
<%}catch(Exception ex){ex.printStackTrace();} %>	
<br>
</html:form>
</BODY>
</html:html>
