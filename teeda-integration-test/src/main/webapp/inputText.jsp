<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<title>inputText.jsp</title>
</head>
<body>
<f:view>
	<h:form id="inputTextForm">
		<h:inputText id="x" value="#{calculatorBean.x}"/>
		<h:inputText id="y" value="#{calculatorBean.y}"/>
		<h:outputText id="result" value="#{calculatorBean.result}"/>
		<h:commandButton id="multiply" action="#{calculatorBean.multiply}"/>
		<h:commandButton id="subtract" action="#{calculatorBean.subtract}"/>
	</h:form>
</f:view>
</body>
</html>
