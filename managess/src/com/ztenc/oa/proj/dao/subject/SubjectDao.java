package com.ztenc.oa.proj.dao.subject;

import java.sql.ResultSet;
import java.util.List;

import com.ztenc.oa.proj.bean.Courseware;
import com.ztenc.oa.proj.bean.Subject;


public interface SubjectDao {
	
	public ResultSet subjectSearch(int index,int length);
	//public ResultSet groupSearchByKey(int index,int length,String membername,String telno);
	public String deleteSubject(String id);
	public String saveInfo(Object obj);
	public String updateInfo(Object obj);
	public Subject getProdctId(int id);
	
}
