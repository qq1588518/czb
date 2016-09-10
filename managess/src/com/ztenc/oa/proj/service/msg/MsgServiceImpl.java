package com.ztenc.oa.proj.service.msg;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.ztenc.oa.proj.bean.WlMessageboard;
import com.ztenc.oa.proj.bean.WlReply;
import com.ztenc.oa.proj.bean.WlReplymessage;
import com.ztenc.oa.proj.dao.msg.MsgDao;
import com.ztenc.oa.proj.json.JSONArray;
import com.ztenc.oa.proj.json.JSONObject;
import com.ztenc.oa.proj.util.pagination.PcurrentDataDaoService;

public class MsgServiceImpl implements MsgService{
	
	MsgDao msgdao;
	public void setMsgDao(MsgDao msgdao) {
		this.msgdao = msgdao;
	}
	
	//留言板信息读取，index为搜索开始位置，length为每页显示的长度
	public String msgSearch(int index, int length,String title) {
		String rtn = "";
		ResultSet rs = null;
		
		try{
			rs = msgdao.msgSearch(index,length,title);
			JSONArray all = new JSONArray();
			JSONObject jsonobj = new JSONObject(); 
			JSONObject totalobj = new JSONObject(); 
			while(rs.next()){
				JSONArray jsonarray = new JSONArray();
				String fbId = (String)rs.getString(1);
				String corporation = (String)rs.getString(2);
				String tel = (String)rs.getString(3);
				String FaBMsg = (String)rs.getString(4);
				String faDate = (String)rs.getString(5);
				String type = (String)rs.getString(6);
				String sfhf = (String)rs.getString(7);
				String user	= (String)rs.getString(8);
				String count = (String)rs.getString(9);
				jsonarray.put(fbId);
				jsonarray.put(corporation);
				jsonarray.put(tel);
				jsonarray.put(FaBMsg);
				jsonarray.put(faDate);
				jsonarray.put(type);
				jsonarray.put(sfhf);
				jsonarray.put(count);
				jsonarray.put(user);
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
	
	//回复留言
	public String reMsg(String id,String sfhf,String replyId,String replycon,String replyName){
		String rtn = "0";
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			Integer fbid = Integer.valueOf(id);
			Integer Sfhf = Integer.valueOf(sfhf);
			String ReplyId = replyId;
			String Replycon = replycon;
			String ReplyName = replyName;
			SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd" + " " + "HH:mm:ss");
			Calendar p_cal = Calendar.getInstance();
	        java.util.Date p_date = p_cal.getTime();
			String datetime = tempDate.format(p_date);
			WlReply reply = new WlReply();
			reply.setResId(ReplyId);
			reply.setResadmin(ReplyName);
			reply.setReMsg(Replycon);
			reply.setResdate(datetime);
			rtn = msgdao.saveInfo(reply);
			WlReplymessage replyMsg = new WlReplymessage();
			replyMsg.setFbId(fbid);
			replyMsg.setResId(ReplyId);
			rtn = msgdao.saveInfo(replyMsg);
			WlMessageboard message =(WlMessageboard) msgdao.getMsgId(fbid);
			message.setSfhf(1);
			rtn = msgdao.updateInfo(message);
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
	
	
	//删除留言包括回复信息
	public String deleteMsg(String info){//留言板
		String rtn = "0";
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			String fbid = info;
			rtn = msgdao.deleteMsg(fbid);
			rtn = "1";
			jsonRtn.put("flag",rtn);
		}catch(Exception e){
			rtn = "0";
			System.out.println(e);
		}
		rtn = sb.append(jsonRtn).toString();
		return rtn;
	}
	
	
	public String replyedMsg(String id){//留言板
		 String rtn = "";
			ResultSet rs = null;
			
			try{
				String fbid = id;
				rs = msgdao.replyedMsg(fbid);
				JSONArray all = new JSONArray();
				JSONObject jsonobj = new JSONObject(); 
				JSONObject totalobj = new JSONObject(); 
				while(rs.next()){
					JSONArray jsonarray = new JSONArray();
					String corporation = (String)rs.getString(1);
					String FaBMsg = (String)rs.getString(2);
					String faDate = (String)rs.getString(3);
					String tel = (String)rs.getString(4);
					String type = (String)rs.getString(5);
					String resadmin = (String)rs.getString(6);
					String ReMsg = (String)rs.getString(7);
					String resdate = (String)rs.getString(8);
					String Id = (String)rs.getString(9);
					String resId = (String)rs.getString(10);
					jsonarray.put(corporation);
					jsonarray.put(FaBMsg);
					jsonarray.put(faDate);
					jsonarray.put(tel);
					jsonarray.put(type);
					jsonarray.put(resadmin);
					jsonarray.put(ReMsg);
					jsonarray.put(resdate);
					jsonarray.put(Id);
					jsonarray.put(resId);
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
	
	
	//删除回复信息
	public String deleteReplay(String info,String resid){//留言板
		String rtn = "0";
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			//String fbid = info;
			rtn = msgdao.deleteReplay(info,resid);
			rtn = "1";
			jsonRtn.put("flag",rtn);
		}catch(Exception e){
			rtn = "0";
			System.out.println(e);
		}
		rtn = sb.append(jsonRtn).toString();
		return rtn;
	}
	
	
	
	//修改回复留言
	public String modifyreplay(String id,String replycon,String replyName){
		String rtn = "0";
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			String Id = id;
			String Replycon = replycon;
			String ReplyName = replyName;
			SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd" + " " + "HH:mm:ss");
			Calendar p_cal = Calendar.getInstance();
	        java.util.Date p_date = p_cal.getTime();
			String datetime = tempDate.format(p_date);
			WlReply reply = new WlReply();
			rtn = msgdao.modifyreplay(Id,Replycon,ReplyName,datetime);
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
