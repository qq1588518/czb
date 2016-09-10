package com.ztenc.oa.proj.web.subject;

import java.io.File;
import java.io.IOException;
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

import basic.base.test.FilePart;
import basic.base.test.MultipartParser;
import basic.base.test.ParamPart;
import basic.base.test.Part;

import com.ztenc.oa.proj.json.JSONObject;
import com.ztenc.oa.proj.service.group.GroupService;
import com.ztenc.oa.proj.service.member.*;
import com.ztenc.oa.proj.service.subject.SubjectService;
import com.ztenc.oa.proj.util.pagination.PaginationService;
import com.ztenc.oa.proj.util.pagination.PcurrentDataDaoService;

public class SubjectController extends MultiActionController {
	
	private SubjectService subjectService;
	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}
	String result = "";
	public ModelAndView getUrl(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		Map model = new HashMap();
		return new ModelAndView("subject/subjectmanage",model);
	}
	
	public ModelAndView subjectSearch(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		int index = Integer.valueOf(request.getParameter("index"));
		int length = Integer.valueOf(request.getParameter("length"));
		
//		String searchvalue = request.getParameter("searchvalue");
//		if(searchvalue!=null){
//			JSONObject jsonobj = new JSONObject(searchvalue);
//			if(!jsonobj.has("subjectname")){
//				jsonobj.put("subjectname", "");
//			}
//			//result = memberService.MemberSearchByKey(index, length,jsonobj.getString("membername"));
//		}else{
			result = subjectService.SubjectSearch(index, length);
//		}
		
		
		
		response.getWriter().print(result);
		response.getWriter().close();
		return null;
	}
	
	public ModelAndView deleteSubject(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String info = request.getParameter("fbid");
		result = subjectService.deleteSubject(info);
		response.getWriter().print(result);
		response.getWriter().close();
		return null;
	}

	public ModelAndView modifySubject(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session =request.getSession();
		Map model = new HashMap();
		String obj = new String(request.getParameter("obj").getBytes("iso8859-1"),"utf-8");
		if(!obj.equals("null")){
			String [] object = obj.split(",");
			String subjectno = object[0];
			String subjectname = object[1];
			String imgurl = object[2];
			String level = object[3];
			model.put("subjectno", subjectno);
			model.put("subjectname", subjectname);
			model.put("imgurl", imgurl);
			model.put("level",level);
		}
		return new ModelAndView("subject/addSubject",model);
	}
	
	public ModelAndView addSubject(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session =request.getSession();
		Map model = new HashMap();
		return new ModelAndView("subject/addSubject",model);
	}
	
	
	public ModelAndView saveSubject(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session =request.getSession();
		
		File dir;
		String saveDirectory1="";
		//modify by Tim 20110915 ssczb -> managess
		String saveDirectory2= this.getServletContext().getContext("ssczb").getRealPath("/");
	    String saveDirectory = this.getServletContext().getContext("ssczb").getRealPath("/");
	    saveDirectory1 = saveDirectory;
	    int maxPostSize = 5 * 1024;
	    MultipartParser mp = new MultipartParser(request, maxPostSize); // 10MB
	    mp.setEncoding("utf-8");
	    Part part;
	    String subjectname="";
	    String subjectno = "";
	    String imgurl ="";
		String filepro ="upload/subject/images/";
		String fileName="";
		String pic = "";
		String level = "";
		saveDirectory =saveDirectory+filepro;
		dir = new File(saveDirectory);
	    dir.mkdirs();
	    while ((part = mp.readNextPart()) != null) {
	        String name = part.getName(); //得到form标单中控件的名称，包括按钮等。
	        //System.out.println(">> " + name + "<br>");  //输出“文件名称”。
	        if (part.isParam()) {  //判断是不是“文本域”控件，如果不是“文本域”，则执行这句话。
	            ParamPart paramPart = (ParamPart) part;
	            String value = paramPart.getStringValue();  //得到其值
	            System.out.println("param: name=" + name + "; value=" + value + "<br>");
	            if(name.equals("subjectname")){
	            	subjectname = value;
	            }else if(name.equals("imgurl")){
	            	pic = value;
	            }else if(name.equals("subjectno")){
	            	subjectno = value;
	            }else if(name.equals("level")){
	            	level = value;
	            }
	        } else if (part.isFile()) {   //假如是文本域
	            FilePart filePart = (FilePart) part;
	            fileName = filePart.getFileName();  //得到上传图片的图片名称
	            //System.out.println("fileName:"+fileName);
	            if (fileName != null) {  //得到上传图片的图片名称
	                String filename = filePart.writeTo(dir,fileName);  //把图片保存的指定的目录中
	                imgurl = filepro+filename;
	            } else {
	            }
	        }
	    }
		if(!subjectno.equals("")){
			
			if(imgurl.equals("")){
				imgurl = pic;
			}
			result = subjectService.modifySubject(subjectno, subjectname, imgurl,level);
			System.out.print("in modify result=="+result);
			JSONObject obj = new JSONObject(result);
			if(obj.getString("flag").equals("1")){
				
				response.getWriter().print("<script language=javascript>alert('课件栏目修改成功！');parent.restore();</script>");
			
			}else{
				response.getWriter().print("<script language=javascript>alert('课件栏目修改失败！');history.go(-1);</script>");
				
			}
		}else{
			
			
			result = subjectService.addSubject(subjectname, imgurl,level);
			JSONObject obj = new JSONObject(result);
			System.out.print("in add result=="+result);
			if(obj.getString("flag").equals("1")){
				
				response.getWriter().print("<script language=javascript>alert('课件栏目添加成功！');parent.restore();</script>");
			
			}else{
				response.getWriter().print("<script language=javascript>alert('课件栏目添加失败！');history.go(-1);</script>");
			}
		}
		response.getWriter().print(result);
		response.getWriter().close();
		}catch (Exception e){
			System.out.println(e);
			try {
				response.getWriter().print("<script language=javascript>alert('"+e.getMessage()+"');history.go(-1)</script>");
			} catch (IOException e1) {
				}
		}
		return null;
	}
}
