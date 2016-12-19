<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@page import="java.util.ArrayList" %>
<%@page import="com.ibm.hbo.dto.DistStockDTO" %>
<%try{ %>   
<html>
<head>
<link rel="stylesheet" href="../../theme/Master.css" type="text/css">
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT language="JavaScript" 
  src="<%=request.getContextPath()%>/scripts/subtract.js"
	type="text/javascript"></SCRIPT>
	
	
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/scripts/validation.js" type="text/javascript">
</SCRIPT>

<script language="JavaScript">
function prodList()
{
var Index = document.getElementById("category").selectedIndex;
var element=document.getElementById("category").options[Index].value;
var prodCategory=element.substring(0,element.indexOf("#"));
var url= "initAssignADStock.do?methodName=getProdList&cond1="+prodCategory;
var elementId = "product" ;
ajax(url,elementId);
} 

function userToList()
{
var Index = document.getElementById("accountFrom").selectedIndex;
var element=document.getElementById("accountFrom").options[Index].value;
var url= "initAssignADStock.do?methodName=getAssignTo&cond1="+element+"&cond2=RETAssign";
var elementId = "accountTo" ;
ajax(url,elementId);
}
function showFoc(){
	if(document.getElementById("foc").value==1)
		document.getElementById("focamount").style.display="inline";
	else
		document.getElementById("focamount").style.display="none";
}


function availableQty(){

//document.getElementById("find").style.display="none";
if(document.getElementById("category").value==""){
	alert("Please Select Product Category First");
	document.getElementById("accountFrom").value=""
	return false;
}
if(document.getElementById("product").value==""){
	alert("Please Select Product First");
	document.getElementById("accountFrom").value=""
	return false;
}

if(document.getElementById("accountFrom").value==""){
	//alert("Please Select Account From First");
	document.getElementById("availableProdQty").value=0;
	document.getElementById("product").value=="";
	return false;
}

var Index = document.getElementById("accountFrom").selectedIndex;
var accountFrom=document.getElementById("accountFrom").options[Index].value;

var Indexprod=document.getElementById("product").selectedIndex;
var productId=document.getElementById("product").options[Indexprod].value;
var productName=document.getElementById("product").options[Indexprod].text;

var Index = document.getElementById("category").selectedIndex;
var element=document.getElementById("category").options[Index].value;
var flag=element.substring(element.indexOf("#")+1,element.length);

var url= "initAssignADStock.do?methodName=getStockQtySecondorySale&cond1="+accountFrom+"&cond2="+productId+"&cond3="+flag;
var elementId = "availableProdQty" ;
ajaxText(url,elementId,"text");
}


function ajax(url,elementId){
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
		getAjaxValues(elementId);
	}
	req.open("POST",url,false);
	req.send();
}

