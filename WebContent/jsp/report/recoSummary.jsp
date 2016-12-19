<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.ibm.dp.common.Constants" %>
<html>
<HEAD>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<link href="${pageContext.request.contextPath}/theme/text_new.css" rel="stylesheet" type="text/css">
<LINK href="<%=request.getContextPath()%>/theme/Master.css"
	rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>


<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<script type='text/javascript'
	src='${pageContext.request.contextPath}/jScripts/jQuery/jquery-1.6.2.min.js'></script>
<script type='text/javascript'
	src='${pageContext.request.contextPath}/jScripts/jQuery/jquery-ui-1.8.17.min.js'></script>
<script language="javascript" src="scripts/cal.js">
</script>
<script language="javascript" src="scripts/cal_conf.js">
</script>
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/jScripts/progressBar.js" type="text/javascript">
</SCRIPT>
<SCRIPT language="javascript" src="<%=request.getContextPath()%>/scripts/Ajax.js" type="text/javascript">
</SCRIPT>
<script language="JavaScript">

function disableSelected(){
var groupId = document.getElementById("groupId").value;
if(groupId==7){
	document.getElementById("circleid").multiple=false;
	document.getElementById("circleid").size=1;
	document.getElementById("circleid").disabled=true;
	var circleid = document.getElementById("circleid").value;
		
	}
if(groupId==6){
	document.getElementById("circleid").multiple=false;
	document.getElementById("circleid").disabled=true;
	
	}
//if(groupId==3 || groupId==4 || groupId==5){
if(groupId==4 || groupId==5){
	document.getElementById("circleid").multiple=false;
	document.getElementById("circleid").disabled=true;
	}
}

function submitForm(form) {

	if(document.getElementById("circleid").value ==""){
				alert("Please Select atleast one Circle");
				return false;
	   		}
		
		

		populateCircleIdArray(form);
		{
			document.forms[0].action="recoSummaryPaginationAction.do"; 
			    
			document.forms[0].submit();
		}
}
function exportToExcel(form)
	{
		var circleid = document.getElementById("circleid").value;
		var selectedProductId = document.getElementById("selectedProductId").value;
		var selectedProductIdList = document.getElementById("selectedProductId");	
		var SelBranchVal = ""; 
		populateCircleIdArray(form);
		
	     var url = "recoSummaryPaginationAction.do";
		document.forms[0].action=url;
		document.forms[0].method="post";
		setInterval(getReportDownloadStatus,1000);   
		document.forms[0].submit();
	}
	
	
var arSelected = new Array(); 

function getMultiple(ob) 
{ 
	//alert(ob);
	//alert(ob.selectedIndex)
	while (ob.selectedIndex != -1) 
	{ 
		if (ob.selectedIndex != 0) 
			arSelected.push(ob.options[ob.selectedIndex].value); 
		ob.options[ob.selectedIndex].selected = false; 
	} // You can use the arSelected array for further processing.
	return true;
}

function populateCircleIdArray(form) { 
	

		
	var result = ""; 
    var jsCircleIdArr = new Array();
    for (var i = 0; i < form.circleId.length; i++) { 
        if (form.circleId.options[i].selected) { 
        	
        		jsCircleIdArr.push(form.circleId.options[i].value);
        	
        } 
    } 
    document.forms[0].circleIdArr.value=jsCircleIdArr;
   
    
}

function checkIfAllSelected(form) {
	
	
	var circleIdList = document.getElementById("circleId");
	circleIdList.focus();
 	if(form.circleId.options[0].selected) {
		//form.circleId.options[0].selected = false;
		for(i=1; i<circleIdList.options.length; i++) {
			circleIdList.options[i].selected = true;
		}
	}
}
	
