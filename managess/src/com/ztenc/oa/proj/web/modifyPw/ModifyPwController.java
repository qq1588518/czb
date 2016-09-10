package com.ztenc.oa.proj.web.modifyPw;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Session;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.ztenc.oa.proj.json.JSONObject;
import com.ztenc.oa.proj.service.modifyPw.ModifyPwService;

public class ModifyPwController extends MultiActionController {

	private ModifyPwService modifyPwService;
	public void setModifyPwService(ModifyPwService modifyPwService) {
		this.modifyPwService = modifyPwService;
	}
	
	/*
	 *
	 * */
	public ModelAndView modifyPw(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		HttpSession session =request.getSession(false);
		if(session==null){
			session = request.getSession();
		}
		String name = (String)session.getAttribute("username");
		String flag = request.getParameter("flag");
		String result = "";
		if(flag !=null && flag.equals("checkPassword")){
			String oldPassword = request.getParameter("oldPassword");
			result = modifyPwService.checkPassword(name,oldPassword);
			if(!result.equals("")){
				response.getWriter().print("{flag:true}");
				response.getWriter().close();
			}else{
				response.getWriter().print("{flag:false}");
				response.getWriter().close();
			}
		}else if(flag !=null && flag.equals("modifyPassword")){
			String verifypassword = request.getParameter("verifypassword");
			result = String.valueOf(modifyPwService.modifyPassword(verifypassword,name));
			if(result.equals("1")){
				response.getWriter().print("{flag:true}");
				response.getWriter().close();
			}else{
				response.getWriter().print("{flag:false}");
				response.getWriter().close();
			}
		}else{
			response.getWriter().print("{flag:false}");
		}
		return null;
	}
	
	/*
	 *
	 * */
	public ModelAndView getName(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session =request.getSession(false);
		if(session==null){
			session = request.getSession();
		}
		String name = (String)session.getAttribute("username");
		Map model = new HashMap();
		model.put("userName", name);
		request.setAttribute("userInfo", model);
		return new ModelAndView("modifyPw",model);
	}
}
