
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

function cancelStockApproval()
{
		document.forms[0].action="missingStockApproval.do?methodName=init";
		document.forms[0].method="post";
		document.forms[0].submit();
}

function saveMissingStock()


{

	var alertMsg = validateSave();
	
	if(alertMsg=="")
	{
	
	var inputs = document.getElementsByTagName('input');
    
    if(inputs.length>1)
  	{
     	for (var i =1; i < inputs.length; i++) 
   		 {

          if (inputs[i].type == 'checkbox' && !inputs[i].disabled)
        	inputs[i].checked=true;
          }
     }
		document.forms[0].methodName.value = "saveMissingStock";
		document.forms[0].submit();
		
		
		/*window.opener.location.href = "missingStockApproval.do?methodName=init";
		//document.getElementById("strSuccessMsg").value=document.getElementById("strSuccessMsg").value;
		window.close();
		if (window.opener.progressWindow)
		{
    		window.opener.progressWindow.close();
  		}*/
 		
		
	}
	else
	{
		alert(alertMsg);
	}
}


function validateSave()
{
	var alertMsg = "";
	var checkedRowLen = document.forms[0].strCheckedMSA.length;
	var noOfCheckedRows = 0;
	var tableRows = document.getElementById("tableMSA").rows;
	var newVal="";
	//Added by shilpa on 08-09-2012
	var jsArrChecked = new Array();
	if(checkedRowLen==null)
	{
		
			if(trimAll(tableRows[1].cells[8].innerText)=="NO")
			{if(document.getElementById("strActionText0").value=="Lost in Transit")
				{
					newVal="LIT";
				}
				else if(document.getElementById("strActionText0").value=="Available in Warehouse")
				{
					newVal="EIB";
				}
				else if(document.getElementById("strActionText0").value=="Cancel")
				{
					newVal="CAN";
				}
				else if(document.getElementById("strActionText0").value=="Wrong Shipped")
				{
					newVal="WS";
				}
				else 
				{
					newVal=document.getElementById("strActionText0").value;
				}
			
				if(document.getElementById("strAction0").value==0)
				{
					alertMsg +="Select an Action for Serial No. "+tableRows[1].cells[1].innerText+"\n";
				}
				else
				{
					document.forms[0].strCheckedMSA.value = 
						document.forms[0].strCheckedMSA.value+"#"+newVal;
						jsArrChecked = document.forms[0].strCheckedMSA.value ;
				}
			}
			else
			{
				document.forms[0].strCheckedMSA.value = 
					document.forms[0].strCheckedMSA.value+"#SWP";
					jsArrChecked = document.forms[0].strCheckedMSA.value ; 
			}
		
	}
	else
	{
		for(rowCount=1; rowCount<tableRows.length; rowCount++)
		{ var count=rowCount-1;
			
			if(document.getElementById("strActionText"+count).value=="Lost in Transit")
				{
					newVal="LIT";
				}
				else if(document.getElementById("strActionText"+count).value=="Available in Warehouse")
				{
					newVal="EIB";
				}
				else if(document.getElementById("strActionText"+count).value=="Cancel")
				{
					newVal="CAN";
				}
				else if(document.getElementById("strActionText"+count).value=="Wrong Shipped")
				{
					newVal="WS";
				}
				else 
				{
					newVal=document.getElementById("strActionText"+count).value;
				}
				noOfCheckedRows = 1;
				if(trimAll(tableRows[rowCount].cells[8].innerText)=="NO")
				{
					if(document.getElementById("strActionText"+(rowCount-1)).value==0 || document.getElementById("strActionText"+(rowCount-1)).value=="")
					{
						alertMsg +="Select an Action for Serial No. "+tableRows[rowCount].cells[1].innerText+"\n";
					}
					else
					{
						document.forms[0].strCheckedMSA[rowCount-1].value = 
							document.forms[0].strCheckedMSA[rowCount-1].value+"#"+newVal;
							jsArrChecked[rowCount-1] = document.forms[0].strCheckedMSA[rowCount-1].value  ;
					}
				}
				else
				{
					document.forms[0].strCheckedMSA[rowCount-1].value = 
					document.forms[0].strCheckedMSA[rowCount-1].value+"#SWP";
					jsArrChecked[rowCount-1] = document.forms[0].strCheckedMSA[rowCount-1].value  ; 
				}
			
		}
		
		
	}
	
	document.forms[0].hidCheckedMSA.value =jsArrChecked;
	
	return alertMsg;
}

