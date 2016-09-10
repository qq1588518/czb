package com.ztenc.oa.proj.service.webservice;

import com.ztenc.oa.proj.bean.Member;
import com.ztenc.oa.proj.dao.webservice.SysOtpDao;

public class SysOptServiceImpl implements SysOptService{

	SysOtpDao sysOtpDao;
	
	public void setSysOtpDao(SysOtpDao sysOtpDao) {
		this.sysOtpDao = sysOtpDao;
	}

	/**
	 * 同步更新手机号码
	 * @param oldSimNum 原手机号
	 * @param newSimNum 新手机号
	 * @return
	 */
	public String sysUpdate(String oldSimNum,String newSimNum){
		StringBuffer sb = new StringBuffer();
		Member mem = (Member)sysOtpDao.getMemberInfo(oldSimNum);
		if(mem != null){
			sb.append("ok.");
			mem.setTelno(newSimNum);
			if(sysOtpDao.updateMemberInfo(mem)){
				sb.append("success");
				return sb.toString();
			}else{
				sb.append("defeat");
				return sb.toString();
			}
		}else{
			sb.append("err.");
			sb.append("0");
			return sb.toString();
		}		
	}
	
	/**
	 * 同步删除用户
	 * @param userName 用户名
	 * @return
	 */
	public String SysDelete(String userName){
		StringBuffer sb = new StringBuffer();
		if(sysOtpDao.deleteMember(userName)){
			sb.append("ok.success");
			return sb.toString();
		}else{
			sb.append("ok.defeat");
			return sb.toString();
		}
	}
	
}
