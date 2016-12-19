<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
//	response.setContentType("application/vnd.ms-excel");
//	response.setHeader("content-Disposition",
//			"attachment;filename=StockReport.xls");
%>
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<title><bean:message bundle="view" key="application.title" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<LINK href="<%=request.getContextPath()%>/theme/text.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript" language="javascript">
	function exportDataToExcel(){
		var url="reportActionExcel.do?methodName=getReportData&reportId=3";
		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();
	}
</script>
</HEAD>


<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onkeypress="return checkKeyPressed();" onload="setSearchControlDisabled()">
<table>
<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/stockReport.gif"
		width="505" height="22" alt="">
	</TD>
</tr>
<logic:messagesPresent message="true">
	<tr align="center">
		<td colspan="4" align="center">			
			<html:messages id="msg" property="NO_RECORD" bundle="hboView" message="true">
				<font color="red" size="2"><strong><bean:write name="msg"/></strong></font>
			</html:messages>
		</td>
	</tr>
</logic:messagesPresent>
</table>
	<table cellspacing="0" cellpadding="0" border="0" width="100%" height="100%" valign="top">
<% 
    String rowDark ="#FFE4E1";
    String rowLight = "#FFFFFF";
%>		
    <html:form action="reportAction">
					<logic:notEmpty name="DPReportForm" property="reportDataList">
										<TR align="center">
						<TD align="left" colspan="12"><INPUT class="submit"
							onclick="exportDataToExcel();" type="button" name="export"
							value="Export to Excel" tabindex="8"></TD>
					</TR>
						<tr align="center" bgcolor="#104e8b">
<%--							<td align="center" bgcolor="#cd0504">
								<bean:message bundle="view"
								key="report.SNo" /></td>	--%>
								<td></td>
							<td align="center" bgcolor="#cd0504">
								<bean:message bundle="view" key="report.hub" /></td>
							<td align="center" bgcolor="#cd0504">
								<bean:message bundle="view" key="report.circle" /></td>
							<td align="center" bgcolor="#cd0504">
								<bean:message bundle="view" key="report.zone" /></td>
							<td align="center" bgcolor="#cd0504">
								<bean:message bundle="view" key="report.distributor" /></td>
							<td align="center" bgcolor="#cd0504">
								<bean:message bundle="view" key="report.retCode" /></td>
							<td align="center" bgcolor="#cd0504">
								<bean:message bundle="view" key="report.retName" /></td>
							<td align="center" bgcolor="#cd0504">
								<bean:message bundle="view" key="report.fseName" /></td>
							<td align="center" bgcolor="#cd0504">
								<bean:message bundle="view" key="report.productCat" /></td>		
							<td align="center" bgcolor="#cd0504">
								<bean:message bundle="view" key="report.productType" /></td>				
							<td align="center" bgcolor="#cd0504">
								<bean:message bundle="view" key="report.closingStock" /></td>									
							<td align="center" bgcolor="#cd0504">
								<bean:message bundle="view" key="report.stockInDays" /></td>	
								<td align="center"></td>
					</tr>
						<logic:iterate id="report" name="DPReportForm"
							property="reportDataList" indexId="i">
							<logic:equal value="ZZZTOTAL" name="report" property="prodCat">
					<tr>
						<td></td>
						<td colspan="9" align="center" bgcolor="#CCCCCC"><b>Total</b></td>
						<td align="center" bgcolor="#CCCCCC"><bean:write name="report" property="closingStock"/></td>
						<td align="center" bgcolor="#CCCCCC"><bean:write name="report" property="stockNoOfDays"/></td>
						<td></td>
					</tr>
				</logic:equal>
				<logic:notEqual value="ZZZTOTAL" name="report" property="prodCat">
				<TR BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>'>
<%-- 				<td><c:out
					value="${i+1}"></c:out></td>	--%>
					<td align="center"></td>
					<td align="center"><bean:write name="report" property="hub" /></td>
					<td align="center"><bean:write name="report" property="circle" /></td>
					<td align="center"><bean:write name="report" property="zone" /></td>
					<td align="center"><bean:write name="report" property="distributorName" /></td>
					<td align="center"><bean:write name="report" property="retmsisdn" /></td>
					<td align="center"><bean:write name="report" property="retName" /></td>			
					<td align="center"><bean:write name="report" property="fseName" /></td>	
					<td align="center"><bean:write name="report" property="prodCat" /></td>	
					<td align="center"><bean:write name="report" property="prodType" /></td>	
					<td align="center"><bean:write name="report" property="closingStock" /></td>									
					<td align="center"><bean:write name="report" property="stockNoOfDays" /></td>	
					<td align="center"></td>
				</tr>
				</logic:notEqual>
			</logic:iterate>
			</logic:notEmpty>
			</html:form>
		</table>
</BODY>
</html:html>