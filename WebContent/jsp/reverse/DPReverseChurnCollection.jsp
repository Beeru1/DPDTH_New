<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ page import="com.ibm.virtualization.recharge.dto.UserSessionContext;" %>


<LINK rel="stylesheet" href="theme/text.css" type="text/css">
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>

<html>
<head>    
<title>Reverse Collection Screen</title>
<META http-equiv=Content-Type content="text/html; charset=iso-8859-1">
<link href="${pageContext.request.contextPath}/jsp/hbo/theme/text_new.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/theme/naaztt.css" rel="stylesheet" type="text/css">
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/jScripts/progressBar.js" type="text/javascript">
</SCRIPT>
<script language="JavaScript" src="<%=request.getContextPath()%>/jScripts/AccountMaster.js"></script> 
<script type="text/javascript">

	function trimAll(sString) {
		while (sString.substring(0,1) == ' '){
		sString = sString.substring(1, sString.length);
		}		
		while (sString.substring(sString.length-1, sString.length) == ' '){
		sString = sString.substring(0,sString.length-1);	
		}
		return sString;
	} 
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
	         for (var i = 1; i < inputs.length; i++) 
	         {
	          if (inputs[i].type == 'checkbox') 
	        	  inputs[i].checked=false;
	         }
	       }
	   }
	}
  
	function checkCheckBox(e)
	{
		if(e.checked==false)
			document.getElementById("chkAll").checked = false;
	}

	function validateForm()
	{
		if(document.getElementById("uploadedFile").value == "")
		{
			alert('Please Select File to Upload');
			return false;
		}
		
	 	document.forms[0].action="ReverseChurnCollection.do?methodName=uploadExcel";	
			return true;
	}

	function uploadExcel(obj)
	{
	
		var distType = document.getElementById("typedist").value;
		
		if(distType==0)
		{
		alert("You are not of CPE Type. Please get your ID created by ID Helpdesk");
		return false;
		}
		else
		{
		if(document.getElementById("uploadedFile").value == "") 
		{		
			alert("Please Select csv File");		
		 	return false;	
	    } 
	    else 
	    {
	    	if(confirm('Are you sure these STBs are physicaly CPE recovered & lying with you, By clicking on \"Upload File\" all validated STB\'s will be part of your swap stock till dispatched to WH.'))
	    	{
		 		document.forms[0].action="ReverseChurnCollection.do?methodName=uploadExcel";
		 		obj.disabled = true;
		 		loadSubmit();
				document.forms[0].submit();
			}
			else
				return false;	
		}
		}
	}

	function acceptChurn(obj)
	{
		var stbNos="";
		var val=0;
		var countSrNo=0;

 		var selected = document.getElementsByTagName('input');
   		for (var i =1; i < selected.length; i++) 
   		{
          	if(selected[i].type == 'checkbox' && selected[i].checked==true && selected[i].name == 'strCheckedChurn')
          	{
          		stbNos = stbNos + selected[i].value +",";
          		countSrNo = countSrNo +1;
          		val = val+1;   
          	}
		}
		if(countSrNo == 0)
 		{
		 	alert('Please select atleast one serial number');	 
		 	return false;
		}
 		if(document.getElementById("remarks").value == "") 
 		{
	 		alert('Please enter remarks ');	 
		 	document.getElementById("remarks").focus();	
		 	return false;
		} 
 		
  		if(document.getElementById("statusId").value == "0" )
 		{
 			alert('Please select Churn Status');	 
 			return false;
 		}
 
 		if(document.getElementById("statusId").value == "1" )
 		{
			if(stbNos != "null" && countSrNo > 0)
			{
			 	stbNos = stbNos.substring(0,stbNos.length-1);
			}	

		 	var req;
		 	
		 	if(window.XmlHttpRequest) 
				req = new XmlHttpRequest();
			else if(window.ActiveXObject) 
				req=new ActiveXObject("Microsoft.XMLHTTP");
			
			if(req == null)
			{
				alert ("Your Browser Does Not Support AJAX!");
				return;
			} 
			
			var url = "ReverseChurnCollection.do?methodName=checkCPEInventory&stbNos="+stbNos;
			
			
			req.onreadystatechange = function()
			{
				if(req.readyState == 4 || req.readyState == "complete")
				{
					if(req.status != 200)
					{
						alert("Some Internal Error occured. Please try again.");
						return false;
					}
					else
					{
						var text = req.responseText;
						
						if(text == "true")
						{ 
						 	document.forms[0].methodName.value = "acceptChurnAddToStock";			
         					document.forms[0].submit();
						}
						else if(trimAll(text) != "" )
						{
							alert(text);
							return false;
						}
						else
						{
							alert("Not able to connect to CPE. Please try again");
							return false;	
						}
					}
				}
				//setAjaxTextValues( req);
			}
			req.open("POST",url,false);
			req.send();
 		}
 		else
 		{
        	document.forms[0].methodName.value = "acceptChurnGenerateDC";			
          	document.forms[0].submit();
 		}
	}
	
	function showError()
	{
		document.forms[0].methodName.value = "showErrorFile";	
		document.forms[0].submit();
		return true;
	}

