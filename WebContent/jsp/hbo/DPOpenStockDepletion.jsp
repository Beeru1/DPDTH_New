<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<LINK rel="stylesheet" href="theme/text.css" type="text/css">
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<html>
<head>

<title>Assign Stock</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script language="JavaScript">

function filterDitributors()
{
	var xmlHttp = GetXmlHttpObject();
	if(xmlHttp == null)
	{
		alert("Your Browser Does Not Support AJAX !!");
		return false;
	}
	else
	{
		var tsmID = document.getElementById("intTSMID").value;
		var url = "initOpeningStockDepletion.do";
		url = url + "?methodName=filterDistributors&intTSMID=" + tsmID;
		
		xmlHttp.onreadystatechange = function()
										{
											setDistributors(xmlHttp);
									 	}			
			xmlHttp.open("POST",url,true);
			xmlHttp.send();
	}
}

function setDistributors(xmlHttp)
{
	if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete")
	{
		var xmldoc = xmlHttp.responseXML.documentElement;
		if(xmldoc == null)
		{		
			return false;
		}
		else
		{
			var optionValues = xmldoc.getElementsByTagName("option");
			var distList = document.getElementById("intDistributorID");
			
			distList.options.length = 0;
			distList.options[0] = new Option("--Select a Distributor--","0");
			for(var i = 0, j = 1; i < optionValues.length; i++, j++)
			{
				distList.options[j] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			}
		}
	}
}

function GetXmlHttpObject()
{
	var xmlHttp = null;
	try
	{  				
 		xmlHttp = new XMLHttpRequest();	// Firefox, Opera 8.0+, Safari
 	}
	catch (e)
	{	  			
 		try
 		{
   			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");	// Internet Explorer
   		}
 		catch (e)
 		{
   			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
   		}
 	}
	return xmlHttp;
}

function getOpenStockBalance()
{
	var distID = document.getElementById("intDistributorID").value;
	if(distID == '0')
	{
		alert('Select a Distributor');
		document.getElementById("intDistributorID").focus();
		document.getElementById("intProductID").value = '0';
		return false;
	}
	
	var prodID = document.getElementById("intProductID").value;
	if(prodID ==  '0')
	{
		document.getElementById("intCurrentStock").value = '';
		document.getElementById("intStockDeplete").value = '';		
		return false;
	}
	
	var xmlHttp = GetXmlHttpObject();
	if(xmlHttp == null)
	{
		alert("Your Browser Does Not Support AJAX !!");
		return false;
	}
	else
	{
		var url = "initOpeningStockDepletion.do";
		url = url + "?methodName=getOpenStockBalance&intDistributorID="+distID+"&intProductID="+prodID;
		
		xmlHttp.onreadystatechange = function()
										{
											setOpenStockBalance(xmlHttp);
									 	}			
			xmlHttp.open("POST",url,true);
			xmlHttp.send();
	}
}

function setOpenStockBalance(req)
{
	var elementId = "intCurrentStock";
	
	if(req.readyState == 4 || req.readyState == "complete")
	{
		if(req.status != 200)
		{
			document.getElementById(elementId).value = "Exception During Transaction" ;
		}
		else
		{
			var text = req.responseText;
			try
			{
				text = parseInt(text);
				
				if(text != null)
					document.getElementById(elementId).value = text; 		
				else
					document.getElementById(elementId).value = "0" ; 
			}
			catch(e)
			{
				document.getElementById(elementId).value = "Exception During Transaction" ;
			}
		}
	}
}
function resetPage()
{
	document.getElementById("intTSMID").value = '0';
	document.getElementById("intDistributorID").value = '0';
	document.getElementById("intProductID").value = '0';
	document.getElementById("intCurrentStock").value = '';
	document.getElementById("intStockDeplete").value = '';		
}

function depleteOpenStock()
{
	var alertMsg = vaildateDepleteStock();
	if(alertMsg == '')
	{
		document.getElementById("methodName").value = 'depleteOpenStock';
		document.forms[0].submit();
	}
	else
	{
		alert(alertMsg);
	}
}
function vaildateDepleteStock()
{
	var alertMsg = '';
	if(document.getElementById("intDistributorID").value == '0')
	{
		alertMsg = 'Select a Distributor\n';
	}
	
	if(document.getElementById("intProductID").value == '0')
	{
		alertMsg += 'Select a Product\n';
	}
	else
	{
		var currStock = document.getElementById("intCurrentStock").value;
		var depleteStock = document.getElementById("intStockDeplete").value;
		
		if(currStock == '')
		{
			alertMsg +="No Current Stock available\n";
		}
		else if(isNaN(currStock))
		{
			alertMsg +="Wrong value of Current Stock.. Reselect the values\n";
		}
		else
		{
			currStock = parseInt(currStock);
			
			if(depleteStock  == '')
			{
				alertMsg +="Enter some stock to deplete\n";
			}
			else if(isNaN(depleteStock))
			{
				alertMsg +="Stock to be Depleted should be numeric\n"
				document.getElementById("intStockDeplete").value = '';
			}
			/*else 
			{
				depleteStock = parseInt(depleteStock);
				
				if(depleteStock<1)
				{
					alertMsg +="Stock to be Depleted should be greater than 0\n";
					document.getElementById("intStockDeplete").value = '';
				}
				else if(depleteStock>currStock)
				{
					alertMsg +="Stock to be Depleted should be less than Current Stock\n";
					document.getElementById("intStockDeplete").value = '';
				}
			}*/
		}
	}
	
	return alertMsg;
}

