<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<title>selectOneListbox.jsp</title>
</head>
<body>
<f:view>
	<h:form>
		<h:messages globalOnly="false" showDetail="true"/>
		<h:selectOneListbox value="#{selectOneListbox.aaa}" required="true">
			<f:selectItem itemLabel="One" itemValue="1"/>
			<f:selectItem itemLabel="Two" itemValue="2"/>
			<f:selectItem itemLabel="Three" itemValue="3"/>
		</h:selectOneListbox>
		<br/>
	    <h:outputText value="#{selectOneListbox.aaa}"/>
		<br/>
		<br/>
		<h:selectOneListbox value="#{selectOneListbox.bbb}">
			<f:selectItems value="#{selectOneListboxBbbItems}"/>
		</h:selectOneListbox>
		<br/>
	    <h:outputText value="#{selectOneListbox.bbb}"/>
		<br/>
	    <h:commandButton action="#{selectOneListbox.doAction}" value="calculate"/>
	</h:form>
</f:view>
</body>
</html>
