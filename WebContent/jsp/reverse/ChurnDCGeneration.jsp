<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ page
	import="com.ibm.virtualization.recharge.dto.UserSessionContext;"%>

<LINK rel="stylesheet" href="theme/text.css" type="text/css">
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>

<html>
<head>
<title>Churn DC Generation</title>
<META http-equiv=Content-Type content="text/html; charset=iso-8859-1">
<!--  <link href="${pageContext.request.contextPath}/jsp/hbo/theme/text_new.css" rel="stylesheet" type="text/css"> -->
<link href="<%=request.getContextPath()%>/theme/naaztt.css"
	rel="stylesheet" type="text/css">

<script language="JavaScript"
	src="<%=request.getContextPath()%>/jScripts/AccountMaster.js"></script>
<script type="text/javascript">
function SelectAll() {
	var inputs = document.getElementsByTagName('input');
     var j=0;
    var allSrNo = "";
    var chkAll=document.getElementById("chkAll");
 	if(inputs.length>1)  	{
     if(chkAll.checked ==true)     {
   		 for (var i =1; i < inputs.length; i++)    		 {

          if (inputs[i].type == 'checkbox' && inputs[i].name == 'strCheckedChurn')
          	inputs[i].checked=true;
        	/*var strNumber=document.getElementById("serialCheck"+j).value;
        		
        	    j++;        		      	
        		document.getElementById("isERR").value="";        		
 				isStatusERR(strNumber);
				if(document.getElementById("isERR").value=='Y'){
					chkAll.checked=false;	
					inputs[i].checked=false;
					allSrNo +=strNumber+",";					
					}
        	}*/
        	}
        	/*var allSrNoStr = allSrNo.substring(0,allSrNo.length-1);
        	if(allSrNoStr!="")
				alert("These STB cannot be added in DC : "+allSrNoStr+". Please contact TSM for details.");*/
          }
          else     {
         for (var i = 1; i < inputs.length; i++)          {

          if (inputs[i].type == 'checkbox' && inputs[i].name == 'strCheckedChurn') 
        	  inputs[i].checked=false;
         }
       }
       
   }
}

function SelectAllN() {
	var inputs = document.getElementsByTagName('input');
    var j=0;
    var allSrNo = "";
    var chkAllN=document.getElementById("chkAllN");
 	if(inputs.length>1)  	{
     if(chkAllN.checked ==true)     {
   		 for (var i =1; i < inputs.length; i++)    		 {		  
          if (inputs[i].type == 'checkbox' && inputs[i].name == 'strCheckedChurnN') 
          inputs[i].checked=true;
          /*		var strNumber=document.getElementById("serialCheckN"+j).value;
        		
        	    j++;        		      	
        		document.getElementById("isERR").value="";        		
 				isStatusERR(strNumber);
				if(document.getElementById("isERR").value=='Y'){
					chkAllN.checked=false;	
					inputs[i].checked=false;
					allSrNo +=strNumber+",";					
					}
				}*/
				}
				/*var allSrNoStr = allSrNo.substring(0,allSrNo.length-1);
				if(allSrNoStr!="")
				alert("These STB cannot be added in DC : "+allSrNoStr+". Please contact TSM for details.");*/
				}
          
          else     {
         for (var i = 1; i < inputs.length; i++)          {

          if (inputs[i].type == 'checkbox' && inputs[i].name == 'strCheckedChurnN') 
        	  inputs[i].checked=false;
         }
       }
   
}
}
	function focusTab()
	{
		if(document.forms[0].hidstrDcNo.value !="")
		{
			var strDCNo=document.forms[0].hidstrDcNo.value;
			var url="printDCDetails.do?methodName=printDC&Dc_No="+strDCNo+"&TransType=CHURN";
		    window.open(url,"SerialNo","width=700,height=600,scrollbars=yes,toolbar=no");
				
  		}
  	}
  	
function specialChar(obj)
{
  var fieldName;

  if (obj.name == "courierAgency")
  {
   fieldName = "Courier Agency";
  }
  if (obj.name == "docketNumber")
  {
   fieldName = "Docket Number";
  }

  var iChars = "!@#$%^&*()+=-[]\\\';,./{}|\":<>?~`_";

  for (var i = 0; i < obj.value.length; i++) 
    {
  	if (iChars.indexOf(obj.value.charAt(i)) != -1) 
    {
  	alert ("Special characters are not allowed for "+fieldName);
  	obj.value = obj.value.substring(0,i);
  	return false;
  	}
  }	
}

