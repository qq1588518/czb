package com.ztenc.oa.proj.dao.member;

import java.sql.ResultSet;
import java.util.List;

import com.ztenc.oa.proj.bean.Member;


public interface MemberDao {
	
	public ResultSet memberSearch(int index,int length);
	public ResultSet memberSearchByKey(int index,int length,String membername,String telno,String vpdn);
	public String deleteMember(String id);
	public String saveInfo(Object obj);
	public String updateInfo(Object obj);
	public List getGroup();
	public Member getVPDN(String memberno);
	public Member memberSearchByAcc(String userAcc);
	public ResultSet memberSearchByExpert(String useracc,String telno,String vpdn);
	public Member getMemberId(String id);
	
}
