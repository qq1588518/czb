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
<title>管理后台</title>
</head>
<body>
<div class="nav" id="container">
	<div class="main_top">
		<ul class="main_top_title">
			<li>管理人员基本信息列表</li>
		</ul>
		<ul class="main_top_title_r">
			<!-- li class="main_top_title_r_c" onclick="selectAll();">全 选</li -->
			<li class="main_top_title_r_a" onclick="showOpen(document.getElementById('container'),'发布物流信息','login.jsp',700,310);">添 加</li>
			<!-- li class="main_top_title_r_d">删 除</li-->
		</ul>
	</div>
	<table class="main_select" cellpadding="0" cellspacing="0" align=center>
		<tr>
			<td><span>关键字：</span><input type="text" class="text"></td>
			<td><span>关键字：</span><input type="text" class="text"></td>
			<td><img src="images/index/select.gif"/></td>
		</tr>
	</table>
	<table class="main_con" cellpadding="0" cellspacing="0">
		<tr class="main_con_title">	
			<td class="main_con_select">查看</td>
			<td>联系人</td>
			<td>留言内容</td>
			<td>时间</td>
			<td>时间</td>
			<td>时间</td>
			<td>时间</td>
			<td class="main_con_operation">基本操作</td>
		</tr>
		<tr class="main_con" id="maindetail1" onmouseover="changecolor(this);" onmouseout="changecolor(this);">	
			<td class="main_con_check"><img src="images/index/detail_have.jpg" onclick="showdetail(this,document.getElementById('item1'));"/></td>
			<td>联系人</td>
			<td>留言内容</td>
			<td>时间</td>
			<td>时间</td>
			<td>时间</td>
			<td>时间</td>
			<td class="main_con_operation">
				<ul>
					<li><img src="images/index/veiw.gif" onclick="showdetail(this,document.getElementById('item1'));"/></li>
					<li><img src="images/index/alert.gif" onclick="showOpen(container,'添加新功能','fabuxx.html',200,500);"/></li>
					<li><img src="images/index/delete.gif"/></li>
				</ul>
			</td>
		</tr>
		<tr>	
			<td colspan="8">
				<div id="item1" align="left" style="display:none;padding-left:30px;padding-top:10px;padding-bottom:10px;">
					<span class="tle">业务介绍：</span><br/>
					<span class="tle">使用方式：</span><br/>
					<span class="tle">业务地址：</span><br/>
					<span class="tle">提示设定：</span><br/>																		
				</div>
			</td>
		</tr>

		<tr id="maindetail2" onmouseover="changecolor(this);" onmouseout="changecolor(this);">	
			<td class="main_con_check"><img src="images/index/detail_have.jpg" onclick="showdetail(this,document.getElementById('item2'));"/></td>
			<td>联系人</td>
			<td>留言内容</td>
			<td>时间</td>
			<td>时间</td>
			<td>时间</td>
			<td>时间</td>
			<td class="main_con_operation">
				<ul>
					<li><img src="images/index/veiw.gif" onclick="showdetail(document.getElementById('item2'));"/></li>
					<li><img src="images/index/alert.gif" onclick="showOpen(container,'添加新功能','left.jsp',200,400);"/></li>
					<li><img src="images/index/delete.gif"/></li>
				</ul>
			</td>
		</tr>
		<tr>	
			<td colspan="8">
				<div id="item2" align="left" style="display:none;padding-left:30px;padding-top:10px;padding-bottom:10px;">
					<span class="tle">业务介绍：</span><br/>
					<span class="tle">使用方式：</span><br/>
					<span class="tle">业务地址：</span><br/>
					<span class="tle">提示设定：</span><br/>																		
				</div>
			</td>
		</tr>

		<tr id="maindetail3" onmouseover="changecolor(this);" onmouseout="changecolor(this);">	
			<td class="main_con_check"><img src="images/index/detail_have.jpg" onclick="showdetail(this,document.getElementById('item3'));"/></td>
			<td>联系人</td>
			<td>留言内容</td>
			<td>时间</td>
			<td>时间</td>
			<td>时间</td>
			<td>时间</td>
			<td class="main_con_operation">
				<ul>
					<li><img src="images/index/veiw.gif" onclick="showdetail(document.getElementById('item3'));"/></li>
					<li><img src="images/index/alert.gif" onclick="showOpen(container,'添加新功能','left.jsp',200,400);"/></li>
					<li><img src="images/index/delete.gif"/></li>
				</ul>
			</td>
		</tr>
		<tr>	
			<td colspan="8">
				<div id="item3" align="left" style="display:none;padding-left:30px;padding-top:10px;padding-bottom:10px;">
					<span class="tle">业务介绍：</span><br/>
					<span class="tle">使用方式：</span><br/>
					<span class="tle">业务地址：</span><br/>
					<span class="tle">提示设定：</span><br/>																		
				</div>
			</td>
		</tr>

		<tr id="maindetail4" onmouseover="changecolor(this);" onmouseout="changecolor(this);">	
			<td class="main_con_check"><img src="images/index/detail_have.jpg" onclick="showdetail(this,document.getElementById('item4'));"/></td>
			<td>联系人</td>
			<td>留言内容</td>
			<td>时间</td>
			<td>时间</td>
			<td>时间</td>
			<td>时间</td>
			<td class="main_con_operation">
				<ul>
					<li><img src="images/index/veiw.gif" onclick="showdetail(document.getElementById('item4'));"/></li>
					<li><img src="images/index/alert.gif" onclick="showOpen(container,'添加新功能','left.jsp',200,400);"/></li>
					<li><img src="images/index/delete.gif"/></li>
				</ul>
			</td>
		</tr>
		<tr>	
			<td colspan="8">
				<div id="item4" align="left" style="display:none;padding-left:30px;padding-top:10px;padding-bottom:10px;">
					<span class="tle">业务介绍：</span><br/>
					<span class="tle">使用方式：</span><br/>
					<span class="tle">业务地址：</span><br/>
					<span class="tle">提示设定：</span><br/>																		
				</div>
			</td>
		</tr>

		<tr id="maindetail5" onmouseover="changecolor(this);" onmouseout="changecolor(this);">	
			<td class="main_con_check"><img src="images/index/detail_have.jpg" onclick="showdetail(this,document.getElementById('item5'));"/></td>
			<td>联系人</td>
			<td>留言内容</td>
			<td>时间</td>
			<td>时间</td>
			<td>时间</td>
			<td>时间</td>
			<td class="main_con_operation">
				<ul>
					<li><img src="images/index/veiw.gif" onclick="showdetail(document.getElementById('item5'));"/></li>
					<li><img src="images/index/alert.gif" onclick="showOpen(container,'添加新功能','left.jsp',200,400);"/></li>
					<li><img src="images/index/delete.gif"/></li>
				</ul>
			</td>
		</tr>
		<tr>	
			<td colspan="8">
				<div id="item5" align="left" style="display:none;padding-left:30px;padding-top:10px;padding-bottom:10px;">
					<span class="tle">业务介绍：</span><br/>
					<span class="tle">使用方式：</span><br/>
					<span class="tle">业务地址：</span><br/>
					<span class="tle">提示设定：</span><br/>																		
				</div>
			</td>
		</tr>

		<tr id="maindetail6" onmouseover="changecolor(this);" onmouseout="changecolor(this);">	
			<td class="main_con_check"><img src="images/index/detail_have.jpg" onclick="showdetail(this,document.getElementById('item6'));"/></td>
			<td>联系人</td>
			<td>留言内容</td>
			<td>时间</td>
			<td>时间</td>
			<td>时间</td>
			<td>时间</td>
			<td class="main_con_operation">
				<ul>
					<li><img src="images/index/veiw.gif" onclick="showdetail(document.getElementById('item6'));"/></li>
					<li><img src="images/index/alert.gif" onclick="showOpen(container,'添加新功能','left.jsp',200,400);"/></li>
					<li><img src="images/index/delete.gif"/></li>
				</ul>
			</td>
		</tr>
		<tr>	
			<td colspan="8">
				<div id="item6" align="left" style="display:none;padding-left:30px;padding-top:10px;padding-bottom:10px;">
					<span class="tle">业务介绍：</span><br/>
					<span class="tle">使用方式：</span><br/>
					<span class="tle">业务地址：</span><br/>
					<span class="tle">提示设定：</span><br/>																		
				</div>
			</td>
		</tr>

		<tr id="maindetail7" onmouseover="changecolor(this);" onmouseout="changecolor(this);">	
			<td class="main_con_check"><img src="images/index/detail_have.jpg" onclick="showdetail(this,document.getElementById('item7'));"/></td>
			<td>联系人</td>
			<td>留言内容</td>
			<td>时间</td>
			<td>时间</td>
			<td>时间</td>
			<td>时间</td>
			<td class="main_con_operation">
				<ul>
					<li><img src="images/index/veiw.gif" onclick="showdetail(document.getElementById('item7'));"/></li>
					<li><img src="images/index/alert.gif" onclick="showOpen(container,'添加新功能','left.jsp',200,400);"/></li>
					<li><img src="images/index/delete.gif"/></li>
				</ul>
			</td>
		</tr>
		<tr>	
			<td colspan="8">
				<div id="item7" align="left" style="display:none;padding-left:30px;padding-top:10px;padding-bottom:10px;">
					<span class="tle">业务介绍：</span><br/>
					<span class="tle">使用方式：</span><br/>
					<span class="tle">业务地址：</span><br/>
					<span class="tle">提示设定：</span><br/>																		
				</div>
			</td>
		</tr>

		<tr id="maindetail8" onmouseover="changecolor(this);" onmouseout="changecolor(this);">	
			<td class="main_con_check"><img src="images/index/detail_have.jpg" onclick="showdetail(this,document.getElementById('item8'));"/></td>
			<td>联系人</td>
			<td>留言内容</td>
			<td>时间</td>
			<td>时间</td>
			<td>时间</td>
			<td>时间</td>
			<td class="main_con_operation">
				<ul>
					<li><img src="images/index/veiw.gif" onclick="showdetail(document.getElementById('item8'));"/></li>
					<li><img src="images/index/alert.gif" onclick="showOpen(container,'添加新功能','left.jsp',200,400);"/></li>
					<li><img src="images/index/delete.gif"/></li>
				</ul>
			</td>
		</tr>
		<tr>	
			<td colspan="8">
				<div id="item8" align="left" style="display:none;padding-left:30px;padding-top:10px;padding-bottom:10px;">
					<span class="tle">业务介绍：</span><br/>
					<span class="tle">使用方式：</span><br/>
					<span class="tle">业务地址：</span><br/>
					<span class="tle">提示设定：</span><br/>																		
				</div>
			</td>
		</tr>
	</table>
	<%@include file="setpage1.jsp"%>
</div>
</body>
</html>