<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title>checkbox</title>
</head>
<body>
<f:view>
	<h:form>
		<h:selectBooleanCheckbox value="#{checkboxDtoDI.teeda}"/>
		<h:outputText value="Teeda!"/><br/>
		<h:outputText value="#{checkboxDtoDI.teeda}"/><br/>
		<input type="submit" value="submit"/>
	</h:form>
</f:view>
</body>
</html>
