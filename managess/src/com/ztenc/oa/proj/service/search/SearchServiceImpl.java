package com.ztenc.oa.proj.service.search;

import java.io.File;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.WebApplicationContext;

import com.ztenc.oa.proj.bean.CataGroup;
import com.ztenc.oa.proj.bean.Catagory;
import com.ztenc.oa.proj.bean.SearchInfo;
import com.ztenc.oa.proj.dao.search.SearchDao;
import com.ztenc.oa.proj.json.JSONArray;
import com.ztenc.oa.proj.json.JSONObject;
import com.ztenc.oa.proj.util.pagination.PcurrentDataDaoService;

public class SearchServiceImpl implements SearchService{
	
	SearchDao searchDao;
	public void setSearchDao(SearchDao searchDao) {
		this.searchDao = searchDao;
	}
	
	public List getpro(){
		List list = null;
		list = searchDao.getpro();
		return list;
	}
	
	//读取所有栏目信息
	public String getprotype(){
		String rtn = "";
		List list = null;
		try{
			list = searchDao.getprotype();
			JSONArray all = new JSONArray();
			JSONObject jsonobj = new JSONObject();
			for(Iterator item = list.iterator();item.hasNext();){
				SearchInfo searchInfo = (SearchInfo)item.next();
				JSONArray jsonarray = new JSONArray();
				jsonarray.put(searchInfo.getId());
				jsonarray.put(searchInfo.getName());
				jsonarray.put(searchInfo.getPic());
				jsonarray.put(searchInfo.getUri());
				jsonarray.put(searchInfo.getColcom());
				all.put(jsonarray);
			}
			jsonobj.put("rs",all);
			StringBuilder sb = new StringBuilder();
			rtn = sb.append(jsonobj).toString(); 
		}catch (Exception ex){
			
		}
		return rtn;
	}
	
	
	
	
	
	
	
	
	
	
	
	
//	//读取所有栏目信息
//	public String getprotype(String topproid){
//		 String rtn = "";
//			ResultSet rs = null;
//			String topproId = topproid;
//			try{
//				rs = searchDao.getprotype(topproId);
//				JSONArray all = new JSONArray();
//				JSONObject jsonobj = new JSONObject(); 
//				JSONObject totalobj = new JSONObject(); 
//				while(rs.next()){
//					JSONArray jsonarray = new JSONArray();
//					String id = (String)rs.getString(1);
//					String name = (String)rs.getString(2);
//					String topId = (String)rs.getString(3);
//					String datetime = (String)rs.getString(4);
//					String imageUrl = (String)rs.getString(5);
//					String groupid = (String)rs.getString(6);
//					String groupname = (String)rs.getString(7);
//					jsonarray.put(id);
//					jsonarray.put(name);
//					jsonarray.put(topId);
//					jsonarray.put(datetime);
//					jsonarray.put(imageUrl);
//					jsonarray.put(groupid);
//					jsonarray.put(groupname);
//					all.put(jsonarray);
//				}
//				jsonobj.put("rs",all);
//				StringBuilder sb = new StringBuilder();
//				rtn = sb.append(jsonobj).toString(); 
//			}catch(Exception e){
//				try{
//				rs.close();
//				}catch(Exception ex){
//					System.out.println(ex);
//				}
//				System.out.println(e);
//			}finally{
//				try{
//					rs.close();
//				}catch(Exception ex){
//					System.out.println(ex);
//				}
//			}
//			return rtn;
//	}
	
	
	
	//删除栏目信息
	public String deleteproType(String info){//
		String rtn = "0";
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			rtn = searchDao.deleteproType(info);
			rtn = "1";
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
		String filepro ="upload/search/images/";
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
		list = searchDao.getProductInfo(id);
		System.out.println("listcon == "+list.size());
		return list;
	}
	
	//栏目添加
	public  JSONObject saveProduct(String proName,String proAddress,String colcom,String addr)
	{
		String rtn = "0";
		JSONObject rs = new JSONObject();
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			SearchInfo searchInfo = new SearchInfo();
			searchInfo.setName(proName);
			searchInfo.setPic(proAddress);
			searchInfo.setColcom(colcom);
			if(addr.equals("")){
				searchInfo.setUri("0");
			}else{
				searchInfo.setUri(addr);
			}
			rtn = searchDao.saveInfo(searchInfo);
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
	public  JSONObject alterProduct(String proName,String proAddress,String colcom,String addr,String id)
	{
		String rtn = "0";
		JSONObject rs = new JSONObject();
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			SearchInfo searchInfo =(SearchInfo) searchDao.getSearchId(Integer.valueOf(id));
			searchInfo.setName(proName);
			searchInfo.setPic(proAddress);
			searchInfo.setColcom(colcom);
			if(addr.equals("")){
				searchInfo.setUri("0");
			}else{
				searchInfo.setUri(addr);
			}
			rtn = searchDao.updateInfo(searchInfo);
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
