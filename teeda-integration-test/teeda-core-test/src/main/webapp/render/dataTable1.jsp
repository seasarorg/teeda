<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<title>dataTable1.jsp</title>
</head>
<body>
<f:view>
	<h:dataTable value="#{dataTableBean.items}" var="item" border="1">
		<h:column>
			<h:outputText value="#{item.name}" />
		</h:column>
		<h:column>
			<h:outputText value="#{item.price}" />
		</h:column>
	</h:dataTable>
</f:view>
</body>
</html>
