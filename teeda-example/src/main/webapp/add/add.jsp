<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title>add</title>
</head>
<body>
<f:view>
	<h:form>
		<h:inputText value="#{addDto.arg1}"/> +
		<h:inputText value="#{addDto.arg2}"/> =
		<h:outputText value="#{addDto.result}"/>
		<h:commandButton action="#{addAction.calculate}" value="calculate"/>
	</h:form>
</f:view>
</body>
</html>
