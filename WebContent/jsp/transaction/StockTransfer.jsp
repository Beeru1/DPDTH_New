<!-- Added by Mohammad Aslam -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>
<HEAD>
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
	<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<META name="GENERATOR" content="IBM Software Development Platform">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK href="${pageContext.request.contextPath}/theme/text.css" rel="stylesheet" type="text/css">
	<TITLE><bean:message bundle="dp" key="application.title"/></TITLE>
	<script>
		function getTransferToDistributorsList(){
			//alert('inside getTransferToDistributorsList function');
			var xmlHttp = GetXmlHttpObject();
			if (xmlHttp == null){
  				alert ("Your Browser Does Not Support AJAX!");
 				return;
  			}   			
			var trnsReqByDist = document.getElementById('transferRequestedBy');
			var selDistID = trnsReqByDist.options[trnsReqByDist.selectedIndex].value;
			var url = "getTransferToDistributors.do";
			url = url + "?methodName=getToDistributors&distID=" + selDistID;
			xmlHttp.onreadystatechange = function(){
											setAjaxXmlValues(xmlHttp);
									 	 }			
			xmlHttp.open("POST",url,true);
			xmlHttp.send();
		}		
		function GetXmlHttpObject(){
			var xmlHttp = null;
			try{  				
	  			xmlHttp = new XMLHttpRequest();	// Firefox, Opera 8.0+, Safari
	  		}
			catch (e){	  			
	  			try{
	    			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");	// Internet Explorer
	    		}
	  			catch (e){
	    			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	    		}
	  		}
			return xmlHttp;
		}	
		function setAjaxXmlValues(xmlHttp){
			//alert('inside setAjaxXmlValues function');
			if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete"){
				var xmldoc = xmlHttp.responseXML.documentElement;
				if(xmldoc == null){		
					return;
				}
				var optionValues = xmldoc.getElementsByTagName("option");
				var transToOpt = document.getElementById("transferTo");
				transToOpt.options.length = 0;
				transToOpt.options[0] = new Option("-- Select --","");
				for(var i = 0, j = 1; i < optionValues.length; i++, j++){
					transToOpt.options[j] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			    }
			}
		}				
		function getTransferDetail(){
			//alert('inside getTransferDetail() function');
			if(document.getElementById('transferRequestedBy').value > 0 && document.getElementById('transferTo').value > 0){
				document.getElementById('goButton').disabled = true;
				var transTo = document.getElementById("transferTo");
				//alert("TRANSFER TO  ::  "+transTo.options[transTo.selectedIndex].text);
				document.forms[0].strTransferTo.value = transTo.options[transTo.selectedIndex].text;
				document.forms[0].submit();
			}else{
				alert('Please select both Transfer Requested By and Transfer To !');
			}
		}	
		function OpenSerialWindow(prodId, currentRowIndex, requestedQuantity){		
		  document.getElementById('currentRowIndex').value = currentRowIndex;	
          document.getElementById('reqQuanForTrans').value = requestedQuantity;
          
          if(requestedQuantity == 0)
				{
					alert("Transfered Quantity should be greater then Zero");
					return false;
				}	
			 
          var url = "viewStockSerialNoList.do?methodName=viewStockSerialNoList&productId="+prodId;		  		  	        
		  window.open(url,"SerialNo","width=900,height=500,scrollbars=yes,toolbar=no");
		}
		/*Commented by Nazim Hussain as to allow single stock to transfer			
		function submitTransferDetails(){
		
			var tableRows = document.getElementById("transDetailTable").rows;
			
			if(tableRows.length == 1)
			{
				alert("Please select some stock to transfer.");
				return false;
			}
			
			for(count=1; count<tableRows.length; count++){
				var prodName = tableRows[count].cells[0].innerText;
				if(document.getElementsByName("totalCount")[count-1].value == "")
				{
					alert("Please select serial numbers for " + prodName);
					return false;
				}
				if(tableRows.length > 2){			
					if(document.forms[0].action[count-1].value == 0)
					{
						alert("Please select an action for " + prodName);
						return false;
					}
				}else{
					if(document.forms[0].action.value == 0)
					{
						alert("Please select an action for " + prodName);
						return false;
					}
				}	
			}
			document.getElementById('submitButton').disabled = true;
			document.getElementById("methodName").value = "submitTransferDetails";
			document.forms[0].submit();
		}
		*/
		
		function submitTransferDetails()
		{
		
			var tableRows = document.getElementById("transDetailTable").rows;
			var noOfRows = tableRows.length;
			var valCount = 0;
			var prodName;
			
			if(noOfRows == 1)
			{
				alert("No stock to transfer.");
				return false;
			}
			else if(noOfRows == 2)
			{
				prodName = tableRows[1].cells[0].innerText;
				
				
				if(document.getElementById("action").value == 0)
				{
					alert("Please select an action for " + prodName);
					return false;
				}
				if(document.getElementById("totalCount").value == 0 && document.getElementById("action").value != "CANCEL")
				{
					alert("Transfered Quantity can not be Zero");
					return false;
				}					
				else if(document.getElementsByName("totalCount")[0].value == "")
				{
					alert("Please select serial numbers for " + prodName);
					return false;
				}
				
				valCount++;
			}
			else
			{
				for(count=1; count<tableRows.length; count++)
				{
					prodName = tableRows[count].cells[0].innerText;
					
					if(document.forms[0].action[count-1].value != 0
						&& document.getElementsByName("totalCount")[count-1].value != "")
					{
						valCount++;
					}
					else if(document.forms[0].action[count-1].value != 0
						&& document.getElementsByName("totalCount")[count-1].value == "")
					{
						alert("Please select serial numbers for " + prodName);
						return false;
					}
				}	
			}
			
			if(valCount>0)
			{
				document.getElementById('submitButton').disabled = true;
				document.getElementById("methodName").value = "submitTransferDetails";
				//alert("Success");
				document.forms[0].submit();
			}
			else
			{
				alert("Enter some stock to transfer");
				return false;
			}
		}
		
		function setLoadData()
		{
			var strAction = document.getElementById("strAction").value;
			//alert(strAction);
			if(strAction=="GO")
			{
				document.forms[0].transferRequestedBy.disabled = true;
				document.forms[0].transferTo.disabled = true;
				document.getElementById("goButton").disabled = true;
			}
			else
			{
				document.forms[0].transferRequestedBy.disabled = false;
				document.forms[0].transferTo.disabled = false;
				document.getElementById("goButton").disabled = false;	
			}
		}
		
		function resetOnCancel(rowIndex)
		{
			var tableRows = document.getElementById("transDetailTable").rows;
			
			if(tableRows.length>2)
			{
				if(document.forms[0].action[rowIndex].value == 'CANCEL')
					document.getElementsByName("totalCount")[rowIndex].value = 0;
				else if(document.forms[0].action[rowIndex].value == '0')
					document.getElementsByName("totalCount")[rowIndex].value = '';
				else if(document.forms[0].action[rowIndex].value == 'TRANSFER')
				{
					if(document.getElementsByName("totalCount")[rowIndex].value == '0')
						document.getElementsByName("totalCount")[rowIndex].value = '';
				}
			}
			else 
			{
				if(document.forms[0].action.value == 'CANCEL')
					document.getElementById("totalCount").value = 0;
				else if(document.forms[0].action.value == '0')
					document.getElementById("totalCount").value = '';
				else if(document.forms[0].action.value == 'TRANSFER')
				{
					if(document.getElementById("totalCount").value == '0')
						document.getElementById("totalCount").value = '';
				}
			}
		}
	</script>	
