package com.ztenc.oa.proj.service.proanswertype;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ztenc.oa.proj.json.JSONObject;


public interface AnswerTypeService {
	
	//查找栏目信息
	public String getType(int index, int length);
	//删除栏目信息
	public String deleteType(String info);
	
	//添加栏目
	public  JSONObject saveType(String typeName,String remark,String proAddress,String level);
	
	//修改
	public  JSONObject alterType(String typeName,String remark,String proAddress,String id,String level);
}
