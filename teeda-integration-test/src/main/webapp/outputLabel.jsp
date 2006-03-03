<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<title>this is outputLabel.jsp</title>
</head>
<body>
<f:view>
    <%--
	<h:outputLabel for="hello" value="helloLabel">
		<h:outputText id="hello" value="Hello OutputLabel"/>
	</h:outputLabel>
	--%>
	<h:outputText id="hello" value="Hello OutputLabel"/>
	<h:outputLabel for="hello" value="helloLabel"/>
</f:view>
</body>
</html>
