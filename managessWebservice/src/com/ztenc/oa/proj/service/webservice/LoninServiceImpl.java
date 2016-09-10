package com.ztenc.oa.proj.service.webservice;

import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;

import com.ztenc.oa.proj.bean.Advertisment;
import com.ztenc.oa.proj.bean.Member;
import com.ztenc.oa.proj.bean.Member2group;
import com.ztenc.oa.proj.bean.Servicecount;
import com.ztenc.oa.proj.dao.webservice.GetCountDao;
import com.ztenc.oa.proj.dao.webservice.LoginDao;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.codehaus.xfire.client.Client;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
public class LoninServiceImpl implements LoninService {

	LoginDao loginDaow;

	public void setLoginDaow(LoginDao loginDaow) {
		this.loginDaow = loginDaow;
	}
	
	GetCountDao getCountDao;

	public void setGetCountDao(GetCountDao getCountDao) {
		this.getCountDao = getCountDao;
	}
	
	private boolean send(String telno,String content){
		boolean res = false;
		try {
			Client ser = new Client(
					new URL(
							"http://192.168.0.245:8069/sms/service/send.ws?wsdl"));
			Object[] o = ser.invoke("send",
					new String[] { telno, content });
			for (int i = 0; i < o.length; i++) {
				res = (Boolean) o[i];
			}
			return res;
		} catch (ConnectException e) {
			e.printStackTrace();
			return res;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return res;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return res;
		}
	}
	
	/**
	 * 手机用户登录业务处理方法
	 * 
	 * @param serialNum
	 *            8位的数字流水号
	 * @param simNum
	 *            手机号码
	 * @return
	 */
	public boolean testVisit(){
		boolean flag = false;
		int res = this.getService("","");
//		int res = 0;
		if(res==6 || res==5){
			
		}else{
			flag = true;
		}
		return flag;
	}
	
	public String doVpdn(String account,String vpdn,String telno){
		String rtn = "0";
		String content = "";
		try{
			Member member = loginDaow.getUserByNoAndVPDN(account);
			if(member!=null){
				SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd" + " " + "HH:mm:ss");
				Calendar p_cal = Calendar.getInstance();
		        java.util.Date p_date = p_cal.getTime();
				String old_vpdn = member.getVpdn();
				Date old_enabledate = member.getEnableDate();
				
				if(vpdn.equals("0")){
					if(member.getTelno().equals(telno)){
						content = "您好，您的视频服务已关闭！";
						member.setVpdn("0");
						if(!vpdn.equals(old_vpdn)){
								member.setEnableDate(old_enabledate);
								member.setDisableDate(p_date);
						}
						loginDaow.updateUser(member);
						send(telno,content);
					}else{
						return "2";
					}
				}else if(vpdn.equals("1")){
					content = "欢迎您使用三生财智宝视频包月服务，现服务已开通，每月将收取￥30元包月费，您可使用2G视频包月流量。请重新登陆财智宝。";
					if(member.getVpdn().equals("0")){
						member.setVpdn("1");
						member.setTelno(telno);
						if(!vpdn.equals(old_vpdn)){
							member.setEnableDate(p_date);
							member.setDisableDate(null);
						}
						loginDaow.updateUser(member);
						send(telno,content);
							
					}else{
						return "3";
					}
				}else{
					return "4";
				}
			}else{
				rtn = "1";
			}
		}catch(Exception e){
			rtn = "2";
		}
		return rtn;
	}
	
	public String verify(String account){
		String rtn = "2";
		Member mem = loginDaow.getUserByNoAndVPDN(account);
		if(mem!=null){
			rtn = "1";
		}
		System.out.println("rtn==="+rtn);
		return rtn;
	}
	
