<%@ page contentType="text/html;charset=GBK"%>
<%
String sFullScreen=request.getParameter("fullscreen")==null?"":request.getParameter("fullscreen");
%>
<HTML>
    <HEAD><TITLE>在线编辑器</TITLE>
        <META http-equiv=Content-Type content="text/html; charset=GBK">
        <link href="include/Editor.css" rel="stylesheet" type="text/css"/>
        <SCRIPT language="Javascript">
//var sPath = document.location.pathname;
//sPath = sPath.substr(0, sPath.length-10); 
<%if(sFullScreen.equals("1")){%>
var sLinkFieldName = "ContentFullScreen" ;
<%}else{%>
var sLinkFieldName = "ContentNormal" ;
<%}%>
var sLinkOriginalFileName = "" ;
var sLinkSaveFileName = "" ;
var sLinkSavePathFileName = "" ;

// 全局设置对象
var config = new Object() ;
config.Version = "2.8.0" ;
config.ReleaseDate = "2004-07-06" ;
config.License = "" ;
config.StyleName = "s_coolblue";
config.StyleMenuHeader = "<head><link href=\"css/coolblue/MenuArea.css\" type=\"text/css\" rel=\"stylesheet\"></head><body scroll=\"no\" onConTextMenu=\"event.returnValue=false;\">";
config.StyleDir = "standard";
config.StyleUploadDir = "UploadFile/";
config.InitMode = "EDIT";
config.AutoDetectPasteFromWord = true;
config.BaseUrl = "1";
config.BaseHref = "";
config.AutoRemote = "1";
config.ShowBorder = "0";

var sBaseHref = "";
if(config.BaseHref!=""){
	sBaseHref = "<base href=\"" + document.location.protocol + "//" + document.location.host + config.BaseHref + "\">";
}
config.StyleEditorHeader = "<head><link href=\"../../editor/css/coolblue/EditorArea.css\" type=\"text/css\" rel=\"stylesheet\">" + sBaseHref + "</head><body MONOSPACE>" ;
                                                                                                                                                                               </SCRIPT>
        
        <SCRIPT id="edit" language=Javascript src="include/editor.js" charset="utf-8"></SCRIPT>
        
        <SCRIPT language=Javascript src="include/table.js"></SCRIPT>
        
        <SCRIPT language=Javascript src="include/menu.js"></SCRIPT>
        
        <SCRIPT language=javascript event="onerror(msg, url, line)" for=window>
return true ;	 // 隐藏错误
        </SCRIPT>
        <SCRIPT language=Javascript>
