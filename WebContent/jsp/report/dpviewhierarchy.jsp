<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="${pageContext.request.contextPath}/theme/text.css" rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/theme/chromestyle2.css" />
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/scripts/chrome.js"></SCRIPT>
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/scripts/JScriptLib.js"></SCRIPT>
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/scripts/validation.js"></SCRIPT>
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/scripts/Cal.js"></SCRIPT>
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/scripts/validation.js"></SCRIPT>
   
<style type="text/css">
.noScroll{
position: relative;
	 top: 0px;
	left: 0px;
	 top:expression(this.offsetParent.scrollTop-2);
  
}
</style>

<TITLE><bean:message bundle="view" key="application.title" /></TITLE>

<SCRIPT language="javascript" type="text/javascript"><!-- 
function checkforall()
{
		var flag="false";
		
		if(document.forms[0].strCheckedVHD.length == undefined)
		{
			if(document.forms[0].strCheckedVHD.checked == false)
			{
				document.getElementById("strCheckedVHDAll").checked=false;
				return;
			}
			else
			{
				document.getElementById("strCheckedVHDAll").checked=true;
				return;
			}
		}
		
		
		
		for(var i=0; i < document.forms[0].strCheckedVHD.length; i++)
		{
			if(document.forms[0].strCheckedVHD[i].checked == false)
			{
				document.getElementById("strCheckedVHDAll").checked=false;
				flag = "true";
			}
		}
	if(flag=="false")
	{
		document.getElementById("strCheckedVHDAll").checked=true;
	}
}
function viewAllHierarchy()
{
	var flag="false";
	if(document.forms[0].strCheckedVHD.length == undefined)
	{
		if(document.forms[0].strCheckedVHD.checked == false)
		{
			alert("Please Select atleast one Distributor");
			return false;
		}
		else
		{
			flag = "true";
		}
	}
	else
	{
		for(var i=0; i < document.forms[0].strCheckedVHD.length; i++)
			{
				
				//alert(document.forms[0].strCheckedVHD[i].checked);
				if(document.forms[0].strCheckedVHD[i].checked == true)
				{
					flag="true";
				}
			}
	}
	if(flag == "false")
	{
		alert("Please Select atleast one Distributor");
		return false;
	}
	if(flag == "true")
	{
				var url = "viewhierarchy.do?methodName=viewAllHierarchy";
				document.forms[0].action=url;
				document.forms[0].method="post";
				document.forms[0].submit();
	}
}


