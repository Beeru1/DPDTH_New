<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>	
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import = "java.util.*" %>
<%
	String downloadFilename = (String)request.getAttribute("buttonName");
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-disposition","attachment;filename="+downloadFilename+".xls");
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
	<bean:define id="tabId" name="DistRecoBean" property="tabId"/>
 		<bean:define id="columnId" name="DistRecoBean" property="columnId"/>
 		<%System.out.println("======== in jsp :tabId :"+tabId+"  columnId :"+columnId); %>
	
	 
	<logic:equal name="DistRecoBean" property="tabId" value="1"><!--  opening stock -->
		<logic:equal name="DistRecoBean" property="columnId" value="1"><!--  Pending PO -->
		<tr>
					<td rowspan="2" class="colhead" align="center"><b>S.No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor OLM ID</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Tsm Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Circle Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>PO No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>PO Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>DC No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>DC Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Dispatched Qty per PO</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Serial No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>PO Status</b></td>
		</tr>
		</logic:equal>
		<logic:equal name="DistRecoBean" property="columnId" value="2"><!--  Pending DC -->
		<tr>
					<td rowspan="2" class="colhead" align="center"><b>S.No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor OLM ID</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Tsm Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Circle Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>DC No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>DC Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Collection Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Dispatched Qty per DC</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Serial No.</b></td>
					<td rowspan="2" class="colhead" align="center"><b>DC STB Status</b></td>
		</tr>
		</logic:equal>
		<logic:equal name="DistRecoBean" property="columnId" value="3"><!--  Serialized Stock -->
				<tr>
					<td rowspan="2" class="colhead" align="center"><b>S.No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor OLM ID</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Tsm Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Circle Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>PR No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>PO No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Product Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Serial No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor Purchase Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>FSE Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>FSE Purchase Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Retailer Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Retailer Purchase Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Status Description</b></td>
					
				</tr>		
		</logic:equal>
		<logic:equal name="DistRecoBean" property="columnId" value="4"><!--  Defective Stock -->
				<tr>
					<td rowspan="2" class="colhead" align="center"><b>S.No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor OLM ID</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Tsm Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Circle Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Collection Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Collection Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Inventory Change Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Recovered STB No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Installed STB No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Defect Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Ageing</b></td>
				</tr>		
		</logic:equal>
			<logic:equal name="DistRecoBean" property="columnId" value="5"><!--  Upgrade Stock -->
				<tr>
					<td rowspan="2" class="colhead" align="center"><b>S.No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor OLM ID</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Tsm Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Circle Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Collection Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Collection Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Inventory Change Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Recovered STB No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Installed STB No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Defect Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Ageing</b></td>
				</tr>		
		</logic:equal>
		<logic:equal name="DistRecoBean" property="columnId" value="6"><!--  Churned Stock -->
				<tr>
					<td rowspan="2" class="colhead" align="center"><b>S.No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor OLM ID</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Tsm Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Circle Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Collection Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Collection Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Recovered STB No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Ageing</b></td>
				</tr>		
		</logic:equal>
		</logic:equal><!-- End of Opening stock -->
		<logic:equal name="DistRecoBean" property="tabId" value="2"><!--  Received stock -->
			<logic:equal name="DistRecoBean" property="columnId" value="1"><!-- Rec. WH -->
				<tr>
				
					<td rowspan="2" class="colhead" align="center"><b>S.No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor OLM ID</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Tsm Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Circle Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>PO No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>PO Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>DC No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>DC Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Serial No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>PO Status</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Status</b></td>
				
					<td rowspan="2" class="colhead" align="center"><b>Last PO Action Date</b></td>
				</tr>
			</logic:equal>
			<logic:equal name="DistRecoBean" property="columnId" value="2"> <!-- Received Inter ssd ok -->
				<tr>
					<td rowspan="2" class="colhead" align="center"><b>S.No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>From Distributor ID</b></td>
					<td rowspan="2" class="colhead" align="center"><b>From Distributor Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>To Distributor ID</b></td>
					<td rowspan="2" class="colhead" align="center"><b>To Distributor Name </b></td>
					<td rowspan="2" class="colhead" align="center"><b>Initiator Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Transfer Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Circle</b></td>
					<td rowspan="2" class="colhead" align="center"><b>DC No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Serial No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Initiation Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Transfer Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Acceptance Date</b></td>
				</tr>
			</logic:equal>
			<logic:equal name="DistRecoBean" property="columnId" value="3"> <!-- Received Inter ssd defective -->
				<tr>
					<td rowspan="2" class="colhead" align="center"><b>S.No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>From Distributor ID</b></td>
					<td rowspan="2" class="colhead" align="center"><b>From Distributor Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>To Distributor ID</b></td>
					<td rowspan="2" class="colhead" align="center"><b>To Distributor Name </b></td>
					<td rowspan="2" class="colhead" align="center"><b>Initiator Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Collection Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Collection Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Inv. Change Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Recovered STB No</b></td>
				
					<td rowspan="2" class="colhead" align="center"><b>Defect Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Initiation Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Transfer Date</b></td>					
				</tr>
			</logic:equal>
				<logic:equal name="DistRecoBean" property="columnId" value="4"> <!-- Received Upgrade -->
				<tr>
					<td rowspan="2" class="colhead" align="center"><b>S.No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor OLM ID</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Tsm Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Circle Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Collection Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Collection Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Inventory Change Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Recovered STB No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Installed STB No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Defect Type</b></td>
				</tr>
			</logic:equal>
			<logic:equal name="DistRecoBean" property="columnId" value="5"> <!-- Received chured -->
				<tr>
					<td rowspan="2" class="colhead" align="center"><b>S.No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor OLM ID</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Tsm Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Circle Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Collection Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Collection Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Recovered STB No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Ageing</b></td>
				</tr>
			</logic:equal>
		</logic:equal>
		<logic:equal name="DistRecoBean" property="tabId" value="3"><!--  Returned stock -->
		<%if(columnId.equals("4") || columnId.equals("5") || columnId.equals("6") || columnId.equals("7") || columnId.equals("8")){ %>
			<tr>
					<td rowspan="2" class="colhead" align="center"><b>S.No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor OLM ID</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Tsm Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Circle Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Collection Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>DC No.</b></td>
					<td rowspan="2" class="colhead" align="center"><b>DC Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Serial No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>WH Received Date</b></td>
				</tr>
				<%} %>
				<logic:equal name="DistRecoBean" property="columnId" value="1">
			<tr>
					<td rowspan="2" class="colhead" align="center"><b>S.No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor OLM ID</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Tsm Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Circle Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Collection Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>DC No.</b></td>
					<td rowspan="2" class="colhead" align="center"><b>DC Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>DC STATUS</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Serial No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>DC STB STATUS</b></td>
					<td rowspan="2" class="colhead" align="center"><b>WH Received Date</b></td>
					</tr>
			</logic:equal>
			<logic:equal name="DistRecoBean" property="columnId" value="2">
			<tr>
					<td rowspan="2" class="colhead" align="center"><b>S.No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>From Distributor ID</b></td>
					<td rowspan="2" class="colhead" align="center"><b>From Distributor Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>To Distributor ID</b></td>
					<td rowspan="2" class="colhead" align="center"><b>To Distributor Name </b></td>
					<td rowspan="2" class="colhead" align="center"><b>Initiator Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Transfer Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Circle</b></td>
					<td rowspan="2" class="colhead" align="center"><b>DC No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Serial No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Initiation Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Transfer Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Acceptance Date</b></td>
				</tr>
			</logic:equal>
			<logic:equal name="DistRecoBean" property="columnId" value="3">
			<tr>
					<td rowspan="2" class="colhead" align="center"><b>S.No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>From Distributor ID</b></td>
					<td rowspan="2" class="colhead" align="center"><b>From Distributor Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>To Distributor ID</b></td>
					<td rowspan="2" class="colhead" align="center"><b>To Distributor Name </b></td>
					<td rowspan="2" class="colhead" align="center"><b>Initiator Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Collection Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Collection Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Inventory Change Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Recovered STB No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Installed STB No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Defect Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Initiation Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Transfer Date</b></td>
				</tr>
			</logic:equal>	
		</logic:equal>
		<logic:equal name="DistRecoBean" property="tabId" value="4"><!--  Activated stock -->
			<logic:equal name="DistRecoBean" property="columnId" value="1">
			<tr>
					<td rowspan="2" class="colhead" align="center"><b>S.No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor OLM ID</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Tsm Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Circle Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>FSE Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Retailer Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Serial No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Status</b></td>
					<td rowspan="2" class="colhead" align="center"><b>PR No.</b></td>
					<td rowspan="2" class="colhead" align="center"><b>PO No.</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Stock Acceptance Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Activation Date</b></td>
				</tr>	
			</logic:equal>
			<logic:equal name="DistRecoBean" property="columnId" value="2">
			<tr>
					<td rowspan="2" class="colhead" align="center"><b>S.No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor OLM ID</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Tsm Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Circle Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Collection Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>STB Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Collection Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Inventory Change Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Recovered STB No.</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Installed STB No.</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Defect Type</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Ageing</b></td>
				</tr>
			</logic:equal>
				<logic:equal name="DistRecoBean" property="columnId" value="3">
			<tr>
					<td rowspan="2" class="colhead" align="center"><b>S.No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Distributor Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Circle Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Old Product Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>New Product Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Old SR No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>New SR No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>PR No.</b></td>
					<td rowspan="2" class="colhead" align="center"><b>PO No</b></td>
					<td rowspan="2" class="colhead" align="center"><b>FSE Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Retailer Name</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Dist. Purchase Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Retailer Purchase Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>FSE Purchase Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Inv Change Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Collection Date</b></td>
					<td rowspan="2" class="colhead" align="center"><b>Defect Type</b></td>
					
				</tr>
			</logic:equal>
		</logic:equal>
		<tr></tr>
		<%
		int i=0;
		 %>
		 <logic:notEmpty name="DistRecoBean" property="detailsList">
 		<logic:iterate id="reportData" name="DistRecoBean" property="detailsList">
 		<%i++; %>
 		
 		<logic:equal name="DistRecoBean" property="tabId" value="1"><!--  opening stock -->
		<logic:equal name="DistRecoBean" property="columnId" value="1"><!--  Pending PO -->
				<tr>
					<td align="center"><%=i %></td>					
					<td align="center"><bean:write name="reportData" property="distOlmId"/></td>
					<td align="center"><bean:write name="reportData" property="distName"/></td>
					<td align="center"><bean:write name="reportData" property="tsmName"/></td>
					<td align="center"><bean:write name="reportData" property="circleName"/></td>
					<td align="center"><bean:write name="reportData" property="poNo"/></td>
					<td align="center"><bean:write name="reportData" property="poDate"/></td>
					<td align="center"><bean:write name="reportData" property="dcNo"/></td>
					<td align="center"><bean:write name="reportData" property="dcDate"/></td>
					<td align="center"><bean:write name="reportData" property="productName"/></td>
					<td align="center"><bean:write name="reportData" property="dispatchedQtyPerDc"/></td>
					<td align="center"><bean:write name="reportData" property="productSerialNo"/></td>
					<td align="center"><bean:write name="reportData" property="poStatus"/></td>
				</tr>
		</logic:equal>
		<logic:equal name="DistRecoBean" property="columnId" value="2"><!--  Pending DC -->
				<tr>
					<td align="center"><%=i %></td>					
					<td align="center"><bean:write name="reportData" property="distOlmId"/></td>
					<td align="center"><bean:write name="reportData" property="distName"/></td>
					<td align="center"><bean:write name="reportData" property="tsmName"/></td>
					<td align="center"><bean:write name="reportData" property="circleName"/></td>
					<td align="center"><bean:write name="reportData" property="dcNo"/></td>
					<td align="center"><bean:write name="reportData" property="dcDate"/></td>
					<td align="center"><bean:write name="reportData" property="collectionType"/></td>
					<td align="center"><bean:write name="reportData" property="productName"/></td>
					<td align="center"><bean:write name="reportData" property="dispatchedQtyPerDc"/></td>
					<td align="center"><bean:write name="reportData" property="productSerialNo"/></td>
					<td align="center"><bean:write name="reportData" property="dcStbStatus"/></td>
				</tr>
		</logic:equal>
		<logic:equal name="DistRecoBean" property="columnId" value="3"><!--  Serialized Stock -->
			<tr>
					<td align="center"><%=i %></td>					
					<td align="center"><bean:write name="reportData" property="distOlmId"/></td>
					<td align="center"><bean:write name="reportData" property="distName"/></td>
					<td align="center"><bean:write name="reportData" property="tsmName"/></td>
					<td align="center"><bean:write name="reportData" property="circleName"/></td>
					<td align="center"><bean:write name="reportData" property="prNo"/></td>
					<td align="center"><bean:write name="reportData" property="poNo"/></td>
					<td align="center"><bean:write name="reportData" property="productName"/></td>
					<td align="center"><bean:write name="reportData" property="productSerialNo"/></td>
					<td align="center"><bean:write name="reportData" property="distPurchageDate"/></td>
					<td align="center"><bean:write name="reportData" property="fscName"/></td>
					<td align="center"><bean:write name="reportData" property="fscPurchageDate"/></td>
					<td align="center"><bean:write name="reportData" property="retailerName"/></td>
					<td align="center"><bean:write name="reportData" property="retailerPurchageDate"/></td>
					<td align="center"><bean:write name="reportData" property="statusDesc"/></td>
				</tr>
		</logic:equal>
		<logic:equal name="DistRecoBean" property="columnId" value="4"><!--  Defective Stock -->
				<tr>
					<td align="center"><%=i %></td>					
					<td align="center"><bean:write name="reportData" property="distOlmId"/></td>
					<td align="center"><bean:write name="reportData" property="distName"/></td>
					<td align="center"><bean:write name="reportData" property="tsmName"/></td>
					<td align="center"><bean:write name="reportData" property="circleName"/></td>
					<td align="center"><bean:write name="reportData" property="collectionType"/></td>
					<td align="center"><bean:write name="reportData" property="productName"/></td>
					<td align="center"><bean:write name="reportData" property="collectionDate"/></td>
					<td align="center"><bean:write name="reportData" property="inventoryChangeDate"/></td>
					<td align="center"><bean:write name="reportData" property="recoveredStbNo"/></td>
					<td align="center"><bean:write name="reportData" property="installedStbNo"/></td>
					<td align="center"><bean:write name="reportData" property="defectType"/></td>
					<td align="center"><bean:write name="reportData" property="aging"/></td>
				</tr>
		</logic:equal>
			<logic:equal name="DistRecoBean" property="columnId" value="5"><!--  Upgrade Stock -->
				<tr>
					<td align="center"><%=i %></td>					
					<td align="center"><bean:write name="reportData" property="distOlmId"/></td>
					<td align="center"><bean:write name="reportData" property="distName"/></td>
					<td align="center"><bean:write name="reportData" property="tsmName"/></td>
					<td align="center"><bean:write name="reportData" property="circleName"/></td>
					<td align="center"><bean:write name="reportData" property="collectionType"/></td>
					<td align="center"><bean:write name="reportData" property="productName"/></td>
					<td align="center"><bean:write name="reportData" property="collectionDate"/></td>
					<td align="center"><bean:write name="reportData" property="inventoryChangeDate"/></td>
					<td align="center"><bean:write name="reportData" property="recoveredStbNo"/></td>
					<td align="center"><bean:write name="reportData" property="installedStbNo"/></td>
					<td align="center"><bean:write name="reportData" property="defectType"/></td>
					<td align="center"><bean:write name="reportData" property="aging"/></td>
				</tr>
		</logic:equal>
		<logic:equal name="DistRecoBean" property="columnId" value="6"><!--  Churned Stock -->
				<tr>
					<td align="center"><%=i %></td>					
					<td align="center"><bean:write name="reportData" property="distOlmId"/></td>
					<td align="center"><bean:write name="reportData" property="distName"/></td>
					<td align="center"><bean:write name="reportData" property="tsmName"/></td>
					<td align="center"><bean:write name="reportData" property="circleName"/></td>
					<td align="center"><bean:write name="reportData" property="collectionType"/></td>
					<td align="center"><bean:write name="reportData" property="productName"/></td>
					<td align="center"><bean:write name="reportData" property="collectionDate"/></td>
					<td align="center"><bean:write name="reportData" property="recoveredStbNo"/></td>
					<td align="center"><bean:write name="reportData" property="aging"/></td>
				</tr>
		</logic:equal>
		</logic:equal><!-- End of Opening stock -->	
		<logic:equal name="DistRecoBean" property="tabId" value="2"><!--  Received stock -->
			<logic:equal name="DistRecoBean" property="columnId" value="1"><!-- Rcv. WH  -->
			<tr>
					<td align="center"><%=i %></td>					
					<td align="center"><bean:write name="reportData" property="distOlmId"/></td>
					<td align="center"><bean:write name="reportData" property="distName"/></td>
					<td align="center"><bean:write name="reportData" property="tsmName"/></td>
					<td align="center"><bean:write name="reportData" property="circleName"/></td>
					<td align="center"><bean:write name="reportData" property="poNo"/></td>
					<td align="center"><bean:write name="reportData" property="poDate"/></td>
					<td align="center"><bean:write name="reportData" property="dcNo"/></td>
					<td align="center"><bean:write name="reportData" property="dcDate"/></td>
					<td align="center"><bean:write name="reportData" property="productName"/></td>
					<td align="center"><bean:write name="reportData" property="productSerialNo"/></td>
					<td align="center"><bean:write name="reportData" property="poStatus"/></td>
					<td align="center"><bean:write name="reportData" property="stbStatus"/></td>
			
					<td align="center"><bean:write name="reportData" property="lastPoAcceptanceDate"/></td>
				
				</tr>
			</logic:equal>
			
			<logic:equal name="DistRecoBean" property="columnId" value="2"><!-- Rcv. Inter-SSD OK  -->
			<tr>
					<td align="center"><%=i %></td>	
					<td align="center"><bean:write name="reportData" property="fromDistId"/></td>
					<td align="center"><bean:write name="reportData" property="fromDistName"/></td>
					<td align="center"><bean:write name="reportData" property="toDistId"/></td>
					<td align="center"><bean:write name="reportData" property="toDistName"/></td>
					<td align="center"><bean:write name="reportData" property="initiatorName"/></td>
					<td align="center"><bean:write name="reportData" property="transferType"/></td>
					<td align="center"><bean:write name="reportData" property="circleName"/></td>
					<td align="center"><bean:write name="reportData" property="dcNo"/></td>
					<td align="center"><bean:write name="reportData" property="productName"/></td>
					<td align="center"><bean:write name="reportData" property="productSerialNo"/></td>
					<td align="center"><bean:write name="reportData" property="initiationDate"/></td>
					<td align="center"><bean:write name="reportData" property="transferDate"/></td>
					<td align="center"><bean:write name="reportData" property="acceptanceDate"/></td>
					
				</tr>
			</logic:equal>	
			<logic:equal name="DistRecoBean" property="columnId" value="3"><!-- Rcv. Inter-SSD Defective  -->
			<tr>
					<td align="center"><%=i %></td>	
					<td align="center"><bean:write name="reportData" property="fromDistId"/></td>
					<td align="center"><bean:write name="reportData" property="fromDistName"/></td>
					<td align="center"><bean:write name="reportData" property="toDistId"/></td>
					<td align="center"><bean:write name="reportData" property="toDistName"/></td>
					<td align="center"><bean:write name="reportData" property="initiatorName"/></td>
					<td align="center"><bean:write name="reportData" property="collectionType"/></td>
					<td align="center"><bean:write name="reportData" property="productName"/></td>
					<td align="center"><bean:write name="reportData" property="collectionDate"/></td>
					<td align="center"><bean:write name="reportData" property="inventoryChangeDate"/></td>
					<td align="center"><bean:write name="reportData" property="productSerialNo"/></td>
				
					<td align="center"><bean:write name="reportData" property="defectType"/></td>
					<td align="center"><bean:write name="reportData" property="initiationDate"/></td>
					<td align="center"><bean:write name="reportData" property="transferDate"/></td>
				</tr>
			</logic:equal>	
				<logic:equal name="DistRecoBean" property="columnId" value="4"><!-- Rcv. upgrade  -->
				<tr>
					<td align="center"><%=i %></td>					
					<td align="center"><bean:write name="reportData" property="distOlmId"/></td>
					<td align="center"><bean:write name="reportData" property="distName"/></td>
					<td align="center"><bean:write name="reportData" property="tsmName"/></td>
					<td align="center"><bean:write name="reportData" property="circleName"/></td>
					<td align="center"><bean:write name="reportData" property="collectionType"/></td>
					<td align="center"><bean:write name="reportData" property="productName"/></td>
					<td align="center"><bean:write name="reportData" property="collectionDate"/></td>
					<td align="center"><bean:write name="reportData" property="inventoryChangeDate"/></td>
					<td align="center"><bean:write name="reportData" property="recoveredStbNo"/></td>
					<td align="center"><bean:write name="reportData" property="installedStbNo"/></td>
					<td align="center"><bean:write name="reportData" property="defectType"/></td>
				</tr>
			</logic:equal>	
			<logic:equal name="DistRecoBean" property="columnId" value="5"><!-- Rcv. churned  -->
				<tr>
					<td align="center"><%=i %></td>					
					<td align="center"><bean:write name="reportData" property="distOlmId"/></td>
					<td align="center"><bean:write name="reportData" property="distName"/></td>
					<td align="center"><bean:write name="reportData" property="tsmName"/></td>
					<td align="center"><bean:write name="reportData" property="circleName"/></td>
					<td align="center"><bean:write name="reportData" property="collectionType"/></td>
					<td align="center"><bean:write name="reportData" property="productName"/></td>
					<td align="center"><bean:write name="reportData" property="collectionDate"/></td>
					<td align="center"><bean:write name="reportData" property="recoveredStbNo"/></td>
					<td align="center"><bean:write name="reportData" property="aging"/></td>
				</tr>
			</logic:equal>	
			</logic:equal>
			<logic:equal name="DistRecoBean" property="tabId" value="3"><!--  Returned stock -->
			<%if( columnId.equals("4") || columnId.equals("5") || columnId.equals("6") || columnId.equals("7") || columnId.equals("8")){ %>
			<tr>
					<td align="center"><%=i %></td>					
					<td align="center"><bean:write name="reportData" property="distOlmId"/></td>
					<td align="center"><bean:write name="reportData" property="distName"/></td>
					<td align="center"><bean:write name="reportData" property="tsmName"/></td>
					<td align="center"><bean:write name="reportData" property="circleName"/></td>
					<td align="center"><bean:write name="reportData" property="collectionType"/></td>
					<td align="center"><bean:write name="reportData" property="productName"/></td>
					<td align="center"><bean:write name="reportData" property="dcNo"/></td>
					<td align="center"><bean:write name="reportData" property="dcDate"/></td>
					<td align="center"><bean:write name="reportData" property="recoveredStbNo"/></td>
					<td align="center"><bean:write name="reportData" property="whReceivedDate"/></td>
					</tr>
					<%} %>
					<logic:equal name="DistRecoBean" property="columnId" value="1">
					<tr>
						<td align="center"><%=i %></td>					
					<td align="center"><bean:write name="reportData" property="distOlmId"/></td>
					<td align="center"><bean:write name="reportData" property="distName"/></td>
					<td align="center"><bean:write name="reportData" property="tsmName"/></td>
					<td align="center"><bean:write name="reportData" property="circleName"/></td>
					<td align="center"><bean:write name="reportData" property="collectionType"/></td>
					<td align="center"><bean:write name="reportData" property="productName"/></td>
					<td align="center"><bean:write name="reportData" property="dcNo"/></td>
					<td align="center"><bean:write name="reportData" property="dcDate"/></td>
						<td align="center"><bean:write name="reportData" property="statusDesc"/></td>
					<td align="center"><bean:write name="reportData" property="recoveredStbNo"/></td>
						<td align="center"><bean:write name="reportData" property="dcStbStatus"/></td>
					<td align="center"><bean:write name="reportData" property="whReceivedDate"/></td>
				</tr>
			</logic:equal>
			
					<logic:equal name="DistRecoBean" property="columnId" value="2">
					<tr>
					<td align="center"><%=i %></td>	
					<td align="center"><bean:write name="reportData" property="fromDistId"/></td>
					<td align="center"><bean:write name="reportData" property="fromDistName"/></td>
					<td align="center"><bean:write name="reportData" property="toDistId"/></td>
					<td align="center"><bean:write name="reportData" property="toDistName"/></td>
					<td align="center"><bean:write name="reportData" property="initiatorName"/></td>
					<td align="center"><bean:write name="reportData" property="transferType"/></td>
					<td align="center"><bean:write name="reportData" property="circleName"/></td>
					<td align="center"><bean:write name="reportData" property="dcNo"/></td>
					<td align="center"><bean:write name="reportData" property="productName"/></td>
					<td align="center"><bean:write name="reportData" property="productSerialNo"/></td>
					<td align="center"><bean:write name="reportData" property="initiationDate"/></td>
					<td align="center"><bean:write name="reportData" property="transferDate"/></td>
					<td align="center"><bean:write name="reportData" property="acceptanceDate"/></td>
				</tr>
			</logic:equal>
			<logic:equal name="DistRecoBean" property="columnId" value="3">
					<tr>
					
					<td align="center"><%=i %></td>	
					<td align="center"><bean:write name="reportData" property="fromDistId"/></td>
					<td align="center"><bean:write name="reportData" property="fromDistName"/></td>
					<td align="center"><bean:write name="reportData" property="toDistId"/></td>
					<td align="center"><bean:write name="reportData" property="toDistName"/></td>
					<td align="center"><bean:write name="reportData" property="initiatorName"/></td>
					<td align="center"><bean:write name="reportData" property="collectionType"/></td>
					<td align="center"><bean:write name="reportData" property="productName"/></td>
					<td align="center"><bean:write name="reportData" property="collectionDate"/></td>
					<td align="center"><bean:write name="reportData" property="inventoryChangeDate"/></td>
					<td align="center"><bean:write name="reportData" property="recoveredStbNo"/></td>
					<td></td>
					<td align="center"><bean:write name="reportData" property="defectType"/></td>
					<td align="center"><bean:write name="reportData" property="initiationDate"/></td>
					<td align="center"><bean:write name="reportData" property="transferDate"/></td>
				</tr>
			</logic:equal>
			</logic:equal>	
			<logic:equal name="DistRecoBean" property="tabId" value="4"><!--  Activated stock -->
			<logic:equal name="DistRecoBean" property="columnId" value="1">
			<tr>
					<td align="center"><%=i %></td>					
					<td align="center"><bean:write name="reportData" property="distOlmId"/></td>
					<td align="center"><bean:write name="reportData" property="distName"/></td>
					<td align="center"><bean:write name="reportData" property="tsmName"/></td>
					<td align="center"><bean:write name="reportData" property="circleName"/></td>
					<td align="center"><bean:write name="reportData" property="fscName"/></td>
					<td align="center"><bean:write name="reportData" property="retailerName"/></td>
					<td align="center"><bean:write name="reportData" property="productName"/></td>
					<td align="center"><bean:write name="reportData" property="activatedSerialNo"/></td>
					<td align="center"><bean:write name="reportData" property="stbStatus"/></td>
					<td align="center"><bean:write name="reportData" property="prNo"/></td>
					<td align="center"><bean:write name="reportData" property="poNo"/></td>
					<td align="center"><bean:write name="reportData" property="acceptanceDate"/></td>
					<td align="center"><bean:write name="reportData" property="activationDate"/></td>
			</tr>
			</logic:equal>
			<logic:equal name="DistRecoBean" property="columnId" value="2">
			<tr>
					<td align="center"><%=i %></td>					
					<td align="center"><bean:write name="reportData" property="distOlmId"/></td>
					<td align="center"><bean:write name="reportData" property="distName"/></td>
					<td align="center"><bean:write name="reportData" property="tsmName"/></td>
					<td align="center"><bean:write name="reportData" property="circleName"/></td>
					<td align="center"><bean:write name="reportData" property="collectionType"/></td>
					<td align="center"><bean:write name="reportData" property="productName"/></td>
					<td align="center"><bean:write name="reportData" property="collectionDate"/></td>
					<td align="center"><bean:write name="reportData" property="inventoryChangeDate"/></td>
					<td align="center"><bean:write name="reportData" property="recoveredStbNo"/></td>
					<td align="center"><bean:write name="reportData" property="installedStbNo"/></td>
					<td align="center"><bean:write name="reportData" property="defectType"/></td>
					<td align="center"><bean:write name="reportData" property="aging"/></td>
				
			</tr>
			</logic:equal>
			<logic:equal name="DistRecoBean" property="columnId" value="3">
			<tr>
					<td align="center"><%=i %></td>					
					<td align="center"><bean:write name="reportData" property="distName"/></td>
					<td align="center"><bean:write name="reportData" property="circleName"/></td>
					<td align="center"><bean:write name="reportData" property="productName"/></td>
					<td align="center"><bean:write name="reportData" property="newProductName"/></td>
					<td align="center"><bean:write name="reportData" property="recoveredStbNo"/></td>
					<td align="center"><bean:write name="reportData" property="installedStbNo"/></td>
					<td align="center"><bean:write name="reportData" property="prNo"/></td>
					<td align="center"><bean:write name="reportData" property="poNo"/></td>
					<td align="center"><bean:write name="reportData" property="fscName"/></td>
					<td align="center"><bean:write name="reportData" property="retailerName"/></td>
					<td align="center"><bean:write name="reportData" property="distPurchageDate"/></td>
					<td align="center"><bean:write name="reportData" property="retailerPurchageDate"/></td>
					<td align="center"><bean:write name="reportData" property="fscPurchageDate"/></td>
					<td align="center"><bean:write name="reportData" property="inventoryChangeDate"/></td>
					<td align="center"><bean:write name="reportData" property="collectionDate"/></td>
					<td align="center"><bean:write name="reportData" property="defectType"/></td>
			</tr>
			</logic:equal>
			</logic:equal>
			</logic:iterate>
			</logic:notEmpty>
			<logic:empty name="DistRecoBean" property="detailsList">
			<tr><td colspan="10" align="center">No Record Found</td></tr>
			</logic:empty>
		 	</table>	
</BODY>
</html:html>