</HEAD>
<BODY onload="setLoadData()" background="${pageContext.request.contextPath}/images/bg_main.gif" bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<html:form action="/transferDetails" >
	<br>	
		<IMG src="${pageContext.request.contextPath}/images/stocktransfer.JPG" width="532" height="26" alt=""/>
		<html:hidden property="methodName" value="getTransferDetails"/>
		<html:hidden property="strTransferTo"/>
		<html:hidden property="strAction"/>		
		
		<TABLE border="0" align="Center" class="border" width="45%">
			<TR>
				<TD colspan="2">
				<logic:notEmpty property="strSuccessMsg" name="StockTransferFormBean">
					<BR>
						<strong><font color="red"><bean:write property="strSuccessMsg"  name="StockTransferFormBean"/></font></strong>
					<BR>
				</logic:notEmpty>
				</TD> 
			</TR>
			<TR>
				<TD class="text pLeft15" width="65%"><STRONG><FONT color="#000000"><bean:message bundle="dp" key="label.PO.TransReqBy"/></FONT></STRONG></TD>
				<TD width="35%">
					<html:select styleId="transferRequestedBy" property="transferRequestedBy" onchange="getTransferToDistributorsList();" name="StockTransferFormBean" > 
						<html:option value="">-- Select --</html:option>
						<html:options collection="distributorFromList" property="accountId" labelProperty="accountName"/>							
					</html:select>
				</TD>
			</TR>
			<TR> 
				<TD class="text pLeft15" width="65%"><STRONG><FONT color="#000000"><bean:message bundle="dp" key="label.PO.TransTo"/></FONT></STRONG></TD>
				<TD width="35%">
					<html:select styleId="transferTo" property="transferTo">
						<html:option value="">-- Select --</html:option>
						<html:optionsCollection property="toDistList" label="accountName" value="accountId"/>
					</html:select>
				</TD>
			</TR>					
			<TR>
				<TD align="center" colspan="2"><INPUT type="button" id="goButton" name="goButton" onclick="getTransferDetail();" Class="medium" value="  GO  "></TD>
			</TR>								
		</TABLE>
		<BR><BR>	
			
		<TABLE border="0" align="center" class="mLeft5">			
			<TR>
				<TD bgcolor="#CD0504" align="center" colspan="5"><FONT color="white">Stock Transfer Details</FONT></TD>
			</TR>	
			<TR>
				<TD class="text pLeft15" width="15%"><STRONG><FONT color="#000000"><bean:message bundle="dp" key="label.PO.DCNO"/></FONT></STRONG></TD>
				<TD colspan="3"><html:text property="dcNumber" readonly="true" size="40" name="StockTransferFormBean"/></TD>
			</TR>
			<TR>
				<TD class="text pLeft15" width="15%"><STRONG><FONT color="#000000"><bean:message bundle="dp" key="label.PO.From"/></FONT></STRONG></TD>
				<TD width="35%"><html:text property="from" readonly="true" size="40" name="StockTransferFormBean" /></TD>
				<TD class="text pLeft15" width="15%" align="center"><STRONG><FONT color="#000000"><bean:message bundle="dp" key="label.PO.To"/></FONT></STRONG></TD>
				<TD width="35%"><html:text property="to" readonly="true" size="40" name="StockTransferFormBean"/></TD>
			</TR>
			<tr>
			<td colspan="5">
			<table id="transDetailTable" border="0" align="center" class="mLeft5" styleId="tblSample" >
			<thead>
			<TR>
				<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="166"><FONT color="white"><span class="white10heading mLeft5 pRight5 mTop5"><bean:message bundle="dp" key="label.PO.ProductName"/></span></FONT></TD>
				<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="166"><FONT color="white"><span class="white10heading mLeft5 pRight5 mTop5"><bean:message bundle="dp" key="label.PO.ReqQuantity"/></span></FONT></TD>
				<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="166"><FONT color="white"><span class="white10heading mLeft5 pRight5 mTop5"><bean:message bundle="dp" key="label.PO.SelecSrNum"/></span></FONT></TD>
				<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="166"><FONT color="white"><span class="white10heading mLeft5 pRight5 mTop5"><bean:message bundle="dp" key="label.PO.TransferQty"/></span></FONT></TD>
				<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center"><FONT color="white"><span class="white10heading mLeft5 pRight5 mTop5"><bean:message bundle="dp" key="label.PO.Action"/></span></FONT></TD>
			</TR>
			</thead>
			
		<!-- iterate over the results of the query -->
		<tbody>
		<logic:notEmpty name="distributorDetailsList">
		<logic:iterate id="distributorDetails" name="distributorDetailsList" indexId="i">
			<TR>
				<TD><bean:write name="distributorDetails" property="productName"/></TD>
				<TD><bean:write name="distributorDetails" property="requestedQuantity"/></TD>
				<TD><a href="#" onclick="OpenSerialWindow('<bean:write name="distributorDetails" property="productId"/>', '<%=i%>', '<bean:write name="distributorDetails" property="requestedQuantity"/>');">Select Serial Numbers</a></TD>
				<TD><INPUT type="text" id="totalCount" name="totalCount" readonly="true"/></TD>
				<TD style="display: none"><INPUT type="text" id="serialNos" name="serialNos"/></TD>				
				<TD>
					<select styleId="action" id="action" name="action" onchange="resetOnCancel('<%=i%>');">
						<option value="0">Select Action</option>
						<option value="TRANSFER">Transfer</option>
						<option value="CANCEL">Cancel</option>							
					</select>
				</TD>											
			</TR>
		</logic:iterate>
		</logic:notEmpty>
		</tbody>
		</table>
		</td></tr>
		<input type="hidden" name="currentRowIndex" value="0">	
		<input type="hidden" name="reqQuanForTrans" value="0">
		</TABLE>
			
		<BR><BR>
		<TABLE border="0" align="Center" class="border" width="30%">
			<TR>
				<TD align="center"><INPUT type="button" name="submitButton" onclick="submitTransferDetails();" Class="medium" value=" SUBMIT "></TD>
			</TR>				
		</TABLE>
	</html:form>
</BODY>
</html:html>