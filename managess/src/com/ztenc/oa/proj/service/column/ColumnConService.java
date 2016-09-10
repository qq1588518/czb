package com.ztenc.oa.proj.service.column;

import java.util.List;

import com.ztenc.oa.proj.json.JSONObject;


public interface ColumnConService {
	
	//查找栏目信息
	public String getprotype(String title,String sub_protype,int index,int lenghth);
	public String listPro(String id);
	public List getpro(String proid);
	public List getGroups();
	public List getlistCatagorycon(String id);
	public List getCatgroupcon(String id);
	public String saveContentToFile(String title,String con,String dirname,String filename,int curpageno,String total,String total_filenames,String filepro,String dates);
	public String getContentFromFile(String dirname,String url);
	public String deleteFromFile(String dirname,String url);
	public String deleteFromImages(String dirname,String url);
	//删除栏目信息
	public String deleteproType(String id);
	
	//添加栏目
	public  JSONObject saveProduct(String title,String proAddress,String proid,String subProid,int permission,String con,String datetime1,String id,String conpic);
	
	//修改
	public  JSONObject alterProduct(String title,String proAddress,String proid,String subProid,int permission,String con,String datetime1,String id,String conpic);

	public String saveContentToFileAddPic(String title,String con,String dirname,String filename,int curpageno,String total,String total_filenames,String filepro,String dates,String[] picp);

}
