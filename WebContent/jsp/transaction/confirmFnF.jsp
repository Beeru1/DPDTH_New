<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<TITLE> </TITLE>
</head>
<style type="text/css">
.noScroll{
position: relative;
	 top: 0px;
	left: 0px;
	 top:expression(this.offsetParent.scrollTop-2);
  
}
</style>
<script language="javascript">
function confirmFnF(distId)
		{
		var remarkid="confirmerRemark_"+distId;
		var remark = document.getElementById(remarkid).value;
		if(remark==null || remark=="")
		{
		alert("Please enter remark.");
		document.getElementById(remarkid).focus();
		return false;
		}
		var url = "dpInitiateFnfAction.do?methodName=confirmDistFnF&distId="+distId;
		document.forms[0].action=url;
		document.forms[0].method="post";
		var r=confirm("Do you want to perform this action? ");
		if (r==true)
  		{
		document.forms[0].submit();	
		}
		}
				
function viewStock(distId,tsmId)
{
  var url="dpInitiateFnfAction.do?methodName=viewStock&distId="+distId+"&tsmId="+tsmId;
  window.open(url,"SerialNo","width=700,height=600,scrollbars=yes,toolbar=no");
}	
	
function viewHierarchyReport(distId)
{
  var url="dpInitiateFnfAction.do?methodName=viewHierarchyReport&distId="+distId;
  window.open(url,"SerialNo","width=700,height=600,scrollbars=yes,toolbar=no");
}		
</script>

<body>
<html:form action="/dpInitiateFnfAction.do"  method="post">
<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>
<br>
 <IMG src="<%=request.getContextPath()%>/images/confirmFnF.jpg"
		width="550" height="25" alt=""/>
    <center>   
  <%
  if(!"null".equals(request.getAttribute("msg")+""))
   {
    
    %>
    <%="<b>"+request.getAttribute("msg")+"</b><br>" %> 
   <%
   request.removeAttribute("msg");
   }
   %>
    <div style="width:100%; height:500px; overflow:scroll;" >
	<TABLE width="90%" border="0" cellpadding="1" cellspacing="1" align="center" class ="border" style="overflow: scroll" >
		<TBODY>
			<TR>
				<TD></TD>
				<TD width="400"></TD>
			</TR>
			<TR id="showRestriction">
				<TD colspan="3">
   				   <fieldset style="border-width:5px">
			  	 	 <legend> <STRONG><FONT color="#C11B17" size="3">FnF: Distributor's List</FONT></STRONG></legend>
	<table width="100%" class="mTop30" align="center" border="0" id="distributorListTable">
		<TR>
			<td colspan="8" align="left"><font color="red"><strong>
				 <logic:notEqual name="DpInitiateFnfFormBean" property="strMsg" value="true">
				 		<bean:write name="DpInitiateFnfFormBean" property="strMsg"/>
				 </logic:notEqual></strong></font>
			</td>
		</TR>
					
		<tr class="text white">
			<TD bgcolor="#CD0504" align="center"><FONT color="white" class="text" align="center">Distributor ID.</FONT></TD>
			<TD bgcolor="#CD0504" align="center"><FONT color="white" class="text" align="center">Distributor Name.</FONT></TD>
			<TD bgcolor="#CD0504" align="center"><FONT color="white" class="text" align="center">Days Since Last Activation.</FONT></TD>
			<TD bgcolor="#CD0504" align="center"><FONT color="white" class="text" align="center">TSM</FONT></TD>
			<TD bgcolor="#CD0504" align="center"><FONT color="white" class="text" align="center">TSM Remarks</FONT></TD>
			<TD bgcolor="#CD0504" align="center"><FONT color="white" class="text" align="center">ZSM</FONT></TD>
			<TD bgcolor="#CD0504" align="center"><FONT color="white" class="text" align="center">ZSM Remarks</FONT></TD>
			<TD bgcolor="#CD0504" align="center"><FONT color="white" class="text" align="center">Debit Reqd.</FONT></TD>
			<TD bgcolor="#CD0504" align="center"><FONT color="white" class="text" align="center">View Stock</FONT></TD>
			<TD bgcolor="#CD0504" align="center"><FONT color="white" class="text" align="center">View Hierarchy Report</FONT></TD>
			<TD bgcolor="#CD0504" align="center"><FONT color="white" class="text" align="center">Remark</FONT></TD>
			<TD bgcolor="#CD0504" align="center"><FONT color="white" class="text" align="center">Confirm FnF</FONT></TD>
		</tr>
		<logic:notEmpty name="DIST_LIST">
		 <bean:define id="distList" name="DIST_LIST" type="java.util.ArrayList" scope="request" />
		   <logic:notEmpty name="distList" >
			<logic:iterate name="distList" id="report" indexId="i" type="com.ibm.dp.dto.DpInitiateFnfDto">
				<TR BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>'>
					<TD align="center"><FONT color="black" class="text"><bean:write name="report" property="strDistOlmId"/></FONT>
											<input type="hidden" name="distOlmId_${report.distId}" value="${report.strDistOlmId}" />
											<input type="hidden" name="reqId_${report.distId}" value="${report.reqId}" />
					</TD>
					<TD align="center"><FONT color="black" class="text"><bean:write name="report" property="strDistName"/></FONT></TD>
					<TD align="center"><FONT color="black" class="text"><bean:write name="report" property="days"/></FONT></TD>
					<TD align="center"><FONT color="black" class="text"><bean:write name="report" property="approverName"/></FONT></TD>
					<TD align="center"><FONT color="black" class="text"><bean:write name="report" property="approverRemark"/></FONT></TD>
					<TD align="center"><FONT color="black" class="text"><bean:write name="report" property="confirmerName"/></FONT></TD>
					<TD align="center"><FONT color="black" class="text"><bean:write name="report" property="confirmerRemark"/></FONT></TD>
					<TD align="center"><FONT color="black" class="text"><bean:write name="report" property="debitReq"/></FONT></TD>
					<TD align="center"><FONT color="black" class="text"><a href="#" onclick="viewStock('${report.distId}','${report.tsmId}')" name="stockList">View Stock</FONT></TD>
					<TD align="center"><FONT color="black" class="text"><a href="#" onclick="viewHierarchyReport('${report.distId}')" >View Hierarchy Report</FONT></TD>
					<TD align="center"><FONT color="black" class="text"><input type="text" name="confirmerRemark_${report.distId}" property="confirmerRemark" id="confirmerRemark_${report.distId}"  ></FONT></TD>
					<TD align="center"><FONT color="black" class="text"><input type=button value="Confirm FnF" onclick="confirmFnF('${report.distId}','${report.reqId}')"/></FONT></TD>	
				</TR>
			</logic:iterate>
		  </logic:notEmpty>
	    </logic:notEmpty>
	</table>
   <TABLE width="60%" border="0" cellpadding="1" cellspacing="1" align="center" class ="border">
	<tr>
		<td width="25%" >&nbsp;</td>
		<td align="left" width="55%"></td>
	</tr>
	</TABLE>
	</TD></TR>
	</TBODY>
  </TABLE>
  </div></center>
</html:form>
</body>
</html>