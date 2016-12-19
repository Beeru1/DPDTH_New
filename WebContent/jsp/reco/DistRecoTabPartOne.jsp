<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t"%>
<%
System.out.println("************************* START OF DISTRECOTABPARTONE.jsp***********************************");
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
			<DIV id=DIV_OPEN style="DISPLAY: block">
				<DIV style="height: 310px; width: 100%; overflow: auto; visibility: visible;z-index:1; left: 133px; top: 250px;">
					<table width="100%" align="left" border="1" cellpadding="0" cellspacing="0" id="tableOpen"
					style="border-collapse: collapse;">
						<thead>
							<tr class="noScroll">
								<TD bgcolor="#CD0504" class="width104 height19 pLeft5"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Type</span></FONT></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Product Name</span></TD>
							<TD bgcolor="#CD0504" title="Pending PO(Opening In-Transit)" class="width104 height19 pLeft5"
								align="center" ><FONT color="white"><span  
								class="white10heading mLeft5 pRight5 mTop5">Pending PO
								</span></TD>
							<TD bgcolor="#CD0504" title="Pending DC(Opening In-Transit)" class="width104 height19 pLeft5"
								align="center"><FONT color="white"><span  
								class="white10heading mLeft5 pRight5 mTop5">Pending DC</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Serialized Open Stock"
								align="center"><FONT color="white"><span 
								class="white10heading mLeft5 pRight5 mTop5">Serialized Stock</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Non Serialized Open Stock"
								align="center"><FONT color="white"><span 
								class="white10heading mLeft5 pRight5 mTop5">Non Serialized Stock</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Defective Open Stock"
								align="center"><FONT color="white"><span 
								class="white10heading mLeft5 pRight5 mTop5">Defective Stock</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Upgrade Open Stock"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Upgrade Stock</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Churn Open Stock"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Churn Stock</span></TD>
								
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
								class="white10heading mLeft5 pRight5 mTop5">Inventory Change</span></TD>
							
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
									<TR BGCOLOR='<%=((i.intValue()+1) % 3==0 ? rowMidLight : ((i.intValue()) % 3==0 ? rowDark : rowLight)) %>'>
										<TD align="center" class="text">
											<bean:write name="productRecoList" property="dataTypa"/>					
										</TD>
										<TD align="center" class="text">
											<bean:write name="productRecoList" property="productName"/>					
											<input type="hidden" value='<bean:write name="productRecoList" property="productId"/>'/>
										</TD>
										<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<div value='<bean:write name="productRecoList" property="pendingPOIntransitOpen"/>'>
												<bean:write name="productRecoList" property="pendingPOIntransitOpen"/>
												</div>
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
													<div value='<bean:write name="productRecoList" property="pendingPOIntransitOpen"/>'>
													</div>
												<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',2,'tableOpen');
													</script>
												</logic:equal>
												
											</logic:notEqual>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
													<div value='<bean:write name="productRecoList" property="pendingPOIntransitOpen"/>'>
													</div>
												<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',2,'tableOpen');
													</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
											<input  type="text" id="pendingPOIntransitOpen" name="pendingPOIntransitOpen" style="width: 50px; background-color : #C0C0C0;" onchange="setVariance('<%=(i.intValue()+1)%>',2,'tableOpen'); getTotOpen('<%=(i.intValue()+1)%>');" disabled="true"
															value='<bean:write name="productRecoList" property="pendingPOIntransitOpen"/>'/>
											</logic:equal>
											<!-- adding by ram  -->	
											 <logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
													<input  type="text" id="pendingPOIntransitOpen" name="pendingPOIntransitOpen" style="width: 50px" onchange="setVariance('<%=(i.intValue()+1)%>',2,'tableOpen'); getTotOpen('<%=(i.intValue()+1)%>');" 
															value='<bean:write name="productRecoList" property="pendingPOIntransitOpen"/>'/>	
											</logic:notEqual>
											</logic:notEqual>
											</logic:equal>	
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<logic:notEqual name="productRecoList" property="pendingPOIntransitOpen" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',1,1 ,'OpeningStockOf'+'<bean:write name="productRecoList" property="productName"/>'+'PendingPO');"/>
												</logic:notEqual>
												</logic:equal>
												</logic:equal>	
												<!-- end adding by ram -->	
										</TD>
										<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
											<div value='<bean:write name="productRecoList" property="openingPendgDCIntrnsit"/>'>
													<bean:write name="productRecoList" property="openingPendgDCIntrnsit"/>		
											</div>
											<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="openingPendgDCIntrnsit"/>'>
												</div>
												<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',3,'tableOpen');
												</script>
												</logic:equal>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="openingPendgDCIntrnsit"/>'>
												</div>
												<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',3,'tableOpen');
												</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
												<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
											<input type="text" id="openingPendgDCIntrnsit" name="openingPendgDCIntrnsit" value='<bean:write name="productRecoList" property="openingPendgDCIntrnsit"/>' 
														style="width: 50px; background-color : #C0C0C0;" onchange="setVariance('<%=(i.intValue()+1)%>',3,'tableOpen'); getTotOpen('<%=(i.intValue()+1)%>');" disabled="true"/>	
											</logic:equal>
											<logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
											<!-- End of adding by ram  -->
													<input type="text" id="openingPendgDCIntrnsit" name="openingPendgDCIntrnsit" value='<bean:write name="productRecoList" property="openingPendgDCIntrnsit"/>' 
														style="width: 50px" onchange="setVariance('<%=(i.intValue()+1)%>',3,'tableOpen'); getTotOpen('<%=(i.intValue()+1)%>');"/>	
												</logic:notEqual>
												</logic:notEqual>
											</logic:equal>	
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<logic:notEqual name="productRecoList" property="openingPendgDCIntrnsit" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',1,2 ,'OpeningStockOf'+'<bean:write name="productRecoList" property="productName"/>'+'PendingDC');"/>	
												</logic:notEqual>
												</logic:equal>	
												</logic:equal>	
												<!-- end adding by ram -->			
										</TD>
										<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<div value='<bean:write name="productRecoList" property="serialisedOpenStock"/>'>
												<bean:write name="productRecoList" property="serialisedOpenStock"/>		
												</div>
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
													<div value='<bean:write name="productRecoList" property="serialisedOpenStock"/>'>
													</div>
													<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',4,'tableOpen');
													</script>
												</logic:equal>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
													<div value='<bean:write name="productRecoList" property="serialisedOpenStock"/>'>
													</div>
													<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',4,'tableOpen');
													</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
												<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
											<input type="text" id="serialisedOpenStock" name="serialisedOpenStock" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="serialisedOpenStock"/>' onchange="setVariance('<%=(i.intValue()+1)%>',4,'tableOpen'); getTotOpen('<%=(i.intValue()+1)%>');" disabled="true"/>	
											</logic:equal>
											<logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
													<input type="text" id="serialisedOpenStock" name="serialisedOpenStock" style="width: 50px" value='<bean:write name="productRecoList" property="serialisedOpenStock"/>' onchange="setVariance('<%=(i.intValue()+1)%>',4,'tableOpen'); getTotOpen('<%=(i.intValue()+1)%>');"/>	
											</logic:notEqual>
											</logic:notEqual>
												
											</logic:equal>	
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<logic:notEqual name="productRecoList" property="showDetailsOpeningser" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',1,3 ,'OpeningStockOf'+'<bean:write name="productRecoList" property="productName"/>'+'Serialised');"/>	
												</logic:notEqual>
												</logic:equal>	
												</logic:equal>	
												<!-- end adding by ram -->			
										</TD>
										<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<div value='<bean:write name="productRecoList" property="nonSerialisedOpenStock"/>'>
													<bean:write name="productRecoList" property="nonSerialisedOpenStock"/>		
												</div>
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
														<div value='<bean:write name="productRecoList" property="nonSerialisedOpenStock"/>'>
														</div>
														<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',5,'tableOpen');
												</script>
												</logic:equal>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
											<logic:equal name="productRecoList" property="dataTypa" value="Variance">
														<div value='<bean:write name="productRecoList" property="nonSerialisedOpenStock"/>'>
														</div>
														<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',5,'tableOpen');
												</script>
												</logic:equal>
											<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
											<input type="text" id="nonSerialisedOpenStock" name="nonSerialisedOpenStock" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="nonSerialisedOpenStock"/>'onchange="setVariance('<%=(i.intValue()+1)%>',5,'tableOpen'); getTotOpen('<%=(i.intValue()+1)%>');" disabled="true"/>	
											</logic:equal>
											<!-- ENd adding by ram  -->
											<logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
												<input type="text" id="nonSerialisedOpenStock" name="nonSerialisedOpenStock" style="width: 50px" value='<bean:write name="productRecoList" property="nonSerialisedOpenStock"/>'onchange="setVariance('<%=(i.intValue()+1)%>',5,'tableOpen'); getTotOpen('<%=(i.intValue()+1)%>');"/>	
											</logic:notEqual>
											</logic:notEqual>
											</logic:equal>	
											<!-- adding by ram  download not needed here-->
											<!-- end adding by ram -->			
										</TD>
										<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<div value='<bean:write name="productRecoList" property="defectiveOpenStock"/>'>
													<bean:write name="productRecoList" property="defectiveOpenStock"/>		
												</div>
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
													<div value='<bean:write name="productRecoList" property="defectiveOpenStock"/>'>
													</div>
													<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',6,'tableOpen');
												</script>
												</logic:equal>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
													<div value='<bean:write name="productRecoList" property="defectiveOpenStock"/>'>
													</div>
													<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',6,'tableOpen');
												</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
												<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<input type="text" id="defectiveOpenStock" name="defectiveOpenStock" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="defectiveOpenStock"/>' onchange="setVariance('<%=(i.intValue()+1)%>',6,'tableOpen'); getTotOpen('<%=(i.intValue()+1)%>');" disabled="true"/>	
											</logic:equal>
											<!-- end adding by ram  -->
											<logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
													<input type="text" id="defectiveOpenStock" name="defectiveOpenStock" style="width: 50px" value='<bean:write name="productRecoList" property="defectiveOpenStock"/>' onchange="setVariance('<%=(i.intValue()+1)%>',6,'tableOpen'); getTotOpen('<%=(i.intValue()+1)%>');"/>	
												</logic:notEqual>
												</logic:notEqual>
											</logic:equal>	
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<!-- added by vishwas on  17/04/2014 to hide download button-->
													<logic:notEqual name="productRecoList" property="showDetailsOpeningdef" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',1,4 ,'OpeningStockOf'+'<bean:write name="productRecoList" property="productName"/>'+'Defective');"/>	
												</logic:notEqual>
												</logic:equal>	
												</logic:equal>	
												<!-- end adding by ram -->			
										</TD>
										<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<div value='<bean:write name="productRecoList" property="upgradeOpenStock"/>'>
													<bean:write name="productRecoList" property="upgradeOpenStock"/>		
													</div>
													<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="upgradeOpenStock"/>'>
													</div>
													<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',7,'tableOpen');
												</script>
												</logic:equal>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="upgradeOpenStock"/>'>
													</div>
													<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',7,'tableOpen');
												</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
												<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
											<logic:equal name="productRecoList" property="productType" value="0">
														<input type="text" id="upgradeOpenStock" name="upgradeOpenStock" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="upgradeOpenStock"/>' onchange="setVariance('<%=(i.intValue()+1)%>',7,'tableOpen'); getTotOpen('<%=(i.intValue()+1)%>');" disabled="true"/>	
													</logic:equal>
													<logic:equal name="productRecoList" property="productType" value="1">
														<input type="text" id="upgradeOpenStock" name="upgradeOpenStock" style="width: 50px; background-color : #C0C0C0;" onchange="setVariance('<%=(i.intValue()+1)%>',7,'tableOpen'); getTotOpen('<%=(i.intValue()+1)%>');" disabled="true"/>	
													</logic:equal>
											</logic:equal>
											<logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
													<logic:equal name="productRecoList" property="productType" value="0">
														<input type="text" id="upgradeOpenStock" name="upgradeOpenStock" style="width: 50px" value='<bean:write name="productRecoList" property="upgradeOpenStock"/>' onchange="setVariance('<%=(i.intValue()+1)%>',7,'tableOpen'); getTotOpen('<%=(i.intValue()+1)%>');"/>	
													</logic:equal>
													<logic:equal name="productRecoList" property="productType" value="1">
														<input type="text" id="upgradeOpenStock" name="upgradeOpenStock" style="width: 50px; background-color : #C0C0C0;" onchange="setVariance('<%=(i.intValue()+1)%>',7,'tableOpen'); getTotOpen('<%=(i.intValue()+1)%>');" disabled="true"/>	
													</logic:equal>
												</logic:notEqual>
												</logic:notEqual>
											</logic:equal>	
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
												<logic:equal name="productRecoList" property="productType" value="0">
													<logic:notEqual name="productRecoList" property="showDetailsOpeningup" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',1,5 ,'OpeningStockOf'+'<bean:write name="productRecoList" property="productName"/>'+'Upgrade');"/>	
												</logic:notEqual>
												</logic:equal>
												</logic:equal>	
												</logic:equal>	
												<!-- end adding by ram -->			
										</TD>
										<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<div value='<bean:write name="productRecoList" property="churnOpenStock"/>'>
													<bean:write name="productRecoList" property="churnOpenStock"/>		
													</div>
													<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="churnOpenStock"/>'>
													</div>
													<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',8,'tableOpen');
												</script>
												</logic:equal>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="churnOpenStock"/>'>
													</div>
													<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',8,'tableOpen');
												</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
												<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="productType" value="0">
														<input type="text" id="churnOpenStock" name="churnOpenStock"  style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="churnOpenStock"/>' onchange="setVariance('<%=(i.intValue()+1)%>',8,'tableOpen'); getTotOpen('<%=(i.intValue()+1)%>');" disabled="true"/>	
													</logic:equal>
													<logic:equal name="productRecoList" property="productType" value="1">
														<input type="text" id="churnOpenStock" name="churnOpenStock" style="width: 50px; background-color : #C0C0C0;" onchange="setVariance('<%=(i.intValue()+1)%>',8,'tableOpen'); getTotOpen('<%=(i.intValue()+1)%>');" disabled="true"/>	
													</logic:equal>
											</logic:equal>
											<logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
													<logic:equal name="productRecoList" property="productType" value="0">
														<input type="text" id="churnOpenStock" name="churnOpenStock" style="width: 50px" value='<bean:write name="productRecoList" property="churnOpenStock"/>' onchange="setVariance('<%=(i.intValue()+1)%>',8,'tableOpen'); getTotOpen('<%=(i.intValue()+1)%>');"/>	
													</logic:equal>
													<logic:equal name="productRecoList" property="productType" value="1">
														<input type="text" id="churnOpenStock" name="churnOpenStock" style="width: 50px; background-color : #C0C0C0;" onchange="setVariance('<%=(i.intValue()+1)%>',8,'tableOpen'); getTotOpen('<%=(i.intValue()+1)%>');" disabled="true"/>	
													</logic:equal>
												</logic:notEqual>
												</logic:notEqual>
											</logic:equal>	
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
												<logic:equal name="productRecoList" property="productType" value="0">
													<logic:notEqual name="productRecoList" property="showDetailsOpeningchu" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',1,6 ,'OpeningStockOf'+'<bean:write name="productRecoList" property="productName"/>'+'Churn');"/>	
											   </logic:notEqual>
											   </logic:equal>
												</logic:equal>	
												</logic:equal>	
												<!-- end adding by ram -->			
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
 												getSummVariance('<%=(i.intValue()+1)%>',9,'tableOpen');
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
 												getSummVariance('<%=(i.intValue()+1)%>',10,'tableOpen');
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
 														getSummVariance('<%=(i.intValue()+1)%>',11,'tableOpen');
												</script>
										</logic:equal>
										<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<logic:notEqual name="productRecoList" property="activationTotal" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',4,1 ,'StockOf'+'<bean:write name="productRecoList" property="productName"/>'+'Activation');"/>	
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
 														getSummVariance('<%=(i.intValue()+1)%>',12,'tableOpen');
												</script>
										</logic:equal>
										<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<logic:notEqual name="productRecoList" property="invChngTotal" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',4,2,'StockOf'+'<bean:write name="productRecoList" property="productName"/>'+'InventoryChanges');"/>	
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
 														getSummVariance('<%=(i.intValue()+1)%>',13,'tableOpen');
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
										<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
										<div value='<bean:write name="productRecoList" property="closingTotal"/>'>
													<bean:write name="productRecoList" property="closingTotal"/>		
										</div>
										</logic:notEqual>
										<logic:equal name="productRecoList" property="dataTypa" value="Variance">
											<div value='<bean:write name="productRecoList" property="closingTotal"/>'>
													</div>
											<script type="text/javascript">
 														getSummVariance('<%=(i.intValue()+1)%>',14,'tableOpen');
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
			<DIV id=DIV_RECEIVED style="DISPLAY: none">
				<DIV style="height: 310px; width: 100%; overflow: auto; visibility: visible;z-index:1; left: 133px; top: 250px;">
					<table width="100%" align="left" border="1" cellpadding="0" cellspacing="0" id="tableRcvd"
					style="border-collapse: collapse;">
						<thead>
							<tr class="noScroll">
								<TD bgcolor="#CD0504" class="width104 height19 pLeft5"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Type</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Product Name</span></FONT></TD>
							<TD bgcolor="#851717" class="width104 height19 pLeft5" title="Total Open"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Total Open</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Received From WH"
								align="center"><FONT color="white"><span 
								class="white10heading mLeft5 pRight5 mTop5">Rcv. WH </span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Received Inter-SSD OK"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Rcv. Inter-SSD OK</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Inter-SSD Defective"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Inter-SSD Defective</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Received Upgrade"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Rec. Upgrade</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Received Churn"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Rec. Churn</span></TD>
							
							
							<TD bgcolor="#851717" class="width104 height19 pLeft5" title="Total Returned"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Total Returned</span></TD>
							<TD bgcolor="#851717" class="width104 height19 pLeft5" title="Total Activation"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Total Activation</span></TD>
							<TD bgcolor="#851717" class="width104 height19 pLeft5" title="Inventory Change"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Inventory Change</span></TD>
							
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
 												getSummVariance('<%=(i.intValue()+1)%>',2,'tableRcvd');
										</script>
										</logic:equal>
										
										</TD>
										<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<div value='<bean:write name="productRecoList" property="receivedWh"/>'>
													<bean:write name="productRecoList" property="receivedWh"/>		
													</div>
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
													<div value='<bean:write name="productRecoList" property="receivedWh"/>'>
																</div>
															<script type="text/javascript">
			 												getSummVariance('<%=(i.intValue()+1)%>',3,'tableRcvd');
													</script>
													</logic:equal>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
											<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="receivedWh"/>'>
													</div><script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',3,'tableRcvd');
												</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
												<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
											<input type="text" id="receivedWh" name="receivedWh" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="receivedWh"/>' onchange="setVariance('<%=(i.intValue()+1)%>',3,'tableRcvd'); getTotRec('<%=(i.intValue()+1)%>');"  disabled="true"/>	
											</logic:equal>
											<logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
												<input type="text" id="receivedWh" name="receivedWh" style="width: 50px" value='<bean:write name="productRecoList" property="receivedWh"/>' onchange="setVariance('<%=(i.intValue()+1)%>',3,'tableRcvd'); getTotRec('<%=(i.intValue()+1)%>');"/>	
											</logic:notEqual>
											</logic:notEqual>
											</logic:equal>	
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<logic:notEqual name="productRecoList" property="showDetailsreceivedWh" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',2,1,'ReceivedStockOf'+'<bean:write name="productRecoList" property="productName"/>'+'ReceivedWh');"/>	
												</logic:notEqual>
												</logic:equal>	
												</logic:equal>	
												<!-- end adding by ram -->
										</TD>
										<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<div value='<bean:write name="productRecoList" property="receivedInterSSDOK"/>'>
													<bean:write name="productRecoList" property="receivedInterSSDOK"/>		
													</div>
													<logic:equal name="productRecoList" property="dataTypa" value="Variance">
															<div value='<bean:write name="productRecoList" property="receivedInterSSDOK"/>'>
															</div><script type="text/javascript">
 															getVariance('<%=(i.intValue()+1)%>',4,'tableRcvd');
																	</script>
													</logic:equal>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="receivedInterSSDOK"/>'>
													</div><script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',4,'tableRcvd');
												</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
												<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
											<input type="text" id="receivedInterSSDOK" name="receivedInterSSDOK" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="receivedInterSSDOK"/>' onchange="setVariance('<%=(i.intValue()+1)%>',4,'tableRcvd'); getTotRec('<%=(i.intValue()+1)%>');" disabled="true"/>	
											</logic:equal>
											<logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
													<input type="text" id="receivedInterSSDOK" name="receivedInterSSDOK" style="width: 50px" value='<bean:write name="productRecoList" property="receivedInterSSDOK"/>' onchange="setVariance('<%=(i.intValue()+1)%>',4,'tableRcvd'); getTotRec('<%=(i.intValue()+1)%>');"/>	
												</logic:notEqual>
												</logic:notEqual>
											</logic:equal>	
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<logic:notEqual name="productRecoList" property="showDetailsreceivedInterSSDOK" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',2,2,'ReceivedStockOf'+'<bean:write name="productRecoList" property="productName"/>'+'ReceivedInterSSDOK');"/>	
												</logic:notEqual>
												</logic:equal>	
												</logic:equal>	
												<!-- end adding by ram -->			
										</TD>
										<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<div value='<bean:write name="productRecoList" property="receivedInterSSDDef"/>'>
													<bean:write name="productRecoList" property="receivedInterSSDDef"/>		
													</div>
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="receivedInterSSDDef"/>'>
													</div><script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',5,'tableRcvd');
												</script>
												</logic:equal>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="receivedInterSSDDef"/>'>
													</div><script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',5,'tableRcvd');
												</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
												<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
											<input type="text" id="receivedInterSSDDef" name="receivedInterSSDDef" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="receivedInterSSDDef"/>' onchange="setVariance('<%=(i.intValue()+1)%>',5,'tableRcvd'); getTotRec('<%=(i.intValue()+1)%>');" disabled="true"/>	
											</logic:equal>
											<logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
													<input type="text" id="receivedInterSSDDef" name="receivedInterSSDDef" style="width: 50px" value='<bean:write name="productRecoList" property="receivedInterSSDDef"/>' onchange="setVariance('<%=(i.intValue()+1)%>',5,'tableRcvd'); getTotRec('<%=(i.intValue()+1)%>');"/>	
												</logic:notEqual>
												</logic:notEqual>
											</logic:equal>	
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<logic:notEqual name="productRecoList" property="showDetailsreceivedInterSSDDef" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',2,3,'ReceivedStockOf'+'<bean:write name="productRecoList" property="productName"/>'+'ReceivedInterSSDDef');"/>	
												</logic:notEqual>
												</logic:equal>	
												</logic:equal>	
												<!-- end adding by ram -->			
										</TD>
										<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<div value='<bean:write name="productRecoList" property="receivedUpgrade"/>'>
													<bean:write name="productRecoList" property="receivedUpgrade"/>		
													</div>
													<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="receivedUpgrade"/>'>
													</div><script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',6,'tableRcvd');
												</script>
												</logic:equal>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="receivedUpgrade"/>'>
													</div><script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',6,'tableRcvd');
												</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
												<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
											<logic:equal name="productRecoList" property="productType" value="0">
														<input type="text" id="receivedUpgrade" name="receivedUpgrade" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="receivedUpgrade"/>' onchange="setVariance('<%=(i.intValue()+1)%>',6,'tableRcvd'); getTotRec('<%=(i.intValue()+1)%>');" disabled="true"/>	
													</logic:equal>
													<logic:equal name="productRecoList" property="productType" value="1">
														<input type="text" id="receivedUpgrade" name="receivedUpgrade" style="width: 50px; background-color : #C0C0C0;" onchange="setVariance('<%=(i.intValue()+1)%>',5,'tableRcvd'); getTotRec('<%=(i.intValue()+1)%>');" disabled="true"/>	
													</logic:equal>
											</logic:equal>
											<logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
													<logic:equal name="productRecoList" property="productType" value="0">
														<input type="text" id="receivedUpgrade" name="receivedUpgrade" style="width: 50px" value='<bean:write name="productRecoList" property="receivedUpgrade"/>' onchange="setVariance('<%=(i.intValue()+1)%>',6,'tableRcvd'); getTotRec('<%=(i.intValue()+1)%>');"/>	
													</logic:equal>
													<logic:equal name="productRecoList" property="productType" value="1">
														<input type="text" id="receivedUpgrade" name="receivedUpgrade" style="width: 50px; background-color : #C0C0C0;" onchange="setVariance('<%=(i.intValue()+1)%>',5,'tableRcvd'); getTotRec('<%=(i.intValue()+1)%>');" disabled="true"/>	
													</logic:equal>
												</logic:notEqual>
												</logic:notEqual>
											</logic:equal>	
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<logic:equal name="productRecoList" property="productType" value="0">
														<logic:notEqual name="productRecoList" property="showDetailsreceivedUpgrade" value="0">
														<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',2,4,'ReceivedStockOf'+'<bean:write name="productRecoList" property="productName"/>'+'ReceivedUpgrade');"/>	
													</logic:notEqual>
													</logic:equal>
												</logic:equal>	
											</logic:equal>	
												<!-- end adding by ram -->			
										</TD>
										<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<div value='<bean:write name="productRecoList" property="receivedChurn"/>'>
													<bean:write name="productRecoList" property="receivedChurn"/>		
													</div>
													<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="receivedChurn"/>'>
													</div><script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',7,'tableRcvd');
												</script>
												</logic:equal>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="receivedChurn"/>'>
													</div><script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',7,'tableRcvd');
												</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
												<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
											<logic:equal name="productRecoList" property="productType" value="0">
														<input type="text" id="receivedChurn" name="receivedChurn"style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="receivedChurn"/>' onchange="setVariance('<%=(i.intValue()+1)%>',7,'tableRcvd'); getTotRec('<%=(i.intValue()+1)%>');" disabled="true"/>	
													</logic:equal>
													<logic:equal name="productRecoList" property="productType" value="1">
														<input type="text" id="receivedChurn" name="receivedChurn" style="width: 50px; background-color : #C0C0C0;" onchange="setVariance('<%=(i.intValue()+1)%>',6,'tableRcvd'); getTotRec('<%=(i.intValue()+1)%>');" disabled="true"/>	
													</logic:equal>
											
											</logic:equal>
											<logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
													<logic:equal name="productRecoList" property="productType" value="0">
														<input type="text" id="receivedChurn" name="receivedChurn" style="width: 50px" value='<bean:write name="productRecoList" property="receivedChurn"/>' onchange="setVariance('<%=(i.intValue()+1)%>',7,'tableRcvd'); getTotRec('<%=(i.intValue()+1)%>');"/>	
													</logic:equal>
													<logic:equal name="productRecoList" property="productType" value="1">
														<input type="text" id="receivedChurn" name="receivedChurn" style="width: 50px; background-color : #C0C0C0;" onchange="setVariance('<%=(i.intValue()+1)%>',6,'tableRcvd'); getTotRec('<%=(i.intValue()+1)%>');" disabled="true"/>	
													</logic:equal>
													
												</logic:notEqual>
												</logic:notEqual>
											</logic:equal>	
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="productType" value="0">
													<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
														<logic:notEqual name="productRecoList" property="showDetailsreceivedChurn" value="0">
														<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',2,5,'ReceivedStockOf'+'<bean:write name="productRecoList" property="productName"/>'+'ReceivedChurn');"/>	
												</logic:notEqual>
													</logic:equal>	
												</logic:equal>
											</logic:equal>	
												<!-- end adding by ram -->			
										</TD>
										
										<TD align="center" class="text">
										<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
										<div value='<bean:write name="productRecoList" property="returnedTotal"/>'>
													<bean:write name="productRecoList" property="returnedTotal"/>		
										</div>
										</logic:notEqual>
										<logic:equal name="productRecoList" property="dataTypa" value="Variance">
										<div value='<bean:write name="productRecoList" property="returnedTotal"/>'>
													</div>	<script type="text/javascript">
 												getSummVariance('<%=(i.intValue()+1)%>',8,'tableRcvd');
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
 														getSummVariance('<%=(i.intValue()+1)%>',9,'tableRcvd');
												</script>
										</logic:equal>
										<!-- adding by ram  -->
										<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
										<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
											<logic:notEqual name="productRecoList" property="activationTotal" value="0">
											<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',4,1,'StockOf'+'<bean:write name="productRecoList" property="productName"/>'+'Activation');"/>	
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
 														getSummVariance('<%=(i.intValue()+1)%>',10,'tableRcvd');
												</script>
										</logic:equal>
										<!-- adding by ram  -->
										<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
										<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
												<logic:notEqual name="productRecoList" property="invChngTotal" value="0">
											<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',4,2,'StockOf'+'<bean:write name="productRecoList" property="productName"/>'+'Invetorychange');"/>	
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
 														getSummVariance('<%=(i.intValue()+1)%>',11,'tableRcvd');
												</script>
										</logic:equal>
										<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<logic:notEqual name="productRecoList" property="showDetailsClosingADJTOL" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',4,3,'StockOf'+'<bean:write name="productRecoList" property="productName"/>'+'AdjustmentTotal');"/>	
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
 														getSummVariance('<%=(i.intValue()+1)%>',12,'tableRcvd');
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
           <DIV id=DIV_RETURNED style="DISPLAY: none">
				<DIV style="height: 310px; width: 100%; overflow: auto; visibility: visible;z-index:1; left: 133px; top: 250px;">
					<table width="100%" align="left" border="1" cellpadding="0" cellspacing="0" id="tableRet"
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
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Returned OK Stock"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Ret. OK
							</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Returned Inter-SSD OK"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Ret. Inter-SSD OK</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Inter-SSD Defective"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Inter-SSD Defective</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Return Dead On Arrival Before Installation"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Ret. DOA BI</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Return Dead On Arrival After Installation"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Ret. DOA AI</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Return C2S"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Ret. C2S</span></TD>
							<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Return Churn"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Ret. Churn</span></TD>
								<TD bgcolor="#CD0504" class="width104 height19 pLeft5" title="Return Defective Swap"
								align="center" ><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Ret. Def. Swap</span></TD>
						
							
							<TD bgcolor="#851717" class="width104 height19 pLeft5" title="Total Activation"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Total Activation</span></TD>
							<TD bgcolor="#851717" class="width104 height19 pLeft5" title="Inventory Change"
								align="center"><FONT color="white"><span
								class="white10heading mLeft5 pRight5 mTop5">Inventory Change</span></TD>
							
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
 												getSummVariance('<%=(i.intValue()+1)%>',2,'tableRet');
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
 												getSummVariance('<%=(i.intValue()+1)%>',3,'tableRet');
											</script>
										</logic:equal>
										</TD>
										<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<div value='<bean:write name="productRecoList" property="returnedFresh"/>'>
													<bean:write name="productRecoList" property="returnedFresh"/>		
													</div>
													<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="returnedFresh"/>'>
											</div>
											<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',4,'tableRet');
												</script>
												</logic:equal>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="returnedFresh"/>'>
											</div>
											<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',4,'tableRet');
												</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
												<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
											<input type="text" id="returnedFresh" name="returnedFresh" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="returnedFresh"/>' onchange="setVariance('<%=(i.intValue()+1)%>',4,'tableRet'); getTotRet('<%=(i.intValue()+1)%>');" disabled="true"/>	
											</logic:equal>
											<logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
													<input type="text" id="returnedFresh" name="returnedFresh" style="width: 50px" value='<bean:write name="productRecoList" property="returnedFresh"/>' onchange="setVariance('<%=(i.intValue()+1)%>',4,'tableRet'); getTotRet('<%=(i.intValue()+1)%>');"/>	
												</logic:notEqual>
												</logic:notEqual>
											</logic:equal>
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<logic:notEqual name="productRecoList" property="showDetailsreturnedFresh" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',3,1,'ReturnedStockOf'+'<bean:write name="productRecoList" property="productName"/>'+'Fresh');"/>	
												</logic:notEqual>
												</logic:equal>	
												</logic:equal>	
												<!-- end adding by ram -->				
										</TD>
										<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<div value='<bean:write name="productRecoList" property="returnedInterSSDOk"/>'>
													<bean:write name="productRecoList" property="returnedInterSSDOk"/>		
													</div>
													<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="returnedInterSSDOk"/>'>
												</div>
												<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',5,'tableRet');
												</script>
												</logic:equal>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="returnedInterSSDOk"/>'>
												</div>
												<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',5,'tableRet');
												</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
												<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
											<input type="text" id="returnedInterSSDOk" name="returnedInterSSDOk" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="returnedInterSSDOk"/>' onchange="setVariance('<%=(i.intValue()+1)%>',5,'tableRet'); getTotRet('<%=(i.intValue()+1)%>');" disabled="true"/>	
											</logic:equal>
											<logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
													<input type="text" id="returnedInterSSDOk" name="returnedInterSSDOk" style="width: 50px" value='<bean:write name="productRecoList" property="returnedInterSSDOk"/>' onchange="setVariance('<%=(i.intValue()+1)%>',5,'tableRet'); getTotRet('<%=(i.intValue()+1)%>');"/>	
												</logic:notEqual>
												</logic:notEqual>
											</logic:equal>	
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<logic:notEqual name="productRecoList" property="showDetailsreturnedInterSSDOk" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',3,2,'ReturnedStockOf'+'<bean:write name="productRecoList" property="productName"/>'+'InterSSDOk');"/>	
												</logic:notEqual>
												</logic:equal>	
												</logic:equal>	
												<!-- end adding by ram -->			
										</TD>
										<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<div value='<bean:write name="productRecoList" property="returnedInterSSDDEF"/>'>
													<bean:write name="productRecoList" property="returnedInterSSDDEF"/>		
													</div>
													<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="returnedInterSSDDEF"/>'>
												</div>
												<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',6,'tableRet');
												</script>
												</logic:equal>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="returnedInterSSDDEF"/>'>
												</div>
												<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',6,'tableRet');
												</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
												<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
											<input type="text" id="returnedInterSSDDEF" name="returnedInterSSDDEF" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="returnedInterSSDDEF"/>' onchange="setVariance('<%=(i.intValue()+1)%>',6,'tableRet'); getTotRet('<%=(i.intValue()+1)%>');" disabled="true"/>	
											</logic:equal>
											<logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
													<input type="text" id="returnedInterSSDDEF" name="returnedInterSSDDEF" style="width: 50px" value='<bean:write name="productRecoList" property="returnedInterSSDDEF"/>' onchange="setVariance('<%=(i.intValue()+1)%>',6,'tableRet'); getTotRet('<%=(i.intValue()+1)%>');"/>	
												</logic:notEqual>
												</logic:notEqual>
											</logic:equal>	
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<logic:notEqual name="productRecoList" property="showDetailsreturnedInterSSDDEF" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',3,3,'ReturnedStockOf'+'<bean:write name="productRecoList" property="productName"/>'+'InterSSDDef');"/>	
												</logic:notEqual>
												</logic:equal>	
												</logic:equal>	
												<!-- end adding by ram -->			
										</TD>
										<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<div value='<bean:write name="productRecoList" property="returnedDoaBI"/>'>
													<bean:write name="productRecoList" property="returnedDoaBI"/>		
												</div>
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="returnedDoaBI"/>'>
												</div>
												<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',7,'tableRet');
												</script>
												</logic:equal>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="returnedDoaBI"/>'>
												</div>
												<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',7,'tableRet');
												</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
												<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
											<input type="text" id="returnedDoaBI" name="returnedDoaBI" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="returnedDoaBI"/>' onchange="setVariance('<%=(i.intValue()+1)%>',7,'tableRet'); getTotRet('<%=(i.intValue()+1)%>');" disabled="true"/>
											</logic:equal>
											<logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
													<input type="text" id="returnedDoaBI" name="returnedDoaBI" style="width: 50px" value='<bean:write name="productRecoList" property="returnedDoaBI"/>' onchange="setVariance('<%=(i.intValue()+1)%>',7,'tableRet'); getTotRet('<%=(i.intValue()+1)%>');"/>
												</logic:notEqual>
												</logic:notEqual>
											</logic:equal>
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<logic:notEqual name="productRecoList" property="showDetailsreturnedDoaBI" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',3,4,'ReturnedStockOf'+'<bean:write name="productRecoList" property="productName"/>'+'DoaBI');"/>	
												</logic:notEqual>
												</logic:equal>	
												</logic:equal>	
												<!-- end adding by ram -->				
										</TD>
										<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<div value='<bean:write name="productRecoList" property="returnedDoaAi"/>'>
													<bean:write name="productRecoList" property="returnedDoaAi"/>		
													</div>
													<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="returnedDoaAi"/>'>
												</div>
												<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',8,'tableRet');
												</script>
												</logic:equal>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="returnedDoaAi"/>'>
												</div>
												<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',8,'tableRet');
												</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
												<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
											<logic:equal name="productRecoList" property="productType" value="0">
														<input type="text" id="returnedDoaAi" name="returnedDoaAi" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="returnedDoaAi"/>' onchange="setVariance('<%=(i.intValue()+1)%>',8,'tableRet'); getTotRet('<%=(i.intValue()+1)%>');" disabled="true"/>	
													</logic:equal>
													<logic:equal name="productRecoList" property="productType" value="1">
														<input type="text" id="returnedDoaAi" name="returnedDoaAi"  style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="returnedDoaAi"/>' onchange="setVariance('<%=(i.intValue()+1)%>',8,'tableRet'); getTotRet('<%=(i.intValue()+1)%>');" disabled="true"/>	
													</logic:equal>
											</logic:equal>
											<logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
													<logic:equal name="productRecoList" property="productType" value="0">
														<input type="text" id="returnedDoaAi" name="returnedDoaAi" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="returnedDoaAi"/>' onchange="setVariance('<%=(i.intValue()+1)%>',8,'tableRet'); getTotRet('<%=(i.intValue()+1)%>');" disabled="true"/>	
													</logic:equal>
													<logic:equal name="productRecoList" property="productType" value="1">
														<input type="text" id="returnedDoaAi" name="returnedDoaAi" style="width: 50px" value='<bean:write name="productRecoList" property="returnedDoaAi"/>' onchange="setVariance('<%=(i.intValue()+1)%>',8,'tableRet'); getTotRet('<%=(i.intValue()+1)%>');"/>	
													</logic:equal>
													
												</logic:notEqual>
												</logic:notEqual>
											</logic:equal>	
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
														<logic:notEqual name="productRecoList" property="showDetailsreturnedDoaAi" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',3,5,'ReturnedStockOf'+'<bean:write name="productRecoList" property="productName"/>'+'DoaAI');"/>	
												</logic:notEqual>
												</logic:equal>	
												</logic:equal>	
												<!-- end adding by ram -->			
										</TD>
										<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<div value='<bean:write name="productRecoList" property="returnedC2S"/>'>
													<bean:write name="productRecoList" property="returnedC2S"/>		
													</div>
													<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="returnedC2S"/>'>
												</div>
												<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',9,'tableRet');
												</script>
												</logic:equal>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="returnedC2S"/>'>
												</div>
												<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',9,'tableRet');
												</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
												<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
											<logic:equal name="productRecoList" property="productType" value="0">
														<input type="text" id="returnedC2S" name="returnedC2S" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="returnedC2S"/>' onchange="setVariance('<%=(i.intValue()+1)%>',9,'tableRet')" disabled="true"/>	
													</logic:equal>
													<logic:equal name="productRecoList" property="productType" value="1">
														<input type="text" id="returnedC2S" name="returnedC2S" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="returnedC2S"/>' onchange="setVariance('<%=(i.intValue()+1)%>',9,'tableRet'); getTotRet('<%=(i.intValue()+1)%>');" disabled="true"/>	
													</logic:equal>
											
											</logic:equal>
											<logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
													<logic:equal name="productRecoList" property="productType" value="0">
														<input type="text" id="returnedC2S" name="returnedC2S" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="returnedC2S"/>' onchange="setVariance('<%=(i.intValue()+1)%>',9,'tableRet')" disabled="true"/>	
													</logic:equal>
													<logic:equal name="productRecoList" property="productType" value="1">
														<input type="text" id="returnedC2S" name="returnedC2S" style="width: 50px" value='<bean:write name="productRecoList" property="returnedC2S"/>' onchange="setVariance('<%=(i.intValue()+1)%>',9,'tableRet'); getTotRet('<%=(i.intValue()+1)%>');"/>	
													</logic:equal>
												</logic:notEqual>
												</logic:notEqual>
											</logic:equal>	
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<logic:notEqual name="productRecoList" property="showDetailsreturnedC2S" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',3,6,'ReturnedStockOf'+'<bean:write name="productRecoList" property="productName"/>'+'C2S');"/>	
												</logic:notEqual>
												</logic:equal>	
												</logic:equal>	
												<!-- end adding by ram -->			
										</TD>
										<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<div value='<bean:write name="productRecoList" property="returnedChurn"/>'>
													<bean:write name="productRecoList" property="returnedChurn"/>		
													</div>
													<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="returnedChurn"/>'>
												</div>
												<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',10,'tableRet');
												</script>
												</logic:equal>
											</logic:notEqual>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="returnedChurn"/>'>
												</div>
												<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',10,'tableRet');
												</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
												<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
											<logic:equal name="productRecoList" property="productType" value="0">
														<input type="text" id="returnedChurn" name="returnedChurn" style="width: 50px; background-color : #C0C0C0;"  value='<bean:write name="productRecoList" property="returnedChurn"/>' onchange="setVariance('<%=(i.intValue()+1)%>',10,'tableRet'); getTotRet('<%=(i.intValue()+1)%>');" disabled="true"/>	
													</logic:equal>
													<logic:equal name="productRecoList" property="productType" value="1">
														<input type="text" id="returnedChurn" name="returnedChurn" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="returnedChurn"/>' onchange="setVariance('<%=(i.intValue()+1)%>',10,'tableRet')" disabled="true"/>	
													</logic:equal>
											</logic:equal>
											<logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
													<logic:equal name="productRecoList" property="productType" value="0">
														<input type="text" id="returnedChurn" name="returnedChurn" style="width: 50px" value='<bean:write name="productRecoList" property="returnedChurn"/>' onchange="setVariance('<%=(i.intValue()+1)%>',10,'tableRet'); getTotRet('<%=(i.intValue()+1)%>');"/>	
													</logic:equal>
													<logic:equal name="productRecoList" property="productType" value="1">
														<input type="text" id="returnedChurn" name="returnedChurn" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="returnedChurn"/>' onchange="setVariance('<%=(i.intValue()+1)%>',10,'tableRet')" disabled="true"/>	
													</logic:equal>
												</logic:notEqual>
												</logic:notEqual>
											</logic:equal>
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
											<logic:equal name="productRecoList" property="productType" value="0">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<logic:notEqual name="productRecoList" property="showDetailsRChurn" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',3,7,'ReturnedStockOf'+'<bean:write name="productRecoList" property="productName"/>'+'Churn');"/>	
												</logic:notEqual>
												</logic:equal>	
												</logic:equal>
											</logic:equal>	
												<!-- end adding by ram -->				
										</TD>
											<TD align="center" class="text">
											<logic:notEqual name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<div value='<bean:write name="productRecoList" property="returnedDefectiveSwap"/>'>
													<bean:write name="productRecoList" property="returnedDefectiveSwap"/>
												</div>
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="returnedDefectiveSwap"/>'>
												</div>
												<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',11,'tableRet');
												</script>
												</logic:equal>
													
											</logic:notEqual>
											<logic:equal name="productRecoList" property="isPartnerEntered" value="INITIATE">
												<logic:equal name="productRecoList" property="dataTypa" value="Variance">
												<div value='<bean:write name="productRecoList" property="returnedDefectiveSwap"/>'>
												</div>
												<script type="text/javascript">
 														getVariance('<%=(i.intValue()+1)%>',11,'tableRet');
												</script>
												</logic:equal>
												<logic:notEqual name="productRecoList" property="dataTypa" value="Variance">
												<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
											<logic:equal name="productRecoList" property="productType" value="0">
														<input type="text" id="returnedDefectiveSwap" name="returnedDefectiveSwap" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="returnedDefectiveSwap"/>' onchange="setVariance('<%=(i.intValue()+1)%>',11,'tableRet'); getTotRet('<%=(i.intValue()+1)%>');" disabled="true"/>	
													</logic:equal>
													<logic:equal name="productRecoList" property="productType" value="1">
														<input type="text" id="returnedDefectiveSwap" name="returnedDefectiveSwap" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="returnedDefectiveSwap"/>' onchange="setVariance('<%=(i.intValue()+1)%>',11,'tableRet')" disabled="true"/>	
													</logic:equal>
											</logic:equal>
											<logic:notEqual name="DistRecoBean" property="newRecoLiveDate" value="true">
													<logic:equal name="productRecoList" property="productType" value="0">
														<input type="text" id="returnedDefectiveSwap" name="returnedDefectiveSwap" style="width: 50px" value='<bean:write name="productRecoList" property="returnedDefectiveSwap"/>' onchange="setVariance('<%=(i.intValue()+1)%>',11,'tableRet'); getTotRet('<%=(i.intValue()+1)%>');"/>	
													</logic:equal>
													<logic:equal name="productRecoList" property="productType" value="1">
														<input type="text" id="returnedDefectiveSwap" name="returnedDefectiveSwap" style="width: 50px; background-color : #C0C0C0;" value='<bean:write name="productRecoList" property="returnedDefectiveSwap"/>' onchange="setVariance('<%=(i.intValue()+1)%>',11,'tableRet')" disabled="true"/>	
													</logic:equal>
												</logic:notEqual>
												</logic:notEqual>
											</logic:equal>
											<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<logic:notEqual name="productRecoList" property="showDetailsreturnedDefectiveSwap" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',3,8,'ReturnedStockOf'+'<bean:write name="productRecoList" property="productName"/>'+'DefectiveSwap');"/>	
												</logic:notEqual>
												</logic:equal>	
												</logic:equal>	
												<!-- end adding by ram -->				
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
 														getSummVariance('<%=(i.intValue()+1)%>',12,'tableRet');
												</script>
										</logic:equal>
										<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<logic:notEqual name="productRecoList" property="activationTotal" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',4,1,'StockOf'+'<bean:write name="productRecoList" property="productName"/>'+'Activation');"/>	
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
 														getSummVariance('<%=(i.intValue()+1)%>',13,'tableRet');
												</script>
										</logic:equal>
										<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
													<logic:notEqual name="productRecoList" property="invChngTotal" value="0">
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
 														getSummVariance('<%=(i.intValue()+1)%>',14,'tableRet');
												</script>
										</logic:equal>
										<!-- adding by ram  -->
											<logic:equal name="DistRecoBean" property="newRecoLiveDate" value="true">
												<logic:equal name="productRecoList" property="dataTypa" value="Partner Figure">
														<logic:notEqual name="productRecoList" property="showDetailsClosingADJTOL" value="0">
													<input type="button" value="Details" class="button" onclick="downloadDetails('<bean:write name="productRecoList" property="productId"/>',4,3,'StockOf'+'<bean:write name="productRecoList" property="productName"/>'+'AdjustmentTotal');"/>	
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
 														getSummVariance('<%=(i.intValue()+1)%>',15,'tableRet');
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
<%
System.out.println("************************* END OF DISTRECOTABPARTONE.jsp***********************************");
 %>
</body>
</html:html>
