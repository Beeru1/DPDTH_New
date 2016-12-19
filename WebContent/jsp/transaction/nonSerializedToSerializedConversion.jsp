<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html:html>

<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="${pageContext.request.contextPath}/jsp/transaction/theme/text.css" type="text/css">
<TITLE>NON SERIALIZED TO SERIALIZED CONVERSION</TITLE>

<SCRIPT language="JavaScript">
var fileXSLAlert = "Only Excel file can be uploaded.";


function isDigit (c)
{
	//alert("c--"+c+"--");
	
   return ((parseInt(c) >= 0) && (parseInt(c) <= 10));
}

function isLetter (c)
{   
	return ( ((c >= "a") && (c <= "z")) || ((c >= "A") && (c <= "Z")) )
}
function isAlphanumericVal(s)
{
    var i;
    for (i = 0; i < s.length; i++) 
    {   
        var c = s.charAt(i);
       // alert("c::::"+c);
        //alert("isLetter::::"+isLetter(c));
       // alert("isDigit::::"+isDigit(c));
       // alert(! (isLetter(c) || isDigit(c) ));
        if (! (isLetter(c) || isDigit(c) ) )
        return false;
    }
    return true;
}

function isNumberVal(strString) {
   var strValidChars = "0123456789";
   var strChar;
   var blnResult = true;
 	var len=strString.length;
   
   if (strString.length == 0) {
   return false;
   }
   if (strString.length == 20){
   		len = strString.length-1;
   }else if (strString.length == 19){
   		len = strString.length;
   }
   
   //  test strString consists of valid characters listed above
   for (i = 0; i < len && blnResult == true; i++)
      {
	      strChar = strString.charAt(i);
    	  if (strValidChars.indexOf(strChar) == -1)
         {
        	 blnResult = false;
         }
      }
     return blnResult;
}
function checkOLM()
{
	var olmID = document.getElementById("olmId").value;

	if(olmID==null || olmID=="")
	{
		alert("Please Enter OLM ID.");
		document.getElementById("olmId").focus();
		return false;
	}
	
	var firstCharApp1 = isDigit(olmID.charAt(0));
	if(firstCharApp1==true ){
		alert("OLM ID Cannot Begin With A Numeric Character ");
		document.getElementById("olmId").focus();
		return false;
	}
	if(document.getElementById("olmId").value.length < 8)
	{
		alert("OLM ID Must Contain At Least 8 Characters.");
		document.getElementById("olmId").focus();
		return false;
	}
	
	var validolmId = isAlphanumericVal(olmID);
	//alert("olmid"+olmID);
	//alert(""+validolmId);
	
	if(validolmId==false)
	{
		alert("Special Characters and Blank Spaces are not allowed in OLM ID");
		document.getElementById("olmId").focus();
		return false;
	}
	else
	{
		document.getElementById("methodName").value = "getDistDetail";
		document.forms[0].submit();
	}
}
function uploadExcel()
{


   var fup = document.getElementById("uploadedFile"); 
	var fileName = fup.value; 
	var ext = fileName.substring(fileName.lastIndexOf('.') + 1); 
	
	if(ext == "XLS" || ext == "xls" || ext == "Xls" )
	{ 
		
        document.forms[0].action="./distavStockUpload.do?methodName=uploadExcelConversion";
		document.forms[0].method="POST";
		document.forms[0].submit();


	}  else { 
		alert("Upload excel file only"); 
		fup.focus(); 
		return false; 
		} 


	
}

</SCRIPT> 
</HEAD>

<BODY BACKGROUND="${pageContext.request.contextPath}/jsp/transaction/images/bg_main.gif">
<html:form  action="/distavStockUpload"  enctype="multipart/form-data"  > 
<html:hidden property="methodName"/>	
<html:hidden property="intBusCatHid"/>
<html:hidden property="circleId" styleId="circleId" />
<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
    int gridHeight=50;
