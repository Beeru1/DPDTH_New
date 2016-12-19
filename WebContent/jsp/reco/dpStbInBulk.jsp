<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html>
<head>
<title>STB IN BULK</title>
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv=Content-Type content="text/html; charset=iso-8859-1">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="theme/text.css" type="text/css">
<link href="${pageContext.request.contextPath}/jsp/hbo/theme/text_new.css" rel="stylesheet" type="text/css">
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/jScripts/progressBar.js" type="text/javascript">
</SCRIPT>
<SCRIPT language="javascript" src="<%=request.getContextPath()%>/scripts/Ajax.js" type="text/javascript">
</SCRIPT>
<script type="text/javascript">

	
function exportToExcel() {
validateForm();


    var fup = document.getElementById("StbFile"); 
	var fileName = fup.value; 
	var ext = fileName.substring(fileName.lastIndexOf('.') + 1); 
	if(ext == "CSV" || ext == "csv" || ext == "Csv" )
	{ 
		//alert("hi in export to excel line 44");
        var url = "stbInBulk.do?methodName=exportToExcel";
		document.forms[0].action=url;
		document.forms[0].method="post";
		setInterval(getReportDownloadStatus,1000);
		document.forms[0].submit();


	}  else { 
		alert("Upload .csv file only"); 
		fup.focus(); 
		return false; 
		} 
}

//added by aman for STB History


function exportToExcelHistory() {
validateForm();


    var fup = document.getElementById("StbFile"); 
	var fileName = fup.value; 
	var ext = fileName.substring(fileName.lastIndexOf('.') + 1); 
	if(ext == "CSV" || ext == "csv" || ext == "Csv" )
	{ 
		
        var url = "stbInBulk.do?methodName=exportToExcelHistory";
		document.forms[0].action=url;
		document.forms[0].method="post";
		setInterval(getReportDownloadStatus,2000);
		document.forms[0].submit();


	}  else { 
		alert("Upload .csv file only"); 
		fup.focus(); 
		return false; 
		} 
}

//end of changes by aman

function validateForm(){
	if(document.getElementById("StbFile").value == ""){
		alert('Please select file to upload');
		return false;
	}
	
}
//Added by sugandha to show error for hist
function chooseError()
{
	var check = '<%=session.getAttribute( "normalexcel" )%>';

	if(check=='normalExcel')
	{	
		document.forms[0].action="stbInBulk.do?methodName=showErrorFile";
		document.forms[0].submit();
		return true;
	}
	else if(check=='HistExcel')
	{
			document.forms[0].action="stbInBulk.do?methodName=showHistErrorFile";
			document.forms[0].submit();
			return true;
		
	}
}
//changes by sugandha  ends here.

function Checkfiles() { 
	//alert("hi")
	var fup = document.getElementById("StbFile"); 
	var fileName = fup.value; 
	var ext = fileName.substring(fileName.lastIndexOf('.') + 1); 
	if(ext == "CSV" || ext == "csv" || ext == "Csv" ) { 
		return true; 
	}  else { 
		alert("Upload .csv file only"); 
		fup.focus(); 
		return false; 
		} 
	} 
		
function setErrorBlank(){
	document.getElementById("errorTr").style.display = 'none';
	//document.getElementById("errorTr1").style.display = 'none';
}
</script>
</head>
<%
	String rowDark = "#FCE8E6";
	String rowLight = "#FFFFFF";
