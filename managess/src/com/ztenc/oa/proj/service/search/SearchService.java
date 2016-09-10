package com.ztenc.oa.proj.service.search;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ztenc.oa.proj.json.JSONObject;


public interface SearchService {
	
	//查找栏目信息
	public String getprotype();
	public List getpro();
	
	public List getProductInfo(String id);
	public String deleteFromImages(String dirname,String url);
	
	//删除栏目信息
	public String deleteproType(String info);
	
	//添加栏目
	public  JSONObject saveProduct(String proName,String proAddress,String colcom,String addr);
	
	//修改
	public  JSONObject alterProduct(String proName,String proAddress,String colcom,String addr,String id);
}
