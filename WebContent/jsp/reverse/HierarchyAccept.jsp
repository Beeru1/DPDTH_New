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

<title>HierarchyTransferAccept</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<script type="text/javascript">
function viewTRDetail(trNo, trnsBy, trnsTime)
{
	var url="dpHierarchyAccept.do?methodName=viewHeirarchyAccept&TR_NO="+trNo+"&TRNS_BY="+trnsBy+"&TRNS_TIME="+trnsTime;
	window.open(url,"HierarchyTransfer","width=900,height=400,scrollbars=yes,toolbar=no");
	window.moveTo(0,0);
}

function acceptHierarchy(frmObj)
{
    var frm = frmObj.form;
    //alert(frm);
	if(validateAccept(frm))
	{
		frm.methodName.value = "acceptHierarchy";
		
		if( confirm("Are you confirm to add these Retailers/FSEs in your Hierarchy?") )
		{
					frm.submit();			
		}
		}
	else
	{
		alert("No row/rows selected");
	}
}

function validateAccept(frm)
{
	var flag = false;
	if(frm.strCheckedTR==null)
	{
		return false;
	}
	else if(frm.strCheckedTR.length != undefined)
	{
		for (count = 0; count < frm.strCheckedTR.length; count++)
		{
	     	if(frm.strCheckedTR[count].checked==true)
			{
				flag = true;
			}
		}
	}
	else if(frm.strCheckedTR.checked==true)
	{
		flag = true;
	}
	return flag;
}

</script>
<html:form action="/dpHierarchyAccept">
<html:hidden property="methodName"/>
<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>
	<IMG src="<%=request.getContextPath()%>/images/Accept_Heir_Trans.jpg"
		width="500" height="24" alt=""/>
		<logic:notEmpty property="strSuccessMsg" name="HierarchyAccept">
			<BR>
				<strong><font color="red"><bean:write property="strSuccessMsg"  name="dpMissingStockApprovalBean"/></font></strong>
			<BR>
		</logic:notEmpty>
		<table width="100%" align="left" border="0" cellpadding="0" cellspacing="0" 
				style="border-collapse: collapse;">
			<TR>
				<TD width='100%'>
					<DIV style="height: 300px; overflow: auto;" width="100%">
						<table width="100%" align="left" border="1" cellpadding="0" cellspacing="0" 
								style="border-collapse: collapse;">
							<thead>
								<TR style="position: relative; top:expression(this.offsetParent.scrollTop-2);" class="text white">
								<td bgcolor="#CD0504" class="colhead" align="center" width="4%"><FONT color="white">&nbsp;</FONT></td>
									<td bgcolor="#CD0504" class="colhead" align="center" width="14%"><FONT color="white"><bean:message  bundle="dp" key="label.HTA.TRNO"/></FONT></td>
									<td bgcolor="#CD0504" class="colhead" align="center" width="20%"><FONT color="white"><bean:message  bundle="dp" key="label.HTA.TR_TYPE"/></FONT></td>
									<td bgcolor="#CD0504" class="colhead" align="center" width="15%"><FONT color="white"><bean:message  bundle="dp" key="label.HTA.REQ_BY"/></FONT></td>
									<td bgcolor="#CD0504" class="colhead" align="center" width="9%"><FONT color="white"><bean:message  bundle="dp" key="label.HTA.REQ_ON"/></FONT></td>
									<td bgcolor="#CD0504" class="colhead" align="center" width="15%"><FONT color="white"><bean:message  bundle="dp" key="label.HTA.TR_BY"/></FONT></td>
									<td bgcolor="#CD0504" class="colhead" align="center" width="9%"><FONT color="white"><bean:message  bundle="dp" key="label.HTA.TR_ON"/></FONT></td>
									<td bgcolor="#CD0504" class="colhead" align="center" width="7%"><FONT color="white"><bean:message  bundle="dp" key="label.HTA.TR_QTY"/></FONT></td>				
									<td bgcolor="#CD0504" class="colhead" align="center" width="7%"><FONT color="white"><bean:message  bundle="dp" key="label.HTA.VIEW"/></FONT></td>				
								</TR>
							</thead>
							<tbody>
								<logic:empty name="HierarchyAccept" property="listTransferAccept">
									<TR>
										<TD class="text" align="center" colspan="8">No Transfer Acceptance available</TD>
									</TR>			
								</logic:empty>
								
								<logic:notEmpty name="HierarchyAccept" property="listTransferAccept">
									<logic:iterate name="HierarchyAccept" id="dpHTAList" indexId="i" property="listTransferAccept">
										<TR BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>' >
											<TD align="center" class="text">
												<input type="checkbox" id="strCheckedTR" name="strCheckedTR" 
													value='<bean:write name='dpHTAList' property='strTransferReq'/>' />
											</TD>
											
											<TD align="center" class="text">
												<bean:write name="dpHTAList" property="strTRNO"/>					
											</TD>
											
											<TD align="center" class="text">
												<bean:write name="dpHTAList" property="strTransferType"/>					
											</TD>
											
											<TD align="center" class="text">
												<bean:write name="dpHTAList" property="strRequestBy"/>					
											</TD>
											
											<TD align="center" class="text">
												<bean:write name="dpHTAList" property="strRequestOn"/>					
											</TD>
											
											<TD align="center" class="text">
												<bean:write name="dpHTAList" property="strTransferBy"/>					
											</TD>
											
											<TD align="center" class="text">
												<bean:write name="dpHTAList" property="strTransferOn"/>					
											</TD>
											
											<TD align="center" class="text">
												<bean:write name="dpHTAList" property="intTransferQty"/>					
											</TD>
											
											<TD align="center" class="text">
												<a href="#" onclick="viewTRDetail('<bean:write name='dpHTAList' property='strTRNO'/>',
																					'<bean:write name='dpHTAList' property='intTrnsBy'/>',
																					'<bean:write name="dpHTAList" property="tsTrnsTime"/>')">
													<bean:message  bundle="dp" key="label.HTA.VIEW_TEXT"/>					
												</a>					
											</TD>
										</TR>
									</logic:iterate>
								</logic:notEmpty>
							</tbody>
						
						</table>
					</DIV>
				</TD>
			</TR>
			<TR>
				<TD width='100%'>&nbsp;</TD>
			</TR>
			
			<TR>
				<td width='100%' align="center">
					<input type='button' value='Accept' onclick='acceptHierarchy(this);'/>
				</td>				
			</TR>
		</table>
	</html:form>
</body>
</html:html>