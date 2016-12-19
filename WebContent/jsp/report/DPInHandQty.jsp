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
<LINK href="${pageContext.request.contextPath}/theme/text.css" rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/theme/chromestyle2.css" />
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/scripts/chrome.js"></SCRIPT>
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/scripts/JScriptLib.js"></SCRIPT>
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/scripts/validation.js"></SCRIPT>
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/scripts/Cal.js"></SCRIPT>
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/scripts/validation.js"></SCRIPT>



<TITLE><bean:message bundle="view" key="application.title" /></TITLE>

<SCRIPT language="javascript" type="text/javascript"><!-- 


	function exportToExcel()
	{
	
		var circle  = 	document.getElementById("circleid").value;
		var accountId  = 	document.getElementById("accountId").value;
		var distAccId = document.getElementById("distAccId").value;
		
		if(circle == null || circle == "")
		{
			alert("Please Select Circle");
			return false;
		}
		if(accountId == null || accountId == "")
		{
			alert("Please Select TSM");
			return false;
		}
		if(distAccId == null || distAccId == "")
		{
			alert("Please Select Distributor");
			return false;
		}
		var url = "inHandQtyReport.do?methodName=getlastPOReportExcel&circleId="+circle+"&accountId="+accountId+"&distAccId="+distAccId;
		
		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();
	}
	
	function getTsmName() 
	{

	    var levelId = document.getElementById("circleID").value;

	    if(levelId!=''){
			var url = "inHandQtyReport.do?methodName=initTsmReport&levelId="+levelId;
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
			req.onreadystatechange = getTsmNameChange;
			req.open("POST",url,false);
			req.send();
		}else{
			resetValues(1);

		}
	}
	
	function resetValues(flag){
		var accountId = document.getElementById("accountId");
		var distAccId = document.getElementById("distAccId");
		if(flag==1){
			accountId.options.length = 0;
			accountId.options[accountId.options.length] = new Option("Select a TSM","");
			distAccId.options.length = 0;
			distAccId.options[distAccId.options.length] = new Option("Select a Distributor","");
		}
		if(flag==2){
			distAccId.options.length = 0;
			distAccId.options[distAccId.options.length] = new Option("Select a Distributor","");
		}
	}

	function getTsmNameChange()
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
			selectObj.options[selectObj.options.length] = new Option("Select a TSM","");
			selectObj.options[selectObj.options.length] = new Option("All","0");
			for(var i=0; i<optionValues.length; i++)
			{
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			}
		}
	}	
		
	function getDistName()
	{
	    var levelId = document.getElementById("accountId").value;
	    var circleId = document.getElementById("circleid").value;
	
	 	if(levelId!=''){
			var url = "inHandQtyReport.do?methodName=initDistReport&parentId="+levelId+"&circleId="+circleId;
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
			req.onreadystatechange = getDistNameChange;
			req.open("POST",url,false);
			req.send();
		}else{
			resetValues(2);
		}
	}

	function getDistNameChange()
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
			var selectObj = document.getElementById("distAccId");
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select a Distributor","");
			selectObj.options[selectObj.options.length] = new Option("All","0");
			for(var i=0; i<optionValues.length; i++)
			{
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			}
		}
	}
	
	
	
	
	--></SCRIPT>

</HEAD>

