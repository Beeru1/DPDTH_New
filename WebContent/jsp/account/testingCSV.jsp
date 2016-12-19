<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/csv");
	response.setHeader("content-Disposition",
			"attachment;filename=listAggregate.csv");
%>
<title>DISTRIBUTOR PORTAL</title>
</head>
<body>
 <html:form action="initCreateAccount.do?methodName=downloadAggregateList">
hello !!! m in CSV file....
</html:form>
</body>
</html>