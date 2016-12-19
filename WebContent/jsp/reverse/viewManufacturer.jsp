<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>


<%@page import="com.ibm.dp.dto.ManufacturerDetailsDto"%><html>

<head>
<link rel="stylesheet" href="../../theme/Master.css" type="text/css">
<title>Manufacturer Details </title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script>
function SelectAll()
	{
		var inputs = document.getElementsByTagName('input');
	    
	    var chkAll=document.getElementById("chkAll");
	  	if(inputs.length>1)
	  	{
	     if(chkAll.checked ==true)
	     {
	   		 for (var i =1; i < inputs.length; i++) 
	   		 {
	
	          if (inputs[i].type == 'checkbox') 
	        	inputs[i].checked=true;
	          }
	     }
	     else
	     {
	         for (var a=1; a < inputs.length; a++) 
	         {
	
	          if (inputs[a].type == 'checkbox') 
	        	  inputs[a].checked=false;
	         }
	       }
	   }
	}
	
	function editManufacturer()
	{	var flag=[];
		var result=[];
		var data =document.forms[0].box; 
    document.forms[0].action="manufacturerDetails.do?methodName=getManufacturerDetails";
    document.forms[0].submit();

}
		
	
	
</script>
</head>
<body background="<%=request.getContextPath()%>/images/bg_main.gif"
	background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">
<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
    
%>


<html:form styleId="manuList" action="/manufacturerDetails.do" name="ManufacturerDetailsBean" type="com.ibm.dp.beans.ManufacturerDetailsBean" enctype="multipart/form-data">
<html:hidden property="methodName"/>
<table>
				<tr>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/viewManufacturer.gif"
						width="505" height="22" alt="">
					
					</TD>
				</tr>
			</table>
<table width="80%" align="left" border="1" cellpadding="0" cellspacing="0" id="tableTH"
			style="border-collapse: collapse;">
			<thead>
				<tr class="noScroll">
					<td width='5%' bgcolor="#CD0504" class="colhead" align="center">
						<FONT color="white">
						<input id="chkAll" type="checkbox" onclick="SelectAll()" disabled="disabled" >	
							</FONT>
					</td>
					<td width='15%' bgcolor="#CD0504" class="colhead" align="center"> <FONT color="white">Manufacturer Id</FONT></td>
					
					<td width='15%' bgcolor="#CD0504" class="colhead" align="center"> <FONT color="white">Model Number</FONT></td>
					
					<td width='20%' bgcolor="#CD0504" class="colhead" align="center"> <FONT color="white">Manufacturer</FONT></td>
					
				</tr>
			</thead>
			<tbody>
			
				<logic:empty name="ManufacturerDetailsBean" property="manufacturerList">
					<logic:iterate name="ManufacturerDetailsBean" id="userList" indexId="i" property="manufacturerList" >
					<TR id='<%="tr"+i%>'  BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>'>
						<td align="center" class="text">
						<logic:equal  name="userList" property ="selectionFlag" value="Y">
							<input id='<%="checkbox"+i%>' type="checkbox"   name="box"  value='<bean:write name="userList" property="manufacturerId" />' 
								checked="checked" disabled="disabled" >
						</logic:equal>
						<logic:notEqual  name="userList" property ="selectionFlag" value="Y" >
							<input id='<%="checkbox"+i%>' type="checkbox"   name="box"  value='<bean:write name="userList"  property="manufacturerId" />'   
							 	disabled="disabled" >
						</logic:notEqual>		
						</td>
						<TD align="center" class="text">
								<bean:write name="userList" property="manufacturerId"/>					
						</TD>
						<TD align="center" class="text">
								<bean:write name="userList" property="modelNumber"/>					
						</TD>
						<TD align="center" class="text">
								<bean:write name="userList" property="manufacturer"/>					
						</TD>
					</TR>
					</logic:iterate>			
				</logic:empty>
				<logic:notEmpty name="ManufacturerDetailsBean" property="manufacturerList">
					<logic:iterate name="ManufacturerDetailsBean" id="userList" indexId="i" property="manufacturerList" >
					<TR id='<%="tr"+i%>'  BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>'>
						<td align="center" class="text">
						<logic:equal  name="userList" property ="selectionFlag" value="Y">
							<input id='<%="checkbox"+i%>' type="checkbox"   name="box"  value='<bean:write name="userList" property="manufacturerId" />' 
								checked="checked" disabled="disabled" >
						</logic:equal>
						<logic:notEqual  name="userList" property ="selectionFlag" value="Y" >
							<input id='<%="checkbox"+i%>' type="checkbox"   name="box"  value='<bean:write name="userList"  property="manufacturerId" />'   
							 	disabled="disabled" >
						</logic:notEqual>		
						</td>
						<TD align="center" class="text">
								<bean:write name="userList" property="manufacturerId"/>					
						</TD>
						<TD align="center" class="text">
								<bean:write name="userList" property="modelNumber"/>					
						</TD>
						<TD align="center" class="text">
								<bean:write name="userList" property="manufacturer"/>					
						</TD>
					</TR>
					</logic:iterate>
				</logic:notEmpty>
			</tbody>
			
		</table>
		<table width="10%" >
		<tr>		
			<td align="center" style="height: 130px;">	<input type="button" value="Edit" onclick = "editManufacturer()" ></td>
		</tr>			
		</table>
</html:form>
</body>
</html>