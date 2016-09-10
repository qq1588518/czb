/************************表单提交蒙板*********************************/

/*
**生成蒙板
*/
function showOpen(obj,title,url,w,h){
	var objHeight = document.documentElement.clientHeight;
	var objWidth = document.documentElement.clientWidth;
	var width = w+10;
	var w1 = w;
	var height = h+38;
	var h1 = h;
	
	if(width < 200){
		width = 200;
	}
	if(width > objWidth){
		width = objWidth;
		w1 = objWidth-20;
	}
	if(height > objHeight){
		h1 = objHeight-38;
		height = objHeight;
	}
	var html = "<div style='z-index: 20000;position: absolute;top:0;left:0px;width:100%;height:100%;background-color:#ccc;-moz-opacity: 0.5;opacity:.50;filter: alpha(opacity=50);'></div>";
	var arr = [];
	arr.push('<div>');
	arr.push('	<ul style="background:url(images/index/passwordbg.jpg) repeat-x;line-height:23px;height:23px;">');
	arr.push('	   <li style="float:left;margin-left:3px;padding-left:20px;color:#fff;font-weight:bold;background:url(images/index/tit.bmp) no-repeat 0 6px;">'+title+'</li>');
	arr.push('	   <li style="float:right;margin-right:5px;"><img style="cursor:pointer;" alt="关闭" onclick="closebutton(document.getElementById(\''+obj.id+'\'));" src="images/index/password1.jpg" /></li>');
	arr.push('	</ul>');
	arr.push('	<ul  style="border:solid 5px #65a6ce;border-top:0px;">');
	arr.push('		<li><iframe src="'+url+'" frameborder="0"  scrolling="yes" style="width:'+w1+'px;height:'+h1+'px;"></iframe></li>');
	arr.push('	    <li><input type="button" onclick="window.frames[0].document.forms[0].submit();" value="提 交"/><input type="button" value="取 消" onclick="window.frames[0].document.forms[0].reset();"/></li>');
	arr.push('	</ul>');
	arr.push('</div>');
	var div_content = arr.join('');
	var msghtml = "<div style='z-index: 20001;position: absolute;background-color:#ffffff;cursor:move;left:0px;width:"+width+"px;' onmousedown='drag_down(this)' onmousemove='drag_move(this)'>"+div_content+"</div>";
	if("MSIE" == getOs()){
		obj.insertAdjacentHTML('BeforeEnd', html);
	}else{
		var range = obj.ownerDocument.createRange();
		var frag;
		if(obj.lastChild){		
			range.setStartAfter(obj.lastChild);  
			frag = range.createContextualFragment(html);  
			obj.appendChild(frag);			
		}else{  	
			obj.innerHTML = html;   
		}
	}
	if("MSIE" == getOs()){
		obj.insertAdjacentHTML('BeforeEnd',msghtml);
	}else{
		var range = obj.ownerDocument.createRange();
		var frag;
		if(obj.lastChild){
			range.setStartAfter(obj.lastChild);  
			frag = range.createContextualFragment(msghtml);  
			obj.appendChild(frag);  
		}else{  
			obj.innerHTML = msghtml;  
		}
	}
	var msgcon = obj.lastChild;
	msgcon.style.left=(objWidth-width)/2+'px';
	msgcon.style.top=(objHeight-height)/2+'px';
}



/************************异步交互蒙板*********************************/

