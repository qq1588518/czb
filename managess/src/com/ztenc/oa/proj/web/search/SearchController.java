package com.ztenc.oa.proj.web.search;

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

import com.ztenc.oa.proj.bean.Advertisment;
import com.ztenc.oa.proj.bean.SearchInfo;
import com.ztenc.oa.proj.json.JSONObject;
import com.ztenc.oa.proj.service.search.SearchService;
import com.ztenc.oa.proj.util.CUtil;
import com.ztenc.oa.proj.util.pagination.PaginationService;
import com.ztenc.oa.proj.util.pagination.PcurrentDataDaoService;

public class SearchController extends MultiActionController {
	
	private SearchService searchService;
	
	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}
	
	String result = "";
	
	/**
	 * 跳转到栏目管理
	 * 
	 * @param
	 * @return
	 */
	public ModelAndView getUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		Map model = new HashMap();
		return new ModelAndView("search/searchinfo", model);
	}
	
	/**
	 * 跳转到栏目添加修改
	 * 
	 * @param
	 * @return
	 */
	public ModelAndView addpro(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		Map model = new HashMap();
		String obj = new String(request.getParameter("obj").getBytes("iso8859-1"), "utf-8");
		if (!obj.equals("null")) {
			String[] object = obj.split(",");
			String id = object[0];
			String name = object[1];
			String pic = object[2];
			String uri = object[3];
			String colcom = object[4];
			model.put("id", id);
			model.put("name", name);
			model.put("pic", pic);
			if (uri.equals("0")) {
				uri = "";
				model.put("uri", uri);
			} else {
				model.put("uri", uri);
			}
			model.put("colcom", colcom);
		}
		List list = searchService.getpro();
		model.put("protype_clime", list);
		return new ModelAndView("search/addsearchinfo", model);
	}
	
	/**
	 * 添加、修改栏目信息
	 * 
	 * @param
	 * @return
	 */
	public ModelAndView saveProtype(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			
			String saveDirectory1 = "";
			// modify by Tim 20110907 ssczb -> managess
			String saveDirectory2 = this.getServletContext().getContext("/ssczb").getRealPath("/");
			File dir;
			String saveDirectory = this.getServletContext().getContext("/ssczb").getRealPath("/");
			saveDirectory1 = saveDirectory;
			int maxPostSize = 5 * 1024;
			MultipartParser mp = new MultipartParser(request, maxPostSize); // 10MB
			mp.setEncoding("utf-8");
			Part part;
			String proName = "";
			String colcom = "";
			String addr = "";
			String pic = "";
			String permission = "";
			String proId = "";
			String proAddress = "";
			String fileName = "";
			String filepro = "upload/search/images/";
			saveDirectory = saveDirectory + filepro;
			dir = new File(saveDirectory);
			dir.mkdirs();
			while ((part = mp.readNextPart()) != null) {
				String name = part.getName(); // 得到form标单中控件的名称，包括按钮等。
				if (part.isParam()) { // 判断是不是“文本域”控件，如果不是“文本域”，则执行这句话。
					ParamPart paramPart = (ParamPart) part;
					String value = paramPart.getStringValue(); // 得到其值
					System.out.println("param: name=" + name + "; value=" + value + "<br>");
					if (name.equals("name_product")) {
						proName = value;
					} else if (name.equals("select_protype")) {
						colcom = value;
					} else if (name.equals("pic_addr")) {
						pic = value;
					} else if (name.equals("addr")) {
						addr = value;
					} else if (name.equals("cata_id")) {
						proId = value;
						// modify by Tim 20110914 ssczb -> managess
						if (!proId.equals("")) {
							List add = searchService.getProductInfo(proId);
							String img_arr = ((SearchInfo) add.toArray()[0]).getPic();
							String total_filenames = "";
							String saveDerectory3 = this.getServletContext().getContext("/ssczb").getRealPath("/");
							total_filenames = img_arr.substring(img_arr.lastIndexOf("/") + 1, img_arr.length());
							searchService.deleteFromImages(saveDerectory3, total_filenames);
						}
					}
				} else if (part.isFile()) { // 假如是文本域
					FilePart filePart = (FilePart) part;
					fileName = filePart.getFileName(); // 得到上传图片的图片名称
					if (fileName != null) { // 得到上传图片的图片名称
						String filename = filePart.writeTo(dir, fileName); // 把图片保存的指定的目录中
						proAddress = filepro + filename;
					} else {
					}
				}
			}
			if (proId.equals("")) {// 如果proId值为空，则为新增，
				JSONObject obj = searchService.saveProduct(proName, proAddress, colcom, addr);
				if (!obj.getBoolean("flag")) {
					response.getWriter().print("<script language=javascript>alert('添加搜索引擎失败！');history.go(-1);</script>");
				} else {
					response.getWriter().print("<script language=javascript>alert('添加搜索引擎成功！');parent.restore();</script>");
				}
			} else {// 如果proId值不为空，则为修改，
				if (proAddress.equals("")) {
					proAddress = pic;
					JSONObject obj = searchService.alterProduct(proName, proAddress, colcom, addr, proId);
					if (!obj.getBoolean("flag")) {
						response.getWriter().print("<script language=javascript>alert('修改搜索引擎失败！');history.go(-1)</script>");
					} else {
						response.getWriter().print("<script language=javascript>alert('修改搜索引擎成功！');parent.restore();</script>");
					}
				} else {
					JSONObject obj = searchService.alterProduct(proName, proAddress, colcom, addr, proId);
					if (!obj.getBoolean("flag")) {
						response.getWriter().print("<script language=javascript>alert('修改搜索引擎失败！');history.go(-1)</script>");
					} else {
						response.getWriter().print("<script language=javascript>alert('修改搜索引擎成功！');parent.restore();</script>");
					}
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
	
	/**
	 * 栏目信息读取
	 * 
	 * @param
	 * @return
	 */
	public ModelAndView protypeSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		result = searchService.getprotype();
		response.getWriter().print(result);
		response.getWriter().close();
		return null;
	}
	
	/**
	 * 栏目信息删除
	 * 
	 * @param
	 * @return
	 */
	public ModelAndView deleteproType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		
		List addr = searchService.getProductInfo(id);
		result = searchService.deleteproType(id);
		
		String img_arr = ((SearchInfo) addr.toArray()[0]).getPic();
		String total_filenames = "";
		
		// modify by Tim 20110914 ssczb -> managess
		String saveDerectory3 = this.getServletContext().getContext("/ssczb").getRealPath("/");
		total_filenames = img_arr.substring(img_arr.lastIndexOf("/") + 1, img_arr.length());
		searchService.deleteFromImages(saveDerectory3, total_filenames);
		
		response.getWriter().print(result);
		response.getWriter().close();
		return null;
	}
	
}
