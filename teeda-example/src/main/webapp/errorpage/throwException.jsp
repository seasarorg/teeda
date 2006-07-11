<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<title>throwException.jsp</title>
</head>
<body>
<f:view>
	<h:form>
		<h:commandButton id="submit1" action="#{errorPageBean.throwException}"/>
	</h:form>
</f:view>
</body>
</html>
