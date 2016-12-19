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
<LINK href="<%=request.getContextPath()%>/theme/Master.css"
	rel="stylesheet" type="text/css">


<title>ViewStockEligibility</title>
<SCRIPT language="JavaScript"
	src="<%=request.getContextPath()%>/scripts/Cal.js"
	type="text/javascript"></SCRIPT>

<SCRIPT language="javascript" type="text/javascript"> 

//added by aman for extending stock report 27/06/13
function isDigit (c)
{
	//alert("c--"+c+"--");
	
   return ((parseInt(c) >= 0) && (parseInt(c) <= 10));
}

function isNumberVal(strString) {
   var strValidChars = "0123456789";
   var strChar;
   var blnResult = true;
 	var len=strString.length;
   
   if (strString.length == 0) {
   return false;
   }
   if (strString.length == 20){
   		len = strString.length-1;
   }else if (strString.length == 19){
   		len = strString.length;
   }
   
   //  test strString consists of valid characters listed above
   for (i = 0; i < len && blnResult == true; i++)
      {
	      strChar = strString.charAt(i);
    	  if (strValidChars.indexOf(strChar) == -1)
         {
        	 blnResult = false;
         }
      }
     return blnResult;
}

// This function checks if the given character is an alphabet
function isLetter (c)
{   
	return ( ((c >= "a") && (c <= "z")) || ((c >= "A") && (c <= "Z")) )
}
function isAlphanumericVal(s)
{
    var i;
    for (i = 0; i < s.length; i++) 
    {   
        var c = s.charAt(i);
       // alert("c::::"+c);
        //alert("isLetter::::"+isLetter(c));
       // alert("isDigit::::"+isDigit(c));
       // alert(! (isLetter(c) || isDigit(c) ));
        if (! (isLetter(c) || isDigit(c) ) )
        return false;
    }
    return true;
}
function checkOLM()
{
	var olmID = document.getElementById("olmId").value;
	
	if(olmID==null || olmID=="")
	{
		alert("Please Enter OLM ID.");
		document.getElementById("olmId").focus();
		return false;
	}
	
	
		var firstCharApp1 = isDigit(olmID.charAt(0));
			if(firstCharApp1==true ){
				alert("OLM ID Cannot Begin With A Numeric Character ");
				document.getElementById("olmId").focus();
				return false;
			}
			if(document.getElementById("olmId").value.length < 8)
			{
				alert("OLM ID Must Contain At Least 8 Characters.");
				document.getElementById("olmId").focus();
				return false;
			}
	
	
	
	
	var validolmId = isAlphanumericVal(olmID);
	//alert("olmid"+olmID);
	//alert(""+validolmId);
	
	if(validolmId==false)
	{
	    
		alert("Special Characters and Blank Spaces are not allowed in OLM ID");
		document.getElementById("olmId").focus();
		return false;
	}
	else
	{
		document.getElementById("methodName").value = "getStockEligibility";
		document.forms[0].submit();
	}
}

function doInitials()
{
	var acctLevel = document.getElementById("intUserAcctLevel").value;
	if(acctLevel!=6)
	{
		document.getElementById("searchOLMRow").style.display = "block";
		//document.getElementById("securityCol").style.display = "none";
		//document.getElementById("loanCol").style.display = "none";
		//document.getElementById("balanceCol").style.display = "none";
	}
	else
	{
		document.getElementById("distOlmId").value ="<%=session.getAttribute("distOLMID")%>";
		//alert(" document.getElementById(distOlmId).value  :"+document.getElementById("distOlmId").value);
		document.getElementById("searchOLMRow").style.display = "none";
	}
}

function exportToExcel()
{
	    var url = "ViewStockEligibility.do?methodName=exportToExcel";
		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();
}
//end of changes by aman
</SCRIPT>
</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" marginheight="0" onload="doInitials()">

