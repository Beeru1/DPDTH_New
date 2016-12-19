<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<html:html>
<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="../theme/Master.css" rel="stylesheet"
	type="text/css">
<TITLE>ASSIGN PRODUCTS</TITLE>


<script language="JavaScript">
function setCircleVal()
{
if(document.getElementById("product").value =="")
{
alert("Please Select A Product First");
document.getElementById("bundle").value="Unbundled";
//window.location.reload(false);
return false;
}
var Id = document.getElementById("bundle").value;
var cond="Bundling";
var url="initAssignProdStock.do?methodName=getChange&cond1="+Id+"&cond2="+cond;
if(window.XmlHttpRequest) {
		req = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		req=new ActiveXObject("Microsoft.XMLHTTP");
		}if(req==null) {
		alert("Browser Doesnt Support AJAX");
		return;
	}
	req.onreadystatechange = getOnChange1;
	req.open("POST",url,false);
	req.send();
}
function getOnChange1()
{
	if (req.readyState==4 || req.readyState=="complete") 
  	{
		
		var xmldoc = req.responseXML.documentElement;
		if(xmldoc == null){		
			return;
		}
		optionValues = xmldoc.getElementsByTagName("option");
		var selectObj = document.getElementById("circle");
		selectObj.options.length = 0;
		selectObj.options[selectObj.options.length] = new Option(" -Select A Circle- ","");
		for(var i=0; i<optionValues.length; i++){
		
			selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
		}

	}

}

function resetFields(){
document.getElementById("product").value="";
document.getElementById("bundle").value="Unbundled";
document.getElementById("circle").value="";
document.getElementById("availableProdQty").value="";
document.getElementById("warehouseId").value="";
document.getElementById("assignedProdQty").value="";

}

