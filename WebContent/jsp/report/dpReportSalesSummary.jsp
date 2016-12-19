<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sales Summary Report</title>

       <link href="${pageContext.request.contextPath}/theme/displayTag.css" rel="stylesheet" type="text/css" />
    </head>
    <body>

<display:table id="data" style="border=1px" requestURI="#" name="sessionScope.DPReportForm.reportDataList" decorator="org.displaytag.decorator.TotalTableDecorator" pagesize="10"  export="true" >
			<display:column property="retName" title="RETAILER NAME" sortable="true" />
			<display:column property="category" title="CATEGORY" sortable="true" />
			<display:column property="beatName" title="BEAT NAME" sortable="true" />
			<display:column property="fseName" title="FSE NAME" sortable="true" />
            <display:column property="zsmName" title="ZSM" sortable="true"/>
            <display:column property="tmName" title="TSM" sortable="true" />
            <display:column property="grossActivations" title="GROSS ACTIVATIONS" sortable="true" />
			<display:column property="easyRecharge" title="EASY RECHARGE" sortable="true" />
            <display:setProperty name="export.xls" value="true" />
			<display:setProperty name="export.pdf" value="true" />
</display:table>

</BODY>
</html>
