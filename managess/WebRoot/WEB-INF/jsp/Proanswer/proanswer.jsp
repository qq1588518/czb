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
<script src='${ctx}/js/pagescrolling.js'></script>
<link rel=stylesheet type=text/css href="css/right.css"/>
<style>
.main_con td span{margin-left:10px;cursor:pointer;}
.main_con_operation{width:170px !important;}
</style>
<title>事业百问信息管理</title>
</head>
<body>
<div class="nav" id="container">
	<div class="main_top">
		<ul class="main_top_title">
			<li>事业百问信息管理</li>
		</ul>
		<ul class="main_top_title_r">
			<!-- li class="main_top_title_r_c" onclick="selectAll();">全 选</li -->
			<li class="main_top_title_r_a" onclick="showOpen_ajax(document.getElementById('container'),'添 加','proanswer.htm?action=addpro&obj=&pro=',610,480);">添 加</li>
			<!-- li class="main_top_title_r_d">删 除</li-->
		</ul>
	</div>
	<table class="main_select" cellpadding="0" cellspacing="0" align=center>
		<tr align=left>
			<td><span>关键字：</span><input type="text" class="text" id="title"><img src="${ctx}/images/index/select.gif" onclick="getprotype();" style="margin-left:10px;"/></td>
		</tr>
	</table>
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
			<td>序列号</td>
			<td>标 题</td>
			<td>所属类别</td>
			<td>发布日期</td>
			<td class="main_con_operation">基本操作</td>
		</tr>
	</table>
	<div id="count" style="text-align:right;margin-top:10px;"></div>
</div>
</body>
<script>
var item = 15;//每页显示的条数，分页
var res;
var pageno = 1;
var index = 0;
getprotype();
function getprotype(page,_index,_len){
	var title = document.getElementById("title").value;
	pageno = (page==undefined)?1:page;
	index = (_index==undefined)?0:_index;
	item = (_len==undefined)?item:_len;
	//alert(index+","+length+",title:"+title);
	var pars="flag=getprotype&title="+title+"&index="+index+"&length="+item;
	var url = "proanswer.htm?action=protypeSearch";
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
	var countpage = document.getElementById("count");
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
	   	cell3.innerText = info[i][0];
	   	var cell4 =row.insertCell();
	   	cell4.innerText = info[i][1];;
	   	var cell5 =row.insertCell();
	   	cell5.innerText = info[i][7];;
	   	var cell6 =row.insertCell();
	   	cell6.innerText = info[i][4];
	   //	var cell6 =row.insertCell();
   		//cell6.innerText=info[i][4];
	   	var cell7 =row.insertCell();
	   	//alert(info[i][2].length);
		//cell7.innerHTML ='<!--<span onclick="showOpen_ajax(container,\'修改栏目内容\',\'colcon.htm?action=addpro&obj='+info[i][0]+'&pro='+info[i][7]+'\',610,480);">修 改</span--><span onclick="delconfirm(\''+info[i][0]+'\');">删 除</span>';
   		cell7.innerHTML ='<span onclick="showOpen_ajax(container,\'修 改\',\'proanswer.htm?action=addpro&obj='+info[i][0]+'&pro='+info[i][7]+'\',610,480);">修 改</span><span onclick="delconfirm(\''+info[i][0]+'\');">删 除</span>';
   	
   	}
   	
   	var count = info[0][6];
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
	   var row = protype_obj.insertRow();
	   var cell1 = row.insertCell();
	   cell1.innerHTML = '<!--span style="margin-right:20px;cursor:pointer;" onclick="document.getElementById(\'title\').value=\'\';getprotype();">返 回</span-->';
	   document.getElementById('count').style.display="none";
   }
}


function movePageTo(page,startnum,len){  //分页
	//var title = document.getElementById("title").value;
	getprotype(page,startnum,len);
}
/////////////////////////////////////读取信息列表///////////////////////////////////

/////////////////////////////////////删除选中列表///////////////////////////////////
function delconfirm(info){
	var a = confirm("确定删除本条信息吗？");
	var proid = info;
	if(a){
		deleteproType(proid);
	}else{
		return;
	}
}

function deleteproType(info){
	var pars ="flag=deleteproType&id="+info;
	    	var url = "proanswer.htm?action=deleteproType";
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
		var protype_obj = document.getElementById("productType");
		if(pageno !=1){
			if(protype_obj.rows.length > 2){
				getprotype(pageno,index,item);
			}else{
				pageno = pageno-1;
				index = index-item;
				getprotype(pageno,index,item);
			}
		}else{
			getprotype(pageno,index,item);
		}
	}
}
/////////////////////////////////////删除选中列表///////////////////////////////////
function restore(){
var obj = document.getElementById("container");
getprotype(pageno,index,item);
closebutton(obj);

}
</script>
</html>