function hideVal()
{
x=document.HBOAssignProdStockForm.product.value;
document.HBOAssignProdStockForm.circle.length=1;
document.HBOAssignProdStockForm.circle.value="";
document.HBOAssignProdStockForm.bundle.value="Unbundled";
document.HBOAssignProdStockForm.warehouseId.length=1;
document.HBOAssignProdStockForm.warehouseId.value="";
if(x.indexOf("#Pair#")==-1)
{
var div_ref = document.getElementById("bundled");
div_ref.style.display='none';
/* document.getElementById("bundle").disabled=true; */
setCircleVal();
}
else if (x.indexOf("#NonP#")==-1)
{
var div_ref = document.getElementById("bundled");
div_ref.style.display='block';
}
}
//function to get available QTY
function getAvailQnty(cond)
{	
	if(document.getElementById("product").value==""){
		alert("Select A Product First");
		return false;
	}

	var pd = document.getElementById("product").value;
	var Id = document.getElementById("bundle").value;
	var circle=document.getElementById("circle").value;
		
	if(pd.indexOf("#Pair#")==-1)
	{
	Id="Unbundled";
	var url="initAssignProdStock.do?methodName=getChange&cond1="+Id+"&cond2="+pd+"&cond3="+circle;
	}
	else if(pd.indexOf("#NonP#")==-1)
    {
	var url="initAssignProdStock.do?methodName=getChange&cond1="+Id+"&cond2="+pd+"&cond3="+circle;
    }
 
	if(window.XmlHttpRequest) {
		req = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		req=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if(req==null) {
		alert("Browser Doesnt Support AJAX");
		return;
	}
	req.onreadystatechange = getOnAvailQnty;
	
	req.open("POST",url,false);
	req.send();
	
}
function getOnAvailQnty()
{
	if (req.readyState==4 || req.readyState=="complete") 
  	{
		document.getElementById("availableProdQty").value = req.responseText; 		
		
	}
	
}
function getWarehouse()
{
	var circle = document.getElementById("circle").value;
	var prod = document.getElementById("product").value;
	var bundle = document.getElementById("bundle").value;
	var url = "initAssignProdStock.do?methodName=getChange&cond1=" + circle +"&cond2=warehouse&cond3=" + prod +"&cond4="+ bundle;

	if(window.XmlHttpRequest) {
		req = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		req=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if(req==null) { 
		alert("Browser Doesnt Support AJAX");
		return;
	}
	req.onreadystatechange = getOnChangeWare;
	
	req.open("POST",url,false);
	req.send();
}
function getOnChangeWare()
{
	if (req.readyState==4 || req.readyState=="complete") 
  	{
		var xmldoc = req.responseXML.documentElement;
		if(xmldoc == null){		
			return;
		}
		optionValues = xmldoc.getElementsByTagName("option");
		var selectObj = document.getElementById("warehouseId");
		selectObj.options.length = 0;
		selectObj.options[selectObj.options.length] = new Option(" -Select Account- ","");
		for(var i=0; i<optionValues.length; i++){
		
			selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
		}

	}

}
function validate()
{
if(document.getElementById("product").value==""){
	alert("Select A Product");
	return false;
}
else if(document.getElementById("circle").value==""){
	alert("Select A Circle");
	return false;
}
else if(document.getElementById("assignedProdQty").value==""){
	alert("Assigned Quantity Must Be A Positive Integer Value");
	return false;
}
 var availQty=parseInt(document.getElementById("availableProdQty").value);
 var assignQty=parseInt(document.getElementById("assignedProdQty").value);

 if(assignQty==0 || availQty==0)
 {
 alert("Available Quantity & Assign Qty Cannot Be Zero");
 return false;
 }else
 if(assignQty>availQty)
 {
 alert("Assigned Quantity Cannot Be More Than Available Quantity");
 return false;
 }
 else
  if (assignQty <0)
  {
   alert("Assigned Quantity Cannot Be Negative.");
 return false;
  }

  if(IsNumeric(document.getElementById("assignedProdQty").value)== false){
  	alert("Assign Quantity Field Takes Positive Integer Values only");
  	return false;
  }
  else
 return true;
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

</script>
</HEAD>

<BODY>

<logic:messagesPresent message="true">
	<ul>
		<html:messages id="message" property="INSERT_SUCCESS" bundle="hboView" message="true">
			<li><bean:write name="message" /></li>
		</html:messages>
	</ul>
</logic:messagesPresent>

<html:form action="/AssignProdStock.do" onsubmit="return validate()">
<html:hidden property="methodName" value="assignProdStock"/>
<table>
<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/assignStock.gif"
		width="505" height="22" alt="">
	</TD>
</tr>
</table>
	<TABLE width="46%" border="0" cellpadding="1" cellspacing="1"
		align="center" class ="border">
		<TBODY>
		<tr><td colspan="2" ><B><center><font color="Red"><ul><html:errors /></ul></font></center></B></td>
		</tr>
			<TR>
				<TD width="222"></TD>
				<TD width="161"></TD>
			</TR>
			<TR>
			<TD class="txt" width="222"><bean:message bundle="hboView" key="assignHandset.Product"/><font color="red">*</font></TD>
				<TD class="txt" width="161">
				<html:select property="product"	onchange="hideVal();" style="width:230px">
					<logic:notEmpty property="arrProducts" name="HBOAssignProdStockForm">
						<html:option value="">--Select A Product--</html:option>
						<bean:define id="product" name="HBOAssignProdStockForm" property="arrProducts" />
						<html:options labelProperty="prod_stck_type" property="pseudo_prodcode" collection="product" />
					</logic:notEmpty>
				</html:select></TD>
			</TR>
			<TR id="bundled">
				<TD class="txt" width="222"><bean:message bundle="hboView" key="assignHandset.Bundle"/><font color="red">*</font></TD>
				<TD class="txt" width="161"><html:select property="bundle"
					onchange="return setCircleVal();" style="width:230px">
					<html:option value="Unbundled">--Select Stock Type--</html:option>
					<html:options name="HBOAssignProdStockForm" property="arrBndlSatus"></html:options>
				</html:select></TD>
			</TR>
			<TR>
				<TD class="txt" width="222"><bean:message bundle="hboView" key="assignHandset.Circle"/><font color="red">*</font></TD>
				<TD class="txt" width="161"><html:select property="circle"
					onchange="getAvailQnty('Paired');getWarehouse();" style="width:230px">
					<html:option value="">--Select A Circle--</html:option>					
				</html:select></TD>
			</TR>
			<TR>
				<TD class="txt" width="222"><bean:message bundle="hboView" key="assignHandset.AvlProdQty"/></TD>
				<TD class="txt" width="161"><html:text name="HBOAssignProdStockForm"
					property="availableProdQty" readonly="true" style="width:230px"></html:text></TD>
			</TR>
			<tr>
				<TD class="txt" width="222"><bean:message bundle="hboView" key="assignHandset.Assignto"/><font color="red">*</font></td>
				<TD class="txt" width="161">
				<html:select property="warehouseId" style="width:230px">
					<html:option value="">--Select Account--</html:option>
				</html:select></td>
			</tr>
			<TR>
				<TD class="txt" width="222"><bean:message bundle="hboView" key="assignHandset.AssignQty"/><font color="red">*</font></TD>
				<TD class="txt" width="161"><html:text name="HBOAssignProdStockForm"
					property="assignedProdQty" style="width:230px" maxlength="6"></html:text></TD>
			</TR>
			<TR>
				<TD width="222"></TD>
				<TD width="161"></TD>
			</TR>
			<TR>
				<TD align="right" width="222"></TD>
				<TD align="left" width="161"><html:button  property="btn" styleClass="medium" onclick="resetFields()" value="Reset"/><html:submit value="Assign"
					styleClass="medium"></html:submit></TD>
			</TR>
			
				</TBODY>
	</TABLE>
</html:form>
</BODY>
</html:html>
