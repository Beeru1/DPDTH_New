<%try{%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import = "java.util.ArrayList,java.util.HashMap"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import = "java.util.*,com.ibm.dp.dto.DPPoAcceptanceBulkDTO" %>
<html:html>
<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-disposition","attachment;filename=ErrorLog.xls");
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
    List error_list = (List)session.getAttribute("error_file");
    System.out.println("Size in JSP page:::"+error_list.size());
    Iterator itt = error_list.iterator();
    DPPoAcceptanceBulkDTO dpPoAcceptanceBulkDTO = null;
%>
	<table border="1">
		<tr>
					<td class="colhead" align="center"><b>Serial Number</b></td>
					<td class="colhead" align="center"><b>Error</b></td>
		</tr>
 		<%
 			while(itt.hasNext())
 			{
 				dpPoAcceptanceBulkDTO = (DPPoAcceptanceBulkDTO) itt.next();
 				
 				%>
 				<tr>
	 				<td align="center">&nbsp;<%=dpPoAcceptanceBulkDTO.getSerial_no()%>&nbsp;</td>
	 				<%
	 				if(dpPoAcceptanceBulkDTO.getErr_msg().equalsIgnoreCase("SUCCESS"))
	 				{
	 					 %>
	 						<td align="center">&nbsp;</td>
	 					 <%
	 				}
	 				else
	 				{
	 					 %>
	 						<td align="center"><%=dpPoAcceptanceBulkDTO.getErr_msg()%></td>
	 					 <%
	 				}
	 			%>	
 				</tr>
 				<%
 				dpPoAcceptanceBulkDTO = null;
 			}
 		 %>
 	</table>
<P><BR>
</P>
</BODY>
</html:html>
 <%}catch(Exception e){e.printStackTrace();}%>