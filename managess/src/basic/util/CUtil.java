/*
 * util.java
 *
 * Created on 2006年5月25日, 下午5:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package basic.util;

import java.util.Random;
import java.util.Set;
import java.util.HashMap;
import java.io.UnsupportedEncodingException;

public class CUtil implements java.io.Serializable {
    
    private final static int[] li_SecPosValue =
    {
        1601, 1637, 1833, 2078, 2274, 2302,2433, 2594, 2787, 3106, 3212, 3472,
                3635, 3722, 3730, 3858, 4027, 4086,4390, 4558, 4684, 4925, 5249, 5590
    };
    
    private final static String[] lc_FirstLetter =
    {
        "a", "b", "c", "d", "e", "f", "g","h", "j", "k", "l", "m", "n", "o", "p",
                "q", "r", "s", "t", "w", "x", "y","z"
    };
    
    private final static String[] random_NumberAndLetter =
    {
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"
    };
    
    private final static String[] random_Number =
    {
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
    };
    
    private final static String[] random_Letter =
    {
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"
    };
    
    /** Creates a new instance of util */
    public CUtil() {
    }
    
    /**
     * 取得给定汉字串的首字母串,即声母串
     * @param str 给定汉字串
     * @return 声母串
     */
    public static String getAllFirstLetter(String str) {
        if (str == null ||str.trim().length() == 0) {
            return "";
        }
        
        StringBuffer tmp_str = new StringBuffer();
        for (int i = 0; i < str.trim().length(); i++){
            tmp_str.append(getFirstLetter(str.substring(i, i + 1)));
        }
        
        return tmp_str.toString();
    }
    
    /**
     * 取得给定汉字的首字母,即声母
     * @param chinese 给定的汉字
     * @return 给定汉字的声母
     */
    public static String getFirstLetter(String chinese) {
        if (chinese == null ||chinese.trim().length() == 0) {
            return "";
        }
        chinese =conversionStr(chinese, "GB2312", "ISO8859-1");
        
        if (chinese.length()> 1)//判断是不是汉字
        {
            int li_SectorCode = (int)chinese.charAt(0); //汉字区码
            int li_PositionCode = (int) chinese.charAt(1); //汉字位码
            li_SectorCode = li_SectorCode -160;
            li_PositionCode =li_PositionCode - 160;
            int li_SecPosCode = li_SectorCode * 100 + li_PositionCode; //汉字区位码
            if (li_SecPosCode> 1600 && li_SecPosCode< 5590) {
                for (int i = 0; i< 23;i++) {
                    if (li_SecPosCode>=li_SecPosValue[i] && li_SecPosCode< li_SecPosValue[i+1]) {
                        chinese = lc_FirstLetter[i];
                        break;
                    }
                }
            } else //非汉字字符,如图形符号或ASCII码
            {
                chinese =conversionStr(chinese, "ISO8859-1", "GB2312");
                chinese =chinese.substring(0,1);
            }
        }
        
        return chinese;
    }
    
    /**
     * 字符串编码转换
     * @param str 要转换编码的字符串
     * @param charsetName 原来的编码
     * @param toCharsetName 转换后的编码
     * @return 经过编码转换后的字符串
     */
    private static String conversionStr(String str, String charsetName,String toCharsetName) {
        try {
            str = new String(str.getBytes(charsetName), toCharsetName);
        } 
        catch (UnsupportedEncodingException ex) {
            StringBuffer sb=new StringBuffer("字符串编码转换异常：");
            sb.append(ex.getMessage());
            System.out.println(sb.toString());
        }
        
        return str;
    }
    
    /**
     * 生成单据编号
     * @param length 要生成的单据编号长度
     * @return 生成的单据编号(字母数字混合)
     */
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
    
    /**
     * 用模板生成单据编号(如：模板(Y-M-D H:m)$$##-@\\Y\\y{YYYY},输出(2006-6-8 9:10)BJ14-3YyYYYY)
     * @param template 要生成的单据编号模板($:字母; #:数字; @:字母或数字; Y:4位年份; y:2位年份; M:月份; D:日期; H:小时; m:分钟; \\:转义字符,如\\Y输出为Y; {}:大括号中内容原样输出,可以是字符串变量)
     * @return 生成的单据编号
     */
    public static String createBillNo(String template){
        String __random_billno="";
        StringBuffer __buf_billno=new StringBuffer();
        Random random=new Random();
        
        int template_length=template.length();
        char[] char_template=new char[template_length];
        template.getChars(0,template_length,char_template,0);
        for(int count=0; count<char_template.length; count++){
            char tmp_char=char_template[count];
            switch (tmp_char){
                case '$':
                    if(count==0){
                        __buf_billno=__buf_billno.append(random_Letter[random.nextInt(26)]);
                    }else{
                        if(char_template[count-1]=='\\'){
                            __buf_billno=__buf_billno.append(char_template[count]);
                        }else{
                            __buf_billno=__buf_billno.append(random_Letter[random.nextInt(26)]);
                        }
                    }
                    break;
                case '#':
                    if(count==0){
                        __buf_billno=__buf_billno.append(random_Number[random.nextInt(10)]);
                    }else{
                        if(char_template[count-1]=='\\'){
                            __buf_billno=__buf_billno.append(char_template[count]);
                        }else{
                            __buf_billno=__buf_billno.append(random_Number[random.nextInt(10)]);
                        }
                    }
                    break;
                case '@':
                    if(count==0){
                        __buf_billno=__buf_billno.append(random_NumberAndLetter[random.nextInt(36)]);
                    }else{
                        if(char_template[count-1]=='\\'){
                            __buf_billno=__buf_billno.append(char_template[count]);
                        }else{
                            __buf_billno=__buf_billno.append(random_NumberAndLetter[random.nextInt(36)]);
                        }
                    }
                    break;
                case 'Y':
                    if(count==0){
                        __buf_billno=__buf_billno.append(getServerDateYear());
                    }else{
                        if(char_template[count-1]=='\\'){
                            __buf_billno=__buf_billno.append(char_template[count]);
                        }else{
                            __buf_billno=__buf_billno.append(getServerDateYear());
                        }
                    }
                    break;
                case 'y':
                    if(count==0){
                        __buf_billno=__buf_billno.append(Integer.toString(getServerDateYear()).substring(2));
                    }else{
                        if(char_template[count-1]=='\\'){
                            __buf_billno=__buf_billno.append(char_template[count]);
                        }else{
                            __buf_billno=__buf_billno.append(Integer.toString(getServerDateYear()).substring(2));
                        }
                    }
                    break;
                case 'M':
                    if(count==0){
                        __buf_billno=__buf_billno.append(getServerDateMonth());
                    }else{
                        if(char_template[count-1]=='\\'){
                            __buf_billno=__buf_billno.append(char_template[count]);
                        }else{
                            __buf_billno=__buf_billno.append(getServerDateMonth());
                        }
                    }
                    break;
                case 'D':
                    if(count==0){
                        __buf_billno=__buf_billno.append(getServerDateDay());
                    }else{
                        if(char_template[count-1]=='\\'){
                            __buf_billno=__buf_billno.append(char_template[count]);
                        }else{
                            __buf_billno=__buf_billno.append(getServerDateDay());
                        }
                    }
                    break;
                case 'H':
                    if(count==0){
                        __buf_billno=__buf_billno.append(getServerDateHour());
                    }else{                   
                        if(char_template[count-1]=='\\'){
                            __buf_billno=__buf_billno.append(char_template[count]);
                        }else{
                            __buf_billno=__buf_billno.append(getServerDateHour());
                        }
                    }
                    break;
                case 'm':
                    if(count==0){
                        __buf_billno=__buf_billno.append(getServerDateMinute());
                    }else{
                        if(char_template[count-1]=='\\'){
                            __buf_billno=__buf_billno.append(char_template[count]);
                        }else{
                            __buf_billno=__buf_billno.append(getServerDateMinute());
                        }
                    }
                    break;
                case '{':
                    if(count==0){
                        int expression_length=0;
                        for(int t=count+1; t<template_length; t++){
                            if(char_template[t]=='}'){
                                break;
                            }else{
                                expression_length++;
                            }
                        }
                        __buf_billno=__buf_billno.append(char_template,count+1,expression_length);
                        count=count+expression_length+1;
                    }else{
                        if(char_template[count-1]=='\\'){
                            __buf_billno=__buf_billno.append(char_template[count]);
                        }else{
                            int expression_length=0;
                            for(int t=count+1; t<template_length; t++){
                                if(char_template[t]=='}'){
                                    break;
                                }else{
                                    expression_length++;
                                }
                            }
                            __buf_billno=__buf_billno.append(char_template,count+1,expression_length);
                            count=count+expression_length+1;
                        }
                    }
                    break;
                case '\\':
                    if(count==0){
                        
                    }else{
                        if(char_template[count-1]=='\\'){
                            __buf_billno=__buf_billno.append(char_template[count]);
                        }
                    }
                    break;
                default:
                    __buf_billno=__buf_billno.append(char_template[count]);
            }
            
        }
        
        __random_billno=__buf_billno.toString();
        return __random_billno;
    }
    
    /**
     * 生成单据编号(原始格式：2006-12-06 12:15:30.546 202.164.59.42, 转化后格式：200612061215305462021645942)
     * @param time 当前时间
     * @param ip 当前IP
     * @return 生成的单据编号
     */
    public static String createBillNo(String time,String ip){
        //时间
        StringBuffer __buf_time=new StringBuffer(time);
        for(int i=0;i<2;i++){
            int __del_time1=__buf_time.indexOf("-");
            __buf_time=__buf_time.deleteCharAt(__del_time1);
        }
        int __del_time2=__buf_time.indexOf(" ");
        __buf_time=__buf_time.deleteCharAt(__del_time2);
        for(int i=0;i<2;i++){
            int __del_time3=__buf_time.indexOf(":");
            __buf_time=__buf_time.deleteCharAt(__del_time3);
        }
        int __del_time4=__buf_time.indexOf(".");
        __buf_time=__buf_time.deleteCharAt(__del_time4);
        //IP
        StringBuffer __buf_ip=new StringBuffer(ip);
        for(int i=0;i<3;i++){
            int __del_index=__buf_ip.indexOf(".");
            __buf_ip=__buf_ip.deleteCharAt(__del_index);
        }
        __buf_time=__buf_time.append(__buf_ip);
        return __buf_time.toString();
    }
    
    /**
     * 获得服务器时间--年份
     * @return 年份(2006)
     */
    private static int getServerDateYear(){
        java.util.Calendar mycal=java.util.Calendar.getInstance();
        int year; // 当前显示的年份
        year = mycal.get(java.util.Calendar.YEAR);
        return year;
    }
    
    /**
     * 获得服务器时间--月份
     * @return 月份
     */
    private static int getServerDateMonth(){
        java.util.Calendar mycal=java.util.Calendar.getInstance();
        int month; // 当前显示的月份
        month = mycal.get(java.util.Calendar.MONTH)+1;
        return month;
    }
    
    /**
     * 获得服务器时间--日期
     * @return 日期
     */
    private static int getServerDateDay(){
        java.util.Calendar mycal=java.util.Calendar.getInstance();
        int day; // 当前选择的日期
        day = mycal.get(java.util.Calendar.DAY_OF_MONTH);
        return day;
    }
    
    /**
     * 获得服务器时间--小时
     * @return 小时
     */
    private static int getServerDateHour(){
        java.util.Calendar mycal=java.util.Calendar.getInstance();
        int hour; // 当前选择的小时
        hour = mycal.get(java.util.Calendar.HOUR_OF_DAY);
        return hour;
    }
    
    /**
     * 获得服务器时间--分钟
     * @return 分钟
     */
    private static int getServerDateMinute(){
        java.util.Calendar mycal=java.util.Calendar.getInstance();
        int minute; // 当前选择的分钟
        minute = mycal.get(java.util.Calendar.MINUTE);
        return minute;
    }
    
    /**
     * 获得服务器时间
     * @return 服务器时间(2006年6月6日 星期三)
     */
    public static String getServerDate(){
        StringBuffer serverDate=new StringBuffer();
        java.util.Calendar mycal=java.util.Calendar.getInstance();
        int year; // 当前显示的年份
        int month; // 当前显示的月份
        int day; // 当前选择的日期
        int week; // 当前选择的星期
        String strWeek=""; //中文星期
        year = mycal.get(java.util.Calendar.YEAR);
        month = mycal.get(java.util.Calendar.MONTH)+1;
        day = mycal.get(java.util.Calendar.DAY_OF_MONTH);
        week = mycal.get(java.util.Calendar.DAY_OF_WEEK)-1;
        switch(week){
            case 0 : strWeek="日"; break;
            case 1 : strWeek="一"; break;
            case 2 : strWeek="二"; break;
            case 3 : strWeek="三"; break;
            case 4 : strWeek="四"; break;
            case 5 : strWeek="五"; break;
            case 6 : strWeek="六"; break;
        }
        serverDate.append(year);
        serverDate.append("年");
        serverDate.append(month);
        serverDate.append("月");
        serverDate.append(day);
        serverDate.append("日");
        serverDate.append(" 星期");
        serverDate.append(strWeek);
        return serverDate.toString();
    }
    
    public static String linkString(Object... param) throws Exception {
        StringBuffer rtn=new StringBuffer();
        
        if(param.length>0){
            int max=param.length;
            for(int index=0;index<max;index++){
                rtn.append(param[index].toString());
            }
        }
        
        return rtn.toString();
    }
    
    public static String linkString(String... arg){
        String rtn=null;
        StringBuffer sb=new StringBuffer();
        
        if(arg!=null){
            int len=arg.length;
            for(int index=0;index<len;index++){
                sb.append(arg[index]);
            }
            
            rtn=sb.toString();
        }
        sb=null;
        
        return rtn;
    }
    
    public static String toJSON(Object obj) throws Exception {
        StringBuffer rtn_str=new StringBuffer();
        if(obj.getClass().getName()=="java.lang.String"){
            rtn_str.append("\"");
            rtn_str.append(obj);
            rtn_str.append("\"");
        }
        else{
            if(obj.getClass().getName()=="basic.util.CHashMapList"){
                rtn_str.append(obj.toString());
            }
            else{
                if(obj.getClass().getName()=="java.util.HashMap"){
                    rtn_str.append("{");
                    Set<String> keys=((HashMap)obj).keySet();
                    for(String key:keys){
                        Object v=((HashMap)obj).get(key);
                        rtn_str.append("\"");
                        rtn_str.append(key);
                        rtn_str.append("\":");
                        rtn_str.append("\"");
                        rtn_str.append(v.toString());
                        rtn_str.append("\",");
                    }
                    rtn_str.delete(rtn_str.length()-1,rtn_str.length());
            
                    rtn_str.append("}");
                }
                else{
                    if(obj.getClass().getName()=="java.util.LinkedList"){
                        rtn_str.append("[");
                        int max=((java.util.LinkedList)obj).size();
                        for(int index=0;index<max;index++){
                            Object tmp=((java.util.LinkedList)obj).get(index);
                            if(index!=0){
                                rtn_str.append(",");

                            }
                            rtn_str.append(tmp.toString());
                        }
                        rtn_str.append("]");
                    }
                    else{
                        if(obj.getClass().getName()=="java.lang.Integer"){
                            rtn_str.append(((java.lang.Integer)obj).intValue());
                        }
                        else{
                            if(obj.getClass().getName()=="java.lang.Float"){
                                rtn_str.append(((java.lang.Float)obj).floatValue());
                            }
                            else{
                                StringBuffer err=new StringBuffer();
                                err.append("系统目前不支持对");
                                err.append(obj.getClass().getName());
                                err.append("进行远程调用");
                                throw new Exception(err.toString());
                            }
                        }
                    }
                }
                
            }
        }
        
        return rtn_str.toString();
    }
    
}
