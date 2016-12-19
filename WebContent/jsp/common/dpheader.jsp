<%--><%@ page import="com.ibm.virtualization.recharge.dto.UserSessionContext, com.ibm.virtualization.recharge.common.Constants" %> --%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/theme/text.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/theme/chromestyle2.css" />
<link href="<%=request.getContextPath()%>/theme/Master.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/chrome.js">

</script>
<script language="JavaScript" src="${pageContext.request.contextPath}/cal/calendar_us.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/cal/calendar.css">
</head>


	<%
	if (session.getAttribute(com.ibm.virtualization.recharge.common.Constants.AUTHENTICATED_USER)  == null ){
	%>
	<jsp:forward page="/jsp/authentication/index.jsp"/>
	<%
	}else{
	%>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<table border="0" cellpadding="0" cellspacing="1" bgcolor="#000000" width="100%" valign="top">

	<tr bgcolor="FE000C" valign="top" align="left">
		<td height="17"  bgcolor="cd0504" style="padding-left:5px;padding-top:3px;"><span class="head1"><font color="white">
		<bean:message bundle="view" key="application.header.welcome" /></font></span><span class="yellow10Bold">

	<font color="white"><bean:write name="USER_INFO" property="loginName" /></font>
	</span></td>
	<td height="17" align="right" bgcolor="cd0504" style="padding-right:5px;">
		<span class="white10" id="clock">
		<SCRIPT language="javascript" src="<%=request.getContextPath()%>/scripts/DateTime.js"></SCRIPT> 
		<SCRIPT>getthedate();dateTime();</SCRIPT></span>&nbsp;&nbsp;&nbsp;	
		<a href="#" styleClass="menu" onclick="menulink('ChangePasswordAction.do?methodName=init')">
	   <span class="head1"><font color="white">
		 Change Password </font></span>	   
	   </a>
	    <span class="head1">&nbsp;<font color="white">|</font>&nbsp;</span>
	   <html:link action="LogoutAction.do" styleClass="menu">
	   <span class="head1"><font color="white">Logout</font></span>	
	   </html:link>
		</td>
	</tr>


	<tr bgcolor="#FFFFFF">
		<td width="167" rowspan="2" align="left" height="90" valign="top"><img src="<%=request.getContextPath()%>/images/pic01_1.jpg" width="167" height="90"></td>
    	<td height="19" align="left" valign="center" bgcolor="#FE000C" >
    <div class="chromestyle" id="chromemenu">

		<ul>
		<logic:notEmpty property="arrSysAdminLinks" name="AuthenticationFormBean">
			<li><a href="#" rel="dropmenu1"><img src="<%=request.getContextPath()%>/images/sys_adm_menu.jpg"  border="0"></a></li>
		</logic:notEmpty>
		<logic:notEmpty property="arrSysConfigLinks" name="AuthenticationFormBean">
			<li><a href="#" rel="dropmenu2"><img src="<%=request.getContextPath()%>/images/po_stock_mgmt_menu.jpg"  border="0"></a></li>
		</logic:notEmpty>

		<!--Added by nazim hussain for Reverse Flow  -->
		<logic:notEmpty property="arrReverseFlowLinks" name="AuthenticationFormBean">	
			<li><a href="#" rel="dropmenu7"><img src="<%=request.getContextPath()%>/images/menu07.gif" width="165" height="19" border="0"></a></li>
		</logic:notEmpty>

		<logic:notEmpty property="arrAcctMgmtLinks" name="AuthenticationFormBean">	
			<li><a href="#" rel="dropmenu3"><img src="<%=request.getContextPath()%>/images/reports_menu.jpg"  border="0"></a></li>
		</logic:notEmpty>

		<logic:notEmpty property="arrMoneyTranLinks" name="AuthenticationFormBean">		
			<li><a href="#" rel="dropmenu4"><img src="<%=request.getContextPath()%>/images/menu4.gif" width="129" height="19" border="0"></a></li>
		</logic:notEmpty>
		<logic:notEmpty property="arrUssdActvLinks" name="AuthenticationFormBean">	
			<li><a href="#" rel="dropmenu0"><img src="<%=request.getContextPath()%>/images/menu05.gif" width="111" height="19" border="0"></a></li>
		</logic:notEmpty>
		<logic:notEmpty property="arrReportLinks" name="AuthenticationFormBean">	
			<li><a href="#" rel="dropmenu5"><img src="<%=request.getContextPath()%>/images/menu06.gif" width="85" height="19" border="0"></a></li>
		</logic:notEmpty>
		<logic:notEmpty property="arrStockReportLinks" name="AuthenticationFormBean">	
			<li><a href="#" rel="dropmenu11"><img src="<%=request.getContextPath()%>/images/stock_reports.jpg"  border="0"></a></li>
		</logic:notEmpty>
		<logic:notEmpty property="arrForwardReportLinks" name="AuthenticationFormBean">	
			<li><a href="#" rel="dropmenu8"><img src="<%=request.getContextPath()%>/images/forward_reports.jpg"  border="0"></a></li>
		</logic:notEmpty>
		<logic:notEmpty property="arrReverseReportLinks" name="AuthenticationFormBean">	
			<li><a href="#" rel="dropmenu9"><img src="<%=request.getContextPath()%>/images/reverse_reports.jpg"  border="0"></a></li>
		</logic:notEmpty>
		<logic:notEmpty property="arrRecoReportLinks" name="AuthenticationFormBean">	
			<li><a href="#" rel="dropmenu10"><img src="<%=request.getContextPath()%>/images/reco_reports.jpg"  border="0"></a></li>
		</logic:notEmpty>
		<logic:notEmpty property="arrConsumptionReportLinks" name="AuthenticationFormBean">	
			<li><a href="#" rel="dropmenu12"><img src="<%=request.getContextPath()%>/images/consumption_reports.jpg" border="0"></a></li>
		</logic:notEmpty>
	
		</ul>
	</div>

                                            
	<div id="dropmenu1" class="dropmenudiv" style="width: 150px;">
	<logic:notEmpty property="arrSysAdminLinks" name="AuthenticationFormBean">
		<logic:iterate id="link" name="AuthenticationFormBean"
			property="arrSysAdminLinks" indexId="i">
				<a href="#" onclick="menulink('<%=request.getContextPath()%>/<bean:write name="link" property="linkDisplayUrl" />')">
				<bean:write name="link" property="linkDisplayLabel" />
			</a>
		</logic:iterate>
	</logic:notEmpty>
		
	</div>

	
	<div id="dropmenu2" class="dropmenudiv" style="width: 150px;">
	<logic:notEmpty property="arrSysConfigLinks" name="AuthenticationFormBean">
		<logic:iterate id="link" name="AuthenticationFormBean"
			property="arrSysConfigLinks" indexId="i">
				<a href="#" onclick="menulink('<%=request.getContextPath()%>/<bean:write name="link" property="linkDisplayUrl" />')">
				<bean:write name="link" property="linkDisplayLabel" />
			</a>
		
		</logic:iterate>
		
	</logic:notEmpty>
		
	</div>
	
	
<!--Added by nazim hussain for Reverse Flow  -->
	<div id="dropmenu7" class="dropmenudiv" style="width: 150px;">
	<logic:notEmpty property="arrReverseFlowLinks" name="AuthenticationFormBean">	
		<logic:iterate id="link" name="AuthenticationFormBean" property="arrReverseFlowLinks" indexId="i">
		<a href="#" onclick="menulink('<%=request.getContextPath()%>/<bean:write name="link" property="linkDisplayUrl" />')">
				<bean:write name="link" property="linkDisplayLabel" />
			</a>
		</logic:iterate>
	</logic:notEmpty>
	</div>
	
	<div id="dropmenu3" class="dropmenudiv" style="width: 150px;">
	<logic:notEmpty property="arrAcctMgmtLinks" name="AuthenticationFormBean">	
		<logic:iterate id="link" name="AuthenticationFormBean" property="arrAcctMgmtLinks" indexId="i">
		<a href="#" onclick="menulink('<%=request.getContextPath()%>/<bean:write name="link" property="linkDisplayUrl" />')">
				<bean:write name="link" property="linkDisplayLabel" />
			</a>
		</logic:iterate>
	</logic:notEmpty>
	</div>

	
	<div id="dropmenu4" class="dropmenudiv" style="width: 150px;">
	<logic:notEmpty property="arrMoneyTranLinks" name="AuthenticationFormBean">		
		<logic:iterate id="link" name="AuthenticationFormBean"
			property="arrMoneyTranLinks" indexId="i">
			<a href="#" onclick="menulink('<%=request.getContextPath()%>/<bean:write name="link" property="linkDisplayUrl" />')">
				<bean:write name="link" property="linkDisplayLabel" />
			</a>
		</logic:iterate>
	</logic:notEmpty>
	</div>

	

	<div id="dropmenu0" class="dropmenudiv">
	<logic:notEmpty property="arrUssdActvLinks" name="AuthenticationFormBean">
		<logic:iterate id="link" name="AuthenticationFormBean" 
			property="arrUssdActvLinks" indexId="i">
			<a href="#" onclick="menulink('<%=request.getContextPath()%>/<bean:write name="link" property="linkDisplayUrl" />')">
				<bean:write name="link" property="linkDisplayLabel" />
			</a>
		</logic:iterate>
	</logic:notEmpty>
	</div>

	<div id="dropmenu5" class="dropmenudiv">
	<logic:notEmpty property="arrReportLinks" name="AuthenticationFormBean">
		<logic:iterate id="link" name="AuthenticationFormBean" 
			property="arrReportLinks" indexId="i">
		<a href="#" onclick="menulink('<%=request.getContextPath()%>/<bean:write name="link" property="linkDisplayUrl" />')">
				<bean:write name="link" property="linkDisplayLabel" />
			</a>
		</logic:iterate>
	</logic:notEmpty>
	</div>
	<div id="dropmenu11" class="dropmenudiv">
	<logic:notEmpty property="arrStockReportLinks" name="AuthenticationFormBean">
		<logic:iterate id="link" name="AuthenticationFormBean" 
			property="arrStockReportLinks" indexId="i">
		<a href="#" onclick="menulink('<%=request.getContextPath()%>/<bean:write name="link" property="linkDisplayUrl" />')">
				<bean:write name="link" property="linkDisplayLabel" />
			</a>
		</logic:iterate>
	</logic:notEmpty>
	</div>	
	
	<div id="dropmenu8" class="dropmenudiv">
	<logic:notEmpty property="arrForwardReportLinks" name="AuthenticationFormBean">
		<logic:iterate id="link" name="AuthenticationFormBean" 
			property="arrForwardReportLinks" indexId="i">
		<a href="#" onclick="menulink('<%=request.getContextPath()%>/<bean:write name="link" property="linkDisplayUrl" />')">
				<bean:write name="link" property="linkDisplayLabel" />
			</a>
		</logic:iterate>
	</logic:notEmpty>
	</div>
	<div id="dropmenu9" class="dropmenudiv">
	<logic:notEmpty property="arrReverseReportLinks" name="AuthenticationFormBean">
		<logic:iterate id="link" name="AuthenticationFormBean" 
			property="arrReverseReportLinks" indexId="i">
		<a href="#" onclick="menulink('<%=request.getContextPath()%>/<bean:write name="link" property="linkDisplayUrl" />')">
				<bean:write name="link" property="linkDisplayLabel" />
			</a>
		</logic:iterate>
	</logic:notEmpty>
	</div>	
	<div id="dropmenu10" class="dropmenudiv">
	<logic:notEmpty property="arrRecoReportLinks" name="AuthenticationFormBean">
		<logic:iterate id="link" name="AuthenticationFormBean" 
			property="arrRecoReportLinks" indexId="i">
		<a href="#" onclick="menulink('<%=request.getContextPath()%>/<bean:write name="link" property="linkDisplayUrl" />')">
				<bean:write name="link" property="linkDisplayLabel" />
			</a>
		</logic:iterate>
	</logic:notEmpty>
	</div>	
	<div id="dropmenu12" class="dropmenudiv">
	<logic:notEmpty property="arrConsumptionReportLinks" name="AuthenticationFormBean">
		<logic:iterate id="link" name="AuthenticationFormBean" 
			property="arrConsumptionReportLinks" indexId="i">
		<a href="#" onclick="menulink('<%=request.getContextPath()%>/<bean:write name="link" property="linkDisplayUrl" />')">
				<bean:write name="link" property="linkDisplayLabel" />
			</a>
		</logic:iterate>
	</logic:notEmpty>
	</div>	
		
</td>
	    </tr>
	<tr bgcolor="#FFFFFF"> 
    <td height="64" align="left" valign="top" background="<%=request.getContextPath()%>/images/t_bg.gif"><img src="<%=request.getContextPath()%>\images\distributor portal.jpg" width="556" height="64"></td>
  </tr>
 
</table>
<script>
cssdropdown.startchrome("chromemenu");
</script>
</body>
<%
}
 %>
</html>

