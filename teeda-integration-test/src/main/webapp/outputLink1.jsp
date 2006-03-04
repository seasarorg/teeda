<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<title>this is outputLink1.jsp</title>
</head>
<body>
<f:view>
	<h:outputLink id="link1" value="outputLink2.jsp">
		<h:outputText id="text1" value="foo"/>
	</h:outputLink>
</f:view>
</body>
</html>
