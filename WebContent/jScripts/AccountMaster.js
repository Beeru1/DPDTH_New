
	// trim the spaces
		function trimAll(sString) {
		while (sString.substring(0,1) == ' '){
		sString = sString.substring(1, sString.length);
		}		
		while (sString.substring(sString.length-1, sString.length) == ' '){
		sString = sString.substring(0,sString.length-1);	
		}
		return sString;
	}

	var addressStatus=false;
	// function that checks that is any field value is blank or not
	function validate(){
		if(document.getElementById("accountCode").value==null || document.getElementById("accountCode").value==""){
			alert('<bean:message bundle="error" key="error.account.accountcode" />');
			document.getElementById("accountCode").focus();
			return false; 
		}
		var accCode = document.getElementById("accountCode").value;
		var firstChar = isNumber(accCode.charAt(0));
		if(firstChar==true){
			alert("User Name cannot begin with a numeric character.");
			document.getElementById("accountCode").focus();
			return false;
		}
		if(document.getElementById("accountCode").value.length < 8)
		{
			alert("User Name must contain at least 8 characters.");
			document.getElementById("accountCode").focus();
			return false;
		}
		if(document.getElementById("accountName").value==null || document.getElementById("accountName").value==""){
			alert('<bean:message bundle="error" key="error.account.accountname" />');
			document.getElementById("accountName").focus();
			return false; 
		}
		var accName = document.getElementById("accountName").value;
		var firstChar = isNumber(accName.charAt(0));
		if(firstChar==true){
			alert("Account Name cannot begin with a numeric character.");
			document.getElementById("accountName").focus();
			return false;
		}
		if(document.getElementById("contactName").value==null || document.getElementById("contactName").value==""){
			alert('<bean:message bundle="error" key="error.account.contactname" />');
			document.getElementById("contactName").focus();
			return false; 
		}
		var contactName = document.getElementById("contactName").value;
		var firstChar = isNumber(contactName.charAt(0));
		if(firstChar==true){
			alert("Contact Name cannot begin with a numeric character.");
			document.getElementById("contactName").focus();
			return false;
		}
		// Validate Email
		var emailID=document.forms[0].email;
		var errorMsg ='<bean:message bundle="error" key="errors.user.email_invalid" />'; 
	    if(!(document.getElementById("email").value==null || document.getElementById("email").value=="")){
			if (!(isEmail(emailID.value, errorMsg))){
				emailID.focus();
				return false;
			}
		}
		if(!(document.getElementById("simNumber").value==null|| document.getElementById("simNumber").value=="")){
			if(!(document.getElementById("simNumber").value.length==20 || document.getElementById("simNumber").value.length==19)){
				alert('<bean:message bundle="error" key="error.account.simnolength" />');
				document.getElementById("simNumber").focus();
				return false;
			} else if(isNumber(document.forms[0].simNumber.value)==false){
					alert('<bean:message bundle="error" key="error.account.simnonumeric" />');
					return false;
			}else if(document.getElementById("simNumber").value.length==20){
				if(isLastCharAlphabet(document.forms[0].simNumber.value)==false){
					alert('<bean:message bundle="error" key="error.account.simnoinvalid" />');
					return false;
				}
			}
		} 
		if(document.getElementById("mobileNumber").value==null || document.getElementById("mobileNumber").value==""){
			alert('<bean:message bundle="error" key="error.account.mobilenumber" />');
			document.getElementById("mobileNumber").focus();
			return false; 
		}
		if((document.getElementById("mobileNumber").value.length)<10){
			alert('<bean:message bundle="error" key="error.account.mobilenumberlength" />');
			document.getElementById("mobileNumber").focus();
			return false;
		}
		 if(document.getElementById("accountPinNumber").value==null || document.getElementById("accountPinNumber").value==""){
			alert('<bean:message bundle="error" key="error.account.pin" />');
			document.getElementById("accountPinNumber").focus();
			return false; 
		}
				
		
		if((document.getElementById("accountPinNumber").value.length)<6){
			alert('<bean:message bundle="error" key="error.account.pinlength" />');
			document.getElementById("accountPinNumber").focus();
			return false; 
		}
		
		var pinNumber=document.getElementById("accountPinNumber").value;	
		if((pinNumber*1)==0){
		alert('<bean:message bundle="error" key="error.account.invalidpin" />');
		document.getElementById("accountPinNumber").focus();
		return false;
		}
	 	var pinNumber=document.getElementById("accountPinNumber").value;
		if(pinNumber.indexOf(0)==0){
		alert('<bean:message bundle="error" key="error.account.invalidpinformat" />');
		document.getElementById("accountPinNumber").focus();
		return false;
		}
		if(document.getElementById("circleId").value == ""){
			alert('<bean:message bundle="error" key="error.account.circleid" />');
			document.getElementById("circleId").focus();
			return false; 
		}
		if(document.getElementById("circleId").value != "0"){
			if(document.getElementById("accountZoneId").value==null || document.getElementById("accountZoneId").value==""){
				alert('<bean:message bundle="error" key="error.account.zone" />');
				document.getElementById("accountZoneId").focus();
				return false; 
			}
			if(document.getElementById("accountCityId").value==null || document.getElementById("accountCityId").value==""){
				alert('<bean:message bundle="error" key="error.account.city" />');
				document.getElementById("accountCityId").focus();
				return false; 
			}
			
		if(parseInt(document.getElementById("accountLevel").value) == 8){
			if (!(document.getElementById("accountCityId").value=="")){
				if(document.getElementById("beatId").value==null || document.getElementById("beatId").value=="0"){
					alert('<bean:message bundle="error" key="error.account.beat" />');
					document.getElementById("beatId").focus();
					return false; 
				}
			}
		}
		}
		if(document.getElementById("accountAddress").value==null || document.getElementById("accountAddress").value==""){
			alert('<bean:message bundle="error" key="error.account.accountaddress" />');
			document.getElementById("accountAddress").focus();
			return false; 
		}
		if(document.getElementById("accountAddress2").value==null || document.getElementById("accountAddress2").value==""){
			alert('<bean:message bundle="error" key="error.account.accountaddressAlternate" />');
			document.getElementById("accountAddress2").focus();
			return false; 
		}
		if(document.getElementById("accountLevel").value==null || document.getElementById("accountLevel").value=="0"){
			alert('<bean:message bundle="error" key="error.account.invalidlevel" />');
			document.getElementById("accountLevel").focus();
			return false; 
		}

//		if(isNull('document.forms[0]','groupId')|| document.getElementById("groupId").value=="0"){  
	//		alert('<bean:message bundle="error" key="errors.user.groupid_required" />');
//			document.getElementById("groupId").focus();
//			return false; 
//		}

		if(parseInt(document.getElementById("accountLevel").value) < 7){
		if(document.getElementById("IPassword").value==null || document.getElementById("IPassword").value==""){
			alert('<bean:message bundle="error" key="error.account.IPassword" />');
			document.getElementById("IPassword").focus();
			return false; 
		}
		if(document.getElementById("checkIPassword").value==null || document.getElementById("checkIPassword").value==""){
			alert('<bean:message bundle="error" key="error.account.checkipassword" />');
			document.getElementById("checkIPassword").focus();
			return false; 
		}
		if((document.getElementById("IPassword").value.length)<8){
			alert('<bean:message bundle="error" key="error.account.passwordlength" />');  
			document.getElementById("IPassword").value='';
			document.getElementById("checkIPassword").value='';
			document.getElementById("IPassword").focus();
			return false;
		}
		if(document.getElementById("IPassword").value!=document.getElementById("checkIPassword").value){
			alert('<bean:message bundle="error" key="error.account.passwordmatch" />');
			document.getElementById("IPassword").value='';
			document.getElementById("checkIPassword").value='';
			document.getElementById("IPassword").focus();
			return false; 
		}
}		
		if(document.getElementById("heirarchyId").value=="1"){

			if((!document.getElementById("parentAccount").disabled)&& (document.getElementById("parentAccount").value==null||document.getElementById("parentAccount").value=="")){

				alert('<bean:message bundle="error" key="error.account.parentAccount" />');
				document.getElementById("searchbutton").focus();
				return false; 
			}  
		}	
		
		if(document.getElementById("showNumbers").value=="Y"){
		if(document.getElementById("lapuMobileNo").value==null|| document.getElementById("lapuMobileNo").value==""){
			alert('<bean:message bundle="error" key="error.account.lapuMobileNo" />');
			document.getElementById("lapuMobileNo").focus();
			return false; 
		}
		if(document.getElementById("lapuMobileNo").value != "" || document.getElementById("lapuMobileNo").value != null){
		if((document.getElementById("lapuMobileNo").value.length)<10){
			alert('<bean:message bundle="error" key="error.account.mobilenumberlength" />');
			document.getElementById("lapuMobileNo").focus();
			return false;
		}
		}
		if(!(document.getElementById("lapuMobileNo1").value == "" || document.getElementById("lapuMobileNo1").value == null)){
		if((document.getElementById("lapuMobileNo1").value.length)<10){
			alert('<bean:message bundle="error" key="error.account.mobilenumberlength" />');
			document.getElementById("lapuMobileNo1").focus();
			return false;
		}
		}
		if(!(document.getElementById("FTAMobileNo").value == "" || document.getElementById("FTAMobileNo").value == null)){
		if((document.getElementById("FTAMobileNo").value.length)<10){
			alert('<bean:message bundle="error" key="error.account.mobilenumberlength" />');
			document.getElementById("FTAMobileNo").focus();
			return false;
		}
		}
		if(!(document.getElementById("FTAMobileNo1").value == "" || document.getElementById("FTAMobileNo1").value == null)){
		if((document.getElementById("FTAMobileNo1").value.length)<10){
			alert('<bean:message bundle="error" key="error.account.mobilenumberlength" />');
			document.getElementById("FTAMobileNo1").focus();
			return false;
		}
		}

		if(document.getElementById("rate").value==null || document.getElementById("rate").value=="" || parseInt(document.getElementById("rate").value)==0){

			alert('<bean:message bundle="error" key="error.account.rate" />');
			document.getElementById("rate").focus();
		    return false; 
		}
		if(isAmount(document.forms[0].rate.value,'<bean:message bundle="view" key="account.rate" />',true)==false){
			document.forms[0].rate.focus();
			return false;	
		}
		
		if(parseFloat(document.getElementById("rate").value)==0){
			alert('<bean:message bundle="error" key="error.account.invalidrate" />');
			document.getElementById("rate").focus();
			return false;
		}
	    if(parseFloat(document.getElementById("rate").value)>100){
			alert('<bean:message bundle="error" key="error.account.invalidrate" />');
			document.getElementById("rate").focus();
			return false;
		}
		if(isAmount(document.forms[0].threshold.value,'<bean:message bundle="view" key="account.threshold" />',false)==false){
			alert('<bean:message bundle="error" key="error.account.invalidthreshold" />');
			document.forms[0].threshold.focus();
			return false;	
		}
		if(document.getElementById("tradeId").value==null || document.getElementById("tradeId").value=="0"){
			alert('<bean:message bundle="error" key="error.account.tradeId" />');
			document.getElementById("tradeId").focus();
			return false; 
		}
		if(document.getElementById("tradeCategoryId").value==null || document.getElementById("tradeCategoryId").value=="0"){
			alert('<bean:message bundle="error" key="error.account.tradeCategoryId" />');
			document.getElementById("tradeCategoryId").focus();
			return false; 
		}
	}   
 		if(document.getElementById("code").value==null || document.getElementById("code").value==""){
			alert('<bean:message bundle="error" key="error.account.code" />');
			document.getElementById("code").focus();
			return false; 
		}
		if(document.getElementById("code").value != null){
		if((document.getElementById("code").value.length)<10){
			alert('<bean:message bundle="error" key="error.account.code.length" />');
			document.getElementById("code").focus();
			return false;
		}
		}		
		if(!(document.getElementById("openingDt").value==null || document.getElementById("openingDt").value=="")){	
			if(!(isDateLessToday(document.getElementById("openingDt").value))){
				alert('<bean:message bundle="error" key="error.account.date" />');
				document.getElementById("openingDt").focus();
				return false;
			}
		}
		document.forms[0].accountAddress.value=trimAll(document.forms[0].accountAddress.value);
		if(parseInt(document.getElementById("accountLevel").value) >= 6){
		document.getElementById("tradeIdInBack").value = document.getElementById("tradeId").value;
		document.getElementById("tradeCategoryIdInBack").value = document.getElementById("tradeCategoryId").value;
		document.getElementById("isBillableInBack").value = document.getElementById("isBillable").value;
		}
		document.getElementById("methodName").value="createAccount";
		return true;
	}

	// disable if distributor 	
	function parentCheck(){
		var sn;
		if(document.getElementById("heirarchyId").value=="1"){
			document.getElementById("accountLevelStatus").value= document.getElementById("accountLevel").value;
			var acclevelId=document.getElementById("accountLevel").value;
            document.getElementById("accountLevelStatus").value=acclevelId;
            document.getElementById("accountLevelId").value=document.getElementById("accountLevelStatus").options[document.getElementById("accountLevelStatus").selectedIndex].text;
		}  
// to check the hierarchy id of the selected accountlevel.
			accountLevel();
			if(document.getElementById("accountLevelFlag").value == "2"){
			document.getElementById("showParentRow").style.display="none";
			document.getElementById("showBillableTags").style.display="none";
			}else
		if(document.getElementById("accountLevelFlag").value=="1"){
			if(document.getElementById("accountLevelId").value == (parseInt(document.getElementById("accessLevelId").value)+1)){	//parseInt(document.getElementById("accountLevel").value)<=2 ||  
				document.getElementById("parentAccount").disabled=true;
//				document.getElementById("parentAccount").value="";
				document.getElementById("searchbutton").disabled=true;
				document.getElementById("divParentNotMand").style.display="none";
				document.getElementById("divParentMand").style.display="block";	
		}else{
				document.getElementById("parentAccount").disabled=false;
				document.getElementById("parentAccount").readOnly="readonly";
				document.getElementById("searchbutton").disabled=false;
//				document.getElementById("parentAccount").value="";
				document.getElementById("divParentNotMand").style.display="block";
				document.getElementById("divParentMand").style.display="none";
				}					
		if(parseInt(document.getElementById("accountLevel").value)==6){	
		document.getElementById("showNumbers").value="Y";
		document.getElementById("showBillableTags").style.display="inline";
 	    document.getElementById("isbillable").value="Y";
		document.getElementById("billableCode").value="";
		document.getElementById("isbillable").disabled=true;
		document.getElementById("openingBalance").value="0.00";
		document.getElementById("div12").style.display="none";
 //	 	document.getElementById("distManagers").style.display="inline";
	 	document.getElementById("showBeat").style.display="none";
			}
			else if(parseInt(document.getElementById("accountLevel").value)>6){
			    document.getElementById("showBillableCode").value="Y";
			    document.getElementById("showBillableTags").style.display="inline";
			    document.getElementById("isbillable").value="N";
			    document.getElementById("isbillable").disabled=true;
			    document.getElementById("openingBalance").value="0.00";
			    document.getElementById("div12").style.display="inline";
	//    	 	document.getElementById("distManagers").style.display="none";
			 	document.getElementById("showBeat").style.display="inline";
			}
			else if(parseInt(document.getElementById("accountLevel").value)<6){
				document.getElementById("showBillableCode").value="N";
				document.getElementById("showBillableTags").style.display="none";
	//		 	document.getElementById("distManagers").style.display="none";
			 	document.getElementById("showBeat").style.display="none";
				}
			if(document.getElementById("showBillableCode").value=="Y"){
		  document.getElementById("billableCode").value=="";
		}
		document.getElementById("showParentRow").style.display="inline";
	}
	// Code for State by Digvijay Singh start
	if(parseInt(document.getElementById("accountLevel").value) == 6)
		{
			document.getElementById("showStateId").style.display="inline";
		}
		else
		{
			document.getElementById("showStateId").style.display="none";
		}
	// Code for State by Digvijay Singh Ends
	

			
		if(parseInt(document.getElementById("accountLevel").value) >= 6 && document.getElementById("accountLevelFlag").value == "1")
		{	
			sn="Y";
			document.getElementById("ShowNumbers").value="Y";
			document.getElementById("showRate").style.display="inline";
		}else{
			sn="N";
			document.getElementById("ShowNumbers").value="N";
			document.getElementById("showRate").style.display="none";
		}
		document.getElementById()
		showNumber(sn);
		onChangePassword(parseInt(document.getElementById("accountLevel").value),document.getElementById("accountLevelFlag").value);
		alert(parseInt(document.getElementById("accountLevel").value));
		
			}

	function showNumber(sn)
	{
		var frm = document.forms[DPCreateAccountForm];
		if(sn == "Y")
		{
		document.getElementById("showLapuNumber").style.display="inline";
		document.getElementById("showFTANumber").style.display="inline";
		document.getElementById("tradeCategory").style.display="inline";
		}	else if(sn == "N")
		{
		document.getElementById("showLapuNumber").style.display="none";
		document.getElementById("showFTANumber").style.display="none";
		document.getElementById("tradeCategory").style.display="none";
		}
	}
	
	function onChangePassword(al,flag)
	{
	if(al>6 && flag == "1")
	{
	document.getElementById("showPasswordRow").style.display="none";
	document.getElementById("tradeId").disabled = true;
	document.getElementById("tradeCategoryId").disabled = true;
	}
	else
	{
		document.getElementById("showPasswordRow").style.display="block";
		document.getElementById("tradeId").disabled = false;
		document.getElementById("tradeCategoryId").disabled = false;
	}
}
	
	// disable if distributor 	
	function onLoadparentCheck(){
			if(document.getElementById("heirarchyId").value=="1"){
			document.getElementById("divParentMand").style.display="none";
			document.getElementById("divParentNotMand").style.display="block";
			document.getElementById("divSearchButton").style.display="block";
//			if((document.getElementById("accountLevelId").value=="2") || (document.getElementById("accountLevel").value=="1") || (document.getElementById("accountLevelId").value=="1")){
				document.getElementById("parentAccount").disabled=true;
				document.getElementById("searchbutton").disabled=true;
				document.getElementById("divParentNotMand").style.display="block";
				document.getElementById("divParentMand").style.display="none";
/*			}else{
				document.getElementById("parentAccount").disabled=false;
				document.getElementById("parentAccount").readOnly="readonly";
				document.getElementById("searchbutton").disabled=false;
				document.getElementById("divParentNotMand").style.display="block";
				document.getElementById("divParentMand").style.display="none";
			} */
			if (document.getElementById("accessLevelId").value=="7"){
				document.getElementById("isbillable").disabled=true;
				document.getElementById("isbillable").value="N";
				document.getElementById("accountZoneId").disabled=true;
				document.getElementById("accountCityId").disabled=true;
				document.getElementById("showPasswordRow").style.dispaly="none";
				document.getElementById("tradeId").disabled = true;
				document.getElementById("tradeCategoryId").disabled = true;
				document.getElementById("showBeat").style.display="inline";
			}else {
			document.getElementById("showBeat").style.display="none";
			document.getElementById("showPasswordRow").style.dispaly="inline";
			}
		}else if (document.getElementById("heirarchyId").value=="2"){
			document.getElementById("divParentMand").style.display="inline";
			document.getElementById("divParentNotMand").style.display="none";
			document.getElementById("divSearchButton").style.display="none";
			document.getElementById("showBeat").style.display="none";
			document.getElementById("showPasswordRow").style.dispaly="inline";
		}
		accountLevel();
		if(document.getElementById("accountLevelNameHidden").value=="Airtel Distributor"){
			document.getElementById("isbillable").value="Y";
			document.getElementById("billableCode").value="";
			document.getElementById("isbillable").disabled=true;
		}
		showNumber(document.getElementById("showNumbers").value);
		showPassword(parseInt(document.getElementById("accountLevelId").value));
	} 
