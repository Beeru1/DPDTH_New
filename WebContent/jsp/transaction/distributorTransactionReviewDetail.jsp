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
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<SCRIPT language="javascript" type="text/javascript"> 

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

	var perfomeUpdate=false; 
	var remarkStatus=false;
	// function that checks that is any field value is blank or not
	function validate(){
	
	     document.forms[0].reviewComment.value=trimAll(document.forms[0].reviewComment.value);	
	
		return perfomeUpdate;
	}
function checkKeyPressed()
{
	if(window.event.keyCode=="13")
	{
	 if(remarkStatus==false){
		  if(validate()){
		 	    
			}else{
			     return false;
			}
		}
		
		
	}
}
	
	// set  the action class method name which call when we click on submit button 
	function setFunctionName(perfomeUpdate){
		document.getElementById("methodName").value="updateDistributorTransaction";
		
		if((perfomeUpdate=='A')||(perfomeUpdate=='R')){
			if(isNull('document.forms[0]','reviewComment')|| trim(document.getElementById("reviewComment").value)==""){
				perfomeUpdate=false;
				alert('<bean:message bundle="error" key="errors.transaction.remarks_required" />')
				return false; 
			}
			document.forms[0].reviewComment.value=trimAll(document.forms[0].reviewComment.value);	
			document.getElementById("approve").disabled="true";
			document.getElementById("reject").disabled="true"; 
			document.getElementById("reviewStatus").value=perfomeUpdate;
			perfomeUpdate=true;
			document.forms[0].submit();
			
		}
		
		return true;
	}
	function goBack(){
		document.getElementById("methodName").value="getAllDistributorTransactionList";
	}
	
 function setRemarkStatus(){
		remarkStatus=true;
	}
	
 function ResetRemarkStatus(){
		remarkStatus=false;
	}
	
</SCRIPT>

