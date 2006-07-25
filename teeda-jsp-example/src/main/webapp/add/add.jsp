<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
  <title>add</title>
</head>
<body>
<f:view>
  <h:form>
    <h:messages globalOnly="false" showDetail="true"/>
    <h:inputText value="#{addBean.arg1}" required="true"/> +
    <h:inputText value="#{addBean.arg2}" required="true"/> =
    <h:outputText value="#{addBean.result}"/>
    <h:commandButton action="#{addBean.calculate}" value="calculate"/>
  </h:form>
</f:view>
</body>
</html>
