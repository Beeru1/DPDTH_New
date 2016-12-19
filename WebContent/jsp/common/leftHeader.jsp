<%-- 
<%@ page import="com.ibm.virtualization.recharge.dto.UserSessionContext,com.ibm.virtualization.recharge.common.Constants" %>
--%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<%
	if (session.getAttribute(com.ibm.virtualization.recharge.common.Constants.AUTHENTICATED_USER)  == null ){
	%>
	<jsp:forward page="/jsp/authentication/index.jsp"/>
	<%
	}else{
	%>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif" >

 <table width="167" border="0" cellspacing="0" cellpadding="0" height="100%" valign="top">
 <tr bgcolor="#FFFFFF"> 
    <td valign="top" bgcolor="#B00000" style="background-image:url(<%=request.getContextPath()%>/images/left_bg.gif); background-repeat: no-repeat" width="167" align="left"> 
      <table width="167" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td valign="top"><a href="#"><img src="<%=request.getContextPath()%>/images/but01.gif" width="168" height="37" border="0"></a></td>
        </tr>
		<tr> 
          <td valign="top"><a href="#"><img src="<%=request.getContextPath()%>/images/but05.gif" width="168" height="37" border="0"></a></td>
        </tr>
      </table>
    </td>
  </tr>
</TABLE>
</BODY>
<%
}
 %>
</html>