function getAjaxValues(elementId)
{
	if (req.readyState==4 || req.readyState=="complete") 
  	{
		
		var xmldoc = req.responseXML.documentElement;
		
		if(xmldoc == null)
		{		
			var selectObj = document.getElementById(elementId);
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option(" -Select "+ elementId + "-", "");
			return;
		}
		optionValues = xmldoc.getElementsByTagName("option");
		var selectObj = document.getElementById(elementId);
		selectObj.options.length = 0;
		selectObj.options[selectObj.options.length] = new Option(" -Select "+ elementId + "-", "");
		for(var i=0; i<optionValues.length; i++){
		   selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
		}

	}

}
function ajaxText(url,elementId,text){
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
		getAjaxTextValues(elementId);
	}
	req.open("POST",url,false);
	req.send();
}
function getAjaxTextValues(elementId)
{
	
	if (req.readyState==4 || req.readyState=="complete") 
	{
		var text=req.responseText;
		if(text!=null)
		document.getElementById(elementId).value = text; 		
		else
		document.getElementById(elementId).value = "0" ; 
		
	}
	
}

	function hide()
	{
		var Index = document.getElementById("category").selectedIndex;
		var element=document.getElementById("category").options[Index].value;
		
		var flag=element.substring(element.indexOf("#")+1,element.length);
		//document.getElementById("startSno").style.display = document.getElementById("endSno").style.display = (flag == 'Y' || flag == 'y')?'inline':'none';
		document.getElementById("find").style.display = (flag == 'Y' || flag == 'y')?'inline':'none';
		//document.getElementById("find").style.display ="none";
		document.getElementById("product").value="";	
		document.getElementById("accountFrom").value="";
		document.getElementById("accountTo").value="";
		document.getElementById("assignedProdQty").value=0;
		//document.getElementById("endingSno").value="";
		//document.getElementById("startingSerial").value="";
		document.getElementById("availableProdQty").value=0;
				
	}
	
	function validateData()
	{
	//alert(document.getElementById("category").value);
	if(document.getElementById('category').value == ""){
			alert("Please Select Product Category");
			document.forms[0].category.focus();
			return 0;
	}

	if(document.getElementById('product').value == ""){
			alert("Please Select Product");
			document.forms[0].product.focus();
			return 0;
	 }
	if(document.getElementById('accountFrom').value == ""){
			alert("Please Select The Sender's Account");
			document.forms[0].accountFrom.focus();
			return 0;
	}
		if(document.getElementById('accountTo').value == ""){
			alert("Please Select The Receiver's Account");
			document.forms[0].accountTo.focus();
			return 0;
	}
	var category=document.getElementById("category").options[document.getElementById('category').selectedIndex].text;
	if(category.toLowerCase() == 'handset'){
		if(document.getElementById("assignedProdQty").value==0){
		alert("Assigned Quantity Cannot Be 0");
		return 0;
	}
	
	if (IsNumeric(document.getElementById('assignedProdQty').value) == false) 
      {
      alert("Please Check - Non Numeric Value!");
      return 0;
      }
	
	if(parseInt(document.getElementById("assignedProdQty").value)>parseInt(document.getElementById("availableProdQty").value)){
		alert("Assigned Quantity Cannot Be Greater Than Available Quantity");		
		return 0;
	}
		if(document.getElementById("assignedProdQty").value==0){
			alert("Assigned Quantity Cannot Be 0");
			return 0;
		}
	}

	if(category.toLowerCase() != 'handset'&& category!='Paper Recharge'&& category.toLowerCase()!='Easy Recharge'){
	/*if(document.getElementById('startingSerial').value == ""){
			alert("Please Enter The Starting Serial No.");
			document.forms[0].startingSerial.focus();
			return 0;
	}
	if(document.getElementById('endingSno').value == ""){
			alert("Please Enter The Ending Serial No.");
			document.forms[0].endingSno.focus();
			return 0;
	}*/
	if(document.getElementById("assignedProdQty").value==0){
		alert("Assigned Quantity Cannot Be 0");
		return 0;
	}
	
     /* if (IsNumeric(document.getElementById('startingSerial').value) == false) 
      {
      alert("Please Check Starting Serial No.- Non Numeric Value! ");
      return 0;
      }
      
      if (IsNumeric(document.getElementById('endingSno').value) == false) 
      {
      alert("Please Check Ending Serial No.- Non Numeric Value!");
      return 0;
      }
	*/
	  if (IsNumeric(document.getElementById('assignedProdQty').value) == false) 
      {
      alert("Please Check Assigned Quantity- Non Numeric Value!");
      return 0;
      }
      
	if(parseInt(document.getElementById("assignedProdQty").value)>parseInt(document.getElementById("availableProdQty").value)){
		if (confirm("Assigned Quantity Cannot Be Greater Than Available Quantity")) {
			return 0;
		}else { return 1 };
	}
	var a=document.getElementById("startingSerial").value;
	var b=document.getElementById("endingSno").value;
	//var diff=Sub(b,a);

	//diff = parseInt(diff)+1;
	/*if(diff<0){
		alert("Starting Serial No Should Be Less Than Ending Serial No.");
		return 0;
	}
	if((document.getElementById("assignedProdQty").value)!=diff){
		if (confirm("Serial No Assigned Is Not Same As Assigned Quantity ")) {
			return 1;
		}else { return 0 };
	} */
 }
 	if(document.getElementById("remarks").length>250){
 		alert("Max Character Length Of Remarks Is 250");
 	}
 	if(document.getElementById('billno').value == ""){
			alert("Please Enter The Bill No");
			document.forms[0].billno.focus();
			return 0;
	 }
	 
	 var billnum=trim(document.getElementById('billno').value);
		if(billnum == "")
		{
		alert("Please Enter The valid Bill No");
		document.getElementById('billno').value='';
		document.forms[0].billno.focus();
		return 0;
		}
	 if(document.getElementById('vat').value == "" && document.getElementById('foc').value==1){
			alert("Please Enter VAT");
			//document.forms[0].vat.focus();
			return 0;
	 }
	 if(IsNumericOrDot(document.getElementById("vat").value)== false && document.getElementById('foc').value==1){
	 		alert("Please Check VAT- Non Decimal Value!");
	 		//document.forms[0].vat.focus();
	 		return 0;
	 }
	
	return 1;
	}
	
