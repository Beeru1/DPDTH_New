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

<script type="text/javascript">
function printPage()
{
if(typeof(document.forms[0].hidtkprint)!="undefined"){
 	if(document.forms[0].hidtkprint.value !=""){
			var invoice_no=document.getElementById("invoiceNo").value;
			
			
			var url="DPPrintBulkAcceptanceAction.do?methodName=printBulkAcceptance&invoice_no="+invoice_no;
			window.open(url,"Print","width=950,height=800,scrollbars=yes,toolbar=no");	
			
				
  			}
  		}
 // printDCDetails.do?methodName=printDC&Dc_No=
  
}
function unassignSerials(opt,dest)
  {
      var index = dest.length;
      var i = opt.length;

      for (var intLoop=0; intLoop < index; intLoop++) {

          if (dest[intLoop].selected==true) {
              opt[i]=new Option(dest[intLoop].text);
              //alert("Text: "+opt[i]);
              opt[i].value=dest[intLoop].value;
              //alert("Value: "+opt[i].value);

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
        opt[i].value=dest[intLoop].value;
        //alert("Value: "+opt[i].value);
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
    
    //alert('source length  ' + index);
    ///alert('dest length  ' + i);

    for (var intLoop=0; intLoop < index; intLoop++) 
    {
        if (opt.options[intLoop].selected==true) 
        {
            dest[i]=new Option(opt[intLoop].text);
            //alert("Text: "+dest[i]+","+opt[intLoop].text);
            dest[i].value= opt[intLoop].value;
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
        var index=opt.length;
        var i=dest.length;

        for(var intLoop=0; intLoop < index; intLoop++) {
            dest[i]=new Option(opt[intLoop].text);
            dest[i].value= opt[intLoop].value;
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
	
	if(extraSerialNo.substring(0,1)!=0)
	{
	alert("Input serial number should start with zero.")
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
	var deliveryChallanNo = document.getElementById("deliveryChallanNo").value;
	var url = "WrongShipment.do";
	url = url + "?methodName=checkWrongInventory&extraSerialNo="+extraSerialNo+"&productID="+productID+"&deliveryChallanNo="+deliveryChallanNo;
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
					var inputvalue = document.getElementById("extraSerialNo").value;
				    var boxLength = extraBox.length;
				 	extraBox[boxLength]=new Option(inputvalue);
				 	extraBox[boxLength].value=inputvalue+'#'+productID;
				 	document.getElementById("extraSerialNo").value="";
				}
				else
				{
					alert(text);
					return false;
				}
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

function validateForm()
{
	
	
	// Validation on shortshipment.
	var deliveryChallanNo = document.getElementById("deliveryChallanNo").value;
	var availableSerials = document.getElementById("shortShipmentSerialsBox");
	var wrongSerialBox = document.getElementById("wrongSerialsNos");
	var extraSerialBox = document.getElementById("extraSerialsNoBox");
		
	if(deliveryChallanNo == 0)
	{
		alert('Please select Delivery Challan No.');
		return false;
	}

		
	// Validation Check.
	if(wrongSerialBox.length == 0 && extraSerialBox.length == 0)
	{
	  	var x=window.confirm("Please confirm no extra and short stock reported. Press OK! to accept the stock as you receiveD else Press CANCEL! .");
		if (!x)
		{
			return false;
		}
    }
    if(wrongSerialBox.length < extraSerialBox.length )
    {
    	alert("Extra serials should be equal 'or' less then wrong serials numbers.");
    	return false;
    }
    
	// alert('Length extraSerialBox : ' + extraSerialBox.length);
	if(extraSerialBox.length > 0)
	{
	  var X=extraSerialBox.length;
      for (var i=0; i < X ;i++) 
      {
        extraSerialBox[i].selected=true;
      }
    }
	
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
	var arrextraSerialsNoBox = document.getElementById("extraSerialsNoBox");
	var strextraSerialsNoBox;
	var newArrextraSerialsNoBox;
	
	if( arrwrongSerialsNos.length > 0  && arrextraSerialsNoBox.length > 0 )
	{
		for(var i = 0 ; i < arrwrongSerialsNos.length; i++)
		{
			for(var j = 0 ; j < arrextraSerialsNoBox.length; j++)
			{
				newArrextraSerialsNoBox = arrextraSerialsNoBox.options[j].value.split("#");
				strextraSerialsNoBox = newArrextraSerialsNoBox[0];
				if(strextraSerialsNoBox == arrwrongSerialsNos.options[i].value)
				{
					alert("Same Serial Numer can not be there in Short Shipment and Extra Shipment : "+strextraSerialsNoBox);
					return false;
				}
			}
		}
	}
	
	document.getElementById("methodName").value="submitWrongShipmentDetails";
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
	alert("onload");
  document.getElementById("invoiceNo").value = '';  
  	alert("onload");
}
</script>
</head>
<BODY  onload="javascript:printPage();" background="<%=request.getContextPath()%>/images/bg_main.gif"  background="<%=request.getContextPath()%>/images/bg_main.gif" bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
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
		<html:form	name="DPWrongShipmentForm" action="/WrongShipment" type="com.ibm.dp.beans.DPWrongShipmentBean">
		<html:hidden property="methodName" styleId="methodName" value="submitWrongShipmentDetails"/>
		<html:hidden property="hidtkprint" styleId="hidtkprint" name="DPWrongShipmentBean"  />
		
		<DIV class="scrollacc" style="OVERFLOW: auto; WIDTH: 100%; HEIGHT: 470px" align="center">
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<TR>
					<TD colspan="4" class="text"><BR>
					<IMG src="<%=request.getContextPath()%>/images/shipmenterrordetails.JPG" width="544" height="26" alt=""></TD>
				</TR> 
				
				<TR>
					<TD colspan="4" class="text"><font color="red" size="2"><b><bean:write name="DPWrongShipmentBean" property="transMessage" /></b></font></TD>
				</TR> 
				
				
				<TR>
					<TD colspan="4" class="text"> &nbsp;</TD>
				</TR> 
				
		  <tr>
	    		<td colspan="4" class="text">
				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
				  <tr>
				  	<td  align="right" >
				  		<STRONG><FONT color="#000000"><bean:message bundle="view" key="wrong.shipment.DeliveryChallanNo" /> :</FONT></STRONG>
				  	</td>
				  	
				  	<td  align="left" >
				  	<html:select property="deliveryChallanNo" onchange="getShortSerialNo()" style="width: 150px">
						<html:option value="0">
						  <bean:message key="application.option.select" bundle="view" />
						</html:option>
						<logic:notEmpty name="DPWrongShipmentBean" property="dcNoList">
						  <html:options collection="dcNoList" labelProperty="deliveryChallanNo" property="deliveryChallanNo" />
						</logic:notEmpty>
					</html:select>
				  	</td>
				  	<td align="center" colspan="2">	&nbsp;</td>
				  	<td align="right">
				  		<STRONG><FONT color="#000000"><bean:message bundle="view" key="wrong.shipment.InvoiceNo" /> :</FONT></STRONG>
				  	</td>
				  	<td  align="left" >
				  		<FONT color="#003399"> 
						<html:text	property="invoiceNo" styleClass="box" size="50" maxlength="64" name="DPWrongShipmentBean" style="width: 150px"/> 
						</FONT>
				  	</td>
				  </tr>
					
				 <tr>
					  	<td align="left" colspan="6">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				 </tr>
							 	  
				  <tr>
				  	<td colspan="6">
				  	 <fieldset style="border-width:5px">
				  	 	<legend> <STRONG><FONT color="#C11B17" size="2"><bean:message bundle="view" key="wrong.shipment.ShortShipment" /> </FONT></STRONG></legend>
				  	 
				  	 <table border="0" width="100%">
				  		 
				  <tr>
				  	<td  colspan="2" align="center"><font color="#000000"><strong>Received Serial Nos<span class="orange8">*</span>:</strong></font></td>
				    <td  colspan="2" align="center" ><font color="#000000"><strong></strong></font></td>
				    <td  colspan="2" align="center" ><font color="#000000"><strong>Missing Serial Nos<span class="orange8">*</span>:</strong></font></td>
				  </tr>
				  
				  
				  
			      <tr>
				    
					<td colspan="2" align="center" ><font color="#3C3C3C"> <font color="#3C3C3C"><font color="#3C3C3C"><font color="#003399"> <font color="#003399"><font color="#003399"><font color="#3C3C3C"><font color="#3C3C3C"><font color="#3C3C3C"><font color="#3C3C3C"><font color="#003399"><font color="#003399"><font color="#3C3C3C"><font color="#3C3C3C"><font color="#3C3C3C"><font color="#3C3C3C">
                      <html:select property="shortShipmentSerialsBox" size="8" multiple="true"  style="width: 150px">
                          <html:optionsCollection value="label" label="value" property="availableSerialList" />
                      </html:select>
                    </font></font></font></font></font></font></font></font></font></font></font></font> </font> </font></font> </font></td>
                    <td valign="top" colspan="2" align="center">
                    <input type="button" onclick="assignAllSerials(shortShipmentSerialsBox,wrongSerialsNos)" class="btnActive mLeft5 mTop5 mBot5" width="150" value=" &gt;&gt; "><br>
                    <input type="button" onclick="assignSerials(shortShipmentSerialsBox,wrongSerialsNos)" class="btnActive mLeft5 mTop5 mBot5" value="  &gt;  "><br>
                    <input type="button" onclick="unassignAllSerials(shortShipmentSerialsBox,wrongSerialsNos)" class="btnActive mLeft5 mTop5 mBot5" width="150" value=" &lt;&lt; "><br>
                    <input type="button" onclick="unassignSerials(shortShipmentSerialsBox,wrongSerialsNos)" class="btnActive mLeft5 mTop5 mBot5" value="  &lt;  ">
                     </td>
				    <td colspan="2" align="center">
                      <html:select property="wrongSerialsNos" size="8" multiple="true" style="width: 150px">
						<html:optionsCollection value="label" label="value" property="assignedSerialsSerialsList" />
                      </html:select>
                   	 </td>
					</tr>
			  		<!-- 
			  		<tr>
				  	<td  align="right" colspan="4">
				  		<STRONG><FONT color="#000000"><bean:message bundle="view" key="wrong.shipment.TotalShort" /> :</FONT></STRONG>
				  	</td>
				  	<td  align="center" colspan="2">
						<FONT color="#003399"> 
						<html:text	property="totalShortSerialNo" styleClass="box" size="19" tabindex="1" maxlength="64" name="DPWrongShipmentBean"/> 
						</FONT>
				  	</td>
				  </tr>
				   -->	
			  			
			  			</table>
						</fieldset>
			  		</td>
			  	</tr>
			  	
				<tr>
				  <td colspan="6">&nbsp;</td>
				</tr>
				
				<!-- Extra Shipped -->
				<tr>
				  	<td colspan="6">
			     <fieldset style="border-width:5px">
				  	 	<legend> <STRONG><FONT color="#C11B17" size="2"><bean:message bundle="view" key="wrong.shipment.ExtraShipment" /></FONT></STRONG></legend>
				  	 
					<table border="0" width="100%">
			
				 <tr>
				  	<td  colspan="2" align="center" width="320"><font color="#000000"><strong></strong></font></td>
				    <td  colspan="2" align="center" width="228"><font color="#000000"><strong></strong></font></td>
				    <td  colspan="2" align="center" width="338"><font color="#000000"><strong>Wrong/Extra Serial Nos<span class="orange8">*</span>:</strong></font></td>
				  </tr>
			      
			      <tr>
			      	<td  colspan="2" align="center" width="320">
			      	  <table width="90%">		      
			      		<tr>
						    <td align="center">
						     	<STRONG><FONT color="#000000"><bean:message bundle="view" key="wrong.shipment.InputExtraSerialNo" /> :</FONT></STRONG>
						  	</td>
						    
							<td  align="center">
								<FONT color="#003399"> 
								<html:text	property="extraSerialNo"  styleClass="box" style="width: 150px" maxlength="11" name="DPWrongShipmentBean"/> 
								</FONT>
						  	</td>
						  </tr>
						  
						  <tr>
						    <td align="center">
						     	<STRONG><FONT color="#000000"><bean:message bundle="view" key="wrong.shipment.ProductId" /> :</FONT></STRONG>
						  	</td>
						    
						    <td  align="center">
						    <html:select property="productId" style="width: 150px">
							<html:option value="0">
								<bean:message key="application.option.select" bundle="view" />
							</html:option>
							<bean:define id="product" name="DPWrongShipmentBean" property="productList" />
								<logic:notEmpty name="DPWrongShipmentBean" property="productList">
									<html:options collection="product" labelProperty="productName"	property="productId" />
								</logic:notEmpty>
							</html:select>
						   </td>
						  </tr>
						 </table>
						</td>
						
                    <td valign="middle" colspan="2" align="center" width="228">
                        <input type="button" onclick="assignExtraSerials(extraSerialsNoBox)" class="btnActive mLeft5 mTop5 mBot5" value="  &gt;  "><br>
                    	<input type="button" onclick="clearExtraSerials(extraSerialsNoBox)" class="btnActive mLeft5 mTop5 mBot5" value="  Clear  ">
                     </td>
				    <td colspan="2" align="center" width="338">
                      <html:select property="extraSerialsNoBox" size="8" multiple="true" style="width: 150px" >
						<html:optionsCollection value="label" label="value" property="assignedSerialsSerialsList" />
                      </html:select>
						
			  	  	</td>
					</tr>
				   </table>
				   </fieldset>
			  	  </td>
				</tr>
				
				   			   
				   
				  <tr>
					  <td colspan="6">&nbsp;</td>
				  </tr>
					<tr>
						<td align="right" colspan="3"><input class="submit" type="submit" tabindex="4"	name="Submit" value="Submit" onclick="return validateForm();">&nbsp;&nbsp;&nbsp;</td>
						<td align="left" colspan="3"><input class="submit" type="button" onclick="resetAll();" name="Submit2" value="Reset" tabindex="5"></td>
					</tr>
				</table>
			</td>
		  </tr>
		</table>
		</div>

		</html:form></TD>
	</TR>
	<TR align="center" bgcolor="4477bb" valign="top">
		<TD colspan="2" height="17" align="center">
		<jsp:include page="../common/footer.jsp" flush="" />
	</TD>
	</TR>
</TABLE>
</BODY>
</html>