function showPassword(al)
{
if(al==7)
{
document.getElementById("showBillableTags").style.display="inline";
document.getElementById("showRate").style.display="inline";

}
else
{
document.getElementById("showPasswordRow").style.display="block";
document.getElementById("showBillableTags").style.display="none";
document.getElementById("showRate").style.display="none";
}
}
	function checkKeyPressed(){
		if(addressStatus==false){ 
	    	 if(window.event && window.event.keyCode=="13")
	    	{
				if(validate()){
			alert("valiodate");	
					document.getElementById("Submit").disabled=true; 
			alert("valiodate submit");	
					if(document.getElementById("submitStatus").value=="1"){  
						document.getElementById("submitStatus").value="0";
						document.forms[0].submit();
				alert(document.getElementById("submitStatus").value);		
					}
					return true;
				}else{	
					return false;
				}
			}	
		}
	}

	function submitAccount()
	{	
		if(validate()){
			document.getElementById("Submit").disabled=true; 
				document.forms[0].submit();
		}
			return true;
	}
	function setAddressStatus(){
		addressStatus=true;
	}
	
	function ResetAddressStatus(){
		addressStatus=false;
	}

	// this function open child window to search and select distributor code 
	function OpenParentWindow(){
		if(document.getElementById("accountLevel").options[document.getElementById("accountLevel").selectedIndex].text=="Airtel Distributor"){
			document.getElementById("roleName").value="Y";
		}
		else
			document.getElementById("roleName").value="N";
		if(document.getElementById("circleId").value=="0"){
			alert('<bean:message bundle="error" key="error.account.circleid" />');
			document.getElementById("circleId").focus();
			return false; 
		}else if(document.getElementById("accountLevel").value=="0"){
			alert('<bean:message bundle="error" key="error.account.accountlevel" />');
			document.getElementById("accountLevel").focus();
			return false; 
		}
		document.submitForm.action="./initCreateAccount.do?methodName=openSearchParentAccountPage";
		document.submitForm.circleid.value=document.getElementById("circleId").value;
		document.submitForm.parent.value=document.getElementById("parentAccount").value;
		displayWindow = window.open('', "newWin", "width =700,height= 220, left=50, top=100, scrollbars=yes,resizable=yes, status=yes");
       	document.submitForm.submit();
	}
	function resetParent(){
		document.getElementById("parentAccount").value="";
		document.getElementById("lapuMobileNo").value="";
		document.getElementById("lapuMobileNo1").value="";
		document.getElementById("FTAMobileNo").value="";
		document.getElementById("FTAMobileNo1").value="";
		alert("reset parent");
		document.getElementById("tradeId").value="0";
		document.getElementById("tradeCategoryId").value="0";
	}
	
	function isDateLessToday(date1){
		var startDt=new Date();
		var start = new Date(date1);
		if(start > startDt){
			return true;
		}else if(start.getDate()==startDt.getDate() && start.getMonth()==startDt.getMonth() && start.getDay()==startDt.getDay()){
			return true;
		}
		return false;
	}
	
	function checkBillable(){
	     if(document.getElementById("showBillableCode").value=="Y" ){
	     	if(document.getElementById("isBillable").value=="Y"){
		//	 		document.getElementById("searchBillablebutton").disabled=true;
			 		document.getElementById("billableCode").value="";
			}else{
		 //	 		document.getElementById("searchBillablebutton").disabled=false;
			}
		} 	
	
	}
	
	
	// this function open child window to search and select distributor code 
	function OpenBillableWindow(){
		if(document.getElementById("circleId").value=="0"){
			alert('<bean:message bundle="error" key="error.account.circleid" />');
			document.getElementById("circleId").focus();
			return false; 
		}
		else if(document.getElementById("accountLevel").value=="0"){
			alert('<bean:message bundle="error" key="error.account.accountlevel" />');
			document.getElementById("accountLevel").focus();
			return false; 
		}
		document.submitForm.action="./initCreateAccount.do?methodName=openSearchBillableAccountPage";
		document.submitForm.circleid.value=document.getElementById("circleId").value;
		document.submitForm.parent.value=document.getElementById("parentAccount").value;
		displayWindow = window.open('', "newWin", "width =700,height= 220, left=50, top=100, scrollbars=yes,resizable=yes, status=yes");
       	document.submitForm.submit();
	}
	function OpenHelpWindow(url){
		document.submitForm.action=url;
		displayWindow = window.open('', "newWin", "width =700,height= 220, left=50, top=100, scrollbars=yes,resizable=yes, status=yes");
       	document.submitForm.submit();
	}
	
	function setBillableDiv(){
		 // if user type internal 
		 
		 if(document.getElementById("heirarchyId").value=="1"){
			    if(document.getElementById("isBillable").value=="Y"){ 
				 	 document.getElementById("div12").style.display="none";
			 	}else{
			 	 	 document.getElementById("div12").style.display="block";
			 	}
		  }	
	}
	function setZoneFlag(){
	 document.getElementById("flagForZoneFocus").value=true;
	}
	function setCityFlag(){
	 document.getElementById("flagForCityFocus").value=true;
	}
	function setCategoryFlag(){
	 document.getElementById("flagForCategoryFocus").value=true;
	}
	
