<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display" %>
<%@ page import="java.util.ArrayList"%>
<LINK rel="stylesheet" href="theme/text.css" type="text/css">

<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/jScripts/progressBar.js" type="text/javascript">
</SCRIPT>
<SCRIPT language="javascript" type="text/javascript">
	
function exportToExcel()
	{
   		 	   
	var url = "checkRetailerBalance.do?methodName=exporttoExcel";
	
	document.forms[0].action=url;
	document.forms[0].method="post";
	document.forms[0].submit();
	return false;
	}
</SCRIPT>
<html>
<head>

<title>Check Retailer Balance</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script>

</script>
</head>
<BODY background="/DPDTH/images/bg_main.gif"  bgcolor="#FFFFFF">
<p></p>
<logic:notEmpty name="retDetails">
<!--<table cellspacing="0" cellpadding="0" border="1" width="80%" height="80%" valign="top">
<%int i = 1; %>

                         <tr align="center" bgcolor="#104e8b">
                         <td align="center" bgcolor="#CD0504"><span
									class="white10heading mLeft5 mTop5"><bean:message bundle="view"
									key="application.sno" /></span></td>
									
																	
								<td align="center" bgcolor="#CD0504"><span
									class="white10heading mLeft5 mTop5"><bean:message bundle="view"
									key="retailer.name" /></span></td>
									
								<td align="center" bgcolor="#CD0504"><span
									class="white10heading mLeft5 mTop5"><bean:message bundle="view"
									key="retailer.lapu" /></span></td>
									
								<td align="center" bgcolor="#CD0504"><span
									class="white10heading mLeft5 mTop5"><bean:message bundle="view"
									key="retailer.balance" /></span></td> 
                         
                         </tr>
                         
                         
						<logic:iterate id="retailerDTO" name="retDetails">
							<tr bgcolor="#FCE8E6">
								<td class="height19"><span class="mTop5 mBot5 black10Bold"><%=i%></c:out></span></td>
																				
								<td class="height16"><span class="mLeft5 mTop5 mBot5 black10"><bean:write name="retailerDTO" property="retailername"></bean:write></span></td>
									
								<td class="height16"><span class="mLeft5 mTop5 mBot5 black10"><bean:write name="retailerDTO" property="retailerlapu"></bean:write></span></td>
									
								<td class="height16"><span class="mLeft5 mTop5 mBot5 black10"><bean:write name="retailerDTO" property="balance"></bean:write></span></td>
								<%i = i+1; %>	
									
							</tr>
						</logic:iterate>

</table>
-->

<jsp:useBean id="data" class="com.ibm.dp.beans.CheckRetailerBalanceBean"/>

                                <display:table id="records" name="retDetails"  pagesize="10" requestURI="./checkRetailerBalance.do?methodName=init"
                                cellspacing="7px;" cellpadding="7px;" style="margin-left:50px;margin-top:20px;">
                                
    <display:column property="retailername" title="Retailer Name" />
    <display:column property="retailerlapu" title="Lapu Mobile Number" />
    <display:column property="balance" title="Balance"   />
    
    
    
                   <!--<tr>
					<td colspan="2" align="center">
					<html:button property="excelBtn" value="Download" onclick="return downloadExcel();"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;
						
					</td>
					</tr>
    
    
                                -->
                                
                                </display:table>





</logic:notEmpty>
<logic:empty name="retDetails" >
<font size="3">No Record exists</font>
</logic:empty>
<html:button property="excelBtn" value="Export To Excel"   onclick="return exportToExcel();"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;

<html:hidden property="methodName" styleId="methodName" value="exporttoExcel"  />					
</body>
</html>