</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onkeypress="return checkKeyPressed();">

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
			action="DistributorTransaction" >
			<TABLE border="0" cellspacing="0" cellpadding="0" class="text">
				<TR>
					<TD width="521" valign="top">
					<TABLE width="545" border="0" cellpadding="5" cellspacing="0"
						class="text">
						<TR>
							<TD colspan="4" class="text"><BR>
							<IMG src="<%=request.getContextPath()%>/images/transactionDetails.gif" width="505" height="22"
								alt=""></TD>
						</TR>
						<TR>
							<TD colspan="4" class="text"><FONT color="#ff0000" size="-2"><html:errors
								bundle="error" /><html:errors /></FONT></TD>
						</TR>

						<!-- ---------Transaction Id and Account Name  -->
						<TR>
							<TD class="text pLeft15" nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="transaction.transactionid" /></FONT> :</STRONG></TD>
							<TD><html:text property="transactionId"
								styleId="transactionId" styleClass="box" size="19"
								maxlength="10" readonly="true" /></TD>

							<TD class="text "><STRONG><FONT color="#000000"><bean:message
								bundle="view" key="transaction.accountname" /></FONT> :</STRONG></TD>
							<TD><html:text property="accountName" styleId="accountName"
								styleClass="box" size="19" readonly="true" /></TD>



						</TR>



						<!-- ---------Transaction amount and Account Code  -->
						<TR>
							<TD class="text pLeft15" nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="transaction.account_code" /></FONT> :</STRONG></TD>
							<TD><html:text property="accountCode" styleId="accountCode"
								styleClass="box" size="19" maxlength="10" readonly="true" /></TD>

							<TD class="text " nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="transaction.bankname" /></FONT> :</STRONG></TD>
							<TD><html:text property="bankName" styleId="bankName"
								styleClass="box" size="19" readonly="true" /></TD>
						</TR>

						<!-- ---------Cheque date and Cheque No.---------  -->
						<TR>
							<TD class="text pLeft15" nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="transaction.paymentMode" /></FONT> :</STRONG></TD>
							<TD><html:text property="paymentMode" styleId="paymentMode"
								styleClass="box" size="19" readonly="true" /></TD>
							<TD class="text " nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="transaction.cheq_cardno" /></FONT> :</STRONG></TD>
							<TD><html:text property="chqccNumber" styleId="chqccNumber"
								styleClass="box" size="19" readonly="true" /></TD>

						</TR>
						<!-- ---------Created By Name Date AND Credited Amount---------  -->
						<TR>

							<TD class="text pLeft15" nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="transactiontype.transaction_amount" /></FONT> :</STRONG></TD>
							<TD><html:text property="transactionAmount"
								styleId="transactionAmount" styleClass="box" size="19"
								readonly="true" /></TD>

							<TD class="text " nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="transactiontype.amount_credit" /></FONT><FONT color="RED">*</FONT>
							:</STRONG></TD>
							<TD><html:text property="creditedAmount"
								styleId="creditedAmount" styleClass="box" size="19"
								readonly="true" /></TD>

						</TR>

						<!-- ---------transaction Type-and bank name--------  -->
						<TR>

							<TD class="text pLeft15" nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="transaction.chequedate" /></FONT> :</STRONG></TD>
							<TD><html:text property="chequeDate" styleId="chequeDate"
								styleClass="box" size="15" readonly="true" /></TD>

							<TD class="text" nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="transaction.carddate" /></FONT> :</STRONG></TD>
							<TD><html:text property="ccvalidDate" styleId="ccvalidDate"
								maxlength="10" styleClass="box" size="15" readonly="true" /></TD>
						</TR>

						<TR>

							<TD class="text pLeft15" nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="transaction.ecsno" /></FONT> :</STRONG></TD>
							<TD><html:text property="ecsno" styleId="ecsno"
								styleClass="box" size="15" readonly="true" /></TD>

							<TD class="text" nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="transaction.purchaseorderno" /></FONT> :</STRONG></TD>
							<TD><html:text property="purchaseOrderNo"
								styleId="purchaseOrderNo" maxlength="10" styleClass="box"
								size="15" readonly="true" /></TD>
						</TR>

						<TR>
							<TD class="text pLeft15" nowrap><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="transaction.purchasedate" /></FONT> :</STRONG></TD>
							<TD><html:text property="purchaseOrderDate"
								styleId="purchaseOrderDate" styleClass="box" readonly="true"
								tabindex="-1" size="15" maxlength="10" /></TD>
						</TR>

						<logic:notEqual name="DistributorTransactionFormBean" property="reviewStatus" value="P">
							<TR>

								<TD class="text pLeft15" nowrap><STRONG><FONT
									color="#000000"><bean:message bundle="view"
									key="transaction.comment" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
								<TD colspan="3"><html:textarea property="reviewComment"
									styleId="reviewComment" tabindex="1" cols="60" rows="3"
									readonly="true"></html:textarea></TD>

							</TR>
						</logic:notEqual>
						<!-- ---------Review Status and Comments.---------  -->
						<logic:match name="DistributorTransactionFormBean" property="reviewStatus" value="P">

							<TR>
								<TD class="text pLeft15" nowrap><STRONG><FONT
									color="#000000"><bean:message bundle="view"
									key="transaction.comment" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
								<TD colspan="3"><html:textarea property="reviewComment"
									styleId="reviewComment" onfocus="setRemarkStatus()" onblur="ResetRemarkStatus()" tabindex="1" cols="60" rows="3"></html:textarea>
								</TD>
							</TR>

							<TR>
								<TD class="text pLeft15"><FONT color="#000000">&nbsp;</FONT></TD>
								<TD colspan="3">
								<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
									<TR align="center">

										<TD width="70"><INPUT class="submit" type="submit"
											tabindex="2" name="approve" id="approve" value="Approve"
											onclick="return setFunctionName('A')"></TD>

										<TD width="70"><INPUT class="submit" type="submit"
											tabindex="3" name="reject" id="reject" value="Reject"
											onclick="return setFunctionName('R')"></TD>
										<%-- <TD><input class="submit" type="submit" tabindex="8"
										name="BACK" value="BACK" onclick="goBack()"></TD>       --%>
									</TR>
								</TABLE>
								</TD>
							</TR>
						</logic:match>

						<logic:notMatch name="DistributorTransactionFormBean"
							property="reviewStatus" value="P">
							<TR>
								<TD class="text pLeft15"><FONT color="#000000">&nbsp;</FONT></TD>
								<TD colspan="3">
								<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
									<TR align="center">
										<TD width="70"><INPUT type="submit" tabindex="4"
											name="ok" style="WIDTH:40px" value="ok"
											onclick="setFunctionName('N')"></TD>

									</TR>
								</TABLE>
								</TD>
							</TR>
						</logic:notMatch>


					</TABLE>
					</TD>
				</TR>
				<TR>
					<TD colspan="4" align="center">&nbsp;</TD>

					<!-- hidden  use to set dispatch action method name -->
					<html:hidden property="methodName" styleId="methodName" />
					<!-- hidden  use to set accountId -->
					<html:hidden property="accountId" styleId="accountId" />
					<!-- hidden  use to set Selected  Transaction ID from list -->
					<html:hidden property="transactionId" styleId="transactionId" />
					<!-- hidden  use to set  Transaction type selected From search list page and after update to get updated list -->
					<html:hidden property="transactionListType"
						styleId="transactionListType" />
					<html:hidden property="reviewStatus" styleId="reviewStatus" />
					<html:hidden property="circleId" styleId="circleId" />
					<html:hidden property="startDate" styleId="startDate" />
					<html:hidden property="endDate" styleId="endDate" />
					<html:hidden property="page" styleId="page" />
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
