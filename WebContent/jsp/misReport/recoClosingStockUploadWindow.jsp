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

<title>Upload Excel</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script>
function uploadExcel()
{

	var fileTypeAlert = "Only Excel file can be uploaded.";
	if(document.getElementById("uploadedFile").value == "")
	{
		alert("Please Select File");
		return false;
	}
    else if(!(/^.*\.xls$/.test(document.getElementById("uploadedFile").value)) && !(/^.*\.xlsx$/.test(document.getElementById("uploadedFile").value)) ) {
		alert(fileTypeAlert);
		return false;
	}else if(navigator.userAgent.indexOf("MSIE") != -1) {
		if(document.getElementById("uploadedFile").value.indexOf(":\\") == -1 ) {
			alert("Please Select a proper file.");
			return false;
		}
	}
	loadSubmit();
	document.forms[0].methodName.value="uploadClosingStockDetailsXls";
	document.forms[0].submit();
}
</script>
</head>
<BODY background="/DPDTH/images/bg_main.gif"  bgcolor="#FFFFFF">
<p></p>
<html:form action="/DistReco.do" method="post" name="DistRecoBean" type="com.ibm.dp.beans.DistRecoBean" enctype="multipart/form-data">
<html:hidden property="methodName"/>
<html:hidden property="tabId" name="DistRecoBean"/>
<html:hidden property="columnId" name="DistRecoBean"/>
<html:hidden property="stbTypeId" name="DistRecoBean"/>
<html:hidden property="browseButtonName" name="DistRecoBean"/>
<html:hidden property="recoPeriodId" name="DistRecoBean"/>


	<TABLE width="100%" border="0" cellpadding="2" cellspacing="2"
		align="center" class="border">
		<TBODY>
		<TR>
					<TD  align="center" colspan=2>
					<p style="visibility:hidden;" id="progress">
					<img id="progress_image" style="visibility:hidden;padding-left:55px;px;width:30;height:34" src="${pageContext.request.contextPath}/images/ajax-loader.gif" /> 
					</p>
					</TD>
				</TR>
		  <tr>
				  <td  align="center" colspan="2"><strong><font color="red">
					 <bean:write property="strmsg" name="DistRecoBean" /> </font><strong>
					 </td>
				</tr>
			<TR>
				<TD class="txt" align="right"><B>Select File :</B><font color="red">*</font>
				</TD>
				<TD class="txt" align="left">
				<input type="file" id="uploadedFile" name="uploadedFile">
				</TD>
			</TR>
			<TR>
				<TD align="center" align="center" colspan=2><input type="button" id="Submit"
					value="Upload Excel" name="uploadbtn" id="uploadbtn" onclick="uploadExcel();">
				</TD>
			</TR>
				
				  
		</TBODY>
	</TABLE>
</html:form>
</body>
</html>
