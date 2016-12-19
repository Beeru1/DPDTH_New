
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>



<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/theme/Master.css" type="text/css">
<title>Assign Stock</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT language="JavaScript" 
  src="${pageContext.request.contextPath}/scripts/subtract.js"
	type="text/javascript">
</SCRIPT>
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/scripts/validation.js" type="text/javascript">
</SCRIPT>

<script language="JavaScript"><!--
function prodList(){
var Index = document.getElementById("category").selectedIndex;
var element=document.getElementById("category").options[Index].value;
var prodCategory=element.substring(0,element.indexOf("#"));
var url= "initSwapDOAStock.do?methodName=getProdList&cond1="+prodCategory;
var elementId = "product" ;
ajax(url,elementId);
}

function availableQty(){
if(document.getElementById("category").value==""){
	alert("Please Select Product Category First");
	document.getElementById("category").value=""
	return false;
}
if(document.getElementById("product").value==""){
	alert("Please Select Product First");
	document.getElementById("product").value=""
	return false;
}

var Indexprod=document.getElementById("product").selectedIndex;
var productId=document.getElementById("product").options[Indexprod].value;
var productName=document.getElementById("product").options[Indexprod].text;

var Index = document.getElementById("category").selectedIndex;
var element=document.getElementById("category").options[Index].value;
var flag=element.substring(element.indexOf("#")+1,element.length);


var url= "initSwapDOAStock.do?methodName=getStockQtyDistributor&cond2="+productId+"&cond3="+flag;
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
			if(null != text)
			document.getElementById(elementId).value = text; 		
			else
			document.getElementById(elementId).value = "0" ; 
		}
			
	}
	
}

function validateProduct()
{
	//if(document.getElementById("productID") != null)
	
}

	function hide()
	{
		var Index = document.getElementById("category").selectedIndex;
		var element=document.getElementById("category").options[Index].value;
		
		var flag=element.substring(element.indexOf("#")+1,element.length);
		//document.getElementById("startSno").style.display = document.getElementById("endSno").style.display = (flag == 'Y' || flag == 'y')?'inline':'none';
		document.getElementById("find").style.display ="none";
		//document.getElementById("product").value="";	
		//document.getElementById("accountFrom").value="";
	//	document.getElementById("accountTo").value="";
		document.getElementById("assignedProdQty").value=0;
		//document.getElementById("endingSno").value="";
		//document.getElementById("startingSerial").value="";
		document.getElementById("availableProdQty").value=0;
		
	}
	
