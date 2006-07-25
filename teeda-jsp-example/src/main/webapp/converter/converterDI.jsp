<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
	<title>converter</title>
</head>
<body>
<f:view>
	<h:form>
		<h:messages globalOnly="false" showDetail="true"/>
		<h:inputText id="text1" value="#{converterBean.date1}" converter="#{inputDateTimeConverter}" />
		<h:outputText value="#{converterBean.date1}" converter="#{outputDateTimeConverter}" />
		<h:commandButton id="button1"/>
	</h:form>
</f:view>
</body>
</html>
