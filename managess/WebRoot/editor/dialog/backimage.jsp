<%@page contentType="text/html;charset=GBK"%>
<%@page import="basic.util.*"%>
<%@include file="/WEB-INF/jspf/pub_uri.jspf" %>

<HTML>
<%
String record_no=CUtil.createBillNo("YMDHm######");
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0); 
%>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<style type="text/css">
body, a, table, div, span, td, th, input, select{font:9pt;font-family: "宋体", Verdana, Arial, Helvetica, sans-serif;}
body {padding:5px}
</style>

<script language="JavaScript" src="dialog.js"></script>

<script language="JavaScript">
var sAction = "";
var sTitle = "";

var oControl;
var oSeletion;
var sRangeType;

var sImage = "";
var sRepeat = "";
var sAttachment = "";

var sCheckFlag = "sys";

if (URLParams['action'] == "other"){
	sAction = "OTHER"
	sTitle = "设置"
	sImage = dialogArguments.d_image.value;
	sRepeat = dialogArguments.d_repeat.value;
	sAttachment = dialogArguments.d_attachment.value;
	sCheckFlag = "url";
}else{
	
	sAction = "INSERT";
	sTitle = "网页";

	oSelection = dialogArguments.eWebEditor.document.selection.createRange();
	sRangeType = dialogArguments.eWebEditor.document.selection.type;

	if (sRangeType == "Control") {
		oControl = GetControl(oSelection, "TABLE");
	}else{
		oControl = GetImageParent(oSelection.parentElement());
	}
	if (oControl) {
		switch(oControl.tagName){
		case "TD":
			sTitle = "单元格";
			break;
		case "TR":
		case "TH":
			sTitle = "表格行";
			break;
		default:
			sTitle = "表格";
			break;
		}
		sAction = "MODI";
		sImage = oControl.style.backgroundImage;
		sRepeat = oControl.style.backgroundRepeat;
		sAttachment = oControl.style.backgroundAttachment;
		sCheckFlag = "url";
		sImage = sImage.substr(4, sImage.length-5)
	}
}
document.write("<title>背景图属性（" + sTitle + "）</title>");


// 初始值
function InitDocument(){
	SearchSelectValue(d_repeat, sRepeat.toLowerCase());
	SearchSelectValue(d_attachment, sAttachment.toLowerCase());
	d_fromurl.value = sImage;
}

// 返回有背景图属性的对象
function GetImageParent(obj){
	while(obj!=null && obj.tagName!="TD" && obj.tagName!="TR" && obj.tagName!="TH" && obj.tagName!="TABLE")
		obj=obj.parentElement;
	return obj;
}

// 返回标签名的选定控件
function GetControl(obj, sTag){
	obj=obj.item(0);
	if (obj.tagName==sTag){
		return obj;
	}
	return null;
}


// 图片来源单选点击事件
function RadioClick(what){
	switch(what){
	case "url":
		d_checkfromfile.checked=false;
		d_checkfromurl.checked=true;
		d_checkfromsys.checked=false;
		d_checkcancel.checked=false;
		d_file.myform.uploadfile.disabled=true;
		d_fromurl.disabled=false;
		d_fromsys.disabled=true;
		break;
	case "file":
		d_checkfromfile.checked=true;
		d_checkfromurl.checked=false;
		d_checkfromsys.checked=false;
		d_checkcancel.checked=false;
		d_file.myform.uploadfile.disabled=false;
		d_fromurl.disabled=true;
		d_fromsys.disabled=true;
		break;
	case "sys":
		d_checkfromfile.checked=false;
		d_checkfromurl.checked=false;
		d_checkfromsys.checked=true;
		d_checkcancel.checked=false;
		d_file.myform.uploadfile.disabled=true;
		d_fromurl.disabled=true;
		d_fromsys.disabled=false;
		break;
	case "cancel":
		d_checkfromfile.checked=false;
		d_checkfromurl.checked=false;
		d_checkfromsys.checked=false;
		d_checkcancel.checked=true;
		d_file.myform.uploadfile.disabled=true;
		d_fromurl.disabled=true;
		d_fromsys.disabled=true;
		break;
	}
}

// 上传帧调入完成时执行
function UploadLoaded(){
	// 初始radio
	RadioClick(sCheckFlag);
}

// 上传错误
function UploadError(sErrDesc){
	AbleItems();
	RadioClick('file');
	divProcessing.style.display="none";
	try {
		BaseAlert(d_file.myform.uploadfile,sErrDesc);
	}
	catch(e){}
}

// 文件上传完成时执行,带入上传文件名
function UploadSaved(sPathFileName){
	d_fromurl.value = sPathFileName;
	ReturnValue();
}

