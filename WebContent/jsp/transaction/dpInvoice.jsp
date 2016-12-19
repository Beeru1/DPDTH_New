<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@page import="com.ibm.dp.dto.DpInvoiceDto" %>
<%@page isELIgnored="false" %>
<bean:define id="invForm" name="InvoiceForm" type="com.ibm.dp.beans.InvoiceForm"></bean:define>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">

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
function populatebillCell(text){

	//var obj=e.target;
	document.getElementById("billCell").innerHTML=text.value;
	return true;
}
	//var obj=e.target;
function acceptInvoice(){
  //   alert(billNo);
  	 if(document.forms[0].invoiceNo.value == null || document.forms[0].invoiceNo.value==""){
  	  alert("Please provide invoice No");
  	  return false;
  	  }
  	 //window.print();
  	  var partnerRem=document.getElementById("remarks").value;
  	 if( /[^a-zA-Z0-9_]/.test( document.forms[0].invoiceNo.value ) ) {
       alert("Invoice number should be alphanumeric with underscores only");
       return false;
    } 
  	 var partnerRem=document.getElementById("remarks").value;
     document.forms[0].methodName.value="acceptInvoice";
     document.getElementById("partnerRem").value=partnerRem;
    // document.forms[0].billNo.value=billNo;
     document.forms[0].submit();
     return true;
}

function populatebillCell(text){

	//var obj=e.target;
	document.getElementById("billCell").innerHTML=text.value;
	return true;
}

 function download(){
 window.location.href = "/ViewPartnerInvoice.do?methodName=downloadInvoice";
 }
function rejectInvoice(){
     document.forms[0].methodName.value="rejectInvoice";
     var partnerRem=document.getElementById("remarks").value;
     
     if(partnerRem ==null || partnerRem == "")
       {
       alert("Please provide Remarks for rejection before Rejecting the invoice");
       return false;
       }
    // document.forms[0].billNo.value=billNo;
     document.getElementById("partnerRem").value=partnerRem;
     document.forms[0].submit();
     return true;
 }
 
 function download(){
 window.location.href = "/ViewPartnerInvoice.do?methodName=downloadInvoice";
 }
 
</script>
<title>Insert title here</title>
</head>
<body onload="disableLink();">

<logic:present name="invoice">
<html:form name="InvoiceForm" action="/ViewPartnerInvoice.do" type="com.ibm.dp.beans.InvoiceForm"
 method="POST">
