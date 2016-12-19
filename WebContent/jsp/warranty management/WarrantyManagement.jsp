<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<SCRIPT language="JavaScript"
	src="<%=request.getContextPath()%>/scripts/validation.js"
	type="text/javascript"></SCRIPT>
<SCRIPT language="javascript" type="text/javascript"> 
function doSubmit(){
alert("doSubmit");
document.forms[0].action="./DPViewWarrantyAction.do?methodName=Selectdata";
document.forms[0].submit();
}
function validate(){
 alert("validate");
  if(document.getElementById("imeino").value == null || document.getElementById("imeino").value==""){
	    alert('<bean:message bundle="error" key="error.warranty.imeino" />');
	    document.getElementById("imeino").focus();
	    return false; 
	}
 doSubmit();
}
function validate1()
{
	alert("validate1");
	document.getElementById("imeino").value="";
}
function validate2()
{
	alert("validate1");
	var imei=document.getElementById("imeino").value;
	document.getElementById("imeino").value="";
	document.getElementById("extwrnty").value="0";
	document.getElementById("productname").value="";	
	document.getElementById("dmgstatus").value="0";
	document.getElementById("stdwrnty").value="0";			
}			
function formSubmit(){
alert("formsubmit");
	if(document.getElementById("imeino").value == null || document.getElementById("imeino").value==""){
		alert('<bean:message bundle="error" key="error.warranty.imeino" />');
		document.getElementById("imeino").focus();
		return false; 
	}
	if(document.getElementById("dmgstatus").value=="Y"){
		alert("Cannot Extend Warranty Of A Damaged Product");
		return false;
	}
	if(isNumber(document.getElementById("extwrnty").value)== false){
		alert("Extended Warranty Field Takes Positive Number Values Only");
		return false;
	}
	document.forms[0].action="./DPEditWarrantyAction.do?methodName=UpdateWarrantydata";
	document.forms[0].submit();
}
</script>
</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">
<%
	String rowDark = "#FFE4E1";
	String rowLight = "#FFFFFF";
