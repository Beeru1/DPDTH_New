<%@page import="com.ibm.dp.common.USSDConstants"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html:html>
<head>
</head>
<script language="javascript">
	
	function getLocation(flag){
	if(flag=='circle')
	{
		if(document.editForm.zoneName!= null && document.editForm.zoneName1!= null) {
			document.editForm.zoneName1.value="-1";
		}
		
	}
	
	if(validate()){
		if(document.editForm.circleCode!= null) {
			document.editForm.circleCode.value = document.editForm.circleCode1.value;
			document.editForm.circleCode1.disabled=true;
		}
	
		if(document.editForm.locMstrId!= null) {
			document.editForm.locMstrId.value = document.editForm.locMstrId1.value;
			document.editForm.locMstrId1.disabled=true;
		}
		if(document.editForm.zoneName!= null) {
			document.editForm.zoneName.value = document.editForm.zoneName1.value;
			document.editForm.zoneName1.disabled=true;
		}
		document.getElementById("flag").value= flag;
		document.editForm.action="LocationData.do?methodName=initView";
		document.editForm.submit();
		return true;
	} 	
	else
		return false;
	}
      
      function validate(){
      	if(document.getElementById("circleCode1").value=="-1"){
			document.getElementById("circleCode1").focus();
			return false;
		}
		if(document.getElementById("locMstrId1").value=="-1"){
			document.getElementById("locMstrId1").focus();
			return false;
    	 }
      		return true;
      }
      
   
      
       function viewLocation(){
         if(validate()){
			document.getElementById("methodName").value = "initView";
		}
	    else
		  return false;
	    }
	    
        function editLocation(locDataId) {
         	document.getElementById("locDataId").value = locDataId;
         	document.editForm.action="LocationData.do?methodName=initEditLocation";
          	document.editForm.submit();
          	return true;
         }
        function onLoadValues() {
			if(document.editForm.circleCode.value!= null && document.editForm.circleCode.value != -1 
			&&  document.editForm.circleCode.value != "" ) {
				document.editForm.circleCode1.value = document.editForm.circleCode.value ;
			}	
			
			if(document.editForm.locMstrId!= null && document.editForm.locMstrId.value != "0") {
				document.editForm.locMstrId1.value = document.editForm.locMstrId.value ;
			}	
			if(document.editForm.zoneName!= null && document.editForm.zoneName.value != "") {
				document.editForm.zoneName1.value = document.editForm.zoneName.value ;
			}	
		}		 
         
        function createModalWindow(targetStr,title,width,height) {
       		var myBars='directories=no,location=no,menubar=no,status=no';
				myBars+=',toolbar=no,titlebar=no';
				var myOptions='scrollbars=no,width='+width+',height='+height+',resizable=no';
				var myFeatures=myBars+','+myOptions;
				newWin=window.open(targetStr,title,myFeatures);
				return newWin;
            }
            
function submitData(nextPage,searchType,searchValue,circleId,listStatus,startDate,endDate,circleCode,locMstrId,cityName,zoneName,flag)
{
	viewLocation();

	document.forms[0].action="LocationData.do?page1="
		+ nextPage + "&searchType=" + searchType + "&searchValue="
		+ searchValue + "&circleId="
		+ circleId +  "&listStatus=" + listStatus + "&startDate=" 
		+ startDate
		+ "&circleCode=" + circleCode
		+ "&locMstrId=" + locMstrId
		+ "&cityName=" + cityName
		+ "&zoneName=" + zoneName
		+ "&flag=" + flag
		+ "&endDate=" + endDate + "&methodName=" +"initView";
	document.getElementById("page1").value="";
	document.forms[0].method="post";
	document.forms[0].submit();
}
	
	

</script>

<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0" onload="onLoadValues();">

