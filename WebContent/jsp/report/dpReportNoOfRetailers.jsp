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
//			"attachment;filename=NoOfRetailerReport.xls");
%>
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<title><bean:message bundle="view" key="application.title" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript" language="javascript">
	function exportDataToExcel(){
		var url="reportActionExcel.do?methodName=getReportData&reportId=6";
		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();
	}
</script>
</HEAD>
<% 
    String rowDark ="#FFE4E1";
    String rowLight = "#FFFFFF";
%>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<table>
	<tr>
	<TD colspan="4" class="text">
		<IMG src="<%=request.getContextPath()%>/images/noOfRetailersReport.gif"
		width="505" height="22" alt="">
	</TD>
	</tr>
	<logic:messagesPresent message="true">
		<tr align="center">
			<td colspan="4" align="center">			
				<html:messages id="msg" property="NO_RECORD" bundle="hboView" message="true">
					<font color="red" size="3"><strong><bean:write name="msg"/></strong></font>
				</html:messages>
			</td>
		</tr>

	</logic:messagesPresent>
	</table>		
    <html:form action="reportAction">
		<table cellspacing="0" cellpadding="0" border="1" width="100%" valign="top">	
					<logic:notEmpty name="DPReportForm" property="reportDataList">
					<TR align="center">
						<TD align="left" colspan="12"><INPUT class="submit"
							onclick="exportDataToExcel();" type="button" name="export"
							value="Export to Excel" tabindex="8"></TD>
					</TR>
						<tr align="center" bgcolor="#CD0504" style="color:#ffffff !important;">
							<td align="center"><span
								class="white10heading mLeft5 mTop5"><bean:message bundle="view"
								key="report.hub" /></span></td>
							<td align="center" ><span
								class="white10heading mLeft5 mTop5"><bean:message bundle="view"
								key="report.circle" /></span></td>
							<td align="center" ><span
								class="white10heading mLeft5 mTop5"><bean:message bundle="view"
								key="report.zone" /></span></td>
							<td align="center" ><span
								class="white10heading mLeft5 mTop5"><bean:message bundle="view"
								key="report.distributor" /></span></td>
							<td align="center" ><span
								class="white10heading mLeft5 mTop5"><bean:message bundle="view"
								key="report.prodcat" /></span></td>		
							<td align="center" ><span
								class="white10heading mLeft5 mTop5"><bean:message bundle="view"
								key="report.prodtype" /></span></td>				
							<td align="center" ><span
								class="white10heading mLeft5 mTop5"><bean:message bundle="view"
								key="report.bucketname" /></span></td>									
							<td align="center" ><span
								class="white10heading mLeft5 mTop5"><bean:message bundle="view"
								key="report.noofretailers" /></span></td>	
					</tr>

						<logic:iterate id="report" name="DPReportForm"
							property="reportDataList" indexId="i">
						<logic:equal value="ZZZTOTAL" name="report" property="prodCat">	
							<tr bgcolor="gray">
						<td colspan="7" align="center"><b>Total</b></td>
						<td align="center"><bean:write name="report" property="noOfRetailers"/></td>
					</tr>
					</logic:equal>
				<logic:notEqual value="ZZZTOTAL" name="report" property="prodCat">	
						<TR BGCOLOR="#FCE8E6">
								<td class="height19"><bean:write name="report"
									property="hub" /></td>
								<td class="height19"><bean:write name="report"
									property="circle" /></td>
								<td class="height19"><bean:write name="report"
									property="zone" /></td>
								<td class="height19"><bean:write name="report"
									property="distributorName" /></td>
								<td class="height19"><bean:write name="report"
									property="prodCat" /></td>	
								<td class="height19"><bean:write name="report"
									property="prodType" /></td>	
								<td class="height19"><bean:write name="report"
									property="bucketName" /></td>									
								<td class="height19"><bean:write name="report"
									property="noOfRetailers" /></td>
							</tr>
							</logic:notEqual>
						</logic:iterate>
						</logic:notEmpty>
					</table>
			</html:form>		
</BODY>
</html:html>