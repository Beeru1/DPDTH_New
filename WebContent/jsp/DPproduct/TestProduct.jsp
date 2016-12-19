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

<SCRIPT language="javascript" type="text/javascript"> 

function doShow()
{
var tbl = document.getElementById('businessCategory').value;
  alert(tbl);
  }

function Show()
{

var tbl = document.getElementById('businessCategory').value;
 alert(tbl);
if(tbl=='3'){
document.getElementById("hndset").style.display="block";
document.getElementById("submission").style.display="block";
} 
   if(tbl=='' )
   { 
document.getElementById("hndset").style.display="none";
document.getElementById("submission").style.display="none";


}

 }

function doSubmit(){

document.DPCreateProductFormBean.submit();
}

function validate(){
if(document.getElementById("hndset").value=="3"){
	document.getElementById("hndset").style.display="block";
	
	document.getElementById("submission").style.display="block";
	
}
doSubmit();	
	}
	
</script>
</HEAD>
<BODY ">
 <form action="/DistributorPortal1/TestwsClient">
<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top" >
	
	
	
	<TR valign="top">
		
			<td>	
			<table>
			<tr>
			<td  height="100%" align="center" >
					<font> <b> welcome to Product Web Service</b></font></td>
			</tr>
			</table>
			<table>
			<TR>
			<TD align="center"><b	class="text pLeft15" >
			ACTIVITY</b>
			<html:select styleId="activity" property="activity">
			>
					<html:option value="0">- Select A Category -</html:option>
					<html:option value="C">CREATE PRODUCT</html:option>
					<html:option value="V">VIEW PRODUCT</html:option>
					<html:option value="U">UPDATE PRODUCT</html:option>
					
				</html:select><br>
					<br>
					
					<br>
					</TD>
				</TR>
		
		<TR>
			<TD align="center"><b	class="text pLeft15" >
			Category</b>
			<html:select styleId="businessCategory" property="businessCategory"
			onchange="Show();">
					<html:option value="0">- Select A Category -</html:option>
					<html:option value="3">Handset</html:option>
					<html:option value="1">SUK</html:option>
					<html:option value="2">RC</html:option>
					
				</html:select><br>
					<br>
					
					<br>
					</TD>
				</TR>
		
		
		<tr>
		<td width="754">
		<br>
		<TABLE id="hndset" style="display:none">
			<TR>
				<TD valign="top" height="100%" align="center">
				<b>HANDSET SERVICES </b> <font>&nbsp;<font color='red'></font></TD>
			</TR>
			
			
			<tr>
			<td width="130">PRODUCT ID<input type="text" name="prodid" value=""></td>
		</tr>
		<tr>
			<td width="130">PRODUCT NAME<input type="text" name="prodid1" value=""></td>
		</tr>
		<tr>
			<td width="130">COMPANY NAME<input type="text" name="prodid2" value=""></td>
		</tr>
	
		<tr>
			<td width="130">PRODUCT WARRANTY<input type="text" name="prodid4" value=""></td>
		</tr>
		
		<tr>
			<td width="130">PRODUCT DESCRIPTION<input type="text" name="prodid5" value=""></td>
		</tr>
		
			
			

			
				
		</TABLE>	
		
		

	
			</TR>

				<TR>
				<TD COLSPAN="2">&nbsp;</TD>
				</TR>
			
				</TABLE>
	
		</td>
		</tr>
	
		<TR>
			<td id="submission" colspan="1" align="center" width="754">
			<html:button  property="btn" value="Submit"
			onclick="return validate();" />
			</td>				
		</TR>
</table>
</form>

</BODY>
</html:html>