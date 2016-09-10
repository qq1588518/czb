package com.ztenc.oa.proj.web.login;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.jms.Session;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.RedirectView;

import com.ztenc.oa.proj.json.JSONArray;
import com.ztenc.oa.proj.json.JSONObject;
import com.ztenc.oa.proj.service.login.LoginService;

public class LoginController extends MultiActionController {

	private LoginService loginService;
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	/*
	 *
	 * */
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RedirectView view;
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session =request.getSession(false);
		if(session==null){
			session = request.getSession();
		}
		Map model = new HashMap();
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String test = request.getParameter("test");
		String validCode = request.getParameter("validCode");
		System.out.println(userName);
		String result = "";
		String warning = "";
		String ValidCode = (String)session.getAttribute("validCode");
		System.out.println("test=="+test); 
		System.out.println("valid=="+validCode);
		System.out.println("validCode=="+ValidCode);
		if(ValidCode!=null){
			if(ValidCode.equals(validCode)){
				System.out.println("ValidCode=="+ValidCode);
				result = loginService.login(userName, password);
				System.out.println("result="+result);
				if(!result.equals("")){
					session.setAttribute("username", result);
					view = new RedirectView(request.getContextPath()+"/main.jsp");
				}else{
					warning = "用户名或密码错误";
					model.put("warning", warning);
					view = new RedirectView(request.getContextPath()+"/login.jsp");
					return new ModelAndView(view,model);
				}
			}else{
				warning = "您输入的验证码有误";
				model.put("warning", warning);
				view = new RedirectView(request.getContextPath()+"/login.jsp");
				return new ModelAndView(view,model);
			}
		}else{
			view = new RedirectView(request.getContextPath()+"/login.jsp");
		}
		return new ModelAndView(view,model);
	}
	
	/*
	 *
	 * */
	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RedirectView view;
		response.setContentType("text/html;charset=utf-8");
		HttpSession session =request.getSession(false);
		if(session==null){
			session = request.getSession();
		}
		session.removeAttribute("username");
		view = new RedirectView(request.getContextPath()+"/login.jsp");
		return new ModelAndView(view);
	}
	
	
	
	
	public ModelAndView getcount(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RedirectView view;
		response.setContentType("text/html;charset=utf-8");
		HttpSession session =request.getSession(false);
		Properties settings = new Properties();
		String model = request.getParameter("model");
		//modify by Tim 20110913 managess -> ssczb
		String countFilePath = getServletContext().getContext("/ssczb").getRealPath("/WEB-INF/count.txt");
		JSONObject obj = new JSONObject();
		JSONObject m_obj = new JSONObject(model);
		JSONArray m_arr = m_obj.getJSONArray("model");
		JSONObject count_obj = new JSONObject();
		String rtn = "";
		try {
			FileInputStream fis = new FileInputStream(countFilePath);
			settings.load(fis);
			fis.close();
		} catch (Exception e) {

		}
		String count = "0";
		try {
			for(int i=0;i<m_arr.length();i++){
				count = settings.getProperty(m_arr.getString(i));
				if (count == null) {
					count = "0";
				}
				count_obj.put(m_arr.getString(i), count);
				
			}
			
			obj.put("rs",count_obj);
			StringBuilder sb = new StringBuilder();
			rtn = sb.append(obj).toString();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		response.getWriter().print(rtn);
		response.getWriter().close();
		return null;
	}
	public ModelAndView getVPDNAddr(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RedirectView view;
		response.setContentType("text/html;charset=utf-8");
		HttpSession session =request.getSession(false);
		Properties settings = new Properties();
		//modify by Tim 20110913 managess -> ssczb
		String countFilePath = getServletContext().getContext("/ssczb").getRealPath("/WEB-INF/vpdn.txt");
		JSONObject obj = new JSONObject();
		JSONObject count_obj = new JSONObject();
		String rtn = "";
		try {
			FileInputStream fis = new FileInputStream(countFilePath);
			settings.load(fis);
			fis.close();
		} catch (Exception e) {

		}
		String count = "0";
		try {
			
				String verifyurl = settings.getProperty("verifyurl");
				String gourl = settings.getProperty("gourl");
				if (count == null) {
					count = "0";
				}
				count_obj.put("verifyurl", verifyurl);
				count_obj.put("gourl", gourl);
				
			
			obj.put("rs",count_obj);
			StringBuilder sb = new StringBuilder();
			rtn = sb.append(obj).toString();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		response.getWriter().print(rtn);
		response.getWriter().close();
		return null;
	}
	public ModelAndView saveVPDNAddr(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RedirectView view;
		response.setContentType("text/html;charset=utf-8");
		HttpSession session =request.getSession(false);
		Properties settings = new Properties();
		//modify by Tim 20110913 managess -> ssczb
		String countFilePath = getServletContext().getContext("/ssczb").getRealPath("/WEB-INF/vpdn.txt");
		String verifyurl = request.getParameter("verifyurl");
		//String gourl = request.getParameter("gourl");
		JSONArray arr = new JSONArray();
		System.out.println("verifyurl=="+verifyurl);
		arr.put(verifyurl);
		//arr.put(gourl);
		JSONObject obj = new JSONObject();
		JSONObject count_obj = new JSONObject();
		String rtn = "";
		boolean flag = false;
		flag = loginService.setVPDNAddr(countFilePath, arr);
		if(flag){
			rtn = "{flag:true}";
		}else{
			rtn = "{flag:false}";
		}
		response.getWriter().print(rtn);
		response.getWriter().close();
		return null;
	}
	
}
