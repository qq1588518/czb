<%@page contentType="text/html;charset=GBK"%><%@page import="java.io.*,java.sql.*,basic.base.test.*,java.util.*"%>
<%@include file="/WEB-INF/jspf/pub_dburi.jspf" %>
<%@include file="/WEB-INF/jspf/pub_uri.jspf" %>
<%String picaddr=request.getParameter("picaddr")==null?"":request.getParameter("picaddr");
	String file = ""; 
	int maxSize = 2 * 1024 * 1024;
%>
<%
try{
    picaddr=request.getParameter("picaddr")==null?"":request.getParameter("picaddr");
    //CRes res=new CRes();
    //int res_count=res.open(record_no);
   // if(res_count>0){
   /*Connection conn =null;
   ResultSet rs = null;
   	Class.forName(db_driver);
	String url = dburl;
	conn = DriverManager.getConnection(url,db_user,db_pass);
   	PreparedStatement ps = conn.prepareStatement("select recordno,pic_addr from Pic_resource where recordno='"+record_no+"'");
   	rs = ps.executeQuery();
   	String saveDirectory = this.getServletContext().getRealPath("/");
   	String res_type="";
   	while(rs.next()){
               file = rs.getString(2);
               System.out.println("ttt="+file);
               //response.getWriter().print(uri+file);
     }
     File files = new File(saveDirectory+file);
  	 FileInputStream fileInput = new FileInputStream(files);
     byte[] in_byte=new byte[2*1024*1024];
        int test =0;
       while((test=fileInput.read(in_byte))!=-1){
        }
       if(in_byte!=null){
            response.getOutputStream().write(in_byte);
            response.getOutputStream().flush();
            response.getOutputStream().close();
       }
        fileInput.close();
        in_byte=null;*/
    //}
    System.out.println("uri=="+uri);
    System.out.println("picaddr=="+picaddr);
}catch(Exception e){
    System.out.println("e=="+e.getMessage());
}
%>
<img src="<%=c_url+picaddr%>">