function IsNumeric(strString)
   //  check for valid numeric strings	
   {
   var strValidChars = "0123456789";
   var strChar;
   var blnResult = true;

   if (strString.length == 0) return false;

   //  test strString consists of valid characters listed above
   for (i = 0; i < strString.length && blnResult == true; i++)
      {
      strChar = strString.charAt(i);
      if (strValidChars.indexOf(strChar) == -1)
         {
         blnResult = false;
         }
      }
   return blnResult;
   }

function IsNumericOrDot(strString)
   //  check for valid numeric strings	
   {
   var strValidChars = "0123456789.";
   var strChar;
   var blnResult = true;

   if (strString.length == 0) return false;

   //  test strString consists of valid characters listed above
   for (i = 0; i < strString.length && blnResult == true; i++)
      {
      strChar = strString.charAt(i);
      if (strValidChars.indexOf(strChar) == -1)
         {
         blnResult = false;
         }
      }
   return blnResult;
   }
 
function prodAmount(){
	var indexSelected=document.getElementById("product").selectedIndex;
	var assignedProdQty=document.getElementById("assignedProdQty").value;
	var elementId = "rate";
	var url= "initAssignADStock.do?methodName=getProductRate&cond1="+indexSelected+"&cond2="+assignedProdQty;
	ajaxText(url,elementId,"text");
}

