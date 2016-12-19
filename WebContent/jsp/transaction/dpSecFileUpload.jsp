<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
 
<html:html>

<HEAD>
<LINK href="style/style.css" type=text/css rel=stylesheet>
<TITLE>Upload File</TITLE>

<script>
function frmsubmit()
{
	if(document.dpSecFileUpload.thefile.value==""){
		alert("Please Enter The File Location");
		return false;
	}
	else{		
	var flag = checkfileExtension();
	if(flag){
			//document.dpSecFileUpload.methodName.value="SecFileUPload";
			document.dpSecFileUpload.submit();
		return true;
		
		}
		else{
			return false;
		}		
	}
}
function checkfileExtension()
{
	if(document.dpSecFileUpload.thefile.value.lastIndexOf(".csv")==-1){
		alert("Please Upload Only .csv Extention File");
		document.dpSecFileUpload.thefile.focus();
		return false;
	}
	if(document.dpSecFileUpload.thefile.value.indexOf(":\\")==-1){
		alert("File Does Not Exist");
		document.dpSecFileUpload.thefile.focus();
		return false;
	}	
	if(!isWhitespace1(document.dpSecFileUpload.thefile.value)){	
		alert("File Name Should Not Contain Spaces");
		document.dpSecFileUpload.thefile.focus();
		return false;
	}	
	return true;
}
</script>
</HEAD>



<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">
<html:form action="/SecDataULoad" name="dpSecFileUpload" type="com.ibm.dp.beans.DPSecFileUploadBean" enctype="multipart/form-data"  method="post">
<input type = "hidden" name ="methodName" value = "SecFileUPload" />
<TABLE cellpadding="4" cellspacing="3" align="center" width="100%" border="0">

<TR align="center">
	<TD class="border">
	<logic:messagesPresent message="true" >
		<html:messages property="secUPload" id="pomsg" bundle="dp" message="true" >
			<strong><font color="red"> <bean:write name="pomsg" /></font>
			</html:messages>
		</logic:messagesPresent>
			
		<TABLE cellpadding="6" cellspacing="0" align="center" width="50%" border="0">		
			<TR>	
				<TD ><B><bean:message bundle="dp" key="application.SecFileUpload.browse" /></B></TD>
				<TD ><html:file name="dpSecFileUpload" property="thefile"/></TD>
			</TR>
			<TR align="center">
				<TD colspan="2">
				
			<INPUT class="submit" type="submit"	id="Submit" name="Submit" value="Submit"
					onclick="return frmsubmit()">
				</TD>
			</TR>
			</TABLE>
		</TD>
	</TR>	
</TABLE>
</html:form>
</html:html>