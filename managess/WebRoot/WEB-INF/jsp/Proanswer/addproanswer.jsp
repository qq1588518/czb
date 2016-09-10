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
<script src='js/json.js'></script>
<script src='js/Power.js'></script>
<title>添加修改信息</title>
<%
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0); 
String index = (String)request.getAttribute("index");
//System.out.println("hyh========="+index);
 %>
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
<form id="theForm" name='theForm' method='post' action="proanswer.htm?action=saveProtype" encType="multipart/form-data">
<div class="pwnav">
	<ul class="pwcon">
		<li >
			<span>内容标题：</span><input type="text" class="text" id="namep" maxlength="30" name="name_product" value="${title}"/>
		</li>
		<li>
			<span>外部地址：</span><input type="text" id="outurl" maxlength="300" name="outurl" value="${outurl}" style="width:300px;"/><span style="color:red;margin-left:10px;">(输入的地址必须为http://开头)</span>
		</li>
		<li>
			<span>所属类别：</span>
			<select  id="typeno" name="typeno" size="1" style="width:120px;height:20px;" class="Tpcform-dorpdownlist">
			<option value="0">--请选择--</option>
			<c:forEach var="clime" items="${types}">
				<option value="${clime.typeno }" <c:if test="${typeno==clime.typeno }">selected="selected"</c:if>>${clime.typename}</option>
			</c:forEach>
			</select>
		</li>
		<li id="tjym"><div style="float:left;padding-top:5px;"><span onclick="add();" style='cursor:hand;margin-right:10px;' id='add_span'><img  src='${ctx}/images/editor/add.jpg' style="cursor:pointer;" /></span><span onclick="del();" style='cursor:hand;margin-right:10px;'><img src='${ctx}/images/editor/del.jpg' style="cursor:pointer;" /></span><!--<span onclick="test();" style='cursor:hand;margin-right:10px;'><img src='${ctx}/images/editor/commit.jpg' style="cursor:pointer;" /></span>-->&nbsp;&nbsp;<span id="curpage" style='color:blue;display:none'>当前第1页</span></div><div style="float:right;margin-right:1px;"><input type="button" value="提 交" onclick="saveProduct()"/></div></li>
		<li id="content">
			<input type="hidden" value="${id}" name="cata_id" />
			<input type="hidden" value="${dates}" name="dates" />
			<iframe ID='editor' name='myframe' src='editor/edit.jsp?id=ContentNormal&style=s_coolblue' frameborder='0' scrolling='no' width='580' height='280'></iframe>
			<input type="hidden" value='' name='txtContent' id='txtContent'/>
			<input type="hidden" value='' name='total' id='total'/>
			<input type="hidden" value='' name='editmode' id='editmode'/>
			
			<div id="modify_content">
			<c:forEach var="all" items="${con}" varStatus="status">
				<textarea id="mycon" name="mycon${status.count}" style="display:none" value="567">${all}</textarea></c:forEach>
			</div>
		</li>
		<li id="viewli" style="display:none"></li>
		<li id="myli" style="display:none"></li>
		<li style="display:none"></li>
	</ul>
</div>
</form>
</BODY>
<script>
var index = 1;
var view_index = 1;
//alert('content=='+document.getElementById("modify_content").innerText);
test22();
function test22(){
    if(document.getElementById("modify_content").getElementsByTagName("textarea").length>0){
        document.getElementById("editmode").value="1";
    	var obj = document.getElementById("modify_content").getElementsByTagName("textarea");
        var len = obj.length;
   		//var tmp_str = document.getElementById("mycon").innerText;
		//editor.setHTML(document.getElementById("mycon").innerText);
		for(var i=0;i<len;i++){
			document.getElementById("tjym").firstChild.insertAdjacentHTML('BeforeEnd',"<span id='num"+view_index+"' style='margin-right:8px;cursor:pointer'  onclick='changecontent("+view_index+");'>"+view_index+"</span>");
			var html = '<input type=hidden value="" id=vewContent'+view_index+' />';	  
			document.getElementById("viewli").insertAdjacentHTML('BeforeEnd', html); 
			tmp_str = obj[i].innerHTML.replace(/&lt;/gi,"<");
			tmp_str = tmp_str.replace(/&gt;/gi,">");
			document.getElementById("vewContent"+view_index).value = tmp_str;
			view_index = view_index+1;
			if(i==0) {
					var limit = false;
					var time = setInterval(function(data,obj){
						var flag_editor = window.frames["editor"].document.getElementById('edit');
						if(flag_editor!=null){
							var value = flag_editor.readyState;
							
								if(value!='complete'){
									limit = false;
								}else{
									limit = true;
									var _time = setInterval(function(){
										if(document.getElementById("editor").readyState=="complete"){
											obj.setHTML(data,true);
											changecontent(1);
											clearInterval(_time);
										}else{
										
											
										}
									},100);
									
									clearInterval(time);
								}
								
						}
					}.createDelegate(window.frames["editor"],[tmp_str,window.frames["editor"]]),100);
					
			}
		}
		
				
	}
}
function del(){
	var edited = false;
	for(var i=1;i<5;i++){
	    if(document.getElementById("num"+i)!=undefined){
	    	if(document.getElementById("num"+i).style.color=="red"){
	    		document.getElementById("vewContent"+i).parentNode.removeChild(document.getElementById("vewContent"+i));
	    		document.getElementById("num"+i).parentNode.removeChild(document.getElementById("num"+i));
	    		edited = true;
			}else{
			
			}
		}
	}
	if(!edited){
	
		alert("请选择一个页面");
		return;
	}
	var view_len = document.getElementById("viewli").childNodes.length;
	var num_len = document.getElementById("tjym").firstChild.childNodes.length;
	for(var y=0;y<view_len;y++){
	
		document.getElementById("viewli").childNodes[y].id="vewContent"+(y+1);
	}
	for(var k=5,j=1;k<num_len;k++,j++){
		document.getElementById("tjym").firstChild.childNodes[k].id="num"+(k-4);
		document.getElementById("tjym").firstChild.childNodes[k].innerText=(k-4);
		//document.getElementById("tjym").childNodes[k].onclick="function(){changecontent("+(k-5)+")}()";
		document.getElementById("tjym").firstChild.childNodes[k].onclick=function(){var _value= arguments[0]; return function(){changecontent.apply(window,[_value])}}(j);
		//if(k==6) document.getElementById("tjym").childNodes[k].style.color="red";
	}
	view_index--;
	editor.setHTML("",true);
}
function changecontent(no){
	/*if(editor.getHTML!=""){
	var html = '<input type=hidden value="" name=txtContent'+index+' id=txtContent'+index+' />';
		   document.getElementById("myli").insertAdjacentHTML('BeforeEnd', html);
		document.getElementById("txtContent"+index).value = editor.getHTML();
	}*/
	//alert('no=='+no+' --'+document.getElementById("vewContent"+no).value);
	updateCon();
	editor.setHTML(document.getElementById("vewContent"+no).value,true);
	for(var i=1;i<5;i++){
	    if(document.getElementById("num"+i)!=undefined){
	    	if(i==no){
				document.getElementById("num"+i).style.color="red";
			}else{
			
				document.getElementById("num"+i).style.color="black";
			}
		
		}
	}
}
function add(){
	
	//alert(editor.document.body.contentEditable);
	//editor.document.body.contentEditable = "true";
	//editor.setMode('EDIT');
	if(view_index<5){
		var value = editor.getHTML().replace(/(^\s*)|(\s*$)/g,'');
		var isEdit = false;
		for(var i=1;i<5;i++){
		    if(document.getElementById("num"+i)!=undefined){
		    	if(document.getElementById("num"+i).style.color=="red"){
		    		isEdit = true;
		    		break;
				}else{
					//alert("请选择一个页面");
				}
			}
		}
		//alert(isEdit);
		if(value.search(/[^&nbsp;*]/gi)!=-1 && !isEdit){
			document.getElementById("tjym").firstChild.insertAdjacentHTML('BeforeEnd',"<span id='num"+view_index+"' style='margin-right:8px;cursor:pointer'  onclick='changecontent("+view_index+");'>"+view_index+"</span>");
			
			var html = '<input type=hidden value="" id=vewContent'+view_index+' />';	  
			document.getElementById("viewli").insertAdjacentHTML('BeforeEnd', html); 
			//alert('value=='+value);
			document.getElementById("vewContent"+view_index).value = value;
			view_index = view_index+1;
			editor.setHTML("",true);
		}else if(value.search(/[^&nbsp;*]/gi)!=-1  && isEdit){
			
			document.getElementById("tjym").firstChild.insertAdjacentHTML('BeforeEnd',"<span id='num"+view_index+"' style='margin-right:8px;cursor:pointer'  onclick='changecontent("+view_index+");'>"+view_index+"</span>");
			
			var html = '<input type=hidden value="" id=vewContent'+view_index+' />';	  
			document.getElementById("viewli").insertAdjacentHTML('BeforeEnd', html); 
			//alert('value=='+value);
			document.getElementById("vewContent"+view_index).value = "";
			changecontent(view_index);
			view_index = view_index+1;
			
			/*var old_value = editor.getHTML();
			for(var i=1;i<5;i++){
			    if(document.getElementById("num"+i)!=undefined){
			    	if(document.getElementById("num"+i).style.color=="red"){
			    		document.getElementById("vewContent"+i).value=old_value;
					}else{
					
						//document.getElementById("num"+i).style.color="black";
					}
				
				}
			}*/
			editor.setHTML("",true);
		}else{
		
			editor.setHTML("",true);
		}
		
		
	}else{
	   
	   	   alert("最多分页数4页");
	   	   //editor.setHTML("",true);
	   	   return;
	   }
	
}
function modify(){




}
function updateCon(){

	var old_value = editor.getHTML();
	for(var i=1;i<5;i++){
	    if(document.getElementById("num"+i)!=undefined){
	    	if(document.getElementById("num"+i).style.color=="red"){
	    		document.getElementById("vewContent"+i).value=old_value;
			}else{
			
			}
		
		}
	}

}
function isEmpty(){
	var empty = false;
	var inp_obj = document.getElementById("viewli").getElementsByTagName("INPUT");
	var len = inp_obj.length;
	for(var j=0;j<len;j++){
		if(document.getElementById("num"+(j+1)).style.color=="red"){
			var old_value = editor.getHTML();
			old_value = old_value.replace(/\r|\n|\t/g,'');
			var tmp_str = old_value.replace(/(^\s*)|(\s*$)/g,'');
			var isEmpty = old_value.search(/[^&nbsp;*]/gi)!=-1?false:true;
			var _isEmpty = old_value.search(/[^<P>&nbsp;*</P>]/gi)!=-1?false:true;
			if(tmp_str=="" || isEmpty || _isEmpty){
				alert("第"+(j+1)+"页不允许为空");
				empty = true;
				return empty;
			}else{
			
			}
		}else{
		if(inp_obj[j].type=="hidden"){
			var tmp_str = inp_obj[j].value.replace(/(^\s*)|(\s*$)/g,'');
			inp_obj[j].value = inp_obj[j].value.replace(/\r|\n|\t/g,'');
			var isEmpty = inp_obj[j].value.search(/[^&nbsp;*]/gi)!=-1?false:true;
			var _isEmpty = inp_obj[j].value.search(/[^<P>&nbsp;*</P>]/gi)!=-1?false:true;
			//if(isEmpty) alert("hhhh");
			//if(_isEmpty) alert("jjj");
			if(tmp_str=="" || isEmpty || _isEmpty){
			
				alert("第"+(j+1)+"页不允许为空");
				empty = true;
				return empty;
			}else{
			
				
			}
			
		}
		}
	}
	return empty;

}
function test(){
   if(!isEmpty()){
   
   }else{
   	  return;
   }
   
   for(var i=1;i<5;i++){
   		var old_value = editor.getHTML();
	    if(document.getElementById("num"+i)!=undefined){
	    	if(document.getElementById("num"+i).style.color=="red"){
	    		document.getElementById("vewContent"+i).value=old_value;
	    		alert('提交成功');
	    		
			}else{
			
			}
		
		}
	}
}



//读取子栏目列表
function search_sub_pro(obj){
	var id = obj.value;
	var pars = "id="+id;
   	var url = "colcon.htm?action=listPro";
   	var reqAjax = new Ajax.Request(    
       	url,    
       	{    
           	method: 'post', 
           	parameters: pars,
			onCreate: loadingFun,
   		    onComplete: search_sub_proResponse    
       	}    
   );
}
function loadingFun(){}
function search_sub_proResponse(response){
	var text = response.responseText;
	var obj = eval("("+text+")");
	var info = obj["rs"];
	var len = info.length;
	var eprovinceobj = document.getElementById("sub_protype");
	eprovinceobj.innerHTML = "";
	var eopt = document.createElement("option");
	eopt.value= 0;
	eopt.innerText= '--请选择--';
	eprovinceobj.appendChild(eopt);
   if(len>0){
   	for(var i=0;i<len;i++){
   		var eopt = document.createElement("option");
		eopt.value= info[i][0];
		eopt.innerText= info[i][1];
		eprovinceobj.appendChild(eopt);
   	}
   }else{
   }
}


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
	 		alert("请上传栏目内容标题图片");
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
	var type = document.getElementById("typeno").value;
	var con = editor.getHTML();
	//var txtContent = document.getElementById("txtContent").value;
	var obj = new Object();
	var array = [];
	var total = [];
	obj["content"] = array;
	
	var param_str = "";
	if(column_name==""){
		alert("请输入标题");
		return;
	}
	if(type=="0"){
		alert("请选择所属类别");
		return;
	}
	if(isEmpty()){
	
		return;
	}
	var inp_obj = document.getElementById("viewli").getElementsByTagName("INPUT");
	var len = inp_obj.length;
	if(len<1){
		alert("请输入正文内容");
		return;
	}else{
		if(len>0){
			updateCon();
			for(var j=0;j<len;j++){
				if(inp_obj[j].type=="hidden"){
					array[j]=inp_obj[j].value;
					total[j]= "<div id='total"+j+"'style=\"display:none\">"+array[j]+"</div>";
				}
			}
		}
		obj["all"] = total.join("");
		param_str = obj.toJSONString().replace(/(^\s*)|(\s*$)|/,'');
		document.getElementById("myli").innerHTML = "";
		document.getElementById("txtContent").value = param_str;
	}
	document.getElementById("theForm").submit();
	
}



/////////////////////////////////////控制textarea输入框的输入长度///////////////////////////////////
/*var element
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
}*/

/////////////////////////////////////控制textarea输入框的输入长度///////////////////////////////////
</script>
</HTML>
