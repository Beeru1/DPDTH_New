<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html:html>
<HEAD>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<LINK href="<%=request.getContextPath()%>/theme/text.css" rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/theme/chromestyle2.css" />
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/chrome.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/JScriptLib.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<script language="javascript" src="scripts/calendar1.js"></script>
<script language="javascript" src="scripts/Utilities.js"></script>
<script language="javascript" src="scripts/cal2.js"></script>
<script language="javascript" src="scripts/cal_conf2.js"></script>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/Cal.js"></SCRIPT>
<SCRIPT language="Javascript" type="text/javascript">

	// trim the spaces
	function trimAll(sString) {
		while (sString.substring(0,1) == ' '){
		sString = sString.substring(1, sString.length);
		}		
		while (sString.substring(sString.length-1, sString.length) == ' '){
		sString = sString.substring(0,sString.length-1);	
		}
		return sString;
	}

	function validateForm(){

		var circleName=document.forms[0].circleName;
		var circleRegion=document.forms[0].regionId;
		var circleDescription=document.forms[0].description;
		var circleCode=document.forms[0].circleCode;
		var circlePhone=document.forms[0].phone;
		var circlePhoneValue=trimAll(document.forms[0].phone.value);
		var terms1=document.forms[0].terms1;
		var terms2=document.forms[0].terms2;
		var terms3=document.forms[0].terms3;
		var terms4=document.forms[0].terms4;
		var remarks=document.forms[0].remarks;
		var compname=document.forms[0].compName;
		
		var saletax=document.forms[0].saletax;
		var saletaxdate=document.forms[0].saletaxdate;
		var tin=document.forms[0].tin;
		var tinDate=document.forms[0].tinDate;
		var cst=document.forms[0].cst;
		var cstdate=document.forms[0].cstdate;
		
		if ((circleCode.value==null)||(circleCode.value=="")){
			alert('<bean:message bundle="error" key="errors.circle.circlecode_required" />');
			circleCode.focus();
			return false;
		}
		
		
		
//		alert("2");
		if (isNull('document.forms[0]','circleCode')){
			alert('<bean:message bundle="error" key="errors.circle.circlecode_required" />');
			circleCode.value="";
			circleCode.focus();
			return false;
		}
	//	alert("3");
		if ((circleName.value==null)||(circleName.value=="")){
			alert('<bean:message bundle="error" key="errors.circle.circlename_required" />');
			circleName.focus();
			return false;
		}
	//	alert("4");
		if (isNull('document.forms[0]','circleName')){
			alert('<bean:message bundle="error" key="errors.circle.circlename_required" />');
			circleName.value="";
			circleName.focus();
			return false;
		}
		//alert("5");
		if(circleRegion.value == "0"){
			alert('<bean:message bundle="error" key="errors.circle.region_invalid" />');
			circleRegion.focus();
			return false;
		}
		//		alert("9");
		if ((compname.value==null)||(compname.value=="")){
			alert('<bean:message bundle="error" key="errors.circle.compname_required" />');
			compname.focus();
			return false;
		}
//		alert("9");
		if (isNull('document.forms[0]','compName')){
			alert('<bean:message bundle="error" key="errors.circle.compname_required" />');
			compname.value="";
			compname.focus();
			return false;
		}
		
		if( circlePhoneValue.length ==0){
			alert('<bean:message bundle="error" key="errors.circle.phone_required" />');
			circlePhone.focus();
			return false;
		}
//		alert("6");
		if(!isNumeric(circlePhoneValue)){
			alert('<bean:message bundle="error" key="errors.circle.phone_numeric_required" />');
			circlePhone.value="";
			circlePhone.focus();
			return false;
		}
		
		if (!(isNull('document.forms[0]','saletax')))
		{
			if( (saletax.value).length>0){
				if ((saletaxdate.value==null)||(saletaxdate.value==""))
				{	
					alert('Sale Tax Date is Required');
					saletaxdate.value="";
					saletaxdate.focus();
					return false;
				}
			
			}
		}
		if (!(isNull('document.forms[0]','tin')))
		{
			if((tin.value).length>0){
				if ((tinDate.value==null)||(tinDate.value==""))
				{	
					alert('Tin Date is Required');
					tinDate.focus();
					return false;
				}
			
			}
		}
		if (!(isNull('document.forms[0]','cst')))
		{
			if( (cst.value).length>0){
				if ((cstdate.value==null)||(cstdate.value==""))
				{	
					alert('Cst Date is Required');
					cstdate.focus();
					return false;
				}
			
			}
		}
	//	alert("7");
		//alert("8");
		
	//	alert("10");
		if(circleDescription.value.length > 200){
			alert('<bean:message bundle="error" key="errors.circle.description_maxlimit" />');
			circleDescription.focus();
			return false;
		}
		//alert("11");
		if(terms1.value.length > 150){
			alert('<bean:message bundle="error" key="errors.circle.terms1_maxlimit" />');
			terms1.focus();
			return false;
		}
		//alert("12");
		if(terms2.value.length > 150){
			alert('<bean:message bundle="error" key="errors.circle.terms2_maxlimit" />');
			terms2.focus();
			return false;
		}
//alert("13");
		if(terms3.value.length > 150){
			alert('<bean:message bundle="error" key="errors.circle.terms3_maxlimit" />');
			terms3.focus();
			return false;
		}
	//	alert("14");
if(terms4.value.length > 150){
			alert('<bean:message bundle="error" key="errors.circle.terms4_maxlimit" />');
			terms4.focus();
			return false;
		}
//alert("15");
if(remarks.value.length > 250){
			alert('<bean:message bundle="error" key="errors.circle.remarks_maxlimit" />');
			remarks.focus();
			return false;
		}
		//else{
	//	alert("16");
			document.forms[0].description.value=trimAll(document.forms[0].description.value);
		//	alert("17");
			document.getElementById("methodName").value="editCircle";
		document.forms[0].submit();
		//}	}
		
	}	
	function updateOperatingBalance(){
		// Form Bean Data
		var operatingBal = <bean:write name="CircleFormBean" property="operatingBalance" />
		var availableBal = <bean:write name="CircleFormBean" property="availableBalance" />
		// for fetching data from the text fields
		var circleAvlBal=document.forms[0].availableBalance;
		var circleOprBal=document.forms[0].operatingBalance;
		
		
		// Check the value entered in Available balance is numeric
		if (!(isNumeric(circleAvlBal.value))){
			// alert('<bean:message bundle="error" key="errors.circle.availablebalance_non_numeric" />');
			// re-set with the value of available balance in form bean
			circleAvlBal.value = availableBal;
			circleAvlBal.focus();
			return false;
		}
		var avaBal = circleAvlBal.value;
		if( avaBal >= availableBal){
			var updatedAmount = circleAvlBal.value - availableBal;
			circleOprBal.value = operatingBal + updatedAmount;
		}else{
			var updatedAmount = availableBal - circleAvlBal.value;
			circleOprBal.value = operatingBal - updatedAmount;
		}
	}
	//Validity of Amount
	function isValidAmount(evt, source){
		evt =(evt)?evt :window.event;
		var charCode =(evt.which)?evt.which :evt.keyCode;
		var amt = source.value;
		var len = parseInt(amt.length);
		var ind = parseInt(amt.indexOf('.'));
		if(evt.keyCode == 13){
			return (evt.keyCode) ;
		}
		if(isNaN(amt)){
			evt.keyCode = 0;
			source.value = ".00";
		}
		if(charCode == 46){
			if(ind>-1)
				evt.keyCode = 0;
		}else if(charCode < 48 || charCode >57)
			evt.keyCode = 0;
		else{
			if((len - ind)>4 && ind > -1)
				evt.keyCode = 0;
		}
	}

	function checkKeyPressed(){
		if(window.event.keyCode=="13"){
			return validateForm();
		}
	}
	function goBack(){
		 document.getElementById("methodName").value="getCircleList";
		}
	function checkIsCircleAdmin(){
		var  userType = document.getElementById("isCircleAdmin").value;
		if(userType == "Y"){
			document.getElementById("circleCode").readOnly = "readonly";
			document.getElementById("circleName").readOnly = "readonly";
			document.getElementById("regionId").disabled = true;
			document.forms[0].rate.focus();
		}
	}

