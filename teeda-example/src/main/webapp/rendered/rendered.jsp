<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
  <title>rendered without managedbean</title>
</head>
<body>
<f:view>
  <h:form>
	<h:inputText id="a" value="#{foo}"/>
    <h:commandButton action="/rendered/rendered.jsp" value="doRendered"/>
	<h:outputText rendered="#{foo == null}" value="foo is null"/>
	<h:outputText rendered="#{foo != null}" value="foo is not null"/>
  </h:form>
</f:view>
</body>
</html>
