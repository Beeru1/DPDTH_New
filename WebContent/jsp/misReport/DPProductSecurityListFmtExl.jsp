<%try{%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>	
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import = "java.util.ArrayList,java.util.HashMap"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.ibm.dp.service.DPProductSecurityListService" %>
<%@ page import = "com.ibm.dp.service.impl.DPProductSecurityListServiceImpl" %>
<%@ page import = "com.ibm.dp.dto.DPProductSecurityListDto" %>


<%@page import="com.ibm.utilities.Utility"%>
<html:html>
<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE>DP Reports</TITLE>
</HEAD>																		
<BODY>

<% 
    String rowDark ="#FCE8E6";
    String rowLight = "#FFFFFF";
	String [][] excelData = null;
	DPProductSecurityListService excelBulkupload = new DPProductSecurityListServiceImpl();
		List  productSecurityList = excelBulkupload.getALLProuductSecurityList();	
	Iterator itr =productSecurityList.iterator();
	System.out.println("dpProductSecurityListBean.getProductSecurityList()"+productSecurityList.size());
	excelData =  new String [productSecurityList.size()+2][productSecurityList.size()+2];
	System.out.println("dpProductSecurityListBean.excelData.length()"+excelData.length);
	excelData[0][0] = "Product Name" ;
	excelData[0][1] = "Security" ;
	int count = 1;
	int colNumber = 3;
	while(itr.hasNext()){
		
		DPProductSecurityListDto dto =	(DPProductSecurityListDto) itr.next();
		excelData[count][0] = dto.getProductName();
		excelData[count][1] = dto.getSecurityAmt();
		count++;
		
	}
	System.out.println("dpProductSecurityListBean.count"+count);
	Utility.writeDataToExcelFile("C:\\temp\\ProductSecuritySampleBulkUploadFile1.xls",excelData,colNumber);
%>	
	
	
<P><BR>
</P>
</BODY>
</html:html>
 <%}
 catch(Exception e){e.printStackTrace();}%>