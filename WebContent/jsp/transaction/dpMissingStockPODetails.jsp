
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html:html>

<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="theme/text.css" type="text/css">

<TITLE> </TITLE>
<SCRIPT language="JavaScript" type="text/javascript">

var propertyName;


function paginationStep(nextPage,circleId){
	// alert("nextPage->"+nextPage);
	// alert("circleId->"+circleId);
	document.forms[0].action="missingStockApproval.do?methodName=init&page="+nextPage;
	//+ "&circleId="+ circleId ;
	document.forms[0].method="post";
	document.forms[0].submit();
	}




function clickSimilar(val){
	var checkLength = document.forms[0].check1.length;
	
	if(checkLength!=undefined){
		 if(val.checked==true){
		for(var i =0; i < checkLength ; i++)
		{	
			if(document.forms[0].check1[i].value==val.value){
			 	document.forms[0].check1[i].checked=true; 
			 }	
		}	
	}
	else if(val.checked==false){
		for(var i =0; i < checkLength ; i++)
		{
			if(document.forms[0].check1[i].value==val.value){
				document.forms[0].check1[i].checked=false; 
			}
		}	
	}
	}
	}
	
function showSTBDetail(dcNo,po_no)	
{		

var url = "missingStockApproval.do?methodName=getGridData&dcNo="+dcNo+ "&poNo="+po_no;
document.forms[0].action=url;
document.forms[0].method="post";
document.forms[0].submit();

		//window.open(url,"STB","width=900,height=500,scrollbars=yes,toolbar=no");
	//document.forms[0].action="missingStockApproval.do?methodName=getGridData&dcNo="+dcNo;
	//document.forms[0].method="post";
	
	//document.forms[0].submit();	
	
}

function checkDCStatus()
{
	<%
	String poNo1 = "";
	String childStatus = "";
	try
	{

		if(request.getAttribute("childStatus")!=null)
			childStatus = (String)request.getAttribute("childStatus");
		
		if(request.getAttribute("poNo1")!=null)
			poNo1 = (String)request.getAttribute("poNo1");

	%>
	
	var chStatus = '<%=childStatus%>';
	var poNo1 = '<%=poNo1%>';
	<%
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	%>
	alert(chStatus);
	if(chStatus=="Success")
	{
		alert(chStatus);
		document.getElementById("hiddenChildStatus").style.display="block";
	}
}
</SCRIPT>
</HEAD>

<BODY BACKGROUND="images/bg_main.gif">

<html:form action="/missingStockApproval"> 

<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>
<br>
	<IMG src="<%=request.getContextPath()%>/images/shipmenterrorcorrection.JPG"
		width="550" height="20" alt=""/>
		
	<logic:notEmpty property="strSuccessMsg" name="dpMissingStockApprovalBean">
			<BR>
				<strong><font color="red"><bean:write property="strSuccessMsg"  name="dpMissingStockApprovalBean"/></font></strong>
			<BR>
		</logic:notEmpty>
		
		
		

	<table class="mTop30" align="center" border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse;" width='100%'>

	
		<tr class="text white">
			<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message  bundle="dp" key="label.VPO.PO."/></FONT></td>
			<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message  bundle="dp" key="label.VPO.DCNO"/></FONT></td>
		<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message  bundle="dp" key="label.VPO.DistName"/></FONT></td>
			<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message  bundle="dp" key="label.VPO.PoQTY"/></FONT></td>
						<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message  bundle="dp" key="label.VPO.InvQTY"/></FONT></td>
			
					
			<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message  bundle="dp" key="label.VPO.AcceptQty"/></FONT></td>
			<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message  bundle="dp" key="label.VPO.MissingQty"/></FONT></td>
			<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message  bundle="dp" key="label.VPO.AddedQty"/></FONT></td>
			
		</tr>

		<logic:empty name="dpMissingStockApprovalBean" property="poList">
			<TR class="lightBg">
				<TD class="text" align="center" colspan="16">Missing Purchase Order Details are not Avaliable</TD>
			</TR>
		</logic:empty>
				
		<logic:notEmpty name="dpMissingStockApprovalBean" property="poList">
			<logic:iterate name="dpMissingStockApprovalBean"
			 id="POInfo" indexId="i"
				property="poList">
				<TR BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>' >

					
					<TD align="center"   class="text"><a href="javascript:showSTBDetail('${POInfo.invoice_no}','${POInfo.po_no}')"  ><bean:write name="POInfo"
						property="po_no" /></a></TD>
						
					
					<TD align="center"  class="text"><bean:write name="POInfo"
						property="dcNo" /></TD>
			
					<TD align="center"  class="text"><bean:write name="POInfo"
						property="distName" /></TD>	
						
					<TD align="center"  class="text"><bean:write name="POInfo"
						property="poqty" /> </TD>
						
					<TD align="center"  class="text"><bean:write name="POInfo"
						property="invqty" /></TD>
					<TD align="center"  class="text"><bean:write name="POInfo"
						property="accptqty" /></TD>
					
					<TD align="center"  class="text"><bean:write name="POInfo"
						property="missingqty" /></TD>

					
					<TD align="center"  class="text"><bean:write name="POInfo"
						property="addedty" /> </TD>
						
					
			</TR>
			
			</logic:iterate>
			</logic:notEmpty>
	
	
		<!-- pagination code start -->
					<TR align="center">
						<TD align="left" bgcolor="#daeefc" colspan="16"><FONT
							face="verdana" size="2">
							<c:if test="${PAGES!=''}">
							<c:if test="${PAGES>1}">
								    	Page:
								<c:if test="${PRE>=1}">
									<a href="#"
										onclick="paginationStep('<c:out value='${PRE}' />','<c:out value='${circleId}' />');">
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
													onclick="paginationStep('<c:out value="${item}"/>','<c:out value='${circleId}' />');"><c:out
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
												<a href="#"
													onclick="paginationStep('<c:out value="${item}"/>','<c:out value='${circleId}' />');"><c:out
													value="${item}" /></a>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:if>
								
								<c:if test="${NEXT<=PAGES}">
									<a href="#"
										onclick="paginationStep('<c:out value='${NEXT}' />','<c:out value='${circleId}' />');">Next></a>
								</c:if>
								
							</c:if>
						</c:if> </FONT></TD>
					</TR>
				<input type = "hidden" name ="methodName" value = "" />
				<html:hidden property="poNo1" styleId="poNo1" > </html:hidden>
		<html:hidden property="childStatus" styleId="childStatus" > </html:hidden>
						
			<!-- pagination code end -->
	
	</table>
</html:form>
</body>

</html:html>					