%>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">

	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include
			page="../common/dpheader.jsp" flush="" /></TD>
	</TR>
	<TR valign="top">
		<TD width="167" align="left" bgcolor="b4d2e7" valign="top"
			height="100%"><jsp:include page="../common/leftHeader.jsp"
			flush="" /></TD>
		<TD valign="top" width="100%" height="100%"><html:form
			action="/stbInBulk.do" method="post" name="DPStbInBulkFormBean"
			type="com.ibm.dp.beans.DPStbInBulkBean" enctype="multipart/form-data">
			<html:hidden property="methodName" styleId="methodName" />
			<html:hidden property="error_flag" styleId="error_flag"
				name="DPStbInBulkFormBean" />

			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<TR>
					<TD colspan="4" class="text">:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
				</TR>
				<TR>
					<TD colspan="4" class="text"><IMG
						src="<%=request.getContextPath()%>/images/stbinbulk.jpg"
						width="544" height="26" alt=""></TD>
				</TR>
				<TR>
					<TD colspan="4" class="text"><font color="red" size="2">
					<b><bean:write name="DPStbInBulkFormBean"
						property="transMessage" /></b> </font></TD>
				</TR>
				<TR>
					<TD colspan="4" class="text">:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
				</TR>
				<tr>
					<td colspan="4" class="text">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width='10%'>&nbsp;</td>
							<td width='20%' align="right"><STRONG><FONT color="#000000">
							<bean:message bundle="view" key="lebel.reco.file" /> </FONT></STRONG></td>
							<td width='70%'><FONT color="#003399"><html:file
								size="70" property="stbFile" name="DPStbInBulkFormBean"
								onchange="setErrorBlank();"></html:file></FONT></td>
						</tr>

						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td align="left"><!--<input class="submit" type="submit" tabindex="4" name="Submit" value="Upload" onclick="return validateForm();">
								--><input type=button id="excelBtn" value="Search STB in Bulk"
								onclick="return exportToExcel();">
								<input type=button id="excelBtn" value="Search STB History"
								onclick="return exportToExcelHistory();"></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr id="errorTr" style="display: inline">
							<logic:equal name="DPStbInBulkFormBean" property="error_flag"
								value="true">
								<td align="center" colspan="5"><strong><font
									color="red"> The transaction could not be processed due
								to some errors. Click on <a href="#"
									onclick="return chooseError();">View Details </a> for details. <!-- <input type='button' name="error"  value="Click Here" onclick="return showError();" /> -->
								</font><strong></td>
							</logic:equal>
						</tr>
						<logic:messagesPresent message="true">
							<html:messages id="msg" property="MESSAGE_SENT_SUCCESS"
								bundle="view" message="true">
								<font color="red" size="2"><strong><bean:write
									name="msg" /></strong></font>
							</html:messages>
							<html:messages id="msgfail" property="MESSAGE_SENT_FAIL"
								bundle="view" message="true">
								<font color="red" size="2"><strong><bean:write
									name="msgfail" /></strong></font>
							</html:messages>
							<html:messages id="msg" property="STB_FILE_TYPE_WRONG"
								bundle="view" message="true">
								<font color="red" size="2"><strong><bean:write
									name="msg" /></strong></font>
							</html:messages>
							<html:messages id="msg" property="STB_FILE_SIZE_WRONG"
								bundle="view" message="true">
								<font color="red" size="2"><strong><bean:write
									name="msg" /></strong></font>
							</html:messages>

						</logic:messagesPresent>


					</table>
					</td>
				</tr>
				
				
			</table>
		</html:form></TD>
	</TR>
	<!-- Added by Neetika for BFR 16 on 13aug 2013 Release 3 -->
				<TR>
					<TD  align="center" colspan=4>
					<p style="visibility:hidden;" id="progress">
					<img id="progress_image" style="visibility:hidden;padding-left:55px;padding-top:500px;width:30;height:34" src="${pageContext.request.contextPath}/images/ajax-loader.gif" /> 
					</p>
					</TD>
				</TR>
	<TR align="center" bgcolor="4477bb" valign="top">
		<TD colspan="2" height="17" align="center"><jsp:include
			page="../common/footer.jsp" flush="" /></TD>
	</TR>
</TABLE>
<input type="hidden" name="reportDownloadStatus" value=""> <!-- Neetika BFR 16 14Aug -->
<script type="text/javascript">
/*Method Added by Amrit for BFR 16 of Release 3*/
	function getReportDownloadStatus() {
	var url= "stbInBulk.do?methodName=getReportDownloadStatus";
	var elementId = document.getElementById("reportDownloadStatus");
	var txt = true;
	
	makeAjaxRequest(url,"reportDownloadStatus",txt);
	if(document.getElementById("reportDownloadStatus").value != null && document.getElementById("reportDownloadStatus").value == 'inprogress') 
	{
	document.getElementById("excelBtn").disabled=true;	
    loadSubmit();
    }
    else
    {
    document.getElementById("excelBtn").disabled=false;
    finishSubmit();
	}
	
	}
	</SCRIPT>
</BODY>
</html>

