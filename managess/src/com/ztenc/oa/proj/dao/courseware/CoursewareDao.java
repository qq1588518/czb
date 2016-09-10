package com.ztenc.oa.proj.dao.courseware;

import java.sql.ResultSet;
import java.util.List;

import com.ztenc.oa.proj.bean.Courseware;
import com.ztenc.oa.proj.bean.Product;


public interface CoursewareDao {
	
	public ResultSet coursewareSearchByKey(int index,int length,String coursewarename,String sub_protype);
	public String deleteCourseware(String id);
	public String saveInfo(Object obj);
	public String updateInfo(Object obj);
	public List getSubject();
	public Courseware getProdctId(String id);
	public List getlistcoursegroup(String id);
	
	public List getProductInfo(String id);
}
