<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@page import="java.text.SimpleDateFormat,java.util.*,com.ibm.dp.dto.*"%>
<html:html>
<HEAD>
<%@ page language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="<%=request.getContextPath()%>/theme/Master.css" rel="stylesheet"
	type="text/css">
	<LINK href=<%=request.getContextPath()%>/jsp/hbo/theme/text.css rel="stylesheet" type="text/css">
<TITLE></TITLE>
<script>
function closeWindow()
{
 window.close();
}
</script>
</HEAD>
<BODY>
<h2 align="center" ></h2>
<BR>
<html:form action="/stockAcceptTransfer.do">

	
<%try{

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	String date = sdf.format(new java.util.Date()) ;
	PrintRecoDto objPrintRecoDto = null;
	Iterator ittDist=null;
	String distname="";
	String loginName="";
	String circleName="";	
	String prodName="";
	String qty="";
	String ref_no="";
	String certDate="";
	boolean flag= false;
	List list = (List) request.getAttribute("printRecoList");
	List listDist=null;
	Iterator itt= list.iterator();
	int size=0;
	while(itt.hasNext())
	{
		flag=false;
		listDist = (List) itt.next();
		
		size = listDist.size();
		objPrintRecoDto = (PrintRecoDto) listDist.get(0);
		
		prodName=objPrintRecoDto.getProductName();
		qty=objPrintRecoDto.getQuantity();
		distname=objPrintRecoDto.getDistName();
		loginName = objPrintRecoDto.getLoginName();
		circleName=objPrintRecoDto.getCircleName();
		ref_no = objPrintRecoDto.getRefNo();
		certDate=objPrintRecoDto.getCertDate();
		

%>
 <STYLE TYPE="text/css">
     Details {page-break-before: always}
 </STYLE>
 
<table border="0" width="100%" >

<tr>
<td align="left" width="50%">Ref NO.:<%=ref_no%></td>

<td align="right" width="50%">DATE: <%=date%> </td>

</tr>

<tr><td align="left"></td></tr><tr><td align="left"></td></tr><tr><td></td></tr><tr><td></td></tr>
<tr>
<td align="left">To,</td>
</tr>
<tr>
<td align="left">M/S Bharti Telemedia Limited,</td>
</tr>
<tr>
<td align="left">Plot No. 16,</td>
</tr>
<tr>
<td align="left">Udyog Vihar Phase-IV,</td>
</tr>
<tr>
<td align="left"> Gurgaon-122015,</td>
</tr>
<tr>
<td align="left">Haryana</td>
</tr>
<tr><td align="left"></td></tr><tr><td align="left"></td></tr><tr><td></td></tr><tr><td></td></tr>
<tr>
<td align="left"> <strong>Subject:Stock Confirmation Letter.</strong></td>
</tr>
<tr><td align="left"></td></tr><tr><td align="left"></td></tr><tr><td></td></tr><tr><td></td></tr>

<tr>
<td align="left"> Dear Sir/Madam,</td>
</tr>
<tr>
<td align="left" colspan="2">Please find below the details of the stock held by us on the date <%=certDate %>.</td>
</tr>


<tr>
<td>&nbsp;</td>
</tr>
</table>

<table width="100%" bordercolor="black" cellspacing="0" cellpadding="0" border='0' height="230"  >
<tr height="30" style="border-right:1px solid;border-bottom:1px solid;border-left:1px solid;border-top:1px solid;">
	<td width="10%" align="center" style="border-bottom:1px solid;border-left:1px solid;border-top:1px solid;"><strong>Sl. No</strong></td>
	<td width="15%" align="center" style="border-bottom:1px solid;border-left:1px solid;border-top:1px solid;"><strong>Item</strong></td>
	<td width="20%" align="center" style="border-bottom:1px solid;border-left:1px solid;border-top:1px solid;border-right:1px solid;"><strong>Quantity</strong></td>
</tr>

<%
	
	for(int i=0; i < listDist.size(); i++)	
		{
				
				objPrintRecoDto = (PrintRecoDto) listDist.get(i);
				
				prodName=objPrintRecoDto.getProductName();
				qty=objPrintRecoDto.getQuantity();
				distname=objPrintRecoDto.getDistName();
				loginName = objPrintRecoDto.getLoginName();
				circleName = objPrintRecoDto.getCircleName();
				if(qty != null && !"".equals(qty) )
				{
					flag = true;
 %>

					<tr>
						<td width="10%" align="center"  style="border-bottom:1px solid;border-left:1px solid;border-right:1px solid;" ><font size=2><%=(i+1)%></td>
						<td width="15%" align="left" style="border-bottom:1px solid;border-right:1px solid;"><font size=2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<%=prodName%></td>
						<td width="20%" align="center" style="border-bottom:1px solid;border-right:1px solid;"><font size=2><%=qty %></td>
					</tr>
	<%
				}
	}
	if(!flag)
	{
		%>
			<tr height="25"><td>&nbsp;</td>
			</tr>
			<tr height="175">
				<td colspan="3" align="center" valign="top"  ><strong>RECO Not Submitted by <%=distname%></strong></td>
			</tr>
		<%
	}
	 %>
</table>
<BR>
<tr>
<td>

	*Note- All swap category stock is including C2S, DOA & Upgrade.

</td>
</tr>
<BR>
<table border="0" width="100%" >

<tr>
<td>I hereby certify that the above mentioned quantities are true to the best of my knowledge.</td>
</tr>
<tr>
<td></td>
</tr>
<tr>
<td></td>
</tr>
<tr>
<td></td>
</tr>

<tr>
<td>With Best Regards,</td>
</tr>
<tr>
<td> <%=distname%>&nbsp;(<%=loginName %>)&nbsp;(<%=circleName %>)</td>
</tr>
</table>
<br/><br/><br/>

<table border="0" width="100%">
	<TR>
	<td align="center" width="100%">
		This is system generated certificate thus does not require any signature.
		<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
		</td>
	</TR>	
</table>
<%
}

}catch(Exception ex){
//System.out.println("==================== by pratap ================");
//ex.printStackTrace();
	//System.out.println(ex);
	
} %>	

</html:form>
<script>
	window.print();
</script>
</BODY>
</html:html>
