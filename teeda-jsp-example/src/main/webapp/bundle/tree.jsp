<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://www.seasar.org/teeda/extension" prefix="te" %>
<html>
<head>
  <title>tree</title>
</head>
<body>
<f:view>
<h:form id="form">
	<te:tree value="#{treeBean.tree}" id="client-tree" var="node" varNodeToggler="t">
		<f:facet name="folder-A">
			<h:panelGroup>
				<f:facet name="expand">
					<te:graphicImage style="border: 0px" value="yellow-folder-open.png" rendered="#{t.nodeExpanded}"/>
				</f:facet>
				<f:facet name="collapse">
                	<te:graphicImage style="border: 0px" value="yellow-folder-closed.png" rendered="#{!t.nodeExpanded}"/>
				</f:facet>
				<h:outputText value="#{node.description}"/>
			</h:panelGroup>
		</f:facet>
		<f:facet name="folder-B">
			<h:panelGroup>
				<f:facet name="expand">
					<te:graphicImage style="border: 0px" value="yellow-folder-open.png" rendered="#{t.nodeExpanded}"/>
				</f:facet>
				<f:facet name="collapse">
					<te:graphicImage style="border: 0px" value="yellow-folder-closed.png" rendered="#{!t.nodeExpanded}"/>
				</f:facet>
				<h:outputText value="#{node.description}" />
				<h:outputText value=" (#{node.childCount})" rendered="#{!empty node.children}"/>
			</h:panelGroup>
		</f:facet>
		<f:facet name="document">
			<h:panelGroup>
				<h:commandLink immediate="true" >
					<te:graphicImage style="border: 0px" value="document.png" />
					<h:outputText value="#{node.description}"/>
				</h:commandLink>
			</h:panelGroup>
		</f:facet>
	</te:tree>
</h:form>
</f:view>
</body>
</html>
