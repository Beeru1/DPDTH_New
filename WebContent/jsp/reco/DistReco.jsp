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
<style type="text/css">
.noScroll1{
position: relative;
	 top: 0px;
	left: 0px;
	 top:expression(this.offsetParent.scrollTop-2);
  
}
.noScroll{
position: relative;
	 top: 0px;
	left: 0px;
	 top:expression(this.offsetParent.scrollTop-3);
  
}

</style>
<style>
 .odd{background-color: #FCE8E6;}
 .even{background-color: #FFFFFF;}
</style>
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>
<script>
var mins,timeToextend,TimerRunning,TimerID;
 TimerRunning=false;
 
 function Init() //call the Init function when u need to start the timer
 {
    mins=30; 
    timeToextend= 20;  
    StopTimer(); 
    StartTimer();
 } 
 function StopTimer()
 {
    if(TimerRunning)
       clearTimeout(TimerID);
    TimerRunning=false;
 }
 function StartTimer()
 {
    TimerRunning=true;
    window.status="Time Remaining : "+Pad(mins)+" minutes.";
    TimerID=self.setTimeout("StartTimer()",60000);   
    mins--; 
    timeToextend--; 
     
    if(timeToextend %5 == 0)    
       extendSession();  
    if(mins==0)
       StopTimer();
 }
 function Pad(number) //pads the mins with a 0 if its less than 10
 {
    if(number<10)
       number=0+""+number;
    return number;
 }

function extendSession(){
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
	  getAjaxTextValues();
	}
	var url= "DistReco.do?methodName=extendSession";
	req.open("POST",url,false);
	req.send();
}
function getAjaxTextValues()
{
	if (req.readyState==4 || req.readyState=="complete") 
	{
		var msg=req.responseText;			
	}
}
		
	function alternate(){
   if(document.getElementsByTagName){  
   var table = document.getElementById("prodDataTable");  
   var rows = table.getElementsByTagName("TR");  
   var cellsStyle = table.getElementsByTagName("TD");  
   for(i = 2; i < rows.length; i++){          
 //manipulate rows
     if(i % 2 == 0){
       rows[i].className = "even";
     }else{
       rows[i].className = "odd";
     }      
   }
   
    for(i = 1; i < cellsStyle.length; i++){          
 //manipulate cells
     cellsStyle[i].align = "center";
     if(i % 2 == 0){
       cellsStyle[i].className = "text";
      }else{
       cellsStyle[i].className = "text";
     }      
   }
   
   
 }
}
	function disableLink()
	{
		var dls = document.getElementById('chromemenu').getElementsByTagName("li");
		 
		for (i=0;i<dls.length;i++) {
		dls[i].setAttribute("className", "defaultCursor");
			var dds = dls[i].getElementsByTagName("a");
			for(j=0;j<dds.length;j++){
				document.getElementById(dds[j].getAttribute("rel")).style.visibility="hidden";
				document.getElementById(dds[j].getAttribute("rel")).style.display="none";
			}
		}
	}	
	function enableLink()
	{
		var dls = document.getElementById('chromemenu').getElementsByTagName("li");
		
		for (i=0;i<dls.length;i++) {
		dls[i].setAttribute("className", "pointerCursor");
			var dds = dls[i].getElementsByTagName("a");
			for(j=0;j<dds.length;j++){
				document.getElementById(dds[j].getAttribute("rel")).style.display="block";
			}
		}
	}	
	
function getProductRecoList()
	{
	 var stbType = document.getElementById("stbTypeId").value;
	 var recoPeriod = document.getElementById("recoPeriodId").value;
	 if(recoPeriod == "" || recoPeriod == "-1"){
		alert("Please select Reco Period");
		var selectObj = document.getElementById("prodDataTable");
		var rowCount2 = selectObj.rows.length;
		
		if(rowCount2 >2){
			var rowdelete =rowCount2-1;
			var loopCondition=rowCount2-2;
			 for(var i=0;i< loopCondition;i++){
				document.getElementById("prodDataTable").deleteRow(rowdelete);
				rowdelete --;
				}
			
			}
		document.getElementById("recoPeriodId").focus();
		
		return false;
	}
			
   	var url = "DistReco.do?methodName=viewRecoProducts&stbType="+stbType+"&recoPeriod="+recoPeriod;

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
		req.onreadystatechange = getProductRecoListAJAX;
		req.open("POST",url,false);
		req.send();
	}

//  call my Ajax
	function getProductRecoListAJAX()
	{
		// Get defect type 
		if (req.readyState==4 || req.readyState=="complete") 
	  	{
			var xmldoc = req.responseXML.documentElement;
			
			if(xmldoc == null) 
			{
				return;
			}
						
			// Get all product Nos.
			
		var optionValues = xmldoc.getElementsByTagName("optionProduct");
		var optionValue2 = xmldoc.getElementsByTagName("optionCertId");
		var length = optionValue2.length;
		var certtId = optionValue2[0].getAttribute("certId");
		var certId = optionValue2[0].getAttribute("certificateId");
		var selectObj = document.getElementById("prodDataTable");
		var rowCount2 = selectObj.rows.length;
		
		if(optionValues[0].getAttribute("isValidToEnter")=="-1"){
			
		if(rowCount2 >2){
			var rowdelete =rowCount2-1;
			var loopCondition=rowCount2-2;
			 for(var i=0;i< loopCondition;i++){
				document.getElementById("prodDataTable").deleteRow(rowdelete);
				rowdelete --;
				}
			
			}
			return false;
		}
		//alert(optionValues[0].getAttribute("isValidToSubmit"));
		//alert(optionValues[0].getAttribute("isValidToEnter"));
		if(optionValues[0].getAttribute("isValidToEnter")=="0"){
			alert("Note: Selected reco cannot be updated unless previous reco is complete !!");
			//return false;
		}
		
		if(rowCount2 >2){
			var rowdelete =rowCount2-1;
			var loopCondition=rowCount2-1;
			 for(var i=0;i< loopCondition;i++){
				document.getElementById("prodDataTable").deleteRow(rowdelete);
				rowdelete --;
				}
			
			}
			var loopSize =parseInt(optionValues.length) + ( parseInt(optionValues.length)/2);
			var row;
			 var rowCount;
			 var partnerEntered;
			 var intLoop1 =0;
			 
			 var startLimit=0;
			 if(optionValues[0].getAttribute("isValidToEnter")=="0"){
			 	startLimit=0;
			 }
			 else
			 {
			 	startLimit=0
			 }
			// alert(startLimit);
			 for (var intLoop=startLimit; intLoop < loopSize;intLoop++) 
			 {
			 	
			 	var mod3 = (intLoop+1)%3; 
			 	if(mod3!=0){
		        	var dataType = optionValues[intLoop1].getAttribute("dataTypa");
					var dataTypaId= optionValues[intLoop1].getAttribute("dataTypaId");
					var productName = optionValues[intLoop1].getAttribute("productName");
					var productId = optionValues[intLoop1].getAttribute("productId");
					//Opneing In Transit Stock
					var pendingPOIntransitOpen = optionValues[intLoop1].getAttribute("pendingPOIntransitOpen");
					var openingInTransitDC = optionValues[intLoop1].getAttribute("openingPendgDCIntrnsit");
					//Ends Opneing In Transit Stock Here 
					
					//Open Stocks
					var serialisedOpenStock = optionValues[intLoop1].getAttribute("serialisedOpenStock");
					var nonSerialisedOpenStock = optionValues[intLoop1].getAttribute("nonSerialisedOpenStock");
					var defectiveOpenStock = optionValues[intLoop1].getAttribute("defectiveOpenStock");
					var upgradeOpenStock = optionValues[intLoop1].getAttribute("upgradeOpenStock");
					var churnOpenStock = optionValues[intLoop1].getAttribute("churnOpenStock");
					//End Open Stock
					
					//Received Stock
					var receivedWh = optionValues[intLoop1].getAttribute("receivedWh");
					// -- Recvd InterSSD Ok === InterSSD Ok+HierarchyTransfer
					var receivedInterSSDOk = optionValues[intLoop1].getAttribute("receivedInterSSDOk");
					var receivedHierarchyTrans = optionValues[intLoop1].getAttribute("receivedHierarchyTrans");
					var receivedInterSSDDef = optionValues[intLoop1].getAttribute("receivedInterSSDDef");
					var receivedUpgrade = optionValues[intLoop1].getAttribute("receivedUpgrade");
		            var receivedChurn = optionValues[intLoop1].getAttribute("receivedChurn");
		            //End Rcvd Stock
					
					//Returnd Stock
					var returnedFresh = optionValues[intLoop1].getAttribute("returnedFresh");
					// -- Return InterSSD Ok === InterSSD Ok+HierarchyTransfer
					var returnedInterSSDOk = optionValues[intLoop1].getAttribute("returnedInterSSDOk");
					var returnedHierarchyTrans = optionValues[intLoop1].getAttribute("returnedHierarchyTrans");
					var returnedInterSSDDEF = optionValues[intLoop1].getAttribute("returnedInterSSDDEF");
					var returnedDoaBI = optionValues[intLoop1].getAttribute("returnedDoaBI");
					var returnedDoaAi = optionValues[intLoop1].getAttribute("returnedDoaAi");
					var returnedC2S = optionValues[intLoop1].getAttribute("returnedC2S");
		            var returnedChurn = optionValues[intLoop1].getAttribute("returnedChurn");
					var returnedDefectiveSwap = optionValues[intLoop1].getAttribute("returnedDefectiveSwap");
					//End Returned Stock
					
					//Activation Stock == Ser Activation+NSR Activation
					var serialisedActivation = optionValues[intLoop1].getAttribute("serialisedActivation");
		          	var nonSerialisedActivation = optionValues[intLoop1].getAttribute("nonSerialisedActivation");
		           //Ends Activation
					
					//Inventory Change
					var receivedDefective = optionValues[intLoop1].getAttribute("receivedDefective");
					//Ends Inventory change
					
					//Adjustment == Serialised +Defective + Non Serialized 
					var flushOutOk = optionValues[intLoop1].getAttribute("flushOutOk");  // Serialised 
					var flushOutDef = optionValues[intLoop1].getAttribute("flushOutDef"); // Defective
					var recoQty = optionValues[intLoop1].getAttribute("recoQty");   // Non Serialized 
					//Ends Adjustment
					
					//Closing Stock 
					var serializedClosingStock = optionValues[intLoop1].getAttribute("serializedClosingStock");
					var nonSerializedClosingStock = optionValues[intLoop1].getAttribute("nonSerializedClosingStock");
					var defectiveClosingStock = optionValues[intLoop1].getAttribute("defectiveClosingStock");
					var upgradeClosingStock = optionValues[intLoop1].getAttribute("upgradeClosingStock");
					var churnClosingStock = optionValues[intLoop1].getAttribute("churnClosingStock");
					//Ends Closing
					
					//Closing Intransit Stock
					var pendingPOIntransit = optionValues[intLoop1].getAttribute("pendingPOIntransit");
					var returnedInTransit = optionValues[intLoop1].getAttribute("pendingDCIntransit");
					//Ends Closing Intransit Stock
					
					var isPartnerEntered = optionValues[intLoop1].getAttribute("isPartnerEntered");
		            var productType = optionValues[intLoop1].getAttribute("productType");
		            
		           	var optionValues = xmldoc.getElementsByTagName("optionProduct");
		
		            	            
		            rowCount = selectObj.rows.length;
		            row = selectObj.insertRow(rowCount);
						
					var cell1 = row.insertCell(0);
					cell1.innerHTML = "<div value=\"" + dataType +  "\">" + dataType + "</div><input type=\"hidden\" value=\"" + dataTypaId + "\" />";
					
					var cell2 = row.insertCell(1);
					cell2.innerHTML = "<div value=\"" + productName +  "\">" + productName + "</div><input type=\"hidden\" value=\"" + productId + "\" />";
					
					var cell3 = row.insertCell(2);
					if(dataTypaId != "0"){
						if(pendingPOIntransitOpen == null){
							pendingPOIntransitOpen ="0";
							cell3.innerHTML = "<input type=\"textfield\" id=\"pendingPOIntranOpen\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",2) \" />";
						}else{
							cell3.innerHTML = "<div value=\"" + pendingPOIntransitOpen +  "\">" + pendingPOIntransitOpen + "</div>";
						}
					}else{
						if(pendingPOIntransitOpen == null)
						pendingPOIntransitOpen = "0";
						cell3.innerHTML = "<div value=\"" + pendingPOIntransitOpen +  "\">" + pendingPOIntransitOpen + "</div>";
					}
					
					
					var cell4 = row.insertCell(3);
					if(dataTypaId != "0"){
						if(openingInTransitDC == null){
							openingInTransitDC ="0";
							cell4.innerHTML = "<input type=\"textfield\" id=\"openInTransDC\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",3) \" />";
						}else{
							cell4.innerHTML = "<div value=\"" + openingInTransitDC +  "\">" + openingInTransitDC + "</div>";
						}
					}else{
						if(openingInTransitDC == null)
						openingInTransitDC = "0";
						cell4.innerHTML = "<div value=\"" + openingInTransitDC +  "\">" + openingInTransitDC + "</div>";
					}
				
				var cell5 = row.insertCell(4);
					if(dataTypaId != "0"){
						if(serialisedOpenStock == null){
							partnerEntered = "N";
							serialisedOpenStock ="0";
							cell5.innerHTML = "<input type=\"textfield\" id=\"serialisedOpenStock\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",4) \" />";
						}else{
							partnerEntered = "Y";
							cell5.innerHTML = "<div value=\"" + serialisedOpenStock +  "\">" + serialisedOpenStock + "</div>";
						}
					}else{
						partnerEntered = "N";
						if(serialisedOpenStock == null)
						serialisedOpenStock = "0";
						cell5.innerHTML = "<div value=\"" + serialisedOpenStock +  "\">" + serialisedOpenStock + "</div>";
					}
					
					
					var cell6 = row.insertCell(5);
					if(dataTypaId != "0"){
						if(nonSerialisedOpenStock == null){
							nonSerialisedOpenStock ="0";
							cell6.innerHTML = "<input type=\"textfield\" id=\"nSrOpenStock\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",5) \" />";
						}else{
							cell6.innerHTML = "<div value=\"" + nonSerialisedOpenStock +  "\">" + nonSerialisedOpenStock + "</div>";
						}
					}else{
						if(nonSerialisedOpenStock == null)
						nonSerialisedOpenStock = "0";
						cell6.innerHTML = "<div value=\"" + nonSerialisedOpenStock +  "\">" + nonSerialisedOpenStock + "</div>";
					}
				
				var cell7 = row.insertCell(6);
					if(dataTypaId != "0"){
						if(defectiveOpenStock == null){
							defectiveOpenStock ="0";
							cell7.innerHTML = "<input type=\"textfield\" id=\"defOpenStock\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",6) \" />";
						}else{
							cell7.innerHTML = "<div value=\"" + defectiveOpenStock +  "\">" + defectiveOpenStock + "</div>";
						}
					}else{
						if(defectiveOpenStock == null)
						defectiveOpenStock = "0";
						cell7.innerHTML = "<div value=\"" + defectiveOpenStock +  "\">" + defectiveOpenStock + "</div>";
					}
				
				var cell8 = row.insertCell(7);
					if(dataTypaId != "0"){
						if(upgradeOpenStock == null){
							upgradeOpenStock ="0";
							if(productType=="0")
							{
							cell8.innerHTML = "<input type=\"textfield\" id=\"upgdOpenStock\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",7) \" />";
							}else{
							cell8.innerHTML = "<input type=\"textfield\" id=\"upgdOpenStock\" style=\"width: 50px; background-color : #C0C0C0;\"  onchange=\"setVariance("+rowCount+",7) \" disabled=\"true\" />";
							}
						}else{
							cell8.innerHTML = "<div value=\"" + upgradeOpenStock +  "\">" + upgradeOpenStock + "</div>";
						}
					}else{
						if(upgradeOpenStock == null)
						upgradeOpenStock = "0";
						cell8.innerHTML = "<div value=\"" + upgradeOpenStock +  "\">" + upgradeOpenStock + "</div>";
					}
				
				var cell9 = row.insertCell(8);
					if(dataTypaId != "0"){
						if(churnOpenStock == null){
							churnOpenStock ="0";
							if(productType=="0")
							{
							cell9.innerHTML = "<input type=\"textfield\" id=\"churnOpenStock\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",8) \" />";
							}else{
							cell9.innerHTML = "<input type=\"textfield\" id=\"churnOpenStock\" style=\"width: 50px; background-color : #C0C0C0;\"  onchange=\"setVariance("+rowCount+",8) \" disabled=\"true\" />";
							}
						}else{
							cell9.innerHTML = "<div value=\"" + churnOpenStock +  "\">" + churnOpenStock + "</div>";
						}
					}else{
						if(churnOpenStock == null)
						churnOpenStock = "0";
						cell9.innerHTML = "<div value=\"" + churnOpenStock +  "\">" + churnOpenStock + "</div>";
					}
				
				
				var cell10 = row.insertCell(9);
					if(dataTypaId != "0"){
						if(receivedWh == null){
							receivedWh ="0";
							cell10.innerHTML = "<input type=\"textfield\" id=\"receivedWh\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",9) \" />";
						}else{
							cell10.innerHTML = "<div value=\"" + receivedWh +  "\">" + receivedWh + "</div>";
						}
					}else{
						if(receivedWh == null)
						receivedWh = "0";
						cell10.innerHTML = "<div value=\"" + receivedWh +  "\">" + receivedWh + "</div>";
					}
			
						
					var totalReceivedInterSSDOK =0;
					
					if(receivedInterSSDOk != null){
						totalReceivedInterSSDOK +=parseInt(receivedInterSSDOk);
					}
					if(receivedHierarchyTrans != null){
						totalReceivedInterSSDOK +=parseInt(receivedHierarchyTrans);
					}
					
					
					var cell11 = row.insertCell(10);
					if(dataTypaId != "0"){
						if(receivedInterSSDOk == null){
							receivedInterSSDOk ="0";
							cell11.innerHTML = "<input type=\"textfield\" id=\"receivedInterSSDOk\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",10) \"  />";
						}else{
							cell11.innerHTML = "<div value=\"" + receivedInterSSDOk +  "\">" + receivedInterSSDOk + "</div>";
						}
						
					}else{
						
						if(receivedInterSSDOk  == null)
						receivedInterSSDOk  = "0";
						if(receivedHierarchyTrans   == null)
						receivedHierarchyTrans   = "0";
						cell11.innerHTML = "<div value=\"" + totalReceivedInterSSDOK +  "\">" + totalReceivedInterSSDOK+"("+ receivedInterSSDOk +"+" + receivedHierarchyTrans+" )</div>";
					}
				
				
				var cell12 = row.insertCell(11);
					if(dataTypaId != "0"){
						if(receivedInterSSDDef == null){
							receivedInterSSDDef ="0";
							cell12.innerHTML = "<input type=\"textfield\" id=\"receivedInterSSDDef\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",11) \" />";
						}else{
							cell12.innerHTML = "<div value=\"" + receivedInterSSDDef +  "\">" + receivedInterSSDDef + "</div>";
						}
					}else{
						if(receivedInterSSDDef == null)
						receivedInterSSDDef = "0";
						cell12.innerHTML = "<div value=\"" + receivedInterSSDDef +  "\">" + receivedInterSSDDef + "</div>";
					}
				
				var cell13 = row.insertCell(12);
					if(dataTypaId != "0"){
						if(receivedUpgrade == null){
							receivedUpgrade ="0";
							if(productType=="0")
							{
							cell13.innerHTML = "<input type=\"textfield\" id=\"receivedUpgrade\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",12) \" />";
							}else{
							cell13.innerHTML = "<input type=\"textfield\" id=\"receivedUpgrade\" style=\"width: 50px; background-color : #C0C0C0;\"  onchange=\"setVariance("+rowCount+",12) \"  disabled=\"true\" />";
							}
						}else{
							cell13.innerHTML = "<div value=\"" + receivedUpgrade +  "\">" + receivedUpgrade + "</div>";
						}
					}else{
						if(receivedUpgrade == null)
						receivedUpgrade = "0";
						cell13.innerHTML = "<div value=\"" + receivedUpgrade +  "\">" + receivedUpgrade + "</div>";
					}
					
				var cell14 = row.insertCell(13);
					if(dataTypaId != "0"){
						if(receivedChurn == null){
							receivedChurn ="0";
							if(productType=="0")
							{
							cell14.innerHTML = "<input type=\"textfield\" id=\"receivedChurn\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",13) \" />";
							}else{
							cell14.innerHTML = "<input type=\"textfield\" id=\"receivedChurn\" style=\"width: 50px; background-color : #C0C0C0;\"  onchange=\"setVariance("+rowCount+",13) \" disabled=\"true\" />";
							}
						}else{
							cell14.innerHTML = "<div value=\"" + receivedChurn +  "\">" + receivedChurn + "</div>";
						}
					}else{
						if(receivedChurn == null)
						receivedChurn = "0";
						cell14.innerHTML = "<div value=\"" + receivedChurn +  "\">" + receivedChurn + "</div>";
					}
					
				var cell15 = row.insertCell(14);
					if(dataTypaId != "0"){
						if(returnedFresh == null){
							returnedFresh ="0";
							cell15.innerHTML = "<input type=\"textfield\" id=\"returnedFresh\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",14) \" />";
						}else{
							cell15.innerHTML = "<div value=\"" + returnedFresh +  "\">" + returnedFresh + "</div>";
						}
					}else{
						if(returnedFresh == null)
						returnedFresh = "0";
						cell15.innerHTML = "<div value=\"" + returnedFresh +  "\">" + returnedFresh + "</div>";
					}
				
				
					
					var totalReturnInterSSDOK =0;
					
					if(returnedInterSSDOk != null){
						totalReturnInterSSDOK +=parseInt(returnedInterSSDOk);
					}
					if(returnedHierarchyTrans != null){
						totalReturnInterSSDOK +=parseInt(returnedHierarchyTrans);
					}
					
					
					var cell16 = row.insertCell(15);
					if(dataTypaId != "0"){
						if(returnedInterSSDOk == null){
							returnedInterSSDOk ="0";
							cell16.innerHTML = "<input type=\"textfield\" id=\"returnedInterSSDOk\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",15) \"  />";
						}else{
							cell16.innerHTML = "<div value=\"" + returnedInterSSDOk +  "\">" + returnedInterSSDOk + "</div>";
						}
						
					}else{
						
						if(returnedInterSSDOk  == null)
						returnedInterSSDOk  = "0";
						if(returnedHierarchyTrans   == null)
						returnedHierarchyTrans   = "0";
						cell16.innerHTML = "<div value=\"" + totalReturnInterSSDOK +  "\">" + totalReturnInterSSDOK+"("+ returnedInterSSDOk +"+" + returnedHierarchyTrans+" )</div>";
					}
				
				
				var cell17 = row.insertCell(16);
					if(dataTypaId != "0"){
						if(returnedInterSSDDEF == null){
							returnedInterSSDDEF ="0";
							cell17.innerHTML = "<input type=\"textfield\" id=\"returnedInterSSDDEF\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",16) \" />";
						}else{
							cell17.innerHTML = "<div value=\"" + returnedInterSSDDEF +  "\">" + returnedInterSSDDEF + "</div>";
						}
					}else{
						if(returnedInterSSDDEF == null)
						returnedInterSSDDEF = "0";
						cell17.innerHTML = "<div value=\"" + returnedInterSSDDEF +  "\">" + returnedInterSSDDEF + "</div>";
					}
				var cell18 = row.insertCell(17);
					if(dataTypaId != "0"){
						if(returnedDoaBI == null){
							returnedDoaBI ="0";
							if(productType=="1")
							{
							cell18.innerHTML = "<input type=\"textfield\" id=\"returnedDoaBI\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",17) \" />";
							}else{
							cell18.innerHTML = "<input type=\"textfield\" id=\"returnedDoaBI\" style=\"width: 50px; background-color : #C0C0C0;\"  onchange=\"setVariance("+rowCount+",17) \" disabled=\"true\" />";
							}
						}else{
							cell18.innerHTML = "<div value=\"" + returnedDoaBI +  "\">" + returnedDoaBI + "</div>";
						}
					}else{
						if(returnedDoaBI == null)
						returnedDoaBI = "0";
						cell18.innerHTML = "<div value=\"" + returnedDoaBI +  "\">" + returnedDoaBI + "</div>";
					}
				var cell19 = row.insertCell(18);
					if(dataTypaId != "0"){
						if(returnedDoaAi == null){
							returnedDoaAi ="0";
							if(productType=="1")
							{
							cell19.innerHTML = "<input type=\"textfield\" id=\"returnedDoaAi\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",18) \" />";
							}else{
							cell19.innerHTML = "<input type=\"textfield\" id=\"returnedDoaAi\" style=\"width: 50px; background-color : #C0C0C0;\"  onchange=\"setVariance("+rowCount+",18) \"  disabled=\"true\" />";
							}
						}else{
							cell19.innerHTML = "<div value=\"" + returnedDoaAi +  "\">" + returnedDoaAi + "</div>";
						}
					}else{
						if(returnedDoaAi == null)
						returnedDoaAi = "0";
						cell19.innerHTML = "<div value=\"" + returnedDoaAi +  "\">" + returnedDoaAi + "</div>";
					}
				var cell20 = row.insertCell(19);
					if(dataTypaId != "0"){
						if(returnedC2S == null){
							returnedC2S ="0";
							if(productType=="1")
							{
							cell20.innerHTML = "<input type=\"textfield\" id=\"returnedC2S\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",19) \" />";
							}else{
							cell20.innerHTML = "<input type=\"textfield\" id=\"returnedC2S\" style=\"width: 50px; background-color : #C0C0C0;\"  onchange=\"setVariance("+rowCount+",19) \"  disabled=\"true\" />";
							}
						}else{
							cell20.innerHTML = "<div value=\"" + returnedC2S +  "\">" + returnedC2S + "</div>";
						}
					}else{
						if(returnedC2S == null)
						returnedC2S = "0";
						cell20.innerHTML = "<div value=\"" + returnedC2S +  "\">" + returnedC2S + "</div>";
					}
				var cell21 = row.insertCell(20);
					if(dataTypaId != "0"){
						if(returnedChurn == null){
							returnedChurn ="0";
							if(productType=="0")
							{
							cell21.innerHTML = "<input type=\"textfield\" id=\"returnedChurn\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",20) \" />";
							}else{
							cell21.innerHTML = "<input type=\"textfield\" id=\"returnedChurn\" style=\"width: 50px; background-color : #C0C0C0;\"  onchange=\"setVariance("+rowCount+",20) \"  disabled=\"true\" />";
							}
						}else{
							cell21.innerHTML = "<div value=\"" + returnedChurn +  "\">" + returnedChurn + "</div>";
						}
					}else{
						if(returnedChurn == null)
						returnedChurn = "0";
						cell21.innerHTML = "<div value=\"" + returnedChurn +  "\">" + returnedChurn + "</div>";
					}
					
				var cell22 = row.insertCell(21);
					if(dataTypaId != "0"){
						if(returnedDefectiveSwap == null){
							returnedDefectiveSwap ="0";
							if(productType=="0")
							{
							cell22.innerHTML = "<input type=\"textfield\" id=\"returnedDefectiveSwap\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",21) \" />";
							}else{
							cell22.innerHTML = "<input type=\"textfield\" id=\"returnedDefectiveSwap\" style=\"width: 50px; background-color : #C0C0C0;\"  onchange=\"setVariance("+rowCount+",21) \"  disabled=\"true\" />";
							}
						}else{
							cell22.innerHTML = "<div value=\"" + returnedDefectiveSwap +  "\">" + returnedDefectiveSwap + "</div>";
						}
					}else{
						if(returnedDefectiveSwap == null)
						returnedDefectiveSwap = "0";
						cell22.innerHTML = "<div value=\"" + returnedDefectiveSwap +  "\">" + returnedDefectiveSwap + "</div>";
					}
				
				
				
				var totalActivation =0;
					
					if(serialisedActivation != null){
						totalActivation +=parseInt(serialisedActivation);
					}
					if(nonSerialisedActivation != null){
						totalActivation +=parseInt(nonSerialisedActivation);
					}
					
					
					var cell23 = row.insertCell(22);
					if(dataTypaId != "0"){
						if(serialisedActivation == null){
							serialisedActivation ="0";
							if(productType=="1")
							{
							cell23.innerHTML = "<input type=\"textfield\" id=\"serialisedActivation\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",22) \"  />";
							}else{
							cell23.innerHTML = "<input type=\"textfield\" id=\"serialisedActivation\" style=\"width: 50px; background-color : #C0C0C0;\"  onchange=\"setVariance("+rowCount+",22) \"  disabled=\"true\" />";
							}
						}else{
							cell23.innerHTML = "<div value=\"" + serialisedActivation +  "\">" + serialisedActivation + "</div>";
						}
						
					}else{
						
						if(serialisedActivation  == null)
						serialisedActivation  = "0";
						if(nonSerialisedActivation   == null)
						nonSerialisedActivation   = "0";
						cell23.innerHTML = "<div value=\"" + totalActivation +  "\">" + totalActivation+"("+ serialisedActivation +"+" + nonSerialisedActivation+" )</div>";
					}
				
					var cell24 = row.insertCell(23);
					if(dataTypaId != "0"){
						if(receivedDefective == null){
							receivedDefective ="0";
							cell24.innerHTML = "<input type=\"textfield\" id=\"receivedDefective\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",23) \" />";
						}else{
							cell24.innerHTML = "<div value=\"" + receivedDefective +  "\">" + receivedDefective + "</div>";
						}
					}else{
						if(receivedDefective == null)
						receivedDefective = "0";
						cell24.innerHTML = "<div value=\"" + receivedDefective +  "\">" + receivedDefective + "</div>";
					}
				
				var totalSerialisedAdjustment =0;
					// Toatal Adjustment  == Flushout OK +Flushout Def - Reco Qty --- As Suggested by Archana on 19-06-2012
					// As for NS Stock in Reco case , Closing Stock = Opening Stock-Sale+Reco
					
					if(flushOutOk!= null){
						totalSerialisedAdjustment +=parseInt(flushOutOk);
					}
					if(flushOutDef!= null){
						totalSerialisedAdjustment += parseInt(flushOutDef);
					}
					if(recoQty!= null){
						totalSerialisedAdjustment = parseInt(totalSerialisedAdjustment) - parseInt(recoQty);
					}
					
					
					var cell25 = row.insertCell(24);
					if(dataTypaId != "0"){
							//commented by Shilpa on 27-06-12
						//if(flushOutOk == null){
						//flushOutOk="0";
						///cell25.innerHTML = "<div value=\"" + flushOutOk +  "\">" + flushOutOk + "</div>";
						//}else{
						//	cell25.innerHTML = "<div value=\"" + flushOutOk +  "\">" + flushOutOk + "</div>";
						//}
						//Ends
						flushOutOk="";
						cell25.innerHTML = "<div value=\"" + flushOutOk +  "\">&nbsp;" + flushOutOk + "</div>";
					}else{
					if(flushOutOk == null)
						flushOutOk = "0";
						if(flushOutDef == null)
						flushOutDef = "0";
						if(recoQty == null)
							recoQty = "0";
						cell25.innerHTML = "<div value=\"" + totalSerialisedAdjustment +  "\">" + totalSerialisedAdjustment+"</div>";
					}
					var button;
					var cell26 = row.insertCell(25);
					if(dataTypaId != "0"){
						if(serializedClosingStock == null){
						button=null;
							serializedClosingStock ="0";
							cell26.innerHTML = "<input type=\"textfield\" id=\"serializedClosingStock\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",25) \" /><input type=\"hidden\" value=\"" + isPartnerEntered + "\" />";
						}else{
							cell26.innerHTML = "<div value=\"" + serializedClosingStock +  "\">" + serializedClosingStock + "</div><input type=\"hidden\" value=\"" + isPartnerEntered + "\" />";
						}
					}else{
						button=0;
						if(serializedClosingStock == null)
						serializedClosingStock = "0";
						cell26.innerHTML = "<div value=\"" + serializedClosingStock +  "\">" + serializedClosingStock + "</div><input type=\"hidden\" value=\"" + isPartnerEntered + "\" />";
					}
				var cell27 = row.insertCell(26);
					if(dataTypaId != "0"){
						if(nonSerializedClosingStock == null){
							nonSerializedClosingStock ="0";
							cell27.innerHTML = "<input type=\"textfield\" id=\"nonSerializedClosingStock\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",26) \" />";
						}else{
							cell27.innerHTML = "<div value=\"" + nonSerializedClosingStock +  "\">" + nonSerializedClosingStock + "</div>";
						}
					}else{
						if(nonSerializedClosingStock == null)
						nonSerializedClosingStock = "0";
						cell27.innerHTML = "<div value=\"" + nonSerializedClosingStock +  "\">" + nonSerializedClosingStock + "</div>";
					}
				
					var cell28 = row.insertCell(27);
					if(dataTypaId != "0"){
						if(defectiveClosingStock == null){
							defectiveClosingStock ="0";
							cell28.innerHTML = "<input type=\"textfield\" id=\"defectiveClosingStock\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",27) \" />";
						}else{
							cell28.innerHTML = "<div value=\"" + defectiveClosingStock +  "\">" + defectiveClosingStock + "</div>";
						}
					}else{
						if(defectiveClosingStock == null)
						defectiveClosingStock = "0";
						cell28.innerHTML = "<div value=\"" + defectiveClosingStock +  "\">" + defectiveClosingStock + "</div>";
					}
				
					var cell29 = row.insertCell(28);
					if(dataTypaId != "0"){
						if(upgradeClosingStock == null){
							upgradeClosingStock ="0";
							if(productType=="0")
							{
							cell29.innerHTML = "<input type=\"textfield\" id=\"upgradeClosingStock\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",28) \"  />";
							}else{
							cell29.innerHTML = "<input type=\"textfield\" id=\"upgradeClosingStock\" style=\"width: 50px; background-color : #C0C0C0;\"  onchange=\"setVariance("+rowCount+",28) \"  disabled=\"true\" />";
							}
						}else{
							cell29.innerHTML = "<div value=\"" + upgradeClosingStock +  "\">" + upgradeClosingStock + "</div>";
						}
						
					}else{
						
						if(upgradeClosingStock  == null)
						upgradeClosingStock  = "0";
						cell29.innerHTML = "<div value=\"" + upgradeClosingStock +  "\">" + upgradeClosingStock+" </div>";
					}
				
					var cell30 = row.insertCell(29);
					if(dataTypaId != "0"){
						if(churnClosingStock == null){
							churnClosingStock ="0";
							if(productType=="0")
							{
							cell30.innerHTML = "<input type=\"textfield\" id=\"churnClosingStock\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",29) \"  />";
							}else{
							cell30.innerHTML = "<input type=\"textfield\" id=\"churnClosingStock\" style=\"width: 50px; background-color : #C0C0C0;\"  onchange=\"setVariance("+rowCount+",29) \"  disabled=\"true\" />";
							}
						}else{
							cell30.innerHTML = "<div value=\"" + churnClosingStock +  "\">" + churnClosingStock + "</div>";
						}
						
					}else{
						
						if(churnClosingStock  == null)
						churnClosingStock  = "0";
						cell30.innerHTML = "<div value=\"" + churnClosingStock +  "\">" + churnClosingStock+" </div>";
					}
				
				
				var cell31 = row.insertCell(30);
					if(dataTypaId != "0"){
						if(pendingPOIntransit == null){
							pendingPOIntransit ="0";
							cell31.innerHTML = "<input type=\"textfield\" id=\"pendingPOIntransit\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",30) \" />";
						}else{
							cell31.innerHTML = "<div value=\"" + pendingPOIntransit +  "\">" + pendingPOIntransit + "</div>";
						}
					}else{
						if(pendingPOIntransit == null)
						pendingPOIntransit = "0";
						cell31.innerHTML = "<div value=\"" + pendingPOIntransit +  "\">" + pendingPOIntransit + "</div>";
					}
				
				var cell32 = row.insertCell(31);
					if(dataTypaId != "0"){
						if(returnedInTransit == null){
							returnedInTransit ="0";
							cell32.innerHTML = "<input type=\"textfield\" id=\"returnedInTransit\" style=\"width: 50px\"  onchange=\"setVariance("+rowCount+",31) \" />";
						}else{
							cell32.innerHTML = "<div value=\"" + returnedInTransit +  "\">" + returnedInTransit + "</div>";
						}
					}else{
						if(returnedInTransit == null)
						returnedInTransit = "0";
						cell32.innerHTML = "<div value=\"" + returnedInTransit +  "\">" + returnedInTransit + "</div>";
					}
				
				
				
				if(button == null){
						var cell33 = row.insertCell(32);
						
						if(optionValues[0].getAttribute("isValidToEnter")=="0"){
								cell33.innerHTML = "<input type=\"button\" onclick=\"submitData("+rowCount+") \" value=\" Submit \" disabled=\"true\"  />";
						}
						else
						{
								cell33.innerHTML = "<input type=\"button\" onclick=\"submitData("+rowCount+") \" value=\" Submit \"  />";
						}
					}
					intLoop1++;
				}else{
				
					rowCount = selectObj.rows.length;
		            row = selectObj.insertRow(rowCount);
					var valueVar;
						
					var cell1 = row.insertCell(0);
					cell1.innerHTML = "<div value=\" Variance \">Variance</div><input type=\"hidden\" value=\"-1\" />";
					
					var cell2 = row.insertCell(1);
					cell2.innerHTML = "<div value=\"" + productName +  "\">" + productName + "</div><input type=\"hidden\" value=\"" + productId + "\" />";
					//alert("isPartnerEntered=="+isPartnerEntered);
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,2);
					}
					var cell3 = row.insertCell(2);
					cell3.innerHTML = "<input type=\"textfield\" id=\"pendingPOOpenVar\" style=\"width: 65px\" readonly=\"true\" value=\"" + valueVar + "\" />";
						
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,3);
					}
					var cell4 = row.insertCell(3);
					cell4.innerHTML = "<input type=\"textfield\" id=\"pendingDCOpen\" style=\"width: 50px\"  readonly=\"true\"  value=\"" + valueVar + "\" />";
					
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,4);
					}
					
					var cell5 = row.insertCell(4);
					cell5.innerHTML = "<input type=\"textfield\" id=\"serOpenStock\" style=\"width: 50px\"  readonly=\"true\" value=\"" + valueVar + "\" />";
					
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,5);
					}
					var cell6 = row.insertCell(5);
					cell6.innerHTML = "<input type=\"textfield\" id=\"nsrOpenStock\" style=\"width: 50px\" readonly=\"true\" value=\"" + valueVar + "\"  />";
					
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,6);
					}
					var cell7 = row.insertCell(6);
					cell7.innerHTML = "<input type=\"textfield\" id=\"defOpenStock\" style=\"width: 50px\" readonly=\"true\"  value=\"" + valueVar + "\" />";
					
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,7);
					}
					var cell8 = row.insertCell(7);
					cell8.innerHTML = "<input type=\"textfield\" id=\"upgardeOpen\"  style=\"width: 50px\" readonly=\"true\" value=\"" + valueVar + "\" />";
					
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,8);
					}
					
					var cell9 = row.insertCell(8);
					cell9.innerHTML = "<input type=\"textfield\" id=\"churnOpen\"  style=\"width: 50px\" readonly=\"true\" value=\"" + valueVar + "\" />";
					
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,9);
					}
					
					
					var cell10 = row.insertCell(9);
					cell10.innerHTML = "<input type=\"textfield\" id=\"rcvdWH\"  style=\"width: 50px\" readonly=\"true\" value=\"" + valueVar + "\" />";
					
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,10);
					}
					var cell11 = row.insertCell(10);
					cell11.innerHTML = "<input type=\"textfield\" id=\"rcvdInterSSDOk\"  style=\"width: 50px\" readonly=\"true\" value=\"" + valueVar + "\" />";
					
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,11);
					}
					var cell12 = row.insertCell(11);
					cell12.innerHTML = "<input type=\"textfield\" id=\"rcvdInterssdDef\"  style=\"width: 50px\" readonly=\"true\" value=\"" + valueVar + "\" />";
					
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,12);
					}
					var cell13 = row.insertCell(12);
					cell13.innerHTML = "<input type=\"textfield\" id=\"recUpgrade\"  style=\"width: 50px\" readonly=\"true\" value=\"" + valueVar + "\" />";
					
					
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,13);
					}
					var cell14 = row.insertCell(13);
					cell14.innerHTML = "<input type=\"textfield\" id=\"recChurn\"  style=\"width: 50px\" readonly=\"true\" value=\"" + valueVar + "\" />";
										
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,14);
					}	
									
					
					var cell15 = row.insertCell(14);
					cell15.innerHTML = "<input type=\"textfield\" id=\"retOK\" style=\"width: 50px\" readonly=\"true\" value=\"" + valueVar + "\" />";
					
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,15);
					}
					
						var cell16 = row.insertCell(15);
					cell16.innerHTML = "<input type=\"textfield\" id=\"retInterSSDOk\" style=\"width: 50px\" readonly=\"true\" value=\"" + valueVar + "\" />";
					
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,16);
					}
					
					
						var cell17 = row.insertCell(16);
					cell17.innerHTML = "<input type=\"textfield\" id=\"retInterSsdDef\" style=\"width: 50px\" readonly=\"true\" value=\"" + valueVar + "\" />";
					
					if(partnerEntered == "N"){
						valueVar = " ";
//						valueVar = "0";
					}else if(partnerEntered == "Y"){
						//valueVar = getVariance(rowCount,17);
						valueVar = " ";
					}
					
					
						var cell18 = row.insertCell(17);
					cell18.innerHTML = "<input type=\"textfield\" id=\"retDOABI\" style=\"width: 50px\" readonly=\"true\" value=\"" + valueVar + "\" />";
					
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,18);
					}
					var cell19 = row.insertCell(18);
					cell19.innerHTML = "<input type=\"textfield\" id=\"retDAOAI\" style=\"width: 50px\" readonly=\"true\" value=\"" + valueVar + "\" />";
					
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,19);
					}
					var cell20 = row.insertCell(19);
					cell20.innerHTML = "<input type=\"textfield\" id=\"retC2S\" style=\"width: 65px\" readonly=\"true\" value=\"" + valueVar + "\" />";
					
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,20);
					}
					
					var cell21 = row.insertCell(20);
					cell21.innerHTML = "<input type=\"textfield\" id=\"retChurn\"  style=\"width: 50px\"  readonly=\"true\" value=\"" + valueVar + "\" />";
					
					
					
					
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,21);
					}
					var cell22 = row.insertCell(21);
					cell22.innerHTML = "<input type=\"textfield\" id=\"retDefSwap\"  style=\"width: 50px\"  readonly=\"true\" value=\"" + valueVar + "\" />";
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,22);
					}
					var cell23 = row.insertCell(22);
					cell23.innerHTML = "<input type=\"textfield\" id=\"activation\"  style=\"width: 50px\"  readonly=\"true\" value=\"" + valueVar + "\" />";
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,23);
					}
					var cell24 = row.insertCell(23);
					cell24.innerHTML = "<input type=\"textfield\" id=\"InventoryChange\"  style=\"width: 50px\"  readonly=\"true\" value=\"" + valueVar + "\" />";
					
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,24);
					}
					var cell25 = row.insertCell(24);
					cell25.innerHTML = "<div value=\"" + valueVar +  "\">&nbsp;</div>";
					//commented by Shilpa on 27-06-12
					//cell25.innerHTML = "<input type=\"textfield\" id=\"adjustment\"  style=\"width: 50px\"  readonly=\"true\" value=\"" + valueVar + "\" />";
					//Ends
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,25);
					}
					
					
					
					var cell26 = row.insertCell(25);
					cell26.innerHTML = "<input type=\"textfield\" id=\"serClosingStock\"  style=\"width: 50px\"  readonly=\"true\" value=\"" + valueVar + "\" />";
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,26);
					}
					var cell27 = row.insertCell(26);
					cell27.innerHTML = "<input type=\"textfield\" id=\"nsrClosingStock\"  style=\"width: 50px\"  readonly=\"true\" value=\"" + valueVar + "\" />";
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,27);
					}
					var cell28 = row.insertCell(27);
					cell28.innerHTML = "<input type=\"textfield\" id=\"defClosinStock\"  style=\"width: 50px\"  readonly=\"true\" value=\"" + valueVar + "\" />";
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,28);
					}
					var cell29 = row.insertCell(28);
					cell29.innerHTML = "<input type=\"textfield\" id=\"upgradeClosStock\"  style=\"width: 50px\"  readonly=\"true\" value=\"" + valueVar + "\" />";
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,29);
					}
					var cell30 = row.insertCell(29);
					cell30.innerHTML = "<input type=\"textfield\" id=\"churnClosing\"  style=\"width: 50px\"  readonly=\"true\" value=\"" + valueVar + "\" />";
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,30);
					}
					var cell31 = row.insertCell(30);
					cell31.innerHTML = "<input type=\"textfield\" id=\"pendingPOClose\"  style=\"width: 50px\"  readonly=\"true\" value=\"" + valueVar + "\" />";
					if(partnerEntered == "N"){
						valueVar = "0";
					}else if(partnerEntered == "Y"){
						valueVar = getVariance(rowCount,31);
					}
					var cell32 = row.insertCell(31);
					cell32.innerHTML = "<input type=\"textfield\" id=\"PendingDCClose\"  style=\"width: 50px\"  readonly=\"true\" value=\"" + valueVar + "\" />";
				}
								
		    }
		     alternate();
		   if(certId != "" && certId != "-1"){
		   		document.getElementById("printPage").disabled = false;
		  		document.getElementById("certId").value=certId; 
		    	//printPage(certId);
		    	enableLink();
		    }
		   if(certtId != "" && certtId != "-1"){
		  	 	document.getElementById("printPage").disabled = false;
		  	 	document.getElementById("certId").value=certtId;
		    }
		    
		    if(certId=="-1" && certtId=="-1"){
		     	document.getElementById("printPage").disabled = true;
		    } 
		    if(certId=="" && certtId==""){
		     document.getElementById("printPage").disabled = true;
		    } 
		    
		    
	}
}



