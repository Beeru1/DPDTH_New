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

<title>Assign Stock</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script>
function uploadExcel()
{

	if(document.getElementById("uploadedFile").value == "")
	{
		alert("Please Select File");
		return false;
	}
	document.forms[0].methodName.value="uploadExcel";
	loadSubmit();
	document.forms[0].submit();
}
</script>
</head>
<body>
<p></p>
<html:form action="/nsBulkupload.do" method="post" name="NSBulkUploadBean" type="com.ibm.dp.beans.NSBulkUploadBean" enctype="multipart/form-data">
<html:hidden property="methodName"/>

	<TABLE>
		<TBODY>
			<tr>
			<TD colspan="4" class="text"><BR>
				<IMG src="<%=request.getContextPath()%>/images/NSBulkUpload.jpg"
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
				<strong><a href="nsBulkupload.do?methodName=exportExcel">Download Data File</a><strong>
				</TD>
			</TR>

			<TR>
				<TD colspan='2'>&nbsp;</TD>
			</TR>
			<TR>
				<TD align="center" align="center" colspan=2><input type="button" id="Submit"
					value="Upload Excel" name="uploadbtn" id="uploadbtn" onclick="uploadExcel();">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
			</TR>
			<logic:equal name="NSBulkUploadBean" property="error_flag" value="true" >
				  <tr>
				  	<td  align="center" colspan="2"><strong><font color="red">
							  The transaction could not be processed due to some errors. Click on <a href="nsBulkupload.do?methodName=showErrorFile">View Details </a> 
					  for details.</font><strong>
					  
					 </td>
				  </tr>
				  </logic:equal>
				  <tr>
				  	<td  align="center" colspan="2"><strong><font color="red">
					 <bean:write property="strmsg" name="NSBulkUploadBean" /> </font><strong>
					 </td>
				  </tr>
				  <!-- Added by Neetika for BFR 16 on 8 aug 2013 Release 3 -->
				<TR>
					<TD  align="center" colspan=2>
					<p style="visibility:hidden;" id="progress">
					<img id="progress_image" style="visibility:hidden;padding-left:55px;padding-top:500px;width:30;height:34" src="${pageContext.request.contextPath}/images/ajax-loader.gif" /> 
					</p>
					</TD>
				</TR>
				  
		</TBODY>
	</TABLE>
</html:form>
</body>
</html>
