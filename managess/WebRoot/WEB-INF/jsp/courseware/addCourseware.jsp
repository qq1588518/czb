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
<title>添加课件</title>
</HEAD>
<style>
*{	MARGIN:0px;	PADDING:0px;}
UL{	MARGIN:0px;	PADDING:0px;	list-style-type:none;}
BODY{	WIDTH:100%;	FONT-SIZE:12px;height:100%;width:100%;}
.pwnav{margin:5px 0px 5px 10px;}
.pwcon li{padding-top:5px;}
.pwcon_span{width:60px;display:block;float:left;text-align:right;}
</style>

<style>
        .el-mask {
    z-index: 20000;
    position: absolute;
    top:0;
    left:0;
    -moz-opacity: 0.5;
    opacity: .50;
    filter: alpha(opacity=30);
    background-color: #7cafda;
    width: 100%;
    height: 100%;
    zoom: 1;
}
.el-mask-msg {
    z-index: 20001;
    position: absolute;
    top: 0;
    left: 0;
    border:1px solid #6593cf;
    background: #c3daf9 url(/images/tb-blue.gif) repeat-x 0 -16px;
    padding:2px;
}
.el-mask-msg div {
    padding:5px 10px 5px 10px;
    background: #eee;
    border:1px solid #a3bad9;
    color:#222;
    font:normal 11px tahoma, arial, helvetica, sans-serif;
    cursor:wait;
    width:100px;
}
</style>
<BODY>
<form id="theForm" name='theForm' method='post' action="courseware.htm?action=saveCourseware" encType="multipart/form-data">
<div class="pwnav" id="content">
	<ul class="pwcon">
		<li >
			<span>课件名称：</span><input type="text" class="text" id="coursewarename" maxlength="30" name="coursewarename" value="${coursewarename}"/></li>
		<li >
		<li >
			<span class="pwcon_span">课件栏目：</span><select name="subjectno" size="1" style="width:120px;height:20px;" class="Tpcform-dorpdownlist" id="subjectno"  >
			<option value="0">--请选择--</option>
			<c:forEach var="clime" items="${subject}">
				<option value="${clime.subjectno }"   <c:if test="${subjectno==clime.subjectno}">selected="selected"</c:if>>${clime.subjectname }</option>
			</c:forEach>
			</select>
		</li>
		<li>
			<span>课件内容：</span><input class="file"  id="courseware"  type="file" name="courseware" style="width:300px;"/><input id="url" name="url" type="hidden" value="${url}"/>
			<input type="button" value="开始上传" style="border:1px solid #a1b7cb;height:20px;display:none;"/><li>
			<img src="about:blank" id="fileChecker" alt="test"  height="18" style="visibility:hidden"/>
		</li>
		<li >
			<span>外部URL：</span><input type="text" class="text" id="otherurl" maxlength="300" name="otherurl" value="${otherurl}"/></li>
		<li >
		<li>
			<span>栏目图片：</span><input class="file"  id="read_pic"  type="file" name="read_pic" style="width:300px;"/><input id="titleimg" name="titleimg" type="hidden" value="${titleimg}"/><br/>
			<span style="color:red;padding-left:60px;">（上传60*60像素，大小不超过5K，以节省手机访问流量）</span>
			<input type="button" value="开始上传" style="border:1px solid #a1b7cb;height:20px;display:none;"/><li>
			<img src="about:blank" id="fileChecker" alt="test"  height="18" style="visibility:hidden"/>
			<div id='mytest'></div>
		</li>
		<li ><input type="hidden" value="${coursewareno}" name="coursewareno"/>
			
		</li>
		<li>
			<input type="button" value="提 交" onclick="saveCourseware()"/><input type="button" value="取 消" onclick="parent.closebutton(parent.document.getElementById('container'));"/>
		</li>
	</ul>
</div>
</form>
</BODY>
<script>




