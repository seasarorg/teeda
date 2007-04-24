<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<title>dateTime.jsp</title>
</head>
<body>
<f:view>
	<h:form id="dateTimeForm">
		<h:inputText id="aaa" value="#{dateBean.a}" converter="inputDateTimeConverter"/>
		<h:outputText id="bbb" value="#{dateBean.a}" converter="outputDateTimeConverter"/>
		<h:commandButton id="submit1"/>
	</h:form>
</f:view>
</body>
</html>
