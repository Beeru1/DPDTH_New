
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html:html>
<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="../theme/Master.css" rel="stylesheet" type="text/css">
<LINK href="<%=request.getContextPath()%>/theme/text.css" rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/theme/chromestyle2.css" />
<script language="javascript" src="jScripts/Common.js">
</script>

<TITLE></TITLE>
</HEAD>

<BODY>


<html:form action="/AssignSimStock.do?methodName=AssignSimStock">
<table>
<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/assignSIMStock.gif"
		width="505" height="22" alt="">
	</TD>
</tr>
</table>
	<TABLE cellpadding="4" cellspacing="3" border="0" align="center" width="50%">
		<tr><B><center><font color="Red"><lI><html:errors /></lI></font></center></B>
		</tr>
		<TR align="center">
			<TD class="border"><logic:equal value="0"
				name="HBOAssignSimStockForm" property="avlSimStock">
				<CENTER>Sim Stock in not available to assign !!!!</CENTER>
			</logic:equal></TD>
		</TR>
		<logic:greaterThan value="0" name="HBOAssignSimStockForm"
			property="avlSimStock">
			<TR align="center">
				<TD class="border">
				<TABLE cellpadding="6" cellspacing="0" align="center" width="100%" border=0>
					<TBODY>
						
						<TR>
							<TD  class="text pLeft15"><STRONG><FONT color="#000000">
							<bean:message bundle="hboView"
								key="hboAssignSimStockForm.avlSimStock" /><font color="red">*</font>:</FONT></STRONG></TD>
							<TD width="262"><html:text name="HBOAssignSimStockForm"
								property="avlSimStock" readonly="true"></html:text></TD>
						</TR>
						<TR>
							<TD  class="text pLeft15"><STRONG><FONT color="#000000"><bean:message bundle="hboView"
								key="hboAssignSimStockForm.assignTo" /><font color="red">*</font>:</FONT></STRONG></TD>
							
							<TD ><bean:define id="account" name="HBOAssignSimStockForm" property="accountdetails" />
							 <html:select property="warehouseId">
							  <html:option value="" >-Select National/Local Distributor-</html:option>
								<logic:notEmpty property="accountdetails" name="HBOAssignSimStockForm">
									<html:options labelProperty="accountName" property="accountId" collection="account" />
								</logic:notEmpty>
								</html:select></TD>
							
						</TR>
						<TR>
							<TD  class="text pLeft15"><STRONG><FONT color="#000000"><bean:message bundle="hboView"
								key="hboAssignSimStockForm.assignedSimQty" /><font color="red">*</font>:</FONT></STRONG></TD>
							<TD ><html:text name="HBOAssignSimStockForm"
								property="assignedSimQty" maxlength="8"></html:text></TD>
						</TR>
						
						<TR>

							<TD colspan="2" align="center"><html:submit value="Assign" styleClass="medium"
								onclick="return validateAssignSimStock();"></html:submit></TD>
						</TR>
					</TBODY>
				</TABLE>
				</TD>
			</TR>
				</logic:greaterThan>
	</TABLE>

</html:form>
</BODY>
</html:html>
