<!-- Modified by Mohammad Aslam -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="<%=request.getContextPath()%>/theme/text.css"
	rel="stylesheet" type="text/css">
<TITLE><bean:message bundle="dp" key="application.title" /></TITLE>
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<script language="JavaScript"
	src="<%=request.getContextPath()%>/scripts/POCreate.js"></script>
<script>
		var xmlHttp;
		function checkTransaction(){
			var businessCateg = document.getElementById("businessCategory").value;
			var distTransactionType = document.getElementById("hiddenTransaction").value;
			var transArray = distTransactionType.split(",");
			var isValid =0;
			//For business category 1 only transaction type 2 can be accepted
			if(businessCateg == 1){
				for(var count = 0 ; count< transArray.length ; count++){
					if(transArray[count] == 2){
						isValid =1;
					}
				}
			}else{
				for(var count = 0 ; count< transArray.length ; count++){
					if(transArray[count] == 1){
						isValid =1;
					}
				}
			}
			if(isValid != 1){
				alert("You are not authorised to work on this Business category!");
				return false;	
			}else{
//				showProduct();
				disablecategory();
				//getBalance();//Neetika commented it as it will be called on product selection BFR 2 28 July 2014
			}
			return true;
				
		}
		
		// jyotsna 
		
		function checkProductType(){
			//var productType = document.getElementById("productType").value;
			
			var businessCateg = document.getElementById("businessCategory").value;
			
			if(businessCateg == '')
			{
			 
			alert ('Please select Business Category.');
			document.getElementById("productType").selectedIndex  = 0;
			return false;
			}
			else
			{
				showProduct();
			  	if(document.getElementById("productType").value >0)
 				{
 				 //document.getElementById("productType").disabled=true;
 				}
 			    
			return true;
			}	
		}
		
		
		
		
		
		
	
		function showProduct(){ 
			document.forms[0].quantity.value = "";
			var selected = document.getElementById("businessCategory").value;
			var productType = document.getElementById('productType').value;
			xmlHttp=GetXmlHttpObject();
			if (xmlHttp==null){
  				alert ("Your Browser Does Not Support AJAX!");
 				return;
  			} 
  			
  			
  			
			var url="getProductList.do";
			url=url+"?methodName=getProductList&selected="+selected+"&productType="+productType;
			xmlHttp.onreadystatechange=stateChanged;
			xmlHttp.open("POST",url,true);
			xmlHttp.send();
		}
		
		
		function showQuantityAsOpenStock()
		{ 
			//alert('inside showQuantityAsPerDp');
			var prd_temp = document.getElementById('productName');
			var selPrdID = prd_temp.options[prd_temp.selectedIndex].value;
			var req = GetXmlHttpObject();
			if (xmlHttp == null){
  				alert ("Your Browser Does Not Support AJAX!");
 				return;
  			} 
			var url = "getQuantityAsPerDp.do";
			url = url + "?methodName=getQuantityAsOpenStock&productID=" + selPrdID;
			var elementId = "openStockAsPerDP" ;
			req.onreadystatechange = function()
			{
				setAjaxTextValues(elementId, req);
			}			
			req.open("POST",url,true);
			req.send();
		}
		
		function showQuantityAsPerDp()
		{ 
			//alert('inside showQuantityAsPerDp');
			var prd_temp = document.getElementById('productName');
			var selPrdID = prd_temp.options[prd_temp.selectedIndex].value;
			var req = GetXmlHttpObject();
			if (xmlHttp == null){
  				alert ("Your Browser Does Not Support AJAX!");
 				return;
  			} 
			var url = "getQuantityAsPerDp.do";
			url = url + "?methodName=getQuantityAsPerDp&productID=" + selPrdID;
			var elementId = "quantityAsPerDP" ;
			req.onreadystatechange = function()
			{
				setAjaxTextValues(elementId, req);
			}			
			req.open("POST",url,true);
			req.send();
		}
		
		function setAjaxTextValues(elementId, req){
		try
		{
			if (req.readyState == 4 || req.readyState == "complete"){
				//alert(req.status);
				if(req.status != 200){
					//alert("Session timeout");
					document.getElementById(elementId).value = "Exception During Transaction" ;
				}else{
				//	alert("Session not timeout");
					var text = req.responseText;
					//alert('Returned Quantity as per DP : '+ text);
					
					if(text != null)
					{
						if(isNaN(text))
						{
							window.location.href="./jsp/common/sessionErrorMessage.jsp";
						}
						else 
						{
							document.getElementById(elementId).value = text; 		
						}
					}
					else
						document.getElementById(elementId).value = "0" ; 
				}
			}
		}
		catch (e)
		{
			//alert("Session out");
		}
		}		
		
		function stateChanged(){ 
			if (xmlHttp.readyState == 4){ 
				var xmldoc = xmlHttp.responseXML.documentElement;
				if(xmldoc == null){		
					return;
				}
				optionValues = xmldoc.getElementsByTagName("option");
				var selectObj = document.getElementById("productName");
				selectObj.options.length = 0;
				selectObj.options[selectObj.options.length] = new Option("-Select A Product-","0");
				for(var i = 0; i < optionValues.length; i++){
					selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			    }
			   
			    
			    
			}
		}

		function GetXmlHttpObject(){
			var xmlHttp=null;
			try{
  				// Firefox, Opera 8.0+, Safari
	  			xmlHttp=new XMLHttpRequest();
	  		}
			catch (e){
	  			// Internet Explorer
	  			try{
	    			xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
	    		}
	  			catch (e){
	    			xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
	    		}
	  		}
			return xmlHttp;
		}

		/*
		function prodList(){
			var Index = document.getElementById("businessCategory").selectedIndex;
			var element=document.getElementById("businessCategory").options[Index].value;
			var prodCategory=element.substring(0,element.indexOf("#"));
			var url="getProductList.do";
			url=url+"?methodName=getProductList&selected="+selected;
			var elementId = "productName" ;
			ajax(url,elementId);
		}
		function ajax(url,elementId){
			if(window.XmlHttpRequest) {
				req = new XmlHttpRequest();
			}else if(window.ActiveXObject) {
				req=new ActiveXObject("Microsoft.XMLHTTP");
			}
			if(req==null) { 
				alert("Browser Doesnt Support AJAX");
				return;
			}
			req.onreadystatechange = function(){
				getAjaxValues(elementId);
			}
			req.open("POST",url,false);
			req.send();
		}
		function getAjaxValues(elementId){
			if (req.readyState==4 || req.readyState=="complete"){
				var xmldoc = req.responseXML.documentElement;				
				if(xmldoc == null){		
					var selectObj = document.getElementById(elementId);
					selectObj.options.length = 0;
					selectObj.options[selectObj.options.length] = new Option(" -Select "+ elementId + "-", "");
					return;
				}
				optionValues = xmldoc.getElementsByTagName("option");
				var selectObj = document.getElementById(elementId);
				selectObj.options.length = 0;
				selectObj.options[selectObj.options.length] = new Option(" -Select "+ elementId + "-", "");
				for(var i=0; i<optionValues.length; i++){
				   selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
				}		
			}		
		}
		*/
		var inputs = 0;
		function IsNumeric(strString){
		   //  check for valid numeric strings;
		   var strValidChars = "0123456789";
		   var strChar; 
		   var blnResult = true;
		   if (strString.length == 0) return false;
		   //  test strString consists of valid characters listed above
		   for (i = 0; i < strString.length && blnResult == true; i++){
		      strChar = strString.charAt(i);
		      if (strValidChars.indexOf(strChar) == -1){
		         blnResult = false;
		      }
		   }
		   return blnResult;
		}

		function validateData(){
		
		    var pn_temp = document.getElementById('productName');
		  	var pr_temp = document.getElementById('productType').value;
		  	
			var id_temp = pn_temp.options[pn_temp.selectedIndex].value;	
		 	var temp = document.getElementById('quantity').value;
		 //	var tempVar = document.getElementById('qtycol');
		    var tempVar = document.getElementsByName('qty');
		 	if(pr_temp == 1)
		 	{	
		 		if(tempVar.length >0){
		 				for(var counter=0;counter<tempVar.length;counter++){
							if(tempVar[counter].value != temp){
							alert("Product's quantity is same in case of Android.");
							document.getElementById('quantity').focus();
							return 0;
							}
					}								
				}
			}
			
			var Quantity = document.getElementById('quantity').value;
			if(document.getElementById('businessCategory').value == ""){
				alert("Please select Business Category");
				document.forms[0].businessCategory.focus();
				return 0;
			}
			
			if(document.getElementById('productType').value == ""){
				alert("Please select Product Type");
				document.forms[0].businessCategory.focus();
				return 0;
			}
			if(document.getElementById('productName').value == "0"){
				alert("Please select Product");
				document.forms[0].productName.focus();
				return 0;
			 }
			if(document.getElementById('quantity').value == "" || document.getElementById('quantity').value == '0'){
				alert("Please enter - Positive, non-zero numeric value!");
				document.getElementById('quantity').focus();
				return 0;
			}
			if (IsNumeric(document.getElementById('quantity').value) == false){
				alert("Please check - non numeric value!");
		        document.getElementById('quantity').focus();
			    return 0;
			}
			var prd_temp = document.getElementById('businessCategory');
			var id_temp = prd_temp.options[prd_temp.selectedIndex].text;
			//if(id_temp == 'CPE'){ 
				if(document.getElementById('inHandQuantity').value == "")
				{
					alert("Please enter - Positive numeric value in In Hand Quantity!");
					document.forms[0].inHandQuantity.focus();
					return 0;
				}
				else if(!isNumeric(document.getElementById('inHandQuantity').value))
				{
					alert("Please enter - Positive numeric value In Hand Quantity!");
					document.forms[0].inHandQuantity.focus();
					return 0;
				}
				else if(document.getElementById('inHandQuantity').value < 0)
				{
					alert("Please enter - Positive numeric value In Hand Quantity!");
					document.forms[0].inHandQuantity.focus();
					return 0;
				}
				else if(document.getElementById('inHandQuantity').value == "" )
				//|| document.getElementById('inHandQuantity').value == '0')
				{
					alert("Please enter - Positive numeric value!");
					document.getElementById('inHandQuantity').focus();
					return 0;
				}
				else if (IsNumeric(document.getElementById('inHandQuantity').value) == false)
				{
					alert("Please check - non numeric value!");
		        	document.forms[0].inHandQuantity.focus();
			    	return 0;
				}
			//}			
			return 1;
		}

		function chkQuan(){
		var inhandQuantity =document.getElementById('inHandQuantity').value ;
		var systemQuantity =document.getElementById('quantityAsPerDP').value ;
		var openStock =document.getElementById('openStockAsPerDP').value ;
		var totalStock =  parseInt(systemQuantity) + parseInt(openStock);
		var x=true;
		if(totalStock!=inhandQuantity){
			//x=window.confirm("Warning: You have entered incorrect quantity in 'In Hand Qty' field which is not equal to system stock. Your account might be debited for the difference.Click 'Ok' to continue or click 'Cancel' to change the 'In Hand Qty' field.");
			x=window.confirm("Attention: You have entered In Hand Qty. which is less than the stock indicated by system. Click 'Ok' to confirm or click 'Cancel' to change the 'In Hand Qty'.");
		}
		return x;
		}
		function addPORow(){
		//alert("addPORow++++");
		     
		     
			if(validateData() == 1){
			var bal = document.getElementById("balAmount").value;
			var quantity =document.getElementById('quantity').value ;
			var eligibleQuantity =document.getElementById('eligibleQuantity').value ;
			var validQuan = chkQuan();
			
			if(validQuan){
			
			
			var selected = document.getElementById("businessCategory").value
 			if(selected != null && selected == "1")
  			{
				if(eligibleQuantity<quantity){
				   var result = confirm("Requested quantity is greater than eligibile quantity. Kindly reconsider or click OK to continue");
				   if(result==false) 
				   return false;
				}
			}
			
			getTotalProductCost();
			
				document.forms[0].button1.disabled= false ;
			   	var table =  document.getElementById('contacts');
		    	var tr    =  document.createElement('TR');
		    	var td1   =  document.createElement('TD');
		    	var td2   =  document.createElement('TD');
		    	var td3   =  document.createElement('TD');
		    	var td_inHandQuantity   =  document.createElement('TD');
		    	var td_quantityAsPerDP   =  document.createElement('TD');
		    	var td_openStockAsPerDP   =  document.createElement('TD');	    	
		    	var td5   =  document.createElement('TD');
		    	
		     
		    	var inp1  =  document.createElement('INPUT');
				var bg =  document.getElementById("businessCategory");
			   	
				inp1.value = bg.options[bg.selectedIndex].text; 
			    var bgselect = inp1.value;
			    
			    var inp2  = document.createElement('INPUT');
			    var pn = document.getElementById('productName');
			   	inp2.value = pn.options[pn.selectedIndex].text;
			  	var pnselect = inp2.value;
			  	
			  	var inpID4  = document.createElement('INPUT');
			  	inpID4.setAttribute("type", "hidden");
			  	inpID4.setAttribute("name", "prdID");
			  	inpID4.setAttribute("id", "prdID");
				inpID4.value =	pn.options[pn.selectedIndex].value;
			
			    var inp3  = document.createElement('INPUT');
				inp3.value = document.getElementById('quantity').value; 
				var qtyselect = inp3.value;
				
				var inp_inHandQuantity  = document.createElement('INPUT');
				inp_inHandQuantity.value = document.getElementById('inHandQuantity').value; 
				var inhandqtyselect = inp_inHandQuantity.value;
				
				var inp_quantityAsPerDP  = document.createElement('INPUT');
				inp_quantityAsPerDP.value = document.getElementById('quantityAsPerDP').value; 
				var quantityAsPerDPselect = inp_quantityAsPerDP.value;
								
				var inp_openStockAsPerDP  = document.createElement('INPUT');
				inp_openStockAsPerDP.value = document.getElementById('openStockAsPerDP').value; 
				var openStockAsPerDPselect = inp_openStockAsPerDP.value;
		
			   //set attribute input text inp1 
			  		inp1.name="bg";
					inp1.id="bgcol";
					inp1.width="200";
					inp1.style.fontSize ="11";
					inp1.readOnly = true ;
			 	//set attribute input text inp2 
					inp2.name="pn";
					inp2.id="pncol";
					inp2.width="200";
					inp2.style.fontSize ="11";
					inp2.readOnly = true ;
			 	//set attribute input text inp3 
					inp3.name="qty";
					inp3.id="qty";
					inp3.width="75";
					inp3.style.fontSize ="11";
					inp3.readOnly = true ;
				//set attribute input text inp_inHandQuantity 
					inp_inHandQuantity.name="inHandQty";
					inp_inHandQuantity.id="inhandqtycol";
					inp_inHandQuantity.width="100";
					inp_inHandQuantity.style.fontSize ="11";
					inp_inHandQuantity.readOnly = true ;
				//set attribute input text inp_quantityAsPerDP
					inp_quantityAsPerDP.name="qtyDP";
					inp_quantityAsPerDP.id="qtyDPCol";
					inp_quantityAsPerDP.width="150";
					inp_quantityAsPerDP.style.fontSize ="11";
					inp_quantityAsPerDP.readOnly = true ;
				//set attribute input text inp_openStockAsPerDP 
					inp_openStockAsPerDP.name="opStkDP";
					inp_openStockAsPerDP.id="openStcol";
					inp_openStockAsPerDP.width="100";
					inp_openStockAsPerDP.style.fontSize ="11";
					inp_openStockAsPerDP.readOnly = true ;
					
					
			
					  	var inpIDEligibleAmt  = document.createElement('INPUT');
			  			inpIDEligibleAmt.setAttribute("type", "hidden");
			  			inpIDEligibleAmt.setAttribute("name", "balAmount1");
					  	inpIDEligibleAmt.setAttribute("id", "balAmount1");		
						inpIDEligibleAmt.value = parseFloat(document.getElementById("balanceAmount").value);
					//alert("addPORow2.inpIDEligibleAmt."+inpIDEligibleAmt.value);
						var inpIDEligibleQty  = document.createElement('INPUT');
					  	inpIDEligibleQty.setAttribute("type", "hidden");
					  	inpIDEligibleQty.setAttribute("name", "balQty");
					  	inpIDEligibleQty.setAttribute("id", "balQty");
						inpIDEligibleQty.value = parseFloat(document.getElementById("eligibleQty").value);
					//alert("addPORow3.elig."+inpIDEligibleQty.value);
						var inpIDMaxPOQty = document.createElement('INPUT');
					  	inpIDMaxPOQty.setAttribute("type", "hidden");
					  	inpIDMaxPOQty.setAttribute("name", "maxPOQty");
					  	inpIDMaxPOQty.setAttribute("id", "maxPOQty");
						inpIDMaxPOQty.value = parseFloat(document.getElementById("maxPoQty").value);
						//alert("addPORow4.max po."+inpIDMaxPOQty.value);
						var inpIDFlag= document.createElement('INPUT');
			  			inpIDFlag.setAttribute("type", "hidden");
			  			inpIDFlag.setAttribute("name", "flag1");
			  			inpIDFlag.setAttribute("id", "flag1");
						inpIDFlag.value = document.getElementById("flag").value;
				
						var inpIDTotalCost= document.createElement('INPUT');
			  			inpIDTotalCost.setAttribute("type", "hidden");
			  			inpIDTotalCost.setAttribute("name", "totalCost");
			  			inpIDTotalCost.setAttribute("id", "totalCost");
						inpIDTotalCost.value = document.getElementById("totalProductCost").value;
					
								
						var arry = new Array();
						
						if(document.getElementById('productType').value != 1)
						{
							if(inputs>-1){
		    						var img = document.createElement('button');
		        					img.value="Remove";
									//	img.setAttribute('src', 'images/delete.gif');
		        					img.onclick = function(){
		        					removeProductCost(inpIDTotalCost.value);
		            				removeContact(tr);
		            				document.getElementById('productName').selectedIndex=0;
		            				document.getElementById("eligibleQuantity").value = "";
		        					}
		        					td5.appendChild(img);
		    				}
		    		
		    			}
		    			else
		    			{
		    			   	if(inputs == 0){
		    			   	
		    						var img1 = document.createElement('button');
		        					img1.value="Remove";
									//	img.setAttribute('src', 'images/delete.gif');
		        				    
		        					img1.onclick = function(){
		    						var rowCount = table.rows.length;                        
		                            var tempVar = document.getElementsByName('totalCost');
									if(tempVar.length >0){
										for(var x=0;x<tempVar.length;x++){
						                       removeProductCost(tempVar[x].value);
		            					}								
			                        }
			                            					
			                        for(var i=0; i<rowCount; i++) {
                 						 if(i != 0) {
                      								var row = table.rows[i];
                		    						table.deleteRow(i);
                   		 							rowCount--;
                    								i--;
                    								inputs = 0;
                    						}
                           				}
		        														 		        					
		            				document.getElementById('productName').selectedIndex=0;
		            				document.getElementById("eligibleQuantity").value = "";
		        					}
		        				
		        				
		        				
		        					td5.appendChild(img1);
		    				     }
		    			}
		    
			   			 table.appendChild(tr);
			    		// tr.appendChild(td3);
			    		td1.appendChild(inp1);
			    		td2.appendChild(inp2);
			    		td3.appendChild(inp3);
			    		td_inHandQuantity.appendChild(inp_inHandQuantity); 
			    		td_quantityAsPerDP.appendChild(inp_quantityAsPerDP);
			    		td_openStockAsPerDP.appendChild(inp_openStockAsPerDP);   
		
						tr.appendChild(td1);
			    		tr.appendChild(td2);
					    tr.appendChild(td3);
			    		tr.appendChild(td_inHandQuantity);
			    		tr.appendChild(td_quantityAsPerDP);
			    		tr.appendChild(td_openStockAsPerDP);
					    tr.appendChild(td5);
		   
		    			tr.appendChild(inpID4);
		    	
		    			tr.appendChild(inpIDEligibleAmt);
		    			tr.appendChild(inpIDEligibleQty);
		    			tr.appendChild(inpIDMaxPOQty);
		    			tr.appendChild(inpIDFlag);
		    			tr.appendChild(inpIDTotalCost);
		     			inputs++;
		     			return true;
					 }
				}
			else
			{
				return false;
			} 
		}

		function removeContact(tr){
		    tr.parentNode.removeChild(tr);
		}
		
		
		
		function removeProductCost(val){
		var bal = document.getElementById("balAmount").value;
		var totProductCost =val;
		var balAmt = parseFloat(bal)+ parseFloat(totProductCost);
			//alert("removeProductCost.."+balAmt);
		document.getElementById("balAmount").value = parseFloat(balAmt.toFixed(2));
		}
		
		
		function resetval(on)
		{
			if(on === 1)
			{				
				var tabBody = document.getElementById("contacts");
				var tabRowsLen = tabBody.rows.length;
				var pr_temp = document.getElementById('productType').value;
			    if(pr_temp == 1)
			    {
			   	 var pnLen = document.getElementById("productName").length;
			        if( ( pnLen) != tabRowsLen)
			        {
			         alert("Please add all Product in case of Android.")
			         return false;
			        }
			      
			    }
			   
			   if(tabRowsLen<2)
				{
					alert("Add Requisition to the List Below");
					return false;
				}
				else
				{
					document.getElementById('businessCategory').value == "";
					document.getElementById('productName').value == "0";
					document.forms[0].quantity.value = "";
					var pn = document.getElementById('prdID');
					if(pn != null)
					{
						document.forms[0].submit();
					}
				}
			}
			else
			{
				document.forms[0].quantity.value = "";				
			}
		}
		function checkKeyPressed(){
			if(window.event.keyCode=="13"){
				if(validateData() == 1){
					alert("Add Requisition to the List Below");
					return false;
				}else{
					return false;
				}
			}
		}
		
		function visible(){
			document.forms[0].button1.disabled=true ;
		}
 
	
		function checkProductDuplicate(){
		
			
			var flag = false;
		 	var pn_temp = document.getElementById('productName');
			var id_temp = pn_temp.options[pn_temp.selectedIndex].value;	
			var tempVar = document.getElementsByName('prdID');
			if(tempVar.length >0){
				for(var counter=0;counter<tempVar.length;counter++){
					if(tempVar[counter].value == id_temp){
						alert("Product is already selected, Please choose another Product ..");
						document.getElementById('productName').selectedIndex=0;
						return false;
					}
				}								
			}
			getEligibilty();
		}
		
		
		function checkQuantity(){
			var flag = false;
			
			var pn_temp = document.getElementById('productName');
		  	var pr_temp = document.getElementById('productType').value;
		  	
			var id_temp = pn_temp.options[pn_temp.selectedIndex].value;	
		 	var temp = document.getElementById('quantity').value;
		 //	var tempVar = document.getElementById('qtycol');
		    var tempVar = document.getElementsByName('qty');
		 	if(pr_temp == 1)
		 	{	
		 		if(tempVar.length >0){
		 				for(var counter=0;counter<tempVar.length;counter++){
							if(tempVar[counter].value != temp){
							alert("Product's quantity is same in case of Android.");
							document.getElementById('quantity').value=0;
							return false;
							}
			
					}								
				}
			}
			
		}
		
		
		function checkProduct(){
			var flag = false;
		 	var pn_temp = document.getElementById('productName');
			var id_temp = pn_temp.options[pn_temp.selectedIndex].value;	
			var tempVar = document.getElementsByName('prdID');
			if(tempVar.length >0){
				for(var counter=0;counter<tempVar.length;counter++){
					if(tempVar[counter].value == id_temp){
						alert("Product is already selected, Please choose another Product ..");
						return false;
					}
				}
				
				 flag = addPORow();		
				
			}else{
			
				 flag = addPORow();
			}
			
			if(flag)
			{
				// Reset all the fields
				document.getElementById('productName').value = "0";
				document.getElementById('quantity').value = "";
				document.getElementById('inHandQuantity').value = "";
				document.getElementById('quantityAsPerDP').value = "";
				document.getElementById("eligibleQuantity").value = "";
				//document.getElementById('balAmount').value = "";
				//document.getElementById('openStockAsPerDP').value = "";
			}
		}

		function textareaMaxlength() {
			var size=50;
			var address = document.getElementById("comments").value;
		   	if (address.length >= size) {
			   	address = address.substring(0, size-1);
			   	document.getElementById("comments").value = address;
			    alert ("Comments field  can contain 50 characters only.");
			   	document.getElementById("comments").focus();
				return false;
			}
		}

		function productUnit(){
			var Index = document.getElementById("productName").selectedIndex;
			var productName=document.getElementById("productName").options[Index].value;
			var url= "InsertPurchaseOrder.do?methodName=getProductUnit&cond1="+productName;
			var elementId = "showUnit" ;
			ajaxText(url,elementId,"text");
			resetval(0);
		}
		function ajaxText(url,elementId,text){
			if(window.XmlHttpRequest) {
				req = new XmlHttpRequest();
			}else if(window.ActiveXObject) {
				req=new ActiveXObject("Microsoft.XMLHTTP");
			}
			if(req==null) { 
				alert("Browser Doesnt Support AJAX");
				return;
			}
			req.onreadystatechange = function(){
				getAjaxTextValues(elementId);
			}
			req.open("POST",url,false);
			req.send();
		}
		function getAjaxTextValues(elementId){
		try
		{	
			if (req.readyState==4 || req.readyState=="complete"){
				var text=req.responseText;
				 if(text.length!=0){
				 	document.getElementById(elementId).innerHTML=text;
				 	//document.getElementById(elementId).value = text;
				 }
				else if (text.length==0){
				 	document.getElementById(elementId).style.display="none";
				}
			}
		}
		catch(e)
		{
			//alert("session out in getAjaxTextValues");
		}
		}

 		function disablecategory(){
			if(document.getElementById("businessCategory").value >0)
 				document.getElementById("businessCategory").disabled=true;
 		
 		}
 		
 		
 		function getBalance(){
 			//alert("getBalance++++");
 			var selected = document.getElementById("businessCategory").value
 			if(selected != null && selected == "1")
  			{
  				document.getElementById("td1").style.display="inline";
				document.getElementById("td2").style.display="inline";
				document.getElementById("td3").style.display="inline";
				document.getElementById("td4").style.display="inline";
				var Index = document.getElementById("productName").selectedIndex;
				var productName=document.getElementById("productName").options[Index].value;
				//var productId = document.getElementById("productName").value;
				//alert(Index+" productName "+productName);
  			}
  			else
  			{
  				document.getElementById("balAmount").value="0";
  				return;
  			}
 			
			//var url= "InsertPurchaseOrder.do?methodName=getBalance";
			var url= "InsertPurchaseOrder.do?methodName=getBalance&productId="+productName;
			var elementId = "balAmount" ;			
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
			req.onreadystatechange = getAjaxBalance;
			req.open("POST",url,false);
			req.send();		
			
		}
		function getAjaxBalance()
	{
		if (req.readyState==4 || req.readyState=="complete") 
	  	{
			var xmldoc = req.responseXML.documentElement;
			if(xmldoc == null) 
			{
				return;
			}
			
			// Get Conditions.
			optionValues = xmldoc.getElementsByTagName("option");						
			var balance = optionValues[0].getAttribute("balance");
			document.getElementById("balAmount").value=parseFloat(balance);
		}
	}	
	
	function getTotalProductCost()
	{
	//alert("getTotalProductCost this is setting eligible qty");
	var totProductCost = document.getElementById("totalProductCost").value;
	var productId = document.getElementById("productName").value;
	var quantity = document.getElementById("quantity").value;
	var balAmt = document.getElementById("balAmount").value;
	//alert("getTotalProductCost"+balAmt);
			var url= "InsertPurchaseOrder.do?methodName=getTotalProductCostQty&productId="+productId+"&quantity="+quantity+"&balAmt="+balAmt;
			var elementId = "balAmount" ;			
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
			req.onreadystatechange = getAjaxTotalProduct;
			req.open("POST",url,false);
			req.send();	
	}
	
		function getAjaxTotalProduct()
	{
		if (req.readyState==4 || req.readyState=="complete") 
	  	{
			var xmldoc = req.responseXML.documentElement;
			if(xmldoc == null) 
			{
				return;
			}
			
			// Get Conditions.
			optionValues = xmldoc.getElementsByTagName("option");						
			var balance = optionValues[0].getAttribute("balance");	
			
			var balanceBefore = optionValues[0].getAttribute("balanceBefore");
			//alert("balance---"+balance+"balanceBefore---"+balanceBefore);
			var totalProductCost = optionValues[0].getAttribute("totalProductCostStr");	
			var eligibleQty = optionValues[0].getAttribute("eligibleQty");
			var maxPoQty = optionValues[0].getAttribute("maxPoQty");	
			var flag = optionValues[0].getAttribute("flag");	
			//alert("eligibleQty---"+eligibleQty+"maxPoQty---"+maxPoQty);		
			document.getElementById("totalProductCost").value=parseFloat(totalProductCost);
			document.getElementById("balAmount").value=parseFloat(balance);
			document.getElementById("balanceAmount").value=parseFloat(balanceBefore);
			document.getElementById("eligibleQty").value=parseFloat(eligibleQty);
			document.getElementById("maxPoQty").value=parseFloat(maxPoQty);
			document.getElementById("flag").value=flag;
			
		}
	}	
	
	function getEligibilty(){
	var product = document.getElementById("productName").value;
	var balAmt = document.getElementById("balAmount").value;
		var url= "InsertPurchaseOrder.do?methodName=getEligibleQty&product="+product+"&balAmt="+balAmt;
			var elementId = "eligibleQuantity" ;			
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
			req.onreadystatechange = getAjaxEligibleQty;
			req.open("POST",url,false);
			req.send();		
	
	}
	function getAjaxEligibleQty()
	{
		if (req.readyState==4 || req.readyState=="complete") 
	  	{
			var xmldoc = req.responseXML.documentElement;
			if(xmldoc == null) 
			{
				return;
			}
					
			optionValues = xmldoc.getElementsByTagName("option");						
			var eligibleQuantity = optionValues[0].getAttribute("eligibleQuantity");				
			document.getElementById("eligibleQuantity").value=parseFloat(eligibleQuantity);						
		}
	}	

	</script>
