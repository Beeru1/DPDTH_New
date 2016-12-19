<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t"%>

<html:html>

<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet"
	href="<%=request.getContextPath()%>/jsp/reverse/theme/text.css"
	type="text/css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/theme/naaz_tt.css" type="text/css">
<script language="JavaScript"
	src="<%=request.getContextPath()%>/jScripts/naaztt.js"></script>
<script language="JavaScript"
	src="<%=request.getContextPath()%>/jScripts/AccountMaster.js"></script>
	<SCRIPT language="JavaScript" src="<%=request.getContextPath()%>/scripts/validation.js" type="text/javascript">
</SCRIPT>
<TITLE></TITLE>

<style type="text/css">
.noScroll{
position: relative;
	 top: 0px;
	left: 0px;
	 top:expression(this.offsetParent.scrollTop-2);
  
}
</style>
</HEAD>
<SCRIPT language="JavaScript" type="text/javascript">

function viewSerialNo(invoiceNo)
{
	//alert(invoiceNo);
  var url="dcDamageStatus.do?methodName=viewSerialsStatus&invoiceNo="+invoiceNo;
  window.open(url,"SerialNo","width=700,height=600,scrollbars=yes,toolbar=no");
}

function printPage(strDCNo, strDCDate)
{
	if(strDCNo !="")
	{
		var url="printDCDetails.do?methodName=printDC&Dc_No="+strDCNo+"&dc_date="+strDCDate;
 		window.open(url,"SerialNo","width=700,height=600,scrollbars=yes,toolbar=no");
  	}
}

function specialChar(obj)
{
  var fieldName;

  if (obj.name == "courierAgency")
  {
   fieldName = "Courier Agency";
  }
  if (obj.name == "docketNumber")
  {
   fieldName = "Docket Number";
  }

  var iChars = "!@#$%^&*()+=-[]\\\';,./{}|\":<>?~`_";

  for (var i = 0; i < obj.value.length; i++) 
    {
  	if (iChars.indexOf(obj.value.charAt(i)) != -1) 
    {
  	alert ("Special characters are not allowed for "+fieldName);
  	obj.value = obj.value.substring(0,i);
  	return false;
  	}
  }	
}


function viewSerialNoFresh(invoiceNo)
{
	//alert(invoiceNo);
  //alert("invoiceNo  ::  "+invoiceNo);
  var url="dcDamageStatus.do?methodName=viewSerialsStatusFresh&invoiceNo="+invoiceNo;
 // alert(url);
  window.open(url,"SerialNo","width=700,height=600,scrollbars=yes,toolbar=no");
}

function printPageFresh(strDCNo, strDCDate)
{
	if(strDCNo !="")
	{
		var url="printDCDetails.do?methodName=printDC&Dc_No="+strDCNo+"&TransType=Fresh&dc_date="+strDCDate;
 	    window.open(url,"SerialNo","width=700,height=600,scrollbars=yes,toolbar=no");
	}
}

function viewSerialNoChurn(invoiceNo)
{
  var url="dcDamageStatus.do?methodName=viewSerialsStatusChurn&invoiceNo="+invoiceNo;
 // alert(url);
  window.open(url,"SerialNo","width=700,height=600,scrollbars=yes,toolbar=no");
}

function printPageChurn(strDCNo, strDCDate)
{
	if(strDCNo !="")
	{
		var url="printDCDetails.do?methodName=printDC&Dc_No="+strDCNo+"&TransType=Churn&dc_date="+strDCDate;
 	    window.open(url,"SerialNo","width=700,height=600,scrollbars=yes,toolbar=no");
	}
}

function submitDamageData(obj, strDCNo, courierAgency,docketNum,rowNum){
	var submitRow = document.getElementById("tableMSA").rows[rowNum].cells;
	var courierAgencyInserted;
	var docketNumInserted;
	if(courierAgency==""){
		if(document.all){
	     	courierAgencyInserted = submitRow[6].getElementsByTagName("input")[0].value;
		} else {
	    	courierAgencyInserted = submitRow[6].getElementsByTagName("input")[0].value.textContent;
		}
		if(trim(courierAgencyInserted) =="" || courierAgencyInserted ==null){
			alert("Please Enter Courier Agency Name.");
			return false;
		}
		document.forms[0].hidCourAgen.value=trim(courierAgencyInserted);
	}
	if(docketNum==""){
		if(document.all){
	     	docketNumInserted = submitRow[7].getElementsByTagName("input")[0].value;
		} else {
	    	docketNumInserted = submitRow[7].getElementsByTagName("input")[0].value.textContent;
		}
		if(trim(docketNumInserted) =="" || docketNumInserted ==null){
			alert("Please Enter Docket Number.");
			return false;
		}
		document.forms[0].hidDocketNum.value=trim(docketNumInserted);
	}
	
	document.forms[0].action ="dcChangeStatus.do?methodName=initDcStatus&methodValue=SubmitDamgDetail&dcNo="+strDCNo;
	obj.disabled = true;
	document.forms[0].submit();
	
	
}

