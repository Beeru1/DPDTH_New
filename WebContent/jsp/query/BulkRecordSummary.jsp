
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %> 

<html:html>
<HEAD>
<TITLE>Bulk Record Summary</TITLE>
</HEAD>
<Body>
		<logic:notEmpty name="HBOFileUploadForm" property="status"> 
		<TABLE align="center" border="1" width="50%">
		<TR align="center">
				 <TD height="27" ><B>Uploaded File Status</B></TD>
			</TR>
			<TR align="center">
				 <TD height="27" ><bean:write name="HBOFileUploadForm" property ="status"/></TD>
			</TR>
		</TABLE>	
		</logic:notEmpty>	
		<logic:notEmpty name="HBOFileUploadForm" property="statusDetails"> 	
		<TABLE align="center" border="1" width="50%">
			<TR align="center">
				 <TD height="27" ><B>Uploaded File Status Details</B></TD>
			</TR>
			<TR align="center">
				 <TD height="27" ><bean:write name="HBOFileUploadForm" property ="statusDetails"/></TD>
			</TR>
			</TABLE>	
		</logic:notEmpty>					

</body>	
</html:html>