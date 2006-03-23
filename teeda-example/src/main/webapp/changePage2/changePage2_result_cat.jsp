<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<html>
	<body bgcolor="pink">
		<f:view>
			<h2>Changing page result.</h2>
			<h:form id="changingPagesForm2_result_cat">
				<h:outputText value="#{changePageBean2.favorite}"></h:outputText>
				<br />
			</h:form>
		</f:view>
	</body>
</html>