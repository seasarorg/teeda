<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<title>commandButton.jsp</title>
</head>
<body>
<f:view>
	<h:form id="commandButtonForm">
		<h:inputText id="text1" value="#{commandLinkBean.a}"/>
		<h:commandButton id="link1" action="#{commandLinkBean.countUp}">
			<h:outputText value="foo"/>
		</h:commandButton>
	</h:form>
</f:view>
</body>
</html>
