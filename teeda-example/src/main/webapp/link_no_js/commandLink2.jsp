<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<title>commandLink.jsp</title>
</head>
<body>
<f:view>
	<h:form id="commandLinkForm">
		<h:commandLink id="link1" action="#{commandLinkBean2.doHoge}">
			<h:outputText value="foo"/>
		</h:commandLink>
	</h:form>
</f:view>
</body>
</html>
