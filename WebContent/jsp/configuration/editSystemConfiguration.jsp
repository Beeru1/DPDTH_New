<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>
<HEAD>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<LINK href="<%=request.getContextPath()%>/theme/text.css" rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/theme/chromestyle2.css" />
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/chrome.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/JScriptLib.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>

<SCRIPT language="javascript" type="text/javascript">
 // This function will check the validation for all the fields like fields are not left blank ,onlynumeric values.
	function validate(){
		// validate circleId
		var circleIdField=document.forms[0].circleId;
		if ((circleIdField.value== "0")){
			alert('<bean:message bundle="error" key="errors.sys.circleid_required" />');
			circleIdField.focus();
			return false;
		}
		// validate channelName
		var channelNameField=document.forms[0].channelName;
		if ((channelNameField.value== "0")){
			alert('<bean:message bundle="error" key="errors.sys.channelname_required" />');
			channelNameField.focus();
			return false;
		} 
		// validate transactionType
		var transTypeField=document.forms[0].transactionType;
		if ((transTypeField.value== "0")){
			alert('<bean:message bundle="error" key="errors.sys.transtype_required" />');
			transTypeField.focus();
			return false;
		}
		// validate minAmount
		var minAmountobj=document.forms[0].minAmount;
		if ((minAmountobj.value==null)||(minAmountobj.value=="")){
			alert('<bean:message bundle="error" key="errors.sys.minamount_required" />');
			minAmountobj.focus();
			return false;
		}
		
		if (parseFloat(document.getElementById("minAmount").value)==0){
			alert('<bean:message bundle="error" key="errors.sys.minamount_less" />');
			minAmountobj.focus();
			return false;		
		}
		
		if(isAmount(document.forms[0].minAmount.value,'<bean:message bundle="view"
								key="sysconfig.min_amount" />',true)==false){
			document.forms[0].minAmount.focus();
			return false;	
		}
		
		if (!(isNumeric(minAmountobj.value))){
			alert('<bean:message bundle="error" key="errors.sys.minamount_invalid" />');
			minAmountobj.focus();
			return false;
		}
		
		// validate maxAmount
		var maxAmountobj=document.forms[0].maxAmount;
		if ((maxAmountobj.value==null)||(maxAmountobj.value=="")){
			alert('<bean:message bundle="error" key="errors.sys.maxamount_required" />');
			maxAmountobj.focus();
			return false;
		}
		if(isAmount(document.forms[0].maxAmount.value,'<bean:message bundle="view"
								key="sysconfig.max_amount" />',true)==false){
			document.forms[0].maxAmount.focus();
			return false;	
		}
		if (!(isNumeric(maxAmountobj.value))){
			alert('<bean:message bundle="error" key="errors.sys.maxamount_invalid" />');
			maxAmountobj.focus();
			return false;
		}
		// Set the methodName for Dispatch
		document.getElementById("methodName").value="updateSystemConfiguration";	 
		return true;
	}
	function checkKeyPressed(){
		if(window.event.keyCode=="13"){
			return validate();
		}
	}

	function goBack(){
		document.getElementById("methodName").value="searchSysConfig";
	}

</SCRIPT>
</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" >

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
			action="/SysConfigAction" 
			focus="circleId">
			<html:hidden property="sysConfigId" styleId="sysConfigId"
				name="SysConfigFormBean" />
			<html:hidden property="methodName" styleId="methodName"
				name="SysConfigFormBean" />
			<html:hidden property="selectedCircleId" styleId="selectedCircleId"
				name="SysConfigFormBean" />
				


			<TABLE width="545" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/EditSystemConfig.gif" width="505" height="22"
						alt=""></TD>
				</TR>
				<TR>
					<TD colspan="4"><FONT color="#ff0000" size="2"><B><html:errors
						bundle="error" /> </B></FONT></TD>
				</TR>
				<TR>
					<TD width="152" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="sysconfig.circle_name" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
					<TD width="146"><FONT color="#003399"> <html:select
						property="circleId" styleId="circleId" styleClass="w130"
						tabindex="1">
						<html:option value="0">
							<bean:message bundle="view" key="application.option.select" />
						</html:option>
						<html:options property="circleId" labelProperty="circleName"
							collection="circleList" />
					</html:select> </FONT></TD>
					<TD align="center" colspan="4"><FONT size="2" color="Red">
					<B><html:errors bundle="error"
						property="errors.sys.circleid_required" /></B></FONT></TD>
				</TR>
				<TR>
					<TD width="152" class="text pLeft15" nowrap><STRONG><bean:message
						bundle="view" key="sysconfig.channel_name" /><FONT color="RED">*</FONT>
					:</STRONG></TD>
					<TD><FONT color="#003399"> <html:select
						property="channelName" name="SysConfigFormBean" styleClass="w130"
						tabindex="2">
						<html:option value="0">
							<bean:message bundle="view" key="application.option.select" />
						</html:option>
						<html:option value="SMSC">
							<bean:message bundle="view" key="sysconfig.channel_smsc" />
						</html:option>
						<html:option value="WEB">
							<bean:message bundle="view" key="sysconfig.channel_web" />
						</html:option>
						<html:option value="SELFCARE">
							<bean:message bundle="view" key="sysconfig.channel_selfcare" />
						</html:option>
						<html:option value="USSD">
							<bean:message bundle="view" key="sysconfig.channel_ussd" />
						</html:option>
					</html:select> </FONT></TD>
					<TD align="center" colspan="4"><FONT size="2" color="Red"><B>
					<html:errors bundle="error"
						property="errors.sys.channelname_required" /></B></FONT></TD>
				</TR>
				<TR>
					<TD width="152" class="text pLeft15" nowrap><STRONG><bean:message
						bundle="view" key="sysconfig.transaction_type" /><FONT
						color="RED">*</FONT> :</STRONG></TD>
					<TD width="146"><FONT color="#003399"> <html:select
						property="transactionType" name="SysConfigFormBean"
						styleClass="w130" tabindex="3">
						<html:option value="0">
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
					<TD align="center" colspan="4"><FONT size="2" color="Red"><B>
					<html:errors bundle="error"
						property="errors.sys.transtype_required" /></B></FONT></TD>
				</TR>
				<TR>
					<TD width="152" class="text pLeft15" nowrap><STRONG><bean:message
						bundle="view" key="sysconfig.min_amount" /><FONT color="RED">*</FONT>
					:</STRONG></TD>
					<TD><FONT color="#3C3C3C"> <html:text
						property="minAmount" styleId="minAmount" styleClass="box"
						tabindex="4" size="19" maxlength="10" name="SysConfigFormBean" onkeypress="isValidRate()"/>
					</FONT></TD>
					<TD align="center"><FONT size="2" color="Red"><B> <html:errors
						bundle="error" property="errors.sys.minamount_required" /><html:errors
						bundle="error" property="errors.sys.minamount_invalid" /></B></FONT></TD>
				</TR>
				<TR>
					<TD width="152" class="text pLeft15" nowrap><STRONG><bean:message
						bundle="view" key="sysconfig.max_amount" /><FONT color="RED">*</FONT>
					:</STRONG></TD>
					<TD><FONT color="#3C3C3C"> <html:text
						property="maxAmount" styleId="maxAmount" styleClass="box"
						tabindex="5" size="19" maxlength="10" name="SysConfigFormBean" onkeypress="isValidRate()" />
					</FONT></TD>
					<TD align="center" colspan="4"><FONT size="2" color="Red"><B>
					<html:errors bundle="error"
						property="errors.sys.maxamount_required" /><html:errors
						bundle="error" property="errors.sys.maxamount_invalid" /></B></FONT></TD>
				</TR>
				<TR>
					<TD class="text pLeft15"><FONT color="#000000">&nbsp;</FONT></TD>
					<TD colspan="3">
					<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
						<TR align="center">
							<TD width="70" colspan="2"><INPUT class="submit"
								type="submit" name="Submit" value="Submit" tabindex="6"
								onclick="return validate()"></TD>
							<TD><INPUT class="submit" type="submit" tabindex="7"
								name="BACK" value="Back" onclick="goBack();"></TD>
								<td><html:hidden styleId="isCircleAdmin" property="isCircleAdmin" /></td>
						</TR>
					</TABLE>
					</TD>
				</TR>
				<TR>
					<TD colspan="2"><FONT color="RED"><STRONG> <html:errors
						bundle="view" property="message.sysconfig.update_success" /> </STRONG></FONT></TD>
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
	if(document.getElementById("isCircleAdmin").value=="Y")
	{  
	   
		document.getElementById("circleId").disabled = true;
	}
}
</SCRIPT>
</BODY>

</html:html>
