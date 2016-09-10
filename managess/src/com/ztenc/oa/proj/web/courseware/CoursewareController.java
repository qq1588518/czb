package com.ztenc.oa.proj.web.courseware;

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
import com.ztenc.oa.proj.service.courseware.CoursewareService;
import com.ztenc.oa.proj.service.member.*;
import com.ztenc.oa.proj.util.pagination.PaginationService;
import com.ztenc.oa.proj.util.pagination.PcurrentDataDaoService;

import com.ztenc.oa.proj.bean.Courseware;

public class CoursewareController extends MultiActionController {
	
	private CoursewareService coursewareService;
	
	public void setCoursewareService(CoursewareService coursewareService) {
		this.coursewareService = coursewareService;
	}
	
	String result = "";
	
	public ModelAndView getUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		List list3 = coursewareService.getSubject();
		Map model = new HashMap();
		model.put("sub_pro", list3);
		return new ModelAndView("courseware/coursewaremanage", model);
	}
	
	public ModelAndView coursewareSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		int index = Integer.valueOf(request.getParameter("index"));
		int length = Integer.valueOf(request.getParameter("length"));
		String searchvalue = request.getParameter("searchvalue");
		String sub_protype = request.getParameter("sub_protype");
		result = coursewareService.coursewareSearchByKey(index, length, searchvalue, sub_protype);
		response.getWriter().print(result);
		response.getWriter().close();
		return null;
	}
	
	public ModelAndView deleteCourseware(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String info = request.getParameter("fbid");
		String url = request.getParameter("url");
		String img = request.getParameter("img");
		//modify by Tim 20110915 ssczb -> managess
		result = coursewareService.deleteCourseware(info, url, img, this.getServletContext().getContext("/ssczb").getRealPath("/"));
		response.getWriter().print(result);
		response.getWriter().close();
		return null;
	}
	
	public ModelAndView modifyCourseware(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		Map model = new HashMap();
		String obj = new String(request.getParameter("obj").getBytes("iso8859-1"), "utf-8");
		if (!obj.equals("null")) {
			String[] object = obj.split(",");
			String coursewareno = object[0];
			String coursewarename = object[1];
			String url = object[2];
			String otherurl = object[3];
			String titleimg = object[4];
			String subjectno = object[5];
			model.put("coursewareno", coursewareno);
			model.put("coursewarename", coursewarename);
			model.put("otherurl", otherurl);
			model.put("titleimg", titleimg);
			model.put("subjectno", subjectno);
			model.put("url", url);
		}
		List list = coursewareService.getSubject();
		model.put("subject", list);
		return new ModelAndView("courseware/addCourseware", model);
	}
	
	public ModelAndView addCourseware(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		Map model = new HashMap();
		List list = coursewareService.getSubject();
		model.put("subject", list);
		return new ModelAndView("courseware/addCourseware", model);
	}
	
	public ModelAndView saveCourseware(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			HttpSession session = request.getSession();
			String coursewareno = "";
			String coursewarename = "";
			String subjectno = "";
			String url = "";
			String pic = "";
			String otherurl = "";
			String titleimg = "";
			String fileName = "";
			String read_pic = "";
			String filepro = "upload/courseware/images/";
			String filepro2 = "upload/courseware/file/";
			File dir;
			String saveDirectory1 = "";
			
			//modify by Tim 20110915 ssczb -> managess
			String saveDirectory2 = this.getServletContext().getContext("/ssczb").getRealPath("/");
			String saveDirectory = this.getServletContext().getContext("/ssczb").getRealPath("/");
			saveDirectory1 = saveDirectory;
			
			int maxPostSize = 2 * 5 * 1024 * 1024;
			MultipartParser mp = new MultipartParser(request, maxPostSize); // 10MB
			mp.setEncoding("utf-8");
			Part part;
			saveDirectory = saveDirectory + filepro;
			saveDirectory2 = saveDirectory2 + filepro2;
			dir = new File(saveDirectory);
			File dir2 = new File(saveDirectory2);
			dir.mkdirs();
			dir2.mkdirs();
			while ((part = mp.readNextPart()) != null) {
				String name = part.getName(); // 得到form标单中控件的名称，包括按钮等。
				// System.out.println(">> " + name + "<br>"); //输出“文件名称”。
				if (part.isParam()) { // 判断是不是“文本域”控件，如果不是“文本域”，则执行这句话。
					ParamPart paramPart = (ParamPart) part;
					String value = paramPart.getStringValue(); // 得到其值
					System.out.println("param: name=" + name + "; value=" + value + "<br>");
					if (name.equals("coursewarename")) {
						coursewarename = value;
					} else if (name.equals("otherurl")) {
						otherurl = value;
					} else if (name.equals("url")) {
						pic = value;
					} else if (name.equals("titleimg")) {
						titleimg = value;
					} else if (name.equals("coursewareno")) {
						coursewareno = value;
						// modify by Tim 20110914
						if (!coursewareno.equals("")) {
							List add = coursewareService.getProductInfo(coursewareno);
							String img_arr = ((Courseware) add.toArray()[0]).getTitleimg();
							String total_filenames = "";
							//modify by Tim 20110915 ssczb -> managess
							String saveDerectory3 = this.getServletContext().getContext("/ssczb").getRealPath("/");
							total_filenames = img_arr.substring(img_arr.lastIndexOf("/") + 1, img_arr.length());
							coursewareService.deleteFromImages(saveDerectory3, total_filenames);
							
							String apk_arr = ((Courseware) add.toArray()[0]).getUrl();
							total_filenames = apk_arr.substring(apk_arr.lastIndexOf("/") + 1, apk_arr.length());
							coursewareService.deleteFromApk(saveDerectory3, total_filenames);
						}
					} else if (name.equals("subjectno")) {
						subjectno = value;
					}
				} else if (part.isFile()) { // 假如是文本域
					FilePart filePart = (FilePart) part;
					fileName = filePart.getFileName(); // 得到上传图片的图片名称
					// System.out.println("fileName:"+fileName);
					String _name = filePart.getName();
					if (fileName != null) { // 得到上传图片的图片名称
						if (filePart.getName().equals("courseware")) {
							boolean flag = false;
							if (!coursewarename.equals("")) {
								int pos = fileName.lastIndexOf(".");
								String lastname = fileName.substring(pos, fileName.length());
								fileName = coursewarename + lastname;
								flag = false;
							}
							String filename = filePart.writeTo(dir2, fileName, flag); // 把图片保存的指定的目录中
							url = filepro2 + filename;
						} else if (filePart.getName().equals("read_pic")) {
							String filename = filePart.writeTo(dir, fileName); // 把图片保存的指定的目录中
							read_pic = filepro + filename;
						}
						
					} else {
					}
				}
			}
			
			if (!coursewareno.equals("")) {
				
				if (url.equals("")) {
					url = pic;
				}
				if (read_pic.equals("")) {
					read_pic = titleimg;
				}
				result = coursewareService.modifyCourseware(coursewareno, coursewarename, subjectno, url, otherurl, read_pic);
				JSONObject obj = new JSONObject(result);
				if (obj.getString("flag").equals("1")) {
					
					response.getWriter().print("<script language=javascript>alert('课件修改成功！');parent.restore();</script>");
					
				} else {
					response.getWriter().print("<script language=javascript>alert('课件修改失败！');history.go(-1);</script>");
					
				}
				
			} else {
				result = coursewareService.addCourseware(coursewarename, subjectno, url, otherurl, read_pic);
				JSONObject obj = new JSONObject(result);
				
				if (obj.getString("flag").equals("1")) {
					
					response.getWriter().print("<script language=javascript>alert('课件添加成功！');parent.restore();</script>");
					
				} else {
					response.getWriter().print("<script language=javascript>alert('课件添加失败！');history.go(-1);</script>");
					
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			try {
				response.getWriter().print("<script language=javascript>alert('" + e.getMessage() + "');history.go(-1)</script>");
			} catch (IOException e1) {
			}
		}
		return null;
	}
}