function submitFreshData(obj, strDCNo, courierAgency,docketNum,rowNum){
	var submitRow = document.getElementById("tableFresh").rows[rowNum].cells;
	var courierAgencyInserted;
	var docketNumInserted;
	if(courierAgency==""){
		if(document.all){
	     	courierAgencyInserted = submitRow[6].getElementsByTagName("input")[0].value;
		} else {
	    	courierAgencyInserted = submitRow[6].getElementsByTagName("input")[0].value.textContent;
		}
		if(trim(courierAgencyInserted) =="" || courierAgencyInserted ==null){
			alert("Please Enter Courier Agency Name.");
			return false;
		}
		document.forms[0].hidCourAgen.value=trim(courierAgencyInserted);
	}
	if(docketNum==""){
		if(document.all){
	     	docketNumInserted = submitRow[7].getElementsByTagName("input")[0].value;
		} else {
	    	docketNumInserted = submitRow[7].getElementsByTagName("input")[0].value.textContent;
		}
		if(trim(docketNumInserted) =="" || docketNumInserted ==null){
			alert("Please Enter Docket Number.");
			return false;
		}
		document.forms[0].hidDocketNum.value=trim(docketNumInserted);
	}
	
	document.forms[0].action ="dcChangeStatus.do?methodName=initDcStatus&methodValue=SubmitFrsDetail&dcNo="+strDCNo;
	obj.disabled = true;
	document.forms[0].submit();
	
}

function submitChurnData(obj, strDCNo, courierAgency,docketNum,rowNum)
{
	//alert("strDCNo  ::  "+strDCNo+"\ncourierAgency  ::  "+courierAgency+
	//		"\ndocketNum  ::  "+docketNum+"\nrowNum  ::  "+rowNum);
	
	var submitRow = document.getElementById("tableChurn").rows[rowNum].cells;
	var courierAgencyInserted;
	var docketNumInserted;
	if(courierAgency=="")
	{
		if(document.all)
	     	courierAgencyInserted = submitRow[6].getElementsByTagName("input")[0].value;
		else 
	    	courierAgencyInserted = submitRow[6].getElementsByTagName("input")[0].value.textContent;
	    	
		if(trim(courierAgencyInserted) =="" || courierAgencyInserted ==null)
		{
			alert("Please Enter Courier Agency Name.");
			return false;
		}
		
		document.forms[0].hidCourAgen.value=trim(courierAgencyInserted);
	}
	else
		document.forms[0].hidCourAgen.value=trim(courierAgency);	//Code inserted to resolve DC issue @ CR58299
	
	if(docketNum=="")
	{
		if(document.all)
	     	docketNumInserted = submitRow[7].getElementsByTagName("input")[0].value;
		else 
	    	docketNumInserted = submitRow[7].getElementsByTagName("input")[0].value.textContent;
	    	
		if(trim(docketNumInserted) =="" || docketNumInserted ==null)
		{
			alert("Please Enter Docket Number.");
			return false;
		}
		
		document.forms[0].hidDocketNum.value=trim(docketNumInserted);
	}
	else
		document.forms[0].hidDocketNum.value=trim(docketNum);	//Code inserted to resolve DC issue @ CR58299
		
	//alert("HIDEN COUR NO  ::  "+document.forms[0].hidCourAgen.value+"\nHIDEN DOC NO  ::  "+document.forms[0].hidDocketNum.value);
	document.forms[0].action ="dcChangeStatus.do?methodName=initDcStatus&methodValue=SubmitChurnDetail&dcNo="+strDCNo;
	obj.disabled = true;
	document.forms[0].submit();
}

</SCRIPT>
<BODY
	BACKGROUND="<%=request.getContextPath()%>/jsp/reverse/images/bg_main.gif">
<html:form action="/dcChangeStatus">

<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>

