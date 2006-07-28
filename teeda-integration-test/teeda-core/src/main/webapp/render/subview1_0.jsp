<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<title>this is subview1_0.jsp</title>
</head>
<body>
<f:view>
	<f:subview id="sub1">
		<jsp:include page="subview1_1.jsp"/>
	</f:subview>
	<f:subview id="sub2">
		<%@ include file="subview1_2.jsp" %>
	</f:subview>
</f:view>
</body>
</html>
