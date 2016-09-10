package com.ztenc.oa.proj.web.column;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import basic.base.test.MacBinaryDecoderOutputStream;
import basic.base.test.MultipartParser;
import basic.base.test.ParamPart;
import basic.base.test.Part;

import com.ztenc.oa.proj.bean.CatagoryCon;
import com.ztenc.oa.proj.bean.CatatoryConGroup;
import com.ztenc.oa.proj.json.JSONArray;
import com.ztenc.oa.proj.json.JSONObject;
import com.ztenc.oa.proj.service.column.ColumnConService;
import com.ztenc.oa.proj.service.msg.MsgService;
import com.ztenc.oa.proj.util.CUtil;
import com.ztenc.oa.proj.util.pagination.PaginationService;
import com.ztenc.oa.proj.util.pagination.PcurrentDataDaoService;

public class ColumnConController extends MultiActionController {
	
	private ColumnConService columnConService;
	
	public void setColumnConService(ColumnConService columnConService) {
		this.columnConService = columnConService;
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
		List list3 = columnConService.getpro("HRLCBW9T");
		Map model = new HashMap();
		model.put("sub_pro", list3);
		return new ModelAndView("column/colcon", model);
	}
	
	/**
	 * 子栏目信息读取
	 * 
	 * @param
	 * @return
	 */
	public ModelAndView listPro(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		result = columnConService.listPro(id);
		response.getWriter().print(result);
		response.getWriter().close();
		return null;
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
		String pro = request.getParameter("pro");
		// System.out.println("pro=="+pro);
		if (!obj.equals("")) {
			List listCatagorycon = columnConService.getlistCatagorycon(obj);
			List listCatagorygroupcon = columnConService.getCatgroupcon(obj);
			String index = "";
			// modify by Tim 20110913 ssczb -> managess
			String saveDirectory = this.getServletContext().getContext("/ssczb").getRealPath("/");
			System.out.println("listcon == " + listCatagorycon.size());
			ArrayList al = new ArrayList();
			for (Iterator iter = listCatagorycon.iterator(); iter.hasNext();) {
				CatagoryCon catagoryCon = (CatagoryCon) iter.next();
				model.put("id", catagoryCon.getId());
				model.put("title", catagoryCon.getTitle());
				model.put("dates", catagoryCon.getDates());
				model.put("pic", catagoryCon.getPic());
				model.put("conpicimg", catagoryCon.getConpic());
				// String test = catagoryCon.getCon().replaceAll("\"", "");
				// test = catagoryCon.getCon().replaceAll("\'", "");
				String urls = catagoryCon.getCon();
				String[] url = urls.split(",");
				int len = url.length;
				
				for (int i = 0; i < len - 1; i++) {
					
					String content = columnConService.getContentFromFile(saveDirectory, url[i]);
					int start = content.indexOf("<span id=\"start\">");
					int end = content.indexOf("</span><span id=\"end\" style=\"display:none\"></span>");
					System.out.println("start==" + start);
					System.out.println("end==" + end);
					if (start != -1 && end != -1) {
						content = content.substring((start + 17), end);
					}
					System.out.println("content==" + content);
					al.add(content);
					if (i == 0)
						index = content;
				}
				
				// String test = content.replaceAll("\"", "");
				// test = test.replaceAll("\'", "");
				
				request.setAttribute("hyh", al);
				request.setAttribute("index", index);
				model.put("con", al);
				model.put("index", index);
				System.out.println("test==" + al.size());
				
			}
			for (Iterator iter = listCatagorygroupcon.iterator(); iter.hasNext();) {
				CatatoryConGroup catatoryConGroup = (CatatoryConGroup) iter.next();
				model.put("permission", catatoryConGroup.getGroupid());
				model.put("pro", catatoryConGroup.getCatagoryId());
				model.put("subPro", catatoryConGroup.getSubCatagoryId());
				// System.out.println(catatoryConGroup.getSubCatagoryId());
			}
		}
		List list = columnConService.getpro("0");
		List list2 = columnConService.getGroups();
		List list3 = columnConService.getpro(pro);
		// System.out.println("list3==="+list3.size());
		model.put("protype_clime", list);
		model.put("groups", list2);
		model.put("sub_pro", list3);
		return new ModelAndView("column/addcolcon", model);
	}
	
