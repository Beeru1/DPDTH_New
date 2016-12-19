<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page 
language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
import="com.ibm.hbo.dto.HBOUserBean,java.util.ArrayList,java.util.Iterator,java.util.HashMap"
%>
<html>  
<head>
<title>HBO PORTAL</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="./theme/text.css" rel="stylesheet" type="text/css">
</head>

<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">
<%try {

	HBOUserBean HBOUserBean = (HBOUserBean) session.getAttribute("USER_INFO");

	HashMap moduleData = HBOUserBean.getModuleData();

	ArrayList modules1 = null;
	ArrayList modules2 = null;
	ArrayList modules3 = null;
	ArrayList modules4 = null;

	Iterator iterator1 = (moduleData.keySet()).iterator();
	while (iterator1.hasNext()) {
		String categoryId = iterator1.next().toString();

		if (categoryId.equals("1")) {
			modules1 = (ArrayList) moduleData.get(categoryId);

		}
		if (categoryId.equals("2")) {
			modules2 = (ArrayList) moduleData.get(categoryId);
		}
		if (categoryId.equals("3")) {
			modules3 = (ArrayList) moduleData.get(categoryId);
		}
		if (categoryId.equals("4")) {
			modules4 = (ArrayList) moduleData.get(categoryId);
		}

	}

	ArrayList categoryList1 = null;
	ArrayList categoryList2 = null;
	ArrayList categoryList3 = null;
	ArrayList categoryList4 = null;
	String categoryName1 = null;
	String categoryName2 = null;
	String categoryName3 = null;
	String categoryName4 = null;
	HashMap categoryMap = (HashMap) HBOUserBean.getCategoryList();
	Iterator iterator = (categoryMap.keySet()).iterator();
	while (iterator.hasNext()) {
		String category = iterator.next().toString();
		if (category.equals("1")) {
			categoryList1 = (ArrayList) categoryMap.get(category);
			categoryName1 = (String) categoryList1.get(0);
		}
		if (category.equals("2")) {
			categoryList2 = (ArrayList) categoryMap.get(category);
			categoryName2 = (String) categoryList2.get(0);
		}
		if (category.equals("3")) {
			categoryList3 = (ArrayList) categoryMap.get(category);
			categoryName3 = (String) categoryList3.get(0);

		}
		if (category.equals("4")) {
			categoryList4 = (ArrayList) categoryMap.get(category);
			categoryName4 = (String) categoryList4.get(0);

		}

	}%>
<table width="189" height="100%" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<td height="59" valign="top" align="left"><img src="./images/HBO.gif"
			width="189" height="59"></td>
	</tr>


	<tr>
		<td align="center" valign="top">
		<table width="184" border="0" cellpadding="3" cellspacing="0"
			class="text">
			<tr>
				<td colspan="2"><img src="./images/spacer.gif" width="10" height="3"></td>
			</tr>
			<tr>
				<td colspan="2" class="text"><font color="#772626"
					face="Arial, Helvetica, sans-serif"><strong><%=categoryName1%></strong></font></td>
			</tr>
			<%for (int i = 0; i < modules1.size(); i++) {
	HashMap resultMap = (HashMap) modules1.get(i);%>

			<tr>
				<td width="10"><img src="./images/dots.gif" width="10" height="10"></td>
				<td width="162"><a class="red"
					href="<%=resultMap.get("MODULE_URL")%>"> <%=resultMap.get("MODULE_NAME")%>
				</a></td>
			</tr>
			<%}%>

			<tr>
				<td colspan="2"><img src="./images/spacer.gif" width="10" height="3"></td>
			</tr>
			<tr>
				<td colspan="2" class="text"><font color="#772626"
					face="Arial, Helvetica, sans-serif"><strong><%=categoryName2%></strong></font></td>
			</tr>
			<%for (int i = 0; i < modules2.size(); i++) {
	HashMap resultMap = (HashMap) modules2.get(i);%>
			<tr>
				<td width="10"><img src="./images/dots.gif" width="10" height="10"></td>
				<td width="162"><a class="red"
					href="<%=resultMap.get(" MODULE_URL")%>"> <%=resultMap.get("MODULE_NAME")%>
				</a></td>
			</tr>
			<%}%>
			<tr>
				<td colspan="2"><img src="./images/spacer.gif" width="10" height="3"></td>
			</tr>
			<tr>
				<td colspan="2" class="text"><font color="#772626"
					face="Arial, Helvetica, sans-serif"><strong><%=categoryName3%></strong></font></td>
			</tr>
			<%for (int i = 0; i < modules3.size(); i++) {
	HashMap resultMap = (HashMap) modules3.get(i);%>
			<tr>
				<td width="10"><img src="./images/dots.gif" width="10" height="10"></td>
				<td width="162"><a class="red"
					href="<%=resultMap.get(" MODULE_URL")%>"> <%=resultMap.get("MODULE_NAME")%>
				</a></td>
			</tr>
			<%}%>
			<tr>
				<td colspan="2"><img src="./images/spacer.gif" width="10" height="3"></td>
			</tr>
			<tr>
				<td colspan="2" class="text"><font color="#772626"
					face="Arial, Helvetica, sans-serif"><strong><%=categoryName4%></strong></font></td>
			</tr>
			<%for (int i = 0; i < modules4.size(); i++) {
	HashMap resultMap = (HashMap) modules4.get(i);%>
			<tr>
				<td width="10"><img src="./images/dots.gif" width="10" height="10"></td>
				<td width="162"><a class="red"
					href="<%=resultMap.get(" MODULE_URL")%>"> <%=resultMap.get("MODULE_NAME")%>
				</a></td>
			</tr>
			<%}
} catch (Exception e) {
}%>
		</table>
		</td>
	</tr>
</table>
</body>
</html>
