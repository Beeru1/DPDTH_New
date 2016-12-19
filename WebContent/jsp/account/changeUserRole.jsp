<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>
<HEAD>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<LINK href="<%=request.getContextPath()%>/theme/text.css" rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/theme/chromestyle2.css" />
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/chrome.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/JScriptLib.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>

<SCRIPT language="Javascript" type="text/javascript"><!--
	function checkTSM()
	{
		var newUserRole = document.getElementById("userRoleNew").value;			
		//alert(newUserRole);
		if(newUserRole == "6")
		{
			document.getElementById("td1").style.display="inline";
			document.getElementById("td2").style.display="inline";
			document.getElementById("td3").style.display="inline";
		}
		else
		{
			document.getElementById("td1").style.display="none";
			document.getElementById("td2").style.display="none";
			document.getElementById("td3").style.display="none";
		}
		if(newUserRole == "2")
		{
			document.getElementById("toCircleId").disabled = true ;
			document.getElementById("parentList").disabled = true ;
			document.getElementById("toCircleId").value="-1";
			var select=document.getElementById("parentList");
			//alert(select.length);
			//alert(select);
			
			select.options.length = 0;
			select.options[select.options.length] = new Option("--Select Parent--","");
			
			//for (i=0;i<select.length;  i++) {
			//     select.remove(i);
			//}
			document.getElementById("parentList").value="";
			//getUserList();
		}
		else
		{
			document.getElementById("toCircleId").disabled = false;
			document.getElementById("parentList").disabled = false ;
			document.getElementById("toCircleId").value="-1";
			document.getElementById("parentList").value="";
		}
		if(newUserRole == "3")
		{
		document.getElementById("showParent").style.display="none";
		}
		else
		{
		document.getElementById("showParent").style.display="block";
		}
		
	}
	function checkforDTHAdmin()
	{
		//alert("--"+document.getElementById("userRole").value+"--");
		 
		if(document.getElementById("userRole").value == "2")
		{
			//alert("if");
			document.getElementById("fromCircleId").disabled = true ;
			document.getElementById("fromCircleId").value="-1";
			getUserList();
		}
		else
		{
			//alert("else");
			document.getElementById("fromCircleId").disabled = false;
		}
		//alert(document.getElementById("fromCircleId").disabled);
	}
	
	function getAllRoles(){
	document.getElementById("approver1").value="";
	document.getElementById("approver2").value="";
	document.getElementById("sr_Number").value="";
		var url = "ChangeUserRole.do?methodName=getRole";
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
			req.onreadystatechange = getUserRole;
			req.open("POST",url,false);
			req.send();
	}
	
	function getUserRole() 
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
			var selectObj2 = document.getElementById("userRole");	
			var newUserRole = document.getElementById("userRoleNew");					
			selectObj2.options.length = 0;
			selectObj2.options[selectObj2.options.length] = new Option("--Select User Role--","");
			
			newUserRole.options.length = 0;
			newUserRole.options[newUserRole.options.length] = new Option("--Select User Role--","");
			
					
            		for(var i=0; i<optionValues.length; i++)
					{
					selectObj2.options[selectObj2.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
					newUserRole.options[newUserRole.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
					
					}
			
  		}
	}
	function displayCurrentCircle() 
	{
		document.getElementById("fromCircleId").selectedIndex=0;
		document.getElementById("userList").selectedIndex=0;
		document.getElementById("userRoleNew").selectedIndex=0;
		document.getElementById("parentList").selectedIndex=0;
		var user = document.getElementById('userRole').options[document.getElementById("userRole").selectedIndex].text;
		if(user=="MobilityAdmin" || user=="Administrator"){
			document.getElementById("currentCircle").style.display = 'none';
			//document.getElementById("newCircle").style.display = 'inline';
			getUserList();
				
		}else{
			document.getElementById("currentCircle").style.display = 'inline';	
			//document.getElementById("newCircle").style.display = 'none';
		}
	}
	
	function getUserList(){
	
	   document.getElementById("userList").selectedIndex=0;
	   var circleId = document.getElementById("fromCircleId").value;
	 	
	   var selectedCircleValues ="";
	   selectedCircleValues =0;
	   var userRole = document.getElementById('userRole').options[document.getElementById("userRole").selectedIndex].value;
		 	//alert(" else loop of circle selected");
		 	if(circleId==0 && (!(userRole==3 || userRole==2 )))
		 	//if(userRole!=3 && circleId==0)
		 	{
		 	alert("Select Valid Circle");
		 	document.getElementById("fromCircleId").value=-1;
		 	return false;
		 	}
		 	
		 	var panfound=false;
		 	var count=0;
			for (var i=0; i < document.forms[0].fromCircleId.length; i++)
		  	 {
		   		 if (document.forms[0].fromCircleId[i].selected)
		     	 {
		     	 	count=count+1;
		     		if(selectedCircleValues !=""){
						selectedCircleValues += ",";
		     		}
					var selectedValCircle = document.forms[0].fromCircleId[i].value.split(",");
					selectedCircleValues += selectedValCircle[0];
					if(selectedValCircle==0)
			     	{
			     	panfound=true;
			     	}
			     	if(count>1 && panfound==true)
			     	{
			     			alert("Please select either PAN India or Particular Circle");
		 					document.getElementById("fromCircleId").value=-1;
		 					return false;
			     	}
			     	
		     	 }
		   	}
	   	
	   	 //	document.getElementById("hiddenCircleSelectIds").value =selectedCircleValues;
	 	var userRole = document.getElementById('userRole').options[document.getElementById("userRole").selectedIndex].value;
    	var url = "ChangeUserRole.do?methodName=getUserList&circleId="+selectedCircleValues+"&userRole="+userRole+"&random="+new Date().toString();
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
			req.onreadystatechange = getAllUsers;
			req.open("POST",url,false);
			req.send();
	}
	function getAllUsers() 
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
			var selectObj2 = document.getElementById("userList");					
			selectObj2.options.length = 0;
			selectObj2.options[selectObj2.options.length] = new Option("--Select User--","");
					
            		for(var i=0; i<optionValues.length; i++)
					{
					selectObj2.options[selectObj2.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
					}
  		}
	}
	function getParentList(){
		if(validate()==1){
		var newUserRole = document.getElementById("userRoleNew").value;
		var toCircleId = document.getElementById("toCircleId").value;
		var currentUser = document.getElementById('userList').value;
	
		
		
	   var toCircleId = document.forms[0].toCircleId[0].value;
	   var selectedCircleValues ="";
		selectedCircleValues =0;
			
		 	
		 	var panfound=false;
		 	var count=0;
			for (var i=0; i < document.forms[0].toCircleId.length; i++)
		  	 {
		   		 if (document.forms[0].toCircleId[i].selected)
		     	 {
		     	 	count=count+1;
		     		if(selectedCircleValues !=""){
						selectedCircleValues += ",";
		     		}
//		     			alert(">"+document.forms[0].toCircleId[i].value);
			     	var selectedValCircle = document.forms[0].toCircleId[i].value.split(",");
			     	
			     	if(selectedValCircle==0)
			     	{
			     		panfound=true;
			     	}
			     	if(count>1 && panfound==true)
			     	{
			     			alert("Please select either PAN India or Particular Circle");
		 					document.getElementById("toCircleId").value=-1;
		 					return false;
			     	}
			     	
//			     	alert("selectedValCircle::"+selectedValCircle);
			     	selectedCircleValues += selectedValCircle[0];
		     	 }
		   	}
	   		//alert(" else loop of circle selected"+newUserRole+"toCircleId"+toCircleId);
		 	if(selectedCircleValues==0 && (!(newUserRole==3 || newUserRole==2 )))
		 	{
		 	alert("Please select Valid circle");
		 	document.getElementById("toCircleId").value=-1;
		 	return false;
		 	}
	   	 	document.getElementById("hiddenCircleSelectIds").value =selectedCircleValues;
//	 		alert("==>>"+document.getElementById("hiddenCircleSelectIds").value);
		
		
	
		var circleId;		
		if(newUserRole==1 || newUserRole==2 || newUserRole==3){
	 	circleId=0;
	 	}else{
	 	circleId = document.getElementById("fromCircleId").value;
	 	}
	 var userRole = document.getElementById('userRoleNew').options[document.getElementById("userRoleNew").selectedIndex-1].value;
	 var url = "ChangeUserRole.do?methodName=getParentList&userRole="+userRole+"&circleId="+selectedCircleValues+"&currentUser="+currentUser+"&random="+new Date().toString();
//	 	alert(url);
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
			req.onreadystatechange = getParentUsers;
			req.open("POST",url,false);
			req.send();
			}
	}
	function getParentUsers() 
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
			var selectObj2 = document.getElementById("parentList");					
			selectObj2.options.length = 0;
			selectObj2.options[selectObj2.options.length] = new Option("--Select Parent--","");
					
            		for(var i=0; i<optionValues.length; i++)
					{
					var parentId = document.getElementById('userList').options[document.getElementById("userList").selectedIndex].value;					
					if(parentId!=optionValues[i].getAttribute("value")){//in case of downgrade the Parent cannot be the current user
					selectObj2.options[selectObj2.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
					}
					}
  		}
	}
	function validate(){
	var currentRole = document.getElementById('userRole').options[document.getElementById("userRole").selectedIndex].text
	var newRole = document.getElementById('userRoleNew').options[document.getElementById("userRoleNew").selectedIndex].text
	if(currentRole==newRole){
	alert("You have chosen the same Role as user has currently. Please choose another Role.");
	document.getElementById("userRoleNew").selectedIndex=0;
	document.getElementById("parentList").selectedIndex=0;
	return 0;
	   }
	   return 1;
    }
    
    function changeRole(){
    
		    

    
var userRole = document.getElementById("userRole").value;
var fromCircleId = document.getElementById("fromCircleId").value;
var userList = document.getElementById("userList").value;
var userRoleNew = document.getElementById("userRoleNew").value;
var toCircleId = document.getElementById("toCircleId").value;
var parentList = document.getElementById("parentList").value;         
           
           
    var approver1 = document.getElementById("approver1").value;
    var srNo = document.getElementById("sr_Number").value;   
    
    if(userRole=="" || userRole==-1){
   		 alert("Please Select Current User Role.");
    	return 0;
    	}
    if(userRole != "2")	
    {
	    if(document.getElementById("fromCircleId").value=="" || document.getElementById("fromCircleId").value==-1){
	   		 	alert("Please Select Current Circle.");
	    		return 0;
	    	}
    }    
    if(userList=="" || userList==-1){
    	 alert("Please Select Current User.");
    	return 0;
    }
    if(userRoleNew=="" || userRoleNew==-1){
    	 alert("Please Select New User Role.");
    	return 0;
    }
    
    
    
    if(userRoleNew != "2")
    {
	    if(document.getElementById("toCircleId") !="undefined")
	    {
	    	if(document.getElementById("toCircleId").value =="" || document.getElementById("toCircleId").value==-1){
	   			alert("Please Select New Circle.");
	    		return 0;
	    	}
	    }
        
        if(userRoleNew != "3")
        {
		    if(parentList=="" || parentList==-1){
		    	 alert("Please Select New Parent.");
		    	return 0;
		    }
    	}
	    if(userRoleNew == "6")	{
		   	if(document.getElementById("transType").value =="" || document.getElementById("transType").value==-1){
		   			alert("Please Select Transaction Type");
		    		return 0;
		    }	 
		}  
	
	}
	
		
   	if(approver1==""){
    	alert("Please enter Approver1 Name.");
    	return 0;
    }
    	
   	var firstCharApp1 = isNumber(approver1.charAt(0));
	if(firstCharApp1==true || approver1.charAt(0)==""){
		alert("Approver 1 Cannot Begin With A Numeric Character Or Blank Space.");
		document.getElementById("approver1").focus();
		return false;
	}
	if(document.getElementById("approver1").value.length < 8)
	{
		alert("Approver 1 Must Contain At Least 8 Characters.");
		document.getElementById("approver1").focus();
		return false;
	}
	var validapprover1 = isAlphaNumeric(approver1);
	if(validapprover1==false){
		alert("Special Characters and Blank Spaces are not allowed in Approver 1");
		document.getElementById("approver1").focus();
		return false;
	}
    

    if(document.getElementById("approver2").value!=null && document.getElementById("approver2").value!=""){
			var approver2 = document.getElementById("approver2").value;
			var firstCharApp2 = isNumber(approver2.charAt(0));
			if(firstCharApp2==true || approver2.charAt(0)==""){
				alert("Approver 2 Cannot Begin With A Numeric Character Or Blank Space.");
				document.getElementById("approver2").focus();
				return false;
			}
			if(document.getElementById("approver2").value.length < 8)
			{
				alert("Approver 2 Must Contain At Least 8 Characters.");
				document.getElementById("approver2").focus();
				return false;
			}
			var validapprover2 = isAlphaNumeric(approver2);
			if(validapprover2==false){
				alert("Special Characters and Blank Spaces are not allowed in Approver 2");
				document.getElementById("approver2").focus();
				return false;
			}
				
}
    
    
    if(srNo==""){
    	alert("Please enter SR_Number.");
    	return 0;
    }
     if(userRoleNew == "2") {
		    if(validate()!=1)
		    {
		    	return 0;
		    }
    }
    var currentRole = document.getElementById('userRole').options[document.getElementById("userRole").selectedIndex].value
	var newRole = document.getElementById('userRoleNew').options[document.getElementById("userRoleNew").selectedIndex].value
	    
	    //alert("calling check ");
	    checkChilds();
	    
	    var childs = document.getElementById("haveChilds").value;
		
		if(childs !="true") {
			alert(childs);
			return 0;
		}
//		alert(newRole);
	//	alert(currentRole);
		if(newRole < currentRole)	{	
		    alert("You are upgrading the role.");			   	  
	    }
	  	else
	    {
	    	alert("You are downgrading the role.");
	    }
	    
	    return 1;
    }
    function checkChilds()
    {
    
    var currentRole = document.getElementById('userRole').options[document.getElementById("userRole").selectedIndex].value
	var newRole = document.getElementById('userRoleNew').options[document.getElementById("userRoleNew").selectedIndex].value
	var currentUser = document.getElementById('userList').options[document.getElementById("userList").selectedIndex].value
	
   //  if(newRole<currentRole)	{ //removed by neetika as if we are checking for upgrade then we should check the same for downgrade
      var url = "ChangeUserRole.do?methodName=checkChilds&currentUser="+currentUser+"&currentRole="+currentRole+"&random="+new Date().toString();
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
				req.onreadystatechange = checkUserChilds;
				req.open("POST",url,false);
				req.send();
	//	}
    
    }  
    
    function checkUserChilds() 
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
				var haveChilds = optionValues[0].getAttribute("haveChilds1");
				//alert("flag from ajax::"+haveChilds);
				document.getElementById("haveChilds").value=haveChilds;
			}	
	}
	
	function updateUserRoles(){
	if(changeRole()==1){
	
	//checkChilds();
	
    var currentRole = document.getElementById('userRole').options[document.getElementById("userRole").selectedIndex].value;
    var currentCircle = document.getElementById('fromCircleId').options[document.getElementById("fromCircleId").selectedIndex].value	;
	var currentUser = document.getElementById('userList').options[document.getElementById("userList").selectedIndex].value;
	var newRole = document.getElementById('userRoleNew').options[document.getElementById("userRoleNew").selectedIndex].value;
    var newCircle = document.getElementById('toCircleId').options[document.getElementById("toCircleId").selectedIndex].value;
    var parent = document.getElementById('parentList').options[document.getElementById("parentList").selectedIndex].value;
    
    
     var selectedCircleValues ="";
	 selectedCircleValues =0;
			
		 	//alert(" else loop of circle selected");
			for (var i=0; i < document.forms[0].fromCircleId.length; i++)
		  	 {
		   		 if (document.forms[0].fromCircleId[i].selected)
		     	 {
		     		if(selectedCircleValues !=""){
						selectedCircleValues += ",";
		     		}
					var selectedValCircle = document.forms[0].fromCircleId[i].value.split(",");
					selectedCircleValues += selectedValCircle[0];
		     	 }
		   	}
    
    
    
     var selectedToCircleValues ="";
	 selectedToCircleValues =0;
			
		 	//alert(" else loop of circle selected");
			for (var i=0; i < document.forms[0].toCircleId.length; i++)
		  	 {
		   		 if (document.forms[0].toCircleId[i].selected)
		     	 {
		     		if(selectedToCircleValues !=""){
						selectedToCircleValues += ",";
		     		}
					var selectedValToCircle = document.forms[0].toCircleId[i].value.split(",");
					selectedToCircleValues += selectedValToCircle[0];
		     	 }
		   	}
   var answer= confirm("Are you sure you want to change the role");
			if (answer==true)
			  {
			    var url = "ChangeUserRole.do?methodName=updateUserRoles&currentRole="+currentRole+"&currentCircle="+selectedCircleValues+"&currentUser="+currentUser+"&newRole="+newRole+"&newCircle="+selectedToCircleValues+"&parent="+parent+"&random="+new Date().toString();
				//alert(url);
				document.forms[0].action=url;
				document.forms[0].method="POST";
				document.forms[0].submit();
			 }
			  
			else
			  {
			    return false;
			  }
    }
	
	
	}
	
    
