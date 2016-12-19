// This file contains miscellaneous javascript functions that are used
// to validate user entered data.
var whitespace     = " \t\n\r";
var defaultEmptyOK = false

function isEmpty(s)
{   
   return ((s == null) || (s.length == 0))
}
// This function displays a warning message
function warnInvalid (theField, s)
{   theField.focus()
    theField.select()
    alert(s)
    return false
}
// This function displays a warning message that a field can not
// be empty and the user must enter data for that field.
function warnEmpty (theField, s)
{   theField.focus()
    alert(s)
    return false
}
// This function checks if the given character is a digit.
function isDigit (c)
{
   return ((c >= "0") && (c <= "10"))
}
// This function checks if the given character is an alphabet
function isLetter (c)
{   return ( ((c >= "a") && (c <= "z")) || ((c >= "A") && (c <= "Z")) )
}
// This function checks if the given string is an integer
function isInteger (s)
{   var i;
    for (i = 0; i < s.length; i++) {   
        var c = s.charAt(i);
        if (!isDigit(c)) return false;
    }
    return true;
}
// This function checks if the given string contains any whitespace
function isWhitespace (s)
{   var i;
    if (isEmpty(s)) return true;
    for (i = 0; i < s.length; i++) {   
        var c = s.charAt(i);
        if (whitespace.indexOf(c) == -1) {
           return false;
        }
    }
    return true;
}

//added by avadesh
// This function checks if the given string contains any whitespace
function isWhitespace1 (s)
{
	var i;
   var str
   if (isEmpty(s)) return true;
   var j = s.lastIndexOf("\\");
   str = s.substr(j+1, s.length);
   for (i =0; i < str.length; i++) {   
    var c = str.charAt(i);
	    if (c == ' ') {
	       return false;
	    }
    }
    return true;
}
// This function checks if the given string contains only aphanumeric
// characters.
function isAlphanumeric (s)
{
   
	var i;
    for (i = 0; i < s.length; i++) {   
        var c = s.charAt(i);
        if (! (isLetter(c) || isDigit(c) ) )
        return false;
    }
    
    return true;
}

function hasSpecialCharactersSearch(field, errorMsg){   
	var SpecialCharacters="`~!$^&*><{}[]+|=?':;\"";
	if (field.value.length >= 0)	{
		for(i=0; i<SpecialCharacters.length; i++)	{
			if(field.value.indexOf(SpecialCharacters.substr(i, 1))>= 0)	{ 
				alert (errorMsg);
				field.focus();
				return true;
			}
		}
		return false;
	}	
	return false;
}





// Script for handling number of rows.


var invalidFirst 	= "The First Name you entered is not valid. Please re-enter.";
var emptyUser 	= "User ID cannot be empty.";
var invalidLast 	= "The Last Name you entered is not valid. Please re-enter.";
var emptyPwd 	= "Password cannot be empty.";
var invalidName 	= "The  Name you entered is not valid. Please re-enter.";
var emptyName 	= "Name cannot be empty.";
var invalidMobile 	= "The Mobile No. you entered is not valid. Please enter integers only.";
var emptyMobile 	= "Mobile No.cannot be empty.";
var emptyFSO 	= "FSO Code cannot be empty.";
var emptyChannelID 	= "Sales Channel ID. cannot be empty.";
var emptyCircleName = "Circle name cannot be empty.";
var emptyCircleId = "Circle ID cannot be empty.";
var invalidCircleId = "Circle ID entered is not valid. Please enter Numeric only.";
var emptyfsoCode = "FSO Code cannot be empty.";
var emptyConnectName = "Connect Name cannot be empty.";
var emptyConnectId = "Connect ID cannot be empty.";
var invalidConnectId = "Connect ID entered is not valid. Please enter Numeric only.";

var emptyAgencyName = "Agency Name cannot be empty.";
var emptyAgencyId = "Agency ID cannot be empty.";



var invalidYear 	= "The Year entered is not valid. Please re-enter.";
var emptyYear 	= "Year field cannot be empty.";

var emptyRows 	= "Add rows cannot be empty.";
var invalidRow 	= "Row Number is not valid.Please re-enter.";
var emptyUtility 	= "The utility name field cannot be left blank. Please enter your utility name.";
var invalidUtility 	= "Utility Name is not valid.Please re-enter.";
var emptyVersion 	= "The Version No.field cannot be left blank. Please enter your Version No.";
var invalidVersion 	= "Versiion No.is not valid.Please re-enter.";
var checkRecords = "Max Records should be greater than Min Records.Please rectify."
var checkTime = "Start time cannot be greater than end time.Please rectify."
var invalidGroup = "Please select the group name.";

var emptyEmailTime = "No. of days to keep in Booked status cannot be empty.";
var emptyShorCode = "Short code cannot be empty.";



