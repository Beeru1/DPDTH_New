<%try{%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import = "java.util.ArrayList,java.util.HashMap"%>
<%@ page import = "java.util.*" %>
<html:html>
<script>
function getChange(cond)
{
	var Id = document.getElementById("businessCategory").value;   
	var url="initCreateRequisition.do?methodName=getChange&Id="+Id+"&cond="+cond+"&cond2=0";
	
	if(window.XmlHttpRequest) {
		req = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		req=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if(req==null) {
		alert("Browser Doesn't Support AJAX");
		return;
	}
	req.onreadystatechange = getOnChange;
	
	req.open("POST",url,false);
	req.send();
}
function getOnChange()
{
	if (req.readyState==4 || req.readyState=="complete") 
  	{
		
		var xmldoc = req.responseXML.documentElement;
		if(xmldoc == null){		
			return;
		}
		optionValues = xmldoc.getElementsByTagName("option");
		var selectObj = document.getElementById("productCode");
		selectObj.options.length = 0;
		selectObj.options[selectObj.options.length] = new Option(" --Select a Product-- ","");
		for(var i=0; i<optionValues.length; i++){
		
			selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
		}
		
		
	}
}
</script>
<script>

function Validate(){

field=document.getElementById("qtyRequisition").value;

			var valid = "abcdefghijklmnopqrstuwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ`~&'^#$%!?/\|+*"
			var ok = "yes";
			var temp;
			var temp1;
			for (var i=0; i<field.length; i++)
			 {
				temp = "" + field.substring(i, i+1);
				if (valid.indexOf(temp) >= 0) 
				{
				ok = "no";
				temp1=temp;
				}
			}

if(document.getElementById("businessCategory").value=="0" || document.getElementById("businessCategory").value=="")
{
alert("Please Select A Category");
document.getElementById("businessCategory").focus();
return false;
} 
else

if(document.getElementById("productCode").value=="0" || document.getElementById("productCode").value=="")
{
alert("Please Select A Product");
document.getElementById("productCode").focus();
return false;
}
else 
if(document.getElementById("qtyRequisition").value==" " || document.getElementById("qtyRequisition").value.length<1)
{
alert("Please Enter The Required Quantity");
document.getElementById("qtyRequisition").focus();
return false;
}
else
if(ok == "no") 
{
alert("Requisition Quantity Field Cannot Contain Special Characters!");
document.getElementById("qtyRequisition").focus();
return false;
}
else 
if(document.getElementById("warehouse_to").value=="0" || document.getElementById("warehouse_to").value=="")
{
alert("Please Select A Warehouse");
document.getElementById("warehouse_to").focus();
return false;
}
else 
if(document.getElementById("month").value=="0" || document.getElementById("month").value=="")
{
alert("Please Select A Month");
document.getElementById("month").focus();
return false;
}
else
if(document.getElementById("qtyRequisition").value<=0){
alert("Requisition Quantity Cannot Be Less Than Or Equal To Zero");
document.getElementById("qtyRequisition").focus();
return false;
}
else
return true;
}

<<<<<<< .mine
function productUnit(){
var Index = document.getElementById("productCode").selectedIndex;
var productName=document.getElementById("productCode").options[Index].value;
var url= "InsertPurchaseOrder.do?methodName=getProductUnit&cond1="+productName;
var elementId = "unitName" ;
ajaxText(url,elementId,"text");
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
		 if(text.length!=0){
		 	document.getElementById("showUnit").innerHTML=text;
		 	//document.getElementById(elementId).value = text;
		 }
		else if (text.length==0){
		 	document.getElementById("showUnit").style.display="none";
	}
}
}
=======
function isValidNumber()
{
	var c=	event.keyCode;
	if(event.keyCode == 13){
	  return (event.keyCode) ;
	 }
	event.keyCode=(!(c>=48 && c<=57))?0:event.keyCode;
}
</script>

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

<TITLE></TITLE>
</HEAD>																		
<BODY>

<html:form action="/createRequisition.do?methodName=createRequisition" onsubmit="return Validate();" method="post">
<br>

<html:hidden name="HBORequisitionForm" property="condition"/>
<table>
<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/createHandsetRequisition.gif"
		width="505" height="22" alt="">
	</TD>
</tr>
</table>
<table align="center" border="0" class ="border">
	<tr>
		<td colspan="2">
			<font color="red"><b><html:errors/></b></font>
		</td>
	</tr>
<logic:messagesPresent message="true">
	<tr>
		<html:messages id="msg" property="INSERT_SUCCESS" bundle="hboView" message="true">
			<font color="red" size="2"><strong><bean:write name="msg"/></strong></font>
		</html:messages>
	</tr>
	<tr>
		<html:messages id="msg1" property="ERROR_OCCURED" bundle="hboView" message="true">
			<font color="red" size="2"><strong><bean:write name="msg1"/></strong></font>
		</html:messages>
	</tr>
</logic:messagesPresent>
  	<tr>
  				<TD width="155" class="txt"><STRONG><FONT 
  				color="#000000"><bean:message bundle="hboView" key="createRequisition.Category"/></FONT></STRONG><font color="red">*</font></td>
				<TD width="174">
				<html:select property="businessCategory" onchange="getChange('requisitionCategory')" styleClass="box">
					<option value="">--Select a category--</option>
				<logic:notEmpty property="arrBusinessCategory" name="HBORequisitionForm">
				<bean:define id="category"
					name="HBORequisitionForm" property="arrBusinessCategory" />
					<html:options labelProperty="bname" property="bcode"
						collection="category" />
				</logic:notEmpty>	
				</html:select>
				</TD>
  	</tr>
  	<tr>
	    <TD width="155" class="txt"><STRONG><FONT
								color="#000000"><bean:message bundle="hboView" key="createRequisition.Product"/></FONT></STRONG><font color="red">*</font></td>
		<TD width="174">
			<html:select property="productCode" styleClass="box" onchange="productUnit();">
				<option  value="">--Select a Product--</option>
			</html:select>
		</TD>
  	</tr>
		<TR>
			<TD width="155" class="txt"><STRONG><FONT
								color="#000000"><bean:message bundle="hboView" key="createRequisition.ReqQty"/></FONT></STRONG><font color="red">*</font></TD>
			<TD width="174">
			<html:text property="qtyRequisition" maxlength="8" styleClass="box" onkeypress="isValidNumber()"/>&nbsp;<b id="showUnit"></b>
			</TD>
		</TR>
		<tr>
			<TD width="155" class="txt" class="txt"><STRONG><FONT
								color="#000000"><bean:message bundle="hboView" key="createRequisition.OrderReqTo"/></FONT></STRONG><font color="red">*</font>
  			</td>
			<TD width="174" class="txt">
			<html:select property="warehouse_to" styleClass="box">
				<option value="">--Select a Warehouse--</option>
			<logic:notEmpty property="arrWarehouse" name="HBORequisitionForm">
			<bean:define id="warehouse" name="HBORequisitionForm" property="arrWarehouse" />
				<html:options labelProperty="name" property="warehouseId" collection="warehouse" />
			</logic:notEmpty>
			</html:select>
			</TD>
		</tr>
		
		<tr>
  	 
			<td width="155"  class="txt"><STRONG><FONT
								color="#000000"><bean:message bundle="hboView" key="createRequisition.ForMonth"/></FONT></STRONG><font color="red">*</font></td>
			<TD width="174">
				<html:select property="month" styleClass="box">
					<OPTION selected="selected" value = "">--Select a month--</OPTION>
					<OPTION value="1">January</OPTION>
					<html:option value="2">February</html:option>
					<html:option value="3">March</html:option>
					<html:option value="4">April</html:option>
					<html:option value="5">May</html:option>
					<html:option value="6">June</html:option>
					<html:option value="7">July</html:option>
					<html:option value="8">August</html:option>
					<html:option value="9">September</html:option>
					<html:option value="10">October</html:option>
					<html:option value="11">November</html:option>
					<html:option value="12">December</html:option>
   			   </html:select>
   			</TD>
		</tr>
  	<tr><td>
  	
  	</td>
			<td align="left" colspan="2" height="15"><html:submit
				value="Submit" styleClass="medium" /><input type="reset"
				value="Reset"></td>
		</tr>
 </table>
	<BR>
	<BR>
</html:form>
<P><BR>
</P>
</BODY>
</html:html>
 <%}catch(Exception e){e.printStackTrace();}%>