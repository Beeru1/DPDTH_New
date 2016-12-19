<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>SHORT SHIPMENT SERIAL VERIFICATION</title>
<META http-equiv=Content-Type content="text/html; charset=iso-8859-1">
<link href="${pageContext.request.contextPath}/jsp/hbo/theme/text_new.css" rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/jScripts/progressBar.js" type="text/javascript">
</SCRIPT>
<style type="text/css">
.noScroll{
position: relative;
	 top: 0px;
	left: 0px;
	 top:expression(this.offsetParent.scrollTop-2);
  
}
.buttonSmall
{
   font-weight:bold;
   color:#000000;
   width:40px;
   height:20px;
   background-color:#BBBBBB;
}

</style>
<script type="text/javascript">
function checkForError()
{
	var deliveryChallanNo = document.getElementById("deliveryChallanNo").value;
	
	var req;
 	
 	if(window.XmlHttpRequest) {
		req = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		req=new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	if(req == null)
	{
		alert ("Your Browser Does Not Support AJAX!");
		return;
	} 
	document.getElementById("methodName").value="checkErrorDC";
	
	var url = "PoAcceptanceBulk.do";
	url = url + "?methodName=checkErrorDC&deliveryChallanNo="+deliveryChallanNo;
	
	req.onreadystatechange = function()
	{
		if(req.readyState == 4 || req.readyState == "complete")
		{
			if(req.status != 200)
			{
				alert("Error during Transaction");
				return false;
			}
			else
			{
				var text = req.responseText;
				if(text == "true")
				{ 
					//alert("Details of this DC are not visible due to some error, kindly contact your TSM");
					alert("This DC cannot be accepted due to PO error. kindly contact your TSM"); 
					document.getElementById("POfile").disabled=true;
					document.getElementById("Submit").disabled=true;
					document.getElementById("Missing").disabled=true;
					return false;
				}
				else
				{
					document.getElementById("POfile").disabled=false;
					document.getElementById("Submit").disabled=false;
					document.getElementById("Missing").disabled=false;
				}
				
			}
		}
		//setAjaxTextValues( req);
	}
	req.open("POST",url,false);
	req.send();
	
	
}
function validateFormWrong()
{
	// Validation on shortshipment.
	var deliveryChallanNo = document.getElementById("deliveryChallanNo").value;
	var availableSerials = document.getElementById("extraSerialsNoBox");
	var wrongSerialBox = document.getElementById("wrongSerialsNos");
 	var productID = document.forms[0].productId[0].selected;
	document.getElementById("prodId").value =productID;
	//var extraSerialBox = document.getElementById("extraSerialsNoBox");
		
	if(deliveryChallanNo == 0)
	{
		alert('Please select Delivery Challan No.');
		return false;
	}

		
	// Validation Check.
	if(availableSerials.length != 0)
	{
	  	alert("Please select all serial Number for Wrong shipment");
	  	return false;
    }
   
    
	// alert('Length extraSerialBox : ' + extraSerialBox.length);
	
	// alert('Length wrongSerialBox : ' + wrongSerialBox.length);
	if(wrongSerialBox.length > 0)
	{
	  var ind=wrongSerialBox.length;
      for (var i=0; i < ind ;i++) 
      {
        wrongSerialBox[i].selected=true;
      }  
    }
    
   // alert('Length availableSerials : ' + availableSerials.length);
	if(availableSerials.length > 0)
	{
	  var X=availableSerials.length;
      for (var i=0; i < X ;i++) 
      {
        availableSerials[i].selected=true;
      }
    }

	
	var arrwrongSerialsNos = document.getElementById("wrongSerialsNos");
	//alert("arrwrongSerialsNos::"+arrwrongSerialsNos);
	//var arrextraSerialsNoBox = document.getElementById("extraSerialsNoBox");
	var strextraSerialsNoBox;
	//var newArrextraSerialsNoBox;
	
	///if( arrwrongSerialsNos.length > 0  )
	//{
	//	for(var i = 0 ; i < arrwrongSerialsNos.length; i++)
	//	{
			//for(var j = 0 ; j < arrextraSerialsNoBox.length; j++)
			//{
				//alert("-->"+arrwrongSerialsNos.options[i].value);
				//newArrextraSerialsNoBox = arrwrongSerialsNos.options[i].value;
				//strextraSerialsNoBox = newArrextraSerialsNoBox[0];
				
		//	}
	//	}
	//}
		document.getElementById("methodName").value="acceptPOWrong";
	loadSubmit();
	return true;
}
function acceptPO()
{
	document.getElementById("methodName").value="acceptPO";
	loadSubmit();
	return true;
}
function unassignSerials(opt,dest)
  {
      var index = dest.length;
      var i = opt.length;

      for (var intLoop=0; intLoop < index; intLoop++) {

          if (dest[intLoop].selected==true) {
              opt[i]=new Option(dest[intLoop].text);
              //alert("Text: "+opt[i]);
              opt[i].value=(dest[intLoop].value).substring(0,dest[intLoop].value.indexOf('#'));
             // alert("Value: "+opt[i].value);

              i++;
          }
      }
      for (var intLoop=0; intLoop < index; intLoop++) {

          if (dest.options[intLoop]!=null && dest.options[intLoop].selected==true) {

              dest[intLoop]=null;
              intLoop=-1;
          }
      }
  }
/* This method is used to move functions between function panes */
function unassignAllSerials(opt,dest)
{
    var index=dest.length;
    var i=opt.length;

    for (var intLoop=0; intLoop < index; intLoop++) {

        opt[i]=new Option(dest[intLoop].text);
        
        opt[i].value=(dest[intLoop].value).substring(0,dest[intLoop].value.indexOf('#'));
        //alert(opt[i].value)
        
        i++;

    }
    for (var intLoop=0; intLoop < index; intLoop++) {

        if (dest.options[intLoop]!=null) {

            dest[intLoop]=null;
            intLoop=-1;
        }
    }
}
/* This method is used to move functions between function panes */
function assignSerials(opt,dest)
{
    var index=opt.length;
    var i=dest.length;
    var productID = document.getElementById("productId").value;
	if(productID == 0)
	{
	  alert('Please select any product.');
	  return false;
	}
    //alert('source length  ' + index);
    ///alert('dest length  ' + i);

    for (var intLoop=0; intLoop < index; intLoop++) 
    {
        if (opt.options[intLoop].selected==true) 
        {
            dest[i]=new Option(opt[intLoop].text);
            //alert("Text: "+dest[i]+","+opt[intLoop].text);
            dest[i].value= opt[intLoop].value+"#"+productID;
            //alert(dest[i].value);
            //alert("Value: "+dest[i].value+","+opt[intLoop].value);
            i++;
        }
    }
    
    for (var intLoop=0; intLoop < opt.length;intLoop++) 
    {
        if (opt.options[intLoop]!=null && opt.options[intLoop].selected==true) 
        {
            opt[intLoop]=null;
            intLoop=-1;
        }
    }

}
    /* This method is used to move functions between function panes */
    function assignAllSerials(opt,dest)
    {
//   		 alert("111");
        var index=opt.length;
        var i=dest.length;
		var productID = document.getElementById("productId").value;
		if(productID == 0)
		{
		  alert('Please select any product.');
		  return false;
		}
        for(var intLoop=0; intLoop < index; intLoop++) {
            dest[i]=new Option(opt[intLoop].text);
            dest[i].value= opt[intLoop].value+"#"+productID;
            i++;
	}
        for (var intLoop=0; intLoop < opt.length;intLoop++) {
            if (opt.options[intLoop]!=null) {
                opt[intLoop]=null;
                intLoop=-1;
            }
        }
    }    
    
    function getShortSerialNo()
	{
	    var deliveryChallanNo = document.getElementById("deliveryChallanNo").value;
		var url = "WrongShipment.do?methodName=getAllSerialNo&deliveryChallanNo="+deliveryChallanNo;
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
			return;
		}
		req.onreadystatechange = getShortSerialNoAJAX;
		req.open("POST",url,false);
		req.send();
	}

	function getShortSerialNoAJAX()
	{
		// Get all Received Serials
		if (req.readyState==4 || req.readyState=="complete") 
	  	{
			var xmldoc = req.responseXML.documentElement;
			if(xmldoc == null) 
			{
				return;
			}
			
			// Get all serials Nos.
			var optionValues = xmldoc.getElementsByTagName("option");
			var selectObj = document.getElementById("shortShipmentSerialsBox");
			selectObj.options.length = 0;
			for(var i=0; i<optionValues.length; i++)
			{
			    selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			}
			
			// Get all product Nos.
			var optionValues = xmldoc.getElementsByTagName("optionProductList");
			var selectObj = document.getElementById("productId");
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select Product ","0");
			for(var i=0; i<optionValues.length; i++)
			{
			    selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			}
								
			
			// Get deliveryChallanNo Text.
			var optionValuesTxt = xmldoc.getElementsByTagName("optionInvoiceNo");
			var selectObj = document.getElementById("invoiceNo");
			selectObj.value = optionValuesTxt[0].getAttribute("value");
		}
	}
	
