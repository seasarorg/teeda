<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
  <title>rendered.jsp</title>
</head>
<body>
<f:view>
  <h:form>
	<h:inputText id="a" value="#{foo}"/>
    <h:commandButton action="/render/rendered.jsp" id="submit1" value="go"/>
	<h:outputText id="b1" rendered="#{foo != null}" value="hoge"/>
  </h:form>
</f:view>
</body>
</html>