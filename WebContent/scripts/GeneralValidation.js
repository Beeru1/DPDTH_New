/**
 * This javascript files has all the methods required to perform client-side verification of
 * data entered in form.
 *
 * @author	Baysquare Inc.
 * @version 1.0
 */

//the noise string that contains characters that are not allowed
var NOISE_STRING = "'-&-,@";


/**
 * Checks if the given input box is empty. Returns true if its empty or contains
 * whitespace characters only. Else a false.
 *
 * @param	objInput	The input text object.
 * @return	True if empty else false.
 */
function isEmpty(objInput)
{
	//whitespace characters
 	var whitespace = " \b\t\n\r";
 	
	
	theInput = trimValue(objInput);
	theLength = theInput.length;

	// Is the text field empty?
  	if((theInput == null) || (theLength == 0))
	{
    	return (true);
  	}

	// Search through string's characters one by one
  	// until we find a non-whitespace character.
	// When we do, return false; if we don't, return true.
	for (var i = 0; i < theLength ; i++)
  	{
    	// Check that current character isn't whitespace.
    	var theChar = theInput.charAt(i);

    	if (whitespace.indexOf(theChar) == -1)
    	{
	    	return (false);
    	}
  	}//for loop ends

  	// All characters are whitespace.
  	return (true);
}// function isEmpty ends


/**
 * Removes the leading and trailing white spaces from the value in the given text object.
 * The trimmed string is set as the value of the given object and the same is returned by
 * the function.
 *
 * @param	objInput	The input text object.
 * @return 	The trimmed string.
 */
function trimValue(objInput)
{
	strValue = objInput.value;

	var leftIndex;
	var rigthIndex;

	//get the location of the first character that is not a white space.
	for(var i = 0; i < strValue.length; i++)
	{
		if(strValue.charAt(i) != " ")
		{
			leftIndex = i;
			break;
		}
	}

	//get the location of the last character that is not a white space.
	for(var i = strValue.length; i > 0; i--)
	{
		if(strValue.charAt(i - 1) != " ")
		{
			//not i - 1 as the last place is not included while
			// performing a substring
			rigthIndex = i;
			break;
		}
	}

	var strReturn = strValue.substring(leftIndex, rigthIndex);

	//set the same value to the input object
	objInput.value = strReturn;

	//return the trimmed string
	return strReturn;
}


/**
 * Checks if the data entered is an integer. eg 12, 12000, etc.
 * ie its a number with no decimal entries.
 * true if its a number, else false
 *
 * @param	objInput	The input text object.
 * @return 	True if a number else false.
 */
function isInteger(objInput)
{
	if (isEmpty(objInput))
	{
		return false;
	}

	theInput = trimValue(objInput);
	theLength = theInput.length ;

  	for (var i = 0 ; i < theLength ; i++)
  	{
    	if (theInput.charAt(i) < '0' || theInput.charAt(i) > '9')
    	{
      		 //the text field has a non numeric entry
        	return(false);
    	}
	}// for ends

  	return (true);
}// function isInteger ends


/**
 * Checks if the data entered is a number. eg 12.1, 12000, etc.
 * ie a number with only one decimal entry
 * true if its a number, else false
 *
 * @param	objInput	The input text object.
 * @return 	True if a number else false.
 */
function isFloat(objInput)
{
	if (isEmpty(objInput))
	{
		return false;
	}

	theInput = trimValue(objInput);
	theLength = theInput.length ;

  	var dec_count = 0 ;

  	for (var i = 0; i < theLength; i++)
  	{
    	if(theInput.charAt(i) == '.')
		{
			// the text field has decimal point entry
			dec_count = dec_count + 1;
		}
  	}

	if(dec_count > 1)
	{
		return(false);
	}

  	//check if number field contains only a single '.'
  	if (dec_count == 1 && dec_count == theLength)
  	{
  		return false;
  	}

  	for (var i = 0 ; i < theLength ; i++)
  	{
    	if (theInput.charAt(i) < '0' || theInput.charAt(i) > '9')
    	{
        	if(theInput.charAt(i) != '.')
      		{
      		 	//the text field has alphabet entry
        		return(false);
      		}
    	}
	 }// for ends

  	return (true);
}// function isFloat ends

