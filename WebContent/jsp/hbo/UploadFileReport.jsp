
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %> 
<html:html>
<HEAD>    
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="theme/Master.css" rel="stylesheet" type="text/css">
<TITLE>HBO	 ::Uploaded File Report</TITLE>
<SCRIPT language=javascript src="theme/cal.js"> 
</SCRIPT>
</HEAD>
<script language="javascript" src="theme/cal2.js">
</script>
<script language="javascript" src="theme/cal_conf2.js"> 
</script>
<script language="javascript" src="theme/editvalidations.js">
</script>
<SCRIPT language=javascript src="jScripts/fileUploadValidation.js">


</script>
<script language ="javascript">
  function openWindowStatus(fileType,fileId)
  {

 	window.open("status.do?fileType="+fileType+"&fileId="+fileId );
  }
 
  function openWindowStatusDetails(fileType,fileId)
  {

 	window.open("statusDetails.do?fileType="+fileType+"&fileId="+fileId );
  }
</script>
<BODY onload="populate();">
<html:form action="/bulkDataAction" name="HBOFileUploadForm" type="com.ibm.hbo.forms.HBOFileUploadFormBean">
<html:hidden property="repStartDate" name="HBOFileUploadForm" />
<html:hidden property="repEndDate" name="HBOFileUploadForm" />
<html:hidden property="fileType" name="HBOFileUploadForm"/>
<html:hidden name="HBOFileUploadForm" property="contentOfFile"/>
<TABLE cellpadding="4" cellspacing="3" align="center" width="100%">
  
<logic:equal name="HBOFileUploadForm" property="contentOfFile" value="SIM">
	<TR align="center">
		<td height="23" class="colhead"><bean:message bundle="hboView" key="viewUpload.simtitle" /></TD>
	</TR>
</logic:equal>
 <logic:equal name="HBOFileUploadForm" property="contentOfFile" value="PROD">
<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/bulkProductDataReport.gif"
		width="505" height="22" alt="">
	</TD>
</tr>
</logic:equal>

	<TR align="center">
	 	<TD class="border">
		<TABLE cellpadding="6" cellspacing="0" align="center" width="100%">			
			<tr>
			<td align="center" > Date&nbsp;<html:text name="HBOFileUploadForm" readonly="true" property="toDate1"/>
	          	<a href="javascript:showCal('HBOStartDate')"><IMG border="0" height="19" src="images/calendar.gif"></a>
			&nbsp;&nbsp;
			
		<input type="button" value="Go" name="nextRights" class="medium" onclick="setAction();"></td>
			</tr>
			<TR>
				<TD class="border">
				<TABLE align="center" width="100%">
					<TR align="center">
						<TD height="27" class="colhead"><bean:message bundle="hboView" key="uploadFileReport.sno"/></TD>
						<TD height="27" class="colhead"><bean:message bundle="hboView" key="uploadFileReport.fileName"/></TD>
						<TD height="27" class="colhead"><bean:message bundle="hboView" key="uploadFileReport.uploadedBy"/></TD>
						<TD height="27" class="colhead"><bean:message bundle="hboView" key="uploadFileReport.uploadedDate"/></TD>
						<TD height="27" class="colhead"><bean:message bundle="hboView" key="uploadFileReport.processedStatus"/></TD>
						<TD height="27" class="colhead"><bean:message bundle="hboView" key="uploadFileReport.processedDate"/></TD>
						<TD height="27" class="colhead"><bean:message bundle="hboView" key="uploadFileReport.successFile"/></TD>
						<TD height="27" class="colhead"><bean:message bundle="hboView" key="uploadFileReport.errorFile"/></TD>
					</TR>
				    <logic:empty name="HBOFileUploadForm" property="uploadFileList">
						 <TD colspan="4" align="center" ><font color="red">No File found</font></TD>
					</logic:empty>
				
					<logic:notEmpty name="HBOFileUploadForm" property="uploadFileList">
					<logic:iterate id="reportData" name="HBOFileUploadForm" property="uploadFileList"  indexId="i">			
					<TR>
									
						<TD class="txt" align="center"><%=(i.intValue()+1)%>.</TD>	
						<TD  class="txt" align="center"><bean:write name="reportData" property="fileName"/></TD>				
												
						<TD class="txt" align="center"><bean:write name="reportData" property="uploadedby"/></TD>
						<TD class="txt" align="center"><bean:write name="reportData" property="uploadedDate"/></TD>
						<TD class="txt" align="center"><bean:write name="reportData" property="processedStatus"/></TD>
						<TD class="txt" align="center"><bean:write name="reportData" property="processedDate"/></TD>
						
						<logic:equal name="reportData" property="processedStatus" value="Y">
							<TD  class="txt" align="center"><a href="javascript:openWindowStatus('<bean:write name="HBOFileUploadForm" property="fileType"/>','<bean:write name="reportData" property="fileId"/>')" >Status</a></TD>
							<TD  class="txt" align="center"><a href="javascript:openWindowStatusDetails('<bean:write name="HBOFileUploadForm" property="fileType"/>','<bean:write name="reportData" property="fileId"/>')" >Status Details</a></TD>
						</logic:equal>
					
						<logic:notEqual name="reportData" property="processedStatus" value="Y">
							<TD class="txt"  align="center"><bean:message bundle="hboView" key="uploadFileReport.processMessage"/></TD>
							<TD class="txt"  align="center"><bean:message bundle="hboView" key="uploadFileReport.processMessage"/></TD>
						</logic:notEqual>
											
					</TR>			
					</logic:iterate>
					</logic:notEmpty>
				</TABLE>
				</TD>
				
			</TR>
		</TABLE>
		</TD>
	</TR>
</TABLE>
<logic:messagesPresent message="true">
<p align="center">
	<html:messages id="msg" message="true">
	   <b><font color="red" size="2"><bean:write name="msg" filter="false" /></font></b>
   	</html:messages>
</p>
</logic:messagesPresent>
</html:form>	
</BODY>
</html:html>
