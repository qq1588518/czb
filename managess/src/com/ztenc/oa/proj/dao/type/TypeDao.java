package com.ztenc.oa.proj.dao.type;

import java.sql.ResultSet;
import java.util.List;

import com.ztenc.oa.proj.bean.CataGroup;
import com.ztenc.oa.proj.bean.Catagory;
import com.ztenc.oa.proj.bean.Producttype;


public interface TypeDao {
	
	public String saveInfo(Object obj);
	public Producttype getTypeId(int id);
	//public CataGroup getCataGroupId(String id);
	public String updateInfo(Object obj);
	//查找栏目信息
	public ResultSet getType(int index, int length);
	//public List getpro();
	//public List getGroups();
	//删除栏目信息
	public String deleteType(String id);
}
