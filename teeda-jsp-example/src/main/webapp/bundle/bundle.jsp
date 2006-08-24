<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
  <title>bundle</title>
</head>
<body>
<f:view>
	<f:verbatim>aaa: </f:verbatim><h:outputText value="#{exampleMessages.aaa}"/>
	<f:verbatim><br /></f:verbatim>
	<f:verbatim>bbb: </f:verbatim><h:outputText value="#{exampleMessages.bbb}"/>
</f:view>
</body>
</html>
