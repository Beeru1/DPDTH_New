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
	response.setHeader("content-disposition","attachment;filename=DistributorPincodeMapping.xls");
	response.setHeader("Cache-Control", "must-revalidate");
	response.setHeader( "Pragma", "public" );
    
%> 

<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> 
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css"> 
<TITLE>Distributor Pincode Bulk Upload</TITLE>
</HEAD>																		
<BODY>
           
  <table border="1">
		<tr>
					<td class="colhead" align="center"><b>Distributor OLMID</b></td>
					<td class="colhead" align="center"><b>PinCode</b></td>
						
		</tr>
	</table> 
	
 

</BODY>
</html:html>
 <%}
 catch(Exception e){e.printStackTrace();}%>