</SCRIPT>

</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onkeypress="return checkKeyPressed();"
	onload="">

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
			name="CircleAction" action="/CircleAction"
			type="com.ibm.virtualization.recharge.beans.CircleFormBean"
			focus="circleName">
			<html:hidden property="methodName" styleId="methodName" />
			<html:hidden property="circleStatus" styleId="circleStatus" name="CircleFormBean"/>
			<html:hidden property="circleId" styleId="circleId"
				name="CircleFormBean" />
			<html:hidden property="isCircleAdmin" styleId="isCircleAdmin"
				name="CircleFormBean" />
			<html:hidden property="disabledRegion" styleId="disabledRegion"
				name="CircleFormBean" />
			<html:hidden property="page" styleId="page" name="CircleFormBean" />
			<TABLE width="545" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/editCircle.gif" width="505" height="22" alt=""></TD>
				</TR>
				<TR>
					<TD colspan="4" class="text"><FONT color="#ff0000" size="-2"><html:errors
						bundle="error" /><html:errors /></FONT></TD>
				</TR>
				<TR>
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.circlecode" /><FONT color="RED">*</FONT>:</FONT></STRONG></TD>
					<TD width="155"><FONT color="#003399"> <html:text
						property="circleCode" styleClass="box" styleId="circleCode"
						size="19" tabindex="1" maxlength="10" name="CircleFormBean" readonly="true" /> </FONT></TD>
					
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.saletax" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155"><FONT color="#003399"> <html:text
						property="saletax" styleClass="box" styleId="saletax"
						size="19" tabindex="1" maxlength="10" name="CircleFormBean" /> </FONT></TD>	

				</TR>
				
				<TR>
					<TD width="120" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.circlename" /><FONT color="RED">*</FONT>:</FONT></STRONG></TD>
					<TD width="163"><FONT color="#003399"> <html:text
						property="circleName" styleClass="box" styleId="circleName"
						size="19" tabindex="2" maxlength="64" name="CircleFormBean" /> </FONT><FONT
						color="#ff0000" size="-2"></FONT></TD>
					
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.saletaxdate" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155"><FONT color="#003399"> <html:text
						property="saletaxdate" styleClass="box" styleId="saletaxdate" readonly="true" 
						size="19" tabindex="1" maxlength="10" name="CircleFormBean" /><script language="JavaScript">
