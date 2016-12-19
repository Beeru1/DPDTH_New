
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html:html>
<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="./theme/Master.css" rel="stylesheet"
	type="text/css">
<TITLE>MARK AS DAMAGED</TITLE>

<script>
function getChange(cond)
{	
	var Index = document.getElementById("category").selectedIndex;
	var Id = document.getElementById("category").options[Index].value;
	var url="initMarkDamagedDirect.do?methodName=getChange&Id="+Id+"&cond="+cond;
	
	if(window.XmlHttpRequest) {
		req = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		req=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if(req==null) {
		alert("Browser Doesn't Support AJAX");
		return;
	}
	req.onreadystatechange = getOnChange;
	
	req.open("POST",url,false);
	req.send();
}
	function getOnChange()
	{
	if (req.readyState==4 || req.readyState=="complete") 
  	{
		
		var xmldoc = req.responseXML.documentElement;
		if(xmldoc == null){		
			return;
		}
		optionValues = xmldoc.getElementsByTagName("option");
		var selectObj = document.getElementById("product");
		selectObj.options.length = 0;
		selectObj.options[selectObj.options.length] = new Option(" --Select a product-- ","");
		for(var i=0; i<optionValues.length; i++){
		
			selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
		}
	}
}

function showHideDiv(){
	var Index = document.getElementById("category").selectedIndex;
	var element=document.getElementById("category").options[Index].value;
	if(element.substring(element.indexOf('@')+1)=='N'){
		//document.getElementById("product").style.display = 'none';
		document.getElementById("sno").style.display = 'none';
		document.getElementById("remarks").style.display = 'none';
		document.getElementById("cost").style.display = 'none';
		document.getElementById("showImeiNo").style.display = 'inline';
		document.getElementById("catType").value='H';
	}
	else{
		//document.getElementById("product").style.display = 'inline';
		document.getElementById("sno").style.display = 'inline';
		document.getElementById("remarks").style.display = 'inline';
		document.getElementById("cost").style.display = 'inline';
		document.getElementById("showImeiNo").style.display = 'none';
		document.getElementById("catType").value='I';
		document.getElementById("snodiv").style.display = 'inline';
	}
	getChange('requisitionCategory');
}
	
	function openWindow(imeiNo,simnoList,bundle,role) {
	//document.getElementById("simNo").value=simnoList;
	if (bundle=="Y" && (role=="ROLE_ND"||role=="ROLE_LD"))
	{	
		document.getElementById("catType").value='H';
		window.showModalDialog("viewMarkDamaged.do?methodName=setNewImeiD&imeiNo="+imeiNo+"&simNo="+simnoList+"&flag=DirectDmg",null,"dialogWidth:300px;dialogHeight:300px");
		window.location.reload(true);
	}
	else if (bundle=="N" || (bundle=="Y" && (role=="ROLE_AD"||role=="ROLE_SS")))
	{	
		document.getElementById("catType").value='H';
		var url="markDamagedDirect.do?methodName=updtDmgDirect&imeiNo="+imeiNo+"&flag=DirectDmg";
		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();
	}
}

function validate(){
	if(document.getElementById("category").value=="0" || document.getElementById("category").value=="")
		{
			alert("Please Select A Category");
			document.getElementById("category").focus();
			return false;
		}
	if(document.getElementById("product").value=="0" || document.getElementById("product").value=="")
		{
			alert("Please Select A Product");
			document.getElementById("product").focus();
			return false;
		}		
	if(document.getElementById("catType").value=='I'){
		if(document.getElementById("serialNo").value=="0" || document.getElementById("serialNo").value=="")
		{
			alert("Please Enter A Serial No");
			document.getElementById("serialNo").focus();
			return false;
		}
	}
	else if(document.getElementById("catType").value=='H'){
		if(document.getElementById("imeiNo").value=="0" || document.getElementById("imeiNo").value=="")
			{
				alert("Please Enter An IMEI No");
				document.getElementById("imeiNo").focus();
				return false;
			}
		if(IsNumeric(document.getElementById("imeiNo").value)== false)
			{
				alert("IMEI No Field Takes Postive Integer Values Only");
				document.getElementById("imeiNo").focus();
				return false;
			}	
	}
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
</script>
</HEAD>

<BODY>
<%String role = (String)request.getAttribute("role");%>
<html:form action="/markDamagedDirect.do" onsubmit="return validate();">
	<BR>
<html:hidden property="simNo" value=""/>
<html:hidden property="methodName" value="markDirectDmg"/>
	<table>
		<TR>
			<TD colspan="2" align="left"><IMG src="<%=request.getContextPath()%>/images/markAsDamaged.gif"
								width="505" height="22" alt="">
			</TD>
		</TR>
	</table>
	
	<TABLE border="0" align="center">
		<TBODY>
		<%--	<TR>
				<TD colspan="2" align="left"><IMG src="<%=request.getContextPath()%>/images/markAsDamaged.gif"
								width="505" height="22" alt="">
				</TD>
			</TR> --%>
			<TR>
				<TD><bean:message bundle="hboView" key="markDamaged.category"/><font color="red">*</font></TD>
				<TD>
					<html:select  property="category" onchange="showHideDiv();">
					<html:option value="">--Select a category--</html:option>
					<logic:notEmpty name="HBOMarkDamagedForm" property="categoryList">
						<bean:define id="category" name="HBOMarkDamagedForm" property="categoryList"/>
						<html:options collection="category" labelProperty="bname" property="newBcode"/>
					</logic:notEmpty>
					</html:select>
				</TD> 
			</TR>
			<tr>
				<td>Product<font color="red">*</font></td>
				<td>
					<html:select  property="product">
					<html:option value="">--Select a product--</html:option>
					</html:select>
				</td>
			</tr>
			<TR id="sno" style="display:inline">
				<TD><html:hidden property="catType" value="I"/><bean:message bundle="hboView" key="markDamaged.sno"/><div id="snodiv" style="display:none"><font color="red">*</font></div></TD>
				<TD><html:text property="serialNo" maxlength="11"></html:text></TD> 
			</TR>
			<TR id="showImeiNo" style="display:none">
				<TD><bean:message bundle="hboView" key="markDamaged.imeiNo"/><font color="red">*</font></TD>
				<TD><html:text property="imeiNo" maxlength="15"></html:text></TD> 
			</TR>
			<TR id="remarks" style="display:inline">
				<TD><bean:message bundle="hboView" key="markDamaged.remarks"/></TD>
				<TD><html:text property="remarks"></html:text></TD> 
			</TR>
			<TR id="cost" style="display:inline">
				<TD><bean:message bundle="hboView" key="markDamaged.cost"/></TD>
				<TD ><html:text property="cost"></html:text></TD> 
			</TR>
			<TR>
				<!--  <TD width="125" align="right" ></TD>-->
				<TD align="center" colspan="2"><html:reset styleClass="medium" value="Reset"></html:reset><html:submit
					styleClass="medium" value="Mark As Damaged"></html:submit></TD>
			</TR>
			<tr>
    			<td  align="center" colspan="6">
    				<span>
 						<logic:messagesPresent message="true">
							<html:messages id="msg" bundle="hboView" property="NO_RECORD" message="true">
					      		<b><font color="red"><bean:write name="msg" filter="false" /></font></b>
		   					</html:messages>
		   					<html:messages id="msg1" bundle="hboView" property="UpdateSuccess" message="true">
					      		<b><font color="red"><bean:write name="msg1" filter="false" /></font></b>
		   					</html:messages>
		   					<html:messages id="msg2" bundle="hboView" property="failure" message="true">
					      		<b><font color="red"><bean:write name="msg2" filter="false" /></font></b>
		   					</html:messages>
		   					<html:messages id="msg4" bundle="hboView" property="ProductUnavailable" message="true">
					      		<b><font color="red"><bean:write name="msg4" filter="false" /></font></b>
		   					</html:messages>
		   					<html:messages id="msg3" bundle="hboView" property="AlreadyDamaged" message="true">
					      		<b><font color="red"><bean:write name="msg3" filter="false" /></font></b>
		   					</html:messages>		   					
						</logic:messagesPresent>
					</span>
			<html:errors />
    			</td>
    		</tr>
			
		</TBODY>
	</TABLE>
	
	<BR>
	<BR>
	<BR>
	<BR>
	<BR>
		<%
			int j=1; 
			String rowDark ="#FCE8E6";
 		    String rowLight = "#FFFFFF";		
		%>
	<logic:notEmpty name="HBOMarkDamagedForm" property="arrImeino">
	<TABLE border="0" width="100%" align="center" class ="border" >
		<TR bgcolor="#CD0504">
			<TD class="colhead" align="center">SNo</TD>
		
			<TD class="colhead" align="center">IMEI</TD>
		
			<TD class="colhead" align="center">SIM NO</TD>
			
			<TD class="colhead" align="center">Bundling Status</TD>
				
			<TD class="colhead" align="center">Mark as Damaged</TD>
		</TR>
 		<logic:iterate id="i" name="HBOMarkDamagedForm" property="arrImeino" indexId="p">
			
		<TR BGCOLOR='<%=((p.intValue()+1) % 2==0 ? rowDark : rowLight) %>'>
			<TD align="center"><%=j%></TD>
			<TD align="center"><div id="No"><bean:write name="i" property="imeiNo"/></div></TD>
			<TD align="center"><bean:write name="i" property="simnoList"/></TD>
			<TD align="center"><bean:write name="i" property="bundle"/></TD>
			<TD align="center">
				<logic:equal value="N" name="i" property="status">
					<a href="javascript:openWindow('<bean:write name="i" property="imeiNo"/>','<bean:write name="i" property="simnoList"/>','<bean:write name="i" property="bundle"/>','<%=role%>')">Mark As damaged</A>
				</logic:equal>	
				<logic:equal value="Y" name="i" property="status">
				Damaged
				</logic:equal>	
			</TD>
		</TR>
		<%
		j=j+1;
		%>
		</logic:iterate>
	</TABLE>
	<br>
	<br>
	</logic:notEmpty>
	<logic:notEmpty name="HBOMarkDamagedForm" property="damagedProdList">
		<table align="center" width="80%">
			<tr>
				<td align="center" width="271"><b>Serial No/IMEI No</b></td>
				<td align="center" width="271"><b>Product Name</b></td>				
				<td align="center"><b>Damaged Status</b></td>
				<td align="center"><b>Damage Remarks</b></td>								
			</tr>

		<logic:iterate id="damagedProdList" name="HBOMarkDamagedForm" property="damagedProdList" indexId="m">
			<tr BGCOLOR='<%=((m.intValue()+1) % 2==0 ? rowDark : rowLight) %>'>
				<td align="center" width="271"><bean:write name="damagedProdList" property="serialNo"/></td>
				<td align="center" width="271"><bean:write name="damagedProdList" property="productName"/></td>
				<td align="center"><bean:write name="damagedProdList" property="damagedStatus"/></td>
				<td align="center"><bean:write name="damagedProdList" property="damageRemarks"/></td>
			</tr>
		</logic:iterate>
			
		</table>
	</logic:notEmpty>
</html:form>
</BODY>
</html:html>
