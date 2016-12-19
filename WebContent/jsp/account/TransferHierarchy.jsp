<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.ibm.dp.dto.TransferHierarchyDto"%>

<html:html>
<HEAD>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<LINK href="<%=request.getContextPath()%>/theme/text.css"
	rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/theme/chromestyle2.css" />
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/chrome.js"></SCRIPT>
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/JScriptLib.js"></SCRIPT>
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
	
<script>

function stopRKey(evt) { 
  var evt = (evt) ? evt : ((event) ? event : null); 
  var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null); 
  if ((evt.keyCode == 13) && (node.type=="text"))  {return false;} 
} 

document.onkeypress = stopRKey; 


	function trimAll(sString) {
		while (sString.substring(0,1) == ' '){
		sString = sString.substring(1, sString.length);
		}		
		while (sString.substring(sString.length-1, sString.length) == ' '){
		sString = sString.substring(0,sString.length-1);	
		}
		return sString;
	}
	function getChild()
	{
		var userName=document.getElementById('userName');
		if(userName.value=="")
		{
			alert("User can not be blank");
			document.getElementById('userName').focus();
		}
		else
		{
			document.getElementById('methodName').value = "getChildUser";
			document.forms[0].submit();
		}
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
	         for (var a=1; a < inputs.length; a++) 
	         {
	
	          if (inputs[a].type == 'checkbox') 
	        	  inputs[a].checked=false;
	         }
	       }
	   }
	}
	
	function cancelTransferHierarchy()
{
		document.forms[0].action="TransferHierarchy.do?methodName=init";
		document.forms[0].method="post";
		document.forms[0].submit();
}
	
	
function saveTransferHierarchy()
	{
   		var count;
		var childUserAccountId;
		var parentID;
		var childUserAccountIdAndparentName="";
	   	var tableRows = document.getElementById("tableTH").rows;
	   	
		var alertMsg = validateSave();
		
		if(alertMsg=="ok")
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
		// START of submitting the for to save
			
			for(var rowCount=1; rowCount<tableRows.length; rowCount++)
			{
				count=	rowCount-1;
				//alert(typeof(document.forms[0].intAccountID)!="undefined");
				//alert(typeof(document.forms[0].intAccountID[count])!="undefined");
				
				if(typeof(document.forms[0].intAccountID)!="undefined" && typeof(document.forms[0].intAccountID[count])!="undefined" )
				{
				if(document.forms[0].intAccountID[count].checked==true  && !document.forms[0].intAccountID[count].disabled)
					{
						childUserAccountId=document.forms[0].intAccountID[count].value;
						parentID=tableRows[rowCount].cells[8].innerText;
						if(parentID=="")
							parentID=" ";
						childUserAccountIdAndparentName=childUserAccountIdAndparentName+childUserAccountId+"#"+parentID+",";
					}
				}
				// for Single row( child user) STARTS here 
				else
				{
				if(document.forms[0].intAccountID.checked==true  && !document.forms[0].intAccountID.disabled)
					{
					childUserAccountId=document.forms[0].intAccountID.value;
						parentID=tableRows[rowCount].cells[8].innerText;
						if(parentID=="")
							parentID=" ";
						childUserAccountIdAndparentName=childUserAccountIdAndparentName+childUserAccountId+"#"+parentID+",";
					}
				}
			// for Single row( child user) ENDS here 
			}
			//alert(childUserAccountIdplusparentID);
			//alert(" childUserAccountIdAndparentName :"+childUserAccountIdAndparentName);
			document.getElementById('accountNameParentNameMapping').value = childUserAccountIdAndparentName;
			document.forms[0].methodName.value="saveTransferHierarchy";
			document.forms[0].submit();
			// END of submitting for save
		}
		else
		{
			if(alertMsg!="")
			{
				alert(alertMsg);
			}
		}
	}
	
	function validateSave()
	{
		var result="";
		var inputs = document.getElementsByTagName('input');
		var flag=false;
		var tableRows = document.getElementById("tableTH").rows;
		 
		
		
		for(var rowCount=1; rowCount<tableRows.length; rowCount++)
		{
			//alert("tableRows[rowCount].cells[8].innerText :"+tableRows[rowCount].cells[8].innerText);
			count =	rowCount-1;
			if(typeof(document.forms[0].intAccountID)!="undefined" && typeof(document.forms[0].intAccountID[count])!="undefined" )
			{
				if(tableRows[rowCount].cells[8].innerText!="")
					{
						result="ok";
					}
					else
					{
						result= "Please assign new parents for all users.";
					}
			}
			// for single row (single child user , validation start)
			else
			{
			if(typeof(document.forms[0].intAccountID)!="undefined" && typeof(document.forms[0].intAccountID)!="undefined" )
			{
				if(tableRows[rowCount].cells[8].innerText!="")
					{
						result="ok";
					}
					else
					{
						result= "Please assign new parents for all users.";
					}
			}
			}
			// // for single row (single child user , validation END)
		}
		//alert(" validation result is :"+result);
		return result;
	}
	
	
	function assignParent()
	{
		var newParentIdAndCircle=document.getElementById("assignValues").value;
		var inputs = document.getElementsByTagName('input');
	    var flag=true;
	    var assignmentFlag=false;
	    var parentObj=document.getElementById("assignValues");
	    var parentSelectedText=parentObj.options[parentObj.selectedIndex].text;
	    var tableRows = document.getElementById("tableTH").rows;
	    var accountIdforAlert="";
	    var accountId ;
		var circleids ;
		var index;
		var newparentidx;
		var childCircleId;
		var parentCircleID;
		var object;
		// checking circle ids belogs to
		var childcircleIdsArray;
	   // alert(" parentValue :"+parentSelectedText);
	   if(inputs.length>1)
		{
	     	for (var i =1; i < inputs.length; i++) 
	   		 {
	   		// alert("not working in single rows:");
				if (inputs[i].type == 'checkbox' && !inputs[i].disabled)
				{
				//	alert("strActionText id:"+(document.getElementById("strActionText"+i).id));
					if(inputs[i].checked == true)
					{
					flag=true;
						break;
					}
					else
					{
					flag=false;
					}
				}
				
			}
			
		}
		//alert("Flag :"+flag);
		if(!flag && document.getElementById("assignValues").value=='-1')
		    {
		     alert(" Please select at least one child user name and New Parent Name");
		    }
	    else if(!flag )
		    {
		    alert("Please select at least one child user");
		    }
	    else if(document.getElementById("assignValues").value=='-1')
		    {
		    alert("Please select New Parent Name");
		    }
	    else
	    {
	     var newParentIdandCircleArr=newParentIdAndCircle.split("#");
			var newParentsID=newParentIdandCircleArr[0];
			//alert("newParentsID :"+newParentsID);
			//alert(" Parent's Circles :"+newParentIdandCircleArr[1]);
			var newParentsCircleIds=newParentIdandCircleArr[1].split(",");
			//alert("tableRows.length :"+tableRows.length);
			for(var rowCount=1; rowCount<tableRows.length; rowCount++)
				{
				count=	rowCount-1;
					if(typeof(document.forms[0].intAccountID)!="undefined" && typeof(document.forms[0].intAccountID[count])!="undefined")
					{
					if(document.forms[0].intAccountID[count].checked==true  && !document.forms[0].intAccountID[count].disabled)
						{
						accountId = tableRows[rowCount].cells[1].innerText;
						circleids = tableRows[rowCount].cells[7].innerText;
						childcircleIdsArray= circleids.split(",");
							for(index=0; index < childcircleIdsArray.length ;index++)
							{
								for(newparentidx=0;newparentidx < newParentsCircleIds.length ;newparentidx++)
								{
									childCircleId=trimAll(childcircleIdsArray[index]);
									parentCircleID=trimAll(newParentsCircleIds[newparentidx]);
									//alett(childcircleIdsArray[index]+"--"+newParentsCircleIds[newparentidx]);
									if((childcircleIdsArray[index]!=" " || newParentsCircleIds[newparentidx]!=" ") && (childCircleId == parentCircleID))
									{
										assignmentFlag=true;
										break;
									}
									else
									{
										if((childcircleIdsArray[index]!=" " || newParentsCircleIds[newparentidx]!=" ") && ( parentCircleID==0)) //Neetika PAN india Circle Admin
											{assignmentFlag=true;
											break;}
											else
										assignmentFlag=false;
									}
								}
								if(assignmentFlag == true)
								{
									break;
								}
							}
						
							if(assignmentFlag)
							{
								document.forms[0].intAccountID[count].checked=false;
								object=tableRows[rowCount].cells[6];
								object.innerHTML="<input disabled='disabled'align='center' type='text' value='"+parentSelectedText+"' readonly='true'/>" ;
								tableRows[rowCount].cells[8].innerText=newParentsID;
							}
							else
							{
								document.forms[0].intAccountID[count].checked=true;
								tableRows[rowCount].cells[8].innerText=" ";
								accountIdforAlert=accountIdforAlert+accountId+",";
							}
						}
					}
					
					// for Single row only (Single child ) STARTs here
					else
						{
						if(document.forms[0].intAccountID.checked==true  && !document.forms[0].intAccountID.disabled)
							{
								accountId = tableRows[rowCount].cells[1].innerText;
								circleids = tableRows[rowCount].cells[7].innerText;
								// checking circle ids belogs to
								childcircleIdsArray= circleids.split(",");
								for(index=0; index < childcircleIdsArray.length ;index++)
								{
									for(newparentidx=0;newparentidx < newParentsCircleIds.length ;newparentidx++)
									{
										//alert("child circle ID :"+childcircleIdsArray[index] +" Parent Circle Id :"+newParentsCircleIds[newparentidx]);
										childCircleId=trimAll(childcircleIdsArray[index]);
										parentCircleID=trimAll(newParentsCircleIds[newparentidx]);
										//alert(" (childcircleIdsArray[index] == newParentsCircleIds[newparentidx]) :"+(childCircleId==parentCircleID));
										if((childcircleIdsArray[index]!=" " || newParentsCircleIds[newparentidx]!=" ") && (childCircleId == parentCircleID))
										{
											assignmentFlag=true;
											break;
										}
										else
										{	
											if((childcircleIdsArray[index]!=" " || newParentsCircleIds[newparentidx]!=" ") && ( parentCircleID==0)) //Neetika PAN india Circle Admin
											{assignmentFlag=true;
											break;}
											else
											assignmentFlag=false;
										}
									}
									if(assignmentFlag == true)
									{
										break;
									}
								}
							
								if(assignmentFlag)
								{
									document.forms[0].intAccountID.checked=false;
									object=tableRows[rowCount].cells[6];
									object.innerHTML="<input disabled='disabled'align='center' type='text' value='"+parentSelectedText+"' readonly='true'/>" ;
									tableRows[rowCount].cells[8].innerText=newParentsID;
								}
								else
								{
									//alert("Circle not matched for "+accountId+" , Parent could not assigned");
									document.forms[0].intAccountID.checked=true;
									tableRows[rowCount].cells[8].innerText=" ";
									accountIdforAlert=accountIdforAlert+accountId+",";
								}
							}
					}
					// for Single row only (Single child ) ENDs here
		}
		
	}
	if(accountIdforAlert!="")
	alert("Circle for the users :\n"+accountIdforAlert.substr(0,accountIdforAlert.lastIndexOf(","))+"\nnot matching with the new parent assigned");
	document.getElementById("chkAll").checked=false;
}
		
		
  
