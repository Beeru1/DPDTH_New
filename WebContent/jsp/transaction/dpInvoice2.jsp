<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
           <%@page isELIgnored="false" %>
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


function acceptInvoice(){
  //   alert(billNo);
  	 if(document.forms[0].invoiceNo.value == null || document.forms[0].invoiceNo.value==""){
  	  alert("Please provide invoice No");
  	  return false;
  	  }
  	 //window.print();
  	 if( /[^a-zA-Z0-9_]/.test( document.forms[0].invoiceNo.value ) ) {
       alert("Invoice number should be alphanumeric with underscores only");
       return false;
		} 
  	  var partnerRem=document.getElementById("remarks").value;
  	  document.getElementById("partnerRem").value=partnerRem;
     document.forms[0].methodName.value="acceptInvoiceSTB";
    // document.forms[0].billNo.value=billNo;
     document.forms[0].submit();
     return true;
}

function checkValidinvNo(event){
		
}



function rejectInvoice(){
     document.forms[0].methodName.value="rejectInvoiceSTB";
     var partnerRem=document.getElementById("remarks").value;
     
     if(partnerRem ==null || partnerRem == "")
       {
       alert("Please provide Remarks for rejection before Rejecting the invoice");
       return false;
       }
    // document.forms[0].billNo.value=billNo;
     document.getElementById("partnerRem").value=partnerRem;
    // document.forms[0].billNo.value=billNo;
     document.forms[0].submit();
     return true;
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
		<th colspan="2" style="color:black;"><bean:write name="invoice" property="partnerNm"/></th>
	</tr>
	<tr>
		<td colspan="2" align="center" style="color:black;"><b>INVOICE</b></td>
	</tr>


	<tr>
		<td width="53%">

		<table width="100%" border="1" align="left" display="block"
			style="border-collapse: collapse; border: 1px;">
			<tr>
				<th colspan="2" height="41" style="color:black;">NAME OF THE CUSTOMER</th><tr>
			<tr>
				<td style="color:black;">Bharti Telemedia Limited</td>
			</tr>
			
			<tr>
			<td style="color:black"><bean:write name="address"/></td>
			</tr>
			
			<!--<tr>
				<td style="color:black">Plot No 16, 6th Floor, Tower A, Udyog Vihar, Phase IV, Gurgaon, </td>
			</tr>
			<tr>
				<td style="color:black">Haryana-122001</td>
			</tr>
			--><tr>
				<!--<td>&nbsp</td>
		--></table>

		</td>

		<td width="47%">
		<table width="100%" align="left" border="1" display="block"
			style="border-collapse: collapse; border: 1px;">
			<tr>
				<th colspan="2" height="40" style="color:black;">SERVICE FRACNHISEE NAME & ADDRESS</th><tr>
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
		<td width="53%">


		<table width="100%" border="1" align="left" display="block"
			style="border-collapse: collapse; border: 1px;" style="font:bold;">
			<tr>
				<th colspan="2" style="color:black">Bank Details of Service Providers for E-Payments</th>
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

		<td width="47%">


		<table width="100%" border="1" align="left" display="block"
			style="border-collapse: collapse; border: 1px;">
			<tr>
				<th colspan="3">&nbsp</th>
			<tr>
			<tr>
				<td width="50%" style="font:bold;">Bill No.</td>
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
				<td width="30%" style="font:bold;">Bill Date</td>
				<td width="25%">&nbsp</td>
				<td width="25%"><bean:write name="invoice" property="bilDt"/></td>
			</tr>
			<tr>
				<td width="50%" style="font:bold;">Period</td>
				<td colspan="2" style="text-align:center;"><bean:write name="invoice" property="monthFr"/></td>
			</tr>
			<tr>
				<td width="50%" style="font:bold;">Tin No.</td>
				<td colspan="2" style="text-align:center;"><bean:write name="partner" property="tinNo"/></td>
			</tr>
			<tr>
				<td width="50%" style="font:bold;">PAN Number</td>
				<td colspan="2" style="text-align:center;"><bean:write name="partner" property="panNo"/></td>
			</tr>
			<tr>
				<td width="50%" style="font:bold;">Service Tax Regn. No.</td>
				<td colspan="2" style="text-align:center;"><bean:write name="partner" property="servcTxNo"/></td>
			</tr>
			<tr>
				<td width="30%" style="font:bold;">Service Tax Category</td>
				<td width="25%">&nbsp</td>
				<td width="25%">&nbsp
			</tr>
		</table>

		</td>
	</tr>




	<tr>
		<td colspan="2" height="24">&nbsp</td>
	</tr>



	<tr>
		<td width="53%" height="564">

		<table width="100%" border="1" align="left" display="block"
			style="border-collapse: collapse; border: 1px;" height="564" style="font:bold;">
			<tr height="6%">
				<th colspan="1" style="color:black;">COMPONENT</th>
			</tr>
			<!--<tr>
				<td>Installation Call Standard STB (Tier-1/2/3)</td>
			</tr>
			--><tr>
				<td>Installation Call Standard STB/HD (Tier-1)</td>
			</tr>
			<tr>
				<td>Installation Call Standard STB/HD (Tier-2)</td>
			</tr>
			<tr>
				<td>Installation Call Standard STB/HD (Tier-3)</td>
			</tr>

			<tr>
				<td>Installation Call HD DVR STB (Tier-1)</td>
			</tr>
			<tr>
				<td>Installation Call HD DVR STB (Tier-2)</td>
			</tr>
			<tr>
				<td>Installation Call HD DVR STB (Tier-3)</td>
			</tr>
			<tr>
				<td>Installation Call Multi Connection HD DVR STB (Tier-1/2/3)
				</td>
			</tr>
			<tr>
		 		<td>Installation Call Multi Connection STB (Tier-1/2/3)</td>
			</tr>
			<tr>
				<td>Upgradation to HD</td>
			</tr>
			<tr>
				<td>Upgradation to DVR</td>
			</tr>
			<tr>
				<td>Field Repairs and Relocation</td>
			</tr>
			<tr>
				<td>Weak Geography Payout</td>
			</tr>
			<tr>
				<td>Service Request payable after One year</td>
			</tr>
			<tr>
				<td>Additional Installation Payout Hilly Area</td>
			</tr>
			<tr>
				<td>ARP</td>
			</tr>
			<tr>
				<td>Others</td>
			</tr>
			<tr>
				<td>Others1</td>
			</tr>
			<tr height="6%">
				<td>Finance Remarks</td>
			</tr>
			<tr>
				<td height="6%"><b>Total</b></td>
			<tr>
		</table>


		</td>

		<td width="410" height="564">
		<table width="100%" border="1" align="left" display="block"
			style="border-collapse: collapse; border: 1px;" height="564">
			<tr height="6%">
				<th colspan="1" width="130" style="color:black;">NOs</th>
				<th colspan="1" width="126" style="color:black;">RATE</th>
				<th colspan="1" width="129" style="color:black;">AMOUNT</th>
			</tr>
			<!--<tr style="text-align:right;">  Standard stb 
				<td width="130">&nbsp</td>
				<logic:present name="invoice" property="tier">
					<logic:equal name="invoice" property="tier" value="1">
						<td width="126"><bean:write name="rates" property="stbRateT1"/></td>
					</logic:equal>
					<logic:equal name="invoice" property="tier" value="2">
						<td width="126"><bean:write name="rates" property="stbRateT2"/></td>
					</logic:equal>
					<logic:equal name="invoice" property="tier" value="3">
						<td width="126"><bean:write name="rates" property="stbRateT3"/></td>
					</logic:equal>
				</logic:present>
				<logic:notPresent name="invoice" property="tier">
					 <td width="126">&nbsp</td>
				</logic:notPresent>
				<td width="129">&nbsp</td>
				
			</tr>
			--><tr style="text-align:right;"><!-- HD install -->
				<td width="130">${invoice.hdextT1}</td>
				<td width="126"><bean:write name="rates" property="hdRateT1"/></td>
				<td width="129">${(invoice.hdextT1)*rates.hdRateT1}</td>
			</tr>
			<tr style="text-align:right;"><!-- HD install -->
				<td width="130">${invoice.hdextT2}</td>
				<td width="126"><bean:write name="rates" property="hdRateT2"/></td>
				<td width="129">${(invoice.hdextT2)*rates.hdRateT2}</td>
			</tr>
			<tr style="text-align:right;"><!-- HD install -->
				<td width="130">${invoice.hdextT3}</td>
				<td width="126"><bean:write name="rates" property="hdRateT3"/></td>
				<td width="129">${(invoice.hdextT3)*rates.hdRateT3}</td>
			</tr>
			<tr style="text-align:right;"><!-- install dvr stb -->
				<td width="130">${invoice.dvrT1}</td>
				<td width="126"><bean:write name="rates" property="dvrRateT1"/></td>
				<td width="129">${(invoice.dvrT1)*rates.dvrRateT1}</td>
			</tr>
			<tr style="text-align:right;"><!-- install dvr stb -->
				<td width="130">${invoice.dvrT2}</td>
				<td width="126"><bean:write name="rates" property="dvrRateT2"/></td>
				<td width="129">${(invoice.dvrT2)*rates.dvrRateT2}</td>
			</tr>
			<tr style="text-align:right;"><!-- install dvr stb -->
				<td width="130">${invoice.dvrT3}</td>
				<td width="126"><bean:write name="rates" property="dvrRateT3"/></td>
				<td width="129">${(invoice.dvrT3)*rates.dvrRateT3}</td>
			</tr>
			<tr style="text-align:right;"><!-- multiconn hd dvr -->
				<td width="130">${invoice.multidvrT1+invoice.multidvrT2+invoice.multidvrT3}</td>
				
				<logic:present name="invoice" property="tier">
					<logic:equal name="invoice" property="tier" value="1">
						<td width="126"><bean:write name="rates" property="multiDvrT1"/></td>
					</logic:equal>
					<logic:equal name="invoice" property="tier" value="2">
						<td width="126"><bean:write name="rates" property="multiDvrT2"/></td>
					</logic:equal>
					<logic:equal name="invoice" property="tier" value="3">
						<td width="126"><bean:write name="rates" property="multiDvrT3"/></td>
					</logic:equal>
				</logic:present>
				<logic:notPresent name="invoice" property="tier">
					 <td width="126">&nbsp</td>
				</logic:notPresent>
			
					
			<logic:notPresent name="invoice" property="tier">
				<td width="129">&nbsp</td>
			</logic:notPresent>
			<logic:present name="invoice" property="tier">
				<logic:equal name="invoice" property="tier" value="1">
							<td width="129">${(invoice.multidvrT1+invoice.multidvrT2+invoice.multidvrT3)*rates.multiDvrT1}</td>
				</logic:equal>
				
				<logic:equal name="invoice" property="tier" value="2">
							<td width="129">${(invoice.multidvrT1+invoice.multidvrT2+invoice.multidvrT3)*rates.multiDvrT2}</td>
				</logic:equal>
				
				<logic:equal name="invoice" property="tier" value="3">
							<td width="129">${(invoice.multidvrT1+invoice.multidvrT2+invoice.multidvrT3)*rates.multiDvrT3}</td>
			</logic:equal>
			</logic:present>
			</tr>
			<tr style="text-align:right;"><!-- STB multi hd  -->
				<td width="130">${invoice.multiT1 + invoice.multiT2 + invoice.multiT3}</td>
				
				<logic:present name="invoice" property="tier">
					<logic:equal name="invoice" property="tier" value="1">
						<td width="126"><bean:write name="rates" property="stbRateT1"/></td>
					</logic:equal>
					<logic:equal name="invoice" property="tier" value="2">
						<td width="126"><bean:write name="rates" property="stbRateT2"/></td>
					</logic:equal>
					<logic:equal name="invoice" property="tier" value="3">
						<td width="126"><bean:write name="rates" property="stbRateT3"/></td>
					</logic:equal>
				</logic:present>
				<logic:notPresent name="invoice" property="tier">
					 <td width="126">&nbsp</td>
				</logic:notPresent>
			
					
			<logic:notPresent name="invoice" property="tier">
				<td width="129">&nbsp</td>
			</logic:notPresent>
			<logic:present name="invoice" property="tier">
				<logic:equal name="invoice" property="tier" value="1">
							<td width="129">${(invoice.multiT1 + invoice.multiT2 + invoice.multiT3)*rates.stbRateT1}</td>
				</logic:equal>
				
				<logic:equal name="invoice" property="tier" value="2">
							<td width="129">${(invoice.multiT1 + invoice.multiT2 + invoice.multiT3)*rates.stbRateT2}</td>
				</logic:equal>
				
				<logic:equal name="invoice" property="tier" value="3">
							<td width="129">${(invoice.multiT1 + invoice.multiT2 + invoice.multiT3)*rates.stbRateT3}</td>
			</logic:equal>
			</logic:present>
			</tr>
			<tr style="text-align:right;"><!-- upgrade hd -->
				<td width="130"><bean:write name="invoice" property="hdplusExt"/></td>
				<td width="126"><bean:write name="rates" property="upgradeHd"/></td>
				<td width="129">${invoice.hdplusExt * rates.upgradeHd}</td>
			</tr>
			<tr style="text-align:right;"><!-- upgrade dvr -->
				<td width="130"><bean:write name="invoice" property="dvr"/></td>
				<td width="126"><bean:write name="rates" property="upgradeDvr"/></td>
				<td width="129">${invoice.dvr * rates.upgradeDvr}</td>
			</tr>
			<tr style="text-align:right;"><!-- one yr toward warranty -->
				<td width="130">&nbsp</td>
				<td width="126">&nbsp</td>
				<td width="129"><bean:write name="invoice" property="fieldreprelAmt"/></td>
			</tr>
			<tr style="text-align:right;"><!-- Weak Geo -->
				<td width="130">&nbsp</td>
				<td width="126">&nbsp</td>
				<td width="129"><bean:write name="invoice" property="weakGeo"/></td>
			</tr>
			<tr style="text-align:right;"><!-- SR after one yr  -->
				<td width="130"><bean:write name="invoice" property="oneyrCount"/></td>
				<td width="126"><bean:write name="rates" property="hdeuCess"/></td>
				<td width="129"><bean:write name="invoice" property="oneyrAmt"/></td>
			</tr>
			<tr style="text-align:right;"><!-- Hilly Area -->
				<td width="130">&nbsp</td>
				<td width="126">&nbsp</td>
				<td width="129"><bean:write name="invoice" property="hillyArea"/></td>
			</tr>
			<tr style="text-align:right;"><!-- ARP -->
				<td width="130">&nbsp</td>
				<td width="126">&nbsp</td>
				<td width="129"><bean:write name="invoice" property="arpPayout"/></td>
			</tr>
			<tr style="text-align:right;"><!-- others -->
				<td width="130">&nbsp</td>
				<td width="126">&nbsp</td>
				<td width="129"><bean:write name="invoice" property="others"/></td>
			</tr>
			<tr style="text-align:right;">
				<td width="130">&nbsp</td>
				<td width="126">&nbsp</td>
				<td width="129"><bean:write name="invoice" property="others1"/></td>
			</tr>
			<logic:equal name="invoice" property="finRemarks" value="">
			<tr style="text-align:right;">
				<td colspan="3" width="385">&nbsp</td>
			</tr>
			</logic:equal>
			<logic:notEqual name="invoice" property="finRemarks" value="">
			<tr style="text-align:center;" height="6%">
				<!--<td width="130">&nbsp</td>
				<td width="126">&nbsp</td>
				--><td colspan="3" width="385"><bean:write name="invoice" property="finRemarks"/></td>
			</tr>
			</logic:notEqual>
			<tr style="text-align:right;" height="6%"><!-- Total -->
				<td width="130">&nbsp</td>
				<td width="126">&nbsp</td>
				<td width="129"><bean:write name="invoice" property="totbasAmt"/></td>
			</tr>
		</table>
		</td>

	</tr>



	<tr>
		<td colspan="2">


		<table width="100%" border="1" display="block"
			style="border-collapse: collapse; border: 1px;">
			<tr>
				<td width="69%">

				<table width="100%" border="1" display="block"
					style="border-collapse: collapse; border: 1px;">
					<tr>
						<td style="font:bold;">Service Tax</td>
					</tr>
					<tr>
						<td style="font:bold;;">Swachcha Bharat Cess</td>
					</tr>
					
					<tr>
						<td style="font:bold;;">Krishi Kalyan Cess</td>
					</tr>
					
					<!--<tr>
						<td>Higher Educational Cess</td>
					</tr>
					--><tr>
						<td style="font:bold;">GRAND TOTAL</td>
					</tr>
				</table>


				</td>


				<td width="31%">

				<table width="100%" border="1" display="block"
					style="border-collapse: collapse; border: 1px;border-right-style=outset;">
					<tr style="text-align:right;">
						<td width="47%"><bean:write name="rates" property="serviceTx"/>%</td>
						<td width="53%"><bean:write name="invoice" property="servTx"/></td><!-- ST -->
					</tr>
					<tr style="text-align:right;">
						<td width="47%"><bean:write name="rates" property="eduCess"/>%</td>
						<td width="53%"><bean:write name="invoice" property="eduCes"/></td><!-- EC -->
					</tr>
					<!--<tr style="text-align:right;">
						<td width="47%"><bean:write name="rates" property="hdeuCess"/>%</td>
						<td width="53%"><bean:write name="invoice" property="heduCes"/></td> HEDU 
					</tr>
					-->
					<tr style="text-align:right;">
						<td width="47%"><bean:write name="rates" property="kkcess"/>%</td>
						<td width="53%"><bean:write name="invoice" property="kkc"/></td><!-- KKC -->
					</tr>
					
					
					<tr style="text-align:right;">
						<td width="47%">&nbsp</td>
						<td width="53%"><bean:write name="invoice" property="totinvAmt"/></td><!-- Total inv -->
					</tr>
				</table>

				</td>
			</tr>

		</table>



		</td>
	</tr>

	<tr>
		<td colspan="2"><b>Amount In words(Rs.)&nbsp&nbsp&nbsp&nbsp&nbsp<bean:write name="invoice" property="amountInWords"/></b></td>
	<tr>
</table>
<br>
<br>
<logic:present name="Finlogin">
 <logic:equal name="Finlogin" value="Y">
  <center><LABEL>Remarks:</LABEL><html:textarea name="invoice" property="remarks" styleId="remarks" disabled="true"></html:textarea></center>
 </logic:equal>
</logic:present>

<logic:notPresent name="Finlogin">
<center><LABEL>Remarks:</LABEL><html:textarea name="invoice" property="remarks" styleId="remarks"></html:textarea></center>
<br>
<br>
<center><LABEL>Invoice No:</LABEL><html:text property="invoiceNo" name="invoiceNo" value="" onkeypress="return checkValidinvNo(event)" onkeyup="populatebillCell(this)" >Invoice No:</html:text></center>

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
<br>
<br>
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