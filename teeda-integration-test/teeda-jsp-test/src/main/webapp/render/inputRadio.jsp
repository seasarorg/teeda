<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://www.seasar.org/teeda/extension" prefix="te" %>
<html>
<head>
<title>inputRadio</title>
</head>
<body>
<f:view>
	<h:form id="inputRadioForm">
		<te:inputRadio id="aaaa" value="#{selectOneRadio1Bean.bbb}">
			<te:radioItem value="1" /><f:verbatim>One</f:verbatim>
			<te:radioItem value="2" /><f:verbatim>Two</f:verbatim>
			<te:radioItem value="3" /><f:verbatim>Three</f:verbatim>
		</te:inputRadio>
		<h:outputText id="bbb" value="#{selectOneRadio1Bean.bbb}" />
		<h:commandButton id="submit1" />
	</h:form>
</f:view>
</body>
</html>
