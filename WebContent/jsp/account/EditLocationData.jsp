<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>
<head>
</head>

<script language="javascript">

  function disable(){

   	if(document.editLocForm.rd2.checked==true)
	{
		var x=window.confirm("Please confirm if you really wish to change the status of this location?");
		if (x)
		{
			if(document.editLocForm.moveToLocation.value==-1)
			{
				alert("Please SelectLocation To Which The Sub-Locations And Business Users Of This Location Can Be Moved.");
				document.editLocForm.rd1.checked=true;
				return false;
			}
		}
		else
		{
			document.editLocForm.rd1.checked=true;
			return false;
		}	
	}
	if(document.editLocForm.rd1.checked==true)
	{
		var x=window.confirm("Please Confirm If You Really Wish To Change The Status Of This Location?" );
		if (x)
		{
		}
		else
		{
			document.editLocForm.rd2.checked=true;
			return false;
		}	
	}
	
  }     
 
 function callView(){
	document.getElementById("methodName").value="viewIntialized";
	document.editLocForm.submit();
		return true;
	}

	function validate(){
	if(trim(document.getElementById("locationDataName").value)==""){
		 alert('<bean:message bundle="view" key="LocationName_NotFound" />');
  		 document.getElementById("locationDataName").focus();
  		 return false;
    	}
     		 
      	 if(hasSpecial(document.getElementById("locationDataName"),'!@#$%^*()+=[]\\\';,./{}|\":<>?~&_')) {  
			alert('<bean:message bundle="view" key="Service.MSG_SERVICECLASS_CHARACTER" />');
			document.getElementById("locationDataName").focus();
			return false;
	 	 }
				
   		if(document.editLocForm.rd1.checked==true){
   			document.editLocForm.status.value=document.editLocForm.rd1.value;
   		}	
   		if(document.editLocForm.rd2.checked==true){
   			document.editLocForm.status.value=document.editLocForm.rd2.value;
   		}
   		
   		if(document.editLocForm.rd2.checked==true)
		{
			if(document.editLocForm.moveToLocation.value==-1)
			{
				alert("Please Select Location To Which The Sub-Locations And Business Users Of This Location Can Be Moved.");
				document.editLocForm.rd1.checked=true;
				return false;
			}
		}
   		return true;
}
	function callSubmit(){
		if(validate()){
		document.editLocForm.action="LocationData.do?methodName=saveEditLocation";
		document.editLocForm.locMasterIdHidden.value=document.editLocForm.locMstrId.value;
		return true;
		}
		else{
		return false;
		}
	 } 
		
		function checkKeyPressed(){
			if(window.event.keyCode=="13"){
			return callSubmit();
		}
	}
	function trim(sString) {
		while (sString.substring(0,1) == ' '){
		sString = sString.substring(1, sString.length);
		}		
		while (sString.substring(sString.length-1, sString.length) == ' '){
		sString = sString.substring(0,sString.length-1);	
		}
		return sString;
	}
	function hasSpecial(objInput,iChars)
 {
		//alert("satish");
		for (var i = 0; i < objInput.value.length; i++)
		{

			if (iChars.indexOf( objInput.value.charAt(i)) != -1)
			{
				//alert(objInput.value.charAt(i));
				//alert ("Your username has special characters. \nThese are not allowed.\n Please remove them and try again.");
				return (true);
			}
		}
	return (false);
 }
 
 function checkActiveFlag(){
 	var statusFlag ='<%=(String)session.getAttribute("statusFlag")%>';
 	if(statusFlag=="InActive")
 		document.getElementById("moveToLocation").disabled=true; 
	return true;
 }
</script>


<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onkeypress="return checkKeyPressed();" onload="return checkActiveFlag();">

