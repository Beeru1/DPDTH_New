<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>
<HEAD>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<LINK href="<%=request.getContextPath()%>/theme/text.css"
	rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/theme/chromestyle2.css" />
<LINK href="<%=request.getContextPath()%>/theme/CalendarControl.css"
	rel="stylesheet" type="text/css">
<SCRIPT language="javascript" type="text/javascript">


function validate(){
 //Validating circle name
	if(document.forms[0].circleId.value=='-1'){
			alert('<bean:message bundle="error" key="errors.topup.circleid" />');
			document.forms[0].circleId.focus();
			return false;
	}
	//Validating transactionType
	if(document.forms[0].transactionType.value=='-1'){
			alert('<bean:message bundle="error" key="errors.topup.transactionType" />');
			document.forms[0].transactionType.focus();
			return false;
	}	
	//Validating upload file	
	if(document.forms[0].uploadFile.value==""){
		alert('<bean:message bundle="error" key="errors.topup.uploadFile" />');
		document.forms[0].uploadFile.focus();
		return false;
	}	
	//Validating the file extension
	if(document.forms[0].uploadFile.value.lastIndexOf(".csv") != (document.forms[0].uploadFile.value.length -4)){
		alert("Please upload only .csv extention file");
		document.forms[0].uploadFile.focus();
		return false;
	}
	
	var upload=confirm('<bean:message bundle="view" key="topup.confirmbulkupload" />');
	   if(upload){ 
	   		
	   		document.getElementById("methodName").value="uploadSlabData";
       		document.forms[0].submit();
      } else{
      		document.forms[0].circleId.value="";
      		document.forms[0].transactionType.value="";
      		
 	  }
 }
 
 function viewDetails(){
 	document.getElementById("methodName").value="getTopupConfigFailData";
	document.forms[0].submit();
 }
 
 function checkKeyPressed(){
		if(window.event.keyCode=="13"){
			return validate();
		}
	}

 
</SCRIPT>
</HEAD>
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
			action="/TopupSlabAction" focus="circleId"
			enctype="multipart/form-data">
			<html:hidden property="methodName" styleId="methodName" />
			<Table width="545" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="4" class="text" align="center"><BR>
					<IMG src="<%=request.getContextPath()%>/images/uploadBulkSlabConfig.gif"
								width="505" height="22" alt="">
					</TD>
				</TR>
				<TR>

					<TD colspan="4"><FONT color="#ff0000" size="-2"><html:errors
						bundle="view" /><br>
					<html:errors bundle="error" /></FONT></TD>
				</TR>
				<TR>

					<TD width="126" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="topup.circle_name" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
					<html:hidden styleId="hdnCircleId" name="topupSlabBean"
						property="hdnCircleId" />
					<TD width="155"><FONT color="#003399"> <html:select
						property="circleId" styleClass="w130" tabindex="1">
						<html:option value="-1">
							<bean:message bundle="view" key="application.option.select" />
						</html:option>
						<html:optionsCollection name="topupSlabBean" property="circleList"
							label="circleName" value="circleId" />
					</html:select></FONT></TD>
				</TR>
				<TR>
					<TD width="135" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="topup.transaction_type" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
					<TD width="163"><FONT color="#3C3C3C"> <html:select
						property="transactionType" name="topupSlabBean"
						styleId="transactionType" styleClass="w130" tabindex="2">
						<html:option value="-1">
							<bean:message bundle="view" key="application.option.select" />
						</html:option>
						<html:option value="RECHARGE">
							<bean:message bundle="view"
								key="sysconfig.transaction_type.recharge" />
						</html:option>
					</html:select> </FONT></TD>
				</TR>
				<TR>
					<TD class="text pLeft15" width="132"><FONT color="#000000"><STRONG>
					<bean:message bundle="view" key="topup.file_name" /> </STRONG></FONT><strong><FONT
						COLOR="RED">* </FONT>: </strong></TD>
					<TD colspan="2" class="text"><input type="file"
						name="uploadFile" tabindex="3" contentEditable="false"></TD>
				</TR>
				<TR>
					<TD class="text pLeft15" nowrap><FONT color="#000000">&nbsp;</FONT></TD>
					<TD><INPUT type="submit" class="submit" name="Submit"
						tabindex="4" value="Submit" onclick="return validate();">&nbsp;
					<input class="submit" type="reset" tabindex="5" value="Reset"></TD>
				</TR>
				<TR align="left">
					<TD colspan="4"><a href="<%=request.getContextPath()%>/jsp/files/sampleFormat.xls" target="window.open"><bean:message
						bundle="view" key="topup.view.sample_file" /></a>
				<logic:equal name="topupSlabBean" property="viewFailedRecords" value="true">		
					<TD class="text" nowrap><A href="#" onclick="viewDetails()"><bean:message
						bundle="view" key="topup.view.failed_records" /></A></TD>
				</logic:equal>	

				</TR>

			</Table>
		</html:form></TD>
	</TR>



	<TR align="center" bgcolor="4477bb" valign="top">
		<TD colspan="2" height="17" align="center"><jsp:include
			page="../common/footer.jsp" flush="" /></TD>
	</TR>

</TABLE>
</BODY>
</html:html>
