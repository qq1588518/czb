<%@page contentType="text/html;charset=GBK"%>
<%@page import="basic.util.*"%>
<%@include file="/WEB-INF/jspf/pub_uri.jspf" %>
<%
String record_no=CUtil.createBillNo("YMDHm######");
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0); 
%>
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script src="../../js/common.js"></script>
<style type="text/css">
body, a, table, div, span, td, th, input, select{font:9pt;font-family: "����", Verdana, Arial, Helvetica, sans-serif;}
body {padding:5px}
</style>

<script language="JavaScript" src="dialog.js"></script>

<script language="JavaScript">
var sAction = "INSERT";
var sTitle = "����";

var oControl;
var oSeletion;
var sRangeType;

var sFromUrl = "http://";
var sAlt = "";
var sBorder = "0";
var sBorderColor = "";
var sFilter = "";
var sAlign = "";
var sWidth = "";
var sHeight = "";
var sVSpace = "";
var sHSpace = "";

var sCheckFlag = "file";

oSelection = dialogArguments.eWebEditor.document.selection.createRange();
sRangeType = dialogArguments.eWebEditor.document.selection.type;

if (sRangeType == "Control") {
	if (oSelection.item(0).tagName == "IMG"){
		sAction = "MODI";
		sTitle = "�޸�";
		sCheckFlag = "url";
		oControl = oSelection.item(0);
		sFromUrl = oControl.getAttribute("src", 2);
		sAlt = oControl.alt;
		sBorder = oControl.border;
		sBorderColor = oControl.style.borderColor;
		sFilter = oControl.style.filter;
		sAlign = oControl.align;
		sWidth = oControl.width;
		sHeight = oControl.height;
		sVSpace = oControl.vspace;
		sHSpace = oControl.hspace;
	}
}


document.write("<title>ͼƬ���ԣ�" + sTitle + "��</title>");


// ��ʼֵ
function InitDocument(){
	SearchSelectValue(d_filter, sFilter);
	SearchSelectValue(d_align, sAlign.toLowerCase());

	d_fromurl.value = sFromUrl;
	d_alt.value = sAlt;
	d_border.value = sBorder;
	d_bordercolor.value = sBorderColor;
	s_bordercolor.style.backgroundColor = sBorderColor;
	d_width.value = sWidth;
	d_height.value = sHeight;
	d_vspace.value = sVSpace;
	d_hspace.value = sHSpace;
}


// ͼƬ��Դ��ѡ����¼�
function RadioClick(what){
	if (what=="url"){
		d_checkfromfile.checked=false;
		d_fromurl.disabled=false;
		d_checkfromurl.checked=true;
		d_file.myform.uploadfile.disabled=true;
	}else{
		d_checkfromurl.checked=false;
		d_file.myform.uploadfile.disabled=false;
		d_checkfromfile.checked=true;
		d_fromurl.disabled=true;
	}
}

// �ϴ�֡�������ʱִ��
function UploadLoaded(){
	// ��ʼradio
	RadioClick(sCheckFlag);
}

// �ϴ�����
function UploadError(sErrDesc){
	AbleItems();
	RadioClick('file');
	divProcessing.style.display="none";
	try {
                BaseAlert(d_file.myform.uploadfile,sErrDesc);
	}
	catch(e){}
}

// �ļ��ϴ����ʱִ��,�����ϴ��ļ���
function UploadSaved(sPathFileName){
	d_fromurl.value = sPathFileName;
	ReturnValue();
}