/**
 * Checks if the entry is a valid email address in terms of format.
 * Email address must be of form a@b.c i.e.
 * there must be at least one character before and afterthe '.'
 * the characters @ and . are both required
 * Returns true for a valid email address else a false.
 *
 * @param	objInput	The input text object.
 * @return 	True for a valid email id else false.
 */
function isEmailAddressOld(objInput)
{
	if (isEmpty(objInput))
	{
		return false;
	}

	theInput = trimValue(objInput);
	theLength = theInput.length;

 	// there must be >= 1 character before @, so
  	// start looking from character[1]
  	// (i.e. second character in the text field)
  	// look for '@'
	var i = 1;
	var cnt = 0;

	for(j=0;j<=theLength;j++)
	{
		if (theInput.charAt(j) == '@')
			cnt += 1;
	}

	if (cnt != 1)
	{
		//This cant be a email address
		return(false);
	}

	while ((i < theLength) && (theInput.charAt(i) != "@"))
	{
		// search till the last character
		i++ ;
	}

	if ((i >= theLength) || (theInput.charAt(i) != "@"))
	{
		// did not find the '@' character hence can't be email address.
		return (false);
	}
	else
	{
		// go 2 characters forward so that '@' and . are not simultaneous.
		i += 2;
	}

	// look for . (dot)
	while ((i < theLength) && (theInput.charAt(i) != "."))
	{
		// keep searching for '.'
		i++ ;
	}

	// there must be at least one character after the '.'
	if ((i >= theLength - 1) || (theInput.charAt(i) != "."))
	{
		// didn't find a '.' so its not a valid email ID
		return (false);
	}
	if(hasSpecialCharactersEmail(objInput))
	{
		return false;
	}
	else
	{
		// finally its got to be email ID
		return (true);
	}
}// function isEmailAddress ends


/**
 * This function checks whether the contents of the textfield is a valid date.
 * Date should be in DD/MM/YYYY format.
 *
 * @param objEntry The textfield containing the date value
 * @return boolean - true if valid date/ false if not
 */
function isDate(objEntry)
{
	if (isEmpty(objEntry))
	{
		return false;
	}

	//trim the textfield content before validating
	theInput = trimValue(objEntry);
	theLength = theInput.length;

	if( ((theInput.charAt(2)!= "/")&& (theInput.charAt(1)!= "/")) || ((theInput.charAt(5)!= "/") && (theInput.charAt(4)!= "/") && (theInput.charAt(3)!= "/")))
	{
		return false;
	}

	var firstSlashAt = theInput.indexOf("/");
	var lastSlashAt = theInput.lastIndexOf("/");
	var year = theInput.substring(lastSlashAt+1);
	var date = theInput.substring(0, firstSlashAt);
	var month = theInput.substring(firstSlashAt+1,lastSlashAt);

	//check if month,date and year has correct number of characters.
	//This check is done so that if date/month is entered as '010'/'011', an error should
	//be thrown

	if ((year.length !=4) || (month.length>2) || (date.length > 2))
	{
		return false;
	}

	//check if date contains characters. This check is done so that if characters such
	//as '+' is entered, the function should not allow such inputs
	//if ( !isNumber(year) || !isNumber(month) || !isNumber(date) )
	//{
	//	return (false);
	//}

	//check if date is within the allowed range of values
	if(( (month > 0) && (month <= 12) && (date > 0) && (date <= 31) && (year >= 1900)) == false)
	{
		return false;
	}

	//check if date is within the range for the month
	if ( ! isDateRange( date, month, year ) )
	{
		return false;
	}

	return true;

}// function isDate ends



/**
 * This function is taken from validate.js that was sent from onsite.
 * Function which verifies whether date  supplied to it is in range
 * @param day	The day value
 * @param month	The month value
 * @param year	The year value
 * @return boolean - true if valid / false if not
 */
