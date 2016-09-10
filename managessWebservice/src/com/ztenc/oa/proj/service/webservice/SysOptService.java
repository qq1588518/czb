package com.ztenc.oa.proj.service.webservice;

public interface SysOptService {

	/**
	 * 同步更新手机号码
	 * @param oldSimNum 原手机号
	 * @param newSimNum 新手机号
	 * @return
	 */
	public String sysUpdate(String oldSimNum,String newSimNum);
	
	/**
	 * 同步删除用户
	 * @param userName 用户名
	 * @return
	 */
	public String SysDelete(String userName);
}
