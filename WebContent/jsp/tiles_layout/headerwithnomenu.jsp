<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="com.ibm.hbo.dto.HBOUserBean,java.util.ArrayList,java.util.Iterator,java.util.HashMap"%>
<HTML>
<%
	// set this page to not be cached by the browser
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
	response.setHeader("Cache-Control", "no-cache");
%>
<%
			HBOUserBean HBOUserBean = (HBOUserBean) session
			.getAttribute("USER_INFO");
%>
<HEAD>
<TITLE>HBO Process</TITLE>
<LINK href="style/style.css" type=text/css rel=stylesheet>

<%
	String rowDark = "#ffe4e1";
	String rowLight = "#FFFFFF";
%>

</HEAD>
<BODY bgColor=#ffffff leftMargin=0 topMargin=0 marginheight="0"
	marginwidth="0">
<TABLE id=TblTop cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TBODY>
		<TR>
			<TD vAlign=top align=left width="100%">
			<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
				<TBODY>
					<TR>
						<TD><IMG alt="" src="images/header.gif"></TD>
						<td></td>
						<td></td>
					</TR>
					<TR>
						<TD bgColor=#999999 colSpan=3><IMG height=1
							src="images/spacer.gif" width=1></TD>
					</TR>
					<TR vAlign=top align=left>
						<TD height=43><IMG height=43 src="images/h_Area.jpg"
							width=800></TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
</BODY>

</HTML>
