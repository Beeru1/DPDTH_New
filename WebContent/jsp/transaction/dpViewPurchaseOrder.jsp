
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
<LINK rel="stylesheet" href="theme/text.css" type="text/css">

<TITLE> </TITLE>
<style type="text/css">
.noScroll{
position: relative;
	 top: 0px;
	left: 0px;
	 top:expression(this.offsetParent.scrollTop-2);
  
}
</style>
<SCRIPT language="JavaScript" type="text/javascript">
//Added By Harpreet
function viewSerialNo(invoiceNo,productID,circleId)
{
  //alert("invoiceNo  ::  "+invoiceNo);
  //alert("productID  ::  "+productID);
  //alert("circleId  ::  "+circleId);
  var url="ViewPurchaseOrder.do?methodName=viewSerialsAndProductList&invoiceNo="+invoiceNo+"&productID="+productID+"&circleId="+circleId;
  window.open(url,"SerialNo","width=700,height=600,scrollbars=yes,toolbar=no");
}

function  postatus(check)
{
//alert("Hello"+check.value)
	document.forms[0].statusSelect.value=check.value;
	document.forms[0].action="ViewPurchaseOrder.do?methodName=viewPODetails";
	document.forms[0].method="post";
	document.forms[0].submit();

}
function paginationStep(nextPage,circleId){
	// alert("nextPage->"+nextPage);
	// alert("circleId->"+circleId);
	document.forms[0].action="ViewPurchaseOrder.do?methodName=viewPODetails&page="+nextPage;
	//+ "&circleId="+ circleId ;
	document.forms[0].method="post";
	document.forms[0].submit();
	}

//function porCancel(porNo,status,productid){
function porCancel(porNo,status){
	// alert("status"+status)
if(confirm('Press OK to Cancel, PORequisition NO.'+porNo)){	
	
	if(status == "PO RAISED"){
		document.forms[0].prcancelStatus.value=status;
		document.forms[0].cancelpor_no.value= porNo;
		document.forms[0].methodName.value="cancelporNumber";
		document.forms[0].submit();
	}
	
	if(status == "UNDER APPROVAL"){
		document.forms[0].prcancelStatus.value=status;
		document.forms[0].cancelpor_no.value= porNo;
		document.forms[0].methodName.value="cancelporNumber";
		document.forms[0].submit();
	}

	if(status == ""){
		document.forms[0].cancelpor_no.value= porNo;
		//document.forms[0].prcancelStatus.value=1;
		document.forms[0].methodName.value="cancelporNumber";
		document.forms[0].submit();
	}
	if(status == "DISPATCHED"){
		alert("Purchase Order Already Dispatched");
	}
}}

function acceptStock(){

	var obj = document.forms[0].check1;
	var len = obj.length;
	var chkCnt = 0;
	var unchkcnt=0;
	if(len == undefined)
	{		
		if(document.forms[0].check1.checked) 
			{
		  	chkCnt++;
		  	}
			}
	
	for(var i =0; i < len ; i++)
		{
		if(document.forms[0].check1[i].checked) 
			{
				chkCnt++;
				break;
			}
		
		}
	if(chkCnt == 0)
	{
		alert("Please Check Atleast One Box");
		return false;
	}
	
	document.forms[0].action="ViewPurchaseOrder.do?methodName=acceptStock";
	document.forms[0].method="post";
	document.forms[0].submit();
}

function clickSimilar(val){
	var checkLength = document.forms[0].check1.length;
	
	if(checkLength!=undefined){
		 if(val.checked==true){
		for(var i =0; i < checkLength ; i++)
		{	
			if(document.forms[0].check1[i].value==val.value){
			 	document.forms[0].check1[i].checked=true; 
			 }	
		}	
	}
	else if(val.checked==false){
		for(var i =0; i < checkLength ; i++)
		{
			if(document.forms[0].check1[i].value==val.value){
				document.forms[0].check1[i].checked=false; 
			}
		}	
	}
	}							
}
</SCRIPT>
</HEAD>

<BODY BACKGROUND="images/bg_main.gif">
<html:form  action="/ViewPurchaseOrder"> 

<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
%>

	<IMG src="<%=request.getContextPath()%>/images/view-po-status.gif"
		width="550" height="25" alt=""/>
		
<br>
<table align="center" border="0" cellpadding="0" cellspacing="0" style="border-collapse: collapse;" width='100%'>

<tr>

<TD align="center" width="150" class="text"><STRONG><FONT color="#000000">
<bean:message  bundle="dp" key="label.VPO.POS"/></FONT>
<FONT color="RED">*</FONT> :</STRONG></TD>
			<TD align="left" width="176"><FONT color="#003399"> <html:select
				property="statusSelect" styleId="statusSelect" tabindex="1"
				styleClass="w130" onchange="postatus(this)">
				<!--<html:option value="5">
					<bean:message key="application.option.select" bundle="view" />
				</html:option>
				--><html:option value="0">