	//登录
	public String MobileLogin(String serialNum, String IMEINum, String userNo,
			String pwd) {
		String userName = "";
		String serCode = "";
		StringBuffer sb = new StringBuffer();
		List list = loginDaow.getUser(userNo);
		String getcount = getCount();
		//HTTP
		/*sb.append("ok.");
		sb.append("666666");
		sb.append(".");
		sb.append("888888");
		return sb.toString();*/
		// 验证流水号格式和手机号格式
		if (!Util.isNumber(IMEINum) || IMEINum.length() != 15) {
			// 手机号码有误
			sb.append("err.");
			sb.append(serialNum);
			sb.append(".");
			sb.append(Util.ERR_SIM);
			return sb.toString();
		}
		if (!Util.checkSerialNumber(8, serialNum)) {
			// 流水号有误误
			sb.append("err.");
			sb.append(serialNum);
			sb.append(".");
			sb.append(Util.ERR_SERI);
			return sb.toString();
		}
		// if (list.size() != 0) {// 本地存在该帐号
		// // 判断IMEI号是否与该帐号匹配
		// boolean isExist = false;
		// for (Iterator itr = list.iterator(); itr.hasNext();) {
		// Member mem = (Member) itr.next();
		// if (IMEINum.equals(mem.getImeinum())) {
		// userName = mem.getMembername();
		// serCode = mem.getCode();
		// isExist = true;
		// }
		// }
		// if (isExist) {// IMEI号与本地账号匹配
		// sb.append("ok.");
		// sb.append(serialNum);
		// sb.append(".");
		// sb.append(serCode);
		// return sb.toString();
		// } else {
		// Date now = new Date();
		// Date loginDate = new Date();
		// List l = loginDaow.getUserByTime(userNo);
		// if (l.size() != 0) {
		// for (Iterator itr = l.iterator(); itr.hasNext();) {
		// Member mem = (Member) itr.next();
		// loginDate = mem.getLoginDate();
		// }
		//
		// long mint = (Util
		// .fromDateStringToLong(Util.TimeFomart(now)) - Util
		// .fromDateStringToLong(Util.TimeFomart(loginDate))) / 1000;
		// int hor = (int) mint / 3600;
		// System.out.println("相差小时数：" + hor);
		// if (hor <= 24) {// 账号存在但IMEI号与之不匹配且上一次绑定时间小于24小时
		// sb.append("err.");
		// sb.append(serialNum);
		// sb.append(".");
		// sb.append(Util.ERR_BIND);
		// return sb.toString();
		// } else {
		boolean isExist = false;
		if (list.size() != 0) {// 本地存在该帐号
			// 判断IMEI号是否与该帐号匹配

			for (Iterator itr = list.iterator(); itr.hasNext();) {
				Member mem = (Member) itr.next();
				if (IMEINum.equals(mem.getImeinum())) {
					userName = mem.getMembername();
					serCode = mem.getCode();
					isExist = true;
				}
			}
		} else {
			// sb.append("ok.");
			// sb.append(serialNum);
			// sb.append(".0");
			// return sb.toString();
			// 调用外部接口，查到后插入本表，未查到则返回无此用户，用户无权登录
			/*if (userNo.equals("test1")) {
				// 先判断本地是否已存在该用户
				Member member = loginDaow.getUserByNO(userNo);
				if (member != null) {
					// 更新绑定
					Member mem = member;
					mem.setImeinum(IMEINum);
					mem.setLoginDate(new Date());
					mem.setMembername(userNo);
					mem.setPwd(pwd);
					mem.setUserAcc(userNo);
					String seriCode = Util.createBillNo(16);
					mem.setCode(seriCode);
					loginDaow.updateUser(mem);
					sb.append("ok.");
					sb.append(serialNum);
					sb.append(".");
					sb.append(seriCode);
					System.out.println("===========1" + sb.toString());
					return sb.toString();
				} else {
					// 将参数插入表中
					Member mem = new Member();
					mem.setMemberno(Util.createBillNo(16));
					mem.setImeinum(IMEINum);
					mem.setLoginDate(new Date());
					mem.setMembername(userNo);
					mem.setPwd(pwd);
					mem.setUserAcc(userNo);
					String seriCode = Util.createBillNo(16);
					mem.setCode(seriCode);
					loginDaow.saveUser(mem);
					sb.append("ok.");
					sb.append(serialNum);
					sb.append(".");
					sb.append(seriCode);
					System.out.println("===========1" + sb.toString());
					return sb.toString();
				}
			} else {*/
				int res = this.getService(userNo, pwd);
//				int res = 0;
				System.out.println("res in MobileLogin22 ==="+res);
				if (res == 0) {// 成功
					// 先判断本地是否已存在该用户
					Member member = loginDaow.getUserByNO(userNo);
					if (member != null) {
						// 更新绑定
						Member mem = member;
						mem.setImeinum(IMEINum);
						mem.setLoginDate(new Date());
						mem.setMembername(userNo);
						mem.setPwd(pwd);
						mem.setUserAcc(userNo);
						String seriCode = Util.createBillNo(16);
						mem.setCode(seriCode);
						//mem.setTelno("18657400000");
						loginDaow.updateUser(mem);
						sb.append("ok.");
						sb.append(serialNum);
						sb.append(".");
						sb.append(seriCode);
						sb.append(".");
						sb.append(mem.getVpdn());
						sb.append(".");
						sb.append(getcount);
						System.out.println("===========1" + sb.toString());
						return sb.toString();
					} else {
						// 将参数插入表中
						Member mem = new Member();
						String memberno = Util.createBillNo(16);
						mem.setMemberno(memberno);
						mem.setImeinum(IMEINum);
						mem.setLoginDate(new Date());
						mem.setMembername(userNo);
						mem.setPwd(pwd);
						mem.setUserAcc(userNo);
						mem.setTelno("18657400000");
						String seriCode = Util.createBillNo(16);
						mem.setCode(seriCode);
						mem.setVpdn("0");
						loginDaow.saveUser(mem);
						Member2group group = new Member2group();
						group.setMemberno(memberno);
						group.setGroupno("1");
						loginDaow.saveGroup(group);
						sb.append("ok.");
						sb.append(serialNum);
						sb.append(".");
						sb.append(seriCode);
						sb.append(".");
						sb.append(mem.getVpdn());
						sb.append(".");
						sb.append(getcount);
						System.out.println("===========1" + sb.toString());
						return sb.toString();
					}
				} else if (res == 1) {// 账号不存在
					sb.append("ok.");
					sb.append(serialNum);
					sb.append(".0");
					System.out.println("===========1" + sb.toString());
					return sb.toString();
				} else if (res == 2) {// 密码错误
					sb.append("err.");
					sb.append(serialNum);
					sb.append(".");
					sb.append(Util.ERR_PWD);
					return sb.toString();
				} else if (res == 3) {// 未购买
					sb.append("err.");
					sb.append(serialNum);
					sb.append(".");
					sb.append(Util.ERR_NOTBUY);
					return sb.toString();
				} else if (res == 4) {// 其它错误
					sb.append("err.");
					sb.append(serialNum);
					sb.append(".");
					sb.append(Util.ERR_OTHER);
					return sb.toString();
				} else if (res == 5) {// 其它错误
					sb.append("err.");
					sb.append(serialNum);
					sb.append(".");
					sb.append(Util.ERR_OTHER);
					return sb.toString();
				} else if (res == 6) {// 远程WEBSERVICE连接失败
					sb.append("err.");
					sb.append(serialNum);
					sb.append(".");
					sb.append(Util.ERR_CANTCON);
					return sb.toString();
				} else {
					sb.append("err.");
					sb.append(serialNum);
					sb.append(".");
					sb.append(Util.ERR_OTHER);
					return sb.toString();
				}
			}
//		}
		if (isExist) {// IMEI号与本地账号匹配
		// 调用外部接口，查到后插入本表，未查到则返回无此用户，用户无权登录
			/*if (userNo.equals("test1")) {
				// 先判断本地是否已存在该用户
				Member member = loginDaow.getUserByNO(userNo);
				if (member != null) {
					// 更新绑定
					Member mem = member;
					mem.setImeinum(IMEINum);
					mem.setLoginDate(new Date());
					mem.setMembername(userNo);
					mem.setPwd(pwd);
					mem.setUserAcc(userNo);
					String seriCode = Util.createBillNo(16);
					mem.setCode(seriCode);
					loginDaow.updateUser(mem);
					sb.append("ok.");
					sb.append(serialNum);
					sb.append(".");
					sb.append(seriCode);
					System.out.println("===========1" + sb.toString());
					return sb.toString();
				} else {
					// 将参数插入表中
					Member mem = new Member();
					mem.setMemberno(Util.createBillNo(16));
					mem.setImeinum(IMEINum);
					mem.setLoginDate(new Date());
					mem.setMembername(userNo);
					mem.setPwd(pwd);
					mem.setUserAcc(userNo);
					String seriCode = Util.createBillNo(16);
					mem.setCode(seriCode);
					loginDaow.saveUser(mem);
					sb.append("ok.");
					sb.append(serialNum);
					sb.append(".");
					sb.append(seriCode);
					System.out.println("===========1" + sb.toString());
					return sb.toString();
				}
			} else {*/
		        //modify by Tim 20110812
				//int res = this.getService(userNo, pwd);
				int res = 0;
				System.out.println("res in MobileLogin ==="+res);
				if (res == 0) {// 成功
					// 先判断本地是否已存在该用户
					Member member = loginDaow.getUserByNO(userNo);
					if (member != null) {
						// 更新绑定
						Member mem = member;
						mem.setImeinum(IMEINum);
						mem.setLoginDate(new Date());
						mem.setMembername(userNo);
						mem.setPwd(pwd);
						mem.setUserAcc(userNo);
						String seriCode = Util.createBillNo(16);
						mem.setCode(seriCode);
						loginDaow.updateUser(mem);
						sb.append("ok.");
						sb.append(serialNum);
						sb.append(".");
						sb.append(seriCode);
						sb.append(".");
						sb.append(mem.getVpdn());
						sb.append(".");
						sb.append(getcount);
						System.out.println("===========1" + sb.toString());
						return sb.toString();
					} else {
						// 将参数插入表中
						Member mem = new Member();
						mem.setMemberno(Util.createBillNo(16));
						mem.setImeinum(IMEINum);
						mem.setLoginDate(new Date());
						mem.setMembername(userNo);
						mem.setPwd(pwd);
						mem.setUserAcc(userNo);
						mem.setTelno("18657400000");
						mem.setVpdn("0");
						String seriCode = Util.createBillNo(16);
						mem.setCode(seriCode);
						loginDaow.saveUser(mem);
						sb.append("ok.");
						sb.append(serialNum);
						sb.append(".");
						sb.append(seriCode);
						sb.append(".");
						sb.append(mem.getVpdn());
						sb.append(".");
						sb.append(getcount);
						System.out.println("===========1" + sb.toString());
						return sb.toString();
					}
				} else if (res == 1) {// 账号不存在
					sb.append("ok.");
					sb.append(serialNum);
					sb.append(".0");
					System.out.println("===========1" + sb.toString());
					return sb.toString();
				} else if (res == 2) {// 密码错误
					sb.append("err.");
					sb.append(serialNum);
					sb.append(".");
					sb.append(Util.ERR_PWD);
					return sb.toString();
				} else if (res == 3) {// 未购买
					sb.append("err.");
					sb.append(serialNum);
					sb.append(".");
					sb.append(Util.ERR_NOTBUY);
					return sb.toString();
				} else if (res == 4) {// 其它错误
					sb.append("err.");
					sb.append(serialNum);
					sb.append(".");
					sb.append(Util.ERR_OTHER);
					return sb.toString();
				} else if (res == 5) {// 其它错误
					sb.append("err.");
					sb.append(serialNum);
					sb.append(".");
					sb.append(Util.ERR_OTHER);
					return sb.toString();
				} else if (res == 6) {// 远程WEBSERVICE连接失败
					sb.append("err.");
					sb.append(serialNum);
					sb.append(".");
					sb.append(Util.ERR_CANTCON);
					return sb.toString();
				} else {
					sb.append("err.");
					sb.append(serialNum);
					sb.append(".");
					sb.append(Util.ERR_OTHER);
					return sb.toString();
				}
//			}
		} else {

			Date loginDate = new Date();
			List l = loginDaow.getUserByTime(userNo);
			if (l.size() != 0) {
				for (Iterator itr = l.iterator(); itr.hasNext();) {
					Member mem = (Member) itr.next();
					loginDate = mem.getLoginDate();
				}

				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				long t = 0;
				try {
					Date now = df.parse(Util.TimeFomart(new Date()));
					Date date = df.parse(Util.TimeFomart(loginDate));
					t = now.getTime() - date.getTime();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// long t=now.getTime()-date.getTime();
				long day = t / (24 * 60 * 60 * 1000);
				long hor = t / (60 * 60 * 1000);
				long min = ((t / (60 * 1000)) - day * 24 * 60 - hor * 60);
				long s = (t / 1000 - day * 24 * 60 * 60 - hor * 60 * 60 - min * 60);
				System.out.println("" + day + "天" + hor + "小时" + min + "分" + s
						+ "秒");

				System.out.println("相差小时数：" + hor);
				if (hor <= 24) {// 账号存在但IMEI号与之不匹配且上一次绑定时间小于24小时
					sb.append("err.");
					sb.append(serialNum);
					sb.append(".");
					sb.append(Util.ERR_BIND);
					return sb.toString();
				} else {
					// 调用外部接口，查到后插入本表，未查到则返回无此用户，用户无权登录
					/*if (userNo.equals("test1")) {
						// 先判断本地是否已存在该用户
						Member member = loginDaow.getUserByNO(userNo);
						if (member != null) {
							// 更新绑定
							Member mem = member;
							mem.setImeinum(IMEINum);
							mem.setLoginDate(new Date());
							mem.setMembername(userNo);
							mem.setPwd(pwd);
							mem.setUserAcc(userNo);
							String seriCode = Util.createBillNo(16);
							mem.setCode(seriCode);
							loginDaow.updateUser(mem);
							sb.append("ok.");
							sb.append(serialNum);
							sb.append(".");
							sb.append(seriCode);
							System.out.println("===========1" + sb.toString());
							return sb.toString();
						} else {
							// 将参数插入表中
							Member mem = new Member();
							mem.setMemberno(Util.createBillNo(8));
							mem.setImeinum(IMEINum);
							mem.setLoginDate(new Date());
							mem.setMembername(userNo);
							mem.setPwd(pwd);
							mem.setUserAcc(userNo);
							String seriCode = Util.createBillNo(16);
							mem.setCode(seriCode);
							loginDaow.saveUser(mem);
							sb.append("ok.");
							sb.append(serialNum);
							sb.append(".");
							sb.append(seriCode);
							System.out.println("===========1" + sb.toString());
							return sb.toString();
						}
					} else {*/
						//modify by Tim 20110918
//						int res = this.getService(userNo, pwd);
						int res = 0;
						if (res == 0) {// 成功
							// 先判断本地是否已存在该用户
							Member member = loginDaow.getUserByNO(userNo);
							if (member != null) {
								// 更新绑定
								Member mem = member;
								mem.setImeinum(IMEINum);
								mem.setLoginDate(new Date());
								mem.setMembername(userNo);
								mem.setPwd(pwd);
								mem.setUserAcc(userNo);
								String seriCode = Util.createBillNo(16);
								mem.setCode(seriCode);
								loginDaow.updateUser(mem);
								sb.append("ok.");
								sb.append(serialNum);
								sb.append(".");
								sb.append(seriCode);
								sb.append(".");
								sb.append(mem.getVpdn());
								sb.append(".");
								sb.append(getcount);
								System.out.println("===========1"
										+ sb.toString());
								return sb.toString();
							} else {
								// 将参数插入表中
								Member mem = new Member();
								mem.setMemberno(Util.createBillNo(16));
								mem.setImeinum(IMEINum);
								mem.setLoginDate(new Date());
								mem.setMembername(userNo);
								mem.setPwd(pwd);
								mem.setUserAcc(userNo);
								String seriCode = Util.createBillNo(16);
								mem.setCode(seriCode);
								mem.setTelno("18657400000");
								mem.setVpdn("0");
								loginDaow.saveUser(mem);
								sb.append("ok.");
								sb.append(serialNum);
								sb.append(".");
								sb.append(seriCode);
								sb.append(".");
								sb.append(mem.getVpdn());
								sb.append(".");
								sb.append(getcount);
								System.out.println("===========1"
										+ sb.toString());
								return sb.toString();
							}
						} else if (res == 1) {// 账号不存在
							sb.append("ok.");
							sb.append(serialNum);
							sb.append(".0");
							System.out.println("===========1" + sb.toString());
							return sb.toString();
						} else if (res == 2) {// 密码错误
							sb.append("err.");
							sb.append(serialNum);
							sb.append(".");
							sb.append(Util.ERR_PWD);
							return sb.toString();
						} else if (res == 3) {// 未购买
							sb.append("err.");
							sb.append(serialNum);
							sb.append(".");
							sb.append(Util.ERR_NOTBUY);
							return sb.toString();
						} else if (res == 4) {// 其它错误
							sb.append("err.");
							sb.append(serialNum);
							sb.append(".");
							sb.append(Util.ERR_OTHER);
							return sb.toString();
						} else if (res == 5) {// 其它错误
							sb.append("err.");
							sb.append(serialNum);
							sb.append(".");
							sb.append(Util.ERR_OTHER);
							return sb.toString();
						} else if (res == 6) {// 远程WEBSERVICE连接失败
							sb.append("err.");
							sb.append(serialNum);
							sb.append(".");
							sb.append(Util.ERR_CANTCON);
							return sb.toString();
						} else {
							sb.append("err.");
							sb.append(serialNum);
							sb.append(".");
							sb.append(Util.ERR_OTHER);
							return sb.toString();
						}
//					}
				}
			} else {
				sb.append("err.");
				sb.append(serialNum);
				sb.append(".");
				sb.append(Util.ERR_OTHER);
				return sb.toString();
			}
		}
		// }
		// } else {
		// sb.append("err.");
		// sb.append(serialNum);
		// sb.append(".");
		// sb.append(Util.ERR_OTHER);
		// return sb.toString();
		// }
		// }
		// } else {
		// // 调用外部接口，查到后插入本表，未查到则返回无此用户，用户无权登录
		// int res = this.getService(userName, pwd);
		// if (res == 0) {//成功
		// //将参数插入表中
		// Member mem = new Member();
		// mem.setImeinum(IMEINum);
		// mem.setLoginDate(new Date());
		// mem.setMembername(userNo);
		// mem.setPwd(pwd);
		// mem.setUserAcc(userNo);
		// String seriCode = Util.createBillNo(16);
		// mem.setCode(seriCode);
		// loginDaow.saveUser(mem);
		// sb.append("ok.");
		// sb.append(serialNum);
		// sb.append(".");
		// sb.append(seriCode);
		// System.out.println("===========1" + sb.toString());
		// return sb.toString();
		// }else if(res == 1){//账号不存在
		// sb.append("ok.");
		// sb.append(serialNum);
		// sb.append(".0");
		// System.out.println("===========1" + sb.toString());
		// return sb.toString();
		// }else if(res == 2){//密码错误
		// sb.append("err.");
		// sb.append(serialNum);
		// sb.append(".");
		// sb.append(Util.ERR_PWD);
		// return sb.toString();
		// }else if(res == 3){//未购买
		// sb.append("err.");
		// sb.append(serialNum);
		// sb.append(".");
		// sb.append(Util.ERR_NOTBUY);
		// return sb.toString();
		// }else if(res == 4){//其它错误
		// sb.append("err.");
		// sb.append(serialNum);
		// sb.append(".");
		// sb.append(Util.ERR_OTHER);
		// return sb.toString();
		// }else if(res == 5){//其它错误
		// sb.append("err.");
		// sb.append(serialNum);
		// sb.append(".");
		// sb.append(Util.ERR_OTHER);
		// return sb.toString();
		// }else{
		// sb.append("err.");
		// sb.append(serialNum);
		// sb.append(".");
		// sb.append(Util.ERR_OTHER);
		// return sb.toString();
		// }
		//		
		// }

	}

	/**
	 * 终端本地存有验证码时
	 * 
	 * @param serialNum
	 * @param IMEINum
	 * @param userNo
	 * @return
	 */
	public String hasCodeLogin(String serialNum, String IMEINum, String userNo,String code) {
		String userName = "";
		String serCode = "";
		StringBuffer sb = new StringBuffer();
		Member _member = null;
		List list = loginDaow.getUserInCode(userNo,code);
		String getcount = getCount();
		// 验证流水号格式和手机号格式
		if (!Util.isNumber(IMEINum) || IMEINum.length() != 15) {
			// 手机号码有误
			sb.append("err.");
			sb.append(serialNum);
			sb.append(".");
			sb.append(Util.ERR_SIM);
			return sb.toString();
		}
		if (!Util.checkSerialNumber(8, serialNum)) {
			// 流水号有误误
			sb.append("err.");
			sb.append(serialNum);
			sb.append(".");
			sb.append(Util.ERR_SERI);
			return sb.toString();
		}
		boolean isExist = false;
		if (list.size() != 0) {// 本地存在该帐号
			// 判断IMEI号是否与该帐号匹配

			for (Iterator itr = list.iterator(); itr.hasNext();) {
				Member mem = (Member) itr.next();
				_member = mem;
				if (IMEINum.equals(mem.getImeinum())) {
					userName = mem.getMembername();
					serCode = mem.getCode();
					isExist = true;
				}
			}
		} else {
			sb.append("err.");
			sb.append(serialNum);
			sb.append(".");
			sb.append(Util.ERR_CODE);
			return sb.toString();
		}
		if (isExist) {// IMEI号与本地账号匹配
			sb.append("ok.");
			sb.append(serialNum);
			sb.append(".");
			sb.append(Util.OK_ALLOW_LOGIN);
			if(_member!=null){
				sb.append(".");
				sb.append(_member.getVpdn());
			}
			sb.append(".");
			sb.append(getcount);
			return sb.toString();
		} else {

			Date loginDate = new Date();
			List l = loginDaow.getUserByTime(userNo);
			if (l.size() != 0) {
				for (Iterator itr = l.iterator(); itr.hasNext();) {
					Member mem = (Member) itr.next();
					loginDate = mem.getLoginDate();
				}

				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				long t = 0;
				try {
					Date now = df.parse(Util.TimeFomart(new Date()));
					Date date = df.parse(Util.TimeFomart(loginDate));
					t = now.getTime() - date.getTime();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// long t=now.getTime()-date.getTime();
				long day = t / (24 * 60 * 60 * 1000);
				long hor = t / (60 * 60 * 1000);
				long min = ((t / (60 * 1000)) - day * 24 * 60 - hor * 60);
				long s = (t / 1000 - day * 24 * 60 * 60 - hor * 60 * 60 - min * 60);
				System.out.println("" + day + "天" + hor + "小时" + min + "分" + s
						+ "秒");

				System.out.println("相差小时数：" + hor);
				if (hor <= 24) {// 账号存在但IMEI号与之不匹配且上一次绑定时间小于24小时
					sb.append("err.");
					sb.append(serialNum);
					sb.append(".");
					sb.append(Util.ERR_BIND);
					return sb.toString();
				} else {
					sb.append("ok.");
					sb.append(serialNum);
					sb.append(".");
					sb.append(Util.OK_PUTIN_PER);
					return sb.toString();
				}
			} else {
				sb.append("err.");
				sb.append(serialNum);
				sb.append(".");
				sb.append(Util.ERR_OTHER);
				sb.append(".");
				sb.append(getcount);
				return sb.toString();
			}
		}

	}

	/**
	 * 远程调用WEBSERIVE
	 * 
	 * @param userName
	 *            用户账号
	 * @param pwd
	 *            密码
	 * @return 0成功 、1账号不存在、2密码错误、3未购买、4其它、5调用WEBSERVICE失败
	 */
	private static int getService(String userName, String pwd) {

		try {
		    
			int res = 0;
			//modify by Tim 20110811
			String url = "http://czb.czton.cn:8088/czbWebService/services/CzbWebService";
			//String url = "http://test.yofoto.cn:8080/yofoto_webserver/Service1.asmx?wsdl";
			String namespace_uri = "http://test.yofoto.cn:8080/yofoto_webserver/";
//			Client ser = new Client(
//					new URL(url));
			System.out.println("url==================="+url);
			System.out.println("username=="+userName);
			System.out.println("pwd=="+pwd);
			//String url = "http://test.yofoto.cn:8080/yofoto_webserver/Service1.asmx?wsdl";
			//http://test.yofoto.cn:8080/yofoto_webserver/Service1.asmx?wsdl
			//http://webservice.yofoto.cn/service1.asmx?wsdl
			// WSS4JOutHandler wsOut = new WSS4JOutHandler();
			// String actions =WSHandlerConstants.USERNAME_TOKEN;
			// wsOut.setProperty(WSHandlerConstants.ACTION, actions);
			// wsOut.setProperty(WSHandlerConstants.PASSWORD_TYPE,
			// WSConstants.PASSWORD_DIGEST);
			// wsOut.setProperty(WSHandlerConstants.USER, "dibin");
			// wsOut.setProperty(WSHandlerConstants.PW_CALLBACK_CLASS,
			// PasswordHandler.class.getName());
			//		       
			// ser.addOutHandler(new DOMOutHandler());
			// ser.addOutHandler(wsOut);

//			Object[] o = ser.invoke("GetFlag_CWB",
//					new String[] { userName, pwd });
//			for (int i = 0; i < o.length; i++) {
//				res = (Integer) o[i];
//				System.out.println("res"+i+"=="+res);
//			}
//			return res;
			Service service = new Service();
			Call call = (Call)service.createCall();
			call.setTargetEndpointAddress(new URL(url));
			call.setOperationName(new QName(namespace_uri,"validUser"));
			call.addParameter(new QName(namespace_uri,"bh"),XMLType.XSD_STRING,ParameterMode.IN);
			call.addParameter(new QName(namespace_uri,"pwd"),XMLType.XSD_STRING,ParameterMode.IN);
			call.setReturnType(XMLType.XSD_STRING);
			return Integer.parseInt(call.invoke(new Object[]{userName,pwd}).toString());
		} catch (RemoteException e) {
			e.printStackTrace();
			return 6;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 5;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 5;
		}

	}
	
	
	
	
	//获取各模块总数
	public String getCount(){
		StringBuffer sb = new StringBuffer();
		List list = getCountDao.getCount();
		System.out.println("size:"+list.size());
		String str[] = new String[list.size()];
		for(int i=0;i<list.size();i++){
			Servicecount sc = (Servicecount)list.get(i);
			String a = sc.getCataName();
			String b = String.valueOf(sc.getCount());
			String c = String.valueOf(sc.getItem());
			String d = sc.getMeno();
			if(i<list.size()-1){
				str[i]=a+":"+b+":"+c+":"+d+",";
			}else{
				str[i]=a+":"+b+":"+c+":"+d;
			}
			sb.append(str[i]);
		}
		System.out.println(sb.toString());
		return sb.toString();
		
	}
	
	
	
	//获取指定模块总数
	public String getItemCount(String value){
		StringBuffer sb = new StringBuffer();
		List list = getCountDao.getCount(value);
		System.out.println("size:"+list.size());
		String str[] = new String[list.size()];
		for(int i=0;i<list.size();i++){
			Servicecount sc = (Servicecount)list.get(i);
			String a = sc.getCataName();
			String b = String.valueOf(sc.getCount());
			String c = String.valueOf(sc.getItem());
			String d = sc.getMeno();
			if(i<list.size()-1){
				str[i]=a+":"+b+":"+c+":"+d+",";
			}else{
				str[i]=a+":"+b+":"+c+":"+d;
			}
			sb.append(str[i]);
		}
		System.out.println(sb.toString());
		return sb.toString();
		
	}
	
	//得到广告图片信息
	public String getAdvertisment() {
		String picadr = null;
		List list = loginDaow.getAdvertisment();
		for(int i=0;i<list.size();i++){
			Advertisment advertisment = (Advertisment)list.get(i);
			picadr = advertisment.getPicAdr();
			System.out.println("list====="+picadr);
		}
		return picadr;
	}
	
	
	
	
	
	
	
	
	
	private static String mytest(String uri){
		try {
			String res = "";
			//String url = "http://czbsysyz.yofoto.com.cn/Service1.asmx?wsdl";
			String namespace_uri = "http://test.yofoto.cn:8080/yofoto_webserver/";
			Client ser = new Client(
					new URL(uri));
			System.out.println("url==================="+uri);
			//Object[] o = ser.invoke("doVpdn",
			//new String[] { "00001","1","15079114672" });
			Object[] o = ser.invoke("MobileUserLogIn",
			new String[] { "00001","12345678","888888888","YoFoTo456" });
			
			for (int i = 0; i < o.length; i++) {
				res = (String) o[i];
				System.out.println("res"+i+"=="+res);
			}
			return res;
		} catch (RemoteException e) {
			e.printStackTrace();
			return "6";
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "5";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "5";
		}

	}
	public static void main(String args[]) throws ParseException {
		LoninServiceImpl l = new LoninServiceImpl();
		String aa = l.getCount();
		//LoninServiceImpl l = new LoninServiceImpl();
		//l.mytest("http://10.81.48.203:8000/managess/service/managess.ws?wsdl");
		// System.out.println(l.getService("13812345678", "123456"));

		// String serviceUrl = null;
		// Service serviceModel = new
		// ObjectServiceFactory().create(CommuniWebservice.class);
		// System.out.println("callSoapServiceLocal(): got service model." );

		// XFire xfire = XFireFactory.newInstance().getXFire();
		// XFireProxyFactory factory = new XFireProxyFactory(xfire);

		// //String serviceUrl =
		// "http://10.81.48.173:8080/cds/service/cdsPositionService.ws";
		// //CommuniWebservice communi
		// =(CommuniWebservice)applicationContext.getBean("communiMina");
		// //String serviceUrl = "";
		// System.out.println("serviceUrl:"+serviceUrl);
		// CommuniWebservice client = null;
		// try {
		// client = (CommuniWebservice) factory.create(serviceModel,
		// serviceUrl);
		// System.out.println("client:"+client);
		// return client;
		// } catch (MalformedURLException e) {
		// System.out.println("WsClient.callWebService(): EXCEPTION: " +
		// e.toString());
		// return null;
		// }

		// String res ="0";
		// try {
		// Client ser = new Client(
		// new URL(
		// "http://10.81.48.173:8080/managess/service/test.ws?wsdl"));
		// Object[] o = ser.invoke("sumAandB",
		// new String[] { "nba001", "dfgdfg" });
		// for (int i = 0; i < o.length; i++) {
		// res = String.valueOf(o[i]);
		// }
		// System.out.println(res);
		// } catch (MalformedURLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// Client client = null;
		// try {
		// client = new Client(new
		// URL("http://10.81.48.173:8080/plug/service/plug.ws?wsdl"));
		// Object[] result1 = client.invoke("setTimeSect", new String[]
		// {"sinSect","13812345678｜08:30-18:00"});
		//  
		// System.out.println(result1[0]);
		// } catch (MalformedURLException e) {
		// e.printStackTrace();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// System.out.println(Util.TimeFomart(new Date()));
		// System.out.println(Util
		// .fromDateStringToLong(Util.TimeFomart(new Date())));
		// long mint = Util
		// .fromDateStringToLong(Util.TimeFomart(new Date())) - Util
		// .fromDateStringToLong(Util.TimeFomart(Util.StringToDateTime("2010-03-25
		// 16:04:23")));
		// //long l=now.getTime()-date.getTime();
		// long day=mint/(24*60*60*1000);
		// long hour=(mint/(60*60*1000)-day*24);
		//
		// System.out.println("相差小时数：" + hour);

		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// java.util.Date now = df.parse(Util.TimeFomart(new Date()));
		// java.util.Date date=df.parse("2010-03-25 15:30:24");
		// long t=now.getTime()-date.getTime();
		// long day=t/(24*60*60*1000);
		// long hour=(t/(60*60*1000)-day*24)+day*24;
		// long min=((t/(60*1000))-day*24*60-hour*60);
		// long s=(t/1000-day*24*60*60-hour*60*60-min*60);
		// System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");

		// long mint = Util.fromDateStringToLong("2010-04-09
		// 14:50:30")-Util.fromDateStringToLong("2010-03-25 15:50:40");
		//		 
		// long day=mint/(24*60*60*1000);
		// long hour=(mint/(60*60*1000)-day*24);
		// long min=((mint/(60*1000))-day*24*60-hour*60);
		// long s=(mint/1000-day*24*60*60-hour*60*60-min*60);
		// System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");
	}



}
