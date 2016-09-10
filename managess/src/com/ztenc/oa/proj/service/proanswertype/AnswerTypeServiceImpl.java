package com.ztenc.oa.proj.service.proanswertype;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.WebApplicationContext;

import com.ztenc.oa.proj.bean.AnswerType;
import com.ztenc.oa.proj.bean.CataGroup;
import com.ztenc.oa.proj.bean.Catagory;
import com.ztenc.oa.proj.bean.Producttype;
import com.ztenc.oa.proj.bean.WlMessageboard;
import com.ztenc.oa.proj.dao.column.ColumnDao;
import com.ztenc.oa.proj.dao.proanswertype.AnswerTypeDao;
import com.ztenc.oa.proj.dao.type.TypeDao;
import com.ztenc.oa.proj.json.JSONArray;
import com.ztenc.oa.proj.json.JSONObject;
import com.ztenc.oa.proj.util.CUtil;
import com.ztenc.oa.proj.util.pagination.PcurrentDataDaoService;

public class AnswerTypeServiceImpl implements AnswerTypeService{
	
	AnswerTypeDao answerTypeDao;
	public void setAnswerTypeDao(AnswerTypeDao answerTypeDao) {
		this.answerTypeDao = answerTypeDao;
	}
	
	//读取所有栏目信息
	public String getType(int index, int length){
		 String rtn = "";
			ResultSet rs = null;
			try{
				rs = answerTypeDao.getType(index, length);
				JSONArray all = new JSONArray();
				JSONObject jsonobj = new JSONObject(); 
				JSONObject totalobj = new JSONObject(); 
				while(rs.next()){
					JSONArray jsonarray = new JSONArray();
					String id = (String)rs.getString(1);
					String name = (String)rs.getString(2);
					String imageUrl = (String)rs.getString(3);
					String remark = (String)rs.getString(4);
					String level = (String)rs.getString(5);
					String total = (String)rs.getString(6);
					jsonarray.put(id);
					jsonarray.put(name);
					jsonarray.put(imageUrl);
					jsonarray.put(remark);
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
	
	
	
	//删除栏目信息
	public String deleteType(String info){//
		String rtn = "0";
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			rtn = answerTypeDao.deleteType(info);
			jsonRtn.put("flag",rtn);
		}catch(Exception e){
			rtn = "0";
			System.out.println(e);
		}
		rtn = sb.append(jsonRtn).toString();
		return rtn;
	}
	
	
	//栏目添加
	public  JSONObject saveType(String typeName,String remark,String proAddress,String level)
	{
		String rtn = "0";
		JSONObject rs = new JSONObject();
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			//String saveDirectory1 = saveDirectory;
			//String datetime1 = datetime;
			AnswerType answerType = new AnswerType();
			answerType.setTypename(typeName);
			answerType.setImages(proAddress);
			System.out.println("level======="+level);
			if(!level.equals("")){
				answerType.setLevel(Integer.valueOf(level));
			}else{
				answerType.setLevel(0);
			}
			answerType.setRemark(remark);
			rtn = answerTypeDao.saveInfo(answerType);
		}catch (Exception e)
	    {
	    	rtn = "0";
	        try{
	        	rs.put("flag",false);
	    	}catch(Exception ex){
	    		System.out.println(ex);
	    	}
	    	System.out.println(e);
	        return rs;
	    }
	    if(rtn.equals("1")){
	    	try{
	    		rs.put("flag",true);
	    	}catch(Exception e){
	    		System.out.println(e);
	    	}
	    	return rs;
	    }else{
	    	try{
	        	rs.put("flag",false);
	    	}catch(Exception ex){
	    		System.out.println(ex);
	    	}
	    	return rs;
	    }
		
	}
	
//栏目修改
	public  JSONObject alterType(String typeName,String remark,String proAddress,String id,String level)
	{
		String rtn = "0";
		JSONObject rs = new JSONObject();
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			//String saveDirectory1 = saveDirectory;
			//String datetime1 = datetime;
			AnswerType answerType =(AnswerType) answerTypeDao.getTypeId(Integer.valueOf(id));
			answerType.setTypename(typeName);
			answerType.setImages(proAddress);
			System.out.println("level======="+level);
			if(!level.equals("")){
				answerType.setLevel(Integer.valueOf(level));
			}else{
				answerType.setLevel(0);
			}
			answerType.setRemark(remark);
			rtn = answerTypeDao.updateInfo(answerType);
			
		}catch (Exception e)
	    {
	    	rtn = "0";
	        try{
	        	rs.put("flag",false);
	    	}catch(Exception ex){
	    		System.out.println(ex);
	    	}
	        return rs;
	    }
	    if(rtn.equals("1")){
	    	try{
	    		rs.put("flag",true);
	    	}catch(Exception e){
	    		System.out.println(e);
	    	}
	    	return rs;
	    }else{
	    	try{
	        	rs.put("flag",false);
	    	}catch(Exception ex){
	    		System.out.println(ex);
	    	}
	    	return rs;
	    }
	}
	
}
