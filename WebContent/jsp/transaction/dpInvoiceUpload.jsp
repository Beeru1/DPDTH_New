\
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<html>
<head>

<script>
	function disableLink()
		{
			var dls = document.getElementById('chromemenu').getElementsByTagName("li");
			// alert("hi");
			for (i=0;i<dls.length;i++) {
			dls[i].setAttribute("className", "defaultCursor");
				var dds = dls[i].getElementsByTagName("a");
				for(j=0;j<dds.length;j++){
					document.getElementById(dds[j].getAttribute("rel")).style.visibility="hidden";
					document.getElementById(dds[j].getAttribute("rel")).style.display="none";
				}
			}
		}	
	function checkFile(){
		if(document.getElementById("fileExcl").value=="" || document.getElementById("fileExcl").value==null){
		
		alert("Please select a file");
		return false;
		}
	return true;
}

function checkFileNew(){
		if(document.getElementById("fileExclNew").value=="" || document.getElementById("fileExclNew").value==null){
		
		alert("Please select a file");
		return false;
		}
	return true;
}
	
</script>
<link rel="stylesheet" href="../../theme/Master.css" type="text/css">
<link href="<%=request.getContextPath()%>/theme/statusMsg.css"
	rel="stylesheet" type="text/css">
<title>Upload Invoice</title>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


</head>
<%
	if (request.getAttribute("disableLinkPay") == null) {
%>
<body>
<%
	} else if (request.getAttribute("disableLinkPay").equals("true")) {
%>

<body onload="disableLink();">
<%
	}
%>


<h1 style="color: black;"><bean:message
	key="Invoice.UploadPage.header" /></h1>

<br>

<logic:messagesPresent message="true">
	<span class="statMsg"> <html:messages id="message"
		message="true">
		<bean:write name="message" />
	</html:messages> </span>
</logic:messagesPresent>

<logic:present name="ErrPath" scope="request">
	<logic:notEqual name="ErrPath" value="">
	</logic:notEqual>
	<html:link action="/UploadInv.do?methodName=downloadErr"
		paramId="errPath" paramName="ErrPath">Click Here </html:link>
 	to download error file
</logic:present>



<html:form name="DpInvoiceForm" action="/UploadInv.do"
	type="com.ibm.dp.beans.DpInvoiceForm" enctype="multipart/form-data"
	method="post">
	<html:hidden property="methodName" value="uploadInvoiceFile" />
	<html:hidden property="fileFlag"   value="1"/>
	<br>
	<span class="fileLabl"> <bean:message
		key="Invoice.label.common.label" /> : </span>

	<html:file property="file" size="50" styleId="fileExcl" />
	<br>
	<br>
	<html:submit onclick="return checkFile()">
		<bean:message key="Invoice.label.common.button.submit" />
	</html:submit>

</html:form>

<html:form name="DpInvoiceForm" action="/UploadInv.do"
	type="com.ibm.dp.beans.DpInvoiceForm" enctype="multipart/form-data"
	method="post">
	<html:hidden property="methodName" value="uploadInvoiceFileNew" />
	<html:hidden property="fileFlag"   value="2"/>
	<br>
	<span class="fileLabl"> <bean:message
		key="Invoice.label.common.label.new" /> : </span>

	<html:file property="file" size="50" styleId="fileExclNew" />
	<br>
	<br>
	<html:submit onclick="return checkFileNew()">
		<bean:message key="Invoice.label.common.button.submit" />
	</html:submit>

</html:form>





</body>
</html>