<TABLE cellspacing="0" cellpadding="0" border="0" height="100%"
	valign="top">
	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include
			page="../common/dpheader.jsp" flush="" /></TD>
	</TR>
	<TR valign="top">
		<TD width="167" align="left" bgcolor="b4d2e7" valign="top"
			height="100%"><jsp:include page="../common/leftHeader.jsp"
			flush="" /></TD>

		<TD valign="top" width="100%" height="100%"><html:form
			action="/ViewStockEligibility" method="post"
			type="com.ibm.dp.beans.ViewStockEligibilityBean"
			name="ViewStockEligibilityBean" scope="request">
			<html:hidden property="methodName" />
			<html:hidden property="intUserAcctLevel" styleId="intUserAcctLevel" />
			<html:hidden property="distOlmId" styleId="distOlmId" />

			<TABLE border="0" cellpadding="5" cellspacing="0" class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					<IMG
						src="<%=request.getContextPath()%>/images/viewStockEligibility.jpg"
						width="505" height="22" alt=""></TD>
				</TR>
			</TABLE>
			<TABLE align="center" class="mLeft5" width="50%">

				<!-- added by aman BFR 39 27/6/13 -->
				<logic:notEmpty property="strSuccessMsg" name="ViewStockEligibilityBean">
					<tr>
						<td colspan='3'>
							<strong>
								<font color="red">
									<bean:write property="strSuccessMsg"  name="ViewStockEligibilityBean"/>
								</font>
							</strong>
						</td>
					</tr>
				</logic:notEmpty>
				<tr align="center" id="searchOLMRow" style='display:none'>
					<td id="olm" class="txt" align="left">
						<strong>Enter OLM ID</strong>
					</td>
					<td align="left" id="olmBox">
						<html:text	name="ViewStockEligibilityBean" property="olmId" styleId="olmId" />
					</td>
					<td align="left" id="olmBox">
						<input type=button value="Go" onclick="return checkOLM();">
					</td>
						
				</tr>
				<!-- end of changes -->
				
				<logic:notEmpty property="viewStockEligibilityList" name="ViewStockEligibilityBean">
				
					<tr id="securityCol" align="center" style='display:inline'>
						<td class="txt" align="left"><strong>Security</strong></td>
						<td align="left"><html:text name="ViewStockEligibilityBean"
							property="security" styleId="security" readonly="true" /></td>
							
						<td align="left" >
							&nbsp;
						</td>
					</tr>
					<tr id="loanCol" align="center" style='display:inline'>
						<td class="txt" align="left"><strong>Loan</strong></td>
						<td align="left"><html:text name="ViewStockEligibilityBean"
							property="loan" styleId="loan" readonly="true" /></td>
							
						<td align="left" >
							&nbsp;
						</td>
					</tr>
					<tr id="balanceCol" align="center" style='display:inline'>
						<td class="txt" align="left"><strong>Balance</strong></td>
						<td align="left"><html:text name="ViewStockEligibilityBean"
							property="balance" styleId="balance" readonly="true" /></td>
						
						
						
						<td align="left" >
							&nbsp;
						</td>
					</tr>
				</logic:notEmpty>
			</TABLE>

			<TABLE align="center" class="mLeft5" width="100%">

				<logic:notEmpty property="viewStockEligibilityList"
					name="ViewStockEligibilityBean">

					<TR align="center" bgcolor="#CD0504"
						style="color: #ffffff !important;">
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="stock.ProdName" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5">Available Stock</SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5">Min Days</SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="stock.MinStock" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5">Max Days</SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5"><bean:message
							bundle="view" key="stock.MaxStock" /></SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5">Max PO <br>
						Product Qty</SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5">Product Wise Security</SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5">Balance Security Qty</SPAN></TD>
						<TD align="center" bgcolor="#CD0504"><SPAN
							class="white10heading mLeft5 mTop5">Eligiblity</SPAN></TD>
					</TR>

					<%-- comment --%>
					<logic:iterate id="account" property="viewStockEligibilityList"
						name="ViewStockEligibilityBean" indexId="i">
						<TR bgcolor="#FCE8E6">
							<TD class="text" align="center"><bean:write name="account"
								property="productName" /></TD>
							<TD class="text" align="center"><bean:write name="account"
								property="availableStock" /></TD>
							<TD class="text" align="center"><bean:write name="account"
								property="minDays" /></TD>
							<TD class="text" align="center"><bean:write name="account"
								property="minStock" /></TD>
							<TD class="text" align="center"><bean:write name="account"
								property="maxDays" /></TD>
							<TD class="text" align="center"><bean:write name="account"
								property="maxStock" /></TD>
							<TD class="text" align="center"><bean:write name="account"
								property="maxPoProductQty" /></TD>

							<TD class="text" align="center"><bean:write name="account"
								property="productWiseSecurity" /></TD>
							<TD class="text" align="center"><bean:write name="account"
								property="balanceSecurityQty" /></TD>
							<TD class="text" align="center"><bean:write name="account"
								property="eligibilty" /></TD>

						</TR>
						
						
					</logic:iterate>
					
			 	</logic:notEmpty>  
			</TABLE>
			<Table width='100%'>
			<logic:notEmpty property="viewStockEligibilityList"
					name="ViewStockEligibilityBean">
			
				<tr>
				 	<td width='100%' align="center">
						<input type='button' value='Export to Excel' onclick='exportToExcel()'>
					</td>
					
				</tr>
			</logic:notEmpty> 
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