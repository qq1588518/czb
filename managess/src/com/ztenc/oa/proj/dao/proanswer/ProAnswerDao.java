package com.ztenc.oa.proj.dao.proanswer;

import java.sql.ResultSet;
import java.util.List;

import com.ztenc.oa.proj.bean.CataGroup;
import com.ztenc.oa.proj.bean.Catagory;
import com.ztenc.oa.proj.bean.CatagoryCon;
import com.ztenc.oa.proj.bean.Corporateculture;
import com.ztenc.oa.proj.bean.ProAnswer;
import com.ztenc.oa.proj.bean.ProNews;


public interface ProAnswerDao {
	
	public String saveInfo(Object obj);
	public ProAnswer getProAnswerId(int id);
	public List getTypes();
	public String updateInfo(Object obj);
	public List getCorporate(String id);
	//查找栏目信息
	public ResultSet getprotype(String title,int index,int length);
	//删除栏目信息
	public String deleteproType(String id);
	public List getTypeName(String pro);
	
}
