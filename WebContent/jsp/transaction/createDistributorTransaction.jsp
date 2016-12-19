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
<link href="<%=request.getContextPath()%>/theme/CalendarControl.css" rel="stylesheet"
	type="text/css">
<link href="<%=request.getContextPath()%>/theme/text.css" rel="stylesheet" type="text/css">	
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></script>
<TITLE></TITLE>
<SCRIPT language=JavaScript src="<%=request.getContextPath()%>/scripts/Cal.js" type=text/javascript></SCRIPT>
<SCRIPT language="javascript"> 
		

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

var remarkStatus=false;
	  // function that checks that is any field value is blank or not
	  function validate(){
	    
		var transAmount= document.getElementById("transactionAmount").value;
		
		if(isNull('document.forms[0]','accountCode')|| document.getElementById("accountCode").value=="")
		 {
			   alert('<bean:message bundle="error" key="errors.transaction.accountcode_required" />')
			    document.getElementById("accountCode").focus();
			   return false; 
		 }
	     else if(isNull('document.forms[0]','paymentMode')|| document.getElementById("paymentMode").value=="SELECT"){
		       alert('<bean:message bundle="error" key="errors.transaction.paymentmode_required" />')
		       document.getElementById("paymentMode").focus();
			   return false;  
	     }else if((!document.getElementById("chqccNumber").disabled)&&(isNull('document.forms[0]','bankName')|| document.getElementById("bankName").value=="")){
		       
			       alert('<bean:message bundle="error" key="errors.transaction.bankname_required" />') 
			        document.getElementById("bankName").focus();
				   return false;  
		       
	      		
	     }else if(isNull('document.forms[0]','transactionAmount')|| document.getElementById("transactionAmount").value==""){
		       alert('<bean:message bundle="error" key="errors.transaction.amount_invalid" />')
		        document.getElementById("transactionAmount").focus();
			   return false;  
	     }
	    else if(isNumericNumber(document.getElementById("transactionAmount").value)==false){
	    	 alert('<bean:message bundle="error" key="errors.transaction.amount_invalid" />')
			document.forms[0].transactionAmount.focus();
			return false;	
		}
	    else if((transAmount*1)==0){
	  			alert('<bean:message bundle="error" key="errors.transaction.amount_invalid" />')
		           document.getElementById("transactionAmount").focus();
		           return false;
	  	  }
	  
	  /*	
	  else if((!document.getElementById("chqccNumber").disabled)&&(isNull('document.forms[0]','chqccNumber')|| document.getElementById("chqccNumber").value=="")){
		       
	      		if(document.getElementById("paymentMode").value=="CREDIT"){
	      		   alert('<bean:message bundle="error" key="errors.transaction.enter_creditcardno" />');   
	      		}
	      		if(document.getElementById("paymentMode").value=="CHEQUE"){
	      		   alert('<bean:message bundle="error" key="errors.transaction.enter_chequeno" />');   
	      		}
	      		document.getElementById("chqccNumber").focus();
		  		return false; 
		  
		  }
		  */
		  else if((document.getElementById("paymentMode").value=="CHEQUE" && (!document.getElementById("chqccNumber").disabled)&&(isNull('document.forms[0]','chqccNumber')|| document.getElementById("chqccNumber").value==""))){
		        alert('<bean:message bundle="error" key="errors.transaction.enter_chequeno" />');   
	      		document.getElementById("chqccNumber").focus();
		  		return false; 
		  
		  }else if(document.getElementById("paymentMode").value=="CREDIT" && document.getElementById("chqccNumber").value.length>16){
		     alert('<bean:message bundle="error" key="error.transaction.cardlength" />')
		     document.getElementById("chqccNumber").focus();
			   return false;
		  }else if((!document.getElementById("chequeDate").disabled)&&(isNull('document.forms[0]','chequeDate')|| document.getElementById("chequeDate").value=="")&& (document.getElementById("paymentMode").value=="CHEQUE")){
			    alert('<bean:message bundle="error" key="errors.transaction.chqdate_required" />')
			    document.getElementById("chequeDateImg").focus();
			 	return false;  
	     } else if((!document.getElementById("ccvalidDate").disabled)&&(isNull('document.forms[0]','ccvalidDate')|| document.getElementById("ccvalidDate").value=="")&& (document.getElementById("paymentMode").value=="CREDIT")){
			    alert('<bean:message bundle="error" key="errors.transaction.card_date_required" />')
			      document.getElementById("creditValidDateImg").focus();
				return false;  
	     }
	      else if(document.getElementById("paymentMode").value=="ECS"){
	         if(isNull('document.forms[0]','ecsno')|| document.getElementById("ecsno").value==""){
		          alert('<bean:message bundle="error" key="errors.transaction.ecsno_required" />')
				  document.getElementById("ecsno").focus();
				  return false;  
	         }
	          if(isNaN(document.getElementById("ecsno").value)==false){
		          if(document.getElementById("ecsno").value*1==0){
			        alert('<bean:message bundle="error" key="errors.transaction.ecsno_invalid" />')
				    document.getElementById("ecsno").focus();
		            return false;
		         }
	         
	         }
	          if(isNull('document.forms[0]','purchaseOrderNo')|| document.getElementById("purchaseOrderNo").value==""){
		          alert('<bean:message bundle="error" key="errors.transaction.purchageorderno_required" />')
				  document.getElementById("purchaseOrderNo").focus();
				  return false; 
	         }
	         
	         if(isNaN(document.getElementById("purchaseOrderNo").value)==false){
		          if(document.getElementById("purchaseOrderNo").value*1==0){
			        alert('<bean:message bundle="error" key="errors.transaction.purchageorderno_invalid" />')
				    document.getElementById("purchaseOrderNo").focus();
		            return false;
		         }
	         
	         }
	          if(isNull('document.forms[0]','purchaseOrderDate')|| document.getElementById("purchaseOrderDate").value==""){
		          alert('<bean:message bundle="error" key="errors.transaction.purchaseorderdate_required" />')
				  document.getElementById("purchaseOrderDate").focus();
				  return false; 
	           
	         }
	                
	      }
	      else if(isNull('document.forms[0]','reviewComment')|| trim(document.getElementById("reviewComment").value)==""){
				alert('<bean:message bundle="error" key="errors.transaction.remarks_required" />');
				document.getElementById("reviewComment").focus();
				return false; 
			}
		 else if( document.getElementById("reviewComment").value.length>510){
				alert('<bean:message bundle="error" key="errors.transaction.remarkslength" />');
				document.getElementById("reviewComment").focus();
				return false; 
			}	
			if(document.getElementById("paymentMode").value=="CHEQUE" && (!isNull('document.forms[0]','ecsno')|| document.getElementById("ecsno").value!="")){
				if(isNaN(document.getElementById("ecsno").value)==false){
		  			if(document.getElementById("ecsno").value*1==0){
			      		alert('<bean:message bundle="error" key="errors.transaction.ecsno_invalid" />')
				   		document.getElementById("ecsno").focus();
		           		return false;
		        	}
	         	}
		 }
		 if(document.getElementById("paymentMode").value=="CHEQUE" && (!isNull('document.forms[0]','purchaseOrderNo')|| document.getElementById("purchaseOrderNo").value!="")){
				if(isNaN(document.getElementById("purchaseOrderNo").value)==false){
					if(document.getElementById("purchaseOrderNo").value*1==0){
						alert('<bean:message bundle="error" key="errors.transaction.purchageorderno_invalid" />')
						document.getElementById("purchaseOrderNo").focus();
						return false;
					}
				}
			
		}
		if(document.getElementById("paymentMode").value=="CASH" && (!isNull('document.forms[0]','ecsno')|| document.getElementById("ecsno").value!="")){
				if(isNaN(document.getElementById("ecsno").value)==false){
		  			if(document.getElementById("ecsno").value*1==0){
			      		alert('<bean:message bundle="error" key="errors.transaction.ecsno_invalid" />')
				   		document.getElementById("ecsno").focus();
		           		return false;
		        	}
	         	}
		 }
		 if(document.getElementById("paymentMode").value=="CASH" && (!isNull('document.forms[0]','purchaseOrderNo')|| document.getElementById("purchaseOrderNo").value!="")){
				if(isNaN(document.getElementById("purchaseOrderNo").value)==false){
					if(document.getElementById("purchaseOrderNo").value*1==0){
						alert('<bean:message bundle="error" key="errors.transaction.purchageorderno_invalid" />')
						document.getElementById("purchaseOrderNo").focus();
						return false;
					}
				}
			
		}
		if(document.getElementById("paymentMode").value=="CREDIT" && (!isNull('document.forms[0]','ecsno')|| document.getElementById("ecsno").value!="")){
				if(isNaN(document.getElementById("ecsno").value)==false){
		  			if(document.getElementById("ecsno").value*1==0){
			      		alert('<bean:message bundle="error" key="errors.transaction.ecsno_invalid" />')
				   		document.getElementById("ecsno").focus();
		           		return false;
		        	}
	         	}
		 }
		 if(document.getElementById("paymentMode").value=="CREDIT" && (!isNull('document.forms[0]','purchaseOrderNo')|| document.getElementById("purchaseOrderNo").value!="")){
				if(isNaN(document.getElementById("purchaseOrderNo").value)==false){
					if(document.getElementById("purchaseOrderNo").value*1==0){
						alert('<bean:message bundle="error" key="errors.transaction.purchageorderno_invalid" />')
						document.getElementById("purchaseOrderNo").focus();
						return false;
					}
				}
			
		}
			
		document.forms[0].reviewComment.value=trimAll(document.forms[0].reviewComment.value);	
				
	   return true;
	}
   
	// this function disables the control based on selection of payment Mode 
	function disableControls(){
		       
		 if(document.getElementById("paymentMode").value=="CASH"){
		    document.getElementById("ccvalidDate").disabled=true;
		    document.getElementById("chequeDate").disabled=true;
	        document.getElementById("chqccNumber").disabled=true;
			document.getElementById("creditValidDateImg").disabled=true;
			document.getElementById("chequeDateImg").disabled=true;
			document.getElementById("ccvalidDate").value="";
		    document.getElementById("chequeDate").value="";
			document.getElementById("chqccNumber").value="";
			document.getElementById("bankName").disabled=true;
			document.getElementById("ecsno").disabled=false;
			//document.getElementById("ecsno").value="";
			document.getElementById("bankName").value="";
			//document.getElementById("purchaseOrderDate").value="";
		    document.getElementById("purchaseOrderDateImg").disabled=false;
		    document.getElementById("purchaseOrderNo").disabled=false;
			//document.getElementById("purchaseOrderNo").value="";
			
			document.getElementById("divpurdtnotreq").style.display="block";
			document.getElementById("divpurdtreq").style.display="none";
			document.getElementById("divchqdtnotreq").style.display="block";
			document.getElementById("divchqdtreq").style.display="none";
			
			document.getElementById("divcarddtnotreq").style.display="block";
			document.getElementById("divcarddtreq").style.display="none";
			
			document.getElementById("divecsnonotreq").style.display="block";
			document.getElementById("divecsnoreq").style.display="none";
			
			document.getElementById("divPurOrdernoNotreq").style.display="block";
			document.getElementById("divPurOrdernoreq").style.display="none";
			
			document.getElementById("divChqCardNonotreq").style.display="block";
			document.getElementById("divChqCardNoreq").style.display="none";
			
			document.getElementById("divBankNamenotreq").style.display="block";
			document.getElementById("divBankNamereq").style.display="none";
			
		
	      }
	      if(document.getElementById("paymentMode").value=="CREDIT"){
		  	document.getElementById("chequeDate").disabled=true;
	        document.getElementById("chequeDate").value="";
		  	document.getElementById("ccvalidDate").disabled=false;
	        document.getElementById("chequeDateImg").disabled=true;
	        document.getElementById("creditValidDateImg").disabled=false;
	        document.getElementById("chqccNumber").disabled=false;
			document.getElementById("bankName").disabled=false;
			document.getElementById("ecsno").disabled=false;
			//document.getElementById("ecsno").value="";
			
			//document.getElementById("purchaseOrderDate").disabled="";
			//document.getElementById("purchaseOrderDate").value="";
		    document.getElementById("purchaseOrderDateImg").disabled=false;
			document.getElementById("purchaseOrderNo").disabled=false;
			//document.getElementById("purchaseOrderNo").value="";
			
			
			document.getElementById("divpurdtnotreq").style.display="block";
			document.getElementById("divpurdtreq").style.display="none";
			document.getElementById("divchqdtnotreq").style.display="block";
			document.getElementById("divchqdtreq").style.display="none";
			
			document.getElementById("divcarddtnotreq").style.display="none";
			document.getElementById("divcarddtreq").style.display="block";
			
			document.getElementById("divChqCardNonotreq").style.display="block";
			document.getElementById("divChqCardNoreq").style.display="none";
			
			document.getElementById("divecsnonotreq").style.display="block";
			document.getElementById("divecsnoreq").style.display="none";
			
			document.getElementById("divPurOrdernoNotreq").style.display="block";
			document.getElementById("divPurOrdernoreq").style.display="none";
			
			document.getElementById("divBankNamenotreq").style.display="none";
			document.getElementById("divBankNamereq").style.display="block";
			
		  }
	      if(document.getElementById("paymentMode").value=="CHEQUE"){
		  	document.getElementById("ccvalidDate").disabled=true;
		  	document.getElementById("chequeDate").disabled=false;
			document.getElementById("chqccNumber").disabled=false;
			document.getElementById("creditValidDateImg").disabled=true;
	        document.getElementById("chequeDateImg").disabled=false;
	        document.getElementById("ccvalidDate").value=""; 
	        document.getElementById("ecsno").disabled=false;
			//document.getElementById("ecsno").value="";
			document.getElementById("bankName").disabled=false;
			
			//document.getElementById("purchaseOrderDate").value="";
		    document.getElementById("purchaseOrderDateImg").disabled=false;
		    
			document.getElementById("purchaseOrderNo").disabled=false;
			//document.getElementById("purchaseOrderNo").value="";
						
			document.getElementById("divchqdtnotreq").style.display="none";
			document.getElementById("divchqdtreq").style.display="block";
			
			document.getElementById("divChqCardNonotreq").style.display="none";
			document.getElementById("divChqCardNoreq").style.display="block";
			
			
			document.getElementById("divcarddtnotreq").style.display="block";
			document.getElementById("divcarddtreq").style.display="none";
			
			
			
			document.getElementById("divecsnonotreq").style.display="block";
			document.getElementById("divecsnoreq").style.display="none";
			
			document.getElementById("divPurOrdernoNotreq").style.display="block";
			document.getElementById("divPurOrdernoreq").style.display="none";
			
			
			
			document.getElementById("divpurdtnotreq").style.display="block";
			document.getElementById("divpurdtreq").style.display="none";
			
			document.getElementById("divBankNamenotreq").style.display="none";
			document.getElementById("divBankNamereq").style.display="block";
			

		  } 
		 if(document.getElementById("paymentMode").value=="ECS"){
		  	document.getElementById("ccvalidDate").disabled=true;
		  	document.getElementById("chequeDate").disabled=true;
		  	document.getElementById("chqccNumber").disabled=true;
			document.getElementById("creditValidDateImg").disabled=true;
	        document.getElementById("chequeDateImg").disabled=true;
	       	document.getElementById("chqccNumber").value="";
	        document.getElementById("purchaseOrderDateImg").disabled=false;
	        document.getElementById("bankName").disabled=false;
	        document.getElementById("ccvalidDate").value=""; 
	        document.getElementById("chequeDate").value=""; 
	        document.getElementById("ecsno").disabled=false;
		    document.getElementById("purchaseOrderDateImg").disabled=false;
		    document.getElementById("purchaseOrderNo").disabled=false;
		    
		    document.getElementById("divchqdtnotreq").style.display="block";
			document.getElementById("divchqdtreq").style.display="none";
			
			document.getElementById("divChqCardNonotreq").style.display="block";
			document.getElementById("divChqCardNoreq").style.display="none";
			
			
			document.getElementById("divcarddtnotreq").style.display="block";
			document.getElementById("divcarddtreq").style.display="none";
			
			
			
			document.getElementById("divecsnonotreq").style.display="none";
			document.getElementById("divecsnoreq").style.display="block";
			
			document.getElementById("divPurOrdernoNotreq").style.display="none";
			document.getElementById("divPurOrdernoreq").style.display="block";
			
			
			
			document.getElementById("divpurdtnotreq").style.display="none";
			document.getElementById("divpurdtreq").style.display="block";
			
			document.getElementById("divBankNamenotreq").style.display="block";
			document.getElementById("divBankNamereq").style.display="none";
		    
		    
		  }
		  
	}

	// this function open child window to search and select distributor code 
	function OpenDistribtorWindow(){
			
		document.submitForm.action="./DistributorTransaction.do?methodName=showSelectDistributor";
		document.submitForm.accountCode.value=document.getElementById("accountCode").value;
		displayWindow = window.open('', "newWin", "width =700,height= 220, left=50, top=100, scrollbars=yes,resizable=yes, status=yes");
       	document.submitForm.submit();
	
	}

