package com.ztenc.oa.proj.dao.group;

import java.sql.ResultSet;
import java.util.List;


public interface GroupDao {
	
	public ResultSet groupSearch(int index,int length);
	//public ResultSet groupSearchByKey(int index,int length,String membername,String telno);
	public String deleteGroup(String id);
	public String saveInfo(Object obj);
	public String updateInfo(Object obj);
	
}