<table cellspacing="1" cellpadding="0" border="0" width="100%"
	height="100%" bgcolor="#000000">
		<tr bgcolor="#FFFFFF">

		<td valign="top" width="100%" height="100%">
		<html:form name="editForm" type="com.ibm.dp.beans.LocationDataBean"
			action="/LocationData" method="post">
			<html:hidden property="methodName" />
			<html:hidden property="flag" />
			<html:hidden property="page1" styleId="page1" />
			
			
			<table>
			<!-- Error to be inserted here -->
			
				<tr>
					<td valign="top" width="178"><img
						src="${pageContext.request.contextPath}/images/view-edit-location.gif"
						width="505" height="22"></td>
				</tr>
				<html:errors/>
				
				<tr align="center" class="mTop5">
					<td width="178">
		<table>
			<tr>
			<td class="text pLeft15" nowrap colspan="1" ><strong><bean:message bundle="view"
			key="user.circleCode"/></strong></td>
			<td width="162">
			<html:select name="LocationDataBean" property="circleCode1" onchange="return getLocation('circle');">
			<html:option value="-1">
				<bean:message bundle="view" key="Select" />
			</html:option>
			<logic:notEmpty name="LocationDataBean" property="circleList">
				<bean:define id="circleList"
						name="LocationDataBean" property="circleList" />
				<html:options collection="circleList"
						labelProperty="circleName" property="circleId" />
			</logic:notEmpty>			
			</html:select><font color="#003399"><html:hidden name="LocationDataBean" 
						property="circleCode" 
						styleClass="box" /></font></td>
			</tr>
							
			<tr>
			<td class="text pLeft15" nowrap ><strong><bean:message bundle="view"
				key="LocationType" /></strong></td>
			<td width="162">
			<html:select name="LocationDataBean"
				property="locMstrId1" onchange="return getLocation('location');">
			<html:option value="-1"><bean:message bundle="view" key="Select" />
			</html:option>
			<html:optionsCollection name="LocationDataBean"
				property="locationList" label="locationName" value="locMstrId"/>
			</html:select><font color="#003399"><html:hidden name="LocationDataBean" 
						property="locMstrId" 
						styleClass="box" /></font></td>
			</tr>
			<!-- Displaying Zone List -->
			<logic:notEqual  value='<%= String.valueOf(USSDConstants.Zone) %>' name="LocationDataBean" property="locMstrId">
			<logic:present name="LocationDataBean" property="zoneList" >
				<tr>					
					<td class="text pLeft15" nowrap>
							<strong><bean:message bundle="view" key="zoneList" /></strong>
					</td>
					<td width="162">
					<html:select name="LocationDataBean"
						property="zoneName1" onchange="return getLocation('Zone');">
						<html:option value="-1"><bean:message bundle="view"
						key="Select" /></html:option>
						
						<html:optionsCollection name="LocationDataBean"
						property="zoneList" label="locationDataName" value="locDataId" />
					</html:select>
					<font color="#003399"><html:hidden name="LocationDataBean" 
						property="zoneName" 
						styleClass="box" /></font>
					
					</td>
				</tr>
				</logic:present>
				</logic:notEqual>
				</table></td></tr>
							<tr ></tr><tr></tr>
							
		<logic:notEmpty name="LocationDataBean" property="showLocationList">
		<tr>
			<td class="text pLeft15">
			<logic:equal name="LocationDataBean" property="locMstrId" value='<%= String.valueOf(USSDConstants.City) %>'>
				<strong><bean:message bundle="view" key="City_List" /></strong>
			</logic:equal>
			
			<logic:equal name="LocationDataBean" property="locMstrId" value='<%= String.valueOf(USSDConstants.Zone) %>'>
				<strong><bean:message bundle="view" key="Zone_List" /></strong>
			</logic:equal>
			</td>
		</tr>
			</logic:notEmpty>
		<tr>					
			<td width="178"> <table width="90%">
				<!-- Inserting Table -->
					<logic:notEmpty name="LocationDataBean" 
							property="showLocationList" scope="request" >
							<tr></tr>&nbsp;&nbsp;&nbsp;<tr></tr>
							<tr>							
								<td bgcolor="#fe3838" align="center" class="blueBg height19" >
									<span class="mLeft5 white10heading mTop5"><bean:message bundle="view"
									key="SerialNumber" /></span></td>
								<td bgcolor="#fe3838" align="center" class="blueBg height19"
									nowrap="nowrap" width="100"><span
									class="mLeft5 white10heading mTop5"><bean:message bundle="view"
									key="locationClass.LocationName" /></span></td>
								<td  bgcolor="#fe3838" align="center"
									class="blueBg height19"><span
									class="mLeft5 white10heading mTop5"><bean:message bundle="view"
									key="locationClass.Status" /></span></td>
								<td bgcolor="#fe3838" align="center" class="blueBg height19" nowrap="nowrap">
									<span class="mLeft5 white10heading mTop5"><bean:message bundle="view"
									key="locationClass.CreatedBy" /></span></td>
								<td  bgcolor="#fe3838" align="center" class="blueBg height19" >
									<span class="mLeft5 white10heading mTop5"><bean:message bundle="view"
									 key="locationClass.CreatedOn" /></span></td>
								<td  bgcolor="#fe3838" align="center"
									class="blueBg height19" width="71"><span
									class="mLeft5 white10heading mTop5"><bean:message bundle="view"
									key="locationClass.Action" /></span></td>

							</tr>
								<!-- inserting Contents -->
								<%int w = 0;%>
							
							<logic:iterate id="iterator" name="LocationDataBean" 
								property="showLocationList" scope="request" >
								<html:hidden property="locDataId" styleId="locDataId" name="iterator" />
									
									<tr BGCOLOR='<%=((w + 1) % 2 == 0 ? "#fdeeee" : "#f5d8d8")%>'>
									<td align="center" class="text"><bean:write name="iterator" property="rowNumber" /> </td>
									<td align="left" class="text"><span class="mLeft5 mTop5 mBot5"><span>
									<bean:write name="iterator" property="locationDataName" /> </span></span></td>
									<td align="left" class="text" height="29" align="center"><span
										class="mLeft5 mTop5 mBot5"><span><bean:write
										name="iterator"  property="status" /></span></span></td>
									<td align="left" class="text" height="29" 
										align="center" width="165"><span class="mLeft5 mTop5 mBot5"><span>
									<bean:write name="iterator" property="createdBy" /></span></span></td>
									<td align="left" class="text" height="29" align="center" nowrap="nowrap" width="133"><span
										class="mLeft5 mTop5 mBot5"><span><bean:write
										name="iterator" property="createdDate" /></span></span></td>
								<td class="text" align="center" width="71"> <a href="#"
										onclick="return editLocation('${iterator.locDataId}');">Edit</a></td>
									
							
									
								</tr>
								
									<%w++;%>
							</logic:iterate>
						<tr>
							<td width="20" align="left">&nbsp;&nbsp;&nbsp;</td>
						</tr>

						<TR align="center">
						<TD align="center" bgcolor="#fe3838" colspan="15"><FONT
							face="verdana" size="2"><c:if test="${PAGES!=''}">
							<c:if test="${PAGES>1}">
								    	Page:
								<c:if test="${PRE>=1}">
									<a href="#"
										onclick="submitData('<c:out value='${PRE}' />','<c:out value='${searchType}' />','<c:out value='${searchValue}' />','<c:out value='${circleId}' />','<c:out value='${listStatus}' />','<c:out value='${startDate}' />','<c:out value='${endDate}' />','<bean:write name="LocationDataBean" property="circleCode"/>','<bean:write name="LocationDataBean" property="locMstrId"/>','<bean:write name="LocationDataBean" property="cityName"/>','<bean:write name="LocationDataBean" property="zoneName"/>','<bean:write name="LocationDataBean" property="flag"/>');"><
									Prev</a>
								</c:if>

								<c:if test="${PAGES>LinksPerPage}">
									<c:set var="start_page" value="1" scope="request" />
									<c:if test="${SELECTED_PAGE+1>LinksPerPage}">
										<c:set var="start_page"
											value="${SELECTED_PAGE-(LinksPerPage/2)}" scope="request" />
									</c:if>
									<c:if test="${SELECTED_PAGE+(LinksPerPage/2)-1>=PAGES}">
										<c:set var="start_page" value="${PAGES-LinksPerPage+1}"
											scope="request" />
									</c:if>
									<c:set var="end_page" value="${start_page+LinksPerPage-1}"
										scope="request" />
									<c:forEach var="item" step="1" begin="${start_page}"
										end="${end_page}">
										<c:choose>
											<c:when test="${item==(NEXT-1)}">
												<c:out value="${item}"></c:out>
											</c:when>
											<c:otherwise>
												<a href="#"
													onclick="submitData('<c:out value="${item}"/>','<c:out value='${searchType}' />','<c:out value='${searchValue}' />','<c:out value='${circleId}' />','<c:out value='${listStatus}' />','<c:out value='${startDate}' />','<c:out value='${endDate}' />','<bean:write name="LocationDataBean" property="circleCode"/>','<bean:write name="LocationDataBean" property="locMstrId"/>','<bean:write name="LocationDataBean" property="cityName"/>','<bean:write name="LocationDataBean" property="zoneName"/>','<bean:write name="LocationDataBean" property="flag"/>');"><c:out
													value="${item}" /></a>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:if>

								<c:if test="${PAGES<=LinksPerPage}">
									<c:forEach var="item" step="1" begin="1" end="${PAGES}">
										<c:choose>
											<c:when test="${item==(NEXT-1)}">
												<c:out value="${item}"></c:out>
											</c:when>
											<c:otherwise>
												<a href="#"
													onclick="submitData('<c:out value="${item}"/>','<c:out value='${searchType}' />','<c:out value='${searchValue}' />','<c:out value='${circleId}' />','<c:out value='${listStatus}' />','<c:out value='${startDate}' />','<c:out value='${endDate}' />','<bean:write name="LocationDataBean" property="circleCode"/>','<bean:write name="LocationDataBean" property="locMstrId"/>','<bean:write name="LocationDataBean" property="cityName"/>','<bean:write name="LocationDataBean" property="zoneName"/>','<bean:write name="LocationDataBean" property="flag"/>');"><c:out
													value="${item}" /></a>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:if>
								
								<c:if test="${NEXT<=PAGES}">
									<a href="#"
										onclick="submitData('<c:out value='${NEXT}' />','<c:out value='${searchType}' />','<c:out value='${searchValue}' />','<c:out value='${circleId}' />','<c:out value='${listStatus}' />','<c:out value='${startDate}' />','<c:out value='${endDate}' />','<bean:write name="LocationDataBean" property="circleCode"/>','<bean:write name="LocationDataBean" property="locMstrId"/>','<bean:write name="LocationDataBean" property="cityName"/>','<bean:write name="LocationDataBean" property="zoneName"/>','<bean:write name="LocationDataBean" property="flag"/>');">Next></a>
								</c:if>
								
							</c:if>
						</c:if> </FONT></TD>
					</TR>
				</logic:notEmpty>
			</table></td></tr></table>
		</html:form></td>
	</tr>
</table>
</body>
</html:html>
