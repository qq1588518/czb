package com.ztenc.oa.proj.dao.advertisement;

import java.sql.ResultSet;
import java.util.List;

import com.ztenc.oa.proj.bean.CataGroup;
import com.ztenc.oa.proj.bean.Catagory;


public interface AdvertisDao {
	
	public String saveInfo(Object obj);
	public Catagory getCatagoryId(String id);
	public CataGroup getCataGroupId(String id);
	public String updateInfo(Object obj);
	public List getProductInfo(String id);
	//查找栏目信息
	public ResultSet getprotype(String id);
	public List getpro();
	public List getGroups();
	//删除栏目信息
	public String deleteproType(String id,String delete);
}
