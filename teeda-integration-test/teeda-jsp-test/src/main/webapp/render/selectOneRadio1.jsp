<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<html>
<head>
<title>selectOneRadio1</title>
</head>
<body>
<f:view>
	<h:form id="selectOneRadio1Form">
		<h:selectOneRadio id="aaaa" value="#{selectOneRadio1Bean.bbb}">
			<f:selectItem itemLabel="One" itemValue="1" />
			<f:selectItem itemLabel="Two" itemValue="2" />
			<f:selectItem itemLabel="Three" itemValue="3" />
		</h:selectOneRadio>
		<h:outputText id="bbb" value="#{selectOneRadio1Bean.bbb}" />
		<h:commandButton id="submit1" />
	</h:form>
</f:view>
</body>
</html>
