
<%try{%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import = "java.util.ArrayList,java.util.HashMap"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import = "java.util.*" %>
<html:html>
<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-Disposition",
			"attachment;filename=RetailerLapuData.xls");
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<TITLE>LAPU Data</TITLE>
</HEAD>																		
<BODY>

 <logic:notEmpty property="lapuDataList" name="DPRetailerLapuDataBean">	
 	<table align="center" border="1">
 				<tr bgcolor="gray">
					<td class="colhead" align="center"><bean:message bundle="view" key="retailer.loginid" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="retailer.accountname" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="retailer.mobile1" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="retailer.mobile2" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="retailer.mobile3" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="retailer.mobile4" /></td>
					<td class="colhead" align="center"><bean:message bundle="view" key="retailer.lapu" /></td>
				</tr>
			<logic:iterate id="lapuDataList" name="DPRetailerLapuDataBean" property="lapuDataList" indexId="m">
				<tr>
					<td align="center"><bean:write name="lapuDataList" property="loginId"/></td>
					<td align="center"><bean:write name="lapuDataList" property="accountName"/></td>
					<td align="center" style="mso-number-format:'\@'"><bean:write name="lapuDataList" property="mobile1"/></td>
					<td align="center" style="mso-number-format:'\@'"><bean:write name="lapuDataList" property="mobile2"/></td>
					<td align="center" style="mso-number-format:'\@'"><bean:write name="lapuDataList" property="mobile3"/></td>
					<td align="center" style="mso-number-format:'\@'"><bean:write name="lapuDataList" property="mobile4"/></td>
					<td align="center" style="mso-number-format:'\@'"><bean:write name="lapuDataList" property="lapuNumber"/></td>
				</tr>
			</logic:iterate>
 	</table>
 </logic:notEmpty>
<P><BR>
</P>
</BODY>
</html:html>
 <%}catch(Exception e){e.printStackTrace();}%>
 
 
 
 <%--
 <%try{%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>	
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import = "java.util.ArrayList,java.util.HashMap"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.ibm.dp.service.RetailerLapuDataService" %>
<%@ page import = "com.ibm.dp.service.impl.RetailerLapuDataServiceImpl" %>
<%@ page import = "com.ibm.dp.dto.RetailerLapuDataDto" %>


<%@page import="com.ibm.utilities.Utility"%>
<html:html>
<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-Disposition",
			"attachment;filename=RetailerLapuData123.xls");
%>

<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE>DP Reports</TITLE>
</HEAD>																		
<BODY>

<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
	String [][] excelData = null;
	RetailerLapuDataService excelBulkDownload = new RetailerLapuDataServiceImpl();
	List  retailerLapuDataList = excelBulkDownload.getAllRetailerLapuData();	
	Iterator itr =retailerLapuDataList.iterator();
	excelData =  new String [retailerLapuDataList.size()+2][retailerLapuDataList.size()+2];

	excelData[0][0] = "Login ID" ;
	excelData[0][1] = "Account Name" ;
	excelData[0][2] = "Mobile 1" ;
	excelData[0][3] = "Mobile 2" ;
	excelData[0][4] = "Mobile 3" ;
	excelData[0][5] = "Mobile 4" ;
	excelData[0][6] = "Lapu Mobile Number" ;
	
	int count = 1;
	int colNumber = 7;
	while(itr.hasNext()){
		
		RetailerLapuDataDto dto =	(RetailerLapuDataDto) itr.next();
		excelData[count][0] = dto.getLoginId();
		excelData[count][1] = dto.getAccountName();
		excelData[count][2] = dto.getMobile1();
		excelData[count][3] = dto.getMobile2();
		excelData[count][4] = dto.getMobile3();
		excelData[count][5] = dto.getMobile4();
		excelData[count][6] = dto.getLapuNumber();
		
		count++;
		
	}
	Utility.writeDataToExcelFile("C:\\temp\\RetailerLapuData123.xls",excelData,colNumber);
%>	
	
	
<P><BR>
</P>
</BODY>
</html:html>
 <%}
 catch(Exception e){e.printStackTrace();}%>
 
  --%>