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
<SCRIPT src="scripts/system.js" type=text/javascript></SCRIPT>
<SCRIPT src="scripts/layer.js" type=text/javascript></SCRIPT>
<script src="js/prototype.js"></script>
<title>添加修改类别</title>
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
<form id="theForm" name='theForm' method='post' action="type.htm?action=saveType" encType="multipart/form-data">
<div class="pwnav">
	<ul class="pwcon">
		<li >
			<span>类别名称：</span><input type="text" class="text" id="namep" maxlength="30" name="typename" value="${typename}"/></li>
		<li >
			<span>类别图片：</span><input class="file"  id="read_pic"  type="file" name="proAddress" style="width:300px;"/><input id="pic" name="pic_addr" type="hidden" value="${pic}"/>
			<span style="color:red;padding-left:60px;">（建议上传60*60像素图片，以节省手机访问流量）</span>
			<input type="button" value="开始上传" style="border:1px solid #a1b7cb;height:20px;display:none;"/>
		</li>
		<li>
			<span class="pwcon_span">&nbsp;&nbsp;&nbsp;&nbsp;Level：</span><input type="text" class="text" id="level" maxlength="6" name="level" value="${level}"/></li>
		<li >
			<span class="pwcon_span">备&nbsp;&nbsp;&nbsp;&nbsp;注：</span><textarea id="fb_content" Cols='35' rows='5' maxlength=200 name="remark" onkeypress='doKeypress()' onkeydown='doKeydown()' onbeforepaste='doBeforePaste()' onpaste='doPaste()'>${remark}</textarea>
		</li>
		<li >
			<input type="hidden" value="${typeno}" name="typeno"/>
		</li>
		<li>
			<input type="button" value="提 交" onclick="saveProduct()"/>
		</li>
	</ul>
</div>
</form>
</BODY>
<script>
	function viewpic(){
	var str = document.getElementById("read_pic").value;
	var pic = document.getElementById("pic").value;
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
	 		alert("请上传产品类别图片");
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
function saveProduct(){
	var column_name = document.getElementById("namep").value.replace(/(^\s*)|(\s*$)/g,'');
	var level = document.getElementById("level").value.replace(/(^\s*)|(\s*$)/g,'');
	if(level==""){
	
		document.getElementById("level").value = "0";
	}
	if(column_name==""){
		alert("请输入产品类别名称");
		return;
	}
	if(!viewpic()){return;}
	if(document.getElementById("fb_content").value == ""){
		document.getElementById("fb_content").value = "无备注";
	}
	document.getElementById("theForm").submit();
}



/////////////////////////////////////控制textarea输入框的输入长度///////////////////////////////////
var element
var maxLength
element=document.getElementById("fb_content");
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
}

/////////////////////////////////////控制textarea输入框的输入长度///////////////////////////////////
</script>
</HTML>
