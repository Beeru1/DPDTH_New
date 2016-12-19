<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<%@page import="com.ibm.dp.beans.RecoPeriodConfFormBean"%><html>
<head>
<title>SHORT SHIPMENT SERIAL VERIFICATION</title>
<META http-equiv=Content-Type content="text/html; charset=iso-8859-1">
<link href="${pageContext.request.contextPath}/theme/text_new.css" rel="stylesheet" type="text/css">
<TITLE></TITLE>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/scripts/Cal.js"></SCRIPT>
<script type="text/javascript"><!--

function addRecoPeriod()
{    


    var fromDate = document.getElementById("fromDate").value;
    var toDate = document.getElementById("toDate").value; 
    var gracePeriod = document.getElementById("gracePeriodReco").value; 
    //Added by shiva for frontend validation for reco period configuration
    var currentdate = new Date();
    currentdate = Date.parse(currentdate);
    var newfromDate = Date.parse(document.getElementById("fromDate").value);
    var newToDate = Date.parse(document.getElementById("toDate").value);
    var recoOpening = document.forms[0].recoOpening.value;
    var recoClosing = document.forms[0].recoClosing.value;
    var fromDateReco = new Date(fromDate);
    var fromDateDate = fromDateReco.getDate();
    var now = new Date();
    var fromDate3monthold = new Date(now);
    fromDate3monthold = fromDate3monthold.setMonth(fromDate3monthold.getMonth() - 3);    
    //----END shiva
    
	
    if(document.getElementById("fromDate").value==null || document.getElementById("fromDate").value==""){
    	alert("Select the From Date");
    	return false;
    	}
    if(document.getElementById("toDate").value==null || document.getElementById("toDate").value==""){
     	alert("Select the To Date");
			return false;
    }
    //Added by Shiva for frontend validation for reco period configuration- Start
    if(newfromDate < fromDate3monthold){
		alert("RECO can not be created as FROM DATE can not be less than 3 months");
		return false;
	}else{
    if(newfromDate < currentdate && newToDate < currentdate){
    	if(fromDateDate != recoOpening){
    	alert("Please select FROM DATE as "+recoOpening+" of the month and TO DATE date as "+recoClosing+" of the month");
    	return false;    	
    	}
    }
    
    if(newfromDate < currentdate && newToDate == currentdate){
    	if(fromDateDate != recoOpening){
    	alert("Please select FROM DATE as "+recoOpening+" of the month and TO DATE date as "+recoClosing+" of the month");
    	return false;
    	}
    }
    
    if(newfromDate <= currentdate && newToDate > currentdate){
    	if(fromDateDate != recoOpening){
    	alert("Please select FROM DATE as "+recoOpening+" of the month");
    	return false;
    	}
    }
    
    if(newfromDate == currentdate && newToDate == currentdate){
    	if(fromDateDate != recoOpening){
    	alert("Please select FROM DATE as "+recoOpening+" of the month and TO DATE date as "+recoClosing+" of the month");
    	return false;
    	}
    }
    
    if(newfromDate == currentdate && newToDate > currentdate){
    	if(fromDateDate != recoOpening){
    	alert("Please select FROM DATE as "+recoOpening+" of the month");
    	return false;
    	}
    }
    
    /*if(newfromDate == currentdate && newToDate < currentdate){
    	if(fromDateDate != recoOpening){
    	alert("Please select FROM DATE as "+recoOpening+" of the month and TO DATE date as "+recoClosing+" of the month");
    	return false;
    	}
    }*/
    
     if(newfromDate <= currentdate && newToDate > currentdate){
     	if(fromDateDate != recoOpening){
    	alert("Please select FROM DATE as "+recoOpening+" of the month");
    	return false;
    	}
    }
    
   // if(newfromDate < currentdate && newToDate <= currentdate){
   	//  if(fromDateDate != recoOpening){
    //	alert("Please select FROM DATE as "+recoOpening+" of the month and TO DATE date as "+recoClosing+" of the month");
    	//return false;
    	//}
    //}
    
    }
    
    
    
    
    //--------End--- shiva
    
    //validation for overlapping reco period
   
    
    
   
   	if(document.getElementById("fromDate").value!=null && document.getElementById("fromDate").value!="")
   	{
			if(!(isDate2Greater(document.getElementById("fromDate").value, document.getElementById("toDate").value)))
			{   
    		return false;
    		}
			else if(!validateRecoPeriod())
			{
				 	return false;
				 	}
				 else if(gracePeriod<=0  || gracePeriod==null || !gracePeriod.toString().match(/^[-]?\d*\.?\d*$/)){
						alert("Grace Period is not valid");
					    return false;
					  }							
	     			 else{
						document.forms[0].methodName.value = 'addRecoPeriod'; 
						document.forms[0].submit();
					 }
	}
}
function validateRecoPeriod()
{ 

	var lastToDate = document.getElementById("lastToDate").value;	
	if(lastToDate!="" && lastToDate!=null){
	var fromDate = document.getElementById("fromDate").value;
		var start = new Date(lastToDate);
		var end = new Date(fromDate);
		var diff =(end - start)/(24*3600*1000);
		if (diff !=1){
		    alert("From date should be immediate next date of "+lastToDate+" (To Date of last reco).");
			
			return false;
		}
		alert("True");
	}
		
		return true;	
}
/*Added by sugandha to view the fromdate,todate,updategraceperiod */

	function checkGraceperiod(recoPeriodId)
	{
  		var url="recoPeriodConf.do?methodName=viewGracePeriod&recoPeriodId="+recoPeriodId;
  		window.open(url,"SerialNo","width=700,height=600,scrollbars=yes,toolbar=no");
	}
