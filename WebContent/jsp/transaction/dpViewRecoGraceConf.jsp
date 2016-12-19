<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@page import="java.util.ArrayList"%>
<html:html>
<HEAD>
<%@ page language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>


<%
System.out.println("--------------+++++++++++++++++++view recoperiod detail+++++++++++++++++++++++++--------------------");
 %>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="<%=request.getContextPath()%>/theme/Master.css" rel="stylesheet"
	type="text/css">
	<LINK href=./theme/text.css rel="stylesheet" type="text/css">
<TITLE></TITLE>
<script>
	function closeWindow()
	{
	 window.close();
	}
	function updateGraceperiod(recoPeriodId)
		{
		var url = "recoPeriodConf.do?methodName=updateRecoGracePeriod&recoPeriodId="+recoPeriodId;
		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();	
		}
	function deleteReco(recoPeriodId)
		{
		var url = "recoPeriodConf.do?methodName=deleteReco&recoPeriodId="+recoPeriodId;
		//document.forms[0].action=url;
		var form = window.opener.document.forms[0];
		//form.methodName="deleteReco";
		//form.closeRecoIds.value=recoPeriodId;
		form.action=url;
		form.method="post";
		
		window.close();
		form.submit();
		
		//document.forms[0].method="post";
		//document.forms[0].submit();	
		}
</script>
</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif">
<h2 align="center" ></h2>
<BR>
<html:form action="recoPeriodConf.do">
<IMG src="<%=request.getContextPath()%>/images/recoPeriodConfig.jpg" width="450" height="22" alt="">
	
<%try{%>
<table border="0" width="100%" >
		<TR>
		<FONT color="RED"><STRONG> <html:errors
							bundle="error" property="success.grace" /></STRONG></FONT>
							</TR>
							</table>
<logic:notEmpty property="recoPeriodList" name="RecoPeriodConfFormBean">
<div style="width:100%; height:500px; overflow:scroll;" >
<table border="2" width="100%" >
<tr><td>
<table border="0" width="100%" >

<% if(((ArrayList)(request.getAttribute("recoPeriodList"))).size()!=0) {%>
<tr>
<td align="center"><h3>From Date</h3></td>
<td align="center"><h3>To Date</h3></td>					
<td align="center"><h3>Grace Period</h3></td>
</tr>
	
	<logic:iterate name="recoPeriodList" id="recoId"  type="com.ibm.dp.dto.RecoPeriodDTO">
	<tr>
	<td align="center"><b><bean:write name="recoId" property="fromDate"/></td>
	<td align="center"><b><bean:write name="recoId" property="toDate"/></b></td>
	<td align="center"><html:text property="gracePeriod" styleClass="box" styleId="gracePeriod" size="10" maxlength="3" name="recoId"  /></td>
	</tr>
	</logic:iterate>
	<tr align ="centre" colspan="3" colspacing="3">
	<td align="center"><input type=button value="Delete Reco" onclick="deleteReco('${recoId.recoPeriodId}')"/></td>
	<td align="center"><input type=button value="Update Graceperiod" onclick="updateGraceperiod('${recoId.recoPeriodId}')"/></td>
	</tr>
	<%}%>
	</table>

</td></tr>
</table>
</div>

<table border="1" width="100%">
	<TR>
	<td align="center" width="100%">
		<html:button property="btn" value="Close" onclick="closeWindow();" /></td>
	</TR>	
</table>
</logic:notEmpty>	
<%}catch(Exception ex){ex.printStackTrace();} %>	
<br>
</html:form>
</BODY>
</html:html>
