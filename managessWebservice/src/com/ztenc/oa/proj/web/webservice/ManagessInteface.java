package com.ztenc.oa.proj.web.webservice;

public interface ManagessInteface {
	/**
	 * 修改vpdn的值
	 * @param account 会员帐号
	 * @param vpdn vpdn值
	 * @return 
	 */
	public String doVpdn(String account,String vpdn,String telno);
	/**
	 * 判断是否有此用户,三生业务系统调用
	 * @param account 会员帐号
	 * @return 1.帐号存在并且vpdn等于0,返回1
	 * 		   2.帐号不存在返回2
	 * 	       3.帐号存在并且vpdn等于1,返回3
	 */
	public String verify(String account);
	
	public int TestNet(int a,int b);
	/**
	 * 手机用户登录
	 * @param serialNum 流水号
	 * @param simNum 手机号
	 * @return 
	 */
	public String MobileUserLogIn(String serialNum,String IMEINum, String userNo, String pwd);
	
	
	/**
	 * 手机用户本地无校验码情况登录
	 * @param serialNum
	 * @param IMEINum
	 * @param userNo
	 * @return 1.账号不存在返回：ok.8位流水号.0
	 *         2.本地存有此账号并与IMEI号匹配：ok.8位流水号.allow
	 *         3.绑定时间超过24小时,提示用户输入认证信息：ok.8位流水号.putin
	 *         4.如果流水号有误返回：err.8位流水号.0001
               5.如果imei号有误返回：err.8位流水号.0002 
               6.如果24小时内换卡重新绑定账号：err.8位流水号.0003 
	 */
	public String hasCodeLogin(String serialNum, String IMEINum, String userNo,String code);
	
	/**
	 * 系统同步更新手机号码
	 * @param oldSimNum 原号码
	 * @param newSimNum 新号码
	 * @return
	 */
	public String sysUpdate(String oldSimNum,String newSimNum);
	
	
	/**
	 * 系统同步删除用户
	 * @param userName 用户名
	 * @return
	 */
	public String sysDelete(String userName);
	
	
	public String getItemCount(String value);
	
	public String getAdvertisment();
}
