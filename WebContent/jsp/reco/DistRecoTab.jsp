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
	src="<%=request.getContextPath()%>/jScripts/reco.js"></script>
	<script language="JavaScript"
	src="<%=request.getContextPath()%>/jScripts/recoPage.js"></script>
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
.button
{
background-color: #FE2E2E;
border-color: #aaa #444 #444 #aaa;
color: #ffffff";
border: 1px;
}

fieldset.field_set_main {
	border-bottom-width: 3px;
	border-top-width: 3px;
	border-left-width: 3px;
	border-right-width: 3px;
	margin: auto;
	padding: 0px 0px 0px 0px;
	border-color: CornflowerBlue;
}

legend.header {
	text-align: right;
}

</style>
</HEAD>
<SCRIPT language="JavaScript" type="text/javascript">
/********************************** Deleted Script by RAM and made new js as recoPage.js ***********/
</SCRIPT>
<%if(request.getAttribute("disabledLink")==null)
{%>
<body onload="checkIsPswdExpiring();getprintpageButton();" onkeydown="extendSession();">
<%}
else
{%>
<body onload="checkIsPswdExpiring();disableLink();getprintpageButton();"  onkeydown="extendSession();">
<% }%>

<p></p>

<html:form action="/DistReco.do" enctype="multipart/form-data" >
<html:hidden property="methodName"  name="DistRecoBean"/>
<html:hidden property="certificateId"  name="DistRecoBean"/>
<html:hidden property="certId" styleId="certId" name="DistRecoBean"/>
<html:hidden property="disabledLink"  name="DistRecoBean"/>
<html:hidden property="isValidToEnter"  name="DistRecoBean"/>
<html:hidden property="oldOrNewReco"  name="DistRecoBean"/>
<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
    String rowMidLight = "#e6e6e6";
    
%>

<IMG src="<%=request.getContextPath()%>/images/Reco_Detail.jpg"
		width="525" height="26" alt=""/>
<div>
<TABLE border="0" align="left" width="100%">
				<TBODY>
					<tr>
						<td colspan="5"><strong><font color="red"
							class="text" size="15px"> <bean:write name="DistRecoBean"
							property="message" /> </font> </strong></td>
					</tr>
					<TR>
						<TD align="left" width="150"><strong><font
							color="#000000">STB Type</font><FONT COLOR="RED">*</font></TD>
						<TD width="200"><html:select property="stbTypeId"
							style="width:180px">
							<logic:notEmpty property="prodTypeList" name="DistRecoBean">
								<html:option value="-1">--All--</html:option>
								<bean:define id="prodTypeList" name="DistRecoBean"
									property="prodTypeList" />
								<html:options labelProperty="productType" property="productId"
									collection="prodTypeList" />

							</logic:notEmpty>
						</html:select></TD>

						<TD align="left" class="text pLeft15" height="29" width="150"><strong><font
							color="#000000">Reco Period</font><FONT COLOR="RED">*</font></TD>
						 <TD height="29" width="200"><html:select
							property="recoPeriodId" style="width:180px">
							<logic:notEmpty property="recoPeriodList" name="DistRecoBean">
								<html:option value="-1">--Select Reco Period--</html:option>
								<bean:define id="recoPeriodList" name="DistRecoBean"
									property="recoPeriodList" />
								<html:options labelProperty="recoPeriodName"
									property="recoPeriodId" collection="recoPeriodList" />

							</logic:notEmpty>
						</html:select></TD>
						<TD><INPUT type="button" name="button"
							onclick=" getProductRecoList();" Class="medium" value="Go">
							<html:hidden name="DistRecoBean" property="strmsg" styleId="strmsgId"/>
							</TD>
					</TR>
				</TBODY>
</TABLE>

</div>
<br></br><br/>
	<DIV id=formcontainer>
	<SCRIPT language=JavaScript>
							tobj = new TT('mainTabTable','DIV_OPEN','tab1','tabHighlight','tabNormal','reco');
							iobj = tobj.additem('tab1','#','','_self','Opening Stock','Opening Stock','DIV_OPEN','TTCURRENT');
							iobj = tobj.additem('tab2','#','','_self','Received Stock','Received Stock','DIV_RECEIVED','TTNORMAL');	
							iobj = tobj.additem('tab3','#','','_self','Returned Stock','Returned Stock','DIV_RETURNED','TTNORMAL');	
							
							iobj = tobj.additem('tab4','#','','_self','Activation','Activation','DIV_ACTIVATION','TTNORMAL');
							iobj = tobj.additem('tab5','#','','_self','Closing Stock','Closing Stock','DIV_CLOSING','TTNORMAL');	
							iobj = tobj.additem('tab6','#','','_self','Summary','Summary','DIV_SUMMARY','TTNORMAL');	
							var tsrc=tobj.showTT('reco');//Neetika
							document.write(tsrc);
						</SCRIPT>
	
		<DIV id=formbody>
		
			
			<!-- including here both splited jsp files (RAM)-->	
			<jsp:include flush="true" page="DistRecoTabPartOne.jsp"></jsp:include>
			<jsp:include flush="true" page="DistRecoTabPartTwo.jsp"></jsp:include>
			<!-- including here both splited jsp files (RAM)-->	

		</DIV>
	</DIV>
	<table>
	<tr><td></td><td></td><td><input type="button" id="printPage" value="Print Certificate" disabled="disabled" onclick="printtPage()" /> </td>
			<!-- Adding by RAM -->
	<td>
<logic:equal name="DistRecoBean" property="oldOrNewReco" value="N">						
<div id="closingStockUploadScreen">
<fieldset class="field_set_main">
<legend class="header"><font color="#FF0000">Upload Closing Stock</font></legend>

	<table>
		<tr>
			<td>Select Product :</td>
			<td>
			<html:select property="productsLists" onchange="disableUpload();showSwap()" styleId="productId">
				<logic:notEmpty property="dropDownProductRecoList" name="DistRecoBean">
				<bean:define id="productRecoList" name="DistRecoBean" 	property="dropDownProductRecoList" />
				<html:option value="-1">--select--</html:option>
				<html:options labelProperty="productName" property="productId" 	collection="productRecoList" />
				</logic:notEmpty>
			</html:select>
			
			</td>
			</tr>
			<tr>
			<td>Stock Type :</td>
			<td>
				<span id="showswapId">
				<select id="stockType" name ="stockType" onchange="disableUpload()" >
					<option value="-1">--select--</option>
					<option value="1">Serialized Stock</option>
					<option value="2">Defective Stock</option></select>
				</span>	
			</td>
			</tr>
			<tr>
			<td>Select File :</td>
			<td>
				<input type="file" id="uploadedFile" name="uploadedFile" disabled="disabled">
				<br>
				<input type="button" value="Upload" class="button" id="upload" name="upload"
				onclick="return uploadExcelcheck(5,document.getElementById('stockType').value,'buttonname',document.forms[0].productsLists.value);" disabled="disabled"/>
				<input type="button" value="Confirm First" class="button" onclick="return confirmFirst(document.getElementById('stockType').value,document.forms[0].productsLists.value);"  width="86px;"/> 
			<html:hidden property="swapProductList" name="DistRecoBean" styleId="swapproductId"></html:hidden>
			</td>
			</tr>
	</table>
	
	</fieldset>
</div>
</logic:equal>
</td>
	</tr>
			</table>
	<!-- End of Adding by RAM -->
</html:form>

</body>
</html:html>
