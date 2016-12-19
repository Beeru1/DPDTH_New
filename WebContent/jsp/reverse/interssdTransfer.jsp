
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
<LINK rel="stylesheet" href="theme/text.css" type="text/css">
<script language="JavaScript"
	src="<%=request.getContextPath()%>/jScripts/AccountMaster.js"></script>
<TITLE> </TITLE>

<style type="text/css">
.noScroll{
position: relative;
	 top: 0px;
	left: 0px;
	 top:expression(this.offsetParent.scrollTop-2);
   
}
</style>
<SCRIPT language="JavaScript" type="text/javascript">
function viewTRDetail(trNo)
{
	var trnsBy ="0";
	
	var url="dpHierarchyAccept.do?methodName=viewHeirarchyAccept&TR_NO="+trNo+"&TRNS_BY="+trnsBy;
	window.open(url,"HierarchyTransfer","width=900,height=400,scrollbars=yes,toolbar=no");
	//window.open(url,"SerialNo");
	window.moveTo(0,0);
}
function getdetails(TRNO,transType,transsubtype)
{
		var url="interssdtransfer.do?methodName=getInterssdDetails&TRNO="+TRNO+"&transType="+transType+"&transsubtype="+transsubtype;
		///alert(url);
		win3	=	window.open(url,"SerialNo","width=900,height=500,scrollbars=yes,toolbar=no");
		//window.open(url,"SerialNo");
		win3.moveTo(80,80);
		
}	

</SCRIPT>
</HEAD>

<BODY BACKGROUND="images/bg_main.gif">
<html:form  action="/interssdtransfer"> 

	
<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>
<br>
	<IMG src="<%=request.getContextPath()%>/images/Trans_Hier.jpg"
		width="525" height="26" alt=""/>

	<DIV style="height: 320px;width: 100%; overflow-x:scroll; overflow-y:scroll;visibility: visible;z-index:1; left: 133px; top: 250px;">

	<table width="99%" align="left" border="1" cellpadding="0"
		cellspacing="0" id="tableMSA" style="border-collapse: collapse;">
		<thead>
			<tr class="noScroll">

				<td width="5%" bgcolor="#CD0504" class="colhead" align="center"><FONT
					color="white"> <SPAN>TR NO</SPAN></FONT></td>
				<td bgcolor="#CD0504" class="colhead" align="center" width="22%"><FONT
					color="white"> <SPAN>Request Type</SPAN></FONT></td>
				<td bgcolor="#CD0504" class="colhead" align="center" width="13%"><FONT
					color="white"> <SPAN>Distributor</SPAN></FONT></td>
				<td width="10%" bgcolor="#CD0504" class="colhead" align="center"><FONT
					color="white"> <SPAN>No. of account Requested</SPAN></FONT></td>
				<td width="10%" bgcolor="#CD0504" class="colhead" align="center"><FONT
					color="white"> <SPAN>No. of account Transferred</SPAN></FONT></td>
				<td bgcolor="#CD0504" class="colhead" align="center" width="13%"><FONT
					color="white"> <SPAN>Requested By</SPAN></FONT></td>
				<td bgcolor="#CD0504" class="colhead" align="center" width="10%"><FONT
					color="white"> <SPAN>Requested On</SPAN></FONT></td>
				<td bgcolor="#CD0504" class="colhead" align="center" width="9%"><FONT
					color="white"> <SPAN>Action</SPAN></FONT></td>
				<td bgcolor="#CD0504" class="colhead" align="center" width="9%"><FONT
					color="white"> <SPAN>Stock Details</SPAN></FONT></td>
			</tr>
		</thead>

		<tbody>
			<logic:empty name="InterSSDFormBean" property="arrList">
				<TR>
					<TD class="text" align="center" colspan="8" width="853">No
					record available</TD>
				</TR>
			</logic:empty>

			<logic:notEmpty name="InterSSDFormBean" property="arrList">
				<logic:iterate name="InterSSDFormBean" id="arrList" indexId="i"
					property="arrList">
					<TR BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>'>


						<TD align="center" class="text"><bean:write name="arrList"
							property="tr_no" /></TD>
						<TD align="center" class="text"><bean:write name="arrList"
							property="tr_type" /></TD>
						<TD align="center" class="text"><bean:write name="arrList"
							property="from_dist" /></TD>
						<TD align="center" class="text"><bean:write name="arrList"
							property="init_unit" /></TD>
						<TD align="center" class="text"><bean:write name="arrList"
							property="trans_unit" /></TD>
						<TD align="center" class="text"><bean:write name="arrList"
							property="created_by" /></TD>
						<TD align="center" class="text"><bean:write name="arrList"
							property="created_on" /></TD>


						<TD align="center" class="text"><a href="#"
							onclick="javascript:getdetails('<bean:write name="arrList" property="tr_no"/>','<bean:write name="arrList" property="tr_type_int"/>','<bean:write name="arrList" property="tr_sub_type_int"/>')">
						<logic:equal value="0" property="remain_qty" name="arrList">View</logic:equal>
						<logic:notEqual value="0" property="remain_qty" name="arrList">Transfer</logic:notEqual>
						</a></TD>
						<TD align="center" class="text"><a href="#" onclick="viewTRDetail('<bean:write name='arrList' property='tr_no'/>')">
		View Stock					
	</a></TD>
					</TR>
				</logic:iterate>
			</logic:notEmpty>
		</tbody>
	</table>
	</DIV>
	<br>
	<!--<DIV>
		<table width="80%">
			<tr>
				<td width="100%" align="center">
					<input type="button" value="Submit" onclick="saveMissingStock();" >
				</td>
			</tr>
		</table>
	</DIV>
	--><html:hidden property="methodName"/>
	
</html:form>
</body>
</html:html>