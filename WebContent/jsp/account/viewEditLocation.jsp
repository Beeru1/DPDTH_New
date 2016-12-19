<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View/Edit Location</title>
</head>
<body>

<form>
<table align="center">
	<tr align="center">
		<td width="155" class="txt"><STRONG><FONT 
  				color="#000000"><bean:message bundle="hboView" key="viewEditLocation.selectCircle"/></FONT></STRONG><font color="red">*</font></td>
		<td>
		<html:select property="circleId" onchange="" styleClass="box">
					<option value="">--Select--</option>
				<logic:notEmpty property="" name="">
				<bean:define id=""
					name="" property="" />
					<html:options labelProperty="bname" property="bcode"
						collection="category" />
				</logic:notEmpty>	
				</html:select>
		</td>
	</tr>
	<tr align="center">
		<td width="155" class="txt"><STRONG><FONT 
  				color="#000000"><bean:message bundle="hboView" key="viewEditLocation.locationType"/></FONT></STRONG><font color="red">*</font></td>
		<td>
		<html:select property="locationType" onchange="" styleClass="box">
					<option value="">--Select--</option>
				<logic:notEmpty property="" name="">
				<bean:define id=""
					name="" property="" />
					<html:options labelProperty="bname" property="bcode"
						collection="category" />
				</logic:notEmpty>	
				</html:select>
		</td>
	</tr>
</table>
</form>
</body>
</html>