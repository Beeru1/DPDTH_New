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
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/Cal.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
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
	
	  
		/* var fromDate = document.getElementById("fromDate").value;
		
		if ((fromDate=="")|| /^\s+$/.test(fromDate)){
		alert('From Date is Required');
		return false;
		}
	
		//alert('From Date :' + fromDate);

				
		var startDate = new Date(fromDate);
    	var endDate = new Date(toDate);
    	var now = new Date();
    	
    	if(daysBetween(startDate,now) > 90)
		{
		  alert('Please select dates having only max 90 days difference from current date.');
		  return false;
		}
	    
	    
	    
	    var toDate = document.getElementById("toDate").value;
	  if ((toDate=="")|| /^\s+$/.test(toDate)){
			alert('To Date is Required');
			return false;
		}
		//alert('To Date :' + toDate);
	    
	    
	    
    	
    	//alert('Days Between :' + daysBetween(startDate,endDate));
		if(daysBetween(startDate,endDate) >= 30)
		{
		  alert('Please select dates having only max 30 days difference.');
		  return false;
		}
		
		
		if(daysBetween(now,endDate) > 0)
		{
		  alert('Please select dates less then current date.');
		  return false;
		} */
	    
		var url = "initOpenStockDepleteReport.do?methodName=getOpenStockDepleteReportDataExcel";
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
	
	<html:form name="DPOpenStockDepleteReportBean" action="/initOpenStockDepleteReport" type="com.ibm.dp.beans.DPOpenStockDepleteReportBean" >
	<html:hidden property="methodName" value="getOpenStockDepleteReportDataExcel"/>
	
	<TR valign="top">
		<TD width="167" align="left" valign="top"	height="100%"><jsp:include
				page="../common/leftHeader.jsp" flush="" /></TD>
		<td>
			<TABLE width="570" border="0" cellpadding="5" cellspacing="0" class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/Opening Stock Depletion report.jpg"
						width="410" height="21" alt=""></TD>
		
				</TR>
				<!--<TR>
					<TD ><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="report.fromDate" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
						<TD width="464"><FONT color="#003399"> <html:text
						property="fromDate" styleClass="box" styleId="fromDate" readonly="true" 
						size="15" maxlength="10" name="DPOpenStockDepleteReportBean" />
						<script language="JavaScript">
new tcal ({
// form name
'formname': 'DPOpenStockDepleteReportBean',
// input name
'controlname': 'fromDate'
});

</script>
					</FONT> </TD>
					
					</tr>
					<tr>
					<TD ><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="report.toDate" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
						<TD width="464"><FONT color="#003399"> <html:text
						property="toDate" styleClass="box" styleId="toDate" readonly="true"
						size="15"  maxlength="10" name="DPOpenStockDepleteReportBean" />
						<script language="JavaScript">
new tcal ({
// form name
'formname': 'DPOpenStockDepleteReportBean',
// input name
'controlname': 'toDate'
});

</script> </FONT>
					</TD>
				</TR>
			
				-->
				<tr>
					<td colspan="4" align="left">
						<html:button property="excelBtn" value="Export To Excel" onclick="exportToExcel();"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
	  <tr>
         <td colspan="3"><center><span class="error"><html:errors /></span></center></td>
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