<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<title>outputFormat.jsp</title>
</head>
<body>
<f:view>
	<h:outputFormat id="format1" value="Hello {0}!">
		<f:param value="Teeda"/>
	</h:outputFormat>
</f:view>
</body>
</html>