function checkAll()
{
	var all = document.getElementById("strCheckedVHDAll").checked;
	if(all  == true)
	{ 
		if(document.forms[0].strCheckedVHD.length == undefined)
		{
				document.forms[0].strCheckedVHD.checked=true;
				return;
		}
		for(var i=0; i < document.forms[0].strCheckedVHD.length; i++)
		{
			document.forms[0].strCheckedVHD[i].checked=true;
		}
	}
	else
	{
		if(document.forms[0].strCheckedVHD.length == undefined)
		{
				document.forms[0].strCheckedVHD.checked=false;
				return;
		}
		for(var i=0; i < document.forms[0].strCheckedVHD.length; i++)
		{
			document.forms[0].strCheckedVHD[i].checked=false;
		}
	}
}
function viewHierarchy()
{
	 var levelId = document.getElementById("accountId").value;
	 var circleId = document.getElementById("circleid").value;
	
	if(circleId == "")
	{
		alert("Please Select Circle");
		return false;
	}
	if(levelId == "")
	{
		alert("Please Select TSM");
		return false;
	}
	
	
	
	var url = "viewhierarchy.do?methodName=initDistReport&parentId="+levelId+"&circleId="+circleId;
	
	document.forms[0].action=url;
	document.forms[0].method="post";
	document.forms[0].submit();
	
}


	
	function exportToExcel()
	{
		var url = "viewhierarchy.do?methodName=initDistReportExcel";
		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();
	} 
	
	function getTsmName() 
	{

	    var levelId = document.getElementById("circleID").value;

	    if(levelId!=''){
			var url = "viewhierarchy.do?methodName=initTsmReport&levelId="+levelId;
			if(window.XmlHttpRequest) 
			{
				req = new XmlHttpRequest();
			}else if(window.ActiveXObject) 
			{
				req=new ActiveXObject("Microsoft.XMLHTTP");
			}
			if(req==null) 
			{
				alert("Browser Doesnt Support AJAX");
				return;
			}
			req.onreadystatechange = getTsmNameChange;
			req.open("POST",url,false);
			req.send();
		}else{
			resetValues(1);

		}
	}
	
	function resetValues(flag){
		var accountId = document.getElementById("accountId");
		var distAccId = document.getElementById("distAccId");
		if(flag==1){
			accountId.options.length = 0;
			accountId.options[accountId.options.length] = new Option("Select a TSM","");
			distAccId.options.length = 0;
			distAccId.options[distAccId.options.length] = new Option("Select a Distributor","");
		}
		if(flag==2){
			distAccId.options.length = 0;
			distAccId.options[distAccId.options.length] = new Option("Select a Distributor","");
		}
	}

	function getTsmNameChange()
	{
		if (req.readyState==4 || req.readyState=="complete") 
	  	{
			var xmldoc = req.responseXML.documentElement;
			if(xmldoc == null) 
			{
				return;
			}
			
			// Get Conditions.
			optionValues = xmldoc.getElementsByTagName("option");
			var selectObj = document.getElementById("accountId");
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select a TSM","");
			selectObj.options[selectObj.options.length] = new Option("All","0");
			for(var i=0; i<optionValues.length; i++)
			{
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			}
		}
	}	
		
	function getDistName()
	{
	    var levelId = document.getElementById("accountId").value;
	    var circleId = document.getElementById("circleid").value;
	
	 	if(levelId!=''){
			var url = "reverseLogisticReport.do?methodName=initDistReport&parentId="+levelId+"&circleId="+circleId;
			if(window.XmlHttpRequest) 
			{
				req = new XmlHttpRequest();
			}else if(window.ActiveXObject) 
			{
				req=new ActiveXObject("Microsoft.XMLHTTP");
			}
			if(req==null) 
			{
				alert("Browser Doesnt Support AJAX");
				return;
			}
			req.onreadystatechange = getDistNameChange;
			req.open("POST",url,false);
			req.send();
		}else{
			resetValues(2);
		}
	}

	function getDistNameChange()
	{
		if (req.readyState==4 || req.readyState=="complete") 
		
	  	{
			var xmldoc = req.responseXML.documentElement;
			if(xmldoc == null) 
			{
				return;
			}
			// Get Conditions.
			optionValues = xmldoc.getElementsByTagName("option");
			var selectObj = document.getElementById("distAccId");
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select a Distributor","");
			selectObj.options[selectObj.options.length] = new Option("All","0");
			for(var i=0; i<optionValues.length; i++)
			{
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			}
		}
	}
	
	
	
	
	--></SCRIPT>

</HEAD>



