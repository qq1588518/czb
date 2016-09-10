package com.ztenc.oa.proj.service.courseware;

import java.sql.ResultSet;
import java.util.List;


public interface CoursewareService {
	public List getSubject();
	public String coursewareSearchByKey(int index,int length,String coursewarename,String sub_pro);
	public String deleteCourseware(String no,String url,String img,String basepath);
	
	public List getProductInfo(String id);
	public String deleteFromImages(String dirname,String url);
	public String deleteFromApk(String dirname,String url);
	
	public String addCourseware(String subjectname,String subjectno,String url,String otherurl,String titleimg);
	public String modifyCourseware(String coursewareno,String coursewarename,String subjectno,String url,String otherurl,String titleimg);
}
