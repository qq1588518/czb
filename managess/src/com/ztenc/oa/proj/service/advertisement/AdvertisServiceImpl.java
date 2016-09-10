package com.ztenc.oa.proj.service.advertisement;

import java.io.File;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.WebApplicationContext;

import com.ztenc.oa.proj.bean.Advertisment;
import com.ztenc.oa.proj.bean.CataGroup;
import com.ztenc.oa.proj.bean.Catagory;
import com.ztenc.oa.proj.bean.WlMessageboard;
import com.ztenc.oa.proj.dao.advertisement.AdvertisDao;
import com.ztenc.oa.proj.dao.column.ColumnDao;
import com.ztenc.oa.proj.json.JSONArray;
import com.ztenc.oa.proj.json.JSONObject;
import com.ztenc.oa.proj.util.pagination.PcurrentDataDaoService;

public class AdvertisServiceImpl implements AdvertisService{
	
	AdvertisDao advertisDao;
	public void setAdvertisDao(AdvertisDao advertisDao) {
		this.advertisDao = advertisDao;
	}
	
	public List getpro(){
		List list = null;
		list = advertisDao.getpro();
		return list;
	}
	
	public List getGroups(){
		List list = null;
		list = advertisDao.getGroups();
		return list;
	}
	
	//读取所有栏目信息
	public String getprotype(String topproid){
		 String rtn = "";
			ResultSet rs = null;
			String topproId = topproid;
			try{
				rs = advertisDao.getprotype(topproId);
				JSONArray all = new JSONArray();
				JSONObject jsonobj = new JSONObject(); 
				JSONObject totalobj = new JSONObject(); 
				while(rs.next()){
					JSONArray jsonarray = new JSONArray();
					String id = (String)rs.getString(1);
					String name = (String)rs.getString(2);
					String imageUrl = (String)rs.getString(3);
					String nome = (String)rs.getString(4);
					jsonarray.put(id);
					jsonarray.put(name);
					jsonarray.put(imageUrl);
					jsonarray.put(nome);
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
			rtn = advertisDao.deleteproType(info,delete);
			//rtn = "1";
			jsonRtn.put("flag",rtn);
		}catch(Exception e){
			rtn = "0";
			System.out.println(e);
		}
		rtn = sb.append(jsonRtn).toString();
		return rtn;
	}
	
	public String deleteFromImages(String dirname, String url) {
		String rtn = "0";
		String filepro ="upload/advertis/images/";
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
		list = advertisDao.getProductInfo(id);
		System.out.println("listcon == "+list.size());
		return list;
	}
	
	
	//栏目添加
	public  JSONObject saveProduct(String proName,String images,String remark)
	{
		String rtn = "0";
		JSONObject rs = new JSONObject();
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			//String saveDirectory1 = saveDirectory;
			//String datetime1 = datetime;
			Advertisment advertisment = new Advertisment();
			//catagory.setProId(proid);
			advertisment.setAdvertisName(proName);
			//catagory.setTopproId(topProid);
			advertisment.setNome(remark);
			advertisment.setPicAdr(images);
			//System.out.println("level======="+level);
//			if(!level.equals("")){
//				catagory.setLevel(Integer.valueOf(level));
//			}else{
//				catagory.setLevel(0);
//			}
			rtn = advertisDao.saveInfo(advertisment);
			
//			CataGroup cataGroup = new CataGroup();
//			cataGroup.setProid(proid);
//			cataGroup.setGroupid(Integer.valueOf(permission));
//			rtn = advertisDao.saveInfo(cataGroup);
			
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
			Catagory catagory =(Catagory) advertisDao.getCatagoryId(id);
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
			rtn = advertisDao.updateInfo(catagory);
			
			//CataGroup cataGroup =(CataGroup) columnDao.getCataGroupId(id);
			//cataGroup.setGroupid(Integer.valueOf(permission));
			CataGroup cataGroup = new CataGroup();
			cataGroup.setProid(id);
			cataGroup.setGroupid(Integer.valueOf(permission));
			rtn = advertisDao.updateInfo(cataGroup);
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
