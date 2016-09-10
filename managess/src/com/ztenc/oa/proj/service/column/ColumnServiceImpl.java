package com.ztenc.oa.proj.service.column;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.WebApplicationContext;

import com.ztenc.oa.proj.bean.CataGroup;
import com.ztenc.oa.proj.bean.Catagory;
import com.ztenc.oa.proj.bean.WlMessageboard;
import com.ztenc.oa.proj.dao.column.ColumnDao;
import com.ztenc.oa.proj.json.JSONArray;
import com.ztenc.oa.proj.json.JSONObject;
import com.ztenc.oa.proj.util.pagination.PcurrentDataDaoService;

public class ColumnServiceImpl implements ColumnService{
	
	ColumnDao columnDao;
	public void setColumnDao(ColumnDao columnDao) {
		this.columnDao = columnDao;
	}
	
	public List getpro(){
		List list = null;
		list = columnDao.getpro();
		return list;
	}
	
	public List getGroups(){
		List list = null;
		list = columnDao.getGroups();
		return list;
	}
	
	//读取所有栏目信息
	public String getprotype(String topproid){
		 String rtn = "";
			ResultSet rs = null;
			String topproId = topproid;
			try{
				rs = columnDao.getprotype(topproId);
				JSONArray all = new JSONArray();
				JSONObject jsonobj = new JSONObject(); 
				JSONObject totalobj = new JSONObject(); 
				while(rs.next()){
					JSONArray jsonarray = new JSONArray();
					String id = (String)rs.getString(1);
					String name = (String)rs.getString(2);
					String topId = (String)rs.getString(3);
					String datetime = (String)rs.getString(4);
					String imageUrl = (String)rs.getString(5);
					String level = (String)rs.getString(6);
					String groupid = (String)rs.getString(7);
					String groupname = (String)rs.getString(8);
					jsonarray.put(id);
					jsonarray.put(name);
					jsonarray.put(topId);
					jsonarray.put(datetime);
					jsonarray.put(imageUrl);
					jsonarray.put(groupid);
					jsonarray.put(groupname);
					jsonarray.put(level);
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
	public String deleteproType(String info,String delete){//
		String rtn = "0";
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			rtn = columnDao.deleteproType(info,delete);
			//rtn = "1";
			jsonRtn.put("flag",rtn);
		}catch(Exception e){
			rtn = "0";
			System.out.println(e);
		}
		rtn = sb.append(jsonRtn).toString();
		return rtn;
	}
	
	
	//栏目添加
	public  JSONObject saveProduct(String proName,String topProid,String remark,String images,String permission,String proid,String level)
	{
		String rtn = "0";
		JSONObject rs = new JSONObject();
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			//String saveDirectory1 = saveDirectory;
			//String datetime1 = datetime;
			Catagory catagory = new Catagory();
			catagory.setProId(proid);
			catagory.setProName(proName);
			catagory.setTopproId(topProid);
			catagory.setRemark(remark);
			catagory.setImages(images);
			System.out.println("level======="+level);
			if(!level.equals("")){
				catagory.setLevel(Integer.valueOf(level));
			}else{
				catagory.setLevel(0);
			}
			rtn = columnDao.saveInfo(catagory);
			
			CataGroup cataGroup = new CataGroup();
			cataGroup.setProid(proid);
			cataGroup.setGroupid(Integer.valueOf(permission));
			rtn = columnDao.saveInfo(cataGroup);
			
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
	
//栏目修改
	public  JSONObject alterProduct(String proName,String topProid,String remark,String images,String permission,String id,String level)
	{
		String rtn = "0";
		JSONObject rs = new JSONObject();
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			//String saveDirectory1 = saveDirectory;
			//String datetime1 = datetime;
			Catagory catagory =(Catagory) columnDao.getCatagoryId(id);
			catagory.setProName(proName);
			catagory.setTopproId(topProid);
			catagory.setRemark(remark);
			catagory.setImages(images);
			System.out.println("level======="+level);
			if(!level.equals("")){
				catagory.setLevel(Integer.valueOf(level));
			}else{
				catagory.setLevel(0);
			}
			rtn = columnDao.updateInfo(catagory);
			
			//CataGroup cataGroup =(CataGroup) columnDao.getCataGroupId(id);
			//cataGroup.setGroupid(Integer.valueOf(permission));
			CataGroup cataGroup = new CataGroup();
			cataGroup.setProid(id);
			cataGroup.setGroupid(Integer.valueOf(permission));
			rtn = columnDao.updateInfo(cataGroup);
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
