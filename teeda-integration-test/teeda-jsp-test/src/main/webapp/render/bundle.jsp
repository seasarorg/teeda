<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<f:view>
<head>
<title><h:outputText value="#{appMessages.title}"/></title>
</head>
<body>
	<h:outputText id="greeting" value="#{appMessages.greeting}"/>
</body>
</f:view>
</html>
