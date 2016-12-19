<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<link href="<%=request.getContextPath()%>/theme/CalendarControl.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/theme/text.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/JScriptLib.js"></script>
<TITLE>
<bean:message bundle="view" key="application.title" /></TITLE>
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>

<SCRIPT language="javascript"> 
// 6 is for ABTS
// 5 for Mobile
	function chkPaymentType(){
	
	    if ((document.forms[0].paymentType.value=="4") || (document.forms[0].paymentType.value=="SELECT")){
			document.getElementById("confirmNoRowId").style.visibility="hidden";
			document.getElementById("divDth").style.display="none";
			document.getElementById("divmobile").style.display="block";
			document.getElementById("divabts").style.display="none";
			
			
		}else if (document.forms[0].paymentType.value=="7"){
			document.getElementById("confirmNoRowId").style.visibility="visible";
			document.getElementById("divDth").style.display="none";
			document.getElementById("divmobile").style.display="none";
			document.getElementById("divabts").style.display="block";
			
			
		}else if (document.forms[0].paymentType.value=="8"){
			document.getElementById("confirmNoRowId").style.visibility="visible";
			document.getElementById("divDth").style.display="block";
			document.getElementById("divmobile").style.display="none";
			document.getElementById("divabts").style.display="none";
			
			
		}else{
			document.getElementById("confirmNoRowId").style.visibility="hidden";
		}
	}
 
 
  // function that checks that is any field value is blank or not
