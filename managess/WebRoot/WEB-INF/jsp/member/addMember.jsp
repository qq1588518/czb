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
		<li >
			<span class="pwcon_span">会员名称11：</span><input type="text" class="text" id="membername" maxlength="16" name="membername" value="${name}"/></li>
		<li >
			<span class="pwcon_span">所属权限组：</span><select name="group" size="1" style="width:120px;height:20px;" class="Tpcform-dorpdownlist" id="group"  >
			<option value="0">--请选择--</option>
			<c:forEach var="clime" items="${group}">
				<option value="${clime.groupid }">${clime.groupname }</option>
			</c:forEach>
			</select>
		</li>
		<li >
			<span class="pwcon_span">手机号：</span><input type="text" class="text" id="telno" maxlength="11" name="telno" value=""/>
		</li>
		<li >
			<span class="pwcon_span">机器码：</span><input type="text" class="text" id="machineid" maxlength="16" name="machineid" value=""/>
		</li>
		<li >
			<!-- <c:if test="${'1'==vpdn}">checked</c:if><c:if test="${'0'==vpdn}">checked</c:if><span class="pwcon_span">备&nbsp;&nbsp;&nbsp;&nbsp;注：</span><textarea id="memo" Cols='35' rows='5' maxlength=200 name="remark" onkeypress='doKeypress()' onkeydown='doKeydown()' onbeforepaste='doBeforePaste()' onpaste='doPaste()'>${remark}</textarea> -->
		</li>
		<li>
			<input type="button" value="提 交" onclick="saveMember()"/><input type="button" value="取 消" onclick="parent.closebutton(parent.document.getElementById('container'));"/>
		</li>
	</ul>
</div>
</form>
</BODY>
<script>

function doSave(member_name,groupno,telno,machineid,vpdn){//index为搜索开始位置，len为每页显示的长度
	var pars ="membername="+member_name+"&group="+groupno+"&telno="+telno+"&machineid="+machineid+"&vpdn="+vpdn;
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
	
		alert("添加成功!");
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
	var member_name = document.getElementById("membername").value.replace(/(^\s*)|(\s*$)/g,'');
	var groupno = document.getElementById("group").value;
	var telno = document.getElementById("telno").value;
	var machineid = document.getElementById("machineid").value;
	var vpdn = document.getElementsByName('vpdn')[0].checked?"1":"0";
	//var remark = document.getElementById("fb_content").value;
	if(member_name==""){
		alert("请输入会员名称");
		return;
	}
	if(groupno=="0"){
		alert("请选择所属权限组");
		return;
	}
	//document.getElementById("theForm").submit();
	doSave(member_name,groupno,telno,machineid,vpdn);
}



/////////////////////////////////////控制textarea输入框的输入长度///////////////////////////////////


/////////////////////////////////////控制textarea输入框的输入长度///////////////////////////////////
</script>
</HTML>
