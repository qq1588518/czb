<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
<link rel=stylesheet type=text/css href="css/right.css"/>
<style>
.main_con td span{margin-left:10px;cursor:pointer;}
.main_con_operation{width:170px !important;}
</style>
<title>站内外搜索信息管理</title>
</head>
<body>
<div class="nav" id="container">
	<div class="main_top">
		<ul class="main_top_title">
			<li>站内外搜索信息管理</li>
		</ul>
		<ul class="main_top_title_r">
			<!-- li class="main_top_title_r_c" onclick="selectAll();">全 选</li -->
			<li class="main_top_title_r_a" onclick="showOpen_ajax(document.getElementById('container'),'搜索引擎添加','search.htm?action=addpro&obj=null',400,270);">添 加</li>
			<!-- li class="main_top_title_r_d">删 除</li-->
		</ul>
	</div>
	<!-- table class="main_select" cellpadding="0" cellspacing="0" align=center>
		<tr align=left>
			<td><span>栏目名称：</span><input type="text" class="text"/>
			<span>所属栏目：</span><select style="width:70px;"><option>--请选择--</option></select>
			<span>所属权限：</span><select style="width:70px;"><option>全部</option><option>会员</option></select>
			<span>上传图片：</span><input onchange="viewpic();" class="file" id="read_pic"  type="file" name="picproduct" value="'+url+'"></td>
		</tr>
		<tr align=left>
			<td><span>备 注：</span><textarea  id='fb_content'  name='fb_content'  Cols='90%' rows='5' maxlength=200></textarea>
			<input type="button" value="添 加"></td>
		</tr>
	</table -->
	<table class="main_con" cellpadding="0" cellspacing="0"  id="productType">
		<tr class="main_con_title">	
			<td>名 称</td>
			<td>图 片</td>
			<td>地 址</td>
			<td>搜索类型</td>
			<td class="main_con_operation">基本操作</td>
		</tr>
	</table>