<html:hidden property="methodName"/>
<html:hidden name="invoice" property="bilNo"/>
<html:hidden property="invoiceMessage"/>
<html:hidden property="partnerRem" styleId="partnerRem"/>
<table border="1" align="center" width="66%"
	style="border-collapse: collapse; border: 1px;border-bottom-style:outset;border-right-style=outset;">
	<tr>
		<th colspan="2" style="color:black"><bean:write  name="partner" property="accountName"/></th>
	</tr>
	<tr>
		<td colspan="2" align="center" style="color:black"><b>INVOICE</b></td>
	</tr>


	<tr>
		<td width="50%">

		<table width="100%" border="1" align="left" display="block"
			style="border-collapse: collapse; border: 1px;">
			<tr>
				<th colspan="2" style="color:black">NAME OF THE CUSTOMER</th>
			<tr>
			<tr>
				<td style="color:black">Bharti Telemedia Limited</td>
			</tr>
			
			
			<tr>
			<td style="color:black"><bean:write name="address"/></td>
			</tr>
			
			<!--<tr>
				<td  style="color:black"></td>
			</tr>
			<tr>
				<td style="color:black">Haryana-122001</td>
			</tr>
				
			--><!--<tr>
				<td style="color:black">Address</td>
			</tr>
			<tr>
				<td>&nbsp</td>
			</tr>
			-->
			<tr>
				<!--<td>&nbsp</td>
		--></table>

		</td>

		<td width="50%">
		<table width="100%" align="left" border="1" display="block"
			style="border-collapse: collapse; border: 1px;">
			<tr>
				<th colspan="2" style="color:black">SERVICE FRACNHISEE NAME & ADDRESS</th>
			<tr>
			<tr style="text-align:center;">
				<td><bean:write name="partner" property="accountAddress"/></td>
			</tr>
			<tr style="text-align:center;">
				<td><bean:write name="partner"  property="accAddress2"/></td>
			</tr>
			<tr>
				<td>&nbsp</td>
			</tr>
			<tr>
				<td>&nbsp</td>
		</table>


		</td>
	</tr>








	<tr>
		<td width="50%">


		<table width="100%" border="1" align="left" display="block"
			style="border-collapse: collapse; border: 1px; font-color:black;font:bold;">
			<tr>
				<th colspan="2" style="color:black">Bank Details of Service Providers for
				E-Payments</th>
			<tr>
			<tr>
				<td width="50%">Sales Code.</td>
				<td>&nbsp</td>
			</tr>
			<tr>
				<td width="50%">Vendor Code.</td>
				<td>&nbsp</td>
			</tr>
			<tr>
				<td width="50%">Bank Details</td>
				<td>&nbsp</td>
			</tr>
			<tr>
				<td width="50%">Name Of the Bank</td>
				<td>&nbsp</td>
			</tr>
			<tr>
				<td width="50%">Branch Name</td>
				<td>&nbsp</td>
			</tr>
			<tr>
				<td width="50%">IFSC Code</td>
				<td>&nbsp</td>
			</tr>
			<tr>
				<td width="50%">Account Number</td>
				<td>&nbsp</td>
			</tr>
		</table>
		</td>

		<td width="50%">


		<table width="100%" border="1" align="left" display="block"
			style="border-collapse: collapse; border: 1px;">
			<tr>
				<th colspan="3">&nbsp</th>
			<tr>
			<tr>
				<td width="50%" style="font-color:black;font:bold">Bill No.</td>
					<logic:present name="Finlogin">
 					<logic:equal name="Finlogin" value="Y">
						<td colspan="2" id="billCell" style="text-align:center"><bean:write name="invoice" property="invNo"/></td>
					</logic:equal>
				</logic:present >
				<logic:notPresent name="Finlogin">
					<td colspan="2" id="billCell" style="text-align:center">&nbsp</td>
				</logic:notPresent>

			</tr>
			<tr>
				<td width="30%" style="font-color:black;font:bold">Bill Date</td>
				<td width="25%">&nbsp</td>
				<td width="25%"><bean:write name="invoice" property="bilDt"/></td>
			</tr>
			<tr>
				<td width="50%" style="font-color:black;font:bold">Period</td>
				<td colspan="2" style="text-align:center;"><bean:write name="invoice" property="monthFr"/></td>
			</tr>
			<tr >
				<td width="50%" style="font-color:black;font:bold">Tin No.</td>
				<logic:present name="partner" property="tinNo" >
				 	<td colspan="2" style="text-align:center;"><bean:write name="partner" property="tinNo"/></td>
				</logic:present >
				<logic:notPresent name="partner" property="tinNo" >
					<td colspan="2">&nbsp</td>
				</logic:notPresent>
			</tr>
			<tr >
				<td width="50%" style="font-color:black;font:bold">PAN Number</td>
				<logic:present name="partner" property="panNo" >
				 	<td colspan="2" style="text-align:center;" ><bean:write name="partner" property="panNo"/></td>
				</logic:present>
				<logic:notPresent name="partner" property="panNo" >
					<td colspan="2">&nbsp</td>
				</logic:notPresent>
			</tr>
			<tr>
				<td width="50%" style="font-color:black;font:bold">Service Tax Regn. No.</td>
				<td colspan="2" style="text-align:center;"><bean:write name="partner" property="servcTxNo"/></td>
			</tr>
			<tr>
				<td width="30%" style="font-color:black;font:bold">Service Tax Category</td>
				<td width="25%">&nbsp</td>
				<td width="25%">&nbsp
			</tr>
		</table>

		</td>
	</tr>




	<tr>
		<td colspan="2">&nbsp</td>
	</tr>



	<tr>
		<td width="50%">

		<table width="100%" border="1" align="left" display="block"
			style="border-collapse: collapse; border: 1px;">
			<tr>
				<th colspan="2" style="color:black">COMPONENT</th>
			</tr>
			<tr>
				<td style="font-color:black;font:bold">CPE Recovery</td>
			</tr>
			<tr>
				<td style="font-color:black;font:bold">SR Reward</td>
			</tr>
			<tr>
				<td style="font-color:black;font:bold">Retention</td>
			</tr>
			<tr>
				<td style="font-color:black;font:bold">PTR</td>
			</tr>
			<tr>
				<td style="font-color:black;font:bold">Others</td>
			</tr>
			<tr>
				<td style="font-color:black;font:bold">Others1</td>
			</tr>
			<tr>
				<td>&nbsp</td>
			</tr>
			<tr>
				<td>&nbsp</td>
			</tr>
			<tr>
				<td><b>Total</b></td>
			<tr>
		</table>


		</td>

		<td width="50%">

		<table width="100%" border="1" align="left" display="block"
			style="border-collapse: collapse; border: 1px;">
			<tr>
				<th colspan="1" style="color:black">NOs</th>
				<th colspan="1" style="color:black">RATE</th>
				<th colspan="1" style="color:black">AMOUNT</th>
			</tr>
			<!-- CPE RECOVERY -->
			<tr style="text-align:right;">
				<td>&nbsp</td>
				<td>&nbsp</td>
				<td><bean:write name="invoice" property="cperecAmt"/></td>
			</tr>
			<!-- SR REWARD -->
			<tr style="text-align:right;">
				<td>&nbsp</td>
				<td>&nbsp</td>
				<td><bean:write name="invoice" property="srrwdAmt"/></td>
			</tr>
			<!-- PTR -->
			<tr style="text-align:right;">
				<td>&nbsp</td>
				<td>&nbsp</td>
				<td><bean:write name="invoice" property="retAmt"/></td>
			</tr>
			<tr style="text-align:right;">
				<td>&nbsp</td>
				<td>&nbsp</td>
				<td><bean:write name="invoice" property="othAmt"/></td>
			</tr>
			<!-- Others -->
			<tr style="text-align:right;">
				<td>&nbsp</td>
				<td>&nbsp</td>
				<td><bean:write name="invoice" property="others"/></td>
			</tr>
			<tr style="text-align:right;">
				<td>&nbsp</td>
				<td>&nbsp</td>
				<td><bean:write name="invoice" property="others1"/></td>
			</tr>
			<tr>
				<td>&nbsp</td>
				<td>&nbsp</td>
				<td>&nbsp</td>
			</tr>
			<tr>
				<td>&nbsp</td>
				<td>&nbsp</td>
				
				<td>&nbsp</td>
				
			</tr>
			<tr>
				<td>&nbsp</td>
				<td>&nbsp</td>
				<!-- TOTAL -->
				<td style="text-align:right;"><bean:write name="invoice" property="total"/></td>
			</tr>
		</table>

		</td>

	</tr>



	<tr>
		<td colspan="2">


		<table width="100%" border="1" display="block"
			style="border-collapse: collapse; border: 1px;">
			<tr>
				<td width="53%">

				<table width="100%" border="1" display="block"
					style="border-collapse: collapse; border: 1px;">
					<tr>
						<td style="font-color:black;font:bold">Service Tax</td>
					</tr>
					<tr>
						<td style="font-color:black;font:bold">Swachcha Bharat Cess</td>
					</tr>
					
					<tr>
						<td style="font-color:black;font:bold">Krishi Kalyan Cess</td>
					</tr>
					
					<!--<tr>
						<td>Higher Educational Cess</td>
					</tr>
					--><tr>
						<td style="font-color:black;font:bold">GRAND TOTAL</td>
					</tr>
				</table>


				</td>


				<td width="34%">

				<table width="100%" border="1" display="block"
					style="border-collapse: collapse; border: 1px;border-right-style=outset;">
					<!-- TAXES -->
					<tr>
						<td width="39%" style="text-align:right"><bean:write name="rates" property="serviceTx"/>%</td>
						<td style="text-align:right;"><bean:write name="invoice" property="srvTxAmt"/></td>
					</tr>
					<tr>
						<td width="39%" style="text-align:right"><bean:write name="rates" property="eduCess"/>%</td>
						<td style="text-align:right;"><bean:write name="invoice" property="eduCesAmt"/></td>
					</tr>
					
					<tr>
						<td width="39%" style="text-align:right"><bean:write name="rates" property="kkcess"/>%</td>
						<td style="text-align:right"><bean:write name="invoice" property="kkcamt"/></td>
					</tr>
					
					
					
					<!--
					<tr>
						<td width="39%" style="text-align:right"><bean:write name="rates" property="hdeuCess"/>%</td>
						<td style="text-align:right"><bean:write name="invoice" property="heduCesAmt"/></td>
					</tr>
					-->
					<tr>
						<td>&nbsp</td>
						<!-- Grand Total -->
						<td style="text-align:right;"><bean:write name="invoice" property="grndTotal"/></td>
					</tr>
				</table>

				</td>
			</tr>

		</table>



		</td>
	</tr>

	<tr>
		<td colspan="2"style="font-color:black;font:bold"><b>Amount In words(Rs.) <span style="text-align:center;"><bean:write name="invoice" property="amountInWords"/></span></b></td>
	<tr>