function viewpic(){
	var str = document.getElementById("courseware").value;
	var pic = document.getElementById("url").value;
	var pos = str.lastIndexOf(".");
	var lastname = str.substring(pos,str.length).replace(/(^\s*)|(\s*$)/g,'');
	if(lastname !=""){ 
		if (lastname.toLowerCase()!=".jpg" && lastname.toLowerCase()!=".gif"){
		     //alert("您上传的图片格式为"+lastname+"，图片格式必须为.jpg,.gif类型");
		     //document.getElementById("read_pic").value="";
		     return true;
		 }else {
		 	//document.getElementById("veiwPic").src=str;
			//alert(document.getElementById("veiwPic").src);
		 	return true;
		 }
	 }else{
	 	var otherurl = document.getElementById("otherurl").value.replace(/(^\s*)|(\s*$)/g,'');
	 	if(pic == "" && otherurl==""){
	 		alert("请上传课件或者填入外部url");
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
function saveCourseware(){
	var coursewarename = document.getElementById("coursewarename").value.replace(/(^\s*)|(\s*$)/g,'');
	var subjectno = document.getElementById("subjectno").value;
	var otherurl = document.getElementById("otherurl").value;
	if(otherurl.search(/(^http:\/\/)/g,'')!=-1){
		
	}else {
		if((otherurl.replace(/(^\s*)|(\s*$)/g,'')!="")){
			document.getElementById("otherurl").value="http://"+otherurl;
		}
	}
	if(coursewarename==""){
		alert("请输入课件名称");
		return;
	}
	if(subjectno=="0"){
		alert("请选择所属栏目");
		return;
	}
	if(!viewpic()){return;}
	//changeSrc(document.getElementById("courseware"));
	
	document.getElementById("theForm").submit();
	//document.getElementById("suggestion").style.display="block";
	var panel = document.body;
	var html = "<div class='el-mask'></div>";
   		panel.insertAdjacentHTML('BeforeEnd', html);
   		var mask = panel.lastChild;
   		mask.style.width=panel.offsetWidth;
        mask.style.height=panel.offsetHeight;
   		var msghtml = "<div class='el-mask-msg'  test='hyh'><div>数据上传中</div>";
   		panel.insertAdjacentHTML('BeforeEnd',msghtml);
   		var ele = panel.lastChild;
   		var div_position = panel.getBoundingClientRect();
		w = parseInt(ele.offsetWidth,10)-(parseInt(ele.currentStyle.borderLeftWidth)+parseInt(ele.currentStyle.borderRightWidth,10));
		h=  parseInt(ele.offsetHeight,10)-(parseInt(ele.currentStyle.borderTopWidth)+parseInt(ele.currentStyle.borderBottomWidth,10));
		x = Math.round(w*.5);
        y = Math.round(h*.5);
        var w2 = parseInt(panel.offsetWidth,10);
        var h2 = parseInt(panel.offsetHeight,10);
        var x2 = w2 = Math.round(w2*.5)+parseInt(div_position.left,10);
        var y2 = h2 = Math.round(h2*.5)+parseInt(div_position.top,10);
        var r_x = x2-x;
        var r_y = y2-y-parseInt(div_position.top,10)-20;
   		ele.style.left=r_x;
        ele.style.top=r_y;
        ele.firstChild.style.textAlign="left";
        setInterval(function(){if(ele.firstChild.innerText.length>10){ele.firstChild.innerText="数据上传中";}else{ele.firstChild.innerText=ele.firstChild.innerText+".";}},1000);
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
    oFileChecker.src = filePicker;
    checkSize();
    //alert('item=='+document.getElementById("mytest").filters.item('DXImageTransform.Microsoft.AlphaImageLoader'));
    //document.getElementById("mytest").filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src =filePicker;
    
}
oFileChecker.attachEvent("onreadystatechange",
	function (){
    if (oFileChecker.readyState == "complete")
    {
   		//alert('NNND');
        checkSize();
    }
}


);

/*oFileChecker.onreadystatechange = function ()
{
	alert('jinlaile');
    if (oFileChecker.readyState == "complete")
    {
   
        checkSize();
    }
}*/

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
   		//alert('yyy');
   		alert(document.getElementById("suggestion"));
    	//document.getElementById("suggestion").style.display="inline";
    	//alert('it"s succes6s6f6u7ll');
    	document.getElementById("theForm").submit();
    	
    }
}

</script>
</HTML>