function maxlength() {
   var size=200;
   var remarks = document.getElementById("remarks").value;
  	if (remarks.length >= size)   	{
   	  remarks = remarks.substring(0, size-1);
   	  document.getElementById("remarks").value = remarks;
      alert ("Remarks Can Contain, 200 Characters Only.");
   	  document.getElementById("remarks").focus();
	  return false;
  	}
  	
  		var iChars = "~`!@#$%^&*()-_=[{]}\|;:,<\/?\\";

	for (var i = 0; i < document.getElementById("remarks").value.length; i++) {
  	if (iChars.indexOf(document.getElementById("remarks").value.charAt(i)) != -1)   	{
  		alert("Special characters (` ~ ' ,) are not allowed.");
  		document.getElementById("remarks").value = remarks.substring(0, document.getElementById("remarks").value.length -1);
  		return false;
  	}}
 } 

	function acceptDC(obj)
	{
		var val=0;
		var isSubmit = false;
		obj.disabled = true;
		
	 	var selected = document.getElementsByTagName('input');
	 	
	   	for (var i =1; i < selected.length; i++) 
	   	{
	    	if (selected[i].type == 'checkbox' && selected[i].checked==true && (selected[i].name == 'strCheckedChurn'))
	    	{ 
	    		val=val+1;
	    	}
		}
		
		if (val < 71)
		{    
			if (val>0)
			{
				if(document.getElementById("remarks").value ==null
					|| trim(document.getElementById("remarks").value) == "") 
				{
					alert('Please enter remarks ');
					document.getElementById("remarks").value = trim(document.getElementById("remarks").value);
					document.getElementById("remarks").focus();	
				} 
				else 
				{
					var agencyName = document.getElementById('courierAgency').value;
					var docketNum = document.getElementById('docketNumber').value;
					var remarks = document.getElementById('remarks').value;
					var isSubmit = true;
					if(agencyName == null || trim(agencyName) =="" || docketNum == null || trim(docketNum)=="")
					{
						alert("Please enter courier agency/docket number");
						isSubmit =false;
					}
				}
			} 
			else 
			{
				isSubmit = false; 
				alert('Please select Stb Serial No.');
			} 
		} 
		else 
		{
			isSubmit = false;
			alert('Only 70 Stbs are allowed with one Dc No.');
		}
		
		if(isSubmit ==true)
		{
			if(agencyName!=null)
				document.getElementById('courierAgency').value = trim(document.getElementById('courierAgency').value);
			if(docketNum !=null)
				document.getElementById('docketNumber').value = trim(document.getElementById('docketNumber').value);
			
			document.getElementById("remarks").value = trim(document.getElementById("remarks").value);
				
			document.forms[0].methodName.value = "performDcCreation";
			
			obj.disabled = true;
			
			document.forms[0].submit();
		}
	}
	
	function checkCheckBox(e, checkType)
	{
		if(checkType==1)
		{
			if(e.checked==false)
				document.getElementById("chkAll").checked = false;
		}
		else
		{
			if(e.checked==false)
				document.getElementById("chkAllN").checked = false;
		}
	}

</script>
</head>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onload="focusTab();">
<%
	String rowDark = "#FCE8E6";
	String rowLight = "#FFFFFF";
%>

