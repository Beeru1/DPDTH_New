function changeVal(tableId,textBoxId,list){
	getSelectedSTB();
	removeRow(tableId);
	var tempFinalList =new Array();
	var flag=false;
	var getTextValue = document.getElementById("textBoxId").value;
	var finalList = searchInList(list,getTextValue);
	var len =finalList.length;
	for (var counter = 0; counter < checkedStbList.length; counter++){
		var tempSTB = checkedStbList[counter];
		for (var i = 0; i < len; i++){
			if (finalList[i]==tempSTB){
				checkedStbList[counter]='000'
				flag=true;
				break;
			}else{
			  flag=false;
			}
		}
			
	 //if(flag == false){
	 //	finalList[len] = checkedStbList[counter];
	//	len++;
	//	}
	}
	var len =checkedStbList.length;
	var j=0;
	for (var i = 0; i < len; i++){
		if(checkedStbList[i]=='000'){
			
		}else{
			tempFinalList[j]=checkedStbList[i];
			j++;
		}
	}

	addRowToTable(finalList,tableId);
	addRowToTableAgain(tempFinalList,tableId);
	selectedSerialNos();
}
function removeRow(tableId) {
	var table = document.getElementById("tableId");
	var rowCount = table.rows.length;
	for( var i = rowCount; i > 1; i--){
		var row = table.deleteRow(i-1);
	}
}
function searchInList(list,getTextValue) {     
	var len = getTextValue.length;
	var finalList = new Array();
	var i = 0;
	var k=0;
	for (var j = 0; j < list.length; j++) {
		var  value = list[j];
		var flag = CheckIt(value,len,getTextValue)
		if(flag == true){
			finalList[i]= value; 
			i++;
		}
	} 
	return finalList ;
}
function CheckIt(value,len,getTextValue){
		var subSection = value.substring(len,0);
		var answer = (subSection == getTextValue);
		return answer 
}

function addRowToTable(finalList,tableId) {
	try {
		var k=0;
		if(finalList.length>0){
			
			for( var x=0;x<5;x++){
				var table = document.getElementById("tableId");
				var rowCount = table.rows.length;
				var row = table.insertRow(rowCount);
				var cell11 = row.insertCell(0);
				cell11.innerHTML = ""
			}
			
		for (var j=0; j<finalList.length; ) {
			var table = document.getElementById("tableId");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);
			if(j<finalList.length){
				var cell11 = row.insertCell(0);
				cell11.height = "30" 
				cell11.width = "25" ;
				cell11.align = "right" 
				cell11.innerHTML = "<input type='checkbox' name='checkbox'  value ='"+finalList[j]+"'>"  
				var cell12 = row.insertCell(1);
				cell12.innerHTML = finalList[j++];
			}
			if(j<finalList.length){
				var cell21 = row.insertCell(2);
				cell21.height = "30" 
				cell21.width = "25" ;
				cell21.align = "right" 
				cell21.innerHTML = "<input type='checkbox' name='checkbox'   value ='"+finalList[j]+"' >"  
				var cell22 = row.insertCell(3);
				cell22.innerHTML = finalList[j++];
			}
			if(j<finalList.length){
				var cell31 = row.insertCell(4);
				cell31.height = "30" 
				cell31.width = "25" ;
				cell31.align = "right" 
				cell31.innerHTML = "<input type='checkbox' name='checkbox'  value ='"+finalList[j]+"' >"  
				var cell32 = row.insertCell(5);
				cell32.innerHTML = finalList[j++];
			}
			if(j<finalList.length){
				var cell41 = row.insertCell(6);
				cell41.height = "30" 
				cell41.width = "25" ;
				cell41.align = "right" 
				cell41.innerHTML = "<input type='checkbox' name='checkbox'  value ='"+finalList[j]+"' >"  
				var cell42 = row.insertCell(7);
				cell42.innerHTML = finalList[j++];
			}
		}
		}
	}catch(e) {
		alert(e);
	}
}
function addRowToTableAgain(finalList,tableId) {
	try {
		var k=0;
		if(finalList.length>0){
			
			for( var x=0;x<20;x++){
				var table = document.getElementById("tableId");
				var rowCount = table.rows.length;
				var row = table.insertRow(rowCount);
				var cell11 = row.insertCell(0);
				cell11.innerHTML = ""
			}
			var table = document.getElementById("tableId");
				var rowCount = table.rows.length;
				var row = table.insertRow(rowCount);
				var cell11 = row.insertCell(0);
				cell11.innerHTML = "<B>Selected STB<b>"
			    
			    var table = document.getElementById("tableId");
				var rowCount = table.rows.length;
				var row = table.insertRow(rowCount);
				
				var cell11 = row.insertCell(0);
				cell11.innerHTML = "<B><HR><b>";
				var cell12 = row.insertCell(1);
				cell12.innerHTML = "<B><HR><b>";
				
				var cell21 = row.insertCell(2);
				cell21.innerHTML = "<B><HR><b>" ; 
				var cell22 = row.insertCell(3);
				cell22.innerHTML = "<B><HR><b>";

				var cell31 = row.insertCell(4);
				cell31.innerHTML = "<B><HR><b>";
				var cell32 = row.insertCell(5);
				cell32.innerHTML = "<B><HR><b>";
				
				var cell41 = row.insertCell(6);
				cell41.innerHTML = "<B><HR><b>";
				var cell42 = row.insertCell(7);
				cell42.innerHTML = "<B><HR><b>"
			
		
		for (var j=0; j<finalList.length; ) {
			var table = document.getElementById("tableId");
			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);
			if(j<finalList.length){
				var cell11 = row.insertCell(0);
				cell11.height = "30" 
				cell11.width = "25" ;
				cell11.align = "right" 
				cell11.innerHTML = "<input type='checkbox' name='checkbox'  value ='"+finalList[j]+"'>"  
				var cell12 = row.insertCell(1);
				cell12.innerHTML = finalList[j++];
			}
			if(j<finalList.length){
				var cell21 = row.insertCell(2);
				var cell21 = row.insertCell(2);
				cell21.height = "30" 
				cell21.width = "25" ;
				cell21.align = "right" 
				cell21.innerHTML = "<input type='checkbox' name='checkbox'   value ='"+finalList[j]+"' >"  
				var cell22 = row.insertCell(3);
				cell22.innerHTML = finalList[j++];
			}
			if(j<finalList.length){
				var cell31 = row.insertCell(4);
				cell31.height = "30" 
				cell31.width = "25" ;
				cell31.align = "right" 
				cell31.innerHTML = "<input type='checkbox' name='checkbox'  value ='"+finalList[j]+"' >"  
				var cell32 = row.insertCell(5);
				cell32.innerHTML = finalList[j++];
			}
			if(j<finalList.length){
				var cell41 = row.insertCell(6);
				cell41.height = "30" 
				cell41.width = "25" ;
				cell41.align = "right" 
				cell41.innerHTML = "<input type='checkbox' name='checkbox'  value ='"+finalList[j]+"' >"  
				var cell42 = row.insertCell(7);
				cell42.innerHTML = finalList[j++];
			}
		}
		}
	}catch(e) {
		alert(e);
	}
}
