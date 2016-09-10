package com.ztenc.oa.proj.service.courseware;

import java.io.File;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import basic.base.test.MultipartParser;
import basic.base.test.Part;

import com.ztenc.oa.proj.bean.CatatoryConGroup;
import com.ztenc.oa.proj.bean.Courseware;
import com.ztenc.oa.proj.bean.Courseware2subject;
import com.ztenc.oa.proj.bean.Groups;
import com.ztenc.oa.proj.bean.Member;
import com.ztenc.oa.proj.bean.Member2group;
import com.ztenc.oa.proj.bean.Product;
import com.ztenc.oa.proj.bean.Subject;
import com.ztenc.oa.proj.dao.ServiceCount.ServiceCountDao;
import com.ztenc.oa.proj.dao.courseware.CoursewareDao;
import com.ztenc.oa.proj.dao.group.*;
import com.ztenc.oa.proj.dao.subject.SubjectDao;
import com.ztenc.oa.proj.json.JSONArray;
import com.ztenc.oa.proj.json.JSONObject;
import com.ztenc.oa.proj.util.pagination.PcurrentDataDaoService;
import com.ztenc.oa.proj.util.CUtil;



public class CoursewareServiceImpl implements CoursewareService{
	
	CoursewareDao coursewaredao;
	public void setCoursewareDao(CoursewareDao courseware) {
		this.coursewaredao = courseware;
	}
	
	ServiceCountDao serviceCountDao;
	public void setServiceCountDao(ServiceCountDao serviceCountDao) {
		this.serviceCountDao = serviceCountDao;
	}
	
public List getSubject(){
		String rtn = "";
		List rs = null;
		List list = null;
		list = coursewaredao.getSubject();
		return list;
	}
	
	public String coursewareSearchByKey(int index,int length,String coursewarename,String sub_protype){
		
		String rtn = "";
		ResultSet rs = null;
		
		try{
			rs = coursewaredao.coursewareSearchByKey(index, length,coursewarename,sub_protype);
			JSONArray all = new JSONArray();
			JSONObject jsonobj = new JSONObject(); 
			JSONObject totalobj = new JSONObject(); 
			while(rs.next()){
				JSONArray jsonarray = new JSONArray();
				String coursewareno = (String)rs.getString(1);
				String _coursewarename = (String)rs.getString(2);
				String url = (String)rs.getString(3);
				String otherurl = (String)rs.getString(5);
				String titleimg = (String)rs.getString(6);
				String subjectno = rs.getString(10);
				String subjectname = rs.getString(11);
				int total = rs.getInt(7);
				jsonarray.put(coursewareno);
				jsonarray.put(_coursewarename);
				jsonarray.put(url);
				jsonarray.put(otherurl);
				jsonarray.put(titleimg);
				jsonarray.put(subjectno);
				jsonarray.put(subjectname);
				jsonarray.put(total);
				all.put(jsonarray);
			}
			jsonobj.put("rs",all);
			StringBuilder sb = new StringBuilder();
			rtn = sb.append(jsonobj).toString(); 
		}catch(Exception e){
			try{
			rs.close();
			}catch(Exception ex){
				System.out.println(ex);
			}
			System.out.println(e);
		}finally{
			try{
				rs.close();
			}catch(Exception ex){
				System.out.println(ex);
			}
		}
		return rtn;
	}
	
	
	public String deleteCourseware(String no,String url,String img,String basepath){
		String rtn = "0";
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		String typeno = null;
		try{
			
			List list = coursewaredao.getlistcoursegroup(no);
			for(int i=0;i<list.size();i++){
				Courseware2subject pro = (Courseware2subject)list.get(i);
				typeno = String.valueOf(pro.getSubjectno());
			}
			System.out.println("typeno:"+typeno);
			rtn = coursewaredao.deleteCourseware(no);
			if(typeno.equals("1")){
				rtn = serviceCountDao.deleteInfo("coursewareDown");
			}else if(typeno.equals("2")){
				rtn = serviceCountDao.deleteInfo("productQuest");
			}else if(typeno.equals("3")){
				rtn = serviceCountDao.deleteInfo("gameDown");
			}
			
			rtn = "1";
			//ResultSet rs = coursewaredao.coursewareSearchByNo(no);
			try{
			//if(rs.next()){
				//String _url = rs.getString(1);
				//String titleimg = rs.getString(2);
				if(!(url==null || url.equals(""))){
					String saveDirectory1=basepath;
					saveDirectory1 =saveDirectory1+url;
					File dir = new File(saveDirectory1);
					dir.delete();
				}
				if(!(img==null || img.equals(""))){
					String saveDirectory2= basepath;
					saveDirectory2 = saveDirectory2+img;
					File dir2 = new File(saveDirectory2);
					dir2.delete();
				}
			
			//}
			}catch(Exception e){
					
			}finally{
				
				
			}
			jsonRtn.put("flag",rtn);
		}catch(Exception e){
			rtn = "0";
			System.out.println(e);
		}
		rtn = sb.append(jsonRtn).toString();
		return rtn;
	}
	