<html:form name="ChurnDCGenerationBean" method="post"
	action="/ChurnDCGeneration.do"
	type="com.ibm.dp.beans.ChurnDCGenerationBean">
	<html:hidden property="methodName" styleId="methodName"
		value="ChurnDCGeneration" />
	<html:hidden property="isERR" name="ChurnDCGenerationBean" value=""/>
	<html:hidden property="hidStrProductId" name="ChurnDCGenerationBean"
		value="" />
	<html:hidden property="hidstrDcNo" name="ChurnDCGenerationBean" />
	<img src="<%=request.getContextPath()%>/images/Churn_DC_Generation.jpg"
		width="544" height="25" alt="">

	<table width="100%" border="0" cellspacing="0" cellpadding="0"
		bordercolor='red'>
		<tr>
			<td colspan="4"><strong><font color="red" class="text"
				size="20px"> <bean:write name="ChurnDCGenerationBean"
				property="message" /> </font> </strong></td>
		</tr>
		<TR>
			<td colspan='6'>&nbsp;</td>
		</TR>
		</table>

		<fieldset style="border-width:5px">
		<div class="scrollacc"
			style="OVERFLOW: auto; WIDTH: 100%; HEIGHT: 180px" align="center">
		<table id="tableChurn" width="100%" border="1" cellpadding="2"
			cellspacing="0" align="center">
			<tr
				style="position: relative; top:expression(this.offsetParent.scrollTop-2);"
				class="text white">

				<td align="center" bgcolor="#CD0504" width="8%"><FONT color="white"><span
					class="white10heading mLeft5 mTop5"><strong>Serial
				Number</strong></span></FONT></td>
				<td align="center" bgcolor="#CD0504" width="8%"><FONT color="white"><span
					class="white10heading mLeft5 mTop5"><strong>Product
				</strong></span></FONT></td>
				<td align="center" bgcolor="#CD0504" width="8%"><FONT color="white"><span
					class="white10heading mLeft5 mTop5"><strong>VC Id</strong></span></FONT></td>
				<td align="center" bgcolor="#CD0504" width="8%"><FONT color="white"><span
					class="white10heading mLeft5 mTop5"><strong>Customer
				Id</strong></span></FONT></td>
				<td align="center" bgcolor="#CD0504" width="8%"><FONT color="white"><span
					class="white10heading mLeft5 mTop5"><strong>SI Id</strong></span></FONT></td>

				<td align="center" bgcolor="#CD0504" width="5%"><FONT color="white"><span
					class="white10heading mLeft5 mTop5"><strong>Collection Date</strong></span></FONT></td>
				<td align="center" bgcolor="#CD0504" width="5%"><input
					id="chkAll" type="checkbox" onclick="SelectAll()"></td>
			</tr>


			<logic:empty name="ChurnDCGenerationBean" property="poList">
				<TR class="lightBg">
					<TD class="text" align="center" colspan="16"></TD>
				</TR>
			</logic:empty>

			<logic:notEmpty name="ChurnDCGenerationBean" property="poList">
				<logic:iterate name="ChurnDCGenerationBean" id="POInfo" indexId="i"
					property="poList">
					<TR BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>'>

						<TD align="center" class="text"><bean:write name="POInfo"
							property="serial_Number" /></TD>

						<TD align="center" class="text"><bean:write name="POInfo"
							property="product_Name" /></TD>

						<TD align="center" class="text"><bean:write name="POInfo"
							property="vc_Id" /></TD>

						<TD align="center" class="text"><bean:write name="POInfo"
							property="customer_Id" /></TD>
						<TD align="center" class="text"><bean:write name="POInfo"
							property="si_Id" /></TD>
						<TD align="center" class="text"><bean:write name="POInfo"
							property="collectionDate" /></TD>


						<TD width="5%" align="center"><input type="checkbox" 
							id="strCheckedChurn" name="strCheckedChurn" onclick="checkCheckBox(this, '1')"   
							value='<bean:write name='POInfo' property='reqId'/>' />
							<input style="display:none" name="serialCheck" id="serialCheck<%=i.intValue()%>" value='<bean:write name='POInfo'
							property='serial_Number' />'>
							</input></TD>
					</TR>

				</logic:iterate>
			</logic:notEmpty>


		</table>
		</div>
		</fieldset>

		<br/>
		<table>
			<tr>
				<td><strong><bean:message bundle="view"
					key="churn.dcgeneration.CourierAgency" /><font
					color="RED">*</font> : </font> </strong> <html:text
					name="ChurnDCGenerationBean" property="courierAgency"
					styleId="courierAgency" maxlength="100" onkeyup="return specialChar(this);" /></td>
				<td>&nbsp;&nbsp;<strong><bean:message bundle="view"
					key="churn.dcgeneration.DocketNumber" /> <font
					color="RED">*</font> : </font></strong> <html:text
					name="ChurnDCGenerationBean" property="docketNumber" 
					styleId="docketNumber"  maxlength="50" onkeyup="return specialChar(this);" /></td>
				<td>&nbsp;&nbsp;<strong><font color="#000000">
				<bean:message bundle="view" key="churn.dcgeneration.Remarks" /><font
					color="RED">*</font> : </font></strong></td>
				<td><html:textarea name="ChurnDCGenerationBean"
					styleId="remarks" property="remarks" rows="4" cols="26"
					onkeyup="return maxlength();" /></td>
			</tr>
			<tr>
				<td></td>
				
				<td align="center"><input type="Button" id="submitAllocation"
					value="GenerateDC" onclick="acceptDC(this);"></td>
				<td></td>
				<td></td>
			</tr>
		</table>



</html:form>
</body>
</html>


