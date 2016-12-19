
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">	
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<%
	String isSUK=null;
	String isRC=null;
	String isAV=null;
	String isEasyRecharge=null;
	isSUK=(String)request.getAttribute("suk");
	isRC=(String)request.getAttribute("rc");
	isAV=(String)request.getAttribute("ActivationVoucher");
	isEasyRecharge=(String)request.getAttribute("easyRecharge");
		
	
%>
<SCRIPT language="javascript" type="text/javascript"> 

	function submitData(nextPage,categorycode,circleId)
	{
		document.forms[0].action="DPViewProductAction.do?methodName=Selectdata&page="	+ nextPage + "&categorycode=" + categorycode+"&circleId="+circleId;
		document.getElementById("page").value="";
		document.forms[0].method="post";
		document.forms[0].submit();
	}

	function Show()
	{
		var tbl = document.getElementById('businessCategory').value;
		document.getElementById("categorycode").value = tbl;
	}

	function validate()
	{
		if(document.getElementById("businessCategory").value=="0"){
			alert('<bean:message bundle="error" key="error.product.selectedSub" />');
			document.getElementById("businessCategory").focus();
			return false;			
		}
		var tbl = document.getElementById('businessCategory').value;
		document.getElementById("categorycode").value = tbl;
		
		document.forms[0].action="DPViewProductAction.do?methodName=Selectdata";
		document.getElementById("page").value="";
		document.forms[0].submit();
	}
	
	
	function generateExcel()
	{
		
		if(document.getElementById("businessCategory").value=="0"){
			alert('<bean:message bundle="error" key="error.product.selectedSub" />');
			document.getElementById("businessCategory").focus();
			return false;			
		}
		var tbl = document.getElementById('businessCategory').value;
		document.forms[0].action="DPViewProductAction.do?methodName=exportExcel";
		document.getElementById("page").value="";
		document.forms[0].submit();
	}
	

	function formEdit(obj){

		document.forms[0].action="./DPEditProductAction.do?methodName=edit";
		document.getElementById("prodId").value=obj;
		document.forms[0].submit();
	}
	function focusTab(){
document.getElementById("businessCategory").focus();
}
</script>
</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="focusTab();">
<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>
   <TABLE cellspacing="0" cellpadding="0" border="0" width="100%" height="100%" valign="top" >
	<html:form action="DPCreateProductAction">
	<TR><TD colspan="2" valign="top" width="100%"><jsp:include page="../common/dpheader.jsp" flush="" /></TD></TR>
	<TR valign="top">
		<TD  align="left" bgcolor="#FFFFFF" background="<%=request.getContextPath()%>/images/bg_main.gif" valign="top"
			height="100%"><jsp:include page="../common/leftHeader.jsp"	flush="" /></TD>
		<td>	
			<table><tr><TD colspan="4" class="text"><BR>	<IMG src="<%=request.getContextPath()%>/images/searchProduct.gif" width="505" height="22" alt=""></TD></tr></table>
	
			<logic:messagesPresent message="true">
				<html:messages id="msg" property="MESSAGE_SENT_SUCCESS" bundle="view" message="true">
					<font color="red" size="2"><strong><bean:write name="msg"/></strong></font>
				</html:messages>
				<html:messages id="msg" property="ERROR_OCCURED" bundle="view" message="true">
					<font color="red" size="2"><strong><bean:write name="msg"/></strong></font>
				</html:messages>
			</logic:messagesPresent>
			<TABLE border="0" align="Center" class="border" width="423">
			<TBODY>	<TR>			</TR>
			<TR>
				<TD align="center"><b class="text pLeft15" >BUSINESS CATEGORY</b>
				<html:select styleId="businessCategory" property="businessCategory"	onchange="Show();" name="DPViewProductFormBean" style="width:150px" style="height:20px">
					<html:option value="0">- Select A Category -</html:option>
					<logic:notEmpty name="DPViewProductFormBean" property="arrBCList">
						<html:optionsCollection name="DPViewProductFormBean" property="arrBCList"/>
					</logic:notEmpty>
				</html:select><br><br></TD>
			<TD align="center" height="41"><bean:define id="sub" name="DPViewProductFormBean" property="subList" />	</TD>
			</TR>
			<TR><td colspan="1" align="center" width="754">	<html:button property="btn" value="Search" onclick="return validate();" />
				<html:button property="btn" value="Export To Excel" onclick="return generateExcel();" />
				</td>
			</TR>
	</TABLE>
	
	<TABLE id="hndset" >
		<tr><td><html:hidden property="prodId"  name="DPViewProductFormBean"/></td></tr>
	</TABLE>

	<TABLE id="sukservice">
	<%
		if(isSUK !=null  && isSUK.equalsIgnoreCase("suk")){%>
			<TR id="sukheader">
				<TD valign="top" height="100%" align="center">
			<font color='red'>	<b>CPE SERVICES </b> </font></TD>
			</TR>
			<%} if(isRC !=null  && isRC.equalsIgnoreCase("rc")){ %>
			<TR id="rcheader">
				<TD valign="top" height="100%" align="center">
				<font color = "red">
				<b>PAPER RECHARGE SERVICES </b> </font></TD>
			</TR>
			<%}if(isEasyRecharge !=null  && isEasyRecharge.equalsIgnoreCase("easyRecharge")){ %>
			<TR id="easyheader">
				<TD valign="top" height="100%" align="center">
				<font color = "red">
				<b>EASY RECHARGE SERVICES </b> </font></TD>
			</TR>
			<%} if(isAV !=null  && isAV.equalsIgnoreCase("ActivationVoucher")){ %>
			<TR id="avheader">
				<TD valign="top" height="100%" align="center">
				<font color = "red">
				<b>ACTIVATION VOUCHER </b> </font></TD>
			</TR>
			<%} %>
			</TABLE>


			<br>
			<br>
			<table id="sukservice">
				<%if(isRC!=null || isSUK !=null || isEasyRecharge !=null||isAV !=null ){%>
				<tr  bgcolor="#CD0504">
                    <td align="center"><b><FONT color="white"><bean:message bundle="view"
						key="productview.productid" /></FONT></b></td>
					<td align="center"><b><FONT color="white"><bean:message bundle="view"
						key="productview.productname" /></FONT></b></td>
					<td align="center"><b><FONT color="white"><bean:message bundle="view"
						key="productview.cardgroup" /></FONT></b></td>
					<td align="center"><b><FONT color="white"><bean:message bundle="view"
						key="productview.packtype" /></FONT></b></td>
					<td align="center"><b><FONT color="white"><bean:message bundle="view"
						key="productview.simcardcost" /></FONT></b></td>
					<td align="center" width="143"><b><FONT color="white"><bean:message bundle="view"
						key="productview.simmrp" /></FONT></b></td>
					<td align="center" width="143"><b><FONT color="white"><bean:message bundle="view"
						key="productview.circlename" /></FONT></b></td>
					<td align="center" width="143"><b><FONT color="white">Edit</FONT></b></td>
				</tr>
				<logic:notEmpty name="DPViewProductFormBean" property="productList">
					<logic:iterate name="DPViewProductFormBean" id="report" indexId="m"
						property="productList">
						<tr bgcolor="<%=((m.intValue()+1) % 2==0 ? rowDark : rowLight) %>">
						<td width="70"><bean:write name="report"
								property="productid" /></td>
							<td width="70"><bean:write name="report"
								property="productname" /></td>
							<td width="196"><bean:write name="report"
								property="cardgroup" /></td>
							<td width="252"><bean:write name="report"
								property="packtype" /></td>
							<td width="252"><bean:write name="report"
								property="simcardcost" /></td>
							<td width="143"><bean:write name="report" property="mrp" /></td>
							<td width="143"><bean:write name="report" property="circlename" /></td>
							<td class="txt" align="center"><a
								href="javascript: formEdit('<bean:write name="report" property="productid" />');"> <font color="red">Edit</font></a></td>
						</tr>
					</logic:iterate>

				</logic:notEmpty>
				<%} %>
				<logic:notEmpty property="productList" name="DPViewProductFormBean">
			<TR align="center">
			<TD align="center" bgcolor="#daeefc" colspan="15"><FONT
				face="verdana" size="2"><c:if test="${PAGES!=''}">
				<c:if test="${PAGES>1}">
					    	Page:
					<c:if test="${PRE>=1}">
						<a href="#"
							onclick="submitData('<c:out value='${PRE}'/>','<c:out value='${categorycode}'/>','<c:out value='${circleId}'/>');"><
						Prev</a>
					</c:if>
					<c:if test="${PAGES>LinksPerPage}">
						<c:set var="start_page" value="1" scope="request" />
						<c:if test="${SELECTED_PAGE+1>LinksPerPage}">
							<c:set var="start_page"
								value="${SELECTED_PAGE-(LinksPerPage/2)}" scope="request" />
						</c:if>
						<c:if test="${SELECTED_PAGE+(LinksPerPage/2)-1>=PAGES}">
							<c:set var="start_page" value="${PAGES-LinksPerPage+1}"
								scope="request" />
						</c:if>
						<c:set var="end_page" value="${start_page+LinksPerPage-1}"
							scope="request" />
						<c:forEach var="item" step="1" begin="${start_page}"
							end="${end_page}">
							<c:choose>
								<c:when test="${item==(NEXT-1)}">
									<c:out value="${item}"></c:out>
								</c:when>
								<c:otherwise>
									<a href="#"
										onclick="submitData('<c:out value='${item}'/>','<c:out value='${categorycode}'/>','<c:out value='${circleId}'/>');"><c:out
										value="${item}" /></a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:if>
					<c:if test="${PAGES<=LinksPerPage}">
						<c:forEach var="item" step="1" begin="1" end="${PAGES}">
							<c:choose>
								<c:when test="${item==(NEXT-1)}">
									<c:out value="${item}"></c:out>
								</c:when>
								<c:otherwise>
									<a href="#" onclick="submitData('<c:out value='${item}'/>','<c:out value='${categorycode}'/>','<c:out value='${circleId}'/>');"><c:out value="${item}" /></a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:if>
					<c:if test="${NEXT<=PAGES}">
						<a href="#"
							onclick="submitData('<c:out value='${NEXT}'/>','<c:out value='${categorycode}'/>','<c:out value='${circleId}'/>');">Next></a>
					</c:if>
				</c:if>
			</c:if> </FONT></TD>
		</TR>
		</logic:notEmpty>
			</table>
			</td>
			</TR>
				<html:hidden property="page" styleId="page" />
				<html:hidden property="circleId" styleId="circleId" name="DPViewProductFormBean"/>
				<html:hidden property="categorycode" styleId="categorycode" />
		</html:form>
		<tr>
			<td colspan="4">
				<jsp:include page="../common/footer.jsp" flush="" />
			</td>	
		</tr>
	</TABLE>
</BODY>
</html:html>