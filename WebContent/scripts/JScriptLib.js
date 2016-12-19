/*********************************************************************************
////////////////////////////////////////////////////////////////////////////////// 
//		This is javaScript function Library which contains following functions:
//
//		FUNCTION NAMES
//		function to change Curser Type
//		function to update through KeyBoard
//		function to Move curser up & down in a table 
//		function to Color the MouseOver & MouseOut Row 
//		function to OpenLookUp on F6  
//		1.	fCheckVal(Obj,ObjName,Label,str)
//		2.	fValidate(Obj,ObjName,Label,str)
//		3.	isDate(checkStr)
//		4.	isNumber()
//		5.	Trim()	
//		6.  isEmail()
//		7.	isNumber()
//		8.   isAlphaOrNumber()
//		9.
//		10.
//		11.
//		12.
//		13.
//		14.
//		15.
//		16.
//		17.
//		18.
//		19.
//		20.
//		21.
//		22.
//		23.
//		24.
//		25.
//		26.
//		27.
//		28.
//		29.
//		30.
//		31.
//		32.
//		33.
//		34.
//		35.
//		36.
//		37.
//		38.
//		39.
//		40.
//		41.
//		42.
//		43.
//		44.
//		45.
//		
//////////////////////////////////////////////////////////////////////////////////
**********************************************************************************/
/**************************************************************************************
FUNCTION 1: Function Called from fValidate is used to 
check the current object for selected criteria.
---------------------------------------------------------------------------------------
1 - FOR CHECKING BLANK=====isBlank(Obj.value)
2 - FOR CHECKING NUMERIC=====isInteger(Obj.value)
3 - FOR CHECKING CHARACTERS (ALPHA=====isAlphaOnly(ss,min,max,bolSpace)
4 - FOR CHECKING EMAIL
5 - FOR CHECKING DATE

**************************************************************************************/




//---function to change Curser Type--
// 2 is when present curser type is none 
// 1 is when present curser type is Hand
function ChangeCurser(CurserType)
{
	if (CurserType=="1")
	{ 
	   document.forms[0].style.cursor = 'hand';
	}
	else
	{
		document.forms[0].style.cursor = 'default';

	}	   
}

//function to update & class Date through space bar through KeyBoard
function UpdateThrghKB(event,tableName,type)
{
	if (type=="Update")
	{
		if (event.keyCode==32)
		{
			if ((tableName=="tblteamMembersForAll") || (tableName=="tblteamMembersForGrp"))
			{
				SaveData();
			}
			else if(tableName=="tblNewteamLkUp")	
			{
				Save(document.forms[0].hdnLocName.value,document.forms[0].hdnShiftName.value);
			}
		}		
		else
			{
				return false;
			}
	}
	else if(type=="Date")
	{
		if (event.keyCode==9)
		{
			fPopCalendar(tableName,tableName);
		}	
		else
		{
			return false;
		}	
	}	
	else
	{
		return false;
	}		
}	


// function to Move curser up & down in a table 
var GlobalRowId=1;
function MoveUpDown(event,TableId)
{
	var tempRow=1;
	var RowTotCnt=0;
	var table = document.getElementById(TableId); 
	var RowsObj = table.getElementsByTagName("tr"); 
	RowTotCnt=RowsObj.length-1;
	if ((TableId=="tblteamMembersForAll") || (TableId=="tblteamMembersForGrp"))
	{
		RowTotCnt=RowTotCnt-1
	}	  
	if (event.keyCode==38)
	{
		tempRow=GlobalRowId-1;
		if (tempRow==0)
		{
			tempRow=RowTotCnt;
		}	
		
	}	
	else if(event.keyCode==40)
	{
		tempRow=GlobalRowId+1;
		if (GlobalRowId==RowTotCnt)
		{
			tempRow=1;
		}	
	}
	else 
	{
		return false;
	}
	
	var obj=document.getElementById('Rowid'+GlobalRowId);
	if (GlobalRowId%2==0)
	{
		obj.className='OddLine';
	}
	else
	{
		obj.className='EvenLine';
	}	
	var obj=document.getElementById('Rowid'+tempRow);
	obj.className='clsLookUpSelectedIcare';
	GlobalRowId=tempRow;
	
}

// function to OpenLookUp on F6  
	function OpenLookUp(type,event)
	{
	   
	   if (type=="RptEmpName")
	   {
		   OpenWindow(type);
	   }
	   else
	   {   	   	  
	   	  if (event.keyCode==8)
	   	  {
		    return false;		   
	   	  }
	   	  if (event.keyCode==117)
	   	  {
		    OpenLookUpForAll(type);
		    return;
	     }	
	   }
	 }  
// function to Color Rows on  MouseOver & MouseOut-------Now its not being used 
	function ColorRow(count,OverNOut)
	{
		var obj=document.getElementById('Rowid'+count);
		if (OverNOut==1)
		{
			obj.className='clsLookUpSelectedIcare';
		}
		else
		{
			if (count%2==0)
			{
				obj.className='OddLine';
			}
			else
			{
			obj.className='EvenLine';
			}		
		}		
	}
	
