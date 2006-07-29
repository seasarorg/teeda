<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://www.seasar.org/teeda/extension" prefix="te"%>
<html>
<head>
<title>inputCommaText.jsp</title>
</head>
<body>
<f:view>
	<h:form>
		<h:messages globalOnly="false" showDetail="true"/>
		<te:inputCommaText value="#{inputCommaTextBean.value}"/>
		<h:outputText value="#{inputCommaTextBean.value}"/>
		<br />
		<input type="submit" value="submit"/>
	</h:form>
</f:view>
</body>
</html>