new tcal ({
// form name
'formname': 'CircleAction',
// input name
'controlname': 'saletaxdate'
});

</script> </FONT></TD>	
				
				</TR>
				<TR> 
					<TD class="text"><STRONG><FONT color="#000000"><bean:message
						bundle="view" key="circle.regionid" /></FONT><FONT color="RED">*</FONT>
					:</STRONG></TD>
					<TD width="163"><FONT color="#003399"> <html:select
						property="regionId" name="CircleFormBean" styleClass="w130"
						tabindex="3">
						<html:option value="0">
							<bean:message key="application.option.select" bundle="view" />
						</html:option>
						<html:options collection="regionList" labelProperty="name"
							property="id" />
					</html:select> </FONT></TD>
					
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.service" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155"><FONT color="#003399"> <html:text
						property="service" styleClass="box" styleId="service"
						size="19" tabindex="1" maxlength="10" name="CircleFormBean" /> </FONT></TD>	
				</TR>
				<TR>
				<TD><STRONG><FONT color="#000000"><bean:message
						bundle="view" key="circle.status" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
					<TD width="171"><FONT color="#3C3C3C"> <html:select
						property="status" name="CircleFormBean" styleClass="w130"
						tabindex="7">
						<html:option value="A">
							<bean:message key="application.option.active" bundle="view" />
						</html:option>
						<html:option value="D">
							<bean:message key="application.option.inactive" bundle="view" />
						</html:option>
					</html:select> </FONT></TD>
				<TD></TD>	
				<TD></TD>
					
				</TR>
				

	
				<TR>

					
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.compName" /><FONT color="RED">*</FONT>:</FONT></STRONG></TD>
					<TD width="155"><FONT color="#003399"> <html:text
						property="compName" styleClass="box" styleId="compName"
						size="19" tabindex="1" maxlength="10" name="CircleFormBean" /> </FONT></TD>
					
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.tin" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155"><FONT color="#003399"> <html:text
						property="tin" styleClass="box" styleId="tin"
						size="19" tabindex="1" maxlength="10" name="CircleFormBean" /> </FONT></TD>	

				</TR>
				<TR>
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.address1" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155"><FONT color="#003399"> <html:text
						property="address1" styleClass="box" styleId="address1"
						size="19" tabindex="1" maxlength="10" name="CircleFormBean" /> </FONT></TD>
					
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.tinDate" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155"><FONT color="#003399"> <html:text
						property="tinDate" styleClass="box" styleId="tinDate" readonly="true" 
						size="19" tabindex="1" maxlength="10" name="CircleFormBean" /><script language="JavaScript">
