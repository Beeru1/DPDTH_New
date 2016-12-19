<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>
<head>
<title><bean:message bundle="view" key="application.title" /></title>
<% Integer pay=(Integer)request.getAttribute("PayoutCutoff"); %>
<% Integer distPay =(Integer)request.getAttribute("PayoutCutoffDist");%>
<SCRIPT language="javascript" type="text/javascript">
 	function checkIsPswdExpiring(){
		var res = document.getElementById("result").value;
		var pay='<%=pay%>';
		var payDist='<%=distPay%>';			
	 	if(res>0 && res!=""){
		 	var ans = confirm("Your password will expire in "+res+" days. Do you like to change your password now?");
		 	if(ans == true){
		 	window.location.href = "./jsp/authentication/changePassword.jsp";
	 	}
 	  }
 	 // alert(pay);
 	  if(pay!=null && Number(pay)>=0 && pay!=""){
	 	  var ans1=confirm("You have only "+pay+ " days left to upload partner payout file. Would you like to upload now?");
	 	  if(ans1==true)
	 	   window.location.href="./InvoiceUpload.do";
	 	  }
	 	 if(payDist!=null && Number(payDist) >=0 && payDist!=""){
	 	  	 var ans2=confirm("You have only "+payDist+ " days left to take Accept/Reject  Invoices. Would you like to do it now?");
	 	  	 if(ans2==true){
	 	  	 	window.location.href="./ViewListInvoice.do?methodName=listAllInvoicesDist";
	 	  	 	}
	 	  }
 	
	}
</SCRIPT>

</head>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="javascript:checkIsPswdExpiring()">
	
	<table cellspacing="0" cellpadding="0" border="0" width="100%" height="100%" valign="top">
		<tr>
			<td colspan="2" valign="top" width="100%"><jsp:include page="dpheader.jsp" /></td>
		</tr>
		<tr valign="top">
			<td width="167" align="left" bgcolor="b4d2e7" valign="top" height="100%"><jsp:include page="leftHeader.jsp" /></td>
			<td valign="top" width="100%" height="100%"></td>
	</tr>
	<tr align="center" bgcolor="4477bb" valign="top"> 
          <td colspan="2" height="17" align="center"><jsp:include page="../common/footer.jsp" /></td>
		</tr>
	</table>
<html:hidden property="result" styleId="result" name="AuthenticationFormBean"/>
</BODY>
</html:html>