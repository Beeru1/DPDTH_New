<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><bean:message bundle="view" key="grouprole.availablelist" /></title>

<script language="JavaScript" type="text/javascript">

function addOption(theSel, theText, theValue)
{
  var newOpt = new Option(theText, theValue);
  var selLength = theSel.length;
  theSel.options[selLength] = newOpt;
}

function deleteOption(theSel, theIndex)
{ 
  var selLength = theSel.length;
  if(selLength>0)
  {
    theSel.options[theIndex] = null;
  }
}

function moveOptions(theSelFrom, theSelTo)
{
  
  var selLength = theSelFrom.length;
  var selectedText = new Array();
  var selectedValues = new Array();
  var selectedCount = 0;
  
  var i;
  
  // Find the selected Options in reverse order
  // and delete them from the 'from' Select.
  for(i=selLength-1; i>=0; i--)
  {
    if(theSelFrom.options[i].selected)
    {
      selectedText[selectedCount] = theSelFrom.options[i].text;
      selectedValues[selectedCount] = theSelFrom.options[i].value;
      deleteOption(theSelFrom, i);
      selectedCount++;
    }
  }
  
  // Add the selected text/values in reverse order.
  // This will add the Options to the 'to' Select
  // in the same order as they were in the 'from' Select.
  for(i=selectedCount-1; i>=0; i--)
  {
    addOption(theSelTo, selectedText[i], selectedValues[i]);
  }
 
}

	function selectAll()
	{
		    for(i=0;i<document.forms[0].sel2.length;i++){
				document.forms[0].sel2.options[i].selected=true;
			}
		    document.getElementById("methodName").value="updateRoles";
	}
	
	// get list roles when there is change in channel type.
	function getlistRoles()
	{
		var gpId=document.getElementById("groupId");
		document.getElementById("groupName").value=gpId.options[gpId.selectedIndex].text;
		if (document.getElementById("groupId").value == "SELECT")
 		{
 			//alert("Please select one from group from the list");
 			alert('<bean:message bundle="error" key="errors.roles.mapping.group_list" />');
 			return ;
 		 }
 		else if (document.getElementById("channelType").value == "SELECT")
		{
			alert('<bean:message bundle="error" key="errors.roles.mapping.channel_type" />');
			return;
		}
		else
		{
			document.getElementById("channelType").value;
			document.getElementById("methodName").value ="listRoles";
			document.forms[0].action="GroupRoleAction.do?methodName="+document.getElementById("methodName").value;
			document.getElementById("showTable").value ="false";
			document.forms[0].submit();
		}
	}

//-->
</script>

</head>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">

<table cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">
	<tr>
		<td colspan="2" valign="top" width="100%"><jsp:include
			page="../common/dpheader.jsp" /></td>
	</tr>
	<tr valign="top">
		<td width="167" align="left" bgcolor="b4d2e7" valign="top"
			height="100%"><jsp:include page="../common/leftHeader.jsp" /></td>
		<td valign="top" width="100%" height="100%"><html:form action="/GroupRoleAction"
			type="com.ibm.virtualization.recharge.beans.GroupRoleMappingFormBean">


			<table width="400" border="0" cellpadding="5" cellspacing="0"
				class="text" align=left>
				<TR>
					<TD nowrap colspan="4"><img src="<%=request.getContextPath()%>/images/GroupRoleMapping.gif"
						width="505" height="22"><br>
					</TD>
				</TR>

				<tr>
					<td nowrap colspan="2"><b><bean:message bundle="view"
						key="grouprole.groupname" /></b><FONT COLOR="RED">*</FONT></td>
					<td nowrap colspan="2"><font color="#003399"> <html:select
						property="groupId" styleId="groupId" tabindex="1" styleClass="w130">
						<html:option value="SELECT">
							<bean:message bundle="view" key="application.option.select" />
						</html:option>
						<logic:notEmpty name="GroupRoleMappingFormBean"
							property="groupList">
							<html:optionsCollection name="GroupRoleMappingFormBean"
								property="groupList" label="groupName" value="groupId" />
						</logic:notEmpty>
					</html:select> </font></td>
				</tr>
				<tr>
				<tr>
					<TD colspan="2" width="20%"><b><bean:message bundle="view"
						key="transaction.channeltype" /></b><FONT COLOR="RED">*</FONT></td>
					<TD colspan="2"><html:select property="channelType"
						styleClass="w130" styleId="channelType" tabindex="2">
						<html:option value="SELECT">--Select--</html:option>
						<html:option value="ALL">
							<bean:message bundle="view" key="application.option.select_all" />
						</html:option>
						<html:option value="WEB">
							<bean:message bundle="view" key="sysconfig.channel_web" />
						</html:option>
	<%-- 					<html:option value="SMSC">
							<bean:message bundle="view" key="sysconfig.channel_smsc" />
						</html:option>
						<html:option value="SELFCARE">
							<bean:message bundle="view" key="sysconfig.channel_selfcare" />
						</html:option>
						<html:option value="USSD">
							<bean:message bundle="view" key="sysconfig.channel_ussd" />
						</html:option>
