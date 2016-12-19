function specialCharCheck(text){
  	var iChars = "!@#$%^&*()+=-[]\\\';,./{}|\":<>?"; 
           for (var i = 0; i < text.length; i++) 
           { 
             if (iChars.indexOf(text.charAt(i)) != -1) 
             { 
               return false;
             } 
   		  }
  return true;
}

// function to check email
function isEmail(str, errorMsg) {
	var at="@";
	var dot=".";
	var lat=str.indexOf(at);
	var lstr=str.length;
	var ldot=str.indexOf(dot);
	if (str.indexOf(at)==-1){
	alert(errorMsg);
	return false;
	}
	
	if (str.indexOf(at)==-1 || str.indexOf(at)==0 || str.indexOf(at)==lstr){
		alert(errorMsg);
		return false;
	}
	
	if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 || str.indexOf(dot)==(lstr-1)){
		alert(errorMsg);
		return false;
	}
	
	if (str.indexOf(at,(lat+1))!=-1){
		alert(errorMsg);
		return false;
	}
	
	if (str.substring(lat-1,lat)==dot || str.substring(lat+1,lat+2)==dot){
		alert(errorMsg);
		return false;
	}
	
	if (str.indexOf(dot,(lat+2))==-1){
		alert(errorMsg);
		return false;
	}
	
	if (str.indexOf(" ")!=-1){
		alert(errorMsg);
		return false;
	}
	
	return true;
}

