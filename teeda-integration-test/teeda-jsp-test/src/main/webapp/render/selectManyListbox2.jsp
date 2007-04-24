<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<title>selectManyListbox2.jsp</title>
</head>
<body>
<f:view>
	<h:form id="selectManyListboxForm">
		<h:selectManyListbox id="aaa" value="#{selectManyListboxBbbBean.bbbSelected}">
			<f:selectItems value="#{selectManyListboxBbbBean.list}"/>
		</h:selectManyListbox>
		<h:outputText id="selectedString" value="#{selectManyListboxBbbBean.selectedString}"/>
		<h:commandButton id="submit1"/>
	</h:form>
</f:view>
</body>
</html>
