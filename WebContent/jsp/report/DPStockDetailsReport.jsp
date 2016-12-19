<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="<%=request.getContextPath()%>/theme/text.css" rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/theme/chromestyle2.css" />
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/chrome.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/JScriptLib.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>

<SCRIPT language="javascript" type="text/javascript"> 

	
	function getAccountName()
	{
	 
	    var levelId = document.getElementById("levelId").value;
//	    alert('Level ::::' + levelId);
		var url = "initInventoryStatusReport.do?methodName=initStatusAccount&levelId="+levelId;
		if(window.XmlHttpRequest) 
		{
			req = new XmlHttpRequest();
		}else if(window.ActiveXObject) 
		{
			req=new ActiveXObject("Microsoft.XMLHTTP");
		}
		if(req==null) 
		{
			alert("Browser Doesnt Support AJAX");
			return;
		}
		req.onreadystatechange = getAccountNameChange;
		req.open("POST",url,false);
		req.send();
	}

	function getAccountNameChange()
	{
		if (req.readyState==4 || req.readyState=="complete") 
	  	{
			var xmldoc = req.responseXML.documentElement;
			if(xmldoc == null) 
			{
				return;
			}
			
			// Get Conditions.
			optionValues = xmldoc.getElementsByTagName("option");
			var selectObj = document.getElementById("accountId");
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select Account Name","0");
			selectObj.options[selectObj.options.length] = new Option("All","9999");
			for(var i=0; i<optionValues.length; i++)
			{
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			}
		}
	}
	
	function exportToExcel()
	{
		var levelId = document.getElementById("levelId").value;
	//	alert('LevelId '+ levelId);
		if ((levelId==null)||(levelId=="0")){
			alert('Select Any Role.');
			return false;
		}

	    var accountId = document.getElementById("accountId").value;
	//    alert('Account ID' + accountId);
	    if ((accountId==null)||(accountId=="0")){
			alert('Select Account Name.');
			return false;
		}
	    
		var url = "initInventoryStatusReport.do?methodName=getReportDataExcel&levelId="+levelId+"&accountId="+accountId;
		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();
	}
	</SCRIPT>

</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" >

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%" 	height="100%" valign="top">
	
	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include page="../common/dpheader.jsp" flush="" /></TD>
	</TR>
	
	<html:form name="DPInventoryStatusFormBean" action="/initInventoryStatusReport" type="com.ibm.dp.beans.DPInventoryStatusFormBean" >
	<html:hidden property="methodName" value="getReportDataExcel"/>
	
	<TR valign="top">
		<TD width="167" align="left" valign="top"	height="100%"><jsp:include
				page="../common/leftHeader.jsp" flush="" /></TD>
		<td>
			<TABLE width="570" border="0" cellpadding="5" cellspacing="0"
						class="text">
						<TR>
							<TD colspan="2" class="text"><BR>
							<IMG src="<%=request.getContextPath()%>/images/stockdetailreport.JPG"
								width="505" height="22" alt=""></TD>
				
					</TR>
					<TR>
					<TD align="left" height="41" width="156">
						<b class="text pLeft15">Role<STRONG><FONT color="RED">*</FONT></STRONG></b>
					</td>
					<td height="41" width="414"><b>:</b>&nbsp;&nbsp;
						<html:select property="levelId" name="DPInventoryStatusFormBean" onchange="javascript:getAccountName();">
						<html:option value="0">- Select A Role -</html:option>
						<logic:notEmpty	property="productStatusList" name="DPInventoryStatusFormBean">
  						    <html:options collection="productStatusList" labelProperty="levelName" property="levelId" />
						</logic:notEmpty>
						</html:select>
					 
					 </TD>
				</TR>
			
				<TR>
					<TD align="left" height="46" width="156">
						<b class="text pLeft15">Account<STRONG><FONT color="RED">*</FONT></STRONG></b>
					</td>
					<TD height="46" width="414"><b>:</b>&nbsp;&nbsp;					   
						<html:select property="accountId" onchange="" >
							<html:option value="0">- Select Account -</html:option>
							<logic:notEmpty property="productStatusList" name="DPInventoryStatusFormBean">
							<option value="0">All</option>
						<html:options labelProperty="accountName" property="accountId"	collection="productStatusList" />
						</logic:notEmpty>
						</html:select>
					</TD>
				</TR>
			
				<tr>
					<td>
					</td>
					<td align="left" height="54" width="156">
						<html:button property="excelBtn" value="Export To Excel" onclick="exportToExcel();"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					
				</tr>
	 
			</table>
		</td>
		</TR>
		</html:form>
		<TR align="center" bgcolor="4477bb" valign="top">
		<TD colspan="2" height="17" align="center"><jsp:include
			page="../common/footer.jsp" flush="" /></TD>
	</TR>
				
</TABLE>

</BODY>
</html:html>