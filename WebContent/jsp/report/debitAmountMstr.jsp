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

<SCRIPT language="javascript" src="<%=request.getContextPath()%>/scripts/Ajax.js" type="text/javascript">
</SCRIPT>
<script language="JavaScript">


function submitForm() {

	if(document.getElementById("productType").value =="-1"){
				alert("Please Select Product Type");
				return false;
	   		}
	   		
	if(document.getElementById("amount").value ==""){
				alert("Please Enter Amount");
				return false;
	   		}
	   		
	   		
	document.forms[0].methodName.value="insertAmount";
	document.forms[0].submit();
		
}

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

}
</SCRIPT>
</HEAD>


<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" >
<TABLE valign="top" align="left">
	<TR valign="top">
		<TD valign="top">
		
			<html:form action="/debitAmountMstr.do">
			
			<Table width="545" border="0" cellpadding="5" cellspacing="0"
				class="text">
				<TR>
							<TD colspan="4" class="text"><BR>
												<!-- Page title start -->
		
						<IMG src="<%=request.getContextPath()%>/images/DebitAmtMstr.png"
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
          			<logic:present name = "DebitAmountMstrFormBean" property="message"> 
          			<logic:notEmpty  name = "DebitAmountMstrFormBean" property="message">               		
          			<bean:write property="message" name="DebitAmountMstrFormBean"/>  <br>                          
             		</logic:notEmpty> 
             		</logic:present>
             		
            		</strong>
            	</td>
		</tr>



<TR align="center">
					<TD class="txt" align="left"><strong><font
						color="#000000"><bean:message bundle="view"
						key="debit.productType"  /></font><font color="red">*</font>:</strong></TD>
						
						<TD class="txt" align="left"  width="200" ><html:select
						property="productType" style="width:230px" styleId="productType" onchange="getAmount()"  name="DebitAmountMstrFormBean">
						
						<html:option value="-1">
							<bean:message bundle="view" key="debit.select.productType" />
						</html:option>
							
							<logic:notEmpty name="DebitAmountMstrFormBean"
								property="productList">
								
								<bean:define id="productList" name="DebitAmountMstrFormBean" property="productList" />
								<html:options labelProperty="productType" property="productType" collection="productList" />
							</logic:notEmpty>
						</html:select>
					</td>
				</TR>
				

		
				<TR align="center">
					<TD class="txt" align="left" width="40%"><strong><font
						color="#000000"><bean:message bundle="view"
						key="debit.amount" /></font><font color="red">*</font>:</strong></TD>
					<TD class="txt" align="left"  width="275" >
					
					<html:text property="amount" style="width:230px" styleId="amount"
						name="DebitAmountMstrFormBean"></html:text>
						
						
						<logic:notEmpty name="DebitAmountMstrFormBean"
								property="amount">
								
							</logic:notEmpty>
						
						
						
						
						
						
								
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
					<TD/>	
					<TD><input type="button" id="submit_form" value="Add Amount" tabindex="8" onclick="submitForm();" />
					</TD>
				</TR>
						<!-- Added by Neetika for BFR 16 on 13aug 2013 Release 3 -->
				<TR>
					<TD  align="center" colspan=4>
					<p style="visibility:hidden;" id="progress">
					<img id="progress_image" style="visibility:hidden;padding-left:55px;padding-top:500px;width:30;height:34" src="${pageContext.request.contextPath}/images/ajax-loader.gif" /> 
					</p>
					</TD>
				</TR>
				<TR align="center" bgcolor="4477bb" valign="top">

				</TR>
							
			</TABLE>	
	
		</html:form>	
		</TD>
	</TR>
	
	</TABLE>
	
</BODY>
</html>
		