new tcal ({
// form name
'formname': 'CircleAction',
// input name
'controlname': 'tinDate'
});

</script>  </FONT></TD>	
				</TR>
				<TR>
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.address2" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155"><FONT color="#003399"> <html:text
						property="address2" styleClass="box" styleId="address2"
						size="19" tabindex="1" maxlength="10" name="CircleFormBean" /> </FONT></TD>
					
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.terms1" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155"><FONT color="#003399"> <html:text
						property="terms1" styleClass="box" styleId="terms1"
						size="19" tabindex="1" maxlength="10" name="CircleFormBean" /> </FONT></TD>	
				</TR>
				<TR>
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.phone" /><FONT color="RED">*</FONT>:</FONT></STRONG></TD>
					<TD width="155"><FONT color="#003399"> <html:text
						property="phone" styleClass="box" styleId="phone"
						size="19" tabindex="1" maxlength="10" name="CircleFormBean" /> </FONT></TD>
					
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.terms2" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155"><FONT color="#003399"> <html:text
						property="terms2" styleClass="box" styleId="terms2"
						size="19" tabindex="1" maxlength="10" name="CircleFormBean" /> </FONT></TD>	
				</TR>		
				<TR>
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.fax" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155"><FONT color="#003399"> <html:text
						property="fax" styleClass="box" styleId="fax"
						size="19" tabindex="1" maxlength="10" name="CircleFormBean" /> </FONT></TD>
					
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.terms3" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155"><FONT color="#003399"> <html:text
						property="terms3" styleClass="box" styleId="terms3"
						size="19" tabindex="1" maxlength="10" name="CircleFormBean" /> </FONT></TD>	
				</TR>

				<TR>
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.cst" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155"><FONT color="#003399"> <html:text
						property="cst" styleClass="box" styleId="cst"
						size="19" tabindex="1" maxlength="10" name="CircleFormBean" /> </FONT></TD>
					
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.terms4" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155"><FONT color="#003399"> <html:text
						property="terms4" styleClass="box" styleId="terms4"
						size="19" tabindex="1" maxlength="10" name="CircleFormBean" /> </FONT></TD>
				</TR>
				<TR>
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.cstdate" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155"><FONT color="#003399"> <html:text
						property="cstdate" styleClass="box" styleId="cstdate" readonly="true"
						size="19" tabindex="1" maxlength="10" name="CircleFormBean" /><script language="JavaScript">
new tcal ({
// form name
'formname': 'CircleAction',
// input name
'controlname': 'cstdate'
});

</script> </FONT></TD>
					
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.remarks" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155"><FONT color="#003399"> <html:text
						property="remarks" styleClass="box" styleId="remarks"
						size="19" tabindex="1" maxlength="10" name="CircleFormBean" /> </FONT></TD>
				</TR>
				
				<TR>
					<TD width="126" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.description" />:</FONT></STRONG></TD>
					<TD width="171" colspan="3"><FONT color="#3C3C3C"> <html:textarea
						property="description" cols="62" rows="3" tabindex="7"
						styleClass="box" name="CircleFormBean" /> </FONT></TD>
					<TD width="121" class="text" nowrap></TD>
					<TD width="171"><FONT color="#3C3C3C"> </FONT></TD>
				</TR>
				
				
				
				
				
				<TR>
					<TD class="text pLeft15"><FONT color="#000000">&nbsp;</FONT></TD>
					<TD colspan="2">
					<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
						<TR align="center" valign="top">
							<TD>
<html:button property="submit1" value="submit" onclick="return validateForm()">Submit</html:button>

</TD>
							<TD><INPUT class="submit" type="submit" tabindex="10"
								name="BACK" value="Back" onclick="goBack()"></TD>
							<TD></TD>
						</TR>
					</TABLE>
					</TD>
				</TR>
				<TR>
					<TD colspan="4" align="center">&nbsp; <html:hidden
						property="startDate" name="CircleFormBean" /> <html:hidden
						property="endDate" name="CircleFormBean" />
					</TD>
				</TR>
			</TABLE>

		</html:form></TD>
	</TR>
	<TR align="center" bgcolor="4477bb" valign="top">
		<TD colspan="2" height="17" align="center"><jsp:include
			page="../common/footer.jsp" flush="" /></TD>
	</TR>
</TABLE>

</BODY>
</html:html>
