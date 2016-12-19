<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">	
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>

<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<%System.err.println("here inside jsp"); %>
<SCRIPT language="javascript" type="text/javascript"> 

function doShow()
{
  var tbl = document.getElementById("category").value;
  alert(tbl);
}



function doSubmit(){
	document.forms[0].action="DPEditWarrantyAction.do?methodName=Updatedata";
	document.forms[0].submit();
}
function validate(){
	
		doSubmit();		
	}

</SCRIPT>
</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
 <html:form action="DPEditWarrantyAction.do?methodName=Updatedata">
<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top" >
	
	
	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include
			page="../common/dpheader.jsp" flush="" /></TD>
	</TR>
	<TR valign="top">
		<TD  align="left" bgcolor="#FFFFFF" background="<%=request.getContextPath()%>/images/bg_main.gif" valign="top"
			height="100%"><jsp:include page="../common/leftHeader.jsp"
			flush="" />
			<td>	
			<table>
		<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/editWarranty.gif"
		width="505" height="22" alt="">
	</TD>
</tr>	
			</table>
			<table border="0" align="center" >
	<TR>
		<TD align="center"><b class="text pLeft15" >
			<bean:message bundle="view" key="warrantycreate.businesscategory" /></b>	
			<html:hidden property="category" >
			</html:hidden>
				
			<html:text property="businessCategory"  readonly="true">
	
			</html:text>			
			</TD>
		
		</TR> 
			
		<tr>
		<td width="754">
		<br>
		<TABLE id="hndset">
			<TR>
			<TD class="text pLeft15"><bean:message bundle="view"
				key="warrantycreate.productid" /></TD>
		
			<td>
			<html:text property="productid" readonly="true"/>			
		
			</TD>
			</TR>
			
			<TR>
			
			
			<TD class="text pLeft15"><bean:message bundle="view"
					key="warrantycreate.Productname" /></TD>
					<TD><html:text property="productname" maxlength="50" readonly="true" /></TD>
			
			
			</TR>
			
			
			<TR>
			
				<TD class="text pLeft15"><bean:message bundle="view"
								key="warrantycreate.productwarranty" /></TD>
								<TD><html:text property="warranty" maxlength="10" readonly="true">
								
								</html:text>
								</TD>
				</TR>
			<TR>
		  
		  <TD class="text pLeft15"><bean:message bundle="view"
					key="warrantyext.productwarranty" /></TD><TD>
				<html:text  property="extwarranty" maxlength="10" /></TD> 
		   
		   
		</TR>
		
		</TABLE>	
		
	
		</td>
		</tr>
		 <TR>
		<td id="updation" colspan="1" align="center" width="754">
			<input type="button" class="subbig" value="Update"
			onclick="return validate();" >
		</td>
		</TR> 	
		<TR>
         <TD width="754"><FONT color="RED"><STRONG><html:errors
            bundle="view" property="message.product" /><html:errors
            bundle="error" property="errors.product" /><html:errors /></STRONG></FONT></TD>
        </TR>
		</table>   
	
	
	</TABLE>
	</html:form>
	<TR align="center" bgcolor="4477bb" valign="top">
		<TD colspan="2" height="17" align="center"><jsp:include
			page="../common/footer.jsp" flush="" /></TD>
	</TR>

</BODY>
</html:html>