<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	//document.forms[0].methodName.value="uploadClosingStockDetailsXls";
	document.forms[0].submit();
	
}
</script>
</head>
<BODY background="/DPDTH/images/bg_main.gif"  bgcolor="#FFFFFF">
<p></p>
<html:form action="/closeInactivePartners.do" name="CloseInactivePartnersBean" type="com.ibm.dp.beans.CloseInactivePartnersBean" enctype="multipart/form-data">
<html:hidden property="methodName" value ="uploadClosingInactivePartnersDetailsXls"/>
<html:hidden property="olmid" name="CloseInactivePartnersBean"/>
<html:hidden property="status" name="CloseInactivePartnersBean"/>

<html:hidden property="browseButtonName" name="CloseInactivePartnersBean"/>



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
					 <bean:write property="strmsg" name="CloseInactivePartnersBean" /> </font><strong>
					 </td>
				</tr>
				<TR>
				<TD class="txt" align="right" width="500"><B>Please upload an excel file with first column having OLM id
				 of Inactive Partners and second column having status as 'C' for closed.</B>
				</TR>
				
				
				
			<TR>
				<TD class="txt" align="right" width="500"><B>Select File :</B><font color="red">*</font>
				</TD>
				<TD class="txt" align="center">
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
<logic:present name="details">
   	 <!--<input type="text" size="150" height="50" width="50"  value="${requestScope.details.olmid}" disabled ></input>

<textarea rows="10" cols="50"  value="${requestScope.details.olmid}" disabled  ></textarea>
-->

<b>Partners Closed Successfully </b>: <html:textarea name="details" property="olmid" disabled="true" rows="8" cols="80"> </html:textarea>
</logic:present>
			
	
</html:form>
</body>
</html>
