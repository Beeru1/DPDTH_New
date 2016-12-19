<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

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
   
 }
function doSubmit(){
alert(document.forms[0].prodid.value);
//document.getElementById("submit");
document.forms[0].submit();
}
function validate(){
if(document.getElementById("hndset").value=="3"){
document.getElementById("hndset").style.display="block";	
document.getElementById("submission").style.display="block";	
}
doSubmit();	
	}
	
</script>

<BODY>
 <form method="post" action="/DistributorPortal1/TestwsClient">
 
<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top" >
	
	<TR>
		
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
			<select styleId="activity" name="activity" value="">
					<option value="0">- Select A Category -</option>
					<option value="C">CREATE PRODUCT</option>
					<option value="V">VIEW PRODUCT</option>
					<option value="U">UPDATE PRODUCT</option>
					
				</select>
				<br>
					<br>
					
					<br>
					</TD>
				</TR>
		
		<TR>
			<TD align="center"><b	class="text pLeft15" >
			Category</b>
			<select styleId="businessCategory" name="businessCategory" value="" onchange="Show();">
					<option value="0">- Select A Category -</option>
					<option value="3">Handset</option>
					<option value="1">SUK</option>
					<option value="2">RC</option>
					
				</select>
				<br>
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
		<TR>
				<TD width="130">STOCK TYPE</TD><TD><select  name="prodid3" value="">
				<option value="0">-select a Stock Type-</option>
				<option value="P">paired</option>
				<option value="N">unpaired</option>
				</select>
				</TD>
			</TR>
		<tr>
			<td width="130">PRODUCT WARRANTY<input type="text" name="prodid4" value=""></td>
		</tr>
		
		<tr>
			<td width="130">PRODUCT DESCRIPTION<input type="text" name="prodid5" value=""></td>
		</tr>
		
			
			

			
				
		</TABLE>	
		
		

	
			</TR>

			
				</TABLE>
	
		</td>
		</tr>
	<tr><td>
	<input type="submit" name="submit" value="submit" >
	</td></tr>
</TABLE>
	</form>

</BODY>
</html>