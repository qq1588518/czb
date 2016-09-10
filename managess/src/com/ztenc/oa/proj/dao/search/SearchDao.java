package com.ztenc.oa.proj.dao.search;

import java.sql.ResultSet;
import java.util.List;

import com.ztenc.oa.proj.bean.CataGroup;
import com.ztenc.oa.proj.bean.Catagory;
import com.ztenc.oa.proj.bean.SearchInfo;


public interface SearchDao {
	
	public String saveInfo(Object obj);
	public SearchInfo getSearchId(int id);
	public String updateInfo(Object obj);
	
	public List getProductInfo(String id);
	
	//查找栏目信息
	//public ResultSet getprotype(String id);
	public List getprotype();
	public List getpro();
	//删除栏目信息
	public String deleteproType(String id);
}