	/**
	 * 添加、修改栏目信息
	 * 
	 * @param
	 * @return
	 */
	public ModelAndView saveProtype(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			System.out.println("kaishi test imageserver");
			// System.out.println("savedirectory==="+this.getServletContext().getContext("/test2").getRealPath("/"));
			// modify by Tim 20110913 ssczb -> managess
			String saveDirectory2 = this.getServletContext().getContext("/ssczb").getRealPath("/");
			// System.out.println("saveDirectory="+saveDirectory2);
			File dir;
			File dir2;
			// modify by Tim 20110913 ssczb -> managess
			String saveDirectory = this.getServletContext().getContext("/ssczb").getRealPath("/");
			
			// modify by Tim 20110913 ssczb -> managess
			String saveDerectory3 = this.getServletContext().getContext("/ssczb").getRealPath("/");
			// System.out.println("saveDirectory="+saveDirectory);
			int maxPostSize = 2 * 1024 * 1024;
			// int maxPostSize2 = 5 * 1024;
			MultipartParser mp = new MultipartParser(request, maxPostSize); // 10MB
			// MultipartParser mp2 = new MultipartParser(request, maxPostSize2);
			mp.setEncoding("utf-8");
			// mp2.setEncoding("utf-8");
			Part part;
			Part part2;
			String title = "";
			String pic = "";// 隐藏域内的图片地址
			String proid = "";
			String subProid = "";
			int permission = 0;
			String con = "";
			String id = "";
			String filenames = "";
			String proAddress = "";
			String fileName = "";
			String url = "";
			String datetime11 = "";
			String dates = "";
			String editmode = "";
			int ipici = 0; // 标识第二个图片上传
			String conpic = ""; // 存储内容图片文件的路径，用，隔开
			String flagfile = ""; // 判断是否有内容图片上传
			String fileimg = "";
			// SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd" + " " + "HH:mm:ss");
			SimpleDateFormat tempDate1 = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
			Calendar p_cal = Calendar.getInstance();
			java.util.Date p_date = p_cal.getTime();
			// String datetime = tempDate.format(p_date);
			String datetime1 = tempDate1.format(p_date);
			
			String filepro = "upload/catagory/images/";
			String filepath = "upload/catagory/file/";
			saveDirectory = saveDirectory + filepro;
			dir = new File(saveDirectory);
			dir.mkdirs();
			while ((part = mp.readNextPart()) != null) {
				String name = part.getName(); // 得到form标单中控件的名称，包括按钮等。
				// System.out.println(">> " + name + "<br>"); //输出“文件名称”。
				if (part.isParam()) { // 判断是不是“文本域”控件，如果不是“文本域”，则执行这句话。
					ParamPart paramPart = (ParamPart) part;
					String value = paramPart.getStringValue(); // 得到其值
					System.out.println("param: name=" + name + "; value=" + value + "<br>");
					if (name.equals("name_product")) {
						title = value;
					} else if (name.equals("pic_addr")) {
						pic = value;
					} else if (name.equals("select_protype")) {
						proid = value;
					} else if (name.equals("sub_protype")) {
						subProid = value;
					} else if (name.equals("select_protype1")) {
						permission = Integer.valueOf(value);
					} else if (name.equals("cata_id")) {
						id = value;
					} else if (name.equals("dates")) {
						dates = value;
					} else if (name.equals("flagfile")) {
						flagfile = value;
						System.out.println("==flagfile=======================" + flagfile);
					} else if (name.equals("conpicimg")) {
						fileimg = value;
						System.out.println("==fileimg======================" + fileimg);
					}

					else if (name.equals("txtContent")) {
						con = value;
						JSONObject json = new JSONObject(con);
						JSONArray arr = json.getJSONArray("content");
						String total = json.getString("all");
						String total_filenames = "";
						int len = 0;
						String[] confilepicname = conpic.split(",");
						System.out.println("confilepicname=----==================" + confilepicname.length);
						if (id.equals("")) {
							len = arr.length();
							for (int i = 0; i < len; i++) {
								SimpleDateFormat tempDate11 = new SimpleDateFormat("yyMMddHHmmss");
								Calendar p_cal2 = Calendar.getInstance();
								java.util.Date p_date2 = p_cal2.getTime();
								datetime11 = tempDate11.format(p_date2) + CUtil.createBillNo(6) + ".html";
								filenames += datetime11 + ",";
								if (i == len - 1) {
									total_filenames = tempDate11.format(p_date2) + CUtil.createBillNo(6) + ".html";
								}
								// url += columnConService.saveContentToFile(title,arr.getString(i),saveDerectory3)+",";
							}
							filenames = filenames.substring(0, filenames.length() - 1);
							for (int y = 0; y < len; y++) {
								if (!flagfile.equals("")) {
									url += columnConService.saveContentToFileAddPic(title, arr.getString(y), saveDerectory3, filenames, y, total, total_filenames, filepath, dates, confilepicname)
											+ ",";
									
								} else {
									// System.out.println("2222222222222222222222222222222222222222" );
									url += columnConService.saveContentToFile(title, arr.getString(y), saveDerectory3, filenames, y, total, total_filenames, filepath, dates) + ",";
								}
							}
						} else {
							System.out.println("2222222222222222222222222222222222222222");
							List addr = columnConService.getlistCatagorycon(id);
							String con_arr = ((CatagoryCon) addr.toArray()[0]).getCon();
							String[] con_addr = con_arr.split(",");
							len = con_addr.length;
							String tmp_filename = "";
							String tmp_total = "";
							for (int i = 0; i < len; i++) {
								System.out.println("111111111333333333333333333");
								if (i != 0 && i == len - 1) {
									total_filenames = con_addr[i].substring(con_addr[i].lastIndexOf("/") + 1, con_addr[i].length());
									columnConService.deleteFromFile(saveDerectory3, total_filenames);
								} else {
									filenames = con_addr[i].substring(con_addr[i].lastIndexOf("/") + 1, con_addr[i].length());
									tmp_filename += con_addr[i].substring(con_addr[i].lastIndexOf("/") + 1, con_addr[i].length()) + ",";
									columnConService.deleteFromFile(saveDerectory3, filenames);
								}
							}
							
							// modify by Tim 20110914
							String pic_filenames = "";
							String conpic_arr = ((CatagoryCon) addr.toArray()[0]).getConpic();
							String pic_arr = ((CatagoryCon) addr.toArray()[0]).getPic();
							String[] conpic_addr = { "" };
							if (conpic_arr != null) {
								conpic_addr = conpic_arr.split(",");
							}
							int conpic_len = conpic_addr.length;
							for (int i = 0; i < conpic_len; i++) {
								
								if (i != 0 && i == conpic_len - 1) {
									pic_filenames = conpic_addr[i].substring(conpic_addr[i].lastIndexOf("/") + 1, conpic_addr[i].length());
									columnConService.deleteFromImages(saveDerectory3, pic_filenames);
								} else {
									filenames = conpic_addr[i].substring(conpic_addr[i].lastIndexOf("/") + 1, conpic_addr[i].length());
									columnConService.deleteFromImages(saveDerectory3, filenames);
								}
								
							}
							pic_filenames = pic_arr.substring(pic_arr.lastIndexOf("/") + 1, pic_arr.length());
							columnConService.deleteFromImages(saveDerectory3, pic_filenames);
							
							tmp_filename = tmp_filename.substring(0, tmp_filename.length() - 1);
							len = arr.length();
							int tmp_len = tmp_filename.split(",").length;
							String real_filename = "";
							if (len > tmp_len) {
								tmp_filename = tmp_filename + ",";
								for (int y = 0; y < len - tmp_len; y++) {
									SimpleDateFormat tempDate11 = new SimpleDateFormat("yyMMddHHmmss");
									Calendar p_cal2 = Calendar.getInstance();
									Date p_date2 = p_cal2.getTime();
									datetime11 = tempDate11.format(p_date2) + CUtil.createBillNo(6) + ".html";
									tmp_filename = tmp_filename + datetime11 + ",";
								}
								
								real_filename = tmp_filename.substring(0, tmp_filename.length() - 1);
								for (int k = 0; k < len; k++)
									url = url + this.columnConService.saveContentToFile(title, arr.getString(k), saveDerectory3, real_filename, k, total, total_filenames, filepath, dates) + ",";
							} else if (tmp_len > len) {
								for (int j = 0; j < len; j++) {
									real_filename = real_filename + tmp_filename.split(",")[j] + ",";
								}
								real_filename = real_filename.substring(0, real_filename.length() - 1);
								for (int k = 0; k < len; k++)
									url = url + this.columnConService.saveContentToFile(title, arr.getString(k), saveDerectory3, real_filename, k, total, total_filenames, filepath, dates) + ",";
							} else {
								for (int k = 0; k < len; k++) {
									url = url + this.columnConService.saveContentToFile(title, arr.getString(k), saveDerectory3, tmp_filename, k, total, total_filenames, filepath, dates) + ",";
								}
							}
							
						}
						// url = url.substring(0, url.length()-1);
						url = url + filepath + total_filenames;
					} else if (name.equals("editmode")) {
						editmode = value;
					}
				} else if (name.equals("total")) {
					
				} else if (part.isFile()) { // 假如是文本域
				
					System.out.println("rafafd------------------------dadsa====================sfdsfddfdsds");
					FilePart filePart = (FilePart) part;
					fileName = filePart.getFileName(); // 得到上传图片的图片名称
					System.out.println("fileName:" + fileName);
					ipici = ipici + 1;
					if (fileName != null) { // 得到上传图片的图片名称
						String filename = filePart.writeTo(dir, fileName); // 把图片保存的指定的目录中
						// conpic = filepro+filename;
						System.out.println("filename=====" + filename + "   proAddress==" + proAddress);
						if (ipici == 1)
							proAddress = filepro + filename;
						if (ipici > 1)
							conpic = conpic + "," + filepro + filename;
						
						// System.out.println("ipici==========="+ipici);
						// ipici=ipici+1;
					} else {
					}
				}
				System.out.println("conpic====" + conpic);
				// else if (part.isFile()) { //假如是文本域
				// System.out.println("34=============================787");
				// FilePart filePart = (FilePart) part;
				// fileName = filePart.getFileName(); //得到上传图片的图片名称
				// //System.out.println("fileName:"+fileName);
				// if (fileName != null) { //得到上传图片的图片名称
				// String filename = filePart.writeTo(dir,fileName); //把图片保存的指定的目录中
				// proAddress = filepro+filename;
				// } else {
				// }
				// }
			}
			if (id.equals("")) {// 如果proId值为空，则为新增，
				// ReadServiceImpl ie=new ReadServiceImpl();
				id = CUtil.createBillNo(8);
				System.out.println(id);
				
				JSONObject obj = columnConService.saveProduct(title, proAddress, proid, subProid, permission, url, datetime1, id, conpic);
				if (!obj.getBoolean("flag")) {
					response.getWriter().print("<script language=javascript>alert('栏目内容添加失败！');history.go(-1);</script>");
				} else {
					response.getWriter().print("<script language=javascript>alert('栏目内容添加成功！');parent.restore();</script>");
				}
			} else {// 如果proId值不为空，则为修改，
				if (proAddress.equals("")) {
					proAddress = pic;
					// ReadServiceImpl ie=new ReadServiceImpl();
					JSONObject obj = columnConService.alterProduct(title, proAddress, proid, subProid, permission, url, datetime1, id, conpic);
					if (!obj.getBoolean("flag")) {
						response.getWriter().print("<script language=javascript>alert('栏目内容修改失败！');history.go(-1)</script>");
					} else {
						response.getWriter().print("<script language=javascript>alert('栏目内容修改成功！');parent.restore();</script>");
					}
				} else {
					// ReadServiceImpl ie=new ReadServiceImpl();
					JSONObject obj = columnConService.alterProduct(title, proAddress, proid, subProid, permission, url, datetime1, id, conpic);
					if (!obj.getBoolean("flag")) {
						response.getWriter().print("<script language=javascript>alert('栏目内容修改失败！');history.go(-1)</script>");
					} else {
						response.getWriter().print("<script language=javascript>alert('栏目内容修改成功！');parent.restore();</script>");
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
		// Map model = new HashMap();
		// return new ModelAndView("column/portype",model);
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
		String title = request.getParameter("title");
		String sub_protype = request.getParameter("sub_protype");
		int index = Integer.valueOf(request.getParameter("index"));
		int length = Integer.valueOf(request.getParameter("length"));
		result = columnConService.getprotype(title, sub_protype, index, length);
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
		List addr = columnConService.getlistCatagorycon(id);
		result = columnConService.deleteproType(id);
		
		String con_arr = ((CatagoryCon) addr.toArray()[0]).getCon();
		String conpic_arr = ((CatagoryCon) addr.toArray()[0]).getConpic();
		String pic_arr = ((CatagoryCon) addr.toArray()[0]).getPic();
		
		String[] con_addr = con_arr.split(",");
		
		String[] conpic_addr = { "" };
		if (conpic_arr != null) {
			conpic_addr = conpic_arr.split(",");
		}
		
		String total_filenames = "";
		String filenames = "";
		// modify by Tim 20110913 ssczb -> managess
		String saveDerectory3 = this.getServletContext().getContext("/ssczb").getRealPath("/");
		int len = con_addr.length;
		for (int i = 0; i < len; i++) {
			
			if (i != 0 && i == len - 1) {
				total_filenames = con_addr[i].substring(con_addr[i].lastIndexOf("/") + 1, con_addr[i].length());
				columnConService.deleteFromFile(saveDerectory3, total_filenames);
			} else {
				filenames = con_addr[i].substring(con_addr[i].lastIndexOf("/") + 1, con_addr[i].length());
				columnConService.deleteFromFile(saveDerectory3, filenames);
			}
			
		}
		
		int conpic_len = conpic_addr.length;
		for (int i = 0; i < conpic_len; i++) {
			
			if (i != 0 && i == conpic_len - 1) {
				total_filenames = conpic_addr[i].substring(conpic_addr[i].lastIndexOf("/") + 1, conpic_addr[i].length());
				columnConService.deleteFromImages(saveDerectory3, total_filenames);
			} else {
				filenames = conpic_addr[i].substring(conpic_addr[i].lastIndexOf("/") + 1, conpic_addr[i].length());
				columnConService.deleteFromImages(saveDerectory3, filenames);
			}
			
		}
		
		total_filenames = pic_arr.substring(pic_arr.lastIndexOf("/") + 1, pic_arr.length());
		columnConService.deleteFromImages(saveDerectory3, total_filenames);
		
		// filenames = filenames.substring(0, filenames.length() - 1);
		response.getWriter().print(result);
		response.getWriter().close();
		return null;
	}
	
}
