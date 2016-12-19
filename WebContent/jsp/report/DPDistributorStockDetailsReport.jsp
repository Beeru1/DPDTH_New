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

<SCRIPT language="javascript" type="text/javascript"> 

	function daysBetween(date1, date2) 
	{
	    var DSTAdjust = 0;
	    // constants used for our calculations below
	    oneMinute = 1000 * 60;
	    var oneDay = oneMinute * 60 * 24;
	    // equalize times in case date objects have them
	    date1.setHours(0);
	    date1.setMinutes(0);
	    date1.setSeconds(0);
	    date2.setHours(0);
	    date2.setMinutes(0);
	    date2.setSeconds(0);
	    // take care of spans across Daylight Saving Time changes
	    if (date2 > date1) {
	        DSTAdjust = (date2.getTimezoneOffset() - date1.getTimezoneOffset()) * oneMinute;
	    } else {
	        DSTAdjust = (date1.getTimezoneOffset() - date2.getTimezoneOffset()) * oneMinute;    
	    }
	    var diff = Math.abs(date2.getTime() - date1.getTime()) - DSTAdjust;
	    return Math.ceil(diff/oneDay);
	}
	
	function exportToExcel()
	{
	
		var circle  = 	document.getElementById("circleid").value;
		var accountId  = 	document.getElementById("accountId").value;
		
		if(circle == null || circle == "")
		{
			alert("Please Select Circle");
			return false;
		}
		if(accountId == null || accountId == "")
		{
			alert("Please Select Distributor");
			return false;
		}
		
		var fromDate = document.getElementById("fromDate").value;
		if ((fromDate==null)||(fromDate=="0") || fromDate==""){
			alert('From Date is Required');
			return false;
		}
		
		var toDate = document.getElementById("toDate").value;
	    if ((toDate==null)||(toDate=="0") || toDate==""){
			alert('To Date is Required');
			return false;
		}
		

		var startDate = new Date(fromDate);
    	var endDate = new Date(toDate);
    	var currentDate = new Date();
    	
    	if(startDate > endDate)
    	{
    		 alert('From date can not be greater than To Date');
  		  	 return false;
    	}
    	if(endDate > currentDate)
    	{
    		 alert('To date can not be greater than Today Date');
  		  	 return false;
    	}
    	
    	if(daysBetween(startDate,currentDate) > 90)
    	{
    		 alert('From date can not be more than 90 days old from today');
  		  	 return false;
    	}
    	
    	if(daysBetween(startDate,endDate) >= 30)
		{
		  alert('Please Select dates having only max 30 days difference.');
		  return false;
		}
	    
		var url = "distributorStockReport.do?methodName=getDistributorStockReportExcel&fromDate="+fromDate+"&toDate="+toDate+"&circleId="+circle+"&accountId="+accountId;
		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();
	}
	
	function getAccountName()
	{
	 
	    var levelId = document.getElementById("circleID").value;
//	    alert('Level ::::' + levelId);
		var url = "distributorStockReport.do?methodName=initStatusAccount&levelId="+levelId;
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
			selectObj.options[selectObj.options.length] = new Option("Select Account","");
			selectObj.options[selectObj.options.length] = new Option("All","0");
			for(var i=0; i<optionValues.length; i++)
			{
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			}
		}
	}
	
	
	
	
	</SCRIPT>

</HEAD>

