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
<script src="js/common.js"></script>
<script src="js/prototype.js"></script>
<script src='js/pagescrolling.js'></script>
<script src='js/json.js'></script>
<link rel=stylesheet type=text/css href="${ctx}/css/right.css"/>
<title>群组信息管理</title>
</head>
<body>
<div class="nav" id="container"  style="">
	<div class="main_top">
		<ul class="main_top_title">
			<li>群组信息管理</li>
		</ul>
		<ul class="main_top_title_r">
			<!-- <li class="main_top_title_r_c" onclick="selectAll();">全 选</li> -->
			<li class="main_top_title_r_a" onclick="showOpen_ajax(document.getElementById('container'),'添加群组信息','group.htm?action=addGroup',400,180);">添 加</li>
			<!-- <li class="main_top_title_r_d">删 除</li>-->
		</ul>
	</div>
	<table class="main_select" cellpadding="0" cellspacing="0" align=center style="display:none">
		<tr>
			<td><span>群组名称：</span><input id="groupname" type="text" class="text" value=""></td>
			<td><img src="${ctx}/images/index/select.gif" onclick="search();"/></td>
		</tr>
	</table>
	<table class="main_con" cellpadding="0" cellspacing="0"  id="group">
		<tr class="main_con_title">	
			<td>群组名称</td>
			<td>备注</td>
			<td class="main_con_operation">基本操作</td>
		</tr>
	</table>
	<div id="count" style="text-align:right;margin-top:10px;"></div>
	
</div>
</body>
<script>
/////////////////////////////////////读取用户分页///////////////////////////////////
var item = 10;//每页显示的条数，分页
var res;
var pageno = 1;
var index = 0;
readUser();
function search(){

	var groupname = document.getElementById("groupname").value.replace(/(^\s*)|(\s*$)/g,'');
	var obj = new Object();
	if(groupname!=""){
	
	   obj["membername"]=groupname;
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
	    	var url = "group.htm?action=groupSearch";
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
	var group_obj = document.getElementById("group");
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
		   	var cell2 =row.insertCell();
	   		cell2.innerText = info[i][1];
	   	    var cell3 =row.insertCell();
			if(info[i][2].replace(/[^u4e00-u9fa5]/g,'aa').length>8){
			
				cell3.innerText = info[i][2].substring(0,8)+"......";
			}else{
				cell3.innerText = info[i][2]==""?" ":info[i][2];
			}
			var cell7 =row.insertCell();
	   		cell7.innerHTML='<span  style="cursor:hand" onclick="showOpen_ajax(container,\'修改群组信息\',\'group.htm?action=modifyGroup&obj='+encodeURIComponent(info[i])+'\',400,180);">修 改</span>&nbsp;&nbsp;<span style="cursor:hand" onclick="delconfirm(\''+info[i][0]+'\');">删 除</span>';
	   	}
	   	
	   	var count = info[0][3];
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
   		var row = group_obj.insertRow();
	    var cell1 = row.insertCell();
	    cell1.innerHTML = '<!--span style="margin-right:20px;cursor:pointer;" onclick="document.getElementById(\'groupname\').value=\'\';readUser(1,0,10);">返 回</span-->';
   	    document.getElementById('count').style.display="none";
   }
}
function movePageTo(page,startnum,len){  //分页
	readUser(page,startnum,len);
}
/////////////////////////////////////读取群组板分页///////////////////////////////////

function delconfirm(info){
	var a = confirm("确定删除此群组吗？");
	var fbid = info;
	if(a){
		deleteGroup(fbid);
	}else{
		return;
	}
}

function deleteGroup(info){
	var fbid = info;
	var pars ="fbid="+fbid;
	    	var url = "group.htm?action=deleteGroup";
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
		var protype_obj = document.getElementById("group");
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
	}else if(obj["flag"]=='-1'){
	
		alert("请先删除群组对应的信息、例如产品、课件、用户信息等!");
	}else if(obj["flag"]=="0"){
		alert("删除失败!");
	}
}
/////////////////////////////////////删除选中的群组信息///////////////////////////////////



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