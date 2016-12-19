// fileUploadValidation.js

function setHidden(a){

	document.forms[0].requisition_id.value = a;
	var b = document.forms[0].requisition_id.value;

}
function setInitialHiddelValue(){

	document.forms[0].requisition_id.value = -1;
	var b = document.forms[0].requisition_id.value;
	
}
function frmsubmit()
{

	if((document.HBOFileUploadForm.fileType.value=="PROD")&&(document.forms[0].requisition_id.value=="-1"))
	{
		alert("Please Select Requisition Id");
		return false;

	}
	if(document.HBOFileUploadForm.thefile.value==""){
		alert("Please Enter The File Location");		
		return false;
	}
	else{		
		
		var flag = checkfileExtension();
		if(flag){			
			document.HBOFileUploadForm.submit();
		return true;
		
		}
		else{
			return false;
		}		
	}
}
function checkfileExtension()
{
	
	if(document.HBOFileUploadForm.thefile.value.lastIndexOf(".csv")==-1){
		alert("Please upload only .csv extention file");
		document.HBOFileUploadForm.thefile.focus();
		return false;
	}

	if(document.HBOFileUploadForm.thefile.value.indexOf(":\\")==-1){
		alert("File does not exist");
		document.HBOFileUploadForm.thefile.focus();
		return false;
	}
	
	if(!isWhitespace1(document.HBOFileUploadForm.thefile.value)){
	
		alert("File Name should not contain spaces");
		document.HBOFileUploadForm.thefile.focus();
		return false;
	}
	
	return true;
}

// Methods Used in UploadFileReport.jsp
function populate()
{
	
	document.HBOFileUploadForm.toDate1.value=document.HBOFileUploadForm.repStartDate.value;
	
}
function setAction()
{
	txtdt1 = document.HBOFileUploadForm.toDate1.value;	
	if(txtdt1=="")
	{
	alert("Start date cannot be blank");
	return false;
	}
	document.HBOFileUploadForm.action="bulkDataAction.do?methodName=bulkUpload";
	document.HBOFileUploadForm.method="post";
	document.HBOFileUploadForm.submit();
}
