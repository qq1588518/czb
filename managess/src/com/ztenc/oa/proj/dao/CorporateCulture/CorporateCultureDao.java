package com.ztenc.oa.proj.dao.CorporateCulture;

import java.sql.ResultSet;
import java.util.List;

import com.ztenc.oa.proj.bean.CataGroup;
import com.ztenc.oa.proj.bean.Catagory;
import com.ztenc.oa.proj.bean.CatagoryCon;
import com.ztenc.oa.proj.bean.Corporateculture;


public interface CorporateCultureDao {
	
	public String saveInfo(Object obj);
	public Corporateculture getCorporatecultureId(int id);
	public String updateInfo(Object obj);
	public List getCorporate(String id);
	//查找栏目信息
	public ResultSet getprotype(String title,int index,int length);
	//删除栏目信息
	public String deleteproType(String id);
	
}