function insertData(){

//alert(document.getElementById("startingSerial").value);return false;
 //alert('return value=' + validateData()); return false
		if( validateData()== 1){

			//var	commentId= assignTable();
			var categoryId = document.getElementById("category").value;	
			var productId = document.getElementById("product").value;	
			var accountFrom=document.getElementById("accountFrom").value;
			var accountTo = document.getElementById("accountTo").value;
			var assignedProdQty = document.getElementById("assignedProdQty").value;
			
			var endingSno = document.getElementById("endingSno").value;
			var startingSerial = document.getElementById("startingSerial").value;
			
			var availableProdQty = document.getElementById("availableProdQty").value;
			var productName= document.getElementById("product").options[document.getElementById('product').selectedIndex].text;
			var remarks=document.getElementById("remarks").value;
			var billno = document.getElementById("billno").value;
			var vat;
			if(document.getElementById("foc").value==0)
			vat=0.0;
			else			
			vat = document.getElementById("vat").value;
			var rate = document.getElementById("rate").value;
			//var rate = 0.0;
			
			
			
			/*
			alert("categoryId :"+categoryId);
			alert("productId :"+productId);
			alert("accountFrom :"+accountFrom);
			alert("assignedProdQty :"+assignedProdQty);
			alert("startingSerial :"+startingSerial);
			alert("endingSno :"+endingSno);
			alert("availableProdQty"+availableProdQty);
			return false;
			var url= "initAssignADStock.do";
			var param="methodName=insertNewRetailerData&cond1="+categoryId+"&cond2="+productId+"&cond3="+
			accountFrom+"&cond4="+accountTo+"&cond5="+assignedProdQty+"&endingSno="+endingSno+"&startingSerial="
			+startingSerial+"&cond8="+availableProdQty+"&cond9="+productName+"&remarks="+remarks+"&billno="+billno+"&vat="+vat+"&rate="+rate;
			var elementId = commentId ;resetValues();
			ajaxTextPost(url,elementId,"text",param);*/
			//ajaxText(url,elementId,"text");
			//rajiv jha added
			
			document.getElementById("cond1").value = categoryId;
			document.getElementById("cond2").value = productId;
			document.getElementById("cond3").value = accountFrom;
			document.getElementById("cond4").value = accountTo;
			document.getElementById("cond5").value = assignedProdQty;
			document.getElementById("cond8").value = availableProdQty;
			document.getElementById("cond9").value = productName;
			document.getElementById("cond9").value = productName;
			document.getElementById("vat").value = vat;
			document.getElementById("rate").value = rate;
			
			document.getElementById("accountFromName").value = document.getElementById("accountFrom").options[document.getElementById('accountFrom').selectedIndex].text;
			document.getElementById("accountToName").value = document.getElementById("accountTo").options[document.getElementById('accountTo').selectedIndex].text;
			
			
			
			document.forms[0].action="initAssignADStock.do?methodName=insertNewRetailerData";
			document.forms[0].method="POST";
			document.forms[0].submit();
		}	
	}
	
	
	function assignTable()
	{
		var tbl = document.getElementById('assignTable');
		tbl.style.display='inline'
		var lastRow = tbl.rows.length;
		// if there's no header row in the table, then iteration = lastRow + 1
		var iteration = lastRow;
		var row = tbl.insertRow(lastRow);
	  
		// left cell
		var cellProduct = row.insertCell(0);
		cellProduct.innerHTML = document.getElementById("product").options[document.getElementById('product').selectedIndex].text;
	  
		var cellAssignFrom = row.insertCell(1);
		cellAssignFrom.innerHTML = document.getElementById("accountFrom").options[document.getElementById('accountFrom').selectedIndex].text;
	  
		var cellAssignTo = row.insertCell(2);
		cellAssignTo.innerHTML = document.getElementById("accountTo").options[document.getElementById('accountTo').selectedIndex].text;
	  
		var cellAssignedQty = row.insertCell(3);
		cellAssignedQty.innerHTML = document.getElementById("assignedProdQty").value;
	  
		// rightmost cell
		var cellComments = row.insertCell(4);
		var oTextBox = document.createElement('input');
		oTextBox.type = 'text';
		oTextBox.name = 'txtRow' + iteration;
		oTextBox.id = 'txtRow' + iteration;
		oTextBox.size = 40;
		oTextBox.setAttribute('readOnly',"readonly");
		cellComments.appendChild(oTextBox);
		
		return oTextBox.id; 
	 }
 
 	function resetting()
 	{
 	    document.getElementById("foc").value=''; 		
 		document.getElementById("remarks").value=''; 		
 		document.getElementById("category").value="";	
		document.getElementById("product").value="";	
		document.getElementById("accountFrom").value="";
		document.getElementById("accountTo").value="";
		document.getElementById("assignedProdQty").value=0;
		
		//document.getElementById("endingSno").value="";
		document.getElementById("startingSerial").value="";
		
		document.getElementById("availableProdQty").value=0;
		document.getElementById("billno").value="";
		document.getElementById("vat").value="";
		document.getElementById("rate").value="";
		document.getElementById("find").style.display ='none';
		document.getElementById("focamount").style.display ='none';
		document.getElementById("foc").value="0";
 	}
 	
 	function resetValues()
 	{
 	    document.getElementById("foc").value=''; 		
 	    document.getElementById("remarks").value=''; 		
 		document.getElementById("accountFrom").value="";
		document.getElementById("accountTo").value="";
		document.getElementById("assignedProdQty").value=0;
		
		//document.getElementById("endingSno").value="";
	   document.getElementById("startingSerial").value="";
		
		document.getElementById("availableProdQty").value=0;
		document.getElementById("find").style.display ='none';
		document.getElementById("rate").value=""; 
		document.getElementById("focamount").style.display ='none';
		document.getElementById("foc").value="0";
		productUnit();
 	}
 	
 	function maxlength() {
	var size=250;
	var remarks = document.getElementById("remarks").value;
    	if (remarks.length >= size) {
    	remarks = remarks.substring(0, size-1);
    	document.getElementById("remarks").value = remarks;
	    alert ("Remarks  Can Contain 250 Characters Only.");
    	document.getElementById("remarks").focus();
 	    return false;
    	}
   } 	
   
   
	function OpenSerialWindow()
	{
	  document.getElementById("rate").value='';
	  var availQty = document.getElementById("availableProdQty").value;
	  	  
	  var url="viewSerialNoList.do?methodName=viewSerialNoList&intSRAvail="+availQty+"&funcFlag=S&cond1="+document.getElementById("accountFrom").value+"&cond2="+document.getElementById("accountTo").value+"&cond3="+document.getElementById("product").value+"&cond4="+document.getElementById("category").value;
	  window.open(url,"SerialNo","width=900,height=500,scrollbars=yes,toolbar=no");
   }

	function showHideFind(){
	    //alert(document.getElementById("assignedProdQty").readonly);
	    var Index = document.getElementById("category").selectedIndex;
		var element=document.getElementById("category").options[Index].value;
		var flag=element.substring(element.indexOf("#")+1,element.length);
	    if(flag == 'Y'||flag == 'y')
		{
		document.getElementById("assignedProdQty").disabled = true;
		}
		else
		{
		document.getElementById("assignedProdQty").disabled = false;
		}

		if(document.getElementById("availableProdQty").value ==0 || flag == 'N'){
		document.getElementById("find").style.display ="none";}
		/*if(document.getElementById("category").value!="" && document.getElementById("product").value!="" && document.getElementById("accountFrom").value!="" && document.getElementById("accountTo").value!=""){
			document.getElementById("find").style.display="inline";
		}*/	
		else{
			document.getElementById("find").style.display="inline";
		}
	}
	
	function productUnit(){
	var Index = document.getElementById("product").selectedIndex;
	var productName=document.getElementById("product").options[Index].value;
	var url= "initAssignADStock.do?methodName=getProductUnit&cond1="+productName;
	var elementId = "unitName" ;
	ajaxText1(url,elementId,"text");
	}
	function ajaxText1(url,elementId,text){
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
			getAjaxTextValues1(elementId);
		}
		//alert("before req.oper"+req);
		req.open("POST",url,false);
		req.send();
	}
	
	//rajiv jha changes for ie6 start
