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
<SCRIPT src="${ctx}/scripts/system.js" type=text/javascript></SCRIPT>
<SCRIPT src="${ctx}/scripts/layer.js" type=text/javascript></SCRIPT>
<script src="js/common.js"></script>
<script src="${ctx}/js/prototype.js"></script>
<script src='${ctx}/js/pagescrolling.js'></script>
<link rel=stylesheet type=text/css href="${ctx}/css/right.css"/>
<title>留言板信息管理</title>
<style>
.item_msg{	border:1px solid #a8c7ce;	margin-top:10px;	width:99%;	text-align:left;	display:none;}
.item_msg li{	border-bottom:1px dashed #acacac;	padding:10px;}
.item_msg li span{	margin-right:40px;}
</style>
</head>
<body>
<div class="nav" id="container"  style="">
	<div class="main_top">
		<ul class="main_top_title">
			<li>留言板信息管理</li>
		</ul>
		<ul class="main_top_title_r" style="display:none;">
			<li class="main_top_title_r_c" onclick="selectAll();">全 选</li>
			<li class="main_top_title_r_a" onclick="showOpen(document.getElementById('container'),'发布物流信息','login.jsp',700,310);">添 加</li>
			<li class="main_top_title_r_d">删 除</li>
		</ul>
	</div>
	<table class="main_select" cellpadding="0" cellspacing="0" align=center>
		<tr align=left>
			<td>
				<span>留言者：</span>
				<input type="text" class="text" id="title" onblur="readMsg(1,0,5);"><img src="${ctx}/images/index/select.gif" onclick="readMsg(1,0,5);" style="margin-left:10px;"/>
			</td>
		</tr>
	</table>
	<table class="main_con" cellpadding="0" cellspacing="0" id="item_con" style="table-layout：fixed;">
		<tr class="main_con_title">	
			<td>留言内容</td>
			<td width='150'>留言者</td>
			<td width='150'>时间</td>
			<td class="main_con_operation">基本操作</td>
		</tr>
	</table>
	
	<div id="count" style="text-align:right;margin-top:10px;"></div>
	<div class="item_msg" id="item_msg"></div>
	
</div>
</body>
<script>
/////////////////////////////////////读取留言板分页///////////////////////////////////
var item = 15;
var res;
var pageno = 1;
var index = 0;
readMsg(1,0,item);
function readMsg(page,_index,_len){//index为搜索开始位置，len为每页显示的长度
		pageno = page;
		index = _index;
		item = _len;
		var title = document.getElementById("title").value.replace(/(^\s*)|(\s*$)/g,'');
	 	var pars ="index="+index+"&length="+item+"&title="+title;
	    	var url = "msg.htm?action=msgSearch";
	    	var reqAjax = new Ajax.Request(    
	        	url,    
	        	{    
	            	method: 'post', 
	            	parameters: pars,
					onCreate: loadingFun,
	           	    onComplete: readMsgResponse    
	        	}    
	    ); 
	 
	 }
function loadingFun(){}
function readMsgResponse(response){
   	var text = response.responseText;
	var obj = eval("("+text+")");
	var info = obj["rs"];
	infomsg = info;
	var len = info.length;
	var group_obj = document.getElementById("item_con");
	var countpage = document.getElementById("count");
	for(var i=group_obj.rows.length;i>1;i--){
		group_obj.deleteRow();
	}
   if(len>0){
	   	for(var i=0;i<len;i++){//判断读取过来的数据有多少条
	   		var row = group_obj.insertRow();
	   		row.id = "maindetail"+(i+1);
		   	row.onmouseover="changecolor(this);";
		   	row.onmouseout="changecolor(this);";
		   	var cell1 =row.insertCell();
		   	if(info[i][3].length >30){
				cell1.innerText=info[i][3].substring(0,30)+"......";
			}else{
				cell1.innerText=info[i][3];
			}
	   	    var cell2 =row.insertCell();
	   	    cell2.innerHTML = info[i][8];
	   	    var cell3 = row.insertCell();
	   	    cell3.innerText = info[i][4];
			var cell4 =row.insertCell();
			if(info[i][6]=='0'){
	   			cell4.innerHTML='<span style="color:red;cursor:pointer;"  onclick="replyMsg('+i+');">未回复</span><span style="padding-left:10px;cursor:pointer;" onclick="delconfirm('+info[i][0]+');">删除</span>';
	   		}else{
	   			cell4.innerHTML='<span style="cursor:pointer;" onclick="msgdisplay('+info[i][0]+');">已回复</span><span style="padding-left:10px;cursor:pointer;" onclick="delconfirm('+info[i][0]+');">删除</span>';
	   		}
	   	}
	   	
	   	
	   	var count = info[0][7];
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
   }else{
	   	countpage.innerHTML = "";
   }
}
function movePageTo(page,startnum,len){  //分页
	readMsg(page,startnum,len);
}
/////////////////////////////////////读取留言板分页///////////////////////////////////

/////////////////////////////////////回复留言者信息///////////////////////////////////
var infomsg;
function replyMsg(i){
	var fbid = infomsg[i][0];
	var name = infomsg[i][1];
	var Tel = infomsg[i][2];
	var Msg = infomsg[i][3];
	var Date = infomsg[i][4];
	var Type = infomsg[i][5];
	var Sfhf = infomsg[i][6];
	var tmp_str = "";
	var replyMsg_html = document.getElementById("item_msg");
	replyMsg_html.innerHTML = "";
	tmp_str = tmp_str + "<ul>";
	tmp_str = tmp_str + "	<li><!--span>留言者："+name+"</span><span>电话："+Tel+"</span><span>留言类型："+Type+"</span--><span>留言时间："+Date+"</span></li>";
	tmp_str = tmp_str + "	<li>留言内容："+Msg+"</li>";
	tmp_str = tmp_str + "	<li style='color:red;'><span style='margin-right:0px;'>回复：</span><textarea  id='fb_content'  name='fb_content'  Cols='90%' rows='5' maxlength=200 onkeypress='doKeypress()' onkeydown='doKeydown()' onbeforepaste='doBeforePaste()' onpaste='doPaste()'></textarea></li>";
	tmp_str = tmp_str + "	<li><input type='button' value='回 复' style='margin-left:50px;;float:left' onclick='reMsg("+fbid+","+Sfhf+");'/></li>";
	tmp_str = tmp_str + "</ul>";
	replyMsg_html.innerHTML=tmp_str;
	replyMsg_html.style.display="block";
	element=document.getElementById("fb_content");
	maxLength = element.maxlength;
}
var fid;
function reMsg(id,hf){
	var fbid = id;
	var sfhf = hf;
	fid = fbid;
	var replyID = createRandomMultiID(8);
	var replycon = document.getElementById("fb_content").value;
	if(replycon.replace(/(^\s*)|(\s*$)/g,'')==""){
		alert("请输入您要回复的内容");
		return;
	}
	var pars ="fbid="+fbid+"&sfhf="+sfhf+"&replyID="+replyID+"&replycon="+replycon;
	    	var url = "msg.htm?action=reMsg";
	    	var reqAjax = new Ajax.Request(    
	        	url,    
	        	{    
	            	method: 'post', 
	            	parameters: pars,
					onCreate: loadingFun,
	           	    onComplete: reMsgResponse    
	        	}    
	    ); 
}
function reMsgResponse(response){
	var text = response.responseText;
	var obj = eval("("+text+")");
	if(obj["flag"]=="1"){
		alert("回复成功！");
		readMsg(pageno,index,item);
		msgdisplay(fid);
	}
}
/////////////////////////////////////回复留言者信息///////////////////////////////////


/////////////////////////////////////删除选中的留言信息///////////////////////////////////
function delconfirm(info){
	var a = confirm("确定删除这条留言吗？删除后管理员回复的留言也将删除，留言信息将不能恢复。");
	var fbid = info;
	if(a){
		deleteMsg(fbid);
	}else{
		return;
	}
}

function deleteMsg(info){
	var fbid = info;
	var pars ="flag=deleteMsg&fbid="+fbid;
	    	var url = "msg.htm?action=deleteMsg";
	    	var reqAjax = new Ajax.Request(    
	        	url,    
	        	{    
	            	method: 'post', 
	            	parameters: pars,
					onCreate: loadingFun,
	           	    onComplete: deleteMsgResponse    
	        	}    
	    ); 
}
function deleteMsgResponse(response){
	var text = response.responseText;
	var obj = eval("("+text+")");
	if(obj["flag"]=='1'){
		alert("删除成功!");
		var protype_obj = document.getElementById("item_con");
		if(pageno !=1){
			if(protype_obj.rows.length > 2){
				readMsg(pageno,index,item);
			}else{
				pageno = pageno-1;
				index = index-item;
				readMsg(pageno,index,item);
			}
		}else{
			readMsg(pageno,index,item);
		}
		document.getElementById("item_msg").style.display="none";
	}
}
/////////////////////////////////////删除选中的留言信息///////////////////////////////////

/////////////////////////////////////读取己回复信息的详细信息///////////////////////////////////
function msgdisplay(info){
	var fbid = info;
	var pars ="flag=replyed_msg&fbid="+fbid;
	    	var url = "msg.htm?action=replyed_msg";
	    	var reqAjax = new Ajax.Request(    
	        	url,    
	        	{    
	            	method: 'post', 
	            	parameters: pars,
					onCreate: loadingFun,
	           	    onComplete: msgdisplayResponse    
	        	}    
	    ); 
}
function msgdisplayResponse(response){
	var text = response.responseText;
	var obj = eval("("+text+")");
	var info = obj["rs"];
	var item_msg = document.getElementById("item_msg");
    var tmp_str = "";
    item_msg.innerHTML="";
   	//tmp_str=tmp_str + '<ul><li><span>留言者：'+info[0][0]+'</span><span>电话：'+info[0][3]+'</span><span>留言类型：'+info[0][4]+'</span><span>留言时间：'+info[0][2]+'</li><li>留言内容：'+info[0][1]+'</li><li style="color:red;"><span>回复者：'+info[0][5]+'</span><span>回复时间：'+info[0][7]+'</span></li><li style="color:red;"">回复内容：'+info[0][6]+'</li></ul>';
   	tmp_str=tmp_str + "<ul>";
   	tmp_str=tmp_str + "	<li><span>留言者："+info[0][0]+"</span><span>电话："+info[0][3]+"</span><span>留言类型："+info[0][4]+"</span><span>留言时间："+info[0][2]+"</span></li><li>留言内容："+info[0][1]+"</li>";
   	tmp_str=tmp_str + "	<li style='color:red;'><span>回复者："+info[0][5]+"</span><span>回复时间："+info[0][7]+"</span></li>";
   	tmp_str=tmp_str + "	<li style='color:red;'><span style='margin-right:0px;'>回复：</span><textarea  id='fb_content'  name='fb_content'  Cols='90%' rows='5' maxlength=200 onkeypress='doKeypress()' onkeydown='doKeydown()' onbeforepaste='doBeforePaste()' onpaste='doPaste()'>"+info[0][6]+"</textarea></li>";
   	tmp_str=tmp_str + "	<li><input type='button' value='回 复' onclick='modifyreplay(\""+info[0][9]+"\");' style='margin-left:50px;;float:left'/><input type='button' value='删  除' onclick='delete_replay_confirm("+info[0][8]+",\""+info[0][9]+"\");' style='margin-left:50px;;float:left'/></li>";
   	tmp_str=tmp_str + "</ul>";
   	item_msg.innerHTML=tmp_str;
   	item_msg.style.display="block";
   	element=document.getElementById("fb_content");
	maxLength = element.maxlength;
   	}
/////////////////////////////////////读取己回复信息的详细信息///////////////////////////////////


/////////////////////////////////////删除回复信息///////////////////////////////////
function delete_replay_confirm(id,resid){
	var a = confirm("确定删除这条管理员回复的信息吗？");
	var fbid = id;
	var resId = resid;
	if(a){
		delete_replay(fbid,resId)
	}else{
		return;
	}
}

function delete_replay(id,resid){
	var fbid = id;
	var resId = resid;
	var pars ="flag=deleteMsg&fbid="+fbid+"&resId="+resId;
	    	var url = "msg.htm?action=deleteReplay";
	    	var reqAjax = new Ajax.Request(    
	        	url,    
	        	{    
	            	method: 'post', 
	            	parameters: pars,
					onCreate: loadingFun,
	           	    onComplete: delete_replayResponse    
	        	}    
	    ); 
}
function delete_replayResponse(response){
	var text = response.responseText;
	var obj = eval("("+text+")");
	if(obj["flag"]=='1'){
		alert("删除成功!");
		var protype_obj = document.getElementById("item_con");
		if(pageno !=1){
			if(protype_obj.rows.length > 2){
				readMsg(pageno,index,item);
			}else{
				pageno = pageno-1;
				index = index-item;
				readMsg(pageno,index,item);
			}
		}else{
			readMsg(pageno,index,item);
		}
	}
}
/////////////////////////////////////删除回复信息///////////////////////////////////



/////////////////////////////////////修改留言信息///////////////////////////////////
function modifyreplay(id){
	var Id = id;
	var replycon = document.getElementById("fb_content").value;
	if(replycon.replace(/(^\s*)|(\s*$)/g,'')==""){
		alert("请输入您要回复的内容");
		return;
	}
	var pars ="id="+Id+"&replycon="+replycon;
	    	var url = "msg.htm?action=modifyreplay";
	    	var reqAjax = new Ajax.Request(    
	        	url,    
	        	{    
	            	method: 'post', 
	            	parameters: pars,
					onCreate: loadingFun,
	           	    onComplete: modifyreplayResponse    
	        	}    
	    ); 
}
function modifyreplayResponse(response){
	var text = response.responseText;
	var obj = eval("("+text+")");
	if(obj["flag"]=="1"){
		alert("修改成功！");
		readMsg(pageno,index,item);
	}
}
/////////////////////////////////////修改留言信息///////////////////////////////////


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
/////////////////////////////////////控制textarea输入框的输入长度///////////////////////////////////
</script>
</html>