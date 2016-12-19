<%@ page import="com.ibm.hbo.forms.HBOFileUploadFormBean"%>
<%String redirectURL = null;
	HBOFileUploadFormBean fileUploadFormBean = new HBOFileUploadFormBean();
	session.setAttribute("defaulter", request.getAttribute("defaulter")); //
	String fileType1 = request.getAttribute("fileType1").toString();
	String contentOfFile1 = request.getAttribute("ContentOfFile1").toString();
	session.setAttribute("fileType", contentOfFile1);
	redirectURL = request.getContextPath()+"/bulkDataAction.do?methodName=bulkUpload&fileType="+ fileType1+ "&contentOfFile="+ contentOfFile1;	
	
	response.sendRedirect(redirectURL);
	
%>
