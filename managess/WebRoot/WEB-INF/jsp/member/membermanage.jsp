<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@page import="java.sql.*,java.io.*,java.util.*,basic.util.CUtil,basic.base.test.*"%>
<%@page import="com.ztenc.oa.proj.service.member.MemberServiceImpl,com.ztenc.oa.proj.json.*;"%>
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
<script src="js/common.js"></script>
<script src="js/prototype.js"></script>
<script src='js/pagescrolling.js'></script>
<script src='js/json.js'></script>
<link rel=stylesheet type=text/css href="${ctx}/css/right.css"/>
<title>用户信息管理</title>

</head>
<body>
<div class="nav" id="container"  style="">
	<form method="post" name="theForm" action='member.htm?action=importMember' enctype="multipart/form-data">
	<div class="main_top">
		<ul class="main_top_title">
			<li>用户信息管理</li>
		</ul>
		<ul class="main_top_title_r">
			<!-- <li class="main_top_title_r_c" onclick="selectAll();">全 选</li> -->
			<!--<li class="main_top_title_r_a" onclick="showOpen_ajax(document.getElementById('container'),'添加用户信息','member.htm?action=addMember',300,180);">添 加</li>-->
			<!-- <li class="main_top_title_r_d">删 除</li>>
			<li>
			<INPUT style="WIDTH:180px" type=file onchange="originalfile.value=this.value" size=1 name=uploadfile value="" />
		    <img src="images/import.gif" onclick='changeSrc();'style='cursor:hand;'/>
		    <img src="images/export.gif" onclick='download();'style='cursor:hand;'/>
		   </li-->
		</ul>
	</div>
	
	<table class="main_select" cellpadding="0" cellspacing="0" align=center style="">
		<tr>
			<td><span>会员帐号：</span><input id="membername" type="text" class="text" value=""></td>
			<td><span>手机号：</span><input id="telno" type="text" class="text" value=""></td>
			<td><span>vpdn：</span><select id="vpdn"><option value="-1">请选择</option><option value="1">开启</option><option value="0">未开启</option></select></td>
			<td><img src="${ctx}/images/index/select.gif" onclick="search();"/></td>
		</tr>
	</table>
	<table class="main_con" cellpadding="0" cellspacing="0"  id="member">
		<tr class="main_con_title">	
			<td>会员卡号</td>
			<td>手机号</td>
			<td style="display:none">所属群组</td>
			<td>VPDN</td>
			<td>注册日期</td>
			<td>开启日期</td>
			<td>关闭日期</td>
			<td class="main_con_operation">基本操作</td>
		</tr>
	</table>
	<div id="count" style="text-align:right;margin-top:10px;"></div>
	</form>
</div>
</body>
<script>
var sAllowExt = "xls";
	function IsExt(url, opt){
		var sTemp;
		var b=false;
		var s=opt.toUpperCase().split("|");
		for (var i=0;i<s.length ;i++ ){
			sTemp=url.substr(url.length-s[i].length-1);
			sTemp=sTemp.toUpperCase();
			s[i]="."+s[i];
			if (s[i]==sTemp){
				b=true;
				break;
			}
		}
		return b;
	}
function changeSrc()
{
	if(document.theForm.uploadfile.value!=""){
   		if(!IsExt(document.theForm.uploadfile.value,sAllowExt)){
		
			alert('上传文件格式不正确');
			return false;
		}
		document.theForm.enctype="multipart/form-data";
		document.theForm.action = "member.htm?action=importMember";
		document.theForm.submit();
	}
}

