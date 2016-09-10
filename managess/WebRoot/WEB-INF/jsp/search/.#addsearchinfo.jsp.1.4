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
<title>添加修改搜索栏目</title>
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
<form id="theForm" name='theForm' method='post' action="search.htm?action=saveProtype" encType="multipart/form-data">
<div class="pwnav">
	<ul class="pwcon">
		<li >
			<span class="pwcon_span">名&nbsp;&nbsp;&nbsp;&nbsp;称：</span><input type="text" class="text" id="namep" maxlength="10" name="name_product" value="${name}"/></li>
		<li >
			<span>搜索栏目：</span><select name="select_protype" size="1" style="width:120px;height:20px;" class="Tpcform-dorpdownlist" id="select_protype"  >
			<option value="0">--请选择--</option>
			<c:forEach var="clime" items="${protype_clime }">
				<option value="${clime.proId }" <c:if test="${colcom==clime.proId }">selected="selected"</c:if>>${clime.proName }</option>
			</c:forEach>
				<option value="1008" <c:if test="${colcom=='1008' }">selected="selected"</c:if>>事业百问</option>
			</select>
		</li>
		<!-- li >
			<span>所属权限：</span><select name="select_protype1" size="1" style="width:120px;height:20px;" class="Tpcform-dorpdownlist" id="select_protype1"  >
			<c:forEach var="groupss" items="${groups }">
				<option value="${groupss.groupid }" <c:if test="${quanxian==groupss.groupid}">selected="selected"</c:if>>${groupss.groupname }</option>
			</c:forEach>
			</select><span style="color:red;">（默认权限为非会员）</span>
		</li-->
		<li >
			<span>搜索图片：</span><input class="file"  id="read_pic"  type="file" name="picproduct" style="width:300px;"/><input id="pic" name="pic_addr" type="hidden" value="${pic}"/>
			<span style="color:red;padding-left:60px;">（上传35*35像素，大小不超过5K，以节省手机访问流量）</span>
			<input type="button" value="开始上传" style="border:1px solid #a1b7cb;height:20px;display:none;"/>
		</li>
		<li >
			<span class="pwcon_span">地&nbsp;&nbsp;&nbsp;&nbsp;址：</span><textarea id="fb_content" Cols='35' rows='5' maxlength=50 name="addr" onkeypress='doKeypress()' onkeydown='doKeydown()' onbeforepaste='doBeforePaste()' onpaste='doPaste()'>${uri}</textarea>
			<span style="color:red;padding-left:60px;">例如：百度（http://www.baidu.com/s?wd=）</span>
		</li>
		<li >
			<input type="hidden" value="${id}" name="cata_id"/>
		</li>
		<li>
			<input type="button" value="提 交" onclick="saveProduct()"/><input type="button" value="取 消" onclick="parent.closebutton(parent.document.getElementById('container'));"/>
		</li>
	</ul>
</div>
<input type="text" style="display:none" />
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
	 		alert("请上传您的搜索条目图片");
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
	var column_type = document.getElementById("select_protype").value;
	var uri = document.getElementById("fb_content").value;
	if(column_name==""){
		alert("请输入名称");
		return;
	}
	if(!viewpic()){return;}
	if(column_type ==0 && uri == ""){
		alert("搜索栏目与地址必须写一个");
		return;
	}else if(column_type !=0 && uri != ""){
		alert("搜索栏目与地址必须写一个,站内搜索必须在搜索栏目选择栏目，站外搜索必须在地址栏输入地址");
		return;
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
