package com.ztenc.oa.proj.service.advertisement;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ztenc.oa.proj.json.JSONObject;


public interface AdvertisService {
	
	//查找栏目信息
	public String getprotype(String id);
	public List getpro();
	public List getGroups();
	public List getProductInfo(String id);
	public String deleteFromImages(String dirname,String url);
	
	//删除栏目信息
	public String deleteproType(String info,String delete);
	
	//添加栏目
	public  JSONObject saveProduct(String proName,String proAddress,String remark);
	
	//修改
	public  JSONObject alterProduct(String proName,String topProid,String remark,String proAddress,String permission,String id,String level);
}
