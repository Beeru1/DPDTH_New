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
<TITLE>STB Flush Out</TITLE>
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/jScripts/progressBar.js" type="text/javascript">
</SCRIPT>
<SCRIPT language="JavaScript">
var fileCSVAlert = "Only CSV file can be uploaded.";
var fileTypeAlert = "Only Excel file can be uploaded.";
function uploadForSearch()
{ 	   
   if(document.getElementById("stbSearchFile").value==""){
		alert("Please select a file to Search STB");
		return false;
	}else if(!(/^.*\.csv/.test(document.getElementById("stbSearchFile").value))) {
			//Extension not proper.
			alert(fileCSVAlert);
			return false;
				
	}else if(navigator.userAgent.indexOf("MSIE") != -1) {
		if(document.getElementById("stbSearchFile").value.indexOf(":\\") == -1 ) {
			alert("Please Select a proper file.")
			return false;
		}
	}
		
	document.forms[0].action="StbFlushOut.do?methodName=uploadSearchFile";
	document.forms[0].method="POST";
	loadSubmit();
	document.forms[0].submit();
}

function uploadFlushOutFiles()
{
  if(document.getElementById("freshStockFlushFile").value == ""  )
  {
  alert("Please select a file for Flush Out.");
  return false;
  }
  if(document.getElementById("freshStockFlushFile").value != "")
  {
/*if(!(/^.*\.csv$/.test(document.getElementById("freshStockFlushFile").value))) {

    if(!(/^.*\.xls$/.test(document.getElementById("freshStockFlushFile").value))) {

			alert(fileTypeAlert);
			return false;
				
	}else if(navigator.userAgent.indexOf("MSIE") != -1) {
		if(document.getElementById("freshStockFlushFile").value.indexOf(":\\") == -1 ) {
			alert("Please Select a proper file.")
			return false;
		}
	}*/
   }

  
   

	document.forms[0].action="StbFlushOut.do?methodName=flushErrorPO";
	document.forms[0].method="POST";
	loadSubmit();
	document.forms[0].submit();
}

function downloadFreshStockList()
{ 
  //var url="StbFlushOut.do?methodName=downloadFreshStockList";  	
 // window.open(url,"SerialNo","width=1000,height=600,scrollbars=yes,toolbar=no"); 	
  
    document.forms[0].action="StbFlushOut.do?methodName=downloadFreshStockList";
	document.forms[0].method="POST";
	document.forms[0].submit();
	
}
</SCRIPT> 
</HEAD>

<BODY BACKGROUND="${pageContext.request.contextPath}/jsp/transaction/images/bg_main.gif">
<html:form  action="/StbFlushOut"  enctype="multipart/form-data"  > 
<html:hidden property="methodName"/>	
<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
    int gridHeight=50;
%>
<br><IMG src="${pageContext.request.contextPath}/images/stbFlushOut.jpg" width="542" height="26" alt=""/>
			
