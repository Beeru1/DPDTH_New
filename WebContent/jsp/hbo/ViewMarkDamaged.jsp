
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import = "com.ibm.hbo.forms.HBOMarkDamagedFormBean"%>
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
<LINK href="../theme/Master.css" rel="stylesheet"
	type="text/css">
<TITLE></TITLE>
<script language="JavaScript">

function updateFlag()
{  

}
</script>
</HEAD>


<BODY link="#0000ff" vlink="#000000" onunload="updateFlag()">
<html:form action="/viewMarkDamaged.do">
<%HBOMarkDamagedFormBean markDamagedFormBean = (HBOMarkDamagedFormBean) request.getAttribute("prodInfo");
%>
	<DIV align="left"></DIV>
	<DIV align="left">
	<TABLE border="0" class="border" width="299" height="146">
		<TBODY align="center" valign="bottom">
		<TR class="colhead">
				<TD width="122" colspan="2" class="colhead" align="center"><B>Assign
				New IMEI No.</B></TD>
			</TR>
			<TR>
				<TD align="left" width="122" >IMEI No.</TD>
				<TD width="145" ><html:text property="imeiNo" readonly="true"><%=markDamagedFormBean.getNewImeiNo()%></html:text></TD>
			</TR>
			<TR>
				<TD align="left" width="122" >SIM No.</TD>
				<TD width="145" ><html:text property="simno" readonly="true"><%=markDamagedFormBean.getSimno()%></html:text></TD>
			</TR>
			<TR>
				<TD width="122" align="left" >New IMEI No.</TD>
				<TD width="145" ><html:text property="newImeiNo" readonly="true"><%=markDamagedFormBean.getNewImeiNo()%></html:text></TD>
				
			</TR>

		</TBODY>
	</TABLE>

	</DIV>
</html:form>
</BODY>
</html:html>
