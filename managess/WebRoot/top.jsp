<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT src="scripts/system.js" type=text/javascript></SCRIPT>
<title>管理后台</title>
<style type="text/css">
<!--
a,a:link,a:visited,a:hover{text-decoration:none;color:#000;}
ul{margin:0px;padding:0px;list-style-type:none;}
body {margin: 0px;padding:0px;background-color:#1d3d86;font-size:12px;}
img{margin-bottom:-3px;}
.header{width:100%;height:57px;background-image: url(images/index/header_bg.gif); background-repeat: repeat-x;overflow:hidden}
.header_l{float:left;}
.header_r{float:right;padding-top:27px;height:57px;width:327px;background:url("images/index/nav_bg.gif") no-repeat bottom;}
.hearer_r_nav{padding-left:20px;heiht:30px;padding-top:7px;font-size:12px;}
.hearer_r_nav ul{float:left;height:17px;width:69px;background:url("images/index/header14.gif") no-repeat;margin-left:5px;}
.hearer_r_nav li{}
.hearer_r_nav1{padding-top:1px;margin-left:3px;padding-left:15px;background:url("images/index/header4.gif") no-repeat 0 3px;}
.hearer_r_nav2{padding-top:1px;margin-left:3px;padding-left:13px;background:url("images/index/header3.gif") no-repeat 0 3px;}
.hearer_r_nav3{padding-top:1px;margin-left:3px;padding-left:13px;background:url("images/index/header5.gif") no-repeat 0 3px;}
.hearer_r_nav_black{background:url("images/index/header15.gif") no-repeat !important;color:#fff;}
.hearer_r_nav_black a,.hearer_r_nav_black a:link,.hearer_r_nav_black a:visited,.hearer_r_nav_black a:hover{color:#fff;}
.hearer_r_nav_black li{padding:1px 0px 0px 10px;}
.header_m{clear:left;width:100%;height:40px;background-image: url(images/index/main_m.gif); background-repeat: repeat-x;overflow:hidden}
.header_m_r{float:right;height:22px;width:208px;background:url("images/index/datetime.gif") no-repeat bottom;margin-right:10px;margin-top:8px;padding-left:10px;color:#fff;line-height:22px;}
.header_b{clear:left;margin-left:8px;margin-right:8px;height:28px;background:url("images/index/header16.gif") no-repeat #d4e3ff;line-height:28px;padding-left:190px;}
-->
</style>
</head>
<%String name = (String)session.getAttribute("username"); %>
<body onload="clockon(dat);">
<div>
	<div class="header">
		<ul>
			<li class="header_l"><img src="images/index/header.gif"></li>
		</ul>
		<ul>
			<li class="header_r">
				<div class="hearer_r_nav">
					<ul>
						<li class="hearer_r_nav1"><a href="right.jsp" target=cxj>首&nbsp;&nbsp;&nbsp;页</a></li>
					</ul>
					<ul>
						<li class="hearer_r_nav2"><a href="modify.htm?action=getName" target=cxj>修改密码</a></li>
					</ul>
					<ul>
						<li class="hearer_r_nav3">用户信息</li>
					</ul>
					<ul class="hearer_r_nav_black">
						<li><a href="login.htm?action=logout" target="_top">退出系统</a></li>
					</ul>
				</div>
			</li>
		</ul>
	</div>

	<div class="header_m">
		<ul>
			<li class="header_l"><img src="images/index/header_bt.gif"></li>
		</ul>
		<ul>
			<li class="header_m_r"><span>当前时间：</span><span id="dat"></span></li>
		</ul>
	</div>
	<div class="header_b">
		<ul>
			<li class="header_l">当前登录用户：<%=name%>  &nbsp;角色：管理员</li>
		</ul>
	</div>
	
</div>
</body>
<script>

</script>
</html>