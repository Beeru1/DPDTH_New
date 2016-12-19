<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>	
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import = "java.util.*" %>


<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-disposition","attachment;filename=Report.xls");
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
		<logic:iterate id="headerData" name="GenericReportsFormBean" property="headers">
		<td class="colhead" align="center">
			<b>
			<bean:write name="headerData"/>
			</b>
		</td>
		</logic:iterate>														
		</tr>
		
		<logic:notEmpty name="GenericReportsFormBean" property="output">
		
		<%List output= (List) request.getAttribute("output");
			int rowNo = 0;
	 		Iterator itr= output.iterator();
	 		while(itr.hasNext()){
		 		String[] reportData=(String[]) itr.next();
		 		%>
		 		<tr><td><%=++rowNo%></td>
		 		<% 
		 		for(int i=0;i<reportData.length;i++)
		 		{
			 		%>
			 		<%
			 		if(reportData[i].matches("^[0-9]{11,16}$")){ %> 
			 		<td>&nbsp;<%=reportData[i].toString()%>&nbsp;</td>
			 		<%}else if(reportData[i].matches("[0-9]{16}")){ %>
			 		<td>&nbsp;<%=reportData[i].toString()%>&nbsp;</td>
			 		<%}else{
			 		 %>
			 		<td><%=reportData[i].toString()%></td>
			 		<%}
		 		}%>
	 		</tr>
	 		<% }
 		%>
			
		
		</logic:notEmpty>
		
 	</table>
 
<P><BR>
</P>
</BODY>



</html:html>

<%
try{
			String reportId = (String) request.getAttribute("reportId");
			session.setAttribute("Report"+reportId, "done");
    }
catch (Exception e) 
	{
   				System.out.println("Error: "+e);
    }
 %>
