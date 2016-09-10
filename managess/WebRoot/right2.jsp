<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache" />
<META HTTP-EQUIV="Expires" CONTENT="0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<SCRIPT src="scripts/system.js" type=text/javascript></SCRIPT>
<SCRIPT src="scripts/layer.js" type=text/javascript></SCRIPT>
<link rel=stylesheet type=text/css href="css/right.css"/>
<link href="css/sample.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/fckeditor.js"></script>
<style>
.main_con_operation{width:170px !important;}
</style>
<title>管理后台</title>
</head>
<body>
<div class="nav" id="container">
	<div class="main_top">
		<ul class="main_top_title">
			<li>栏目信息管理</li>
		</ul>
		<ul class="main_top_title_r">
			<!-- li class="main_top_title_r_c" onclick="selectAll();">全 选</li -->
			<li class="main_top_title_r_a" onclick="showOpen(document.getElementById('container'),'发布物流信息','login.jsp',700,310);">添 加</li>
			<!-- li class="main_top_title_r_d">删 除</li-->
		</ul>
	</div>
	<!-- table class="main_select" cellpadding="0" cellspacing="0" align=center>
		<tr>
			<td><span>关键字：</span><input type="text" class="text"></td>
			<td><span>关键字：</span><input type="text" class="text"></td>
			<td><img src="images/index/select.gif"/></td>
		</tr>
	</table-->
	<table class="main_con" cellpadding="0" cellspacing="0">
		<tr class="main_con_title">	
			<td class="main_con_select">查看</td>
			<td>栏目编号</td>
			<td>栏目名称</td>
			<td class="main_con_operation">基本操作</td>
		</tr>
	</table>
<iframe ID='editor' name='myframe' src='editor2/edit.jsp?id=ContentNormal&style=s_coolblue' frameborder='0' scrolling='no' width='660' height='350'></iframe>
	<form action="../php/sampleposteddata.php" method="post" target="_blank">
		<script type="text/javascript">
var sBasePath = document.location.href.substring(0,document.location.href.lastIndexOf('_samples')) ;
var oFCKeditor = new FCKeditor( 'FCKeditor1' ) ;
oFCKeditor.BasePath	= sBasePath ;
oFCKeditor.Height	= 300 ;
oFCKeditor.Value	= '<p>This is some <strong>sample text<\/strong>. You are using <a href="http://www.fckeditor.net/">FCKeditor<\/a>.<\/p>' ;
oFCKeditor.Create() ;
		</script>
		<br />
		<input type="submit" value="Submit" />
	</form>



</body>
</html>