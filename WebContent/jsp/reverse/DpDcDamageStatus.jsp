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
<%
System.out.println("--------------+++++++++++++++++++DpDcDamageStatus.jsp+++++++++++++++++++++++++--------------------");
 %>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="${pageContext.request.contextPath}/jsp/reverse/theme/text.css" type="text/css">
<script language="JavaScript"
	src="<%=request.getContextPath()%>/jScripts/AccountMaster.js"></script>
<TITLE></TITLE>

<style type="text/css">
.noScroll{
position: relative;
	 top: 0px;
	left: 0px;
	 top:expression(this.offsetParent.scrollTop-2);
  
}
</style>
<SCRIPT language="JavaScript" type="text/javascript">

function viewSerialNo(invoiceNo)
{
	//alert(invoiceNo);
  var url="dcDamageStatus.do?methodName=viewSerialsStatus&invoiceNo="+invoiceNo;
  window.open(url,"SerialNo","width=700,height=600,scrollbars=yes,toolbar=no");
}


function cancelDCStatus()
{
// alert("it is damage page and going for fresh page");
	  var url="dcChangeStatus.do?methodName=initDcStatus&methodValue=Damage";
      document.forms[0].action=url;
      document.forms[0].submit();
  //alert(url);
}

function validateSave()
{
	var alertMsg = "";
	//var checkedRowLen = document.forms[0].strCheckedDC.length;
	var noOfCreatedRows = 0;
	var noOfCheckedRows = 0;
	var tableRows = document.getElementById("tableMSA").rows;
	var dcNo =new Array();	
	if(tableRows.length>2){
		for(rowCount=1; rowCount<tableRows.length; rowCount++)
		{
			if(document.forms[0].strCheckedDCVal[rowCount-1].value=="CREATED")
				{
					var checkedRowLen = document.forms[0].strCheckedDC.length;
					if(checkedRowLen ==null){
						if(document.forms[0].strCheckedDC.checked){
							dcNo[0]=tableRows[rowCount].cells[1].innerText;
						}
					}else{
						 if(document.forms[0].strCheckedDC[noOfCreatedRows].checked)
						{
						dcNo[noOfCheckedRows]=tableRows[rowCount].cells[1].innerText;
						noOfCheckedRows = noOfCheckedRows+1;
						}
						noOfCreatedRows =noOfCreatedRows+1;
					}
				}
				
		}
	}
	else
	{
		var rowCount=1;
		var checkedRowLen = document.forms[0].strCheckedDC.length;
					if(checkedRowLen ==null){
						if(document.forms[0].strCheckedDC.checked){
							dcNo[0]=tableRows[rowCount].cells[1].innerText;
						}
					}else{
						 if(document.forms[0].strCheckedDC[noOfCreatedRows].checked)
						{
						dcNo[noOfCheckedRows]=tableRows[rowCount].cells[1].innerText;
						noOfCheckedRows = noOfCheckedRows+1;
						}
						noOfCreatedRows =noOfCreatedRows+1;
					}
	}
	
	
	if(dcNo != ""){
	document.forms[0].hidArrDcNos.value=dcNo;
	
	}else{
		alertMsg = "No row/rows selected";
	}
	
	return alertMsg;
}


function printPage(strDCNo)
{
	if(strDCNo !=""){
				var url="printDCDetails.do?methodName=printDC&Dc_No="+strDCNo+"&TransType=Fresh";
 			    window.open(url,"SerialNo","width=700,height=600,scrollbars=yes,toolbar=no");
				
  			}
}
</SCRIPT>
</HEAD>

<BODY BACKGROUND="${pageContext.request.contextPath}/jsp/reverse/images/bg_main.gif">