// function to change color of row on the Click
	function ChngColorRow(RowNum,TableId)
	{
		var TotaRows="";
		var table = document.getElementById(TableId); 
		var RowsObj = table.getElementsByTagName("tr"); 
		TotaRows=RowsObj.length
		if (TableId=="tblRotaCall")
		{
			//TotaRows=Math.floor(Math.sqrt(RowsObj.length))-1;
			TotaRows=Math.floor(RowsObj.length/4)+1;
		}
		for(i = 1; i < TotaRows; i++)
		{
			if(i%2==0)
			{
				var obj=document.getElementById('Rowid'+i);
				obj.className='OddLine';
			}
			else
			{
				var obj=document.getElementById('Rowid'+i);
				obj.className='EvenLine';
			}		    
		}
		var obj=document.getElementById('Rowid'+RowNum);
		obj.className='clsLookUpSelectedIcare';
		GlobalRowId=RowNum;
		
	}		

	
	function fCheckVal(Obj,ObjName,Label,str)
	{
		var test="";
		test=Obj.value;
		Obj.value=Trim(Obj.value)
		if (str=="1")
		{
			if(isBlank(Obj.value))
			{
				alert(Label + " Field Is Required But Has Not Been Entered");
				Obj.Value="";
				Obj.focus();
				return false;
			}
			else
			{
				return true;
			}
		}
		if(str=="2")
		{
			if(!isInteger(Obj.value))
			{
				alert(Label + " Field Can Have Integer Value Only");
				strExp1 = "document.forms[0]."+ObjName+".select()";
				eval(strExp1);
				strExp = "document.forms[0]."+ObjName+".focus()";
				eval(strExp);
				return false;
			}
			else
			{
				return true;
			}				
		}
		if(str=="3")
		{
			if(!isEmail(Obj.value))
			{		
				strExp1 = "document.forms[0]."+ObjName+".select()";
				eval(strExp1);
				strExp = "document.forms[0]."+ObjName+".focus()";
				eval(strExp);				
				return false;			
			}
			else
				return true;
		}
		if(str=="4")
		{
			if(!isDate(Obj.value))
			{		
				strExp1 = "document.forms[0]."+ObjName+".select()";
				eval(strExp1);
				strExp = "document.forms[0]."+ObjName+".focus()";
				eval(strExp);				
				return false;			
			}
			else
				return true;
		}
		/*
		if(str=="5")
		{
			 if(isAlpha(Obj.value))
			 {
				alert(Label + " field should be of Alpha characters");
				strExp1 = "document.forms[0]."+ObjName+".value=''";
				eval(strExp1);
				strExp = "document.forms[0]."+ObjName+".focus()";
				eval(strExp);
				return false;
			}
			else
			{
				return true;
			}
		}
		*/
		if(str=="6")
		{
			 if(!isAlphaOnly(Obj.value))
			 {
				alert(Label + " Field Can Have Alphabets Only");
				strExp1 = "document.forms[0]."+ObjName+".select()";
				eval(strExp1);
				strExp = "document.forms[0]."+ObjName+".focus()";
				eval(strExp);
				return false;
			}
			else
			{
				return true;
			}
		}
		if(str=="7")
		{
			 //alert("str=7");
			 if(!isAlphaNumeric(Obj.value))
			 {
				alert(Label + " Field Can Have Alphanumeric Characters Only");
				strExp1 = "document.forms[0]."+ObjName+".select()";
				eval(strExp1);
				strExp = "document.forms[0]."+ObjName+".focus()";
				eval(strExp);
				//Obj.Value="";
				//Obj.focus();
				return false;
			}
			else
			{
				return true;
			}
		}
		if(str=="8")
		{
			 if(!isAlphaOrNumber(Obj.value))
			 {
				alert(Label + " Field Can Have Alphabets Or Digits Only");
				strExp1 = "document.forms[0]."+ObjName+".select()";
				eval(strExp1);
				strExp = "document.forms[0]."+ObjName+".focus()";
				eval(strExp);
				return false;
			}
			else
			{
				return true;
			}
		}
		if(str=="9")
		{
			 if(!isSSN(Obj.value))
			 {
				alert(Label + " Field Can Only Have Values Between 10 To 15 Characters");
				strExp1 = "document.forms[0]."+ObjName+".select()";
				eval(strExp1);
				strExp = "document.forms[0]."+ObjName+".focus()";
				eval(strExp);
				return false;
			}
			else
			{
				return true;
			}
		}
		if(str=="10")
		{
			 if(!PhoneLength(Obj.value))
			 {
				alert(Label + " Field Can Have Numeric Values Of Length Less Than Ten");
				strExp1 = "document.forms[0]."+ObjName+".select()";
				eval(strExp1);
				strExp = "document.forms[0]."+ObjName+".focus()";
				eval(strExp);
				return false;
			}
			else
			{
				return true;
			}
		}
		if(str=="11")
		{
			 if(!checkDropdown(Obj.value,Label))
			 {
				//alert(Label + " field should be Selected");
				strExp1 = "document.forms[0]."+ObjName+".select()";
				eval(strExp1);
				strExp = "document.forms[0]."+ObjName+".focus()";
				eval(strExp);
				return false;
			}
			else
			{
				return true;
			}
		}
		
		if(str=="12")
		{
			 if(!checkPassword(Obj.value))
			 {
				alert(Label + " Field Can Have Numeric Value Only");
				strExp1 = "document.forms[0]."+ObjName+".select()";
				eval(strExp1);
				strExp = "document.forms[0]."+ObjName+".focus()";
				eval(strExp);
				return false;
			}
			else
			{
				return true;
			}
		}
		if(str=="13")
		{
			 if(!PhoneLength3(Obj.value))
			 {
				alert(Label + " Field Can Only Have Integer Value Of Length Three");
				strExp1 = "document.forms[0]."+ObjName+".select()";
				eval(strExp1);
				strExp = "document.forms[0]."+ObjName+".focus()";
				eval(strExp);
				return false;
			}
			else
			{
				return true;
			}
		}
		if(str=="14")
		{
			 if(!PhoneLength4(Obj.value))
			 {
				alert(Label + " Field Can Only Have An Integer Value Of Length Four");
				strExp1 = "document.forms[0]."+ObjName+".select()";
				eval(strExp1);
				strExp = "document.forms[0]."+ObjName+".focus()";
				eval(strExp);
				return false;
			}
			else
			{
				return true;
			}
		}
		if(str=="15")
		{
			 if(!isMonth(Obj.value))
			 {
				alert(Label + " Field Can Only Have Integer Value Less Than Or Equal To 12");
				strExp1 = "document.forms[0]."+ObjName+".select()";
				eval(strExp1);
				strExp = "document.forms[0]."+ObjName+".focus()";
				eval(strExp);
				return false;
			}
			else
			{
				return true;
			}
		}	
		if(str=="16")
		{
			 if(!isDay(Obj.value))
			 {
				alert(Label + " field can only have integer value less then and equal to 31");
				strExp1 = "document.forms[0]."+ObjName+".select()";
				eval(strExp1);
				strExp = "document.forms[0]."+ObjName+".focus()";
				eval(strExp);
				return false;
			}
			else
			{
				return true;
			}
		}
		if(str=="17")
		{	
			 if(!isYear(Obj.value))
			 {
				alert(Label +" Field Can Only Have Integer Values Between 1841 And 2200");
				strExp1 = "document.forms[0]."+ObjName+".select()";
				eval(strExp1);
				strExp = "document.forms[0]."+ObjName+".focus()";
				eval(strExp);
				return false;
			}
			else
			{
				return true;
			}
		}
		if(str=="18")
		{
			 if(!isWeight(Obj.value))
			 {
				alert(Label + " Field Can Only Have Integer Values Less Than Or Equal To 3000");
				strExp1 = "document.forms[0]."+ObjName+".select()";
				eval(strExp1);
				strExp = "document.forms[0]."+ObjName+".focus()";
				eval(strExp);
				return false;
			}
			else
			{
				return true;
			}
		}	
		if(str=="19")
		{
			 if(!isAlphaCharacter(Obj.value))
			 {
				alert(Label + " Field Can Have Alphabets Only");
				strExp1 = "document.forms[0]."+ObjName+".select()";
				eval(strExp1);
				strExp = "document.forms[0]."+ObjName+".focus()";
				eval(strExp);
				return false;
			}
			else
			{
				return true;
			}
		} 
		if(str=="20")
		{
			 if(!isSSN2(Obj.value))
			 {
				alert(Label + " Field Can Only Have Integer Value Of Length Two");
				strExp1 = "document.forms[0]."+ObjName+".select()";
				eval(strExp1);
				strExp = "document.forms[0]."+ObjName+".focus()";
				eval(strExp);
				return false;
			}
			else
			{
				return true;
			}
		} 
		
		if(str=="21")
		{
			 if(!isMlentxtarea(Obj))
			 {
					return false;
			}
			else
			{
				return true;
			}
		} 
		if(str=="22")
		{
			 if(!isInvalidChar(Obj.value))
			 {
				alert(Label + " Field Cannot Have A Caret Sign(^)");
				while(Obj.value.indexOf("^")!=-1)
				{
				var CarrotPos=Obj.value.indexOf("^")
				//alert(CarrotPos);
				var len=Obj.value.length;
				Obj.value=Obj.value.substring(0,CarrotPos)+Obj.value.substring(CarrotPos+1,len);
				}
				strExp1 = "document.forms[0]."+ObjName+".select()";
				eval(strExp1);
				strExp = "document.forms[0]."+ObjName+".focus()";
				eval(strExp);
				return false;
			 }
			else
			 {
				return true;
			 }
		} 
		if(str=="23")
		{
			 if(!isAlphaCity(Obj.value))
			 {
				alert(Label + " Field Can Have Alphabets And Space Only");
				strExp1 = "document.forms[0]."+ObjName+".select()";
				eval(strExp1);
				strExp = "document.forms[0]."+ObjName+".focus()";
				eval(strExp);
				//Obj.Value="";
				//Obj.focus();
				return false;
			}
			else
			{
				return true;
			}
		} 
			 	   
	}

