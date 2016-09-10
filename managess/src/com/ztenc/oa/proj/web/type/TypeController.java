package com.ztenc.oa.proj.web.type;

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
import com.ztenc.oa.proj.service.type.TypeService;
import com.ztenc.oa.proj.util.CUtil;
import com.ztenc.oa.proj.util.pagination.PaginationService;
import com.ztenc.oa.proj.util.pagination.PcurrentDataDaoService;

public class TypeController extends MultiActionController {
	
	private TypeService typeService;
	public void setTypeService(TypeService typeService) {
		this.typeService = typeService;
	}
	String result = "";
	public ModelAndView getUrl(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		Map model = new HashMap();
		return new ModelAndView("type/typemanage",model);
	}
	
	public ModelAndView deleteType(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String info = request.getParameter("fbid");
		result = typeService.deleteType(info);
		response.getWriter().print(result);
		response.getWriter().close();
		return null;
	}

	public ModelAndView modifyType(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session =request.getSession();
		Map model = new HashMap();
		String obj = new String(request.getParameter("obj").getBytes("iso8859-1"),"utf-8");
		if(!obj.equals("")){
			String [] object = obj.split(",");
			String typeno = object[0];
			String typename = object[1];
			String pic = object[2];
			String remark = object[3];
			String level = object[4];
			model.put("typeno", typeno);
			model.put("typename", typename);
			model.put("pic", pic);
			model.put("remark", remark);
			model.put("level", level);
		}
		return new ModelAndView("type/addType",model);
	}
	
	public ModelAndView addType(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session =request.getSession();
		Map model = new HashMap();
		return new ModelAndView("type/addType",model);
	}
	
	
	public ModelAndView saveType(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		String saveDirectory1="";
		String saveDirectory2= this.getServletContext().getRealPath("/");
		    //System.out.println("saveDirectory="+saveDirectory2);
		    File dir;
		    String saveDirectory = this.getServletContext().getRealPath("/");
		    saveDirectory1 = saveDirectory;
		    //System.out.println("saveDirectory="+saveDirectory);
		    int maxPostSize = 5 * 1024;
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
				String filepro ="images/catagory/";
				saveDirectory =saveDirectory+filepro;
				dir = new File(saveDirectory);
		    	dir.mkdir();
		    while ((part = mp.readNextPart()) != null) {
		        String name = part.getName(); //得到form标单中控件的名称，包括按钮等。
		        //System.out.println(">> " + name + "<br>");  //输出“文件名称”。
		        if (part.isParam()) {  //判断是不是“文本域”控件，如果不是“文本域”，则执行这句话。
		            ParamPart paramPart = (ParamPart) part;
		            String value = paramPart.getStringValue();  //得到其值
		            System.out.println("param: name=" + name + "; value=" + value + "<br>");
		            if(name.equals("typename")){
		            	proName = value;
		            }else if(name.equals("pic_addr")){
		            	pic = value;
		            }else if(name.equals("remark")){
		            	remark = value;
		            }else if(name.equals("typeno")){
		            	proId = value;
		            }else if(name.equals("level")){
		            	level = value;
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
		   if(proId.equals("")){//如果proId值为空，则为新增，
			    //ReadServiceImpl ie=new ReadServiceImpl();
			   String proid = CUtil.createBillNo(8);
			   System.out.println(proid);
				JSONObject obj=typeService.saveType(proName,remark,proAddress,level);
				if(!obj.getBoolean("flag")){
			       	response.getWriter().print("<script language=javascript>alert('产品类别信息添加失败！');history.go(-1);</script>");
			    }else{
			    	response.getWriter().print("<script language=javascript>alert('产品类别信息添加成功！');parent.restore();</script>");
			    }
		    }else{//如果proId值不为空，则为修改，
			   if(proAddress.equals("")){
				   proAddress = pic;
				 //ReadServiceImpl ie=new ReadServiceImpl();
					JSONObject obj=typeService.alterType(proName,remark,proAddress,proId,level);
					if(!obj.getBoolean("flag")){
				       	response.getWriter().print("<script language=javascript>alert('产品类别信息修改失败！');history.go(-1)</script>");
				    }else{
				    	response.getWriter().print("<script language=javascript>alert('产品类别信息修改成功！');parent.restore();</script>");
				    }
			   }else{
				    //ReadServiceImpl ie=new ReadServiceImpl();
					JSONObject obj=typeService.alterType(proName,remark,proAddress,proId,level);
					if(!obj.getBoolean("flag")){
				       	response.getWriter().print("<script language=javascript>alert('产品类别信息修改失败！');history.go(-1)</script>");
				    }else{
				    	response.getWriter().print("<script language=javascript>alert('产品类别信息修改成功！');parent.restore();</script>");
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
	}
	public ModelAndView typeSearch(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		int index = Integer.valueOf(request.getParameter("index"));
		int length = Integer.valueOf(request.getParameter("length"));
		result = typeService.getType(index, length);
		response.getWriter().print(result);
		response.getWriter().close();
		return null;
	}
}
