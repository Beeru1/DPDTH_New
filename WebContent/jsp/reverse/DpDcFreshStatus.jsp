<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html:html>

<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<%
System.out.println("--------------+++++++++++++++++++DpDcFreshStatus.jsp++++++++++++++++--------------------");
 %>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="theme/text.css" type="text/css">
<script language="JavaScript"
	src="<%=request.getContextPath()%>/jScripts/AccountMaster.js"></script>
<TITLE> </TITLE>

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
  //alert("invoiceNo  ::  "+invoiceNo);
  var url="dcDamageStatus.do?methodName=viewSerialsStatusFresh&invoiceNo="+invoiceNo;
 // alert(url);
  window.open(url,"SerialNo","width=700,height=600,scrollbars=yes,toolbar=no");
}

function printPage(strDCNo)
{

	if(strDCNo !=""){
				var url="printDCDetails.do?methodName=printDC&Dc_No="+strDCNo;
 			    window.open(url,"SerialNo","width=700,height=600,scrollbars=yes,toolbar=no");
				
  			}
}
</SCRIPT>
</HEAD>

<BODY BACKGROUND="images/bg_main.gif">
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
						<SPAN><bean:message  bundle="view" key="dcStatus.DCNo"/></SPAN></FONT></td>
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
				<logic:empty name="DpDcDamageStatusBean" property="dcNosListFresh">
					<TR>
						<TD class="text" align="center" colspan="8">No record available</TD>
					</TR>			
				</logic:empty>
				
				<logic:notEmpty name="DpDcDamageStatusBean" property="dcNosListFresh">
					<logic:iterate name="DpDcDamageStatusBean" id="dcNosListFresh" indexId="i" property="dcNosListFresh">
						<TR  BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>'>
							
							<!-- 	Commented because calcel DC is not allowed
							<logic:equal name="dcNosListFresh" property="strStatus" value="CREATED">
							<TD align="center" class="text">
								<input type="checkbox" value='<bean:write name="dcNosListFresh" property="strDCNo"/>' id="strCheckedDC" property="strCheckedDC" name="strCheckedDC">
								<input type="textField" value='<bean:write name="dcNosListFresh" property="strStatus"/>' id="strCheckedDCVal" property="strCheckedDCVal" name="strCheckedDCVal" style="display: none">	
							</TD>
							</logic:equal>
							
							<logic:notEqual name="dcNosListFresh" property="strStatus" value="CREATED">
							<TD align="center" class="text">
							<input type="textField" value='<bean:write name="dcNosListFresh" property="strStatus"/>' id="checkDCValue" property="strCheckedDCVal" name="strCheckedDCVal" style="display: none">	
							</TD>
							</logic:notEqual>
							 -->
						
							
							<!--<TD align="center" class="text">
								<bean:write name="dcNosListFresh" property="strSerialNo"/>					
							</TD>
							--><TD align="center" class="text">
								<bean:write name="dcNosListFresh" property="strDCNo"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="dcNosListFresh" property="strDCDate"/>					
							</TD>
							<TD align="center" class="text">
								<a href="#" onclick="printPage('<bean:write name='dcNosListFresh' property='strDCNo'/>')">
									<bean:message  bundle="view" key="dcStatus.View"/>				
								</a>	
							</TD>
							<TD align="center" class="text">
								<a href="#" onclick="viewSerialNo('<bean:write name='dcNosListFresh' property='strDCNo'/>')">
									<bean:message  bundle="view" key="dcStatus.View"/>				
								</a>	
							</TD>
							<TD align="center" class="text">
								<bean:write name="dcNosListFresh" property="strDcStatus"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="dcNosListFresh" property="strBOTreeRemarks"/>					
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

	<html:hidden property="hidArrDcNosFresh"  name="DpDcDamageStatusBean" value=""/>
	<html:hidden property="methodName"/>
</html:form>
</body>
</html:html>