	public String modifyCourseware(String coursewareno,String coursewarename,String subjectno,String url,String otherurl,String titleimg){
		String rtn = "0";
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd" + " " + "HH:mm:ss");
			Calendar p_cal = Calendar.getInstance();
	        java.util.Date p_date = p_cal.getTime();
			String datetime = tempDate.format(p_date);
			Courseware courseware = (Courseware) coursewaredao.getProdctId(coursewareno);
			//courseware.setCoursewareno(coursewareno);
			courseware.setCoursewarename(coursewarename);
			courseware.setUrl(url);
			courseware.setOtherUrl(otherurl);
			courseware.setTitleimg(titleimg);
			//courseware.setCreateDate(p_date);
			Courseware2subject c2s = new Courseware2subject();
			c2s.setCoursewareno(coursewareno);
			c2s.setSubjectno(Integer.valueOf(subjectno));
			rtn = coursewaredao.updateInfo(courseware);
			rtn = coursewaredao.updateInfo(c2s);
			jsonRtn.put("flag",rtn);
			System.out.println("===="+rtn);
		}catch(DataAccessException date){
			rtn = "0";
			try{
				jsonRtn.put("flag",rtn);
			}catch(Exception e){
				System.out.println(e);
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		rtn = sb.append(jsonRtn).toString();
		return rtn;
	}
	public String addCourseware(String coursewarename,String subjectno,String url,String otherurl,String titleimg){
		String rtn = "0";
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd" + " " + "HH:mm:ss");
			Calendar p_cal = Calendar.getInstance();
	        java.util.Date p_date = p_cal.getTime();
			String datetime = tempDate.format(p_date);
			String coursewareno = CUtil.createBillNo(8);
			Courseware courseware = new Courseware();
			Courseware2subject c2s = new Courseware2subject();
			c2s.setCoursewareno(coursewareno);
			c2s.setSubjectno(Integer.valueOf(subjectno));
			courseware.setCoursewareno(coursewareno);
			courseware.setCoursewarename(coursewarename);
			courseware.setUrl(url);
			courseware.setOtherUrl(otherurl);
			courseware.setTitleimg(titleimg);
			courseware.setCreateDate(p_date);
			rtn = coursewaredao.saveInfo(courseware);
			rtn = coursewaredao.saveInfo(c2s);
			
			if(subjectno.equals("1")){
				rtn = serviceCountDao.updateInfo("coursewareDown");
			}else if(subjectno.equals("2")){
				rtn = serviceCountDao.updateInfo("productQuest");
			}else if(subjectno.equals("3")){
				rtn = serviceCountDao.updateInfo("gameDown");
			}
			
			jsonRtn.put("flag",rtn);
			System.out.println("===="+rtn);
		}catch(DataAccessException date){
			rtn = "0";
			try{
				jsonRtn.put("flag",rtn);
			}catch(Exception e){
				System.out.println(e);
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		rtn = sb.append(jsonRtn).toString();
		return rtn;
	}
	
	public String deleteFromImages(String dirname, String url) {
		String rtn = "0";
		String filepro ="upload/courseware/images/";
		dirname =dirname+filepro;
	    File dir2 = new File(dirname);
	    File _name = new File(dir2,url);
	    try{
		    _name.delete();
		    rtn = "1";
	    }catch(Exception e){
	    	System.out.println(e);
	    	rtn = "0";
	    }
		return rtn;
	}
	
	public String deleteFromApk(String dirname, String url) {
		String rtn = "0";
		String filepro ="upload/courseware/file/";
		dirname =dirname+filepro;
	    File dir2 = new File(dirname);
	    File _name = new File(dir2,url);
	    try{
		    _name.delete();
		    rtn = "1";
	    }catch(Exception e){
	    	System.out.println(e);
	    	rtn = "0";
	    }
		return rtn;
	}
	
	public List getProductInfo(String id) {
		List list = null;
		list = coursewaredao.getProductInfo(id);
		System.out.println("listcon == "+list.size());
		return list;
	}
}
