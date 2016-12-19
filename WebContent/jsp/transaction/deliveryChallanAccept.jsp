
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html:html>

<HEAD>
<%@ page 
language="java" import="java.lang.Integer"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="theme/text.css" type="text/css">

<TITLE> </TITLE>
<SCRIPT language="JavaScript" type="text/javascript">

function viewSerialNo(invoiceNo)
{
  //alert("invoiceNo  ::  "+invoiceNo);
  var url="DeliveryChallanAccept.do?methodName=viewSerialsAndProductList&invoiceNo="+invoiceNo;
  window.open(url,"SerialNo","width=700,height=600,scrollbars=yes,toolbar=no");
}

function acceptDeliveryChallan(obj)
{
	var frm = obj.form;
	//alert(frm);
	if(validateAccept(frm))
	{
	    
	    var flag = confirm("Are you sure that stock recieved is as per DC?");
	    if(flag == true)    
	    {
			frm.methodName.value = "acceptDeliveryChallan";
			frm.submit();
		}
	}
	else
	{
		alert("No row/rows selected");
	}
}

function wrongShipment(obj)
{
    var frm = obj.form;
    //alert(frm);
	if(validateAccept(frm))
	{
		frm.methodName.value = "wrongShipment";
		frm.submit();
	}
	else
	{
		alert("No row/rows selected");
	}
}

function validateAccept(frm)
{

	//alert('Length :' + frm.strCheckedDC);
	var flag = false;
	if(frm.strCheckedDC==null)
	{
		return false;
	}
	else if(frm.strCheckedDC.length != undefined)
	{
		for (count = 0; count < frm.strCheckedDC.length; count++)
		{
	     	if(frm.strCheckedDC[count].checked==true)
			{
				flag = true;
			}
		}
	}
	else if(frm.strCheckedDC.checked==true)
	{
		flag = true;
	}
	return flag;
}

</SCRIPT>
</HEAD>

<BODY BACKGROUND="images/bg_main.gif">
<html:form  action="/DeliveryChallanAccept"> 

	
<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>
<br>
	<IMG src="<%=request.getContextPath()%>/images/postockacceptance.JPG"
		width="542" height="26" alt=""/><br /> 
		
		<logic:notEmpty name="DPDeliveryChallanAcceptBean" property="errMsg">
				<font color="red"><strong><bean:write name="DPDeliveryChallanAcceptBean" property="errMsg"/></strong></font>
		</logic:notEmpty>
		
	<DIV style="height: 330px; overflow: auto;" width="80%">
		<table width="100%" align="left" border="1" cellpadding="0" cellspacing="0" 
		style="border-collapse: collapse;">
			<thead>
				<tr style="position: relative; top:expression(this.offsetParent.scrollTop-2);" class="text white">
					<td bgcolor="#CD0504" class="colhead" align="center" width="15%"><FONT color="white"><bean:message  bundle="dp" key="label.DCA.PRNO"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="15%"><FONT color="white"><bean:message  bundle="dp" key="label.DCA.PONO"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="15%"><FONT color="white"><bean:message  bundle="dp" key="label.DCA.InvoiceNO"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="15%"><FONT color="white"><bean:message  bundle="dp" key="label.DCA.DCNO"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="10%"><FONT color="white"><bean:message  bundle="dp" key="label.DCA.DCDate"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="10%"><FONT color="white"><bean:message  bundle="dp" key="label.DCA.VSRNO"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="10%"><FONT color="white"><bean:message  bundle="dp" key="label.DCA.Status"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="10%"><FONT color="white"><bean:message  bundle="dp" key="label.DCA.Action"/></FONT></td>
				</tr>
			</thead>
			<tbody>
				<logic:empty name="DPDeliveryChallanAcceptBean" property="deliveryChallanList">
					<TR>
						<TD class="text" align="center" colspan="8">No record available</TD>
					</TR>			
				</logic:empty>
				
				<logic:notEmpty name="DPDeliveryChallanAcceptBean" property="deliveryChallanList">
					<logic:iterate name="DPDeliveryChallanAcceptBean" id="dpDCAList" indexId="i" property="deliveryChallanList">
						<TR BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>' >
							<TD align="center" class="text">
								<bean:write name="dpDCAList" property="intPRNo"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="dpDCAList" property="strPONo"/>					
							</TD>
							
							<TD align="center" class="text">
								<bean:write name="dpDCAList" property="strInvoiceNo"/>					
							</TD>
							
							<TD align="center" class="text">
								<bean:write name="dpDCAList" property="strDCNo"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="dpDCAList" property="strDCDate"/>					
							</TD>
							<TD align="center" class="text">
							   <bean:define id="iIsError" name="dpDCAList" property="isError" type="java.lang.Integer"/>
								<%							    
							    int iIs_Error = new Integer(iIsError+"").intValue(); 
							    if (iIs_Error==1)
							    { 
							    %> 
							        <a href="#" onclick="JavaScript:alert('<bean:message  bundle="dp" key="label.DCA.ERROR_Desc"/>	')">
										<bean:message  bundle="dp" key="label.DCA.ERROR_Text"/>					
									</a>
							    <%
							    }
							    
								else
								 {
								%>
									<a href="#" onclick="viewSerialNo('<bean:write name='dpDCAList' property='strInvoiceNo'/>')">
										<bean:message  bundle="dp" key="label.DCA.VSRNO_Text"/>					
									</a>	
								<%
								}
								%>																			
							</TD>
							<TD align="center" class="text">
								<bean:write name="dpDCAList" property="strStatus"/>					
							</TD>
							<TD align="center" class="text">
							    <%
							    
							    if (iIs_Error==1)
							    { 
							    %> 
							        <input type="checkbox"  disabled=disabled id="strCheckedDC" name="strCheckedDC" value='<bean:write name='dpDCAList' property='strInvoiceNo'/>' />
							    <%
							    }
								else
								 {
								%>
									<input type="checkbox" id="strCheckedDC" name="strCheckedDC" value='<bean:write name='dpDCAList' property='strInvoiceNo'/>' />
								<%
								}
								%>
							</TD>
						</TR>
					</logic:iterate>
				</logic:notEmpty>
			</tbody>
		</table>
	</DIV>
	<br>
	<DIV>
		<table width="80%">
			<tr>
				<td width="40%" align="right">
					<input type="button" value="Accept" onclick="acceptDeliveryChallan(this);">
				</td>
				<td width="60%" align="center">
					<input type="button" value="Shipment Error" onclick="wrongShipment(this);">
				</td>				
			</tr>
		</table>
	</DIV>
	<html:hidden property="methodName"/>
	
</html:form>
</body>

</html:html>