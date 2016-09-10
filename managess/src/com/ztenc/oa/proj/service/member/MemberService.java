package com.ztenc.oa.proj.service.member;

import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ztenc.oa.proj.json.JSONObject;


public interface MemberService {
	public List getGroup();
	public String MemberSearchByKey(int index,int length,String membername,String telno,String vpdn);
	public String MemberSearch(int index,int length);//留言板
	public String deleteMember(String no);//留言板
	public String addMember(String username,String telno,String machineid,String groupno,String vpdn);
	public String modifyMember(String memberno,String username,String useracc,String imsi,String groupno,String vpdn,String telno);
	public JSONObject readexcel(HttpServletRequest request,java.io.InputStream is);
	public boolean getExcel(OutputStream   output,HttpServletRequest request,JSONObject obj);
}