function isDateRange( day, month, year )
{
	var LEAP_YEAR_FEB_DAYS=29;

	day1 = new Array();
	day2 = new Array();
	days = new Array();

	days[0] = 0;
	days[1] = 31;
	days[2] = 28;
	days[3] = 31;
	days[4] = 30;
	days[5] = 31;
	days[6] = 30;
	days[7] = 31;
	days[8] = 31;
	days[9] = 30;
	days[10] = 31;
	days[11] = 30;
	days[12] = 31;

	day2[2] = 29;

	if( month.indexOf( 0 ) == "0"  )
	{
		month = month.substring( 1, month.length );
	}

	leap = ( ( year % 4 ) == 0 )&&( ( year%100 ) != 0 )||( ( year %400 ) == 0 ) ;

	if (leap)
		days[2]=LEAP_YEAR_FEB_DAYS

	if( day <= days[month] )
	{
		return true ;
	}

	return false;
 }//end of isDateRange


/**
 * date1 and date2 are dates.
 * This function checks if date2 is later than date1
 *
 * @param	date1	The text object containing date1
 * @param	date2	The text object containing date2
 * @return	True if date2 >= date1 else a false
 */
function isDate2Greater(date1, date2)
{
	if (isEmpty(date1) || isEmpty(date2))
	{
		return false;
	}

	var sd = date1.value;
	var Start = new Date(sd);

	var ed = date2.value;
	var End = new Date(ed);

	var diff = End - Start;

	if (diff >= 0)
	{
		return true;
	}

	return false;

} // function isDate2Greater ends


/**
 * Checks the given textfield for special characters. It inturn calls the actual function
 * that does the verification.
 *
 * @param	objEntry	The text object containing text
 * @return	True if special characters are found else false
 */
function hasSearchSpecialChars(objEntry)
{
	return hasSpecialChars(objEntry, NOISE_STRING);
}

/**
 * Verifies the supplied text object for existence of special characters.
 *
 * @param	objEntry	The text object containing text
 * @param	searchChars	The characters that are disallowed
 * @return	True if special characters are found else false
 */
function hasSpecialChars(objEntry, searchChars)
{
	if (isEmpty(objEntry))
	{
		return false;
	}

	theInput = trimValue(objEntry);
	theLength = theInput.length;

	// theNoisyString Is a special Characters String
	var theNoisyString;

	if (searchChars == "")
		theNoisyString = "";	//everything allowed
	else
		theNoisyString = searchChars;


	for (var i = 0; i < theLength ; i++)
	{
		// Check that current character isn't noisy.
		theChar = theInput.charAt(i);
		//(") was not checked due to String constraint
		// check it first

		if(theChar =='"')
		{
			return(true);
		}

		if ((theNoisyString.indexOf(theChar) != -1) )
		{
			return (true);
		}
	}//for loop ends

	return (false);
 } // function hasSpecialChars(theEntry) ends


 function hasSpecial(objInput,iChars)
 {
		//alert("satish");
		for (var i = 0; i < objInput.value.length; i++)
		{
			
			if (iChars.indexOf( objInput.value.charAt(i)) != -1)
			{
				//alert(objInput.value.charAt(i));
				//alert ("Your username has special characters. \nThese are not allowed.\n Please remove them and try again.");
				return (true);
			}
		}
	return (false);
 }
 
 function hasSpecialCharactersEmail(field){   
	var SpecialCharacters="`~!$^&*()=+><{}[]+|=?':;\\\"";
	if (field.value.length >= 0)	{
		for(i=0; i<SpecialCharacters.length; i++)	{
			if(field.value.indexOf(SpecialCharacters.substr(i, 1))>= 0)	{ 
					return true;
			}
		}
		return false;
	}	
	return false;
}
 
 function compareDates(value1, value2) {

//	0 if the dates are same 
//	-1 if the first one is an earlier date 
//	1 if the first one is a later date 

  /* var date1, date2;
   var month1, month2;
   var year1, year2;
   */
    var year1 = value1.charAt(6) + value1.charAt(7) + value1.charAt(8) + value1.charAt(9) ;
	var year2 = value2.charAt(6) + value2.charAt(7) + value2.charAt(8) + value2.charAt(9);
	
	var month1 = value1.charAt(3) + value1.charAt(4);
	var month2 = value2.charAt(3) + value2.charAt(4);
	
	var date1 = value1.charAt(0) + value1.charAt(1);
	var date2 = value2.charAt(0) + value2.charAt(1);

   //month1 = value1.substring (0, value1.indexOf ("/"));
   //date1 = value1.substring (value1.indexOf ("/")+1, value1.lastIndexOf ("/"));
   //year1 = value1.substring (value1.lastIndexOf ("/")+1, value1.length);

   //month2 = value2.substring (0, value2.indexOf ("/"));
   //date2 = value2.substring (value2.indexOf ("/")+1, value2.lastIndexOf ("/"));
   //year2 = value2.substring (value2.lastIndexOf ("/")+1, value2.length);

   if (year1 > year2) return 1;
   else if (year1 < year2) return -1;
   else if (month1 > month2) return 1;
   else if (month1 < month2) return -1;
   else if (date1 > date2) return 1;
   else if (date1 < date2) return -1;
   else return 0;
}

 function hello()
 {
	alert("@@@@@@@@@@@@@");
 }