function resetRestPage()
{
	document.getElementById("intProductID").value = '0';
	document.getElementById("intCurrentStock").value = '';
	document.getElementById("intStockDeplete").value = '';
}
</script>

<style type="text/css">

.fieldvalueGrayReadOnly {
	FONT-WEIGHT: regular;
	FONT-SIZE: 10px;
	COLOR: #000000;
	background: #c0c0c0;
	FONT-FAMILY: verdana,arial,helvetica
}

.fieldvalue {
	FONT-WEIGHT: regular;
	FONT-SIZE: 10px;
	COLOR: #000000;
	FONT-FAMILY: verdana,arial,helvetica
}

</style>
</head>
<body>
<p></p>
<html:form action="/initOpeningStockDepletion.do" >
<html:hidden property="methodName"/>
	<TABLE>
		<TBODY>
			<tr>
			<TD colspan="4" class="text"><BR>
				<IMG src="<%=request.getContextPath()%>/images/open_Stock_Correction.png"
				width="505" height="22" alt="">
				<logic:notEmpty property="strSuccessMsg" name="OpeningStockDepletionFormBean">
					<BR>
						<strong><font color="red"><bean:write property="strSuccessMsg" name="OpeningStockDepletionFormBean"/></font></strong>
					<BR>
				</logic:notEmpty>
			</TD>
		</tr>
		</TBODY>
	</TABLE>
	<TABLE width="46%" border="0" cellpadding="2" cellspacing="2" align="center" class ="border">
		<TBODY>
			<TR>
				<TD width="100%" colspan='2'></TD>
			</TR>
			
			<TR>
				<TD class="txt" width="50%">
					<B><bean:message bundle="hboView" key="open.stock.TSM"/></B><font color="red">*</font>
				</TD>
				
				<TD class="txt" width="50%">
					<html:select property="intTSMID" onchange="filterDitributors()" style="width:200px">
						<html:option value="0">
							<bean:message key="open.stock.SELECT_TSM" bundle="hboView"/>
						</html:option>
						
						<html:optionsCollection property="listTSM" label="strTSMName" value="intTSMID"/>
					</html:select>
				</TD>
			</TR>

 
			<tr>
				<TD class="txt" width="50%">
					<B><bean:message bundle="hboView" key="open.stock.DIST"/></B><font color="red">*</font>
				</td>
				<TD class="txt" width="50%">
					<html:select property="intDistributorID" onchange="resetRestPage()" style="width:200px">
						<html:option value="0">
							<bean:message key="open.stock.SELECT_DIST" bundle="hboView"/>
						</html:option>
						
						<html:optionsCollection property="listDist" label="strDistName" value="intDistID"/>
					</html:select>				
				</TD>
			</tr>
			<tr>
				<TD class="txt" width="50%">
					<B><bean:message bundle="hboView" key="open.stock.PROD_TYPE"/></B><font color="red">*</font>
				</TD>
				<TD class="txt" width="50%">
					<html:select property="intProductID" onchange="getOpenStockBalance()" style="width:200px">
						<html:option value="0">
							<bean:message key="open.stock.SELECT_PROD" bundle="hboView"/>
						</html:option>
						
						<html:optionsCollection property="listProduct" label="strProdName" value="intProdID"/>
					</html:select>	
				</td>
			</tr>
			
			<TR>
				<TD class="txt" width="50%">
					<B><bean:message bundle="hboView" key="open.stock.CURRENT_STOCK"/></B><font color="red">*</font>
				</TD>
				<TD class="txt" width="50%">
					<html:text property="intCurrentStock" readonly="true" styleClass="fieldvalueGrayReadOnly" size="15"></html:text>
				</TD>
			</TR>
			
			<TR>
				<TD class="txt" width="50%">
					<B><bean:message bundle="hboView" key="open.stock.STOCK_DEPLETE"/></B><font color="red">*</font>
				</TD>
				<TD class="txt" width="50%">
					<html:text property="intStockDeplete" styleClass="fieldvalue" size="15"></html:text>
				</TD>
			</TR>
			
			<TR>
				<TD width="100%" colspan='2'>&nbsp;</TD>
			</TR>
			<TR>
				<TD align="right" width="50%"></TD>
				<TD align="left" width="50%">
					<input type="button" id="Submit" value="Submit" onclick="depleteOpenStock();">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="Reset" onclick="resetPage();">
				</TD>								
			</TR>
		</TBODY>
	</TABLE>
</html:form>
</body>
</html>
