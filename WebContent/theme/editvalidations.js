function copyAddress2()
{
 if(document.CreateSEFBean.copyAddress.checked==true)
	{ 
 	document.CreateSEFBean.txtBillAdd2Address1.value=document.CreateSEFBean.txtBillAdd1Address1.value;
	document.CreateSEFBean.txtBillAdd2Address2.value=document.CreateSEFBean.txtBillAdd1Address2.value;
	document.CreateSEFBean.txtBillAdd2Address3.value=document.CreateSEFBean.txtBillAdd1Address3.value;
	document.CreateSEFBean.selBillAdd2State1.value=document.CreateSEFBean.selBillAdd1State1.value;
	document.CreateSEFBean.selBillAdd2District1.value=document.CreateSEFBean.selBillAdd1District1.value;
	document.CreateSEFBean.selBillAdd2City1.value=document.CreateSEFBean.selBillAdd1City1.value;
	document.CreateSEFBean.selBillAdd2Pincode1.value=document.CreateSEFBean.selBillAdd1Pincode1.value;
	document.CreateSEFBean.txtBillAdd2STDCode.value=document.CreateSEFBean.txtBillAdd1STDCode.value;
	document.CreateSEFBean.txtBillAdd2Tel.value=document.CreateSEFBean.txtBillAdd1Tel.value;
	
	document.CreateSEFBean.txtBillAdd2Landmark.value=document.CreateSEFBean.txtBillAdd1Landmark.value;
	
	document.CreateSEFBean.txtBillAdd2Email.value=document.CreateSEFBean.txtBillAdd1Email.value;
	
	document.CreateSEFBean.txtBillAdd2Fax.value=document.CreateSEFBean.txtBillAdd1Fax.value;
	
	document.CreateSEFBean.txtBillAdd2Address1.disabled=true;
	document.CreateSEFBean.txtBillAdd2Address2.disabled=true;
	document.CreateSEFBean.txtBillAdd2Address3.disabled=true;
	document.CreateSEFBean.selBillAdd2State1.disabled=true;
	document.CreateSEFBean.selBillAdd2District1.disabled=true;
	document.CreateSEFBean.selBillAdd2City1.disabled=true;
	document.CreateSEFBean.selBillAdd2Pincode1.disabled=true;
	document.CreateSEFBean.txtBillAdd2STDCode.disabled=true;
	document.CreateSEFBean.txtBillAdd2Tel.disabled=true;	
	document.CreateSEFBean.txtBillAdd2Landmark.disabled=true;
	document.CreateSEFBean.txtBillAdd2Email.disabled=true;
	document.CreateSEFBean.txtBillAdd2Fax.disabled=true;
 	}
}

function copyAddress2_1(field)
{
	converttoTitleAddress(field);
    copyAddress2();
}

