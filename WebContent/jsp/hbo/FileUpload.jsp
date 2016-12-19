<%try{%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import = "com.ibm.virtualization.recharge.dto.UserSessionContext,com.ibm.virtualization.recharge.common.Constants,com.ibm.hbo.common.HBOUser,java.util.ArrayList,com.ibm.hbo.dto.HBORequisitionDTO;" %>
<html:html>

<HEAD>
<LINK href="style/style.css" type=text/css rel=stylesheet>
<TITLE>Upload.jsp</TITLE>
<script language="javascript" src="theme/MstrValidations.js">
</script>
<script>

function setHidden(a){

	document.forms[0].requisition_id.value = a;
	var b = document.forms[0].requisition_id.value;

}
function setInitialHiddelValue(){

	document.forms[0].requisition_id.value = -1;
	var b = document.forms[0].requisition_id.value;
	
}
function frmsubmit()
{

	var reqId = document.forms[0].requisition_id.value;	
	if((document.HBOFileUploadForm.fileType.value=="PROD")&&(document.forms[0].requisition_id.value=="-1"))
	{
		alert("Please Select Requisition Id");
		return false;

	}
	if(document.HBOFileUploadForm.thefile.value==""){
		alert("Please Enter The File Location");
		
		return false;
	}
	else{		
		
		var flag = checkfileExtension();
		if(flag){
			
			document.HBOFileUploadForm.submit();
		return true;
		
		}
		else{
			return false;
		}		
	}
}
function checkfileExtension()
{
	if(document.HBOFileUploadForm.thefile.value.lastIndexOf(".csv")==-1){
		alert("Please Upload Only .csv Extention File");
		document.HBOFileUploadFormBean.thefile.focus();
		return false;
	}
	if(document.HBOFileUploadForm.thefile.value.indexOf(":\\")==-1){
		alert("File Does Not Exist");
		document.HBOFileUploadFormBean.thefile.focus();
		return false;
	}	
	if(!isWhitespace1(document.HBOFileUploadForm.thefile.value)){	
		alert("File Name Should Not Contain Spaces");
		document.HBOFileUploadForm.thefile.focus();
		return false;
	}	
	return true;
}

</script>
</HEAD>
<html:errors property="SYS001" />
<html:errors property="SYS002" />
<html:errors property="BUS003" />
<center>
<html:errors property="BUS004" />
</center>

<html:errors property="BUS005" />
<html:errors property="BUS006" />
<html:errors property="BUS007" />

<BODY onload="setInitialHiddelValue();">

<html:form name="HBOFileUploadForm" action="/insertBulkDataAction?methodName=bulkUpload" type="com.ibm.hbo.forms.HBOFileUploadFormBean" enctype="multipart/form-data" method="post">
<!-- Start for Requisition Id :Parul-->
<%
UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
HBOUser obj=new HBOUser(sessionContext);	
//String actorId = obj.getActorId();
%>
<html:hidden property="requisition_id" name="HBOFileUploadForm"></html:hidden>
<logic:equal name="HBOFileUploadForm" property="fileType" value="PROD">
	<html:hidden name="HBOFileUploadForm" property="contentOfFile" value="PROD"/>
	<html:hidden name="HBOFileUploadForm" property="fileType" value="PROD"/>
<TABLE cellpadding="4" cellspacing="3" align="center" width="75%">
<logic:notEmpty name="HBOFileUploadForm" property="arrReqList">
<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/fileUpload.gif"
		width="505" height="22" alt="">
	</TD>
</tr>		<tr >
		<TD align="center" colspan="10" height="5"></TD>
		</tr>
	<logic:equal name="HBOFileUploadForm" property="userType" value="ROLE_ND">
			<TR>
			<TD class="colhead" align="center"><bean:message bundle="hboView" key="viewRequisition.select"/></TD>
				<TD align="center"  class="colhead"><bean:message bundle="hboView" key="viewRequisition.requisitionNo"/></TD>
				<TD align="center"  class="colhead"><bean:message bundle="hboView" key="viewRequisition.requisitionDate"/></TD>
				<TD align="center"  class="colhead"><bean:message bundle="hboView" key="viewRequisition.modelCode"/></TD>
				<TD align="center"  class="colhead"><bean:message bundle="hboView" key="viewRequisition.forMonth"/></TD>
				<TD align="center"  class="colhead"><bean:message bundle="hboView" key="viewRequisition.qtyRequisitioned"/></TD>
				<TD align="center"  class="colhead"><bean:message bundle="hboView" key="viewRequisition.invoiceNo"/></TD>
				<TD align="center"  class="colhead"><bean:message bundle="hboView" key="viewRequisition.invoiceDt"/></TD>
				<TD align="center"  class="colhead"><bean:message bundle="hboView" key="viewRequisition.qtyUploaded"/></TD>
			</TR>
		<%ArrayList arrRequisitionList =
				(ArrayList) request.getAttribute("requisitionList");
			
			HBORequisitionDTO reqDTO = null;
			int j = -1;
			int oldRequisitionID = 0;
			int newRequisitionID = 0;%>
			<%int k = 0;%>
		<logic:iterate id="i" name="HBOFileUploadForm" property="arrReqList">			
			<TR>
				<%j++;
				reqDTO = (HBORequisitionDTO) arrRequisitionList.get(j);
				oldRequisitionID = reqDTO.getRequisitionId();
				if (newRequisitionID != oldRequisitionID) {%>
				<TD class="txt" align="center">
				<%String temp =	String.valueOf(((HBORequisitionDTO) arrRequisitionList.get(k)).getRequisitionId());%>

					<%if (reqDTO.getSumqtyuploaded() < Integer.parseInt(reqDTO.getQtyRequisition())) {%>
					<html:radio name="HBOFileUploadForm" property="selectFile" 	value="<%=temp%>" onclick="setHidden(this.value);">
					</html:radio>  <%} else {%> X <%}%></TD>
			
				<TD align="center"  class="txt" ><bean:write name="i" property="requisitionId"/></TD>
				<TD align="center"  class="txt" ><bean:write name="i" property="requisitionDate"/></TD>
			<%newRequisitionID = oldRequisitionID;
				} else {%>
					<TD class="txt" align="center">.</td>
					<TD class="txt" align="center">.</td>
					<TD class="txt" align="center">.</td>
				<%}%>
			 	<TD align="center"  class="txt"><bean:write name="i" property="modelCode"/></TD>
				<TD align="center"  class="txt"><bean:write name="i" property="month"/></TD>
				<TD align="center"  class="txt"><bean:write name="i" property="qtyRequisition"/></TD>
    		<logic:notEmpty name="i" property="invoice_no">
				<TD align="center"  class="txt"><bean:write name="i" property="invoice_no"/></TD>
				<TD align="center"  class="txt"><bean:write name="i" property="invoice_dt"/></TD>
				<TD align="center"  class="txt"><bean:write name="i" property="qtyuploaded"/></TD>
			</logic:notEmpty>
			<logic:empty name="i" property="invoice_no">
				<TD align="center"  class="txt">.</td>
				<TD align="center"  class="txt">.</td>
				<TD align="center"  class="txt">.</td>
			</logic:empty>
			</TR>
				<%k++;%>
		</logic:iterate>
	</logic:equal>
	
</logic:notEmpty>	
</TABLE>
</logic:equal>		
<!-- End for Requisition ID : parul-->
<TABLE cellpadding="4" cellspacing="3" align="center" width="100%" border="0">
<logic:equal name="HBOFileUploadForm" property="fileType" value="SIM">
	<html:hidden name="HBOFileUploadForm" property="contentOfFile" value="SIM"/>
	<html:hidden name="HBOFileUploadForm" property="fileType" value="SIM"/>
	<TR align="center">
		<td height="23" ><bean:message bundle="hboView" key="viewUpload.simtitle1" /></TD>
	</TR>
</logic:equal>
<html:hidden name="HBOFileUploadForm" property="message"/>
<TR align="center">
	 	<TD class="border">
			<TABLE cellpadding="6" cellspacing="0" align="center" width="50%" border="0">
			<logic:equal name="HBOFileUploadForm" property="fileType" value="PROD">
			<logic:empty name="HBOFileUploadForm" property="arrReqList">
							<strong><font color="red">No Requisition Data Found</font></strong>
			</logic:empty>
			</logic:equal>		
			<logic:notEmpty name="HBOFileUploadForm" property="arrReqList">
			<tr>	
				<TD ><B><bean:message bundle="hboView" key="viewUpload.upload" /></B></TD>
				<TD ><html:file name="HBOFileUploadForm" property="thefile"/></TD>
			</TR>
			<TR align="center">
				<TD colspan="2">
					<INPUT TYPE="button" value="Upload" class="subbig" onClick="frmsubmit();">									
				</TD>
			</TR>		
			</logic:notEmpty>
			<logic:equal name="HBOFileUploadForm" property="fileType" value="SIM">
			<tr>	
				<TD ><B><bean:message bundle="hboView" key="viewUpload.upload" /></B></TD>
				<TD ><html:file name="HBOFileUploadForm" property="thefile" /></TD>
			</TR>
			<TR align="center">
				<TD colspan="2">
					<INPUT TYPE="button" value="Upload" class="subbig" onClick="frmsubmit();">									
				</TD>
			</TR>		
			</logic:equal>
			

			<logic:equal name="HBOFileUploadForm" property="fileType" value="SIM">
				<TR align="center">
				<TD colspan="2">
					<bean:message bundle="hboView" key="SIM_FILE_SIZE" />									
				</TD>
			</TR>	
				<TR align="center">
				<TD colspan="2">
					<bean:message bundle="hboView" key="SIM_UPLOAD_FORMAT" />								
				</TD>
			</TR>	
			
			</logic:equal>
			<logic:notEmpty name="HBOFileUploadForm" property="arrReqList">
				<logic:equal name="HBOFileUploadForm" property="fileType" value="PROD">
				<TR>
					<TD colspan="2"><bean:message bundle="hboView" key="PROD_FILE_SIZE" /></TD>
				</TR>			
				<TR>
					<TD colspan="2"><bean:message bundle="hboView" key="PROD_UPLOAD_FORMAT" /></TD>
				</TR>
			</logic:equal>
			</logic:notEmpty>
		</TABLE>
		</TD>
	</TR>	

	<logic:equal name="HBOFileUploadForm" property="fileType" value="SIM">
	<TR>
		<TD class="txt" align="center"><A HREF="bulkDataAction.do?methodName=bulkUpload&fileType=SIM&contentOfFile=SIM">Click Here to view report on Bulk SIM Data uploaded</A></TD>
	</TR>
	
	</logic:equal>
	<logic:notEmpty name="HBOFileUploadForm" property="arrReqList">
	<logic:equal name="HBOFileUploadForm" property="fileType" value="PROD">
	<TR>
		<TD class="txt" align="center"><A HREF="bulkDataAction.do?methodName=bulkUpload&fileType=PROD&contentOfFile=PROD">Click Here to view report on Bulk Handset Data uploaded</A></TD>
	</TR>
	</logic:equal>
	</logic:notEmpty>
</TABLE>
</html:form>
<br>
<br>
</BODY>
</html:html>
<%}catch(Exception e){
e.printStackTrace();

}
%>
