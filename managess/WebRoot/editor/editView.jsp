<%@ page contentType="text/html;charset=GBK"%>
<%
    String sFullScreen=request.getParameter("fullscreen")==null?"":request.getParameter("fullscreen");
%>
<HTML>
<HEAD><TITLE>在线编辑器</TITLE>
<META http-equiv=Content-Type content="text/html; charset=GBK">
<link href="include/Editor.css" rel="stylesheet" type="text/css"/>
<SCRIPT language=Javascript>
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
config.InitMode = "VIEW";
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
// 全屏预览
function MaximizeView() {
	window.open("dialog/fullscreenView.htm?style="+config.StyleName, 'FullScreen'+sLinkFieldName, 'toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,fullscreen=yes');
}
</SCRIPT>

<SCRIPT language=Javascript src="include/editor.js"></SCRIPT>

<SCRIPT language=Javascript src="include/table.js"></SCRIPT>

<SCRIPT language=Javascript src="include/menu.js"></SCRIPT>

<SCRIPT language=javascript event="onerror(msg, url, line)" for=window>
return true ;	 // 隐藏错误
</SCRIPT>

<META content="MSHTML 6.00.2800.1106" name=GENERATOR></HEAD>
<BODY oncontextmenu=event.returnValue=false; SCROLLING="no">
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  <TR>
    <TD>
      <TABLE class=Toolbar id=eWebEditor_Toolbar cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR>
          <TD>
            <DIV class=yToolbar>
            <DIV class=TBHandle></DIV>
<%if(sFullScreen.equals("1")){%>
            <DIV CLASS="Btn" TITLE="全屏返回" onclick="parent.Minimize()" style="cursor:hand"><IMG CLASS="Ico" SRC="ButtonImage/standard/minimize.gif"></DIV>
<%}else{%>
            <DIV class="Btn" title="全屏预览" onclick="MaximizeView()" style="cursor:hand"><IMG class="Ico" src="ButtonImage/standard/maximize.gif"></DIV>
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
                <TD class=StatusBarBtnOff id=eWebEditor_VIEW onclick="setMode('VIEW')">
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
            <TABLE height=20 cellSpacing=0 cellPadding=0 border=0>
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
            </TABLE>
          </TD>
          <%}%>
          </TR>
        </TBODY>
      </TABLE>
    </TD>
  </TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
