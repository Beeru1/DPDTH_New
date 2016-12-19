<%@page import="com.ibm.virtualization.ussdactivationweb.utils.Constants"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<html:html>

<script language="javascript">
  function moveOptions(theSelFrom, theSelTo){
  var selLength = theSelFrom.length;
  var selectedText, selectedValue ;
  var selectedCount = 0;
  
  var theSelTo = document.BizUserMove.sel2;
  var i;
    
  // Available list loop
  for(i=selLength-1; i>=0; i--)
  {
    if(theSelFrom.options[i].selected)
    {
      selectedText = theSelFrom.options[i].text;
      selectedValue = theSelFrom.options[i].value;
     	var duplicateValue = false;
     	
  		if(selLengthTo != 0){ 		
 			
 	  		// Assigned List loop
 	  		 var selLengthTo = theSelTo.length -1;
		  	for(var b=0;b <= selLengthTo ;b++){
		  		var val = theSelTo.options[b].value;	  			
	  			if(selectedValue == val){	  	
	  				duplicateValue = true;
		  			break;
	  			}
  		  	}
  		  }

	 	  if(duplicateValue == false) {
 				addOption(theSelTo, selectedText, selectedValue);
 				deleteOption(theSelFrom, i);
 		   }
 		}
	}
  }

  
 function getBaseLoc(flag){
 alert(document.BizUserMove.typeOfUserId1.text);
	if(document.BizUserMove.typeOfUserId1!=null){	
		if(document.BizUserMove.typeOfUserId1.value==-1){	
			alert("Please select Type of User.");
			document.BizUserMove.typeOfUserId1.focus();
			return false;
		}	
	}
 	
	var theSelTo= document.BizUserMove.sel2;
 	 var selLength = theSelTo.length;
	 var i;
  
  for(i=selLength-1; i>=0; i--){ 
	  theSelTo.options[i].selected = true;
    }

		document.BizUserMove.flag.value=flag;
	 	document.BizUserMove.action = "registrationOfAll.do?methodName=initBusinessUser";
		document.BizUserMove.submit();
	return true;
 }
 
 function submitBizUser(){
 	if(document.BizUserMove.typeOfUserId1.value==-1){	
		alert("Please select Type of User.");
		document.BizUserMove.typeOfUserId1.focus();
		return false;		
		}
 	if(document.BizUserMove.circleCodeFrom == null){
 		alert("Please select Circle");
 		return false;
 	}
 	if(document.BizUserMove.zoneList!= null){
	 	if(document.BizUserMove.zoneIdFrom== null || document.BizUserMove.zoneIdFrom.value=="-1"){
			alert("Please select ZoneFrom");
			return false;
		}
	}
 	if(document.BizUserMove.cityList!= null){
		if(document.BizUserMove.cityIdFrom== null || document.BizUserMove.cityIdFrom.value=="-1"){
			alert("Please select CityFrom");
			return false;
		}
	}
	
	if(document.BizUserMove.distList!=null){
	if(document.BizUserMove.distFrom== null || document.BizUserMove.distFrom.value=="-1"){
		alert("Please select DistributorFrom");
		return false;
	}
	} 
	/*
	if(document.BizUserMove.subUserFrom== null || document.BizUserMove.subUserFrom.value=="-1"){
		alert("Please select Business User");
		return false;
	}
	*/
 	var theSelTo= document.BizUserMove.sel2;
 	 var selLength = theSelTo.length;
	 var i;
  
  for(i=selLength-1; i>=0; i--){ 
	  theSelTo.options[i].selected = true;
    }
    if(selLength==0){
    	alert("Please select some values at Assigned List");
    	return false;
    }
 
	if(document.BizUserMove.circleCodeTo== null || document.BizUserMove.circleCodeTo.value =="-1"){
		alert("Please select Circle");
 		return false;
	}
	if(document.BizUserMove.zoneIdTo== null || document.BizUserMove.zoneIdTo.value=="-1"){
		alert("Please select ZoneTo");
		return false;
	}
	if(document.BizUserMove.cityIdTo== null || document.BizUserMove.cityIdTo.value=="-1"){
		alert("Please select CityTo");
		return false;
	}
	if(document.BizUserMove.zoneListTo!= null){
		if(document.BizUserMove.zoneIdTo== null || document.BizUserMove.zoneIdTo.value=="-1"){
			alert("Please select ZoneTo");
			return false;
		}
	}
	if(document.BizUserMove.cityListTo!= null){
		if(document.BizUserMove.cityIdTo== null || document.BizUserMove.cityIdTo.value=="-1"){
			alert("Please select CityTo");
			return false;
		}
	}
	/*
	if(document.BizUserMove.distListTo!= null){
		if(document.BizUserMove.distTo== null || document.BizUserMove.distTo.value=="-1"){
			alert("Please select DistributorTo");
			return false;
		}
	} */
	
	if(document.BizUserMove.subUserTo== null || document.BizUserMove.subUserTo.value=="-1"){
		alert("Please select Mapped Business User");
		return false;
	}
	/*
	if(document.BizUserMove.subUserTo== null || document.BizUserMove.subUserTo.value=="-1"){
		alert("Please select Mapped Business User");
		return false;
	} */
	
 	document.BizUserMove.action = "/CircleUSSD/registrationOfAll.do?methodName=bulkMovementOfBizUsers";
 	return true;
    }

   </script> 
