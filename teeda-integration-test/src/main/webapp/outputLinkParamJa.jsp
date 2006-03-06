<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<title>outputLinkParamJa.jsp</title>
</head>
<body>
<f:view>
	<h:outputLink id="link1" value="outputLinkParam.jsp">
		<f:param name="あ" value="いう"/>
		<h:outputText value="わをん" />
	</h:outputLink>
</f:view>
</body>
</html>