function checkKeyPressed()
{
	if(window.event.keyCode=="13")
	{
	 if(remarkStatus==false){
		  if(validate()){
		 	    document.getElementById("Submit").disabled=true; 
				if(document.getElementById("submitStatus").value=="0"){  
		  			   document.getElementById("submitStatus").value="1";
		  			   document.forms[0].reviewComment.value=trimAll(document.forms[0].reviewComment.value);	
					   document.forms[0].submit();
				 }
			}else{
			     return false;
			}
		}
		
		
	}
}

 function setRemarkStatus(){
		remarkStatus=true;
	}
	
 function ResetRemarkStatus(){
			remarkStatus=false;
	}




</SCRIPT>

</HEAD>


<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onkeypress="return checkKeyPressed();" onload="disableControls()" >
	
	<table cellspacing="0" cellpadding="0" border="0" width="100%" height="100%" valign="top">
		<tr>
			<td colspan="2" valign="top" width="100%"><jsp:include page="../common/dpheader.jsp" /></td>
		</tr>
		<tr valign="top">
			<td width="167" align="left" bgcolor="b4d2e7" valign="top" height="100%"><jsp:include page="../common/leftHeader.jsp" /></td>
			<td valign="top" width="100%" height="100%"><html:form action="DistributorTransaction" focus="distribtor">
			<table border="0" cellspacing="0" cellpadding="0" class="text">
				<tr>
					<td width="521" valign="top">
					<table width="545" border="0" cellpadding="5" cellspacing="0"
						class="text">
						<tr> 
                <td colspan="4" class="text"><br> <img src="<%=request.getContextPath()%>/images/createTransaction.gif" width="505" height="22"></td>
              </tr>

						<!-- ---  FOR Account Code and distributor Button -->

						<tr>
							<td class="text pLeft15" nowrap><strong><font color="#000000"><bean:message
								bundle="view" key="transaction.account_code" /></font><FONT COLOR="RED">*</FONT> :</strong></td>
							<td colspan=2><html:text property="accountCode"
								styleId="accountCode" styleClass="box" tabindex="1" size="19"
								maxlength="10" readonly="true" onkeypress="isValidNumAlpha()" />
							<INPUT type="button" tabindex="1" class="submit"
								name="distribtor" value='Account'
								onclick="OpenDistribtorWindow()" /></td>
						</TR>


						<!--  FOR Transaction Type  and Bank Name  -->
						<TR>
							<TD class="text pLeft15" nowrap><strong><font color="#000000"><bean:message
								bundle="view" key="transaction.paymentMode" /></font><FONT COLOR="RED">*</FONT> :</strong>
							</TD>
							<TD><html:select property="paymentMode" styleClass="w130"
								styleId="paymentMode" onchange="disableControls()"
								tabindex="2" styleClass="w130" >
								<html:option value="SELECT">--Select--</html:option>
								<html:option value="CASH">
									<bean:message bundle="view" key="paymentMode.cash" />
								</html:option>
								<html:option value="CHEQUE">
									<bean:message bundle="view" key="paymentMode.cheque" />
								</html:option>
								<html:option value="CREDIT">
									<bean:message bundle="view" key="paymentMode.card" />
								</html:option>
								<html:option value="ECS">
									<bean:message bundle="view" key="paymentMode.ecs" />
								</html:option>
							</html:select></TD>

							<td class="text pLeft15" nowrap><strong><font color="#000000"><div id="divBankNameNotreq" align="left" style="display:none;align:left"><bean:message bundle="view"
									key="transaction.bankname" />:</div></font><div id="divBankNamereq" align="left" style="display:block;"><bean:message bundle="view"
									key="transaction.bankname" /><FONT color="RED">*</FONT>:</div></strong></td>
							<td><html:text property="bankName" styleId="bankName"
								styleClass="box" tabindex="3" size="19" maxlength="60" disabled="true" 
								onkeypress="isValidNumAlpha()" /></td>
						</tr>

						<!--  FOR Account Amount and Transaction Cheque CC no. -->
						<tr>


							<TD class="text pLeft15" nowrap><strong><font color="#000000"><bean:message
								bundle="view" key="transactiontype.transaction_amount" /></font><FONT COLOR="RED">*</FONT> :</strong>
							</TD>
							<TD><html:text property="transactionAmount"
								styleId="transactionAmount" styleClass="box" tabindex="4"
								size="19" maxlength="10" onkeypress="isValidRate()"/></TD>

							<td class="text pLeft15" nowrap><strong><font color="#000000"><div id="divChqCardNonotreq" align="left" style="display:none;align:left"><bean:message bundle="view"
									key="transaction.cheq_cardno" />:</div></font><div id="divChqCardNoreq" align="left" style="display:block;"><bean:message bundle="view"
									key="transaction.cheq_cardno" /><FONT color="RED">*</FONT>:</div></strong>
							</td>
							<td><html:text property="chqccNumber" styleId="chqccNumber"
								styleClass="box" tabindex="5" disabled="true"  size="19" maxlength="16"
								onkeypress="isValidNumber(this);" /></td>

						</tr>
						<TR>

							<td class="text pLeft15" nowrap><strong><font color="#000000"><div id="divecsnonotreq" align="left" style="display:none;align:left"><bean:message bundle="view"
									key="transaction.ecsno" />:</div></font><div id="divecsnoreq" align="left" style="display:block;"><bean:message bundle="view"
									key="transaction.ecsno" /><FONT color="RED">*</FONT>:</div></strong></td>
							<td><html:text property="ecsno" styleId="ecsno"
								styleClass="box"  disabled="true" tabindex="8" size="18" onkeypress="alphaNumWithoutSpace()"
								maxlength="16" /></td>
							<td class="text pLeft15" nowrap><strong><font color="#000000"><div id="divPurOrdernoNotreq" align="left" style="display:none;align:left"><bean:message bundle="view"
									key="transaction.purchaseorderno" />:</div></font><div id="divPurOrdernoreq" align="left" style="display:block;"><bean:message bundle="view"
									key="transaction.purchaseorderno" /><FONT color="RED">*</FONT>:</div></strong></td>
							<td><html:text property="purchaseOrderNo" disabled="true" styleId="purchaseOrderNo" onkeypress="alphaNumWithoutSpace()"
								styleClass="box"  tabindex="9" size="18"
								maxlength="16" /></td>

						</TR>
						
						<!--  FOR Cheque Date and Credit Card Validity Date -->
						<TR>

							<td class="text pLeft15" nowrap><strong><font color="#000000"><div id="divchqdtnotreq" align="left" style="display:none;align:left"><bean:message bundle="view"
									key="transaction.chequedate" />:</div></font><div id="divchqdtreq" align="left" style="display:block;"><bean:message bundle="view"
									key="transaction.chequedate" /><FONT color="RED">*</FONT>:</div></td>
							<td><html:text property="chequeDate" styleId="chequeDate"
								styleClass="box" readonly="true" tabindex="6" size="15"
								maxlength="10" /><img src="<%=request.getContextPath()%>/images/cal.gif" id="chequeDateImg"
								name="chequeDateImg"
								onclick="fPopCalendar(chequeDate,chequeDate);return false"></td>
							<td class="text pLeft15" nowrap><strong><font color="#000000"><div id="divcarddtnotreq" align="left" style="display:none;align:left"><bean:message bundle="view"
									key="transaction.carddate" />:</div></font><div id="divcarddtreq" align="left" style="display:block;"><bean:message bundle="view"
									key="transaction.carddate" /><FONT color="RED">*</FONT>:</div></strong></td>
							<td><html:text property="ccvalidDate" styleId="ccvalidDate"
								styleClass="box" readonly="true" tabindex="7" size="15"
								maxlength="10" /><img src="<%=request.getContextPath()%>/images/cal.gif"
								id="creditValidDateImg" name="creditValidDateImg"
								onclick="fPopCalendar(ccvalidDate,ccvalidDate);return false"></td>

						</TR>
						
						<TR>
						
						<td class="text pLeft15" nowrap><strong><font color="#000000"><div id="divpurdtnotreq" align="left" style="display:none;align:left"><bean:message bundle="view"
									key="transaction.purchasedate" />:</div></font><div id="divpurdtreq" align="left" style="display:block;"><bean:message bundle="view"
									key="transaction.purchasedate" /><FONT color="RED">*</FONT>:</div></STRONG></TD>
							<td><html:text property="purchaseOrderDate" styleId="purchaseOrderDate"
								styleClass="box" readonly="true" tabindex="-1" size="15"
								maxlength="10" /><img src="<%=request.getContextPath()%>/images/cal.gif"
								id="purchaseOrderDateImg" tabindex="10" name="purchaseOrderDateImg"
								onclick="fPopCalendar(purchaseOrderDate,purchaseOrderDate);return false"></td>
						
						
						</TR>
						
						<TR>
								<TD class="text pLeft15" nowrap><STRONG><FONT
									color="#000000"><bean:message bundle="view"
									key="transaction.comment" /></FONT><FONT color="RED">*</FONT> :</STRONG></TD>
								<TD colspan="3"><html:textarea property="reviewComment" onfocus="setRemarkStatus()" onblur="ResetRemarkStatus()"
									styleId="reviewComment" tabindex="15" cols="60" rows="3"></html:textarea>
								</TD>
						</TR>
							
						</table>
						
						<table>
						<TR>
							<td class="text pLeft15"><font color="#000000">&nbsp;</font></td>
							<td colspan="3">
							<table width="140" border="0" cellspacing="0" cellpadding="5">
								<TR align="center">
									<TD width="70"><input class="submit" type="submit" tabindex="16"
										name="Submit" id="Submit" value="Submit" onclick="return validate();"></td>
									<td width="70"><INPUT class="submit" type="button" onclick="resetAll();"
										name="Submit2" value="Reset" tabindex="17"></TD>

								</TR>
							</table>
							</td>
						</tr>
						<tr>
							<td colspan="4" align="center">&nbsp;</td>
						</tr>
						<html:hidden property="methodName" styleId="methodName"
							value="createDistributorTransaction" />
						<html:hidden property="channelType" styleId="channelType"
							value="WEB" />
						<html:hidden property="reviewStatus" styleId="reviewStatus"
							value="P" />
						<html:hidden property="accountId" styleId="accountId" />
						
						<html:hidden styleId="submitStatus" property="submitStatus" />

					</table>
					</td>
				</TR>
				<TR>
					<TD><font color="RED"><strong> <html:errors bundle="error"
						property="errors.transaction" /> <html:errors bundle="view"
						property="message.transaction.insertion" /> </strong></font></TD>
				</TR>

			</table>
		</html:form></td>
	</tr>
	<div id="submitFormDiv" style="display:none;">
			<form name="submitForm" method="post" target="newWin">
			  <input type="hidden" name="accountCode">
			</form>	
		</div>
	<tr align="center" bgcolor="4477bb" valign="top"> 
          <td colspan="2" height="17" align="center"><jsp:include page="../common/footer.jsp" /></td>
		</tr>
	</table>



</BODY>
</html:html>