// �����ڷ���ֵ
function ReturnValue(){  
	sFromUrl = d_fromurl.value;
	sAlt = d_alt.value;
	sBorder = d_border.value;
	sBorderColor = d_bordercolor.value;
	sFilter = d_filter.options[d_filter.selectedIndex].value;
	sAlign = d_align.value;
	sWidth = d_width.value;
	sHeight = d_height.value;
	sVSpace = d_vspace.value;
	sHSpace = d_hspace.value;

	if (sAction == "MODI") {
		oControl.src = sFromUrl;
		oControl.alt = sAlt;
		oControl.border = sBorder;
		oControl.style.borderColor = sBorderColor;
		oControl.style.filter = sFilter;
		oControl.align = sAlign;
		oControl.width = sWidth;
		oControl.height = sHeight;
		oControl.style.width = sWidth;
		oControl.style.height = sHeight;
		oControl.vspace = sVSpace;
		oControl.hspace = sHSpace;
	}else{
		var sHTML = '';
		if (sFilter!=""){
			sHTML=sHTML+'filter:'+sFilter+';';
		}
		if (sBorderColor!=""){
			sHTML=sHTML+'border-color:'+sBorderColor+';';
		}
		if (sHTML!=""){
			sHTML=' style="'+sHTML+'"';
		}
		sHTML = '<img id=eWebEditor_TempElement_Img src="'+sFromUrl+'"'+sHTML;
		if (sBorder!=""){
			sHTML=sHTML+' border="'+sBorder+'"';
		}
		if (sAlt!=""){
			sHTML=sHTML+' alt="'+sAlt+'"';
		}
		if (sAlign!=""){
			sHTML=sHTML+' align="'+sAlign+'"';
		}
		if (sWidth!=""){
			sHTML=sHTML+' width="'+sWidth+'"';
		}
		if (sHeight!=""){
			sHTML=sHTML+' height="'+sHeight+'"';
		}
		if (sVSpace!=""){
			sHTML=sHTML+' vspace="'+sVSpace+'"';
		}
		if (sHSpace!=""){
			sHTML=sHTML+' hspace="'+sHSpace+'"';
		}
		sHTML=sHTML+'>';
		dialogArguments.insertHTML(sHTML);

		var oTempElement = dialogArguments.eWebEditor.document.getElementById("eWebEditor_TempElement_Img");
		oTempElement.src = sFromUrl;
		oTempElement.removeAttribute("id");

	}

	window.returnValue = null;
	window.close();
}

//modify by Tim 20110917 secs = 15
var secs = 1;

// ��ȷ��ʱִ��
function ok(){
	// �������������Ч��
	d_border.value = ToInt(d_border.value);
	d_width.value = ToInt(d_width.value);
	d_height.value = ToInt(d_height.value);
	d_vspace.value = ToInt(d_vspace.value);
	d_hspace.value = ToInt(d_hspace.value);
	// �߿���ɫ����Ч��
	if (!IsColor(d_bordercolor.value)){
		BaseAlert(d_bordercolor,'��ʾ��\n\n��Ч�ı߿���ɫֵ��');
		return false;
	}
	if (d_checkfromurl.checked){
		// ����ֵ
		ReturnValue();
	}else{
		// �ϴ��ļ��ж�
		if (!d_file.CheckUploadForm()) return false;
		changeSrc(d_file.myform.uploadfile);
		
	}
}

function update(num) {
    if(d_file.myform.update_success_flag.value=="true" && num == secs){
        var pic_addr = d_file.myform.pic_addr.value;
        var id = createRandomMultiID(8);
        //modify by Tim 20110918 tmp_str = "<%=c_url%>"+pic_addr
        var html="<IMG id='"+id+"' src=../../../../ssczb/"+pic_addr+">";
        var tmp_str = "../../../../ssczb/"+pic_addr;
        dialogArguments.insertHTML(html,tmp_str,id);
        window.close();
    }
}

// ʹ�����������Ч
function DisableItems(){
	d_checkfromfile.disabled=true;
	d_checkfromurl.disabled=true;
	d_fromurl.disabled=true;
	d_alt.disabled=true;
	d_border.disabled=true;
	d_bordercolor.disabled=true;
	d_filter.disabled=true;
	d_align.disabled=true;
	d_width.disabled=true;
	d_height.disabled=true;
	d_vspace.disabled=true;
	d_hspace.disabled=true;
	Ok.disabled=true;
}

// ʹ�����������Ч
function AbleItems(){
	d_checkfromfile.disabled=false;
	d_checkfromurl.disabled=false;
	d_fromurl.disabled=false;
	d_alt.disabled=false;
	d_border.disabled=false;
	d_bordercolor.disabled=false;
	d_filter.disabled=false;
	d_align.disabled=false;
	d_width.disabled=false;
	d_height.disabled=false;
	d_vspace.disabled=false;
	d_hspace.disabled=false;
	Ok.disabled=false;
}

