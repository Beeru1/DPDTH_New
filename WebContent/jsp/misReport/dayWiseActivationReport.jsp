<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>

<%try{ %>   
<%@page import="com.ibm.dp.common.Constants"%>
<html>
<head>
<link rel="stylesheet" href="../../theme/Master.css" type="text/css">
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT language="JavaScript" 
  src="<%=request.getContextPath()%>/scripts/subtract.js"
	type="text/javascript"></SCRIPT>
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/scripts/Cal.js"></SCRIPT>

<SCRIPT language="javascript" src="<%=request.getContextPath()%>/scripts/DropDownAjax.js" type="text/javascript">
</SCRIPT>
<SCRIPT language="javascript" src="<%=request.getContextPath()%>/scripts/Ajax.js" type="text/javascript">
</SCRIPT>
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/scripts/validation.js" type="text/javascript">
</SCRIPT>
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/scripts/Cal.js"></SCRIPT>
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/jScripts/progressBar.js" type="text/javascript">
</SCRIPT>
<SCRIPT language="javascript" type="text/javascript">
function selectedCount(ctrl){
	var count = 0;
	for(i=1; i < ctrl.length; i++){
		if(ctrl[i].selected){
			count++;
		}
	}
	return count;
}

	function selectAllCircles(){
		
		var ctrl = document.getElementById("circleId");
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		
		//alert(document.forms[0].circleId[0].value);
		if (document.getElementById("circleId")[0].selected && document.getElementById("circleId")[0].value =="0") {
			for (var i=1; i < document.getElementById("circleId").length; i++){
  	 			document.getElementById("circleId")[i].selected =true;
     		}
   		}
   		
   		
   		
   	
}


    function validate()
    
         {
	        if(document.getElementById("circleId").value ==""){
			alert("Please Select atleast one Circle");
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
	    	
	    	if(daysBetween(startDate,currentDate) > 365)
	    	{
	    		 alert('From date can not be more than 365 days old from today');
	  		  	 return false;
	    	}
	    	
	    	if(daysBetween(startDate,endDate) > 31)
			{
			  alert('Please select dates having only max 31 days difference');
			  return false;
			}
			
	   var circleId = document.getElementById("circleId").value;
	   var selectedCircleValues ="";
		selectedCircleValues =0;
			
			for (var i=0; i < document.getElementById("circleId").length; i++)
		  	 {
		   		 if (document.getElementById("circleId")[i].selected)
		     	 {
		     		if(selectedCircleValues !=""){
						selectedCircleValues += ",";
		     		}
			     	var selectedValCircle = document.getElementById("circleId")[i].value.split(",");
			     	selectedCircleValues += selectedValCircle[0];
		     	 }
		   	}
	   
	   	 	document.getElementById("hiddenCircleSelecIds").value =selectedCircleValues;
			
			
			setInterval(getReportDownloadStatus,1000);
			document.forms[0].action = "dayWiseActivationSummaryReportAction.do?methodName=exportExcel";
			document.forms[0].submit();
			
}


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
	

function setSelectedCircles() 
{

	 
		var circleId = document.getElementById("circleId").value;
		var selectedCircleValues ="";
		
	if(circleId ==""){
		alert("Please Select atleast one Circle");
		return false;
	 }
	 if(document.forms[0].circleId.length ==1){
			alert("No Circle exists");
		return false;
	   }
 	else{
	 	if (document.forms[0].circleId[0].selected){
			for (var i=1; i < document.forms[0].circleId.length; i++)
  	 		{
   		
     			if(selectedCircleValues !=""){
					selectedCircleValues += ",";
     			}
     		var selectedValCircle = document.forms[0].circleId[i].value.split(",");
     		selectedCircleValues += selectedValCircle[0];
   			}
		}	
		else{
			for (var i=1; i < document.forms[0].circleId.length; i++)
	  	 {
	   		 if (document.forms[0].circleId[i].selected)
	     	 {
	     		if(selectedCircleValues !=""){
					selectedCircleValues += ",";
	     		}
	     	var selectedValCircle = document.forms[0].circleId[i].value.split(",");
	     	selectedCircleValues += selectedValCircle[0];
	     	 }
	   	}
   }
   	 	document.getElementById("hiddenCircleSelecIds").value =selectedCircleValues;
   	 	alert(document.getElementById("hiddenCircleSelecIds").value);
   		 //getAllAccountsMultipleCircles(accountLevel,selectedCircleValues,'tsmId');
 }
		
}



function selectedCount(ctrl){
	var count = 0;
	for(i=1; i < ctrl.length; i++){
		if(ctrl[i].selected){
			count++;
		}
	}
	return count;
}



function selectAllCircle(){
		var ctrl = document.forms[0].circleId;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		if(selCount==ctrLength-1){
				ctrl[0].selected=true;
		}
		if(selCount==ctrLength-2){
				ctrl[0].selected=false;
		}
		
		
		if (document.forms[0].circleId[0].selected){
			for (var i=1; i < document.forms[0].circleId.length; i++){
  	 			document.forms[0].circleId[i].selected =true;
     		}
   		}
   	
}



</SCRIPT>

</HEAD>

<BODY background="${pageContext.request.contextPath}/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onload="">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%" 	height="100%" valign="top">
	
	
	
	<html:form name="DayWiseActivationSummaryReportBean" action="/dayWiseActivationSummaryReportAction.do" type="com.ibm.dp.beans.DayWiseActivationSummaryReportBean"  method="post"  >
	
	<html:hidden property="methodName" styleId="methodName" />
	<html:hidden property="hiddenCircleSelecIds" name="DayWiseActivationSummaryReportBean" />
	
	
	<TR valign="top">
		
		<td>
			<TABLE width="800" border="0" cellpadding="5" cellspacing="0" class="text"  align="top">
				<TR>
					<TD colspan="4" valign="bottom"  class="dpReportTitle">
								Day Wise Activation Report
					</TD>
		
				</TR>
			<TR>
				
					<TD align="center"><b class="text pLeft15"> <bean:message
						bundle="view" key="productcreate.circlelist" /><font color="red">*</font></b>
					</td>
				<td height="46">	
							<logic:equal value="5" property="accountLevel" name="DayWiseActivationSummaryReportBean">	
								<html:select  multiple="true"  property="circleId"  styleId="circleId" style="width:250px; height:80px;"  size="6" >
									<logic:notEmpty name="DayWiseActivationSummaryReportBean" property="circleList">
									 	<html:optionsCollection name="DayWiseActivationSummaryReportBean"	property="circleList" label="strText" value="strValue"/> 
									</logic:notEmpty>
								</html:select> 
							</logic:equal>
							<logic:notEqual value="5" property="accountLevel" name="DayWiseActivationSummaryReportBean">	
								<html:select  multiple="true"  property="circleId" styleId="circleId" style="width:250px; height:80px;"  onchange="selectAllCircles();" size="6" >
									<logic:notEmpty name="DayWiseActivationSummaryReportBean" property="circleList">
									 	<html:optionsCollection name="DayWiseActivationSummaryReportBean"	property="circleList" label="strText" value="strValue"/> 
									</logic:notEmpty>
								</html:select>
							</logic:notEqual>
						</TD>  
						
						
						
						
						
						
						
						<!-- 
						<html:select  multiple="true"  property="circleId" styleId="circleId" style="width:150px;" size="6" onchange="setSelectedCircles();selectAllCircle();">
							<logic:notEmpty name="DayWiseActivationSummaryReportBean" property="circleList">
								 <html:optionsCollection name="DayWiseActivationSummaryReportBean"	property="circleList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
					
						 -->
						<TD colspan="2"></TD>
			
				</TR>
			     <TR>
			     <TD align="center"><b class="text pLeft15"> <bean:message
						bundle="view" key="productcreate.fromDate" /><font color="red">*</font></b>
					</td>
			     <td>
				<html:text 
						property="fromDate" styleClass="box" styleId="fromDate" readonly="true" 
						 size="38"  maxlength="15" name="DayWiseActivationSummaryReportBean" />
						 
						 <script language="JavaScript">
						new tcal ({
							// form name
							'formname': 'DayWiseActivationSummaryReportBean',
							// input name
							'controlname': 'fromDate'
						});
					
						</script>
				</td>
				
				</TR>
				
				 <TR>
			     <TD align="center"><b class="text pLeft15"> <bean:message
						bundle="view" key="productcreate.toDate" /><font color="red">*</font></b>
					</td>
			     <td>
				<html:text 
						property="toDate" styleClass="box" styleId="toDate" readonly="true" 
						 size="38"  maxlength="15" name="DayWiseActivationSummaryReportBean" />
						 
						 <script language="JavaScript">
						new tcal ({
							// form name
							'formname': 'DayWiseActivationSummaryReportBean',
							// input name
							'controlname': 'toDate'
						});
					
						</script>
				
				
				</TR>
				
				<tr align="center" height="100">
				
				<td colspan="2">
						<H1><html:button property="submitBtn" value="Submit"  onclick="return validate()"></html:button></H1>&nbsp;&nbsp;&nbsp;&nbsp;
					</tr>
				
				
				<%--				
				<TR id="calId">
					<TD align="center"><b class="text pLeft15"><bean:message bundle="view"
						key="report.fromDate" /><STRONG><FONT color="RED">*</FONT></STRONG></b></TD>
						<TD width="155"><FONT color="#003399"> <html:text
						property="fromDate" styleClass="box" styleId="fromDate" readonly="true" 
						size="15" maxlength="10" name="CircleActivationSummaryReportBean" />
						<script language="JavaScript">
						new tcal ({
							// form name
							'formname': 'CircleActivationSummaryReportBean',
							// input name
							'controlname': 'fromDate'
						});
					
						</script>
					</FONT></TD>
			
					<TD align="center"><b class="text pLeft15"><bean:message bundle="view"
						key="report.toDate" /><STRONG><FONT color="RED">*</FONT></STRONG></b></TD>
						<TD width="155"><FONT color="#003399"> <html:text
						property="toDate" styleClass="box" styleId="toDate" readonly="true"
						size="15"  maxlength="10" name="CircleActivationSummaryReportBean" />
						<script language="JavaScript">
						new tcal ({
							// form name
							'formname': 'CircleActivationSummaryReportBean',
							// input name
							'controlname': 'toDate'
						});
					
						</script>
	
						 </FONT>
					</TD>
					
					
				</TR>
				--%>
			
				

					
						
						
					
						
				<!-- Added by Neetika for BFR 16 on 13aug 2013 Release 3 -->
				<TR>
					<TD  align="center" colspan=4>
					<p style="visibility:hidden;" id="progress">
					<img id="progress_image" style="visibility:hidden;padding-left:55px;padding-top:500px;width:30;height:34" src="${pageContext.request.contextPath}/images/ajax-loader.gif" /> 
					</p>
					</TD>
				</TR>
			</table>
		</td>
		</TR>
				
				<!-- <tr>
					<td colspan="2" align="center">
						<html:button property="excelBtn" value="Export To Excel" onclick="return exportToExcel();"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;
					</td>       
				</tr> --> 
	            
			</table>
		
		</html:form>
		
	
	

<input type="hidden" name="reportDownloadStatus" value=""> <!-- Neetika BFR 16 14Aug -->
<script type="text/javascript">
/*Method Added by Amrit for BFR 16 of Release 3*/
	function getReportDownloadStatus() {
	var url= "dayWiseActivationSummaryReportAction.do?methodName=getReportDownloadStatus";
	var elementId = document.getElementById("reportDownloadStatus");
	var txt = true;
	makeAjaxRequest(url,"reportDownloadStatus",txt);
	//alert("get report .."+document.getElementById("reportDownloadStatus").value);
	if(document.getElementById("reportDownloadStatus").value != null && document.getElementById("reportDownloadStatus").value == 'inprogress') 
	{
	document.getElementById("submitBtn").disabled=true;
	
    loadSubmit();
    }
    else
    {
    document.getElementById("submitBtn").disabled=false;
    finishSubmit();
	}
	//alert("value:" + document.getElementById("reportDownloadStatus").value);
	}
	</SCRIPT>
</BODY>
</html>

<%}catch(Exception e){
e.printStackTrace();
}%>