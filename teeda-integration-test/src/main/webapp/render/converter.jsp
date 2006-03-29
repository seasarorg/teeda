<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<title>converter.jsp</title>
</head>
<body>
<f:view>
	<h:form id="converterForm">
		<h:inputText id="text1" value="#{converterBean.date1}">
			<f:convertDateTime pattern="yyyyMMdd"/>
		</h:inputText>
		<h:outputText id="result" value="#{converterBean.date1}">
			<f:convertDateTime pattern="yyyy/MM/dd"/>
		</h:outputText>
		<h:commandButton id="button1" value="convert"/>
	</h:form>
</f:view>
</body>
</html>