</script>
<script defer=true>
var oFileChecker = document.getElementById("fileChecker");

oFileChecker.onreadystatechange = function()
{
    if (oFileChecker.readyState == "complete")
    {
        checkSize();
    }
}
function changeSrc(filePicker)
{
	if(getExplorerVesion().ie){
		if(getExplorerVesion().ie=="7.0" || getExplorerVesion().ie=="8.0"  ){
		
			checkSize();
		}else if(getExplorerVesion().ie=="6.0"){
		
			oFileChecker.src = filePicker.value;
		}
    }else{
    
    	alert("��֧�ַ�IE�����!");
    }
    //checkSize();
}


function checkSize()
{
    var limit  = 2*1024 * 1024;
    if (oFileChecker.fileSize > limit)
    {
        alert("�ļ�������󳤶�");
        return;
    }
    else
    {
        // ʹ���������Ч
		DisableItems();
		// ��ʾ�����ϴ�ͼƬ
		divProcessing.style.display="";
		// �ϴ������ύ
                d_file.myform.recordno.value="<%=record_no%>";
		d_file.myform.submit();
		//modify by Tim 20110917 ����ͼƬ�ϴ�
		for(i=1;i<=secs;i++) {
        	window.setTimeout("update(" + i + ")", secs*1000);
        }
    }
}


</script>
<BODY bgColor=menu onload="InitDocument()">

<table border=0 cellpadding=0 cellspacing=0 align=center>
<tr>
	<td>
	<fieldset>
	<legend>ͼƬ��Դ  (ע�⣺ͼƬ���ɳ���2M)</legend>
	<table border=0 cellpadding=0 cellspacing=0>
	<tr><td colspan=9 height=5></td></tr>
	<tr>
		<td width=7></td>
		<td width=54 align=right onclick="RadioClick('file')"><input type=radio id="d_checkfromfile" value="1" onclick="RadioClick('file')">�ϴ�:</td>
		<td width=5></td>
		<td colspan=5>
		<Script Language=JavaScript>
		document.write('<iframe id=d_file frameborder=0 src="../editUpload.jsp?recordno=<%=record_no%>" width="100%" height="22" scrolling=no></iframe>');
		document.write('<img src="about:blank" id="fileChecker" alt="test"  height="18" style="visibility:hidden" />');
		</Script>
		</td>
		<td width=7></td>
	</tr>
	<tr><td colspan=9 height=5></td></tr>
	<tr>
		<td width=7></td>
		<td width=54 align=right onclick="RadioClick('url')"><input type=radio id="d_checkfromurl" value="1" onclick="RadioClick('url')">����:</td>
		<td width=5></td>
		<td colspan=5><input type=text id="d_fromurl" style="width:243px" size=30 value=""></td>
		<td width=7></td>
	</tr>
            <tr><td colspan=9 height=5></td></tr>
	</table>
	</fieldset>
	</td>