%>

			
<table width="100%" height="200">
  <tr>
	<td>
	<table style="width:650px" border='0' cellspacing="2" cellpadding="2">
		<tr>
			<TD colspan="2" class="text"><BR>
				<IMG src="<%=request.getContextPath()%>/images/serializedconversion.jpg"
				width="505" height="22" alt="">
			</TD>
		</tr>
		<tr>
			<td align="left" colspan="2"><strong><FONT color="red">
			  <html:errors/> 					  
			  <html:errors property="errors"  bundle="error"/>
			  </FONT></strong>
			  </td>
		
		   	<logic:notEmpty property="file_message" name="AvStockUploadFormBean">
			 	 	<strong><FONT color="red">
			 	 		<bean:write property="file_message"  name="AvStockUploadFormBean" />
			 	 	</FONT></strong>
			 	 </logic:notEmpty> 
	 	
		</tr>
	 	<tr>
	 	 <td align="left" width='100%'>
	 	 <logic:notEmpty property="success"  name="AvStockUploadFormBean" >
	 		<strong><FONT color="red"> <bean:write  property="success"  name="AvStockUploadFormBean" /></FONT></strong>
	 	</logic:notEmpty>
				 </td>
	    </tr>
	
		<tr>
		<td><fieldset style="border-width:5px">
 	 			<legend> <STRONG><FONT color="#C11B17" size="2">Non-Serialized to Serialiized Conversion</FONT></STRONG></legend>				  	 	
			<table width='90%' cellspacing="2" cellpadding="2">			
			    	 		    
				<tr>
					<TD class="txt" width="30%">
					<STRONG><bean:message bundle="dp" key="label.dp.avstock.BusinessCategory"/></STRONG></TD>
					<TD class="txt" width="70%">
					<html:select property="intBusCatID" style="width:150px"  disabled="true">
						<html:option value="1">CPE</html:option>
					</html:select></td>
				</tr>
				
				<tr>
					 <td align="left" width="30%" nowrap><STRONG>
					 Distributor OLM ID: <FONT color="red">*</FONT></STRONG></td>
					 <td align="left" width="70%" nowrap>
					 <html:text property="olmId" size="30">  </html:text> &nbsp; &nbsp; &nbsp; 
					 <input type="button" align="center" value="Go" style="width:80px" onclick="checkOLM()"/> </td>
				     
				</tr>
				
				<!--  <logic:notEmpty property="error_flag" name="AvStockUploadFormBean" >
				<tr>
				<script>
				
				alert("Invalid OLM ID");
				
				</script>
				</tr>
				</logic:notEmpty>
					-->
				
			
			<tr>
					 
			</table>
			</fieldset>
			
			<tr>&nbsp; &nbsp; &nbsp;</tr>
			<tr>&nbsp; &nbsp; &nbsp;</tr>
			<tr>&nbsp; &nbsp; &nbsp;</tr>
			<tr>&nbsp; &nbsp; &nbsp;</tr>
			
			<logic:equal value="true" property="strmsg" name="AvStockUploadFormBean">
				
			<table width='100%' cellspacing="2" cellpadding="2" border="1">			
			    	 		    
				<tr>
					<TD class="txt" width="20%">
					<STRONG>Distributor OLM ID</STRONG></TD>
					<TD class="txt" width="20%">
					<STRONG>Distributor Name</STRONG></TD>
					<TD class="txt" width="20%">
					<STRONG>TSM OLM ID</STRONG></TD>
					<TD class="txt" width="20%">
					<STRONG>TSM Name</STRONG></TD>
					<TD class="txt" width="20%">
					<STRONG>Circle</STRONG></TD>
				</tr>
				
				<logic:iterate id="account" property="nonSerializedToSerializedList" type="com.ibm.dp.dto.NonSerializedToSerializedDTO"
						name="AvStockUploadFormBean" indexId="i">
				<tr>
				<TD class="txt" width="20%">
					<bean:write name="account"
								property="olmId" /></TD>
					<TD class="txt" width="20%">
					<bean:write name="account"
								property="distName" /></TD>
					<TD class="txt" width="20%">
					<bean:write name="account"
								property="tsmOlmId" /></TD>
					<TD class="txt" width="20%">
					<bean:write name="account"
								property="tsmName" /></TD>
					<TD class="txt" width="20%">
					<bean:write name="account"
								property="circle" /></TD>
				</tr>
				</logic:iterate>
				<tr>
		<!--  		<td align="left" width="30%" nowrap><STRONG>
					 <bean:message bundle="dp" key="label.dp.avstock.upload" /> <FONT color="red">*</FONT></STRONG></td>
					 <td align="left" width="70%" nowrap>
					 <html:file property="stockList" size="30">  </html:file> &nbsp; &nbsp; &nbsp; 
					 <input type="button" align="center" value="Upload" style="width:80px" onclick="uploadFile()"/> </td>  -->
				</tr>

			</table>
			
			
			
			
			
			<logic:notEmpty property="nonSerializedToSerializedList" name="AvStockUploadFormBean" >
			
			<table width='100%' cellspacing="2" cellpadding="2" border="0">			
			<TR>
				<TD class="txt" align="right" width="35%"><B>
				<bean:message bundle="dp"
						key="bulkupload.file" /> :
				</B><font color="red">*</font>
				</TD>
				<TD class="txt" align="left" width="65%"><html:file property="uploadedFile"></html:file>
				<strong>
				<a href="distavStockUpload.do?methodName=exportExcel">Download Data File </a>
				<strong>
				</TD>
			</TR>
		

			<TR>
				<TD colspan='2'>&nbsp;</TD>
			</TR>
			<TR>
				<TD align="center"  colspan=3><input type="button" id="Submit"
					value="Upload Stock" name="uploadbtn" id="uploadbtn" onclick="uploadExcel();">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
			</TR>
			</table>
			</logic:notEmpty>
			</logic:equal>
			
			<logic:equal value="FAIL" property="strmsg" name="AvStockUploadFormBean">
			<font color="red"><strong>	<bean:write property="error_flag" name="AvStockUploadFormBean"/></strong></font>
			
			</logic:equal>
			
	
		<tr><td></td></tr>
		<tr>
		<td>
		</td>
		</tr>							
		<tr>
	</table>	
        <table>
    		 <logic:equal name="AvStockUploadFormBean" property="error_flag" value="true" >
				  <tr>
				  	<td  align="center" colspan="5">
				  	<strong>
				  	<font color="red">
							  The transaction could not be processed due to some errors. Click on <a href="distavStockUpload.do?methodName=showErrorFileSerialize">View Details </a> 
					  for details.
					  </font>
					  </strong>
					 </td>
				  </tr>
			</logic:equal>
			
			<logic:equal name="AvStockUploadFormBean" property="success_message" value="true" >
			<script>
			alert("Conversion of Non-Serialized to Serialized has been successful");
			</script>
			</logic:equal>
			
			
</table>
</td>
</tr>
</table>
</html:form>
</body>
</html:html>