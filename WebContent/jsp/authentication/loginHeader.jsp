<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<HTML>
<HEAD>

<META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<LINK href="<%=request.getContextPath()%>/theme/text.css" rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/theme/chromestyle2.css" />
<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/scripts/chrome.js"></SCRIPT>
</HEAD>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif"  bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table border="0" cellpadding="0" cellspacing="1" bgcolor="#000000" width="100%" valign="top">
	<TR bgcolor="FE000C" valign="top" align="left">
		<TD height="17"  bgcolor="cd0504" style="padding-left:5px;padding-top:3px;"><SPAN
			class="head1">&nbsp;</SPAN><SPAN class="yellow10Bold"> &nbsp;
		</SPAN></TD>

		<td height="17" align="right" bgcolor="cd0504" style="padding-right:5px;">
		<span class="white10" id="clock"><SCRIPT language="javascript" src="<%=request.getContextPath()%>/scripts/DateTime.js"></SCRIPT> <SCRIPT>getthedate();dateTime();</SCRIPT></span></td>
	</TR>
	<TR bgcolor="#FFFFFF">
		<TD width="167" rowspan="2" align="left" height="90" valign="top"><IMG
			src="<%=request.getContextPath()%>/images/pic01_1.jpg" width="167" height="90" alt=""></TD>
		<TD height="19" align="left" valign="center" bgcolor="cd0504"><!--1st drop down menu -->

		<!--2nd drop down menu --> <IMG src="<%=request.getContextPath()%>/images/spacer.gif" width="12"
			height="5" alt=""></TD>
	</TR>
  <tr bgcolor="#FFFFFF"> 
    <td height="64" align="left" valign="top" background="<%=request.getContextPath()%>/images/t_bg.gif"><img
			src="<%=request.getContextPath()%>\images\distributor portal.jpg"
			width="556" height="64"></td>
  </tr>
</TABLE>
</BODY>
</HTML>