%>
<html:form action="DPEditProductAction">
	<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
		height="100%" valign="top">
		<TR>
			<TD colspan="2" valign="top"><jsp:include
				page="../common/dpheader.jsp" flush="" /></TD>
		</TR>
		<TR valign="top">
			<TD align="left" bgcolor="#FFFFFF"
				background="<%=request.getContextPath()%>/images/bg_main.gif"
				valign="top" height="100%" width="176"><jsp:include
				page="../common/leftHeader.jsp" flush="" /></TD>
			<td width="807">
	<table>
		<tr>
			<TD colspan="4" class="text"><BR>
				<IMG src="<%=request.getContextPath()%>/images/warrantyManagement.gif"
				width="505" height="22" alt="">
			</TD>
		</tr>
		</table>
			<TABLE border="0" align="Center" class="border" width="366">
				<TBODY>

					<TR>
						<TD class="text pLeft15" align="center" width="179"><bean:message
							bundle="view" key="warranty.imei" /><font color="red">*</font>  </TD>
						<TD width="189"><html:text name="DPEditWarrantyFormBean"
							property="imeino" maxlength="15" /></TD>
					</TR>

					<logic:messagesPresent message="true">
						<html:messages id="msg" property="MESSAGE_SENT_SUCCESS"
							bundle="view" message="true">
							<font color="red" size="2"><strong><bean:write
								name="msg" /></strong></font>
						</html:messages>
						<html:messages id="msg" property="ERROR_OCCURED" bundle="view"
							message="true">
							<font color="red" size="2"><strong><bean:write
								name="msg" /></strong></font>
						</html:messages>
					</logic:messagesPresent>
					<tr><td width="179"></td></tr>
					<TR>
						<td colspan="1" align="right" width="179"></td>
						<td colspan="1" align="left" width="189"><input type="submit"
							class="subbig" value="Search" onclick="return validate();"></td>
					</TR>
					<logic:messagesPresent message="true">
						<tr>
							<td></td>
							<td align="left"><html:messages id="msg1"
								property="NO_RECORD" bundle="hboView" message="true">
								<font color="red" size="2"><strong><bean:write
									name="msg1" /></strong></font>
							</html:messages></td>
						</tr>			
					</logic:messagesPresent>
			</TABLE>
			<br>
			<TABLE align="center" width="366">
				<!--<TR>
				<TD valign="top" height="100%" align="center">
				<b>WARRANTY SERVICE </b> <font>&nbsp;<font color='red'></font></TD>
			</TR>
			-->
				<%--	<TR>
			<TD class="text pLeft15" width="250"><bean:message bundle="view" key="warranty.mnr" /></TD>
			<td width="479">
			<html:text property="mnreorder" name="DPEditWarrantyFormBean" readonly="true">			
			<bean:write name="DPEditWarrantyFormBean" property="mnreorder"/>
			</html:text>										
			</td>
			</TR> --%>
				<TR>
					<td align="left" width="186" colspan="2"><html:hidden property="imeiNo"
						name="DPEditWarrantyFormBean" value='<bean:write name="DPEditWarrantyFormBean" property="imeiNo" />'/>
					</td>
				</TR>
				<%--	<TR>
			<TD class="text pLeft15" width="250"><bean:message bundle="view" key="warranty.imeiwrhs" /></TD>
			<td width="479">
			<html:text property="imeiwrhs" name="DPEditWarrantyFormBean" readonly="true">			
			<bean:write name="DPEditWarrantyFormBean" property="imeiwrhs"/>
			</html:text>									
			</td>
			</TR> --%>
				<TR>
					<TD class="text pLeft15" align="center" width="174"><bean:message
						bundle="view" key="warranty.dmgsts" /></TD>
					<td align="left" width="186"><html:text property="dmgstatus"
						name="DPEditWarrantyFormBean" readonly="true">
						<bean:write name="DPEditWarrantyFormBean" property="dmgstatus" />
					</html:text></td>
				</TR>
				<TR>
					<TD class="text pLeft15" align="center" width="174"><bean:message
						bundle="view" key="warranty.stdwrnty" /></TD>
					<td align="left" width="186"><html:text property="stdwrnty"
						name="DPEditWarrantyFormBean" readonly="true">
						<bean:write name="DPEditWarrantyFormBean" property="stdwrnty" />
					</html:text></td>
				</TR>
				<TR>
					<TD class="text pLeft15" align="center" width="174"><bean:message
						bundle="view" key="warranty.extwrnty" /></TD>
					<td align="left" width="186"><html:text property="extwrnty"
						name="DPEditWarrantyFormBean" maxlength="8">
						<bean:write name="DPEditWarrantyFormBean" property="extwrnty" />
					</html:text></td>
				</TR>
				<TR>
					<TD class="text pLeft15" align="center" width="174"><bean:message
						bundle="view" key="warrantyview.productname" /></TD>
					<td align="left" width="186"><html:text property="productname"
						name="DPEditWarrantyFormBean" readonly="true">
						<bean:write name="DPEditWarrantyFormBean" property="productname" />
					</html:text></td>
				</TR>
				<tr><td width="174"></td></tr>
				<TR>
					<td colspan="1" align="right" width="174"></td>

					<td colspan="1" align="left" width="186"><input type="button"
						class="subbig" value="Submit" onclick="return formSubmit();"><input
						type="button" class="subbig" value="Reset"
						onclick="return validate2();"></td>
				</TR>
			</TABLE>
			</td>
		</TR>
		<TR align="center" bgcolor="4477bb" valign="top">
		<TD colspan="2" height="17" align="center"><jsp:include
			page="../common/footer.jsp" flush="" /></TD>
	</TR>
	</TABLE>
</html:form>
</BODY>
</html:html>