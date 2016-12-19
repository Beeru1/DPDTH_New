
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html:html>

<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="theme/text.css" type="text/css">

<TITLE> </TITLE>
<SCRIPT language="JavaScript" type="text/javascript">

function viewSerialNo(strDCNo)
{
  //alert("DCNo  ::  "+strDCNo);
  var url="stockAcceptTransfer.do?methodName=viewSerialsAndProductList&DCNo="+strDCNo;
  window.open(url,"SerialNo","width=700,height=600,scrollbars=yes,toolbar=no");
}

function stockTransAccept(obj)
{
	var frm = obj.form;
//	alert(frm);
	if(validateAccept(frm))
	{
		frm.methodName.value = "acceptStockTransferSubmit";
		frm.submit();
	}
	else
	{
		alert("Please Check at least one Record");
	}
}



function validateAccept(frm)
{

	//alert('Length :' + frm.strCheckedSTA);
	var flag = false;
	if(frm.strCheckedSTA.length != undefined)
	{
		for (count = 0; count < frm.strCheckedSTA.length; count++)
		{
	     	if(frm.strCheckedSTA[count].checked==true)
			{
				flag = true;
			}
		}
	}
	else
	if(frm.strCheckedSTA.checked==true)
	{
		flag = true;
	}
	return flag;
}

</SCRIPT>
</HEAD>

<BODY BACKGROUND="images/bg_main.gif">
<html:form  action="/stockAcceptTransfer"> 

	
<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>
<br>
	<IMG src="<%=request.getContextPath()%>/images/stocktransferacceptance.JPG"
		width="549" height="26" alt=""/>
	<br>
	<br>
	<DIV style="height: 300px; overflow: auto;" width="80%" height="40%">
		<table width="100%" align="left" border="1" cellpadding="0" cellspacing="0" 
		style="border-collapse: collapse;">
			<thead>
				<tr style="position: relative; top:expression(this.offsetParent.scrollTop-2);" class="text white">
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="stockAcceptTransfer.transferFrom" /></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="stockAcceptTransfer.transferDCNo" /></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="stockAcceptTransfer.DCdate" /></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="stockAcceptTransfer.serialList" /></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="stockAcceptTransfer.action" /></FONT></td>
				</tr>
			</thead>
			<tbody>
				<logic:empty name="stockAcceptTransferBean" property="stockTransferAccepList">
					<TR>
						<TD class="text" align="center" colspan="7">No record available for Stock Transfer Acceptance</TD>
					</TR>			
				</logic:empty>
				
				<logic:notEmpty name="stockAcceptTransferBean" property="stockTransferAccepList">
					<logic:iterate name="stockAcceptTransferBean" id="staList" indexId="i" property="stockTransferAccepList">
						<TR BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>' >
							<TD align="center" class="text">
								<bean:write name="staList" property="strTransFrom"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="staList" property="strDCNo"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="staList" property="strDCDate"/>					
							</TD>
							<TD align="center" class="text">
								<a href="#" onclick="viewSerialNo('<bean:write name='staList' property='strDCNo'/>')">
									<bean:message bundle="view" key="stockAcceptTransfer.serialList" />			
								</a>	
							</TD>
							<TD align="center" class="text">
								<input type="checkbox" id="strCheckedSTA" name="strCheckedSTA" value='<bean:write name='staList' property='strDCNo'/>' />
							</TD>
						</TR>
					</logic:iterate>
				</logic:notEmpty>
			</tbody>
		</table>
	</DIV>
	<br>
	<DIV>
		<table width="30%">
			<tr>
				<td width="0%" align="right">
					<input type="button" value="Accept" onclick="stockTransAccept(this);">
				</td>			
			</tr>
		</table>
	</DIV>
	<html:hidden property="methodName"/>
	
</html:form>
</body>

</html:html>