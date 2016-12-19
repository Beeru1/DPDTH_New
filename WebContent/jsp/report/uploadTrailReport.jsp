<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>

<HEAD>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<LINK href="${pageContext.request.contextPath}/theme/text.css" rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/theme/chromestyle2.css" />
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/scripts/chrome.js"></SCRIPT>
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/scripts/JScriptLib.js"></SCRIPT>
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/scripts/validation.js"></SCRIPT>

<SCRIPT language="JavaScript"
	src="<%=request.getContextPath()%>/jScripts/GenericReports.js"
	type="text/javascript">
</SCRIPT>

<SCRIPT language="JavaScript"
	src="<%=request.getContextPath()%>/jScripts/progressBar.js"
	type="text/javascript">
</SCRIPT>
<SCRIPT language="javascript"
	src="<%=request.getContextPath()%>/scripts/Ajax.js"
	type="text/javascript">
</SCRIPT>

<SCRIPT language="Javascript" type="text/javascript">



// trim the spaces	
function exportToExcel(obj)
{

var fromDate = document.getElementById("fromDate").value
var toDate = document.getElementById("toDate").value
var uploadType = document.getElementById("uploadType").value;
		if( uploadType == "" || uploadType == "-1")
		{
			document.getElementById("uploadType").focus();
		 alert("Please Select Upload Type..");
		 return false;
		}

			if ((fromDate==null)||(fromDate=="0") || fromDate==""){
				alert('From Date is Required');
				return false;
			}
			
		    if ((toDate==null)||(toDate=="0") || toDate==""){
				alert('To Date is Required');
				return false;
			}
			
			var currentDate = currentDate = new Date();;
			var startDate = new Date(fromDate);
	    	var endDate = new Date(toDate);
	    	 	
	    	
	    	if(startDate > endDate)
	    	{
	    		 alert('From date can not be greater than To Date');
	  		  	 return false;
	    	}
	    	if(endDate > currentDate) {
	    		 	alert('To date can not be greater than todays Date');
	  		  	 	return false;
	    	}
	    	
	    	if(daysBetween(startDate,currentDate) > 365)
	    	{
	    		 alert('From date can not be more than 365 days old from today');
	  		  	 return false;
	    	}
	    	
	      	if(daysBetween(startDate,endDate) >= 31)
			{
				  alert('Please select dates having only max 31 days difference');
					  return false;
			}
		
		var frm = obj.form;
		
		setInterval(getReportDownloadStatus,1000);
		var url = "UploadTrailReport.do?methodName=exportToExcel";
		document.forms[0].action=url;
		document.forms[0].method="post";
	
		document.forms[0].submit();
		return true;
}	

	
</SCRIPT>
</HEAD>
<BODY BACKGROUND="images/bg_main.gif">
		<html:form  action="/UploadTrailReport">	

			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<TR>
					<TD colspan="4" class="text">:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
				</TR>
				<TR>
		<TD colspan="3" valign="bottom" class="dpReportTitle">Upload Stock Transfer Trail Report 
						</TD>
						<TD colspan="4">
				<p style="visibility: hidden;" id="progress"><img
					id="progress_image"
					style="visibility: hidden; padding-left: 55px; padding-top: 500px; width: 30; height: 34"
					src="${pageContext.request.contextPath}/images/ajax-loader.gif" />
				</p>
				</TD>
				</TR>
				<TR>
					<TD colspan="4" class="text"><font color="red" size="2">
					<b></b> </font></TD>
				</TR>
				<TR>
					<TD colspan="4" class="text">:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
				</TR>
				<tr>
					<td colspan="4" class="text">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width='10%'>&nbsp;</td>
					<TD id="fromDateId" align="left" width="100"><b
						class="text pLeft15">Upload Type<STRONG><span id="req"><FONT
						color="RED">*</FONT><span></STRONG></b></TD>
							<td width='70%'><FONT color="#003399">
								<html:select disabled="false" property="uploadType"
								styleId="uploadType" style="width:250px; height:gw80px;">
								<logic:notEmpty name="UploadTrailReportsBean"
									property="uploadTypeList">
								<html:option value="-1">--Select--</html:option>	
								<html:optionsCollection name="UploadTrailReportsBean"
									property="uploadTypeList" label="value" value="id" />
					
						</logic:notEmpty>
					</html:select>
							</FONT></td>
						</tr>

						<tr>
							<td>&nbsp;</td>
			
				
					<TD id="fromDateId" align="left" width="100"><b
						class="text pLeft15">From Date<STRONG><span id="req"><FONT
						color="RED">*</FONT><span></STRONG></b></TD>

					<TD width="277x" align="left"><FONT color="#003399"> <html:text
						 property="fromDate"
						styleClass="box" styleId="fromDate" readonly="true" size="38"
						maxlength="15" name="UploadTrailReportsBean" />
						 <script
						language="JavaScript">
						new tcal ({
							// form name
							'formname': 'UploadTrailReportsBean',
							// input name
							'controlname': 'fromDate'
						});
						</script> </FONT></TD>

						</tr>
				<tr>
							<td>&nbsp;</td>
			
				
					<TD id="fromDateId" align="left" width="100"><b
						class="text pLeft15">To Date<STRONG><span id="req"><FONT
						color="RED">*</FONT><span></STRONG></b></TD>

					<TD width="277x" align="left"><FONT color="#003399"> <html:text
						 property="toDate"
						styleClass="box" styleId="toDate" readonly="true" size="38"
						maxlength="15" name="UploadTrailReportsBean" />
						 <script
						language="JavaScript">
						new tcal ({
							// form name
							'formname': 'UploadTrailReportsBean',
							// input name
							'controlname': 'toDate'
						});
						</script> </FONT></TD>

						</tr>



						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td align="left"><!--<input class="submit" type="submit" tabindex="4" name="Submit" value="Upload" onclick="return validateForm();">
								--><input type=button id="excelBtn" value="Export To Excel"
								onclick="exportToExcel(this);"></td>
						</tr>


					</table>
					</td>
					</tr>
					</table>
	<html:hidden property="methodName"/>

<input type="hidden" name="reportDownloadStatus" value="">

<script>
/*Method Added by Amrit for BFR 16 of Release 3*/
	function getReportDownloadStatus() {
	//if(document.getElementById("reportId") == null)
	//return;	
	var url= "UploadTrailReport.do?methodName=getReportDownloadStatus";
	
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

	//alert("value:" + document.getElementById("reportDownloadStatus").value);
	}
</script>
		</html:form>
</BODY>
</html:html>
