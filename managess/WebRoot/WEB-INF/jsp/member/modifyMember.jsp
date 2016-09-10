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
<title>添加会员</title>
</HEAD>
<style>
*{	MARGIN:0px;	PADDING:0px;}
UL{	MARGIN:0px;	PADDING:0px;	list-style-type:none;}
BODY{	WIDTH:100%;	FONT-SIZE:12px;height:100%;width:100%;}
.pwnav{margin:5px 0px 5px 10px;}
.pwcon li{padding-top:5px;}
.pwcon_span{width:75px;display:block;float:left;text-align:right;}
</style>
<BODY>
<form id="theForm" name='theForm' method='post' action="protype.htm?action=saveProtype" encType="multipart/form-data">
<div class="pwnav">
	<ul class="pwcon">
		<!-- <li>
			<span class="pwcon_span">会员名称：</span><input type="text" class="text" id="membername" maxlength="16" name="membername" value="${membername}" readonly /></li> -->
		<li>
			<span class="pwcon_span">帐号：</span><input type="text" class="text" id="useracc" maxlength="16" name="useracc" readonly value="${userAcc}" readonly />
		</li>
		<li>
			<span class="pwcon_span">所属权限组：</span><select disabled name="group" size="1" style="width:120px;height:20px;" class="Tpcform-dorpdownlist" id="group"  >
			<option value="0">--请选择--</option>
			<c:forEach var="clime" items="${group}">
				<option value="${clime.groupid }" <c:if test="${groupno==clime.groupid}">selected="selected"</c:if>>${clime.groupname }</option>
			</c:forEach>
			</select>
		</li>
		
		<li>
			<span class="pwcon_span">IMSI：</span><input type="text" class="text" id="imsi" maxlength="16" name="imsi" value="${imsi}" readonly />
		</li>
		<li>
			<span class="pwcon_span">手机号：</span><input type="text" class="text" id="telno" maxlength="16" name="telno" value="${telno}" />
		</li>
		<li>
			<span class="pwcon_span">VPDN: </span><input type="radio"  name="vpdn" id='vpdn' <c:if test="${'1'==vpdn}">checked</c:if> value='1'/><label for='vpdn' >开启 </label><input type='radio' id='vpdn2' name='vpdn' value='0' <c:if test="${'0'==vpdn}">checked</c:if> /><label for='vpdn2'>停用</label></li>
		
		<li>
			<!-- <span class="pwcon_span">备&nbsp;&nbsp;&nbsp;&nbsp;注：</span><textarea id="memo" Cols='35' rows='5' maxlength=200 name="remark" onkeypress='doKeypress()' onkeydown='doKeydown()' onbeforepaste='doBeforePaste()' onpaste='doPaste()'>${memo}</textarea> -->
		</li>
		<li class="pwcon_span">
			<input type="hidden" value="${memberno}" name="memberno" id="memberno"/>
		</li>
		<li>
			<input type="button" value="提 交" onclick="saveMember()"/><input type="button" value="取 消" onclick="parent.closebutton(parent.document.getElementById('container'));"/>
		</li>
	</ul>
</div>
</form>
</BODY>
<script>

function doSave(memberno,member_name,groupno,telno,imsi,vpdn,useracc){//index为搜索开始位置，len为每页显示的长度
	var pars ="memberno="+memberno+"&membername="+member_name+"&group="+groupno+"&telno="+telno+"&imsi="+imsi+"&vpdn="+vpdn+"&useracc="+useracc;
	    	var url = "member.htm?action=saveMember";
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
	    if(obj["is_send"]){
	    	alert("修改成功!短信已下发");
	    }else{
	    	alert("修改成功!短信下发失败");
	    }
		parent.readUser(1,0,10);
		parent.closebutton(parent.document.getElementById("container"));
	}
}
/**
	 * 保存要添加、修改的栏目信息
	 * 
	 * @param id
	 * @return
	 */
	 
function saveMember(){
	var memberno = document.getElementById("memberno").value.replace(/(^\s*)|(\s*$)/g,'');
	var groupno = document.getElementById("group").value;
	var telno = document.getElementById("telno").value;
	var useracc = document.getElementById("useracc").value;
	var imsi = document.getElementById("imsi").value;
	var vpdn = document.getElementsByName('vpdn')[0].checked?"1":"0";
	//var remark = document.getElementById("fb_content").value;
	if(memberno==null || memberno==""){
	
		alert("修改操作失败");
		return;
	}
	if(groupno=="0"){
		alert("请选择所属权限组");
		return;
	}
	//document.getElementById("theForm").submit();
	var member_name = "";
	doSave(memberno,member_name,groupno,telno,imsi,vpdn,useracc);
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
</HTML>
