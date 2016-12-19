<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html:html>

<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="${pageContext.request.contextPath}/jsp/transaction/theme/text.css" type="text/css">
<TITLE>STB Flush Out</TITLE>
<SCRIPT language="JavaScript" type="text/javascript">

function viewPODetails(poNumber)
{
  var url="ErrorPO.do?methodName=viewPODetailList&poNumber="+poNumber;
  window.open(url,"SerialNo","width=1000,height=600,scrollbars=yes,toolbar=no"); 
}
</SCRIPT>
</HEAD>

<BODY BACKGROUND="${pageContext.request.contextPath}/jsp/transaction/images/bg_main.gif">
<html:form  action="/ErrorPO" method="POST"> 
<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>
<br>
	<IMG src="${pageContext.request.contextPath}/images/errorPo.jpg"
		width="542" height="26" alt="View Error PO"/>
	<DIV style="height: 330px; overflow: auto;" width="80%">
		<table width="100%" align="left" border="1" cellpadding="0" cellspacing="0" 
		style="border-collapse: collapse;">
			<thead>
				<tr style="position: relative; top:expression(this.offsetParent.scrollTop-2);" class="text white"> 																												
					<td bgcolor="#CD0504" class="colhead" align="center" width="15%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.Circle"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="15%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.DISTOLMID"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="15%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.DISTName"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="15%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.TSMName"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="10%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.PONO"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="10%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.DCNO"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="10%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.DCDate"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="10%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.PODetails"/></FONT></td>
				</tr>
			</thead>
			<tbody>
				<logic:empty name="ErrorPOBean" property="duplicateSTBList">
					<TR>
						<TD class="text" align="center" colspan="8">No record available</TD>
					</TR>			
				</logic:empty>
				
				<logic:notEmpty name="ErrorPOBean" property="duplicateSTBList">
					<logic:iterate name="ErrorPOBean" id="stbList" indexId="i" property="duplicateSTBList">
						<TR BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>' >
							<TD align="center" class="text">
								<bean:write name="stbList" property="strCircleName"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="stbList" property="strDistOLMId"/>					
							</TD>
							
							<TD align="center" class="text">
								<bean:write name="stbList" property="strDistName"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="stbList" property="strTSMName"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="stbList" property="strPONumber"/>					
							</TD>							
							<TD align="center" class="text">
								<bean:write name="stbList" property="strDCNo"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="stbList" property="strDCDate"/>					
							</TD>
							<TD align="center" class="text">
								<a href="#" onclick="viewPODetails('<bean:write name='stbList' property='strPONumber'/>')">
									<bean:message  bundle="dp" key="label.SFO.ViewDetails"/>					
								</a>	
							</TD>							
						</TR>
					</logic:iterate>
				</logic:notEmpty>
			</tbody>
		</table>
	</DIV>
	<br>	
	<html:hidden property="methodName"/>	
</html:form>
</body>
</html:html>