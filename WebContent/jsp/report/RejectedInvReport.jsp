<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>

<%@page import="com.ibm.dp.beans.RejectedInvBean"%>

<%@page import="com.ibm.dpmisreports.common.SelectionCollection"%><html:html>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="<%=request.getContextPath()%>/theme/Master.css"
	rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<TITLE></TITLE>
<SCRIPT language="JavaScript"
	src="<%=request.getContextPath()%>/scripts/Cal.js"
	type="text/javascript"></SCRIPT>

<SCRIPT language="javascript" type="text/javascript">
 
function exportDataToExcel(){
var selectedAnswer= document.getElementById("monId");
var i= selectedAnswer.selectedIndex;
var a= selectedAnswer.options[i].text;
document.forms[0].action="rejectedInvoiceAction.do?methodName=downloadInvoiceList&monthId="+a ;
document.forms[0].submit();
	}
</SCRIPT>
</HEAD>


<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"	bgcolor="#FFFFFF" 
	marginheight="0">
<TABLE cellspacing="0" cellpadding="0" border="0" height="100%"	valign="top">
<html:form  action="/rejectedInvoiceAction" >
<TABLE border="0" cellpadding="5" cellspacing="0" class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/InvoiceView.gif"
						width="505" height="22" alt=""></TD>
				</TR>
				<TABLE align="center" class="mLeft5" width="100%">

				<logic:empty name="RejectedInvBean" property="invList">
				<tr>
				<td>	
				<html:select styleId="monId" property="monthId" name="RejectedInvBean" tabindex="14" styleClass="w130" style="width:175; height:25px;">		
							<html:optionsCollection
								property="monthList" label="strText" value="strValue" />
				</html:select></FONT>
				</td>
				<td align="left" colspan="12"><INPUT class="submit"
							onclick="exportDataToExcel();" type="button" name="export"
							value="Export to Excel"></td>
				</tr>
			
				
				</logic:empty>
				<logic:notEmpty property="invList" name="RejectedInvBean">
					<TR align="center">
						
					</TR>
					</logic:notEmpty>
				</TABLE>
</TABLE>
</html:form>
</TABLE>
</BODY>
</html:html>
