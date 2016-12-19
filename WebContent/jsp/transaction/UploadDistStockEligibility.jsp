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
	<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/jScripts/progressBar.js" type="text/javascript">
</SCRIPT>
	
<html>
<head>

<title>Upload Stock Eligibility</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script>
function uploadExcel()
{
	var regionIdOptions= document.getElementById("regionId");
	
	
	var regionId = regionIdOptions.options[regionIdOptions.options.selectedIndex].value;
	
	/*if(regionId =="-1")
	{
		alert("Please Select zone");
		return false;
	}*/
	var productOptions= document.getElementById("productTypeId");
	var productTypeId = productOptions.options[productOptions.options.selectedIndex].value;
	if(productTypeId =="-1")
	{
		alert("Please Select product type");
		return false;
	}
	var productOptions= document.getElementById("distTypeId");
	var productTypeId = productOptions.options[productOptions.options.selectedIndex].value;
	if(productTypeId =="-1")
	{
		alert("Please Select distributor type");
		return false;
	}
	
	if(document.getElementById("uploadedFile").value == "")
	{
		alert("Please Select File");
		return false;
	}
	
	document.forms[0].action="UploadDistStockEligibility.do?methodName=uploadExcel&&regionId="+regionId;
	loadSubmit();
	document.forms[0].submit();
	return true;
}

function showFileFormat(){	
	document.forms[0].action="whdistmappupload.do?methodName=showFormatFile";
	document.forms[0].submit();
	return true;
}

