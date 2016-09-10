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
<title>修改群组</title>
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
<form id="theForm" name='theForm' method='post' action="protype.htm?action=saveProtype" encType="multipart/form-data">
<div class="pwnav">
	<ul class="pwcon">
		<li >
			<span>群组名称：</span><input type="text" class="text" id="groupname" maxlength="16" name=""groupname"" value="${groupname}"/></li>
		<li >
			<span class="pwcon_span">备&nbsp;&nbsp;&nbsp;&nbsp;注：</span><textarea id="memo" Cols='35' rows='5' maxlength=200 name="remark" onkeypress='doKeypress()' onkeydown='doKeydown()' onbeforepaste='doBeforePaste()' onpaste='doPaste()'>${memo}</textarea>
		</li>
		<li >
			<input type="hidden" value="${groupno}" name="groupno" id="groupno"/>
		</li>
		<li>
			<input type="button" value="提 交" onclick="saveGroup();"/><input type="button" value="取 消" onclick="parent.closebutton(parent.document.getElementById('container'));"/>
		</li>
	</ul>
</div>
</form>
</BODY>
<script>

function doSave(groupid,groupname,memo){//index为搜索开始位置，len为每页显示的长度
	var pars ="groupno="+groupid+"&groupname="+groupname+"&memo="+memo;
	    	var url = "group.htm?action=saveGroup";
	    	var reqAjax = new Ajax.Request(    
	        	url,    
	        	{    
	            	method: 'post', 
	            	parameters: pars,
					onCreate: loadingFun,
	           	    onComplete: readMemberResponse    
	        	}    
	    ); 
	 
	 }
function loadingFun(){}
function readMemberResponse(response){

	var text = response.responseText;
	var obj = eval("("+text+")");
    if(obj["flag"]=='1'){
	
		alert("修改成功!");
		parent.readUser(1,0,10);
		parent.closebutton(parent.document.getElementById("container"));
	}else{
	
		alert("修改失败!");
	}
}
/**
	 * 保存要添加、修改的栏目信息
	 * 
	 * @param id
	 * @return
	 */
function saveGroup(){
    var groupno = document.getElementById("groupno").value.replace(/(^\s*)|(\s*$)/g,'');
	var groupname = document.getElementById("groupname").value.replace(/(^\s*)|(\s*$)/g,'');
	var memo = document.getElementById("memo").value;
	if(groupname==""){
		alert("请输入群组名称");
		return;
	}
	doSave(groupno,groupname,memo);
}


/////////////////////////////////////控制textarea输入框的输入长度///////////////////////////////////
var element
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
}

/////////////////////////////////////控制textarea输入框的输入长度///////////////////////////////////
</script>
</HTML>