function getVariance(rowNo,cellNo){
	var sysRowNo = rowNo -2;
	var partnerRowNo = rowNo -1;
	var sysRow = document.getElementById("prodDataTable").rows[sysRowNo].cells;
	var partnerRow = document.getElementById("prodDataTable").rows[partnerRowNo].cells;
	var sysValue;
	var partnerValue;
	if(document.all){
     	sysValue = sysRow[cellNo].getElementsByTagName("div")[0].value;
	} else {
    	sysValue = sysRow[cellNo].getElementsByTagName("div")[0].value.textContent;
	}
	if(document.all){
     	partnerValue = partnerRow[cellNo].getElementsByTagName("div")[0].value;
	} else {
    	partnerValue = partnerRow[cellNo].getElementsByTagName("div")[0].value.textContent;
	}
	var variance =parseInt(sysValue) - parseInt(partnerValue);
	
	return variance;
	
}
function setVariance(rowNo,cellNo){
	var sysRowNo = rowNo -1;
	var partnerRowNo = rowNo;
	var varianceRowNo = rowNo +1;
	var sysRow = document.getElementById("prodDataTable").rows[sysRowNo].cells;
	var partnerRow = document.getElementById("prodDataTable").rows[partnerRowNo].cells;

	var varianceRow = document.getElementById("prodDataTable").rows[varianceRowNo].cells;
	var sysValue;
	var partnerValue;
	var varianceValue;
	if(document.all){
     	sysValue = sysRow[cellNo].getElementsByTagName("div")[0].value;
	} else {
    	sysValue = sysRow[cellNo].getElementsByTagName("div")[0].value.textContent;
	}
	if(document.all){
     	partnerValue = partnerRow[cellNo].getElementsByTagName("input")[0].value;
	} else {
    	partnerValue = partnerRow[cellNo].getElementsByTagName("input")[0].value.textContent;
	}
	
	var validity = IsNumeric(partnerValue);
	if (validity == false) 
      {
     	 alert("Please Check - Non Numeric Value!");
     	 if(document.all){
	     	partnerRow[cellNo].getElementsByTagName("input")[0].value = "";
		} else {
	    	partnerRow[cellNo].getElementsByTagName("input")[0].value.textContent = "";
		}
		
      }else{
	     varianceValue = parseInt(sysValue) - parseInt(partnerValue); 
		
		if(document.all){
	     	varianceRow[cellNo].getElementsByTagName("input")[0].value = varianceValue;
		} else {
	    	varianceRow[cellNo].getElementsByTagName("input")[0].value.textContent = varianceValue;
		}
      }

}
function IsNumeric(strString)
   //  check for valid numeric strings	
   {
   var strValidChars = "0123456789";
   var strChar;
   var blnResult = true;

   if (strString.length == 0) return false;

   //  test strString consists of valid characters listed above
   for (i = 0; i < strString.length && blnResult == true; i++)
      {
      strChar = strString.charAt(i);
      if (strValidChars.indexOf(strChar) == -1)
         {
         blnResult = false;
         }
      }
   return blnResult;
}

