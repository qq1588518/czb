<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML>
<HEAD>
<TITLE>分页显示</TITLE>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache" />
<META HTTP-EQUIV="Expires" CONTENT="0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</HEAD>
<style>
/*
**分页
*/
body{font-size:9pt;}
.page{width:99%;clear:left;text-align:left;color:#295568;margin-top:10px;overflow:hidden;}
.page td span{font-weight:bold;}
.pagenav td{margin-left:3px;color:#295568;}
.pagenav td img{cursor:pointer;}
.tz{width:35px;border:solid 1px #7aaebd;height:17px;}
</style>
<BODY>
<table class="page">
		<tr>
			<td>共有 <span>243</span> 条记录，当前第 <span>1</span> 页，共 <span>10</span> 页</td>
			<td align=right>
				<table>
					<tr class="pagenav">
						<td><img src="images/page/shouye.gif" alt="首页"/></td>
						<td><img src="images/page/shangye.gif" alt="上一页"/></td>
						<td><img src="images/page/xiaye.gif" alt="下一页"/></td>
						<td><img src="images/page/weiye.gif" alt="尾页"/></td>
						<td>转到<input type="text" class="tz" value=""/>页</td>
						<td><img src="images/page/zhuan.gif" alt="跳转"/></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</BODY>
</HTML>
