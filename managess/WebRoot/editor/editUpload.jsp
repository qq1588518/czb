<%@page contentType="text/html;charset=GBK"%>
<%@page import="java.sql.*,java.io.*,java.util.*,basic.util.CUtil,basic.base.test.*;"%>
<%@include file="/WEB-INF/jspf/pub_dburi.jspf" %>
<%

String update_success_flag="false";
String proAddress ="";
if(request.getMethod().equals("POST")){
    try{
        int maxSize = 2 * 1024 * 1024;
        //CDBContainer cDBContainer = new CDBContainer();        
        /** �ϴ����� ��ȡ��part�ж����ļ��������� */
        Part part = null;
        HashMap paramMap=new HashMap();
        MultipartParser mrequest = new MultipartParser(request, maxSize);
        mrequest.setEncoding("utf-8");
        InputStream fileparts=null;
        //modify by Tim 20110918 managess -> ssczb
        String saveDirectory = this.getServletContext().getContext("/ssczb").getRealPath("/");
        
        File dir;
        String filepro ="upload/images/";
		saveDirectory =saveDirectory+filepro;
		dir = new File(saveDirectory);
    	dir.mkdir();
    	String fileName = "";
    	
        while ((part = mrequest.readNextPart()) != null){
            if(part.isParam()){
                ParamPart paramPart = (ParamPart) part;
                paramMap.put(paramPart.getName(), paramPart.getStringValue());
            }
            if (part.isFile()) {
                /** ת��Ϊ filePart*/
                FilePart filePart = (FilePart) part;
	            fileName = filePart.getFileName();  //�õ��ϴ�ͼƬ��ͼƬ����
	            //System.out.println("fileName:"+fileName);
	            if (fileName != null) {  //�õ��ϴ�ͼƬ��ͼƬ����
	                String filename = filePart.writeTo(dir,fileName);  //��ͼƬ�����ָ����Ŀ¼��
	                proAddress = filepro+filename;
	            } else {
	            }
            }
        }
        //CRes res=new CRes();
        String record_no=(String)paramMap.get("recordno");
        Connection conn = null;
		ResultSet rs =null;
		Class.forName(db_driver);
		String url = dburl;
		conn = DriverManager.getConnection(url,db_user,db_pass);
		PreparedStatement ps = conn.prepareStatement("insert into Pic_resource(recordno,pic_addr) values (?,?)");
		record_no = request.getParameter("recordno");
		ps.setString(1,record_no);
        ps.setString(2,proAddress);
        int stat = ps.executeUpdate();
        if(stat==1){
             response.getWriter().print("<script language=javascript>history.go(-1)</script>");
             update_success_flag="true";
        }else{
        
        	response.getWriter().print("<script language=javascript>history.go(-1)</script>");
        }
    }catch(Exception e){
        out.print(e.getMessage());
    }
}
%>
<HTML><HEAD><TITLE>�ļ��ϴ�</TITLE>
        <META http-equiv=Content-Type content="text/html; charset=gb2312">
        <STYLE type=text/css>BODY {
               FONT: 9pt "����", Verdana, Arial, Helvetica, sans-serif
               }
               A {
               FONT: 9pt "����", Verdana, Arial, Helvetica, sans-serif
               }
               TABLE {
               FONT: 9pt "����", Verdana, Arial, Helvetica, sans-serif
               }
               DIV {
               FONT: 9pt "����", Verdana, Arial, Helvetica, sans-serif
               }
               SPAN {
               FONT: 9pt "����", Verdana, Arial, Helvetica, sans-serif
               }
               TD {
               FONT: 9pt "����", Verdana, Arial, Helvetica, sans-serif
               }
               TH {
               FONT: 9pt "����", Verdana, Arial, Helvetica, sans-serif
               }
               INPUT {
               FONT: 9pt "����", Verdana, Arial, Helvetica, sans-serif
               }
               SELECT {
               FONT: 9pt "����", Verdana, Arial, Helvetica, sans-serif
               }
               BODY {
               PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-TOP: 0px
               }
               </STYLE>
        
        <SCRIPT language=JavaScript src="dialog/dialog.js"></SCRIPT>
        
    </HEAD>
    <BODY bgColor=menu>
        <FORM name="myform" action="editUpload.jsp?recordno=<%=request.getParameter("recordno")%>" method="post" encType="multipart/form-data">
            <INPUT style="WIDTH: 100%" type=file onchange="originalfile.value=this.value" size=1 name=uploadfile>
            <INPUT type=hidden name=originalfile> 
            <input type="hidden" name="recordno" id="recordno" value="">
            <input type="hidden" name="update_success_flag" id="update_success_flag" value="<%=update_success_flag%>">
            <input type="hidden" name="pic_addr" id="pic_addr" value="<%=proAddress%>">
        </FORM>
        
        <SCRIPT language=javascript>
var sAllowExt = "GIF|JPG|JPEG|BMP";
// ����ϴ���
function CheckUploadForm() {
	if (!IsExt(document.myform.uploadfile.value,sAllowExt)){
		parent.UploadError("��ʾ��\n\n��ѡ��һ����Ч���ļ���\n֧�ֵĸ�ʽ�У�"+sAllowExt+"����");
		return false;
	}
	return true;
}

// �ύ�¼��������
var oForm = document.myform ;
oForm.attachEvent("onsubmit", CheckUploadForm) ;

if (! oForm.submitUpload) oForm.submitUpload = new Array() ;
oForm.submitUpload[oForm.submitUpload.length] = CheckUploadForm ;
if (! oForm.originalSubmit) {
	oForm.originalSubmit = oForm.submit ;
	oForm.submit = function() {
		if (this.submitUpload) {
			for (var i = 0 ; i < this.submitUpload.length ; i++) { 
				this.submitUpload[i]() ;
			}
		}
		this.originalSubmit() ;
	}
}

// �ϴ�����װ�����
try {
	parent.UploadLoaded();
}
catch(e){
}
        </SCRIPT>
        
</BODY></HTML>
