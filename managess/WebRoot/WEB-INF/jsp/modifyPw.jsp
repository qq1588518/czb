<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<script src="js/prototype.js"></script>
<link rel=stylesheet type=text/css href="css/right.css"/>
<style>
<!--
.pwnav{	width:300px;	border:4px solid #63a6cd;text-align:left;margin-top:10px;}
.pwnav ul{	list-style-type:none;	margin:0px;	padding:0px;}
.pwmenu{	background:url(images/index/passwordbg.jpg) repeat-x;	padding-left:10px;}
.pwcon{	margin-top:13px !important;	margin-bottom:5px !important;	margin-left:30px !important;}
.pwcon li{	width:100%;	padding-bottom:5px;	height:10px;}
.inputs{	width:150px;	height:16px;	border:1px solid #a1b7cb;}
.pwbutton{	border-top:1px solid #dadee5;	text-align:center;}
.pwbutton li{	padding-top:7px;	padding-bottom:5px;	margin:0 auto;}
-->
</style>
<div class="nav" id="container">
	<div class="main_top">
		<ul class="main_top_title">
			<li>修改密码</li>
		</ul>
	</div>
	<div class="pwnav">
		<ul class="pwcon">
			<li style="color:red;">您好：${userInfo["userName"]}</li>
			<li ><span>原始密码：</span><input class="inputs" onchange="checkpassword()" id="oldPassword" maxlength="16" type="password"><img id="oldright" style="display:none;margin:3px 0 -3px 0;" src="images/index/right.gif" /><img id="oldwrong" style="display:none;margin:3px 0 -3px 0;" src="images/index/wrong.gif" /></li>
			<li ><span>最新密码：</span><input class="inputs" onchange="newpassword()" id="newPassword" maxlength="16" type="password"><img id="newright" style="display:none;margin:3px 0 -3px 0;" src="images/index/right.gif" /><img id="newwrong" style="display:none;margin:3px 0 -3px 0;" src="images/index/wrong.gif" /></li>
			<li ><span>确认密码：</span><input class="inputs" onchange="verifypwd()" id="verifypass" maxlength="16" type="password" onkeydown="if(event.keyCode==13) modifyPassword();"><img id="verifyright" style="display:none;margin:3px 0 -3px 0;" src="images/index/right.gif" /><img id="verifywrong" style="display:none;margin:3px 0 -3px 0;" src="images/index/wrong.gif" /></li>
			<li style="padding-top:5px;color:#63a6cd;"><span>密码最少6个字符，最长不得超过16个字符</span></ul>
		<ul class="pwbutton"><li><img style="cursor:pointer;" onclick="modifyPassword()" src="images/index/password3.jpg" /><img style="margin-left:30px;cursor:pointer;" src="images/index/password4.jpg" onclick="reset();"/></li></ul>
	</div>
</div>
<script>
////////////////////////////////////修改密码////////////////////////////////////
function reset(){
	for( i = 0 ;i < document.getElementsByTagName("input").length;i++){
		document.getElementsByTagName("input")[i].value="";
		document.getElementById("oldright").style.display = "none";
		document.getElementById("oldwrong").style.display = "none";
		document.getElementById("newright").style.display = "none";
		document.getElementById("newwrong").style.display = "none";
		document.getElementById("verifyright").style.display = "none";
		document.getElementById("verifywrong").style.display = "none";
	}
	document.getElementsByTagName("input")[0].focus();
}


var checkpwd = false;
function checkpassword(){
	var oldPassword = document.getElementById("oldPassword").value;
	if(oldPassword.replace(/(^\s*)|(\s*$)/,'')==""){
		document.getElementById("oldright").style.display = "none";
		document.getElementById("oldwrong").style.display = "none";
		return;
	}
		var pars ="flag=checkPassword&oldPassword="+oldPassword;
	    	var url = "modify.htm?action=modifyPw";
	    	var reqAjax = new Ajax.Request(    
	        	url,    
	        	{    
	            	method: 'post', 
	            	parameters: pars,
					onCreate: loadingFun,
	           	    onComplete: checkresponse    
	        	}    
	    ); 
}
var id;
function checkresponse(response){
	var text = response.responseText;
   	var obj = eval("("+text+")");
	 if(obj["flag"]){
	   	document.getElementById("oldright").style.display = "";
	   	document.getElementById("oldwrong").style.display = "none";
	   	//id = obj["id"];
	   	checkpwd = true;
	   	//return obj["id"];
	 }else{
	   	document.getElementById("oldright").style.display = "none";
	   	document.getElementById("oldwrong").style.display = "";
	   	checkpwd = false;
	   	//return obj["id"];
	}
}

function newpassword(){
	var newPassword = document.getElementById("newPassword").value;
	var verifypassword = document.getElementById("verifypass").value;
	if(newPassword.replace(/(^\s*)|(\s*$)/,'')==""){
   		document.getElementById("newright").style.display = "none";
		document.getElementById("newwrong").style.display = "none";
		return false;
   	}
   	if(newPassword.length < 6){
   		document.getElementById("newright").style.display = "none";
		document.getElementById("newwrong").style.display = "";
		return false;
   	}else{
	   	if(verifypassword.replace(/(^\s*)|(\s*$)/,'')==""){
	   		document.getElementById("newright").style.display = "";
			document.getElementById("newwrong").style.display = "none";
			return true;
   		}else{
	   		if(newPassword == verifypassword){
		   		document.getElementById("newright").style.display = "";
				document.getElementById("newwrong").style.display = "none";
				document.getElementById("verifyright").style.display = "";
				document.getElementById("verifywrong").style.display = "none";
				return true;
		   	}else{
		   		document.getElementById("verifyright").style.display = "none";
				document.getElementById("verifywrong").style.display = "";
		   		document.getElementById("newright").style.display = "none";
				document.getElementById("newwrong").style.display = "";
				return false;
		   	}
   		}
   	}
}

function verifypwd() {
	var newPassword = document.getElementById("newPassword").value;
	var verifypassword = document.getElementById("verifypass").value;
	if(verifypassword.replace(/(^\s*)|(\s*$)/,'')==""){
   		document.getElementById("verifyright").style.display = "none";
		document.getElementById("verifywrong").style.display = "none";
		return false;
   	}
	if(newPassword == verifypassword && newPassword.length > 5){
   		document.getElementById("verifyright").style.display = "";
		document.getElementById("verifywrong").style.display = "none";
		document.getElementById("newright").style.display = "";
		document.getElementById("newwrong").style.display = "none";
		return true;
   	}else{
   		document.getElementById("verifyright").style.display = "none";
		document.getElementById("verifywrong").style.display = "";
		document.getElementById("newright").style.display = "none";
		document.getElementById("newwrong").style.display = "";
		return false;
   	}
   	
}

function modifyPassword(){

	var oldPassword = document.getElementById("oldPassword").value;
	var newPassword = document.getElementById("newPassword").value;
	var verifypassword = document.getElementById("verifypass").value;
	if(!checkpwd) {alert("您输入的原始密码不正确。");return;}
   	if(!newpassword()) {alert("您输入的新密码不正确或格式不正确。");return;}
   	if(!verifypwd()) {alert("您输入的新密码不正确或格式不正确。");return;}
   	var pars ="flag=modifyPassword&verifypassword="+verifypassword;
	    	var url = "modify.htm?action=modifyPw";
	    	var reqAjax = new Ajax.Request(    
	        	url,    
	        	{    
	            	method: 'post', 
	            	parameters: pars,
					onCreate: loadingFun,
	           	    onComplete: modifyresponse    
	        	}    
	    ); 
}
function loadingFun(){}
function modifyresponse(response){
	var text = response.responseText;
   	var obj = eval("("+text+")");
	if(obj["flag"]){
		alert("密码修改成功");
		window.location = "modify.htm?action=getName";
	}else{
		alert("密码修改失败，请稍后再试！");
	}
}
////////////////////////////////////修改密码////////////////////////////////////
</script>