package com.ztenc.oa.proj.dao.product;

import java.sql.ResultSet;
import java.util.List;

import com.ztenc.oa.proj.bean.CataGroup;
import com.ztenc.oa.proj.bean.Catagory;
import com.ztenc.oa.proj.bean.CatagoryCon;
import com.ztenc.oa.proj.bean.Product;


public interface ProductDao {
	
	public String saveInfo(Object obj);
	public Product getProdctId(String id);
	public String updateInfo(Object obj);
	public List getProductInfo(String id);
	public List getProductPermission(String id);
	public ResultSet getProductList(String name,String typeno,int index,int length);
	public List getGroups();
	public List getTypes();
	public String deleteProduct(String id);
	
}
