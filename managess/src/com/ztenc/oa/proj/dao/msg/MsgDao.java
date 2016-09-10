package com.ztenc.oa.proj.dao.msg;

import java.sql.ResultSet;
import java.util.List;

import com.ztenc.oa.proj.bean.WlMessageboard;


public interface MsgDao {
	
	public ResultSet msgSearch(int index,int length,String title);//留言板
	public String deleteMsg(String id);//留言板
	public String saveInfo(Object obj);
	public String updateInfo(Object obj);
	public ResultSet replyedMsg(String pageno);//留言板
	public String deleteReplay(String info,String resid);
	public String modifyreplay(String id,String replycon,String replyName,String datetime);
	public WlMessageboard getMsgId(int id);
	
}
