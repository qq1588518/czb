package com.ztenc.oa.proj.dao.webservice;

import java.util.List;

import com.ztenc.oa.proj.bean.Advertisment;
import com.ztenc.oa.proj.bean.Member;
import com.ztenc.oa.proj.bean.Member2group;

public interface LoginDao {

	public List getUser(String simNum);
	
	public List getUserByTime(String userNo);
	
	public void saveUser(Member mem);
	
	public void updateUser(Member mem);
	
	public Member getUserByNO(String userNo);
	public List getUserInCode(String userNo,String code);
	
	public void saveGroup(Member2group group);
	public Member getUserByNoAndVPDN(String userNo);
	
	public Member getMember(String id);
	
	public List getAdvertisment();
}
