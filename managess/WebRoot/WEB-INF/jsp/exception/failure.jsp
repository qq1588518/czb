<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<script language=JavaScript>
function showErr(){
var isHidde = document.all.isHidde.value;
//alert(isHidde);
if( isHidde == "true" ){
  document.all.errdiv.style.display='block';
  document.all.isHidde.value= 'false';
  document.all.showbtn.value="隐藏错误信息";
}else{
  document.all.errdiv.style.display='none';
  document.all.isHidde.value= 'true';
  document.all.showbtn.value="显示错误信息";
}
}
</script>
<html>
<head>
<title>this is failure</title>
</head>
<body onload="showErr()"> 

<c:set value="${exception}" var="ee" />
<jsp:useBean id="ee" type="java.lang.Exception" />
<%=ee.getMessage()%>ok，<br>


<table id="errdiv" align="center" bgcolor="darkseagreen">
<tr><td>
<font color=red>
<%ee.printStackTrace( new java.io.PrintWriter(out));%>
</font>
</td></tr></table>
<input type="hidden" id="isHidde" value="true"/>
<input type="button" id="showbtn" onclick="showErr();"/>

</body>
</html>
