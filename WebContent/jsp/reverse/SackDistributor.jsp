<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.text.SimpleDateFormat,java.util.Date"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<html>
<head>
<title>Sack Distributor</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/theme/Master.css" type="text/css">
<style>
 .odd{background-color: #FCE8E6;}
 .even{background-color: #FFFFFF;}
</style>
<script language="JavaScript">



function validateData() {
		document.forms[0].action ="sackDistributor.do?methodName=viewDistDetails";
		document.forms[0].submit();
		return true;
	}
	
 


	
	
	
	function submitDataTable() {
	try {
			var table = document.getElementById("dataTable1");
			var rowCount = table.rows.length;
			var j=0;
			if(rowCount > 1){
			var i=0;
			var jsAccountId="";
			var stockCount;
			var fseCount;	
			var alertMsg="";
			var accountName;
			var strArraySelected = new Array();
			var remarks = document.getElementById('strRemarks').value;
		
		if(remarks == ""){
			alert("Please Enter Remarks");
		 	return false;
		}else if(remarks.length>200){
			alert("Please Enter Remarks less than 200 characters.");
		 	return false;
		}
	if(document.forms[0].strCheckedSTA.length != undefined)
	{
		for (count = 0; count < document.forms[0].strCheckedSTA.length; count++)
		{
					if(document.forms[0].strCheckedSTA[count].checked==true)
			{
				var selectedRow = count+1;
				strArraySelected[j]= selectedRow;
				j++;
				var rownum = selectedRow+1;
				var x = document.getElementById("dataTable1").rows[rownum-1].cells;
				//for hidden account id 
				if(jsAccountId!=""){
					jsAccountId +=",";
				}
				jsAccountId += x[0].getElementsByTagName("input")[0].value;
				//ends
				//for hidden stock Count
				stockCount = x[1].getElementsByTagName("input")[0].value;
				//ends
				//for hidden FSE Count
				fseCount = x[2].getElementsByTagName("input")[0].value;
				//ends
				if(document.all){
     			accountName= x[0].getElementsByTagName("div")[0].value;
				} else {
    				accountName=  x[0].getElementsByTagName("div")[0].value.textContent;
				}
				if(stockCount>0){
				alertMsg += accountName+ "has some Stock\n" ;
				}
				if(fseCount>0){
				alertMsg += accountName+ "has some FSEs\n" ;
				}
			}
		}
	
	}else 	if(document.forms[0].strCheckedSTA.checked==true)
			{
			var count=0;
				var selectedRow = count+1;
				strArraySelected[j]= selectedRow;
				j++;
		
			var rownum = selectedRow+1;
			var x = document.getElementById("dataTable1").rows[rownum-1].cells;
				//for hidden account id 
				if(jsAccountId!=""){
					jsAccountId +=",";
				}
				jsAccountId += x[0].getElementsByTagName("input")[0].value;
				//ends
				//for hidden stock Count
				stockCount = x[1].getElementsByTagName("input")[0].value;
				//ends
				//for hidden FSE Count
				fseCount = x[2].getElementsByTagName("input")[0].value;
				//ends
				alert("stock count== "+stockCount +" FE Count="+fseCount);
				if(document.all){
     			accountName= x[0].getElementsByTagName("div")[0].value;
				} else {
    				accountName=  x[0].getElementsByTagName("div")[0].value.textContent;
				}
				
				if(stockCount>0){
				alertMsg += accountName+ " has some Stock.\n" ;
				}
				if(fseCount>0){
				alertMsg += accountName+ " has some FSEs.\n" ;
				}
				
			}
	document.forms[0].hidSeletedDistIds.value=jsAccountId;
	}
			
	if((document.forms[0].hidSeletedDistIds.value).length >0)
  	    {
  	 
  	    if(alertMsg!=""){
  	       alert(alertMsg);
  	       return false;
  	    }
  	  	document.forms[0].action ="sackDistributor.do?methodName=performAction";
  	  	var r=confirm("Do you want to perform this action? ");
		if (r==true)
  		{
		document.forms[0].submit();	
		}
		
		
  		}
  		else
  		{
  		   alert("Please Select atleast One Record!!")
  		   return false;
  		}
 
			
} 
catch(e) {
				alert(e);
		 }
}


function submitDataTableWidFse() {
	try {
			var table = document.getElementById("dataTable1");
			var rowCount = table.rows.length;
			var j=0;
			if(rowCount > 1){
			var i=0;
			var jsAccountId ="";
			var stockCount;
			var alertMsg="";
			var accountName;
			var strArraySelected = new Array();
			var remarks = document.getElementById('strRemarks').value;
		
		if(remarks == ""){
			alert("Please Enter Remarks");
		 	return false;
		}else if(remarks.length>200){
			alert("Please Enter Remarks less than 200 characters.");
		 	return false;
		}
	if(document.forms[0].strCheckedSTA.length != undefined)
	{
			
		for (count = 0; count < document.forms[0].strCheckedSTA.length; count++)
		{
				if(document.forms[0].strCheckedSTA[count].checked==true)
			{
				var selectedRow = count+1;
				strArraySelected[j]= selectedRow;
				j++;
		
				var rownum = selectedRow+1;
				var x = document.getElementById("dataTable1").rows[rownum-1].cells;
				//for hidden account id 
				if(jsAccountId!=""){
					jsAccountId +=",";
				}
				jsAccountId += x[0].getElementsByTagName("input")[0].value;
				//ends
				//for hidden stock Count
				stockCount = x[1].getElementsByTagName("input")[0].value;
				//ends
				if(document.all){
     			accountName= x[0].getElementsByTagName("div")[0].value;
				} else {
    				accountName=  x[0].getElementsByTagName("div")[0].value.textContent;
				}
				if(stockCount>0){
				alertMsg += accountName+ " has some Stock.\n" ;
				}
				
			}
		}
	}else 	if(document.forms[0].strCheckedSTA.checked==true)
			{
			var count=0;
				var selectedRow = count+1;
				strArraySelected[j]= selectedRow;
				j++;
		
			var rownum = selectedRow+1;
			var x = document.getElementById("dataTable1").rows[rownum-1].cells;
				//for hidden account id 
				if(jsAccountId!=""){
					jsAccountId +=",";
				}
				jsAccountId += x[0].getElementsByTagName("input")[0].value;
				//ends
				//for hidden stock Count
				stockCount = x[1].getElementsByTagName("input")[0].value;
				//ends
				if(document.all){
     			accountName= x[0].getElementsByTagName("div")[0].value;
				} else {
    				accountName=  x[0].getElementsByTagName("div")[0].value.textContent;
				}
				
				if(stockCount>0){
				alertMsg += accountName+ "has some Stock.\n" ;
				}
				
			}
		
	document.forms[0].hidSeletedDistIds.value=jsAccountId;
	}
			
	if((document.forms[0].hidSeletedDistIds.value).length >0)
  	    {
  	    if(alertMsg!=""){
  	       alert(alertMsg);
  	       return false;
  	    }
  	  	document.forms[0].action ="sackDistributor.do?methodName=performAction";
  	  		var r=confirm("Do you want to perform this action? ");
		if (r==true)
  		{
		document.forms[0].submit();	
		}

		
  		}
  		else
  		{
  		   alert("Please Select atleast One Record!!")
  		   return false;
  		}
 
			
} 
catch(e) {
				alert(e);
		 }
}

function maxlength() 
{
   var size=200;
   var remarks = document.getElementById("strRemarks").value;
  	if (remarks.length >= size) 
  	{
   	  remarks = remarks.substring(0, size-1);
   	  document.getElementById("strRemarks").value = remarks;
      alert ("Remarks Can Contain, 200 Characters Only.");
   	  document.getElementById("strRemarks").focus();
	  return false;
  	}
 } 
	

</script>
</head>
<body>
<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>
<html:form action="/sackDistributor" type="com.ibm.dp.beans.SackDistributorBean">
<html:hidden property="hidSeletedDistIds" name="SackDistributorBean" value=""/>


<IMG src="<%=request.getContextPath()%>/images/Deact_Dist.jpg" 
		width="544" height="25" alt=""/> 
		
	<table>
		<tbody>
			<tr>
				<td><logic:notEmpty name="SackDistributorBean" property="message">
			<font color="red"><B><bean:write name="SackDistributorBean" property="message"/></B></font>
			</logic:notEmpty>
			</td>
			</tr>
		</tbody>
	</table>
<table id="inputTable" width="650px" border="0" align="center">
<tr>
			<td class="txt" width="150px"><strong><bean:message bundle="view" key="Sack.Dist.SelectTsm" /></strong><font color="red">*</font></td>
			
			
					
					
			<td class="txt">
			<html:select property="tsmSelectedId" styleId="tsmSelectedId" style="width:150px" >
				<html:option value="0">--All--</html:option>
				<logic:notEmpty property="tsmList" name="SackDistributorBean"><html:options labelProperty="accountName" property="accountId"
						collection="tsmList" />
				</logic:notEmpty>
			</html:select>
			</td>
			<td class="txt" align="right" colspan="4">
			<input type="button" value="GO" onclick="validateData()" /></td>
</tr>
</table>



<DIV style="height: 240px; overflow: auto;" width="80%" height="70%">
		<table id="dataTable1"  width="100%" align="left" border="1" cellpadding="0" cellspacing="0" 
		style="border-collapse: collapse;">
			<thead>
				<tr style="position: relative; top:expression(this.offsetParent.scrollTop-2);" class="text white">
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="Sack.Dist.DistName" /></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="Sack.Dist.ContactName" /></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="Sack.Dist.MobileNum" /></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="Sack.Dist.City" /></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message bundle="view" key="Sack.Dist.Zone" /></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center"></td>
				</tr>
			</thead>
			<tbody>
					<logic:notEmpty name="SackDistributorBean" property="distributorList">
					<logic:iterate name="SackDistributorBean" id="distList" indexId="i" property="distributorList">
						
						<TR BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>' >
							<TD align="center" class="text">
							<input type="hidden" value='<bean:write name="distList" property="accountId"/>'/>
							<div value='<bean:write name="distList" property="accountName"/>'>	<bean:write name="distList" property="accountName"/>	</div>				
							</TD>
							<TD align="center" class="text">
								<input type="hidden" value='<bean:write name="distList" property="stockCount"/>'/>
								<div value='<bean:write name="distList" property="contactName"/>'><bean:write name="distList" property="contactName"/>					</div>
							</TD>
							<TD align="center" class="text">
							<input type="hidden" value='<bean:write name="distList" property="fseCount"/>'/>
							<div  value='<bean:write name="distList" property="mobileNumber"/>'>	<bean:write name="distList" property="mobileNumber"/>					</div>
							</TD>
							<TD align="center" class="text">
							<div value='<bean:write name="distList" property="cityName"/>'>	<bean:write name="distList" property="cityName"/>					</div>
							</TD>
							<TD align="center" class="text">
							<div value='<bean:write name="distList" property="zone"/>'>	<bean:write name="distList" property="zone"/>					</div>
							</TD>
							<TD align="center" class="text">
							
								<input type="checkbox" id="strCheckedSTA" name="strCheckedSTA" value='<bean:write name='distList' property='accountId'/>' />
							</TD>
						</TR>
					</logic:iterate>
				</logic:notEmpty>
			
			</tbody>
		</table>
		
		</DIV>
	

		<br>
		
		<table width="100%" border="0" cellPadding="0" cellSpacing="0" align="left">
		<tr><td class="txt" width="110px"><strong>Remarks</strong><font color="red">*</font></td>
			<td><html:textarea name="SackDistributorBean"
				property="strRemarks" styleId="strRemarks"  rows="2" cols="39" onkeypress="return maxlength();" ></html:textarea>
			</td></tr>
			<tr><td COLSPAN='2'>&nbsp;</td></tr>

			<tr>
				<td  class="txt" align="center" colspan="2">
				<input type="button" id="submitWoutFse" value="Sack Distributor Without FSE" onclick="submitDataTable()" />
				
				</td>
				<td  class="txt" align="center" colspan="2">
				<input type="button" id="submitWithFse" value="Sack Distributor With FSE" onclick="submitDataTableWidFse()" />
				
				</td>
			</tr>
		</table>
		
		
	
</html:form>
</body>
</html>