function accountLevel(){
var Index = document.getElementById("accountLevel").selectedIndex;
var accountLevel=document.getElementById("accountLevel").options[Index].value;
var url= "initCreateAccount.do?methodName=getAccountLevel&cond1="+accountLevel;
var elementId = "accountLevelFlag" ;
ajaxText(url,elementId,"text");
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
function getAjaxTextValues(elementId)
{
	if (req.readyState==4 || req.readyState=="complete") 
	{
		var text=req.responseText;
		 if(text!=null){
    		var accountName = text.substring(0,text.indexOf("@"));
			var hierarchyId = text.substring(text.indexOf("@")+1,text.length);
			document.getElementById(elementId).value = hierarchyId;
			document.getElementById("accountLevelNameHidden").value=accountName;
		}
		else
		document.getElementById(elementId).value = "0" ; 
	}
}
function maxlength(param) {
	var size=256;
	if(param=="1"){
	var address = document.getElementById("accountAddress").value;
    	if (address.length >= size) {
    	address = address.substring(0, size-1);
    	document.getElementById("accountAddress").value = address;
	    alert ("Account Address  can contain 256 characters only.");
    	document.getElementById("accountAddress").focus();
 	    return false;
    	}
	}else if (param=="2"){
		var address2 = document.getElementById("accountAddress2").value;
		if (address2.length >= size) {
		address2 = address2.substring(0, size-1);
    	document.getElementById("accountAddress2").value = address2;		
	    alert ("Altrenate Address can contain 256 characters only.");
    	document.getElementById("accountAddress2").focus();
	    return false;
	    }
	}
}
function getChange(condition)
{	
	var Id = "";
	if(condition == "category"){
		Id = document.getElementById("tradeId").value;
	}
	if(condition == "beat"){
		Id = document.getElementById("accountCityId").value;
	}else
	if(condition == "zone"){
		Id = document.getElementById("circleId").value;
	}else
	if(condition == "state"){
		Id = document.getElementById("circleId").value;
	}if(condition == "warehouse"){
		Id = document.getElementById("circleId").value;
	}else
	if(condition == "city"){
		Id = document.getElementById("accountZoneId").value;
	}
	 var url="initCreateAccount.do?methodName=getParentCategory&OBJECT_ID="+Id+"&condition="+condition;
	if(window.XmlHttpRequest) {
		req = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		req=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if(req==null) {
		alert("Browser Doesn't Support AJAX");
		return;
	}
	req.onreadystatechange = function(){getOnChange(condition);};
	req.open("POST",url,false);
	req.send();
}
function getOnChange(condition)
{
	if (req.readyState==4 || req.readyState=="complete") 
  	{
		var xmldoc = req.responseXML.documentElement;
		if(xmldoc == null){		
			return;
		}
		optionValues = xmldoc.getElementsByTagName("option");
		var selectObj = "";
		if(condition == "category"){
			selectObj = document.forms[0].tradeCategoryId;
		} 
		if(condition == "beat"){
			selectObj = document.forms[0].beatId;
		}else
		if(condition == "zone"){
			selectObj = document.forms[0].accountZoneId;
		}else
		if(condition == "state"){
			selectObj = document.forms[0].accountStateId;
		} if(condition == "warehouse"){
			selectObj = document.forms[0].accountWarehouseCode;
		}else
		if(condition == "city"){
			selectObj = document.forms[0].accountCityId;
		}
		selectObj.options.length = optionValues.length + 1;
		for(var i=0; i < optionValues.length; i++)
		{	
			selectObj.options[(i+1)].text = optionValues[i].getAttribute("text");
			selectObj.options[(i+1)].value = optionValues[i].getAttribute("value");
		}
	}
}

function getChangeList(condition)
{	
	
	var Id = "";
	var selectedCircleValues ="";
	selectedCircleValues =0;
	if(condition == "zone"){
		//Id = document.getElementById("circleId").value;
	Id = document.forms[0].circleIdList[0].value;
	var panfound=false;
	var count=0;
	if(Id != "" && document.forms[0].circleIdList.length >1){
			for (var i=1; i < document.forms[0].circleIdList.length; i++)
	  	 {
	   		 
				if (document.forms[0].circleIdList[i].selected)
	     	 {
	     		if(selectedCircleValues !=""){
					selectedCircleValues += ",";
	     		}
	     		count=count+1;
	     	var selectedValCircle = document.forms[0].circleIdList[i].value.split(",");
	     
	    	if((document.getElementById("accountLevel").value != 2 && document.getElementById("accountLevel").value != 14) ){
	     	if(parseInt(selectedValCircle[0]) == 0 ){
	     		alert("Select Valid Circle");
	     		document.getElementById("circleIdList").value=-1;
	     		return false;
	     	}
	    	}
	     	
	     	/*if(document.getElementById("accountLevel").value == 14) {
	     	if(parseInt(selectedValCircle[0]) == -1 ){
	     		alert("Select Valid Circle");
	     	}
	     	}
	     	else
	     	{	if(parseInt(selectedValCircle[0]) == 0 ){
		     		alert("Select Valid Circle");
		     	}
	     	}*/
	     	selectedCircleValues += selectedValCircle[0];
	     	//alert("count=="+count+".."+selectedValCircle);
	     	if(selectedValCircle[0]==0)
	     	{
			     	panfound=true;
	     	}
			     	if(count>1 && panfound==true)
			     	{
			     			alert("Please select either PAN India or Particular Circle");
		 					document.getElementById("circleIdList").value=-1;
		 					return false;
			     	}
	     	
	     	 }
	   	}
   
  }
   
}
	if((!document.getElementById("parentAccount").disabled)&& (document.getElementById("parentAccount").value!=null||document.getElementById("parentAccount").value!="")){
		document.getElementById("parentAccount").value="";
		
	}
	 var url="initCreateAccountItHelp.do?methodName=getParentCategoryList&OBJECT_ID="+selectedCircleValues+"&condition="+condition;
	 //alert("url is "+url);
	if(window.XmlHttpRequest) {
		req = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		req=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if(req==null) {
		alert("Browser Doesn't Support AJAX");
		return;
	}
	req.onreadystatechange = function(){getOnChangeList(condition);};
	req.open("POST",url,false);
	req.send();
}
function getOnChangeList(condition)
{
	if (req.readyState==4 || req.readyState=="complete") 
  	{
		var xmldoc = req.responseXML.documentElement;
		if(xmldoc == null){		
			return;
		}
		optionValues = xmldoc.getElementsByTagName("option");
		var selectObj = "";
		
		if(condition == "zone"){
			selectObj = document.forms[0].accountZoneId;
		}else
		if(condition == "state"){
			selectObj = document.forms[0].accountStateId;
		} if(condition == "warehouse"){
			selectObj = document.forms[0].accountWarehouseCode;
		}else
		if(condition == "city"){
			selectObj = document.forms[0].accountCityId;
		}
		selectObj.options.length = optionValues.length + 1;
		for(var i=0; i < optionValues.length; i++)
		{	
			selectObj.options[(i+1)].text = optionValues[i].getAttribute("text");
			selectObj.options[(i+1)].value = optionValues[i].getAttribute("value");
		}
	}
}	