//=============================================================================
//Functions added by Narugopal Sahu on 19/04/2006
//=============================================================================
 
//-----------------------------------------------------------------------------
// To check phone no.
//-----------------------------------------------------------------------------
function checkPhoneNo(value) {
	var bflag = true;
	for (i=0; i<value.length; i++) {
		var intascii = value.charCodeAt(i);
		if (((intascii >= 48) && (intascii <= 57)) // checking 0,1,..,9
					|| (intascii == 32) // for ' ' (space) char
					|| (intascii == 47) || (intascii == 45) // for '/', '-' char
					|| (intascii == 40) || (intascii == 41) || (intascii == 43))  // for '(',')','+' chars
		{ 
			bflag = true;
		}
		else {
			return false;
		}
	}
	return bflag;
}

//-----------------------------------------------------------------------------
// To Focus on particular field on page load
//-----------------------------------------------------------------------------

function setFocus(){
	var obj = eval("document.forms[0]."+arguments[0]);
	if (obj) {
		//alert(obj.type);
		obj.focus();
		return true;
	}
}
	
//--------------------------------------------------------------------------------------------
//Function Added To select or deselect all the Checks under the Header.
//--------------------------------------------------------------------------------------------
function selectDeselectAll(checkName, subject, len) { 
	var chkAll = eval("document.forms[0]."+checkName);
	if (chkAll.checked) {
		for (index=0; index < document.forms[0].elements.length; index++) {
			if (document.forms[0].elements[index].type=='checkbox' ) {
				if(document.forms[0].elements[index].name.substring(0,len) == subject)
					document.forms[0].elements[index].checked = true;
			}
		}
	}
	else {
		for (index=0; index < document.forms[0].elements.length; index++) {
			if (document.forms[0].elements[index].type=='checkbox') {
				if(document.forms[0].elements[index].name.substring(0,len) == subject)
					document.forms[0].elements[index].checked=false;
			}
		}
	}
}

//--------------------------------------------------------------------------------------------
//Function to check float value upto 2 decimal places on keypress event
//--------------------------------------------------------------------------------------------
function isPosFloat() {
	var i = window.event.keyCode;
	var str = "";
	var chr = "";
	window.status = "Ready";
	if (isNumeric()||(i==46)){
		if (i==46) {
				str = window.event.srcElement.value;
				if(str.indexOf(".")==-1){
					window.event.returnValue = true;
					return true;
				}// end of if for . not entered previously
				else {
					window.event.returnValue = false;	
					return false;
				}// end of else for . entered previously
		}//end of else if for "." entered
		else {
			// else for values entered other then "-" and "."
			str = window.event.srcElement.value;
			len = window.event.srcElement.value.length;
			if(str.indexOf(".")==-1) {
				window.event.returnValue = true;
				return true;
			}// end of if for . not entered previously
			else if((len - str.indexOf(".")) < 3 ) {
				window.event.returnValue = true;
				return true;
			}// end of if for . not entered previously
			else {
				window.event.returnValue = false;	
				return false;	
			}// end of else for . entered previously
		}// end of else for characters other then "-" entered				
	}//end of if for float charatcers entered		
	else {
		window.event.returnValue = false;	
		return false;
	}	// end of else for characters entered other the float characters
}// end of function isPosFloat()

