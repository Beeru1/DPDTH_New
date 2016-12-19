<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t"%>
<%
System.out.println("************************* START OF DISTRECOTABPARTTWO.jsp***********************************");
 %>
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
<body>
<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
    String rowMidLight = "#e6e6e6";
    
%>

			<DIV id=DIV_ACTIVATION style="DISPLAY: none">
				<DIV style="height: 310px; width: 100%; overflow: auto; visibility: visible;z-index:1; left: 133px; top: 250px;">
					<table width="100%" align="left" border="1" cellpadding="0" cellspacing="0" id="tableActvn"
					style="border-collapse: collapse;">
						<thead>
							<tr class="noScroll">
								<TD bgcolor="#CD0504" class="width104 height19 pLeft5"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Type</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Product Name</span></TD>
							<TD bgcolor="#851717" class="width104 height19 pLeft5" title="Total Open"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Total Open</span></TD>
							<TD bgcolor="#851717" class="width104 height19 pLeft5" title="Total Received"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Total Received</span></TD>
							<TD bgcolor="#851717" class="width104 height19 pLeft5" title="Total Returned"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5"> Total Returned </span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Serialized and Non Serialized Activation "
								align="center"><FONT color="white"><span 
								class="white10heading mLeft5 pRight5 mTop5">Activation </span></TD>
							<!--<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Non Serialized Activation "
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Non Serialized Activation </span></TD>
							--><TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Inventory Change"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Inventory <br/> Change</span></TD>
							<TD bgcolor="#851717" class="width104 height19 pLeft5" title="Adjustment"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Adjustment</span></TD>
							<TD bgcolor="#851717" class="width104 height19 pLeft5" title="Total Closing"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Total Closing</span></TD>
							</tr>
						</thead>
						
						<tbody>
							<logic:empty name="DistRecoBean" property="productRecoList">
								<TR>
									<TD class="text" align="center" colspan="8">No record available</TD>
								</TR>			
							</logic:empty>
							
							<logic:notEmpty name="DistRecoBean" property="productRecoList">
								<logic:iterate name="DistRecoBean" id="productRecoList" indexId="i" property="productRecoList">
									<TR  BGCOLOR='<%=((i.intValue()+1) % 3==0 ? rowMidLight : ((i.intValue()) % 3==0 ? rowDark : rowLight)) %>'>
										
									<TD align="center" class="text">
											<bean:write name="productRecoList" property="dataTypa"/>					
										</TD>
										<TD align="center" class="text">
											<bean:write name="productRecoList" property="productName"/>					
											<input type="hidden" value='<bean:write name="productRecoList" property="productId"/>'/>
										</TD>
										<TD align="center" class="text">
										<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
										<div value='<bean:write name="productRecoList" property="openTotal"/>'>
													<bean:write name="productRecoList" property="openTotal"/>		
										</div>
										</logic:notEqual>
										<logic:equal name="productRecoList" property="dataTypa" value="Variance">
											<div value='<bean:write name="productRecoList" property="openTotal"/>'>
												</div>
										<script type="text/javascript">
 												getSummVariance('<%=(i.intValue()+1)%>',2,'tableActvn');
										</script>
										</logic:equal>
										
										</TD>
										<TD align="center" class="text">
										<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
										<div value='<bean:write name="productRecoList" property="receivedTotal"/>'>
													<bean:write name="productRecoList" property="receivedTotal"/>		
										</div>
										</logic:notEqual>
										<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="receivedTotal"/>'>
												</div>
											<script type="text/javascript">
 												getSummVariance('<%=(i.intValue()+1)%>',3,'tableActvn');
											</script>
										</logic:equal>
										</TD>
										
										<TD align="center" class="text">
										<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
										<div value='<bean:write name="productRecoList" property="returnedTotal"/>'>
													<bean:write name="productRecoList" property="returnedTotal"/>		
										</div>
										</logic:notEqual>
										<logic:equal name="productRecoList" property="dataTypa" value="Variance">
											<div value='<bean:write name="productRecoList" property="returnedTotal"/>'>
												</div>
													<script type="text/javascript">
 														getSummVariance('<%=(i.intValue()+1)%>',4,'tableActvn');
												</script>
										</logic:equal>
										
										</TD>
										<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<logic:equal name="productRecoList" property="dataTypa" value="System Generated">
													<div value='<bean:write name="productRecoList" property="activationTotal"/>'>
														<bean:write name="productRecoList" property="activationTotal"/> 
														(<bean:write name="productRecoList" property="serialisedActivation"/>+<bean:write name="productRecoList" property="nonSerialisedActivation"/>)
													</div>
												</logic:equal>
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<div value='<bean:write name="productRecoList" property="serialisedActivation"/>'>
														<bean:write name="productRecoList" property="serialisedActivation"/> 
													</div>
												</logic:equal>
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
													<div value='<bean:write name="productRecoList" property="serialisedActivation"/>'>
												</div>
												<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',5,'tableActvn');
												</script>
												</logic:equal>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
											<logic:equal name="productRecoList" property="dataTypa" value="Variance">
													<div value='<bean:write name="productRecoList" property="serialisedActivation"/>'>
												</div>
												<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',5,'tableActvn');
												</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
												<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
											<logic:equal name="productRecoList" property="productType" value="0">
														<input type="text" id="serialisedActivation" name="serialisedActivation" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="activationTotal"/>' onchange="setVariance('<%=(i.intValue()+1)%>',5,'tableActvn')" disabled="true"/>	
													</logic:equal>
													<logic:equal name="productRecoList" property="productType" value="1">
														<input type="text" id="serialisedActivation" name="serialisedActivation" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="serialisedActivation"/>' onchange="setVariance('<%=(i.intValue()+1)%>',5,'tableActvn'); getTotAct('<%=(i.intValue()+1)%>');"  disabled="true"/>															
													</logic:equal>
											</logic:equal>
											<logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
													<logic:equal name="productRecoList" property="productType" value="0">
														<input type="text" id="serialisedActivation" name="serialisedActivation" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="activationTotal"/>' onchange="setVariance('<%=(i.intValue()+1)%>',5,'tableActvn')" disabled="true"/>	
													</logic:equal>
													<logic:equal name="productRecoList" property="productType" value="1">
														<input type="text" id="serialisedActivation" name="serialisedActivation" style="width: 50px" value='<bean:write name="productRecoList" property="serialisedActivation"/>' onchange="setVariance('<%=(i.intValue()+1)%>',5,'tableActvn'); getTotAct('<%=(i.intValue()+1)%>');"/>															
													</logic:equal>
											</logic:notEqual>
											</logic:notEqual>
											</logic:equal>
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<logic:notEqual name="productRecoList" property="serialisedActivation" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',4,1,'StockOf'+'<bean:write name="productRecoList" property="productName"/>'+'Activation');"/>	
												</logic:notEqual>
												</logic:equal>	
												</logic:equal>	
												<!-- end adding by ram -->				
										</TD>
										<!--<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="nonSerialisedActivation" value="">
												<div value='<bean:write name="productRecoList" property="nonSerialisedActivation"/>'>
													<bean:write name="productRecoList" property="nonSerialisedActivation"/>		
													</div>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="nonSerialisedActivation" value="">
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
													<input type="text" id="nonSerialisedActivation" name="nonSerialisedActivation" style="width: 50px" readonly="true"/>	
												<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',3,'tableActvn');
												</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
													<input type="text" id="nonSerialisedActivation" name="nonSerialisedActivation" style="width: 50px" onchange="setVariance('<%=(i.intValue()+1)%>',3,'tableActvn')"/>	
												</logic:notEqual>
											</logic:equal>				
										</TD>
										--><TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<div value='<bean:write name="productRecoList" property="inventoryChange"/>'>
													<bean:write name="productRecoList" property="inventoryChange"/>		
													</div>
													<logic:equal name="productRecoList" property="dataTypa" value="Variance">
													<div value='<bean:write name="productRecoList" property="inventoryChange"/>'>
												</div>
										<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',6,'tableActvn');
												</script>
												</logic:equal>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
													<div value='<bean:write name="productRecoList" property="inventoryChange"/>'>
												</div>
										<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',6,'tableActvn');
												</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
													<div value='<bean:write name="productRecoList" property="inventoryChange"/>'>
													<bean:write name="productRecoList" property="inventoryChange"/>		
													</div>
													<!--  <input type="text" id="inventoryChange" name="inventoryChange" style="width: 50px" value='<bean:write name="productRecoList" property="inventoryChange"/>'  readonly="true" onchange="setVariance('<%=(i.intValue()+1)%>',6,'tableActvn'); getTotInvChng('<%=(i.intValue()+1)%>');"/>	-->
													
												</logic:notEqual>
											</logic:equal>	
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<logic:notEqual name="productRecoList" property="showDetailsClosingINVCHG" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',4,2,'StockOf'+'<bean:write name="productRecoList" property="productName"/>'+'InventoryChange');"/>	
												</logic:notEqual>
												</logic:equal>	
												</logic:equal>	
												<!-- end adding by ram -->			
										</TD>
										
										
										<TD align="center" class="text">
										<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
										<div value='<bean:write name="productRecoList" property="adjustmentTotal"/>'>
													<bean:write name="productRecoList" property="adjustmentTotal"/>		
										</div>
										</logic:notEqual>
										<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="adjustmentTotal"/>'>
												</div>
											<script type="text/javascript">
 														getSummVariance('<%=(i.intValue()+1)%>',7,'tableActvn');
												</script>
										</logic:equal>
										<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<logic:notEqual name="productRecoList" property="showDetailsClosingADJTOL" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',4,3 ,'StockOf'+'<bean:write name="productRecoList" property="productName"/>'+'TotalAdjustment');"/>	
												</logic:notEqual>
												</logic:equal>	
												</logic:equal>	
												<!-- end adding by ram -->
										</TD>
										<TD align="center" class="text">
										<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
										<div value='<bean:write name="productRecoList" property="closingTotal"/>'>
													<bean:write name="productRecoList" property="closingTotal"/>		
										</div>
										</logic:notEqual>
										<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="closingTotal"/>'>
												</div>
											<script type="text/javascript">
 														getSummVariance('<%=(i.intValue()+1)%>',8,'tableActvn');
												</script>
										</logic:equal>
										</TD>
										
										
										
										
									</TR>
								</logic:iterate>
							</logic:notEmpty>
						</tbody>
					</table>
				</DIV>
			</DIV>
			<DIV id=DIV_CLOSING style="DISPLAY: none">
				<DIV style="height: 310px; width: 100%; overflow: auto; visibility: visible;z-index:1; left: 133px; top: 250px;">
					<table width="100%" align="left" border="1" cellpadding="0" cellspacing="0" id="tableClosing"
					style="border-collapse: collapse;">
						<thead>
							<tr class="noScroll">
								<TD bgcolor="#CD0504" class="width104 height19 pLeft5"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Type</span></FONT></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Product Name</span></TD>
								
							<TD bgcolor="#851717" class="width104 height19 pLeft5" title="Total Open"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Total Open</span></TD>
							<TD bgcolor="#851717" class="width104 height19 pLeft5" title="Total Received"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Total Received</span></TD>
							<TD bgcolor="#851717" class="width104 height19 pLeft5" title="Total Returned"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Total Returned</span></TD>
							<TD bgcolor="#851717" class="width104 height19 pLeft5" title="Total Activation"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Total Activation</span></TD>
							<TD bgcolor="#851717" class="width104 height19 pLeft5" title="Inventory Change"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Inventory Change</span></FONT></TD>
							
							<TD bgcolor="#851717" class="width104 height19 pLeft5" title="Adjustment"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Adjustment</span></FONT></TD>
							
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Serialized Stock"
								align="center"><FONT color="white"><span 
								class="white10heading mLeft5 pRight5 mTop5">Serialized Stock</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Non Serialized Stock"
								align="center"><FONT color="white"><span 
								class="white10heading mLeft5 pRight5 mTop5">Non Serialized Stock</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Defective Stock"
								align="center"><FONT color="white"><span 
								class="white10heading mLeft5 pRight5 mTop5">Defective Stock</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Upgrade Stock"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Upgrade Stock</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Churn Stock"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Churn Stock</span></TD>
							<TD bgcolor="#CD0504" title="Pending PO(Closing In-Transit)" class="width104 height19 pLeft5"
								align="center" ><FONT color="white"><span  
								class="white10heading mLeft5 pRight5 mTop5">Pending PO
								</span></TD>
							<TD bgcolor="#CD0504" title="Pending DC(Closing In-Transit)" class="width104 height19 pLeft5"
								align="center"><FONT color="white"><span  
								class="white10heading mLeft5 pRight5 mTop5">Pending DC</span></TD>
							
							
								
							
							</tr>
						</thead>
						
						<tbody>
							<logic:empty name="DistRecoBean" property="productRecoList">
								<TR>
									<TD class="text" align="center" colspan="8">No record available</TD>
								</TR>			
							</logic:empty>

							
							<logic:notEmpty name="DistRecoBean" property="productRecoList">
								<logic:iterate name="DistRecoBean" id="productRecoList" indexId="i" property="productRecoList">
									<TR  BGCOLOR='<%=((i.intValue()+1) % 3==0 ? rowMidLight : ((i.intValue()) % 3==0 ? rowDark : rowLight)) %>'>
										<TD align="center" class="text">
											<bean:write name="productRecoList" property="dataTypa"/>					
										</TD>
										<TD align="center" class="text">
											<bean:write name="productRecoList" property="productName"/>					
											<input type="hidden" value='<bean:write name="productRecoList" property="productId"/>'/>
										</TD>
										<TD align="center" class="text">
										<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
										<div value='<bean:write name="productRecoList" property="openTotal"/>'>
													<bean:write name="productRecoList" property="openTotal"/>		
										</div>
										</logic:notEqual>
										<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="openTotal"/>'>
												</div>	<script type="text/javascript">
 														getSummVariance('<%=(i.intValue()+1)%>',2,'tableClosing');
												</script>
										</logic:equal>
										</TD>
										<TD align="center" class="text">
										<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
										<div value='<bean:write name="productRecoList" property="receivedTotal"/>'>
													<bean:write name="productRecoList" property="receivedTotal"/>		
										</div>
										</logic:notEqual>
										<logic:equal name="productRecoList" property="dataTypa" value="Variance">
											<div value='<bean:write name="productRecoList" property="receivedTotal"/>'>
												</div>
												<script type="text/javascript">
 												getSummVariance('<%=(i.intValue()+1)%>',3,'tableClosing');
										</script>
										</logic:equal>
										
										</TD>
										<TD align="center" class="text">
										<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
										<div value='<bean:write name="productRecoList" property="returnedTotal"/>'>
													<bean:write name="productRecoList" property="returnedTotal"/>		
										</div>
										</logic:notEqual>
										<logic:equal name="productRecoList" property="dataTypa" value="Variance">
											<div value='<bean:write name="productRecoList" property="returnedTotal"/>'>
												</div>
											<script type="text/javascript">
 												getSummVariance('<%=(i.intValue()+1)%>',4,'tableClosing');
											</script>
										</logic:equal>
										</TD>
										
										<TD align="center" class="text">
										<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
										<div value='<bean:write name="productRecoList" property="activationTotal"/>'>
													<bean:write name="productRecoList" property="activationTotal"/>		
										</div>
										</logic:notEqual>
										<logic:equal name="productRecoList" property="dataTypa" value="Variance">
										<div value='<bean:write name="productRecoList" property="activationTotal"/>'>
												</div>
														<script type="text/javascript">
 														getSummVariance('<%=(i.intValue()+1)%>',5,'tableClosing');
												</script>
										</logic:equal>
										<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<logic:notEqual name="productRecoList" property="showDetailsClosingACTTOL" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',4,1,'StockOf'+'<bean:write name="productRecoList" property="productName"/>'+'ActivationTotal');"/>	
												</logic:notEqual>
												</logic:equal>	
												</logic:equal>	
												<!-- end adding by ram -->
										</TD>
										<TD align="center" class="text">
										<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
										<div value='<bean:write name="productRecoList" property="invChngTotal"/>'>
													<bean:write name="productRecoList" property="invChngTotal"/>		
										</div>
										</logic:notEqual>
										<logic:equal name="productRecoList" property="dataTypa" value="Variance">
											<div value='<bean:write name="productRecoList" property="invChngTotal"/>'>
												</div>
												<script type="text/javascript">
 														getSummVariance('<%=(i.intValue()+1)%>',6,'tableClosing');
												</script>
										</logic:equal>
										<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<logic:notEqual name="productRecoList" property="showDetailsClosingINVCHG" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',4,2 ,'StockOf'+'<bean:write name="productRecoList" property="productName"/>'+'InventoryChange');"/>	
												</logic:notEqual>
												</logic:equal>	
												</logic:equal>	
												<!-- end adding by ram -->
										</TD>
										
										<TD align="center" class="text">
										<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
										<div value='<bean:write name="productRecoList" property="adjustmentTotal"/>'>
													<bean:write name="productRecoList" property="adjustmentTotal"/>		
										</div>
										</logic:notEqual>
										<logic:equal name="productRecoList" property="dataTypa" value="Variance">
											<div value='<bean:write name="productRecoList" property="adjustmentTotal"/>'>
												</div>
												<script type="text/javascript">
 														getSummVariance('<%=(i.intValue()+1)%>',7,'tableClosing');
												</script>
										</logic:equal>
										<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<logic:notEqual name="productRecoList" property="showDetailsClosingADJTOL" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',4,3 ,'StockOf'+'<bean:write name="productRecoList" property="productName"/>'+'AdjustmentTotal');"/>	
												</logic:notEqual>
												</logic:equal>	
												</logic:equal>	
												<!-- end adding by ram -->
										</TD>
										<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<div value='<bean:write name="productRecoList" property="serialisedClosingStock"/>'>
												<bean:write name="productRecoList" property="serialisedClosingStock"/>
												</div>
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="serialisedClosingStock"/>'>
												</div>
												<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',8,'tableClosing');
													</script>
												</logic:equal>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="serialisedClosingStock"/>'>
												</div>
												<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',8,'tableClosing');
													</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
												<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
											<input type="text" id="serialisedClosingStock" name="serialisedClosingStock" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="serialisedClosingStock"/>' onchange="setVariance('<%=(i.intValue()+1)%>',8,'tableClosing'); getTotClos('<%=(i.intValue()+1)%>');" disabled="true"/>	
											</logic:equal>
											<logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
													<input type="text" id="serialisedClosingStock" name="serialisedClosingStock" style="width: 50px" value='<bean:write name="productRecoList" property="serialisedClosingStock"/>' onchange="setVariance('<%=(i.intValue()+1)%>',8,'tableClosing'); getTotClos('<%=(i.intValue()+1)%>');" />	
												</logic:notEqual>
												</logic:notEqual>
											</logic:equal>	
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
												<div value='<bean:write name="productRecoList" property="serialisedClosingStock"/>'>
												</div>
													<logic:notEqual name="productRecoList" property="showDetailsClosingser" value="0">
													<input type="button" value="Details" style="width: 50px; background-color :#006600; color=#FFFFFF" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',5,1,'<bean:write name="productRecoList" property="productName"/>');"/>	
													</logic:notEqual>
													</logic:equal>	
												</logic:equal>	
												<!-- end adding by ram -->			
										</TD>
										<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
											<div value='<bean:write name="productRecoList" property="nonSerialisedClosingStock"/>'>
													<bean:write name="productRecoList" property="nonSerialisedClosingStock"/>		
											</div>
											<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="nonSerialisedClosingStock"/>'>
												</div>
											<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',9,'tableClosing');
												</script>
												</logic:equal>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="nonSerialisedClosingStock"/>'>
												</div>
											<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',9,'tableClosing');
												</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
												<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
											<input type="text" id="nonSerialisedClosingStock" name="nonSerialisedClosingStock" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="nonSerialisedClosingStock"/>' onchange="setVariance('<%=(i.intValue()+1)%>',9,'tableClosing'); getTotClos('<%=(i.intValue()+1)%>');" disabled="true"/>
											</logic:equal>
											<logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
													<input type="text" id="nonSerialisedClosingStock" name="nonSerialisedClosingStock" style="width: 50px" value='<bean:write name="productRecoList" property="nonSerialisedClosingStock"/>' onchange="setVariance('<%=(i.intValue()+1)%>',9,'tableClosing'); getTotClos('<%=(i.intValue()+1)%>');"/>
												</logic:notEqual>
												</logic:notEqual>
											</logic:equal>	
											<!-- adding by ram donwload not needed -->
												
												<!-- end adding by ram -->			
										</TD>
										<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<div value='<bean:write name="productRecoList" property="defectiveClosingStock"/>'>
												<bean:write name="productRecoList" property="defectiveClosingStock"/>		
												</div>
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
													<div value='<bean:write name="productRecoList" property="defectiveClosingStock"/>'>
												</div>
											<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',10,'tableClosing');
												</script>
												</logic:equal>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
													<div value='<bean:write name="productRecoList" property="defectiveClosingStock"/>'>
												</div>
											<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',10,'tableClosing');
												</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
												<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
											<input type="text" id="defectiveClosingStock" name="defectiveClosingStock" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="defectiveClosingStock"/>' onchange="setVariance('<%=(i.intValue()+1)%>',10,'tableClosing'); getTotClos('<%=(i.intValue()+1)%>');" disabled="true"/>	
											</logic:equal>
											<logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
													<input type="text" id="defectiveClosingStock" name="defectiveClosingStock" style="width: 50px" value='<bean:write name="productRecoList" property="defectiveClosingStock"/>' onchange="setVariance('<%=(i.intValue()+1)%>',10,'tableClosing'); getTotClos('<%=(i.intValue()+1)%>');"/>	
												</logic:notEqual>
												</logic:notEqual>
											</logic:equal>	
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
												<div value='<bean:write name="productRecoList" property="defectiveClosingStock"/>'>
												</div>
														<logic:notEqual name="productRecoList" property="showDetailsClosingdef" value="0">
													<input type="button" value="Details" style="width: 50px; background-color :#006600; color=#FFFFFF " onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',5,2,'<bean:write name="productRecoList" property="productName"/>');"/>	
												</logic:notEqual>
												</logic:equal>	
												</logic:equal>	
												<!-- end adding by ram -->			
										</TD>
										<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<div value='<bean:write name="productRecoList" property="upgradeClosingStock"/>'>
													<bean:write name="productRecoList" property="upgradeClosingStock"/>		
												</div>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="upgradeClosingStock"/>'>
												</div>
											<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',11,'tableClosing');
												</script>
												</logic:equal>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
											<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="upgradeClosingStock"/>'>
												</div>
											<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',11,'tableClosing');
												</script>
												</logic:equal>
											<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
											<logic:equal name="productRecoList" property="productType" value="0">
													<input type="text" id="upgradeClosingStock" name="upgradeClosingStock" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="upgradeClosingStock"/>' onchange="setVariance('<%=(i.intValue()+1)%>',11,'tableClosing'); getTotClos('<%=(i.intValue()+1)%>');" disabled="true"/>	
												</logic:equal>
												<logic:equal name="productRecoList" property="productType" value="1">
													<input type="text" id="upgradeClosingStock" name="upgradeClosingStock" style="width: 50px; background-color : #C0C0C0;" onchange="setVariance('<%=(i.intValue()+1)%>',11,'tableClosing')" disabled="true"/>	
												</logic:equal>
											</logic:equal>
											<logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="productType" value="0">
													<input type="text" id="upgradeClosingStock" name="upgradeClosingStock" style="width: 50px" value='<bean:write name="productRecoList" property="upgradeClosingStock"/>' onchange="setVariance('<%=(i.intValue()+1)%>',11,'tableClosing'); getTotClos('<%=(i.intValue()+1)%>');"/>	
												</logic:equal>
												<logic:equal name="productRecoList" property="productType" value="1">
													<input type="text" id="upgradeClosingStock" name="upgradeClosingStock" style="width: 50px; background-color : #C0C0C0;" onchange="setVariance('<%=(i.intValue()+1)%>',11,'tableClosing')" disabled="true"/>	
												</logic:equal>
											</logic:notEqual>
											</logic:notEqual>
											</logic:equal>	
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
												<div value='<bean:write name="productRecoList" property="upgradeClosingStock"/>'>
												</div>
												<logic:equal name="productRecoList" property="productType" value="0">
													<logic:notEqual name="productRecoList" property="showDetailsClosingup" value="0">
													<input type="button" value="Details" style="width: 50px; background-color :#006600; color=#FFFFFF" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',5,3,'<bean:write name="productRecoList" property="productName"/>');"/>	
												</logic:notEqual>
												</logic:equal>
												</logic:equal>	
												</logic:equal>	
												<!-- end adding by ram -->			
										</TD>
										<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<div value='<bean:write name="productRecoList" property="churnClosingStock"/>'>
													<bean:write name="productRecoList" property="churnClosingStock"/>		
												</div>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="churnClosingStock"/>'>
												</div>
											<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',12,'tableClosing');
												</script>
												</logic:equal>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="churnClosingStock"/>'>
												</div>
											<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',12,'tableClosing');
												</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
												<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
											<logic:equal name="productRecoList" property="productType" value="0">
														<input type="text" id="churnClosingStock" name="churnClosingStock" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="churnClosingStock"/>' onchange="setVariance('<%=(i.intValue()+1)%>',12,'tableClosing'); getTotClos('<%=(i.intValue()+1)%>');" disabled="true"/>	
													</logic:equal>
													<logic:equal name="productRecoList" property="productType" value="1">
														<input type="text" id="churnClosingStock" name="churnClosingStock" style="width: 50px; background-color : #C0C0C0;" onchange="setVariance('<%=(i.intValue()+1)%>',12,'tableClosing')" disabled="true"/>	
													</logic:equal>
											</logic:equal>
											<logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
													<logic:equal name="productRecoList" property="productType" value="0">
														<input type="text" id="churnClosingStock" name="churnClosingStock" style="width: 50px" value='<bean:write name="productRecoList" property="churnClosingStock"/>' onchange="setVariance('<%=(i.intValue()+1)%>',12,'tableClosing'); getTotClos('<%=(i.intValue()+1)%>');"/>	
													</logic:equal>
													<logic:equal name="productRecoList" property="productType" value="1">
														<input type="text" id="churnClosingStock" name="churnClosingStock" style="width: 50px; background-color : #C0C0C0;" onchange="setVariance('<%=(i.intValue()+1)%>',12,'tableClosing')" disabled="true"/>	
													</logic:equal>
												</logic:notEqual>
												</logic:notEqual>
											</logic:equal>
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
												<div value='<bean:write name="productRecoList" property="churnClosingStock"/>'>
												</div>
												<logic:equal name="productRecoList" property="productType" value="0">
													<logic:notEqual name="productRecoList" property="showDetailsClosingchu" value="0">
													<input type="button" value="Details" style="width: 50px; background-color :#006600; color=#FFFFFF;" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',5,4,'<bean:write name="productRecoList" property="productName"/>');"/>
												</logic:notEqual>
												</logic:equal>
												</logic:equal>	
												</logic:equal>	
												<!-- end adding by ram -->				
										</TD>
										<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<div value='<bean:write name="productRecoList" property="pendingPOIntransit"/>'>
													<bean:write name="productRecoList" property="pendingPOIntransit"/>		
													</div>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="pendingPOIntransit"/>'>
												</div>
											<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',13,'tableClosing');
												</script>
												</logic:equal>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="pendingPOIntransit"/>'>
												</div>
											<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',13,'tableClosing');
												</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
												<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
											<input type="text" id="pendingPOIntransit" name="pendingPOIntransit" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="pendingPOIntransit"/>' onchange="setVariance('<%=(i.intValue()+1)%>',13,'tableClosing'); getTotClos('<%=(i.intValue()+1)%>');" disabled="true"/>	
											</logic:equal>
											<logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
													<input type="text" id="pendingPOIntransit" name="pendingPOIntransit" style="width: 50px" value='<bean:write name="productRecoList" property="pendingPOIntransit"/>' onchange="setVariance('<%=(i.intValue()+1)%>',13,'tableClosing'); getTotClos('<%=(i.intValue()+1)%>');"/>	
												</logic:notEqual>
												</logic:notEqual>
											</logic:equal>
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<logic:notEqual name="productRecoList" property="showDetailsClosingPPO" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',5,5,'<bean:write name="productRecoList" property="productName"/>');"/>	
												</logic:notEqual>
												</logic:equal>	
												</logic:equal>	
												<!-- end adding by ram -->				
										</TD>
										<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<div value='<bean:write name="productRecoList" property="pendingDCIntransit"/>'>
													<bean:write name="productRecoList" property="pendingDCIntransit"/>		
													</div>
													<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="pendingDCIntransit"/>'>
												</div>
											<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',14,'tableClosing');
												</script>
												</logic:equal>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="pendingDCIntransit"/>'>
												</div>
											<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',14,'tableClosing');
												</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
												<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
											<input type="text" id="pendingDCIntransit" name="pendingDCIntransit" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="pendingDCIntransit"/>' onchange="setVariance('<%=(i.intValue()+1)%>',14,'tableClosing'); getTotClos('<%=(i.intValue()+1)%>');" disabled="true"/>	
											</logic:equal>
											<logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
														<input type="text" id="pendingDCIntransit" name="pendingDCIntransit" style="width: 50px" value='<bean:write name="productRecoList" property="pendingDCIntransit"/>' onchange="setVariance('<%=(i.intValue()+1)%>',14,'tableClosing'); getTotClos('<%=(i.intValue()+1)%>');"/>	
												</logic:notEqual>
												</logic:notEqual>
											</logic:equal>	
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<logic:notEqual name="productRecoList" property="showDetailsClosingPDC" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',5,6,'<bean:write name="productRecoList" property="productName"/>');"/>	
												</logic:notEqual>
												</logic:equal>	
												</logic:equal>	
												<!-- end adding by ram -->			
										</TD>
									</TR>
								</logic:iterate>
							</logic:notEmpty>
						</tbody>
					</table>
				</DIV>
			</DIV>
			<DIV id=DIV_SUMMARY style="DISPLAY: none">
				<DIV style="height: 310px; width: 100%; overflow: auto; visibility: visible;z-index:1; left: 133px; top: 250px;">
					<table width="100%" align="left" border="1" cellpadding="0" cellspacing="0" id="tableSummry"
					style="border-collapse: collapse;">
						<thead>
							<tr class="noScroll">
								<TD bgcolor="#CD0504" class="width104 height19 pLeft5"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Type</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Product Name</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Total Open"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Total Open</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Total Received"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Total Received</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Total Returned"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Total Returned</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Total Activation"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Total Activation</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Inventory Change"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Inventory Change</span></TD>
							
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Adjustment"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Adjustment</span></FONT></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Total Closing"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Total Closing</span></FONT></TD>
							
							</tr>
						</thead>
						
						<tbody>
							<logic:empty name="DistRecoBean" property="productRecoList">
								<TR>
									<TD class="text" align="center" colspan="8">No record available</TD>
								</TR>			
							</logic:empty>
							
							<logic:notEmpty name="DistRecoBean" property="productRecoList">
								<logic:iterate name="DistRecoBean" id="productRecoList" indexId="i" property="productRecoList">
									<TR  BGCOLOR='<%=((i.intValue()+1) % 3==0 ? rowMidLight : ((i.intValue()) % 3==0 ? rowDark : rowLight)) %>'>
										
									<TD align="center" class="text">
											<bean:write name="productRecoList" property="dataTypa"/>					
										</TD>
										<TD align="center" class="text">
											<bean:write name="productRecoList" property="productName"/>					
											<input type="hidden" value='<bean:write name="productRecoList" property="productId"/>'/>
										</TD>
										
										<TD align="center" class="text">
										<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
										<div value='<bean:write name="productRecoList" property="openTotal"/>'>
													<bean:write name="productRecoList" property="openTotal"/>		
										</div>
										</logic:notEqual>
										<logic:equal name="productRecoList" property="dataTypa" value="Variance">
											<div value='<bean:write name="productRecoList" property="openTotal"/>'>
												</div>
													<script type="text/javascript">
 														getSummVariance('<%=(i.intValue()+1)%>',2,'tableSummry');
												</script>
										</logic:equal>
										</TD>
										<TD align="center" class="text">
										<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
										<div value='<bean:write name="productRecoList" property="receivedTotal"/>'>
													<bean:write name="productRecoList" property="receivedTotal"/>		
										</div>
										</logic:notEqual>
										<logic:equal name="productRecoList" property="dataTypa" value="Variance">
											<div value='<bean:write name="productRecoList" property="receivedTotal"/>'>
												</div>
											<script type="text/javascript">
 												getSummVariance('<%=(i.intValue()+1)%>',3,'tableSummry');
										</script>
										</logic:equal>
										
										</TD>
										<TD align="center" class="text">
										<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
										<div value='<bean:write name="productRecoList" property="returnedTotal"/>'>
													<bean:write name="productRecoList" property="returnedTotal"/>		
										</div>
										</logic:notEqual>
										<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="returnedTotal"/>'>
												</div>
											<script type="text/javascript">
 												getSummVariance('<%=(i.intValue()+1)%>',4,'tableSummry');
											</script>
										</logic:equal>
										</TD>
										
										<TD align="center" class="text">
										<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
										<div value='<bean:write name="productRecoList" property="activationTotal"/>'>
													<bean:write name="productRecoList" property="activationTotal"/>		
										</div>
										</logic:notEqual>
										<logic:equal name="productRecoList" property="dataTypa" value="Variance">
											<div value='<bean:write name="productRecoList" property="activationTotal"/>'>
												</div>
														<script type="text/javascript">
 														getSummVariance('<%=(i.intValue()+1)%>',5,'tableSummry');
												</script>
										</logic:equal>
										
										</TD>
										<TD align="center" class="text">
										<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
										<div value='<bean:write name="productRecoList" property="invChngTotal"/>'>
													<bean:write name="productRecoList" property="invChngTotal"/>		
										</div>
										</logic:notEqual>
										<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="invChngTotal"/>'>
												</div>
												<script type="text/javascript">
 														getSummVariance('<%=(i.intValue()+1)%>',6,'tableSummry');
												</script>
										</logic:equal>
										</TD>
										
										<TD align="center" class="text">
										<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
										<div value='<bean:write name="productRecoList" property="adjustmentTotal"/>'>
													<bean:write name="productRecoList" property="adjustmentTotal"/>		
										</div>
										</logic:notEqual>
										<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="adjustmentTotal"/>'>
												</div>
												<script type="text/javascript">
 														getSummVariance('<%=(i.intValue()+1)%>',7,'tableSummry');
												</script>
										</logic:equal>
										
										</TD>
										
									<TD align="center" class="text">
										<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
										<div value='<bean:write name="productRecoList" property="closingTotal"/>'>
													<bean:write name="productRecoList" property="closingTotal"/>		
										</div>
										</logic:notEqual>
										<logic:equal name="productRecoList" property="dataTypa" value="Variance">
											<div value='<bean:write name="productRecoList" property="closingTotal"/>'>
												</div>
													<script type="text/javascript">
 														getSummVariance('<%=(i.intValue()+1)%>',8,'tableSummry');
												</script>
										</logic:equal>
									</TD>
									
									
									
									<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
											<TD align="left" class="text">
											<logic:equal name="productRecoList" property="isValidToEnter" value="0">
											Remarks:	<br> <html:textarea name="productRecoList" property="remarks" value=""  disabled="true"/>
											</logic:equal>
											<logic:equal name="productRecoList" property="isValidToEnter" value="1">
											Remarks:  <br>	<html:textarea name="productRecoList" property="remarks" value="" onkeypress="return textareaMaxlength();"/>
											</logic:equal>
										</TD>
										</logic:equal>
										
									
										<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
											<logic:equal name="productRecoList" property="isSubmitted" value="INITIATE">
											<TD align="center" class="text">
											<logic:equal name="productRecoList" property="isValidToEnter" value="0">
												<input type="button" onclick="submitData('<%=(i.intValue()+1)%>','<bean:write name="productRecoList" property="productId"/>')" value="Submit" disabled="true"/>
											</logic:equal>
											<logic:equal name="productRecoList" property="isValidToEnter" value="1">
												<input type="button" onclick="submitData('<%=(i.intValue()+1)%>','<bean:write name="productRecoList" property="productId"/>')"  value="Submit"  />
											</logic:equal>
										</TD>
										</logic:equal>
									</logic:equal>
									</TR>
								</logic:iterate>
							</logic:notEmpty>
						</tbody>
					</table>
				</DIV>
<%
System.out.println("************************* END OF DISTRECOTABPARTTWO.jsp***********************************");
 %>
</body>
</html:html>
