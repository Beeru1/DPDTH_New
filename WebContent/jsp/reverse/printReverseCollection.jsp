<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@page import="java.text.SimpleDateFormat"%>
<html:html>
<HEAD>
<%@ page language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="<%=request.getContextPath()%>/theme/Master.css" rel="stylesheet"
	type="text/css">
	<LINK href=<%=request.getContextPath()%>/jsp/hbo/theme/text.css rel="stylesheet" type="text/css">
<TITLE></TITLE>
<script>
function closeWindow()
{
 window.close();
}
</script>
</HEAD>
<BODY onload="" style="height:310px" background="<%=request.getContextPath()%>/images/bg_main.gif">
<h2 align="center" ></h2>
<BR>
<html:form action="/DPPrintReverseCollectionAction.do">

	
<%try{

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	String date = sdf.format(new java.util.Date()) ;


%>
<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>
<table border="0" width="100%" >
<TR>
						<TD class="text" align="center" colspan="8">Following Upgrade boxes have been added to your Ok Stock.</TD>
					</TR>
					<TR>
							<TD align="center" class="text"><b>
								Serial Number	</b>				
							</TD>
							<TD align="center" class="text">
								<b>Product	</b>				
							</TD>
							<TD align="center" class="text">
								<b>Status	</b>				
							</TD>
						</TR>
<logic:notEmpty name="DPPrintReverseCollectionBean" property="upgradeCollectionDtoList">
					<logic:iterate name="DPPrintReverseCollectionBean" id="dpMSAList" indexId="i" property="upgradeCollectionDtoList">
						<TR id='<%="tr"+i%>'  BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>'>
							<TD align="center" class="text">
								<bean:write name="dpMSAList" property="serialNo"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="dpMSAList" property="productName"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="dpMSAList" property="dcStatus"/>					
							</TD>
						</TR>
					</logic:iterate>
				</logic:notEmpty>
</table>
<table border="0" width="100%">
	<TR>
	<td align="center" width="100%">
		<html:button property="btn" value="Close" onclick="closeWindow();" />
		</td>
	</TR>	
</table>
<%}catch(Exception ex){ex.printStackTrace();} %>	
<br>
</html:form>

</BODY>
</html:html>