</script>
</head>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  background="<%=request.getContextPath()%>/images/bg_main.gif" bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" >
	<%     String rowDark ="#FCE8E6";    String rowLight = "#FFFFFF";	%>
	
<html:form name="DPReverseChurnCollectionBean" method="post" action="/ReverseChurnCollection.do" type="com.ibm.dp.beans.DPReverseChurnCollectionBean" enctype="multipart/form-data">
	<html:hidden property="methodName" styleId="methodName" />
	<html:hidden property="typedist" 
		name="DPReverseChurnCollectionBean"/>
	<TABLE width='100%'>
		<TR>
			<TD width ='100%'>
				<img src="<%=request.getContextPath()%>/images/Reverse_Churn_Collection.jpg" width="544" height="25" alt="">
			</TD>
		</TR>
		<TR>
			<TD width ='100%'>
				<table align="center" width="100%" border="0" cellspacing="0" cellpadding="0" bordercolor='red'>
					<tr>	
						<td colspan="6">
							<strong><font color="red" class="text" size="15px">	<bean:write name="DPReverseChurnCollectionBean" property="message"/></font></strong>
						</td>		
					</tr>
					<tr> 
						<td align="center">
							<STRONG><FONT color="#000000"><bean:message bundle="view" key="lebel.reco.file" /> :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT></STRONG>
						  	<FONT color="#003399"> <html:file  property="uploadedFile" name="DPReverseChurnCollectionBean" size="40"></html:file></FONT>&nbsp;&nbsp;&nbsp;
	         				<input type="button"  value="Upload File" name="uploadbtn" id="uploadbtn" onclick="uploadExcel(this);">
	         			</TD>
	    
	    		<logic:equal name="DPReverseChurnCollectionBean"	property="error_flag" value="true">
					<tr>
						<td  align="center" colspan="5">
							<strong><font color="red">
							  The transaction could not be processed due to some errors. Click on <a href="ReverseChurnCollection.do?methodName=showErrorFile">View Details </a> 
					  for details.</font><strong>
						</td>	
					</tr>
				</logic:equal>
				
					</tr>
	
					<tr>	
						<td colspan="6" align="center" width='100%'>&nbsp;</td>		
					</tr>
					<tr> 
						<td colspan="6"> 
							<div class="scrollacc" style="OVERFLOW: auto; WIDTH: 100%; HEIGHT: 180px" align="center">
								<table width="100%">
									<tr> 
										<td width="100%">
											<table id="tableChurn" width="100%" border="1" cellpadding="2"	cellspacing="0" align="center">
												<tr style="position: relative; top:expression(this.offsetParent.scrollTop-2);" class="text white"> 		
													<td align="center" bgcolor="#CD0504" width="8%"><FONT color="white"><span
														class="white10heading mLeft5 mTop5"><strong>Serial Number</strong></span></FONT></td>
													<td align="center" bgcolor="#CD0504" width="8%"><FONT color="white"><span
														class="white10heading mLeft5 mTop5"><strong>Product</strong></span></FONT></td>
													<td align="center" bgcolor="#CD0504" width="8%"><FONT color="white"><span
														class="white10heading mLeft5 mTop5"><strong>VC Id</strong></span></FONT></td>
													<td align="center" bgcolor="#CD0504" width="8%"><FONT color="white"><span
														class="white10heading mLeft5 mTop5"><strong>Customer Id</strong></span></FONT></td>
													<td align="center" bgcolor="#CD0504" width="8%"><FONT color="white"><span
														class="white10heading mLeft5 mTop5"><strong>SI Id</strong></span></FONT></td>
							
													<td align="center" bgcolor="#CD0504" width="5%"><FONT color="white"><span
														class="white10heading mLeft5 mTop5"><strong>Collection date</strong></span></FONT></td>
													<td align="center" bgcolor="#CD0504" width="5%"><input id="chkAll" type="checkbox" onclick="SelectAll()" >	</td>	
												</tr>
											<logic:empty name="DPReverseChurnCollectionBean" property="poList">
												<TR class="lightBg">		
													<TD class="text" align="center" colspan="16"></TD>			
												</TR>
											</logic:empty>
					
											<logic:notEmpty name="DPReverseChurnCollectionBean" property="poList">
												<logic:iterate name="DPReverseChurnCollectionBean" id="POInfo" indexId="i" property="poList">
												<TR BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>' >
													<TD align="center" class="text"><bean:write name="POInfo" property="serial_Number" /></TD>
													<TD align="center" class="text"><bean:write name="POInfo" property="product_Name" /></TD>
													<TD align="center" class="text"><bean:write name="POInfo" property="vc_Id" /></TD>	
													<TD align="center" class="text"><bean:write name="POInfo" property="customer_Id" /> </TD>
													<TD align="center" class="text"><bean:write name="POInfo" property="si_Id" /> </TD>	
								                    <TD align="center" class="text"><bean:write name="POInfo" property="collectionDate" />
													<TD width="5%" align="center"><input type="checkbox" id="strCheckedChurn" onclick="checkCheckBox(this)" name="strCheckedChurn" value='<bean:write name='POInfo' property='intReqID'/>' /> </TD>		
												</TR>
												</logic:iterate>
											</logic:notEmpty>
										</table>					
									</td>	
								</tr>
							</table>		
						</div>		
					</td>		
				</tr>
			</TABLE>
		</TD>
	</TR>	
	<TR>
		<td>
			<table width='100%' >
				<tr>
					<td>
						<strong> <font color="#000000">	<bean:message bundle="view" key="churn.dcgeneration.Remarks" />
							<font color="RED">*</font> : </font></strong>
					</td>
					<td>
						<html:textarea name="DPReverseChurnCollectionBean" styleId="remarks" property="remarks" rows="4" cols="28" onkeyup="return maxlength();" />
					</td>
					<td align="left">
						<strong>
							<font color="#000000"><bean:message bundle="view" key="reverse.collection.Status" /> : </font>
						</strong>
					</td>
					<td>
						<html:select property="statusId" style="width: 150px" styleId="statusId">
							<html:option value="0">--select--</html:option>		
							<html:option value="1">Add To Stock</html:option>
							<html:option value="2">Generate DC</html:option>		
						</html:select>
					</td>
					<td>
						<input type="Button" tabindex="4"  name="Submit" value="Submit" onclick="acceptChurn(this);"> 
					</td>
				</tr>
			</table>
		</td>
	</TR>	
	<!-- Added by Neetika for BFR 16 on 8 aug 2013 Release 3 -->
				<TR>
					<TD  align="center" colspan=5>
					<p style="visibility:hidden;" id="progress">
					<img id="progress_image" style="visibility:hidden;padding-left:55px;padding-top:500px;width:30;height:34" src="${pageContext.request.contextPath}/images/ajax-loader.gif" /> 
					</p>
					</TD>
				</TR>
</table>
</html:form> 
</BODY>
</html>
