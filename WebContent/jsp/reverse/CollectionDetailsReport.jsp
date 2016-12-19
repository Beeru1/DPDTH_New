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

function getDistName(){
	var tsmId = document.getElementById("tsmId").value;
	var selectedTsmValues ="";
		
	if(tsmId ==""){
		alert("Please Select atleast one TSM");
		return false;
	 }
	 if(document.forms[0].tsmId.length ==1){
			alert("No TSM exists ");
		return false;
	   }
 	else{
	 	if (document.forms[0].tsmId[0].selected){
		
			for (var i=1; i < document.forms[0].tsmId.length; i++)
  	 		{
   		
     			if(selectedTsmValues !=""){
					selectedTsmValues += ",";
     			}
     		var selectedValTSM = document.forms[0].tsmId[i].value.split(",");
     		selectedTsmValues += selectedValTSM[0];
   			}
		}	
		else{
			for (var i=1; i < document.forms[0].tsmId.length; i++)
	  	 {
	   		 if (document.forms[0].tsmId[i].selected)
	     	 {
	     		if(selectedTsmValues !=""){
					selectedTsmValues += ",";
     			}
	     	var selectedValTSM = document.forms[0].tsmId[i].value.split(",");
     		selectedTsmValues += selectedValTSM[0];
	     	 }
	   	}
   }
   		 document.getElementById("hiddenTsmSelecIds").value =selectedTsmValues;
   		 getAllAccountsUnderMultipleAccounts(selectedTsmValues,'distId');
 }
}
function getTsmName() 
{
		 var accountLevel =  '<% out.print(Constants.ACC_LEVEL_TSM); %>';
		var circleId = document.getElementById("circleId").value;
		var selectedCircleValues ="";
		selectedCircleValues =0;
		getAllAccountsUnderMultipleAccounts(selectedCircleValues,'distId');
	   
	if(circleId ==""){
		alert("Please Select atleast one Circle");
		return false;
	 }
	 if(document.forms[0].circleId.length ==1){
			alert("No Circle exists");
		return false;
	   }
 	else{
	 	if (document.forms[0].circleId[0].selected || document.forms[0].circleId[1].selected){
		
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
   		 getAllAccountsMultipleCircles(accountLevel,selectedCircleValues,'tsmId');
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

function selectAllTSM(){
		var ctrl = document.forms[0].tsmId;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		/*if(selCount==ctrLength-1){
				ctrl[0].selected=true;
		}
		if(selCount==ctrLength-2){
				ctrl[0].selected=false;
		}
		*/
		
		if (document.forms[0].tsmId[0].selected){
			for (var i=1; i < document.forms[0].tsmId.length; i++){
  	 			document.forms[0].tsmId[i].selected =true;
     		}
   		}
   	
}

function selectAllDist(){
		var ctrl = document.forms[0].distId;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		if(selCount==ctrLength-1){
				ctrl[0].selected=true;
		}
		if(selCount==ctrLength-2){
				ctrl[0].selected=false;
		}
		
		
		if (document.forms[0].distId[0].selected){
			for (var i=1; i < document.forms[0].distId.length; i++){
  	 			document.forms[0].distId[i].selected =true;
     		}
   		}
   	
}


function selectAllProducts(){
		var ctrl = document.forms[0].productId;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		if(selCount==ctrLength-1){
				ctrl[0].selected=true;
		}
		if(selCount==ctrLength-2){
				ctrl[0].selected=false;
		}
		
		
		if (document.forms[0].productId[0].selected){
			for (var i=1; i < document.forms[0].productId.length; i++){
  	 			document.forms[0].productId[i].selected =true;
     		}
   		}
   	
}


function selectAllCollectionTypes(){
		var ctrl = document.forms[0].collectionName;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		if(selCount==ctrLength-1){
				ctrl[0].selected=true;
		}
		if(selCount==ctrLength-2){
				ctrl[0].selected=false;
		}
		
		
		if (document.forms[0].collectionName[0].selected){
			for (var i=1; i < document.forms[0].collectionName.length; i++){
  	 			document.forms[0].collectionName[i].selected =true;
     		}
   		}
   	
}

function selectAllStatus(){
		var ctrl = document.forms[0].status;
		var selCount = selectedCount(ctrl);
		var ctrLength = ctrl.length;
		if(selCount==ctrLength-1){
				ctrl[0].selected=true;
		}
		if(selCount==ctrLength-2){
				ctrl[0].selected=false;
		}
		
		
		if (document.forms[0].status[0].selected){
			for (var i=1; i < document.forms[0].status.length; i++){
  	 			document.forms[0].status[i].selected =true;
     		}
   		}
   	
}

function selectAllCircles(){
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

function exportToExcel()
	{
		if(document.forms[0].showCircle.value=="A"){
			if(document.getElementById("circleId").value ==""){
				alert("Please Select atleast one Circle");
				return false;
	   		}
	  	}
		if(document.forms[0].showTSM.value=="A"){
			if(document.getElementById("tsmId").value ==""){
				alert("Please Select atleast one TSM");
				return false;
	   		}
	  		if(document.forms[0].tsmId.length ==1){
				alert("No TSM exists under the Cirlce");
				return false;
	   		}
		}
		if(document.forms[0].showDist.value=="A"){
			if(document.getElementById("distId").value ==""){
				alert("Please Select atleast one Distributor");
				return false;
	   		}
	   		var selectedDistValues = "";
	   		if (document.forms[0].distId[0].selected){
		
			for (var i=1; i < document.forms[0].distId.length; i++)
  	 		{
   		
     			if(selectedDistValues !=""){
					selectedDistValues += ",";
     			}
     		var selectedValDist = document.forms[0].distId[i].value.split(",");
     		selectedDistValues += selectedValDist[0];
   			}
			}	
		else{
			for (var i=1; i < document.forms[0].distId.length; i++)
	  	 {
	   		 if (document.forms[0].distId[i].selected)
	     	 {
	     		if(selectedDistValues !=""){
					selectedDistValues += ",";
     			}
	     	var selectedValDist = document.forms[0].distId[i].value.split(",");
     		selectedDistValues += selectedValDist[0];
	     	 }
	   	}
   		}
   		 document.getElementById("hiddenDistSelecIds").value =selectedDistValues;
	  	}
	  	
	  	if(document.getElementById("collectionName").value ==""){
				alert("Please Select atleast one Collection Type");
				return false;
	   		}
	   		var selectedcollectionTypeValues = "";
	   		if (document.forms[0].collectionName[0].selected){
		
			for (var i=1; i < document.forms[0].collectionName.length; i++)
  	 		{
   		
     			if(selectedcollectionTypeValues !=""){
					selectedcollectionTypeValues += ",";
     			}
     		var selectedVal = document.forms[0].collectionName[i].value.split(",");
     		selectedcollectionTypeValues += selectedVal[0];
   			}
			}	
		else{
			for (var i=1; i < document.forms[0].collectionName.length; i++)
	  	 	{
	   		 if (document.forms[0].collectionName[i].selected)
	     	 {
	     		if(selectedcollectionTypeValues !=""){
					selectedcollectionTypeValues += ",";
     			}
	     	var selectedVal = document.forms[0].collectionName[i].value.split(",");
     		selectedcollectionTypeValues += selectedVal[0];
	     	 }
	   		}
   		}
   		 document.getElementById("hiddenCollectionTypeSelec").value =selectedcollectionTypeValues;
   		 
   		 if(document.getElementById("status").value ==""){
				alert("Please Select atleast one status");
				return false;
	   		}
	   		var selectedStatusValues = "";
	   		if (document.forms[0].status[0].selected){
		
			for (var i=1; i < document.forms[0].status.length; i++)
  	 		{
   		
     			if(selectedStatusValues !=""){
					selectedStatusValues += ",";
     			}
     		var selectedVal = document.forms[0].status[i].value.split(",");
     		selectedStatusValues += selectedVal[0];
   			}
			}	
		else{
			for (var i=1; i < document.forms[0].status.length; i++)
	  	 	{
	   		 if (document.forms[0].status[i].selected)
	     	 {
	     		if(selectedStatusValues !=""){
					selectedStatusValues += ",";
     			}
	     	var selectedVal = document.forms[0].status[i].value.split(",");
     		selectedStatusValues += selectedVal[0];
	     	 }
	   		}
   		}
   		 document.getElementById("hiddenStatusSelec").value =selectedStatusValues;
	  	
	  	
	 	if(document.getElementById("productId").value ==""){
				alert("Please Select atleast one STB Type");
				return false;
	   		}
	   		var selectedSTBTypeValues = "";
	   		if (document.forms[0].productId[0].selected){
		
			for (var i=1; i < document.forms[0].productId.length; i++)
  	 		{
   		
     			if(selectedSTBTypeValues !=""){
					selectedSTBTypeValues += ",";
     			}
     		var selectedVal = document.forms[0].productId[i].value.split(",");
     		selectedSTBTypeValues += selectedVal[0];
   			}
			}	
		else{
			for (var i=1; i < document.forms[0].productId.length; i++)
	  	 	{
	   		 if (document.forms[0].productId[i].selected)
	     	 {
	     		if(selectedSTBTypeValues !=""){
					selectedSTBTypeValues += ",";
     			}
	     	var selectedVal = document.forms[0].productId[i].value.split(",");
     		selectedSTBTypeValues += selectedVal[0];
	     	 }
	   		}
   		}
   		 document.getElementById("hiddenProductSeletIds").value =selectedSTBTypeValues;
	  	
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
	    	
	    	if(daysBetween(startDate,endDate) >= 30)
			{
			  alert('Please select dates having only max 30 days difference');
			  return false;
			}
	   
	   
	var url = "CollectionDetailReport.do?methodName=exporttoExcel";
	
	document.forms[0].action=url;
	document.forms[0].method="post";
	document.forms[0].submit();
	}
</SCRIPT>

</HEAD>

<BODY background="${pageContext.request.contextPath}/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onload="">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%" 	height="100%" valign="top">
	
	
	
	<html:form  action="/CollectionDetailReport"   method="post" >
	<html:hidden property="showCircle" name="CollectionReportBean" />
	<html:hidden property="showTSM" name="CollectionReportBean" />
	<html:hidden property="showDist" name="CollectionReportBean" />
	
	<html:hidden property="hiddenCircleSelecIds" name="CollectionReportBean" />
	<html:hidden property="hiddenTsmSelecIds" name="CollectionReportBean" />
	<html:hidden property="hiddenDistSelecIds" name="CollectionReportBean" />
	<html:hidden property="hiddenProductSeletIds" name="CollectionReportBean" />
	<html:hidden property="hiddenCollectionTypeSelec" name="CollectionReportBean" />
	<html:hidden property="hiddenStatusSelec" name="CollectionReportBean" />
	
	
	
	
	<TR valign="top">
		
		<td>
			<TABLE width="570" border="0" cellpadding="5" cellspacing="0" class="text">
				<TR>
					<TD colspan="2" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/Collection_Detail_Report.jpg"
						width="410" height="24" alt="">
						
						</TD>
		
				</TR>
			<TR>
				<logic:match value="A" property="showCircle" name="CollectionReportBean">	
				
					<TD align="left"><b class="text pLeft15"> <bean:message
						bundle="view" key="productcreate.circlelist" /><font color="red">*</font></b>
					</td>
				<td>	
						
						<html:select  multiple="true"  property="circleId" styleId="circleId" style="width:155px; height:50px;"  onchange="selectAllCircles();getTsmName();" size="6" >
							<html:option value="-1">--All--</html:option>
							<logic:notEmpty name="CollectionReportBean" property="circleList">
								 <html:optionsCollection name="CollectionReportBean"	property="circleList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
				
			</logic:match>
			<logic:match value="N" property="showCircle" name="CollectionReportBean">	
				
					<TD align="left"><b class="text pLeft15"> <bean:message
						bundle="view" key="productcreate.circlelist" /><font color="red">*</font></b>
					</td>
				<td>	
						
						<html:select  multiple="true"  property="circleId"  disabled="true" styleId="circleId" style="width:155px; height:50px;"  onchange="selectAllCircles();getTsmName();" size="6" >
							<logic:notEmpty name="CollectionReportBean" property="circleList">
								 <html:optionsCollection name="CollectionReportBean"	property="circleList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
				
			</logic:match>
			</tr><tr>
			<logic:match value="A" property="showTSM" name="CollectionReportBean">		
					<TD align="left"><b class="text pLeft15"> TSM<font color="red">*</font></b>
					</td>
				<td>	
						<html:select  multiple="true"  property="tsmId" styleId="tsmId" style="width:155px; height:50px;" size="6" onchange="getDistName();selectAllTSM();">
							<logic:notEmpty name="CollectionReportBean" property="tsmList">
							 <html:optionsCollection name="CollectionReportBean"	property="tsmList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
				</logic:match>
				<logic:match value="N" property="showTSM" name="CollectionReportBean">		
					<TD align="left"><b class="text pLeft15"> TSM<font color="red">*</font></b>
					</td>
				<td>	
						<html:select  multiple="true"  property="tsmId" disabled="true" styleId="tsmId" style="width:155px; height:50px;" size="6" onchange="getDistName();selectAllTSM();">
							<logic:notEmpty name="CollectionReportBean" property="tsmList">
							 <html:optionsCollection name="CollectionReportBean"	property="tsmList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
				</logic:match>
				</TR>
				
				
					<TR>
				<logic:match value="A" property="showDist" name="CollectionReportBean">		
					<TD align="left"><b class="text pLeft15"> Distributor<font color="red">*</font></b>
					</td>
				<td>	
						<html:select  multiple="true"  property="distId" styleId="distId" style="width:155px; height:50px;" size="6" onchange="selectAllDist();" >
							<logic:notEmpty name="CollectionReportBean" property="distList">
								 <html:optionsCollection name="CollectionReportBean"	property="distList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
						</TD>
				</logic:match>
				<logic:match value="N" property="showDist" name="CollectionReportBean">		
					<TD align="left"><b class="text pLeft15"> Distributor<font color="red">*</font></b>
					</td>
				<td>	
						<html:select  multiple="true"  disabled="true"  property="distId" styleId="distId" style="width:155px; height:50px;" size="6" onchange="selectAllDist();" >
							<logic:notEmpty name="CollectionReportBean" property="distList">
								 <html:optionsCollection name="CollectionReportBean"	property="distList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
						</TD>
				</logic:match>
				</tr><tr>
				<TD align="left" height="46" >
						<b class="text pLeft15">STB Type<font color="red">*</font></b>
						
					</td>
					 <TD >				   
						<html:select property="productId"  style="width:155px; height:50px;" styleId="productId" name="CollectionReportBean" onchange="selectAllProducts()" multiple="true">
							<html:option value="-1">--All--</html:option>
							<logic:notEmpty property="productList" name="CollectionReportBean">
						
						<html:optionsCollection name="CollectionReportBean"	property="productList" label="productCategory" value="productCategoryId"/>
						 </logic:notEmpty>
						</html:select>
						
					</TD>  
				</TR>	
				
				
				<TR>
					<TD align="left"><b class="text pLeft15">Collection Type<font color="red">*</font></b>
					</td>
				<td>	
						
						<html:select property="collectionName"  style="width:155px; height:50px;" styleId="collectionName" name="CollectionReportBean" onchange="selectAllCollectionTypes()" multiple="true">
							<logic:notEmpty property="collectionType" name="CollectionReportBean">
						<html:option value="-1">--All--</html:option>
						<html:optionsCollection name="CollectionReportBean"	property="collectionType" label="collectionName" value="collectionId"/>
						 </logic:notEmpty>
						</html:select>
					
						
						</TD>
				</TR>
				<TR>
					<TD align="left"><b class="text pLeft15"> Status<font color="red">*</font></b>
					</td>
				<td>	
						
						<html:select multiple="true" property="status" onchange="selectAllStatus()" styleId="status" style="width:155px; height:50px;">
							<html:option value="-1">--All--</html:option>
							<html:option value="REC">Pending for Receive</html:option>
							<html:option value="COL">Received by distributor</html:option>
							<html:option value="IDC">Ready to Dispatch</html:option>
							<html:option value="S2W">Sent to Warehouse</html:option>
							<html:option value="AIW">Accepted in Warehouse </html:option>
							<html:option value="MSN">Not Accepted in Warehouse</html:option>
							<html:option value="ABW">Added by Warehouse</html:option>
							<html:option value="ERR">Error in sending to warehouse</html:option>
							
							
						</html:select>
					
						
						</TD>
					
				</TR>
				
				<TR id="calId">
					<TD align="left"><b class="text pLeft15"><bean:message bundle="view"
						key="report.fromDate" /><STRONG><FONT color="RED">*</FONT></STRONG></b></TD>
						
						<TD width="175px" align="left"><FONT color="#003399"> <html:text
						property="fromDate" styleClass="box" styleId="fromDate" readonly="true" 
						 size="21"  maxlength="10" name="CollectionReportBean" />
						<script language="JavaScript">
						new tcal ({
							// form name
							'formname': 'CollectionReportBean',
							// input name
							'controlname': 'fromDate'
						});
					
						</script>
					</FONT></TD>
			</tr><tr>
					<TD align="left"><b class="text pLeft15"><bean:message bundle="view"
						key="report.toDate" /><STRONG><FONT color="RED">*</FONT></STRONG></b></TD>
						<TD width="175px" align="left"><FONT color="#003399"> <html:text
						property="toDate" styleClass="box" styleId="toDate" readonly="true"
						 size="21"  maxlength="10" name="CollectionReportBean" />
						<script language="JavaScript">
						new tcal ({
							// form name
							'formname': 'CollectionReportBean',
							// input name
							'controlname': 'toDate'
						});
					
						</script>
	
						 </FONT>
					</TD>
					
					
				</TR>
				<TR></TR>
				<tr>
					<td colspan="2" align="center">
					<html:button property="excelBtn" value="Export To Excel" onclick="return exportToExcel();"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;
						
					</td>
				</tr>
	 <html:hidden property="methodName" styleId="methodName" value="exporttoExcel"  />
	
			</table>
		</td>
		</TR>
		</html:form>
		
	
	
</TABLE>

</BODY>
</html>

<%}catch(Exception e){
e.printStackTrace();
}%>