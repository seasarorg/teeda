<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<title>selectManyListbox.jsp</title>
</head>
<body>
<f:view>
	<h:form>
		<h:messages globalOnly="false" showDetail="true"/>
		<h:selectManyListbox value="#{selectManyListbox.aaa}" required="true">
			<f:selectItem itemLabel="One" itemValue="1"/>
			<f:selectItem itemLabel="Two" itemValue="2"/>
			<f:selectItem itemLabel="Three" itemValue="3"/>
		</h:selectManyListbox>
		<br/>
	    <h:outputText value="#{selectManyListbox.bbb}"/>
	    <h:commandButton id="button" value="calculate"/>
	</h:form>
</f:view>
</body>
</html>