function submitData(rowNum){
	if(confirm("Are you Sure to enter this reco detail ?")){
		 var stbType = document.getElementById("stbTypeId").value;
		 var recoPeriod = document.getElementById("recoPeriodId").value;
		 
		 if(recoPeriod == "" || recoPeriod == "-1"){
			alert("Please select Reco Period");
			document.getElementById("recoPeriodId").focus();
			return false;
		}
		var partnerRow = document.getElementById("prodDataTable").rows[rowNum].cells;
		if(document.all){
			if(typeof(partnerRow[1].getElementsByTagName("input")[0])!="undefined")
     			var productId = partnerRow[1].getElementsByTagName("input")[0].value;
     		else
     			var productId = partnerRow[1].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[1].getElementsByTagName("input")[0])!="undefined")
     			var productId = partnerRow[1].getElementsByTagName("input")[0].value.textContent;
     		else
     			var productId = partnerRow[1].getElementsByTagName("div")[0].value.textContent;
		}	
				
		if(document.all){
			if(typeof(partnerRow[2].getElementsByTagName("input")[0])!="undefined")
     			var pendingPOOpening = partnerRow[2].getElementsByTagName("input")[0].value;
     		else
     			var pendingPOOpening = partnerRow[2].getElementsByTagName("div")[0].value;
		} else {
			if(typeof(partnerRow[2].getElementsByTagName("input")[0])!="undefined")
     			var pendingPOOpening = partnerRow[2].getElementsByTagName("input")[0].value.textContent;
     		else
     			var pendingPOOpening = partnerRow[2].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(pendingPOOpening) ==false  && !(partnerRow[2].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Pending PO Opening Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRow[3].getElementsByTagName("input")[0])!="undefined")
     			var pendingDCOpen = partnerRow[3].getElementsByTagName("input")[0].value;
     		else
     			var pendingDCOpen = partnerRow[3].getElementsByTagName("div")[0].value;
		} else {
			if(typeof(partnerRow[3].getElementsByTagName("input")[0])!="undefined")
     			var pendingDCOpen = partnerRow[3].getElementsByTagName("input")[0].value.textContent;
     		else
     		 var pendingDCOpen = partnerRow[3].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(pendingDCOpen) ==false  && !(partnerRow[3].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Pending DC Open Stock");
			return false;
		}
		if(document.all){
			if(typeof(partnerRow[4].getElementsByTagName("input")[0])!="undefined")
     			var serOpen = partnerRow[4].getElementsByTagName("input")[0].value;
     		else
     			var serOpen = partnerRow[4].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[4].getElementsByTagName("input")[0])!="undefined")
     			var serOpen = partnerRow[4].getElementsByTagName("input")[0].value.textContent;
     		else
     			var serOpen = partnerRow[4].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(serOpen) ==false  && !(partnerRow[4].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Serialized Open Stock");
			return false;
		}
		
		if(document.all){
     		if(typeof(partnerRow[5].getElementsByTagName("input")[0])!="undefined")
     			var nsrOpen = partnerRow[5].getElementsByTagName("input")[0].value;
     		else
     			var nsrOpen = partnerRow[5].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[5].getElementsByTagName("input")[0])!="undefined")
     			var nsrOpen = partnerRow[5].getElementsByTagName("input")[0].value.textContent;
     		else
     			var nsrOpen = partnerRow[5].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(nsrOpen) ==false  && !(partnerRow[5].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Non Serialized Open Stock");
			return false;
		}
		
		if(document.all){
			if(typeof(partnerRow[6].getElementsByTagName("input")[0])!="undefined")
     			var defOpen = partnerRow[6].getElementsByTagName("input")[0].value;
     		else
     			var defOpen = partnerRow[6].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[6].getElementsByTagName("input")[0])!="undefined")
     			var defOpen = partnerRow[6].getElementsByTagName("input")[0].value.textContent;
     		else
     			var defOpen = partnerRow[6].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(defOpen) ==false && !(partnerRow[6].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Defective Open Stock");
			return false;
		}
		
		if(document.all){
     		if(typeof(partnerRow[7].getElementsByTagName("input")[0])!="undefined")
     			var upgaradeOpen = partnerRow[7].getElementsByTagName("input")[0].value;
     		else
     			var upgaradeOpen = partnerRow[7].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[7].getElementsByTagName("input")[0])!="undefined")
     			var upgaradeOpen = partnerRow[7].getElementsByTagName("input")[0].value.textContent;
     		else
     			var upgaradeOpen = partnerRow[7].getElementsByTagName("div")[0].value.textContent;
		}
		if(chckBlank(upgaradeOpen) ==false  && !(partnerRow[7].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Upgrade Open Stock");
			return false;
		}
		
		
		if(document.all){
     		if(typeof(partnerRow[8].getElementsByTagName("input")[0])!="undefined")
     			var churnOpen = partnerRow[8].getElementsByTagName("input")[0].value;
     		else
     			var churnOpen = partnerRow[8].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[8].getElementsByTagName("input")[0])!="undefined")
     			var churnOpen = partnerRow[8].getElementsByTagName("input")[0].value.textContent;
     		else
     			var churnOpen = partnerRow[8].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(churnOpen) ==false  && !(partnerRow[8].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Received Churn Open Stock");
			return false;
		}
			
		if(document.all){
     		if(typeof(partnerRow[9].getElementsByTagName("input")[0])!="undefined")
     			var rcvdWH = partnerRow[9].getElementsByTagName("input")[0].value;
     		else
     			var rcvdWH = partnerRow[9].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[9].getElementsByTagName("input")[0])!="undefined")
     			var rcvdWH = partnerRow[9].getElementsByTagName("input")[0].value.textContent;
     		else
     			var rcvdWH = partnerRow[9].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(rcvdWH) ==false  && !(partnerRow[9].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Received Open Stock");
			return false;
		}
		
		if(document.all){
     		if(typeof(partnerRow[10].getElementsByTagName("input")[0])!="undefined")
     			var recInterSSdOK = partnerRow[10].getElementsByTagName("input")[0].value;
     		else
     			var recInterSSdOK = partnerRow[10].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[10].getElementsByTagName("input")[0])!="undefined")
     			var recInterSSdOK = partnerRow[10].getElementsByTagName("input")[0].value.textContent;
     		else
     			var recInterSSdOK = partnerRow[10].getElementsByTagName("div")[0].value.textContent;
		}
		if(chckBlank(recInterSSdOK) ==false  && !(partnerRow[10].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Received Inter SSD OK Stock");
			return false;
		}
			
		if(document.all){
     		if(typeof(partnerRow[11].getElementsByTagName("input")[0])!="undefined")
     			var recInterSSDDef = partnerRow[11].getElementsByTagName("input")[0].value;
     		else
     			var recInterSSDDef = partnerRow[11].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[11].getElementsByTagName("input")[0])!="undefined")
     			var recInterSSDDef = partnerRow[11].getElementsByTagName("input")[0].value.textContent;
     		else
     			var recInterSSDDef = partnerRow[11].getElementsByTagName("div")[0].value.textContent;
		}
		if(chckBlank(recInterSSDDef) ==false  && !(partnerRow[11].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Received Inter SSD Defective Stock");
			return false;
		}
			
		if(document.all){
     		if(typeof(partnerRow[12].getElementsByTagName("input")[0])!="undefined")
     			var recUpgrade = partnerRow[12].getElementsByTagName("input")[0].value;
     		else
     			var recUpgrade = partnerRow[12].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[12].getElementsByTagName("input")[0])!="undefined")
     			var recUpgrade = partnerRow[12].getElementsByTagName("input")[0].value.textContent;
     		else
     			var recUpgrade = partnerRow[12].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(recUpgrade) ==false  && !(partnerRow[12].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Received Upgrade Stock");
			return false;
		}
		
		if(document.all){
     		if(typeof(partnerRow[13].getElementsByTagName("input")[0])!="undefined")
     			var recChurn = partnerRow[13].getElementsByTagName("input")[0].value;
     		else
     			var recChurn = partnerRow[13].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[12].getElementsByTagName("input")[0])!="undefined")
     			var recChurn = partnerRow[13].getElementsByTagName("input")[0].value.textContent;
     		else
     			var recChurn = partnerRow[13].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(recChurn) ==false  && !(partnerRow[13].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Received Churn Stock");
			return false;
		}
		
		if(document.all){
     		if(typeof(partnerRow[14].getElementsByTagName("input")[0])!="undefined")
     			var returnedOK = partnerRow[14].getElementsByTagName("input")[0].value;
     		else
     			var returnedOK = partnerRow[14].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[14].getElementsByTagName("input")[0])!="undefined")
     			var returnedOK = partnerRow[14].getElementsByTagName("input")[0].value.textContent;
     		else
     			var returnedOK = partnerRow[14].getElementsByTagName("div")[0].value.textContent;	
		}	
		if(chckBlank(returnedOK) ==false  && !(partnerRow[14].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Return OK Stock");
			return false;
		}
		
		
		
		if(document.all){
     		if(typeof(partnerRow[15].getElementsByTagName("input")[0])!="undefined")
     			var retInterssdOk = partnerRow[15].getElementsByTagName("input")[0].value;
     		else
     			var retInterssdOk = partnerRow[15].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[15].getElementsByTagName("input")[0])!="undefined")
     			var retInterssdOk = partnerRow[15].getElementsByTagName("input")[0].value.textContent;
     		else
     			var retInterssdOk = partnerRow[15].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(retInterssdOk) ==false  && !(partnerRow[15].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Return Inter SSD OK Stock");
			return false;
		}
		
		
		if(document.all){
     		if(typeof(partnerRow[16].getElementsByTagName("input")[0])!="undefined")
     			var retInterssdDef = partnerRow[16].getElementsByTagName("input")[0].value;
     		else
     			var retInterssdDef = partnerRow[16].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[16].getElementsByTagName("input")[0])!="undefined")
     			var retInterssdDef = partnerRow[16].getElementsByTagName("input")[0].value.textContent;
     		else
     			var retInterssdDef = partnerRow[16].getElementsByTagName("div")[0].value;
		}	
		if(chckBlank(retInterssdDef) ==false  && !(partnerRow[16].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Returned Inter SSD Defective Stock");
			return false;
		}
		
				
		if(document.all){
     		if(typeof(partnerRow[17].getElementsByTagName("input")[0])!="undefined")
     			var retDoaBI = partnerRow[17].getElementsByTagName("input")[0].value;
     		else
     			var retDoaBI = partnerRow[17].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[17].getElementsByTagName("input")[0])!="undefined")
     			var retDoaBI = partnerRow[17].getElementsByTagName("input")[0].value.textContent;
     		else
     			var retDoaBI = partnerRow[17].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(retDoaBI) ==false  && !(partnerRow[17].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Return Dead On Arrival Before Installation Stock");
			return false;
		}
		

		if(document.all){
     		if(typeof(partnerRow[18].getElementsByTagName("input")[0])!="undefined")
     			var retDoaAi = partnerRow[18].getElementsByTagName("input")[0].value;
     		else
     			var retDoaAi = partnerRow[18].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[18].getElementsByTagName("input")[0])!="undefined")
     			var retDoaAi = partnerRow[18].getElementsByTagName("input")[0].value.textContent;
     		else
     		var retDoaAi = partnerRow[18].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(retDoaAi) ==false && !(partnerRow[18].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Return Dead On Arrival After Installation Stock");
			return false;
		}
		//**************
		if(document.all){
     		if(typeof(partnerRow[19].getElementsByTagName("input")[0])!="undefined")
     			var retC2s = partnerRow[19].getElementsByTagName("input")[0].value;
     		else
     			var retC2s = partnerRow[19].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[19].getElementsByTagName("input")[0])!="undefined")
     			var retC2s = partnerRow[19].getElementsByTagName("input")[0].value.textContent;
     		else
     		var retC2s = partnerRow[19].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(retC2s) ==false && !(partnerRow[19].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Return C2S Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRow[20].getElementsByTagName("input")[0])!="undefined")
     			var retChurn = partnerRow[20].getElementsByTagName("input")[0].value;
     		else
     			var retChurn = partnerRow[20].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[20].getElementsByTagName("input")[0])!="undefined")
     			var retChurn = partnerRow[20].getElementsByTagName("input")[0].value.textContent;
     		else
     		var retChurn = partnerRow[20].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(retChurn) ==false && !(partnerRow[20].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Retutn Churn Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRow[21].getElementsByTagName("input")[0])!="undefined")
     			var retDefSwap = partnerRow[21].getElementsByTagName("input")[0].value;
     		else
     			var retDefSwap = partnerRow[21].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[21].getElementsByTagName("input")[0])!="undefined")
     			var retDefSwap = partnerRow[21].getElementsByTagName("input")[0].value.textContent;
     		else
     		var retDefSwap = partnerRow[21].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(retDefSwap) ==false && !(partnerRow[21].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Return Defective Swap");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRow[22].getElementsByTagName("input")[0])!="undefined")
     			var activation = partnerRow[22].getElementsByTagName("input")[0].value;
     		else
     			var activation = partnerRow[22].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[22].getElementsByTagName("input")[0])!="undefined")
     			var activation = partnerRow[22].getElementsByTagName("input")[0].value.textContent;
     		else
     		var activation = partnerRow[22].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(activation) ==false && !(partnerRow[22].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Activation Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRow[23].getElementsByTagName("input")[0])!="undefined")
     			var invChange = partnerRow[23].getElementsByTagName("input")[0].value;
     		else
     			var invChange = partnerRow[23].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[23].getElementsByTagName("input")[0])!="undefined")
     			var invChange = partnerRow[23].getElementsByTagName("input")[0].value.textContent;
     		else
     		var invChange = partnerRow[23].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(invChange) ==false && !(partnerRow[23].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Inventory Change Stock");
			return false;
		}
		/*if(document.all){
     		if(typeof(partnerRow[25].getElementsByTagName("input")[0])!="undefined")
     			var retDoaAi = partnerRow[25].getElementsByTagName("input")[0].value;
     		else
     			var retDoaAi = partnerRow[25].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[25].getElementsByTagName("input")[0])!="undefined")
     			var retDoaAi = partnerRow[25].getElementsByTagName("input")[0].value.textContent;
     		else
     		var retDoaAi = partnerRow[25].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(returnedClosing) ==false){
			alert("Please Enter Dead On Arrival After Installation Stock");
			return false;
		}*/
		if(document.all){
     		if(typeof(partnerRow[25].getElementsByTagName("input")[0])!="undefined")
     			var serClosing = partnerRow[25].getElementsByTagName("input")[0].value;
     		else
     			var serClosing = partnerRow[25].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[25].getElementsByTagName("input")[0])!="undefined")
     			var serClosing = partnerRow[25].getElementsByTagName("input")[0].value.textContent;
     		else
     		var serClosing = partnerRow[25].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(serClosing) ==false && !(partnerRow[25].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Serialized Closing Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRow[26].getElementsByTagName("input")[0])!="undefined")
     			var nserClosing = partnerRow[26].getElementsByTagName("input")[0].value;
     		else
     			var nserClosing = partnerRow[26].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[26].getElementsByTagName("input")[0])!="undefined")
     			var nserClosing = partnerRow[26].getElementsByTagName("input")[0].value.textContent;
     		else
     		var nserClosing = partnerRow[26].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(nserClosing) ==false && !(partnerRow[26].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Non Serialized Closing Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRow[27].getElementsByTagName("input")[0])!="undefined")
     			var defClosing = partnerRow[27].getElementsByTagName("input")[0].value;
     		else
     			var defClosing = partnerRow[27].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[27].getElementsByTagName("input")[0])!="undefined")
     			var defClosing = partnerRow[27].getElementsByTagName("input")[0].value.textContent;
     		else
     		var defClosing = partnerRow[27].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(defClosing) ==false && !(partnerRow[27].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Defecting Closing Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRow[28].getElementsByTagName("input")[0])!="undefined")
     			var upgradeClosing = partnerRow[28].getElementsByTagName("input")[0].value;
     		else
     			var upgradeClosing = partnerRow[28].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[28].getElementsByTagName("input")[0])!="undefined")
     			var upgradeClosing = partnerRow[28].getElementsByTagName("input")[0].value.textContent;
     		else
     		var upgradeClosing = partnerRow[28].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(upgradeClosing) ==false && !(partnerRow[28].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Upgrade Closing Stock Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRow[29].getElementsByTagName("input")[0])!="undefined")
     			var churnClosing = partnerRow[29].getElementsByTagName("input")[0].value;
     		else
     			var churnClosing = partnerRow[29].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[29].getElementsByTagName("input")[0])!="undefined")
     			var churnClosing = partnerRow[29].getElementsByTagName("input")[0].value.textContent;
     		else
     		var churnClosing = partnerRow[29].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(churnClosing) ==false && !(partnerRow[29].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Churn Closing Stock");
			return false;
		}
		if(document.all){
     		if(typeof(partnerRow[30].getElementsByTagName("input")[0])!="undefined")
     			var penPOClosing = partnerRow[30].getElementsByTagName("input")[0].value;
     		else
     			var penPOClosing = partnerRow[30].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[30].getElementsByTagName("input")[0])!="undefined")
     			var penPOClosing = partnerRow[30].getElementsByTagName("input")[0].value.textContent;
     		else
     		var penPOClosing = partnerRow[30].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(penPOClosing) ==false && !(partnerRow[30].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Pending PO Closing Stock");
			return false;
		}
		
		if(document.all){
     		if(typeof(partnerRow[31].getElementsByTagName("input")[0])!="undefined")
     			var penDCClosing = partnerRow[31].getElementsByTagName("input")[0].value;
     		else
     			var penDCClosing = partnerRow[31].getElementsByTagName("div")[0].value;
		} else {
    		if(typeof(partnerRow[31].getElementsByTagName("input")[0])!="undefined")
     			var penDCClosing = partnerRow[31].getElementsByTagName("input")[0].value.textContent;
     		else
     		var penDCClosing = partnerRow[31].getElementsByTagName("div")[0].value.textContent;
		}	
		if(chckBlank(penDCClosing) ==false && !(partnerRow[31].getElementsByTagName("input")[0].disabled)){
			alert("Please Enter Pending DC Closing Stock");
			return false;
		}
		
		if(pendingPOOpening=="")
		pendingPOOpening="0";
		if(pendingDCOpen=="")
		pendingDCOpen="0";		
		if(serOpen=="")
		serOpen="0";		
		if(nsrOpen=="")
		nsrOpen="0";	
			if(defOpen=="")
		defOpen="0";	
			if(upgaradeOpen=="")
		upgaradeOpen="0";	
			if(churnOpen=="")
		churnOpen="0";	
			if(rcvdWH=="")
		rcvdWH="0";	
			if(recInterSSdOK=="")
		recInterSSdOK="0";
		if(recInterSSDDef=="")
		recInterSSDDef="0";
		if(recUpgrade=="")
		recUpgrade="0";
		if(recChurn=="")
		recChurn="0";
		if(returnedOK=="")
		returnedOK="0";
		if(retInterssdOk=="")
		retInterssdOk="0";
		if(retInterssdDef=="")
		retInterssdDef="0";
		if(retDoaBI=="")
		retDoaBI="0";
		if(retDoaAi=="")
		retDoaAi="0";
		
		if(retC2s=="")
		retC2s="0";
		if(retChurn=="")
		retChurn="0";
		if(retDefSwap=="")
		retDefSwap="0";
		if(activation=="")
		activation="0";
		if(invChange=="")
		invChange="0";
		if(serClosing=="")
		serClosing="0";
		
		if(nserClosing=="")
		nserClosing="0";
		if(defClosing=="")
		defClosing="0";
		if(upgradeClosing=="")
		upgradeClosing="0";
		if(churnClosing=="")
		churnClosing="0";
		if(penPOClosing=="")
		penPOClosing="0";
		if(penDCClosing=="")
		penDCClosing="0";

		var totalPartClosing = parseInt(serClosing) + parseInt(nserClosing) + parseInt(defClosing) + parseInt(upgradeClosing) +parseInt(churnClosing) + parseInt(penPOClosing) +parseInt(penDCClosing) ;
		
		var totalOpen = parseInt(serOpen) + parseInt(nsrOpen) +parseInt(defOpen) +parseInt(upgaradeOpen) +parseInt(churnOpen) + parseInt(pendingPOOpening) + parseInt(pendingDCOpen);
		var totRcvd =  parseInt(rcvdWH) + parseInt(recInterSSdOK)+ parseInt(recInterSSDDef)+ parseInt(recUpgrade)+ parseInt(recChurn);
		var totRet = parseInt(returnedOK) +parseInt(retInterssdOk)+parseInt(retInterssdDef)+parseInt(retDoaBI)+parseInt(retDoaAi)+ parseInt(retC2s)+parseInt(retChurn) +parseInt(retDefSwap)+parseInt(activation);
		
		var totalSysClosing = parseInt(totalOpen) + parseInt(totRcvd) - parseInt(totRet)
		if(totalPartClosing != totalSysClosing){
			alert("Please validate Closing Stock.");
			return false;
		}
		 
		var url = "DistReco.do?methodName=submitDetail&stbType="+stbType+"&recoPeriod="+recoPeriod+"&productId="+productId+"&pendingPOOpening="+pendingPOOpening+"&pendingDCOpen="+pendingDCOpen+"&serOpen="+serOpen+"&nsrOpen="+nsrOpen+"&defOpen="+defOpen+"&upgaradeOpen="+upgaradeOpen;
		url += "&churnOpen="+churnOpen+"&rcvdWH="+rcvdWH+"&recInterSSdOK="+recInterSSdOK+"&recInterSSDDef="+recInterSSDDef+"&recUpgrade="+recUpgrade+"&recChurn="+recChurn+"&returnedOK="+returnedOK+"&retInterssdOk="+retInterssdOk+"&retInterssdDef="+retInterssdDef;
		url += "&retDoaBI="+retDoaBI+"&retDoaAi="+retDoaAi+"&retC2s="+retC2s+"&retChurn="+retChurn+"&retDefSwap="+retDefSwap+"&activation="+activation+"&invChange="+invChange;
		url += "&serClosing="+serClosing+"&nserClosing="+nserClosing+"&defClosing="+defClosing+"&upgradeClosing="+upgradeClosing+"&churnClosing="+churnClosing+"&penPOClosing="+penPOClosing;
		url += "&penDCClosing="+penDCClosing;
		
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
		req.onreadystatechange = getProductRecoListAJAX;
		req.open("POST",url,false);
		req.send();
	}else{
		return null;
		}
}
function chckBlank(value){
	var blnResult = true;
	if(value == null || value == "null" || value == ""){
		blnResult = false; 
	}
	return blnResult;
}

