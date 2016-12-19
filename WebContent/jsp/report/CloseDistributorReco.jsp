<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%try{ %>   
<%@page import="com.ibm.dp.common.Constants"%>
<html>
<head>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META http-equiv="Content-Style-Type" content="text/css">

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
	
function exportToExcel()
	{
//	alert("document.getElementById(recoID).value : "+document.getElementById("recoID").value);
	var tsmId = document.getElementById("tsmId").value;
	var selectedTsmValues ="";
	var selectedCircleValues ="";
			if(document.getElementById("circleId").value ==""){
				alert("Please Select atleast one Circle");
				return false;
	   		}
	
	  
	//	alert("No of circles selected : "+document.forms[0].circleId.length);
			for (var i=1; i < document.forms[0].circleId.length; i++)
	  	 {
	   		 if (document.forms[0].circleId[i].selected)
	     	 {
	     		if(selectedCircleValues !=""){
					selectedCircleValues += ",";
	     		}		
	      selectedValCircle = document.forms[0].circleId[i].value.split(",");
	     	selectedCircleValues += selectedValCircle[0];
	     	 }
	   	}
   	 	document.getElementById("hiddenCircleSelecIds").value =selectedCircleValues;
	   	//	alert("circleId Selected : "+selectedCircleValues);
			if(document.getElementById("tsmId").value ==""){
				alert("Please Select atleast one TSM");
				return false;
	   		}
	  		if(document.forms[0].tsmId.length ==1){
				alert("No TSM exists under the Cirlce");
				return false;
	   		}
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
	        selectedValTSM = document.forms[0].tsmId[i].value.split(",");
     		selectedTsmValues += selectedValTSM[0];
	     	 }
	   	}
   }
   		 document.getElementById("hiddenTsmSelecIds").value =selectedTsmValues;
   		// alert("TSMs Selected : "+selectedTsmValues); 
   		
			if(document.getElementById("distId").value ==""){
				alert("Please Select atleast one Distributor");
				return false;
	   		}
	   		if(document.forms[0].distId.length ==1){
				alert("No Distributor exists under the TSM");
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
	     	selectedValDist = document.forms[0].distId[i].value.split(",");
     		selectedDistValues += selectedValDist[0];
	     	 }
	   	}
   		}
   		 document.getElementById("hiddenDistSelecIds").value =selectedDistValues;
	  	
	 // 	alert("Distributor Selected : "+selectedDistValues);
	  	if(document.getElementById("productId").value ==""){
				alert("Please Select atleast one Product Category");
				return false;
	   		}
	   		var selectedProductValues = "";
	   		if (document.forms[0].productId[0].selected){
		
			for (var i=1; i < document.forms[0].productId.length; i++)
  	 		{
   		
     			if(selectedProductValues !=""){
					selectedProductValues += ",";
     			}
     		var selectedValProduct = document.forms[0].productId[i].value.split(",");
     		selectedProductValues += selectedValProduct[0];
   			}
			}	
		else{
			for (var i=1; i < document.forms[0].productId.length; i++)
	  	 	{
	   		 if (document.forms[0].productId[i].selected)
	     	 {
	     		if(selectedProductValues !=""){
					selectedProductValues += ",";
     			}
	     	selectedValProduct = document.forms[0].productId[i].value.split(",");
     		selectedProductValues += selectedValProduct[0];
	     	 }
	   		}
   		}
   		 document.getElementById("hiddenProductSeletIds").value =selectedProductValues;
   		//alert("Products Selected : "+selectedProductValues);
   		
   	//	alert("Reco Period Selected : "+document.getElementById("recoID").value); 
   	//	alert("Ok not ok value  : "+document.getElementById("oknotok").value);
   	
   	if(document.getElementById("recoID").value =="-1"){
				alert("Please Select Reco Period");
				return false;
	   		}
   		if((document.getElementById("oknotok").value !="ok")&& (document.getElementById("oknotok").value !="notok")){
				alert("Please Select Distributor Type");
				return false;
	   		}
   		
   	
	   
		document.forms[0].action = "stbFlushOutForDist.do?methodName=exportExcel";
		//document.getElementById("excelBtn").disabled = true;
		document.forms[0].submit();
	}
	
	function CallErrorFile(){
	//alert("Hii Call error File ");
	document.forms[0].action = "stbFlushOutForDist.do?methodName=showErrorFile";
	document.forms[0].submit();
	}

