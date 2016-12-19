<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.ibm.dp.common.Constants" %>
<html>
<HEAD>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<link href="${pageContext.request.contextPath}/theme/text_new.css" rel="stylesheet" type="text/css">
<LINK href="<%=request.getContextPath()%>/theme/Master.css"
	rel="stylesheet" type="text/css">



<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<script type='text/javascript'
	src='${pageContext.request.contextPath}/jScripts/jQuery/jquery-1.6.2.min.js'></script>
<script type='text/javascript'
	src='${pageContext.request.contextPath}/jScripts/jQuery/jquery-ui-1.8.17.min.js'></script>
<script language="javascript" src="scripts/cal.js">
</script>
<script language="javascript" src="scripts/cal_conf.js">
</script>

<SCRIPT language="javascript" src="<%=request.getContextPath()%>/scripts/Ajax.js" type="text/javascript"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>

<script language="JavaScript"><!--


function submitForm() {

		   		
			if(document.getElementById("cardgrpId").value ==""){
							alert("Please Enter Card Group Id");
							return false;
	   		}
	   		
	   		
	   		if(document.getElementById("cardgrpName").value ==""){
				alert("Please Enter Card Group Name");
				return false;
	   		}	
	   		
	   		if(document.getElementById("cardgrpId").value.length >20){
							alert("Card Group Id should be of 20 characters max.");
							return false;
	   		}   
	   		
	   		if(document.getElementById("cardgrpName").value.length >50){
				alert("Card Group Name should be of 50 characters max.");
				return false;
	   		}			
	   		
	document.forms[0].methodName.value="insertCard";
	document.forms[0].submit();
		
}

/*
function editCard() {

	document.forms[0].methodName.value="getCardGrpList";
	document.forms[0].submit();
		
}*/
function submitData(nextPage)
	{
		//alert("vishwas"+nextPage);
		document.forms[0].action="dpCardGrpMstr.do?methodName=init&page="+ nextPage;
		//alert("vishwasss"+nextPage);
		//document.getElementById("page").value="";
		//alert("vishwasddd"+nextPage);
		document.forms[0].method="post";
		//alert("vishwasfff"+nextPage);
		document.forms[0].submit();
	}


function formEdit(obj,obj1){

		//alert("cardgrpId::::::::"+obj);
		//alert("cardgrpName::::::::"+obj1);
		
		
		document.getElementById("cardgrpId").value=obj;
		document.getElementById("cardgrpName").value=obj1;
		
	}
	
	
	function formDelete(obj,status){
	//alert("delete card status:::"+status);
	if(status=='A')
	{
	if(confirm("Do You Want to Proceed with Deletion of Card Group?"))
	{
		document.forms[0].action="./dpCardGrpMstr.do?methodName=deleteCard&status=I&cardid="+obj;
		//document.getElementById("cardgrpId").value=obj;
		document.forms[0].submit();
	}
	}
	else
	{
	alert("The Card Group is already Inactive");
	}
	}
	function formActivate(obj,status){
	//alert("activate card status:::"+status);
	if(status=='I')
	{
	if(confirm("Do You Want to Proceed with Activation of Card Group?"))
	{
		document.forms[0].action="./dpCardGrpMstr.do?methodName=deleteCard&status=A&cardid="+obj;
		//document.getElementById("cardgrpId").value=obj;
		document.forms[0].submit();
	}
	}
	else
	{
	alert("The Card Group is already Active");
	}
	}


/*

function getAmount() {
 var prodType = document.getElementById("productType").value;
  //var amount = document.getElementById("amount").value;
	var url = "debitAmountMstr.do?methodName=getAmount&productType="+prodType;
			if(window.XmlHttpRequest) 
			{
				req = new XmlHttpRequest();
			}else if(window.ActiveXObject) 
			{
				req=new ActiveXObject("Microsoft.XMLHTTP");
			}
			if(req==null) 
			{
				alert("Browser Doesnt Support AJAX");
				return false;
			}
			req.onreadystatechange = getAmountHandler;
			req.open("POST",url,false);
			req.send();
		return true;
}
function  getAmountHandler()
{
if (req.readyState==4 || req.readyState=="complete") 
		
	  	{
	  document.forms[0].amount.value=req.responseText;	
	  	}

}*/
--></SCRIPT>
</HEAD>


<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" >
	
	<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>
	