--></SCRIPT>
</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif" 
 background="<%=request.getContextPath()%>/images/bg_main.gif" bgcolor="#FFFFFF" 
 leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onload="getAllRoles();">
<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">
	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include
			page="../common/dpheader.jsp" flush="" /></TD>
	</TR>
	<TR valign="top">
		<TD width="167" align="left" bgcolor="b4d2e7" valign="top"
			height="100%"><jsp:include page="../common/leftHeader.jsp" flush="" /></TD>
		<TD valign="top" width="100%" height="100%"><html:form
			name="changeUserRoleAction" action="/ChangeUserRole"
			type="com.ibm.dp.beans.ChangeUserRoleBean">
			<html:hidden property="methodName" styleId="methodName"/>
			<html:hidden property="user" styleId="user"/>
			<html:hidden property="haveChilds" styleId="haveChilds" value="true"/>
			<html:hidden property="hiddenCircleSelectIds" styleId="hiddenCircleSelectIds" name="ChangeUserRoleBean" />
			<TABLE width="545" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/changeUserRole.jpg" width="505" height="22" alt=""></TD>
				</TR>
				<TR>
					<TD colspan="4" class="text"><FONT color="#ff0000" size="2">
					<html:errors bundle="error" /> <html:errors bundle="view" />
					<strong><bean:write name="ChangeUserRoleBean" property="strMsg" /></strong>
					 </FONT></TD>
				</TR>
				<tr>
				<td width="50">&nbsp;</td>
					<td ><strong><font color="#000000">Current User Role <FONT color="RED">*</FONT>:  </TD>
				<TD class="txt">
				<html:select property="userRole" styleId="userRole" style="width:150px" onchange="checkforDTHAdmin();displayCurrentCircle();">
					<html:option value="">--Select User Role--</html:option>
				</html:select></td>
												
				</tr>
				<tr id="currentCircle">
				<td width="50">&nbsp;</td>
				<td><strong><font color="#000000">Current Circle <FONT color="RED">*</FONT>:  </TD>
				<TD class="txt">
				<html:select property="fromCircleId" multiple="true" styleId="fromCircleId" onchange="getUserList()" style="width:150px">
					<logic:notEmpty property="circleList" name="ChangeUserRoleBean" >
						<html:option value="0">--Select Circle--</html:option>
						<bean:define id="circleList" name="ChangeUserRoleBean" property="circleList" />
						<html:options labelProperty="circleName" property="circleId" collection="circleList" />
					
					</logic:notEmpty>
				</html:select>
				</TD>
				</tr>
				
				<tr>
				<td width="50">&nbsp;</td>
				<td><strong><font color="#000000">Current Users <FONT color="RED">*</FONT>: </TD>
				<TD class="txt">
				<html:select property="userList" style="width:150px">
					<html:option value="">--Select User--</html:option>
				</html:select></td>
				</tr>
				
				<tr>
				<td width="50">&nbsp;</td>
					<td><strong><font color="#000000">New User Role <FONT color="RED">*</FONT>:  </TD>
				<TD>
				<html:select property="userRoleNew" style="width:150px" onchange="return checkTSM();">
					<html:option value="">--Select User Role--</html:option>
				</html:select></td>
												
				</tr>
								
				<tr id="newCircle">
				<td width="50">&nbsp;</td>
				<td><strong><font color="#000000">New Circle <FONT color="RED">*</FONT>: </TD>
				<TD class="txt">
				<html:select property="toCircleId" style="width:150px" multiple="true" onchange="getParentList()">
					<logic:notEmpty property="circleList" name="ChangeUserRoleBean" >
						<html:option value="-1">--Select Circle--</html:option>
						<bean:define id="circleList" name="ChangeUserRoleBean" property="circleList" />
						<html:options labelProperty="circleName" property="circleId" collection="circleList" />
					
					</logic:notEmpty>
				</html:select>
				</TD>	
				</tr>
				<tr id="showParent" style="display: inline;">
				<td width="50">&nbsp;</td>
				<td id="parentRow"><strong><font color="#000000">Parent <FONT color="RED">*</FONT>: </TD>
				<TD id="parentBox" class="txt">
				<html:select property="parentList" style="width:150px">
					<html:option value="">--Select Parent--</html:option>
				</html:select></td>
				</tr>
				<tr>
				<td width="50" style="display: none;" id="td1">&nbsp;</td>
				<td style="display: none;" id="td2"><strong><font color="#000000">Transaction Type<FONT color="RED">*</FONT>: </TD>
				<TD class="txt" style="display: none;" id="td3">
				<html:select property="transType" style="width:150px">
					<html:option value="">--Select--</html:option>
					<html:option value="1">Sales</html:option>
					<html:option value="2">CPE</html:option>
				</html:select></td>
				</tr>
				<tr>
				<td width="50">&nbsp;</td>
				<td><strong><font color="#000000">
				Approver 1 <FONT color="RED">*</FONT>: 
					</font></strong></td>
				<td><html:text name="ChangeUserRoleBean" maxlength="10"
					styleId="approver1" property="approver1"/></td>
				</tr>
				
				<tr>
				<td width="50">&nbsp;</td>
				<td><strong><font color="#000000">
				Approver 2: 
					</font></strong></td>
				<td><html:text name="ChangeUserRoleBean" maxlength="10"
					styleId="approver2" property="approver2"/></td>
				</tr>
				
				<tr>
				<td width="50">&nbsp;</td>
				<td><strong><font color="#000000">
				SR Number : <FONT color="RED">*</FONT>
					</font></strong></td>
				<td><html:text name="ChangeUserRoleBean" maxlength="20"
					styleId="sr_Number" property="sr_Number"/></td>
				</tr>
				
				<tr>
				<td width="50">&nbsp;</td>
				<td></td>
				<td>
				<input type="button" value="Submit" onclick="updateUserRoles();">
				</td>
				</tr>
				
				
				
		</TABLE>

		</html:form></TD>
	</TR>
	<TR align="center" bgcolor="4477bb" valign="top">
		<TD colspan="2" height="17" align="center"><jsp:include
			page="../common/footer.jsp" flush="" /></TD>
	</TR>
</TABLE>
</BODY>
</html:html>