/**************************************************************************************
FUNCTION 2: Function to validate objects
**************************************************************************************/

	function fValidate(Obj,ObjName,Label,str)
	{//
		var chk;
		if (str.indexOf(",")!=-1)
		{
			var arr=str.split(",");
			for (num = 0; num <arr.length; num++)
			{
				chk = fCheckVal(Obj,ObjName,Label,arr[num]);
				if (chk==false)
				{
					return chk;
				}
				else
				{
					continue;
				}			
			}	
			return chk;
		}
		else
		{			
			chk = fCheckVal(Obj,ObjName,Label,str);
			return chk;
		}
	}

/***********************************************************************************
FUNCTION 3:	Function to Check for valid Email ID
************************************************************************************/
/*
	function isEmail(src)
	{
		var emailReg = "^[\\w-_\.]*[\\w-_\.]\@[\\w]\.+[\\w]+[\\w]$";
		var regex = new RegExp(emailReg);
		return regex.test(src);
	}
*/
/***********************************************************************************
FUNCTION 4:	Function to Check for valid PIN CODE
************************************************************************************/

	function isPIN(arg)
	{
		var flag;
		flag=true;
		var checkOK="1234567890";
		var s = arg.value; 
		if (s.length != 6)	
		{
			return false;
		}	
		for(idx=0;idx<s.length;idx++)
		{
			ch = s.charAt(idx);
			for(ctr=0;ctr<checkOK.length;ctr++)
			{
				if (ch==checkOK.charAt(ctr))
				{
					flag=true;
					break;
				} 
				else 
				{
					flag= false;
				}    
		 		if(ctr==checkOK.length)
		 		{
		 			flag= false;
		 			break;
		 		}
			}
		 }
	 	 if(flag)
		    return true;
		 else 
		    return false; 
	}

/***********************************************************************************
FUNCTION 5:	Function to Check for a pure Alphabetic String (includes whitespace, full-stop and 
	single quotation mark)
**************************************************************************************/
	
	function isAlpha(checkStr)
	{
		var flag;
		flag=true;
		var checkOK="ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz.'";
		/*for(idx=0;idx<checkStr.length;idx++){
			ch = checkStr.charAt(idx);
			for(ctr=0;ctr<checkOK.length;ctr++)
				if (ch==checkOK.charAt(ctr)){
					flag=true;
					break;
				}     
		 	if(ctr==checkOK.length){
		 		flag= false;
		 		break;
		 	}
		 }*/
		 flag = checkCharacter(checkStr,checkOK);
	 	 if(flag)
		    return true;
		 else 
		    return false; 
	} 
	
/***********************************************************************************
FUNCTION 6:	Function to Check for a pure Alphabetic String ( Accepts - also)
**************************************************************************************/
	
	function isAlphaOnly(checkStr)
	{
		
		var flag;
		flag=true;
		var checkOK="ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz-'";
		
		/*
		for(idx=0;idx<checkStr.length;idx++){
			ch = checkStr.charAt(idx);
			for(ctr=0;ctr<checkOK.length;ctr++)
				if (ch==checkOK.charAt(ctr)){
					flag=true;
					break;
				}     
		 	if(ctr==checkOK.length){
		 		flag= false;
		 		break;
		 	}
		 }
		*/
		 flag = checkCharacter(checkStr,checkOK);
	 	 if(flag)
		    return true;
		 else 
		    return false; 
	} 

/***********************************************************************************
FUNCTION 7:	Function to Check for Alphanumeric String (includes whitespace, full-stop, comma, percent,
	hash, question mark, hyphen, exclamation and single quotation mark)
********************************************************************************************/

	function isAlphaNumeric(checkStr)
	{
		var flag;
		 var checkOK="ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz1234567890#-+";
		
		 /*
		 for(idx=0;idx<checkStr.length;idx++){
			ch = checkStr.charAt(idx);
			for(ctr=0;ctr<checkOK.length;ctr++)
				if (ch==checkOK.charAt(ctr)){
					flag=true;
					break;
				}     
		 	if(ctr==checkOK.length){
		 		flag= false;
		 		break;
		 	}
		 }
		 */
		 flag = checkCharacter(checkStr,checkOK);
	 	 if(flag)
		    return true;
		 else 
		    return false; 
	} 

/***********************************************************************************
FUNCTION 8:	Function to Check for Alphanumeric String (includes only Alphabets and Numbers)
********************************************************************************************/

	function isAlphaOrNumber(checkStr)
	{
		var flag;
		var checkOK="ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz1234567890";
		/*	
		 for(idx=0;idx<checkStr.length;idx++){
			ch = checkStr.charAt(idx);
			for(ctr=0;ctr<checkOK.length;ctr++)
				if (ch==checkOK.charAt(ctr)){
					flag=true;
					break;
				}     
		 	if(ctr==checkOK.length){
		 		flag= false;
		 		break;
		 	}
		 }
		 */
		 flag = checkCharacter(checkStr,checkOK)
	 	 if(flag)
		    return true;
		 else 
		    return false; 
	} 

/***********************************************************************************
FUNCTION 9:	Function to Check for 
************************************************************************************/

	function checkCharacter(val,pat)
	{	
		var i;
		var j;
		//var value=val.value;
		var value=val;
		var pattern=pat
		
		if (isBlank(val))				
			return false;
		else
		{
			for (i = 0; i < value.length; i++)
			{   
			    var c = value.charAt(i);
			   
			    if (pattern.indexOf(c) == -1)
					return false;
				else
				{
					flag="t"					
					continue;
				}	
			}
			if(flag=="t")
				return true;
			
		}
	}
	
	
	
/***********************************************************************************
FUNCTION :	Function to Check for pattern whether a particular character from a pattern exists
in the given value or not
************************************************************************************/

	function chkPattern(val,pat)
	{	
		var i;
		var j;
		//var value=val.value;
		var value=val;
		var pattern=pat
		if (isBlank(val))				
			return false;
		else
		{
			for (i = 0; i < value.length; i++)
			{   
			    var c = value.charAt(i);
			    
			    if (pattern.indexOf(c) != -1)
					return true;
				else
				{
					flag="f"					
					continue;
				}	
			}
			if(flag=="f")
				return false;
			
		}
	}
	
	

