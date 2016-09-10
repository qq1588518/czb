<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>平台项目组登录系统</TITLE>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache" />
<META HTTP-EQUIV="Expires" CONTENT="0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src='js/prototype.js'></script>
<script src="scripts/system.js"></script>
<style>
*{	MARGIN:0px;	PADDING:0px;}
UL{	MARGIN:0px;	PADDING:0px;	list-style-type:none;}
BODY{	BACKGROUND:URL("images/login/bg.gif") repeat-x #152652;	WIDTH:100%;	TEXT-ALIGN:center;	FONT-SIZE:12px;height:100%;width:100%;overflow-x:hidden;overflow-y:scroll;
scrollbar-face-color:#fff;/*滚动条凸出部分的颜色*/
scrollbar-3dlight-color:#fff;/*滚动条亮边的颜色*/
scrollbar-darkshadow-color:#fff;/*滚动条强阴影的颜色*/
scrollbar-base-color:#fff;/*滚动条的基本颜色*/
SCROLLBAR-ARROW-COLOR: #999;/*上下按钮上三角箭头的颜色*/
SCROLLBAR-HIGHLIGHT-COLOR:#fff;/*滚动条空白部分的颜色*/
SCROLLBAR-TRACK-COLOR:#fff;/*滚动条的背景颜色*/
 SCROLLBAR-SHADOW-COLOR:#999;/*立体滚动条阴影的颜色*/}
.CONTAINER{	WIDTH:847px;	MARGIN:0 auto;	TEXT-ALIGN:left;}
.HEADER{	WIDTH:847px;	HEIGHT:322px;	BACKGROUND:URL("images/login/login1.gif");}
.LOGIN LI{	float:left;}
.LOGIN IMG{	MARGIN-BOTTOM:-3px;}
.FOOTER{	CLEAR:left;	WIDTH:847px;	HEIGHT:209px;	BACKGROUND:URL("images/login/login7.gif");}
.LOGINBAR{	DISPLAY:BLOCK;	WIDTH:162px;	HEIGHT:82px;	BACKGROUND:URL("images/login/login_bg.gif") repeat-x;}
.BTN{	DISPALY:BLOCK;	WIDTH:67px;	HEIGHT:82px;	BACKGROUND:URL("images/login/login_bg.gif") repeat-x;}
.LOGINBAR_TEXT{	PADDING-TOP:5px;}
.LOGINBAR_TEXT LI{	PADDING-TOP:5px;}
.TBORDER{	WIDTH:110px;	HEIGHT:17px;BORDER:solid 1px #153865;	BACKGROUND-COLOR:#87adbf;}
.TBORDER1{	WIDTH:50px;	HEIGHT:17px;	BORDER:solid 1px #153865;	BACKGROUND-COLOR:#87adbf;}
.check{	WIDTH:10px;	Height:17px;}
.check img{cursor:pointer;width:60px;height:17px;}
.ITEM_TITLE{	CLEAR:left;	WIDTH:50px;	COLOR:#afbccf;}
.btn_login{	PADDING-TOP:19px;	PADDING-LEFT:5px;}
.btn_login li{	PADDING-BOTTOM:5px;}
.btn_login li img{	CURSOR:pointer;}
</style>
</HEAD>
<BODY>
 无版本文件名，无法更新！
<!-- <iframe width="750" height=561 border=0 src="http://117.40.138.151:8068/managess/SSCZBN20100417.apk"  frameborder="0"  scrolling="auto"></iframe>-->
</BODY>
<script>
document.getElementsByTagName("input")[0].focus();
/*
**登录
*/
function login(obj1,obj2,obj3){
	var username = obj1.value;
	var password = obj2.value;
	var validCode = obj3.value;
	var pars ="username="+username+"&password="+password+"&validCode="+validCode;
	alert(pars);
	var url = "login.htm?action=login";
	var reqAjax = new Ajax.Request(    
	        	url,    
	        	{    
	            	method: 'post', 
	            	parameters: pars,
					onCreate: loadingFun,
	           	    onComplete: loginResponse    
	        	}    
	)
}
function loadingFun(){}
function loginResponse(response){
	var text = response.responseText;
	alert("${ctx}");
	var obj = eval("("+text+")");
	if(true == obj["flag"]){
		window.location = "main.jsp";
	}
}
</script>
</HTML>
