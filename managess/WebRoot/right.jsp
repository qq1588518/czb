<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8" %>
<%@ page import="java.util.* ,java.text.SimpleDateFormat;" %>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache" />
		<META HTTP-EQUIV="Expires" CONTENT="0" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<SCRIPT src="scripts/system.js" type=text/javascript></SCRIPT>
		<SCRIPT src="scripts/layer.js" type=text/javascript></SCRIPT>
		<script src="js/prototype.js"></script>
		<link rel=stylesheet type=text/css href="css/right.css"/>
		<title>管理后台</title>
	</head>
	<%!
	String getDate() {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return sdf.format(now);
	} %>
	<body>
		<div class="nav" id="container">
			<div class="main_top">
				<ul class="main_top_title">
					<li>
						服务器基本信息
					</li>
				</ul>
			</div>
			<table class="main_con1" cellpadding="0" cellspacing="0">
				<tr class="main_con_title">
					<td colspan="4">服务器的有关参数</td>
				</tr>
				<tr id="maindetail1" onmouseover="changecolor(this);" onmouseout="changecolor(this);">
					<td class="main_con_item1">系统名称：</td>
					<td class="main_con_item2">三生财智宝系统</td>
					<td class="main_con_item3">服务器操作系统：</td>
					<td class="main_con_item4">windows XP</td>
				</tr>
				<tr id="maindetail1" onmouseover="changecolor(this);" onmouseout="changecolor(this);">
					<td  class="main_con_item1">服务器：</td>
					<td  class="main_con_item2"><%=request.getServerName()%></td>
					<td  class="main_con_item3">服务器端口：</td>
					<td  class="main_con_item4"><%=request.getServerPort()%></td>
				</tr>
				<tr id="maindetail1" onmouseover="changecolor(this);" onmouseout="changecolor(this);">
					<td class="main_con_item1">使用协议：</td>
					<td class="main_con_item2"><%=request.getProtocol() %></td>
					<td class="main_con_item3">系统版本号：</td>
					<td class="main_con_item4">1.4</td>
				</tr>
				<tr id="maindetail1" onmouseover="changecolor(this);" onmouseout="changecolor(this);">
					<td class="main_con_item1">服务器时间：</td>
					<td class="main_con_item2"><%=getDate()%></td>
					<td class="main_con_item3">建议分辩率：</td>
					<td class="main_con_item4">1024*768</td>
				</tr>
			</table>
			<table class="main_con1" cellpadding="0" cellspacing="0">
				<tr class="main_con_title">
					<td colspan="4">系统快捷管理操作</td>
				</tr>
				<tr>
					<td colspan="2" class="main_con_item1">常用管理链接：</td>
					<td colspan="2">
					<%
					String username = (String)request.getSession().getAttribute("username");
					if(username.equals("customer")) { %>
						<a href="msg.htm?action=getUrl" target=cxj>留言板信息管理</a>
					<%
					} else { %>
						<a href="msg.htm?action=getUrl" target=cxj>留言板信息管理</a>
						<a href="colcon.htm?action=getUrl" target=cxj>栏目内容管理</a>
						<a href="member.htm?action=getUrl" target=cxj>用户管理</a>
						<a href="courseware.htm?action=getUrl" target=cxj>课件管理</a>
					<%
					} %>
					</td>
				</tr>
			</table>
			<%
			if(username.equals("admin")) { %>
			<table class="main_con1" cellpadding="0" cellspacing="0">
				<tr class="main_con_title">
					<td colspan="4">在线统计系统访问量</td>
				</tr>
				<tr>
					<td colspan="4" id='count' >访问量：</td>
				</tr>
			</table>
			<table class="main_con1" cellpadding="0" cellspacing="0">
				<tr class="main_con_title">
					<td colspan="4">VPDN验证地址</td>
				</tr>
				<tr>
					<td colspan="4" style="width:100%;">验证地址：
					<input name='verifyurl' id='verifyurl' value="" maxlength=500 style="width:85%;"/>
					&nbsp;&nbsp;&nbsp;
					<button  onclick="updateVDPN();">
						更新
					</button></td>
				</tr>
			</table>
			<%
			} %>
			<table class="main_con1" cellpadding="0" cellspacing="0">
				<tr class="main_con_title">
					<td colspan="4">系统维护联系方式 技术支持</td>
				</tr>
				<tr id="maindetail1" onmouseover="changecolor(this);" onmouseout="changecolor(this);">
					<td class="main_con_item1">公司名称：</td>
					<td class="main_con_item2">中兴软件技术（南昌）有限公司</td>
					<td class="main_con_item3">通迅地址：</td>
					<td class="main_con_item4">江西省南昌市高新区艾湖北路688号中兴软件园A2栋</td>
				</tr>
				<tr id="maindetail1" onmouseover="changecolor(this);" onmouseout="changecolor(this);">
					<td class="main_con_item1">邮政编码：</td>
					<td class="main_con_item2">330029</td>
					<td class="main_con_item3">公司网站：</td>
					<td class="main_con_item4"><a href="http://www.zte-s.com.cn" target=_blank>www.zte-s.com.cn</a></td>
				</tr>
				<tr id="maindetail1" onmouseover="changecolor(this);" onmouseout="changecolor(this);">
					<td class="main_con_item1">服务电话：</td>
					<td class="main_con_item2">0791-6178320</td>
					<td class="main_con_item3">传真号码：</td>
					<td class="main_con_item4">0791-6178314</td>
				</tr>
			</table>
		</div>
	</body>
	<script>
		<%
		if(username.equals("admin")) { %>
			initcount();
			getVPDN();
		<%
		} %>
		function initcount() {
			setInterval(getcount,1000);
		}

		function getVPDN() {
			var pars = "";
			var url = "login.htm?action=getVPDNAddr";
			var reqAjax = new Ajax.Request(url, {
				method: 'post',
				parameters: pars,
				onCreate: loadingFun,
				onComplete: getVPDNResponse});
		}

		function getVPDNResponse(response) {
			var text = response.responseText;
			var obj = eval("("+text+")");
			var rs = obj["rs"];
			document.getElementById("verifyurl").value = rs["verifyurl"]==undefined?"":rs["verifyurl"];
			//document.getElementById("gourl").value = rs["gourl"]==undefined?"":rs["gourl"];
		}

		function updateVDPN() {
			var verifyurl = document.getElementById("verifyurl").value.replace(/&/g,"%26");;
			//var gourl = document.getElementById("gourl").value;
			var pars = "verifyurl="+verifyurl;
			var url = "login.htm?action=saveVPDNAddr";
			var reqAjax = new Ajax.Request(url, {
				method: 'post',
				parameters: pars,
				onCreate: loadingFun,
				onComplete: getUpdateResponse});
		}

		function getUpdateResponse(response) {
			var text = response.responseText;
			var obj = eval("("+text+")");
			if(obj["flag"]) {
				alert("VPDN地址已成功更新");
			} else {
				alert("VPDN地址已更新失败");
			}
		}
		
		function getcount() {
			var pars = "model={model:[ydzx,search,subject,msg,product,culture,pronews,proanswer]}";
			var url = "login.htm?action=getcount";
			var reqAjax = new Ajax.Request(url,{
				method: 'post',
				parameters: pars,
				onCreate: loadingFun,
				onComplete: getprotypeResponse});
		}
		
		function loadingFun(){
			
		}
		
		function getprotypeResponse(response) {
			var text = response.responseText;
			var obj = eval("("+text+")");
			var info = obj["rs"];
			var obj = document.getElementById("count");
			var total_str = "访问量:";
			var str = "";
			var total = 0;
			for(var p in info) {
				total += parseInt(info[p]);
				if(p=="ydzx") {
					str += "<span>    移动资讯:"+info[p]+"</span>&nbsp;";
				}else if(p=="search") {
					str += "<span>    内部搜索:"+info[p]+"</span>&nbsp;";
				}else if(p=="subject") {
					str += "<span>    课件下载:"+info[p]+"</span>&nbsp;";
				}else if(p=="msg") {
					str += "<span>    在线留言:"+info[p]+"</span>&nbsp;";
				}else if(p=="product") {
					str += "<span>    产品介绍:"+info[p]+"</span>&nbsp;";
				}else if(p=="culture") {
					str += "<span>    企业文化:"+info[p]+"</span>&nbsp;";
				}else if(p=="pronews") {
					str += "<span>    事业介绍:"+info[p]+"</span>&nbsp;";
				}else if(p=="proanswer") {
					str += "<span>    事业百问:"+info[p]+"</span>&nbsp;";
				}
			}
			obj.innerHTML = total_str+total+"&nbsp;"+str;
		}</script>
</html>