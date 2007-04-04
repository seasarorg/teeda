<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://www.seasar.org/teeda/extension" prefix="te"%>
<f:view>
<html>
<head>
<title>grid.jsp</title>
<style>
.gridHeader {
	background-color: green;
	color: white;
	font-weight: bold;
	overflow: hidden;
}
.gridTable {
	background-color: black;
}
.gridCell {
	background-color: white;
	overflow: hidden;
	height: 20px;
	padding: 0px;
	margin: 0px;
}
</style>
</head>

<te:body>
<h:form id="inputTextForm">
	<%--
		fooGridXY
		-> fooItemsがCollectionであることを期待
		-> fooItemsの各要素をfooへ置く
	--%>
	<te:grid id="fooGridXY" pageName="gridBean" itemsName="fooItems" width="250px" height="200px">
		<te:gridColGroup styleClass="aaa bbb teeda_leftFixed">
			<te:gridCol span="1" width="10px" styleClass="teeda_leftFixed" />
			<te:gridCol span="1" width="50px" styleClass="teeda_leftFixed" />
			<te:gridCol span="4" width="70px" />
		</te:gridColGroup>
		<te:gridHeader>
			<te:gridTr height="30px">
				<te:gridTh><h:outputText value="&nbsp;" escape="false"/></te:gridTh>
				<te:gridTh><h:outputText value="th1"/></te:gridTh>
				<te:gridTh><h:outputText value="th2"/></te:gridTh>
				<te:gridTh><h:outputText value="th3"/></te:gridTh>
				<te:gridTh><h:outputText value="th4"/></te:gridTh>
				<te:gridTh><h:outputText value="th5"/></te:gridTh>
			</te:gridTr>
		</te:gridHeader>
		<te:gridBody>
			<te:gridTr>
				<te:gridTd><h:outputText value="#{gridBean.fooIndex + 1}"/></te:gridTd>
				<te:gridTd><h:outputText value="#{gridBean.aaa}"/><h:inputHidden value="#{gridBean.aaa}"/></te:gridTd>
				<te:gridTd><h:outputText value="#{gridBean.bbb}"/></te:gridTd>
				<te:gridTd><te:gridInputText value="#{gridBean.bbb}"/></te:gridTd>
				<te:gridTd><h:outputText value="#{gridBean.ccc}"/><h:inputHidden value="#{gridBean.ccc}"/></te:gridTd>
				<te:gridTd><h:outputText value="#{gridBean.ddd}"/><h:inputHidden value="#{gridBean.ddd}"/></te:gridTd>
			</te:gridTr>
		</te:gridBody>
	</te:grid>
	<h:commandButton id="submit1"/>
</h:form>
</te:body>
</html>
</f:view>
