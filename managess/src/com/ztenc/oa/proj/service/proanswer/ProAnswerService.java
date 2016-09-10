package com.ztenc.oa.proj.service.proanswer;

import java.util.List;

import com.ztenc.oa.proj.json.JSONObject;


public interface ProAnswerService {
	
	//获取全部列表
	public String getprotype(String title,int index,int lenghth);
	public List getCorporate(String id);
	public List getTypeName(String pro);
	public List getTypes();
	public String saveContentToFile(String title,String con,String dirname,String filename,int curpageno,String total,String total_filenames,String filepro,String dates);
	public String getContentFromFile(String dirname,String url);
	public String deleteFromFile(String dirname,String url);
	//删除信息
	public String deleteproType(String id);
	//添加
	public  JSONObject saveProduct(String title,String url,String outurl,String datetime1,String typeno);
	//修改
	public  JSONObject alterProduct(String title,String url,String outurl,String datetime1,String typeno,String id);
}