/***********************************************************************************
FUNCTION 10:	Function to Check for a valid Date value (includes forward slash)
***********************************************************************************/

	function isDate(dtStr,ObjName)
	{
		//alert("yes");
		if (dtStr.indexOf("-")!=-1)
		{
			///dtStr=dtStr.replace('-','/');
		dtStr = dtStr.replace(/-/g,"/");
		dtStr = dtStr.replace(/-/g,"/");
		}
		var d = new Date();
		var gdate=d.getDate();
		var gmonth1=d.getMonth();
		var gmonth=gmonth1+1;
		var gyear=d.getYear();
		var daysInMonth = DaysArray(12);
		var pos1=dtStr.indexOf(dtCh);
		var pos2=dtStr.indexOf(dtCh,pos1+1);
		var strMonth=dtStr.substring(0,pos1);
		var strDay=dtStr.substring(pos1+1,pos2);
		var strYear=dtStr.substring(pos2+1);
		strYr=strYear;
		if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1)
		if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1)
		for (var i = 1; i <= 3; i++) {
			if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1)
		}
		month=parseInt(strMonth);
		
		day=parseInt(strDay);
		year=parseInt(strYr);
		if (pos1==-1 || pos2==-1){			
			alert("The Date Format Should Be : mm/dd/yyyy");
			//strExp1 = "document.forms[0]."+ObjName+"MM.value=''";
			//strExp1 = "document.forms[0]."+ObjName+"MM.select()";
			//eval(strExp1);
			//strExp = "document.forms[0]."+ObjName+"MM.focus()";
			//eval(strExp);
			//alert(strExp1);
			//alert(strExp);*/
			return false;
		}
		if (strMonth.length<1 || month<1 || month>12){
			alert("Please Enter A Valid Month ");
			strExp1 = "document.forms[0]."+ObjName+"MM.value=''";
			eval(strExp1);
			strExp = "document.forms[0]."+ObjName+"MM.focus()";
			eval(strExp);
			//alert(strExp1);
			//alert(strExp);*/
			return false;
		}
		if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
			alert("Please Enter A Valid Day");
			//alert(ObjName);
			strExp1 = "document.forms[0]."+ObjName+"DD.value='';";
			eval(strExp1);
			strExp = "document.forms[0]."+ObjName+"DD.focus();";
			eval(strExp);
			//alert(strExp1);
			//alert(strExp);*/
			return false;
		}
		if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
			alert("Please Enter A Valid 4 Digit Year Between "+minYear+" And "+maxYear);
			strExp1 = "document.forms[0]."+ObjName+"YY.value='';";
			//strExp1 = "document.forms[0]."+ObjName+"YY.select();";
			eval(strExp1);
			strExp = "document.forms[0]."+ObjName+"YY.focus();";
			eval(strExp);
			//alert(strExp1);
			//alert(strExp);
			return false;
		}
		if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
			alert("Please Enter A Valid Date.");
			return false;
		}
		if ( strYr >gyear)
		{
			
			//alert("Invalid year");
			alert("The Entered Date Is Greater Than The Current Date. Please Enter A Valid Date.");
			return false;
			}	
		
		if (strYr == gyear)
		{	
			if ( month > gmonth) 
			{	
			alert("The Entered Date Is Greater Than The Current Date. Please Enter A Valid Date.");
			return false;	
			}
			else
			if (month == gmonth)
			{
				if ( strDay > gdate)
				{
				alert("The Entered Date Is Greater Than The Current Date. Please Enter A Valid Date.");
				return false;	
				}
			}
		}			
		return true
	}

	var dtCh= "/";
	var minYear=1841;
	var maxYear=2100;

/***********************************************************************************
FUNCTION 11:	Function to Check for a 
***********************************************************************************/

	function isInteger(s)
	{
		for (i = 0; i < s.length; i++){   
	        // Check that current character is number.
	        var c = s.charAt(i);
	        if (((c < "0") || (c > "9"))) return false;
	    }
	    // All characters are numbers.
	    return true;
	}

/***********************************************************************************
FUNCTION 12:	Function to Check for a 
***********************************************************************************/

	function stripCharsInBag(s, bag){
		var i;
	    var returnString = "";
	    // Search through string's characters one by one.
	    // If character is not in bag, append to returnString.
	    for (i = 0; i < s.length; i++){   
	        var c = s.charAt(i);
	        if (bag.indexOf(c) == -1) returnString += c;
	    }
	    return returnString;
	}

/***********************************************************************************
FUNCTION 13:	Function to Check for a 
***********************************************************************************/

	function daysInFebruary (year){
		// February has 29 days in any year evenly divisible by four,
	    // EXCEPT for centurial years which are not also divisible by 400.
	    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
	}

/***********************************************************************************
FUNCTION 14:	Function to Check for a 
***********************************************************************************/

	function DaysArray(n) {
		for (var i = 1; i <= n; i++) {
			this[i] = 31;
			if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
			if (i==2) {this[i] = 29}
	   } 
	   return this;
	}

/***************************************
FUNCTION 15: Function to Check TO TRIM Text
**************************************/

   	function Trim(sInString) 
   	{
   		sInString = sInString.replace( /^\s+/g, "" );// strip leading
   		return sInString.replace( /\s+$/g, "" );// strip trailing
   	}

/***********************************************************************************
FUNCTION 16:	Function to Check Valid E-MAILID
**************************************/

	function isEmail(str)
    {
		var at="@";
		var dot=".";
		var lat=str.indexOf(at);
		var lstr=str.length;
		var ldot=str.indexOf(dot);
		var test=str.lastIndexOf('.')
		//alert(test);
		if((str.substring(test+1))=="")
		{
			alert("Invalid E-mail ID");
		   	return false;
		}
		if (str.indexOf(at)==-1){
		   alert("Invalid E-mail ID");
		   return false;
		}

		if (str.indexOf(at)==-1 || str.indexOf(at)==0 || str.indexOf(at)==lstr){
		   alert("Invalid E-mail ID");
		   return false;
		}

		if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 || str.indexOf(dot)==lstr){
		    alert("Invalid E-mail ID");
		    return false;
		}

		 if (str.indexOf(at,(lat+1))!=-1){
		    alert("Invalid E-mail ID");
		    return false;
		 }

		 if (str.substring(lat-1,lat)==dot || str.substring(lat+1,lat+2)==dot){
		    alert("Invalid E-mail ID");
		    return false;
		 }

		 if (str.indexOf(dot,(lat+2))==-1){
		    alert("Invalid E-mail ID");
		    return false;
		 }
		
		 if (str.indexOf(" ")!=-1){
		    alert("Invalid E-mail ID");
		    return false;
		 }

 		 return true;					
	}

/***********************************************************************************
FUNCTION 17:	Function to Check Length(<6) 
**************************************/

function isSSN(s)
{
	if ((s.length > 9) || (s.length < 16))
	{
	return false;
	}
	else 
	{
	return true;
	}
}

/***********************************************************************************
FUNCTION 18:	Function to Check for a 
***********************************************************************************/

