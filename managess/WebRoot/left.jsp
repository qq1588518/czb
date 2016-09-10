<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
	<!--**
	/**
	* <p>文件名称: 系统主页面左侧导航 样式排版</p>
	* <p>文件描述: 系统主页面左侧导航 样式排版</p>
	* <p>版权所有: 版权所有(C)2009</p>
	* <p>公    司: 中兴软件技术(南昌)有限公司</p>
	* <p>内容摘要: </p>
	* <p>其他说明: // 其它内容的说明</p>
	* <p>完成日期：2009-12-8</p>
	* <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
	* <pre>
	*    修改日期：
	*    版 本 号：
	*    修 改 人：
	*    修改内容：
	* </pre>
	* <p>修改记录2：…</p>
	* @version 1.0
	* @author  陈雪军
	*/
	*-->
	<HEAD>
		<TITLE>信息管理系统导航</TITLE>
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache" />
		<META HTTP-EQUIV="Expires" CONTENT="0" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script src="scripts/system.js"></script>
		<style>
			body {
				margin: 0px;
				padding: 0px;
				border: 0px;
				font-size: 12px;
			}
			a:hover, a:visited, a:link {
				color: #000;
				text-decoration: none;
			}
			.nav {
				background-color: #1d3d86;
				width: 147px;
				height: 100%;
				margin: 0px;
				overflow: hidden;
			}
			.left_nav {
				background-color: #d4e3ff;
				width: 147px;
				height: 100%;
				position: absolute;
				top: 0px;
				overflow: hidden;
			}
			.left_nav_bar {
			}
			.left_nav_bar ul {
				margin: 0px;
				padding: 0px;
				list-style-type: none;
			}
			.left_nav_bar li {
				width: 73px;
				height: 19px;
				float: left;
				text-align: center;
				cursor: pointer;
				line-height: 19px;
			}
			.change1 {
				background: url("images/index/leftbarbg1.gif") no-repeat;
			}
			.change2 {
				background: url("images/index/leftbarbg2.gif") no-repeat;
			}
			.left_con {
				position: absolute;
				left: 0px;
				top: 20px;
				width: 147px;
				height: 100%;
				overflow: hidden;
				background: #fff;
			}
			.left_con1 {
				position: absolute;
				left: 100px;
				top: 20px;
				width: 147px;
				height: 100%;
				overflow: hidden;
				background: #fff;
				display: none;
			}
			.left_con_div {
				position: absolute;
				left: 0;
				width: 147px;
				overflow: hidden
			}
			.foot {
				height: 43px;
				width: 147px;
				text-align: center;
				display: block;
				font-size: 12px;
				z-index: 10000000000000;
				position: absolute;
				line-height: 25px;
			}
			.headtd1 {
				background: url("images/index/item_bg.gif");
				cursor: pointer;
				font-size: 9pt
			}
			.headtd2 {
				background: url("images/index/item_bg.gif");
				cursor: pointer;
				font-size: 9pt
			}
			.bodytd {
				background: #fff;
				font-size: 9pt;
			}
			.bodytd div ul {
				margin: 0px;
				padding: 0px;
				list-style-type: none;
			}
			.bodytd div ul li {
				background: url("images/index/leftbar_bg.gif") no-repeat;
				width: 89px;
				height: 23px;
				line-height: 23px;
				margin-top: 10px;
				cursor: pointer;
			}

		</style>
	</head>
	<body onload="leftNav();" onresize="leftNav();">
		<div class="nav">
			<div class="left_nav" id="mainb">
				<%
				String username = (String)request.getSession().getAttribute("username");
				if(username.equals("customer")) { %>
				<div class="left_nav_bar">
					<ul>
						<li id="changePic1" class="change1">
							栏目1
						</li>
						<li id="changePic2" class="change2">
							栏目2
						</li>
					</ul>
				</div>
				<!-- 第一个栏目 -->
				<div id='mainboard1' class="left_con">
					<!--每增加一个导航项目就增加一个DIV，需修改id="item1body1",以及onclick中传值item1body1.....ID根据菜单的个数来确定，以1为下标依次增加********开始-->
					<div id='item1body1' class="left_con_div">
						<table width='100%' border='0' height='100%' cellpadding='2' cellspacing='0'>
							<tr>
								<td height='23' class='headtd2' align='center' onclick='moveme(item1body1,this)'>菜单</td>
							</tr>
							<tr>
								<td class='bodytd' align='center' valign='top'>
								<div>
									<ul>
										<li>
											<a href="msg.htm?action=getUrl" target=cxj>留言板信息管理</a>
										</li>
									</ul>
								</div></td>
							</tr>
						</table>
					</div>
				</div>
				<div id='mainboard2' class="left_con1">
					<!--每增加一个导航项目就增加一个DIV，需修改id="item1body"，id="item1head",以及onclick中传值item1body.....ID根据菜单的个数来确定，以1为下标依次增加********开始-->
					<div id='item1body2' class="left_con_div">
						<table width='100%' border='0' height='100%' cellpadding='2' cellspacing='0'>
							<tr>
								<td height='23' class='headtd2' onclick='moveme(item1body2,this)' align='center'>展业工具</td>
							</tr>
							<tr>
								<td class='bodytd' align='center' valign='top'>
								<div>
									<ul></ul>
								</div></td>
							</tr>
						</table>
					</div>
				</div>
				<%
				} else { %>
				<div class="left_nav_bar">
					<ul>
						<li id="changePic1" class="change1" onclick="change(1);">
							栏目1
						</li>
						<li id="changePic2" class="change2" onclick="change(2);">
							栏目2
						</li>
					</ul>
				</div>
				<!-- 第一个栏目 -->
				<div id='mainboard1' class="left_con">
					<!--每增加一个导航项目就增加一个DIV，需修改id="item1body1",以及onclick中传值item1body1.....ID根据菜单的个数来确定，以1为下标依次增加********开始-->
					<div id='item1body1' class="left_con_div">
						<table width='100%' border='0' height='100%' cellpadding='2' cellspacing='0'>
							<tr>
								<td height='23' class='headtd2' align='center' onclick='moveme(item1body1,this)'>菜单</td>
							</tr>
							<tr>
								<td class='bodytd' align='center' valign='top'>
								<div>
									<ul>
										<!-- li><a href="protype.htm?action=getUrl" target=cxj>栏目信息管理</a></li-->
										<li>
											<a href="advertis.htm?action=getUrl" target=cxj>广告宣传</a>
										</li>
										<li>
											<a href="colcon.htm?action=getUrl" target=cxj>栏目内容管理</a>
										</li>
										<li>
											<a href="msg.htm?action=getUrl" target=cxj>留言板信息管理</a>
										</li>
										<li>
											<a href="member.htm?action=getUrl" target=cxj>用户管理</a>
										</li>
										<li>
											<a href="group.htm?action=getUrl" target=cxj>群组管理</a>
										</li>
										<li>
											<a href="search.htm?action=getUrl" target=cxj>站内外搜索管理
										</li>
									</ul>
								</div></td>
							</tr>
						</table>
					</div>
				</div>
				<!-- 第二个栏目 -->
				<div id='mainboard2' class="left_con1">
					<!--每增加一个导航项目就增加一个DIV，需修改id="item1body"，id="item1head",以及onclick中传值item1body.....ID根据菜单的个数来确定，以1为下标依次增加********开始-->
					<div id='item1body2' class="left_con_div">
						<table width='100%' border='0' height='100%' cellpadding='2' cellspacing='0'>
							<tr>
								<td height='23' class='headtd2' onclick='moveme(item1body2,this)' align='center'>展业工具</td>
							</tr>
							<tr>
								<td class='bodytd' align='center' valign='top'>
								<div>
									<ul>
										<!--li><a href="subject.htm?action=getUrl" target=cxj>课件栏目管理</a></li-->
										<li>
											<a href="courseware.htm?action=getUrl" target=cxj>课件管理</a>
										</li>
										<li>
											<a href="corporate.htm?action=getUrl" target=cxj>企业文化管理</a>
										</li>
										<li>
											<a href="pronews.htm?action=getUrl" target=cxj>事业介绍管理</a>
										</li>
										<!--li><a href="answertype.htm?action=getUrl" target=cxj>百问类别管理</li-->
										<li>
											<a href="proanswer.htm?action=getUrl" target=cxj>事业百问管理</a>
										</li>
										<!--li><a href="type.htm?action=getUrl" target=cxj>产品类别管理</li-->
										<li>
											<a href="product.htm?action=getUrl" target=cxj>产品信息管理
										</li>
									</ul>
								</div></td>
							</tr>
						</table>
					</div>
				</div>
				<%
				} %>
			</div>
			<div id="footer" class="foot">
				当前版本:1.4
				<br>
				建议分辩率:1024*768
			</div>
		</div>
	</body>
	<script></script>
</html>