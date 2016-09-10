package com.ztenc.oa.proj.web.advertisement;

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

import com.ztenc.oa.proj.bean.Product;
import com.ztenc.oa.proj.bean.Advertisment;
import com.ztenc.oa.proj.json.JSONObject;
import com.ztenc.oa.proj.service.advertisement.AdvertisService;
import com.ztenc.oa.proj.service.column.ColumnService;
import com.ztenc.oa.proj.service.msg.MsgService;
import com.ztenc.oa.proj.util.CUtil;
import com.ztenc.oa.proj.util.pagination.PaginationService;
import com.ztenc.oa.proj.util.pagination.PcurrentDataDaoService;

public class AdvertisController extends MultiActionController {
	
	private AdvertisService advertisService;
	public void setAdvertisService(AdvertisService advertisService) {
		this.advertisService = advertisService;
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
		Map model = new HashMap();
		return new ModelAndView("advertisment/portype",model);
	}
	
	
	/**
	 * 跳转到栏目添加修改
	 * 
	 * @param 
	 * @return
	 */
	public ModelAndView addpro(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		Map model = new HashMap();
		//int id = Integer.valueOf(request.getParameter("id"));
//		String obj = new String(request.getParameter("obj").getBytes("iso8859-1"),"utf-8");
//		if(!obj.equals("null")){
//			String [] object = obj.split(",");
//			String id = object[0];
//			String name = object[1];
//			String  topID= object[2];
//			String remark = object[3];
//			String pic = object[4];
//			String quanxian = object[5];
//			String level = object[7];
//			model.put("id", id);
//			model.put("name", name);
//			model.put("topid", topID);
//			model.put("remark", remark);
//			model.put("pic", pic);
//			model.put("quanxian", quanxian);
//			model.put("level", level);
//			System.out.println("quanxian="+quanxian);
//		}
//		List list = advertisService.getpro();
//		List list2 = advertisService.getGroups();
//		model.put("protype_clime", list);
//		model.put("groups", list2);
		return new ModelAndView("advertisment/addpro",model);
		//return new ModelAndView("column/addpro",model);
	}
	
	
	/**
	 * 添加、修改栏目信息
	 * 
	 * @param 
	 * @return
	 */
	public ModelAndView saveProtype(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String saveDirectory1="";
		//modify by Tim 20110915 ssczb -> managess
		String saveDirectory2= this.getServletContext().getContext("/ssczb").getRealPath("/");
		    
		    File dir;
		    //modify by Tim 20110915
		    String saveDirectory = this.getServletContext().getContext("/ssczb").getRealPath("/");
		    saveDirectory1 = saveDirectory;
		    
		    int maxPostSize = 5 * 1024*10;
		    MultipartParser mp = new MultipartParser(request, maxPostSize); // 10MB
		    mp.setEncoding("utf-8");
		    Part part;
		    //int type = Integer.valueOf(request.getParameter("type"));
		    //int id = Integer.valueOf(request.getParameter("id"));
		    //System.out.println("id="+id);
		   // System.out.println(type);
		    String proName="";
		    String topProid ="";
		    String remark="";
		    String pic="";
		    String permission = "";
		    String proId = "";
		    String proAddress="";
		    String fileName="";
		    String level = "";
//		    SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd" + " " + "HH:mm:ss");
//			SimpleDateFormat tempDate1 = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
//				Calendar p_cal = Calendar.getInstance();
//		        java.util.Date p_date = p_cal.getTime();
//				String datetime = tempDate.format(p_date);
//				String datetime1 = tempDate1.format(p_date);
				String filepro ="upload/advertis/images/";
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
		            if(name.equals("name_product")){
		            	proName = value;
		            }else if(name.equals("remark")){
		            	remark = value;
		            }
		        } else if (part.isFile()) {   //假如是文本域
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
//		   if(proId.equals("")){//如果proId值为空，则为新增，
			    //ReadServiceImpl ie=new ReadServiceImpl();
			   //String proid = CUtil.createBillNo(8);
			   //System.out.println(proid);
				JSONObject obj=advertisService.saveProduct(proName,proAddress,remark);
				if(!obj.getBoolean("flag")){
			       	response.getWriter().print("<script language=javascript>alert('广告宣传图添加失败！');history.go(-1);</script>");
			    }else{
			    	response.getWriter().print("<script language=javascript>alert('广告宣传图添加成功！');parent.restore();</script>");
			    }
//		    }else{//如果proId值不为空，则为修改，
//			   if(proAddress.equals("")){
//				   proAddress = pic;
//				 //ReadServiceImpl ie=new ReadServiceImpl();
//					JSONObject obj=advertisService.alterProduct(proName,topProid,remark,proAddress,permission,proId,level);
//					if(!obj.getBoolean("flag")){
//				       	response.getWriter().print("<script language=javascript>alert('栏目信息修改失败！');history.go(-1)</script>");
//				    }else{
//				    	response.getWriter().print("<script language=javascript>alert('栏目信息修改成功！');parent.restore();</script>");
//				    }
//			   }else{
//				    //ReadServiceImpl ie=new ReadServiceImpl();
//					JSONObject obj=advertisService.alterProduct(proName,topProid,remark,proAddress,permission,proId,level);
//					if(!obj.getBoolean("flag")){
//				       	response.getWriter().print("<script language=javascript>alert('栏目信息修改失败！');history.go(-1)</script>");
//				    }else{
//				    	response.getWriter().print("<script language=javascript>alert('栏目信息修改成功！');parent.restore();</script>");
//				    }
//			    }
//		    }
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
	public ModelAndView protypeSearch(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("topproId");
		result = advertisService.getprotype(id);
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
	public ModelAndView deleteproType(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		String delete =request.getParameter("flag");
		List addr = advertisService.getProductInfo(id);
		result = advertisService.deleteproType(id,delete);
		
		String img_arr = ((Advertisment)addr.toArray()[0]).getPicAdr();
		String total_filenames = "";
		//modify by Tim 20110915 ssczb -> managess
		String saveDerectory3 =  this.getServletContext().getContext("/ssczb").getRealPath("/");
		total_filenames = img_arr.substring(img_arr.lastIndexOf("/")+1,img_arr.length());
		advertisService.deleteFromImages(saveDerectory3,total_filenames);
		
		response.getWriter().print(result);
		response.getWriter().close();
		return null;
	}
	
	
}