function ReplaceQuotes(s)
	{
		return s.replace(/\"/g, "'");
	}

/***********************************************************************************
FUNCTION 19:	Function to Check for a 
***********************************************************************************/


function jTrim(s)
{
	return s.replace(" ", "");
}

/***********************************************************************************
FUNCTION 20:	Function to Check for a 
***********************************************************************************/


function lTrim (s)
{
	return s.replace( /^\s*/, "" );
}

/***********************************************************************************
FUNCTION 21:	Function to Check for a 
***********************************************************************************/


function rTrim (s)
{
	return s.replace( /\s*$/, "" );
}

/***********************************************************************************
FUNCTION 22:	Function to Check for a 
***********************************************************************************/

/*
function Trim(s)
{
	return rTrim(lTrim(s));
}
*/
/***********************************************************************************
FUNCTION 23:	Function to Check for a 
***********************************************************************************/


function MaxLength(obj, max)
{
	if (obj.value.length > max)
		obj.value = obj.value.substr(0, max);
}

/***********************************************************************************
FUNCTION 24:	Function to Check for a 
***********************************************************************************/



	function isNumber(sText,i) 
	{
		var ValidChars;
		if(i==1)
		{
			 ValidChars = "0123456789";
		}
		if (i==2)
		{
			 ValidChars = "0123456789.";
		}
		
		var IsNo=true;
		var Char;
		for (i = 0; ((i < sText.length) && (IsNo == true)); i++)
		 { 
			Char = sText.charAt(i); 
			
			if (ValidChars.indexOf(Char) == -1)
			 {
				IsNo = false;
			}
		}
		return IsNo;
	} 


/***********************************************************************************
FUNCTION 25:	Function to Check for a 
***********************************************************************************/

/*
function isEmpty(s)
{
	return ((s == null) || (s.length == 0));
}
*/

/***********************************************************************************
FUNCTION 26:	Function to Check for a 
***********************************************************************************/

/*
function isNumber(s, emptyOK)
{
	// Returns true if positive number
  	var oneDecimal = false;
  	if(isEmpty(s))
    	if(isNumber.arguments.length == 1) return false;
    	else return (emptyOK == true);

  
  inputStr = s.toString();
  for (var i = 0; i < inputStr.length; i++) {
var oneChar = inputStr.charAt(i);
if (i == 0 && oneChar == "-") {
  continue;
}
if (oneChar == "." && !oneDecimal) {
  oneDecimal = true;
  continue;
}
if (oneChar < "0" || oneChar > "9") {
return false;
}
}
return true;

}
*/
/***********************************************************************************
FUNCTION 27:	Function to Check for a 
***********************************************************************************/


function isWhiteSpace(s)
{
  var whitespace = " \t\n\r";
  if(isEmpty(s)) return true;

  for(i = new Number(0); i < s.length; i++)
    if(whitespace.indexOf(s.charAt(i)) == -1) 
	  return false;

  return true;
}

/***********************************************************************************
FUNCTION 28:	Function to Check for a 
***********************************************************************************/


function TimeValue( field )
{
	return Match(field,'(0[0-9]|1[0-2]):[0-5][0-9](:[0-5][0-9]){0,1}',true);
} // TimeValue

/***********************************************************************************
FUNCTION 29:	Function to Check for a 
***********************************************************************************/


function Currency( field )
{
   return Match(field,'[$]*([0-9]{1,3}[,])*[0-9]{0,3}\.[0-9]{2}',true);
} // Currency

/***********************************************************************************
FUNCTION 30:	Function to Check for a 
***********************************************************************************/

/*
function SSN( field ) 
{
   return Match(field,'[0-9]{3}-[0-9]{2}-[0-9]{4}',true);
} // SSN
*/
/***********************************************************************************
FUNCTION 31:	Function to Check for a 
***********************************************************************************/


function Phone(field) 
{
	///alert("1->");
	alert(Match(field,'(1-|1 ){0,1}[0-9]{3}-[0-9]{3}-[0-9]{4}',true));
   if (Match(field,'(1-|1 ){0,1}[0-9]{3}-[0-9]{3}-[0-9]{4}',true) ||  Match(field,'(1-|1 ){0,1}\\([0-9]{3}\\) [0-9]{3}-[0-9]{4}',true) ) 
	   {
		
		return true; 
		}
   else 
	   { 
		
		return false;
		} 
} // Phone

/***********************************************************************************
FUNCTION 32:	Function to Check for a 
***********************************************************************************/


/*
function Blank( field ) 
{
   var r;

   if ( typeof(field.type) == "undefined" ) 
	   { // radio button group
	      for (r=0; r<field.length; r++) 
			  {
				if (field[r].checked)
					{
					 return false;
					} // if
				} // for
				return true;
		}
	else 
		{
		return Match(field,"[ ]*",true);
		} // if else

} // Blank

*/

/***********************************************************************************
FUNCTION 33:	Function to Check for a 
***********************************************************************************/


/**
* FUNCTION: checklength( form )
*  PURPOSE: Given the minimum length that an input
*           field should be, this returns true, otherwise 
*           returns false, and displays an error prompt.
*/
/*
function checkLength( field, min_length ) {
   if(Match( field, '[0-9a-zA-Z_]{'+min_length+'}',false )) {
      return true;
   } else {
      alert("The input field isn't long enough.  Must be at least "+
            min_length + " characters in length.");
      return false;
   }
} // checkLength
*/

/***********************************************************************************
FUNCTION 34:	Function to Check for a 
***********************************************************************************/

/*
function isAlphaOnly(ss,min,max,bolSpace){
var inputStr = ss.toString()
var OkChar
var match
if((inputStr.length >= min)&&(inputStr.length <= max)){
if(bolSpace){
OkChar =  "ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz"
}
else{
OkChar =  "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
}
for(var i = 0; i < inputStr.length; i++){
match = false
for (var j = 0; j < OkChar.length; j++){
if((inputStr.charAt(i) == OkChar.charAt(j))){
match=true;
j = OkChar.length
}
}
if (!match){
return false;
}
}
return true;
}
else{
return false;
}

}
*/
/***********************************************************************************
FUNCTION 35:	Function to Check for a 
***********************************************************************************/

/* function isAlphaNumericOnly(ss,min,max,bolSpace){
var inputStr = ss.toString()
var OkChar
var match
if((inputStr.length >= min)&&(inputStr.length <= max)){
if(bolSpace){
OkChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz 1234567890"
}
else{
OkChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890"
}
for (var i = 0; i < inputStr.length; i++){
match = false;
for (var j = 0; j < OkChar.length; j++){
if(inputStr.charAt(i) == OkChar.charAt(j)){
match=true;
j=OkChar.length;
}
}
if(!match){
return false;
}
} 
return true;
}
else{
return false;
}
}
*//***********************************************************************************
FUNCTION 36:	Function to Check for a 
***********************************************************************************/

/*
function isAlphaNumeric(ss, min, max){
var inputStr = ss.toString()
var BadChar = "~!@#$%^&*()_+|`-=\{}:<>?[ ];',./"
var inputStr2 = BadChar.toString()
if (inputStr.length==0){
return false;
}
if (inputStr.length < min || inputStr.length > max){
return false;
}
for (var i = 0; i < inputStr.length; i++){
for (var j = 0; j < inputStr2.length; j++){
if(inputStr.charAt(i) == inputStr2.charAt(j)){
return false;
}
}
} 
return true;
}
*/
/***********************************************************************************
FUNCTION 37:	Function to Check for a 
***********************************************************************************/
/*		

function isLetterOnly(string2, min, max){
var inputStr = string2.toString()
if (inputStr.length == 0){
return false
}
if ((inputStr.length < min) || (inputStr.length > max)){
return false
}
if (isContainBadCharacter(string2,"~!@#$%^&*()_+|<>?{}':`\/.,;=")){
return false;
}
for (var i = 0; i < inputStr.length; i++){
if(!isNaN(inputStr.charAt(i))){
return false;
}
}
return true;
}
*/
/***********************************************************************************
FUNCTION 38:	Function to Check for a 
***********************************************************************************/


/*
function isDigitOnly(ss,min,max){
var inputStr = ss.toString()
var SearchItem = " "
if (inputStr.length == 0){
return false
}
if ((inputStr.length < min) || (inputStr.length > max)){
return false
}
if (inputStr.search(SearchItem) >= 0){
return false;
}
for (var i = 0; i < inputStr.length; i++){
if(isNaN(inputStr.charAt(i))){
return false;
}
}
return true;
}
*/

/***********************************************************************************
FUNCTION 39:	Function to Check for a 
***********************************************************************************/


function checkEmail (strng) {
var error="";
if (strng == "") {
   error = "You didn't enter an email address.\n";
}

    var emailFilter=/^.+@.+\..{2,3}$/;
    if (!(emailFilter.test(strng))) {
       error = "Please enter a valid email address.\n";
    }
    else {
//test email for illegal characters
       var illegalChars= /[\(\)\<\>\,\;\:\\\"\[\]]/
         if (strng.match(illegalChars)) {
          error = "The email address contains illegal characters.\n";
       }
    }
return error;
}

/***********************************************************************************
FUNCTION 40:	Function to Check for a 
***********************************************************************************/

/*

// phone number - strip out delimiters and check for 10 digits
function checkPhone (strng) {
var error = "";
if (strng == "") {
   error = "You didn't enter a phone number.\n";
}

var stripped = strng.replace(/[\(\)\.\-\ ]/g, ''); //strip out acceptable non-numeric characters
    if (isNaN(parseInt(stripped))) {
       error = "The phone number contains illegal characters.";

    }
    if (!(stripped.length == 10)) {
    error = "The phone number is the wrong length. Make sure you included an area code.\n";
    }
return error;
}

*/
/***********************************************************************************
FUNCTION 41:	Function to Check for a 
***********************************************************************************/


// password - between 6-8 chars, uppercase, lowercase, and numeral
/*
function checkPassword (strng) {
var error = "";
if (strng == "") {
   error = "You didn't enter a password.\n";
}

    var illegalChars = /[\W_]/; // allow only letters and numbers

    if ((strng.length < 6) || (strng.length > 8)) {
       error = "The password is the wrong length.\n";
    }
    else if (illegalChars.test(strng)) {
      error = "The password contains illegal characters.\n";
    }
    else if (!((strng.search(/(a-z)+/)) && (strng.search(/(A-Z)+/)) && (strng.search(/(0-9)+/)))) {
       error = "The password must contain at least one uppercase letter, one lowercase letter, and one numeral.\n";
    }
return error;
}
*/

/***********************************************************************************
FUNCTION 42:	Function to Check for a 
***********************************************************************************/


// username - 4-10 chars, uc, lc, and underscore only.
/*
function checkUsername (strng) {
var error = "";
if (strng == "") {
   error = "You didn't enter a username.\n";
}


    var illegalChars = /\W/; // allow letters, numbers, and underscores
    if ((strng.length < 4) || (strng.length > 10)) {
       error = "The username is the wrong length.\n";
    }
    else if (illegalChars.test(strng)) {
    error = "The username contains illegal characters.\n";
    }
return error;
}
*/

/***********************************************************************************
FUNCTION 43:	Function to Check for a 
***********************************************************************************/

// non-empty textbox
/*
function isEmpty(strng, fldName) {
var error = "";
  if (strng.length == 0) {
     error = "The " + fldName + " field has not been filled in.\n"
  }
return error;
}
*/

/***********************************************************************************
FUNCTION 44:	Function to Check for a 
***********************************************************************************/

// valid selector from dropdown list

function checkDropdown(choice, fldName) {
var error = "";
//alert(choice);
    if ((choice == 0)||(choice="")){
  // error = "You didn't choose an option from the " + fldName + " drop-down list.\n";
  alert("You Didn't Choose An Option From The " + fldName + " Drop-down List.");
    return false;
    }
//return error;
	return true;
}

/***********************************************************************************
FUNCTION 45:	Function to Check for a 
***********************************************************************************/


function isBlank(fieldName)
{	
	if (Trim(fieldName)=='')
	{		
		return true;
	}
	else
	{			
	 	return false; 
	}
}

/********************************************************************/
//Function to check length of phone no=10
/********************************************************************/

function PhoneLength(fieldvalue)
{
	if(isInteger(fieldvalue))
	{
		
		if(fieldvalue.length !=10)
		return false;
		else 
		return true;
	}
	else 
	return false;
}
/***********************************************************************************
FUNCTION 47:	Function to Check for Alphanumeric String (includes whitespace, full-stop, comma, percent,
	hash, question mark, hyphen,addition, exclamation and single quotation mark)
********************************************************************************************/

	function isAlphaNum(checkStr)
	{
		var Char;
		
		for (i = 0; (i < checkStr.length); i++)
		 { 
			Char = checkStr.charCodeAt(i); 
			if (((Char>64) && (Char<91)) || ((Char>96) && (Char<123)))
		 	{
		    return true;
		 	}
			else 
			{
		    return false; 
			}
		 }
	} 

/********************************************************************/
//Function to check length of phone no=3
/********************************************************************/

function PhoneLength3(fieldvalue)
{
	if(isInteger(fieldvalue))
	{
		
		if(fieldvalue.length !=3)
		return false;
		else 
		return true;
	}
	else 
	return false;
}	
/********************************************************************/
//Function to check length of phone no=4
/********************************************************************/

function PhoneLength4(fieldvalue)
{
	if(isInteger(fieldvalue))
	{
		
		if(fieldvalue.length !=4)
		return false;
		else 
		return true;
	}
	else 
	return false;
}		


/********************************************************************/
//Function to check Month part of Date 
/********************************************************************/


function isMonth(fieldvalue)
{
	if(isInteger(fieldvalue))
	{
		//alert("month");	
		
		if(fieldvalue>12)
		return false;
		else 
		return true;
	}
	else 
	return false;
}

/********************************************************************/
//Function to check Day part of Date 
/********************************************************************/


function isDay(fieldvalue)
{
	if(isInteger(fieldvalue))
	{
		//alert("day");	
		
		if(fieldvalue>31)
		return false;
		else 
		return true;
	}
	else 
	return false;
}

/********************************************************************/
//Function to check Year part of Date 
/********************************************************************/


function isYear(fieldvalue)
{
	if(isInteger(fieldvalue))
	{
		//alert("year");	
		
		if(( 2200 < fieldvalue)||( fieldvalue< 1841))
		return false;
		else 
		return true;
	}
	else 
	return false;
}


/********************************************************************/
//Function to check Weight less then 3000 
/********************************************************************/


function isWeight(fieldvalue)
{ 
	if(isInteger(fieldvalue))
	{
		//alert(fieldvalue);	
		
		if(fieldvalue>3000)
		return false;
		else 
		return true;
	}
	else 
	return false;
}



/********************************************************************/
//Function to check Alpha characters only
/********************************************************************/

function isAlphaCharacter(checkStr)
	{
		
		var flag;
		flag=true;
		var checkOK="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		
		/*
		for(idx=0;idx<checkStr.length;idx++){
			ch = checkStr.charAt(idx);
			for(ctr=0;ctr<checkOK.length;ctr++)
				if (ch==checkOK.charAt(ctr)){
					flag=true;
					break;
				}     
		 	if(ctr==checkOK.length){
		 		flag= false;
		 		break;
		 	}
		 }
		*/
		 flag = checkCharacter(checkStr,checkOK);
	 	 if(flag)
		    return true;
		 else 
		    return false; 
	} 
/********************************************************************/
//Function to check Alpha characters only
/********************************************************************/

function isAlphaCity(checkStr)
	{
		
		var flag;
		flag=true;
		var checkOK="ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz";
		
		/*
		for(idx=0;idx<checkStr.length;idx++){
			ch = checkStr.charAt(idx);
			for(ctr=0;ctr<checkOK.length;ctr++)
				if (ch==checkOK.charAt(ctr)){
					flag=true;
					break;
				}     
		 	if(ctr==checkOK.length){
		 		flag= false;
		 		break;
		 	}
		 }
		*/
		 flag = checkCharacter(checkStr,checkOK);
	 	 if(flag)
		    return true;
		 else 
		    return false; 
	} 


/********************************************************************/
//Function to check SSN Middle Part
//********************************************************************/


function isSSN2(fieldvalue)
{
	if(isInteger(fieldvalue))
	{
		//alert(fieldvalue);	
		
		if(fieldvalue.length !=2)
		return false;
		else 
		return true;
	}
	else 
	return false;
}

/********************************************************************/
//Function to check Max Length of Text Area ( 250 characters)
//********************************************************************/

function isMlentxtarea(obj)
	{
		//alert(obj.value);
		//alert(obj.getAttribute("maxlength"));
		var mlength=obj.getAttribute? parseInt(obj.getAttribute("maxlength")) : "" 
		//var mlength=parseInt(obj.getAttribute("maxlength"))
		//alert(mlength);
		//if ((obj.getAttribute) && (obj.value.length>mlength)) 
		if (obj.value.length>mlength)
		{
			return false;
		}
		else
		return true;
	} 
	
/********************************************************************/
//Function to Display "show help?" in ICEMR
//********************************************************************/	
	function toggleshowhide()
	{
		obj = document.getElementsByTagName("div"); 
		//alert(obj);
		if (obj.help.style.visibility=="visible")
		{
			obj.help.style.visibility="hidden";
		}
		else if(obj.help.style.visibility=="hidden")
		{
			obj.help.style.visibility="visible";
		}
	}
	
/********************************************************************/
//Function to check for ^(invalid character)
//********************************************************************/	
function isInvalidChar(obj)
	{
		//alert("yes");
		if (obj.indexOf("^")!=-1)
		{
			return false;
		}
		else
		{
			return true;
		}	
	}	
/***********************************************************************************
FUNCTION 10:	Function to Check for a valid Date value (includes forward slash)
***********************************************************************************/

	function isDt(dtStr)
	{
		
		/*if (dtStr.indexOf("-")!=-1)
		{
			///dtStr=dtStr.replace('-','/');
		dtStr = dtStr.replace(/-/g,"/");
		dtStr = dtStr.replace(/-/g,"/");
		}*/
		
		var d = new Date();
		var gdate=d.getDate();
		var gmonth1=d.getMonth();
		var gmonth=gmonth1+1;
		var gyear=d.getYear();
		
		var daysInMonth = DaysArray(12);
		var pos1=dtStr.indexOf(dtCh);
		var pos2=dtStr.indexOf(dtCh,pos1+1);
		var strMonth=dtStr.substring(0,pos1);
		var strDay=dtStr.substring(pos1+1,pos2);
		var strYear=dtStr.substring(pos2+1);
		strYr=strYear;
		if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1)
		if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1)
		for (var i = 1; i <= 3; i++) {
			if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1)
		}
		month=parseInt(strMonth);
		
		day=parseInt(strDay);
		year=parseInt(strYr);
		if (pos1==-1 || pos2==-1){			
			//alert("The date format should be : mm/dd/yyyy");
			return false;
		}
		if (strMonth.length<1 || month<1 || month>12){
			//alert("The date format should be : mm/dd/yyyy 111");
			return false;
		}
		if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
			//alert("Please enter a valid day");
			return false;
		}
		if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
			//alert("The date format should be : mm/dd/yyyy 222");
			return false;
		}
		if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
			//alert("Please enter a valid date");
			return false;
		}
		/*if ( strYr >gyear)
		{
			
			//alert("Invalid year");
			alert("The entered date is greater then the current date. Please enter a valid date.");
			return false;
			}	
		
		if (strYr == gyear)
		{	
			if ( month > gmonth) 
			{	
			alert("The entered date is greater then the current date. Please enter a valid date.");
			return false;	
			}
			else
			if (month == gmonth)
			{
				if ( strDay > gdate)
				{
				alert("The entered date is greater then the current date. Please enter a valid date.");
				return false;	
				}
			}
		}*/			
		return true
	}

	var dtCh= "/";
	var minYear=1841;
	var maxYear=9999;
