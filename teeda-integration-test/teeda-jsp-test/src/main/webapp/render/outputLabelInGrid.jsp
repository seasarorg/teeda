<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<title>this is outputLabelInGrid.jsp</title>
</head>
<body>
<f:view>
	<h:panelGrid columns="2" border="1">
		<h:outputLabel for="foo" value="helloLabel1" id="lbl1">
			<h:outputText id="foo" value="Hello OutputText1"/>
		</h:outputLabel>
		<h:outputLabel for="bar" value="helloLabel2" id="lbl2">
			<h:outputText id="bar" value="Hello OutputText2"/>
		</h:outputLabel>
	</h:panelGrid>
</f:view>
</body>
</html>