// 全屏编辑
function Full() {
	if (!validateMode()) return;
        window.open("dialog/fullscreen.jsp?style="+config.StyleName, 'FullScreen'+sLinkFieldName, 'toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,fullscreen=yes');
}
        </SCRIPT>
    <META content="MSHTML 6.00.2800.1106" name=GENERATOR></HEAD>
    <BODY oncontextmenu=event.returnValue=false; SCROLLING="no">
        <TABLE height="100%" cellSpacing="0" cellPadding="0" width="100%" border="0">
            <TBODY>
                <TR>
                    <TD>
                        <TABLE class=Toolbar id=eWebEditor_Toolbar cellSpacing=0 cellPadding=0 width="100%" border=0>
                            <TBODY>
                                <TR>
                                    <TD>
                                        <DIV class=yToolbar>
                                            <DIV class=TBHandle></DIV>
                                            <!--<SELECT class=TBGen onchange="format('fontname',this[this.selectedIndex].value);this.selectedIndex=0">
                                                <OPTION selected>字体</OPTION>
                                                <OPTION value=宋体>宋体</OPTION>
                                                <OPTION value=黑体>黑体</OPTION>
                                                <OPTION value=楷体_GB2312>楷体</OPTION>
                                                <OPTION value=仿宋_GB2312>仿宋</OPTION>
                                                <OPTION value=隶书>隶书</OPTION>
                                                <OPTION value=幼圆>幼圆</OPTION>
                                                <OPTION value=Arial>Arial</OPTION>
                                                <OPTION value="Arial Black">Arial Black</OPTION>
                                                <OPTION value="Arial Narrow">Arial Narrow</OPTION>
                                                <OPTION value="Brush Script&#9;MT">Brush Script MT</OPTION>
                                                <OPTION value="Century Gothic">Century Gothic</OPTION>
                                                <OPTION value="Comic Sans MS">Comic Sans MS</OPTION>
                                                <OPTION value=Courier>Courier</OPTION> 
                                                <OPTION value="Courier New">Courier New</OPTION>
                                                <OPTION value="MS Sans Serif">MS Sans Serif</OPTION> 
                                                <OPTION value=Script>Script</OPTION>
                                                <OPTION value=System>System</OPTION>
                                                <OPTION value="Times New Roman">Times New Roman</OPTION>
                                                <OPTION value=Verdana>Verdana</OPTION>
                                                <OPTION value="Wide Latin">Wide Latin</OPTION>
                                                <OPTION value=Wingdings>Wingdings</OPTION>
                                            </SELECT>
                                             <SELECT class=TBGen onchange="format('FormatBlock',this[this.selectedIndex].value);this.selectedIndex=0">
                                                <OPTION selected>段落样式</OPTION>
                                                <OPTION value="<P>">普通</OPTION>
                                                <OPTION value="<H1>">标题一</OPTION>
                                                <OPTION value="<H2>">标题二</OPTION> 
                                                <OPTION value="<H3>">标题三</OPTION>
                                                <OPTION value="<H4>">标题四</OPTION>
                                                <OPTION value="<H5>">标题五</OPTION> 
                                                <OPTION value="<H6>">标题六</OPTION>
                                                <OPTION value="<p>">段落</OPTION> 
                                                <OPTION value="<dd>">定义</OPTION>
                                                <OPTION value="<dt>">术语定义</OPTION>
                                                <OPTION value="<dir>">目录列表</OPTION> 
                                                <OPTION value="<menu>">菜单列表</OPTION>
                                                <OPTION value="<PRE>">已编排格式</OPTION>
                                            </SELECT>
                                            <SELECT class=TBGen onchange="format('fontname',this[this.selectedIndex].value);this.selectedIndex=0">
                                                <OPTION selected>字体</OPTION>
                                                <OPTION value=宋体>宋体</OPTION>
                                                <OPTION value=黑体>黑体</OPTION>
                                                <OPTION value=楷体_GB2312>楷体</OPTION>
                                                <OPTION value=仿宋_GB2312>仿宋</OPTION>
                                                <OPTION value=隶书>隶书</OPTION>
                                                <OPTION value=幼圆>幼圆</OPTION>
                                                <OPTION value=Arial>Arial</OPTION>
                                                <OPTION value="Arial Black">Arial Black</OPTION>
                                                <OPTION value="Arial Narrow">Arial Narrow</OPTION>
                                                <OPTION value="Brush Script&#9;MT">Brush Script MT</OPTION>
                                                <OPTION value="Century Gothic">Century Gothic</OPTION>
                                                <OPTION value="Comic Sans MS">Comic Sans MS</OPTION>
                                                <OPTION value=Courier>Courier</OPTION> 
                                                <OPTION value="Courier New">Courier New</OPTION>
                                                <OPTION value="MS Sans Serif">MS Sans Serif</OPTION> 
                                                <OPTION value=Script>Script</OPTION>
                                                <OPTION value=System>System</OPTION>
                                                <OPTION value="Times New Roman">Times New Roman</OPTION>
                                                <OPTION value=Verdana>Verdana</OPTION>
                                                <OPTION value="Wide Latin">Wide Latin</OPTION>
                                                <OPTION value=Wingdings>Wingdings</OPTION>
                                            </SELECT>
                                            <SELECT class=TBGen onchange="format('fontsize',this[this.selectedIndex].value);this.selectedIndex=0">
                                                <OPTION selected>字号</OPTION>
                                                <OPTION value=7>一号</OPTION>
                                                <OPTION value=6>二号</OPTION>
                                                <OPTION value=5>三号</OPTION>
                                                <OPTION value=4>四号</OPTION>
                                                <OPTION value=3>五号</OPTION> 
                                                <OPTION value=2>六号</OPTION> 
                                                <OPTION value=1>七号</OPTION>
                                            </SELECT>
                                            <SELECT class=TBGen onchange=doZoom(this[this.selectedIndex].value)>
                                                <OPTION value=10>10%</OPTION>
                                                <OPTION value=25>25%</OPTION>
                                                <OPTION value=50>50%</OPTION> 
                                                <OPTION value=75>75%</OPTION> 
                                                <OPTION value=100 selected>100%</OPTION> 
                                                <OPTION value=150>150%</OPTION> 
                                                <OPTION value=200>200%</OPTION> 
                                                <OPTION value=500>500%</OPTION>
                                            </SELECT> -->
                                            <DIV class=Btn title=粗体 onclick="format('bold')"><IMG class=Ico src="ButtonImage/standard/bold.gif"></DIV>
                                            <DIV class=Btn title=斜体 onclick="format('italic')"><IMG class=Ico src="ButtonImage/standard/italic.gif"></DIV>
                                            <DIV class=Btn title=下划线 onclick="format('underline')"><IMG class=Ico src="ButtonImage/standard/underline.gif"></DIV>
                                            <DIV class=Btn title=中划线 onclick="format('StrikeThrough')"><IMG class=Ico src="ButtonImage/standard/strikethrough.gif"></DIV>
                                            <DIV class=Btn title=上标 onclick="format('superscript')"><IMG class=Ico src="ButtonImage/standard/superscript.gif"></DIV>
                                            <DIV class=Btn title=下标 onclick="format('subscript')"><IMG class=Ico src="ButtonImage/standard/subscript.gif"></DIV>
                                            <DIV class=TBSep></DIV>
                                            <DIV class=Btn title=字体增大 onclick="insert('big')"><IMG class=Ico src="ButtonImage/standard/tobig.gif"></DIV>
                                            <DIV class=Btn title=字体减小 onclick="insert('small')"><IMG class=Ico src="ButtonImage/standard/tosmall.gif"></DIV>
                                            <DIV class=TBSep></DIV>
                                            <DIV class=Btn title=左对齐 onclick="format('justifyleft')"><IMG class=Ico src="ButtonImage/standard/justifyleft.gif"></DIV>
                                            <DIV class=Btn title=居中对齐 onclick="format('justifycenter')"><IMG class=Ico src="ButtonImage/standard/justifycenter.gif"></DIV>
                                            <DIV class=Btn title=右对齐 onclick="format('justifyright')"><IMG class=Ico src="ButtonImage/standard/justifyright.gif"></DIV>            
                                        </DIV>
                                    </TD>
                                </TR>
                                <TR>
                                    <TD>
                                        <DIV class=yToolbar>
                                            <DIV class=TBHandle></DIV>
                                            <DIV class=Btn title=新建 onclick="format('refresh')"><IMG class=Ico src="ButtonImage/standard/refresh.gif"></DIV>
                                            <DIV class=TBSep></DIV>
                                            <DIV class=Btn title=剪切 onclick="format('cut')"><IMG class=Ico src="ButtonImage/standard/cut.gif"></DIV>
                                            <DIV class=Btn title=复制 onclick="format('copy')"><IMG class=Ico src="ButtonImage/standard/copy.gif"></DIV>
                                            <DIV class=Btn title=常规粘贴 onclick="format('paste')"><IMG class=Ico src="ButtonImage/standard/paste.gif"></DIV>
                                            <DIV class=Btn title=纯文本粘贴 onclick=PasteText()><IMG class=Ico src="ButtonImage/standard/pastetext.gif"></DIV>
                                            <DIV class=Btn title=从Word中粘贴 onclick=PasteWord()><IMG class=Ico src="ButtonImage/standard/pasteword.gif"></DIV>
                                            <DIV class=TBSep></DIV>
                                            <DIV class=Btn title=查找替换 onclick=findReplace()><IMG class=Ico src="ButtonImage/standard/findreplace.gif"></DIV>
                                            <DIV class=Btn title=删除 onclick="format('delete')"><IMG class=Ico src="ButtonImage/standard/delete.gif"></DIV>
                                            <DIV class=Btn title=删除文字格式 onclick="format('RemoveFormat')"><IMG class=Ico src="ButtonImage/standard/removeformat.gif"></DIV>
                                            <DIV class=TBSep></DIV>
                                            <DIV class=Btn title=撤消 onclick=goHistory(-1)><IMG class=Ico src="ButtonImage/standard/undo.gif"></DIV>
                                            <DIV class=Btn title=恢复 onclick=goHistory(1)><IMG class=Ico src="ButtonImage/standard/redo.gif"></DIV>
                                            <DIV class=TBSep></DIV>
                                            <DIV class=Btn title=全部选中 onclick="format('SelectAll')"><IMG class=Ico src="ButtonImage/standard/selectall.gif"></DIV>
                                            <DIV class=Btn title=取消选择 onclick="format('Unselect')"><IMG class=Ico src="ButtonImage/standard/unselect.gif"></DIV>
                                            <DIV class=TBSep></DIV>
                                            <DIV class=Btn title=编号 onclick="format('insertorderedlist')"><IMG class=Ico src="ButtonImage/standard/insertorderedlist.gif"></DIV>
                                            <DIV class=Btn title=项目符号 onclick="format('insertunorderedlist')"><IMG class=Ico src="ButtonImage/standard/insertunorderedlist.gif"></DIV>
                                            <DIV class=Btn title=增加缩进量 onclick="format('indent')"><IMG class=Ico src="ButtonImage/standard/indent.gif"></DIV>
                                            <DIV class=Btn title=减少缩进量 onclick="format('outdent')"><IMG class=Ico src="ButtonImage/standard/outdent.gif"></DIV>
                                            <DIV class=TBSep></DIV>
                                            <DIV class=Btn title=字体颜色 onclick="ShowDialog('dialog/selcolor.html?action=forecolor', 280, 250, true)"><IMG  class=Ico src="ButtonImage/standard/forecolor.gif"></DIV>
                                            <DIV class=Btn title=对象背景颜色 onclick="ShowDialog('dialog/selcolor.html?action=bgcolor', 280, 250, true)"><IMG class=Ico src="ButtonImage/standard/bgcolor.gif"></DIV>
                                            <DIV class=Btn title=字体背景颜色 onclick="ShowDialog('dialog/selcolor.html?action=backcolor', 280, 250, true)"><IMG class=Ico src="ButtonImage/standard/BackColor.gif"></DIV>
                                            <!--<DIV class=Btn title=背景图片 onclick="ShowDialog('dialog/backimage.jsp',650, 530, true)"><IMG class=Ico src="ButtonImage/standard/bgpic.gif"></DIV> -->
                                            <DIV class=TBSep></DIV>
                                            <DIV class=Btn title=绝对或相对位置 onclick=absolutePosition()><IMG class=Ico src="ButtonImage/standard/abspos.gif"></DIV>
                                            <DIV class=Btn title=上移一层 onclick="zIndex('forward')"><IMG class=Ico src="ButtonImage/standard/forward.gif"></DIV>
                                            <DIV class=Btn title=下移一层 onclick="zIndex('backward')"><IMG class=Ico src="ButtonImage/standard/backward.gif"></DIV>
                                        </DIV>
                                    </TD>
                                </TR>
                                <TR>
                                    <TD>
                                        <DIV class=yToolbar>
                                            <DIV class=TBHandle></DIV>
                                            <DIV class=Btn title=插入或修改图片 onclick="ShowDialog('dialog/img.jsp', 750, 350,true)"><IMG class=Ico src="ButtonImage/standard/img.gif"></DIV>
                                            <DIV class=TBSep></DIV>
                                            <!--<DIV class=Btn title=表格菜单 onclick="showToolMenu('table')"><IMG class=Ico src="ButtonImage/standard/tablemenu.gif"></DIV> -->
                                            <DIV class=Btn title=表单菜单 onclick="showToolMenu('form')"><IMG class=Ico src="ButtonImage/standard/formmenu.gif"></DIV>
                                            <DIV class=Btn title=显示或隐藏指导方针 onclick=showBorders()><IMG class=Ico src="ButtonImage/standard/showborders.gif"></DIV>
                                            <DIV class=TBSep></DIV>
                                            <DIV class=Btn title=插入或修改栏目框 onclick="ShowDialog('dialog/fieldset.html', 350, 170, true)"><IMG class=Ico src="ButtonImage/standard/fieldset.gif"></DIV>
                                            <DIV class=Btn title=插入或修改网页帧 onclick="ShowDialog('dialog/iframe.html', 350, 200, true)"><IMG class=Ico src="ButtonImage/standard/iframe.gif"></DIV>
                                            <DIV class=Btn title=插入水平尺 onclick="format('InsertHorizontalRule')"><IMG class=Ico src="ButtonImage/standard/inserthorizontalrule.gif"></DIV>
                                            <DIV class=Btn title=插入或修改字幕 onclick="ShowDialog('dialog/marquee.html', 395, 150, true)"><IMG class=Ico src="ButtonImage/standard/marquee.gif"></DIV>
                                            <DIV class=Btn title=插入换行符 onclick="insert('br')"><IMG class=Ico src="ButtonImage/standard/br.gif"></DIV>
                                            <DIV class=Btn title=插入段落 onclick="format('InsertParagraph')"><IMG class=Ico src="ButtonImage/standard/insertparagraph.gif"></DIV>
                                            <DIV class=TBSep></DIV>
                                            <DIV class=Btn title=插入或修改超级链接 onclick=createLink()><IMG class=Ico src="ButtonImage/standard/createlink.gif"></DIV>
                                            <DIV class=Btn title=图形热点链接 onclick=mapEdit()><IMG class=Ico src="ButtonImage/standard/map.gif"></DIV>
                                            <DIV class=Btn title=取消超级链接或标签 onclick="format('UnLink')"><IMG class=Ico src="ButtonImage/standard/unlink.gif"></DIV>
                                            <DIV class=TBSep></DIV>
                                            <DIV class=Btn title=插入特殊字符 onclick="ShowDialog('dialog/symbol.html', 350, 220, true)"><IMG class=Ico src="ButtonImage/standard/symbol.gif"></DIV>
                                            <DIV class=Btn title=插入表情图标 onclick="ShowDialog('dialog/emot.html', 300, 180, true)"><IMG class=Ico src="ButtonImage/standard/emot.gif"></DIV>
                                            <DIV class=Btn title=插入当前日期 onclick="insert('nowdate')"><IMG class=Ico src="ButtonImage/standard/date.gif"></DIV>
                                            <DIV class=Btn title=插入当前时间 onclick="insert('nowtime')"><IMG class=Ico src="ButtonImage/standard/time.gif"></DIV>
                                            <DIV class=TBSep></DIV>
                                            <!--<DIV class=Btn title=引用样式 onclick="insert('quote')"><IMG class=Ico src="ButtonImage/standard/quote.gif"></DIV>-->
                                            <DIV class=TBSep></DIV>
                                            <%if(sFullScreen.equals("1")){%>
                                            <DIV CLASS="Btn" TITLE="全屏返回" onclick="parent.Minimize()"><IMG CLASS="Ico" SRC="ButtonImage/standard/minimize.gif"></DIV>
                                            <%}else{%>
                                            <!--<DIV class=Btn title=全屏编辑 onclick=Full()><IMG class=Ico src="ButtonImage/standard/maximize.gif"></DIV>-->
                                            <%}%>
                                        </DIV>
                                    </TD>
                                </TR>
                            </TBODY>
                        </TABLE>
                    </TD>
                </TR>
                <TR>
                    <TD height="100%">
                        <TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
                            <TBODY>
                                <TR>
                                    <TD height="100%">
                                        <INPUT id=ContentEdit type=hidden>
                                        <INPUT id=ModeEdit type=hidden> 
                                        <INPUT id=ContentLoad type=hidden> 
                                        <INPUT id=ContentFlag type=hidden value=0> 
                                        <IFRAME class="Composition" id="eWebEditor" marginWidth="1" marginHeight="1" src="about:blank" width="100%" scrolling="yes" height="100%"></IFRAME>
                                    </TD>
                                </TR>
                            </TBODY>
                        </TABLE>
                    </TD>
                </TR>
                <TR>
                    <TD height=25>
                        <TABLE class=StatusBar height=25 cellSpacing=0 cellPadding=0 width="100%" border=0>
                            <TBODY>
                                <TR vAlign=center>
                                    <TD>
                                        <TABLE height=20 cellSpacing=0 cellPadding=0 border=0>
                                            <TBODY>
                                                <TR>
                                                    <TD width=10></TD>
                                                    <TD class=StatusBarBtnOff id=eWebEditor_CODE onclick="setMode('CODE')">
                                                    <IMG height=15 src="ButtonImage/standard/modecode.gif" width=50 align=absMiddle border=0></TD>
                                                    <TD width=5></TD>
                                                    <TD class=StatusBarBtnOff id=eWebEditor_EDIT onclick="setMode('EDIT')">
                                                    <IMG height=15 src="ButtonImage/standard/modeedit.gif" width=50 align=absMiddle border=0></TD>
                                                    <TD width=5></TD>
                                                    <!--<TD class=StatusBarBtnOff id=eWebEditor_TEXT onclick="setMode('TEXT')">
                                                    <IMG height=15 src="ButtonImage/standard/modetext.gif" width=50 align=absMiddle border=0></TD>
                                                    <TD width=5></TD>-->
                                                    <TD class=StatusBarBtnOff id=eWebEditor_VIEW onclick="yulan();">
                                                        <IMG height=15 src="ButtonImage/standard/modepreview.gif" width=50 align=absMiddle border=0>
                                                    </TD>
                                                </TR>
                                            </TBODY>
                                        </TABLE>
                                    </TD>
                                    <TD  style="FONT-SIZE: 9pt" align=middle></TD>
                                    <%if(sFullScreen.equals("1")){%>
                                    <TD align=right></TD>
                                    <%}else{%>
                                    <TD align=right>
                                        <!--<TABLE height=20 cellSpacing=0 cellPadding=0 border=0>
                                            <TBODY>
                                                <TR>
                                                    <TD style="CURSOR: pointer" onclick=sizeChange(300)>
                                                    <IMG height=20 alt=增高编辑区 src="ButtonImage/standard/sizeplus.gif" width=20 border=0></TD>
                                                    <TD width=5></TD>
                                                    <TD style="CURSOR: pointer" onclick=sizeChange(-300)>
                                                    <IMG height=20 alt=减小编辑区 src="ButtonImage/standard/sizeminus.gif" width=20 border=0></TD>
                                                    <TD width=40></TD>
                                                </TR>
                                            </TBODY>
                                        </TABLE>-->
                                    </TD>
                                    <%}%>
        </TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
        <DIV id=eWebEditor_Temp_HTML style="VISIBILITY: hidden; OVERFLOW: hidden; WIDTH: 1px; POSITION: absolute; HEIGHT: 1px"></DIV>
        <FORM id=eWebEditor_UploadForm action=editUpload.jsp?action=remote&type=remote&style=s_coolblue method=post target=eWebEditor_UploadTarget>
              <INPUT type=hidden name=eWebEditor_UploadText> 
        </FORM>
        <IFRAME name=eWebEditor_UploadTarget src="about:blank" width=0 height=0></IFRAME>
        <DIV id=divProcessing style="DISPLAY: none; WIDTH: 200px; POSITION: absolute; HEIGHT: 30px">
            <TABLE height="100%" cellSpacing=1 cellPadding=0 width="100%" bgColor=#000000 border=0>
                <TBODY>
                    <TR>
                        <TD bgColor=#3a6ea5>
                            <MARQUEE style="FONT-SIZE: 9pt" scrollAmount=5 behavior=alternate align="middle"><FONT color=#ffffff>...数据保存中...请等待...</FONT></MARQUEE>
                        </TD>
                    </TR>
                </TBODY>
            </TABLE>
        </DIV>
    </BODY>
    <script>
    	
	    var fire = document.createEventObject();
	    document.getElementById("eWebEditor_EDIT").fireEvent("onclick",fire);	
	    function yulan(){
	    parent.updateCon();
	    var obj = new Object();
		var array = [];
		var total = [];
		obj["content"] = array;
		    var inp_obj = parent.document.getElementById("viewli").getElementsByTagName("INPUT");
		    var title = parent.document.getElementById("namep").value!=""?parent.document.getElementById("namep").value:"三生财智宝";
			var len = inp_obj.length;
			if(len>0){
				for(var j=0;j<len;j++){
					if(inp_obj[j].type=="hidden"){
					    
						array[j]=inp_obj[j].value;
						total[j]= "<div id='total"+j+"'style=\"display:block\">"+array[j]+"</div>";
					}
					
				}
			}
		//obj["all"] = total.join("");
	    var availWidth=320;
        var availHeight=480;
	    //win.document.write('<div style=\'text-align:right\'><input type=\'button\'  value=\'关闭预览\'onclick=\'window.close();\' /></div><br>')
	    //win.document.write(eWebEditor.document.body.innerHTML);
		var win= window.open('about:blank','',"height="+availHeight+",width="+availWidth+",left=0,top=0,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");
	    var today = new Date();
	    var datetime= today.getYear()+"-"+today.getMonth()+"-"+today.getDate()+"  "+today.getHours()+":"+today.getMinutes()+":"+today.getSeconds();
	    //var content_desc = "<div style='text-align:right'><input type='button'  value='关闭预览' onclick='window.close();' /></div><br><table cellpadding='0' cellspacing='0' width='100%' height='100%' BGCOLOR='#f0f7f9'><tr height='80px'><td align='center' valign='center' width='100%' height='80px'><span style='font-size:20px;FONT-FAMILY:宋体; mso-ascii-font-family:Times New Roman; mso-hansi-font-family:Times New Roman'><b></b></span></td></tr><tr height='60px'><td align='center' valign='center' width='100%' height='60px'><a href=''>http://www.5345.com</a>&nbsp;&nbsp;"+datetime+"&nbsp;&nbsp;三生财智宝</td></tr><tr><td align='left' valign='top' width='100%' height='100%'>" + total.join("") + "</td></tr></table>";
	    var content_desc = "<html  xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\" /><meta http-equiv=\"Cache-Control\" content=\"no-cache\"/><META HTTP-EQUIV=\"pragma\" CONTENT=\"no-cache\"><META HTTP-EQUIV=\"expires\" CONTENT=\"0\"><meta id=\"viewport\" name=\"viewport\" content=\"width=320; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\" /><title>三生财智宝</title><style type=\"text/css\">body{margin:0;padding:0;color:#333;background-color:#fff9f2;font-size:13pt;}*{line-height:22px;}p,ul,dl,dt,dd,h1,h2,h3,form{margin:0;padding:0}img{border:0}img,input{vertical-align:middle}li{list-style:none}a{color:#039;text-decoration:underline}a:hover{color:#f00;text-decoration:none}p,dl,li{padding:0 5px}#wrap{margin:0 auto;width:100%; background-color:#fff9f2;}.p{background:#d80c18;border-bottom:1px solid #fff;line-height:25px;color:#fff;}.p a{color:#666}.p a:hover{color:#f00}h3{text-align:center;line-height:25px;border-bottom:1px solid #ef8043;margin:0 5px;font-weight:normal;color:#ef8043}.info{text-align:center}</style></head><body><div id='wrap'><p class=\"p\">"+title+"</p><h3>"+title+"</h3><p class=\"info\">"+datetime+"</p><p><span id=\"start\">"+total.join("")+"</span><span id=\"end\" style=\"display:none\"></span><br/></p><p class=\"p p2\">三生财智宝</p></div></body></html>";
	    win.document.write(content_desc);
    }
    document.body.onkeydown=function(){if(event.keyCode==8) event.returnValue=false;};
    </script>
</HTML>