function getDistName(){
	var tsmId = document.getElementById("tsmId").value;
	var selectedTsmValues ="";
	//alert("tsmId : "+tsmId);
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
   	//	 alert("selectedTsmValues : "+selectedTsmValues); 
   		 getAllAccountsUnderMultipleAccounts(selectedTsmValues,'distId');
 }
}
function getTsmName() 
{

	    var accountLevel =  '<% out.print(Constants.ACC_LEVEL_TSM); %>';
		var circleId = document.getElementById("circleId").value;
		//alert("circleId : "+circleId);
		var selectedCircleValues ="";
		var selectedCircleValues1 =0;
		getAllAccountsUnderMultipleAccounts(selectedCircleValues1,'distId');
		
	if(circleId ==""){
		alert("Please Select atleast one Circle");
		return false;
	 }
	 if(document.forms[0].circleId.length ==1){
			alert("No Circle exists");
		return false;
	   }
 	else{
 	//code commented by Neetika as its not required we dont ave PAN india option on this page
	 /*	if (document.forms[0].circleId[0].selected){
			alert("calculating for pan in	dia");
			/*for (var i=1; i < document.forms[0].circleId.length; i++)
  	 		{
   		
     			if(selectedCircleValues !=""){
					selectedCircleValues += ",";
     			}*/
     		//var selectedValCircle = document.forms[0].circleId[0].value;
     		//selectedCircleValues = selectedValCircle;
   			//}
		/*}	
		else{ */
			//	alert("calculating for other circles");
					for (var i=0; i < document.forms[0].circleId.length; i++)
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
   			//}
   	 	document.getElementById("hiddenCircleSelecIds").value =selectedCircleValues;
   	 //	alert("accountLevel : "+accountLevel);
   	 	//alert("selectedCircleValues : "+selectedCircleValues);
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
		if(selCount==ctrLength-1){
				ctrl[0].selected=true;
		}
		if(selCount==ctrLength-2){
				ctrl[0].selected=false;
		}
		
		
		if (document.forms[0].tsmId[0].selected){
			for (var i=1; i < document.forms[0].tsmId.length; i++){
  	 			document.forms[0].tsmId[i].selected =true;
     		}
   		}
   	
}

function uploadToSystem()
{

	if(document.getElementById("uploadedFile").value == "")
	{
		alert("Please Select File To Be Uploaded");
		return false;
	}
	  if(document.getElementById("recoID").value =="-1"){
				alert("Please Select Reco Period");
				return false;
	   }
	/*document.forms[0].action="stbFlushOutForDist.do?methodName=uploadExcel";
	loadSubmit();
	document.forms[0].submit(); */
	var  recoId =  document.getElementById("recoId").value;
	//alert("Reco Id "+ document.getElementById("recoId").value);
	document.forms[0].action = "stbFlushOutForDist.do?methodName=uploadExcel&recoId="+recoId;
	document.getElementById("uploadBtn").disabled = true;
		document.forms[0].submit();
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
function selectAllProduct(){
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

</SCRIPT>

</HEAD>

<BODY background="${pageContext.request.contextPath}/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onload="">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%" 	height="100%" valign="top">
	
	
	
	<html:form name="CloseDistributorRecoBean" action="/stbFlushOutForDist" type="com.ibm.reports.beans.CloseDistributorRecoBean"  method="post"  enctype="multipart/form-data" >

	<html:hidden property="hiddenCircleSelecIds" name="CloseDistributorRecoBean" />
	<html:hidden property="hiddenTsmSelecIds" name="CloseDistributorRecoBean" />
	<html:hidden property="hiddenDistSelecIds" name="CloseDistributorRecoBean" />
	<html:hidden property="hiddenProductSeletIds" name="CloseDistributorRecoBean" />
	<TR valign="top">
		
		<td>
			<TABLE width="600" border="0" cellpadding="5" cellspacing="0" class="text">
				<TR>
					<td colspan=4 align=left><strong><font size="5">Close Reco For Distributor</font></strong></td>
		
				</TR>
				<tr></tr>
    		    
    		    	<tr>
				  	<td  align="center" colspan="2"><strong><font color="red">
					 <bean:write property="strmsg" name="CloseDistributorRecoBean" /> </font></strong>
					 </td>
				  </tr>	
    		    
			<TR>
							
					<TD align="center"><b class="text pLeft15"> <bean:message
						bundle="view" key="productcreate.circlelist" /><font color="red">*</font></b>
					</td>
				<td>	
						
						<html:select  multiple="true"  property="circleId" styleId="circleId" style="width:150px;" size="6" onchange="getTsmName();">
							<logic:notEmpty name="CloseDistributorRecoBean" property="circleList">
								 <html:optionsCollection name="CloseDistributorRecoBean"	property="circleList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
				
				
					<TD align="center"><b class="text pLeft15"> TSM<font color="red">*</font></b>
					</td>
				<td>	
						<html:select  multiple="true"  property="tsmId" styleId="tsmId" style="width:150px;" size="6" onchange="getDistName();selectAllTSM();">
							<logic:notEmpty name="CloseDistributorRecoBean" property="tsmList">
							 <html:optionsCollection name="CloseDistributorRecoBean"	property="tsmList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
				
				</TR>
				
				
					<TR>
					
					<TD align="center"><b class="text pLeft15"> Distributor<font color="red">*</font></b>
					</td>
				<td>	
						<html:select  multiple="true"  property="distId" styleId="distId" style="width:150px;" size="6" onchange="selectAllDist();" >
							<logic:notEmpty name="CloseDistributorRecoBean" property="distList">
								 <html:optionsCollection name="CloseDistributorRecoBean"	property="distList" label="strText" value="strValue"/> 
							</logic:notEmpty>
						</html:select>
						</TD>
				
				<TD align="center"><b class="text pLeft15"> Product Type<font color="red">*</font></b>
					</td>
				<td colspan="3">	
						
						<html:select  multiple="true"  property="productId" styleId="productId" style="width:150px;" size="6" onchange="selectAllProduct();">
							
							<html:option value="-1">-All Products-</html:option>
							<logic:notEmpty name="CloseDistributorRecoBean" property="productList">
								 <html:optionsCollection name="CloseDistributorRecoBean"	property="productList" label="productCategory" value="productCategoryId"/> 
							</logic:notEmpty>
						</html:select>
					
						
						</TD>
						
				</TR>	
				
				
				
				<TR>
				    <TD align="center"><b class="text pLeft15"> Reco Period <font color="red">*</font></b>
					</td>
					<td><html:select
						styleId="recoID" property="recoID"
						name="CloseDistributorRecoBean" style="width:150px"
						style="height:20px">
						<html:option value="-1" >
							--Select--
						</html:option>
						<logic:notEmpty name="CloseDistributorRecoBean" property="recoListTotal">
							<bean:define id="recoListTotal" name="CloseDistributorRecoBean" property="recoListTotal" />
						<html:options labelProperty="strText" property="strValue" collection="recoListTotal" />
						
						</logic:notEmpty>
					</html:select></TD>
					<TD align="center"><b class="text pLeft15"> Distributor Type <font color="red">*</font></b>
					</td>
					<TD>
					<html:select name="CloseDistributorRecoBean" size="6" property="oknotok" styleId="oknotok" style="width:150px">
					<html:option value="-1">-- Select --</html:option>
					<html:option value="ok">Distributor OK</html:option>
					<html:option value="notok">Distributor Not OK</html:option>
					</html:select>
					</TD>
			     </TR>
				
				
				<tr>
					<td colspan="2" align="center">
						<html:button property="excelBtn" value="Export To Excel" onclick="return exportToExcel();"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<TD class="txt" align="right" width="35%"><B>
				<bean:message bundle="dp"
						key="bulkupload.file" /> :
				</B><font color="red">*</font>
				</TD>
				<TD class="txt" align="left" width="65%"><html:file property="uploadedFile"></html:file>
				</TD>
				</tr>
				
				<tr>
			<td align="right" colspan="5">
			<B><font color="red" >Please ensure that uploaded file does not contain blank cell</font></B>
	
			</td>
			</tr>
	 	<tr>
					<td colspan="4" align="center">
						<html:button property="uploadBtn" value="Upload To System" onclick="return uploadToSystem();"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
			</table>
			
			<table>
    		 <logic:equal name="CloseDistributorRecoBean" property="error_flag" value="true" >
				  <tr>
				  	<td  align="center" colspan="5"><strong><font color="red">
							  The transaction could not be processed due to some errors. Click on <html:button property="uploadBtn" value="View Details" onclick="return CallErrorFile();"></html:button>
					  for details.</font><strong>
					 </td>
				  </tr>
			</logic:equal>
				 
				 <!--  
				  <tr>
				  	<td  align="center" colspan="2"><strong><font color="red">
					 <bean:write property="strmsg" name="CloseDistributorRecoBean" /> </font></strong>
					 </td>
				  </tr>	-->
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