function validate(){
	if(isNull('document.forms[0]','paymentType')|| document.getElementById("paymentType").value=="SELECT")
		 {
			   alert('<bean:message bundle="error" key="errors.postPaid.paymentType_required" />')
			    document.getElementById("paymentType").focus();
			   return false; 
		 }else if(isNull('document.forms[0]','receivingNo')|| document.getElementById("receivingNo").value==""){
		       
		       if(document.getElementById("paymentType").value=="8"){
		         alert('<bean:message bundle="error" key="errors.postPaid.dthNo_required" />')
 		         document.getElementById("receivingNo").focus();
		       }else if(document.getElementById("paymentType").value=="7"){
		         alert('<bean:message bundle="error" key="errors.postPaid.delNo_required" />')
 		         document.getElementById("receivingNo").focus();
		       }else if(document.getElementById("paymentType").value=="4"){
		         alert('<bean:message bundle="error" key="errors.postPaid.mobileNo_required" />')
 		         
		       }
		       document.getElementById("receivingNo").focus();
			   return false;  
	     }else if(isNumber(document.forms[0].receivingNo.value,1)==false){
			alert('<bean:message bundle="error" key="errors.postPaid.receivingNo_numeric" />');
			// document.forms[0].receivingNo.value="";	
			document.forms[0].receivingNo.focus();
			return false;
 		}
 		if (document.forms[0].paymentType.value=="4") {
			  if(document.forms[0].receivingNo.value.length!=10){
				   alert('<bean:message bundle="error" key="errors.postPaid.mobileNo_inValidlength" />');
				//   document.forms[0].receivingNo.value="";	
				   document.forms[0].receivingNo.focus();
				return false;
				}
		}
		if (document.forms[0].paymentType.value=="7") {
			  if((document.forms[0].receivingNo.value.length<10) || (document.forms[0].receivingNo.value.length>12)){
				   alert('<bean:message bundle="error" key="errors.postPaid.receivingDelNo_inValidlength" />');
				//   document.forms[0].receivingNo.value="";	
				   document.forms[0].receivingNo.focus();
				return false;
		}		
		}
		if(isNull('document.forms[0]','amount')|| document.getElementById("amount").value==""){
		       alert('<bean:message bundle="error" key="errors.postPaid.amount_required" />')
		       document.getElementById("amount").focus();
			   return false;  
	     }
	     else if(isNumericNumber(document.getElementById("amount").value)==false){
	    	 alert('<bean:message bundle="error" key="errors.transaction.amount_invalid" />')
			document.forms[0].amount.focus();
			return false;	
		}
		 else if (document.forms[0].paymentType.value=="7")
	     {
    	    if(!(isNull('document.forms[0]','confirmMobileNo'))&& document.getElementById("confirmMobileNo").value!="" && document.forms[0].confirmMobileNo.value.length!=10){
			   alert('<bean:message bundle="error" key="errors.postPaid.confirmMobileNo_inValidlength" />');
			  // document.forms[0].confirmMobileNo.value="";	
			   document.forms[0].confirmMobileNo.focus();
			return false;
			}else if (isNull('document.forms[0]','confirmMobileNo')|| document.getElementById("confirmMobileNo").value==""){
			
			
			}else if(isNumber(document.forms[0].confirmMobileNo.value,1)==false){
				alert('<bean:message bundle="error" key="errors.postPaid.confirmMobileNo_numeric" />');
			//	document.forms[0].confirmMobileNo.value="";	
				document.forms[0].confirmMobileNo.focus();
				return false;
			}
			
			
			
	     }
	     
	     else if (document.forms[0].paymentType.value=="8")
	     {
	     	if(!(isNull('document.forms[0]','confirmMobileNo'))&& document.getElementById("confirmMobileNo").value!="" && document.forms[0].confirmMobileNo.value.length!=10){
			   alert('<bean:message bundle="error" key="errors.postPaid.confirmMobileNo_inValidlength" />');
			  // document.forms[0].confirmMobileNo.value="";	
			   document.forms[0].confirmMobileNo.focus();
			return false;
			}else if (isNull('document.forms[0]','confirmMobileNo')|| document.getElementById("confirmMobileNo").value==""){
			
			
			}
			else if(isNumber(document.forms[0].confirmMobileNo.value,1)==false){
				alert('<bean:message bundle="error" key="errors.postPaid.confirmMobileNo_numeric" />');
			//	document.forms[0].confirmMobileNo.value="";	
				document.forms[0].confirmMobileNo.focus();
				return false;
			}
		  }
	//document.getElementById("methodName").value="makeBillPayment";
	document.getElementById("methodName").value="calculationBeforeConfirmation";
return true;
}


 //Validity of Amount
	/* function isValidAmount(evt, source){
		evt =(evt)?evt :window.event;
		var charCode =(evt.which)?evt.which :evt.keyCode;
		
		if(window.event.keyCode=="13")
	{
		if(validate())
		{
			document.forms[0].submit();
			//document.forms[0].action="AccountTransaction.do?methodName="+document.forms[0].methodName.value;
			return true;
		}
		return false;	
	}
	else
	{
		var amt = source.value;
		var len = parseInt(amt.length);
		var ind = parseInt(amt.indexOf('.'));
		if(isNaN(amt)){
			evt.keyCode = 0;
			source.value = ".00";
		}
		if(charCode == 46){
			if(ind>-1)
				evt.keyCode = 0;
		}
		else if(charCode < 48 || charCode >57)
			evt.keyCode = 0;
		else{
			if((len - ind)>2 && ind > -1)
				evt.keyCode = 0;
		}
}
} */

function checkKeyPressed()
{

	if(window.event.keyCode=="13")
	{
		if(validate())
		{
			document.forms[0].submit();
			//document.forms[0].action="AccountTransaction.do?methodName="+document.forms[0].methodName.value;
			return true;
		}
		return false;	
	}
}

function confirmCancel()
	{
		document.getElementById("transactionDetsDivId").style.visibility="visible";
		document.getElementById("confirmDivId").style.visibility="hidden";
		if (document.forms[0].paymentType.value=="7" || document.forms[0].paymentType.value=="8"){
			document.getElementById("confirmNoRowId").style.visibility="visible";
		}
		 chkPaymentType();
	}

function confirm_transfer()
{
	document.forms[0].flag.value="false";
	document.forms[0].debitAmount.value="";
	document.forms[0].creditAmount.value="";
	document.getElementById("methodName").value="postPaidTransactionAsync";
	document.forms[0].submit();
}


function getReportView()
{
       
	document.getElementById("methodName").value="searchCustomerTransWithId";
	document.forms[0].submit();
	document.forms[0].submit();
}

</SCRIPT>


</HEAD>


<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onkeypress="return checkKeyPressed();" >

