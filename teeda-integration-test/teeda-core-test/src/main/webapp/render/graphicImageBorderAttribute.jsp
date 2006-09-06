<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<title>graphicImageBorderAttribute.jsp</title>
</head>
<body>
<f:view>
	<h:graphicImage id="imageA" value="/image/item_large_b.gif" title="image title">
		<f:attribute name="border" value="0"/>
	</h:graphicImage>
</f:view>
</body>
</html>