function copyAddress1()
{
	if(document.CreateSEFBean.copyAddress.checked==true)
	{
	
		document.CreateSEFBean.txtBillAdd2Address1.value=document.CreateSEFBean.txtBillAdd1Address1.value;

		document.CreateSEFBean.txtBillAdd2Address2.value=document.CreateSEFBean.txtBillAdd1Address2.value;

		document.CreateSEFBean.txtBillAdd2Address3.value=document.CreateSEFBean.txtBillAdd1Address3.value;

		document.CreateSEFBean.selBillAdd2State1.value=document.CreateSEFBean.selBillAdd1State1.value;
		
		//loadDistricts1('2');
		document.CreateSEFBean.selBillAdd2District1.value=document.CreateSEFBean.selBillAdd1District1.value;
		//loadCities1('2');
		document.CreateSEFBean.selBillAdd2City1.value=document.CreateSEFBean.selBillAdd1City1.value;
		//loadPincodes1('2');
		document.CreateSEFBean.selBillAdd2Pincode1.value=document.CreateSEFBean.selBillAdd1Pincode1.value;
		//New Change for STD
		document.CreateSEFBean.txtBillAdd2STDCode.value=document.CreateSEFBean.txtBillAdd1STDCode.value;
		document.CreateSEFBean.txtBillAdd2Tel.value=document.CreateSEFBean.txtBillAdd1Tel.value;

		document.CreateSEFBean.txtBillAdd2Landmark.value=document.CreateSEFBean.txtBillAdd1Landmark.value;

		document.CreateSEFBean.txtBillAdd2Email.value=document.CreateSEFBean.txtBillAdd1Email.value;

		document.CreateSEFBean.txtBillAdd2Fax.value=document.CreateSEFBean.txtBillAdd1Fax.value;
	
		document.CreateSEFBean.txtBillAdd2Address1.disabled=true;
		document.CreateSEFBean.txtBillAdd2Address2.disabled=true;
		document.CreateSEFBean.txtBillAdd2Address3.disabled=true;
		document.CreateSEFBean.selBillAdd2State1.disabled=true;
		document.CreateSEFBean.selBillAdd2District1.disabled=true;
		document.CreateSEFBean.selBillAdd2City1.disabled=true;
		document.CreateSEFBean.selBillAdd2Pincode1.disabled=true;
		document.CreateSEFBean.txtBillAdd2STDCode.disabled=true;
		document.CreateSEFBean.txtBillAdd2Tel.disabled=true;	
		document.CreateSEFBean.txtBillAdd2Landmark.disabled=true;
		document.CreateSEFBean.txtBillAdd2Email.disabled=true;
		document.CreateSEFBean.txtBillAdd2Fax.disabled=true;
	}
	else
	{
		document.CreateSEFBean.txtBillAdd2Address1.disabled=false;
		document.CreateSEFBean.txtBillAdd2Address2.disabled=false;
		document.CreateSEFBean.txtBillAdd2Address3.disabled=false;
		document.CreateSEFBean.selBillAdd2State1.disabled=false;
		document.CreateSEFBean.selBillAdd2District1.disabled=false;
		document.CreateSEFBean.selBillAdd2City1.disabled=false;
		document.CreateSEFBean.selBillAdd2Pincode1.disabled=false;
		document.CreateSEFBean.txtBillAdd2STDCode.disabled=false;
		document.CreateSEFBean.txtBillAdd2Tel.disabled=false;	
		document.CreateSEFBean.txtBillAdd2Landmark.disabled=false;
		document.CreateSEFBean.txtBillAdd2Email.disabled=false;
		document.CreateSEFBean.txtBillAdd2Fax.disabled=false;
	
		document.CreateSEFBean.txtBillAdd2Address1.value="";
		document.CreateSEFBean.txtBillAdd2Address2.value="";
		document.CreateSEFBean.txtBillAdd2Address3.value="";
		document.CreateSEFBean.selBillAdd2State1.value="";
		document.CreateSEFBean.selBillAdd2District1.value="";
		document.CreateSEFBean.selBillAdd2City1.value="";
		document.CreateSEFBean.selBillAdd2Pincode1.value="";
		document.CreateSEFBean.txtBillAdd2STDCode.value="";//New Change for STD
		document.CreateSEFBean.txtBillAdd2Tel.value="";
		document.CreateSEFBean.txtBillAdd2Landmark.value="";
		document.CreateSEFBean.txtBillAdd2Email.value="";
		document.CreateSEFBean.txtBillAdd2Fax.value="";
	}
}

function converttoTitleAddress(field)
{
	var parm=field.value;
	
	if(isValidAddress(parm,lwrAddress))
	{		
	}
	else
	{
		alert("Enter Valid Address");
		field.focus();
		return false;
	}
	if(parm.length>0)
	{
	var down=parm.charAt(0);
	var up=down.toUpperCase();
	var x=up;
	for(i=1;i<parm.length;i++)
	{		
		if(parm.charAt(i)==" ")
		{		
		 if(parm.charAt(i+1)!=" ")
		 {		  
		  x=x+parm.charAt(i)+parm.charAt(i+1).toUpperCase();		 
		  i++;
		 }
		}
		else
		{
		x=x+parm.charAt(i).toLowerCase();
		}
	}
	field.value= x;
	}
	return;
}

var lwrAddress = '!@#$%^&*?<>;:~`';	
function isValidAddress(parm,val) {
 
  if (parm =="") return true;
  for (i=0; i<parm.length; i++) {
    if (val.indexOf(parm.charAt(i),0) >=0) 
    
    return false;
   }
  return true;
}

function converttoTitle(field)
{
	var parm=field.value;
	if(parm.length>0)
	{
	var down=parm.charAt(0);
	var up=down.toUpperCase();
	var x=up;
	for(i=1;i<parm.length;i++)
	{
	x=x+parm.charAt(i).toLowerCase();
	}
	field.value= x;
	}
	return;
	 
}

