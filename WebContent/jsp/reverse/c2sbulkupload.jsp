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
<script>
function uploadExcel()
{

	if(document.getElementById("stbsrno").value == "")
	{
		alert("Please Select File");
		return false;
	}
	document.forms[0].methodName.value="uploadExcel";
	
	//document.forms[0].action="/c2sbulkupload.do";
	//document.forms[0].method="post";
	document.forms[0].submit();
}
</script>
</head>
<body>
<p></p>
<html:form action="/c2sbulkupload.do" method="post" name="C2SBulkUploadBean" type="com.ibm.dp.beans.C2SBulkUploadBean" enctype="multipart/form-data">
<html:hidden property="methodName"/>

	<TABLE>
		<TBODY>
			<tr>
			<TD colspan="4" class="text"><BR>
				<IMG src="<%=request.getContextPath()%>/images/C2S_Bulk.jpg"
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
				<bean:message bundle="view"
						key="c2s.bulkupload.file" /> :
				</B><font color="red">*</font>
				</TD>
				<TD class="txt" align="left" width="65%"><html:file property="stbsrno"></html:file>
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
			<tr>
				<TD align="center" colspan=2><logic:notEmpty property="strmsg"
					name="C2SBulkUploadBean">
					<font color="RED"><strong><bean:write
						property="strmsg" name="C2SBulkUploadBean" /></strong></font>
				</logic:notEmpty></TD>
			</tr>
		</TBODY>
	</TABLE>
</html:form>
</body>
</html>