function printtPage()
{				
				//var certId='161';//document.getElementById("certId").value;
				var certId=document.getElementById("certId").value;
 				var url="printRecoDetails.do?methodName=printRecoDetail&CERT_ID="+certId;
 			    window.open(url,"SerialNo","width=750,height=650,scrollbars=yes,toolbar=no");
  
}


	</script>
</HEAD>
<%if(request.getAttribute("disabledLink")==null)
{%>
<body onload="Init();">
<%}
else
{%>
<body onload="printtPage(); disableLink();Init();">
<% }%>

<p></p>
<html:form action="/DistReco.do" method="post" name="DistRecoBean"
	type="com.ibm.dp.beans.DistRecoBean" enctype="multipart/form-data">
	<html:hidden property="methodName" />
	<html:hidden property="certificateId" />
	
	<html:hidden property="certId" styleId="certId" name="DistRecoBean"/>
	<html:hidden property="disabledLink" />
		<IMG src="<%=request.getContextPath()%>/images/Reco_Detail.jpg" 
		width="544" height="25" alt=""/> 
	
	<table>
		<tr>
			<td>
			<TABLE border="0" align="left" width="100%">
				<TBODY>
					<tr>
						<td colspan="5"><strong><font color="red"
							class="text" size="15px"> <bean:write name="DistRecoBean"
							property="message" /> </font> </strong></td>
					</tr>
					<TR>
						<TD align="left" width="150"><strong><font
							color="#000000">STB Type</font><FONT COLOR="RED">*</font></TD>
						<TD width="200"><html:select property="stbTypeId"
							style="width:180px">
							<logic:notEmpty property="prodTypeList" name="DistRecoBean">
								<html:option value="-1">--All--</html:option>
								<bean:define id="prodTypeList" name="DistRecoBean"
									property="prodTypeList" />
								<html:options labelProperty="productType" property="productId"
									collection="prodTypeList" />

							</logic:notEmpty>
						</html:select></TD>

						<TD align="left" class="text pLeft15" height="29" width="150"><strong><font
							color="#000000">Reco Period</font><FONT COLOR="RED">*</font></TD>
						 <TD height="29" width="200"><html:select
							property="recoPeriodId" style="width:180px">
							<logic:notEmpty property="recoPeriodList" name="DistRecoBean">
								<html:option value="-1">--Select Reco Period--</html:option>
								<bean:define id="recoPeriodList" name="DistRecoBean"
									property="recoPeriodList" />
								<html:options labelProperty="recoPeriodName"
									property="recoPeriodId" collection="recoPeriodList" />

							</logic:notEmpty>
						</html:select></TD>
						<TD><INPUT type="button" name="button"
							onclick="getProductRecoList();" Class="medium" value="Go"></TD>
					</TR>
				</TBODY>

			</TABLE>
			</td>
		</tr>
		<tr>
			<td>
			<div id="divide"
				style="width:100%; height:310px; overflow-x:scroll; overflow-y:scroll;visibility: visible;z-index:1; left: 133px; top: 250px;">
			<table border="0" align="left" width="100%">
				<tr>
					<td>
					<table id="prodDataTable" width="100%" height='100%' border="1"
						cellpadding="2" cellspacing="0" align="center">

						<TR class="noScroll">
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Type</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Product Name</span></TD>
							<TD bgcolor="#CD0504" title="Pending PO(Opening In-Transit)" class="width104 height19 pLeft5"
								align="center" ><FONT color="white"><span  
								class="white10heading mLeft5 pRight5 mTop5">Pending PO
								</span></TD>
							<TD bgcolor="#CD0504" title="Pending DC(Opening In-Transit)" class="width104 height19 pLeft5"
								align="center"><FONT color="white"><span  
								class="white10heading mLeft5 pRight5 mTop5">Pending DC</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Serialized Open Stock"
								align="center"><FONT color="white"><span 
								class="white10heading mLeft5 pRight5 mTop5">Ser Open Stock</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Non Serialized Open Stock"
								align="center"><FONT color="white"><span 
								class="white10heading mLeft5 pRight5 mTop5">Non Ser Open Stock</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Defective Open Stock"
								align="center"><FONT color="white"><span 
								class="white10heading mLeft5 pRight5 mTop5">Def. Open Stock</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Upgrade Open Stock"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Upgrade Open Stock</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Churn Open Stock"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Churn Open Stock</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Received From WH"
								align="center"><FONT color="white"><span 
								class="white10heading mLeft5 pRight5 mTop5">Rcv. WH </span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Received Inter-SSD OK"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Rcv. Inter-SSD OK</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Received Inter-SSD Defective"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Rec. Inter-SSD Def</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Received Upgrade"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Rec. Upgrade</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Received Churn"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Rec. Churn</span></TD>
							
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Returned OK Stock"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Ret. OK
							</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Returned Inter-SSD OK"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Ret. InterSSD OK</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Returned Inter-SSD Defective"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Ret. InterSSD Def</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Return Dead On Arrival Before Installation"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Ret. DOA BI</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Return Dead On Arrival After Installation"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Ret. DOA AI</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Return C2S"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Ret. C2S</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Return Churn"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Ret. Churn</span></TD>
								<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Return Defective Swap"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Ret. Def. Swap</span></TD>
								<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Activation(Serialized+Non Serialized)"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Activation</span></TD>
								<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Inventory Change(DOA(BI)+DOA(AI)+SWAP+C2S)"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Inventory Change</span></TD>
								<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Adjustment(Serialized+Non-Serialized+ Defective)"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Adjustment</span></TD>
								<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Serialized Closing Stock"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Ser Closing Stock</span></TD>
								<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Non Serialized Closing Stock"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Non Ser Closing Stock</span></TD>
								<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Defective Closing Stock"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Def Closing Stock</span></TD>
								<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Upgrade Closing Stock"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Upgrade Closing Stock</span></TD>
								<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Churn Closing Stock"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Churn Closing Stock</span></TD>
								<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Pending PO (Closing In-Transit)"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Pend. PO</span></TD>
								<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Pending DC (Closing In-Transit)"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Pend. DC</span></TD>
								<TD bgcolor="#CD0504" class="width104 height19 pLeft5"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Submit</span></TD>
						</TR>

					</table>
					
					</td>
				</tr>
			</TABLE>

			</div>
			</td>
		</tr>
	</table>
	<table>
	<tr><td></td><td></td><td><input type="button" id="printPage" value="Print Reco" disabled="disabled" onclick="printtPage()" /> </td></tr>
			</table>
	
</html:form>
</BODY>
</html:html>