<table cellspacing="1" cellpadding="0" border="0" width="100%"
	height="100%" bgcolor="#000000">
		<tr bgcolor="#FFFFFF">
		<td valign="top" width="100%" height="100%"><html:form
			name="editLocForm" type="com.ibm.dp.beans.LocationDataBean"
			action="/LocationData" method="post">
			
			<html:hidden property="methodName" />
			<html:hidden property="locMasterIdHidden"/>			
			<html:hidden name="LocationDataBean" property="circleCode" />
			<html:hidden name="LocationDataBean"  property="locDataId"/>
			<html:hidden name="LocationDataBean" property="oldLocationDataName"  />
			<html:hidden  name="LocationDataBean" property="status" />
			<html:hidden  name="LocationDataBean" property="oldStatus" />
           <table width="545" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<tr>
					<td colspan="2" class="text"><br>
					<img
						src="${pageContext.request.contextPath}/images/edit-location.gif"
						width="505" height="22"></td>
				</tr>
				<!-- Error message to be inserted here -->
					<html:errors/>
		</table>
		<table>
             <tr>
				<td class="text pLeft15" nowrap width="132"><strong><bean:message bundle="view"
					key="user.circleCode"/> :</strong> </td>
				<td width="174"><html:select name="LocationDataBean"
					property="circleCode" disabled="true">
					<html:option value="-1"><bean:message bundle="view"
					key="Select" /></html:option>
					<logic:notEmpty name="LocationDataBean" property="circleList">
						<bean:define id="circleList"
								name="LocationDataBean" property="circleList" />
						<html:options collection="circleList"
								labelProperty="circleName" property="circleId" />
					</logic:notEmpty>
				</html:select></td>
			</tr><tr></tr>
			<tr>
				<td class="text pLeft15" ><strong><bean:message bundle="view"
					key="LocationType" /> :</strong> </td>
				<td width="162">
				<html:select name="LocationDataBean"
					property="locMstrId" disabled="true">
				<html:option value="-1"><bean:message bundle="view" key="Select" />
				</html:option>
				<html:optionsCollection name="LocationDataBean"
					property="locationList" label="locationName" value="locMstrId"/>
				</html:select></td>
			</tr>
			<tr>
				<td class="text pLeft15" width="198">
				<font color="#4b0013"><strong><bean:message bundle="view"
					key="LocationName" /> :</strong> </font></td>
				<td ><font color="#003399"><html:text name="LocationDataBean"
					property="locationDataName" maxlength="75" disabled="false" size="19" styleClass="box" />&nbsp;</font></td>
			</tr>
			<tr>
				<td class="text pLeft15" ><strong><bean:message bundle="view"
					key="MoveSubLocations" /> :</strong> </td>
				<td width="162">
					<html:select name="LocationDataBean" property="moveToLocation">
						<html:option value="-1"><bean:message bundle="view" key="Select" /></html:option>
						<html:optionsCollection name="LocationDataBean"
						property="moveLocList" label="locationDataName" value="locDataId"/>
					</html:select>
				</td>
			</tr>	
			<tr>
				
				<td class="text pLeft15"><font color="#4b0013"><strong><bean:message bundle="view"
					key="serviceClass.Status" /> :</strong></font></td>
				<td><input type="radio" 
					id="rd1"
					<logic:equal name="LocationDataBean" property="status" value="Active">
              				  checked
                		</logic:equal>
					value="Active"
					name='<bean:write name="LocationDataBean" property="status"/>'
					onclick="return disable()">
				<bean:message bundle="view" key="CreateService.Enable" /> <input type="radio"
					id="rd2"
					<logic:equal name="LocationDataBean" property="status" value="InActive">
                			checked
                		</logic:equal>
					value="InActive"
					name='<bean:write name="LocationDataBean" property="status"/>'
					onclick="return disable()"> <bean:message bundle="view"
					key="CreateService.Disable" /></td>
			</tr>
			<!-- <tr>
				<td class="text pLeft15" nowrap="nowrap" colspan="2"><font color="#4b0013"><strong> Note <FONT COLOR="RED">*</FONT> : <bean:message
					bundle="view" key="Note_Location_inactive" /></strong></font> </td>
			</tr>-->
			<tr>
				<td class="text pLeft15"><font color="#4b0013">&nbsp;</font></td>
				<td>
				<table width="140" border="0" cellspacing="0" cellpadding="5">
					<tr align="center">
						
						
						<td width="70"><html:submit styleClass="submit"
							onclick="return callSubmit()">
							<bean:message bundle="view" key="Common.submit" />
						</html:submit></td>
						
						<td width="82"><input class="submit"
							type="button" value='<bean:message bundle="view" key="Common.back" />' 
							onclick="return callView()">
						</td>

					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">&nbsp;</td>
			</tr>
			</table>
		</html:form></td>
	</tr>
</table>
</body>
</html:html>
