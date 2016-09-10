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
<title>课件栏目管理</title>
</head>
<body>
<div class="nav" id="container"  style="">
	<div class="main_top">
		<ul class="main_top_title">
			<li>课件栏目管理</li>
		</ul>
		<ul class="main_top_title_r">
			<!-- <li class="main_top_title_r_c" onclick="selectAll();">全 选</li> -->
			<li class="main_top_title_r_a" onclick="showOpen_ajax(document.getElementById('container'),'添加课件栏目信息','subject.htm?action=addSubject',500,280);">添 加</li>
			<!-- <li class="main_top_title_r_d">删 除</li>-->
		</ul>
	</div>
	<table class="main_select" cellpadding="0" cellspacing="0" align=center style="display:none">
		<tr>
			<td><span>栏目名称：</span><input id="subjectname" type="text" class="text" value=""></td>
			<td><img src="${ctx}/images/index/select.gif" onclick="search();"/></td>
		</tr>
	</table>
	<table class="main_con" cellpadding="0" cellspacing="0"  id="subject">
		<tr class="main_con_title">	
			<td>栏目名称</td>
			<td>栏目图片</td>
			<td>Level</td>
			<td class="main_con_operation">基本操作</td>
		</tr>
	</table>
	
	<div id="count" style="text-align:right;margin-top:10px;"></div>
	
</div>
</body>
<script>
/////////////////////////////////////读取用户分页///////////////////////////////////
var res;
var pageno = 1;
var index = 0;
var item = 15;
readUser();
function search(){

	var subjectname = document.getElementById("subjectname").value.replace(/(^\s*)|(\s*$)/g,'');
	var obj = new Object();
	if(subjectname!=""){
	
	   obj["subjectname"]=subjectname;
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
	    	var url = "subject.htm?action=subjectSearch";
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
	var group_obj = document.getElementById("subject");
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
	   	    cell3.innerHTML = '<img src="'+info[i][2]+'" style="height:30px;width:40px;"/>';
	   	    var cell4 = row.insertCell();
	   	    cell4.innerText = info[i][3]
			var cell7 =row.insertCell();
	   		cell7.innerHTML='<span  style="cursor:hand" onclick="showOpen_ajax(container,\'修改栏目信息\',\'subject.htm?action=modifySubject&page='+pageno+'&obj='+encodeURIComponent(info[i])+'\',500,280);">修 改</span>&nbsp;&nbsp;<span style="cursor:hand" onclick="delconfirm(\''+info[i][0]+'\');">删 除</span>';
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
        document.getElementById('count').style.display="";
        countpage.innerHTML=pagescrolling.viewHtml2();
   }else{
   		var row = group_obj.insertRow();
	    var cell1 = row.insertCell();
	    cell1.innerHTML = '<!--span style="margin-right:20px;cursor:pointer;" onclick="document.getElementById(\'subjectname\').value=\'\';readUser(1,0,10);">返 回</span-->';
   	    document.getElementById('count').style.display="none";
   }
}
function movePageTo(page,startnum,len){  //分页
	readUser(page,startnum,len);
	
}
/////////////////////////////////////读取留言板分页///////////////////////////////////

function delconfirm(info){
	var a = confirm("确定删除此课件栏目吗？删除后所有的课件栏目内容也将全部删除，并不能恢复。");
	var fbid = info;
	if(a){
		deleteSubject(fbid);
	}else{
		return;
	}
}

function deleteSubject(info){
	var fbid = info;
	var pars ="fbid="+fbid;
	    	var url = "subject.htm?action=deleteSubject";
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
		var protype_obj = document.getElementById("subject");
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
/////////////////////////////////////删除选中的留言信息///////////////////////////////////



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
function restore(){
	var obj = document.getElementById("container");
	readUser(pageno,index,item);
	closebutton(obj);

}
/////////////////////////////////////控制textarea输入框的输入长度///////////////////////////////////
</script>
</html>