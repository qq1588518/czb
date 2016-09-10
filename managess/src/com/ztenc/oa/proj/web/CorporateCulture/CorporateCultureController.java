package com.ztenc.oa.proj.web.CorporateCulture;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.ztenc.oa.proj.bean.Corporateculture;
import com.ztenc.oa.proj.json.JSONArray;
import com.ztenc.oa.proj.json.JSONObject;
import com.ztenc.oa.proj.service.CorporateCulture.CorporateCultureService;
import com.ztenc.oa.proj.service.column.ColumnConService;
import com.ztenc.oa.proj.service.msg.MsgService;
import com.ztenc.oa.proj.util.CUtil;
import com.ztenc.oa.proj.util.pagination.PaginationService;
import com.ztenc.oa.proj.util.pagination.PcurrentDataDaoService;

public class CorporateCultureController extends MultiActionController {
	
	private CorporateCultureService corporateCultureService;
	public void setCorporateCultureService(CorporateCultureService corporateCultureService) {
		this.corporateCultureService = corporateCultureService;
	}
String result = "";
	
	/**
	 * 跳转到管理界面
	 * 
	 * @param 
	 * @return
	 */
	public ModelAndView getUrl(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		Map model = new HashMap();
		return new ModelAndView("CorporateCulture/corporate",model);
	}
	
	
	/**
	 * 跳转到添加、修改
	 * 
	 * @param 
	 * @return
	 */
	public ModelAndView addpro(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		Map model = new HashMap();
		String obj = new String(request.getParameter("obj").getBytes("iso8859-1"),"utf-8");
		String pro = request.getParameter("pro");
		if(!obj.equals("")){
			List corporate = corporateCultureService.getCorporate(obj);
			String index = "";
			//modify by Tim 20110913 ssczb -> managess
			String saveDirectory = this.getServletContext().getContext("/ssczb").getRealPath("/"); 
			System.out.println("listcon == "+corporate.size());
			ArrayList al = new ArrayList();
			for(Iterator iter = corporate.iterator();iter.hasNext();){
				Corporateculture corporateculture = (Corporateculture) iter.next();
				model.put("id", corporateculture.getId());
				model.put("title", corporateculture.getTitle());
				model.put("outurl", corporateculture.getOutUri());
				model.put("dates", corporateculture.getDates());
				String urls = corporateculture.getConUri();
				String[] url = urls.split(",");
				int len = url.length;
				
				for(int i=0;i<len-1;i++){
					
					String content = corporateCultureService.getContentFromFile(saveDirectory, url[i]);
					int start = content.indexOf("<span id=\"start\">");
					int end = content.indexOf("</span><span id=\"end\" style=\"display:none\"></span>");
					System.out.println("start=="+start);
					System.out.println("end=="+end);
					if(start!=-1 && end!=-1){
						content = content.substring((start+17),end);
					}
					System.out.println("content=="+content);
					al.add(content);
					if(i==0) index = content;
				}
				
				request.setAttribute("hyh",al);
				request.setAttribute("index",index);
				model.put("con", al);
				model.put("index",index);
				System.out.println("test=="+al.size()); 
				
			}
		}
		return new ModelAndView("CorporateCulture/addcorporate",model);
	}
	
	
	/**
	 * 保存添加、修改信息
	 * 
	 * @param 
	 * @return
	 */
	public ModelAndView saveProtype(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		System.out.println("gongsiqiyewenhua...");
		    File dir2;
		  //modify by Tim 20110913 ssczb -> managess
		    String saveDerectory3 =  this.getServletContext().getContext("/ssczb").getRealPath("/");
		    int maxPostSize = 10 * 1024 * 1024;
		    MultipartParser mp = new MultipartParser(request, maxPostSize); // 10MB
		    mp.setEncoding("utf-8");
		    Part part;
		    String title="";
		    String outurl = "";
		    String con = "";
		    String id = "";
		    String url = "";
		    String datetime11 = "";
		    String filenames = "";
		    String editmode = "";
		    String dates = "";
			SimpleDateFormat tempDate1 = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
			Calendar p_cal = Calendar.getInstance();
		    java.util.Date p_date = p_cal.getTime();
				String datetime1 = tempDate1.format(p_date);
				String filepath = "upload/corporateculture/file/";
		    while ((part = mp.readNextPart()) != null) {
		        String name = part.getName(); //得到form标单中控件的名称，包括按钮等。
		        //System.out.println(">> " + name + "<br>");  //输出“文件名称”。
		        if (part.isParam()) {  //判断是不是“文本域”控件，如果不是“文本域”，则执行这句话。
		            ParamPart paramPart = (ParamPart) part;
		            String value = paramPart.getStringValue();  //得到其值
		            System.out.println("param: name=" + name + "; value=" + value + "<br>");
		            if(name.equals("name_product")){
		            	title = value;
		            }else if(name.equals("outurl")){
		            	outurl = value;
		            }else if(name.equals("cata_id")){
		            	id = value;
		            }else if(name.equals("dates")){
		            	dates = value;
		            }
		            else if(name.equals("txtContent")){
		            	con = value;
		            	JSONObject json = new JSONObject(con);
		            	JSONArray arr = json.getJSONArray("content");
		            	String total = json.getString("all");
		            	String total_filenames = "";
		            	int len = 0;
		            	if(id.equals("")){
			            	len = arr.length();
			            	for(int i=0;i<len;i++){
			            		SimpleDateFormat tempDate11 = new SimpleDateFormat("yyMMddHHmmss");
			    				Calendar p_cal2 = Calendar.getInstance();
			    		        java.util.Date p_date2 = p_cal2.getTime();
			    		        datetime11 = tempDate11.format(p_date2)+CUtil.createBillNo(6)+".html";
			    		        filenames += datetime11+",";
			    		        if(i==len-1){
			    		        	total_filenames = tempDate11.format(p_date2)+CUtil.createBillNo(6)+".html";
			    		        }
			            	}
			            	filenames = filenames.substring(0, filenames.length()-1);
			            	for(int y=0;y<len;y++){
			            		url += corporateCultureService.saveContentToFile(title,arr.getString(y),saveDerectory3,filenames,y,total,total_filenames,filepath,dates)+",";
			            	}
		            	}else{
		            		List addr = corporateCultureService.getCorporate(id);
		            		String con_arr = ((Corporateculture)addr.toArray()[0]).getConUri();
		            		String[] con_addr = con_arr.split(",");
		            		len = con_addr.length;
		            		String tmp_filename = "";
		            		String tmp_total = "";
		            		for(int i=0;i<len;i++){
		            			
		            			if(i!=0 && i==len-1){
		            				total_filenames = con_addr[i].substring(con_addr[i].lastIndexOf("/")+1,con_addr[i].length());
		            				corporateCultureService.deleteFromFile(saveDerectory3,total_filenames);
		            			}else{
		            				filenames = con_addr[i].substring(con_addr[i].lastIndexOf("/")+1,con_addr[i].length());
		            				tmp_filename += con_addr[i].substring(con_addr[i].lastIndexOf("/")+1,con_addr[i].length())+",";
		            				corporateCultureService.deleteFromFile(saveDerectory3,filenames);
		            			}
		            		}
		            		
		            		tmp_filename = tmp_filename.substring(0, tmp_filename.length()-1);
		            		len = arr.length();
		            		int tmp_len = tmp_filename.split(",").length;
		            		String real_filename = "";
		            		if(len>tmp_len){
		            			tmp_filename = tmp_filename+",";
		            			for(int y=0;y<(len-tmp_len);y++){
		            				
					            		SimpleDateFormat tempDate11 = new SimpleDateFormat("yyMMddHHmmss");
					    				Calendar p_cal2 = Calendar.getInstance();
					    		        java.util.Date p_date2 = p_cal2.getTime();
					    		        datetime11 = tempDate11.format(p_date2)+CUtil.createBillNo(6)+".html";
					    		        tmp_filename += datetime11+",";
			            		}
		            			real_filename = tmp_filename.substring(0, tmp_filename.length()-1);
		            			for(int k=0;k<len;k++){
			            			url += corporateCultureService.saveContentToFile(title,arr.getString(k),saveDerectory3,real_filename,k,total,total_filenames,filepath,dates)+",";
			            		}
		            		}else if(tmp_len>len){
		            			
		            			for(int j=0;j<len;j++){
		            				
		            				real_filename +=tmp_filename.split(",")[j]+",";
		            			}
		            			real_filename = real_filename.substring(0, real_filename.length()-1);
		            			for(int k=0;k<len;k++){
			            			url += corporateCultureService.saveContentToFile(title,arr.getString(k),saveDerectory3,real_filename,k,total,total_filenames,filepath,dates)+",";
			            		}
		            		}else{
		            			for(int k=0;k<len;k++){
			            			url += corporateCultureService.saveContentToFile(title,arr.getString(k),saveDerectory3,tmp_filename,k,total,total_filenames,filepath,dates)+",";
			            		}
		            		}
		            		
		            	}
		            	//url = url.substring(0, url.length()-1);
		            	url = url + filepath+total_filenames;
		            }else if(name.equals("editmode")){
		            	editmode = value;
		            }
		        }else if (part.isFile()) {   //假如是文本域
		            FilePart filePart = (FilePart) part;
//		            fileName = filePart.getFileName();  //得到上传图片的图片名称
//		            //System.out.println("fileName:"+fileName);
//		            if (fileName != null) {  //得到上传图片的图片名称
//		                String filename = filePart.writeTo(dir,fileName);  //把图片保存的指定的目录中
//		                proAddress = filepro+filename;
//		            } else {
//		            }
		        }
		    }
		   if(id.equals("")){//如果proId值为空，则为新增，
				JSONObject obj=corporateCultureService.saveProduct(title,url,outurl,datetime1);
				if(!obj.getBoolean("flag")){
			       	response.getWriter().print("<script language=javascript>alert('添加失败！');history.go(-1);</script>");
			    }else{
			    	response.getWriter().print("<script language=javascript>alert('添加成功！');parent.restore();</script>");
			    }
		    }else{//如果proId值不为空，则为修改，
				 //ReadServiceImpl ie=new ReadServiceImpl();
				JSONObject obj=corporateCultureService.alterProduct(title,url,outurl,datetime1,id);
				if(!obj.getBoolean("flag")){
			       	response.getWriter().print("<script language=javascript>alert('修改失败！');history.go(-1)</script>");
			    }else{
			    	response.getWriter().print("<script language=javascript>alert('修改成功！');parent.restore();</script>");
			    }
		    }
		}catch (Exception e){
			System.out.println(e);
			try {
				response.getWriter().print("<script language=javascript>alert('"+e.getMessage()+"');history.go(-1)</script>");
			} catch (IOException e1) {
				}
		}
		return null;
//		Map model = new HashMap();
//		return new ModelAndView("column/portype",model);
	}
	
	/**
	 * 信息读取
	 * 
	 * @param 
	 * @return
	 */
	public ModelAndView protypeSearch(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String title = request.getParameter("title");
		int index = Integer.valueOf(request.getParameter("index"));
		int length = Integer.valueOf(request.getParameter("length"));
		result = corporateCultureService.getprotype(title,index,length);
		response.getWriter().print(result);
		response.getWriter().close();
		return null;
	}

	/**
	 * 信息删除
	 * 
	 * @param 
	 * @return
	 */
	public ModelAndView deleteproType(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		List addr = corporateCultureService.getCorporate(id);
		result = corporateCultureService.deleteproType(id);
		String con_arr = ((Corporateculture)addr.toArray()[0]).getConUri();
		String[] con_addr = con_arr.split(",");
		String total_filenames = "";
		String filenames = "";
		//modify by Tim 20110913 ssczb -> managess
		String saveDerectory3 =  this.getServletContext().getContext("/ssczb").getRealPath("/");
		int len = con_addr.length;
		for(int i=0;i<len;i++){
			
			if(i!=0 && i==len-1){
				total_filenames = con_addr[i].substring(con_addr[i].lastIndexOf("/")+1,con_addr[i].length());
				corporateCultureService.deleteFromFile(saveDerectory3,total_filenames);
			}else{
				filenames = con_addr[i].substring(con_addr[i].lastIndexOf("/")+1,con_addr[i].length());
				corporateCultureService.deleteFromFile(saveDerectory3,filenames);
			}
			
		}
		
//		filenames = filenames.substring(0, filenames.length()-1);
		response.getWriter().print(result);
		response.getWriter().close();
		return null;
	}
	
	
}