All
</html:option>
				<html:options collection="POStatusList" labelProperty="statusValue"
					property="statusId" />
			</html:select></table>
<br/>
<logic:equal name="ViewPODetailsBean" property="intStatus" value="no"><div style="DISPLAY: block">
	<DIV style="height: 300px; width: 100%; overflow: auto; visibility: visible;z-index:1; left: 133px; top: 180px; vertical-align:top;">
	<table align="center" border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse;" width='100%'>

	<tr>
		<logic:messagesPresent message="true" >
				<html:messages property="file" id="pomsg" bundle="dp" message="true" >
					<strong><font color="red"> <bean:write name="pomsg" /></font>
				</html:messages>
				<html:messages id="msg" property="INSERT_SUCCESS" bundle="hboView" message="true">
					<font color="red" size="2"><strong><bean:write name="msg"/></strong></font>
				</html:messages>
				<html:messages id="msg1" property="ERROR_OCCURED" bundle="hboView" message="true">
					<font color="red" size="2"><strong><bean:write name="msg1"/></strong></font>
				</html:messages>				
		</logic:messagesPresent>
	</tr>
		<thead>
		<tr class="noScroll">
			<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message  bundle="dp" key="label.VPO.POREQ"/></FONT></td>
			<!-- <td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message  bundle="dp" key="label.VPO.DistName"/></FONT></td> Commented by Nazim Hussain as told by Kivisha-->
			<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message  bundle="dp" key="label.VPO.PRDDate"/></FONT></td>
			<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message  bundle="dp" key="label.VPO.PO."/></FONT></td>
			<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message  bundle="dp" key="label.VPO.PODATE"/></FONT></td>
			<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message  bundle="dp" key="label.VPO.Status"/></FONT></td>
			<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message  bundle="dp" key="label.VPO.DCNO"/></FONT></td>
			<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message  bundle="dp" key="label.VPO.DCDATE"/></FONT></td>
			<!-- <td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message  bundle="dp" key="label.VPO.DD/CHEQUENO"/></FONT></td>Commented by Nazim Hussain as told by Kivisha-->
			<!-- <td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message  bundle="dp" key="label.VPO.DD/CHEQUEDATE"/></FONT></td>Commented by Nazim Hussain as told by Kivisha-->
			<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message  bundle="dp" key="label.VPO.ProductN."/></FONT></td>
			<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message  bundle="dp" key="label.VPO.RsdQTY"/></FONT></td>			
			<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message  bundle="dp" key="label.VPO.PoQTY"/></FONT></td>
			<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message  bundle="dp" key="label.VPO.INVOICEPROD"/></FONT></td>
			<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message  bundle="dp" key="label.VPO.InvQTY"/></FONT></td>
			<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message  bundle="dp" key="label.VPO.Unit"/></FONT></td>
			<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message  bundle="dp" key="label.VPO.REMARKS"/></FONT></td>
			<!-- Added By Harpreet -->
			<td bgcolor="#CD0504" class="colhead" align="center"><FONT color="white"><bean:message  bundle="dp" key="label.DCA.VSRNO"/></FONT></td>
		</tr>
