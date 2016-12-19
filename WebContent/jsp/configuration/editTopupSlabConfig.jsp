<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>
<HEAD>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<LINK href="<%=request.getContextPath()%>/theme/text.css" rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/theme/chromestyle2.css" />
<LINK href="<%=request.getContextPath()%>/theme/CalendarControl.css" rel="stylesheet" type="text/css">
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/scripts/Cal.js"
	type="text/javascript"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/chrome.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/JScriptLib.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>

<SCRIPT language="Javascript" type="text/javascript">
	function validateDetails(){
		var form = document.forms[0];
    	//Validating transactionType
  		if(form.transactionType.value=='-1'){
			alert('<bean:message bundle="error" key="errors.topup.transactionType" />');
			form.transactionType.focus();
			return false;
		}
		//Validating startAmount
		if(form.startAmount.value==''){
			alert('<bean:message bundle="error" key="errors.topup.startamount" />');
			form.startAmount.focus();
			return false;
		}
		else if(isAmount(document.forms[0].startAmount.value,'<bean:message bundle="view"
								key="topup.start_amount" />',true)==false){
			document.forms[0].startAmount.focus();
			return false;	
		} 
		else if(isNumber(form.startAmount.value,2)==false){
    	    alert('<bean:message bundle="error" key="errors.topup.invalidstartamount" />');
        	form.startAmount.value='';
			form.startAmount.focus();
			return false;
	    }
		//Validating tillAmount
		if(form.tillAmount.value==''){
			alert('<bean:message bundle="error" key="errors.topup.tillamount" />');
			form.tillAmount.focus();
			return false;
		}
		else if(isAmount(document.forms[0].tillAmount.value,'<bean:message bundle="view"
								key="topup.till_amount" />',true)==false){
			document.forms[0].tillAmount.focus();
			return false;	
		} 
		else if(isNumber(form.tillAmount.value,2)==false){
    	    alert('<bean:message bundle="error" key="errors.topup.invalidtillamount" />');
        	form.tillAmount.value='';
			form.tillAmount.focus();
			return false;
		}
		var strtAmt = Number(form.startAmount.value);
		var tillAmt = Number(form.tillAmount.value);
		if(strtAmt>tillAmt){
			alert('<bean:message bundle="error" key="errors.topup.incorrecttillamount" />');
			form.tillAmount.value='';
			form.tillAmount.focus();
			return false;
		}
		if(strtAmt == '0' && tillAmt == '0'){
			alert('<bean:message bundle="error" key="errors.topup.incorrectstarttillamount" />');
			form.tillAmount.focus();
			return false;
		}
		//Validating espCommission
		var espValue=parseFloat(document.forms[0].espCommission.value);
		
		if(form.espCommission.value==''){
			alert('<bean:message bundle="error" key="errors.topup.espcommission" />');
			form.espCommission.focus();
			return false;
		}
		else if(isAmount(document.forms[0].espCommission.value,'<bean:message bundle="view"
								key="topup.esp_commission" />',true)==false){
			document.forms[0].espCommission.focus();
			return false;	
		}
		else if(isNumber(form.espCommission.value,2)==false){
    	    alert("<bean:message bundle="error" key="errors.topup.invalidespcommission" />");
	        form.espCommission.value='';
			form.espCommission.focus();
			return false;
	    }
	    else if((espValue >100)||(espValue < 0)){
			alert('<bean:message bundle="error" key="errors.topup.rate_invalid" />');
			document.forms[0].espCommission.focus();
			return false;
		}
		//Validating pspCommission
		var pspValue=parseFloat(document.forms[0].pspCommission.value);
		
		if(form.pspCommission.value==''){
			alert('<bean:message bundle="error" key="errors.topup.pspcommission" />');
			form.pspCommission.focus();
			return false;
		}
		else if(isAmount(document.forms[0].pspCommission.value,'<bean:message bundle="view"
								key="topup.psp_commission" />',true)==false){
			document.forms[0].pspCommission.focus();
			return false;	
		}
		else if(isNumber(form.pspCommission.value,2)==false){
    	    alert('<bean:message bundle="error" key="errors.topup.invalidpspcommission" />');
        	form.pspCommission.value='';
			form.pspCommission.focus();
			return false;
	    }
	    else if((pspValue >100)||(pspValue < 0)){
			alert('<bean:message bundle="error" key="errors.topup.rate_invalid" />');
			document.forms[0].pspCommission.focus();
			return false;
		}
		//Validating serviceTax
			var serviceTax=parseFloat(document.forms[0].serviceTax.value);
		if((document.getElementById("transactionType").value!="POSTPAID_ABTS") && (document.getElementById("transactionType").value!="POSTPAID_MOBILE") && (document.getElementById("transactionType").value!="POSTPAID_DTH") ){	
			if(form.serviceTax.value==''){
				alert('<bean:message bundle="error" key="errors.topup.servicetax" />');
				form.serviceTax.focus();
				return false;
			}
			else if(isAmount(document.forms[0].serviceTax.value,'<bean:message bundle="view"
								key="topup.service_tax" />',true)==false){
			     document.forms[0].serviceTax.focus();
			     return false;	
		   }
			else if(isNumber(form.serviceTax.value,2)==false){
    		    alert('<bean:message bundle="error" key="errors.topup.invalidservicetax" />');
     	   	form.serviceTax.value='';
				form.serviceTax.focus();
				return false;
		    }  else if((serviceTax > 100)||(serviceTax < 0)){
			alert('<bean:message bundle="error" key="errors.topup.tax_invalid" />');
			document.forms[0].serviceTax.focus();
			return false;
		}
		}
		//Validating processingFee
		if(document.getElementById("transactionType").value!="POSTPAID_ABTS" &&	document.getElementById("transactionType").value!="POSTPAID_MOBILE" && document.getElementById("transactionType").value!="POSTPAID_DTH" ){	
			if(form.processingFee.value==''){
				alert('<bean:message bundle="error" key="errors.topup.processingfee" />');
				form.processingFee.focus();
				return false;
			}
			else if(isAmountPfee(document.forms[0].processingFee.value,'<bean:message bundle="view"
								key="topup.processing_fee" />',true)==false){
				document.forms[0].processingFee.focus();
				return false;	
		    }
			 else if(isNumberPfee(form.processingFee.value,2)==false){
			       alert('<bean:message bundle="error" key="errors.topup.invalidprocessingfee" />');
    		    form.processingFee.value='';
				form.processingFee.focus();
				return false;
   		 	}
		}
		//Validating processingCode
		if(document.getElementById("transactionType").value!="POSTPAID_ABTS" &&	document.getElementById("transactionType").value!="POSTPAID_MOBILE" && document.getElementById("transactionType").value!="POSTPAID_DTH" ){	
			if(form.processingCode.value==''){
				alert('<bean:message bundle="error" key="errors.topup.processingcode" />');
				form.processingCode.focus();
				return false;
			}else if(isAlphaNumeric(form.processingCode.value)==false){
       		 	alert('<bean:message bundle="error" key="errors.topup.invalidprocessingcode" />');
				form.processingCode.value='';
				form.processingCode.focus();
				return false;
    		}
		}
		//Validating IN Card Group
		if(document.getElementById("transactionType").value!="POSTPAID_ABTS" &&	document.getElementById("transactionType").value!="POSTPAID_MOBILE" && document.getElementById("transactionType").value!="POSTPAID_DTH" ){	
			if(form.inCardGroup.value==''){
				alert('<bean:message bundle="error" key="errors.topup.incardgroup" />');
				form.inCardGroup.focus();
				return false;
			}else if(isAlphaNumeric(form.inCardGroup.value)==false){
    		    alert('<bean:message bundle="error" key="errors.topup.invalidincardgroup" />');
				form.inCardGroup.value='';
	 	       form.inCardGroup.focus();
				return false;
		    }
		}
		//Validating validity
		if(document.getElementById("transactionType").value=="RECHARGE"){ 
			if(form.validity.value==''){
				alert('<bean:message bundle="error" key="errors.topup.validity" />');
				form.validity.focus();
				return false;
			}else if(isValidDigit(document.forms[0].validity.value,'<bean:message bundle="view"
								key="topup.validity" />',true)==false){
				form.validity.focus();
				return false;
			}
		}
		document.getElementById("methodName").value="editCircleTopup";
	}

	function isNumber(sText,i) {
		var ValidChars;
		if(i==1){
			ValidChars = "0123456789";
		}
		if (i==2){
			ValidChars = "0123456789.";
		}
		var IsNo=true;
		var Char;
		for (i = 0; ((i < sText.length) && (IsNo == true)); i++){ 
			Char = sText.charAt(i); 
			if (ValidChars.indexOf(Char) == -1){
				IsNo = false;
			}
		}
		return IsNo;
	} 
	// Validating Processing Fee
	function isNumberPfee(sText,i){
		var ValidChars;
		if(i==1){
			ValidChars = "0123456789";
		}
  		if (i==2){
			ValidChars = "0123456789.";
		}
		var IsNo=true;
		var Char;
		for (i = 1; ((i < sText.length) && (IsNo == true)); i++){ 
			Char = sText.charAt(i); 
			if (ValidChars.indexOf(Char) == -1){
				IsNo = false;
			}
		}
		return IsNo;
	} 

	function checkKeyPressed(){
		if(window.event.keyCode=="13"){
			return validateDetails();
		}
	}
	function goBack(){
		document.getElementById("methodName").value="showTopupConfig";
	}
	function disableValidity(){
	
		if(document.getElementById("transactionType").value=="POSTPAID_ABTS" || document.getElementById("transactionType").value=="0"
		||	document.getElementById("transactionType").value=="POSTPAID_MOBILE" ||  document.getElementById("transactionType").value=="POSTPAID_DTH" ){		
			document.getElementById("serviceTax").disabled=true;
			document.getElementById("processingFee").disabled=true;
			document.getElementById("processingCode").disabled=true;
			document.getElementById("inCardGroup").disabled=true;
			document.getElementById("validity").disabled=true;

			document.getElementById("serviceTax").value="";
			document.getElementById("processingFee").value="";
			document.getElementById("processingCode").value="";
			document.getElementById("inCardGroup").value="";
			document.getElementById("validity").value="";
			
			document.getElementById("divProcessingCodeReq").style.display="block";
			document.getElementById("divProcessingCodeNotReq").style.display="none";
			document.getElementById("divInCardGroupReq").style.display="block";
			document.getElementById("divInCardGroupNotReq").style.display="none";
			document.getElementById("divProcessingFeeReq").style.display="block";
			document.getElementById("divProcessingFeeNotReq").style.display="none";
			document.getElementById("divServiceTaxReq").style.display="block";
			document.getElementById("divServiceTaxNotReq").style.display="none";
			
			
		}else{
		
		    document.getElementById("serviceTax").disabled=false;
			document.getElementById("processingFee").disabled=false;
			document.getElementById("processingCode").disabled=false;
			document.getElementById("inCardGroup").disabled=false;
			document.getElementById("validity").disabled=false;
		
			document.getElementById("divProcessingCodeReq").style.display="none";
			document.getElementById("divProcessingCodeNotReq").style.display="block";
			document.getElementById("divInCardGroupReq").style.display="none";
			document.getElementById("divInCardGroupNotReq").style.display="block";
			document.getElementById("divProcessingFeeReq").style.display="none";
			document.getElementById("divProcessingFeeNotReq").style.display="block";
			document.getElementById("divServiceTaxReq").style.display="none";
			document.getElementById("divServiceTaxNotReq").style.display="block";
			
		}
		
		if(document.getElementById("transactionType").value=="RECHARGE" || document.getElementById("transactionType").value=="0"  ){
			document.getElementById("validity").disabled=false;
			document.getElementById("divValidityReq").style.display="none";
			document.getElementById("divValidityNotReq").style.display="block";
		}else{
			document.getElementById("validity").disabled=true;
			document.getElementById("validity").value="";
			document.getElementById("divValidityReq").style.display="block";
			document.getElementById("divValidityNotReq").style.display="none";
			
		}
	}
</SCRIPT>

</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onkeypress="return checkKeyPressed();"
	onload="disableValidity();">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">
	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include
			page="../common/dpheader.jsp" flush="" /></TD>
	</TR>
	<TR valign="top">
		<TD width="167" align="left" bgcolor="b4d2e7" valign="top"
			height="100%"><jsp:include page="../common/leftHeader.jsp" flush="" /></TD>
		<TD valign="top" width="100%" height="100%"><html:form
			name="topupSlabAction" action="/TopupSlabAction"
			type="com.ibm.virtualization.recharge.beans.TopupSlabBean"
			focus="inCardGroup">
			<html:hidden property="methodName" styleId="methodName" />
			<html:hidden property="topupConfigId" name="topupSlabBean" />
			<html:hidden property="circleId" name="topupSlabBean" />
			<html:hidden property="page" styleId="page" name="topupSlabBean" />

			<TABLE width="560" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/Edit_SlabConfig.gif" width="505" height="22"
						alt=""></TD>
				</TR>
				<TR>

					<TD colspan="4"><FONT color="#ff0000" size="-2"><html:errors
						bundle="error" /></FONT></TD>
				</TR>
				<TR>
					<TD width="126" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="topup.circle_name" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
					<TD width="155"><FONT color="#003399"> <html:select
						property="circleId" name="topupSlabBean" styleClass="w130"
						disabled="true">
						<html:option value="-1">
							<bean:message bundle="view" key="application.option.select" />
						</html:option>
						<html:optionsCollection name="topupSlabBean" property="circleList"
							label="circleName" value="circleId" />
					</html:select> </FONT></TD>

					<TD width="135" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="topup.transaction_type" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
					<TD width="163"><FONT color="#3C3C3C"><html:select
						property="transactionType" name="topupSlabBean" styleClass="w130"
						tabindex="1" onchange="disableValidity()" styleId="transactionType">
						<html:option value="-1">
							<bean:message bundle="view" key="application.option.select" />
						</html:option>
						<html:option value="RECHARGE">
							<bean:message bundle="view"
								key="sysconfig.transaction_type.recharge" />
						</html:option>
						<html:option value="VAS">
							<bean:message bundle="view" key="sysconfig.transaction_type.vas" />
						</html:option>
						<html:option value="POSTPAID_ABTS">
							<bean:message bundle="view"
								key="sysconfig.transaction_type.postpaid_abts" />
						</html:option>
						<html:option value="POSTPAID_MOBILE">
							<bean:message bundle="view"
								key="sysconfig.transaction_type.postpaid" />
						</html:option>
						
					    <html:option value="POSTPAID_DTH">
							<bean:message bundle="view"
								key="sysconfig.transaction_type.postpaid_dth" />
						</html:option> 
						<html:option value="ACCOUNT">
							<bean:message bundle="view"
								key="sysconfig.transaction_type.account" />
						</html:option>
					</html:select> </FONT></TD>
				</TR>
				<TR>
					<TD width="126" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="topup.start_amount" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
					<TD width="171"><FONT color="#3C3C3C"> <html:text
						property="startAmount" styleClass="box" maxlength="14"
						name="topupSlabBean" tabindex="2" onkeypress="isValidRate()"/> </FONT></TD>
					<TD width="135" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="topup.till_amount" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
					<TD width="163"><FONT color="#3C3C3C"> <html:text
						property="tillAmount" styleClass="box" maxlength="14"
						name="topupSlabBean" tabindex="3" onkeypress="isValidRate()"/> </FONT></TD>
				</TR>
				<TR>
					<TD width="126" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="topup.esp_commission" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
					<TD width="171"><FONT color="#3C3C3C"> <html:text
						property="espCommission" styleClass="box" maxlength="5"
						name="topupSlabBean" tabindex="4" onkeypress="isValidRate()"/> </FONT></TD>
					<TD width="135" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="topup.psp_commission" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
					<TD width="163"><FONT color="#3C3C3C"> <html:text
						property="pspCommission" styleClass="box" maxlength="5"
						name="topupSlabBean" tabindex="5" onkeypress="isValidRate()"/> </FONT></TD>
				</TR>
				<TR>
					<TD width="126" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000">
						
						<div id="divServiceTaxReq" style="display:block;">
						<bean:message	bundle="view" key="topup.service_tax" />:</div>	</FONT>
						<div id="divServiceTaxNotReq" style="display:none;">
						<bean:message bundle="view" key="topup.service_tax" /><FONT color="RED">*</FONT>:</div>
									
									</STRONG></TD>
					<TD width="171"><FONT color="#3C3C3C"> <html:text
						property="serviceTax" styleClass="box" maxlength="5"
						name="topupSlabBean" tabindex="6" styleId="serviceTax" onkeypress="isValidRate()"/> </FONT></TD>
					<TD width="135" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000">
						
						<div id="divProcessingFeeReq" style="display:block;">
						<bean:message	bundle="view" key="topup.processing_fee" />:</div>	</FONT>
						
						<div id="divProcessingFeeNotReq" style="display:none;">
						<bean:message	bundle="view" key="topup.processing_fee" /><FONT color="RED">*</FONT>:</div>
						</STRONG></TD>
					<TD width="163"><FONT color="#3C3C3C"> <html:text
						property="processingFee" styleClass="box" maxlength="14"
						name="topupSlabBean" tabindex="7" styleId="processingFee" onkeypress="isValidPfee()"/> </FONT></TD>
				</TR>
				<TR>
					<TD width="126" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000">
						
						<div id="divProcessingCodeReq" style="display:block;"><bean:message
									bundle="view" key="topup.processing_code" />:</div>	</FONT>
						<div id="divProcessingCodeNotReq" style="display:none;"><bean:message
									bundle="view" key="topup.processing_code" /><FONT color="RED">*</FONT>:</div>
						</STRONG></TD>
					<TD width="171"><FONT color="#3C3C3C"> <html:text
						property="processingCode" styleClass="box" maxlength="16"
						name="topupSlabBean" tabindex="8" styleId="processingCode"/> </FONT></TD>
					<TD width="135" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000">
						<div id="divInCardGroupReq" style="display:block;"><bean:message
									bundle="view" key="topup.incard_group" />:</div></FONT>
						<div id="divInCardGroupNotReq" style="display:none;"><bean:message
									bundle="view" key="topup.incard_group" /><FONT color="RED">*</FONT>:</div>
									
					</STRONG></TD>
					<TD width="163"><FONT color="#3C3C3C"> <html:text
						property="inCardGroup" styleClass="box" maxlength="32"
						name="topupSlabBean" tabindex="9" styleId="inCardGroup"/> </FONT></TD>
				</TR>
				<TR>
					<TD width="126" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><div id="divValidityReq" style="display:block;"><bean:message
									bundle="view" key="topup.validity" />:</div>
								</FONT>
						<div id="divValidityNotReq" style="display:none;"><bean:message
									bundle="view" key="topup.validity" /><FONT color="RED">*</FONT>:</div>
					</STRONG></TD>
					<TD width="171"><FONT color="#3C3C3C"> <html:text
						property="validity" styleClass="box" styleId="validity"
						maxlength="6" tabindex="10" name="topupSlabBean" onkeypress="isValidNumber()"/> </FONT></TD>
					<TD width="135" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="topup.status" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
					<TD width="163"><FONT color="#3C3C3C"> <html:select
						property="status" name="topupSlabBean" styleClass="w130"
						tabindex="11">
						<html:option value="A">
							<bean:message key="application.option.active" bundle="view" />
						</html:option>
						<html:option value="D">
							<bean:message key="application.option.inactive" bundle="view" />
						</html:option>
					</html:select> </FONT></TD>
				</TR>
				<TR>
					<TD class="text pLeft15" nowrap><FONT color="#000000">&nbsp;</FONT></TD>
					<TD colspan="2">
					<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
						<TR align="center" valign="top">
							<TD><INPUT class="submit" type="submit" tabindex="12"
								name="Submit" value="Submit"
								onclick="return validateDetails(this, event);"></TD>
							<TD><INPUT class="submit" type="submit" tabindex="13"
								name="BACK" value="Back" onclick="goBack()"></TD>

							<TD></TD>
						</TR>
					</TABLE>
					</TD>
				</TR>
				<TR>
					<TD colspan="4" align="center">&nbsp;</TD>
					<html:hidden property="listStatus" styleId="listStatus" name="topupSlabBean" />
					<html:hidden property="startDate" name="topupSlabBean" />
					<html:hidden property="endDate" name="topupSlabBean" />
					<html:hidden property="flagForCircleDisplay"
						styleId="flagForCircleDisplay" name="topupSlabBean" />
				</TR>

			</TABLE>

		</html:form></TD>
	</TR>
	<TR align="center" bgcolor="4477bb" valign="top">
		<TD colspan="2" height="17" align="center"><jsp:include
			page="../common/footer.jsp" flush="" /></TD>
	</TR>
</TABLE>
<SCRIPT type="text/javascript">
{
	if(document.getElementById("flagForCircleDisplay").value=="true")
	{
		document.getElementById("circleId").disabled = true;
	}
}
</SCRIPT>
</BODY>
</html:html>
