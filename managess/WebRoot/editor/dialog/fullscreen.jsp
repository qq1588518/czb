<%@ page contentType="text/html;charset=GBK"%>
<HTML>
    <HEAD>
        <TITLE>���߱༭�� - ȫ���༭</TITLE>
        <META http-equiv=Content-Type content="text/html; charset=GBK">
        <style type="text/css">
            body {margin: 0px; border: 0px; background-color: buttonface; }
        </style>
    </HEAD>
    <BODY scroll="no" onunload="Minimize()">
        <input type="hidden" id="ContentFullScreen" name="ContentFullScreen" value="">
        
        <script language=javascript>

// BEGIN: URLParams holds all URL passed parameters (like ?Param1=Value1&Param2=Value2)
var URLParams = new Object() ;

var aParams = document.location.search.substr(1).split('&') ;
for (i=0 ; i < aParams.length ; i++)
{
	var aParam = aParams[i].split('=') ;
	URLParams[aParam[0]] = aParam[1] ;
}
// END: URLParams

ContentFullScreen.value = opener.getHTML(); 
document.write('<iframe ID="EditorFullScreen" src="../edit.jsp?id=ContentFullScreen&fullscreen=1&style='+URLParams['style']+'" frameborder="0" scrolling=no width="100%" HEIGHT="100%"></iframe>');

function Minimize() {
	opener.setHTML(EditorFullScreen.getHTML());
	self.close();
}

        </script>
        
    </BODY>
</HTML>
