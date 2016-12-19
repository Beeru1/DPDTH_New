
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>


<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/theme/Master.css" type="text/css">
<title>Assign Stock</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT language="JavaScript" 
  src="<%=request.getContextPath()%>/scripts/subtract.js"
	type="text/javascript">
</SCRIPT>

<script language="JavaScript">
function prodList(){
var Index = document.getElementById("category").selectedIndex;
var element=document.getElementById("category").options[Index].value;
var prodCategory=element.substring(0,element.indexOf("#"));
var url= "initAssignADStock.do?methodName=getProdList&cond1="+prodCategory;
var elementId = "product" ;
ajax(url,elementId);
}

function userToList(){
var Index = document.getElementById("accountFrom").selectedIndex;
var element=document.getElementById("accountFrom").options[Index].value;
var url= "initAssignADStock.do?methodName=getAssignTo&cond1="+element+"&cond2=RETAssign";
var elementId = "accountTo" ;
ajax(url,elementId);
}

function availableQty(){
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

if(document.getElementById("accountFrom").value==""){
	alert("Please Select Account From First");
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
		if(req.status!=200){
			var selectObj = document.getElementById(elementId);
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Exception During Transaction....");
			return;
		}
		else{
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

function getAjaxTextValues(elementId)
{
	
	if (req.readyState==4 || req.readyState=="complete") 
	{
		if(req.status!=200){
			document.getElementById(elementId).value = "Exception During Transaction" ;
		}
		else{
			var text=req.responseText;
			if(text!=null)
			document.getElementById(elementId).value = text; 		
			else
			document.getElementById(elementId).value = "0" ; 
		}
			
	}
	
}

	function hide()
	{
		var Index = document.getElementById("category").selectedIndex;
		var element=document.getElementById("category").options[Index].value;
		
		var flag=element.substring(element.indexOf("#")+1,element.length);
		//document.getElementById("startSno").style.display = document.getElementById("endSno").style.display = (flag == 'Y' || flag == 'y')?'inline':'none';
		document.getElementById("find").style.display ="none";
		document.getElementById("product").value="";	
		document.getElementById("accountFrom").value="";
	//	document.getElementById("accountTo").value="";
		document.getElementById("assignedProdQty").value=0;
		//document.getElementById("endingSno").value="";
		//document.getElementById("startingSerial").value="";
		document.getElementById("availableProdQty").value=0;
		
	}
	
	function validateData(){
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
	if(document.getElementById("assignedProdQty").value==0){
		alert("Assigned Quantity Cannot Be 0");
		return 0;
	}
	if(document.getElementById("availableProdQty").value==0){
		alert("Available Product Quantity Cannot Be 0");
		return 0;
	}
	var category=document.getElementById("category").options[document.getElementById('category').selectedIndex].text;
	if(category.toLowerCase() == 'handset'){
		
	
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
		if(document.getElementById("availableProdQty").value==0){
			alert("Available Product Quantity Cannot Be 0");
			return 0;
		}
	}
	if(category.toLowerCase() == 'suk'){
	if(document.getElementById('startingSerial').value == ""){
			alert("Please Enter The Starting Serial No.");
			document.forms[0].startingSerial.focus();
			return 0;
	}
	if(document.getElementById('endingSno').value == ""){
			alert("Please Enter The Ending Serial No.");
			document.forms[0].endingSno.focus();
			return 0;
	}
	if(document.getElementById("assignedProdQty").value==0){
		alert("Assigned Quantity Cannot Be 0");
		return 0;
	}
	if(document.getElementById("availableProdQty").value==0){
		alert("Available Product Quantity Cannot Be 0");
		return 0;
	}
	
      if (IsNumeric(document.getElementById('startingSerial').value) == false) 
      {
      alert("Please Check Starting Serial No.- Non Numeric Value! ");
      return 0;
      }
      
      if (IsNumeric(document.getElementById('endingSno').value) == false) 
      {
      alert("Please Check Ending Serial No.- Non Numeric Value!");
      return 0;
      }
	
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
	var diff=Sub(b,a);

	diff = parseInt(diff)+1;
	
	if(diff<0){
		alert("Starting Serial No Should Be Less Than Ending Serial No.");
		return 0;
	}
	if(parseInt(document.getElementById("assignedProdQty").value)!=diff){
		if (confirm("Serial No Assigned Is Not Same As Assigned Quantity ")) {
			return 0;
		}else { return 1 };
	}
 }
 	if(document.getElementById("remarks").length>250){
 		alert("Max Character Length Of Remarks Is 250");
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

function insertData(){

		if( validateData()== 1){

			var	commentId= assignTable();
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

			var url= "initAssignADStock.do";
			var param="methodName=returnStock&cond1="+categoryId+"&cond2="+productId+"&cond3="+
			accountFrom+"&cond4="+accountTo+"&cond5="+assignedProdQty+"&cond6="+endingSno+"&cond7="
			+startingSerial+"&cond8="+availableProdQty+"&cond9="+productName+"&remarks="+remarks;
			
			var elementId = commentId ;
			ajaxTextPost(url,elementId,"text",param);
			
			//ajaxText(url,elementId,"text");
			// rajiv jha added
			resetValues();
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
 
 	function resetting(){
 		
 		document.getElementById("category").value="";	
		document.getElementById("product").value="";	
		document.getElementById("accountFrom").value="";
		document.getElementById("assignedProdQty").value=0;
		document.getElementById("endingSno").value="";
		document.getElementById("startingSerial").value="";
		document.getElementById("availableProdQty").value=0;
		document.getElementById("find").style.display ='none';
		document.getElementById("remarks").value = "";
	}
	
 	function resetValues(){
 		document.getElementById("accountFrom").value="";
		document.getElementById("assignedProdQty").value=0;
		document.getElementById("endingSno").value="";
		document.getElementById("startingSerial").value="";
		document.getElementById("availableProdQty").value=0;
		document.getElementById("find").style.display ='none';
				
 	}
 	
 	function maxlength() {
	var size=250;
	var remarks = document.getElementById("remarks").value;
    	if (remarks.length >= size) {
    	remarks = remarks.substring(0, size-1);
    	document.getElementById("remarks").value = remarks;
	    alert ("Remarks  can contain 250 characters only.");
    	document.getElementById("remarks").focus();
 	    return false;
    	}
   } 	

function OpenSerialWindow()
{
	  var url="viewSerialNoList.do?methodName=viewSerialNoList&cond1="+document.getElementById("accountFrom").value
	  +"&cond2="+document.getElementById("accountTo").value+"&cond3="+document.getElementById("product").value+"&cond4="+document.getElementById("category").value;
	 window.open(url,"SerialNo","width=900,height=500,scrollbars=yes,toolbar=no");
}

function showHideFind(){
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
	    //document.getElementById("assignedProdQty").readonly = (flag == 'Y' || flag == 'y')?'true':'false';
		//alert(flag);
		//alert(document.getElementById("assignedProdQty").readonly);
		//alert(document.getElementById("assignedProdQty").readonly = (flag == 'Y' || flag == 'y')?'true':'false');
		//alert(document.getElementById("assignedProdQty").readonly);
		if(document.getElementById("availableProdQty").value ==0 || flag == 'N'){
		document.getElementById("find").style.display ="none";}
		
		/*if(document.getElementById("category").value!="" && document.getElementById("product").value!="" && document.getElementById("accountFrom").value!="" && document.getElementById("accountTo").value!=""){
			document.getElementById("find").style.display="inline";		
		}*/	
		else{
			document.getElementById("find").style.display="inline";
		}
		
		
	}
			
</script>



</head>
<body> 
<html:form action="/initAssignADStock.do">
<html:hidden property="methodName" value="insertADStock"/>
	<TABLE>
		<TBODY>
		<tr>
			<TD colspan="4" class="text"><BR>
				<IMG src="<%=request.getContextPath()%>/images/returnStock.gif"
				width="505" height="22" alt="">
			</TD>
		</tr>
		</TBODY>
	</TABLE>
	
	<div>
	<TABLE style ="width:70%;" border="0" cellpadding="1" cellspacing="1"
		align="center" class ="border">
		<TBODY>
	
			<TR>
				<TD width="45%"></TD>
				<TD width="55%"></TD>
			</TR>
			<TR  id="busCategory">
				<TD class="txt" width="45%"><bean:message bundle="hboView" key="assignADStock.Category"/><font color="red">*</font></TD>
				<TD class="txt" width="55%">
				<html:select property="category" onchange="prodList();hide();" style="width:230px">
					<logic:notEmpty property="arrCategory" name="AssignDistStockForm" >
						<html:option value="">--Select A Category--</html:option>
						<bean:define id="category" name="AssignDistStockForm" property="arrCategory" />
						<html:options labelProperty="bname" property="bcodeFlag" collection="category" />
					</logic:notEmpty>
				</html:select>
				</TD>
			</TR>
			<TR>
			<TD class="txt" width="45%"><bean:message bundle="hboView" key="assignADStock.Product"/><font color="red">*</font></TD>
				<TD class="txt" width="55%">
				<html:select property="product"	onchange="resetValues()" style="width:230px">
						<html:option value="">--Select A Product--</html:option>
				</html:select></TD>
			</TR>

			<tr>
				<TD class="txt" width="45%"><bean:message bundle="hboView" key="assignADStock.AssignFrom"/><font color="red">*</font></td>
				<TD class="txt" width="55%">
				<html:select property="accountFrom" onchange="availableQty();showHideFind()" style="width:230px">
						<html:option value="">--Select Account--</html:option>
					<logic:notEmpty property="arrAssignFrom" name="AssignDistStockForm">
						<bean:define id="assignFrom" name="AssignDistStockForm" property="arrAssignFrom" />
						<html:options labelProperty="userName" property="userId" collection="assignFrom" />
					</logic:notEmpty>
				</html:select>
				
				</td>
			</tr>
			<tr>
				<TD class="txt" width="45%"><bean:message bundle="hboView" key="assignADStock.AssignTo"/><font color="red">*</font></td>
				<TD class="txt" width="55%">
				<html:select property="accountTo" style="width:230px">
					<logic:notEmpty property="arrAssignTo" name="AssignDistStockForm">
						<bean:define id="assignTo" name="AssignDistStockForm" property="arrAssignTo" />
						<html:options labelProperty="userName" property="userId" collection="assignTo" />
					</logic:notEmpty>
				</html:select></td>
			</tr>
			<TR>
				<TD class="txt" width="45%"><bean:message bundle="hboView" key="assignADStock.AvlProdQty"/><font color="red">*</font></TD>
				<TD class="txt" width="55%"><html:text name="AssignDistStockForm"
					property="availableProdQty" readonly="true" style="width:230px"></html:text></TD>
			</TR>
			<input type="hidden" name="endingSno">
			<input type="hidden" name="startingSerial">
		<!--  	<TR style="display:none" id="startSno">
				<TD class="txt" width="222"><bean:message bundle="hboView" key="assignADStock.StartingSerial"/><font color="red">*</font></TD>
				<TD class="txt" width="161"><html:text name="AssignDistStockForm" 
					property="startingSerial" style="width:230px" maxlength="20"></html:text></TD>
				
			</TR>
			
			<TR style="display:none" id="endSno">
				<TD class="txt" width="222"><bean:message bundle="hboView" key="assignADStock.EndingSno"/><font color="red">*</font></TD>
				<TD class="txt" width="161"><html:text name="AssignDistStockForm"
					property="endingSno" style="width:230px" maxlength="20"></html:text><div id="find" style="display:none;">&nbsp;&nbsp;&nbsp;<a href="#" onClick="OpenSerialWindow()">Find</a></div></TD>
			</TR>			
		-->
			<TR>
				<TD class="txt" width="45%"><bean:message bundle="hboView" key="assignADStock.AssignQty"/><font color="red">*</font></TD>
				<TD class="txt" width="55%"><html:text name="AssignDistStockForm"
					property="assignedProdQty" style="width:230px" maxlength="6" readonly="true"></html:text><span id="find" style="display:none; width:50px;">&nbsp;&nbsp;&nbsp;<a href="#" onClick="OpenSerialWindow()"><b>Find</b></a></span></TD>
			</TR>
			<TR>
				<TD width="45%"><bean:message bundle="hboView" key="assignADStock.Remarks"/></TD>
				<TD width="55%"><html:textarea name="AssignDistStockForm"
					property="remarks" style="width:230px" onkeypress="return maxlength();"/></TD>
			</TR>
			<TR>
				<TD width="45%"></TD>
				<TD width="55%"></TD>
			</TR>
			<TR>
				<TD align="right" width="205"></TD>
				<TD align="left" width="250">
				<input type="button" value="Reset" onclick="resetting();"><input
					type="button" value="Return Stock" onclick="insertData();"></TD>

			</TR>
			<TR style="hidden" id="commentId">
			</TR>
			
			</TBODY>
	</TABLE>
	</div>
	<br>
	<br>
	
	<TABLE id="assignTable" border="1" cellpadding="1" cellspacing="1" align="center" class ="border" style="display:none;">
	<tr bgcolor="#CD0504">
	<td><font color="white">Product</font></td>
	<td><font color="white">Return From</font></td>
	<td><font color="white">Return To</font></td>
	<td><font color="white">Returned Qty</font></td>
	<td><font color="white">Comments</font></td>
	</tr>
	</table>
		
</html:form>
</body>
</html>
