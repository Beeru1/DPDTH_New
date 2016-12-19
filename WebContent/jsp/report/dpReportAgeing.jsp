<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ageing Report</title>

       <link href="${pageContext.request.contextPath}/theme/displayTag.css" rel="stylesheet" type="text/css" />
    </head>
    <body>

<display:table id="data" style="border=1px" requestURI="#" name="sessionScope.DPReportForm.reportDataList" decorator="org.displaytag.decorator.TotalTableDecorator" pagesize="10"  export="true" >
            <display:column property="hub" title="HUB" sortable="true" />
			<display:column property="circle" title="CIRCLE" sortable="true" />
            <display:column property="zone" title="ZONE" sortable="true" />
            <display:column property="distributorName" title="DISTRIBUTOR" sortable="true" />
            <display:column property="retmsisdn" title="RETAILER CODE" sortable="true" />
			<display:column property="retName" title="RETAILER NAME" sortable="true" />
			<display:column property="fseName" title="FSE NAME" sortable="true" />
            <display:column property="prodType" title="PRODUCT CATEGORY" sortable="true" group="1"/>
            <display:column property="prodCat" title="PRODUCT TYPE" sortable="true" />
            <display:column property="noLtEq90" title="<=90" sortable="true" total="true"/>
			<display:column property="noGt90" title=">90" sortable="true" total="true"/>
            <display:setProperty name="export.xls" value="true" />
			<display:setProperty name="export.pdf" value="true" />
</display:table>

</BODY>
</html>