<body>

<table height="100%" width="100%" cellpadding="0" cellspacing="1">
<td  valign="top">
<html:form action="/registrationOfAll" name="BizUserMove"
	type="com.ibm.virtualization.ussdactivationweb.beans.RegistrationOfAllBean" >
<html:hidden name="RegistrationOfAllBean" property="flag"/>

	<table border="0" width="80" height="100" cellpadding="3" class="text">
	 <tr>
			<td colspan="5"class="text">
					<img src="${pageContext.request.contextPath}/jsp/account/images/bulkMovementofBusinessusers.gif" width="505" height="22">
			</td>
		</tr>
		<tr>
					<td align="left" colspan="5"><font color="RED" size="2"><strong><html:errors /></strong></FONT></td>
		</tr>
		<tr>
			<td class="text pLeft10" nowrap="nowrap"><strong><bean:message key="SelectedTypeOfUser" bundle="ftaProperties"/>
				<FONT COLOR="RED">*</FONT> :</strong></td>
			<td width="193"><font color="#003399"><html:select styleClass="w70"
				name="RegistrationOfAllBean" property="typeOfUserId1"  onkeypress="entsub(this.form)" onchange="return getBaseLoc('user');">
			<html:option value="-1"><bean:message key="Select" bundle="ftaProperties"/></html:option>
				<html:optionsCollection name="RegistrationOfAllBean" property="typeOfUserList" label="levelName" value="levelId" />
			</html:select></font></td>	
			<td width="66"></td><td width="223"></td>	<td width="88"></td><td width="201"></td>							
		</tr>
		<tr>
		<td class="text pLeft10"><strong> Select From</strong></td> <td width="193"></td><td width="66"></td><td width="223"></td><td width="88"></td>
		</tr>
		<tr>
		
		<logic:present name="RegistrationOfAllBean" property="circleList">
		
			<td colspan="0" class="text pLeft10" ><strong>Circle<FONT COLOR="RED">*</FONT>:</strong></td>
			<td width="193"><font color="#003399"> <html:select styleClass="w70"
						name="RegistrationOfAllBean" property="circleCodeFrom" tabindex="1" onkeypress="entsub1(this.form)" onchange="return getBaseLoc('circle1');">
						<html:option value="-1"><bean:message key="Select" bundle="ftaProperties"/></html:option>
						<html:optionsCollection name="RegistrationOfAllBean"
										property="circleList" label="value" value="label"/>
						</html:select> </font>
					</td>
		</logic:present>
		   <td class="text pLeft10" nowrap="nowrap" width="66"><strong>Zone<FONT COLOR="RED">*</FONT>:</strong></td>
			 <td width="223">
				<html:select styleClass="w70" name="RegistrationOfAllBean" property="zoneIdFrom" onchange="return getBaseLoc('zone1');">
				<html:option value="-1"><bean:message key="Select" bundle="ftaProperties"/></html:option>
				<logic:notEmpty name="RegistrationOfAllBean" property="zoneList">
					<html:optionsCollection name="RegistrationOfAllBean" property="zoneList" label="locationDataName" value="locDataId" />
				</logic:notEmpty>
				</html:select>
			</td>
		<td class="text pLeft10" nowrap="nowrap" width="88"><strong>City<FONT COLOR="RED">*</FONT>:</strong> </td>
			 <td width="201">
				<html:select styleClass="w70" name="RegistrationOfAllBean" property="cityIdFrom" onchange="return getBaseLoc('city1');">
				<html:option value="-1"><bean:message key="Select" bundle="ftaProperties"/></html:option>
				<logic:notEmpty name="RegistrationOfAllBean" property="cityList">
					<html:optionsCollection name="RegistrationOfAllBean" property="cityList" label="locationDataName" value="locDataId" />
				</logic:notEmpty>
				</html:select>
			 </td>
	<tr>
		<td class="text pLeft10" nowrap="nowrap"><strong>List Of Distributors<FONT COLOR="RED">*</FONT>:</strong> </td>
			 <td colspan="4">
				<html:select name="RegistrationOfAllBean" property="distFrom" onchange="return getBaseLoc('dist1');">
				<html:option value="-1"><bean:message key="Select" bundle="ftaProperties"/></html:option>
				<logic:notEmpty name="RegistrationOfAllBean" property="distList">
					<html:optionsCollection name="RegistrationOfAllBean" property="distList" label="businessUserName" value="bussinessUserId"  />
				</logic:notEmpty>	
				</html:select>
			 </td><td width="201"></td><td></td><td></td>				
	</tr>	

		<tr>
		<%--<logic:present name="RegistrationOfAllBean" property="subUserList">--%>
		<td class = "text pLeft10" nowrap="nowrap" ><strong>Parent Business User From<FONT COLOR="RED">*</FONT> :</strong></td>
				<td colspan="4">
					<html:select  name="RegistrationOfAllBean" property="subUserFrom" onchange="return getBaseLoc('subBiz1');">
					<html:option value="-1"><bean:message key="Select" bundle="ftaProperties"/></html:option>
				<logic:notEmpty name="RegistrationOfAllBean" property="subUserList">
					<html:optionsCollection name="RegistrationOfAllBean" property="subUserList" label="businessUserName" value="bussinessUserId" />
				</logic:notEmpty>	
					</html:select>
				</td><td width="201"></td>
	<%--	</logic:present>--%>
			</tr>	
			
			<tr>
			<td class="text pLeft10"><strong>Insert MSISDN No.</strong><br> <bean:message key="TextBoxSize" bundle="ftaProperties"/></td>
					<td colspan="3"><font color="#003399"><html:textarea
						name="RegistrationOfAllBean" property="msisdnNo" rows="3" cols="65"
						styleClass="box" onkeypress="return entsub(this.form)" /> </font>
			
					<input class="submit"
								type="submit" tabindex="1" name="Submit" value="Find"
								onclick="return findBizUser('msisdnNo');" onkeypress="return entsub(this.form)"></TD>
		<td width="88"></td><td width="201"></td>
		</tr>
	
		<tr><td>
		<logic:equal name="RegistrationOfAllBean" property="typeOfUserId1" value='<%= String.valueOf(Constants.FOS) %>'>
				<bean:message key="FOS_LIST" bundle="ftaProperties"/>
		</logic:equal>
		<logic:equal name="RegistrationOfAllBean" property="typeOfUserId1" value='<%= String.valueOf(Constants.DISTIBUTOR) %>'>
				<bean:message key="DIST_LIST" bundle="ftaProperties"/>
		</logic:equal>
		<logic:equal name="RegistrationOfAllBean" property="typeOfUserId1" value='<%= String.valueOf(Constants.TM) %>'>
				<bean:message key="TM_LIST" bundle="ftaProperties"/>
		</logic:equal>
		
			
		</td></tr>
		<tr>
		
		<!-- Inserting Table -->
			<td colspan="5">
				<table  border="0" cellpadding="2" cellspacing="0"
						class="text" align="left" id="tableId" >
						<TR>
							<TD colspan="2" align="center"><b><bean:message 
								key="businessUserMov.availablelist" bundle="ftaProperties"/></b></TD>
							<TD  align="center" ><b><bean:message 
								key="businessUserMov.assignedlist" bundle="ftaProperties"/></b></TD>
						</TR>
						<tr>
							<td ><font color="#003399"><html:select 
								property="bizUserId" name="RegistrationOfAllBean" size="12"
								style="width:215pt;width:215pt" styleId="sel1" styleClass="w130" multiple="true">
								<logic:notEmpty name="RegistrationOfAllBean"
									property="availableList">
									<html:optionsCollection name="RegistrationOfAllBean"
										property="availableList" label="businessUserName" value="bussinessUserId" />
								</logic:notEmpty>


							</html:select> </font>
							<td align="center" valign="middle"><input type="button"
								value="--&gt;"
								onclick="moveOptions(this.form.sel1, this.form.sel2);" /><br />
							<input type="button" value="&lt;--"
								onclick="moveOptionTo(this.form.sel2, this.form.sel1);" /><br>
							<input type="button" value="--&gt;&gt;"
								onclick="moveAllOption(this.form.sel1, this.form.sel2);" /></td>
				
							<td width="300"><font color="#003399"> <html:select
								property="bizUserId2" name="RegistrationOfAllBean" size="12"
								style="width:215pt;width:215pt" styleId="sel2" styleClass="w130" multiple="true">
									<logic:notEmpty name="RegistrationOfAllBean"
									property="bizUserAssignedList">
							<html:optionsCollection name="RegistrationOfAllBean"
										property="bizUserAssignedList" label="businessUserName" value="bussinessUserId" />
								</logic:notEmpty>
																<br>
							</html:select> &nbsp;&nbsp;&nbsp; </font></td>
							
						</tr>

						<TR>
							<TD colspan="2" align="center"><input class="submit"
								type="submit" tabindex="1" name="Submit" value="Submit"
								onclick="return submitBizUser();"></TD>
							<TD colspan="4" align="center"><input class="submit"
								type="submit" tabindex="1" name="ResetButton" value="Clear"
								onclick="return clearList();"></TD>
						</TR>
						
					</table>			
					
			</td>
		</tr>
		<tr>
		<td  class="text pLeft10"><strong>Select To</strong></td>
		</tr>
		
		<tr>
		<logic:present name="RegistrationOfAllBean" property="circleList">
			<td  class="text pLeft10"><strong>Circle<FONT COLOR="RED">*</FONT> :</strong></td>
			<td width="193"><font color="#003399"> <html:select styleClass="w70"
						name="RegistrationOfAllBean" property="circleCodeTo" tabindex="1" onkeypress="entsub1(this.form)" onchange="return getBaseLoc('circle2');">
						<html:option value="-1"><bean:message key="Select" bundle="ftaProperties"/></html:option>
						<html:optionsCollection name="RegistrationOfAllBean"
						property="circleList" label="value" value="label"/>
						</html:select> </font>
					</td>
		</logic:present>
			<logic:present name="RegistrationOfAllBean" property="zoneListTo">
				<td  class="text pLeft10" nowrap="nowrap" width="66"><strong>Zone<FONT COLOR="RED">*</FONT>:</strong></td>
				<td width="223">
					<html:select styleClass="w70" name="RegistrationOfAllBean" property="zoneIdTo" onchange="return getBaseLoc('zone2');">
					<html:option value="-1"><bean:message key="Select" bundle="ftaProperties"/></html:option>
					<html:optionsCollection name="RegistrationOfAllBean" property="zoneListTo" label="locationDataName" value="locDataId" />
					</html:select>
				</td>
			</logic:present>
				<logic:present name="RegistrationOfAllBean" property="cityListTo">
				<td  class="text pLeft10" nowrap="nowrap" width="88"><strong>City<FONT COLOR="RED">*</FONT>:</strong></td>
				<td width="201">
					<html:select styleClass="w70" name="RegistrationOfAllBean" property="cityIdTo" onchange="return getBaseLoc('city2');">
					<html:option value="-1"><bean:message key="Select" bundle="ftaProperties"/></html:option>
					<html:optionsCollection name="RegistrationOfAllBean" property="cityListTo" label="locationDataName" value="locDataId" />
					</html:select>
				</td>	
			</logic:present>
			</tr>
			<tr>
			<logic:present name="RegistrationOfAllBean" property="distListTo">
		<td class="text pLeft10" nowrap="nowrap"><strong>List Of Distributors<FONT COLOR="RED">*</FONT>:</strong> </td>
			 <td colspan="4">
				<html:select styleClass="w130" name="RegistrationOfAllBean" property="distTo" onchange="return getBaseLoc('dist2');">
				<html:option value="-1"><bean:message key="Select" bundle="ftaProperties"/></html:option>
				<html:optionsCollection name="RegistrationOfAllBean" property="distListTo" label="businessUserName" value="bussinessUserId"  />
				</html:select>
			 </td>
			 <td width="201"></td><td></td>
	</logic:present>
			</tr>
			<tr>
			<logic:present  name="RegistrationOfAllBean" property="subUserListTo">
			<td  class="text pLeft10" nowrap="nowrap" width="130"><strong>Mapped To Business User<FONT COLOR="RED">*</FONT>:</strong></td>
				<td colspan="4">
					<html:select styleClass="w130" name="RegistrationOfAllBean" property="subUserTo">
					<html:option value="-1"><bean:message key="Select" bundle="ftaProperties"/></html:option>
					<html:optionsCollection name="RegistrationOfAllBean" property="subUserListTo" label="businessUserName" value="bussinessUserId" />
					</html:select>
				</td>
			 <td width="201"></td><td></td>
			</logic:present>
		</tr>
		
			
				</table>
												
	</html:form>
</table>
</body>

<script>

</script>
</html:html>