//  check for valid numeric strings	
function isNumeric(strString) {
   var strValidChars = "0123456789.";
   var strChar;
   var blnResult = true;
   if (strString.length == 0) {
   return false;
   }
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

//  check for valid Alpha-numeric strings without space
function isAlphaNumeric(strString)
   {
   var strValidChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
   var strChar;
   var blnResult = true;
   if (strString.length == 0) {
   return false;
   }
 }
// check for valid Alpha-Numeric Strings with space
function isAlphaNumericWithSpace(strString)
{
   var strValidChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ";
   var strChar;
   var blnResult = true;
   var val = false;
   if (strString.length != 0) {
      for (i = 0; i < strString.length && blnResult == true; i++) {
	      strChar = strString.charAt(i);
	      if (strValidChars.indexOf(strChar) == -1) {
	         blnResult = false;
	      }
	  }
      val = blnResult;
   }
   return val;
}

//  check if last char in given string is alphabet or not.Used for 
//  Sim Validation in Create/Edit Account
function isLastCharAlphabet(strString)
{
   var strValidChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
   var strChar;
   var blnResult = true;
	
   if (strString.length == 0) {
   return false;
   }

   //  test strString consists of valid end character listed above
     strChar = strString.charAt(strString.length-1);
      if (strValidChars.indexOf(strChar) == -1)
         {
         blnResult = false;
         }
    
   return blnResult;
}

//Check if first 19 digits of Sim Number are Numeric.Used for 
//  Sim Validation in Create/Edit Account
function isNumber(strString) {
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


// check the null or blank value of form 
function isNull(form_path, field_name)
{
    field = eval(form_path + "." + field_name);
	if(field.value == "" || field.value == null)
	{
		field.focus();
		field.select();
		return true;
	}
	else
	{
		length_of_value = field.value.length;
		check_value = field.value;
		space_count =0;
		for(var i=0; i<length_of_value; i++)
		{
			if(check_value.charAt(i) == " ")
				space_count++;
		}
		if(space_count == length_of_value)
		{
			field.focus();
			field.select();
			return true;
		}
	}
	return false;
}


//Only numeric keys
function isValidNumber()
{
	var c=	event.keyCode;
	if(event.keyCode == 13){
	  return (event.keyCode) ;
	 }
	event.keyCode=(!(c>=48 && c<=57))?0:event.keyCode;
}

//Only numeric keys and dot (.)
function isValidRate()
{
	var c=	event.keyCode;
	if(event.keyCode == 13){
	  return (event.keyCode) ;
	 }
	event.keyCode=(!((c>=48 && c<=57) || c==46))?0:event.keyCode;
}

//Only numeric keys and dot (.) and Symbol(-)  for Processing fee
function isValidPfee()
{
	var c=	event.keyCode;
	if(event.keyCode == 13){
	  return (event.keyCode) ;
	 }
	event.keyCode=(!((c>=48 && c<=57) || c==46 || c==45))?0:event.keyCode;
}

//Allow only Alphanumeric keys
function isValidNumAlpha()
{
	var c= event.keyCode;
	if(event.keyCode == 13){
	  return (event.keyCode) ;
	 }
	event.keyCode=(!((c>=65 && c<=90) ||(c>=97 && c<=122)|| (c==32)|| (c>=48 && c<=57) ))?0:event.keyCode;
}



//Validity of Amount
function isValidAmount(evt, source){
	evt =(evt)?evt :window.event;
	var charCode =(evt.which)?evt.which :evt.keyCode;
	var amt = source.value;
	var len = parseInt(amt.length);
	var ind = parseInt(amt.indexOf('.'));
	if(isNaN(amt)){
		evt.keyCode = 0;
		source.value = ".00";
	}
	if(charCode == 46){
		if(ind>-1)
			evt.keyCode = 0;
	}
	else if(charCode < 48 || charCode >57)
		evt.keyCode = 0;
	else{
		if((len - ind)>4 && ind > -1)
			evt.keyCode = 0;
	}
}
// function to allow only alpha numeric without spaces text in a text field 
function alphaNumWithoutSpace(text)
{
	var c= event.keyCode;
	if(event.keyCode == 13){
	  return (event.keyCode) ;
	}
	event.keyCode=(!((c>=65 && c<=90) ||(c>=97 && c<=122)|| (c>=48 && c<=57)||(c==95) ))?0:event.keyCode;
}

// To check for Account name and Contact name
function alphaNumWithSpace(text)
{
	var c= event.keyCode;
	if(event.keyCode == 13){
	  return (event.keyCode) ;
	}
	event.keyCode=(!((c>=65 && c<=90) ||(c>=97 && c<=122)|| (c>=48 && c<=57)||(c==95)||(c==32) ))?0:event.keyCode;
}


// This function will check that the number entered is valid amount or not  		
function isValidAmount(amount)
{
    if(event.keyCode == 13){
		         return (event.keyCode) ;
	 }
	if(amount.indexOf(".")!=-1)	{
	amount=amount.substring(amount.indexOf(".")+1);
		if(field1.indexOf(".")!=-1)	{
			return false;	
		}
	}
	return true;
}

// function used to trim string in js
function ltrim(str) { 
	for(var k = 0; k < str.length && isWhitespace(str.charAt(k)); k++);
	return str.substring(k, str.length);
}
function rtrim(str) {
	for(var j=str.length-1; j>=0 && isWhitespace(str.charAt(j)) ; j--) ;
	return str.substring(0,j+1);
}
function trim(str) {
	return ltrim(rtrim(str));
}
function isWhitespace(charToCheck) {
	var whitespaceChars = " \t\n\r\f";
	return (whitespaceChars.indexOf(charToCheck) != -1);
}

//Allow Alphanumeric keys and two special characters(& and %)

function nameValidation(){
	var c= event.keyCode;
	if(event.keyCode == 13){
	   return (event.keyCode) ;
	 }
	 event.keyCode=((c==37) || (c==38))?0:event.keyCode;
 }

// function used to reset the form
function resetAll(){
	var len = document.forms[0].length;
	var controlType = null;

	for(i = 0; i < len; i++){
		controlType = document.forms[0].elements[i].type;
		if(controlType == "text"){
			document.forms[0].elements[i].value="";
		}else if(controlType == "textarea"){
			document.forms[0].elements[i].value="";
		}else if(controlType == "password"){
			document.forms[0].elements[i].value="";
		}else if(controlType == "radio"){
			document.forms[0].elements[i].checked=false;
		}else if(controlType == "checkbox") {
			document.forms[0].elements[i].checked=false;
		}else if((controlType == "select-one")&& (document.forms[0].elements[i].disabled != true)){
			document.forms[0].elements[i].selectedIndex=0;
		}
	}
}

/***********************************************************************************
FUNCTION 38:	Function to Check for a Valid Amount.<b>
				Possible set of characters are 1234567890. 
				There can only be 2 digits after the dot(.)
				Last character cannot be dot(.)
***********************************************************************************/

function isAmount(str,label,isMandatory){
	if (isMandatory){
		if(!str.length > 0){
			alert(label + " Cannot Be Left Empty");
			return false;
		}
	}
	if(str.length > 0){
		if (!checkAmount(str)){
				alert("Invalid " + label);
				return false;
			}
	return true;
	}
	else
	{
		return true;
	}
}

function checkAmount(str){
		//if(str.charAt(0) == '-'){
		//	return false;
		//}
		var re=/[^0-9-\.]/g;
		var count = 0;
		var hyphenCount=0;
		for(i = 0 ; i < str.length ; i++){
			if(str.charAt(i) == '.'){
			count++;
			}
			else if(str.charAt(i) == '-'){
			hyphenCount++;
			}
			
		}
		if(hyphenCount>0){
			return false;
		}
		if(count>1){
			return false;
		}
		if (count==1){
			var dec = str.substring(str.lastIndexOf('.')+1,str.length);
			if(dec.length > 2){
				return false;
			}
		}
		if(str.lastIndexOf('.') == str.length-1){
			return false;
		}
		return !re.test(str);
	}
	

/*********************************************************************************
	Function to Check for a Valid Amount.<b>
				Possible set of characters are 1234567890. 
				There can only be 2 digits after the dot(.)
				Last character cannot be dot(.) for Processing fee
***********************************************************************************/	
function isAmountPfee(str,label,isMandatory){
	if (isMandatory){
		if(!str.length > 0){
			alert(label + " Cannot Be Left Empty");
			return false;
		}
	}
	if(str.length > 0){
		if (!checkAmountPfee(str)){
				alert("Invalid " + label);
				return false;
			}
	return true;
	}
	else
	{
		return true;
	}
}
	
function checkAmountPfee(str){
		//if(str.charAt(0) == '-'){
		//	return false;
		//}
		var re=/[^0-9-\.]/g;
		var count = 0;
		var hyphenCount=0;
		for(i = 0 ; i < str.length ; i++){
			if(str.charAt(i) == '.'){
			count++;
			}
			else if(str.charAt(i) == '-'){
			hyphenCount++;
			}
			
		}
		if(hyphenCount>1){
			return false;
		}
		if(count>1){
			return false;
		}
		if (count==1){
			var dec = str.substring(str.lastIndexOf('.')+1,str.length);
			if(dec.length > 4){
				return false;
			}
		}
		if(str.lastIndexOf('.') == str.length-1){
			return false;
		}
		return !re.test(str);
	}
	
	
/***********************************************************************************
	Function to Check for a Valid number.<b>
				Possible set of characters are 1234567890. 
				
***********************************************************************************/

function isValidDigit(str,label,isMandatory){
	if (isMandatory){
		if(!str.length > 0){
			alert(label + " Cannot Be Left Empty");
			return false;
		}
	}
	if(str.length > 0){
		if (!checkNumber(str)){
				alert("Invalid " + label);
				return false;
			}
	return true;
	}
	else
	{
		return true;
	}
}
	
function checkNumber(str){
		if(str.charAt(0) == '-'){
			return false;
		}
		var re=/[^0-9-\.]/g;
		var count = 0;
		for(i = 0 ; i < str.length ; i++){
			if(str.charAt(i) == '.')
			count++;
		}
		if(count>=1){
			return false;
		}
		
		
		return !re.test(str);
	}
	
		
/***********************************************************************************
FUNCTION 38:	Function to Check for a Valid Amount.<b>
				Possible set of characters are 1234567890. 
				There can only be 2 digits after the dot(.)
				Last character cannot be dot(.)
***********************************************************************************/

  function isNumericNumber(strString) {
   var strValidChars = "0123456789";
   var strChar;
   var blnResult = true;
 
   
   if (strString.length == 0) {
   return false;
   }
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
	
// Function to comapare to and from date
 function isDate2Greater(date1, date2){
		var start = new Date(date1);
		var end = new Date(date2);
		var diff =end - start;
		if (diff <0){
			alert("From Date Should Be Smaller Than To Date");
			return false;
		}
		var sysDate = new Date();
		if(start > sysDate){
			alert("From Date Cannot Be More Than Today");
			return false;
		}
		if(end > sysDate){
			alert("To Date Cannot Be More Than Today");
			return false;
		}
		return true;
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