// 本窗口返回值
function ReturnValue(){
	if (d_fromurl.value==""){
		sImage = "";
		sRepeat = "";
		sAttachment = "";
	}else{
		if (sAction == "OTHER"){
			sImage = d_fromurl.value;
		}else{
			sImage = "url(" + d_fromurl.value + ")";
		}
		sRepeat = d_repeat.options[d_repeat.selectedIndex].value;
		sAttachment = d_attachment.options[d_attachment.selectedIndex].value;
	}

	switch(sAction){
	case "MODI":
		oControl.style.backgroundImage = sImage;
		oControl.style.backgroundRepeat = sRepeat;
		oControl.style.backgroundAttachment = sAttachment;
		break;
	case "OTHER":
		dialogArguments.d_image.value = sImage;
		dialogArguments.d_repeat.value = sRepeat;
		dialogArguments.d_attachment.value = sAttachment;
		break;
	default:
		dialogArguments.setHTML("<table border=0 cellpadding=0 cellspacing=0 width='100%' height='100%'><tr><td valign=top style='background-image:"+sImage+";background-repeat:"+sRepeat+";background-attachment:"+sAttachment+";'>"+dialogArguments.getHTML()+"</td></tr></table>");
		break;
	}

	window.returnValue = null;
	window.close();
}

var secs = 5;




// 点确定时执行
function ok(){
	// 图片来源判断
	if (d_checkfromurl.checked){
		ReturnValue();
	}
	changeSrc(d_file.myform.uploadfile);
	//return;
	/*if (d_checkfromfile.checked){
		// 上传文件判断
		if (!d_file.CheckUploadForm()) return false;
		// 使各输入框无效
		DisableItems();
		// 显示正在上传图片
		divProcessing.style.display="";
		// 上传表单提交
                d_file.myform.recordno.value="<%=record_no%>";
		d_file.myform.submit();
		for(i=1;i<=secs;i++) {
                    window.setTimeout("update(" + i + ")", secs*1000);
                }
	}
	if (d_checkfromsys.checked){
		d_fromurl.value = relativePath2setPath("sysimage/bg/" + d_fromsys.options[d_fromsys.selectedIndex].value);
		ReturnValue();
	}
	if (d_checkcancel.checked){
		d_fromurl.value="";
		ReturnValue();
	}*/
}

function update(num) {
    if(num == secs) {
        dialogArguments.setHTML("<table border=0 cellpadding=0 cellspacing=0 width='100%' height='100%'><tr><td valign=top style='background-image:url(<%=uri%>editor/showImg.jsp?recordno=<%=record_no%>); background-repeat:"+sRepeat+";background-attachment:"+sAttachment+";'>"+dialogArguments.getHTML()+"</td></tr></table>");
        //dialogArguments.eWebEditor_CODE.click();
        //dialogArguments.eWebEditor_EDIT.click();
    }
    if(d_file.myform.update_success_flag.value=="true" && num == secs){
        window.close();
    }
}

// 使所有输入框无效
function DisableItems(){
	d_checkfromfile.disabled=true;
	d_checkfromurl.disabled=true;
	d_checkfromsys.disabled=true;
	d_checkcancel.disabled=true;
	d_fromurl.disabled=true;
	d_fromsys.disabled=true;
	d_repeat.disabled=true;
	d_attachment.disabled=true;
	Ok.disabled=true;
}

// 使所有输入框有效
function AbleItems(){
	d_checkfromfile.disabled=false;
	d_checkfromurl.disabled=false;
	d_checkfromsys.disabled=false;
	d_checkcancel.disabled=false;
	d_fromurl.disabled=false;
	d_fromsys.disabled=false;
	d_repeat.disabled=false;
	d_attachment.disabled=false;
	Ok.disabled=false;
}

</script>
<script defer=true>
var oFileChecker = document.getElementById("fileChecker");

function changeSrc(filePicker)
{
    
    if(getExplorerVesion().ie){
		if(getExplorerVesion().ie=="7.0" || getExplorerVesion().ie=="8.0"  ){
		
			checkSize();
		}else if(getExplorerVesion().ie=="6.0"){
		
			oFileChecker.src = filePicker.value;
		}
    }else{
    
    	alert("不支持非IE浏览器!");
    }
}

oFileChecker.onreadystatechange = function ()
{
    if (oFileChecker.readyState == "complete")
    {
        checkSize();
    }
}

function checkSize()
{
    var limit  = 2*1024 * 1024;
	
    if (oFileChecker.fileSize > limit)
    {
        alert("文件超过最大长度");
        return;
    }
    else
    {
        if (d_checkfromfile.checked){
		// 上传文件判断
		if (!d_file.CheckUploadForm()) return false;
		// 使各输入框无效
		DisableItems();
		// 显示正在上传图片
		divProcessing.style.display="";
		// 上传表单提交
                d_file.myform.recordno.value="<%=record_no%>";
		d_file.myform.submit();
		for(i=1;i<=secs;i++) {
                    window.setTimeout("update(" + i + ")", secs*1000);
                }
		}
		if (d_checkfromsys.checked){
			d_fromurl.value = relativePath2setPath("sysimage/bg/" + d_fromsys.options[d_fromsys.selectedIndex].value);
			ReturnValue();
		}
		if (d_checkcancel.checked){
			d_fromurl.value="";
			ReturnValue();
		}
    }
}


