package com.ztenc.oa.proj.service.webservice;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

	// 定义错误码
	public static String ERR_SERI = "0001"; // 流水号有误

	public static String ERR_SIM = "0002"; // 手机号码格式有误

	public static String ERR_BIND = "0003"; // 24小时内不能重新绑定IMEI号

	public static String ERR_OTHER = "0004"; // 其它错误
	
	public static String ERR_PWD = "0005"; // 密码错误
	
	public static String ERR_NOTBUY = "0006";//未购买
	
	public static String ERR_CANTCON = "0007";//远程服务连接失败
	
	public static String ERR_CODE = "0008"; //验证码错误
	
	public static String OK_ALLOW_LOGIN = "allow";//允许登录
	
	public static String OK_PUTIN_PER = "putin";//提示输入认证信息
	
	private final static String[] random_NumberAndLetter =
    {
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"
    };

	/**
	 * 检查流水号格式
	 * 
	 * @param num
	 *            长度
	 * @param serialNumber
	 *            流水号
	 * @return
	 */
	public static boolean checkSerialNumber(int num, String serialNumber) {
		if (serialNumber == null || num != serialNumber.length()
				|| !isNumber(serialNumber)) {
			return false;
		}
		return true;
	}

	/**
	 * 判断是否为数字字符串
	 * 
	 * @param str
	 *            输入的字符串
	 * @return
	 */
	public static boolean isNumber(String str) {
		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 将日期型日期格式化为yyyy-MM-dd HH:mm:ss的格式
	 * 
	 * @param time
	 * @return
	 */
	public static String TimeFomart(Date time) {
		SimpleDateFormat simpleDFmt = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String sendDatetime = simpleDFmt.format(time);
		return sendDatetime;
	}

	public static long fromDateStringToLong(String inVal) {
		Date date = null;
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd hh:ss");
		try {
			date = inputFormat.parse(inVal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date.getTime();
	}
	
	/**
	 * 将String格式转换成DATE类型时间
	 * @param date
	 * @return
	 */
	public static Date StringToDateTime(String date){
		DateFormat df= DateFormat.getDateTimeInstance();
		try {
			return df.parse(date);
		} catch (ParseException e) {			
			return null;
		}
	}

	public static String fromLongToDate(long inVal) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:ss");
		Date currentTime = new Date(inVal);
		return sdf.format(currentTime);
	}
	
	 public static String createBillNo(int length){
	        String __random_billno="";
	        StringBuffer __buf_billno=new StringBuffer();
	        Random random=new Random();
	        for(int i=0; i<length; i++){
	            __buf_billno=__buf_billno.append(random_NumberAndLetter[random.nextInt(36)]);
	        }
	        __random_billno=__buf_billno.toString();
	        return __random_billno;
	    }

}