<BODY background="${pageContext.request.contextPath}/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onload="">
<%try{ %>
<TABLE cellspacing="0" cellpadding="0" border="0" width="100%" 	height="100%" valign="top">
	
	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include page="../common/dpheader.jsp" flush="" /></TD>
	</TR>
	
	<html:form name="DPReverseLogisticReportFormBean" action="/inHandQtyReport" type="com.ibm.dp.beans.DPReverseLogisticReportFormBean" >
	<html:hidden property="methodName" value="getlastPOReportExcel"/>
		<html:hidden property="reportType" value="ihq"/>
	<TR valign="top">
		<TD width="167" align="left" valign="top"	height="100%"><jsp:include
				page="../common/leftHeader.jsp" flush="" /></TD>
		<td align="left">
			<TABLE width="500" border="0" cellpadding="5" cellspacing="0" class="text" align="left">
				<TR>
					<TD colspan="2" class="text"><BR>
					<IMG src="${pageContext.request.contextPath}/images/inHandQty.jpg"
						width="410" height="24" alt=""></TD>
		
				</TR>
				<TR>
					<TD align="right" width="30%"><b class="text pLeft15"> <bean:message
						bundle="view" key="productcreate.circlelist" /><font color="red">*</font></b>
					</td>
				<td width="70%" align="left">	
					<logic:match value="A" property="showCircle" name="DPReverseLogisticReportFormBean">		
						<html:select styleId="circleid" property="circleid"
							name="DPReverseLogisticReportFormBean" style="width:150px"
							style="height:20px" onchange="javascript:getTsmName();">
							<html:option value="">
								<bean:message bundle="view" key="product.selectcircle" />
							</html:option>
							<logic:notEmpty name="DPReverseLogisticReportFormBean" property="arrCIList">
								<html:optionsCollection name="DPReverseLogisticReportFormBean"	property="arrCIList" />
							</logic:notEmpty>
						</html:select>
						</logic:match>
						
						<logic:match value="N" property="showCircle" name="DPReverseLogisticReportFormBean">		
						<html:select styleId="circleid" property="circleid" name="DPReverseLogisticReportFormBean" style="width:150px"
							style="height:20px" disabled="true"  onchange="javascript:getTsmName();">
							<html:option value="">
								<bean:message bundle="view" key="product.selectcircle" />
							</html:option>
							<logic:notEmpty name="DPReverseLogisticReportFormBean"
								property="arrCIList">
								<html:optionsCollection name="DPReverseLogisticReportFormBean"
									property="arrCIList" />
							</logic:notEmpty>
						</html:select>
						</logic:match>
					</TD>
				</TR>
				<tr height="10">
					<td colspan="2">&nbsp;</td>
				</tr>
				<TR>
					<TD align="right" width="30%">
						<b class="text pLeft15">TSM<font color="red">*</font></b>
					</td>
					<TD align="left" width="70%">				   
						<html:select property="accountId" onchange="javascript:getDistName()" name="DPReverseLogisticReportFormBean">
							<html:option value="">- Select a TSM -</html:option>
							<logic:notEmpty property="tsmList" name="DPReverseLogisticReportFormBean">
							<option value="0">Select all TSMs</option>
						<!--<html:options labelProperty="accountName" property="accountId"	collection="tsmList" /> -->
						<html:optionsCollection name="DPReverseLogisticReportFormBean"	property="tsmList" label="accountName" value="accountId"/>
						</logic:notEmpty>
						</html:select>
					</TD>
				</TR>
					<tr height="10">
					<td colspan="2">&nbsp;</td>
				</tr>			
				<TR>
					<TD align="right" width="30%">
						<b class="text pLeft15">Disributor<font color="red">*</font></b>
					</td>
					<TD align="left" width="70%">				   
						<html:select property="distAccId" name="DPReverseLogisticReportFormBean">
							<html:option value="">- Select a Distributor -</html:option>
							<logic:notEmpty property="distList" name="DPReverseLogisticReportFormBean">
							<option value="0">Select all Distributors</option>
						<html:optionsCollection name="DPReverseLogisticReportFormBean"	property="distList" label="distAccName" value="distAccId"/>
						</logic:notEmpty>
						</html:select>
					</TD>
				</TR>
				<tr height="10">
					<td colspan="2">&nbsp;</td>
				</tr>		
				<!--<TR>
						<TD align="right" width="30%">
						<b class="text pLeft15">Product<font color="red">*</font></b>
					</td>
						<TD align="left" width="70%">	
						<html:select property="selectedProductId" styleId="selectedProductId" style="width:150px" >
				<html:option value="0">--All Products---</html:option>
				<logic:notEmpty property="productList" name="DPReverseLogisticReportFormBean"><html:options labelProperty="productName" property="productId"
						collection="productList" />
				</logic:notEmpty>
			</html:select></td></tr>
						
								
				--><TR>
					<tr height="10">
					<td colspan="2">&nbsp;</td>
				</tr>			
				<tr>
					<td width="20%">&nbsp;</td>
					<td align="left" width="80%">
						<html:button property="excelBtn" value="Export To Excel" onclick="return exportToExcel();"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
	 
	 <logic:match value="A" property="showCircle" name="DPReverseLogisticReportFormBean">
		 <script>
		 	document.getElementById("circleid").value = "";
		 </script>
	 </logic:match>
			</table>
		</td>
		</TR>
		</html:form>
		<TR align="center" bgcolor="4477bb" valign="top">
		<TD colspan="2" height="17" align="center"><jsp:include
			page="../common/footer.jsp" flush="" /></TD>
	</TR>
	<logic:equal value="N" name="DPReverseLogisticReportFormBean" property="showTSM">
		<script>
		 		document.getElementById("accountId").disabled = "true";
		 		document.getElementById("distAccId").disabled = "true";
		 </script>	
	</logic:equal>
	
</TABLE>
<%}catch(Exception ex){ex.printStackTrace();} %>
</BODY>
</html:html>