<html:form  action="/dcDamageStatus"> 


	
<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>

	
		<logic:notEmpty property="strSuccessMsg" name="DpDcDamageStatusBean">
			<BR>
				<strong><font color="red"><bean:write property="strSuccessMsg"  name="DpDcDamageStatusBean"/></font></strong>
			<BR>
		</logic:notEmpty>
		
	<DIV style="height: 310px; width: 100%; overflow: auto; visibility: visible;z-index:1; left: 133px; top: 250px;">
		<table width="100%" align="left" border="1" cellpadding="0" cellspacing="0" id="tableMSA"
		style="border-collapse: collapse;">
			<thead>
				<tr class="noScroll">
					<!-- <td width="1%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						&nbsp;</FONT></td> -->
					<!--<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="view" key="dcStatus.SRNo"/></SPAN></FONT></td>
					--><td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="view" key="dcStatus.DCNo"/> Damage</SPAN></FONT></td>
					<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="view" key="dcStatus.DCDate"/></SPAN></FONT></td>
					<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="view" key="dcStatus.View"/></SPAN></FONT></td>
					<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="dp" key="label.dcStatus.srno"/></SPAN></FONT></td>
					<td width="7%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="view" key="dcStatus.Status"/></SPAN></FONT></td>
					<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="view" key="dcStatus.Remarks"/></SPAN></FONT></td>
					
				</tr>
			</thead>
			
			<tbody>
				<logic:empty name="DpDcDamageStatusBean" property="dcNosList">
					<TR>
						<TD class="text" align="center" colspan="8">No record available</TD>
					</TR>			
				</logic:empty>
				
				<logic:notEmpty name="DpDcDamageStatusBean" property="dcNosList">
					<logic:iterate name="DpDcDamageStatusBean" id="dcNosList" indexId="i" property="dcNosList">
						<TR  BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>'>
							
							<!-- 	Commented because calcel DC is not allowed
							<logic:equal name="dcNosList" property="strStatus" value="CREATED">
							<TD align="center" class="text">
								<input type="checkbox" value='<bean:write name="dcNosList" property="strDCNo"/>' id="strCheckedDC" property="strCheckedDC" name="strCheckedDC">
								<input type="textField" value='<bean:write name="dcNosList" property="strStatus"/>' id="strCheckedDCVal" property="strCheckedDCVal" name="strCheckedDCVal" style="display: none">	
							</TD>
							</logic:equal>
							
							<logic:notEqual name="dcNosList" property="strStatus" value="CREATED">
							<TD align="center" class="text">
							<input type="textField" value='<bean:write name="dcNosList" property="strStatus"/>' id="checkDCValue" property="strCheckedDCVal" name="strCheckedDCVal" style="display: none">	
							</TD>
							</logic:notEqual>
							 -->
						
							
							<!--<TD align="center" class="text">
								<bean:write name="dcNosList" property="strSerialNo"/>					
							</TD>
							--><TD align="center" class="text">
								<bean:write name="dcNosList" property="strDCNo"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="dcNosList" property="strDCDate"/>					
							</TD>
							<TD align="center" class="text">
								<a href="#" onclick="printPage('<bean:write name='dcNosList' property='strDCNo'/>')">
									<bean:message  bundle="view" key="dcStatus.View"/>				
								</a>	
							</TD>
							<TD align="center" class="text">
								<a href="#" onclick="viewSerialNo('<bean:write name='dcNosList' property='strDCNo'/>')">
									<bean:message  bundle="view" key="dcStatus.View"/>				
								</a>	
							</TD>
							<TD align="center" class="text">
								<bean:write name="dcNosList" property="strDcStatus"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="dcNosList" property="strBOTreeRemarks"/>					
							</TD>
							
							
						</TR>
					</logic:iterate>
				</logic:notEmpty>
			</tbody>
		</table>
	</DIV>
	<br>
	<table width="100%">
		<!-- 
			Commented because calcel DC is not allowed
		<tr>
			<td width="100%" align="center">
				<input type="button" value="Cancel DC" onclick="cancelDCStatus();" >
			</td>
		</tr>
	 -->
	</table>

	<html:hidden property="hidArrDcNos"  name="DpDcDamageStatusBean" value=""/>
	<html:hidden property="methodName"/>
</html:form>
</body>
</html:html>