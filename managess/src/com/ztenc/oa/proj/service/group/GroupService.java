package com.ztenc.oa.proj.service.group;

import java.sql.ResultSet;
import java.util.List;


public interface GroupService {
	//public String GroupSearchByKey(int index,int length,String membername,String telno);
	public String GroupSearch(int index,int length);
	public String deleteGroup(String no);
	public String addGroup(String groupname,String memo);
	public String modifyGroup(String groupno,String groupname,String memo);
}
