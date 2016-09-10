package com.ztenc.oa.proj.service.subject;

import java.sql.ResultSet;
import java.util.List;


public interface SubjectService {
	//public String GroupSearchByKey(int index,int length,String membername,String telno);
	public String SubjectSearch(int index,int length);
	public String deleteSubject(String no);
	public String addSubject(String subjectname,String memo,String level);
	public String modifySubject(String subjectno,String subjectname,String memo,String level);
}