function updateGracePeriod()
{
	var gracePeriod = document.getElementById("gracePeriod").value; 
    if(gracePeriod<=0  || gracePeriod==null || !gracePeriod.toString().match(/^[-]?\d*\.?\d*$/)){
				alert("Grace Period is not valid");
				return false;
				}	
	else{
	       document.forms[0].toDate.value='';
	       document.forms[0].fromDate.value='';
  		   document.forms[0].methodName.value = 'updateGracePeriod'; 
		   document.forms[0].submit();
		}	
}

function success()
{
/*if(document.getElementById("fromDate").value!=null && document.getElementById("fromDate").value!=""){
alert("Reco Period Added Successfully.");
}else{
alert("Grace Period Updated Successfully.");*/

var message = document.getElementById("strMessage").value;
var recoValidatemsg = document.getElementById("recoValidate").value;
if(recoValidatemsg !=null && recoValidatemsg !=''){
alert(recoValidatemsg);
document.getElementById("recoValidate").value='';
}
if(message!='')
{
	alert(message);	
	document.getElementById("strMessage").value='';
	document.getElementById("methodName").value='';
}
document.forms[0].gracePeriodReco.value=document.forms[0].gracePeriod.value;
}
function isDate2Greater(date1, date2){
		var start = new Date(date1);
		var end = new Date(date2);
		var diff =end - start;
		if (diff <0){
			alert("From Date should be smaller than To Date");
			return false;
		}		
		return true;
	}
	function updatePartnerData()
{
	 if(document.getElementById("recoPeriodId").value==null || document.getElementById("recoPeriodId").value=="-1")
	 {
    	alert("Select Reco Period");
    	return false;
    	}
     if(document.getElementById("file").value==null || document.getElementById("file").value=="")
     {
	    	alert("Upload CSV file");
	    	return false;
    }
    if(!(/^.*\.csv/.test(document.getElementById("file").value))) 
	{
			//Extension not proper.
			alert("Upload CSV file");
			return false;
				
	}
   	if(document.getElementById("gracePeriodMod").value==null || document.getElementById("gracePeriodMod").value=="")
   	{
    	alert("Enter Grace period");
    	return false;
   	}
    document.getElementById("methodName").value="updatePartnerData";
    document.forms[0].submit();
} 
--></script>