</div>
</body>
<script>
getprotype();	
function getprotype(id){
	var topproId = (id==undefined)?0:id;
	var pars="flag=getprotype&topproId="+topproId;
	var url = "search.htm?action=protypeSearch";
    	var reqAjax = new Ajax.Request(    
        	url,    
        	{    
            	method: 'post', 
            	parameters: pars,
				onCreate: loadingFun,
           	    onComplete: getprotypeResponse    
        	}    
    );
}
function loadingFun(){}
var tmp_select;
function getprotypeResponse(response){
	var text = response.responseText;
	var obj = eval("("+text+")");
	var info = obj["rs"];
	var len = info.length;
	var protype_obj = document.getElementById("productType");
	for(var i=protype_obj.rows.length;i>1;i--){
		protype_obj.deleteRow();
		
	}
   if(len>0){
   	for(var i=0;i<len;i++){
   		var row = protype_obj.insertRow();
	   	row.id = "maindetail"+i;
	   	row.onmouseover="changecolor(this);";
	   	row.onmouseout="changecolor(this);";
	   	//var cell1 = row.insertCell();
	   	//cell1.innerHTML ='<img src="images/index/detail_have.jpg" onclick="showdetail(this,document.getElementById(\'item1\'));"/>'; 
	   	var cell3 =row.insertCell();
	   	cell3.innerText = info[i][1];
	   	var cell4 =row.insertCell();
	   	//modify by Tim 20110915 src="'+info[i][2]+'"
	   	cell4.innerHTML = '<img src="/ssczb/'+info[i][2]+'" style="height:30px;width:40px;"/>';
	   	var cell5 =row.insertCell();
	   	if(info[i][3]==0){
	   		cell5.innerText="内部搜索无地址";
	   	}else{
	   		cell5.innerText=info[i][3];
	   	}
	   	var cell6 =row.insertCell();
	   	if(info[i][4]==0){
	   		cell6.innerText="外部搜索";
	   	}else{
	   		cell6.innerText="内部搜索";
	   	}
	   	var cell7 =row.insertCell();
	   	cell7.innerHTML ='<span onclick="showOpen_ajax(container,\'搜索引擎修改\',\'search.htm?action=addpro&obj='+encodeURIComponent(info[i])+'\',400,270);">修 改</span><span onclick="delconfirm(\''+info[i][0]+'\');">删 除</span>';   
   	}
   }else{
	   var row = protype_obj.insertRow();
	   var cell1 = row.insertCell();
	   cell1.innerHTML = '<!--span style="margin-right:20px;cursor:pointer;" onclick="getprotype();">返 回</span-->';
   }
}
/////////////////////////////////////读取搜索///////////////////////////////////
/////////////////////////////////////增加搜索///////////////////////////////////
function saveproType(){
	var name = document.getElementById('proname').value.replace(/(^\s*)|(\s*$)/g,'');
	var topId = document.getElementById('arrive').value.replace(/(^\s*)|(\s*$)/g,'');
	if(name == ""){
		alert("请输入搜索引擎名称");
		return;
	}
	var pars ="flag=saveproType&name="+name+"&topId="+topId;
	    	var url = "test.htm";
	    	var reqAjax = new Ajax.Request(    
	        	url,    
	        	{    
	            	method: 'post', 
	            	parameters: pars,
					onCreate: loadingFun,
	           	    onComplete: saveMsgResponse    
	        	}    
	    );
}
function saveMsgResponse(response){
	var text = response.responseText;
	alert(text);
	var obj = eval("("+text+")");
	if(obj["flag"]=='1'){
		alert("搜索引擎添加成功！");
		getprotype();
		document.getElementById('proname').value="";
	}else{
		alert("系统忙，请稍后再试！");
	}
}
/////////////////////////////////////增加搜索///////////////////////////////////
/////////////////////////////////////修改搜索///////////////////////////////////
function changepro(i,id){
	var proname = document.getElementById('proTypeName'+i).value.replace(/(^\s*)|(\s*$)/g,'');
	var topId = document.getElementById('proTypeId'+i).innerText;
	var topproId = (id==undefined)?document.getElementById('topName'+i).value:0;
	if(proname == ""){
		alert("请输入搜索引擎名称");
		return;
	}
	var pars ="flag=changeproType&proname="+proname+"&topId="+topId+"&topproId="+topproId;
	    	var url = "test.htm";
	    	var reqAjax = new Ajax.Request(    
	        	url,    
	        	{    
	            	method: 'post', 
	            	parameters: pars,
					onCreate: loadingFun,
	           	    onComplete: changeproResponse    
	        	}    
	    );
}
function changeproResponse(response){
	var text = response.responseText;
	var obj = eval("("+text+")");
	if(obj["flag"]=='1'){
		alert("修改成功！");
		getprotype();
	}else{
		alert("系统忙，请稍后再试！");
	}
}
/////////////////////////////////////修改搜索///////////////////////////////////

/////////////////////////////////////删除搜索///////////////////////////////////
function delconfirm(info){
	var a = confirm("确定删除此搜索引擎吗？");
	var proid = info;
	if(a){
		deleteproType(proid);
	}else{
		return;
	}
}

function deleteproType(info){
	var proid = info;
	var pars ="flag=deleteproType&id="+proid;
	    	var url = "search.htm?action=deleteproType";
	    	var reqAjax = new Ajax.Request(    
	        	url,    
	        	{    
	            	method: 'post', 
	            	parameters: pars,
					onCreate: loadingFun,
	           	    onComplete: deleteproTypeResponse    
	        	}    
	    ); 
}
function deleteproTypeResponse(response){
	var text = response.responseText;
	var obj = eval("("+text+")");
	if(obj["flag"]=='1'){
		alert("删除成功!");
		getprotype();
	}
}
/////////////////////////////////////删除搜索///////////////////////////////////
function restore(){
var obj = document.getElementById("container");
getprotype();
closebutton(obj);

}
</script>
</html>