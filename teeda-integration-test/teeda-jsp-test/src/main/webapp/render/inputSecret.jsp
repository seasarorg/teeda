<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<title>inputSecret.jsp</title>
</head>
<body>
<f:view>
	<h:form id="inputSecretForm">
		<h:inputSecret id="secretA" value="#{secretBean.a}"/>
		<h:inputSecret id="secretB" redisplay="true" value="#{secretBean.b}"/>
		<h:commandButton id="submit1"/>
	</h:form>
</f:view>
</body>
</html>
