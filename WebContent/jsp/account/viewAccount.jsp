<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="<%=request.getContextPath()%>/theme/text.css" rel="stylesheet" type="text/css">
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>

<SCRIPT language="javascript" type="text/javascript"> 

	function goBack(){
	 document.getElementById("methodName").value="callSearchAccount";
	 document.forms[0].submit();
	}
	
	function checkKeyPressed(){
			if(window.event.keyCode=="13"){
			 		goBack();
			}
	}
		
	function checkOnLoad()
	{
		if(parseInt(document.getElementById("accountLevelId").value) >= 7)
		{
			document.getElementById("uniqueCode").style.display="inline";
		 	document.getElementById("employeeid").style.display="none";	
		}else{
			document.getElementById("uniqueCode").style.display="none";
		 	document.getElementById("employeeid").style.display="inline";
		}
	
	    if(parseInt(document.getElementById("accountLevelId").value) == 7)
		{
			document.getElementById("distLocatorBox").style.display="inline";	
		}else{
		 	document.getElementById("distLocatorBox").style.display="none";
		}
		
		
	}
	
</SCRIPT>

</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onkeypress="return checkKeyPressed();" onload="checkOnLoad();">


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
			action="/initCreateAccount">
			<TABLE border="0" cellspacing="0" cellpadding="0" class="text">
				<TR>
					<TD width="521" valign="top">
					<html:hidden property="page" styleId="page" />
					<TABLE width="545" border="0" cellpadding="5" cellspacing="0"
						class="text">
						<TR>
							<TD colspan="4" class="text"><BR>
							
							 <IMG src="<%=request.getContextPath()%>/images/viewAccountDetails.gif" width="505" height="22" alt="">  
							</TD>
						</TR>
						<TR>
							<TD width="120" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.accountCode" /></FONT> :</STRONG></TD>
							<TD width="155"><FONT color="#3C3C3C"> <bean:write
								name="DPCreateAccountForm" property="accountCode" /></FONT></TD>
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.accountName" /></FONT>:</STRONG></TD>
							<TD width="171"><FONT color="#3C3C3C"> <bean:write
								name="DPCreateAccountForm" property="accountName" /></FONT></TD>
						</TR>
						<TR>		
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.contactName" /></FONT>:</STRONG></TD>
							<TD width="171"><FONT color="#3C3C3C"> <bean:write
								name="DPCreateAccountForm" property="contactName" /></FONT></TD>	
							<TD width="121" class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.email" /></FONT> :</STRONG></TD>
							<TD width="171"><FONT color="#3C3C3C"> <bean:write
								name="DPCreateAccountForm" property="email" /> </FONT></TD>
							
						</TR>

						<TR>
							<TD width="120" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.simNumber" /></FONT> :</STRONG></TD>
							<TD width="155"><FONT color="#3C3C3C"> <bean:write
								name="DPCreateAccountForm" property="simNumber" /> </FONT></TD>
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.mobileNumber" /></FONT> :</STRONG></TD>
							<TD width="171"><FONT color="#3C3C3C"> <bean:write
								name="DPCreateAccountForm" property="mobileNumber" /> </FONT></TD>
						</TR>
						
						<TR>		
							<TD width="120" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.region" /></FONT> :</STRONG></TD>
							<TD><FONT color="#3C3C3C"> <bean:write
								name="DPCreateAccountForm" property="regionId" /> </FONT></TD>
							<TD width="120" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.circleId" /></FONT> :</STRONG></TD>
							<TD><bean:write name="DPCreateAccountForm" property="circleName" />
							</TD>	
						</TR>
						<TR>
						
							<TD width="120" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.zone" /></FONT>:</STRONG></TD>
							<TD width="155"><FONT color="#3C3C3C">
							<bean:write name="DPCreateAccountForm" property="accountZoneName" />
						  </FONT>
							</TD>
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.city" /></FONT>:</STRONG></TD>
							<TD width="155"><FONT color="#3C3C3C">
							<bean:write name="DPCreateAccountForm" property="accountCityName" />
								</FONT>
							</TD>		
						</TR>
					   <TR>
							<TD width="120" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.accountAddress" /></FONT> :</STRONG></TD>
							<TD width="155" ><FONT color="#3C3C3C"> <bean:write
								name="DPCreateAccountForm" property="accountAddress" /> </FONT></TD>
							<TD width="120" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.accountAddress2" /></FONT> :</STRONG></TD>
							<TD width="155" ><FONT color="#3C3C3C"> <bean:write
								name="DPCreateAccountForm" property="accountAddress2" /> </FONT></TD>
						</TR>
						<TR>
							<TD width="120" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.parentAccount" /></FONT> :</STRONG></TD>
							<TD width="163"><FONT color="#3C3C3C"> <bean:write
							name="DPCreateAccountForm" property="parentAccount" /> </FONT></TD>
							<TD width="120" class="text"><STRONG><FONT
								color="#000000"><div id="uniqueCode" style="display: none;">
								<bean:message bundle="view"
								key="account.Code" /></div>
								<div id="employeeid" style="display: none;">
								<bean:message key="account.EmployeeCode" bundle="view"/>
								</div>
								</FONT> :</STRONG></TD>
							<TD width="163"><FONT color="#3C3C3C"> <bean:write
							name="DPCreateAccountForm" property="code" /> </FONT></TD>
						</TR>
						<TR>
						   <TD width="120" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.pin" /></FONT> :</STRONG></TD>
							<TD width="163"><FONT color="#3C3C3C"> <bean:write
								name="DPCreateAccountForm" property="accountPinNumber"
								 /> </FONT></TD> 
						
							<!-- if user authorized to change or update the group then display the group -->
								<TD width="121" class="text"><STRONG><FONT
									color="#000000"><bean:message key="user.groupid"
									bundle="view" /></FONT> :</STRONG></TD>
								<TD width="163"><FONT color="#3C3C3C">
										<bean:write name="DPCreateAccountForm" property="groupName" />
								</FONT>
								</TD>
						</TR>
						<TR>
								<TD width="121" class="text pLeft15"><STRONG><FONT
									color="#000000"><bean:message bundle="view"
									key="account.accountlevel" /></FONT> :</STRONG></TD>
								<TD><bean:write name="DPCreateAccountForm" property="levelName" />
								</TD>
								<TD width="126" class="text"><STRONG><FONT
									color="#000000"><bean:message key="account.createddate"
									bundle="view" /></FONT>:</STRONG></TD>
								<TD width="163"> <bean:write name="DPCreateAccountForm"
									property="createdDt" formatKey="formatDate" bundle="view" /></TD>
							</TR>
							<tr>
							<TD width="120" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.lapu" /></FONT> :</STRONG></TD>
							<TD width="155"><FONT color="#3C3C3C"> <bean:write
								name="DPCreateAccountForm" property="lapuMobileNo" /> </FONT></TD>
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.lapu1" /></FONT> :</STRONG></TD>
							<TD width="171"><FONT color="#3C3C3C"> <bean:write
								name="DPCreateAccountForm" property="lapuMobileNo1" /> </FONT></TD>
						</tr>
						<tr>
							<TD width="120" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.FTA" /></FONT> :</STRONG></TD>
							<TD width="155"><FONT color="#3C3C3C"> <bean:write
								name="DPCreateAccountForm" property="FTAMobileNo" /> </FONT></TD>
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.FTA1" /></FONT> :</STRONG></TD>
							<TD width="171"><FONT color="#3C3C3C"> <bean:write
								name="DPCreateAccountForm" property="FTAMobileNo1" /> </FONT></TD>
						</tr>
						<tr>
							<TD width="120" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.trade" /></FONT> :</STRONG></TD>
							<TD width="155"><FONT color="#3C3C3C"> <bean:write
								name="DPCreateAccountForm" property="tradeName" /> </FONT></TD>
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.distcategory" /></FONT> :</STRONG></TD>
							<TD width="171"><FONT color="#3C3C3C"> <bean:write
								name="DPCreateAccountForm" property="tradeCategoryName" /> </FONT></TD>
						</tr>
						<TR>
							<TD width="126" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.category" /></FONT> :</STRONG></TD>
							<TD width="155"><FONT color="#3C3C3C"> <logic:equal
								value="G" name="DPCreateAccountForm" property="category">
								<bean:message key="account.category.option_gold" bundle="view" />
							</logic:equal> <logic:equal value="S" name="DPCreateAccountForm"
								property="category">
								<bean:message key="account.category.option_silver" bundle="view" />
							</logic:equal> <logic:equal value="P" name="DPCreateAccountForm"
								property="category">
								<bean:message key="account.category.option_platinum"
									bundle="view" />
							</logic:equal> <logic:equal value="0" name="DPCreateAccountForm"
								property="category">
								None
							</logic:equal>
							<logic:equal
								value="NA" name="DPCreateAccountForm" property="category">
								NA
							</logic:equal>
							 </FONT></TD>
							<TD width="120" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.status" /></FONT> :</STRONG></TD>
							<TD width="163"><FONT color="#3C3C3C"> <logic:equal
								value="A" name="DPCreateAccountForm" property="status">
								<bean:message key="application.option.active" bundle="view" />
							</logic:equal> <logic:equal value="I" name="DPCreateAccountForm" property="status">
								<bean:message key="application.option.inactive" bundle="view" />
							</logic:equal> </FONT></TD>

						</TR>
						<tr>
							<TD width="120" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.billablecode" /></FONT> :</STRONG></TD>
							<TD width="163"><FONT color="#3C3C3C"> <bean:write
							name="DPCreateAccountForm" property="billableCode" /> </FONT></TD>
							<TD width="120" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="beat.beatname" /></FONT> :</STRONG></TD>
							<TD width="163"><FONT color="#3C3C3C"> <bean:write
							name="DPCreateAccountForm" property="beatName" /> </FONT></TD>
						</tr>
						<tr>
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.alternateChannel"/></FONT> :</STRONG></TD>
							<TD width="176"><FONT color="#3C3C3C"> <bean:write
								name="DPCreateAccountForm" property="altChannelName" />
							</TD>
							<TD width="121" class="text"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.channelType" /></FONT> :</STRONG></TD>
							<TD width="176"><FONT color="#3C3C3C">  <bean:write
								name="DPCreateAccountForm" property="channelName" />
							</TD>
						</tr>
						<tr>
						<TD width="121" class="text"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="account.retailerType" /></FONT> :</STRONG></TD>
						<TD width="176"><FONT color="#3C3C3C">
							<bean:write name="DPCreateAccountForm" property="retailerTypeName" />
						</TD>
						<TD width="121" class="text"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="account.outletType" /></FONT> :</STRONG></TD>
						<TD width="176"><FONT color="#3C3C3C"> 
							<bean:write name="DPCreateAccountForm" property="outletName" />
						</TD>
						</tr>
						
						
					<TR>
						<TD class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="account.tinNo" /></FONT> :</STRONG></TD>
						<TD width="175"><FONT color="#3C3C3C"> <bean:write
							property="tinNo" name="DPCreateAccountForm"/>
						</FONT><FONT color="#ff0000" size="-2"></FONT></TD>
						
						<TD class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="account.tinDate" /></FONT> :</STRONG></TD>
						<TD width="175"><FONT color="#3C3C3C"> <bean:write
							property="tinDt" name="DPCreateAccountForm"/>
						</FONT><FONT color="#ff0000" size="-2"></FONT></TD>
						</TR>
						
						
						<TR>
						<TD class="text pLeft15"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="account.PAN" /></FONT> :</STRONG></TD>
						<TD width="175"><FONT color="#3C3C3C"> <bean:write
							property="panNo" name="DPCreateAccountForm"/>
						</FONT></TD>
						<TD width="121" class="text" nowrap><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="account.serviceTax" /></FONT> :</STRONG></TD>
						<TD width="176"><FONT color="#3C3C3C"> <bean:write
							property="serviceTax" name="DPCreateAccountForm"/> </FONT>
						</TD>
						</TR>
						
						<TR>
						<TD width="121" class="text" nowrap><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="account.CST" /></FONT> :</STRONG></TD>
						<TD width="176"><FONT color="#3C3C3C"> <bean:write
							property="cstNo" name="DPCreateAccountForm"/> </FONT>
						</TD>
						<TD width="121" class="text" nowrap><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="account.CSTDATE" /></FONT> :</STRONG></TD>
						<TD width="176"><FONT color="#3C3C3C"> <bean:write
							property="cstDt" name="DPCreateAccountForm"/> </FONT>
						</TD>
						</TR>
			<%-- 				<TD class="text pLeft15"><STRONG><FONT
								color="#000000"><bean:message bundle="view"
								key="account.ReportingEmail" /></FONT> :</STRONG></TD>
							<TD width="175"><FONT color="#3C3C3C"> <bean:write
								property="reportingEmail" name="DPCreateAccountForm"/>
							</FONT></TD>	--%>
					<TR>
							<TD class="text pLeft15">
							<FONT color="#000000"><strong><bean:message bundle="view" key="account.octroiCharge" /></strong> </FONT>
						</TD>
						<TD width="175"><FONT color="#3C3C3C">  
							<logic:equal value="0" property="octroiCharge" name="DPCreateAccountForm">
								<bean:message key="application.option.none" bundle="view"/>
							</logic:equal>
							<logic:equal value="MRP" property="octroiCharge" name="DPCreateAccountForm">
								<bean:message key="application.option.mrp" bundle="view"/>
							</logic:equal>
							<logic:equal value="COST" property="octroiCharge" name="DPCreateAccountForm">
								<bean:message key="application.option.CostPrice" bundle="view" />
							</logic:equal>
							<logic:equal value="OCTROI" property="octroiCharge" name="DPCreateAccountForm">
								<bean:message key="application.option.Octroi" bundle="view" />
							</logic:equal>
						</FONT>
							</TD>
						</TR>
						
						
						<tr id="distLocatorBox" style="display: none;">
							<TD width="121" class="text"><STRONG> 
							<FONT color="#000000"> 
							   <bean:message bundle="view"	key="account.distLocator" /></FONT>:</STRONG></TD>
							<TD width="176"><FONT color="#003399"> 
							<bean:write
							property="distLocator" name="DPCreateAccountForm"/> </FONT></TD>
							<TD width="121"></TD>
						    <TD width="176"></TD>								
						</tr>
						<tr>
						<TD width="121" class="text"><STRONG><FONT
							color="#000000"><bean:message bundle="view"
							key="account.whName" /></FONT> :</STRONG></TD>
						<TD width="176"><FONT color="#3C3C3C">
							<bean:write name="DPCreateAccountForm" property="accountWarehouseName" />
						</TD>
						</tr>
						
						<!-- 
						<tr>
							<TD width="121" class="text"><STRONG> <FONT
								color="#000000"> <bean:message bundle="view"
								key="account.swLocatorId" /></FONT>:</STRONG></TD>
							<TD width="176"><FONT color="#003399"> 
							<bean:write
							property="swLocatorId" name="DPCreateAccountForm"/> </FONT></TD>
							<TD width="121"></TD>
						    <TD width="176"></TD>								
						</tr>
						 -->
						
												
							<TR>
							<TD class="text pLeft15"><FONT color="#000000">&nbsp;</FONT></TD>
							<TD colspan="3">
							<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
								<TR align="center">
									<TD width="70"></TD>
									<TD><INPUT class="submit" type="submit" tabindex="13"
										name="BACK" value="Back" onclick="goBack()"></TD>
								</TR>
							</TABLE>
							</TD>
						</TR>
						<TR>
							<TD colspan="4" align="center">&nbsp;</TD>
							<html:hidden property="methodName" value="updateAccount" />
							<html:hidden property="accountId" />
							<html:hidden property="searchFieldName" styleId="searchFieldName" />
							<html:hidden property="circleId" styleId="circleId" />
							<html:hidden property="startDt" styleId="startDt" />
							<html:hidden property="endDt" styleId="endDt" />
							<html:hidden property="status" styleId="status" />
							<html:hidden styleId="listStatus" property="listStatus" />
							<html:hidden property="searchFieldValue"
								styleId="searchFieldValue" />
							<html:hidden property="accountStatus" styleId="accountStatus" />
							<html:hidden property="groupId" styleId="groupId" />
							<html:hidden property = "accessLevelId" styleId="accessLevelId"/>
							<html:hidden property="accountLevelId" styleId="accountLevelId"/>
						</TR>
					</TABLE>
					</TD>
				</TR>
				<TR>
					<TD><FONT color="RED"><STRONG> <html:errors
						bundle="view" property="message.account" /> <html:errors
						bundle="error" property="error.account" /> </STRONG></FONT></TD>
				</TR>

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