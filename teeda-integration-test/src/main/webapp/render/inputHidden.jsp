<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<title>inputHidden.jsp</title>
</head>
<body>
<f:view>
	<h:form id="inputHiddenForm">
		<h:inputText id="textA" value="#{hiddenBean.a}"/>
		<h:inputHidden id="hiddenA" value="#{hiddenBean.b}"/>
		<h:outputText id="outputA" value="#{hiddenBean.b}"/>
		<h:commandButton id="submit1" action="#{hiddenBean.doSomething}"/>
	</h:form>
</f:view>
</body>
</html>