</table>
<br>
<br>
<logic:present name="Finlogin">
 <logic:equal name="Finlogin" value="Y">
  <center><LABEL>Remarks:</LABEL><html:textarea name="invoice" property="remarks" styleId="remarks" disabled="true"></html:textarea></center>
 </logic:equal>
</logic:present>

<logic:notPresent name="Finlogin" >
<center><LABEL>Remarks:</LABEL><html:textarea  name="invoice" property="remarks" styleId="remarks"></html:textarea></center>
<br>
<br>
<center><LABEL>Invoice No:</LABEL><html:text property="invoiceNo" name="invoiceNo" value="" onkeyup="populatebillCell(this)">Invoice No:</html:text></center>

<br>

	<logic:notPresent name="InvoiceForm" property="invoiceMessage">
	
		<center><html:button property="Accept&Print" onclick="acceptInvoice()">Accept</html:button>
		<html:button property="Reject" onclick="rejectInvoice()">Reject</html:button></center>
	</logic:notPresent>
	
	<logic:present name="InvoiceForm" property="invoiceMessge">
		<logic:equal property="invoiceMessge" value="">
			<center><html:button property="Accept&Print" onclick="acceptInvoice()">Accept</html:button>
			<html:button property="Reject" onclick="rejectInvoice()">Reject</html:button></center>
		</logic:equal>
		
		<logic:equal property="invoiceMessge" value="success">
		  <center><B>Processed the BillNo <bean:write name="invoice" property="bilNo"/></B></center>
		</logic:equal>
		
	  <logic:equal property="invoiceMessge" value="errorInDpdate">
		  <center><B>Error in processing the BillNo <bean:write name="invoice" property="bilNo"/></B></center>
		</logic:equal>
	</logic:present>
</logic:notPresent>
</html:form>

</logic:present>

<logic:present name="InvoiceForm" property="invoiceMessage">
	<logic:equal name="InvoiceForm" property="invoiceMessage" value="success">
		 <font color="RED"> <B>Processed the Bill No  <bean:write name="InvoiceForm" property="bilNo"/></B></font>
		 
	</logic:equal>
		
	<logic:equal name="InvoiceForm" property="invoiceMessage" value="errorInDpdate">
		<font color="RED"><B>Error in processing the BillNo <bean:write name="InvoiceForm" property="bilNo"/></B></font>
	</logic:equal>
</logic:present>


<%
	String fileName = (String) request.getAttribute("fileName");
	 %>	
<script>
	var fileName= '<%=fileName%>';
 	if(fileName!=null && fileName != "null" && fileName!="" ){
		
		var url="/DPDTH/ViewPartnerInvoice.do?methodName=downloadInvoice&fileName="+fileName;
	    window.open(url,"Invoice","width=700,height=600,scrollbars=yes,toolbar=no");				
  	}
</script>

</body>
</html>