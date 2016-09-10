package com.ztenc.oa.proj.web.webservice;

import com.ztenc.oa.proj.service.webservice.LoninService;
import com.ztenc.oa.proj.service.webservice.SysOptService;

public class ManagessIntefaceImpl implements ManagessInteface{

	LoninService loninService;
	
	SysOptService sysOptService;
	
	
	public void setLoninService(LoninService loninService) {
		this.loninService = loninService;
	}
    
	public void setSysOptService(SysOptService sysOptService) {
		this.sysOptService = sysOptService;
	}

	/**
	 * 手机用户本地无校验码登录 
	 * @param serialNum 流水号
	 * @param IMEINum IMEI号
	 * @param userNo 用户帐号
	 * @param pwd    密码
	 * @return 1.账号存在返回:ok.8位流水号.16位校验码                             
               2.如果流水号有误返回：err.8位流水号.0001
               3.如果imei号有误返回：err.8位流水号.0002                             
               4.其它错误：err.8位流水号.0004
               5.密码错误：err.8位流水号.0005
               6.未购买财智宝：err.8位流水号.0006
               7.账号不存在返回：ok.8位流水号.0
               8.如果24小时内换卡重新绑定账号：err.8位流水号.0003 
               9.远程服务连接失败：err.8位流水号.0007
               
	 */
	public int TestNet(int a,int b){
		int sum = 0;
		sum = a + b;
		System.out.println("sum222==："+sum);
		return sum;
	}
	
	public String doVpdn(String account,String vpdn,String telno){
		return loninService.doVpdn(account,vpdn,telno);
	}
	
	public String verify(String account){
		return loninService.verify(account);
	}
	
	public String MobileUserLogIn(String serialNum,String IMEINum, String userNo, String pwd){
		System.out.println("接收到的IMEI号码："+IMEINum);
		return loninService.MobileLogin(serialNum, IMEINum, userNo, pwd);
	}
	
	/**
	 * 手机用户本地有校验码情况登录
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
	public String hasCodeLogin(String serialNum, String IMEINum, String userNo,String code){
		return loninService.hasCodeLogin(serialNum, IMEINum, userNo, code);
	}
	
	
	
	/**
	 * 系统同步更新手机号码
	 * @param oldSimNum 原号码
	 * @param newSimNum 新号码
	 * @return
	 */
	public String sysUpdate(String oldSimNum,String newSimNum){
		return sysOptService.sysUpdate(oldSimNum, newSimNum);
	}
	
	/**
	 * 系统同步删除用户
	 * @param userName 用户名
	 * @return
	 */
	public String sysDelete(String userName){
		return sysOptService.SysDelete(userName);
	}

	public String getItemCount(String value) {
		return loninService.getItemCount(value);
	}

	public String getAdvertisment() {
		return loninService.getAdvertisment();
	}
}
