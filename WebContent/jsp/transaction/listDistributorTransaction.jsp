<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<TITLE></TITLE>
<SCRIPT language="javascript" type="text/javascript"> 
	
    function setBackMethodName(){
    	document.getElementById("methodName").value="showSearchDistributorTransaction";
	}
	function getTransactionId(transactionId,status){
		document.getElementById("transactionId").value=transactionId;
		document.getElementById("transactionListType").value=status;
		document.getElementById("methodName").value="getdistributorTransactionReviewDetail"; 
	}

	// set  the action class method name which call when we click on submit button 
	function setFunctionName(){
		document.getElementById("methodName").value="getAllDistributorTransactionList";
	}

</SCRIPT>

</HEAD>

<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">

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
			action="DistributorTransaction" method="post" focus="Submit">
			<TABLE width="750" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/transactionList.gif" width="505" height="22"
						alt=""></TD>
				</TR>

			</TABLE>
			<TABLE width="99%" align="center" class="mLeft5">

				<TR align="left">
					<!-- <td width="70"><input class="submit" type="submit" name="Submit2" value="Back" onclick="setBackMethodName()"></td> -->
					<TD colspan="3" class="height19"><html:select
						property="transactionListType" styleId="transactionListType"
						styleClass="w130" tabindex="1">
						<html:option value="P">
							<bean:message bundle="view" key="transaction.pending" />
						</html:option>
						<html:option value="R">
							<bean:message bundle="view" key="transaction.reject" />
						</html:option>
						<html:option value="A">
							<bean:message bundle="view" key="transaction.approve" />
						</html:option>
					</html:select></TD>

					<TD><INPUT class="submit" type="submit" tabindex="2"
						name="Submit" value="Search" onclick="setFunctionName()"></TD>

				</TR>
				<TR align="center">


					<TD align="center" bgcolor="#cd0504"><SPAN
						class="white10heading mLeft5 mTop5"><bean:message
						bundle="view" key="transaction.list.srno" /></SPAN></TD>
					<TD align="center" bgcolor="#cd0504"><SPAN
						class="white10heading mLeft5 mTop5"><bean:message
						bundle="view" key="transaction.list.transactionid" /></SPAN></TD>
					<TD align="center" bgcolor="#cd0504"><SPAN
						class="white10heading mLeft5 mTop5"><bean:message
						bundle="view" key="account.accountCode" /></SPAN></TD>
					<TD align="center" bgcolor="#cd0504"><SPAN
						class="white10heading mLeft5 mTop5"><bean:message
						bundle="view" key="transaction.list.transactionamount" /></SPAN></TD>
					<TD align="center" bgcolor="#cd0504"><SPAN
						class="white10heading mLeft5 mTop5"><bean:message
						bundle="view" key="transaction.list.creditamount" /></SPAN></TD>
					<TD align="center" bgcolor="#cd0504"><SPAN
						class="white10heading mLeft5 mTop5"><bean:message
						bundle="view" key="transaction.list.transactiondate" /></SPAN></TD>
					<TD align="center" bgcolor="#cd0504"><SPAN
						class="white10heading mLeft5 mTop5"><bean:message
						bundle="view" key="transaction.list.chqcardno" /></SPAN></TD>
					<TD align="center" bgcolor="#cd0504"><SPAN
						class="white10heading mLeft5 mTop5"><bean:message
						bundle="view" key="transaction.ecsno" /></SPAN></TD>
					<TD align="center" bgcolor="#cd0504"><SPAN
						class="white10heading mLeft5 mTop5"><bean:message
						bundle="view" key="transaction.purchaseorderno" /></SPAN></TD>
					<TD align="center" bgcolor="#cd0504"><SPAN
						class="white10heading mLeft5 mTop5"><bean:message
						bundle="view" key="transaction.purchasedate" /></SPAN></TD>
					<TD align="center" bgcolor="#cd0504"><SPAN
						class="white10heading mLeft5 mTop5"><bean:message
						bundle="view" key="transaction.list.detail" /></SPAN></TD>

				</TR>
				<logic:notEmpty name="DistributorTransactionFormBean"
					property="transactionList">
					
					<logic:iterate id="transInfo" name="DistributorTransactionFormBean"
						property="transactionList" indexId="i">
						<TR align="center"
							bgcolor="#fce8e6">
							<TD class="height19" align="right"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><SPAN
								class="mTop5 mBot5 "><c:out value="${i+1}"></c:out></SPAN></SPAN></TD>
							<TD class="height19" align="center"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="transInfo" property="transactionId" /></SPAN></TD>
							<TD class="height19" align="left"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="transInfo" property="accountCode" /></SPAN></TD>
							<TD class="height19" align="right"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="transInfo" property="transactionAmount" /></SPAN></TD>
							<TD class="height19" align="right"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="transInfo" property="creditedAmount" /></SPAN></TD>
							<TD class="height19" width="19%" align="right"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="transInfo" property="transactionDate" /></SPAN></TD>
							<logic:notEqual name="transInfo" property="chqccNumber" value="0">
								<TD class="height19" align="right"><SPAN
									class="mLeft5 mTop5 mBot5 black10"><bean:write
									name="transInfo" property="chqccNumber" /></SPAN></TD>
							</logic:notEqual>
							<logic:equal name="transInfo" property="chqccNumber" value="0">
								<TD class="height19"><SPAN
									class="mLeft5 mTop5 mBot5 black10">--</SPAN></TD>
							</logic:equal>
							<TD class="height19" width="19%" align="center"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="transInfo" property="ecsno" /></SPAN></TD>
							<TD class="height19" width="19%" align="center"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="transInfo" property="purchaseOrderNo" /></SPAN></TD>
							<TD class="height19" width="19%" align="center"><SPAN
								class="mLeft5 mTop5 mBot5 black10"><bean:write
								name="transInfo" property="purchaseOrderDate" /></SPAN></TD>
							<TD class="height19"><html:submit property="accountid"
								styleId="accountid" value="Detail" styleClass="submit"
								onclick="getTransactionId('${transInfo.transactionId}','${transInfo.reviewStatus}')" /></TD>

						</TR>
					</logic:iterate>
					
				</logic:notEmpty>

				<html:hidden property="methodName" styleId="methodName" />
				<html:hidden property="transactionListType"
					styleId="transactionListType" />
				<html:hidden property="transactionId" styleId="transactionId" />

				<!-- TODO: bhanu,	sep	05,	2007 implement Pagination	  -->

				<TR>
					<TD colspan="4"><FONT color="red"><html:errors
						bundle="error" property="error.transaction" /> <html:errors
						bundle="view" property="message.transaction" /></FONT></TD>
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
