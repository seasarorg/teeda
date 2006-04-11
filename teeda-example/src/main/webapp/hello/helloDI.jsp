<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<title>hello x DI</title>
</head>
<body>
<f:view>
  <h:outputText value="#{helloDto.hello}"/>
</f:view>
</body>
</html>
