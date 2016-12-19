<!-- Added by Mohammad Aslam -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html:html>
	<HEAD>
		<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
		<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<META name="GENERATOR" content="IBM WebSphere Studio">
		<META http-equiv="Content-Style-Type" content="text/css">
		<LINK href="<%=request.getContextPath()%>/theme/Master.css" rel="stylesheet" type="text/css">
		<LINK href=<%=request.getContextPath()%>/jsp/transaction/theme/text.css rel="stylesheet" type="text/css">
		<script>
			function resetFields(){
				if(document.forms[0].checkbox.length == undefined){
					document.forms[0].checkbox.checked=false;
				}
				for (counter = 0; counter < document.forms[0].checkbox.length; counter++){
					document.forms[0].checkbox[counter].checked=false;
				}
			}			
			function closeWindow(){
				var serialNos;
				var total_choices = 0;
				if(document.forms[0].checkbox.length != undefined){
					for (counter = 0; counter < document.forms[0].checkbox.length; counter++){
						if (document.forms[0].checkbox[counter].checked){
							if(counter == 0){
								serialNos = document.forms[0].checkbox[counter].value;
								//alert("counter  ::  "+counter+"\nserialNos  ::  "+serialNos);
							}
							else{
								serialNos = serialNos + "," + document.forms[0].checkbox[counter].value;
								//alert("counter  ::  "+counter+"\nserialNos  ::  "+serialNos);
							}	
							++total_choices; 
						}			
					}				
				} 
				else{
					total_choices = 1;
					serialNos = document.forms[0].checkbox.value;
					document.forms[0].checkbox.checked = true;			
				}
				if(document.forms[0].checkbox.checked == false){
					total_choices=0;
				}	
				var currentRowIndex = window.opener.document.forms[0].currentRowIndex.value;
			
				var reqQuanForTrans = window.opener.document.forms[0].reqQuanForTrans.value;
				//var currentRowIndex = window.opener.document.getElementById('currentRowIndex').value;//this code is running in prod
				//var reqQuanForTrans = window.opener.document.getElementById('reqQuanForTrans').value;
				if(total_choices > reqQuanForTrans){
					alert("You can not select more than Reqquested Quantity");
					return false;
				}
				if(total_choices == 0){
					alert("Please select any of serial number");
					return false;
				}
				window.opener.document.getElementsByName("totalCount")[currentRowIndex].value = total_choices;	
				window.opener.document.getElementsByName("serialNos")[currentRowIndex].value = serialNos;					
				window.close();											
			}		
			function selectedSerialNos(){
				var tableRows = window.opener.document.getElementById("transDetailTable").rows;
				for(i = 1; i < tableRows.length; i++){
					var selSerialNos = window.opener.document.getElementsByName("serialNos")[i-1].value
					var currentTagTokens = selSerialNos.split( "," );
					var splitTemp = "";
					for (var counter = 0; counter < document.forms[0].checkbox.length; counter++){
						for (var count = 0; count < currentTagTokens.length; count++){
							splitTemp = currentTagTokens[count];
							if (document.forms[0].checkbox[counter].value == splitTemp){
								document.forms[0].checkbox[counter].checked = true;
								continue;
							}
						}
	   				} 
				}	
				return false;			
			}	
			function checkAll(){
				var field = document.forms[0].checkbox
				if(field.length == undefined){
					field.checked = true ;
				}else{
					for (i = 0; i < field.length; i++){
						field[i].checked = true ;
					}
				}
			}
			function uncheckAll(){
				var field = document.forms[0].checkbox
				if(field.length == undefined){
					field.checked = true ;
				}else{
					for (i = 0; i < field.length; i++){
						field[i].checked = false ;
					}
				}
			}			
			</script>
		</HEAD>
		<BODY onload="return selectedSerialNos();">
			<h2 align="center"></h2>
			<BR>			
			<html:form action="/viewStockSerialNoList">	
				<IMG src="<%=request.getContextPath()%>/images/serialNos.gif" width="450" height="22" alt="">
	 			<% int i = 1; %>
	  			<div style="width:100%; height:400px; overflow:scroll;" >
					<table border="1" width="100%">
						<tr><logic:iterate id="currentSerialNo" name="serialNumberList">
							<% if((i%4) == 1){out.println("</tr><tr>"); }i++;  %>
							<td align="center"><input type="checkbox" name="checkbox" value='<bean:write name="currentSerialNo"/>'/></td>
							<td><bean:write name="currentSerialNo"/>&nbsp;&nbsp;</td>			
						</logic:iterate></tr>
					</table>
				</div>
				<table border="1" width="100%">
					<TR>
						<td align="center" width="100%">
							<html:button property="btn" value="Close" onclick="closeWindow();"/>&nbsp;&nbsp;&nbsp;
							<html:button property="btn1" value="Reset" onclick="return resetFields();"/>
							<input type="button" name="CheckAll" value="Check All" onClick="checkAll()">
	               			<input type="button" name="UnCheckAll" value="Uncheck All" onClick="uncheckAll()">
		         		</td>
					</TR>	
				</table>	
				<br>
			</html:form>
		</BODY>
</html:html>