/*
**生成蒙板
*/
function showOpen_ajax(obj,title,url,w,h){
	var objHeight = document.documentElement.clientHeight;
	var objWidth = document.documentElement.clientWidth;
	var width = w+10;
	var w1 = w;
	var height = h+38;
	var h1 = h;
	
	if(width < 200){
		width = 200;
	}
	if(width > objWidth){
		width = objWidth;
		w1 = objWidth-20;
	}
	if(height > objHeight){
		h1 = objHeight-38;
		height = objHeight;
	}
	var html = "<div style='z-index: 20000;position: absolute;top:0;left:0px;width:100%;height:100%;background-color:#ccc;-moz-opacity: 0.5;opacity:.50;filter: alpha(opacity=50);'></div>";
	var arr = [];
	arr.push('<div>');
	arr.push('	<ul style="background:url(images/index/passwordbg.jpg) repeat-x;line-height:23px;height:23px;">');
	arr.push('	   <li style="float:left;margin-left:3px;padding-left:20px;color:#fff;font-weight:bold;background:url(images/index/tit.bmp) no-repeat 0 6px;">'+title+'</li>');
	arr.push('	   <li style="float:right;margin-right:5px;"><img style="cursor:pointer;" alt="关闭" onclick="closebutton(document.getElementById(\''+obj.id+'\'));" src="images/index/password1.jpg" /></li>');
	arr.push('	</ul>');
	arr.push('	<ul  style="border:solid 5px #65a6ce;border-top:0px;">');
	arr.push('		<li><iframe src="'+url+'" frameborder="0"  scrolling="yes" style="width:'+w1+'px;height:'+h1+'px;"></iframe></li>');
	//arr.push('	    <li><input type="button" onclick="window.frames[0].document.forms[0].submit();" value="提 交"/><input type="button" value="取 消" onclick="window.frames[0].document.forms[0].reset();"/></li>');
	arr.push('	</ul>');
	arr.push('</div>');
	var div_content = arr.join('');
	var msghtml = "<div style='z-index: 20001;position: absolute;background-color:#ffffff;cursor:move;left:0px;width:"+width+"px;' onmousedown='drag_down(this)' onmousemove='drag_move(this)'>"+div_content+"</div>";
	if("MSIE" == getOs()){
		obj.insertAdjacentHTML('BeforeEnd', html);
	}else{
		var range = obj.ownerDocument.createRange();
		var frag;
		if(obj.lastChild){		
			range.setStartAfter(obj.lastChild);  
			frag = range.createContextualFragment(html);  
			obj.appendChild(frag);			
		}else{  	
			obj.innerHTML = html;   
		}
	}
	if("MSIE" == getOs()){
		obj.insertAdjacentHTML('BeforeEnd',msghtml);
	}else{
		var range = obj.ownerDocument.createRange();
		var frag;
		if(obj.lastChild){
			range.setStartAfter(obj.lastChild);  
			frag = range.createContextualFragment(msghtml);  
			obj.appendChild(frag);  
		}else{  
			obj.innerHTML = msghtml;  
		}
	}
	var msgcon = obj.lastChild;
	msgcon.style.left=(objWidth-width)/2+'px';
	msgcon.style.top=(objHeight-height)/2+'px';
}










/*
**关闭蒙板
*/
function closebutton(obj){
	obj.removeChild(obj.lastChild);
	obj.removeChild(obj.lastChild);
}


/*
**拖动蒙板
*/
var moveobj=null,divtop,divleft;
function drag_down(obj){
	moveobj = obj;
	divleft =  event.x-obj.style.pixelLeft;
	divtop = 	event.y-obj.style.pixelTop;
	moveobj.setCapture();
}
	  
function drag_move(obj){
 if(moveobj!=null){
	obj.style.left =event.x-divleft+'px';
	obj.style.top =event.y-divtop+'px';
 }
}
window.document.attachEvent("onmouseup",function(){if(moveobj!=null){moveobj.releaseCapture();moveobj=null;}});

/*
**得到浏览器的类型
*/
function getOs() {
	var OsObject = ""; 
	if(navigator.userAgent.indexOf("MSIE")>0) { 
		return "MSIE"; 
	} 
	if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){ 
		return "Firefox"; 
	} 
	if(isSafari=navigator.userAgent.indexOf("Safari")>0) { 
		return "Safari"; 
	}  
	if(isCamino=navigator.userAgent.indexOf("Camino")>0){ 
		return "Camino"; 
	} 
	if(isMozilla=navigator.userAgent.indexOf("Gecko/")>0){ 
		return "Gecko"; 
	} 
} 