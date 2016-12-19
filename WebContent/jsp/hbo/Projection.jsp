<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="com.ibm.virtualization.recharge.common.Constants"%>

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
<TITLE>Product Projection </TITLE>
<script language="JavaScript">
<%--<%@ include file="/jScripts/VAL.js" %>--%>

//function to get available QTY
function getChange(cond)
{
	var Id = document.getElementById("business_catg").value;
	var cid = document.getElementById("circle").value;	
	if(cond=="projectionQty")
	{
	var pcode = document.getElementById("product").value;
	var mth = document.getElementById("month").value;
	var yr = document.getElementById("year").value;
	var mth_yr=mth+'@'+yr;
	//alert(cid);
	
	var url="initProjectionStock.do?methodName=getProjectedQuantity&cond1="+pcode+"&cond2="+cid+"&cond3="+mth_yr;
	}
	else if(cond=="category")
	{
		var url="initProjectionStock.do?methodName=getChangeProducts&Id="+Id+"&cond="+cond+"&cond2="+cid;
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
	if(cond=="projectionQty")
	{
	req.onreadystatechange = getOnChange;
	}
	else if(cond=="category")
	{
	req.onreadystatechange = getOnChange1;
	}
	req.open("POST",url,false);
	req.send();
}
function getOnChange()
{
	if (req.readyState==4 || req.readyState=="complete") 
  	{
		document.getElementById("quantity").value = req.responseText;	
	
	}
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
		var selectObj = document.getElementById("product");
		selectObj.options.length = 0;
		selectObj.options[selectObj.options.length] = new Option(" -Select- ","");
		for(var i=0; i<optionValues.length ; i++){
		
			selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
		}
	
	}

}

function validate()
{	
	var role = document.getElementById("role").value;
	var mth = document.getElementById("month").value;
	var yr = document.getElementById("year").value;
	var myDate=new Date();
	var nxtyr=myDate.getFullYear()+1;
	var thisyr=myDate.getFullYear();
	var date = myDate.getDate();
	var month=myDate.getMonth()+1;
	if(document.getElementById("circle").value==""){
		alert("Please Select A Circle");
		return false;
	}
	else if(document.getElementById("business_catg").value==""){
		alert("Please Select A Business Category");
		return false;		
	}
	else if(document.getElementById("product").value==""){
		alert("Please Select A Product");
		return false;
	}
	else if(document.getElementById("month").value==""){
		alert("Please Select A Month");
		return false;		
	}
	else if(document.getElementById("year").value==""){
		alert("Please Select A year");
		return false;
	}
	if (role=='circle' && date>10)
	{
		alert("Projection Can Be Done Before 11th Of Each Month");
		return false;
	}
	if ((mth==11 && nxtyr==yr)||(mth==12 && nxtyr==yr))
	{
	alert("Please Check Year");
	return false;
	}
	if (((month==11||month==12) && mth==1 && thisyr==yr)||((month==11||month==12) && mth==2 && thisyr==yr))
	{
	alert("Please Check Year");
	return false;
	}
	else
	
	if(document.getElementById("quantity").value <0)
	{
	alert("Quanity Cannot Be Negative");
	document.getElementById("quantity").focus();
	return false;
	}
	if(IsNumeric(document.getElementById("quantity").value)==false){
		alert("Quantity Field Takes Positive Integer Values Only");
		document.getElementById("quantity").focus();
		return false;
	}
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
   
   function productUnit(){
	var Index = document.getElementById("product").selectedIndex;
	var productName=document.getElementById("product").options[Index].value;
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
</script>
</HEAD>
<BODY>
<html:form action="/projectionStock.do?methodName=insertProjection" onsubmit="return validate();" >
<b><html:errors/></b>
<table>
<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/defineProjection.gif"
		width="505" height="22" alt="">
	</TD>
</tr>
</table>
	<TABLE border="0" align="center" class ="border">
		<TBODY>
			<tr>
				<logic:messagesPresent message="true">
					<html:messages id="msg" property="INSERT_SUCCESS" bundle="hboView" message="true">
						<font color="red" size="2"><strong><bean:write name="msg"/></strong></font>
					</html:messages>
				</logic:messagesPresent>
			</tr>
			<TR>
				<TD width="222"></TD>
				<TD width="161"></TD>
			</TR>
	
			<TR>
				<TD width="235" class="txt"><bean:message bundle="hboView" key="ProjectionStock.circle"/><font color="red">*</font> </TD>
				<TD width="184" class="txt"><html:select property="circle" style="width:200px">
				<html:option value="">--Select A Circle--</html:option>
				<logic:notEmpty property="arrCircle" name="HBOProjectionForm">
					<bean:define id="circle" name="HBOProjectionForm" property="arrCircle" />
					<html:options labelProperty="circlename" property="circle" collection="circle" />
				</logic:notEmpty>			
				</html:select></TD>
			</TR>
		
			<TR>
				<TD width="235" class="txt"><bean:message bundle="hboView" key="ProjectionStock.BusinessCategory"/><font color="red">*</font> </TD>
				<TD width="184" class="txt"><html:select property="business_catg" onchange="getChange('category')"  style="width:200px">
				<logic:notEmpty property="arrBusinessCat" name="HBOProjectionForm">
					<html:option value="">--Select Business Category--</html:option>
					<bean:define id="business_catg" name="HBOProjectionForm" property="arrBusinessCat" />
					<html:options labelProperty="bname" property="bcode" collection="business_catg" />
				</logic:notEmpty>			
				</html:select></TD>
			</TR>
			<TR>
				<TD width="235" class="txt"><bean:message bundle="hboView" key="ProjectionStock.product"/><font color="red">*</font> </TD>
				<TD width="184" class="txt"><html:select property="product" style="width:200px" onchange="productUnit();">
					<html:option value="">--Select A Product--</html:option>
				</html:select></TD>
			</TR>
			<TR>
				<TD width="235" class="txt"><bean:message bundle="hboView" key="ProjectionStock.month"/><font color="red">*</font> </TD>
				<TD width="321"class="txt"><html:select property="month" style="width:200px">
				<logic:notEmpty property="arrMonth" name="HBOProjectionForm">
					<html:option value="">--Select Month--</html:option>
					<bean:define id="month" name="HBOProjectionForm" property="arrMonth" />
					<html:options labelProperty="monthName" property="monthId" collection="month" />
				</logic:notEmpty>			
				</html:select>
				</TD>
			</TR>
			<TR>
				<TD width="235" class="txt"><bean:message bundle="hboView" key="ProjectionStock.year"/><font color="red">*</font></TD>
				<TD class="txt">
					<html:select property="year" onchange="getChange('projectionQty');" style="width:200px">
					<logic:notEmpty property="arrYear" name="HBOProjectionForm">
						<html:option value="">--Select Year--</html:option>
						<bean:define id="year" name="HBOProjectionForm" property="arrYear" />
						<html:options labelProperty="nextYear" property="nextYear" collection="year" />
					</logic:notEmpty>
					</html:select><html:hidden property="role"/>
				</TD>
			</TR> 
			<TR>
				<TD width="235" class="txt"><bean:message bundle="hboView" key="ProjectionStock.quantity"/></TD>
				<TD width="321" class="txt">
				<html:text property="quantity"  style="width:200px" maxlength="8"/>&nbsp;<b id="showUnit"></b>
				</TD>
			</TR>
			<TR>
				<TD width="235" align="right"></TD>
				<TD width="321" ><html:reset styleClass="medium"></html:reset><html:submit styleClass="medium" value="Submit"></html:submit></TD>
			</TR>
		</TBODY>
	</TABLE>
</html:form>
</BODY>
</html:html>