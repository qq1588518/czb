package com.ztenc.oa.proj.dao.column;

import java.sql.ResultSet;
import java.util.List;

import com.ztenc.oa.proj.bean.CataGroup;
import com.ztenc.oa.proj.bean.Catagory;
import com.ztenc.oa.proj.bean.CatagoryCon;


public interface ColumnConDao {
	
	public String saveInfo(Object obj);
	public CatagoryCon getCatagoryConId(String id);
	public String updateInfo(Object obj);
	public List getlistCatagorycon(String id);
	public List getCatgroupcon(String id);
	//查找栏目信息
	public ResultSet getprotype(String title,String sub_protype,int index,int length);
	public List listPro(String id);
	public List getpro(String proid);
	public List getGroups();
	//删除栏目信息
	public String deleteproType(String id);
	
}
