<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--**
/**
* <p>文件名称: 系统登录跳转</p>
* <p>文件描述: 系统登录跳转</p>
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
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Insert title here</title>
		<style type="text/css">
			<!--
			body {
			margin-left: 0px;
			margin-top: 0px;
			margin-right: 0px;
			margin-bottom: 0px;
			}
			-->

		</style>
	</head>
	<body></body>
	<script>
		var availWidth = screen.availWidth - 4;
		var availHeight = screen.availHeight - 55;
		window.open("main.html", "", "height=" + availHeight + ",width=" + availWidth + ",left=0,top=0,status=yes,toolbar=no,menubar=no,location=no");
		window.opener = null;
		window.close();
	</script>
</html>