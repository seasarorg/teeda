<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
	<title>validator</title>
</head>
<body>
<f:view>
	<h:form>
		<h:messages globalOnly="false" showDetail="true"/>
		<h:outputText value="name(2 letters or more)" />
		<h:inputText id="name" value="#{validatorBean.name}">
			<f:validateLength minimum="2" />
		</h:inputText>
		<h:commandButton id="button1"/>
	</h:form>
</f:view>
</body>
</html>
