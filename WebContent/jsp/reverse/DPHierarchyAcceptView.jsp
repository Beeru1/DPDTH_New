<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html:html>
<head>
<LINK href="<%=request.getContextPath()%>/theme/text.css"
	rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/theme/chromestyle2.css" />
<LINK href="<%=request.getContextPath()%>/theme/Master.css"
	rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/chrome.js"></SCRIPT>
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<SCRIPT language=JavaScript
	src="<%=request.getContextPath()%>/scripts/Cal.js" type=text/javascript></SCRIPT>
<title>ViewHierarchyTransferAccepte</title>

<script>
	function viewStockDetail(account_id , role , tr_no ,trnsBy ,requestType, trns_time)
	{
		//var trnsBy = "0";
		//alert(requestType);
		if(requestType == "ALL")
			trnsBy = "0";
		
		//document.forms[0].methodName.value="getStockDetails";
		//document.forms[0].action="dpHierarchyAccept.do?methodName=getStockDetails&account_id="+account_id+"&role="+role;
		document.forms[0].action = "dpHierarchyAccept.do?methodName=viewHeirarchyAccept&TR_NO="+tr_no+"&TRNS_BY="+trnsBy+"&account_id="+account_id+"&role="+role+"&TRNS_TIME="+trns_time;
		//alert(document.forms[0].action);
		//alert(document.forms[0].action);
		document.forms[0].method="POST";
		document.forms[0].submit();
		
		
		
	}
</script>


</head>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
	
<html:form action="/dpHierarchyAccept" >
<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>
<html:hidden property="methodName"/>
<table width="100%" align="left" border="0" cellpadding="0" cellspacing="0">
<tr>
<td>
<table width="100%" align="left" border="1" cellpadding="0" cellspacing="0">

				<tr>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="HierarchyView.Role" /></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="HierarchyView.AccountName" /></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="HierarchyView.ContactName" /></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="HierarchyView.MobileNo" /></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="HierarchyView.Zone" /></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="HierarchyView.City" /></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">Action</FONT></td>
				</tr>
			
			
					<logic:iterate name="listHierarchyView" id="hierarchyList" indexId="i" >
						
						<TR BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>' >
							<TD align="center" class="text" id='"collectionType"+<%=i%>'>
								<bean:write name="hierarchyList" property="strRole"/>				
							</TD>
							<TD align="center" class="text">
								<bean:write name="hierarchyList" property="strAccountName"/>
							</TD>
							<TD align="center" class="text">
								<bean:write name="hierarchyList" property="strContactName"/>
							</TD>
							<TD align="center" class="text">
								<bean:write name="hierarchyList" property="strMobileNo"/> 
							</TD>
							<TD align="center" class="text">
								<bean:write name="hierarchyList" property="strZone"/>
							</TD>
							<TD align="center" class="text">
								<bean:write name="hierarchyList" property="strCity"/>
							</TD>
							<TD align="center" class="text">
								<a href="#" onclick="viewStockDetail('<bean:write name='hierarchyList' property='account_id'/>' ,
																	 '<bean:write name='hierarchyList' property='strRole'/>',
																	 '<bean:write name='hierarchyList' property='strTRNO'/>' , 
																	 '<bean:write name='hierarchyList' property='strTransferBy'/>',
																	 '<bean:write name='hierarchyList' property='request_type'/>',
																	 '<bean:write name='hierarchyList' property='tsTrnsTime'/>')">
		View Stock					
	</a>
							</TD>
						</TR>
					</logic:iterate>
			
			
		</table>
</td>
</tr>
<tr>
	<td>&nbsp;</td>
</tr>
<tr>
	<td>&nbsp;</td>
</tr>
<tr>
<td>
<logic:equal name="HierarchyAccept" property="strStockCount" value="true">
<logic:empty name="HierarchyAccept" property="list_stock_details">

<table><tr><td align="center"><font color="red"><strong>
	No Stock Available</strong></font>
</td></tr></table>
</logic:empty>

</logic:equal>
<logic:notEmpty name="HierarchyAccept" property="list_stock_details">

		<table width="100%" align="left" border="1" cellpadding="0" cellspacing="0">

				<tr>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">FSE</FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">Retailer</FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">Product Name</FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">Stock Qty.</FONT></td>
				</tr>
				
				<logic:iterate name="HierarchyAccept" property="list_stock_details" id="list_stock_details">

						<TR>
						
					<logic:equal value="7" name="list_stock_details" property="strRole" >
							<TD align="center" class="text">
								<bean:write name="list_stock_details" property="strAccountName"/>				
							</TD>
							<TD align="center" class="text">
								&nbsp;
							</TD>
					</logic:equal>	
					<logic:equal value="8" name="list_stock_details" property="strRole" >
							<TD align="center" class="text">
								&nbsp;				
							</TD>
							<TD align="center" class="text"><bean:write name="list_stock_details" property="strAccountName"/>
							</TD>
					</logic:equal>
					
							<TD align="center" class="text">
								<bean:write name="list_stock_details" property="productName"/>
							</TD>
							<TD align="center" class="text">
								<bean:write name="list_stock_details" property="stockQty"/> 
							</TD>
						</TR>
					</logic:iterate>
		</table>	
</logic:notEmpty>				
</td>
</tr>
<tr>
<td align="center">	
<table align="center">
	<TR>
	<td width='100%'>&nbsp;</td>
	</TR>
	<TR>
	<td width='100%' align='center'><input type='button' value='Close' onclick="window.close();"></td>
	</TR>
</table>
</td></tr>
</table>
	</html:form>
</BODY>
</html:html>