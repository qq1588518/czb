package com.ztenc.oa.proj.service.subject;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.ztenc.oa.proj.bean.Courseware;
import com.ztenc.oa.proj.bean.Groups;
import com.ztenc.oa.proj.bean.Member;
import com.ztenc.oa.proj.bean.Member2group;
import com.ztenc.oa.proj.bean.Subject;
import com.ztenc.oa.proj.dao.group.*;
import com.ztenc.oa.proj.dao.subject.SubjectDao;
import com.ztenc.oa.proj.json.JSONArray;
import com.ztenc.oa.proj.json.JSONObject;
import com.ztenc.oa.proj.util.pagination.PcurrentDataDaoService;
import com.ztenc.oa.proj.util.CUtil;
public class SubjectServiceImpl implements SubjectService{
	
	SubjectDao subjectdao;
	public void setSubjectDao(SubjectDao groupdao) {
		this.subjectdao = groupdao;
	}
	//会员信息读取，index为搜索开始位置，length为每页显示的长度
	public String SubjectSearch(int index, int length) {
		String rtn = "";
		ResultSet rs = null;
		
		try{
			rs = subjectdao.subjectSearch(index, length);
			JSONArray all = new JSONArray();
			JSONObject jsonobj = new JSONObject(); 
			JSONObject totalobj = new JSONObject(); 
			while(rs.next()){
				JSONArray jsonarray = new JSONArray();
				String subjectno = (String)rs.getString(1);
				String subjectname = (String)rs.getString(2);
				String imgurl = (String)rs.getString(3);
				String level = (String)rs.getString(4);
				int total = rs.getInt(5);
				jsonarray.put(subjectno);
				jsonarray.put(subjectname);
				jsonarray.put(imgurl);
				jsonarray.put(level);
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
	
	public String deleteSubject(String no){
		String rtn = "0";
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			rtn = subjectdao.deleteSubject(no);
			//rtn = "1";
			jsonRtn.put("flag",rtn);
		}catch(Exception e){
			rtn = "0";
			System.out.println(e);
		}
		rtn = sb.append(jsonRtn).toString();
		return rtn;
	}
	
	public String modifySubject(String subjectno,String subjectname,String imgurl,String level){
		String rtn = "0";
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd" + " " + "HH:mm:ss");
			Calendar p_cal = Calendar.getInstance();
	        java.util.Date p_date = p_cal.getTime();
			String datetime = tempDate.format(p_date);
			Subject subject = (Subject) subjectdao.getProdctId(Integer.valueOf(subjectno));
			//subject.setSubjectno(Integer.valueOf(subjectno));
			subject.setSubjectname(subjectname);
			subject.setImgurl(imgurl);
			System.out.println("level======="+level);
			if(!level.equals("")){
				subject.setLevel(Integer.valueOf(level));
			}else{
				subject.setLevel(0);
			}
			rtn = subjectdao.updateInfo(subject);
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
	public String addSubject(String subjectname,String imgurl,String level){
		String rtn = "0";
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd" + " " + "HH:mm:ss");
			Calendar p_cal = Calendar.getInstance();
	        java.util.Date p_date = p_cal.getTime();
			String datetime = tempDate.format(p_date);
			Subject subject = new Subject();
			subject.setSubjectname(subjectname);
			subject.setImgurl(imgurl);
			System.out.println("level======="+level);
			if(!level.equals("")){
				subject.setLevel(Integer.valueOf(level));
			}else{
				subject.setLevel(0);
			}
			rtn = subjectdao.saveInfo(subject);
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
}