function validateData()
{
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
	
 	if(document.getElementById("remarks").value == "")
 	{
 		alert("Please write remarks... ");
 		return 0;
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
var inputs = 0;
function SubmitData()
{
		var table = document.getElementById("contacts");
		var rowCount = table.rows.length;
		
		var i=0;
		var jsArrproductId= new Array();
		var jsArrqty = new Array();
		var jsArrremarks = new Array();
		var jsSrNo = new Array();
		var jsArrprodId = new Array();
		
		while (i+1<rowCount)
		{
			
			var x = document.getElementById("contacts").rows[i+1].cells;
			
			
			jsArrproductId[i] ="";
     		// This is for Hidden parameter
     		jsArrproductId[i] = x[0].getElementsByTagName("input")[0].value;
     		//alert(jsArrproductId);
     		
     		jsArrqty[i] ="";
     		// This is for Hidden parameter
     		jsArrqty[i] = x[1].getElementsByTagName("input")[0].value;
     		//alert(jsArrqty);
     		
     		jsArrremarks[i] ="";
     		// This is for Hidden parameter
     		jsArrremarks[i] = x[2].getElementsByTagName("input")[0].value;
     		//alert(jsArrremarks);
     		
     		jsArrprodId[i] = "";
     		jsArrprodId[i] = x[4].getElementsByTagName("input")[0].value;
     		//alert(jsArrprodId);
     		
     		jsSrNo[i] = "";
   
     		jsSrNo[i] = x[5].getElementsByTagName("input")[0].value+"#";
   		i++;
		}
		
		
		document.forms[0].jsArrprodId.value=jsArrprodId;
  		document.forms[0].jsArrqty.value=jsArrqty;
  		document.forms[0].jsArrremarks.value=jsArrremarks;
  		document.forms[0].jsSrNo.value=jsSrNo;
  		
  		// Add by harbans : to fix MASDB00172522 Bug ID.
		if((document.forms[0].jsArrprodId.value).length == 0)
  	    {
  		   alert("Please Add atleast One Record!!")
  		   return false;
  		}
  		
  		var agencyName = document.getElementById('courierAgency').value;
		var docketNum = document.getElementById('docketNum').value;
  		var isSubmit=true;
		if(trim(agencyName) =="" || agencyName ==null || trim(docketNum)=="" || docketNum ==null){
			if(confirm("DC will be saved as draft and will be sent to Warehouse only after Courier Agency and Docket Number will be entered.")){
				isSubmit =true;
			}else{
				isSubmit =false;
			}
		}
  		//Add on UAT observation by harabns on 16th Sept
		if(document.getElementById("dcRemarks").value == "")
		{
			alert("Please write dc remarks... ");
			return 0;
		}
		
		if(document.getElementById("dcRemarks").length>250){
			alert("Max Character Length Of Dc Remarks Is 250");
		}
  		
  		
	
//			document.forms[0].action="initSwapDOAStock.do?methodName=returnFreshStock&productIDWH="+productId+"&returnQuantity="+assignedProdQty+"&returnSerialNo="+startingSerial+"&avlSerialNO="+availableProdQty+"&productName="+productName+"&remarks="+remarks+"&categoryID="+categoryId;
			if(isSubmit ==true){
				document.forms[0].action="initSwapDOAStock.do?methodName=returnFreshStock";
				document.forms[0].method="post";
				document.forms[0].submit();			
				resetValues();
			}
}

function insertData()
{
		
	if(checkProduct() == false )
	{
		return false;
	}
}

function addFreshRow()
{
	if( validateData()== 1)
	{
		//var	commentId= assignTable();
		var categoryId = document.getElementById("category").value;	
		var productId = document.getElementById("product").value;	
		var assignedProdQty = document.getElementById("assignedProdQty").value;
		var startingSerial = document.getElementById("startingSerial").value;			
		var availableProdQty = document.getElementById("availableProdQty").value;
		var productName= document.getElementById("product").options[document.getElementById('product').selectedIndex].text;
		var remarks=document.getElementById("remarks").value;			
					
		var table =  document.getElementById('contacts');
	   	var tr    =  document.createElement('TR');
	   	//var td1   =  document.createElement('TD');
	   	var td2   =  document.createElement('TD');
	   	//var td3   =  document.createElement('TD');
	   	var td4   =  document.createElement('TD');
	   	var td5   =  document.createElement('TD');
	   	var td6   =  document.createElement('TD');
	   	var td7   =  document.createElement('TD');
	   	var td8   =  document.createElement('TD');

	    var inp2  = document.createElement('INPUT');
	    var pn = document.getElementById('product');
	   	inp2.value = pn.options[pn.selectedIndex].text;
	  	var pnselect = inp2.value;
				  	
		var inpID4  = document.createElement('INPUT');
	  	inpID4.setAttribute("type", "hidden");
	  	inpID4.setAttribute("name", "productIdfresh");
	  	inpID4.setAttribute("id", "productIdfresh");
		inpID4.value =	pn.options[pn.selectedIndex].value;
				  
	    var inp7  = document.createElement('INPUT');
		inp7.value = document.getElementById('product').value; 
		var prid = inp7.value;
	
	
	
		 var inp8  = document.createElement('INPUT');
		inp8.value = document.getElementById('startingSerial').value  ; 
		var srnos = inp8.value;
					
					
		var inp4  = document.createElement('INPUT');
		inp4.value = document.getElementById('assignedProdQty').value; 
		var inhandqtyselect = inp4.value;
		
		var inp5= document.createElement('INPUT');
		inp5.value = document.getElementById('remarks').value; 
		var inremarks = inp5.value;
		
			   //set attribute input text inp1 
			  //		inp1.name="bg";
			//		inp1.id="bgcol";
			///		inp1.width="200";
			//		inp1.style.fontSize ="11";
			//		inp1.readOnly = true ;
			 	//set attribute input text inp2 
		inp2.name="pn";
		inp2.id="pn";
		inp2.width="200";
		inp2.style.fontSize ="11";
		inp2.readOnly = true ;
		
		
	 	inp7.name="prid";
		inp7.id="prid";
		inp7.width="200";
		inp7.style.fontSize ="11";
		inp7.readOnly = true ;
		
		inp8.name="prid";
		inp8.id="prid";
		inp8.width="200";
		inp8.style.fontSize ="11";
		inp8.readOnly = true ;
		
						
			
	//set attribute input text inp3 
		inp4.name="inhandqtyselect";
		inp4.id="inhandqtyselect";
		inp4.width="75";
		inp4.style.fontSize ="11";
		inp4.readOnly = true ;

	//set attribute input text inp3 
		inp5.name="inremarks";
		inp5.id="inremarks";
		inp5.width="75";
		inp5.style.fontSize ="11";
		inp5.readOnly = true ;
	
	    table.appendChild(tr);
	    // tr.appendChild(td3);
	  //  td1.appendChild(inp1);
	    td2.appendChild(inp2);
	  //  td3.appendChild(inp3);
	    td4.appendChild(inp4); 
	    td5.appendChild(inp5);
	    td7.appendChild(inp7);
		 td8.appendChild(inp8);

			
		td7.style.display="none";
		td8.style.display="none";
		
		//tr.appendChild(td1);
	    tr.appendChild(td2);
	  //  tr.appendChild(td3);
	    tr.appendChild(td4);
	    tr.appendChild(td5);
	    tr.appendChild(td6);
   		tr.appendChild(td7);
   		tr.appendChild(td8);
   		
    	//tr.appendChild(inpID4);
		    	
		    	
	   	var arry = new Array();
	   	if(inputs>-1){
	       	var img = document.createElement('button');
	       	img.value="Remove";
			//	img.setAttribute('src', 'images/delete.gif');
	       	img.onclick = function(){
	           	removeContact(tr);
	       	}
	       	td6.appendChild(img);
	   	}
		    	
		     	inputs++;
		     				
			resetValues();
			
			return true;

		}
		else
		{
			return false;
		} 	
}
	
		function removeContact(tr){
		    tr.parentNode.removeChild(tr);
		}


 	function checkProduct()
 	{
		var flag = false;
		
		var i=0;
		
	 	var pn_temp = document.getElementById('product');
		var id_temp = pn_temp.options[pn_temp.selectedIndex].value;	
		var tempVar = document.getElementsByName('product');
		
		
		var table = document.getElementById("contacts");
		var rowCount = table.rows.length;
	
	
		while (i+1<rowCount)
		{
			var x = document.getElementById("contacts").rows[i+1].cells;
			var pr  = x[4].getElementsByTagName("input")[0].value;
			if(pr  == pn_temp.value)
			{
				alert("Product is already selected, Please choose another Product ..");
				return false;
			}
			i++;
				
		}
			
		flag = addFreshRow();
		
		if(flag)
		{
			// Reset all the fields
			document.getElementById('product').value = "0";
			resetValuesPage();
			//document.getElementById('openStockAsPerDP').value = "";
		}
	}
		

 	function resetting(){
 		
 		document.getElementById("category").value="";	
		document.getElementById("product").value="";	
		document.getElementById("assignedProdQty").value=0;
		document.getElementById("endingSno").value="";
		document.getElementById("startingSerial").value="";
		document.getElementById("availableProdQty").value=0;
		document.getElementById("find").style.display ='none';
		document.getElementById("remarks").value = "";
		document.getElementById("dcRemarks").value = "";
	}
	
 	function resetValues(){
 		//document.getElementById("category").value="";	
		document.getElementById("product").value="";
		document.getElementById("assignedProdQty").value=0;
		document.getElementById("endingSno").value="";
		document.getElementById("startingSerial").value="";
		document.getElementById("availableProdQty").value=0;
		document.getElementById("find").style.display ='none';
		document.getElementById("remarks").value = "";
		document.getElementById("dcRemarks").value = "";
 	}
 	
 	function resetValuesPage(){
 		document.getElementById("category").value="";	
		document.getElementById("product").value="";
		document.getElementById("assignedProdQty").value=0;
		document.getElementById("endingSno").value="";
		document.getElementById("startingSerial").value="";
		document.getElementById("availableProdQty").value=0;
		document.getElementById("find").style.display ='none';
		document.getElementById("remarks").value = "";
		document.getElementById("dcRemarks").value = "";
				
 	}
 	
function specialChar(obj)
{
  var fieldName;

  if (obj.name == "courierAgency")
  {
   fieldName = "Courier Agency";
  }
  if (obj.name == "docketNumber")
  {
   fieldName = "Docket Number";
  }
 if (obj.name == "dcRemarks")
  {
   fieldName = "DC Remarks";
  }
  
  var iChars = "!@#$%^&*()+=-[]\\\';,./{}|\":<>?~`_";

  for (var i = 0; i < obj.value.length; i++) 
    {
  	if (iChars.indexOf(obj.value.charAt(i)) != -1) 
    {
  	alert ("Special characters are not allowed for "+fieldName);
  	obj.value = obj.value.substring(0,i);
  	return false;
  	}
  }	
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
	var Indexprod=document.getElementById("product").selectedIndex;
	var productId=document.getElementById("product").options[Indexprod].value;
	  var url="viewSerialNoListforWH.do?methodName=viewFreshStockSerialNoList&cond3="+productId;
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
			
--></script>



</head>
<body> 
<html:form action="/initSwapDOAStock.do">




<html:hidden property="jsArrprodId" name="SwapDOAStockForm" />
	<html:hidden property="jsArrremarks" name="SwapDOAStockForm" />
	<html:hidden property="jsArrqty" name="SwapDOAStockForm" />
	<html:hidden property="jsSrNo" name="SwapDOAStockForm" />
	
	
	
	<TABLE WIDTH='100%'>
		<TR>
			<TD colspan="4" class="text">
				<IMG
				src="${pageContext.request.contextPath}/images/SwapDoaStb.jpg"
				width="500" height="27" alt="">
			</TD>
		</TR>
		<TR>
			<TD>
			
			<table>
				<tbody>
					<tr>
						<td><logic:notEmpty name="SwapDOAStockForm" property="message">
						<font color="red"><B><bean:write name="SwapDOAStockForm" property="message"/></B></font>
						</logic:notEmpty>
					</td>
					</tr>
				</tbody>
			</table>
			
				<div>
				<TABLE style ="width:70%;" border="0" cellpadding="1" cellspacing="1"
					align="center" class ="border">
					<TBODY>
				
						<TR>
							<TD width="45%"></TD>
							<TD width="55%"></TD>
						</TR>
						<TR  id="busCategory">
							<TD class="txt" width="45%"><bean:message bundle="hboView" key="returnFreshStock.Category"/><font color="red">*</font></TD>
							<TD class="txt" width="55%">
							<html:select property="category" onchange="prodList();hide();" style="width:230px">
								<logic:notEmpty property="arrCategory" name="SwapDOAStockForm" >
									<html:option value="">--Select A Category--</html:option>
									<bean:define id="category" name="SwapDOAStockForm" property="arrCategory" />
									<html:options labelProperty="bname" property="bcodeFlag" collection="category" />
								</logic:notEmpty>
							</html:select>
							</TD>
						</TR>
						<TR>
						<TD class="txt" width="45%"><bean:message bundle="hboView" key="returnFreshStock.Product"/><font color="red">*</font></TD>
							<TD class="txt" width="55%">
							<html:select property="product"	onchange="validateProduct();availableQty();showHideFind()" style="width:230px">
									<html:option value="">--Select A Product--</html:option>
							</html:select></TD>
						</TR>
			
						<TR>
							<TD class="txt" width="45%"><bean:message bundle="hboView" key="returnFreshStock.Avl.Qty"/><font color="red">*</font></TD>
							<TD class="txt" width="55%"><html:text name="SwapDOAStockForm"
								property="availableProdQty" readonly="true" style="width:230px"></html:text></TD>
						</TR>
						<input type="hidden" name="endingSno">
						<input type="hidden" name="startingSerial">
					
						<TR>
							<TD class="txt" width="45%"><bean:message bundle="hboView" key="returnFreshStock.Ret.Qty"/><font color="red">*</font></TD>
							<TD class="txt" width="55%"><html:text name="SwapDOAStockForm"
								property="assignedProdQty" style="width:230px" maxlength="6" readonly="true"></html:text><span id="find" style="display:none; width:50px;">&nbsp;&nbsp;&nbsp;<a href="#" onClick="OpenSerialWindow()"><b>Find</b></a></span></TD>
						</TR>
						<TR>
							<TD width="45%"><bean:message bundle="hboView" key="returnFreshStock.Remarks"/><font color="red">*</font></TD>
							<TD width="55%"><html:textarea name="SwapDOAStockForm"
								property="remarks" style="width:230px" onkeypress="return maxlength();"/></TD>
						</TR>
						<TR>
							<TD width="45%"></TD>
							<TD width="55%"></TD>
						</TR>
						<TR>
							<TD align="right" width="205"></TD>
							<TD align="left" width="250">
							<input type="button" value="Reset" onclick="resetting();">
							<input type="button" value="Add To Grid" onclick="insertData();">
							</TD>
						</TR>
						<TR style="hidden" id="commentId">
						</TR>
						
						</TBODY>
				</TABLE>
				</div>
			</TD>
		</TR>	
		<TR>
			<TD>
				<div id="divide" style="OVERFLOW: auto; WIDTH: 90%; HEIGHT: 150px">
				<table align="center" class="mLeft5" styleId="tblSample" width="74%">
					<tbody id="contacts">
						<TR>
			
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5"
								align="center" width="20%"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Product</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5"
								align="center" width="29%"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Return Quantity</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5"
								align="center" width="25%"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Remarks</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5"
								align="center" width="26%"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Remove</span></TD>	
						</TR>
					</tbody>
				</TABLE>
				</div>
			</TD>
		</TR>
		
		<!--  Add on UAT observation by harabns on 16th Sept  -->
		<TR>
			<TD>
			  <TABLE style ="width:70%;" border="0" cellpadding="1" cellspacing="1"
					align="center" class ="border">
			  <tr>
			  <td class="txt">
			<bean:message bundle="view" key="DcCreation.courierAgency" />
			</td>
			<td>
			<html:text name="SwapDOAStockForm"	property="courierAgency" styleId="courierAgency" maxlength="100" onkeyup="return specialChar(this);" />
			</td>
			<td class="txt">
			<bean:message bundle="view" key="DcCreation.DocketNumber" />
			</td>
			<td>
			<html:text name="SwapDOAStockForm"	property="docketNum" styleId="docketNum" maxlength="50" onkeyup="return specialChar(this);" />
			</td>
			  </tr>
			  <TR>
				<TD class="txt"><bean:message bundle="hboView" key="returnFreshStock.DCRemarks"/><font color="red">*</font></TD>
				<TD class="txt"><html:textarea name="SwapDOAStockForm"
					property="dcRemarks" style="width:230px" onkeypress="return maxlength();" onkeyup="return specialChar(this);"/></TD>
			  <TD align="right">
				<input type="button" value="Return To Warehouse" onclick="SubmitData();">
			  </TD>
			  </TR>
			</table>
			</TD>
			
		</tr>		
	</TABLE>
	
	
	
	<%
	String dc_no = (String) request.getAttribute("SDCNo");
	 %>	
<script>
	var dc_no= '<%=dc_no%>';
 	if(dc_no!=null && dc_no != "null" ){
		
		var url="printDCDetails.do?methodName=printDC&Dc_No="+dc_no+"&TransType=Fresh";
	    window.open(url,"SerialNo","width=700,height=600,scrollbars=yes,toolbar=no");				
  	}
</script>
		
		
</html:form>
</body>

