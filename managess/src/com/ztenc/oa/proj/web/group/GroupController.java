package com.ztenc.oa.proj.web.group;

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
import com.ztenc.oa.proj.service.group.GroupService;
import com.ztenc.oa.proj.service.member.*;
import com.ztenc.oa.proj.util.pagination.PaginationService;
import com.ztenc.oa.proj.util.pagination.PcurrentDataDaoService;

public class GroupController extends MultiActionController {
	
	private GroupService groupService;
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
	String result = "";
	public ModelAndView getUrl(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		Map model = new HashMap();
		return new ModelAndView("group/groupmanage",model);
	}
	
	public ModelAndView groupSearch(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		int index = Integer.valueOf(request.getParameter("index"));
		int length = Integer.valueOf(request.getParameter("length"));
		
		String searchvalue = request.getParameter("searchvalue");
		if(searchvalue!=null){
			JSONObject jsonobj = new JSONObject(searchvalue);
			if(!jsonobj.has("groupname")){
				jsonobj.put("groupname", "");
			}
			//result = memberService.MemberSearchByKey(index, length,jsonobj.getString("membername"));
		}else{
			result = groupService.GroupSearch(index, length);
		}
		
		
		
		response.getWriter().print(result);
		response.getWriter().close();
		return null;
	}
	
	
	public ModelAndView deleteGroup(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String info = request.getParameter("fbid");
		result = groupService.deleteGroup(info);
		response.getWriter().print(result);
		response.getWriter().close();
		return null;
	}

	public ModelAndView modifyGroup(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session =request.getSession();
		Map model = new HashMap();
		String obj = new String(request.getParameter("obj").getBytes("iso8859-1"),"utf-8");
		if(!obj.equals("null")){
			String [] object = obj.split(",");
			String groupno = object[0];
			String groupname = object[1];
			String memo = object[2];
			model.put("groupno", groupno);
			model.put("groupname", groupname);
			model.put("memo", memo);
			
		}
		return new ModelAndView("group/modifyGroup",model);
	}
	
	public ModelAndView addGroup(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session =request.getSession();
		Map model = new HashMap();
		return new ModelAndView("group/addGroup",model);
	}
	
	
	public ModelAndView saveGroup(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session =request.getSession();
		String groupno = request.getParameter("groupno");
		String groupname = request.getParameter("groupname");
		String memo = request.getParameter("memo");
		if(groupno!=null){
			result = groupService.modifyGroup(groupno, groupname, memo);
			
		}else{
			result = groupService.addGroup(groupname, memo);
		}
		response.getWriter().print(result);
		response.getWriter().close();
		return null;
	}
}
