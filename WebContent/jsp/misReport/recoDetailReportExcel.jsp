<%try{%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>	
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import = "java.util.ArrayList,java.util.HashMap"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import = "java.util.*" %>
<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-disposition","attachment;filename=RecoDetailReport.xls");
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

<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>
	<table border="1">
		<tr>
					<td rowspan="2" class="colhead" align="center"><b>Distributor OLM  Id</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor Name </b></td>
					<td rowspan="2" class="colhead" align="center"><b>Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Product Type</b></td>
					<td colspan="2" class="colhead" align="center"><b>Opening In-Transit</b></td>
					<td colspan="5" class="colhead" align="center"><b>Opening Stock</b></td>
					<td colspan="5" class="colhead" align="center"><b>Received</b></td>
					<td colspan="8" class="colhead" align="center"><b>Returned</b></td>
					
					<td rowspan="2" class="colhead" align="center"><b>Activation</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Inventory Change  In</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Inventory Change Out</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Adjustment</b></td>
					<td colspan="5" class="colhead" align="center"><b>Closing Stock</b></td>
					<td colspan="2" class="colhead" align="center"><b>Closing In-Transit</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Status</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Remarks</b></td>	
					<td rowspan="2" class="colhead" align="center"><b></b></td>	
					<td rowspan="2" class="colhead" align="center"><b></b></td>	
					<td rowspan="2" class="colhead" align="center"><b>Opening</b></td>	
					<td rowspan="2" class="colhead" align="center"><b>Dispatch</b></td>		
					<td rowspan="2" class="colhead" align="center"><b>Return</b></td>		
					<td rowspan="2" class="colhead" align="center"><b>IPD</b></td>		
					<td rowspan="2" class="colhead" align="center"><b>IPR</b></td>		
					<td rowspan="2" class="colhead" align="center"><b>Churn Rec</b></td>		
					<td rowspan="2" class="colhead" align="center"><b>UPG Rec</b></td>	
					<td rowspan="2" class="colhead" align="center"><b>Activation</b></td>	
				    <td rowspan="2" class="colhead" align="center"><b>Inventory Change</b></td>	
					<td rowspan="2" class="colhead" align="center"><b>Adjustment</b></td>	
					<td rowspan="2" class="colhead" align="center"><b>Derived Closing</b></td>		
					<td rowspan="2" class="colhead" align="center"><b>System CL</b></td>		
					<td rowspan="2" class="colhead" align="center"><b>Variance</b></td>		
															
					
		</tr>
		<tr>		
					<td class="colhead" align="center"><b>Pending PO</b></td>		
					<td class="colhead" align="center"><b>Pending DC</b></td>		
					
					<td class="colhead" align="center"><b>Serialized</b></td>					
					<td class="colhead" align="center"><b>Non Serialized</b></td>
					<td class="colhead" align="center"><b>Defective</b></td>
					<td class="colhead" align="center"><b>Upgrade</b></td>
					<td class="colhead" align="center"><b>Churn</b></td>
					
					
					<td class="colhead" align="center"><b>From WH</b></td>					
					<td class="colhead" align="center"><b>Inter-SSD OK</b></td>
					<td class="colhead" align="center"><b>Inter-SSD Reverse</b></td>
					<td class="colhead" align="center"><b>Upgrade</b></td>
					<td class="colhead" align="center"><b>Churn</b></td>
					
					<td class="colhead" align="center"><b>OK Stock</b></td>				
					<td class="colhead" align="center"><b>Inter-SSD OK</b></td>
					<td class="colhead" align="center"><b>Inter-SSD Reverse</b></td>
					<td class="colhead" align="center"><b>DOA(BI)</b></td>
					<td class="colhead" align="center"><b>DOA(AI)</b></td>
					<td class="colhead" align="center"><b>C2S</b></td>
					<td class="colhead" align="center"><b>Chuurn</b></td>
					<td class="colhead" align="center"><b>Defective Swap</b></td>
					
					<td class="colhead" align="center"><b>Serialized</b></td>					
					<td class="colhead" align="center"><b>Non Serialized</b></td>
					<td class="colhead" align="center"><b>Defective</b></td>
					<td class="colhead" align="center"><b>Upgrade</b></td>
					<td class="colhead" align="center"><b>Churn</b></td>
					
					<td class="colhead" align="center"><b>Pending PO</b></td>		
					<td class="colhead" align="center"><b>Pending DC</b></td>		
					
		</tr>
		
		
		<logic:notEmpty name="RecoPeriodConfFormBean" property="reportRecoDataList">
		
 		<logic:iterate id="reportData" name="RecoPeriodConfFormBean" property="reportRecoDataList">
				<tr>					
				<td align="center"><bean:write name="reportData" property="distributorId"/></td>
					<td align="center"><bean:write name="reportData" property="distributorName"/></td>
					<td align="center"><bean:write name="reportData" property="type"/></td>
					<td align="center"><bean:write name="reportData" property="productType"/></td>
					
					<td align="center"><bean:write name="reportData" property="penPOOpen"/></td>
					<td align="center"><bean:write name="reportData" property="penDcOpen"/></td>
					
					<td align="center"><bean:write name="reportData" property="serOpening"/></td>
					<td align="center"><bean:write name="reportData" property="nsrOpening"/></td>
					<td align="center"><bean:write name="reportData" property="defOpen"/></td>
					<td align="center"><bean:write name="reportData" property="upgardeOpen"/></td>
					<td align="center"><bean:write name="reportData" property="churnOpen"/></td>
					<td align="center"><bean:write name="reportData" property="receivedWH"/></td>
					<td align="center"><bean:write name="reportData" property="receivedInterSSDOk"/></td>
					<td align="center"><bean:write name="reportData" property="receivedInterSSDDef"/></td>
					<td align="center"><bean:write name="reportData" property="receivedUpgrade"/></td>
					<td align="center"><bean:write name="reportData" property="receivedChurn"/></td>
					<td align="center"><bean:write name="reportData" property="returnedFresh"/></td>
					<td align="center"><bean:write name="reportData" property="returnedInterSSDOk"/></td>
					<td align="center"><bean:write name="reportData" property="returnedInterSSDDef"/></td>
					<td align="center"><bean:write name="reportData" property="returnedDOABI"/></td>
					<td align="center"><bean:write name="reportData" property="returnedDOAAI"/></td>
					<td align="center"><bean:write name="reportData" property="returnedC2C"/></td>
					<td align="center"><bean:write name="reportData" property="returnedChurn"/></td>
					<td align="center"><bean:write name="reportData" property="returnedSwap"/></td>
					<td align="center"><bean:write name="reportData" property="totalActivation"/></td>
					<td align="center"><bean:write name="reportData" property="inventoryChange"/></td>
				    <td align="center"><bean:write name="reportData" property="inverntoryChangeout"/></td>
					<td align="center"><bean:write name="reportData" property="adjustment"/></td>
					<td align="center"><bean:write name="reportData" property="serClosing"/></td>
					<td align="center"><bean:write name="reportData" property="nsrClosing"/></td>
					<td align="center"><bean:write name="reportData" property="defClosing"/></td>
					<td align="center"><bean:write name="reportData" property="upgardeClosing"/></td>
					<td align="center"><bean:write name="reportData" property="churnClosing"/></td>
					<td align="center"><bean:write name="reportData" property="penPOClosing"/></td>
					<td align="center"><bean:write name="reportData" property="penDcClosing"/></td>
					<td align="center"><bean:write name="reportData" property="status"/></td>
					<td align="center"><bean:write name="reportData" property="remarks"/></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"><bean:write name="reportData" property="opening"/></td>
					<td align="center"><bean:write name="reportData" property="dispatch"/></td>
					<td align="center"><bean:write name="reportData" property="returnVal"/></td>
					<td align="center"><bean:write name="reportData" property="distPdis"/></td>
					<td align="center"><bean:write name="reportData" property="distPrec"/></td>
					<td align="center"><bean:write name="reportData" property="receivedUpgrade"/></td>
					<td align="center"><bean:write name="reportData" property="receivedChurn"/></td>
					<td align="center"><bean:write name="reportData" property="totalActivation"/></td>
					<td align="center"><bean:write name="reportData" property="inventoryChange"/></td>
					<td align="center"><bean:write name="reportData" property="adjustment"/></td>
					<td align="center"><bean:write name="reportData" property="derivedClosing"/></td>
					<td align="center"><bean:write name="reportData" property="systemCL"/></td>
					<td align="center"><bean:write name="reportData" property="variance"/></td>
		
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
  <!-- Neetika BFR 16 on 16 Aug -->
 <%
try{
			
			session.setAttribute("recoDetailReport", "done");
    }
catch (Exception e) 
	{
   				System.out.println("Error: "+e);
    }
 %>