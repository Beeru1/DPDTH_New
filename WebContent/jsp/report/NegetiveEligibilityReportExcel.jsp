<%try{%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>	
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-disposition","attachment;filename=NegetiveEligibilityReport.xls");
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
					<td class="colhead" align="center"><b>Distributor Name</b></td>
					<td class="colhead" align="center"><b>Distributor OLMID</b></td>
					<td class="colhead" align="center"><b>Eligibility Amount</b></td>
					<td class="colhead" align="center"><b>0-30(Ageing Days)</b></td>
					<td class="colhead" align="center"><b>31-60(Ageing Days)</b></td>					
					<td class="colhead" align="center"><b>61-90(Ageing Days)</b></td>
					<td class="colhead" align="center"><b>91-120(Ageing Days)</b></td>
					<td class="colhead" align="center"><b>120+(Ageing Days)</b></td>		
		</tr>
		
		<logic:notEmpty name="NegetiveEligibilityReportBean" property="reportStockDataList">
 		<logic:iterate id="reportData" name="NegetiveEligibilityReportBean" property="reportStockDataList">
				<tr>									
					<td align="center"><bean:write name="reportData" property="distName"/></td>
					<td align="center"><bean:write name="reportData" property="distOlmID"/></td>
					<td align="center"><bean:write name="reportData" property="eligibleAmt"/></td>
					<td align="center"><bean:write name="reportData" property="firstThirty"/></td>
					<td align="center"><bean:write name="reportData" property="secondThirty"/></td>
					<td align="center"><bean:write name="reportData" property="thirdThirty"/></td>
					<td align="center"><bean:write name="reportData" property="fourthThirty"/></td>
					<td align="center"><bean:write name="reportData" property="fifthThirty"/></td>
							
				<br>
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
			
			session.setAttribute("NegEligibilityReport", "done");
    }
catch (Exception e) 
	{
   				System.out.println("Error: "+e);
    }
 %>