function dateCompDOB(txtdt1,txtdt2,msg)
{
	d2=txtdt2.getDate();
	m2=txtdt2.getMonth()+1;
	
	y2=txtdt2.getYear();
	len=txtdt1.length
	if(txtdt1.charAt(0)!=0)
	d1=txtdt1.charAt(0)+txtdt1.charAt(1);
	else
	d1=txtdt1.charAt(1)
	m1=txtdt1.charAt(3)+txtdt1.charAt(4)+txtdt1.charAt(5)
	y1=txtdt1.charAt(7)+txtdt1.charAt(8)+txtdt1.charAt(9)+txtdt1.charAt(10)
	if(m1=="JAN")
	m1=1
	if(m1=="FEB")
	m1=2
	if(m1=="MAR")
	m1=3
	if(m1=="APR")
	m1=4
	if(m1=="MAY")
	m1=5
	if(m1=="JUN")
	m1=6
	if(m1=="JUL")
	m1=7
	if(m1=="AUG")
	m1=8
	if(m1=="SEP")
	m1=9
	if(m1=="OCT")
	m1=10
	if(m1=="NOV")
	m1=11
	if(m1=="DEC")
	m1=12
	
	if(y1>y2)
	{
	alert(msg);
	return false;
	}
	if((y2-y1)<18)
	{
	alert(msg);
	return false;
	}	
	if((y2-y1)==18)
	if(m1>m2)
	{
	alert(msg);
	return false;
	}
	if((y2-y1)==18)
	if(m1==m2)
	if(d1>d2)
	{
	alert(msg);
	return false;
	}
	return true;
	
}


function dateComp(txtdt1,txtdt2,msg)
{
	d2=txtdt2.getDate();
	m2=txtdt2.getMonth()+1;
	
	y2=txtdt2.getYear();
	len=txtdt1.length
	if(txtdt1.charAt(0)!=0)
	d1=txtdt1.charAt(0)+txtdt1.charAt(1);
	else
	d1=txtdt1.charAt(1)
	m1=txtdt1.charAt(3)+txtdt1.charAt(4)+txtdt1.charAt(5)
	y1=txtdt1.charAt(7)+txtdt1.charAt(8)+txtdt1.charAt(9)+txtdt1.charAt(10)
	if(m1=="JAN")
	m1=1
	if(m1=="FEB")
	m1=2
	if(m1=="MAR")
	m1=3
	if(m1=="APR")
	m1=4
	if(m1=="MAY")
	m1=5
	if(m1=="JUN")
	m1=6
	if(m1=="JUL")
	m1=7
	if(m1=="AUG")
	m1=8
	if(m1=="SEP")
	m1=9
	if(m1=="OCT")
	m1=10
	if(m1=="NOV")
	m1=11
	if(m1=="DEC")
	m1=12
	
	if(y1>y2)
	{
	alert(msg);
	return false;
	}
	if(y1==y2)
	if(m1>m2)
	{
	alert(msg);
	return false;
	}
	if(y1==y2)
	if(m1==m2)
	if(d1>d2)
	{
	alert(msg);
	return false;
	}
	return true;
	
}

	var lwrSpace = 'abcdefghijklmnopqrstuvwxyz ';
	var uprSpace = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ ';
function isAlphaSpace(value1,msg) 
{
	//alert("is Alpha");	
	if(isValid(value1.value,lwrSpace+uprSpace))
	{
		return true;
	}
	else
	{
		alert(msg);
		value1.focus();
		return false;
	}
}
var numb = '0123456789';
	var lwr = 'abcdefghijklmnopqrstuvwxyz';
	var upr = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';

// End Change as per bug number 160 10th june 2005
function isAlpha(value1,msg) 
{
	//alert("is Alpha");	
	if(isValid(value1.value,lwr+upr))
	{
		return true;
	}
	else
	{
		alert(msg);
		value1.focus();
		event.returnValue = false;
	}
}

function isValid(parm,val) {
  if (parm =="") return true;
  for (i=0; i<parm.length; i++) {
    if (val.indexOf(parm.charAt(i),0) == -1) 
    
    return false;
   }
  return true;
}

function isAlphaNum(value1,msg) 
{
	//alert("is Alpha");	
	if(isValid(value1.value,lwr+upr+numb))
	{
		return true;
	}
	else
	{
		alert(msg);
		value1.focus();
		event.returnValue = false;
	}
}

function chkMandatoryForDropdown(field,msg)
{
	if(field.value=="0")
	{
		alert(msg);
		field.focus();
		event.returnValue = false;
	}
	else
	{
		return true;
	}
}


