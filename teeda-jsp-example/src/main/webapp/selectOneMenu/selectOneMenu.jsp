<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<title>selectOneMenu.jsp</title>
</head>
<body>
<f:view>
<%--

	Aaa:<br />
    <select m:value="#{selectOneMenuDto.aaa}" m:required="true"
    	m:label="Aaa" size="2">
    	<option>Please select</option>
        <option value="1">One</option>
        <option value="2">Two</option>
        <option value="3">Three</option>
    </select><br />
    <span m:value="#{selectOneMenuDto.aaa}"/><br />

	Bbb:<br />
    <select m:value="#{selectOneMenuDto.bbb}"
    	m:label="Bbb"
        m:items="#{selectOneMenuBbbItems}">
        <option value="">Please select</option>
        <option value="1">One</option>
        <option value="2">Two</option>
    </select><br />
    <span m:value="#{selectOneMenuDto.bbb}"/><br />

	Ccc:<br />
    <select m:value="#{selectOneMenuDto.ccc}"
    	m:label="Ccc"
        m:items="#{selectOneMenuCccItems}"
        m:itemValue="deptno"
        m:itemLabel="dname"
        m:nullLabel="Please select">
        <option value="">Please select</option>
        <option value="10">ACOUNTING</option>
    </select><br />
    <span m:value="#{selectOneMenuDto.ccc}"/><br />

    <input type="submit" value="submit"/>

--%>

	<h:form>
		<h:messages globalOnly="false" showDetail="true"/>
		<h:selectOneMenu value="#{selectOneMenu.aaa}" required="true">
			<f:selectItem itemLabel="One" itemValue="1"/>
			<f:selectItem itemLabel="Two" itemValue="2"/>
			<f:selectItem itemLabel="Three" itemValue="3"/>			
		</h:selectOneMenu>
		<br/>
	    <h:outputText value="#{selectOneMenu.aaa}"/>
		<br/>
		<br/>
		<h:selectOneMenu value="#{selectOneMenu.bbb}" required="true">
			<f:selectItems value="#{selectOneMenuBbbItems}"/>
		</h:selectOneMenu>
		<br/>
	    <h:outputText value="#{selectOneMenu.bbb}"/>
		<br/>
	    <h:commandButton action="#{selectOneMenu.doAction}" value="calculate"/>
	</h:form>
</f:view>
</body>
</html>
