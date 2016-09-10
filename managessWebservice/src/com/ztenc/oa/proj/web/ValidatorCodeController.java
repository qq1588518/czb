package com.ztenc.oa.proj.web;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class ValidatorCodeController extends AbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("usercode");
		PrintWriter out = response.getWriter();
		String usercode = request.getParameter("code");
		
		String realcode = (String)request.getSession().getAttribute("code");
		if(usercode.equals(realcode)){
			out.print("ok");
		}else{
			out.print("error");
		}
		//System.out.println("usercode:"+usercode);
		//System.out.println("realcode:"+realcode);
		out.close();
		return null;
	}

}
