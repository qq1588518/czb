package com.ztenc.oa.proj.service.group;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.ztenc.oa.proj.bean.Groups;
import com.ztenc.oa.proj.bean.Member;
import com.ztenc.oa.proj.bean.Member2group;
import com.ztenc.oa.proj.dao.group.*;
import com.ztenc.oa.proj.json.JSONArray;
import com.ztenc.oa.proj.json.JSONObject;
import com.ztenc.oa.proj.util.pagination.PcurrentDataDaoService;
import com.ztenc.oa.proj.util.CUtil;
public class GroupServiceImpl implements GroupService{
	
	GroupDao groupdao;
	public void setGroupDao(GroupDao groupdao) {
		this.groupdao = groupdao;
	}
	
	/*public String MemberSearchByKey(int index,int length,String membername,String telno){
		
		String rtn = "";
		ResultSet rs = null;
		
		try{
			rs = groupdao.memberSearchByKey(index, length,membername,telno);
			JSONArray all = new JSONArray();
			JSONObject jsonobj = new JSONObject(); 
			JSONObject totalobj = new JSONObject(); 
			while(rs.next()){
				JSONArray jsonarray = new JSONArray();
				String memberno = (String)rs.getString(1);
				String machineid = (String)rs.getString(4);
				String password = (String)rs.getString(5);
				String memo = (String)rs.getString(6);
				String groupid = rs.getString(11);
				String groupname = rs.getString(12);
				String _membername = (String)rs.getString(2);
				String _telno = (String)rs.getString(3);
				int total = rs.getInt(8);
				jsonarray.put(memberno);
				jsonarray.put(_membername);
				jsonarray.put(_telno);
				jsonarray.put(machineid);
				jsonarray.put(password);
				jsonarray.put(memo);
				jsonarray.put(total);
				jsonarray.put(groupname);
				jsonarray.put(groupid);
				all.put(jsonarray);
			}
			jsonobj.put("rs",all);
			StringBuilder sb = new StringBuilder();
			rtn = sb.append(jsonobj).toString(); 
		}catch(Exception e){
			try{
			rs.close();
			}catch(Exception ex){
				System.out.println(ex);
			}
			System.out.println(e);
		}finally{
			try{
				rs.close();
			}catch(Exception ex){
				System.out.println(ex);
			}
		}
		return rtn;
	}*/
	
	
	//会员信息读取，index为搜索开始位置，length为每页显示的长度
	public String GroupSearch(int index, int length) {
		String rtn = "";
		ResultSet rs = null;
		
		try{
			rs = groupdao.groupSearch(index, length);
			JSONArray all = new JSONArray();
			JSONObject jsonobj = new JSONObject(); 
			JSONObject totalobj = new JSONObject(); 
			while(rs.next()){
				JSONArray jsonarray = new JSONArray();
				String groupno = (String)rs.getString(1);
				String groupname = (String)rs.getString(2);
				String memo = (String)rs.getString(3);
				int total = rs.getInt(4);
				jsonarray.put(groupno);
				jsonarray.put(groupname);
				jsonarray.put(memo);
				jsonarray.put(total);
				all.put(jsonarray);
			}
			jsonobj.put("rs",all);
			StringBuilder sb = new StringBuilder();
			rtn = sb.append(jsonobj).toString(); 
		}catch(Exception e){
			try{
			rs.close();
			}catch(Exception ex){
				System.out.println(ex);
			}
			System.out.println(e);
		}finally{
			try{
				rs.close();
			}catch(Exception ex){
				System.out.println(ex);
			}
		}
		return rtn;
	}
	
	public String deleteGroup(String no){
		String rtn = "0";
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			rtn = groupdao.deleteGroup(no);
			jsonRtn.put("flag",rtn);
		}catch(Exception e){
			rtn = "0";
			System.out.println(e);
		}
		rtn = sb.append(jsonRtn).toString();
		return rtn;
	}
	
	public String modifyGroup(String groupid,String groupname,String memo){
		String rtn = "0";
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd" + " " + "HH:mm:ss");
			Calendar p_cal = Calendar.getInstance();
	        java.util.Date p_date = p_cal.getTime();
			String datetime = tempDate.format(p_date);
			Groups group = new Groups();
			group.setGroupid(Integer.valueOf(groupid));
			group.setGroupname(groupname);
			group.setMemo(memo);
			rtn = groupdao.updateInfo(group);
			jsonRtn.put("flag",rtn);
			System.out.println("===="+rtn);
		}catch(DataAccessException date){
			rtn = "0";
			try{
				jsonRtn.put("flag",rtn);
			}catch(Exception e){
				System.out.println(e);
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		rtn = sb.append(jsonRtn).toString();
		return rtn;
	}
	public String addGroup(String groupname,String memo){
		String rtn = "0";
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd" + " " + "HH:mm:ss");
			Calendar p_cal = Calendar.getInstance();
	        java.util.Date p_date = p_cal.getTime();
			String datetime = tempDate.format(p_date);
			Groups group = new Groups();
			group.setGroupname(groupname);
			group.setMemo(memo);
			rtn = groupdao.saveInfo(group);
			jsonRtn.put("flag",rtn);
			System.out.println("===="+rtn);
		}catch(DataAccessException date){
			rtn = "0";
			try{
				jsonRtn.put("flag",rtn);
			}catch(Exception e){
				System.out.println(e);
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		rtn = sb.append(jsonRtn).toString();
		return rtn;
	}
}