function handleFields() 
{   
   if (document.RegForm.new_user_id.value == "")
   {
		document.RegForm.new_user_id.focus();
		document.RegForm.new_user_id.select();
		alert(emptyUser);
		return false;
   }
   /*else if(!isAlphanumeric(document.RegForm.new_user_id.value))
	{
		document.RegForm.new_user_id.focus();
		document.RegForm.new_user_id.select();
		alert(invalidFirst);
		return false;
	}*/
	 if (document.RegForm.new_user_pwd.value == "")
   {
		document.RegForm.new_user_pwd.focus();
		document.RegForm.new_user_pwd.select();
		alert(emptyPwd);
		return false;
   }
  /* else if(!isAlphanumeric(document.RegForm.lname.value))
	{
		document.RegForm.lname.focus();
		document.RegForm.lname.select();
		alert(invalidLast);
		return false;
	}*/
	 if (document.RegForm.new_user_name.value == "")
   {
		document.RegForm.new_user_name.focus();
		document.RegForm.new_user_name.select();
		alert(emptyName);
		return false;
   }
   
	 if (document.RegForm.new_user_mobile_no.value == "")
   {
		document.RegForm.new_user_mobile_no.focus();
		document.RegForm.new_user_mobile_no.select();
		alert(emptyMobile);
		return false;
   }
  /* else if(isAlphanumeric(document.RegForm.new_user_mobile_no.value))
	{
		document.RegForm.new_user_mobile_no.focus();
		document.RegForm.new_user_mobile_no.select();
		alert(invalidMobile);
		return false;
	}*/
	 if (document.RegForm.new_user_fso_code.value == "")
   {
		document.RegForm.new_user_fso_code.focus();
		document.RegForm.new_user_fso_code.select();
		alert(emptyFSO);
		return false;
   }
  
	else
	{
	   document.RegForm.submit();
	   return true;
	}
}

// confirming the deletion.
function checkDelete()
{
	var agree=confirm("Do you want to delete this entry.  OK?");
	if (agree)
	return true;
		else
	return false;
}


// Validates the Circle Id and Circle Name Field in Circle Master.
function ValidateCircleMstr() 
{   
	
  if (document.all.circleName.value == "")
   {
		document.all.circleName.focus();
		document.all.circleName.select();
		alert(emptyCircleName);
		return false;
		
		
   }
   if (isEmpty(document.all.circleName, emptyCircleName))
   {
		document.all.circleName.focus();
		document.all.circleName.select();		
		return false;
		
		
   }
   
      if(hasSpecialCharactersSearch(document.all.circleName,"Characters like Hash, Dollar, Percentage, @, Exclamation , etc. not allowed!!")){
		return false;	
		
	
	}
   if (document.all.circleId.value == "")
   {
		document.all.circleId.focus();
		document.all.circleId.select();
		alert(emptyCircleId);
		return false;
		
   }
      
      if(!isPositiveNumber(document.all.circleId,invalidCircleId,1)){
	return false;
	
	}
	
}

function ValidateConnectMstr() 
{   
   if (document.all.connectName.value == "")
   {
		document.all.connectName.focus();
		document.all.connectName.select();
		alert(emptyConnectName);
		return false;
   }
     if (isEmpty(document.all.connectName, emptyConnectName))
   {
		document.all.connectName.focus();
		document.all.connectName.select();		
		return false;
   }
  /*  if(hasSpecialCharactersSearch(document.all.connectName,"Characters like Hash, Dollar, Percentage, @, Exclamation , etc. not allowed!!")){
		return false;	
	}*/

   if (document.all.connectId.value == "")
   {
		document.all.connectId.focus();
		document.all.connectId.select();
		alert(emptyConnectId);
		return false;
   }
     
   if(!isInteger(document.all.connectId.value.trim())){
     document.all.connectId.focus();
	document.all.connectId.select();
     alert(invalidConnectId);
	return false;
	}
}
 
function ValidateAgencyMstr() 
{   
   if (document.all.agencyName.value == "")
   {
		document.all.agencyName.focus();
		document.all.agencyName.select();
		alert(emptyAgencyName);
		return false;
   }

   if (document.all.agency_type.value == -1)
   {
		document.all.agency_type.focus();
		alert("Select Agency Type");
		return false;
   }
   
     if (isEmpty(document.all.agencyName, emptyAgencyName))
   {
		document.all.agencyName.focus();
		document.all.agencyName.select();		
		return false;
   } 
      if(hasSpecialCharactersSearch(document.all.agencyName,"Characters like Hash, Dollar, Percentage, @, Exclamation , etc. not allowed!!")){
		return false; 
	}
}

function chkBulkBooking()
{
	var agree=confirm("Do you want to change the status.  OK?");
	if (agree)
	return true;
		else
	return false;
	
}

//Radio button change status
function chkBulkBooking(status,path)
{
	if(status == "Disabled")
	status = "Enable";
	else
	status = "Disable";
	var agree=confirm("Do you want to "+status+" bulk booking.  OK?");
	if (agree)
	{
	location = "bulkrights.jsp?"+path;
	return true;
	}
	else
	{
		window.location.reload(); 
		return false;	
	}
	
}
function delArchive(Date1,Date2)
{
	var agree=confirm("Do you want to Delete and Archive this Report. OK?");	
	if (agree)
	{
		location = "DelPriceNumber.jsp?date1="+Date1+"&date2="+Date2;
		return true;
	}
	else
	{
		return false;
	}
	
}

