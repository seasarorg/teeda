<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<html>
<head>
<title>dataTable4.jsp</title>
</head>
<body>
<f:view>
	<h:form id="dataTableForm">
		<h:dataTable value="#{dataTableBean.items}" var="item" border="1">
			<f:facet name="header">
				<h:outputText value="products" />
			</f:facet>
			<f:facet name="footer">
				<h:panelGroup>
					<h:outputText value="#{dataTableBean.itemSize}" />
					<h:outputText value=" items." />
				</h:panelGroup>
			</f:facet>
			<h:column>
				<f:facet name="header">
					<h:outputText value="id" />
				</f:facet>
				<h:commandLink action="#{dataTableBean.viewItem}">
					<f:param name="selectedItemId" value="#{item.id}"/>
					<h:outputText value="#{item.id}" />
				</h:commandLink>
			</h:column>
			<h:column>
				<f:facet name="header">
					<h:outputText value="name" />
				</f:facet>
				<h:outputText value="#{item.name}" />
			</h:column>
			<h:column>
				<f:facet name="header">
					<h:outputText value="price" />
				</f:facet>
				<h:inputText value="#{item.price}" />
			</h:column>
		</h:dataTable>
		<h:commandButton id="submit1"/>
	</h:form>
</f:view>
</body>
</html>
