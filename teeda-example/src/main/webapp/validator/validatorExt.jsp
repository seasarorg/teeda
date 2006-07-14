<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://www.seasar.org/teeda/extension" prefix="te" %>
<html>
<head>
	<title>extension validator using with f:param</title>
</head>
<body>
<f:view>
	<h:form>
		<h:outputText value="name(4 letters or more)" />
		<h:inputText id="name1" value="#{validatorBean.name}">
			<te:validator validatorId="lengthValidator2">
				<f:param name="minimum" value="4" />
				<f:param name="for" value="button1" />
				<f:param name="minimumMessageId" value="hoge.error1" />
			</te:validator>
		</h:inputText>
		<h:commandButton id="button1" value="validation button"/>
		<h:commandButton id="button2" value="no validation"/>
		<h:message for="name1"/>
	</h:form>
</f:view>
</body>
</html>
