<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<html>
	<head>
		<title>changePage1.jsp</title>
	</head>
	<body>
		<f:view>
			<h2>Changing page.</h2>
			<h:form id="changingPagesForm1">
				<h:commandButton id="button1" type="submit" value="submit" action="#{changePageBean.doAction}" />
			</h:form>
		</f:view>
	</body>
</html>