function ajaxTextPost(url,elementId,text,param){
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
		getAjaxTextValues(elementId);
	}
	req.open("POST",url,true);
	req.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	//alert(param);
	req.send(param);
	
}
	
	function getAjaxTextValues1(elementId)
	{	
		if (req.readyState==4 || req.readyState=="complete") 
		{
			var text=req.responseText;
			 if(text.length!=0){
			 	document.getElementById("showUnit").innerHTML=text;
			 	document.getElementById("showUnit1").innerHTML=text;
			 	//document.getElementById(elementId).value = text;
			 }
			else if (text.length==0){
			 	document.getElementById("showUnit").style.display="none";
			 	document.getElementById("showUnit1").style.display="none";
			}
		}
	}
</script>



</head>
<body> 
<html:form action="/initAssignADStock.do">
<html:hidden property="methodName" styleId="methodName"/>
<html:hidden property="cond1"/>
<html:hidden property="cond2"/>
<html:hidden property="cond3"/>
<html:hidden property="cond4"/>
<html:hidden property="cond5"/>
<html:hidden property="cond8"/>
<html:hidden property="cond9"/>

<html:hidden property="accountFromName" />
<html:hidden property="accountToName" />

	<TABLE>
		<TBODY>
		<tr>
			<TD colspan="4" class="text"><BR>
				<IMG src="<%=request.getContextPath()%>/images/secondarySales.gif"
				width="505" height="22" alt="">
			</TD>
		</tr>
		</TBODY>
	</TABLE>
	<TABLE width="80%" border="0" cellpadding="1" cellspacing="1"
		align="center" class ="border">
		<TBODY>
			<TR>
				<TD width="25%"></TD>
				<TD width="55%"></TD>
			</TR>
			<TR  id="busCategory">
				<TD class="txt" ><bean:message bundle="hboView" key="assignADStock.Category"/><font color="red">*</font></TD>
				<TD class="txt" >
				<html:select property="category" onchange="prodList();" style="width:300px">
					<logic:notEmpty property="arrCategory" name="AssignDistStockForm" >
						<html:option value="">--Select A Category--</html:option>
						<bean:define id="category" name="AssignDistStockForm" property="arrCategory" />
						<html:options labelProperty="bname" property="bcodeFlag" collection="category" />
					</logic:notEmpty>
				</html:select>
				</TD>
			</TR>
			<TR>
			<TD class="txt"><bean:message bundle="hboView" key="assignADStock.Product"/><font color="red">*</font></TD>
				<TD class="txt">
				<html:select property="product"	onchange="resetValues()" style="width:300px">
						<html:option value="">--Select A Product--</html:option>
				</html:select></TD>
			</TR>

			<tr>
				<TD class="txt" width="222"><bean:message bundle="hboView" key="assignADStock.AssignFrom"/><font color="red">*</font></td>
				<TD class="txt" width="200">
				<html:select property="accountFrom" onchange="availableQty();userToList();" style="width:305px">
						<html:option value="">--Select Account--</html:option>
					<logic:notEmpty property="arrAssignFrom" name="AssignDistStockForm">
						<bean:define id="assignFrom" name="AssignDistStockForm" property="arrAssignFrom" />
						<html:options labelProperty="userName" property="userId" collection="assignFrom" />
					</logic:notEmpty>
				</html:select>
				
				</td>
			</tr>
			<tr>
				<TD class="txt" width="222"><bean:message bundle="hboView" key="assignADStock.AssignTo"/><font color="red">*</font></td>
				<TD class="txt" width="200">
				<html:select property="accountTo" style="width:305px" onchange="showHideFind();">
					<html:option value="">--Select Account--</html:option>
					<logic:notEmpty property="arrAssignTo" name="AssignDistStockForm">
						<bean:define id="assignTo" name="AssignDistStockForm" property="arrAssignTo" />
						<html:options labelProperty="userName" property="userId" collection="assignTo" />
					</logic:notEmpty>
				</html:select></td>
			</tr>
			<TR>
				<TD class="txt" width="222"><bean:message bundle="hboView" key="assignADStock.AvlProdQty"/><font color="red">*</font></TD>
				<TD class="txt" width="200"><html:text name="AssignDistStockForm"
					property="availableProdQty" readonly="true" style="width:305px"/>&nbsp;<b id="showUnit"></b>
				</TD>
			</TR>
			<input type="hidden" name="endingSno">
			<input type="hidden" name="startingSerial">
			
			
		<!-- rajiv jha comented for replacing total serial nos	
			<TR style="display:none" id="startSno">
				<TD class="txt" width="222"><bean:message bundle="hboView" key="assignADStock.StartingSerial"/><font color="red">*</font></TD>
				<TD class="txt" width="200"><html:text name="AssignDistStockForm" 
					property="startingSerial" style="width:305px" maxlength="20" readonly="true"></html:text>
				</TD>
				
			</TR>
			
			<TR style="display:none" id="endSno">
				<TD class="txt" width="222"><bean:message bundle="hboView" key="assignADStock.EndingSno"/><font color="red">*</font></TD>
				<TD class="txt" width="200"><html:text name="AssignDistStockForm"
					property="endingSno" style="width:305px" maxlength="20" readonly="true" ></html:text><div id="find" style="display:none;">&nbsp;&nbsp;&nbsp;<a href="#" onClick="OpenSerialWindow()">Find</a></div></TD>
			</TR>	
			 
			  
			 <TR style="display:none" id="startSno">
				<TD class="txt" width="222">Total Serials<font color="red">*</font></TD>
				<TD class="txt" width="200"><html:text name="AssignDistStockForm" 
					property="startingSerial" style="width:305px" maxlength="20" readonly="true"></html:text>
				</TD>
				
			</TR>
			
			<TR style="display:none" id="endSno">
				<TD class="txt" width="222">Total Serial Nos<font color="red">*</font></TD>
				<TD class="txt" width="200"><html:text name="AssignDistStockForm"
					property="endingSno" style="width:305px" maxlength="20" readonly="true" ></html:text><div id="find" style="display:none;">&nbsp;&nbsp;&nbsp;<a href="#" onClick="OpenSerialWindow()">Find</a></div></TD>
			</TR>	-->
			
				

			<TR>
				<TD class="txt" width="222"><bean:message bundle="hboView" key="assignADStock.AssignQty"/><font color="red">*</font></TD>
				<TD class="txt" width="200"><html:text name="AssignDistStockForm" onfocus="prodAmount()"
					property="assignedProdQty" readonly="false" style="width:305px" maxlength="6"/>&nbsp;<b id="showUnit1"></b>
					<!--<html:hidden property="prodlist"/>-->
					<div id="find" style="display:none;">&nbsp;&nbsp;&nbsp;<a href="#" onClick="OpenSerialWindow()">Find</a></div></TD>
			</TR>
			<TR>
				<TD class="txt" width="222"><bean:message bundle="hboView" key="assignADStock.Bill"/><font color="red">*</font></TD>
				<TD class="txt" width="200"><html:text name="AssignDistStockForm"onclick="prodAmount()"
					property="billno" style="width:305px" maxlength="20"></html:text></TD>
			</TR>
			<TR>
				<TD class="txt" width="222"><bean:message bundle="hboView" key="assignADStock.Rate"/><font color="red">*</font></TD>
				<TD class="txt" width="200"><html:text name="AssignDistStockForm"
					property="rate" style="width:305px" maxlength="5" readonly="true" onfocus="prodAmount()"></html:text></TD>
			</TR>
			<TR>
				<TD class="txt" width="222"><bean:message bundle="hboView" key="assignADStock.foc"/><font color="red">*</font></TD>
				<TD class="txt" width="200"><html:select name="AssignDistStockForm"
					property="foc" style="width:305px" onchange="showFoc()">
					<html:option value="0">Paid</html:option>
					<html:option value="1">FOC</html:option>					
					</html:select></TD>
			</TR> 
			<TR id="focamount" style="display:none">
				<TD class="txt" width="222"><bean:message bundle="hboView" key="assignADStock.Vat"/><font color="red">*</font></TD>
				<TD class="txt" width="200"><html:text name="AssignDistStockForm"
					property="vat" style="width:305px" maxlength="5" ></html:text></TD>
			</TR>
			<TR>
				<TD width="222"><bean:message bundle="hboView" key="assignADStock.Remarks"/></TD>
				<TD width="161"><html:textarea name="AssignDistStockForm"
					property="remarks" style="width:305px" onkeypress="return maxlength();"/></TD>
			</TR>
			<TR>
				<TD width="222"></TD>
				<TD width="161"></TD>
			</TR>
			<TR>
				<TD align="right" width="222"></TD>
				<TD align="left">
				<input type="button" value="Reset" onclick="resetting();"><input
					type="button" value="Secondary Sales" onclick="insertData();"></TD>
			</TR>
			<TR style="hidden" id="commentId">
			</TR>
			
			</TBODY>
	</TABLE>
	<br>
	<br>
	
<logic:equal value="SUCCESS" name="AssignDistStockForm" property="err_Msg">
	<TABLE id="assignTable" border="1" cellpadding="1" cellspacing="1" align="center" class ="border" >
	<tr bgcolor="#CD0504">
	<td><font color="white">Product</font></td>
	<td><font color="white">Assign From</font></td>
	<td><font color="white">Assign To</font></td>
	<td><font color="white">Assigned Qty</font></td>
	<td><font color="white">Comments</font></td>
	</tr>
	<tr >
	<td><font color="black"><bean:write name="AssignDistStockForm" property="productName" /></font></td>
	<td><font color="black"><bean:write name="AssignDistStockForm" property="accountFromName" /></font></td>
	<td><font color="black"><bean:write name="AssignDistStockForm" property="accountToName" /></font></td>
	<td><font color="black"><bean:write name="AssignDistStockForm" property="assignedProdQty" /></font></td>
	<td><font color="black"><bean:write name="AssignDistStockForm" property="remarks" /></font></td>
	</tr>
	</table>
	
	<script>
		resetting();
	</script>
	
</logic:equal>	
		
</html:form>
</body>
</html>
<%}catch(Exception e){
e.printStackTrace();
}%>