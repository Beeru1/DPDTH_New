<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<TITLE> </TITLE>
</head>
<style type="text/css">
.noScroll{
position: relative;
	 top: 0px;
	left: 0px;
	 top:expression(this.offsetParent.scrollTop-2);
  
}
</style>
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<script language="javascript">

	function updateStatus()
	{
	
		var url = "dpAlertConfiguration.do?methodName=updateStatus";
		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();	
	
	}
	function updateGraceperiod()
	{
	
		var url = "dpAlertConfiguration.do?methodName=updateGraceperiod";
		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();	
	
	}
</script>

<body>
<html:form action="/dpAlertConfiguration.do"  method="post">

<TABLE>
		<TBODY>
		<tr>
			<td></td>		
			<TD>			
			<IMG src="${pageContext.request.contextPath}/images/alertconfig.jpg" width="420" height="30" alt="">
			</TD>					
		</tr>
		</TBODY>
	</TABLE>
	
	
	<TABLE width="70%" border="0" cellpadding="1" cellspacing="1" align="center" class ="border">
		<TBODY>
			<TR>
				<TD></TD>
				<TD width="400"></TD>
			</TR>
			<TR>
				<TD>
					<font color="#C11B17" size="3"> <bean:write property="strNoteMsg" name="DPAlertConfigurationBean"/> </font>
					<BR>
				</TD>
			</TR>
			<TR>
				<TD>
					<font color="#C11B17" size="3"> <bean:write property="strMsg" name="DPAlertConfigurationBean"/> </font>
					<BR>
				</TD>
			</TR>
			<TR id="showRestriction">
				<TD colspan="2">
   				   <fieldset style="border-width:5px">
			  	 	 <legend> <STRONG><FONT color="#C11B17" size="2">FnF Grace Period </FONT></STRONG></legend>				  	 	
						<table width="100%" align="center"  style="position: relative;">
			<TR>
    		<TD width="25%" class="text pLeft15"><STRONG><FONT color="#000000">TSM Grace Period</FONT> </STRONG></TD>
			<TD width="25%"><FONT color="#003399"> <html:text property="tsmGracePeriod" styleClass="box" styleId="tsmGracePeriod"
								size="20" maxlength="3" onkeypress="isValidNumber()" name="DPAlertConfigurationBean" /></FONT></TD>
			<TD width="25%" class="text pLeft15"><STRONG><FONT color="#000000">ZSM Grace Period</FONT> </STRONG></TD>
			<TD width="25%"><FONT color="#003399"> <html:text property="zsmGracePeriod" styleClass="box" styleId="zsmGracePeriod"
								size="20" maxlength="3" onkeypress="isValidNumber()"  name="DPAlertConfigurationBean" /></FONT></TD>
		     <TD width="25%" >&nbsp;</TD>
			<TD align="left" width="55%">
					<input type="button" value="Submit" onclick="updateGraceperiod();">
			</TD>
		   </TR>	
			</table>
			</fieldset>
			</TD>
			</TR>
			</TBODY>
			</TABLE>
				
  <%
  if(!"null".equals(request.getAttribute("msg")+""))
   {
    
    %>
    <%="<b>"+request.getAttribute("msg")+"</b><br>" %> 
   <%
   request.removeAttribute("msg");
   }
   %>
	<TABLE width="70%" border="0" cellpadding="1" cellspacing="1"
		align="center" class ="border">
		<TBODY>
			
			<TR id="showRestriction">
				<TD width='100%'>
   				   <fieldset style="border-width:7px">
			  	 	 <legend> <STRONG><FONT color="#C11B17" size="2">Alert Configuration </FONT></STRONG></legend>				  	 	
						<table width="100%"align="center">
								<tr >
									  <td align="left" width="70%" bgcolor="#CD0504" class="colhead" align="center">
									  <FONT color="white">Alert Description</font></td>
									  <td colspan="2" bgcolor="#CD0504" class="colhead" align="center">
									  <FONT color="white"> Status </font></td>  
								</tr>
							<logic:iterate property="alertList" id="report" indexId="i" name="DPAlertConfigurationBean" type="com.ibm.dp.dto.AlertConfigurationDTO">
								<tr >
									 <td width="300"><%=report.getAlertDesc()%></td>
									 <td align="left" width="100">
									 		<input type="hidden" name="alertId<%=i%>" id="alertId<%=i%>" value=<%=report.getAlertId()%> />
											<input type="radio" name="status<%=i%>" id="status<%=i%>" value="true" <%=report.getEnabledChecked()%> />											
											<bean:message bundle="dp" key="label.Activation.Status.Allowed"/>
									 </td>
									<td align="left" width="100">
											<input type="radio" name="status<%=i%>" id="negativestatus<%=i%>" value="false" <%=report.getDisabledChecked()%> />		
											<bean:message bundle="dp" key="label.Activation.Status.NotAllowed"/>											
									</td>
								   
									
							  </tr>
							</logic:iterate>
						</table>
				</fieldset>
        <TABLE width="60%" border="0" cellpadding="1" cellspacing="1"align="center" class ="border">
		   <tr>
		   	<td colspan='2'>&nbsp;</td>
		   	</tr>
		   	<tr>
		     <td width="25%" height="28">&nbsp;</td>
			 <td aln="left" width="55%" height="28">
			 <input type="button" value="Update Status" onclick="updateStatus();">
			</td>
		   </tr>
	</TABLE>
	</TD></TR>
	</TBODY>
  </TABLE>
</html:form>
</body>
</html>