function assignExtraSerials(extraBox)
{
	// Check Extra serials No
	var extraSerialNo = document.getElementById("extraSerialNo").value;
	if(extraSerialNo == 0)
	{
	  alert('Please enter new Serial No.');
	  document.getElementById("extraSerialNo").focus();
	  return false;
	}
	
	if(!isNumeric(extraSerialNo))
	{
	  alert('Input serial number should be numeric.');
	  document.getElementById("extraSerialNo").focus();
	  return false;
	}
	
	if(extraSerialNo.length != 11)
	{
	  alert('Input serial number should not be less than 11 digits.');
	  document.getElementById("extraSerialNo").focus();
	  return false;
	}
	
	// Check for product.
	var productID = document.getElementById("productId").value;
	if(productID == 0)
	{
	  alert('Please select any product.');
	  return false;
	}
	
	
 	var req;
 	
 	if(window.XmlHttpRequest) {
		req = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		req=new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	if(req == null)
	{
		alert ("Your Browser Does Not Support AJAX!");
		return;
	} 
	
	var url = "WrongShipment.do";
	url = url + "?methodName=checkWrongInventory&extraSerialNo="+extraSerialNo+"&productID="+productID;
	req.onreadystatechange = function()
	{
		if(req.readyState == 4 || req.readyState == "complete")
		{
			if(req.status != 200)
			{
				alert("Error during Transaction");
				return false;
			}
			else
			{
				var text = req.responseText;
				if(text == "true")
				{ 
					//alert("Serial Number already exist in Inventory");
					//document.getElementById("extraSerialNo").value="";
					//return false;
				}
				//else
				//{
					var inputvalue = document.getElementById("extraSerialNo").value;
				    var boxLength = extraBox.length;
				 	extraBox[boxLength]=new Option(inputvalue);
				 	extraBox[boxLength].value=inputvalue+'#'+productID;
				 	
				 	// Set blank
				 	document.getElementById("extraSerialNo").value="";
				 	
				//}
			}
		}
		//setAjaxTextValues( req);
	}
	req.open("POST",url,false);
	req.send();
}


function clearExtraSerials(extraClearBox)
{
   for (var intLoop=0; intLoop < extraClearBox.length; intLoop++) 
   {
        if (extraClearBox.options[intLoop]!=null && extraClearBox.options[intLoop].selected==true) 
        {
            extraClearBox[intLoop]=null;
            intLoop=-1;
        }
    }
}

function printPage()
{
 	if(document.forms[0].hidtkprint.value !=""){
			var productId=document.getElementById("prodId").value;
			var invoice_no=document.getElementById("invoice_no").value;
			var arr_total_sr_no=document.getElementById("totalRcvd").value;
	
			var url="DPPrintBulkAcceptanceAction.do?methodName=printBulkAcceptance&invoice_no="+invoice_no;
			window.open(url,"Print","width=700,height=600,scrollbars=yes,toolbar=no");	
			
				
  			}
 // printDCDetails.do?methodName=printDC&Dc_No=
  
}
function validateFormMissing()
{
	var deliveryChallanNo = document.getElementById("deliveryChallanNo").value;
		
	if(deliveryChallanNo == 0)
	{
		alert('Please select Delivery Challan No.');
		return false;
	}
	if(document.getElementById("POfile").value != "")
	{
		alert('Please do not Select File');
		return false;
	}
	
if(confirm("Clicking on Missing DC means you have physically not received these serial numbers & shipment will be rejected, Cross check & Confirm if you want to proceed"))
	{
		document.getElementById("methodName").value="missingDC";
		loadSubmit();
		return true;
	}
	else
	{
		return false;
	}
	
}
function validateForm()
{
	
	
	// Validation on shortshipment.
	var deliveryChallanNo = document.getElementById("deliveryChallanNo").value;
		
	if(deliveryChallanNo == 0)
	{
		alert('Please select Delivery Challan No.');
		return false;
	}
	if(document.getElementById("POfile").value == "")
	{
		alert('Please Select File to Upload');
		return false;
	}
	
	document.getElementById("methodName").value="uploadExcel";
	loadSubmit();
 	//document.forms[0].submit();
 	return true;
}


function resetAll()
{
	 var wrongSerialBox = document.getElementById("wrongSerialsNos");
	 for (var intLoop=0; intLoop < wrongSerialBox.length;intLoop++) 
	 {
         if (wrongSerialBox.options[intLoop]!=null) 
         {
             wrongSerialBox[intLoop]=null;
             intLoop=-1;
         }
     }
	
	 var extraSerialBox = document.getElementById("extraSerialsNoBox");
	 for (var intLoop=0; intLoop < extraSerialBox.length;intLoop++) 
	 {
         if (extraSerialBox.options[intLoop]!=null) 
         {
             extraSerialBox[intLoop]=null;
             intLoop=-1;
         }
     }
     
     var shortShipmentSerialsBox = document.getElementById("shortShipmentSerialsBox");
	 for (var intLoop=0; intLoop < shortShipmentSerialsBox.length;intLoop++) 
	 {
         if (shortShipmentSerialsBox.options[intLoop]!=null) 
         {
             shortShipmentSerialsBox[intLoop]=null;
             intLoop=-1;
         }
     }
     
     document.getElementById("deliveryChallanNo").value=0;
     document.getElementById("productId").value=0;     
	 document.getElementById("invoiceNo").value="";     
	 document.getElementById("extraSerialNo").value="";     
        
}

function onLoadForm()
{
	//alert("onload");
  document.getElementById("invoiceNo").value = '';  
  	//alert("onload");
}
</script>
</head>
<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>

<BODY onload="javascript:printPage();" background="<%=request.getContextPath()%>/images/bg_main.gif"  background="<%=request.getContextPath()%>/images/bg_main.gif" bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">
<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top">
	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include
			page="../common/dpheader.jsp" flush="" /></TD>
	</TR>
	<TR valign="top">
		<TD width="167" align="left" bgcolor="b4d2e7" valign="top" height="100%">
		<jsp:include page="../common/leftHeader.jsp" flush="" /></TD>
		<TD valign="top" width="100%" height="100%">
<html:form action="/PoAcceptanceBulk.do" method="post" name="DPPoAcceptanceBulkFormBean" type="com.ibm.dp.beans.DPPoAcceptanceBulkBean" enctype="multipart/form-data">
		<html:hidden property="methodName" styleId="methodName" />
		<html:hidden property="invoice_no" styleId="invoice_no" name="DPPoAcceptanceBulkFormBean"  />
		<html:hidden property="hidtkprint" styleId="hidtkprint" name="DPPoAcceptanceBulkFormBean"  />
		<html:hidden property="prodId" styleId="prodId" name="DPPoAcceptanceBulkFormBean"  />
		<html:hidden property="totalRcvd" styleId="totalRcvd" name="DPPoAcceptanceBulkFormBean"  />
		
		<div style="display:none">
	<logic:notEmpty property="totalSrNoList" name="DPPoAcceptanceBulkFormBean" >
				 		<!--<html:select  multiple="true"  property="arr_total_sr_no" styleId="arr_total_sr_no" style="width:155px; height:50px;"  size="6" >
							<logic:notEmpty name="DPPoAcceptanceBulkFormBean" property="totalSrNoList">
								 <html:optionsCollection name="DPPoAcceptanceBulkFormBean"	property="totalSrNoList" label="serial_no" value="serial_no"/> 
							</logic:notEmpty>
						</html:select>-->
						
						<logic:iterate id="totalSrNo" name="DPPoAcceptanceBulkFormBean" property="totalSrNoList" >
				 				<html:hidden name="DPPoAcceptanceBulkFormBean" property="arr_total_sr_no" styleId="arr_total_sr_no" value="${totalSrNo.serial_no}"/>
				 		</logic:iterate>
				 	
				 	</logic:notEmpty>
		</div>
		<html:hidden property="dcId" styleId="dcId" name="DPPoAcceptanceBulkFormBean" />
		<DIV class="scrollacc" style="OVERFLOW: auto; WIDTH: 100%; HEIGHT: 470px" align="center">
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<TR>
					<TD colspan="4" class="text">
					<IMG src="<%=request.getContextPath()%>/images/postockacceptance.JPG" width="544" height="26" alt=""></TD>
				</TR> 
				
				<TR>
					<TD colspan="4" class="text"><font color="red" size="2"><b><bean:write name="DPPoAcceptanceBulkFormBean" property="transMessage" /></b></font></TD>
				</TR> 
				
				
				
				
		  <tr>
	    		<td colspan="4" class="text">
				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
				  <tr>
				  	<td  align="center" >
				  		<STRONG><FONT color="#000000"><bean:message bundle="view" key="wrong.shipment.DeliveryChallanNo" /> :</FONT></STRONG>
				  	</td>
				  	
				  	<td  align="left" >
				  	<html:select property="deliveryChallanNo" style="width: 150px" name="DPPoAcceptanceBulkFormBean" onchange="return checkForError();">
						<html:option value="0">
						  <bean:message key="application.option.select" bundle="view" />
						</html:option>
						<logic:notEmpty name="DPPoAcceptanceBulkFormBean" property="dcNoList">
						  <html:options collection="dcNoList" labelProperty="deliveryChallanNo" property="deliveryChallanNo"  />
						</logic:notEmpty>
					</html:select>
				  	</td>
				  	<td align="right">
				  		<STRONG><FONT color="#000000"><bean:message bundle="view" key="lebel.reco.file" /> :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT></STRONG>
				  	</td>
				  	<td  align="left" >
				  		<FONT color="#003399"> 
				  		<html:file property="POfile"  name="DPPoAcceptanceBulkFormBean"></html:file>
						</FONT>
				  	</td>
				  		<td align="left" colspan="3">
				  			<input class="submit" type="submit" tabindex="4" name="Submit" value="Upload" onclick="return validateForm();">
				  			<input class="submit" type="submit" tabindex="5" name="Missing" value="Missing DC" onclick="return validateFormMissing();">
							</td>
				  </tr>
				  <tr>
				  <td>&nbsp;</td>
				  </tr>
				  <logic:equal name="DPPoAcceptanceBulkFormBean" property="error_flag" value="true" >
				  <tr>
				  	<td  align="center" colspan="5"><strong><font color="red">
					  The transaction could not be processed due to some errors. Click on <a href="PoAcceptanceBulk.do?methodName=showErrorFile">View Details </a> 
					  for details.</font><strong>
					 </td>
				  </tr>
				  </logic:equal>
				  
				  <logic:messagesPresent message="true">
				  	<html:messages id="msg" property="MESSAGE_SENT_SUCCESS" bundle="view" message="true">
				  		<font color="red" size="2"><strong><bean:write name="msg"/></strong></font>
					</html:messages>
					<html:messages id="msgfail" property="MESSAGE_SENT_FAIL" bundle="view" message="true">
				  		<font color="red" size="2"><strong><bean:write name="msgfail"/></strong></font>
					</html:messages>
					<html:messages id="msg" property="PO_FILE_TYPE_WRONG" bundle="view" message="true">
				  		<font color="red" size="2"><strong><bean:write name="msg"/></strong></font>
					</html:messages>
				</logic:messagesPresent>
				  
				  
				  <logic:notEmpty property="availableSerialList" name="DPPoAcceptanceBulkFormBean" >
				 		<logic:iterate id="availableSrNo" name="DPPoAcceptanceBulkFormBean" property="availableSerialList" >
				 				<html:hidden name="DPPoAcceptanceBulkFormBean" property="arr_available_sr_no" value="${availableSrNo.serial_no}"/>
				 		</logic:iterate>
				 	</logic:notEmpty>
				  
				  
				  
				  
				  
				  <logic:equal value="true" name="DPPoAcceptanceBulkFormBean" property="error_flag_wrong_short">
				   <tr>
				  	<td colspan="6">
				  	 <fieldset style="border-width:5px">
				  	 	<legend> <STRONG><FONT color="#C11B17" size="2"><bean:message bundle="view" key="wrong.shipment.ShortShipment" /> </FONT></STRONG></legend>
				  	
<DIV style="height: 140px;width: 100%; overflow-x:scroll; overflow-y:scroll;visibility: visible;z-index:1; left: 133px; top: 250px;"> 
		<table  align="center" border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse;" width='100%'>

				  		 
				  <tr class="noScroll">
				  	<td bgcolor="#CD0504" colspan="2" class="colhead" align="center"><FONT color="white"><strong><bean:message key="label.VPO.Sno." bundle="dp"/></strong></FONT></td>
				    <td bgcolor="#CD0504" colspan="2" class="colhead" align="center"><FONT color="white"><strong><bean:message key="label.po.stbSrNo" bundle="dp"/></strong></FONT></td>
				    <td bgcolor="#CD0504" colspan="2" class="colhead" align="center"><FONT color="white"><strong><bean:message key="label.PO.ProductName" bundle="dp"/></strong></FONT></td>
				  </tr>
				  
				 	<logic:notEmpty property="short_sr_no_list" name="DPPoAcceptanceBulkFormBean" >
				 		<logic:iterate id="shortSrNo" name="DPPoAcceptanceBulkFormBean" property="short_sr_no_list" indexId="i"  >
				 			<TR BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>' >
				 			<td  colspan="2" align="center"   class="text"><%=i.intValue()+1 %></td>
				 			<td  colspan="2" align="center"   class="text">
				 				<bean:write name="shortSrNo" property="serial_no" />
				 				<html:hidden name="DPPoAcceptanceBulkFormBean" property="arrShort_sr_no" value="${shortSrNo.serial_no}"/>
				 				<html:hidden name="DPPoAcceptanceBulkFormBean" property="arrProduct_id" value="${shortSrNo.product_id}"/>
				 			</td>
				 			<td  colspan="2" align="center"   class="text"><bean:write name="shortSrNo" property="product_name" /></td>
				 			</tr>
				 		</logic:iterate>
				 	</logic:notEmpty>	 
				  
			     		</table>
			     	</DIV>
						</fieldset>
			  		</td>
			  	</tr>
			  	  </logic:equal>
			  	 
			  	 <logic:equal value="false" name="DPPoAcceptanceBulkFormBean" property="error_flag_wrong_short">
				<tr><td colspan="6" align="center"><strong><font color="red" >
			  	 All Serial Numbers provided are matching with PO details. Kindly click Accept PO button to accept the PO</font></strong>
			  	 </td></tr>
			  	 <tr><td colspan="6">&nbsp;</td>
			  	 </tr>
			  	  <tr>
				  	<td colspan="6" align="center">
				  		<input class="submit" type="submit" tabindex="4" name="SubmitPO" value="Accept PO" onclick="return acceptPO();">
				  	</td>
				  </tr>
			  	 
			  	 
			  	 </logic:equal> 
			  	<tr>
				  <td colspan="6">&nbsp;</td>
				</tr>
				
			  	 <logic:equal value="true" name="DPPoAcceptanceBulkFormBean" property="error_flag_wrong_short">
			  	 
			  	 <tr>
				  	<td colspan="6">
				  	 <fieldset style="border-width:5px">
				  	 	<legend> <STRONG><FONT color="#C11B17" size="2"><bean:message key="label.PO.wrongstb" bundle="dp"/></FONT></STRONG></legend>
				  	 
				  	 <table border="0" width="100%">
				  		 
				  <tr>
					<td colspan="2" align="center" ><font color="#3C3C3C"> 
                     <strong> <bean:message key="wrong.shipment.InputExtraSerialNo" bundle="view"/></strong>
                   </td>
                    <td valign="top" colspan="2" align="center">&nbsp;</td>
                    <td colspan="2" align="center" ><font color="#3C3C3C"> 
                      <strong><bean:message key="wrong.shipment.InputExtraSerialNo" bundle="view"/></strong>
                   </td>
				    </tr>
				    <tr>
					<td colspan="2" align="center" ><font color="#3C3C3C"> <font color="#3C3C3C"><font color="#3C3C3C"><font color="#003399"> <font color="#003399"><font color="#003399"><font color="#3C3C3C"><font color="#3C3C3C"><font color="#3C3C3C"><font color="#3C3C3C"><font color="#003399"><font color="#003399"><font color="#3C3C3C"><font color="#3C3C3C"><font color="#3C3C3C"><font color="#3C3C3C">
                      <html:select property="extraSerialsNoBox" size="6" multiple="true"  style="width: 150px" name="DPPoAcceptanceBulkFormBean">
                          <html:optionsCollection value="serial_no" label="serial_no" property="extra_sr_no_list" name="DPPoAcceptanceBulkFormBean" />
                      </html:select>
                    </font></font></font></font></font></font></font></font></font></font></font></font> </font> </font></font> </font></td>
                    <td valign="top" colspan="2" align="center">
                    <input type="button" class="buttonSmall" onclick="assignAllSerials(extraSerialsNoBox,wrongSerialsNos)" class="btnActive mLeft5 mTop5 mBot5" width="150" value=" &gt;&gt; "><br>
                    <input type="button" class="buttonSmall" onclick="assignSerials(extraSerialsNoBox,wrongSerialsNos)" class="btnActive mLeft5 mTop5 mBot5" value="  &gt;  "><br>
                    <input type="button" class="buttonSmall" onclick="unassignAllSerials(extraSerialsNoBox,wrongSerialsNos)" class="btnActive mLeft5 mTop5 mBot5" width="150" value=" &lt;&lt; "><br>
                    <input type="button" class="buttonSmall" onclick="unassignSerials(extraSerialsNoBox,wrongSerialsNos)" class="btnActive mLeft5 mTop5 mBot5" value="  &lt;  ">
                     </td>
				    <td colspan="2" align="center">
                      <html:select property="wrongSerialsNos" size="6" multiple="true" style="width: 150px">
						<html:optionsCollection value="label" label="value" property="assignedSerialsSerialsList" />
                      </html:select>
                   	 </td>
					</tr>
					<TR>
						<TD align="center" width="15%">
						<b class="text pLeft15"><bean:message key="label.PO.Product_Name" bundle="dp" /><font color="red">*</font></b>
					</td>
					<td align="left" width="35%"><html:select property="productId" styleId="selectedProductId" style="width:150px" >
				<html:option value="0">--<bean:message key="label.PO.ProductList" bundle="dp" />--</html:option>
				<logic:notEmpty property="productList" name="DPPoAcceptanceBulkFormBean"><html:options labelProperty="productName" property="productId"
						collection="productList" />
				</logic:notEmpty>
			</html:select></td>
			<td colspan="4">&nbsp;</td>
			</tr>
			  		</table>
						</fieldset>
			  		</td>
			  	</tr>
			  	 
			  	 
			  	 </logic:equal>  
			  	  
			  	  <tr>
			  	  	<td colspan="4" align="center">
			  	  		 <logic:equal value="true" name="DPPoAcceptanceBulkFormBean" property="error_flag_wrong_short">
			  	  			<input class="submit" type="submit" tabindex="4" name="SubmitPOWrong" value="Submit" onclick="return validateFormWrong();">	
			  	  		</logic:equal>
			  	  		
			  	  	</td>
			  	  </tr>
			  	  
			  	  
			  	  
				</table>
			</td>
		  </tr>
		</table>
		</div>

		</html:form></TD>
	</TR>
	<!-- Added by Neetika for BFR 16 on 8 aug 2013 Release 3 -->
				<TR>
					<TD  align="center" colspan=5>
					<p style="visibility:hidden;" id="progress">
					<img id="progress_image" style="visibility:hidden;padding-left:55px;padding-top:500px;width:30;height:34" src="${pageContext.request.contextPath}/images/ajax-loader.gif" /> 
					</p>
					</TD>
				</TR>
	<TR align="center" bgcolor="4477bb" valign="top">
		<TD colspan="2" height="17" align="center">
		<jsp:include page="../common/footer.jsp" flush="" />
	</TD>
	</TR>
</TABLE>
</BODY>
</html>
