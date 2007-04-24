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
		<h:inputText id="textA" value="#{textBean.a}"/>
		<h:outputText id="outputA" value="#{textBean.a}"/>
		<h:commandButton id="submit1"/>
	</h:form>
</f:view>
</body>
</html>
