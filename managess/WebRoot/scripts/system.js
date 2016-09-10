/************************登录*********************************/
/*
**登录账号重置
*/
function reset(){
	document.getElementById("username").value="";
	document.getElementById("password").value="";
	document.getElementById("check").value="";
	document.getElementById("username").focus();
}

/*
**页面跳转到到指定大小状态的窗口
*/
function winopen(){
		var availWidth=screen.availWidth-4;
        var availHeight=screen.availHeight-55;
        window.open("index2.html","","height="+availHeight+",width="+availWidth+",left=0,top=0,status=yes,toolbar=no,menubar=no,location=no");
        window.opener=null;
        window.close();
}

/************************主界面左侧导航栏*********************************/

/*
**每个菜单头部的高度
*/
var headHeight = 20;
/*
**栏目一每个菜单的高度
*/
var bodyHeight1;
/*
**栏目二每个菜单的高度
*/
var bodyHeight2;
/*
**栏目一的菜单个数
*/
var objcount1;
/*
**栏目二的菜单个数
*/
var objcount2;
/*
**上下滚动的速度
*/
var step = 20;
var moving = false;

/*
**系统打开时自动加载，用来控制导航页面的高度以及给变量赋值
*/
function leftNav(){
	var h1 = document.body.clientHeight;
	var h = h1-70;
	document.getElementById("mainb").style.height= h1;	
	document.getElementById("mainboard1").style.height= h;
	document.getElementById("mainboard2").style.height= h;
	
	var len = document.getElementById("mainboard1").getElementsByTagName("table").length;
	var len2 = document.getElementById("mainboard2").getElementsByTagName("table").length;

	objcount1 = len;
	objcount2 = len2;
	var item_h = h-len*20;//获取DIV的高度，，len为有菜单栏的个数，h为整个可以显示菜单的高度
	var item_h2 = h-len2*20;//获取DIV的高度，，len为有菜单栏的个数，h为整个可以显示菜单的高度
	document.getElementById("footer").style.top = h1-50;
	bodyHeight1 = item_h;
	bodyHeight2 = item_h2;
	
	for(var i=0;i<len;i++){
		var ite = i+1;
		document.getElementById("item"+ite+"body1").style.height = 400;
		document.getElementById("item"+ite+"body1").style.height = item_h;
		
		if(i==0){
			document.getElementById("item"+ite+"body1").style.top=0;
		}else{
			document.getElementById("item"+ite+"body1").style.top =item_h+i*20;
		}
	
	}
	for(var i=0;i<len2;i++){
		var ite = i+1;
		document.getElementById("item"+ite+"body2").style.height = item_h2;
		if(i==0){
			document.getElementById("item"+ite+"body2").style.top=0;
		}else{
			document.getElementById("item"+ite+"body2").style.top =item_h2+i*20;
		}
	}
}

/*
**对栏目一、栏目二切换时样式的改变以及左右滚动
*/
function change(val){
	if(val==1){
		var mainboard2 = document.getElementById("mainboard2");
		var mainboard1 = document.getElementById("mainboard1");
		document.getElementById("mainboard1").style.display="block";
		document.getElementById("mainboard2").style.display="none";
		document.getElementById("changePic1").className="change1";
		document.getElementById("changePic2").className="change2";
		moveright(mainboard1,mainboard2);
	}
	else if(val == 2){
		var mainboard2 = document.getElementById("mainboard2");
		var mainboard1 = document.getElementById("mainboard1");
		document.getElementById("mainboard2").style.display="block";
		document.getElementById("mainboard1").style.display="none";
		document.getElementById("changePic1").className="change2";
		document.getElementById("changePic2").className="change1";
		moveleft(mainboard1,mainboard2);
	}
}

/*
**点击栏目二切换时向左滚动
*/
function moveleft(obj1,obj2)
{
	left1 = parseInt(obj1.currentStyle.left);
    left2 = parseInt(obj2.currentStyle.left);
    if (left2 > 0)
    {
		obj1.style.left = left1-10;
		
		obj2.style.left = left2-10;
		
        setTimeout('moveleft('+obj1.id+','+obj2.id+')',1)
        return;
    }
}

/*
**点击栏目二切换时向右滚动
*/
function moveright(obj1,obj2)
{
	right1 = parseInt(obj1.currentStyle.left);
    right2 = parseInt(obj2.currentStyle.left);
    if (right1 < 0)
    {
		obj1.style.left = right1+10;
		
		obj2.style.left = right2+10;
		
        setTimeout('moveright('+obj1.id+','+obj2.id+')',1)
        return;
    }
}


function showme(obj1, obj2)
{
//    if (moving)
//        return;
//    moving = true;
//    for(i=0;i<document.all.tags('td').length;i++)
//        if (document.all.tags('td')[i].className.indexOf('headtd') == 0)
//            document.all.tags('td')[i].className = 'headtd1';
//    obj2.className = 'headtd2';
    moveme(obj1);
}


