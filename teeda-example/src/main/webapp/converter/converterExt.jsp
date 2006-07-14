<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://www.seasar.org/teeda/extension" prefix="te" %>
<html>
<head>
	<title>extension converter</title>
</head>
<body>
<f:view>
	<h:form>
		<h:outputText value="Extension converter sample"/>
		<hr/>
		<h:messages globalOnly="false" showDetail="true"/>
		<h:inputText id="text1" value="#{converterBean.date1}">
			<f:convertDateTime pattern="yyyyMMdd"/>
		</h:inputText>
		<h:outputText value="#{converterBean.date1}">
			<te:converter converterId="javax.faces.DateTime">
				<f:param name="pattern" value="yyyy-MM-dd"/>
			</te:converter>
		</h:outputText>
		<h:commandButton id="button1"/>
	</h:form>
</f:view>
</body>
</html>