/***********************************************************************************
FUNCTION :	Function to make date into text box 
***********************************************************************************/

function addDays(myDate,days,operator) 
{
   if (operator=="add")
   {
   var curdate=new Date(myDate.getTime() + days*24*60*60*1000);
   }
   if (operator=="less")
   {
   var curdate=new Date(myDate.getTime() - days*24*60*60*1000);
   }
   var day=curdate.getDate();   
   /*if (day<10)
   { day ="0"+day}      
   if (month<10)
   {month ="0"+month}*/
   var month=curdate.getMonth();   
   month=month+1
   var year=curdate.getYear();
   var exist=day+"/"+month+"/"+year
   return exist 
   
}
function addMonthYear(myDate,value) 
{  
   if (value=="mm")
   {
   	var curdate=new Date(myDate.getTime() + 31*24*60*60*1000);
   	var day=curdate.getDate();   
   	var month=curdate.getMonth();
   	var year=curdate.getYear();
   }
   if (value=="yy")
   {
   	var curdate=new Date(myDate.getTime());
   	var day=curdate.getDate();   
   	var month=curdate.getMonth();
   	var year=curdate.getYear();
   	year=year+1;
   }
   if (value=="dd")
   {
   	var curdate=new Date(myDate.getTime());
   	var day=curdate.getDate();   
   	var month=curdate.getMonth();
   	var year=curdate.getYear();   	
   }
   /*if (day<10)
   { day ="0"+day}   
    if (month<10)
   {month ="0"+month}*/
   month=month+1
    var exist=day+"/"+month+"/"+year
   return exist 
   
}
function date(obj)
{
	var tmpdate=Trim(obj.value)	
	if (tmpdate!="")
	{
		var status=isAlphaNum(tmpdate);		
		if (status==true)
		{
			var tempocc=0;
			var operator;
			var period=obj.value.substring(0,1);
			if ((period=='Y') ||  (period=='y'))
				{	tempocc=1
					if (obj.value.length>1)	
					{
						obj.style.color ='red';
						obj.style.borderColor ='red';
						return false;	
					}
					else
					{
						var exist=addMonthYear(new Date(),"yy")	
						obj.value=exist;
						obj.style.color ='';
						obj.style.borderColor =''
					}
				}
				if ((period=='M') ||  (period=='m'))
				{	tempocc=1
					if (obj.value.length>1)	
					{
						obj.style.color ='red';
						obj.style.borderColor ='red';
						return false;	
					}
					else
					{
						var exist=addMonthYear(new Date(),"mm")
						obj.value=exist;
						obj.style.color ='';
						obj.style.borderColor =''
					}
				}
				if ((period=='T') ||  (period=='t'))
				{	tempocc=1
					if (obj.value.length>1)	
					{
						var operatoraddindex=obj.value.indexOf("+")
						if (operatoraddindex==-1)
						{
							var operatorlessindex=obj.value.indexOf("-")
							if (operatorlessindex==-1)
							{
								var checkdata=obj.value.substring(1,operatorlessindex);
								var tempresult=isNumber(postopdata,1);			
								if (tempresult==true)
								{
									var exist=addMonthYear(new Date(),"dd")				
									obj.value=exist;
									obj.style.color ='';
									obj.style.borderColor =''
								}
								else
								{
									obj.style.color ='red';
									obj.style.borderColor ='red';
									return false;	
								}
							}
							else
							{
								operator="less";
								var preopdata=obj.value.substring(1,operatorlessindex);
								var postopdata=obj.value.substring(operatorlessindex+1,obj.value.length);
								
							}
						}
						else
						{
							operator="add";
							var preopdata=obj.value.substring(1,operatoraddindex);
							var postopdata=obj.value.substring(operatoraddindex+1,obj.value.length);				
						}			
						preopdata=Trim(preopdata);
						if (preopdata.length>0)
						{obj.style.color ='red';
						obj.style.borderColor ='red'; return false;}
						postopdata=Trim(postopdata);
						var result=isNumber(postopdata,1);
						
						if (result==true)
						{
							var exist=addDays(new Date(),postopdata,operator);				
							obj.value=exist;
							obj.style.color ='';
							obj.style.borderColor =''
						}
						else
						{obj.style.color ='red';
						obj.style.borderColor ='red'; return false;}			
					}
					else
					{
						var exist=addMonthYear(new Date(),"dd")				
						obj.value=exist;	
						obj.style.color ='';
						obj.style.borderColor ='';
					}
			} 
			if (tempocc==0)	
				{obj.style.color ='red';
				obj.style.borderColor ='red'; return false;}
		}
		else
		{	
			var dd,mm,yy="";
			var dispdate=SplitNumber(obj.value);
			//alert(dispdate.length);
			if (dispdate!=false)
			{
				var dispdatecnt=dispdate.split("~~")
				var totPiece=dispdatecnt.length;		
				if (totPiece>1)
				{	
					if ((dispdatecnt[0]!="") && (dispdatecnt[0].length<3))
						{var dd=dispdatecnt[0];}
					else
					{obj.style.color ='red';
						obj.style.borderColor ='red'; return false;}
					if ((dispdatecnt[1]!="") && (dispdatecnt[1].length<3))
						{var mm=dispdatecnt[1];}
					else
					{obj.style.color ='red';
						obj.style.borderColor ='red'; return false;}
					//if ((dispdatecnt[2]!="") && (dispdatecnt[2].length<5))
					if (totPiece>2)
					{
						
					if (dispdatecnt[2]!="") 
						{var yy=dispdatecnt[2];}		
					else
					{obj.style.color ='red';
						obj.style.borderColor ='red'; return false;}
					}					
				}
				else if (totPiece==1)
				{
					var currentdate=new Date();
			   		var date=currentdate.getDate();   
			   		var mon=currentdate.getMonth();
			   		var yr=currentdate.getYear(); 
					mon=mon+1;
					var dd=dispdate.substring(0,2);
					var mm=dispdate.substring(2,4);
					var yy=dispdate.substring(4,dispdate.length);				
				}				
				if (yy!="")
				{
					if ((yy.length>4) || (yy.length<2) || (yy.length==3)|| (yy.length>4))
					{obj.style.color ='red';
						obj.style.borderColor ='red'; return false;}
					if (yy.length==2)
					{						
						if (yy<16)
						{yy="20"+yy;}
						if ((yy>15) && (yy<100))
						{yy="19"+yy;}
					}
				}
				else
				{var currentdt=new Date(); var yr=currentdt.getYear(); yy=yr;}
				if (dd=="")
				{dd=date;}
				if (mm=="")				
				{mm=mon;}
				var finaldate=mm+"/"+dd+"/"+yy;				
				var tempresult=isDt(finaldate);				
				if (tempresult==true)
				{
					/*if (dd<10)
   					{ dd ="0"+dd}
   					if (mm<10)
   					{mm ="0"+mm}*/
					obj.value=dd+"/"+mm+"/"+yy;;
					obj.style.color ='';
					obj.style.borderColor ='';
				}
				else
				{
					obj.style.color ='red';
					obj.style.borderColor ='red'; 
					return false;	
				}
			}
		else
		{
			obj.style.color ='red';
			obj.style.borderColor ='red'; 
			return false;	
		}
	}
}
	else
	{
		obj.style.color ='';
		obj.style.borderColor =''; 
		return;
	}	
}
function SplitNumber(sText) 
{
		var ValidChars;
		ValidChars = "0123456789";		
		var newno="";
		var Char;
		var finalno="";
		var count=0;
		for (i = 0; (i < sText.length); i++)
		{ 
			Char = sText.charAt(i); 
			//alert(ValidChars.indexOf(Char));			
			if (ValidChars.indexOf(Char) != -1)
			{
				newno=newno+""+ValidChars.indexOf(Char);	
			}	
			else
			{
				 count=count+1;				 
				 if (count>2)
				 {
				  return false;
				 }
				 if (count==1)
				 {
				 	finalno=newno;
				 }
				 else
				 {
					finalno=finalno+"~~"+newno; 
				 }
				 newno="";
			}					 
	 	 }
	 	 if (count==0)
	 	 {
		 	 finalno=newno;
		 }
	 	 else
	 	 {
		 	 finalno=finalno+"~~"+newno;
		 }
		 return finalno;  
} 