--%>
					</html:select>&nbsp;&nbsp;&nbsp;&nbsp;<input class="submit" type="button"
						value="submit" onClick="getlistRoles();" /></TD>
				</tr>
				<tr>
					<td colspan="4" width="20%"><font color="#ff0000" size="2"><b><html:errors
						bundle="error" property="errors.roleList.notfound" /><html:errors
						bundle="view" property="message.role.update_success" /> <html:errors
						property="errors.grouprole" bundle="error" /> </b></font></td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="4">
					<table width="400" border="0" cellpadding="5" cellspacing="0"
						class="text" align=left id="tableId" style="visibility:hidden">
						<TR>
							<TD colspan="2"><b><bean:message bundle="view"
								key="grouprole.availablelist" /></b></TD>
							<TD nowrap><b><bean:message bundle="view"
								key="grouprole.assignedlist" /></b></TD>
						</TR>
						<tr>
							<td width="300"><font color="#003399"><html:select
								property="roleId" name="GroupRoleMappingFormBean" size="12"
								style="width:215pt;width:215pt" styleId="sel1" styleClass="w130" multiple="true">
								<logic:notEmpty name="GroupRoleMappingFormBean"
									property="roleNotAssignedList">

									<html:optionsCollection name="GroupRoleMappingFormBean"
										property="roleNotAssignedList" label="roleName" value="roleId" />
								</logic:notEmpty>


							</html:select> </font>
							<td align="center" valign="middle"><input type="button"
								value="--&gt;"
								onclick="moveOptions(this.form.sel1, this.form.sel2);" /><br />
							<input type="button" value="&lt;--"
								onclick="moveOptions(this.form.sel2, this.form.sel1);" /></td>

							<td width="300"><font color="#003399"> <html:select
								property="rolesId" name="GroupRoleMappingFormBean" size="12"
								style="width:215pt;width:215pt" styleId="sel2" styleClass="w130" multiple="true">
								<logic:notEmpty name="GroupRoleMappingFormBean"
									property="roleAssignedList">
									<html:optionsCollection name="GroupRoleMappingFormBean"
										property="roleAssignedList" label="roleName" value="roleId" />
								</logic:notEmpty>
								<br>
							</html:select> &nbsp;&nbsp;&nbsp; </font></td>
						</tr>

						<TR>
							<TD colspan="4" align="center"><input class="submit"
								type="submit" tabindex="1" name="Submit" value="Submit"
								onclick="selectAll()"></TD>
						</TR>
						<tr>
							<td colspan="4"><html:hidden property="methodName"
								styleId="methodName" name="GroupRoleMappingFormBean" /> <html:hidden
								property="groupId" styleId="groupId"
								name="GroupRoleMappingFormBean" /> <html:hidden
								property="groupName" styleId="groupName"
								name="GroupRoleMappingFormBean" />
								</td>
						</tr>
					</table>

					</td>

				</tr>
				<tr>
					<td><html:hidden property="showTable" styleId="showTable"
						name="GroupRoleMappingFormBean" /></td>
				</tr>
			</table>
			</html:form>
			</td>
	</tr>

	<tr align="center" bgcolor="4477bb" valign="top">
		<td colspan="2" height="17" align="center"><jsp:include
			page="../common/footer.jsp" /></td>
	</tr>
</table>

<SCRIPT type="text/javascript">
<!--
	if((document.getElementById("showTable").value!="") && (document.getElementById("showTable").value=="true"))
	{
		document.getElementById("tableId").style.visibility="visible";
	}
-->

</SCRIPT>
</body>
</html>
