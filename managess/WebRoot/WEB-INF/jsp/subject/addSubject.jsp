<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache" />
<META HTTP-EQUIV="Expires" CONTENT="0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="js/prototype.js"></script>
<title>添加课件栏目</title>
</HEAD>
<style>
*{	MARGIN:0px;	PADDING:0px;}
UL{	MARGIN:0px;	PADDING:0px;	list-style-type:none;}
BODY{	WIDTH:100%;	FONT-SIZE:12px;height:100%;width:100%;}
.pwnav{margin:5px 0px 5px 10px;}
.pwcon li{padding-top:5px;}
.pwcon_span{width:60px;display:block;float:left;text-align:right;}
</style>
<BODY>
<form id="theForm" name='theForm' method='post' action="subject.htm?action=saveSubject" encType="multipart/form-data">
<div class="pwnav">
	<ul class="pwcon">
		<li >
			<span>栏目名称：</span><input type="text" class="text" id="subjectname" maxlength="30" name="subjectname" value="${subjectname}"/></li>
		<li >
			<span>栏目图片：</span><input class="file"  id="read_pic"  type="file" name="read_pic" style="width:300px;"/><input id="imgurl" name="imgurl" type="hidden" value="${imgurl}"/><br/>
			<span style="color:red;padding-left:60px;">（上传60*60像素，大小不超过5K，以节省手机访问流量）</span>
			<input type="button" value="开始上传" style="border:1px solid #a1b7cb;height:20px;display:none;"/><li>
			<img src="about:blank" id="fileChecker" alt="test"  height="18" style="display:none;"/>
		</li>
		<li >
			<span class="pwcon_span">&nbsp;&nbsp;&nbsp;&nbsp;Level：</span><input type="text" class="text" id="level" maxlength="6" name="level" value="${level}"/>
		</li>
		
		<li >
			<input type="hidden" value="${subjectno}" name="subjectno"/>
		</li>
		<li>
			<input type="button" value="提 交" onclick="saveSubject()"/><input type="button" value="取 消" onclick="parent.closebutton(parent.document.getElementById('container'));"/>
		</li>
	</ul>
</div>
</form>
</BODY>
<script>




function viewpic(){
	var str = document.getElementById("read_pic").value;
	var pic = document.getElementById("imgurl").value;
	var pos = str.lastIndexOf(".");
	var lastname = str.substring(pos,str.length).replace(/(^\s*)|(\s*$)/g,'');
	if(lastname !=""){ 
		if (lastname.toLowerCase()!=".jpg" && lastname.toLowerCase()!=".gif"){
		     alert("您上传的图片格式为"+lastname+"，图片格式必须为.jpg,.gif类型");
		     //document.getElementById("read_pic").value="";
		     return false;
		 }else {
		 	//document.getElementById("veiwPic").src=str;
			//alert(document.getElementById("veiwPic").src);
		 	return true;
		 }
	 }else{
	 	if(pic == ""){
	 		alert("请上传图片");
	 		return false;
	 	}else{
	 		return true;
	 	}
	 }
}

/**
	 * 保存要添加、修改的栏目信息
	 * 
	 * @param id
	 * @return
	 */
function saveSubject(){
	var subjectname = document.getElementById("subjectname").value.replace(/(^\s*)|(\s*$)/g,'');
	var level = document.getElementById("level").value.replace(/(^\s*)|(\s*$)/g,'');
	if(level==""){
	
		document.getElementById("level").value = "0";
	}
	if(subjectname==""){
		alert("请输入课件栏目名称");
		return;
	}
	if(!viewpic()){return;}
	
	changeSrc(document.getElementById("read_pic"));
	document.getElementById("theForm").submit();
}



/////////////////////////////////////控制textarea输入框的输入长度///////////////////////////////////
/*var element
var maxLength
element=document.getElementById("memo");
maxLength = element.maxlength;
function doKeypress(){
	if(!isNaN(maxLength))
	{
	maxLength = parseInt(maxLength)
	var oTR = element.document.selection.createRange()
	if(oTR.text.length >= 1)
	event.returnValue = true
	else if(element.value.length > maxLength-1)
	event.returnValue = false
	}
}
function doKeydown(){
setTimeout(function(){maxLength = parseInt(maxLength);if(!isNaN(maxLength)){if(element.value.length > maxLength-1){var oTR = window.document.selection.createRange();oTR.moveStart("character", -1*(element.value.length-maxLength));oTR.text = ""}}},1)
}
function doBeforePaste(){
if(!isNaN(maxLength))
event.returnValue = false
}
function doPaste(){
	if(!isNaN(maxLength))
	{
	event.returnValue = false
	maxLength = parseInt(maxLength)
	var oTR = element.document.selection.createRange()
	var iInsertLength = maxLength - element.value.length + oTR.text.length
	var sData = window.clipboardData.getData("Text").substr(0, iInsertLength)
	oTR.text = sData;
	}
}*/

/////////////////////////////////////控制textarea输入框的输入长度///////////////////////////////////
</script>

<script defer=true>
var oFileChecker = document.getElementById("fileChecker");

function changeSrc(filePicker)
{
    oFileChecker.src = filePicker.value;
}

oFileChecker.onreadystatechange = function ()
{
    if (oFileChecker.readyState == "complete")
    {
        checkSize();
    }
}

function checkSize()
{
    var limit  = 2*1024*10;
	
    if (oFileChecker.fileSize > limit)
    {
        alert("文件超过最大长度");
        return;
    }
    else
    {
    	document.getElementById("theForm").submit();
    }
}


</script>
</HTML>
