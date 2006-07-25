<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<title>selectManyCheckbox.jsp</title>
</head>
<body>
<f:view>
	<h:form>
		<h:messages globalOnly="false" showDetail="true"/>
		<h:selectManyCheckbox>
			<f:selectItem itemLabel="One" itemValue="1"/>
			<f:selectItem itemLabel="Two" itemValue="2"/>
			<f:selectItem itemLabel="Three" itemValue="3"/>			
		</h:selectManyCheckbox>
	</h:form>
</f:view>
</body>
</html>
