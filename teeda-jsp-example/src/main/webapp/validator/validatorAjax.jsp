<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
  <title>validator Ajax</title>
</head>
<body>
<script language="JavaScript" src="../js/kumu/ajax.js"></script>
<script type="text/javascript">
// <![CDATA[
function checkName(i){
  s = i.value;
  Kumu.Ajax.executeTeedaAjax(nameCheckBean_isExists, [s]);
}
function nameCheckBean_isExists(response){
  if(response){
    document.getElementById("nameError").innerHTML
      = '<span style="color:red;">This name is already regist</span>';
  }else{
    document.getElementById("nameError").innerHTML='';
  }
}
// ]]>
</script>
<f:view>
  <h:form>
    <h:inputText id="name" required="true"
        onblur="javascript:checkName(this);" />
    <span id="nameError"></span>
    <br/>
    <h:commandButton value="test" />
  </h:form>
</f:view>
</body>
</html>