<table width="100%" height="200">
  <tr>
	<td>
	
	
	<table style="width:650px" border=0>
		<tr>
		<td><fieldset style="border-width:5px">
 	 			<legend> <STRONG><FONT color="#C11B17" size="2"><bean:message bundle="dp" key="label.dp.flushout.search" /></FONT></STRONG></legend>				  	 	
			<table>			
			    <tr>
					<td align="left" colspan="2"><strong><FONT color="red">
					  <html:errors/> 					  
					  <html:errors property="errors"  bundle="error"/>
					  </FONT></strong></td>
    		    </tr>	 		    
							
				<tr>
					 <td align="left" width="170" nowrap>&nbsp;<bean:message bundle="dp" key="label.dp.flushout.upload" /> <FONT color="red">*</FONT></td>
					 <td align="left" width="480" nowrap>
					 <html:file property="stbSearchFile" size="30">  </html:file> &nbsp; &nbsp; &nbsp; 
					 <input type="button" align="center" value="Search" style="width:80px" onclick="uploadForSearch()"/> </td>
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
	
	<logic:notEmpty name="StbFlushOutFormBean" property="freshSTBList">		
	
	 <logic:iterate name="StbFlushOutFormBean" id="stbList" indexId="i" property="freshSTBList">
	  <%
	    gridHeight  = gridHeight + 30;	   
	    if(gridHeight>290)
	     gridHeight = 290;
	  %>
	 </logic:iterate>
	 						
    <table width="100%"><tr><td align="left"> <STRONG><FONT color="#C11B17" size="3"><bean:message bundle="dp" key="label.dp.flushout.stbList" /></FONT></STRONG></td>
    	       <td align="right"><img src="${pageContext.request.contextPath}/images/xlsicon.PNG" height="30" width="40" onclick="downloadFreshStockList();" alt="Download STB Details"/>  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </td></tr></table>
    <DIV style="height: <%=gridHeight%>px; overflow: auto;" width="80%">
	 <table width="100%" align="left" border="1" cellpadding="0" cellspacing="0" 
		style="border-collapse: collapse;">
			<thead>
				<tr style="position: relative; top:expression(this.offsetParent.scrollTop-2);" class="text white"> 																												
					<td bgcolor="#CD0504" class="colhead" align="center" width="10%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.SRNO"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="10%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.ProdName"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="10%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.Status"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="10%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.PONO"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="20%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.POStatus"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="10%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.DCNO"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="10%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.DCDate"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="10%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.InvChangeDt"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="10%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.ReceivedOn"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="10%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.StockRecDt"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="10%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.DISTOLMID"/></FONT></td>
					<td bgcolor="#CD0504" class="colhead" align="center" width="10%"><FONT color="white"><bean:message  bundle="dp" key="label.SFO.DISTName"/></FONT></td>
				</tr>
			</thead>
			<tbody>				
					
					<logic:iterate name="StbFlushOutFormBean" id="stbList" indexId="i" property="freshSTBList">
						<TR BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>' >
							<TD align="center" class="text">
								<bean:write name="stbList" property="strSerialNo"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="stbList" property="strProductName"/>					
							</TD>
							
							<TD align="center" class="text">
								<bean:write name="stbList" property="strSTBStatus"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="stbList" property="strPONumber"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="stbList" property="strPOStatus"/>					
							</TD>							
							<TD align="center" class="text">
								<bean:write name="stbList" property="strDCNo"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="stbList" property="strDCDate"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="stbList" property="strInventoryChangeDt"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="stbList" property="strReceivedOn"/>					
							</TD>	
							<TD align="center" class="text">
								<bean:write name="stbList" property="strStockReceivedDate"/>					
							</TD>													
							<TD align="center" class="text">
								<bean:write name="stbList" property="strDistOLMId"/>					
							</TD>
							<TD align="center" class="text">
								<bean:write name="stbList" property="strDistName"/>					
							</TD>
						</TR>	
					</logic:iterate>
				
			</tbody>
		</table>
	</DIV>
    </logic:notEmpty>
        
    <table width="100%">
      <tr><td align="left" height="10"> </td></tr>
      			<tr>
					<td align="left" colspan="2"><strong><FONT color="red">					  					  
					  <html:errors property="noRecordFound"  bundle="error"/>
					  </FONT></strong></td>
    		    </tr>	
     </table>
   
   	<table style="width:650px" border=0>
		<tr>
		 <td >
			<fieldset style="border-width:5px">
			 <legend> <STRONG><FONT color="#C11B17" size="2"><bean:message bundle="dp" key="label.dp.flushout.flush" /> </FONT></STRONG></legend>				  	 	
			  <table>			
			    <tr>
				  <td align="left" colspan="2"><strong><FONT color="red">
					  <html:errors property="flushFileEmpty"  bundle="error"  />
					  <html:errors property="errorUpload"  bundle="error" /> 
					  <html:errors property="fileUploadEexception"  bundle="error" />		 				  				  
					  </FONT></strong></td>
    		    </tr>	
			    				
				<tr><td align="left" width="170" nowrap>&nbsp;<bean:message bundle="dp" key="label.dp.flushout.upload.fresh" /> <FONT color="red">*</FONT></td>
					<td align="left" width="480" nowrap><html:file property="freshStockFlushFile"  size="30"></html:file> &nbsp; &nbsp; &nbsp; 
					<input type="button" align="center" value="Flush Out" onclick="uploadFlushOutFiles()"/>
					</td>
				</tr>	
			  </table>
			</fieldset>
		  </td>						
		 </tr>
	</table>	

        <table>
    		 <logic:equal name="StbFlushOutFormBean" property="error_flag" value="true" >
				  <tr>
				  	<td  align="center" colspan="5"><strong><font color="red">
							  The transaction could not be processed due to some errors. Click on <a href="StbFlushOut.do?methodName=showErrorFile">View Details </a> 
					  for details.</font><strong>
					 </td>
				  </tr>
			</logic:equal>
				 
				  <tr>
				  	<td  align="center" colspan="2"><strong><font color="red">
					 <bean:write property="strmsg" name="StbFlushOutFormBean" /> </font></strong>
					 </td>
				  </tr>	
	     </table>			  
  </td>
 </TR>
 <!-- Added by Neetika for BFR 16 on 8 aug 2013 Release 3 -->
				<TR>
					<TD  align="center" colspan=2>
					<p style="visibility:hidden;" id="progress">
					<img id="progress_image" style="visibility:hidden;padding-left:55px;padding-top:500px;width:30;height:34" src="${pageContext.request.contextPath}/images/ajax-loader.gif" /> 
					</p>
					</TD>
				</TR>
</table>
</html:form>
</body>
</html:html>