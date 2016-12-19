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
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/scripts/Cal.js"></SCRIPT>
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/scripts/JScriptLib.js"></SCRIPT>
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
		var distributorId = document.getElementById("distAccId").value;
		var circle  = 	document.getElementById("circleid").value;
		var accountId  = 	document.getElementById("accountId").value;
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
		if(distributorId == null || distributorId == "")
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
    		 alert('To date can not be greater than todays Date');
  		  	 return false;
    	}
    	
    	if(daysBetween(startDate,currentDate) > 90)
    	{
    		 alert('From date can not be more than 90 days old from today');
  		  	 return false;
    	}
    	
    	if(daysBetween(startDate,endDate) >= 30)
		{
		  alert('Please select dates having only max 30 days difference');
		  return false;
		}
	    
	    
	    
	    
		var url = "consumPostDetReport.do?methodName=getReportDataExcel&distributorId="+distributorId+"&fromDate="+fromDate+"&toDate="+toDate;
		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();
	}
	
	function getTsmName() 
	{
	    var levelId = document.getElementById("circleID").value;

	    if(levelId!=''){
			var url = "consumPostDetReport.do?methodName=initTsmReport&levelId="+levelId;
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
			var url = "consumPostDetReport.do?methodName=initDistReport&parentId="+levelId+"&circleId="+circleId;
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
	
	</SCRIPT>

</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" >

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%" 	height="100%" valign="top">
	
	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include page="../common/dpheader.jsp" flush="" /></TD>
	</TR>
	
	<html:form name="ConsumptionPostingDetailReportBeans" action="/consumPostDetReport" type="com.ibm.dp.beans.ConsumptionPostingDetailReportBeans" >
	
	
	<TR valign="top">
		<TD width="167" align="left" valign="top"	height="100%"><jsp:include
				page="../common/leftHeader.jsp" flush="" /></TD>
		<td>
			<TABLE width="570" border="0" cellpadding="5" cellspacing="0"
						class="text">
						<TR>
							<TD colspan="2" class="text"><BR>
							<IMG src="<%=request.getContextPath()%>/images/ConsumPostReprt.jpg"
								width="505" height="30" alt=""></TD>
				
					</TR>
					
					<TR>
					<TD align="left" height="46" width="156">
						<b class="text pLeft15">Select Circle <FONT color="RED">*</FONT></b>
					</td>
					<td height="46" width="455"><b>:</b>&nbsp;&nbsp;
						<logic:match value="A" property="showCircle" name="ConsumptionPostingDetailReportBeans">		
						<html:select styleId="circleid" property="circleid"
							name="ConsumptionPostingDetailReportBeans" style="width:150px"
							style="height:20px" onchange="javascript:getTsmName();">
							<html:option value="">
								<bean:message bundle="view" key="product.selectcircle" />
							</html:option>
							<logic:notEmpty name="ConsumptionPostingDetailReportBeans" property="arrCIList">
								<html:optionsCollection name="ConsumptionPostingDetailReportBeans"	property="arrCIList" />
							</logic:notEmpty>
						</html:select>
						</logic:match>
						
						<logic:match value="N" property="showCircle" name="ConsumptionPostingDetailReportBeans">		
						<html:select styleId="circleid" property="circleid" name="ConsumptionPostingDetailReportBeans" style="width:150px"
							style="height:20px" disabled="true"  onchange="javascript:getTsmName();">
							<html:option value="">
								<bean:message bundle="view" key="product.selectcircle" />
							</html:option>
							<logic:notEmpty name="ConsumptionPostingDetailReportBeans"
								property="arrCIList">
								<html:optionsCollection name="ConsumptionPostingDetailReportBeans"
									property="arrCIList" />
							</logic:notEmpty>
						</html:select>
						</logic:match>
					 
					 </TD>
				</TR>
			
			
				<TR>
					<TD align="left" height="46" width="156">
						<b class="text pLeft15">Select TSM <FONT color="RED">*</FONT></b>
					</td>
					<td height="46" width="455"><b>:</b>&nbsp;&nbsp;
						<html:select property="accountId" onchange="javascript:getDistName()" name="ConsumptionPostingDetailReportBeans">
							<html:option value="">- Select a TSM -</html:option>
							<logic:notEmpty property="tsmList" name="ConsumptionPostingDetailReportBeans">
							<option value="0">Select all TSMs</option>
						<html:optionsCollection name="ConsumptionPostingDetailReportBeans"	property="tsmList" label="accountName" value="accountId"/>
						</logic:notEmpty>
						</html:select>
					 </TD>
				</TR>
			
					<TR>
					<TD align="left" height="46" width="156">
						<b class="text pLeft15">Select Distributor <FONT color="RED">*</FONT></b>
					</td>
					<td height="46" width="455"><b>:</b>&nbsp;&nbsp;
						<html:select property="distAccId" name="ConsumptionPostingDetailReportBeans">
							<html:option value="">- Select a Distributor -</html:option>
							<logic:notEmpty property="distList" name="ConsumptionPostingDetailReportBeans">
							<option value="0">Select all Distributors</option>
						<html:optionsCollection name="ConsumptionPostingDetailReportBeans"	property="distList" label="distAccName" value="distAccId"/>
						</logic:notEmpty>
						</html:select>
					 
					 </TD>
				</TR>
			
				
				<TR>
					<TD align="left" height="46" width="156"><b class="text pLeft15"><bean:message bundle="view"
						key="report.fromDate" /> 
						</b><FONT color="RED">*</FONT></TD>
						<TD height="46" width="155"><FONT color="#003399"> <b>:</b>&nbsp;&nbsp;<html:text
						property="fromDate" styleClass="box" styleId="fromDate" readonly="true" 
						size="15" maxlength="10" name="ConsumptionPostingDetailReportBeans" />
						<script language="JavaScript">
						new tcal ({
							// form name
							'formname': 'ConsumptionPostingDetailReportBeans',
							// input name
							'controlname': 'fromDate'
						});
					
						</script>
					</FONT></TD>
				</tr>
					<tr>	
					<TD align="left" height="46" width="156"><b class="text pLeft15"><bean:message bundle="view"
						key="report.toDate" /> <FONT color="RED">*</FONT> </b></TD>
						<TD width="155"><FONT color="#003399"><b>:</b> <html:text
						property="toDate" styleClass="box" styleId="toDate" readonly="true"
						size="15"  maxlength="10" name="ConsumptionPostingDetailReportBeans" />
						<script language="JavaScript">
						new tcal ({
							// form name
							'formname': 'ConsumptionPostingDetailReportBeans',
							// input name
							'controlname': 'toDate'
						});
					
						</script>
	
						 </FONT>
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