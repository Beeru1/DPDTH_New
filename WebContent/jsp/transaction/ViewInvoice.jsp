<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page isELIgnored="false" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<script>
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
</script>

<link href="<%=request.getContextPath()%>/theme/statusMsg.css"
	rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<%if(request.getAttribute("disableLinkPay")==null)
{%>
<body>
<%}
else if(request.getAttribute("disableLinkPay").equals("true"))
{%>
<body onload="disableLink();">
<% }%>
<bean:define id="invoice" name="InvoiceListForm"></bean:define>
<h1 style="color:black;"> <bean:message key="Invoice.ViewPage.header"/></h1>
<br>
<br>
<logic:notEmpty name="InvoiceListForm" property="invoiceList" >
  <table class="invTab" style="align:center;width=75%;">
 <tr style="color:black;">
 	
  	<th style="text-align:left;color:black;"><bean:message key="Invoice.ViewPage.S.no"/></th> 
  	<th style="text-align:left;color:black;"><bean:message key="Invoice.ViewPage.Partner"/></th>
  	<th style="text-align:left;color:black;"><bean:message key="Invoice.ViewPage.BillNo"/></th>
  	<th style="text-align:left;color:black;"><bean:message key="Invoice.ViewPage.Month"/></th>
  	<th style="text-align:left;color:black;"><bean:message key="Invoice.ViewPage.Status"/></th>
 
  </tr>
 
   <logic:iterate id="invoice"  name="InvoiceListForm" property="invoiceList" indexId="index">
   	<tr style="color:black;">
   	  <td>${index+1}</td>
   	  <td><bean:write name="invoice" property="partnerNm"/></td>
   	  <td><html:link  action="/ViewPartnerInvoice.do" target="_blank"  paramId="billNo" paramName="invoice" paramProperty="billNo"><bean:write name="invoice" property="billNo" /></html:link></td>
   	  <td><bean:write name="invoice" property="monthFor"/></td>
   	  <td><bean:write name="invoice" property="status"/></td>
   	 </tr>
   </logic:iterate>
  </table>
</logic:notEmpty>
<br>
<br>
<h1 style="color:black;"> <bean:message key="Invoice.Signed.pdf"/></h1>
<logic:notEmpty name="InvoiceListForm" property="signedInvoice">
 <table class="invTab" style="align:center;width:75%;">
	<tr>
 	
  	<th style="text-align:left;color:black;"><bean:message key="Invoice.ViewPage.S.no"/></th> 
  	<th style="text-align:left;color:black;"><bean:message key="Invoice.ViewPage.Partner"/></th>
  	<th style="text-align:left;color:black;"><bean:message key="Invoice.ViewPage.BillNo"/></th>
  	<th style="text-align:left;color:black;"><bean:message key="Invoice.ViewPage.Month"/></th>
  	<th style="text-align:left;color:black;"><bean:message key="Invoice.ViewPage.Status"/></th>
 
  </tr>
     <logic:iterate id="signedPdf" name="InvoiceListForm" property="signedInvoice" indexId="signedInd">
       <tr style="color:black;">
       <td>${signedInd+1}</td>
       <td><bean:write name="USER_INFO" property="accountName"/></td>
       <% 
       		String file=(String) signedPdf;
       		String invoiceNo= file.substring(17,(file.lastIndexOf("_")-1));
       		String month=file.substring(9,11)+"-"+file.substring(12,16);
        %>
       <td><html:link action="/downloadPdfSign" target="_blank" paramId="fileName" paramName="signedPdf"><%=invoiceNo%></html:link></td>
       <td><%=month%></td>
       <td>Digitally Signed</td>
       </tr>
     </logic:iterate>
 	</table>
   </logic:notEmpty>
<logic:empty name="InvoiceListForm" property="invoiceList" >
 <logic:empty name="InvoiceListForm" property="signedInvoice">
 <bean:message key="Invoice.Emptylist"/>
 </logic:empty>
</logic:empty>
</body>
</html>