//--------------------------------------------------------------------------------------------
//Function to check nemeric value in a field on keypress event
//--------------------------------------------------------------------------------------------
function isNumeric() { 
	var i = window.event.keyCode;
	window.status = "Ready";
	// Only Numeric characters  should be entered.
	if ((i>47 && i<58)||(i==8)){
		window.event.returnValue = true;
		return true;
		
	}// end of if for characters to be allowed
	else {
		window.event.returnValue = false;	
		return false;
	}	
}
//-----------------------------------------------------------------------------
// Email check function
//-----------------------------------------------------------------------------
function isEmailAddress(objInput) {
	var value = trim(objInput.value);
	var bflag = true;
	var strAt			= "@";
	var strDot			= ".";
	var intIndexAt		= value.indexOf(strAt);
	var intLIndAt		= value.lastIndexOf(strAt);
	var intSLength		= value.length;
	var intIndexDot		= value.indexOf(strDot);
	var intLIndDot		= value.lastIndexOf(strDot);

	for (i=0; i<value.length; i++) {
		var intascii = value.charCodeAt(i);
		if (((intascii >= 65) && (intascii <= 90)) // checking capital alphabets
					|| ((intascii >= 97) && (intascii <= 122)) // checking small alphabets
					|| (intascii == 46) || (intascii == 64) || (intascii == 95) //.,@,_ checking
					|| (intascii == 45) // - checking
					|| ((intascii >= 48) && (intascii <= 57))) { // checking 0,1,..,9
			bflag = true;
		}
		else {
			bflag = false;
			return bflag;
		}
	}
	/** Length checking for each token of the input seperated by @ and . character **/
	if (bflag) {

		if ((value.indexOf('@') < 0) || (value.indexOf('.') < 0)) {
			return false;
		}
		var arrSepList = value.split("@");
		if (arrSepList.length > 2) return false;
		else {
			for (var i=0; i<arrSepList.length; i++) {
				if (arrSepList[i].length == 0) return false;
			}
			if (arrSepList[1].indexOf(strDot) < 0) return false;
		}
		var arrDotSepList = arrSepList[1].split(".");
		for (var i=0; i<arrDotSepList.length; i++) {
			if (arrDotSepList[i].length == 0) return false;
		}
	}
	return bflag;
}

//-----------------------------------------------------------------------------
// To remove spaces from left of a string
//-----------------------------------------------------------------------------
function leftTrim(value) {
	var strReturn = value;
	while (strReturn.charCodeAt(0) == 32) {
		strReturn = strReturn.substring(1);
	}
	return strReturn;
}	

//-----------------------------------------------------------------------------
// To remove spaces from right of a string
//-----------------------------------------------------------------------------
function rightTrim(value) {
	var strReturn = value;
	while (strReturn.charCodeAt(strReturn.length-1) == 32) {
		strReturn = strReturn.substring(0,strReturn.length-1);
	}
	return strReturn;
}	

//-----------------------------------------------------------------------------
// Trim function to remove spaces from both the sides
//-----------------------------------------------------------------------------
function trim(value) {
	var strReturn = value;
	while (strReturn.charCodeAt(0) == 32) {
		strReturn = strReturn.substring(1);
	}
	while (strReturn.charCodeAt(strReturn.length-1) == 32) {
		strReturn = strReturn.substring(0,strReturn.length-1);
	}
	return strReturn;
}

//=============================================================================
//End Functions added by Narugopal Sahu on 19/04/2006
//=============================================================================