<BODY background="${pageContext.request.contextPath}/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onload="">
<%try{ %>
<TABLE cellspacing="0" cellpadding="0" border="0" width="100%" 	height="100%" valign="top">
	
	<TR>
		<TD colspan="2" valign="top"><jsp:include page="../common/dpheader.jsp" flush="" /></TD>
	</TR>
	
	<html:form name="DPViewHierarchyFormBean" action="/viewhierarchy" type="com.ibm.dp.beans.DPViewHierarchyFormBean" >
	<html:hidden property="methodName" value="getReverseLogisticReportExcel"/>
	
	
	<TR valign="top">
		<TD width="167" align="left" valign="top"	height="100%"><jsp:include
				page="../common/leftHeader.jsp" flush="" /></TD>
		<td>
			<TABLE width="600"  border="0" cellpadding="5" cellspacing="0" class="text" >
				<TR valign="top" height="30">
					<TD colspan="6" class="text" align="left"><img
						src="${pageContext.request.contextPath}/images/View Hierarchy Dist.jpg"
						width="410" height="21" alt=""><BR></TD>
		
				</TR>
				
				<TR valign="top">
					<TD align="right" width="20%"><b class="text pLeft15"> <bean:message
						bundle="view" key="productcreate.circlelist" /><font color="red">*</font></b>
					</td>
				<td align="left" width="20%">	 
					<logic:match value="A" property="showCircle" name="DPViewHierarchyFormBean">		
						<html:select styleId="circleid" property="circleid"
							name="DPViewHierarchyFormBean" style="width:150px"
							style="height:20px" onchange="javascript:getTsmName();">
							<html:option value=""> 
								<bean:message bundle="view" key="product.selectcircle" />
							</html:option>
							<logic:notEmpty name="DPViewHierarchyFormBean" property="arrCIList">
								<html:optionsCollection name="DPViewHierarchyFormBean"	property="arrCIList" />
							</logic:notEmpty>
						</html:select>
						</logic:match>
						
						<logic:match value="N" property="showCircle" name="DPViewHierarchyFormBean">		
						<html:select styleId="circleid" property="circleid" name="DPViewHierarchyFormBean" style="width:150px"
							style="height:20px" disabled="true"  onchange="javascript:getTsmName();">
							<html:option value="">
								<bean:message bundle="view" key="product.selectcircle" />
							</html:option>
							<logic:notEmpty name="DPViewHierarchyFormBean"
								property="arrCIList">
								<html:optionsCollection name="DPViewHierarchyFormBean"
									property="arrCIList" />
							</logic:notEmpty>
						</html:select>
						</logic:match>
					</TD>
				
					<TD align="right" width="20%">
						<b class="text pLeft15">TSM<font color="red">*</font></b>
					</td>
					<TD align="left" width="20%">				   
						<html:select property="accountId"  name="DPViewHierarchyFormBean">
							<html:option value="">- Select a TSM -</html:option>
							<logic:notEmpty property="tsmList" name="DPViewHierarchyFormBean">
							<option value="0">Select all TSMs</option>
						<!--<html:options labelProperty="accountName" property="accountId"	collection="tsmList" /> -->
						<html:optionsCollection name="DPViewHierarchyFormBean"	property="tsmList" label="accountName" value="accountId"/>
						</logic:notEmpty>
						</html:select>
					</TD>
				
				
					<td align="left" width="20%">
						<!--<html:button property="excelBtn" value="Export To Excel" onclick="return exportToExcel();"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;-->
						<html:button property="excelBtn" value="Go" onclick="return viewHierarchy();"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
	 
	 <logic:match value="A" property="showCircle" name="DPViewHierarchyFormBean">
		 <script>
		 	document.getElementById("circleid").value = "";
		 </script>
	 </logic:match>
			
<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>	
<logic:equal value="true" name="DPViewHierarchyFormBean" property="strGetReport">
<logic:notEmpty property="getHierarchyList" name="DPViewHierarchyFormBean">
<tr>
<td colspan="6" class="text" style="height: 300px;" >
<DIV style="height: 280px;width: 800px; overflow-x:scroll; overflow-y:scroll;visibility: visible;z-index:1; left: 133px; top: 250px;">
		<table width="100%" align="left" border="1" cellpadding="0" cellspacing="0" id="tableMSA"
		style="border-collapse: collapse;">
			<thead>
				<tr class="noScroll">
					<!-- <td width="5%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN>Sr. No.</SPAN></FONT></td>-->
					<td width="5%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="dp" key="label.hierarchy.loginname"/></SPAN></FONT></td>
					<td width="5%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="dp" key="label.hierarchy.accountname"/></SPAN></FONT></td>
					<td width="5%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="dp" key="label.hierarchy.contactname"/></SPAN></FONT></td>
					<td width="7%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="dp" key="label.hierarchy.oraclecode"/></SPAN></FONT></td>
					<td width="5%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="dp" key="label.hierarchy.lapunumber"/></SPAN></FONT></td>
					<td width="5%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="dp" key="label.hierarchy.parentloginname"/></SPAN></FONT></td>
					<td width="5%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="dp" key="label.hierarchy.parentname"/></SPAN></FONT></td>
					<td width="5%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="dp" key="label.hierarchy.circle"/></SPAN></FONT></td>
					<td width="5%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="dp" key="label.hierarchy.address"/></SPAN></FONT></td>
					<td width="5%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="dp" key="label.hierarchy.city"/></SPAN></FONT></td>
					<td width="5%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="dp" key="label.hierarchy.pincode"/></SPAN></FONT></td>
					<td width="5%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="dp" key="label.hierarchy.botreecode"/></SPAN></FONT></td>
					<td width="5%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="dp" key="label.hierarchy.emailid"/></SPAN></FONT></td>
					<td width="5%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="dp" key="label.hierarchy.mobilenumber"/></SPAN></FONT></td>
					<td width="5%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><input type="checkbox" id="strCheckedVHDAll" property="strCheckedVHDAll" name="strCheckedVHDAll" onclick="return checkAll();">	</SPAN></FONT></td>
				</tr>
			</thead>
			
			<tbody>
				<logic:empty name="DPViewHierarchyFormBean" property="getHierarchyList">
					<TR>
						<TD class="text" align="center" colspan="8">No record available</TD>
					</TR>			
				</logic:empty>
				
				<logic:notEmpty name="DPViewHierarchyFormBean" property="getHierarchyList">
					<logic:iterate name="DPViewHierarchyFormBean" id="dpHierarchyList" indexId="i" property="getHierarchyList">
						<TR  BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>'>
							<!-- <TD align="center" class="text">
								<%=(i.intValue()+1) %>					
							</TD>-->
							<TD align="center" class="text" width="5%">
								<bean:write name="dpHierarchyList" property="login_name"/>					
							</TD>
							<TD align="center" class="text" width="5%">
								<bean:write name="dpHierarchyList" property="account_name"/>					
							</TD>
							<TD align="center" class="text" width="5%">
								<bean:write name="dpHierarchyList" property="contact_name"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="dpHierarchyList" property="oracle_code"/>					
							</TD>
							<TD align="center" class="text" width="5%">
								<bean:write name="dpHierarchyList" property="lapu_number"/>					
							</TD>
							<TD align="center" class="text" width="5%">
								<bean:write name="dpHierarchyList" property="parent_login_name"/>					
							</TD>
							<TD align="center" class="text" width="5%">
								<bean:write name="dpHierarchyList" property="parent_name"/>					
							</TD>
							<TD align="center" class="text" width="5%">
								<bean:write name="dpHierarchyList" property="circle_name"/>					
							</TD>
							<TD align="center" class="text" width="5%">
								<bean:write name="dpHierarchyList" property="address"/>					
							</TD>
							<TD align="center" class="text" width="5%">
								<bean:write name="dpHierarchyList" property="city_name"/>					
							</TD>
							<TD align="center" class="text" width="5%">
								<bean:write name="dpHierarchyList" property="pin_code"/>					
							</TD>
							
							<TD align="center" class="text" width="5%">
								<bean:write name="dpHierarchyList" property="botree_code"/>					
							</TD>
							<TD align="center" class="text" width="5%">
								<bean:write name="dpHierarchyList" property="email_id"/>					
							</TD>
							<TD align="center" class="text" width="5%">
								<bean:write name="dpHierarchyList" property="mobile_number"/>					
							</TD>
							<TD align="center" class="text" width="5%">
								<input type="checkbox"  id="strCheckedVHD" property="strCheckedVHD" name="strCheckedVHD" onclick="return checkforall();" value='<bean:write name="dpHierarchyList" property="account_id"/>' />	
							</TD>
							
						</TR>
					</logic:iterate>
				</logic:notEmpty>
			</tbody>
		</table>
	</DIV>	</td></tr>
	
		<tr>
					<td colspan="6" align="center">
						<html:button property="excelBtn" value="Export To Excel" onclick="return exportToExcel();"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;
						<html:button property="excelBtn" value="Get Hierarchy" onclick="return viewAllHierarchy();"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
		</tr>
				
				
		<script>
				//alert("<bean:write name='DPViewHierarchyFormBean' property='accountId'/>");
		 		document.getElementById("circleid").value = "<bean:write name='DPViewHierarchyFormBean' property='circleid'/>";
		 		document.getElementById("accountId").value = "<bean:write name='DPViewHierarchyFormBean' property='accountId'/>";
		 </script>	
	
	
</logic:notEmpty>		
</logic:equal>	
<logic:equal value="true" name="DPViewHierarchyFormBean" property="strGetReport">
<logic:empty property="getHierarchyList" name="DPViewHierarchyFormBean">
<TR valign="top">
	<TD class="text" align="center" colspan="8"><font color="red"><strong>No record available</strong></font></TD>
</TR>
</logic:empty>	
</logic:equal>		
</table>
		</td>		
		</TR>
		</html:form>
		<TR align="center" bgcolor="4477bb" valign="top">
		<TD colspan="2" height="17" align="center"><jsp:include
			page="../common/footer.jsp" flush="" /></TD>
	</TR>
	
</TABLE>
<%}catch(Exception ex){ex.printStackTrace();} %>
</BODY>
</html:html>