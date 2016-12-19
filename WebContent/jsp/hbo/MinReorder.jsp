
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">	
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<script language="javascript" src="scripts/calendar1.js">
</script>
<script language="javascript" src="scripts/Utilities.js">
</script>
<script language="javascript" src="scripts/cal2.js">
</script>
<script language="javascript" src="scripts/cal_conf2.js">
</script>
<SCRIPT type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/validation.js"></SCRIPT>

<SCRIPT language="javascript" type="text/javascript"> 
function resetting(){
 document.getElementById("accountId").value="";
 document.getElementById("productid").value="";
 document.getElementById("minreorder").value=0;
 document.getElementById("record1").value="";
 document.getElementById("record2").value="";
}

function doSubmit(){
document.MinReorderFormBean.action="MinReorderAction.do?methodName=Insertorder";
document.MinReorderFormBean.submit();
}

function validate(){
if(document.getElementById("accountId").value==""){
alert('<bean:message bundle="error" key="error.minmreodr.accountId" />');
document.getElementById("accountid").focus();
return false;			
}
if(document.getElementById("productid").value==""){
alert('<bean:message bundle="error" key="error.minmreodr.productid" />');
document.getElementById("productid").focus();
return false;			
}
if (parseInt(document.getElementById("minreorder").value) == 0){
	alert("Assign Re-order Level Takes Positive Number Values Only");
	return false;
} 
if (IsNumeric(document.getElementById("minreorder").value) == false) 
{
alert("Assign Re-order Level Takes Positive Number Values Only");
return false;	
}
var reorder=document.getElementById("minreorder").value
var where_to=confirm("Minimum Reorder Value: "+ reorder+"  Do You Want To Continue ?" )
if(where_to==false){
return  false;
}
doSubmit();
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

function reset(){
	document.getElementById("accountid").value="";
	document.getElementById("productid").value="";
	document.getElementById("minreorder").value=0;
}
	
	function productUnit(){
		var Index = document.getElementById("productid").selectedIndex;
		var productName=document.getElementById("productid").options[Index].value;
		var url= "InsertPurchaseOrder.do?methodName=getProductUnit&cond1="+productName;
		var elementId = "unitName" ;
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
			 if(text.length!=0){
			 	document.getElementById("showUnit").innerHTML=text;
			 	//document.getElementById(elementId).value = text;
			 }
			else if (text.length==0){
			 	document.getElementById("showUnit").style.display="none";
		}
	}
	}
</script>
</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  onload="reset();" bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
 <html:form action="MinReorderAction.do?methodName=Insertorder">
<TABLE cellspacing="0" cellpadding="0" border="0" width="100%"
	height="100%" valign="top" >
	
	
	<TR>
		<TD colspan="2" valign="top" width="100%"><jsp:include
			page="../common/dpheader.jsp" flush="" /></TD>
	</TR>
	<TR valign="top">
		<TD  align="left" bgcolor="#FFFFFF" background="<%=request.getContextPath()%>/images/bg_main.gif" valign="top"
			height="100%"><jsp:include page="../common/leftHeader.jsp"
			flush="" />
			</TD>
			<td>	
			<table>
<tr>
	<TD colspan="4" class="text"><BR>
		<IMG src="<%=request.getContextPath()%>/images/assignMinimumRe-OrderLevel.gif"
		width="505" height="22" alt="">
	</TD>
</tr>
			<tr></tr>
			<tr></tr>
			</table>
		<table>
		
		<TR>
               <TD align="center" colspan="2"><FONT color="RED"><STRONG><html:errors
               bundle="view" property="message.reorder" /><html:errors
               bundle="error" property="errors.reorder" /><html:errors /></STRONG></FONT></TD>
         </TR>
              
		<TR>
			<TD align="right" width="783" height="42"><b class="text pLeft15" >
			<bean:message bundle="view" key="minmreorder.distributor" /></b>
					
					</TD>
					<td align="left" width="783" height="42"><html:select styleId="distributor"
						property="accountid" name="MinReorderFormBean" style="width:150px"
						style="height:20px">
						<html:option value="">- Select A Distributor -</html:option>
						<logic:notEmpty name="MinReorderFormBean" property="arrDSList">
							<html:optionsCollection name="MinReorderFormBean"
								property="arrDSList" />
						</logic:notEmpty>
					</html:select></td>
				</TR>
				<TR>
			
			<TD  align="right" width="783" height="39"><b class="text pLeft15" >
			<bean:message bundle="view"	key="minmreorder.productlist" /></b>
					
					</TD>
					<td align="left" width="783" height="39"><html:select styleId="productlist"
						property="productid" name="MinReorderFormBean" style="width:150px"
						style="height:20px" onchange="productUnit();">
						<html:option value="">- Select A Product -</html:option>
						<logic:notEmpty name="MinReorderFormBean" property="arrPRList">
							<html:optionsCollection name="MinReorderFormBean"
								property="arrPRList" />
						</logic:notEmpty>
					</html:select></td>
				</TR>

		  <TR>
              
              <TD align="right" class="text pLeft15" width="362" height="39"><b class="text pLeft15" ><bean:message bundle="view"
								key="minmreorder.reorderlevel" /></b></TD>
					<td class="text pLeft15" width="132" height="39"><html:text name="MinReorderFormBean"
						property="minreorder" maxlength="8"/>&nbsp;<b id="showUnit"></b>
					</td>

				</TR>
              
              <TR>
                <TD align="right" class="text pLeft15" width="362" height="42"><b class="text pLeft15" ><bean:message bundle="view"
								key="minmreorder.record1" /></b></TD>
					<td class="text pLeft15" width="132" height="42"><html:text name="MinReorderFormBean"
						property="record1" /></td>
				</TR>
			
			 <TR>
                <TD align="right" class="text pLeft15" width="362" height="41"><b class="text pLeft15" ><bean:message bundle="view"
								key="minmreorder.record2" /></b></TD>
					<td class="text pLeft15" width="132" height="41"><html:text name="MinReorderFormBean"
						property="record2" /></td>
			</TR>

		<TR>
		
			<TD align="right" ><input type="button" value="Reset" onclick="resetting();" ></TD>
			<td id="submission" colspan="1" align="left" width="783" height="29">
			<input type="button" class="subbig" value="Submit"
			onclick="return validate();" >
			</td>				
			</TR>
		
				
				
            
			</table>
	</TABLE>
	</html:form>
<jsp:include page="../common/footer.jsp" flush="" />
</BODY>
</html:html>