function submitPersonalBlock()
{
if(isEmpty(document.CreateSEFBean.txtEnterSEF,"Please enter the APEF No.")){
event.returnValue = false;
}
else if(!isPositiveNumber(document.CreateSEFBean.txtEnterSEF,"APEF No. should only contain digits.",1)){
event.returnValue = false;
}
else if(isEmpty(document.CreateSEFBean.txtSefReceivedDate,"Please select the APEF Received Date.",1)){
event.returnValue = false;
}
else if(!dateComp(document.CreateSEFBean.txtSefReceivedDate.value,new Date,"APEF Receive date should not be greater than current date")){
event.returnValue = false;
}
else if(isEmpty(document.CreateSEFBean.txtCustomerFilledDate,"Please select the Customer Filled Date.",1)){
event.returnValue = false;
}
else if(!dateComp(document.CreateSEFBean.txtCustomerFilledDate.value,new Date,"Customer Filled date should not be greater than current date")){
event.returnValue = false;
}
else if(isEmpty(document.CreateSEFBean.txtSubFirstName,"Please enter First Name")){
event.returnValue = false;
}
else if(!isAlpha(document.CreateSEFBean.txtSubFirstName,"Please enter Alphabet characters")){
event.returnValue = false;
}
else if(!isAlpha(document.CreateSEFBean.txtSubMiddleName,"Please enter Alphabet characters")){
event.returnValue = false;
}
else if(isEmpty(document.CreateSEFBean.txtSubLastName,"Please enter Last Name")){
event.returnValue = false;
}
else if(!isAlpha(document.CreateSEFBean.txtSubLastName,"Please enter Alphabet characters")){
event.returnValue = false;
}

else if(isEmpty(document.CreateSEFBean.txtFHFirstName,"Please enter First Name")){
event.returnValue = false;
}

else if(!isAlpha(document.CreateSEFBean.txtFHFirstName,"Please enter Alphabet characters")){
event.returnValue = false;
}
else if(!isAlpha(document.CreateSEFBean.txtFHMiddleName,"Please enter Alphabet characters")){
event.returnValue = false;
}

else if(isEmpty(document.CreateSEFBean.txtFHLastName,"Please enter Last Name")){
event.returnValue = false;
}
else if(!isAlpha(document.CreateSEFBean.txtFHLastName,"Please enter Alphabet characters")){
event.returnValue = false;
}
else if(isEmpty(document.CreateSEFBean.txtDOB,"Please enter Date of Birth")){
event.returnValue = false;
}
else if(!dateComp(document.CreateSEFBean.txtDOB.value,new Date,"Date of Birth date should not be greater than current date")){
event.returnValue = false;
}
else if(!dateCompDOB(document.CreateSEFBean.txtDOB.value,new Date,"Age Should be greater than 18 years")){
event.returnValue = false;
}
else if(isEmpty(document.CreateSEFBean.txtNationality,"Please enter Nationality")){
event.returnValue = false;
}
else if(!isAlpha(document.CreateSEFBean.txtNationality,"Please enter Alphabet characters")){
event.returnValue = false;
}
else if(document.CreateSEFBean.txtPassportNumber.value.length>0)
{
	if(!isAlphaNum(document.CreateSEFBean.txtPassportNumber,"Please Enter AlphaNumeric Characters")){
		return false;
	}
	else if(isEmpty(document.CreateSEFBean.txtPassportExpiryDate,"Please enter Date for Passport Expiry")){
		return false;
	}
	else if(!dateCompPassport(document.CreateSEFBean.txtPassportExpiryDate.value,new Date,"Expiry date should not be less than current date")){
		return false;
	}
}
else {
return true;
}
}


