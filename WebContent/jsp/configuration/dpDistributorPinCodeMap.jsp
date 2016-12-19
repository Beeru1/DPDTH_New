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
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/jScripts/progressBar.js" type="text/javascript">
</SCRIPT>
<script>

function exportToExcel()
{
	document.forms[0].action = "dpDistPinCodeMapAction.do?methodName=exportExcel";
	loadSubmit();
	document.forms[0].submit();
}
function uploadExcel()
{

	if(document.getElementById("uploadedFile").value == "")
	{
		alert("Please Select File");
		return false;
	}
	document.forms[0].action="dpDistPinCodeMapAction.do?methodName=uploadExcel";
	loadSubmit();
	document.forms[0].submit();
}

function showError()
{	
	document.forms[0].action="dpDistPinCodeMapAction.do?methodName=showErrorFile";
	document.forms[0].submit();
	return true;
}
/*
function showFileFormat(){	
	document.forms[0].action="dpProductSecurityListBulkupload.do?methodName=showFormatFile";
	document.forms[0].submit();
	return true;
}
*/
</script>
</head>
<body>
<p></p>
<html:form action="/dpDistPinCodeMapAction.do" method="post" name="DPDistPinCodeMapFormBean" type="com.ibm.dp.beans.DPDistPinCodeMapFormBean" enctype="multipart/form-data">
<html:hidden property="methodName"/>

	<TABLE>
		<TBODY>
			<tr>
			<TD colspan="4" class="text"><BR>
				<IMG src="<%=request.getContextPath()%>/images/distPinCodeMap.jpg"
				width="505" height="22" alt="">
				
			</TD>
		</tr>
		</TBODY>
	</TABLE>
	<TABLE width="100%" border="0" cellpadding="2" cellspacing="2"
		align="center" class="border">
		<TBODY>
			<TR>
				<TD colspan='2'></TD>
			</TR>



			<TR>
				<TD class="txt" align="right" width="35%"><B>
				<bean:message bundle="dp"
						key="bulkupload.file" /> :
				</B><font color="red">*</font>
				</TD>
				<TD class="txt" align="left" width="65%"><html:file property="uploadedFile"></html:file>
				<strong><a href="dpDistPinCodeMapAction.do?methodName=exportExcel">Download Existing OLM ID & Pincode mapping </a><strong>
				
				</TD>
				
				
			</TR>

			<tr>
			<td align="center" colspan="2">
			<B><font color="red" >Please ensure that uploaded file does not contain blank cell</font></B>
	
			</td>
			</tr>
			<TR>
				<TD colspan='2'>&nbsp;</TD>
			</TR>
			<TR>
				<TD align="center" align="center" colspan=2><input type="button" id="Submit"
					value="Upload Excel" name="uploadbtn" id="uploadbtn" onclick="uploadExcel();">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
			</TR>
			<logic:equal name="DPDistPinCodeMapFormBean" property="error_flag" value="true" >
				  <tr>
				  	<td  align="center" colspan="2"><strong><font color="red">
							  The Distributor PinCode mapping  could not be updated due to some errors. Click on <a href="dpDistPinCodeMapAction.do?methodName=showErrorFile">View Details </a> 
					  for details.</font><strong>
				  	
					 </td>
				  </tr>
				  </logic:equal>
				  <tr>
				  	<td  align="center" colspan="2"><strong><font color="red">
					 <bean:write property="strmsg" name="DPDistPinCodeMapFormBean" /> </font><strong>
					 </td>
				  </tr>
				 
				  
		</TBODY>
		<!-- Added by Neetika for BFR 16 on 8 aug 2013 Release 3 -->
				<TR>
					<TD  align="center" colspan=5>
					<p style="visibility:hidden;" id="progress">
					<img id="progress_image" style="visibility:hidden;padding-left:55px;padding-top:500px;width:30;height:34" src="${pageContext.request.contextPath}/images/ajax-loader.gif" /> 
					</p>
					</TD>
				</TR>
	</TABLE>
</html:form>
</body>
</html>
