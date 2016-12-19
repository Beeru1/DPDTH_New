
// Load All Active Circles	
function getAllRetailer(selectedFseValues,distId,target)
{
	var url= "dropDownUtilityAjaxAction.do?methodName=getAllRet&fval="+selectedFseValues+"&distId="+distId;
	var elementId = target;
	var txt = false;
	makeAjaxRequest(url,elementId,txt);

}
function getAllTSM(selectedCircleValues,target,selectedTransTypeValues)
{
	//alert("in dropdownajax.jsp");
	var url= "dropDownUtilityAjaxAction.do?methodName=getAllTSM&cval="+selectedCircleValues+"&businessCat="+selectedTransTypeValues;
	var elementId = target;
	var txt = false;
	makeAjaxRequest(url,elementId,txt);

}
function getAllDist(selectedCircleValues,selectedTsmValues,target,transType)
{
	var url= "dropDownUtilityAjaxAction.do?methodName=getAllDist&cval="+selectedCircleValues+"&tval="+selectedTsmValues+"&transType="+transType;
	
	var elementId = target;
	var txt = false;
	makeAjaxRequest(url,elementId,txt);

}
function getAllCircles(target)
{
	var url= "dropDownUtilityAjaxAction.do?methodName=getAllCircles";
	var elementId = target;
	var txt = false;
	makeAjaxRequest(url,elementId,txt);
}

// Load all the Active Accounts under a single account	
function getAllAccountsUnderSingleAccount(accountID, target)
{
	var url= "dropDownUtilityAjaxAction.do?methodName=getAllAccountsUnderSingleAccount&strAccountID="+accountID;
	var elementId = target;
	var txt = false;
	makeAjaxRequest(url,elementId,txt);
}

// Load all the Active Products in a Circle
function getAllProductsSingleCircle(businessCategory, circleID, target)
{
	var url= "dropDownUtilityAjaxAction.do?methodName=getAllProductsSingleCircle&strBusinessCat="+businessCategory+"&strCircleID="+circleID;
	var elementId = target;
	var txt = false;
	makeAjaxRequest(url,elementId,txt);
}

  



// Load all the Active Accounts of a level
function getAllAccounts(strAccountLevel, target)
{
	var url= "dropDownUtilityAjaxAction.do?methodName=getAllAccounts&strAccountLevel="+strAccountLevel;
	var elementId = target;
	var txt = false;
	makeAjaxRequest(url,elementId,txt);
}

// Load all the Active Accounts under a Single Circle
function getAllAccountsSingleCircle(strAccountLevel, circleID, target)
{
	var url= "dropDownUtilityAjaxAction.do?methodName=getAllAccountsSingleCircle&strAccountLevel="+strAccountLevel+"&strCircleID="+circleID;
	var elementId = target;
	var txt = false;
	makeAjaxRequest(url,elementId,txt);
}
// Load all the Active Accounts under multiple Circle
function getAllAccountsMultipleCircles(strAccountLevel, circleID, target)
{
	var url= "dropDownUtilityAjaxAction.do?methodName=getAllAccountsMultipleCircles&strAccountLevel="+strAccountLevel+"&strCircleID="+circleID;
	var elementId = target;
	var txt = false;
	makeAjaxRequest(url,elementId,txt);
}

// Load all the Active Accounts under a multiple account	
function getAllAccountsUnderMultipleAccounts(accountID, target)
{
	var url= "dropDownUtilityAjaxAction.do?methodName=getAllAccountsUnderMultipleAccounts&strAccountID="+accountID;
	var elementId = target;
	var txt = false;
	makeAjaxRequest(url,elementId,txt);
}

function getAllCollectionTypes(target)
{
	var url= "dropDownUtilityAjaxAction.do?methodName=getAllCollectionTypes";
	var elementId = target;
	var txt = false;
	makeAjaxRequest(url,elementId,txt);
}

function getAjaxAccountNames(distId, type, target){
	var url= "dropDownUtilityAjaxAction.do?methodName=getAccountNames&distId="+distId+"&type="+type;
	var elementId = target;
	var txt = false;
	makeAjaxRequest(url,elementId,txt);
}

