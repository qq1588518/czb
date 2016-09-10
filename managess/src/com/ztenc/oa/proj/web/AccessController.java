package com.ztenc.oa.proj.web;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.*;
public class AccessController implements Filter {

	public void destroy() {

	}
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
//		try {
//			BufferedReader buf_reader = ((HttpServletRequest) request).getReader();
//			String temp_str = "";
//			StringBuffer str_xml = new StringBuffer();
//			do {
//				temp_str = buf_reader.readLine();
//				if (temp_str != null) {
//					str_xml.append(temp_str);
//				}
//			} while (temp_str != null);
//			System.out.println("str_xml in managess=="+str_xml.toString());
//		}catch(Exception e){
//			
//		}
		String servletPath = ((HttpServletRequest) request).getServletPath();
		Object obj = ((HttpServletRequest) request).getSession().getAttribute(
				"username");
		String request_url=((HttpServletRequest)request).getRequestURI();
		String servlet_path=((HttpServletRequest)request).getServletPath();
		String scheme=((HttpServletRequest)request).getScheme();
		String server_name=((HttpServletRequest)request).getServerName();
		int server_port=((HttpServletRequest)request).getServerPort();
		int at_position=request_url.indexOf(servlet_path);
		String current_url;
		if(at_position== -1){
		    int len=request_url.length();
		    current_url=request_url.substring(0,len -1);
		}
		else{
		    current_url=request_url.substring(0,at_position);
		}
		String uri=scheme+"://"+server_name+":"+server_port+current_url+"/login.jsp";
		
		if((servletPath.indexOf(".jsp")!=-1)  || (servletPath.indexOf(".htm")!=-1)){
			if(!(servletPath.equals("/login.jsp") || servletPath.equals("/login_jump.jsp") || servletPath.equals("/Image.htm") || servletPath.equals("/login.htm") || servletPath.equals("/editor/showImg.jsp"))){
				if(servletPath.equals("/selectcity.jsp") || servletPath.equals("/selectcity2.jsp") || servletPath.equals("/selectcitybyquery.jsp.jsp") || servletPath.equals("/selectcitybyquery2.jsp")){
					//chain.doFilter(request, response);
					//return;
					if (obj == null) {
						response.getWriter().write("<script language=\"javascript\">window.dialogArguments.top.location.href=\""+uri+"\";window.close();</script>");
						return;
					}else{
						((HttpServletRequest) request).getSession().setAttribute("loginStatus", "1");
						chain.doFilter(request, response);
						return;
					}
				}
				if(servletPath.indexOf(".htm")!=-1){
					if(obj==null){
						
						//response.getWriter().write("{flag:false,timeout:true}");
						response.getWriter().write("<script language=\"javascript\">top.location.href=\""+uri+"\";</script>");
						return;
						
					}else{
						((HttpServletRequest) request).getSession().setAttribute("loginStatus", "1");
						chain.doFilter(request, response);
						return;
					}
				}else{
					if (obj == null) {
						response.getWriter().write("<script language=\"javascript\">top.location.href=\""+uri+"\";</script>");
						return;
					
					}else{
						((HttpServletRequest) request).getSession().setAttribute("loginStatus", "1");
						chain.doFilter(request, response);
						return;
					}
				}
			}else{
				chain.doFilter(request, response);
				return;
			}
		}else{
			chain.doFilter(request, response);
			return;
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
