package com.ztenc.oa.proj.dao.webservice;

import com.ztenc.oa.proj.bean.Member;

public interface SysOtpDao {

	/**
	 * 通过手机号找到原用户对象
	 * @param mobNum 手机号码
	 * @return
	 */
	public Member getMemberInfo(String mobNum);
	
	/**
	 * 修改用户信息
	 * @param mem 用户对象
	 * @return
	 */
	public boolean updateMemberInfo(Member mem);
	
	/**
	 * 删除用户
	 * @param memName 用户名
	 * @return
	 */
	public boolean deleteMember(String memName);
}