<TABLE valign="top" align="left">
	<TR valign="top">
		<TD valign="top">
		
			<html:form action="/dpCardGrpMstr.do">
			
			<Table width="545" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
							<TD colspan="4" class="text"><BR>
												<!-- Page title start -->
		
						<IMG src="<%=request.getContextPath()%>/images/card1.png"
		width="350" height="18" alt=""/>
						
				
					<!-- Page title end -->
							
							</TD>
				</TR>
				
				<tr>
				<td colspan="4" class="text" align="center"><BR><strong>
					<font size="1" color="red">
			<%
				if(request.getAttribute("success") != null) {
					out.print((String)request.getAttribute("success"));
				}
			 %>
			 
				<TR>
					<TD colspan="4" align="center"><FONT color="#ff0000" size="-2"><html:errors
						bundle="error" property="error.account" /></FONT> <FONT color="#ff0000"
						size="-2"><html:errors bundle="dp"
						property="message.account" /></FONT></TD>
					<input type="hidden" name="methodName" styleId="methodName" />
				</TR>








 <tr>
				<td colspan="2" align="left" class="error">
					<strong> 
          			<logic:present name = "DPCardGrpMstrFormBean" property="message"> 
          			<logic:notEmpty  name = "DPCardGrpMstrFormBean" property="message">               		
          			<bean:write property="message" name="DPCardGrpMstrFormBean"/>  <br>                          
             		</logic:notEmpty> 
             		</logic:present>
             		
            		</strong>
            	</td>
		</tr>
		
		
		
		




				

		
				<TR align="center">
					<TD class="txt" align="left" width="40%"><strong><font
						color="#000000"><bean:message bundle="view"
						key="dpCardgrp.id" /></font><font color="red">*</font>:</strong></TD>
					<TD class="txt" align="left"  width="275" >
					
					<html:text property="cardgrpId" onkeypress="alphaNumWithoutSpace()" 	style="width:230px" styleId="cardgrpId"
						name="DPCardGrpMstrFormBean"></html:text>
						
					
					</TD>
					<TD width="119">
					</TD>
				</TR>
				
				
				
				<TR align="center">
					<TD class="txt" align="left" width="40%"><strong><font
						color="#000000"><bean:message bundle="view"
						key="dpCardgrp.name" /></font><font color="red">*</font>:</strong></TD>
					<TD class="txt" align="left"  width="275" >
					
					<html:text property="cardgrpName" onkeypress="alphaNumWithoutSpace()" style="width:230px" styleId="cardgrpName"
						name="DPCardGrpMstrFormBean"></html:text>
						
					
					</TD>
					<TD width="119">
					</TD>
				</TR>
				
				
				<TR>
					<TD>
						<BR/>
					</TD>
				</TR>
			 
				<TR>
					<td width="40%"></td>	
					<TD>
					<input type="button" id="submit_form" value="Add/Update"  onclick="submitForm();" />
					&nbsp;&nbsp;
					<!-- 
					<input type="button" id="submit_form" value="Edit/Delete"  onclick="editCard();" />
					-->	
					</TD>
					
					
					
					
				</TR>
				
				
				<TR align="center" bgcolor="4477bb" valign="top">

				</TR>
							
			</TABLE>	
			
			
			
			
			<table id="cardMstr" width ="100%">
				
				<tr  bgcolor="#CD0504">
                    <td align="center" width="25%"><b><FONT color="white">CardGroup Id</FONT></b></td>
					<td align="center" width="25%"><b><FONT color="white">CardGroupName</FONT></b></td>
					<td align="center" width="20%"><b><FONT color="white">Status</FONT></b></td>
					<td align="center" width="30%"><b><FONT color="white">Action</FONT></b></td>
				</tr>
				
				<tr>&nbsp;&nbsp;</tr>
				
					
					<logic:notEmpty name="DPCardGrpMstrFormBean" property="cardGroupList">
					<logic:iterate name="DPCardGrpMstrFormBean" id="report" indexId="m"
						property="cardGroupList">
						<tr bgcolor="<%=((m.intValue()+1) % 2==0 ? rowDark : rowLight) %>">
						<td width="70"><bean:write name="report"
								property="cardgrpId" /></td>
							<td width="70"><bean:write name="report"
								property="cardgrpName" /></td>
							<td width="196"><bean:write name="report"
								property="status" /></td>
							<td class="txt" align="center"><a
								href="javascript: formEdit('<bean:write name="report" property="cardgrpId" />','<bean:write name="report" property="cardgrpName" />');"> <font color="red">Edit</font></a>
								/
								<a
								href="javascript: formDelete('<bean:write name="report" property="cardgrpId" />','<bean:write name="report" property="status" />');"> <font color="red">Deactivate</font></a>
								/
								<a
								href="javascript: formActivate('<bean:write name="report" property="cardgrpId" />','<bean:write name="report" property="status" />');"> <font color="red">Activate</font></a>
					
								</td>
						</tr>
					</logic:iterate>

				</logic:notEmpty>
					
			<logic:notEmpty property="cardGroupList" name="DPCardGrpMstrFormBean">
			<TR align="center">
			<TD align="center" bgcolor="#daeefc" colspan="15"><FONT
				face="verdana" size="2"><c:if test="${PAGES!=''}">
				<c:if test="${PAGES>1}">
					    	Page:
					<c:if test="${PRE>=1}">
						<a href="#"
							onclick="submitData('<c:out value='${PRE}'/>');"><
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
										onclick="submitData('<c:out value='${item}'/>');"><c:out
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
									<a href="#" onclick="submitData('<c:out value='${item}'/>');"><c:out value="${item}" /></a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:if>
					<c:if test="${NEXT<=PAGES}">
						<a href="#"
							onclick="submitData('<c:out value='${NEXT}'/>');">Next></a>
					</c:if>
				</c:if>
			</c:if> </FONT></TD>
		</TR>
		</logic:notEmpty>
							</table>
		</html:form>	
		</TD>
	</TR>
	
	</TABLE>
	
</BODY>
</html>
		