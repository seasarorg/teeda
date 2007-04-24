<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<title>verbatim</title>
</head>
<body>
<f:view>
	<h:outputText value="aaa"/>
	<f:verbatim escape="true">
		bbb
		<h:outputText value="ccc"/>
		ddd
	</f:verbatim>
	<h:outputText value="eee"/>
</f:view>
</body>
</html>
