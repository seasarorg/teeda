<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://www.seasar.org/teeda/extension" prefix="te" %>
<html>
<head>
 <title>add</title>
</head>
<body>
<f:view>
 <h:form>
   <h:messages globalOnly="false" showDetail="true"/>
   <te:inputCommaText id="text1" value="#{addExtBean.arg1}" required="true" fraction="4" />
   <h:message for="text1"/>
    +
   <te:inputCommaText id="text2" value="#{addExtBean.arg2}" required="true" fraction="2" />
   <h:message for="text2"/>
    =
   <h:outputText value="#{addExtBean.result}"/>
   <h:commandButton action="#{addExtBean.calculate}" value="calculate"/>
 </h:form>
</f:view>
</body>
</html>