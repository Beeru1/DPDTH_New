<!-- Added by Mohammad Aslam -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>
<HEAD>
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
	<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<META name="GENERATOR" content="IBM Software Development Platform">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK href="<%=request.getContextPath()%>/theme/text.css" rel="stylesheet" type="text/css">
	<TITLE><bean:message bundle="dp" key="application.title"/></TITLE>
	<script>				
		function getDistributorDetails(){
			//alert('inside getDistributorDetails() selected value is ' + document.getElementById('distributor').value);
			if(document.getElementById('distributor').value > 0){
				document.getElementById('showAllButton').disabled = true;
				document.getElementById('exportToExcel').value = "default";
				document.forms[0].submit();
			}else{
				alert('Please select Distributor !');
			}
		}	
		
		function exportToExel(){
			//alert('inside exportToExcel() selected value is ' + document.getElementById('distributor').value);
			if(document.getElementById('distributor').value > 0){
				document.getElementById('exportToExcel').value = "exportToExcel";
				document.forms[0].submit();
			}else{
				alert('Please select Distributor !');
			}			
		}	
	</script>	
</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif" bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<% 
    	String rowDark ="#FCE8E6";
    	String rowLight = "#FFFFFF";
	%>
	<html:form action="/distributorDetails" >	
		<IMG src="<%=request.getContextPath()%>/images/viewstockdeclaration.JPG" width="520" height="25" alt=""/>
		<html:hidden property="methodName" value="getDistributorDetails" />		
		<html:hidden property="exportToExcel" value="default" />
		<TABLE width='100%' border='0'>
		<tr>
		<td width='100%'>
		<TABLE border="0" align="Center" class="border" width="40%">
			<TBODY>
				<TR> 
					<TD class="text pLeft15" width="50%">
						<STRONG><FONT color="#000000"><bean:message bundle="dp" key="label.PO.SelectDistributor"/></FONT></STRONG>
					</TD>
					<TD width="50%">
						<html:select styleId="distributor" property="distributor">
							<html:option value="">- Select Distributor -</html:option>
							<html:options collection="distributorList" property="accountId" labelProperty="accountName"/>							
						</html:select>
					</TD>
				</TR>
				<TR><td>&nbsp;</td></TR>
				<TR>
					<TD align="right"><INPUT type="button" name="showAllButton" onclick="getDistributorDetails();" Class="medium" value="Show All"></TD>
					<TD><INPUT type="button" name="exportButton" onclick="exportToExel();" Class="medium" value="Export to Excel"></TD>
				</TR>				
		</TABLE>
		</td>
		</tr>
		<tr>
		<td width='100%'>
		<BR>
		<DIV style="height: 330px; overflow: auto;" width="100%">
			<TABLE border="0" align="center" class="mLeft5" styleId="tblSample" width="100%" cellpadding='1'>
				<TBODY id="contacts">
					<TR  style="position: relative; top:expression(this.offsetParent.scrollTop-2);">
						<TD colspan='2' align='center' bgcolor="#CD0504" >&nbsp;</TD>
						<TD colspan='2' align='center' bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="166">
							<FONT color="white"><span class="white10heading mLeft5 pRight5 mTop5">
								<bean:message bundle="dp" key="label.PO.AsPerDP"/></span>
							</FONT>
						</TD>
						<TD align='center' bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="166">
							<FONT color="white"><span class="white10heading mLeft5 pRight5 mTop5">
								<bean:message bundle="dp" key="label.PO.AsPerPartner"/></span>
							</FONT>
						</TD>
						<TD colspan='2' align='center' bgcolor="#CD0504" >&nbsp;</TD>
					</TR>
					<TR style="position: relative; top:expression(this.offsetParent.scrollTop-2);">
						<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="166">
							<FONT color="white">
								<span class="white10heading mLeft5 pRight5 mTop5">
								<bean:message bundle="dp" key="label.PO.DistributorName"/></span>
							</FONT>
						</TD>
						<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="166">
							<FONT color="white">
								<span class="white10heading mLeft5 pRight5 mTop5"><bean:message bundle="dp" key="label.PO.ProductName"/></span>
							</FONT>
						</TD>
						<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="166">
							<FONT color="white">
								<span class="white10heading mLeft5 pRight5 mTop5"><bean:message bundle="dp" key="label.PO.DPStock"/></span>
							</FONT>
						</TD>
						<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="166">
							<FONT color="white">
								<span class="white10heading mLeft5 pRight5 mTop5"><bean:message bundle="dp" key="label.PO.OpenStock"/></span>
							</FONT>
						</TD>												
						<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="166">
							<FONT color="white">
								<span class="white10heading mLeft5 pRight5 mTop5"><bean:message bundle="dp" key="label.PO.ClosingStock"/></span>
							</FONT>
						</TD>
						<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="166">
							<FONT color="white">
								<span class="white10heading mLeft5 pRight5 mTop5"><bean:message bundle="dp" key="label.PO.Comments"/></span>
							</FONT>
						</TD>
						<TD bgcolor="#CD0504" class="width104 height19 pLeft5" align="center" width="166">
							<FONT color="white">
								<span class="white10heading mLeft5 pRight5 mTop5"><bean:message bundle="dp" key="label.PO.CreationDate"/></span>
							</FONT>
						</TD>
					</TR>
				<!-- iterate over the results of the query -->
				<logic:iterate id="distributorDetails" name="distributorDetailsList" indexId="i">
					<TR BGCOLOR='<%=((i.intValue()+1) % 2 == 0 ? rowDark : rowLight) %>' >
						<TD>
							<bean:write name="distributorDetails" property="distributorName" />
						</TD>
						<TD>
							<bean:write name="distributorDetails" property="productName" />
						</TD>
						<TD>
							<bean:write name="distributorDetails" property="intDPStock" />
						</TD>
						<TD>
							<bean:write name="distributorDetails" property="intOpenStock" />
						</TD>
						<TD>
							<bean:write name="distributorDetails" property="closingStock" />
						</TD>
						<TD>
							<bean:write name="distributorDetails" property="comments" />
						</TD>
						<TD>
							<bean:write name="distributorDetails" property="creationDate" />
						</TD>						
					</TR>
				</logic:iterate>		
				</TBODY>
			</TABLE>	
		</DIV>
		</td>
		</tr>
		</TABLE>
	</html:form>
</BODY>
</html:html>