function validateData(){
	var dcNo  = document.getElementById("dcNo").value;
	if(dcNo=="0"){
		alert("Please Select DC No.");
		return false;
	}
		document.forms[0].methodName.value = "getGridData";
		document.forms[0].submit();
	
	return true;

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

function assignStock()
{
	var alertMsg = "";
	var checkedRowLen = document.forms[0].strCheckedMSA.length;
	var tableRows = document.getElementById("tableMSA").rows;
	var count=0;
	var assgnVal="";
	var wrongShippedAlert=0;
	var shortStatusAlert=0;
	for(rowCount=1; rowCount<tableRows.length; rowCount++)
		{
	count=	rowCount-1;
	if(typeof(document.forms[0].strCheckedMSA)!="undefined" && typeof(document.forms[0].strCheckedMSA[count])!="undefined" )
	{
			if(document.forms[0].strCheckedMSA[count].checked==true  && !document.forms[0].strCheckedMSA[count].disabled)
			{
			
				if(document.getElementById("assignValues").value=="LIT")
				{
				assgnVal="Lost in Transit";
				}
				else if(document.getElementById("assignValues").value=="EIB")
				{
				assgnVal="Available in Warehouse";
				}
				else if(document.getElementById("assignValues").value=="WS")
				{
				assgnVal="Wrong Shipped";
				
				}
				else if(document.getElementById("assignValues").value=="CAN")
				{
				assgnVal="Cancel";
				}
				if(trimAll(tableRows[rowCount].cells[8].innerText)=="NO")
				{
					
					
						if(document.getElementById("status"+count).innerText=="WRONG ") 
						{
							if((document.getElementById("assignValues").value!="WS") && (document.getElementById("assignValues").value!="CAN") && (document.getElementById("assignValues").value!=""))
				
								{
									if(wrongShippedAlert==0)	
									alert("Please select Wrong Shipped or Cancel for Wrong Status STB");
									wrongShippedAlert++;
									continue;
								}
							
							if(document.getElementById("assignValues").value =="WS" && document.getElementById("assignValues").value!="" )
							{
								
								var req;
 	
							 	if(window.XmlHttpRequest) {
									req = new XmlHttpRequest();
								}else if(window.ActiveXObject) {
									req=new ActiveXObject("Microsoft.XMLHTTP");
								}
								
								if(req == null)
								{
									alert ("Your Browser Does Not Support AJAX!");
									return;
								}  
								var dc_no = document.getElementById("dc_no"+count).innerText ;
								var url = "missingStockApproval.do";
								url = url + "?methodName=checkWrongInventory&extraSerialNo="+document.forms[0].strCheckedMSA[count].value+"&dc_no="+dc_no;
								req.onreadystatechange = function()
								{
									if(req.readyState == 4 || req.readyState == "complete")
									{
										if(req.status != 200)
										{
											alert("Error during Transaction");
											return false;
										}
										else
										{
											var text = req.responseText;
											if(text == "true")
											{ 
												//alert("Serial Number already exist in Inventory");
												//document.getElementById("extraSerialNo").value="";
												//return false;
											}
											else
											{
												alert(text);
												wrongShippedAlert++;
												assgnVal=""; 
												document.getElementById("assignValues").value="";
												return false;
											}
										}
									}
									//setAjaxTextValues( req);
								}
								req.open("POST",url,false);
								req.send();
							}
							
						}
						if(document.getElementById("status"+count).innerText=="SHORT ") 
						{
							if((document.getElementById("assignValues").value=="WS") )
				
								{	
									if(shortStatusAlert==0)
										alert("Wrong Shipped cannot be assigned to STB with SHORT as status");
									shortStatusAlert++;
									continue;
								}
				
						}
						if(assgnVal!="")
						{
							document.getElementById("strActionText"+count).value=assgnVal;
						}
						else
						if(document.getElementById("assignValues").value!=""){
							document.getElementById("strActionText"+count).value=document.getElementById("assignValues").value;
						}
						
						document.forms[0].strCheckedMSA[count].checked=false;
						document.getElementById("chkAll").checked=false;
						//SelectAll();
					
				}
				else
				{
					if(document.getElementById("assignValues").value=="CAN")
					{
						document.getElementById("strActionText"+count).value="Cancel";
						document.forms[0].strCheckedMSA[count].checked=false;
						document.getElementById("chkAll").checked=false;
						//SelectAll();
					}
				}
			}
			}
			else{
			
			if(document.forms[0].strCheckedMSA.checked==true && !document.forms[0].strCheckedMSA.disabled)
			{
			
				if(document.getElementById("assignValues").value=="LIT")
				{
				assgnVal="Lost in Transit";
				}
				else if(document.getElementById("assignValues").value=="EIB")
				{
				assgnVal="Available in Warehouse";
				}
				else if(document.getElementById("assignValues").value=="WS")
				{
				assgnVal="Wrong Shipped";
				
				}
				else if(document.getElementById("assignValues").value=="CAN")
				{
				assgnVal="Cancel";
				}
				if(trimAll(tableRows[rowCount].cells[8].innerText)=="NO")
				{
					
					
						if(document.getElementById("status"+count).innerText=="WRONG ") 
						{
							if((document.getElementById("assignValues").value!="WS") && (document.getElementById("assignValues").value!="CAN") && (document.getElementById("assignValues").value!=""))
				
								{
									if(wrongShippedAlert==0)	
										alert("Please select Wrong Shipped or Cancel for Wrong Status STB");
									wrongShippedAlert++;	
									continue;
								}
								
								
								
								
							if(document.getElementById("assignValues").value =="WS" && document.getElementById("assignValues").value!="" )
							{
								var req;
 	
							 	if(window.XmlHttpRequest) {
									req = new XmlHttpRequest();
								}else if(window.ActiveXObject) {
									req=new ActiveXObject("Microsoft.XMLHTTP");
								}
								
								if(req == null)
								{
									alert ("Your Browser Does Not Support AJAX!");
									return;
								}  
								var dc_no = document.getElementById("dc_no"+0).innerText ;
								var url = "missingStockApproval.do";
								url = url + "?methodName=checkWrongInventory&extraSerialNo="+document.forms[0].strCheckedMSA.value+"&dc_no="+dc_no;
								req.onreadystatechange = function()
								{
									if(req.readyState == 4 || req.readyState == "complete")
									{
										if(req.status != 200)
										{
											alert("Error during Transaction");
											return false;
										}
										else
										{
											var text = req.responseText;
											if(text == "true")
											{ 
												//alert("Serial Number already exist in Inventory");
												//document.getElementById("extraSerialNo").value="";
												//return false;
											}
											else
											{
												alert(text);
												wrongShippedAlert++;
												assgnVal=""; 
												document.getElementById("assignValues").value="";
												return false;
											}
										}
									}
									//setAjaxTextValues( req);
								}
								req.open("POST",url,false);
								req.send();
							}
							
				
						}
						if(document.getElementById("status"+count).innerText=="SHORT ") 
						{
							if((document.getElementById("assignValues").value=="WS") )
				
								{	if(shortStatusAlert==0)
										alert("Wrong Shipped cannot be assigned to STB with SHORT as status");
									shortStatusAlert++;
									continue;
								}
				
						}
						if(assgnVal!="")
						document.getElementById("strActionText"+count).value=assgnVal;
						else
						if(document.getElementById("assignValues").value!=""){
						document.getElementById("strActionText"+count).value=document.getElementById("assignValues").value;
						}			
					document.forms[0].strCheckedMSA.checked=false;
					//document.forms[0].strCheckedMSA[count].checked=false;
					document.getElementById("chkAll").checked=false;
					//SelectAll();
					
				}
				else
				{
					if(document.getElementById("assignValues").value=="CAN")
					{
						document.getElementById("strActionText"+count).value="Cancel";
						document.forms[0].strCheckedMSA.checked=false;
						document.getElementById("chkAll").checked=false;
					//	SelectAll();
					}
				}
			}
			}
		}
		

}

function checkCheckBox(e){
if(e.checked==false)
{
document.getElementById("chkAll").checked=false;
}
}

</SCRIPT>
</HEAD>

<BODY BACKGROUND="images/bg_main.gif">
<html:form  action="/missingStockApproval"> 

	
<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>
<br>
<%String dcNo="dcNo"; 
String poNo="poNo"; 
%>
<html:hidden property="dcNo" styleId="dcNo"  value="<%=request.getParameter(dcNo)%>"> </html:hidden>
<html:hidden property="poNo" styleId="poNo" name="dpMissingStockApprovalBean"/>
<html:hidden property="hidCheckedMSA" styleId="hidCheckedMSA" name="dpMissingStockApprovalBean"/>
	<IMG src="<%=request.getContextPath()%>/images/shipmenterrorcorrection.JPG"
		width="525" height="26" alt=""/>
		<logic:notEmpty property="strSuccessMsg" name="dpMissingStockApprovalBean">
			<BR>
				<strong><font color="red"><bean:write property="strSuccessMsg"  name="dpMissingStockApprovalBean"/></font></strong>
			<BR>
		</logic:notEmpty>
		<table>
		<tr>
		</tr>
		
		</table>
	<DIV style="height: 290px; width:100%; overflow-x:scroll; overflow-y:scroll;visibility: visible;z-index:1; left: 133px; top: 250px;">
	
		<table width="100%" align="left" border="1" cellpadding="0" cellspacing="0" id="tableMSA"
		style="border-collapse: collapse;">
			<thead>
				<tr class="noScroll">
					<td width="5%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<input id="chkAll" type="checkbox" onclick="SelectAll()" >	
							</FONT></td>
					<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="dp" key="label.MSA.SRNO"/></SPAN></FONT></td>
					<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="dp" key="label.MSA.ProductName"/></SPAN></FONT></td>
					<td width="7%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="dp" key="label.MSA.DCNO"/></SPAN></FONT></td>
					<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="dp" key="label.MSA.InvoiceNO"/></SPAN></FONT></td>
					<td width="10%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="dp" key="label.MSA.DistName"/></SPAN></FONT></td>
					<td  width="5%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="dp" key="label.MSA.Status"/></SPAN></FONT></td>
					<td width="9%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="dp" key="label.MSA.DistClaimDate"/></SPAN></FONT></td>
					<td width="7%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="dp" key="label.MSA.Swapped"/></SPAN></FONT></td>
					<td width="9%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="dp" key="label.MSA.NewDistName"/></SPAN></FONT></td>
					<td width="7%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="dp" key="label.MSA.NewDCNO"/></SPAN></FONT></td>
					<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="dp" key="label.MSA.NewInvoiceNO"/></SPAN></FONT></td>
						<td width="9%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
						<SPAN><bean:message  bundle="dp" key="label.MSA.Action"/></SPAN></FONT></td>
			
				</tr>
			</thead>
			
			<tbody>
				<logic:empty name="dpMissingStockApprovalBean" property="missingStockList">
					<TR>
						<TD class="text" align="center" colspan="8">No record available</TD>
					</TR>			
				</logic:empty>
				
				<logic:notEmpty name="dpMissingStockApprovalBean" property="missingStockList">
					<logic:iterate name="dpMissingStockApprovalBean" id="dpMSAList" indexId="i" property="missingStockList">
						<TR id='<%="tr"+i%>'  BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>'>
							<TD align="center" class="text">
							<logic:equal name="dpMSAList" property="strSwapped" value="YES">
								<input id='<%=i%>' disabled="disabled" checked="checked" type="checkbox"  value='<bean:write name="dpMSAList" property="strSerialNo"/>' id="checkMSA" property="strCheckedMSA" onclick="checkCheckBox(this)" name="strCheckedMSA">	
							</logic:equal>
							<logic:notEqual name="dpMSAList" property="strSwapped" value="YES">
								<input id='<%=i%>' type="checkbox"  value='<bean:write name="dpMSAList" property="strSerialNo"/>' id="checkMSA" property="strCheckedMSA" onclick="checkCheckBox(this)" name="strCheckedMSA">	
							</logic:notEqual>
							
							
							<!-- 	<input id='<%=i%>' type="checkbox"  value='<bean:write name="dpMSAList" property="strSerialNo"/>' id="checkMSA" property="strCheckedMSA" onclick="checkCheckBox(this)" name="strCheckedMSA">	 -->
							</TD>
							<TD align="center" class="text">
								<bean:write name="dpMSAList" property="strSerialNo"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="dpMSAList" property="strProductName"/>					
							</TD>
							<TD id = '<%="dc_no"+i%>'   align="center" class="text">
								<bean:write name="dpMSAList" property="strDCNo"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="dpMSAList" property="strInvoiceNo"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="dpMSAList" property="strDistName"/>					
							</TD>
							<TD id ='<%="status"+i%>' align="center" class="text">
								<bean:write name="dpMSAList" property="strStatus"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="dpMSAList" property="strDistClaimDate"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="dpMSAList" property="strSwapped"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="dpMSAList" property="strNewDistName"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="dpMSAList" property="strNewDCNo"/>					
							</TD>
	<TD align="center" class="text">
								<bean:write name="dpMSAList" property="strNewInvoiceNo"/>
							</TD>
							<!-- Modified to handle SWAP case :	SR2379177 -->
							<TD align="center" name="dpMSAList" class="text" id='<%="strAction"+i%>' property="strAction">
							<logic:equal name="dpMSAList" property="strSwapped" value="YES">
								<input disabled="disabled" type="text" id='<%="strActionText"+i%>'  readonly="true" value="Swapped" />
							</logic:equal>
							
							<logic:notEqual name="dpMSAList" property="strSwapped" value="YES">
								<input disabled="disabled" type="text" id='<%="strActionText"+i%>'  readonly="true"/>
							</logic:notEqual>
							<!-- Modified to handle SWAP case ends here -->
				
								<!--  <SPAN>
								<logic:equal value="SWP" name="dpMSAList" property="strAction">
									<html:select property="strAction" name="dpMSAList" disabled="true" style="width: 130px;">
										<html:option value="SWP"><bean:message key="label.MSA.SelectActionSWP" bundle="dp"/></html:option>
									</html:select>
								</logic:equal>
								<logic:equal value="WS" name="dpMSAList" property="strAction">
									<html:select property="strAction" name="dpMSAList" style="width: 130px;">
										<html:option value="WS"><bean:message key="label.MSA.SelectActionWS" bundle="dp"/></html:option>
										<html:option value="CAN"><bean:message key="label.MSA.SelectActionCANCEL" bundle="dp"/></html:option>
									</html:select>
								</logic:equal>
								<logic:equal value="0" name="dpMSAList" property="strAction">
									<html:select property="strAction" name="dpMSAList" style="width: 130px;">
										<html:option value="0"><bean:message key="label.MSA.SelectAction" bundle="dp"/></html:option>
										<html:optionsCollection property="listAction" label="strActionLabel" value="strActionValue" name="dpMissingStockApprovalBean"/>
									</html:select>
								</logic:equal>
								</SPAN>  -->
							</TD>
						</TR>
					</logic:iterate>
				</logic:notEmpty>
			</tbody>
		</table>
	</DIV>
	<br>
	<DIV>
		<table width="80%">
			<tr>
			<td align="center" class="text" width="20%"></td><td align="center" class="text" width="10%"></td>
			
				<td width="20%" align="center"><b class="text pLeft15">Choose Status</b></td>
							
						
				<TD align="center" class="text">
								<SPAN>
								<html:select property="assignValues"  style="width: 130px;">
								<html:option value=""><bean:message key="label.MSA.SelectAction" bundle="dp"/></html:option>
								<html:optionsCollection property="listAction" label="strActionLabel" value="strActionValue" name="dpMissingStockApprovalBean"/>
									<html:option value="WS"><bean:message key="label.MSA.SelectActionWS" bundle="dp"/></html:option>
										
								</html:select>
								</SPAN>
							</TD>	<td></td>
							
						</tr><tr>	
								<td width="20%"></td>
								<td width="20%"></td>
			<td align="center">
					<input type="button" value="Assign" onclick="assignStock();" >
				</td>
				<td align="center" style="width: 130px;">
					<input type="button" value="Submit" onclick="saveMissingStock();" >
				</td>
				<td align="center" style="width: 130px;">
					<input type="button" value="Cancel" onclick="cancelStockApproval();" >
				</td>
			</tr>
		</table>
	</DIV>
	<html:hidden property="methodName"/>
	
</html:form>
</body>
</html:html>