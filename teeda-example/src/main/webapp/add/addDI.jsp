<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
  <title>add x DI</title>
</head>
<body>
<f:view>
  <h:form>
    <h:messages globalOnly="false" showDetail="true"/>
    <h:inputText value="#{addDto.arg1}" required="true"/> +
    <h:inputText value="#{addDto.arg2}" required="true"/> =
    <h:outputText value="#{addDto.result}"/>
    <h:commandButton action="#{addAction.calculate}" value="calculate"/>
  </h:form>
</f:view>
</body>
</html>
