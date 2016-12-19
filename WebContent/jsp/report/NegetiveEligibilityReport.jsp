<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@page import="com.ibm.dp.common.Constants"%>
<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="${pageContext.request.contextPath}/theme/text.css"
	rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/chromestyle2.css" />
<SCRIPT type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/chrome.js"></SCRIPT>
<SCRIPT type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/JScriptLib.js"></SCRIPT>
<SCRIPT type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/validation.js"></SCRIPT>
<script language="javascript" src="scripts/cal2.js">
</script>
<script language="javascript" src="scripts/cal_conf2.js">
</script>
<script language="javascript" src="scripts/cal_conf.js">
</script>
<SCRIPT type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/validation.js"></SCRIPT>

<SCRIPT language="javascript"
	src="<%=request.getContextPath()%>/scripts/DropDownAjax.js"
	type="text/javascript">
</SCRIPT>
<SCRIPT language="javascript"
	src="<%=request.getContextPath()%>/scripts/Ajax.js"
	type="text/javascript">
</SCRIPT>
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/jScripts/progressBar.js" type="text/javascript">
</SCRIPT>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>

<SCRIPT language="javascript" type="text/javascript"><!-- 

	
	function exportToExcel(form)
	{
			
		var circleid = document.getElementById("circleid").value;
		if(circleid =="" )
		{
		 alert('Please select any Circle.');
		 return false;		
		}		
		
	    var url = "NegetiveEligibilityReport.do?methodName=getNegetiveEligibilityExcel";
		document.forms[0].action=url;
		document.forms[0].method="post";
		setInterval(getReportDownloadStatus,1000);
		document.forms[0].submit();
	}

	
function selectAllCircles(){
		var ctrl = document.forms[0].circleid;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		if(selCount==ctrLength-1){
				ctrl[0].selected=true;
		}
		if(selCount==ctrLength-2){
				ctrl[0].selected=false;
		}
		
		
		if (document.forms[0].circleid[0].selected){
			for (var i=1; i < document.forms[0].circleid.length; i++){
  	 			document.forms[0].circleid[i].selected =true;
     		}
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

function getCircleName() 
{		
 		var selectedCircleValues =0;
	 	if (document.forms[0].circleid[0].selected){
		   
			for (var i=1; i < document.forms[0].circleid.length; i++)
  	 		{
   		
     			if(selectedCircleValues !=""){
					selectedCircleValues += ",";
     			}
     		var selectedValCircle = document.forms[0].circleid[i].value.split(",");
     		selectedCircleValues += selectedValCircle[0];
   			}
		}	
		else{
			for (var i=1; i < document.forms[0].circleid.length; i++)
	  	 {
	   		 if (document.forms[0].circleid[i].selected)
	     	 {
	     		if(selectedCircleValues !=""){
					selectedCircleValues += ",";
	     		}
	     	var selectedValCircle = document.forms[0].circleid[i].value.split(",");
	     	selectedCircleValues += selectedValCircle[0];
	     	 }
	   	}
	   	
   }
   
  		 document.getElementById("hiddenCircleSelecIds").value =selectedCircleValues;   		

		
}
	--></SCRIPT>

</HEAD>

<BODY background="${pageContext.request.contextPath}/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">


	<html:form action="/NegetiveEligibilityReport">

	<html:hidden property="hiddenCircleSelecIds" name="NegetiveEligibilityReportBean"/>

		<html:hidden property="methodName"
			value="getNegetiveEligibilityExcel" />		


		<TR valign="top">

			<td>
			<TABLE width="570" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="2" class="text"><BR>
					<!-- Page title start -->
					
								<IMG src="${pageContext.request.contextPath}/images/Negetive_Eligibility_Report1.jpg"
									width="410" height="24" alt="">
					<!-- Page title end --></TD>

				</TR>
				<TR>
					<TD>&nbsp;</TD>
				</TR>

				<TR>
					<TD class="txt" align="center" width="20%"><strong><font
						color="#000000"><bean:message bundle="dp"
						key="label.hierarchy.circle" /></font><font color="red">*</font>: </strong></TD>
					<td><html:select multiple="true" property="circleid"
						styleId="circleid" style="width:150px;" size="5"
						onchange="getCircleName();selectAllCircles()">
						
						<logic:notEmpty name="NegetiveEligibilityReportBean"
							property="arrCIList">
							<html:optionsCollection name="NegetiveEligibilityReportBean"
								property="arrCIList" label="strText" value="strValue" />
						</logic:notEmpty>
					</html:select></TD>
					</TR>
					<TR><td></td>
					<TD align="left"><html:button property="excelBtn"
						value="Export To Excel" onclick="exportToExcel(this.form);" /></TD>
				</TR>
					<!-- Added by Neetika for BFR 16 on 13aug 2013 Release 3 -->
				<TR>
					<TD  align="center" colspan=4>
					<p style="visibility:hidden;" id="progress">
					<img id="progress_image" style="visibility:hidden;padding-left:55px;padding-top:500px;width:30;height:34" src="${pageContext.request.contextPath}/images/ajax-loader.gif" /> 
					</p>
					</TD>
				</TR>
			</Table>
	</html:form>
	<TR align="center" bgcolor="4477bb" valign="top">

	</TR>

</TABLE>
<input type="hidden" name="reportDownloadStatus" value="">
<script>
/*Method Added by Amrit for BFR 16 of Release 3*/
	function getReportDownloadStatus() {
	
	
	var url= "NegetiveEligibilityReport.do?methodName=getReportDownloadStatus";
	
	var elementId = document.getElementById("reportDownloadStatus");
	var txt = true;
	
	makeAjaxRequest(url,"reportDownloadStatus",txt);
	
	if(document.getElementById("reportDownloadStatus").value != null && document.getElementById("reportDownloadStatus").value == 'inprogress') 
    {
    document.getElementById("excelBtn").disabled=true;
    loadSubmit();
    }
    else
    {
    document.getElementById("excelBtn").disabled=false;
    finishSubmit();
    }

	}

</script>
</BODY>
</html:html>