<table cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">
	<tr>
		<td colspan="2" valign="top" width="100%"><jsp:include
			page="../common/dpheader.jsp" /></td>
	</tr>
	<tr valign="top">
		<td width="167" align="left" bgcolor="b4d2e7" valign="top"
			height="100%"><jsp:include page="../common/leftHeader.jsp" /></td>

		<td valign="top" width="100%" height="100%"><html:form
			action="/BillPaymentAction" method="post" focus="paymentType" >
			<table border="0" cellspacing="0" cellpadding="0" class="text">
			
			   
			    
				<tr>
					<td width="521" valign="top">
					<Div id="transactionDetsDivId">
					<table width="545" border="0" cellpadding="5" cellspacing="0"
						class="text">
						 <tr>
							<td colspan="2" class="text"><br>
							<IMG src="<%=request.getContextPath()%>/images/billPayment.gif" width="505" height="22" alt=""></TD>
						</tr>

						<tr>
							<td class="text pLeft15"><strong><font
								color="#000000">
								<bean:message bundle="view"
								key="label.postPaid.paymentType" /> </font><FONT COLOR="RED">*</FONT> :</strong></td>
							<td><html:select property="paymentType" 
								styleId="paymentType" tabindex="1" onchange="chkPaymentType();" styleClass="w130">
								<html:option value="SELECT">
									<bean:message bundle="view"
										key="label.postPaid.paymentType_select" />
								</html:option>
								<html:option value="4">
									<bean:message bundle="view"
										key="label.postPaid.paymentType_mobile" />
								</html:option>
								<html:option value="7">
									<bean:message bundle="view"
										key="label.postPaid.paymentType_abts" />
								</html:option>
								
								<html:option value="8">
									<bean:message bundle="view"
										key="label.postPaid.paymentType_dth" />
								</html:option>
								
							</html:select></td>
							<td width="3" class="text">&nbsp;</td>
							<td width="156">&nbsp;</td>
						</tr>


						<tr>
						
							<TD class="text pLeft15" nowrap ><strong><font
								color="#000000"><div id="divDth" align="left" style="display:none;align:left"><bean:message bundle="view"
									key="label.postPaid.dth_No" /><FONT COLOR="RED">*</FONT>:</div><div id="divmobile"align="left" style="display:block;align:left"><bean:message bundle="view"
								key="label.postPaid.receiving_No" /><FONT COLOR="RED">*</FONT>:</div><div id="divabts" align="left" style="display:none;align:left"><bean:message bundle="view"
								key="label.postPaid.del_No" /><FONT COLOR="RED">*</FONT>:</div></font></strong></TD>
							<TD><html:text property="receivingNo" styleId="receivingNo"
								styleClass="box" tabindex="2" size="19" maxlength="12"
								name="PostPaidBean" onkeypress="isValidNumber()"/></TD>
							<td width="3" class="text">&nbsp;</td>
							<td width="156">&nbsp;</td>
						</tr>


						<tr>
							<TD class="text pLeft15"><strong><font
								color="#000000"><bean:message bundle="view"
								key="label.postPaid.amount" /></font><FONT COLOR="RED">*</FONT> :</strong></TD>
							<TD><html:text property="amount" styleId="amount"
								styleClass="box" tabindex="3" size="19"
								maxlength="30"  onkeypress="isValidRate()"/></TD>
							<td width="3" class="text">&nbsp;</td>
							<td width="156">&nbsp;</td>
						</tr>

						<tr id="confirmNoRowId">
							<TD class="text pLeft15"><strong><font
								color="#000000"><bean:message bundle="view"
								key="label.postPaid.confirmMobileNo" /></font>
							:<br>(<bean:message key="application.digits.mobile_number" bundle="view" />)</br></strong></TD>
							<TD><html:text property="confirmMobileNo"
								 styleId="confirmMobileNo" styleClass="box"
								tabindex="4" size="19" maxlength="10" onkeypress="isValidNumber()"/></TD>
							<td width="3" class="text">&nbsp;</td>
							<td width="156">&nbsp;</td>
						</tr>
						<tr>
							<td class="text pLeft15"><font color="#000000">&nbsp;</font></td>
							<td colspan="3">
							<table width="140" border="0" cellspacing="0" cellpadding="5">
								<TR align="center">
									<TD width="70"><input class="submit" type="submit"
										tabindex="5" name="Submit" value="Submit"
										onclick="return validate();"></td>
									<td width="70"><INPUT class="submit" type="button" onclick="resetAll();"
										name="Submit2" value="Reset" tabindex="6"></TD>

								</TR>
							</table>
							</td>
						</tr>
						
						
						<TR>
							<TD colspan="4" align="center">&nbsp;</TD>
						</TR>
						<TR>
							<TD colspan="4"><FONT color="RED"><STRONG>
							<html:errors /> </STRONG></FONT>
							
							<logic:notEmpty property="transactionId" name="PostPaidBean" >					
							<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
								<TR align="center">
									<TD width="70"><INPUT class="submit" type="button" onclick="getReportView()"
										name="Submit3" value="Details" tabindex="6">
									</TD>
								</TR>
							</TABLE>
						</logic:notEmpty>
						</TD>
						</TR>
						
						
						<tr>
							<TD colspan="4" ><font color="RED"><strong><html:errors property="genericError"	bundle="error" /></strong></font></TD>
						</TR>

					</table>
					</Div>
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center">&nbsp;</td>
				</tr>
				<TR>
					<TD width="521" valign="top">
					<DIV id="confirmDivId"
						style="visibility:hidden;position:absolute;left:170px;top:125px">
					<TABLE width="545" border="0" cellpadding="5" cellspacing="0"
						class="text">
					
					 <tr>
							<td colspan="2" class="text"><br>
							<IMG src="<%=request.getContextPath()%>/images/billPayment.gif" width="505" height="22" alt=""></TD>
				</tr>
					
						<TR>
							<TD class="text" colspan="4"><FONT color="BLACK"><STRONG>
							<html:errors property="ConfirmError" bundle="error" /> </STRONG></FONT></TD>
						</TR>
						<TR align="center">
							<TD align="right"><INPUT type="button" class="submit"
								tabindex="5"
								value='<bean:message bundle="view" key="button.confirm_transfer" />'
								onclick="confirm_transfer();" /></TD>
							<TD align="left"><INPUT type="button" class="submit"
								tabindex="6"
								value='<bean:message bundle="view" key="button.confirm_cancel" />'
								onclick="confirmCancel();" /></TD>
						</TR>
					</TABLE>
					</DIV>
					</TD>
				</TR>
				<html:hidden property="methodName" styleId="methodName" />
				<tr>
					<td><html:hidden property="flag" name="PostPaidBean"
						styleId="flag" /> <html:hidden property="debitAmount"
						name="PostPaidBean" styleId="debitAmount" /> <html:hidden
						property="creditAmount" name="PostPaidBean" styleId="creditAmount" />
						<html:hidden styleId="transactionId" property="transactionId"  name="PostPaidBean"/>
						<html:hidden styleId="transactionType" property="transactionType" name="PostPaidBean" />
																
					</td>
				</tr>
			</table>
		</html:form></td>
	</tr>
	<tr align="center" bgcolor="4477bb" valign="top">
		<td colspan="2" height="17" align="center"><jsp:include
			page="../common/footer.jsp" /></td>
	</tr>
</table>

</BODY>
<script type="text/javascript">

{
    
	if(document.getElementById("paymentType").value=="SELECT"){
	 	document.getElementById("confirmNoRowId").style.visibility="hidden";
	 }
	else if(document.getElementById("paymentType").value=="8" || document.getElementById("paymentType").value=="7" ){
		document.getElementById("confirmNoRowId").style.visibility="visible";
	 }else if (document.getElementById("paymentType").value=="4"){
		document.getElementById("confirmNoRowId").style.visibility="hidden";
	 } 
	 
	 if(document.getElementById("flag").value=="true")
	{
	
	   
		document.getElementById("transactionDetsDivId").style.visibility="hidden";
		document.getElementById("confirmNoRowId").style.visibility="hidden";
		document.getElementById("confirmDivId").style.visibility="visible";
	}else{
	
	    chkPaymentType();
	}
}
</script>

</html:html>