function download(){
		
		var useracc = document.getElementById("membername").value.replace(/(^\s*)|(\s*$)/g,'');
		var telno = document.getElementById("telno").value.replace(/(^\s*)|(\s*$)/g,'');
		var vpdn = document.getElementById("vpdn").value=="-1"?"":document.getElementById("vpdn").value;
		document.theForm.action = "member.htm?action=exportMember&useracc="+useracc+"&telno="+telno+"&vpdn="+vpdn;
		document.theForm.submit();
}
/////////////////////////////////////读取用户分页///////////////////////////////////
var item = 15;//每页显示的条数，分页
var res;
var pageno = 1;
var index = 0;
readUser();
function search(){

	var membername = document.getElementById("membername").value.replace(/(^\s*)|(\s*$)/g,'');
	var telno = document.getElementById("telno").value.replace(/(^\s*)|(\s*$)/g,'');
	var vpdn = document.getElementById("vpdn").value;
	var obj = new Object();
	if(membername!=""){
	
	   obj["membername"]=membername;
	}
	if(telno!=""){
	
		obj["telno"]=telno;
	}
	if(vpdn=="-1"){
	}else{
		obj["vpdn"]=vpdn;
	}
	var param_str = obj.toJSONString().replace(/(^\s*)|(\s*$)/,'');
	readUser(1,0,item,param_str);
}
function readUser(page,_index,_len,param_str){//index为搜索开始位置，len为每页显示的长度
		pageno = (page==undefined)?1:page;
		index = (_index==undefined)?0:_index;
		item = (_len==undefined)?item:_len;
		if(param_str!=undefined){
		
		   var pars = "index="+index+"&length="+item+"&searchvalue="+param_str;
		}else{
	 	   var pars ="index="+index+"&length="+item;
	 	}
	    	var url = "member.htm?action=memberSearch";
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
	var info = obj["rs"];
	infomsg = info;
	var len = info.length;
	var member_obj = document.getElementById("member");
	var countpage = document.getElementById("count");
	for(var i=member_obj.rows.length;i>1;i--){
		member_obj.deleteRow();
	}
   if(len>0){
	   	for(var i=0;i<len;i++){//判断读取过来的数据有多少条
	   	
	   		var row = member_obj.insertRow();
	   		row.id = "maindetail"+(i+1);
		   	row.onmouseover="changecolor(this);";
		   	row.onmouseout="changecolor(this);";
		   	var cell2 =row.insertCell();
	   		cell2.innerText = info[i][2];
	   	    //var cell3 =row.insertCell();
	   		//cell3.innerText = (info[i][3]=="" ||info[i][3]==null)?" ":info[i][3];
	   		var cell4 =row.insertCell();
	   		cell4.innerText = (info[i][8]=="" ||info[i][8]==null)?" ":info[i][8];
	   		var cell8 =row.insertCell();
	   		cell8.innerText = info[i][5];
	   		cell8.id=info[i][6];
	   		cell8.style.display="none";
	   		var cell9 = row.insertCell();
	   		cell9.innerText = info[i][7]
	   		var cell12 = row.insertCell();
	   		cell12.innerText = info[i][11];
	   		var cell10 = row.insertCell();
			cell10.innerText = info[i][9]==null?" ":info[i][9];
			var cell11 = row.insertCell();
			cell11.innerText = info[i][10]==null?" ":info[i][10] ;
			var cell7 =row.insertCell();
			info[i][8] = info[i][8]==null?" ":info[i][8];
	   		cell7.innerHTML='<span  style="cursor:hand" onclick="showOpen_ajax(container,\'修改用户信息\',\'member.htm?action=modifyMember&obj='+encodeURIComponent(info[i])+'\',300,220);">修 改</span>&nbsp;&nbsp;<span style="cursor:hand" onclick="delconfirm(\''+info[i][0]+'\');">删 除</span>';
	   		//cell7.innerHTML='<span style="cursor:hand" onclick="delconfirm(\''+info[i][0]+'\');">删 除</span>';
	   	}
	   	var count = info[0][4];
	   	if(count%item == 0){
	   		var page = count/item;
	   	}else{
	   		var page = parseInt(count/item)+1;
	   	}
	   	var pagescrolling =new PageScrolling();  
        pagescrolling.setCurrentPage(pageno); 
        pagescrolling.setTotalPage(page);   
        pagescrolling.setLength(count);
        pagescrolling.setpageitem(item);
        countpage.innerHTML=pagescrolling.viewHtml2();
        document.getElementById('count').style.display="";
   }else{
   	   var row = member_obj.insertRow();
	   var cell1 = row.insertCell();
	   cell1.innerHTML = '<!--span style="margin-right:20px;cursor:pointer;" onclick="document.getElementById(\'membername\').value=\'\';document.getElementById(\'telno\').value=\'\';readUser(1,0,10);">返 回</span-->';
   	   document.getElementById('count').style.display="none";
   }
}
function movePageTo(page,startnum,len){  //分页


	var membername = document.getElementById("membername").value.replace(/(^\s*)|(\s*$)/g,'');
	var telno = document.getElementById("telno").value.replace(/(^\s*)|(\s*$)/g,'');
	var vpdn = document.getElementById("vpdn").value;
	var obj = new Object();
	if(membername!=""){
	
	   obj["membername"]=membername;
	}
	if(telno!=""){
	
		obj["telno"]=telno;
	}
	if(vpdn=="-1"){
	}else{
		obj["vpdn"]=vpdn;
	}
	var param_str = obj.toJSONString().replace(/(^\s*)|(\s*$)/,'');
	readUser(page,startnum,len,param_str);
}
/////////////////////////////////////读取用户板分页///////////////////////////////////

function delconfirm(info){
	var a = confirm("确定删除此会员吗？");
	var fbid = info;
	if(a){
		deleteMember(fbid);
	}else{
		return;
	}
}

function deleteMember(info){
	var fbid = info;
	var pars ="flag=deleteMember&fbid="+fbid;
	    	var url = "member.htm?action=deleteMember";
	    	var reqAjax = new Ajax.Request(    
	        	url,    
	        	{    
	            	method: 'post', 
	            	parameters: pars,
					onCreate: loadingFun,
	           	    onComplete: deleteMemberResponse    
	        	}    
	    ); 
}
function deleteMemberResponse(response){
	var text = response.responseText;
	var obj = eval("("+text+")");
	if(obj["flag"]=='1'){
		alert("删除成功!");
		var protype_obj = document.getElementById("member");
		if(pageno !=1){
			if(protype_obj.rows.length > 2){
				readUser(pageno,index,item);
			}else{
				pageno = pageno-1;
				index = index-item;
				readUser(pageno,index,item);
			}
		}else{
			readUser(pageno,index,item);
		}
	}
}
/////////////////////////////////////删除选中的用户信息///////////////////////////////////



/////////////////////////////////////控制textarea输入框的输入长度///////////////////////////////////
var element
var maxLength
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
function tests(){}
/////////////////////////////////////控制textarea输入框的输入长度///////////////////////////////////
</script>
</html>