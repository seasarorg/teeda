<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<title>inputTextarea.jsp</title>
</head>
<body>
<f:view>
	<h:form>
		<h:messages globalOnly="false" showDetail="true"/>
		<h:inputTextarea rows="5" cols="20"
			value="#{inputTextareaDtoDI.aaa}">					
		</h:inputTextarea>
		<br/>
		<input type="submit" value="submit"/>
	</h:form>
</f:view>
</body>
</html>
