<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<html>
	<body>
		<f:view>
			<h2>Changing page2.</h2>
			<h:form id="changingPagesForm2">
				<h3>Changing page by action</h3>
				<h:selectOneRadio id="radio1" value="#{changePageBean2.favorite}">
					<f:selectItem itemLabel="DOG" itemValue="dog"/>
					<f:selectItem itemLabel="CAT" itemValue="cat"/>
				</h:selectOneRadio>
				<br/>
				<h:commandButton type="submit" value="submit" action="#{changePageBean2.doAction}" />
			</h:form>
		</f:view>
	</body>
</html>