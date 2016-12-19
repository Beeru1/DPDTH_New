<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@page import="java.util.ArrayList" %>
<%@page import="com.ibm.hbo.dto.DistStockDTO" %>
<%try{ %>   
<%@page import="com.ibm.dp.common.Constants"%>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/theme/Master.css" type="text/css">
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT language="JavaScript" 
  src="<%=request.getContextPath()%>/scripts/subtract.js"
	type="text/javascript"></SCRIPT>

	
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/scripts/validation.js" type="text/javascript">
</SCRIPT>

<SCRIPT language="javascript" src="<%=request.getContextPath()%>/scripts/DropDownAjax.js" type="text/javascript">
</SCRIPT>
<SCRIPT language="javascript" src="<%=request.getContextPath()%>/scripts/Ajax.js" type="text/javascript">
</SCRIPT>


<link rel="stylesheet" href="${pageContext.request.contextPath}/theme/Master.css" type="text/css">
<style>
 .odd{background-color: #FCE8E6;}
 .even{background-color: #FFFFFF;}
</style>
<script language="JavaScript"><!--




function getActivationStatus(){

	    var circleId = document.getElementById("fromCircleId").value;
	 	    	
			if(circleId=="-1"){
			document.getElementById("showRestriction").style.display = 'none';	
			document.getElementById("showRestrictionInv").style.display = 'none';
		
			
			}
	   
	    else{
	   	    document.getElementById("showRestriction").style.display = 'inline';
			document.getElementById("showRestrictionInv").style.display = 'inline';
		if(circleId!=''){
			var url = "nonSerializedActivationAction.do?methodName=getActivationStatus&circleId="+circleId;
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
			req.onreadystatechange = getStatusChange;
			req.open("POST",url,false);
			req.send();
		}
		}
	
}

function getStatusChange()
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
			var selectObj2 = document.getElementById("status");		
			var statusSelectedNser = optionValues[0].getAttribute("StatusNser");	
			var statusSelectedSer = optionValues[0].getAttribute("StatusSer");
			var statusSelectedInvNser = optionValues[0].getAttribute("StatusInvNser");
			var statusSelectedInvSer = optionValues[0].getAttribute("StatusInvSer");	
			
				if(statusSelectedNser==0){
					document.getElementById("allowedNser").checked =true;
				}
				else if(statusSelectedNser==1){
					document.getElementById("notAllowedNser").checked =true;
				}
				
				if(statusSelectedSer==0){
					document.getElementById("allowedSer").checked =true;
				}
				else if(statusSelectedSer==1){
					document.getElementById("notAllowedSer").checked =true;
				}
				
				if(statusSelectedInvNser==0){
					document.getElementById("allowedInvNser").checked =true;
				}
				else if(statusSelectedInvNser==1){
					document.getElementById("notAllowedInvNser").checked =true;
				}
				
				if(statusSelectedInvSer==0){
					document.getElementById("allowedInvSer").checked =true;
				}
				else if(statusSelectedInvSer==1){
					document.getElementById("notAllowedInvSer").checked =true;
				}
					
		}
	}	
	
	function updateStatus(){	
	var circleId = document.getElementById("fromCircleId").value;
	if(circleId=="" || circleId=="-1"){
	alert("Please select any circle.");
	return false;
	}
	var circleName = document.getElementById('fromCircleId').options[document.getElementById("fromCircleId").selectedIndex].text;
	var allowedNser = document.getElementById("allowedNser").checked;
	var notallowedNser = document.getElementById("notAllowedNser").checked;
	
	var allowedSer = document.getElementById("allowedSer").checked;
	var notallowedSer = document.getElementById("notAllowedSer").checked;
	
	var allowedInvNser = document.getElementById("allowedInvNser").checked;
	var notallowedInvNser = document.getElementById("notAllowedInvNser").checked;
	
	var allowedInvSer = document.getElementById("allowedInvSer").checked;
	var notallowedInvSer = document.getElementById("notAllowedInvSer").checked;
	
	var selStatusNser=0;
	var selStatusSer=0;
	var selStatusInvNser=0;
	var selStatusInvSer=0;
		var confirmStr  = "";
		
		var doUpdate =confirm("Please confirm the changes done for "+circleName+" circle?");
			if(doUpdate==false){
		return false;
		}
	
	if(circleId=="" || circleId==-1){
			alert("Please select Any Circle.");
			return false;
		}
	if(allowedNser){

		// var doUpdate =confirm("Are you confirm to allow non serialized stock activation for "+circleName+" circle?");
		 selStatusNser = 0;
		// if(doUpdate==false){
		//return false;
		// }
	}
	else if(notallowedNser){
		// var doUpdate =confirm("Are you confirm not to allow non serialized stock activation for "+circleName+" circle?");
		 selStatusNser = 1;
		// if(doUpdate==false){
		// 	return false;
		// }	
	}
	
	if(allowedSer){		
		 selStatusSer = 0;		
	}
	else if(notallowedSer){
		 selStatusSer =1;		
	}
	
	if(allowedInvNser){		
		 selStatusInvNser = 0;		
	}
	else if(notallowedInvNser){
		 selStatusInvNser =1;		
	}
	
	if(allowedInvSer){		
		 selStatusInvSer = 0;		
	}
	else if(notallowedInvSer){
		 selStatusInvSer =1;		
	}
	
		var url = "nonSerializedActivationAction.do?methodName=updateStatus&circleId="+circleId+"&selStatusNser="+selStatusNser+"&selStatusSer="+selStatusSer+"&selStatusInvNser="+selStatusInvNser+"&selStatusInvSer="+selStatusInvSer;
		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();	
	
	}

--></script>



</head>
<body onload="getActivationStatus();"> 
<html:form action="/nonSerializedActivationAction.do"  method="post" enctype="multipart/form-data" >
<html:hidden property="methodName" styleId="methodName"/>
<html:hidden property="successMessage" styleId="successMessage"/>
	<TABLE>
		<TBODY>
		<tr>			
			<TD>			
			
			<!-- Page title start -->
								<IMG src="${pageContext.request.contextPath}/images/Non_Serialized_Activation1.jpg"
									width="410" height="24" alt="">
					<!-- Page title end -->
			</TD>					
			<!-- Page title end -->	
		</tr>
		</TBODY>
	</TABLE>
	<TABLE width="56%" border="0" cellpadding="1" cellspacing="1"
		align="center" class ="border">
		<TBODY>
		<TR>
				<TD colspan="2">
				<logic:notEmpty property="successMessage" name="NonSerializedActivationBean">
					<BR>
						<strong><font color="red"><bean:write property="successMessage"  name="NonSerializedActivationBean"/></font></strong>
					<BR>
				</logic:notEmpty>
				</TD> 
			</TR>
		<TR>
		<td colspan='2'>&nbsp;
		</td>
		</TR>
			<TR>
				<TD></TD>
				<TD width="380"></TD>
			</TR>
			<TR  id="circle">
				<TD class="txt"><bean:message bundle="dp" key="interSSD.Circle"/><font color="red">*</font></TD>
				<TD class="txt" width="380">
				<html:select property="fromCircleId" style="width:150px" onchange="getActivationStatus()">
					<logic:notEmpty property="circleList" name="NonSerializedActivationBean" >
						<html:option value="-1">--Select Circle--</html:option>
						<bean:define id="circleList" name="NonSerializedActivationBean" property="circleList" />
						<html:options labelProperty="circleName" property="circleId" collection="circleList" />
					
					</logic:notEmpty>
				</html:select>
				</TD>
					
			</TR>
			<TR><td>&nbsp;<td width="380"></TR>
		
			<TR id="showRestriction">
				
				<TD colspan="2">
				
				<fieldset style="border-width:5px">
			  	 			<legend> <STRONG><FONT color="#C11B17" size="2"><bean:message bundle="dp" key="label.Non.Serialized.Activation" /> </FONT></STRONG></legend>				  	 	
								<table>							
									<tr>
									<TD>Non Serialized</TD>	
									<td align="left" width="252">
									
									<input type="radio" name="status" id="allowedNser" value="0">											
											<bean:message bundle="dp" key="label.Activation.Status.Allowed"/>
										
										</td>
										<td align="left" width="287">
										<input type="radio" name="status" id="notAllowedNser" value="1">		
											<bean:message bundle="dp" key="label.Activation.Status.NotAllowed"/>											
											</td>										
																		
										</tr>	
										<tr><td width="252"></BR></td></tr>
									<tr>
									<TD>Serialized</TD>
									<td align="left" width="252">	
									<input type="radio" name="statusSer" id="allowedSer" value="0">											
											<bean:message bundle="dp" key="label.Activation.Status.Allowed"/>
										
										</td>
										<td align="left" width="287">
										<input type="radio" name="statusSer" id="notAllowedSer" value="1">		
											<bean:message bundle="dp" key="label.Activation.Status.NotAllowed"/>											
											</td>										
																		
										</tr>										
								</table>
				</fieldset>
				
				</TD></TR>
				<TR id="showRestrictionInv">				
				<td colspan="2">
				
				<fieldset style="border-width:5px">
			  	 			<legend> <STRONG><FONT color="#C11B17" size="2"><bean:message bundle="dp" key="label.Inventory.Change.Activation" /> </FONT></STRONG></legend>				  	 	
								<table>							
									<tr>
									<TD>Non Serialized</TD>
									<td align="left" width="252">
									<input type="radio" name="statusInv" id="allowedInvNser" value="0" >											
											<bean:message bundle="dp" key="label.Activation.Status.Allowed"/>
										
										</td>
										<td align="left" width="287">
										<input type="radio" name="statusInv" id="notAllowedInvNser" value="1" >		
											<bean:message bundle="dp" key="label.Activation.Status.NotAllowed"/>											
											</td>										
																		
										</tr>	
										<tr><td width="252"></BR></td></tr>
									<tr>
									<TD>Serialized</TD>
									<td align="left" width="252">
									<input type="radio" name="statusInvSer" id="allowedInvSer" value="0">											
											<bean:message bundle="dp" key="label.Activation.Status.Allowed"/>
										
										</td>
										<td align="left" width="287">
										<input type="radio" name="statusInvSer" id="notAllowedInvSer" value="1">		
											<bean:message bundle="dp" key="label.Activation.Status.NotAllowed"/>											
											</td>										
																		
										</tr>										
								</table>
				</fieldset>
				
				</TD>
			</TR>
			
					
</TBODY>
</TABLE>
<br/><br/>
<TABLE width="60%" border="0" cellpadding="1" cellspacing="1"
		align="center" class ="border">
	
	<tr>
	<td width="25%">&nbsp;</td>
				<td align="left" width="55%">
				<input type="button" value="Update Status" onclick="updateStatus();">
				</td>
				
				
	</tr>
</TABLE>


</html:form>

</body>

</html>
<%}catch(Exception e){
e.printStackTrace();
}%>