function submitAddressBlock()
{
if(isEmpty(document.CreateSEFBean.txtBillAdd1Address1,"Please enter Address")){
event.returnValue = false;
}
else if(isEmpty(document.CreateSEFBean.txtBillAdd1Address2,"Please enter Address2")){
event.returnValue = false;
}
else if(!chkMandatoryForDropdown(document.CreateSEFBean.selBillAdd1State1,"Please select the State")){
event.returnValue = false;
}
else if(!chkMandatoryForDropdown(document.CreateSEFBean.selBillAdd1District1,"Please select the District")){
event.returnValue = false;
}
else if(!chkMandatoryForDropdown(document.CreateSEFBean.selBillAdd1City1,"Please select the City")){
event.returnValue = false;
}
else if(!chkMandatoryForDropdown(document.CreateSEFBean.selBillAdd1Pincode1,"Please select the Pincode")){
event.returnValue = false;
}
else if(isEmpty(document.CreateSEFBean.txtBillAdd1Tel,"Please enter Telephone number")){
event.returnValue = false;
}
else if(!isPositiveNumber(document.CreateSEFBean.txtBillAdd1Tel,"Numeric Only!",1)){
event.returnValue = false;
}  
else if(document.CreateSEFBean.txtBillAdd1Tel.value.length<6)
{
alert("Enter Valid Telephone Number");
document.CreateSEFBean.txtBillAdd1Tel.focus();
event.returnValue = false;
}
else if(isEmpty(document.CreateSEFBean.txtBillAdd1Landmark,"Please enter Landmark")){
event.returnValue = false;
}
else if(!isEmail(document.CreateSEFBean.txtBillAdd1Email,"Email")){
event.returnValue = false;
}
else if(!isPositiveNumber(document.CreateSEFBean.txtBillAdd1Fax,"Numeric Only!",1)){
event.returnValue = false;
}
else if(isEmpty(document.CreateSEFBean.txtBillAdd2Address1,"Please enter Address")){
event.returnValue = false;
}
else if(isEmpty(document.CreateSEFBean.selBillAdd2State1,"Please enter State")){ 
event.returnValue = false;
}
else if(isEmpty(document.CreateSEFBean.selBillAdd2District1,"Please enter District")){ 
event.returnValue = false;
}
else if(isEmpty(document.CreateSEFBean.selBillAdd2City1,"Please enter City")){    
event.returnValue = false;
}
else if(isEmpty(document.CreateSEFBean.selBillAdd2Pincode1,"Please enter Pincode")){    
event.returnValue = false;
}
else if(!isPositiveNumber(document.CreateSEFBean.selBillAdd2Pincode1,"Numeric Only!",1)){
event.returnValue = false;
}
else if(isEmpty(document.CreateSEFBean.txtBillAdd2Address2,"Please enter Address")){
event.returnValue = false;
}
else if(!chkMandatoryForDropdown(document.CreateSEFBean.selBillAdd2State1,"Please select the State")){
event.returnValue = false;
}
else if(!chkMandatoryForDropdown(document.CreateSEFBean.selBillAdd2District1,"Please select the District")){
event.returnValue = false;
}
else if(!chkMandatoryForDropdown(document.CreateSEFBean.selBillAdd2City1,"Please select the City")){
event.returnValue = false;
}
else if(!chkMandatoryForDropdown(document.CreateSEFBean.selBillAdd2Pincode1,"Please select the Pincode")){
event.returnValue = false;
}
else if(isEmpty(document.CreateSEFBean.txtBillAdd2Tel,"Please enter Telephone number")){
event.returnValue = false;
}
else if(!isPositiveNumber(document.CreateSEFBean.txtBillAdd2Tel,"Numeric Only!",1)){
event.returnValue = false;
}
else if(document.CreateSEFBean.txtBillAdd2Tel.value.length<6)
{
alert("Enter Valid Telephone Number");
document.CreateSEFBean.txtBillAdd2Tel.focus();
event.returnValue = false;
}
else if(isEmpty(document.CreateSEFBean.txtBillAdd2Landmark,"Please enter Landmark")){
event.returnValue = false;
}
else if(!isEmail(document.CreateSEFBean.txtBillAdd2Email,"Email")){
event.returnValue = false;
}
else if(!isPositiveNumber(document.CreateSEFBean.txtBillAdd2Fax,"Numeric Only!",1)){
event.returnValue = false;
}
else{
	return true;
	}
}


function submitSignoffBlock()
{
if(!chkMandatoryForDropdown(document.CreateSEFBean.slctIdentityProof1,"Please select the Identity Proof")){
event.returnValue = false;
}
else if(!chkMandatoryForDropdown(document.CreateSEFBean.slctResidenceProof1,"Please select the Residential proof")){
event.returnValue = false;
}
else if(!chkMandatoryForDropdown(document.CreateSEFBean.slctOfficeCategory1,"Please select the Category")){
event.returnValue = false;
} 
else if(!isPositiveNumber(document.CreateSEFBean.txtExstngMbNumber,"Numeric Only!",1)){
event.returnValue = false;
}
else {
return true;      
}
}