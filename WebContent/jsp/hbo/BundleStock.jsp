
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
<TITLE></TITLE>
<SCRIPT language="JavaScript"
	src="<%=request.getContextPath()%>/scripts/validation.js"
	type="text/javascript"></SCRIPT>
<SCRIPT>
function getChange(cond)
{
	var Id = document.getElementById("product").value;
  var url="initBundleStock.do?methodName=getChange&Id="+Id+"&cond="+cond;
	
	if(window.XmlHttpRequest) {
		req = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		req=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if(req==null) {
		alert("Browser Doesnt Support AJAX");
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

		document.getElementById("avlPairedStock").value =   req.responseText; 		
		
		
	}
}

function getValueChange(cond)
{
	var Id = document.getElementById("circle").value;
	var url="initBundleStock.do?methodName=getChange&Id="+Id+"&cond="+cond;
	
	if(window.XmlHttpRequest) {
		req = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		req=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if(req==null) {
		alert("Browser Doesnt Support AJAX");
		return;
	}
	req.onreadystatechange = getValueOnChange;
	
	req.open("POST",url,false);
	req.send();
}
function getValueOnChange()
{
	if (req.readyState==4 || req.readyState=="complete") 
  	{

		document.getElementById("avlSimStock").value =   req.responseText; 		
		
		
	}
} 

<%--<%@ include file="/jScripts/VAL.js" %>--%>

function blank(){
if (document.forms[0].circle.value=="0"){
		alert("Select Circle");
		document.forms[0].circle.focus();
		return false;
		}
		else
if (document.forms[0].product.value=="0"){
		alert("Select Product");
		document.forms[0].product.focus();
		return false;
		}
		else
		
if (parseInt(document.forms[0].bundleQty.value)==0 ||document.forms[0].bundleQty.value.length<1){
		alert("Enter Quantity To Bundle");
		document.forms[0].bundleQty.focus();
		return false; 
		}
		else if(isNumber(document.forms[0].bundleQty.value)== false){
			alert("Quantity To Bundle Takes Positive Number Values only");
			return false;
		}
		else
		if(parseInt(document.forms[0].bundleQty.value)>parseInt(document.forms[0].avlSimStock.value) || parseInt(document.forms[0].bundleQty.value)>parseInt(document.forms[0].avlPairedStock.value) ){
		alert("Quantity To Be Bundled Can't Be Greater Than Available Sim Or Unbundled Qty");
		return false;
		document.forms[0].bundleQty.focus();
		}
		return true;		
}

</SCRIPT>


</HEAD>

<BODY>

<html:form action="/bundleStock.do?methodName=bundleStock">
<table>
<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/bundleStock.gif"
		width="505" height="22" alt="">
	</TD>
</tr>
</table>
	<TABLE width="47%" border="0" class="border" cellpadding="1" cellspacing="1"
		align="center">
		<TBODY>
			<TR>
				<TD colspan="2" align="center"  height="13"></TD>
			</TR>
			<TR>
				<TD width="199" class="txt" height="17">Circle<font color="red">*</font></TD>
				<logic:notEmpty property="circledetails" name="HBOBundleStockForm">
					<TD width="246" height="17"><bean:define id="circle" name="HBOBundleStockForm"
						property="circledetails" /> 
						<html:select property="circle" onchange="getValueChange('sim_avlbstk')" style="width:170px">
						<OPTION value="0">--Select--</OPTION>
						<html:options labelProperty="circlename" property="circle"
							collection="circle" />
					</html:select></td>
				</logic:notEmpty>
			</TR>
			<TR>
				<TD width="199" class="txt" height="18">Product<font color="red">*</font></TD>
				<TD width="246" height="18"><bean:define id="product" name="HBOBundleStockForm"	property="productdetails" /> 
					<html:select property="product" onchange="getChange('pr_avlbstk')" style="width:170px">
					<OPTION value="0">--Select--</OPTION>
				<logic:notEmpty property="productdetails" name="HBOBundleStockForm">					
					<html:options labelProperty="modelcode" property="product_id" collection="product" />
				</logic:notEmpty>
					</html:select></td>
			
			</TR>
			<TR>
				<TD width="199" class="txt" height="14">Available Sim Stock</TD>
				<TD width="246" height="14"><html:text name="HBOBundleStockForm" property="avlSimStock" readonly="true" style="width:170px"></html:text></TD>
			</TR>

			<TR>
				<TD width="199" class="txt" height="10">Available Unbundled Stock</TD>
				<TD width="246" height="10"><html:text name="HBOBundleStockForm" property="avlPairedStock" readonly="true" style="width:170px"></html:text></TD>
			</TR>
			<TR>
				<TD width="199" class="txt" height="20">Quantity To Be Bundled<font color="red">*</font></TD>
				<TD width="246" height="20"><html:text name="HBOBundleStockForm"  property="bundleQty" style="width:170px" maxlength="6"></html:text></TD>
			</TR>
			<TR>
				<TD width="199" colspan="2" height="13"></TD>
			</TR>
			<TR>
				<TD align="right" width="199" height="5"></TD>
				<TD width="246" height="5"><html:submit value="Bundle"  onclick="JavaScript:return blank()" styleClass="medium"></html:submit><html:reset styleClass="medium"></html:reset></TD>
			</TR>
		</TBODY>
	</TABLE>
</html:form>
</BODY>
</html:html>