function selectAllCircle(){
		var ctrl = document.forms[0].circleId;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		
		
		
		if (document.forms[0].circleId[0].selected){
			for (var i=1; i < document.forms[0].circleId.length; i++){
  	 			document.forms[0].circleId[i].selected =true;
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
</SCRIPT>
</HEAD>


<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onload="disableSelected()">
<TABLE valign="top" align="left">
	<TR valign="top">
		<TD valign="top">
		
			<html:form action="/recoSummaryAction.do">
			
			<html:hidden property="circleIdArr" name="RecoSummaryFormBean" />
			<html:hidden property="groupId"/>
			<Table width="545" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
							<TD colspan="4" class="text"><BR>
												<!-- Page title start -->
		
						<IMG src="<%=request.getContextPath()%>/images/recoSummary.jpg"
		width="525" height="26" alt=""/>
						
				
					<!-- Page title end -->
							
							</TD>
				</TR>
				
				<tr>
				<td colspan="4" class="text" align="center"><BR><strong>
					<font size="1" color="red">
			<%
				if(request.getAttribute("success") != null) {
					out.print((String)request.getAttribute("success"));
				}
			 %>
			 
				<TR>
					<TD colspan="4" align="center"><FONT color="#ff0000" size="-2"><html:errors
						bundle="error" property="error.account" /></FONT> <FONT color="#ff0000"
						size="-2"><html:errors bundle="dp"
						property="message.account" /></FONT></TD>
					<input type="hidden" name="methodName" styleId="methodName" />
				</TR>


<TR align="center">
					<TD class="txt" align="left"><strong><bean:message bundle="view"
						key="report.circle" /><font color="red">*</font>: </strong>
					</TD>
					
					
					<td class="txt" align="left" width="40" height="145">
						<html:select property="circleId" onchange="selectAllCircle()" styleId="circleId" style="width:230px" multiple="true" size="7" onclick="checkIfAllSelected(this.form)">
							
							
							<logic:notEmpty name="RecoSummaryFormBean"
								property="circleList">
								
								<bean:define id="circleList" name="RecoSummaryFormBean" property="circleList" />
								<html:options labelProperty="strText" property="strValue" collection="circleList" />


							</logic:notEmpty>
						</html:select>
					</td>
				</TR>
				

		
				<TR align="center">
					<TD class="txt" align="left"><strong><font
						color="#000000"><bean:message bundle="view"
						key="report.recoStatus" /></font><font color="red">*</font>:</strong></TD>
					<TD class="txt" align="left"  width="275" ><html:select
						property="recoStatus" style="width:230px" styleId="recoStatus"
						name="RecoSummaryFormBean">
						<html:option value="0">
							<bean:message bundle="view" key="report.option.all" />
						</html:option>
						<html:option value="1">
							<bean:message bundle="view" key="report.recoPending" />
						</html:option>
						<html:option value="2">
							<bean:message bundle="view" key="report.recoCompleted" />
						</html:option>
					</html:select></TD>
					<TD width="119">
					</TD>
				</TR>
				
				<TR>
				    <TD class="txt" align="left" width="20%"><strong><font
						color="#000000"><bean:message bundle="view"
						key="report.recoPeriod" /></font><font color="red">*</font>: </strong></TD>
					<td><html:select
						styleId="recoID" property="recoID"
						name="RecoSummaryFormBean" style="width:150px"
						style="height:20px">
						<html:option value="-1" >
							--Select All--
						</html:option>
						<logic:notEmpty name="RecoSummaryFormBean" property="recoListTotal">
							<bean:define id="recoListTotal" name="RecoSummaryFormBean" property="recoListTotal" />
						<html:options labelProperty="strText" property="strValue" collection="recoListTotal" />
						
						</logic:notEmpty>
					</html:select></TD>
			     </TR>
				
				
				<TR>
					<TD>
						<BR/>
					</TD>
				</TR>
			
				<TR>
					<TD/>	
					<TD><input type="button" id="submit_form" value="Generate Report" tabindex="8" onclick="submitForm(this.form);" />
					</TD>
				</TR>
						<!-- Added by Neetika for BFR 16 on 13aug 2013 Release 3 -->
				<TR>
					<TD  align="center" colspan=4>
					<p style="visibility:hidden;" id="progress">
					<img id="progress_image" style="visibility:hidden;padding-left:55px;padding-top:500px;width:30;height:34" src="${pageContext.request.contextPath}/images/ajax-loader.gif" /> 
					</p>
					</TD>
				</TR>
				<TR align="center" bgcolor="4477bb" valign="top">

				</TR>
							
			</TABLE>	
	
		</html:form>	
		</TD>
	</TR>
	
	</TABLE>
	<input type="hidden" name="reportDownloadStatus" value=""> <!-- Neetika BFR 16 14Aug -->
<script type="text/javascript">
/*Method Added by Amrit for BFR 16 of Release 3*/
	function getReportDownloadStatus() {
	var url= "recoSummaryPaginationAction.do?methodName=getReportDownloadStatus";
	var elementId = document.getElementById("reportDownloadStatus");
	var txt = true;
	makeAjaxRequest(url,"reportDownloadStatus",txt);
	//alert("get report .."+document.getElementById("reportDownloadStatus").value);
	if(document.getElementById("reportDownloadStatus").value != null && document.getElementById("reportDownloadStatus").value == 'inprogress') 
	{
	
	document.getElementById("submit_form").disabled=true;
	loadSubmit();
    }
    else
    {
    document.getElementById("submit_form").disabled=false;
    finishSubmit();
	}
	//alert("value:" + document.getElementById("reportDownloadStatus").value);
	}
	</SCRIPT>
</BODY>
</html>
		