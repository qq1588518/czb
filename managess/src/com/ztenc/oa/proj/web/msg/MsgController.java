package com.ztenc.oa.proj.web.msg;

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
import com.ztenc.oa.proj.service.msg.MsgService;
import com.ztenc.oa.proj.util.pagination.PaginationService;
import com.ztenc.oa.proj.util.pagination.PcurrentDataDaoService;

public class MsgController extends MultiActionController {
	
	private MsgService msgService;
	public void setMsgService(MsgService msgService) {
		this.msgService = msgService;
	}
	String result = "";
	public ModelAndView getUrl(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		Map model = new HashMap();
		return new ModelAndView("msg/msg",model);
	}
	
	//留言板信息读取
	public ModelAndView msgSearch(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		int index = Integer.valueOf(request.getParameter("index"));
		int length = Integer.valueOf(request.getParameter("length"));
		String title = request.getParameter("title");
		System.out.println("title===="+title);
		result = msgService.msgSearch(index,length,title);
		response.getWriter().print(result);
		response.getWriter().close();
		return null;
	}
	
	//留言板信息回复
	public ModelAndView reMsg(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session =request.getSession();
		String fbid = request.getParameter("fbid");
		String sfhf = request.getParameter("sfhf");
		String replyID = request.getParameter("replyID");
		String replyCon = request.getParameter("replycon");
		String replyName = (String)session.getAttribute("username");
		String result1 = msgService.reMsg(fbid,sfhf,replyID,replyCon,replyName);
		System.out.println(result1);
		response.getWriter().print(result1);
		response.getWriter().close();
		return null;
	}
	
	
	//留言板信息删除
	public ModelAndView deleteMsg(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String info = request.getParameter("fbid");
		result = msgService.deleteMsg(info);
		response.getWriter().print(result);
		response.getWriter().close();
		return null;
	}
	
	
	//读取回复信息
	public ModelAndView replyed_msg(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String info = request.getParameter("fbid");
		result = msgService.replyedMsg(info);
		response.getWriter().print(result);
		response.getWriter().close();
		return null;
	}
	
	
	
	//留言板回复信息删除
	public ModelAndView deleteReplay(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String fbid = request.getParameter("fbid");
		String resid = request.getParameter("resId");
		result = msgService.deleteReplay(fbid,resid);
		response.getWriter().print(result);
		response.getWriter().close();
		return null;
	}
	
	
	//修改留言板信息回复
	public ModelAndView modifyreplay(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session =request.getSession();
		String id = request.getParameter("id");
		String replyCon = request.getParameter("replycon");
		String replyName = (String)session.getAttribute("username");
		result = msgService.modifyreplay(id,replyCon,replyName);
		response.getWriter().print(result);
		response.getWriter().close();
		return null;
	}
}