function moveme(obj)
{
    idnumber = parseInt(obj.id.substr(4));//获取是点击了第几个菜单
	idnav = parseInt(obj.id.substr(9));//获取是栏目一还是栏目二的菜单，，
	var objcounts;
	var bodyHeights;
	if(idnav == 1){
		objcounts = objcount1;
		bodyHeights = bodyHeight1;
	}else{
		objcounts = objcount2; 
		bodyHeights = bodyHeight2;
	}
    objtop = headHeight * (idnumber - 1);
    objbuttom = bodyHeights + headHeight * (idnumber - 2);
    currenttop = parseInt(obj.style.top);
    if (currenttop >= objbuttom)
    {
        countid = 1;
        for(i=0;i<document.getElementsByTagName('div').length;i++){
				if (document.getElementsByTagName('div')[i].id == 'item'+countid+'body'+idnav)
				{
					obj = document.getElementsByTagName('div')[i];
					objtop = headHeight * (countid - 1);
					if (countid == idnumber)
					{
						moveup(obj,objtop,false);
						break;
					}
					else
						moveup(obj,objtop,true);
					countid++;
				}
		}
    }
    else if ((currenttop <= objtop) && (idnumber < objcounts))
    {
        idnumber++;
        countid = objcounts;
        for(i=document.getElementsByTagName('div').length-1;i>=0;i--){
				if (document.getElementsByTagName('div')[i].id == 'item'+countid+'body'+idnav)
				{
					obj = document.getElementsByTagName('div')[i];
					objbuttom = bodyHeights + headHeight * (countid - 1);
					if (countid == idnumber)
					{
						movedown(obj,objbuttom,false,objcounts);
						break;
					}
					else
						movedown(obj,objbuttom,true,objcounts);
					countid--;
				}
			}
    }
}

/*
**向上滚动
*/
function moveup(obj,objtop,ismove)
{
    currenttop = parseInt(obj.style.top);
	id = parseInt(obj.id.substr(4));//获取是点击了第几个菜单
	headH = step+20*(id-1)
    if (currenttop > objtop && currenttop > headH)
    {
        obj.style.top = currenttop - step;
        setTimeout('moveup('+obj.id+','+objtop+','+ismove+')',1)
        return;
    }else if (currenttop > objtop && currenttop <= headH){
		obj.style.top = 20*(id-1);
	}
    moving = ismove;
}

/*
**向下滚动
*/
function movedown(obj,objbuttom,ismove,objcounts)

{    currenttop = parseInt(obj.style.top);
	id = parseInt(obj.id.substr(4));//获取是点击了第几个菜单
	headH = step+20*(objcounts-id)
    if (currenttop < objbuttom-headH)
    {
        obj.style.top = currenttop + step;
        setTimeout('movedown('+obj.id+','+objbuttom+','+ismove+','+objcounts+')',1)
        return;
    }else if (currenttop >= objbuttom-headH){
		obj.style.top = objbuttom;
	}
    moving = ismove;
}



/************************主界面右侧导航栏*********************************/

/*
**显示隐藏列表的详细信息
*/
var a = 1;
function showdetail(pic,obj){
	if(a == 1){
		obj.style.display="";
		pic.src = "images/index/detail_no.jpg";
		a = 2;
	}else{
		pic.src = "images/index/detail_have.jpg";
		obj.style.display="none";
		a = 1;
	}
}

/*
**鼠标移进移出列表背景颜色
*/
var c = 1;
function changecolor(obj){
	if(c == 1){
		obj.style.backgroundColor="#d4e3ff";
		c = 2;
	}else{
		obj.style.backgroundColor="#fff";
		c = 1;
	}
}

/*
**全选
*/
var checkbox = 'flase';
function selectAll(){
	var obj = document.getElementsByName("checkbox");
	var len = obj.length;
	if(checkbox == 'flase'){
		for(i = 0 ;i < len ;i++){
			obj[i].checked =true;
			checkbox = 'true';
		}
	}else{
		for(i = 0 ;i < len ;i++){
			obj[i].checked = false;
			checkbox = 'flase';
		}
	}
}


/*
*生成时间
*/
function clockon(obj){
		var thisTime =new Date();
		years = thisTime.getYear();
		months = thisTime.getMonth()+1;
		dates = thisTime.getDate();
		hours = thisTime.getHours();
		minutes = thisTime.getMinutes();
		seconds = thisTime.getSeconds();
		if(months < 10){months = '0'+months}
		if(dates < 10){dates = '0'+dates}
		if(minutes < 10){minutes = '0'+minutes}
		if(seconds < 10){seconds = '0'+seconds}
		obj.innerHTML =years+"-"+months+"-"+dates+" "+hours+":"+minutes+":"+seconds
		var timer = setTimeout("clockon("+obj.id+")",1000)
	}