</tr>
<tr><td height=5></td></tr>
<tr style="display:none;">
	<td>
	<fieldset>
	<legend>��ʾЧ��</legend>
	<table border=0 cellpadding=0 cellspacing=0>
	<tr><td colspan=9 height=5></td></tr>
	<tr>
		<td width=7></td>
		<td>˵������:</td>
		<td width=5></td>
		<td colspan=5><input type=text id=d_alt size=38 value="" style="width:243px"></td>
		<td width=7></td>
	</tr>
	<tr><td colspan=9 height=5></td></tr>
	<tr>
		<td width=7></td>
		<td noWrap>�߿��ϸ:</td>
		<td width=5></td>
		<td><input type=text id=d_border size=10 value="" ONKEYPRESS="event.returnValue=IsDigit();"></td>
		<td width=40></td>
		<td noWrap>�߿���ɫ:</td>
		<td width=5></td>
		<td><table border=0 cellpadding=0 cellspacing=0><tr><td><input type=text id=d_bordercolor size=7 value=""></td><td><img border=0 src="../sysimage/rect.gif" width=18 style="cursor:hand" id=s_bordercolor onclick="SelectColor('bordercolor')"></td></tr></table></td>
		<td width=7></td>
	</tr>
	<tr><td colspan=9 height=5></td></tr>
	<tr>
		<td width=7></td>
		<td>����Ч��:</td>
		<td width=5></td>
		<td>
			<select id=d_filter style="width:72px" size=1>
			<option value='' selected>��</option>
			<option value='Alpha(Opacity=50)'>��͸��</option>
			<option value='Alpha(Opacity=0, FinishOpacity=100, Style=1, StartX=0, StartY=0, FinishX=100, FinishY=140)'>����͸��</option>
			<option value='Alpha(Opacity=10, FinishOpacity=100, Style=2, StartX=30, StartY=30, FinishX=200, FinishY=200)'>����͸��</option>
			<option value='blur(add=1,direction=14,strength=15)'>ģ��Ч��</option><option value='blur(add=true,direction=45,strength=30)'>�綯ģ��</option>
			<option value='Wave(Add=0, Freq=60, LightStrength=1, Phase=0, Strength=3)'>���Ҳ���</option>
			<option value='gray'>�ڰ���Ƭ</option><option value='Chroma(Color=#FFFFFF)'>��ɫ͸��</option>
			<option value='DropShadow(Color=#999999, OffX=7, OffY=4, Positive=1)'>Ͷ����Ӱ</option>
			<option value='Shadow(Color=#999999, Direction=45)'>��Ӱ</option>
			<option value='Glow(Color=#ff9900, Strength=5)'>����</option>
			<option value='flipv'>��ֱ��ת</option>
			<option value='fliph'>���ҷ�ת</option>
			<option value='grays'>���Ͳ�ɫ</option>
			<option value='xray'>X����Ƭ</option>
			<option value='invert'>��Ƭ</option>
            </select>		
		</td>
		<td width=40></td>
		<td>���뷽ʽ:</td>
		<td width=5></td>
		<td>
			<select id=d_align size=1 style="width:72px">
			<option value='' selected>Ĭ��</option>
			<option value='left'>����</option>
			<option value='right'>����</option>
			<option value='top'>����</option>
			<option value='middle'>�в�</option>
			<option value='bottom'>�ײ�</option>
			<option value='absmiddle'>���Ծ���</option>
			<option value='absbottom'>���Եײ�</option>
			<option value='baseline'>����</option>
			<option value='texttop'>�ı�����</option>
			</select>
		</td>
		<td width=7></td>
	</tr>
	<tr><td colspan=9 height=5></td></tr>
	<tr>
		<td width=7></td>
		<td>ͼƬ����:</td>
		<td width=5></td>
		<td><input type=text id=d_width size=10 value="" ONKEYPRESS="event.returnValue=IsDigit();" maxlength=4></td>
		<td width=40></td>
		<td>ͼƬ�߶�:</td>
		<td width=5></td>
		<td><input type=text id=d_height size=10 value="" ONKEYPRESS="event.returnValue=IsDigit();" maxlength=4></td>
		<td width=7></td>
	</tr>
	<tr><td colspan=9 height=5></td></tr>
	<tr>
		<td width=7></td>
		<td>���¼��:</td>
		<td width=5></td>
		<td><input type=text id=d_vspace size=10 value="" ONKEYPRESS="event.returnValue=IsDigit();" maxlength=2></td>
		<td width=40></td>
		<td>���Ҽ��:</td>
		<td width=5></td>
		<td><input type=text id=d_hspace size=10 value="" ONKEYPRESS="event.returnValue=IsDigit();" maxlength=2></td>
		<td width=7></td>
	</tr>
	<tr><td colspan=9 height=5></td></tr>
	</table>
	</fieldset>
	</td>
</tr>
<tr><td height=5></td></tr>
<tr><td align=right><input type=submit value='  ȷ��  ' id=Ok onclick="ok()">&nbsp;&nbsp;<input type=button value='  ȡ��  ' onclick="window.close();"></td></tr>
</table>

<div id=divProcessing style="width:200px;height:30px;position:absolute;left:70px;top:100px;display:none">
<table border=0 cellpadding=0 cellspacing=1 bgcolor="#000000" width="100%" height="100%"><tr><td bgcolor=#3A6EA5><marquee align="middle" behavior="alternate" scrollamount="5"><font color=#FFFFFF>...ͼƬ�ϴ���...��ȴ�...</font></marquee></td></tr></table>
</div>

</body>
</html>