</script>
</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">
	
<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
    
%>
<html:form name="TransferHierarchyBean" action="/TransferHierarchy" type="com.ibm.dp.beans.TransferHierarchyBean">
<html:hidden property="methodName"/>
<html:hidden property="accountNameParentNameMapping"/>

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">
	<TR valign="top">
		<TD valign="top" width="100%">
			<TABLE width="545" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="3" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/Trans_hier_admin.jpg" width="505" height="22" alt=""></TD>
				</TR>
				<tr>
					<td width="50">&nbsp;</td>
					<td><strong><font color="#000000">User Name :<FONT color="RED">*</FONT></TD>
					<TD class="txt"><html:text property="userName" style="width:150px">
					</html:text><input type="button" value="Go" onclick="javascript:getChild()">
					</TD>
				</tr>
				<tr>
					<td colspan="3">
						<logic:notEmpty property="strSuccessMsg" name="TransferHierarchyBean">
							<strong><font color="red"><bean:write property="strSuccessMsg"  name="TransferHierarchyBean"/></font></strong>
						</logic:notEmpty>
					</td>
				</tr>
			</TABLE>
			
			
			<DIV style="height: 290px; width:100%; overflow-x:scroll; overflow-y:scroll;visibility: visible;z-index:1; left: 133px; top: 250px;">
	
		<table width="100%" align="left" border="1" cellpadding="0" cellspacing="0" id="tableTH"
			style="border-collapse: collapse;">
			<thead>
				<tr class="noScroll">
					<td width='5%' bgcolor="#CD0504" class="colhead" align="center">
						<FONT color="white">
						<input id="chkAll" type="checkbox" onclick="SelectAll()" >	
							</FONT>
					</td>
					
					<td width='15%' bgcolor="#CD0504" class="colhead" align="center"> <FONT color="white">User OLM ID</FONT></td>
					
					<td width='20%' bgcolor="#CD0504" class="colhead" align="center"> <FONT color="white">Account Name</FONT></td>
					
					<td width='15%' bgcolor="#CD0504" class="colhead" align="center"> <FONT color="white">Role</FONT></td>
					
					<td width='15%' bgcolor="#CD0504" class="colhead" align="center"> <FONT color="white">Status</FONT></td>
					
					<td width='10%' bgcolor="#CD0504" class="colhead" align="center"> <FONT color="white">Circle</FONT></td>
					
					<td width='20%' bgcolor="#CD0504" class="colhead" align="center"> <FONT color="white">New Parent</FONT></td>
				
				</tr>
			</thead>
			<tbody>
			
				<logic:empty name="TransferHierarchyBean" property="transferHierachyList">
					<TR>
						<TD class="text" align="center" colspan="8">No record found</TD>
					</TR>			
				</logic:empty>
				<logic:notEmpty name="TransferHierarchyBean" property="transferHierachyList">
					<logic:iterate name="TransferHierarchyBean" id="userList" indexId="i" property="transferHierachyList">
					<TR id='<%="tr"+i%>'  BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>'>
						<td align="center" class="text">
							<input id='<%="checkbox"+i%>'  type="checkbox"  value='<bean:write name="userList" property="intAccountID"/>' 
							id="checkUser" property="intAccountID" name="intAccountID">
						</td>
						
						<TD align="center" class="text">
								<bean:write name="userList" property="strOLMID"/>					
						</TD>
						
						<TD align="center" class="text">
								<bean:write name="userList" property="strAccountName"/>					
						</TD>
						
						<TD align="center" class="text">
								<bean:write name="userList" property="strRole"/>					
						</TD>
						
						<TD align="center" class="text">
								<bean:write name="userList" property="strStatus"/>					
						</TD>
						
						<TD align="center" class="text">
								<bean:write name="userList" property="strCircleName"/>					
						</TD>
						
						<TD align="center" class="text">
							<input disabled="disabled" type="text" id='assignedParent' readonly="true"/>
						</TD>
						
						<TD align="center" style="display:none">
							<bean:write name="userList" property="strCircleIDs"/>					
						</TD>
						<TD align="center" style="display:none">
												
						</TD>
					</TR>
					</logic:iterate>
				</logic:notEmpty>
			</tbody>
		</table>
	</DIV>
		</TD>
		
	</TR>
	
	<tr>
	<td>
		<div>
			<table width="80%">
				<tr>
					<td align="center" class="text" width="20%"></td><td align="center" class="text" width="10%"></td>
			
				<td width="20%" align="center"><b class="text pLeft15">Assign New Parent :</b></td>
					
						<TD align="center" class="text">
								<SPAN><html:select property="assignValues"  style="width: 130px;">
							<html:option value="-1"><bean:message key="label.TH.SelectAction" bundle="dp"/></html:option>
							<html:optionsCollection property="transferHierachyParentList" label="strParentName" value="strParentCircleId" name="TransferHierarchyBean"/>
						</html:select></td>
						<td width="10%"></td>
						<td width="10%"></td>
				
				<td></td>
							
						</tr><tr>	
								<td width="20%"></td>
								<td width="20%"></td>
					<td align="center"><input type="button" value="Assign" onclick="assignParent();" ></td>
					<td align="center" style="width: 130px;">	<input type="button" value="Submit" onclick="saveTransferHierarchy();" ></td>
					<td align="center" style="width: 130px;"><input type="button" value="Cancel" onclick="cancelTransferHierarchy();" ></td>
				</tr>
			</table>
		</div>
	</td>
	</tr>
</TABLE>

</html:form>
</BODY>
</html:html>
