<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page  import = " org.acegisecurity.ui.AbstractProcessingFilter "   %> 
<%@ page  import = " org.acegisecurity.ui.webapp.AuthenticationProcessingFilter "   %> 
<%@ page  import = " org.acegisecurity.AuthenticationException "   %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache" />
<META HTTP-EQUIV="Expires" CONTENT="0" />
<title>中国移动物流运营平台</title>
<script language="javascript">
function formsubmit(){
	if(event.keyCode == 13){
		checkValidatorCode();
	}
}
</script>
<script language="javascript" src="${ctx}/scripts/code.js"></script>
<link href="css/public.css" rel="stylesheet" type="text/css"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style>
  .Tborder {
  		border-style:none;
	    background-color: transparent;
	    border-bottom:solid 1px #00000;
	}


</style>
</HEAD>
<BODY>
<form name="login" action="j_acegi_security_check" method="post">

    
    
     

     
     
     
<div class="container">
	<div class="login1">
		<ul>
			<li style="width:50px;">用户名:</li>
			<li><input type='text' name="j_username" id='j_username' class="Tborder" maxlength='12' value=""  style="width:130px;" onKeyDown="formsubmit();"/></li>
			 <li><img src="images/button1.png" onclick="javaScript:checkValidatorCode()" style="cursor:hand;"/></li>

		</ul>
		<ul>
			<li style="clear:left;width:50px;">密&nbsp;&nbsp;码:</li>
			<li><input type='password' name="j_password" id='j_password' class="Tborder" maxlength='16' value="" style="width:130px;" onKeyDown="formsubmit();"/></li>
			<li><img src="images/button2.png" onclick='javascript:login.reset();' style="cursor:hand;"/></li>
		</ul>
	
		<ul>
			<li style="clear:left;width:50px;">验证码:</li>
			<li><input name='check' id='check' size=8 maxlength=6 type="text" value="" class="Tborder" style="width:60px;height:23px;" onKeyDown="formsubmit();"/></li>
			<li ><a href="javaScript:change()" class="STYLE1"><img  border="0" src="${ctx}/commons/img.jsp" alt="看不清,换一张" id="img"></a></li>
		</ul>
		 
		<ul>
	  	<li style="clear:left;">
	  	<font color ='red' >  <div id="valcode"><span>
      
	<% 
        String strError  =  request.getParameter( "failed" );
        
         if  ( null   !=  strError){ 
      %>
                               您的登陆失败,请重试。 <BR>
                               原因 <%String reason= ((AuthenticationException) session.getAttribute(AbstractProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY)).getMessage();
             if(reason.equalsIgnoreCase("Maximum sessions of {0} for this principal exceeded"))
             { reason="当前帐号已登陆！"; }
             else if (reason.equalsIgnoreCase("Bad credentials"))
             { reason="错误的用户名或密码！"; }
            
              else if (reason.startsWith("PreparedStatementCallback; "))
              { reason="数据库连接异常!";}
              else if (reason.equalsIgnoreCase("Could not get JDBC Connection; nested exception is java.sql.SQLException: Network error IOException: Connection refused: connect; nested exception is org.springframework.jdbc.CannotGetJdbcConnectionException: Could not get JDBC Connection; nested exception is java.sql.SQLException: Network error IOException: Connection refused: connect"))
             { reason="数据库连接异常!";}
             else if (reason.equalsIgnoreCase("Could not get JDBC Connection; nested exception is org.apache.commons.dbcp.SQLNestedException: Cannot create PoolableConnectionFactory (Network error IOException: Connection refused: connect); nested exception is org.springframework.jdbc.CannotGetJdbcConnectionException: Could not get JDBC Connection; nested exception is org.apache.commons.dbcp.SQLNestedException: Cannot create PoolableConnectionFactory (Network error IOException: Connection refused: connect)"))
             { reason="数据库连接异常!";}
             else if (reason.equalsIgnoreCase("Could not get JDBC Connection; nested exception is java.sql.SQLException: Network error IOException: No route to host: connect; nested exception is org.springframework.jdbc.CannotGetJdbcConnectionException: Could not get JDBC Connection; nested exception is java.sql.SQLException: Network error IOException: No route to host: connect"))
             { reason="网络连接异常!";}
              else if (reason.equalsIgnoreCase("Could not get JDBC Connection; nested exception is java.sql.SQLException: Network error IOException: Software caused connection abort: connect; nested exception is org.springframework.jdbc.CannotGetJdbcConnectionException: Could not get JDBC Connection; nested exception is java.sql.SQLException: Network error IOException: Software caused connection abort: connect"))
             { reason="网络连接异常!";}
             %>
                <%=reason %>
                    
                    <% 
          } 
       %>
       </span></div>
	   </font>
        </li>
	  </ul>
  </div>
</div>
</form>
</BODY>
</HTML>

