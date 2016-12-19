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
	response.setHeader("content-disposition","attachment;filename=ProductReport.xls");
	response.setHeader("Cache-Control", "must-revalidate");
	response.setHeader( "Pragma", "public" );
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<TITLE>DP Reports</TITLE>
</HEAD>																		
<BODY>

<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>
	<table border="1">
		<tr>
					<td class="colhead" align="center"><b>Circle Name</b></td>
					<td class="colhead" align="center"><b>Product Code</b></td>
					<td class="colhead" align="center"><b>Product Name</b></td>
					<td class="colhead" align="center"><b>Card Group Name</b></td>
					<td class="colhead" align="center"><b>Product Type</b></td>
					<td class="colhead" align="center"><b>MRP</b></td>
					<td class="colhead" align="center"><b>Basic Value</b></td>
					<td class="colhead" align="center"><b>Recharge Value</b></td>
					<td class="colhead" align="center"><b>Activation</b></td>															
					<td class="colhead" align="center"><b>Service Tax</b></td>
					<td class="colhead" align="center"><b>Cess</b></td>
					<td class="colhead" align="center"><b>Sec Cess Tax</b></td>
					<td class="colhead" align="center"><b>Dist Margin</b></td>
					<td class="colhead" align="center"><b>Dist Price</b></td>
					<td class="colhead" align="center"><b>Effective Date</b></td>
					<td class="colhead" align="center"><b>CreationDate</b></td>
					<td class="colhead" align="center"><b>Status</b></td>
		</tr>
		
		<logic:notEmpty name="DPViewProductFormBean" property="prodList">
		<logic:iterate id="reportData" name="DPViewProductFormBean" property="prodList" indexId="m">
				<tr>
					<td align="center"><bean:write name="reportData" property="dpCircleName"/></td>
					<td align="center"><bean:write name="reportData" property="prodCode"/></td>
					<td align="center"><bean:write name="reportData" property="prodName"/></td>
					<td align="center"><bean:write name="reportData" property="cardGrpName"/></td>
					<td align="center"><bean:write name="reportData" property="prodType"/></td>
					<td align="center"><bean:write name="reportData" property="dpMrp"/></td>
					<td align="center"><bean:write name="reportData" property="basicValue"/></td>
					<td align="center"><bean:write name="reportData" property="rechargeValue"/></td>
					<td align="center"><bean:write name="reportData" property="dpActivation"/></td>															
					<td align="center"><bean:write name="reportData" property="serviceTax"/></td>
					<td align="center"><bean:write name="reportData" property="cess"/></td>
					<td align="center"><bean:write name="reportData" property="cessTax"/></td>
					<td align="center"><bean:write name="reportData" property="distMargin"/></td>
					<td align="center"><bean:write name="reportData" property="distPrice"/></td>
					<td align="center"><bean:write name="reportData" property="effectiveDate"/></td>
					<td align="center"><bean:write name="reportData" property="creationDate"/></td>
					<td align="center"><bean:write name="reportData" property="dpStatus"/></td>

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
 <!-- Neetika BFR 16 16 Aug -->
  <%
try{
			
			//session.setAttribute("AccountManagementActivity", "done");
    }
catch (Exception e) 
	{
   				System.out.println("Error: "+e);
    }
 %>