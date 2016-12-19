<%try{%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>	
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-disposition","attachment;filename=STBWiseSerializedStockReport.xls");
	response.setHeader("Cache-Control", "must-revalidate");
	response.setHeader( "Pragma", "public" );
%>
<html:html>
<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE>DP Reports</TITLE>
</HEAD>																		
<BODY>

	<table border="1">
		<tr>
					<td class="colhead" align="center"><b>S.NO.</b></td>
					<td class="colhead" align="center"><b>Distributor Login ID</b></td>
					<td class="colhead" align="center"><b>Distributor Name</b></td>
					<td class="colhead" align="center"><b>TSM Name</b></td>
					<td class="colhead" align="center"><b>Circle</b></td>		
					<td class="colhead" align="center"><b>STB Type</b></td>			
					<td class="colhead" align="center"><b>STB Serial No.</b></td>			
					<td class="colhead" align="center"><b>STB Status</b></td>
					<td class="colhead" align="center"><b>PR No.</b></td>
					<td class="colhead" align="center"><b>PO No.</b></td>
					<td class="colhead" align="center"><b>Stock Acceptance Date</b></td>
					<td class="colhead" align="center"><b>FSE ID</b></td>
					<td class="colhead" align="center"><b>FSE Name</b></td>
					<td class="colhead" align="center"><b>FSE Allocation Date</b></td>
					<td class="colhead" align="center"><b>Retailer ID</b></td>
					<td class="colhead" align="center"><b>Retailer Name</b></td>
					<td class="colhead" align="center"><b>Retailer Allocation Date</b></td>				
					
		</tr>
		
		<logic:notEmpty name="STBWiseSerializedStockReportFormBean" property="reportStockDataList">
 		<logic:iterate id="reportData" name="STBWiseSerializedStockReportFormBean" property="reportStockDataList">
				<tr>					
					<td align="center"><bean:write name="reportData" property="SNO"/></td>
					<td align="center"><bean:write name="reportData" property="dist_login"/></td>
					<td align="center"><bean:write name="reportData" property="distName"/></td>
					<td align="center"><bean:write name="reportData" property="tsmName"/></td>
					<td align="center"><bean:write name="reportData" property="circle"/></td>
					<td align="center"><bean:write name="reportData" property="product_name"/></td>
					<td align="center"><bean:write name="reportData" property="stbSerialNo"/></td>
					<td align="center"><bean:write name="reportData" property="stbStatus"/></td>
					<td align="center"><bean:write name="reportData" property="PRNO"/></td>
					<td align="center"><bean:write name="reportData" property="PONO"/></td>		
					<td align="center"><bean:write name="reportData" property="stockAcceptanceDate"/></td>				
					<td align="center"><bean:write name="reportData" property="fseID"/></td>	
					<td align="center"><bean:write name="reportData" property="fseName"/></td>	
					<td align="center"><bean:write name="reportData" property="fseAllocationDate"/></td>				
					<td align="center"><bean:write name="reportData" property="retailerID"/></td>
					<td align="center"><bean:write name="reportData" property="retailer_name"/></td>		
					<td align="center"><bean:write name="reportData" property="retailerAllocationDate"/></td>		
				</tr>
			</logic:iterate>
			</logic:notEmpty>
		
 	</table>
 
<P><BR>
</P>
</BODY>
</html:html>
 <%}
 catch(Exception e){e.printStackTrace();}%>