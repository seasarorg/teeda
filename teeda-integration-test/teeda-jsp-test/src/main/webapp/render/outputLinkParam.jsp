<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<title>outputLinkParam.jsp</title>
</head>
<body>
<f:view>
	<h:outputLink id="link1" value="outputLinkParam.jsp">
		<f:param name="a" value="1"/>
		<f:param name="b" value="2"/>
		<h:outputText value="xyz" />
	</h:outputLink>
</f:view>
</body>
</html>
