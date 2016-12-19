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
<TITLE>Av Stock Upload</TITLE>
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/jScripts/progressBar.js" type="text/javascript">
</SCRIPT>
<SCRIPT language="JavaScript">
var fileCSVAlert = "Only CSV file can be uploaded.";

function uploadFile()
{
	if(document.getElementById("intProductID").value=="-1")
	{
		alert("Please select a Product");
		return false ;
	}
	   if(document.getElementById("stockList").value==""){
		alert("Please select a file ");
		return false;
	}
	else if(!(/^.*\.csv/.test(document.getElementById("stockList").value))) 
	{
			//Extension not proper.
			alert(fileCSVAlert);
			return false;
				
	}
	else if(navigator.userAgent.indexOf("MSIE") != -1) {
		if(document.getElementById("stockList").value.indexOf(":\\") == -1 ) {
			alert("Please Select a proper file.")
			return false;
		}
	}
	//alert(document.getElementById("intBusCatID").value);
	document.getElementById("intBusCatHid").value = document.getElementById("intBusCatID").value;
	document.forms[0].action="distavStockUpload.do?methodName=uploadExcel";
	document.forms[0].method="POST";
	loadSubmit();
	document.forms[0].submit();
}

</SCRIPT> 
</HEAD>

<BODY BACKGROUND="${pageContext.request.contextPath}/jsp/transaction/images/bg_main.gif">
<html:form  action="/avStockUpload"  enctype="multipart/form-data"  > 
<html:hidden property="methodName"/>	
<html:hidden property="intBusCatHid"/>
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
				<IMG src="<%=request.getContextPath()%>/images/avstockupload.gif"
				width="505" height="22" alt="">
			</TD>
		</tr>
		<tr>
			<td align="left" colspan="2"><strong><FONT color="red">
			  <html:errors/> 					  
			  <html:errors property="errors"  bundle="error"/>
			  </FONT></strong>
			 
			 	<logic:notEmpty property="strmsg" name="AvStockUploadFormBean">
			 	 	<strong><FONT color="red">
			 	 		<bean:write property="strmsg"  name="AvStockUploadFormBean" />
			 	 	</FONT></strong>
			 	 </logic:notEmpty>
			  
			 </td>
	    </tr>
		<tr>
		<td><fieldset style="border-width:5px">
 	 			<legend> <STRONG><FONT color="#C11B17" size="2"><bean:message bundle="dp" key="label.dp.avstock.search" /></FONT></STRONG></legend>				  	 	
			<table width='90%' cellspacing="2" cellpadding="2">			
			    	 		    
				<tr>
					<TD class="txt" width="30%">
					<STRONG><bean:message bundle="dp" key="label.dp.avstock.BusinessCategory"/></STRONG>
					<TD class="txt" width="70%">
					<html:select property="intBusCatID" style="width:150px"  disabled="true">
						<html:option value="-1">--Select A Category--</html:option>
						<html:optionsCollection name="AvStockUploadFormBean" property="listBusCategory" label="circleName" value="circleId"/>
					</html:select></td>
				</tr>
			
				<TR>
					<TD align="left" width="30%" nowrap>
					<STRONG><bean:message bundle="dp" key="label.dp.avstock.Product"/><font color="red">*</font></STRONG></TD>
				 	<TD class="txt" align="left" width="70%" nowrap>
						<html:select property="intProductID" style="width:150px"	 >
							<html:option value="-1">--Select A Product--</html:option>
							<html:optionsCollection name="AvStockUploadFormBean" property="listProduct" label="circleName" value="circleId"/>
						</html:select> 
					</TD>
				</TR>		
				<tr>
					 <td align="left" width="30%" nowrap><STRONG>
					 <bean:message bundle="dp" key="label.dp.avstock.upload" /> <FONT color="red">*</FONT></STRONG></td>
					 <td align="left" width="70%" nowrap>
					 <html:file property="stockList" size="30">  </html:file> &nbsp; &nbsp; &nbsp; 
					 <input type="button" align="center" value="Upload" style="width:80px" onclick="uploadFile()"/> </td>
				</tr>
			</table>
			</fieldset>
		</td>
		</tr>
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
							  The transaction could not be processed due to some errors. Click on <a href="avStockUpload.do?methodName=showErrorFile">View Details </a> 
					  for details.
					  </font>
					  </strong>
					 </td>
				  </tr>
			</logic:equal>
</table>
</td>
</tr>
 <!-- Added by Neetika for BFR 16 on 8 aug 2013 Release 3 -->
				<TR>
					<TD  align="center" colspan=5>
					<p style="visibility:hidden;" id="progress">
					<img id="progress_image" style="visibility:hidden;padding-left:55px;padding-top:500px;width:30;height:34" src="${pageContext.request.contextPath}/images/ajax-loader.gif" /> 
					</p>
					</TD>
				</TR>
</table>
</html:form>
</body>
</html:html>