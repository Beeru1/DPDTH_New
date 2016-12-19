<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>


<html>
<head>
<link rel="stylesheet" href="../../theme/Master.css" type="text/css">
<title>Assign Stock</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script language="JavaScript">
	
	function prodList(){
		var Index = document.getElementById("category").selectedIndex;
		var element=document.getElementById("category").options[Index].value;
		var prodCategory=element.substring(0,element.indexOf("#"));
		var url= "initAssignADStock.do?methodName=getProdList&cond1="+prodCategory;
		var elementId = "product" ;
		ajax(url,elementId);
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

	function validateData(){
	
	if(document.getElementById('category').value == ""){
			alert("Please Select Business Category");
			document.forms[0].category.focus();
			return 0;
	}

	if(document.getElementById('product').value == ""){
			alert("Please Select Product");
			document.forms[0].product.focus();
			return 0;
	 }
	 if(document.HBOFileUploadForm.thefile.value == ""){
		alert("Please Select File To Be Uploaded");
		document.forms[0].thefile.focus();
		return 0;
	 }
	 
	 if(document.HBOFileUploadForm.thefile.value.lastIndexOf(".csv")==-1){
		alert("Please Upload Only .csv Extention File");
		document.forms[0].thefile.focus();
		return 0;
	}
	
	if(document.HBOFileUploadForm.thefile.value.indexOf(":\\")==-1){
		alert("File Does Not Exist");
		document.forms[0].thefile.focus();
		return false;
	}	
			
	return 1;
	}
	
	function pageSubmit(){
		if (validateData()==1){
		document.HBOFileUploadForm.submit();
		}
	}
	
	function resetting(){
 		document.getElementById("category").value="";	
		document.getElementById("product").value="";
		document.getElementById("thefile").value==""
	}
		
	</script>



</head>
<body> 
<html:form name="HBOFileUploadForm" type="com.ibm.hbo.forms.HBOFileUploadFormBean" action="/uploadDistStockAction.do" enctype="multipart/form-data" method="post">
<html:hidden property="methodName" value="bulkUpload"/>
<table>
<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/bulkAssign.gif"
		width="505" height="22" alt="">
	</TD>
</tr>
</table>
	<TABLE width="46%" border="0" cellpadding="1" cellspacing="1"
		align="center" class ="border">
		<TBODY>
			<TR>
				<TD width="222"></TD>
				<TD width="161"></TD>
			</TR>
			<TR  id="busCategory">
				<TD class="txt" width="222"><bean:message bundle="hboView" key="assignADStock.Category"/><font color="red">*</font></TD>
				<TD class="txt" width="161">
				<html:select property="category" onchange="prodList()" style="width:230px">
					<logic:notEmpty property="arrCategory" name="HBOFileUploadForm">
						<html:option value="">--Select A Category--</html:option>
						<bean:define id="buscategory" name="HBOFileUploadForm" property="arrCategory"></bean:define>
						<html:options labelProperty="bname" property="bcodeFlag" collection="buscategory" />
					</logic:notEmpty>
				</html:select>
				</TD>
			</TR>
			<TR>
			<TD class="txt" width="222"><bean:message bundle="hboView" key="assignADStock.Product"/><font color="red">*</font></TD>
				<TD class="txt" width="161">
				<html:select property="product"	style="width:230px">
						<html:option value="">--Select A Product--</html:option>
				</html:select></TD>
			</TR>
			
			<TR>	
				<TD class="txt" width="222"><bean:message bundle="hboView" key="viewUpload.upload" /><font
					color="red">*</font></TD>
				<TD ><html:file name="HBOFileUploadForm" property="thefile" /></TD>
			</TR>
			
			<TR>
				<TD align="right" width="222"><input type="button" value="Reset" onclick="resetting();" ></TD>
				<TD align="left" width="161">
				<input type="button" value="Upload" onclick="pageSubmit();">
				</TD>
			</TR>
			<TR style="hidden" id="commentId">
			</TR>
			
			</TBODY>
	</TABLE>
	<br>
	<br>
		
</html:form>
</body>
</html>