<IMG src="<%=request.getContextPath()%>/images/DC_Status.jpg"
		width="525" height="26" alt=""/>

	<DIV id=formcontainer><SCRIPT language=JavaScript>
							tobj = new TT('mainTabTable','DIV_DAMAGE','tab1','tabHighlight','tabNormal','reverse');
							iobj = tobj.additem('tab1','#','','_self','Damaged Stock DC Status','Damaged Stock DC Status','DIV_DAMAGE','TTCURRENT');
							iobj = tobj.additem('tab2','#','','_self','Fresh Stock DC Status','Fresh Stock DC Status','DIV_FRESH','TTNORMAL');	
							iobj = tobj.additem('tab3','#','','_self','Churn Stock DC Status','Churn Stock DC Status','DIV_CHURN','TTNORMAL');	
							var tsrc=tobj.showTT('reverse');//Neetika
							document.write(tsrc);
						</SCRIPT>
	
		<DIV id=formbody>
			<DIV id=DIV_DAMAGE style="DISPLAY: block">
				<DIV style="height: 310px; width: 100%; overflow: auto; visibility: visible;z-index:1; left: 133px; top: 250px;">
					<table width="100%" align="left" border="1" cellpadding="0" cellspacing="0" id="tableMSA"
					style="border-collapse: collapse;">
						<thead>
							<tr class="noScroll">
								<!-- <td width="1%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									&nbsp;</FONT></td> -->
								<!--<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="view" key="dcStatus.SRNo"/></SPAN></FONT></td>
								--><td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="view" key="dcStatus.DCNo"/></SPAN></FONT></td>
								<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="view" key="dcStatus.DCDate"/></SPAN></FONT></td>
								<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="view" key="dcStatus.View"/></SPAN></FONT></td>
								<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="dp" key="label.dcStatus.srno"/></SPAN></FONT></td>
								<td width="7%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="view" key="dcStatus.Status"/></SPAN></FONT></td>
								<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="view" key="dcStatus.Remarks"/></SPAN></FONT></td>
									
								<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="view" key="dcStatus.Agency"/></SPAN></FONT></td>
								<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="view" key="dcStatus.DocketNum"/></SPAN></FONT></td>
								<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="view" key="dcStatus.SentToWareHouse"/></SPAN></FONT></td>
								
							</tr>
						</thead>
						
						<tbody>
							<logic:empty name="DpDcDamageStatusBean" property="dcNosList">
								<TR>
									<TD class="text" align="center" colspan="8">No record available</TD>
								</TR>			
							</logic:empty>
							
							<logic:notEmpty name="DpDcDamageStatusBean" property="dcNosList">
								<logic:iterate name="DpDcDamageStatusBean" id="dcNosList" indexId="i" property="dcNosList">
									<TR  BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>'>
										
										<!-- 	Commented because calcel DC is not allowed
										<logic:equal name="dcNosList" property="strStatus" value="CREATED">
										<TD align="center" class="text">
											<input type="checkbox" value='<bean:write name="dcNosList" property="strDCNo"/>' id="strCheckedDC" property="strCheckedDC" name="strCheckedDC">
											<input type="textField" value='<bean:write name="dcNosList" property="strStatus"/>' id="strCheckedDCVal" property="strCheckedDCVal" name="strCheckedDCVal" style="display: none">	
										</TD>
										</logic:equal>
										
										<logic:notEqual name="dcNosList" property="strStatus" value="CREATED">
										<TD align="center" class="text">
										<input type="textField" value='<bean:write name="dcNosList" property="strStatus"/>' id="checkDCValue" property="strCheckedDCVal" name="strCheckedDCVal" style="display: none">	
										</TD>
										</logic:notEqual>
										 -->
									
										
										<!--<TD align="center" class="text">
											<bean:write name="dcNosList" property="strSerialNo"/>					
										</TD>
										--><TD align="center" class="text">
											<bean:write name="dcNosList" property="strDCNo"/>					
										</TD>
										<TD align="center" class="text">
											<bean:write name="dcNosList" property="strDCDate"/>					
										</TD>
										<TD align="center" class="text">
											<a href="#" onclick="printPage('<bean:write name='dcNosList' property='strDCNo'/>', '<bean:write name="dcNosList" property="strDCDate"/>')">
												<bean:message  bundle="view" key="dcStatus.View"/>				
											</a>	
										</TD>
										<TD align="center" class="text">
											<a href="#" onclick="viewSerialNo('<bean:write name='dcNosList' property='strDCNo'/>')">
												<bean:message  bundle="view" key="dcStatus.View"/>				
											</a>	
										</TD>
										<TD align="center" class="text">
											<bean:write name="dcNosList" property="strDcStatus"/>					
										</TD>
										<TD align="center" class="text">
											<bean:write name="dcNosList" property="strBOTreeRemarks"/>					
										</TD>
										<TD align="center" class="text">
											<logic:notEmpty name="dcNosList" property="courierAgency">
												<bean:write name="dcNosList" property="courierAgency"/>			
											</logic:notEmpty>
											<logic:empty name="dcNosList" property="courierAgency">
												<input type="textfield" id="courierAgency" maxlength="100" onkeyup="return specialChar(this);" style="width: 80px; background-color: #F3F781;"/>
											</logic:empty>
										</TD>
										<TD align="center" class="text">
											<logic:notEmpty name="dcNosList" property="docketNumber">
												<bean:write name="dcNosList" property="docketNumber"/>			
											</logic:notEmpty>
											<logic:empty name="dcNosList" property="docketNumber">
												<input type="textfield" id="docketNumber" maxlength="50" onkeyup="return specialChar(this);" style="width: 80px; background-color: #F3F781;" />
											</logic:empty>			
										</TD>
										<TD align="center" class="text">
											<logic:equal name="dcNosList" property="isFilled" value="N">
												<input type="button" onclick="submitDamageData(this, '<bean:write name='dcNosList' property='strDCNo'/>', '<bean:write name="dcNosList" property="courierAgency"/>','<bean:write name="dcNosList" property="docketNumber"/>','<%=i.intValue()+1%>')" value="Click to Send"  />		
											</logic:equal>
													
										</TD>
										
									</TR>
								</logic:iterate>
							</logic:notEmpty>
						</tbody>
					</table>
				</DIV>
			</DIV>
			<DIV id=DIV_FRESH style="DISPLAY: none">
				<DIV style="height: 310px; width: 100%; overflow: auto; visibility: visible;z-index:1; left: 133px; top: 250px;">
					<table width="100%" align="left" border="1" cellpadding="0" cellspacing="0" id="tableFresh"
					style="border-collapse: collapse;">
						<thead>
							<tr class="noScroll">
								<!-- <td width="1%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									&nbsp;</FONT></td> -->
								<!--<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="view" key="dcStatus.SRNo"/></SPAN></FONT></td>
								--><td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="view" key="dcStatus.DCNo"/></SPAN></FONT></td>
								<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="view" key="dcStatus.DCDate"/></SPAN></FONT></td>
								<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="view" key="dcStatus.View"/></SPAN></FONT></td>
								<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="dp" key="label.dcStatus.srno"/></SPAN></FONT></td>
								<td width="7%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="view" key="dcStatus.Status"/></SPAN></FONT></td>
								<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="view" key="dcStatus.Remarks"/></SPAN></FONT></td>
								<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="view" key="dcStatus.Agency"/></SPAN></FONT></td>
								<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="view" key="dcStatus.DocketNum"/></SPAN></FONT></td>
								<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="view" key="dcStatus.SentToWareHouse"/></SPAN></FONT></td>
							
							</tr>
						</thead>
						
						<tbody>
							<logic:empty name="DpDcDamageStatusBean" property="dcNosListFresh">
								<TR>
									<TD class="text" align="center" colspan="8">No record available</TD>
								</TR>			
							</logic:empty>
							
							<logic:notEmpty name="DpDcDamageStatusBean" property="dcNosListFresh">
								<logic:iterate name="DpDcDamageStatusBean" id="dcNosListFresh" indexId="i" property="dcNosListFresh">
									<TR  BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>'>
										
										<!-- 	Commented because calcel DC is not allowed
										<logic:equal name="dcNosListFresh" property="strStatus" value="CREATED">
										<TD align="center" class="text">
											<input type="checkbox" value='<bean:write name="dcNosListFresh" property="strDCNo"/>' id="strCheckedDC" property="strCheckedDC" name="strCheckedDC">
											<input type="textField" value='<bean:write name="dcNosListFresh" property="strStatus"/>' id="strCheckedDCVal" property="strCheckedDCVal" name="strCheckedDCVal" style="display: none">	
										</TD>
										</logic:equal>
										
										<logic:notEqual name="dcNosListFresh" property="strStatus" value="CREATED">
										<TD align="center" class="text">
										<input type="textField" value='<bean:write name="dcNosListFresh" property="strStatus"/>' id="checkDCValue" property="strCheckedDCVal" name="strCheckedDCVal" style="display: none">	
										</TD>
										</logic:notEqual>
										 -->
									
										
										<!--<TD align="center" class="text">
											<bean:write name="dcNosListFresh" property="strSerialNo"/>					
										</TD>
										--><TD align="center" class="text">
											<bean:write name="dcNosListFresh" property="strDCNo"/>					
										</TD>
										<TD align="center" class="text">
											<bean:write name="dcNosListFresh" property="strDCDate"/>					
										</TD>
										<TD align="center" class="text">
											<a href="#" onclick="printPageFresh('<bean:write name='dcNosListFresh' property='strDCNo'/>','<bean:write name="dcNosListFresh" property="strDCDate"/>')">
												<bean:message  bundle="view" key="dcStatus.View"/>				
											</a>	
										</TD>
										<TD align="center" class="text">
											<a href="#" onclick="viewSerialNoFresh('<bean:write name='dcNosListFresh' property='strDCNo'/>')">
												<bean:message  bundle="view" key="dcStatus.View"/>				
											</a>	
										</TD>
										<TD align="center" class="text">
											<bean:write name="dcNosListFresh" property="strDcStatus"/>					
										</TD>
										<TD align="center" class="text">
											<bean:write name="dcNosListFresh" property="strBOTreeRemarks"/>					
										</TD>
										
										<TD align="center" class="text">
											<logic:notEmpty name="dcNosListFresh" property="courierAgency">
												<bean:write name="dcNosListFresh" property="courierAgency"/>			
											</logic:notEmpty>
											<logic:empty name="dcNosListFresh" property="courierAgency">
												<input type="textfield" id="courierAgency" maxlength="100" onkeyup="return specialChar(this);" style="width: 80px; background-color: #F3F781;" />
											</logic:empty>
										</TD>
										<TD align="center" class="text">
											<logic:notEmpty name="dcNosListFresh" property="docketNumber">
												<bean:write name="dcNosListFresh" property="docketNumber"/>			
											</logic:notEmpty>
											<logic:empty name="dcNosListFresh" property="docketNumber">
												<input type="textfield" id="docketNumber" maxlength="50" onkeyup="return specialChar(this);" style="width: 80px; background-color: #F3F781;" />
											</logic:empty>			
										</TD>
										<TD align="center" class="text">
											<logic:equal name="dcNosListFresh" property="isFilled" value="N">
												<input type="button" onclick="submitFreshData(this, '<bean:write name='dcNosListFresh' property='strDCNo'/>', '<bean:write name="dcNosListFresh" property="courierAgency"/>','<bean:write name="dcNosListFresh" property="docketNumber"/>','<%=i.intValue()+1%>')" value="Click to Send"  />		
											</logic:equal>
													
										</TD>
										
									</TR>
								</logic:iterate>
							</logic:notEmpty>
						</tbody>
					</table>
				</DIV>
			</DIV>
           <DIV id=DIV_CHURN style="DISPLAY: none">
				<DIV style="height: 310px; width: 100%; overflow: auto; visibility: visible;z-index:1; left: 133px; top: 250px;">
					<table width="100%" align="left" border="1" cellpadding="0" cellspacing="0" id="tableChurn"
					style="border-collapse: collapse;">
						<thead>
							<tr class="noScroll">
								<!-- <td width="1%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									&nbsp;</FONT></td> -->
								<!--<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="view" key="dcStatus.SRNo"/></SPAN></FONT></td>
								--><td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="view" key="dcStatus.DCNo"/></SPAN></FONT></td>
								<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="view" key="dcStatus.DCDate"/></SPAN></FONT></td>
								<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="view" key="dcStatus.View"/></SPAN></FONT></td>
								<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="dp" key="label.dcStatus.srno"/></SPAN></FONT></td>
								<td width="7%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="view" key="dcStatus.Status"/></SPAN></FONT></td>
								<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="view" key="dcStatus.Remarks"/></SPAN></FONT></td>
								<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="view" key="dcStatus.Agency"/></SPAN></FONT></td>
								<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="view" key="dcStatus.DocketNum"/></SPAN></FONT></td>
								<td width="8%" bgcolor="#CD0504" class="colhead" align="center"><FONT color="white">
									<SPAN><bean:message  bundle="view" key="dcStatus.SentToWareHouse"/></SPAN></FONT></td>
							
							</tr>
						</thead>
						
						<tbody>
							<logic:empty name="DpDcDamageStatusBean" property="dcNosListChurn">
								<TR>
									<TD class="text" align="center" colspan="8">No record available</TD>
								</TR>			
							</logic:empty>
							
							<logic:notEmpty name="DpDcDamageStatusBean" property="dcNosListChurn">
								<logic:iterate name="DpDcDamageStatusBean" id="dcNosListChurn" indexId="i" property="dcNosListChurn">
									<TR  BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>'>
										
										<!-- 	Commented because calcel DC is not allowed
										<logic:equal name="dcNosListChurn" property="strStatus" value="CREATED">
										<TD align="center" class="text">
											<input type="checkbox" value='<bean:write name="dcNosListChurn" property="strDCNo"/>' id="strCheckedDC" property="strCheckedDC" name="strCheckedDC">
											<input type="textField" value='<bean:write name="dcNosListChurn" property="strStatus"/>' id="strCheckedDCVal" property="strCheckedDCVal" name="strCheckedDCVal" style="display: none">	
										</TD>
										</logic:equal>
										
										<logic:notEqual name="dcNosListChurn" property="strStatus" value="CREATED">
										<TD align="center" class="text">
										<input type="textField" value='<bean:write name="dcNosListChurn" property="strStatus"/>' id="checkDCValue" property="strCheckedDCVal" name="strCheckedDCVal" style="display: none">	
										</TD>
										</logic:notEqual>
										 -->
									
										
										<!--<TD align="center" class="text">
											<bean:write name="dcNosListChurn" property="strSerialNo"/>					
										</TD>
										--><TD align="center" class="text">
											<bean:write name="dcNosListChurn" property="strDCNo"/>					
										</TD>
										<TD align="center" class="text">
											<bean:write name="dcNosListChurn" property="strDCDate"/>					
										</TD>
										<TD align="center" class="text">
											<a href="#" onclick="printPageChurn('<bean:write name='dcNosListChurn' property='strDCNo'/>','<bean:write name="dcNosListChurn" property="strDCDate"/>')">
												<bean:message  bundle="view" key="dcStatus.View"/>				
											</a>	
										</TD>
										<TD align="center" class="text">
											<a href="#" onclick="viewSerialNoChurn('<bean:write name='dcNosListChurn' property='strDCNo'/>')">
												<bean:message  bundle="view" key="dcStatus.View"/>				
											</a>	
										</TD>
										<TD align="center" class="text">
											<bean:write name="dcNosListChurn" property="strDcStatus"/>					
										</TD>
										<TD align="center" class="text">
											<bean:write name="dcNosListChurn" property="strBOTreeRemarks"/>					
										</TD>
										
										<TD align="center" class="text">
											<logic:notEmpty name="dcNosListChurn" property="courierAgency">
												<bean:write name="dcNosListChurn" property="courierAgency"/>			
											</logic:notEmpty>
											<logic:empty name="dcNosListChurn" property="courierAgency">
												<input type="textfield" id="courierAgency" maxlength="100" onkeyup="return specialChar(this);" style="width: 80px; background-color: #F3F781;" />
											</logic:empty>
										</TD>
										<TD align="center" class="text">
											<logic:notEmpty name="dcNosListChurn" property="docketNumber">
												<bean:write name="dcNosListChurn" property="docketNumber"/>			
											</logic:notEmpty>
											<logic:empty name="dcNosListChurn" property="docketNumber">
												<input type="textfield" id="docketNumber" maxlength="50" onkeyup="return specialChar(this);" style="width: 80px; background-color: #F3F781;" />
											</logic:empty>			
										</TD>
										<TD align="center" class="text">
											<logic:equal name="dcNosListChurn" property="isFilled" value="N">
												<input type="button" onclick="submitChurnData(this, '<bean:write name='dcNosListChurn' property='strDCNo'/>', '<bean:write name="dcNosListChurn" property="courierAgency"/>','<bean:write name="dcNosListChurn" property="docketNumber"/>','<%=i.intValue()+1%>')" value="Click to Send"  />		
											</logic:equal>
													
										</TD>
										
									</TR>
								</logic:iterate>
							</logic:notEmpty>
						</tbody>
					</table>
				</DIV>
			</DIV>


		</DIV>
	</DIV>
	<html:hidden property="hidArrDcNos"  name="DpDcDamageStatusBean" value=""/>
	<html:hidden property="methodName"/>
	<html:hidden property="hidCourAgen"  name="DpDcDamageStatusBean"/>
	<html:hidden property="hidDocketNum"  name="DpDcDamageStatusBean"/>
</html:form>
</body>
</html:html>