</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"
	bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onload="javascript:visible();"
	onkeypress="return checkKeyPressed();">
<html:form action="/InsertPurchaseOrder">
	<IMG
		src="<%=request.getContextPath()%>/images/createPurchaseOrderRequisit.gif"
		width="598" height="22" alt="" />
	<html:hidden property="methodName" value="insertPurchaseOrder" />
	<html:hidden property="totalProductCost" />
	<html:hidden property="eligibleQty" />
	<html:hidden property="maxPoQty" />
	<html:hidden property="flag" />
	<html:hidden property="balanceAmount" />
	<html:hidden property="hiddenTransaction"  name="CreatePorBean"/>
	
	<%if(session.getAttribute("FNFdone")!=null && (session.getAttribute("FNFdone").toString().equalsIgnoreCase("")))
{ %>

<table align="center" border="0" cellpadding="0" cellspacing="0" style="border-collapse: collapse;" width='100%'>

<tr>

<TD align="center" width="100%" class="text"><STRONG><FONT color="RED">
<bean:message  bundle="dp" key="label.Dist.FNFINIT"/></FONT>
</STRONG></TD>
</table>

<% }

else { %>
	
	<TABLE border="0" align="Center" class="border" width="100%" bordercolor='RED'>
		<TBODY>
			<TR>
				<TD colspan='5'>
					<logic:messagesPresent message="true">
						<html:messages property="file" id="pomsg" bundle="dp"
							message="true">
							<strong><font color="red"> <bean:write
								name="pomsg" /></font>
						</html:messages>
					</logic:messagesPresent>
				</TD>
			</TR>
			
			
			
			<TR>
				<TD width='10%'>&nbsp;</TD>
				<TD align="left" class="text pLeft15" width="20%"><strong><font
					color="#000000"><bean:message bundle="dp"
					key="label.PO.BCategoryList" /></font><FONT COLOR="RED">*</font></TD>
				<TD colspan="3" width="70%"><html:select
					styleId="businessCategory" property="businessCategory"
					onchange="checkTransaction();" name="CreatePorBean"
					style="height:20px; width:180px;">
					<html:option value="">-Select A Category-</html:option>
					<bean:define id="categorylist" name="CreatePorBean"
						property="categoryList"></bean:define>
					<logic:notEmpty name="CreatePorBean" property="categoryList">
						<html:options style="" collection="categorylist" property="value"
							labelProperty="label" />
					</logic:notEmpty>
				</html:select></TD>
			</TR>
			<TR>
			<!--Jyotsna -->
			
			<TR>
			<TD width='10%'>&nbsp;</TD>
				<TD align="left" class="text pLeft15" width="20%"><strong><font
					color="#000000"><bean:message bundle="dp"
					key="label.PO.ProductType" /></font><FONT COLOR="RED">*</font></TD>
				<TD colspan="3" width="70%">
				<html:select
					styleId="productType" property="productType"
					onchange="checkProductType();" name="CreatePorBean"
					style="height:20px; width:180px;">
					<html:option value="">-Select-</html:option>
					<bean:define id="productTypeList" name="CreatePorBean"
						property="productTypeList"></bean:define>
					<logic:notEmpty name="CreatePorBean" property="productTypeList">
						<html:options style="" collection="productTypeList" property="value"
							labelProperty="label" />
					</logic:notEmpty>
				</html:select>
				</TD>
			</TR>
			
			
				<TD width='10%'>&nbsp;</TD>
				<TD align="left" class="text pLeft15" height="29" width="20%"><strong><font
					color="#000000"><bean:message bundle="dp"
					key="label.PO.ProductList" /></font><FONT COLOR="RED">*</font></TD>
				<TD width="30%"><html:select
					property="productName" styleId="productName" 
					style="height:20px;width:180px;"
					onchange="getBalance();productUnit();showQuantityAsPerDp();showQuantityAsOpenStock();checkProductDuplicate();"> <!-- Neetika getbalance added -->
					<html:option value="0">-Select A Product-</html:option>
				</html:select></TD>
				
				<TD id="td1" align="left" class="text pLeft15" height="29" width="10%" style="display: none;"><strong><font
					color="#000000"><bean:message bundle="dp"
					key="label.Dist.Balance" /></font></TD>
				<TD id="td2" width="50%" style="display: none;"><html:text
					property="balAmount" styleId="balAmount" readonly="true"
					styleClass="box" style="width:176" tabindex="4" size="19"
					maxlength="8" /></TD>
			</TR>
			<TR>
				<TD width='10%'>&nbsp;</TD>
				<TD align="left" class="text pLeft15" height="31" width="20%">
				<strong><font color="#000000"><bean:message
					bundle="dp" key="label.PO.Quantity" /></font><FONT COLOR="RED">*</font></TD>
				<TD height="31" width="30%" >
				<html:text property="quantity"
					styleId="quantity" styleClass="box" style="width:176" tabindex="4"
					size="19" maxlength="8" /> &nbsp;<font color="red"><b
					id="showUnit"></b></font>
			   </TD>
				<TD id="td3" align="left" class="text pLeft15" height="29" width="10%" style="display: none;" ><strong><font
					color="#000000"><bean:message bundle="dp"
					key="label.Dist.EligibiltyQty" /></font></TD>
				<TD id="td4" width="50%" style="display: none;" ><html:text
					property="eligibleQuantity" styleId="eligibleQuantity" readonly="true"
					styleClass="box" style="width:176" tabindex="4" size="19"
					maxlength="8" /></TD>

			</TR>
			<TR>
				<TD width='10%'>&nbsp;</TD>
				<TD align="left" class="text pLeft15" height="31" width="20%"><strong><font
					color="#000000"><bean:message bundle="dp"
					key="label.PO.InHandQuantity" /></font><FONT COLOR="RED">*</font></TD>
				<TD height="31" width="70%" colspan="3" >
				<html:text
					property="inHandQuantity" styleId="inHandQuantity" styleClass="box"
					style="width:176" tabindex="4" size="19" maxlength="8" onchange="checkQuantity();" /> &nbsp;<font
					color="red"><b id="showUnit"></b></font></TD>
			</TR>
			<TR>
				<TD width='10%'>&nbsp;</TD>
				<TD align="left" class="text pLeft15" height="31" width="20%"><strong><font
					color="#000000"><bean:message bundle="dp"
					key="label.PO.QuantityAsPerDP" /></font><FONT COLOR="RED">*</font></TD>
				<!-- Quantity As Per System -->
				<TD height="31" width="70%" colspan="3" ><html:text
					property="quantityAsPerDP" styleId="quantity" styleClass="box"
					style="width:176" tabindex="4" size="19" maxlength="8"
					readonly="true" /> &nbsp;<font color="red"></font></TD>
			</TR>
			<TR style="display:none;">
				<TD width='10%'>&nbsp;</TD>
				<TD align="left" class="text pLeft15" height="31" width="20%"><strong><font
					color="#000000"><bean:message bundle="dp"
					key="label.PO.OpeningStockAsPerDP" /></font><FONT COLOR="RED">*</font></TD>
				<!-- Open stock As Per provided by Botree -->
				<TD height="31" width="70%" colspan="3"  ><html:text
					property="openStockAsPerDP" styleId="quantity" styleClass="box"
					style="width:176" tabindex="4" size="19" maxlength="8"
					readonly="true" /> &nbsp;<font color="red"></font></TD>
			</TR>


			<TR>
				<TD width='10%'>&nbsp;</TD>
				<TD align="left" class="text pLeft15" width="20%"><strong><font
					color="#000000"><bean:message bundle="dp"
					key="label.PO.Comments" /></font>
				<TD width="70%" colspan="3" ><html:textarea property="comments"
					onkeyup="return textareaMaxlength();" value="Enter RTGS Details"></html:textarea>
				</TD>

			</TR>
			<TR>
				<TD width='30%' colspan='2'>&nbsp;</TD>
				<TD width='30%' align="center"><INPUT type="button"
					name="button" onclick="checkProduct();" Class="medium" value="Add"></TD>
				<TD width='40%' colspan='2'>&nbsp;</TD>
			</TR>
			<TR>
				<TD width='30%' colspan='2'>&nbsp;</TD>
				<TD width='30%' align="center"><INPUT type="button"
					name="button1" onclick="resetval(1);" Class="medium" value="Submit"></TD>
				<TD width='40%' colspan='2'>&nbsp;</TD>
			</TR>
	</TABLE>

	<div id="divide">
	<table align="center" class="mLeft5" styleId="tblSample" width="100%">
		<tbody id="contacts">
			<TR>
				<TD bgcolor="#CD0504" class="width104 height19 pLeft5"
					align="center" width="20%"><FONT color="white"><span
					class="white10heading mLeft5 pRight5 mTop5"><bean:message
					bundle="dp" key="label.PO.BCategoryList" /></span></TD>
				<TD bgcolor="#CD0504" class="width104 height19 pLeft5"
					align="center" width="20%"><FONT color="white"><span
					class="white10heading mLeft5 pRight5 mTop5"><bean:message
					bundle="dp" key="label.PO.ProductName" /></span></TD>
				<TD bgcolor="#CD0504" class="width104 height19 pLeft5"
					align="center" width="10%"><FONT color="white"><span
					class="white10heading mLeft5 pRight5 mTop5"><bean:message
					bundle="dp" key="label.PO.Quantity" /></span></TD>
				<TD bgcolor="#CD0504" class="width104 height19 pLeft5"
					align="center" width="20%"><FONT color="white"><span
					class="white10heading mLeft5 pRight5 mTop5"><bean:message
					bundle="dp" key="label.PO.InHandQuantity" /></span></TD>
				<TD bgcolor="#CD0504" class="width104 height19 pLeft5"
					align="center" width="20%"><FONT color="white"><span
					class="white10heading mLeft5 pRight5 mTop5"><bean:message
					bundle="dp" key="label.PO.QuantityAsPerDP" /></span></TD>
				<TD bgcolor="#CD0504" class="width104 height19 pLeft5"
					align="center" width="20%"><FONT color="white"><span
					class="white10heading mLeft5 pRight5 mTop5"><bean:message
					bundle="dp" key="label.PO.OpeningStockAsPerDP" /></span></TD>
					
				<TD bgcolor="#CD0504" class="width104 height19 pLeft5"
					align="center" width="10%"><FONT color="white"><span
					class="white10heading mLeft5 pRight5 mTop5">
					<bean:message
					bundle="dp" key="label.PO.Remove" />
					</span></TD>
			</TR>
		</tbody>
	</TABLE>
	<br>
	<br>
	<br>
	<br>
	<table align="center" class="mLeft5" styleId="tblSample" width="100%">
		<tbody id="disclaimer">
			<TR>
				<TD bgcolor="#CD0504" align="center" width="100%"><FONT
					color="white"> <span
					class="white10heading mLeft5 pRight5 mTop5"> <bean:message
					bundle="dp" key="label.PO.Disclaimer" /> </span></TD>
			</TR>
		</tbody>
	</TABLE>
	<%} %>
</html:form>
</BODY>
</html:html>
