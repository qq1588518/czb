package com.ztenc.oa.proj.service.msg;

import java.util.List;


public interface MsgService {
	
	public String msgSearch(int index,int length,String title);//留言板
	public String reMsg(String id,String sfhf,String replyId,String replycon,String replyName);//留言板
	public String deleteMsg(String info);//留言板
	public String replyedMsg(String info);//留言板
	public String deleteReplay(String info,String resid);
	public String modifyreplay(String id,String replycon,String replyName);
}