</script>
</HEAD>

<BODY bgColor=menu onload="InitDocument()">

<table border=0 cellpadding=0 cellspacing=0 align=center>
<tr>
	<td>
	<fieldset>
	<legend>图片来源  (注意：图片不可超过2M)</legend>
	<table border=0 cellpadding=0 cellspacing=0>
	<tr><td colspan=9 height=5></td></tr>
	<tr>
		<td width=7></td>
		<td width=54 align=right onclick="RadioClick('file')"><input type=radio id="d_checkfromfile" onclick="RadioClick('file')">上传:</td>
		<td width=5></td>
		<td colspan=2>
		<Script Language=JavaScript>
		document.write('<iframe id=d_file frameborder=0 src="../editUpload.jsp?recordno=<%=record_no%>" width="80%" height="220px" scrolling=yes></iframe>');
		document.write('<img src="about:blank" id="fileChecker" alt="test"  height="18" style="visibility:hidden"/>');
		</Script>
		</td>
		<td width=7></td>
	</tr>
	<tr><td colspan=9 height=5></td></tr>
	<tr>
		<td width=7></td>
		<td width=54 align=right onclick="RadioClick('url')"><input type=radio id="d_checkfromurl" onclick="RadioClick('url')">网络:</td>
		<td width=5></td>
		<td colspan=2><input type=text id="d_fromurl" style="width:243px" size=30 value=""></td>
		<td width=7></td>
	</tr>
	<tr><td colspan=9 height=5></td></tr>
	<tr>
		<td width=7></td>
		<td width=54 align=right onclick="RadioClick('sys')"><input type=radio id="d_checkfromsys" onclick="RadioClick('sys')">系统:</td>
		<td width=5></td>
		<td>
			<select id="d_fromsys" size=1>
			<option value="snow.gif" selected>雪花</option>
			<option value="Nature.jpg">自然</option>
			<option value="Clear.jpg">晴朗</option>
			<option value="Glacier.jpg">冰川</option>
			<option value="Fiesta.jpg">节日</option>
			<option value="Birthday.GIF">生日</option>
			<option value="Citrus.gif">秋叶</option>
			<option value="Hearts.GIF">心型</option>
			<option value="Flower.GIF">鲜花</option>
			<option value="Gathering.jpg">花纹</option>
			<option value="christmas.gif">圣诞节</option>
			<option value="Ivy.gif">常春藤</option>
			<option value="tech.gif">技术型</option>
			<option value="Maize.jpg">黄色旋纹</option>
			<option value="grid.gif">绿色方格</option>
			</select>
		</td>
		<td onclick="RadioClick('cancel')"><input type=radio id="d_checkcancel" onclick="RadioClick('cancel')">取消背景图 </td>
		<td width=7></td>
	</tr>
	<tr><td colspan=9 height=5></td></tr>
	</table>
	</fieldset>
	</td>
</tr>
<tr><td height=5></td></tr>
<tr>
	<td>
	<fieldset>
	<legend>显示效果</legend>
	<table border=0 cellpadding=0 cellspacing=0>
	<tr><td colspan=9 height=5></td></tr>
	<tr>
		<td width=7></td>
		<td>平铺方式:</td>
		<td width=5></td>
		<td>
			<select id=d_repeat size=1 style="width:72px">
			<option value='' selected>默认</option>
			<option value='repeat'>纵向横向</option>
			<option value='no-repeat'>不平铺</option>
			<option value='repeat-x'>横向</option>
			<option value='repeat-y'>纵向</option>
			</select>
		</td>
		<td width=40></td>
		<td>滚动固定:</td>
		<td width=5></td>
		<td>
			<select id=d_attachment style="width:72px" size=1>
			<option value='' selected>默认</option>
			<option value='scroll'>滚动</option>
			<option value='fixed'>固定</option>
            </select>		
		</td>
		<td width=7></td>
	</tr>
	<tr><td colspan=9 height=5></td></tr>
	</table>
	</fieldset>
	</td>
</tr>
<tr><td height=5></td></tr>
<tr><td align=right><input type=submit value='  确定  ' id=Ok onclick="ok()">&nbsp;&nbsp;<input type=button value='  取消  ' onclick="window.close();"></td></tr>
</table>

<div id=divProcessing style="width:200px;height:30px;position:absolute;left:70px;top:45px;display:none">
<table border=0 cellpadding=0 cellspacing=1 bgcolor="#000000" width="100%" height="100%"><tr><td bgcolor=#3A6EA5><marquee align="middle" behavior="alternate" scrollamount="5"><font color=#FFFFFF>...图片上传中...请等待...</font></marquee></td></tr></table>
</div>

</body>
</html>