<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
	<title>validator x DI</title>
</head>
<body>
<f:view>
	<h:form>
		<h:messages globalOnly="false" showDetail="true"/>
		<h:outputText value="name(minimum 2 letters and max 10 letters)" /><br/>
		<h:inputText id="name" value="#{validatorBean.name}" validator="#{validatorBean.validate}">
			<f:validator validatorId="lengthValidator1" />
		</h:inputText>
		<h:commandButton id="button1"/>
	</h:form>
</f:view>
</body>
</html>
