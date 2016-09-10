<%@page contentType="image/jpeg" import="java.awt.*,java.awt.image.*,java.util.*,javax.imageio.*,com.ztenc.oa.proj.function.CreateValidImage"%>
<%
CreateValidImage c=new CreateValidImage();

String content=c.getContent();
BufferedImage image=c.getBufferedImage(content);
session.setAttribute("code", content);
c.response(response,image);
out.clear();
out = pageContext.pushBody();
%>