function getCirclesOfZone()
{
	//var Id = document.getElementById("businessCategory").value; 
	var regionIdOptions= document.getElementById("regionId");
	var regionId = regionIdOptions.options[regionIdOptions.options.selectedIndex].value;
	 var url="UploadDistStockEligibility.do?methodName=getCirclesOfZone&&regionId="+regionId;
	if(window.XmlHttpRequest) {
		req = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		req=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if(req==null) {
		alert("Browser Doesn't Support AJAX");
		return;
	}
	req.onreadystatechange = getCirclesOfZoneHandler;
	req.open("POST",url,false);
	req.send();
}

function getCirclesOfZoneHandler()
{
	if (req.readyState==4 || req.readyState=="complete") 
  	{
		var xmldoc = req.responseXML.documentElement;
		if(xmldoc == null){		
			return;
		}
		optionValues = xmldoc.getElementsByTagName("option");
		var selectObj = document.forms[0].selectedCircleId;
		selectObj.options.length = optionValues.length + 1;
			selectObj.options[0].text = "--Select Circle--";
			selectObj.options[0].value = "-1";
		for(var i=0; i < optionValues.length; i++)
		{
			selectObj.options[(i+1)].text = optionValues[i].getAttribute("text");
			selectObj.options[(i+1)].value = optionValues[i].getAttribute("value");
		}
	}
}
function downloadTemplate(templateId){	
	document.forms[0].action="UploadDistStockEligibility.do?methodName=downloadTemplate&&templateId="+templateId;
	document.forms[0].submit();
	return true;
}
</script>
</head>
<body>
<p></p>
<html:form action="/UploadDistStockEligibility.do" method="post" name="ViewStockEligibilityBean" type="com.ibm.dp.beans.ViewStockEligibilityBean" enctype="multipart/form-data">
<html:hidden property="methodName"/>

	<TABLE>
		<TBODY>
			<tr>
			<TD colspan="4" class="text"><BR>
				<IMG src="<%=request.getContextPath()%>/images/UploadStockEligibility.jpg"
				width="505" height="22" alt="">
				
			</TD>
		</tr>
		</TBODY>
	</TABLE>
	<table><tr><td><fieldset class="field_set_main"><legend class="header"><font
					size=2 color=#ff0000 >Upload Eligibility :</font></legend>
	<TABLE width="100%" border="0" cellpadding="2" cellspacing="2"
		align="center" class="border">
		<TBODY>
			<TR>
				<TD colspan='2'></TD>
			</TR>
			<tr>
			<td class="txt" align="right" width="35%"><B>Zone :</B><font color="red">*</font>
			</td>
			<td class="txt" align="left" width="35%">
			
			
			
			
			
			<html:select property="regionId" onchange="getCirclesOfZone()" style="width:150px">
					<logic:notEmpty property="regionList" name="ViewStockEligibilityBean" >
						<html:option value="-1">--All Zone--</html:option>
							<html:optionsCollection name="ViewStockEligibilityBean"
							property="regionList" label="regionName" value="regionId" />
					</logic:notEmpty>
				</html:select>
			
			
			
			<!--  
			<html:select property="regionId" onchange="getCirclesOfZone()" style="width:150px">
					<logic:notEmpty property="regionList" name="ViewStockEligibilityBean" >
						<html:option value="-1">--Select Zone--</html:option>
						<bean:define id="regionList" name="ViewStockEligibilityBean" property="regionList" />
						<html:options labelProperty="regionName" property="regionId" collection="regionList" />
					</logic:notEmpty>
				</html:select>
			</td>
		</tr>
		-->
		
		<tr style="visibility:hidden ">
			<td class="txt" align="right" width="35%"><B>Circle :</B>
			</td>
			<td class="txt" align="left" width="35%">
			
			<html:select property="selectedCircleId" style="width:150px">
			<html:option value="-1">--Select Circle--</html:option>
			</html:select>
			</td>
		</tr>
		
		<tr>
			<td class="txt" align="right" width="35%"><B>Product Type :</B><font color="red">*</font>
			</td>
			<td class="txt" align="left" width="35%">
			<html:select property="productTypeId" style="width:150px">
			<html:option value="-1">--Select Product Type--</html:option>
			<html:option value="1">Commercial</html:option>
			<html:option value="2">Swap</html:option>
			</html:select>
			</td>
		</tr>
		
	 <tr style="height:20px"><td></td></tr>
		
		<tr>
			<td class="txt" align="right" width="35%"><B>Dist Type :</B><font color="red">*</font>
			</td>
			<td class="txt" align="left" width="35%">
			<html:select property="distTypeId" style="width:150px">
			<html:option value="-1">--Select Dist Type--</html:option>
			<html:option value="1">SF</html:option>
			<html:option value="2">SSD</html:option>
			</html:select>
			</td>
		</tr>
		
		 <tr style="height:20px"><td></td></tr>
		
			<TR>
				<TD class="txt" align="right" width="35%"><B>
				<bean:message bundle="dp"
						key="bulkupload.file" /> :
				</B><font color="red">*</font>
				</TD>
				<TD class="txt" ><html:file property="uploadedFile"></html:file>
				<strong><!-- <a href="whdistmappupload.do?methodName=showFormatFile">File Format</a> --><strong>
				</td>
			</TR>
			<tr>
			<td align="center" colspan="2">
			<B><!-- <font color="red" >Please ensure that uploaded file does not contain blank cell</font></B> -->
			</td>
			</tr>
			<TR>
				<TD    align="center" colspan=2><input type="button" id="Submit"
					value="Upload Excel" name="uploadbtn" id="uploadbtn" onclick="uploadExcel();">
				</TD>
				</TR>
				<TR>
					<TD  align="center" colspan=2>
					<p style="visibility:hidden;" id="progress">
					<img id="progress_image" style="visibility:hidden;padding-left:55px;padding-top:500px;width:30;height:34" src="${pageContext.request.contextPath}/images/ajax-loader.gif" /> 
					</p>
					</TD>
				</TR>
				  <tr>
				  	<td  align="center" colspan="2"><strong><font color="red">
					 <bean:write property="strmsg" name="ViewStockEligibilityBean" /> </font><strong>
					 </td>
				  </tr>
				  <tr>
				<html:messages id="msg" property="WRONG_FILE_UPLOAD" bundle="view" message="true">
				  	<tr>
				  	<td  align="center" colspan ="3">	<font color="red" size="2"><strong><bean:write name="msg"/></strong></font>
				  	</td></tr>
			</html:messages>
						
		</TBODY>
	</TABLE></fieldset>
	</td><!--  ading by ram -->
	<td>
		<fieldset class="field_set_main"><legend class="header"><font
					size=2 color=#ff0000>Download Template :</font></legend>
		
				<table>
				<tr><td><b>Swap:</b></td><td></td></tr>
				<tr><td></td><td><a href="#" onClick="javascript :downloadTemplate('1')"><b>Swap SF</b></a></td></tr>
				<tr><td></td><td><a href="#" onClick="javascript :downloadTemplate('2')"><b>Swap SSD</b></a></td></tr>
				<tr><td><b>Commercial:</b></td><td></td></tr>
				<tr><td></td><td><a href="#" onClick="javascript :downloadTemplate('3')"><b>Commercial SF</b></a></td></tr>
				<tr><td></td><td><a href="#" onClick="javascript :downloadTemplate('4')"><b>Commercial SSD</b></a></b></td></tr>
				</table>
				</fieldset>
	</td>
	</tr>
	</table>
	 
</html:form>
 
</body>
</html>