</thead>
		<logic:empty name="ViewPODetailsBean" property="poList">
			<TR class="lightBg">
				<TD class="text" align="center" colspan="16">Purchase Order Details are not Avaliable</TD>
			</TR>
		</logic:empty>
				
		<logic:notEmpty name="ViewPODetailsBean" property="poList">
			<logic:iterate name="ViewPODetailsBean"
			 id="POInfo" indexId="i"
				property="poList">
				<TR BGCOLOR='<%=((i.intValue()+1) % 2==0 ? rowDark : rowLight) %>' >

					
					<TD align="center"   class="text"><bean:write name="POInfo"
						property="por_no" /></TD>
						
					<!-- <TD align="center"  class="text"><bean:write name="POInfo"
						property="distName" /> </TD> Commented by Nazim Hussain as told by Kivisha-->
					
					<TD align="center"  class="text"><bean:write name="POInfo"
						property="pr_dt" /></TD>
			
					<TD align="center"  class="text"><bean:write name="POInfo"
						property="po_no" /></TD>	
						
					<TD align="center"  class="text"><bean:write name="POInfo"
						property="po_dt" /> </TD>
						
					<TD align="center"  class="text"><bean:write name="POInfo"
						property="status" /></TD>
					<TD align="center"  class="text"><bean:write name="POInfo"
						property="dc_no" /></TD>
					
					<TD align="center"  class="text"><bean:write name="POInfo"
						property="dc_dt" /></TD>

					<!-- <TD align="center"  class="text"><bean:write name="POInfo"
						property="dd_cheque_no" /></TD>	</TD> Commented by Nazim Hussain as told by Kivisha-->				
									
					<!-- <TD align="center"  class="text"><bean:write name="POInfo"
						property="dd_cheque_dt" /></TD> Commented by Nazim Hussain as told by Kivisha-->
				
					<TD align="center"  class="text"><bean:write name="POInfo"
						property="productName" /> 
						</TD>
						
					<TD align="center"  class="text"><bean:write name="POInfo"
						property="raised_quantity" /></TD>					
					
					<TD align="center"  class="text"><bean:write name="POInfo"
						property="quantity" /></TD>			
						
					<TD align="center"  class="text"><bean:write name="POInfo"
						property="invprod" /></TD>		
					
					<TD align="center"  class="text"><bean:write name="POInfo"
						property="invqty" /></TD>
					
					<TD align="center"  class="text">
						<logic:match value="Z" name="POInfo" property="unit">
							<bean:message key="product.option.none" bundle="view" />						
						</logic:match>
						<logic:match value="R" name="POInfo" property="unit">
							<bean:message key="product.option.rupees" bundle="view" />						
						</logic:match>
						<logic:match value="N" name="POInfo" property="unit">
							<bean:message key="product.option.nos" bundle="view" />						
						</logic:match>
					</TD>		
						
					<TD align="center"  class="text"><bean:write name="POInfo"
						property="remarks" /></TD>
					<!-- Added By Harpreet -->	
					<TD align="center" class="text">
					
					<logic:notEmpty  name="POInfo" property="dc_no" >
							<a href="#" onclick="viewSerialNo('<bean:write name='POInfo' property='invoice_no'/>','<bean:write name='POInfo' property='productIDNew'/>','<bean:write name='POInfo' property='circleId'/>')">
									<bean:message  bundle="dp" key="label.DCA.VSRNO_Text"/>					
								</a>	
					</logic:notEmpty>	
										
					</TD>	
			</TR>
			
				</logic:iterate>
			</logic:notEmpty>
	
	
		<!-- pagination code start 
							<TR align="center">
						<TD align="left" bgcolor="#daeefc" colspan="16"><FONT
							face="verdana" size="2">
							<c:if test="${PAGES!=''}">
							<c:if test="${PAGES>1}">
								    	Page:
								<c:if test="${PRE>=1}">
									<a href="#"
										onclick="paginationStep('<c:out value='${PRE}' />','<c:out value='${circleId}' />');">
									Prev</a>
								</c:if>

								<c:if test="${PAGES>LinksPerPage}">
									<c:set var="start_page" value="1" scope="request" />
									<c:if test="${SELECTED_PAGE+1>LinksPerPage}">
										<c:set var="start_page"
											value="${SELECTED_PAGE-(LinksPerPage/2)}" scope="request" />
									</c:if>
									<c:if test="${SELECTED_PAGE+(LinksPerPage/2)-1>=PAGES}">
										<c:set var="start_page" value="${PAGES-LinksPerPage+1}"
											scope="request" />
									</c:if>
									<c:set var="end_page" value="${start_page+LinksPerPage-1}"
										scope="request" />
									<c:forEach var="item" step="1" begin="${start_page}"
										end="${end_page}">
										<c:choose>
											<c:when test="${item==(NEXT-1)}">
												<c:out value="${item}"></c:out>
											</c:when>
											<c:otherwise>
												<a href="#"
													onclick="paginationStep('<c:out value="${item}"/>','<c:out value='${circleId}' />');"><c:out
													value="${item}" /></a>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:if>

								<c:if test="${PAGES<=LinksPerPage}">
									<c:forEach var="item" step="1" begin="1" end="${PAGES}">
										<c:choose>
											<c:when test="${item==(NEXT-1)}">
												<c:out value="${item}"></c:out>
											</c:when>
											<c:otherwise>
												<a href="#"
													onclick="paginationStep('<c:out value="${item}"/>','<c:out value='${circleId}' />');"><c:out
													value="${item}" /></a>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:if>
								
								<c:if test="${NEXT<=PAGES}">
									<a href="#"
										onclick="paginationStep('<c:out value='${NEXT}' />','<c:out value='${circleId}' />');">Next></a>
								</c:if>
								
							</c:if>
						</c:if> </FONT></TD>
					</TR>
				<input type = "hidden" name ="methodName" value = "" />
				<html:hidden property="cancelpor_no" />
				<html:hidden property="circleId" />
				<html:hidden property="prcancelStatus" />		
			
			
			 pagination code end  -->
			 <input type = "hidden" name ="methodName" value = "" />
				<html:hidden property="cancelpor_no" />
				<html:hidden property="circleId"/>
				<html:hidden property="prcancelStatus" />		
	
	</table></DIV></div>
	</logic:equal>

</html:form>
</body>

</html:html>					