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
	response.setHeader("content-disposition","attachment;filename=PendingListTransferBulkUploadFormat.xls");
	response.setHeader("Cache-Control", "must-revalidate");
	response.setHeader( "Pragma", "public" );
    
%> 

<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> 
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css"> 
<TITLE>DP Pending List Transfer  Bulk Upload</TITLE>
</HEAD>																		
<BODY>
           
  <table border="1">
		<tr>
					<td class="colhead" align="center"><b>From Dist OLM ID</b></td>
					<td class="colhead" align="center"><b>To Dist OLM ID</td>
					<td class="colhead" align="center"><b>Product Name Of Defective STB</b></td>
					<td class="colhead" align="center"><b>Defective Serial Number</td>
					<td class="colhead" align="center"><b>Product Name of Changed STB</b></td>
					<td class="colhead" align="center"><b>Changed Serial Number</td>
						
		</tr>
		<tr>
					<td class="colhead" align="center"><b>A0112233</b></td>
					<td class="colhead" align="center"><b>A1247292</td>
					<td class="colhead" align="center"><b>HD STB</b></td>
					<td class="colhead" align="center"><b>09896054242</td>
					<td class="colhead" align="center"><b>HDB</b></td>
					<td class="colhead" align="center"><b>03271921844</td>
						
		</tr>
		<tr>
					<td class="colhead" align="center"><b>A0112233</b></td>
					<td class="colhead" align="center"><b>A1247292</td>
					<td class="colhead" align="center"><b>DVR STB</b></td>
					<td class="colhead" align="center"><b>09896054244</td>
					<td class="colhead" align="center"><b>HDB SWAP</b></td>
					<td class="colhead" align="center"><b>02586642855</td>
						
		</tr>
		<tr>
					<td class="colhead" align="center"><b>A0112233</b></td>
					<td class="colhead" align="center"><b>A1247292</td>
					<td class="colhead" align="center"><b>SD STB</b></td>
					<td class="colhead" align="center"><b>09560005200</td>
					<td class="colhead" align="center"><b>HDB</b></td>
					<td class="colhead" align="center"><b>02586642866</td>
						
		</tr>
	</table> 
	
 

</BODY>
</html:html>
 <%}
 catch(Exception e){e.printStackTrace();}%>