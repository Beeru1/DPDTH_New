
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html:html>
<BODY background="<%=request.getContextPath()%>/images/bg_main.gif" >
<table width="100%" border="0" cellspacing="0" cellpadding="3" height="17">

  <tr align="left" valign="top"> 
    <td colspan="2" background="<%=request.getContextPath()%>/images/footer_bg.gif" height="17"> 
      <table width="100%" border="0" cellspacing="0" cellpadding="3" height="17">
        <tr> 
          <td align="center" height="17"><font color="#D8F1F5" size="1" face="Verdana, Arial, Helvetica, sans-serif">Copyright © 2013 IBM, All Rights Reserved.</font></td>
        </tr>
      </table>
    </td>
  </tr>
  	<form name="menuForm">
    </form>
  </table>
  

  <script type="text/javascript">
	
	function menulink(url)
	{
		document.menuForm.action=url;
		document.menuForm.method="post";
		document.menuForm.submit();
	}
</script>
</BODY>
</html:html>