<script language="JavaScript">
 var varOldestRecId;
	function takeAction()
	{
	var tobeUpdated=false;
	
	document.forms[0].deleteRecoId.value="";
	document.forms[0].closeRecoIds.value="";
	  
   if(document.forms[0].deleteRc)
	{
	
		var chkcount = document.getElementsByName('deleteRc');		
		if(chkcount.length == 1)
		{
			if (document.forms[0].deleteRc.checked) {     	
				document.forms[0].deleteRecoId.value=document.forms[0].deleteRc.value;
			tobeUpdated=true;
			} 
		}
		else
		{
		 for (i=document.forms[0].deleteRc.length-1; i > -1; i--) {
			if (document.forms[0].deleteRc[i].checked) {     	
				document.forms[0].deleteRecoId.value=document.forms[0].deleteRc[i].value;
			tobeUpdated=true;
			} 
	  	 }
	  	 
	  	 
	  	}
	}
	
	if( document.forms[0].closeRc)
	{
	var chks = document.getElementsByName('closeRc');
	document.forms[0].closeRecoIds.value="";
		if(chks.length == 1)
		{
		    if (document.forms[0].closeRc.checked) {     	
			document.forms[0].closeRecoIds.value = document.forms[0].closeRc.value;
			//alert(document.forms[0].closeRecoIds.value+ "   1");
			tobeUpdated=true;
			}
		}
		else
		{
		 
		 for (var i = 0; i < chks.length; i++)
		 {
			if (chks[i].checked)
			{
			  	document.forms[0].closeRecoIds.value = document.forms[0].closeRecoIds.value + chks[i].value+",";
			  //	alert(document.forms[0].closeRecoIds.value+"  2");
				tobeUpdated=true;			
			}
		 }
		}
		
		if ( ! document.forms[0].closeRecoIds.value == "")
		{
		  isRecoOldest(document.forms[0].closeRecoIds.value);
		 // alert(isRecoOldest(document.forms[0].closeRecoIds.value)+"   3");
		 
		  if( varOldestRecId != "-1")
		  {
		  //alert(varOldestRecId+"   4");
 		  alert("Please first complete oldest Reco Period Configuration.");
		  return false; 
		  }
		} 
	 }
	 
	 /************put code here*****************/
	 
	 // Start of changing by RAM
var validateAction="";
 var closeRecoIds=document.forms[0].closeRecoIds.value;
 var deleteRecoId=document.forms[0].deleteRecoId.value;
  

	if(window.XmlHttpRequest) {
		req = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		req=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if(req==null) { 
		alert("Browser Doesnt Support AJAX");
		return;
	}
	req.onreadystatechange = function()
 	 {
	 if (req.readyState==4 || req.readyState=="complete") 
		{
			 validateAction=req.responseText;
			// alert("validateAction::"+validateAction);			
		}	
	}
	var url= "recoPeriodConf.do?methodName=validateAction&closeRecoIds="+closeRecoIds+"&deleteRecoId="+deleteRecoId  ;
	req.open("POST",url,false);
	req.send();
//alert("validateAction"+validateAction);
if(validateAction!="")
{
if(validateAction==1)
{
	if(!confirm("Debit remaining for some distributors , do you want to continue"))
	{
	//alert("false");
	return false;
	}
//	else
	//alert("true");
	}	
}
	 
  	
	 if(tobeUpdated)
	 { 
        var doModify =confirm("Are you confirm to modify Reco?\nSubsequent Recos will be modified.");
		if (doModify==true)
		  {
		  	document.forms[0].methodName.value="updateRecoPeriodConf";
			document.forms[0].submit();
		  }
		else
		  {
		  return false; 
		  }
	 }
	 else
	 {
	   	 alert("No action required");
	 }
	}
	

function isRecoOldest(text){
	if(window.XmlHttpRequest) {
		req = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		req=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if(req==null) { 
		alert("Browser Doesnt Support AJAX");
		return;
	}
	req.onreadystatechange = function(){
	  getAjaxTextValues();
	}
	var url= "recoPeriodConf.do?methodName=getOldestRecoId&closeRecoIds="+text;
	req.open("POST",url,false);
	req.send();
}
function getAjaxTextValues()
{
	if (req.readyState==4 || req.readyState=="complete") 
	{
		var oldestRecoId=req.responseText;		
		varOldestRecId = oldestRecoId;
	}
}
</script>
</head>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onload="success()">
	
	<html:form action="/recoPeriodConf" method="post" name="RecoPeriodConfFormBean" type="com.ibm.dp.beans.RecoPeriodConfFormBean" enctype="multipart/form-data" >
	<table border="0" width="100%" >
		<TR>
		<FONT color="RED"><STRONG> <html:errors
							bundle="error" property="success.grace" /></STRONG></FONT>
							</TR>
							</table>
	<html:hidden property="methodName"/>
	<html:hidden property="strMessage"/>
	<html:hidden property="closeRecoIds"/>
	<html:hidden property="deleteRecoId"/>
	
	<!--  Added by shiva for frontend validation for reco period configuration-->
	<html:hidden name = "RecoPeriodConfFormBean" property="recoOpening" />
	<html:hidden name = "RecoPeriodConfFormBean" property="recoClosing" />
	<html:hidden name = "RecoPeriodConfFormBean" property="recoValidate" />
	
	<!--  END shiva-->
	
	
	
	
	<TABLE cellspacing="0" cellpadding="0" border="0" width="100%" height="100%" valign="top">
		<TR valign="top">
		<TD width="167" align="left" bgcolor="b4d2e7" valign="top" height="100%">		
		<TD valign="top" width="100%" height="100%">
			<TABLE cellpadding="0" cellspacing="0" align="center" width="100%" >
				<TR>
				<td colspan="4" class="text">					
					<!-- Page title start -->
					<TABLE cellpadding="0" cellspacing="0" align="center" width="100%" >						
					<TR>
					<TD colspan="2" class="text"><BR>
					<IMG src="${pageContext.request.contextPath}/images/recoPeriodConfig.jpg"
						width="410" height="24" alt=""></TD>
					</TR>
					</TABLE>
						<!-- Page title end -->	
				</td>
				</TR>
				<TR>
					<TD colspan="4" >&nbsp;</TD>
				</TR>
				<TR>
				<TD colspan="5" >
					<table width="100%" height="170">
						<tr>
						<td width="100%" >
						<table width="75%">
							<tr>
							<td><fieldset style="border-width:5px">
				  	 			<legend> <STRONG><FONT color="#C11B17" size="2"><bean:message bundle="dp" key="label.reco.period.history" /> </FONT></STRONG></legend>				  	 	
								
								<table width='50%'>							
									<tr class="text white" style="position: relative; 
				  											color:#ffffff ">
										<TD bgcolor="#CD0504" align="center"><FONT color="white" class="text" align="center">From Date.</FONT></TD>
										<TD bgcolor="#CD0504" align="center"><FONT color="white" class="text" align="center">To Date.</FONT></TD>
										<TD bgcolor="#CD0504" align="center"><FONT color="white" class="text" align="center">Action</FONT></TD>
									</tr>
									<logic:iterate name="recoHistoryList" id="report" indexId="i" type="com.ibm.dp.dto.RecoPeriodDTO">
									<TR >
										<TD align="center">
											<logic:notEqual value="true" name="report" property="linkFlag">
												<FONT color="black" class="text"><bean:write name="report" property="fromDate"/></FONT>
											</logic:notEqual>
											<logic:equal value="true" name="report" property="linkFlag">
												<FONT color="blue" class="text"><a href="#" title="Click here to Update/Delete Reco" style="color: blue;" onclick="checkGraceperiod('${report.recoPeriodId}')"><bean:write name="report" property="fromDate"/></a></FONT>
											</logic:equal>
										</TD>
									<TD align="center"><FONT color="black" class="text"><bean:write name="report" property="toDate"/></FONT></TD>
									<TD align="center">
									
										<logic:equal name="report" property="recoPeriodAction" value="RADIO">
											<input type=radio name=deleteRc value='${report.recoPeriodId}' /> Delete
										</logic:equal>
										<logic:equal name="report" property="recoPeriodAction" value="PROGRESS">
											<font color=green><b>In Progress</b></font>
										</logic:equal>
										<logic:equal name="report" property="recoPeriodAction" value="CHECKBOX">
											<input type=checkbox name=closeRc value='${report.recoPeriodId}'/> Complete
										</logic:equal>
										<logic:equal name="report" property="recoPeriodAction" value="COMPLETED">
											<font color=#CC0000><b>Completed</b></font>
										</logic:equal>
									</TD>
									</TR>
									</logic:iterate>
								<tr><td colspan="3" align="right"><input type="button" align="center" value="Take Action" onclick="takeAction()"/> </td></tr>
								</table>
								</fieldset>
								<html:hidden styleId="lastToDate" property="lastToDate" />				
							</td>
							</tr>
							<tr><td></td></tr>
							<tr>
							<td>
							</td>
							</tr>							
							<tr>
						</table>
							
						<table width="75%" >
							<tr>
							<td>
								<fieldset style="border-width:5px">
				  	 			<legend> <STRONG><FONT color="#C11B17" size="2"><bean:message bundle="dp" key="label.reco.period" /> </FONT></STRONG></legend>				  	 	
								<table border=0>
									<tr>
									<td align="left" width="80">
									<bean:message bundle="dp" key="label.reco.from.date" />
									</td>
									<td width="200">
									<html:text property="fromDate" styleClass="box" styleId="fromDate" readonly="true"
									 name="RecoPeriodConfFormBean"/>
									<script language="JavaScript">
									new tcal ({
									// form name
									'formname': 'RecoPeriodConfFormBean',
									// input name
									'controlname': 'fromDate'
									});
									
									</script>
									</td>
									<td width="80">
									<bean:message bundle="dp" key="label.reco.to.date" />
									</td>
									<td width="200">
									<html:text property="toDate" styleClass="box" styleId="toDate" readonly="true"
								 	name="RecoPeriodConfFormBean"/>
									<script language="JavaScript">
									new tcal ({
										// form name
										'formname': 'RecoPeriodConfFormBean',
										// input name
										'controlname': 'toDate'
									});								
									</script>
									</td>
									</tr>
									<tr>
									<td align="left">
									<bean:message bundle="dp" key="label.reco.grace.period" />
									</td>
									<td align="left" >
									<html:text maxlength="2" property="gracePeriodReco" styleClass="box" styleId="gracePeriodReco"
									 name="RecoPeriodConfFormBean"/>
									</td>
									<td align="center" colspan="2">
									
									<input type="button" align="center" value="Create Reco Period" onclick="addRecoPeriod()"/>&nbsp;&nbsp;								
									</td>
									</tr>
								</table>							
								</fieldset>
							</td>					
							</tr>
							</table>

						<TR>
						<td colspan="5" >
							<table width="75%" border=0>
							<tr><td >
							<fieldset style="border-width:5px">
			  	 			<legend> <STRONG><FONT color="#C11B17" size="2"><bean:message bundle="dp" key="label.reco.grace.modify" /> </FONT></STRONG></legend>				  	 	
								<table>							
									<tr><td align="left" width="80">
												&nbsp;<bean:message bundle="dp" key="label.reco.grace.period" /></td>
										<td align="left" width="240">
										<html:text maxlength="2" property="gracePeriod" styleClass="box" styleId="gracePeriod"
									 name="RecoPeriodConfFormBean"/>
										</td>
										<td align="left" width="80">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" align="center" value="Update Grace Period" onclick="updateGracePeriod()"/>
										</td>										
										</tr>										
								</table>
							</fieldset>
						</td>						
						</tr>
						</table>					
					</td>
					</TR>
					<TR>
					<td>&nbsp;</td>
					</TR>
					
					
					<TR>
						<td colspan="5" >
							<table width="75%" border=0>
							<tr><td >
							<fieldset style="border-width:5px">
			  	 			<legend> <STRONG><FONT color="#C11B17" size="2"><bean:message bundle="dp" key="label.reco.partner.modify" /> </FONT></STRONG></legend>				  	 	
								<table>	
								
									
									<logic:equal name="RecoPeriodConfFormBean" property="error_flag" value="true" >
										  <tr>
										  	<td  align="center" colspan="5"><strong><font color="red">
											  The transaction could not be processed due to some errors. Click on <a href="recoPeriodConf.do?methodName=showErrorFile">View Details </a> 
											  for details.</font><strong>
											 </td>
										  </tr>
				  					</logic:equal>
									<logic:notEmpty name="RecoPeriodConfFormBean" property="strmsg" >
										  <tr>
										  	<td  align="left" colspan="5"><strong><font color="red">
											  <bean:write name="RecoPeriodConfFormBean" property="strmsg"/>
											  </font><strong>
											 </td>
										  </tr>
				  					</logic:notEmpty>
														
									<tr><td align="left" width="80">
												&nbsp;<bean:message bundle="dp" key="label.reco.period.label" /></td>
										<td align="left" width="240">
														
									 	<html:select
												property="recoPeriodId" style="width:180px">
												<logic:notEmpty property="recoPeriodList" name="RecoPeriodConfFormBean">
													<html:option value="-1">--Select Reco Period--</html:option>
													<bean:define id="recoPeriodList" name="RecoPeriodConfFormBean"
														property="recoPeriodList" />
													<html:options labelProperty="recoPeriodName"
														property="recoPeriodId" collection="recoPeriodList" />
					
												</logic:notEmpty>
										</html:select>		 </td>	
										<td align="left" width="80">
											File
										</td>
										<td align="left" >
										 	<html:file property="file" styleClass="box" styleId="file"
										 				name="RecoPeriodConfFormBean"/>			
									 	</td>
								</tr>
								
								<tr>	 
										<td align="left" width="80">
												&nbsp;<bean:message bundle="dp" key="label.reco.grace.period" /></td>
										<td align="left" width="240">
										<html:text maxlength="2" property="gracePeriodMod" styleClass="box" styleId="gracePeriodMod"
									 			name="RecoPeriodConfFormBean" value=""/>
										</td>	
										<td align="center" colspan="2"><input type="button" align="center" value="Update Partner Data" onclick="updatePartnerData()"/>
										</td>										
										</tr>										
								</table>
							</fieldset>
						</td>						
						</tr>
						</table>					
					</td>
					</TR>
					
					
					
					
				</TABLE>
			</TD>
			</TR>
			</Table>
		</TD>
		</TR>
		</TABLE>
</html:form>
</BODY>
</html>