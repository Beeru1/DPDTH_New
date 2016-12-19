<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html:html>
<HEAD>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<LINK href="<%=request.getContextPath()%>/theme/text.css" rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/theme/chromestyle2.css" />
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/chrome.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/JScriptLib.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>

<SCRIPT language="Javascript">
	function goBack(){
		document.getElementById("methodName").value="getCircleList";
	}
</SCRIPT>

</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">

<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">
	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include
			page="../common/dpheader.jsp" /></TD>
	</TR>
	<TR valign="top">
		<TD width="167" align="left" bgcolor="#b4d2e7" valign="top"
			height="100%"><jsp:include page="../common/leftHeader.jsp" /></TD>
		<TD valign="top" width="100%" height="100%"><html:form
			name="CircleAction" action="/CircleAction"
			type="com.ibm.virtualization.recharge.beans.CircleFormBean">
			<html:hidden property="methodName" styleId="methodName" />
			<html:hidden property="circleId" styleId="circleId"
				name="CircleFormBean" />
			<html:hidden property="isCircleAdmin" styleId="isCircleAdmin"
				name="CircleFormBean" />
			<html:hidden property="disabledRegion" styleId="disabledRegion"
				name="CircleFormBean" />
			<html:hidden property="page" styleId="page" name="CircleFormBean" />
			<TABLE width="545" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					 <img src="<%=request.getContextPath()%>/images/viewCircleDetails.gif" width="505" height="22"> </TD>
				</TR>
				<TR>
					<TD colspan="4" class="text"><FONT color="#ff0000" size="-2"><html:errors
						bundle="error" /><html:errors /></FONT></TD>
				</TR>
				
				<TR>
					<TD width="126" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.circlecode" />&nbsp;:</FONT></STRONG></TD>
					<TD width="155"><bean:write	name="CircleFormBean" property="circleCode" /></TD>

					
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.saletax" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155"><bean:write	name="CircleFormBean" property="saletax" /></TD>

				</TR>
				
				<TR>
					<TD width="120" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.circlename" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155"><bean:write	name="CircleFormBean" property="circleName" /></TD>

					
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.saletaxdate" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>

					<TD width="155"><bean:write	name="CircleFormBean" property="saletaxdate" /></TD>
				
				</TR>
				<TR> 
					<TD class="text"><STRONG><FONT color="#000000"><bean:message
						bundle="view" key="circle.regionid" /></FONT><FONT color="RED"></FONT>
					:</STRONG></TD>
					<TD width="163"> <logic:iterate
						id="region" name="regionList">
						<logic:equal name="CircleFormBean" property="regionId"
							value="${region.id}">
							<bean:write name="region" property="name" />
						</logic:equal>
					</logic:iterate> </TD>
					
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.service" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155"><bean:write	name="CircleFormBean" property="service" />	</TD>	
				</TR>
				<TR>
				<TD><STRONG><FONT color="#000000"><bean:message
						bundle="view" key="circle.status" /></FONT><FONT color="RED"></FONT> :</STRONG></TD>
					<TD width="171"><logic:equal
						value="A" name="CircleFormBean" property="status">
						<bean:message key="application.option.active" bundle="view" />
					</logic:equal> <logic:equal value="D" name="CircleFormBean" property="status">
						<bean:message key="application.option.inactive" bundle="view" />
					</logic:equal> </TD>
				<TD></TD>	
				<TD></TD>
					
				</TR>
				

	
				<TR>

					
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.compName" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155">
						<bean:write	name="CircleFormBean" property="compName" /></TD>
					
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.tin" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155">
						<bean:write	name="CircleFormBean" property="tin" />
					</TD>	

				</TR>
				<TR>
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.address1" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155"><bean:write	name="CircleFormBean" property="address1" /></TD>
					
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.tinDate" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155">
						<bean:write	name="CircleFormBean" property="tinDate" />
					</TD>	
				</TR>
				<TR>
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.address2" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155">
					<bean:write	name="CircleFormBean" property="address2" />
					</TD>
					
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.terms1" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155">
						<bean:write	name="CircleFormBean" property="terms1" />
					</TD>	
				</TR>
				<TR>
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.phone" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155"><bean:write	name="CircleFormBean" property="phone" /></TD>
					
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.terms2" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155"><bean:write	name="CircleFormBean" property="terms2" /></TD>	
				</TR>		
				<TR>
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.fax" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155"><bean:write	name="CircleFormBean" property="fax" /></TD>
					
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.terms3" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155"><bean:write	name="CircleFormBean" property="terms3" /></TD>	
				</TR>

				<TR>
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.cst" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155"><bean:write	name="CircleFormBean" property="cst" /></TD>
					
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.terms4" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155"><bean:write	name="CircleFormBean" property="terms4" /></TD>
				</TR>
				<TR>
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.cstdate" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155"><bean:write	name="CircleFormBean" property="cstdate" /></TD>
					
					<TD width="199" class="text pLeft15"><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.remarks" /><FONT color="RED"></FONT>:</FONT></STRONG></TD>
					<TD width="155"><bean:write	name="CircleFormBean" property="remarks" /></TD>
				</TR>
				
				<TR>
					<TD width="126" class="text pLeft15" nowrap><STRONG><FONT
						color="#000000"><bean:message bundle="view"
						key="circle.description" />:</FONT></STRONG></TD>
					<TD width="171" colspan="3"><bean:write	name="CircleFormBean" property="description" /></TD>
					<TD width="121" class="text" nowrap></TD>
					<TD width="171"><FONT color="#3C3C3C"> </FONT></TD>
				</TR>
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
								
				
				<TR>
					<TD colspan="4" class="text" c><font color="blue"><html:hidden property="page" styleId="page" /></font></TD>
				</TR>
				<TR>
					<TD class="text pLeft15"><FONT color="#000000">&nbsp;</FONT></TD>
					<TD colspan="2">
					<TABLE width="140" border="0" cellspacing="0" cellpadding="5">
						<TR align="center" valign="top">

							<TD><INPUT class="submit" type="submit" tabindex="10"
								name="BACK" value="Back" onclick="goBack()"></TD>
							<TD></TD>
						</TR>
					</TABLE>
					</TD>
				</TR>
				<TR>
					<TD colspan="4" align="center">&nbsp; <html:hidden
						property="startDate" name="CircleFormBean" /> <html:hidden
						property="endDate" name="CircleFormBean" /> <html:hidden
						property="status" name="CircleFormBean" /><html:hidden property="circleStatus" styleId="circleStatus" name="CircleFormBean"/>
					</TD>
				</TR>
			</TABLE>
		</html:form></TD>
	</TR>
	<TR align="center" bgcolor="4477bb" valign="top">
		<TD colspan="2" height="17" align="center"><jsp:include
			page="../common/footer.jsp" /></TD>
	</TR>
</TABLE>

</BODY>
</html:html>
