<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page isErrorPage="true"%>
<html:html>
<TITLE><bean:message bundle="view" key="application.title" /></TITLE>
<FRAMESET cols="0%, 100%">
	 <FRAME />
    <FRAME src="<%=request.getContextPath()%>/jsp/authentication/login.jsp" name="login" />
</FRAMESET>
</html:html>