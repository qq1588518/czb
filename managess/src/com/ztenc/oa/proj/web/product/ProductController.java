package com.ztenc.oa.proj.web.product;

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
import com.ztenc.oa.proj.bean.Product;
import com.ztenc.oa.proj.bean.Product2group;
import com.ztenc.oa.proj.json.JSONArray;
import com.ztenc.oa.proj.json.JSONObject;
import com.ztenc.oa.proj.service.column.ColumnConService;
import com.ztenc.oa.proj.service.msg.MsgService;
import com.ztenc.oa.proj.service.product.ProductService;
import com.ztenc.oa.proj.util.CUtil;
import com.ztenc.oa.proj.util.pagination.PaginationService;
import com.ztenc.oa.proj.util.pagination.PcurrentDataDaoService;

public class ProductController extends MultiActionController {
	
	private ProductService productService;
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
String result = "";
	
	/**
	 * 跳转到栏目管理
	 * 
	 * @param 
	 * @return
	 */
	public ModelAndView getUrl(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		List types = productService.getTypes();
		Map model = new HashMap();
		model.put("types",types);
		return new ModelAndView("product/productmanage",model);
	}
	
	
	
	
	/**
	 * 跳转到栏目添加修改
	 * 
	 * @param 
	 * @return
	 */
	public ModelAndView addProduct(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		Map model = new HashMap();
		String obj = new String(request.getParameter("obj").getBytes("iso8859-1"),"utf-8");
		//System.out.println("pro=="+pro);
		if(!obj.equals("")){
			List listCatagorycon = productService.getProductInfo(obj);
			List listCatagorygroupcon = productService.getProductPermission(obj);
			String index = "";
			//modify by Tim 20110913 ssczb -> managess
			String saveDirectory = this.getServletContext().getContext("/ssczb").getRealPath("/"); 
			System.out.println("listcon == "+listCatagorycon.size());
			ArrayList al = new ArrayList();
			for(Iterator iter = listCatagorycon.iterator();iter.hasNext();){
				Product product = (Product) iter.next();
				model.put("id", product.getProductno());
				model.put("title", product.getProductname());
				model.put("pic", product.getImageurl());
				
				model.put("code", product.getCode());
				model.put("price", product.getPrice());
				model.put("dvvalue", product.getDvalue());
				
				model.put("pvvalue", product.getPvvalue());
				model.put("introduceurl", product.getIntroduceurl());
				model.put("experturl", product.getExperturl());
				model.put("dates", product.getCreatedate());
				
				model.put("useurl", product.getUseurl());
				model.put("level", product.getLevel());
				model.put("typeno", product.getTypeno());
				model.put("standard",product.getStandard());
				String urls = product.getContent();
				String[] url = urls.split(",");
				int len = url.length;
				
				for(int i=0;i<len-1;i++){
					
					String content = productService.getContentFromFile(saveDirectory, url[i]);
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
				
				//String test = content.replaceAll("\"", "");
				//test = test.replaceAll("\'", "");
				
				request.setAttribute("hyh",al);
				request.setAttribute("index",index);
				model.put("con", al);
				model.put("index",index);
				System.out.println("test=="+al.size()); 
				
			}
			for(Iterator iter = listCatagorygroupcon.iterator();iter.hasNext();){
				Product2group pg = (Product2group) iter.next();
				model.put("permission", pg.getGroupno());
				//System.out.println(catatoryConGroup.getSubCatagoryId());
			}
		}
		List list2 = productService.getGroups();
		List list3 = productService.getTypes();
		model.put("groups", list2);
		model.put("types", list3);
		return new ModelAndView("product/addproduct",model);
	}
	
	
	/**
	 * 添加、修改栏目信息
	 * 
	 * @param 
	 * @return
	 */
	public ModelAndView saveProduct(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		System.out.println("22222111111111333333333");
		//modify by Tim 20110913 ssczb -> managess
			String saveDirectory2=  this.getServletContext().getContext("/ssczb").getRealPath("/");
		    //System.out.println("saveDirectory="+saveDirectory2);
		    File dir;
		    File dir2;
		    String saveDirectory =  this.getServletContext().getContext("/ssczb").getRealPath("/");
		   
		  //modify by Tim 20110913 ssczb -> managess
		    String saveDerectory3 =  this.getServletContext().getContext("/ssczb").getRealPath("/");
		    //System.out.println("saveDirectory="+saveDirectory);
		    int maxPostSize = 2*1024 * 1024;
		    int maxPostSize2 = 5 * 1024;
		    MultipartParser mp = new MultipartParser(request, maxPostSize); // 10MB
		    //MultipartParser mp2 = new MultipartParser(request, maxPostSize2); 
		    mp.setEncoding("utf-8");
		   // mp2.setEncoding("utf-8");
		    Part part;
		    Part part2;
		    String title="";
		    String pic ="";//隐藏域内的图片地址
		    String typeno="";
		    String subProid="";
		    String permission = "" ;
		    String con = "";
		    String id = "";
		    String filenames = "";
		    String proAddress="";
		    String fileName="";
		    String url = "";
		    String datetime11 = "";
		    String editmode = "";
		    String introduceurl="";
		    String experturl = "";
		    String useurl = "";
		    String level = "";
		    String code = "";
		    String price = "";
		    String dvalue = "";
		    String pvvalue = "";
		    String dates = "";
		    String standard = "";
//		    SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd" + " " + "HH:mm:ss");
			SimpleDateFormat tempDate1 = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
			Calendar p_cal = Calendar.getInstance();
		    java.util.Date p_date = p_cal.getTime();
//				String datetime = tempDate.format(p_date);
				String datetime1 = tempDate1.format(p_date);
				
				String filepro ="upload/product/images/";
				String filepath = "upload/product/file/";
				saveDirectory =saveDirectory+filepro;
				dir = new File(saveDirectory);
		    	dir.mkdirs();
//		    	while((part2 = mp2.readNextPart()) != null){
//		    		if (part2.isFile()) {   //假如是文本域
//			            FilePart filePart = (FilePart) part2;
//			            fileName = filePart.getFileName();  //得到上传图片的图片名称
//			            //System.out.println("fileName:"+fileName);
//			            if (fileName != null) {  //得到上传图片的图片名称
//			                String filename = filePart.writeTo(dir,fileName);  //把图片保存的指定的目录中
//			                proAddress = filepro+filename;
//			            } else {
//			            }
//			        }
//		    	}
		    while ((part = mp.readNextPart()) != null) {
		        String name = part.getName(); //得到form标单中控件的名称，包括按钮等。
		        //System.out.println(">> " + name + "<br>");  //输出“文件名称”。
		        if (part.isParam()) {  //判断是不是“文本域”控件，如果不是“文本域”，则执行这句话。
		            ParamPart paramPart = (ParamPart) part;
		            String value = paramPart.getStringValue();  //得到其值
		            System.out.println("param: name=" + name + "; value=" + value + "<br>");
		            if(name.equals("name_product")){
		            	title = value;
		            }else if(name.equals("pic_addr")){
		            	pic = value;
		            }else if(name.equals("typeno")){
		            	typeno = value;
		            }else if(name.equals("introduceurl")){
		            	introduceurl = value;
		            }else if(name.equals("experturl")){
		            	experturl = value;
		            }else if(name.equals("useurl")){
		            	useurl = value;
		            }else if(name.equals("code")){
		            	code = value;
		            }else if(name.equals("price")){
		            	price = value;
		            }else if(name.equals("dvalue")){
		            	dvalue = value;
		            }else if(name.equals("pvvalue")){
		            	pvvalue = value;
		            }else if(name.equals("level")){
		            	level = value;
		            }else if(name.equals("select_protype1")){
		            	permission = value;
		            }else if(name.equals("cata_id")){
		            	id = value;
		            }else if(name.equals("dates")){
		            	dates = value;
		            }else if(name.equals("standard")){
		            	standard = value;
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
			            		//url += columnConService.saveContentToFile(title,arr.getString(i),saveDerectory3)+",";
			            	}
			            	filenames = filenames.substring(0, filenames.length()-1);
			            	JSONObject content_obj = new JSONObject();
			            	content_obj.put("name",title);
			            	content_obj.put("code",code);
			            	content_obj.put("price",price);
			            	content_obj.put("dvvalue",dvalue);
			            	content_obj.put("pvalue",pvvalue);
			            	content_obj.put("imgurl",proAddress);
			            	content_obj.put("standard",standard);
			            	content_obj.put("introduceurl",introduceurl);
			            	content_obj.put("experturl",experturl);
			            	content_obj.put("useurl",useurl);
			            	for(int y=0;y<len;y++){
			            		url += productService.saveContentToFile(content_obj.toString(),arr.getString(y),saveDerectory3,filenames,y,total,total_filenames,filepath,dates)+",";
			            	}
		            	}else{
		            		List addr = productService.getProductInfo(id);
		            		String con_arr = ((Product)addr.toArray()[0]).getContent();
		            		String[] con_addr = con_arr.split(",");
		            		len = con_addr.length;
		            		String tmp_filename = "";
		            		String tmp_total = "";
		            		for(int i=0;i<len;i++){
		            			
		            			if(i!=0 && i==len-1){
		            				total_filenames = con_addr[i].substring(con_addr[i].lastIndexOf("/")+1,con_addr[i].length());
		            				productService.deleteFromFile(saveDerectory3,total_filenames);
		            			}else{
		            				filenames = con_addr[i].substring(con_addr[i].lastIndexOf("/")+1,con_addr[i].length());
		            				tmp_filename += con_addr[i].substring(con_addr[i].lastIndexOf("/")+1,con_addr[i].length())+",";
		            				productService.deleteFromFile(saveDerectory3,filenames);
		            			}
		            		}
		            		
		            		//modify by Tim 20110914
		            		String img_filenames = "";
		            		String img_arr = ((Product)addr.toArray()[0]).getImageurl();
		            		img_filenames = img_arr.substring(img_arr.lastIndexOf("/")+1,img_arr.length());
		            		productService.deleteFromImages(saveDerectory3,img_filenames);
		            		
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
//					    		        if(i==len-1){
//					    		        	total_filenames = tempDate11.format(p_date2)+CUtil.createBillNo(6)+".html";
//					    		        }
					            		//url += columnConService.saveContentToFile(title,arr.getString(i),saveDerectory3)+",";
		            				//filenames = filenames.substring(0, filenames.length()-1);
			            		}
		            			real_filename = tmp_filename.substring(0, tmp_filename.length()-1);
		            			JSONObject content_obj = new JSONObject();
				            	content_obj.put("name",title);
				            	content_obj.put("code",code);
				            	content_obj.put("price",price);
				            	content_obj.put("dvvalue",dvalue);
				            	content_obj.put("pvalue",pvvalue);
				            	content_obj.put("imgurl",proAddress.equals("")?pic:proAddress);
				            	content_obj.put("standard",standard);
				            	content_obj.put("introduceurl",introduceurl);
				            	content_obj.put("experturl",experturl);
				            	content_obj.put("useurl",useurl);
		            			for(int k=0;k<len;k++){
			            			url += productService.saveContentToFile(content_obj.toString(),arr.getString(k),saveDerectory3,real_filename,k,total,total_filenames,filepath,dates)+",";
			            		}
		            		}else if(tmp_len>len){
		            			
		            			for(int j=0;j<len;j++){
		            				
		            				real_filename +=tmp_filename.split(",")[j]+",";
		            			}
		            			real_filename = real_filename.substring(0, real_filename.length()-1);
		            			JSONObject content_obj = new JSONObject();
				            	content_obj.put("name",title);
				            	content_obj.put("code",code);
				            	content_obj.put("price",price);
				            	content_obj.put("dvvalue",dvalue);
				            	content_obj.put("pvalue",pvvalue);
				            	content_obj.put("imgurl",proAddress.equals("")?pic:proAddress);
				            	content_obj.put("standard",standard);
				            	content_obj.put("introduceurl",introduceurl);
				            	content_obj.put("experturl",experturl);
				            	content_obj.put("useurl",useurl);
		            			for(int k=0;k<len;k++){
			            			url += productService.saveContentToFile(content_obj.toString(),arr.getString(k),saveDerectory3,real_filename,k,total,total_filenames,filepath,dates)+",";
			            		}
		            		}else{
		            			JSONObject content_obj = new JSONObject();
				            	content_obj.put("name",title);
				            	content_obj.put("code",code);
				            	content_obj.put("price",price);
				            	content_obj.put("dvvalue",dvalue);
				            	content_obj.put("pvalue",pvvalue);
				            	content_obj.put("imgurl",proAddress.equals("")?pic:proAddress);
				            	content_obj.put("standard",standard);
				            	content_obj.put("introduceurl",introduceurl);
				            	content_obj.put("experturl",experturl);
				            	content_obj.put("useurl",useurl);
		            			for(int k=0;k<len;k++){
			            			url += productService.saveContentToFile(content_obj.toString(),arr.getString(k),saveDerectory3,tmp_filename,k,total,total_filenames,filepath,dates)+",";
			            		}
		            		}
		            		
		            	}
		            	//url = url.substring(0, url.length()-1);
		            	url = url + filepath+total_filenames;
		            }else if(name.equals("editmode")){
		            	editmode = value;
		            }
		        }
		        else if (part.isFile()) {   //假如是文本域
		            FilePart filePart = (FilePart) part;
		            fileName = filePart.getFileName();  //得到上传图片的图片名称
		            //System.out.println("fileName:"+fileName);
		            if (fileName != null) {  //得到上传图片的图片名称
		                String filename = filePart.writeTo(dir,fileName);  //把图片保存的指定的目录中
		                proAddress = filepro+filename;
		            } else {
		            }
		        }
		    }
		   if(id.equals("")){//如果proId值为空，则为新增，
			    //ReadServiceImpl ie=new ReadServiceImpl();
			   id = CUtil.createBillNo(8);
			   System.out.println(id);
				JSONObject obj=productService.saveProduct(title, proAddress, typeno, permission, url, introduceurl, experturl, useurl, level, code, price, dvalue, pvvalue,standard);
				if(!obj.getBoolean("flag")){
			       	response.getWriter().print("<script language=javascript>alert('产品内容添加失败！');history.go(-1);</script>");
			    }else{
			    	response.getWriter().print("<script language=javascript>alert('产品内容添加成功！');parent.restore();</script>");
			    }
		    }else{//如果proId值不为空，则为修改，
			   if(proAddress.equals("")){
				   proAddress = pic;
				 //ReadServiceImpl ie=new ReadServiceImpl();
					JSONObject obj=productService.alterProduct(title, proAddress, typeno, permission, url, introduceurl, experturl, useurl, level, code, price, dvalue, pvvalue, id,standard);
					if(!obj.getBoolean("flag")){
				       	response.getWriter().print("<script language=javascript>alert('产品内容修改失败！');history.go(-1)</script>");
				    }else{
				    	response.getWriter().print("<script language=javascript>alert('产品内容修改成功！');parent.restore();</script>");
				    }
			   }else{
				    //ReadServiceImpl ie=new ReadServiceImpl();
					JSONObject obj=productService.alterProduct(title, proAddress, typeno, permission, url, introduceurl, experturl, useurl, level, code, price, dvalue, pvvalue, id,standard);
					if(!obj.getBoolean("flag")){
				       	response.getWriter().print("<script language=javascript>alert('产品内容修改失败！');history.go(-1)</script>");
				    }else{
				    	response.getWriter().print("<script language=javascript>alert('产品内容修改成功！');parent.restore();</script>");
				    }
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
	 * 栏目信息读取
	 * 
	 * @param 
	 * @return
	 */
	public ModelAndView productSearch(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String title = request.getParameter("title");
		String name = request.getParameter("typeno");
		int index = Integer.valueOf(request.getParameter("index"));
		int length = Integer.valueOf(request.getParameter("length"));
		result = productService.getProductList(title,name,index, length);
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
	public ModelAndView deleteProduct(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		List addr = productService.getProductInfo(id);
		result = productService.deleteProduct(id);
		String con_arr = ((Product)addr.toArray()[0]).getContent();
		String img_arr = ((Product)addr.toArray()[0]).getImageurl();
		String[] con_addr = con_arr.split(",");
		String total_filenames = "";
		String filenames = "";
		//modify by Tim 20110913 ssczb -> managess
		String saveDerectory3 =  this.getServletContext().getContext("/ssczb").getRealPath("/");
		int len = con_addr.length;
		for(int i=0;i<len;i++){
			
			if(i!=0 && i==len-1){
				total_filenames = con_addr[i].substring(con_addr[i].lastIndexOf("/")+1,con_addr[i].length());
				productService.deleteFromFile(saveDerectory3,total_filenames);
			}else{
				filenames = con_addr[i].substring(con_addr[i].lastIndexOf("/")+1,con_addr[i].length());
				productService.deleteFromFile(saveDerectory3,filenames);
			}
			
		}
		
		//modify by Tim 20110913
		for(int i=0;i<1;i++){
			
			if(i!=0 && i==len-1){
				total_filenames = img_arr.substring(img_arr.lastIndexOf("/")+1,img_arr.length());
				productService.deleteFromImages(saveDerectory3,total_filenames);
			}else{
				total_filenames = img_arr.substring(img_arr.lastIndexOf("/")+1,img_arr.length());
				productService.deleteFromImages(saveDerectory3,total_filenames);
			}
			
		}
		filenames = filenames.substring(0, filenames.length()-1);
		response.getWriter().print(result);
		response.getWriter().close();
		return null;
	}
	
	
}
