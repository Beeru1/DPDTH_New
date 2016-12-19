
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
 <% String isSUK=null;
	String isRC=null;
	String isHANDSET=null;
	isSUK=(String)request.getAttribute("suk");
	isRC=(String)request.getAttribute("rc");
	isHANDSET=(String)request.getAttribute("handset");
	
	
	%>
<SCRIPT language="javascript" type="text/javascript"> 

function Show()
{

var tbl = document.getElementById('businessCategory').value;
 

}

function doSubmit(){

document.forms[0].action="DPViewWarrantyAction.do?methodName=Selectdata";

document.forms[0].submit();
}

function validate(){
if(document.getElementById("businessCategory").value=="0"){
alert('<bean:message bundle="error" key="error.product.selectedSub" />');
document.getElementById("businessCategory").focus();
return false;			
		}
		doSubmit();
			}
function formSubmit(obj){

document.forms[0].action="./DPEditWarrantyAction.do?methodName=edit";
document.getElementById("productid").value=obj;
document.forms[0].submit();
}


</script>
</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<% 
    String rowDark ="#FFE4E1";
    String rowLight = "#FFFFFF";
%>
 <html:form action="DPViewWarrantyAction">
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
		<IMG src="<%=request.getContextPath()%>/images/viewWarranty.gif"
		width="505" height="22" alt="">
	</TD>
</tr>			</table>

<TABLE border="0" align="Center" class="border" width="423">
		<TBODY>
		
			<TR>
			</TR>
		<TR>
			<TD align="center"><b
						class="text pLeft15" >BUSINESS CATEGORY</b>
			<html:select
					styleId="businessCategory" property="businessCategory"
					onchange="Show();" name="DPViewWarrantyForm" style="width:150px"
					style="height:20px">
					<html:option value="0">- Select A Category -</html:option>
					<logic:notEmpty name="DPViewWarrantyForm" property="arrBCList">
						<html:optionsCollection name="DPViewWarrantyForm"
							property="arrBCList" />
					</logic:notEmpty>
				</html:select><br>
						<br>
						</TD>
				
			<TD align="center" height="41"><bean:define id="sub" name="DPViewWarrantyForm" property="subList" />
			</TD>
		</TR>
		<logic:messagesPresent message="true">
							<html:messages id="msg" property="MESSAGE_SENT_SUCCESS" bundle="view" message="true">
								<font color="red" size="2"><strong><bean:write name="msg"/></strong></font>
							</html:messages>
							<html:messages id="msg" property="ERROR_OCCURED" bundle="view" message="true">
								<font color="red" size="2"><strong><bean:write name="msg"/></strong></font>
							</html:messages>
						</logic:messagesPresent>
<TR>
			<td colspan="1" align="center" width="754"><input type="button" class="subbig" value="Submit"
						onclick="return validate();" ></td>
				
				</TR>
							
	</TABLE>
	
	<TABLE id="hndset" >
     
      <%
	if(isHANDSET !=null  && isHANDSET.equalsIgnoreCase("handset")){
	 %>
	        <TR>
				<TD valign="top" height="100%" align="center">
				<b>HANDSET SERVICES </b> <font>&nbsp;<font color='red'></font></font><br>
					<br>
					<br>
					</TD> 
			</TR>
	  	<TR>
	  	<TD align="center"><b><bean:message bundle="view"
								key="warrantyview.productid" /></b><br>
				
					</td>
			
			<TD align="center"><b><bean:message bundle="view"
								key="warrantyview.productname" /></b><br>
				
					</td>			
			<TD align="center"><b><bean:message bundle="view"
								key="warrantyview.warranty" /></b><br>
				
					</td>
					<td align="center"><b><bean:message bundle="view"
					key="warrantyview.extwarranty"/></b>
					</td>
			
		</TR> 
		 <logic:notEmpty name="DPViewWarrantyForm" property="productList">
			<logic:iterate name="DPViewWarrantyForm" id="report" indexId="m" property="productList"> 
				<TR BGCOLOR='<%=((m.intValue()+1) % 2==0 ? rowDark : rowLight) %>'>
				   <TD width="70"><bean:write name="report" property="productid"  /></TD>					
					<TD width="70"><bean:write name="report" property="productname" /></TD>					
					<TD width="70"><bean:write name="report" property="warranty" /></TD>
					<TD width="70"><bean:write name="report" property="extwarranty" /></TD>					
					<TD class="txt" align="center"><A
						href="javascript: formSubmit('<bean:write name="report" property="productid" />');" >
						<FONT color="red">Edit</FONT></A></TD>
				
				</TR>
			</logic:iterate>
			
		</logic:notEmpty>
		<%} %>
		<tr><td><input type="hidden" name="productid" value=""></td></tr>
	</TABLE>
	
	<%
	
	 %>
	<TABLE id="sukservice">
	
	
	<%
	
	if(isSUK !=null  && isSUK.equalsIgnoreCase("suk")){%>
			<TR id="sukheader" >
				<TD valign="top" height="100%" align="center">
				<b>CPE SERVICES </b> <font>&nbsp;<font color='red'></font></TD>
			</TR>
			<%}
			
			
			 if(isRC !=null  && isRC.equalsIgnoreCase("rc")){ %>
			<TR id="rcheader" >
				<TD valign="top" height="100%" align="center">
				<b>RC SERVICES </b> </TD>
			</TR>
			<%} %>
			</TABLE>
			<br>
			<br>
			<table id="sukservice">
				<%if(isRC!=null || isSUK !=null ){%>
				<tr>
                    <td align="center"><b><bean:message bundle="view"
						key="warrantyview.productid" /></b></td>
					<td align="center"><b><bean:message bundle="view"
						key="warrantyview.productname" /></b></td>
					<td align="center" width="143"><b><bean:message bundle="view"
						key="warrantyview.warranty" /></b></td>
						<td align="center"><b><bean:message bundle="view"
					key="warrantyview.extwarranty"/></b>
					</td>

				</tr>
				<logic:notEmpty name="DPViewWarrantyForm" property="productList">
					<logic:iterate name="DPViewWarrantyForm" id="report" indexId="m"
						property="productList">
						<tr bgcolor="<%=((m.intValue()+1) % 2==0 ? rowDark : rowLight) %>">
						<td width="70"><bean:write name="report"
								property="productid" /></td>
							<td width="70"><bean:write name="report"
								property="productname" /></td>
							<td width="196"><bean:write name="report"
								property="warranty" /></td>
							<TD width="70"><bean:write name="report" property="extwarranty" /></TD>
							
							<td class="txt" align="center"><a
								href="javascript: formSubmit('<bean:write name="report" property="productid" />');"> <font color="red">Edit</font></a></td>

						</tr>
					</logic:iterate>

				</logic:notEmpty>
				<%} %>

			</table>
			
	
	
	</TABLE>
	</html:form>

<jsp:include page="../common/footer.jsp" flush="" />
</BODY>
</html:html>