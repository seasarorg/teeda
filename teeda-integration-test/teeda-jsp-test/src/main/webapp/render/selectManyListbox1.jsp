<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<title>selectManyListbox1.jsp</title>
</head>
<body>
<f:view>
	<h:form id="selectManyListboxForm">
		<h:selectManyListbox id="aaa" value="#{selectManyListboxAaaBean.aaaSelected}">
			<f:selectItem itemLabel="One" itemValue="1"/>
			<f:selectItem itemLabel="Two" itemValue="2"/>
			<f:selectItem itemLabel="Three" itemValue="3"/>
		</h:selectManyListbox>
		<h:outputText id="selectedString" value="#{selectManyListboxAaaBean.selectedString}"/>
		<h:commandButton id="submit1"/>
	</h:form>
</f:view>
</body>
</html>
