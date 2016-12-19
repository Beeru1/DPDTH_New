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

<title>Pending List Transfer Bulk Upload</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/jScripts/progressBar.js" type="text/javascript">
</SCRIPT>
<script>
function uploadExcel()
{

	if(document.getElementById("uploadedFile").value == "")
	{
		alert("Please Select File");
		return false;
	}
	document.forms[0].action="dpInterSSDTransferBulkupload.do?methodName=uploadExcel";
	loadSubmit();
	document.forms[0].submit();
}

</script>
</head>
<body>
<p></p>
<html:form action="/dpInterSSDTransferBulkupload.do" method="post" name="DPInterSSDTransferBulkUploadBean" type="com.ibm.dp.beans.DPInterSSDTransferBulkUploadBean" enctype="multipart/form-data">
<html:hidden property="methodName"/>

	<TABLE>
		<TBODY>
			<tr>
			<TD colspan="4" class="text"><BR>
				<IMG src="<%=request.getContextPath()%>/images/Inter_SSD_Transfer_Bulk.jpg"
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
				<strong><a href="dpInterSSDTransferBulkupload.do?methodName=showFormatFile">Download Template File </a><strong>
				
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
			<logic:equal name="DPInterSSDTransferBulkUploadBean" property="error_flag" value="true" >
				  <tr>
				  	<td  align="center" colspan="2"><strong><font color="red">
							  The transaction could not be processed due to some errors. Click on <a href="dpInterSSDTransferBulkupload.do?methodName=showErrorFile">View Details </a> 
					  for details.</font><strong>
				  	
					 </td>
				  </tr>
				  </logic:equal>
				  <tr>
				  	<td  align="center" colspan="2"><strong><font color="red">
					 <bean:write property="strmsg" name="DPInterSSDTransferBulkUploadBean" /> </font><strong>
					 </td>
				  </tr>
				 
				   <!-- Added by Neetika for BFR 16 on 8 aug 2013 Release 3 -->
				<TR>
					<TD  align="center" colspan=2>
					<p style="visibility:hidden;" id="progress">
					<img id="progress_image" style="visibility:hidden;padding-left:55px;padding-top:500px;width:30;height:34" src="/DPDTH/images/ajax-loader.gif" /> 
					</p>
					</TD>
				</TR>
		</TBODY>
	</TABLE>
</html:form>
</body>
</html>
