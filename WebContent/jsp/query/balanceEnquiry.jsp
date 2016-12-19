<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<script language="javascript">



	function setFunctionName(mName){
		document.getElementById("methodName").value=mName;
	}
	
	function show(){
		document.getElementById("newerror").style.display='none';
		document.getElementById("getChildBalance").style.display='';
		document.getElementById("divId").style.display='none';
		document.getElementById("methodName").value='getChildAccBalanceByMobileNumber';
	}
	function hide(){
		document.getElementById("newerror").style.display='none';
		document.getElementById("getChildBalance").style.display='none';
		document.getElementById("divId").style.display='none';
		document.getElementById("methodName").value='getBalance';
	}

	function check(){
		if(document.getElementById("child").checked==true){
			document.getElementById("self").checked=false;
			document.getElementById("getChildBalance").style.display='';
			document.getElementById("methodName").value='getChildAccBalanceByMobileNumber';
			document.forms[0].self.focus();
		}else{
			if(document.getElementById("child").checked==false){
				document.getElementById("self").checked=true;
				document.getElementById("getChildBalance").style.display='none';
				document.getElementById("methodName").value='getBalance';
				document.forms[0].child.focus();
			}
		}
	
	}


	function checkKeyPressed(){
		if(window.event.keyCode=="13"){
			document.forms[0].submit();
		}
	}

</script>
<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<TITLE></TITLE>


</head>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0"  onload="check()" >

<table cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">
	<tr>
		<td colspan="2" valign="top" width="100%"><jsp:include
			page="../common/dpheader.jsp" /></td>
	</tr>
	<tr valign="top">
		<td width="167" align="left" bgcolor="b4d2e7" valign="top"
			height="100%"><jsp:include page="../common/leftHeader.jsp" /></td>
		<td valign="top" width="100%" height="100%">
		<table width="700" border="0" class="text" align="left">
			<tr>
				<td width="680" valign="top"><html:form action="queryAction">
					<table border="0" cellspacing="0" cellpadding="0" class="text">
						<tr>
							<td width="521" valign="top">
							<table border="0" cellpadding="0" cellspacing="0" class="text">
								<tr>
									<td colspan="4" class="text pLeft10"><br>
									<img src="<%=request.getContextPath()%>/images/balEnquiry.gif"
										width="505" height="22"></td>
								</tr>
							
								<tr>
									<td colspan="4" class="pLeft10"><html:radio 
										 property="rdbSelect" onclick="hide()"  styleId="self" value="self"></html:radio><strong><bean:message bundle="view" key="query.self" /></strong>
									<span class="pLeft15"></span> <html:radio 
										 onclick="show()" styleId="child" value="child" property="rdbSelect"></html:radio><strong><bean:message bundle="view" key="query.child" /></strong></td>
									<td width="391" class="text pLeft15"><font color="#000000">&nbsp;</font></td>

								</tr>
								<tr>
									<td colspan="4" class="text pLeft10">
									<div id="getChildBalance" style="display:none">
									<table width="100%" align="center">
										<tr>
											<td width="150" class="text pLeft10" nowrap><font
												color="#000000"><strong><bean:message bundle="view" key="query.childmobileno" /><span
												class="orange8"><font
												color="#ff0000">* </font></span> :<br>(<bean:message key="application.digits.mobile_number" bundle="view" />)</br></strong></font></td>
											<td width="137"><font color="#003399"> <html:text property="childMobileNumber" styleClass="box" styleId="childMobileNumber"  onkeypress="isValidNumber()" size="19" maxlength="10" />
											</font></td>
											<td class="text">&nbsp;</td>
										</tr>
									</table>
									</div>
									</td>
								</tr>

								<tr>
									<td><html:hidden property="methodName"
										styleId="methodName" /></td>
									<td colspan="3"><html:submit tabindex="3" >
										<bean:message bundle='view' key='query.findbalance' />
									</html:submit></td>
									<td colspan="3">&nbsp;</td>
									<td colspan="3">&nbsp;</td>
								</tr>
								
								<tr><td colspan="4"><div id="divId" >
									<table border="1" cellspacing="0">
										<logic:notEmpty name="BALANCE_INFO">

											<tr align="center" class="mLeft5">
											<!-- <td width="24%" align="center" class="darkBlueBg height19"><span
													class="white10heading mLeft5 mTop5"><bean:message bundle="view" key="account.openingbalance"/>(<bean:message bundle="view" key="application.currency"/>)</span></td>-->	
												<td width="150" align="center" class="darkBlueBg height19"><span
													class="white10heading mLeft5 mTop5"><bean:message bundle="view" key="account.operatingbalance"/>(<bean:message bundle="view" key="application.currency"/>)</span></td> 
												
													<td width="150"  class="darkBlueBg height19"><span
													class="white10heading mLeft5 mTop5"><bean:message bundle="view" key="account.availablebalance"/>(<bean:message bundle="view" key="application.currency"/>)</span></td>
											</tr>
											<tr align="center" bgcolor="#FCE8E6" >
												
										<!--<td class=" height19"><span
													class="mLeft5 mTop5 mBot5 black10"><bean:write
													name="BALANCE_INFO" property="openingBalance" format="#0.00" /></span></td>-->	
												<td class=" height19"><span
													class="mLeft5 mTop5 mBot5 black10"><bean:write
													name="BALANCE_INFO" property="operatingBalance"
													format="#0.00" /></span></td>  	  
												<td class=" height19"><span
													class="mLeft5 mTop5 mBot5 black10"><bean:write
													name="BALANCE_INFO" property="availableBalance"
													format="#0.00" /></span></td>	
											</tr>
										</logic:notEmpty>
									</table></div>
									</td>
								</tr>
								<tr>
									<td colspan="4" align="center">
									<div id="newerror">
										<font color="red"> <strong><html:errors
											bundle="error" /></strong></font>
									</div>
									</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
				</html:form></td>
			</tr>

		</table>

<tr align="center" bgcolor="4477bb" valign="top">
	<td colspan="2" height="17" align="center"><jsp:include
		page="../common/footer.jsp" /></td>
</tr>

</body>
</html:html>
