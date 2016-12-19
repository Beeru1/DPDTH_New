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
	if(document.HBOFileUploadForm.thefile.value == "" || document.HBOFileUploadForm.thefile.value == null){
		alert("Please Select Any File To Upload");
		document.HBOFileUploadForm.thefile.focus();
		return false;
	}
	if(document.HBOFileUploadForm.thefile.value.lastIndexOf(".csv")==-1){
		alert("Please Upload Only .csv Extention File");
		document.HBOFileUploadForm.thefile.value == "";
		document.HBOFileUploadForm.thefile.focus();
		return false;
	}
	if(document.HBOFileUploadForm.thefile.value.indexOf(":\\")==-1){
		alert("File Does Not Exist");
		document.HBOFileUploadForm.thefile.focus();
		return false;
	}	
	if(!isWhitespace1(document.HBOFileUploadForm.thefile.value)){	
		alert("File Name Should Not Contain Spaces");
		document.HBOFileUploadForm.thefile.focus();
		return false;
	}else	
	document.getElementById("methodName").value="damageUploadFile";
	
	//alert();
	return true;
}

function fileFormat(){
	window.open("<%=request.getContextPath()%>/jsp/hbo/Book1.csv","","width=850,scrollbars");
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

<BODY>
<center>
<font color="red"> 
<html:errors property="uploadFile" />
</font>
</center>
<html:form name="HBOFileUploadForm" action="/initUploadDamageFile.do?methodName=damageUploadFile" type="com.ibm.hbo.forms.HBOFileUploadFormBean" enctype="multipart/form-data" method="post">
<%
UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
HBOUser obj=new HBOUser(sessionContext);	
//String actorId = obj.getActorId();
%>
<html:hidden property="requisition_id" name="HBOFileUploadForm"></html:hidden>
<html:hidden property="methodName" name="HBOFileUploadForm" value="damageUploadFile"/>
<logic:equal name="HBOFileUploadForm" property="fileType" value="PROD">
	<html:hidden name="HBOFileUploadForm" property="contentOfFile" value="PROD"/>
	<html:hidden name="HBOFileUploadForm" property="fileType" value="PROD"/>
</logic:equal>		
<TABLE cellpadding="4" cellspacing="3" align="center" width="100%" border="0">
<html:hidden name="HBOFileUploadForm" property="message"/>
<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/bulkHLRDeletion.gif"
		width="505" height="22" alt="">
	</TD>
</tr>
<TR>
		<td class="text pLeft15" width="162"><font color="#4b0013"><strong>File Upload<FONT color="red">*</FONT>:</strong></font> </TD>
				<TD width="820"><html:file property="thefile" name="HBOFileUploadForm"/></TD>
			</TR>
			 <tr>  
		        <td class="text pLeft15" width="162"><font color="#4b0013">&nbsp;</font></td>
           		<td width="820">
				  <table width="140" border="0" cellspacing="0" cellpadding="5">
                      <tr align="center">
                         <td> <input type="submit" value="Submit" class="subbig" onclick="return checkfileExtension();"></td>
                      </tr>
                  </table>
		        </td>
        	 </tr>
        	 <tr>
        	 	<td colspan="2">
				<font color="red">
       	 		File Format(From,To):<br>
					1. Column 1(From) contains starting Serial No.<br>
					2. Column 2(To) contains ending Serial No.<br>
					<br>
					Instructions for File upload:<br>
					1. Serial Nos must have numerical values only.<br>
					2. Serial No length cannot exceed 20 characters.<br>
				</font>	
        	 	</td>
        	 </tr>
        	 <tr>
        	 	<td colspan="2" align="center"><a href="#" onclick="fileFormat()">Click here to download File format</a></td>
        	 </tr>
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
