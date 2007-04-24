<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<title>inputTextarea.jsp</title>
</head>
<body>
<f:view>
	<h:form id="inputTextareaForm">
		<h:inputTextarea id="textareaA" value="#{textBean.a}"/>
		<h:outputText id="outputA" value="#{textBean.a}"/>
		<h:commandButton id="submitA"/>
	</h:form>
</f:view>
</body>
</html>
