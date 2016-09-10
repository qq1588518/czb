package com.ztenc.oa.proj.service.ydzx;

import java.sql.Array;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.ztenc.oa.proj.json.*;
import com.ztenc.oa.proj.util.SafePwd;
import com.ztenc.oa.proj.bean.*;
import com.ztenc.oa.proj.dao.ydzx.SsydzxDao;

public class SsydzxServiceImpl implements SsydzxService {
	
	SsydzxDao ssydzxDao;
	public void setSsydzxDao(SsydzxDao ssydzxDao) {
		this.ssydzxDao = ssydzxDao;
	}

	//初使化界面，查询栏目信息，栏目内容列表
	public String getAll(String id,String index,String length) {
		String rtn = "";
		List list = null;
		List list2 = null;
		JSONArray all = new JSONArray();
		JSONArray subItem = new JSONArray();
		JSONObject jsonobj = new JSONObject(); 
		JSONObject totalobj = new JSONObject();
		Catagory catagory = null;
		try{
			list = ssydzxDao.getAll(id);
			//System.out.println("list====="+list.get(1));
			for(int i=0;i<list.size();i++){
				catagory = (Catagory)list.get(i);
				//System.out.println("id===="+catagory.getProId());
				JSONArray jsonarray = new JSONArray();
				jsonarray.put(catagory.getProId());
				jsonarray.put(catagory.getProName());
				jsonarray.put(catagory.getImages());
				jsonarray.put(catagory.getTopproId());
				all.put(jsonarray);
			}
			
			if(list.size() > 0){
				catagory = (Catagory)list.get(0);
				list2 = ssydzxDao.getItem(catagory.getProId(),index,length);
				//System.out.println("list2================"+list2.get(1));
				for(int j=0;j<list2.size();j++){
					Object[] object = (Object[])list2.get(j);
					System.out.println("object[0]===="+object.length);
					//CatagoryCon catatoryCon = (CatagoryCon)object[0];
					JSONArray jsonarr = new JSONArray();
					jsonarr.put(object[0]);
					jsonarr.put(object[1]);
					jsonarr.put(object[2]);
					jsonarr.put(object[3]);
					jsonarr.put(object[4]);
					jsonarr.put(object[9]);
					//System.out.println("catatoryCon.getId===="+catatoryCon.getId());
					subItem.put(jsonarr);
				}
				
			}
			jsonobj.put("rs", all);
			jsonobj.put("subRs", subItem);
			StringBuilder sb = new StringBuilder();
			rtn = sb.append(jsonobj).toString();
		}catch(Exception e){
			try{
			}catch(Exception ex){
				System.out.println(ex);
			}
			System.out.println(e);
		}finally{
			try{
			}catch(Exception ex){
				System.out.println(ex);
			}
		}
		return rtn;
	}
	 
	
	
	public String getItem(String id,String index,String length) {
		String rtn = "";
		List list2 = null;
		JSONArray all = new JSONArray();
		JSONArray subItem = new JSONArray();
		JSONObject jsonobj = new JSONObject(); 
		JSONObject totalobj = new JSONObject();
		Catagory catagory = null;
		try{
			list2 = ssydzxDao.getItem(id,index,length);
			for(int j=0;j<list2.size();j++){
				Object[] object = (Object[])list2.get(j);
				System.out.println("object[0]===="+object.length);
				JSONArray jsonarr = new JSONArray();
				jsonarr.put(object[0]);
				jsonarr.put(object[1]);
				jsonarr.put(object[2]);
				jsonarr.put(object[3]);
				jsonarr.put(object[4]);
				jsonarr.put(object[9]);
				subItem.put(jsonarr);
			}
			jsonobj.put("subRs", subItem);
			StringBuilder sb = new StringBuilder();
			rtn = sb.append(jsonobj).toString();
		}catch(Exception e){
			try{
			}catch(Exception ex){
				System.out.println(ex);
			}
			System.out.println(e);
		}finally{
			try{
			}catch(Exception ex){
				System.out.println(ex);
			}
		}
		return rtn;
	}
}
