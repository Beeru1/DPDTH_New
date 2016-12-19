<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<LINK rel="stylesheet" href="theme/text.css" type="text/css">
<head>
<script>
	function uploadExcel()
	{
		if(document.getElementById("uploadedFile").value == "")
		{
			alert("Please Select File");
			return false;
		}
		document.forms[0].action="retailerLapuDataAction.do?methodName=uploadExcel";
		 document.getElementById("uploadbtn").disabled = true;
		document.forms[0].submit();
		return true;
	}
	
</script>
</head>

<html>
	<body>
		<html:form action="/retailerLapuDataAction.do" method="post" name="DPRetailerLapuDataBean" type="com.ibm.dp.beans.DPRetailerLapuDataBean" enctype="multipart/form-data">
			<html:hidden property="methodName" value="somevalue"/>

		<TABLE width="100%" border="0" cellpadding="2" cellspacing="2" align="center" class="border">
		<TBODY>
				<tr>
			<TD colspan="4" class="text"><BR>
				<IMG src="<%=request.getContextPath()%>/images/retailers_Lapu_Numbers.jpg"
				width="505" height="22" alt="">
				
			</TD>
		</tr>
			<TR>
				<TD class="txt" align="right" width="35%">
					<B><bean:message bundle="dp" key="bulkupload.file" /> :</B><font color="red">*</font>
				</TD>
				<TD class="txt" align="left" width="65%">
					<html:file property="uploadedFile"></html:file>
					<strong><a href="retailerLapuDataAction.do?methodName=exportToExcel" >Download Lapu Data File </a><strong>
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
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</TD>
			</TR>
			<logic:equal name="DPRetailerLapuDataBean" property="error_flag" value="true" >
				  <tr>
				  	<td  align="center" colspan="2"><strong><font color="red">
							  The transaction could not be processed due to some errors. Click on <a href="retailerLapuDataAction.do?methodName=showErrorFile">View Details </a> 
					  for details.</font><strong>
					 </td>
				  </tr>
			</logic:equal>
			<tr>
				<td  align="center" colspan="2"><strong><font color="red">
					 <bean:write property="strmsg" name="DPRetailerLapuDataBean" /> </font><strong>
				</td>
			</tr>
			</TBODY>
			</TABLE>
	</html:form>
	</body>
</html>