<BODY background="${pageContext.request.contextPath}/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" >
<%try{ %>
<TABLE cellspacing="0" cellpadding="0" border="0" width="100%" 	height="100%" valign="top">
	
	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include page="../common/dpheader.jsp" flush="" /></TD>
	</TR>
	
	<html:form name="DPStockDetailsReportBean" action="/initExceptionReport" type="com.ibm.dp.beans.DPStockDetailsReportBean" >
	<html:hidden property="methodName" value="getExceptionConsumedReportDataExcel"/>
	
	<TR valign="top">
		<TD width="167" align="left" valign="top"	height="100%"><jsp:include
				page="../common/leftHeader.jsp" flush="" /></TD>
		<td>
			<TABLE width="570" border="0" cellpadding="5" cellspacing="0" class="text">
				<TR>
					<TD colspan="2" class="text"><BR>
					<IMG src="${pageContext.request.contextPath}/images/distStockDetailReport.jpg"
						width="410" height="24" alt=""></TD>
		
				</TR>
				
				<TR>
					<TD align="center"><b class="text pLeft15"> <bean:message
						bundle="view" key="productcreate.circlelist" /><font color="red">*</font></b>
					</td>
				<td>	
					<logic:match value="A" property="showCircle" name="DPStockDetailsReportBean">		
						<html:select styleId="circleid" property="circleid"
							name="DPStockDetailsReportBean" style="width:150px"
							style="height:20px" onchange="getAccountName()">
							<html:option value="">
								<bean:message bundle="view" key="product.selectcircle" />
							</html:option>
							<html:option value="0">--All--</html:option>
							<logic:notEmpty name="DPStockDetailsReportBean" property="arrCIList">
								<html:optionsCollection name="DPStockDetailsReportBean"	property="arrCIList" />
							</logic:notEmpty>
						</html:select>
						</logic:match>
						
						<logic:match value="N" property="showCircle" name="DPStockDetailsReportBean">		
						<html:select styleId="circleid" property="circleid" name="DPStockDetailsReportBean" style="width:150px"
							style="height:20px" disabled="true"  onchange="javascript:getAccountName();">
							<html:option value="">
								<bean:message bundle="view" key="product.selectcircle" />
							</html:option>
							<logic:notEmpty name="DPStockDetailsReportBean"
								property="arrCIList">
								<html:optionsCollection name="DPStockDetailsReportBean"
									property="arrCIList" />
							</logic:notEmpty>
						</html:select>
						</logic:match>
					</TD>
				</TR>
								
				<TR>
					<TD align="center" height="46" width="156">
						<b class="text pLeft15">Disributor<font color="red">*</font></b>
					</td>
					<TD >				   
						<html:select property="accountId" onchange="" >
							<html:option value="">- Select Account -</html:option>
							<logic:notEmpty property="productStatusList" name="DPStockDetailsReportBean">
							<option value="0">All</option>
						<html:options labelProperty="accountName" property="accountId"	collection="productStatusList" />
						</logic:notEmpty>
						</html:select>
					</TD>
				</TR>
								
				<TR>
					<TD align="center"><b class="text pLeft15"><bean:message bundle="view"
						key="report.fromDate" /><STRONG><FONT color="RED">*</FONT></STRONG></TD>
						<TD width="155"><FONT color="#003399"> <html:text
						property="fromDate" styleClass="box" styleId="fromDate" readonly="true" 
						size="15" maxlength="10" name="DPStockDetailsReportBean" />
						<script language="JavaScript">
new tcal ({
// form name
'formname': 'DPStockDetailsReportBean',
// input name
'controlname': 'fromDate'
});

</script>
					</FONT></TD>
				</tr>
				<tr>	
					<TD align="center"><b class="text pLeft15"><bean:message bundle="view"
						key="report.toDate" /><STRONG><FONT color="RED">*</FONT></STRONG></TD>
						<TD width="155"><FONT color="#003399"> <html:text
						property="toDate" styleClass="box" styleId="toDate" readonly="true"
						size="15"  maxlength="10" name="DPStockDetailsReportBean" />
						<script language="JavaScript">
new tcal ({
// form name
'formname': 'DPStockDetailsReportBean',
// input name
'controlname': 'toDate'
});

</script> </FONT>
					</TD>
				</TR>
				
				<tr>
					<td colspan="2" align="center">
						<html:button property="excelBtn" value="Export To Excel" onclick="exportToExcel();"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
	 
	 <logic:match value="A" property="showCircle" name="DPStockDetailsReportBean">
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
				
</TABLE>
<%}catch(Exception ex){ex.printStackTrace();} %>>
</BODY>
</html:html>