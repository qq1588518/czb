package com.ztenc.oa.proj.service.pronews;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.dao.DataAccessException;

import basic.base.test.MacBinaryDecoderOutputStream;
import basic.base.test.MultipartParser;
import basic.base.test.Part;

import com.ztenc.oa.proj.bean.Catagory;
import com.ztenc.oa.proj.bean.CatagoryCon;
import com.ztenc.oa.proj.bean.CatatoryConGroup;
import com.ztenc.oa.proj.bean.Corporateculture;
import com.ztenc.oa.proj.bean.ProNews;
import com.ztenc.oa.proj.dao.CorporateCulture.CorporateCultureDao;
import com.ztenc.oa.proj.dao.ServiceCount.ServiceCountDao;
import com.ztenc.oa.proj.dao.column.ColumnConDao;
import com.ztenc.oa.proj.dao.pronews.ProNewsDao;
import com.ztenc.oa.proj.json.JSONArray;
import com.ztenc.oa.proj.json.JSONObject;
import com.ztenc.oa.proj.util.CUtil;
import com.ztenc.oa.proj.util.pagination.PcurrentDataDaoService;

public class ProNewsServiceImpl implements ProNewsService{
	
	ProNewsDao proNewsDao;
	public void setProNewsDao(ProNewsDao proNewsDao) {
		this.proNewsDao = proNewsDao;
	}
	
	ServiceCountDao serviceCountDao;
	public void setServiceCountDao(ServiceCountDao serviceCountDao) {
		this.serviceCountDao = serviceCountDao;
	}
	
	//修改时得到栏目内容
	public List getCorporate(String id){
		List list = null;
		list = proNewsDao.getCorporate(id);
		System.out.println("listcon == "+list.size());
		return list;
	}
	
	public String getContentFromFile(String dirname,String url){
	    File dir2;
	    String pic ="";//隐藏域内的图片地址
	    String proid="";
	    String subProid="";
	    int permission = 0 ;
	    String proAddress="";
	    String fileName="";
	    String tmp_str = "";
		SimpleDateFormat tempDate1 = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		Calendar p_cal = Calendar.getInstance();
        java.util.Date p_date = p_cal.getTime();
		String datetime1 = tempDate1.format(p_date);
		String filepro ="/images/catagory/";
		dirname =dirname+url;
    	long written = 0L;
        java.io.InputStream fileIn = null;
        java.io.InputStreamReader isr = null;
    	String name = datetime1+".html";
    	name = name.replaceAll("-|:", "");
    	url = dirname;
    	File _name = new File(url);
    	BufferedReader br = null;
        try
        {  
        	fileIn = new BufferedInputStream(new FileInputStream(_name));
            if(_name.isFile())
            {
                File file;
                isr = new java.io.InputStreamReader(fileIn);
                br = new BufferedReader(isr);
                String hhh = "";
                tmp_str = "";
                while( (hhh=br.readLine())!=null){
                	tmp_str += hhh;
                }
            }
        }catch(Exception e){
        	System.out.println(e);
        }
        finally
        {
        	try{
            if(fileIn != null)
                fileIn.close();
            if(br!=null)
            	br.close();
        	}catch(Exception e){
        		System.out.println(e);
        	}
        }
    	return tmp_str;
    }
	
	public String deleteFromFile(String dirname,String url){
		String rtn = "0";
		String filepro ="upload/ProNews/file/";
		dirname =dirname+filepro;
	    File dir2 = new File(dirname);
	    File _name = new File(dir2,url);
	    try{
		    _name.delete();
		    rtn = "1";
	    }catch(Exception e){
	    	System.out.println(e);
	    	rtn = "0";
	    }
		return rtn;
	}
	
	//保存内容到物理地址
    public String saveContentToFile(String title,String con,String dirname,String filename,int curpageno,String total,String total_filenames,String filepro,String dates){
    	String url = "";
    	String saveDirectory1="";
	    File dir2;
	    SimpleDateFormat tempDate2 = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
		Calendar p_cal = Calendar.getInstance();
	    java.util.Date p_date = p_cal.getTime();
		String datetime2 = tempDate2.format(p_date);
		String content_desc = "";
		String total_desc = "";
		//modify by Tim 20110919
		content_desc = "<html  xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\" /><meta http-equiv=\"Cache-Control\" content=\"no-cache\"/><META HTTP-EQUIV=\"pragma\" CONTENT=\"no-cache\"><META HTTP-EQUIV=\"expires\" CONTENT=\"0\"><meta id=\"viewport\" name=\"viewport\" content=\"width=320; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\" /><title>三生财智宝</title><script src=\"../../../scripts/common.js\"  charset=\"utf-8\"></script><script src=\"../../../scripts/pagination.js\"  charset=\"utf-8\"></script><style type=\"text/css\">body{margin:0;padding:0;color:#333;background-color:#fff9f2;font-size:13pt;}*{line-height:22px;}p,ul,dl,dt,dd,h1,h2,h3,form{margin:0;padding:0px 0px 0px 10px}img{border:0}img,input{vertical-align:middle}li{list-style:none}a{color:#039;text-decoration:underline}a:hover{color:#f00;text-decoration:none}p,dl,li,div{padding:0 0px 0px 10px}#wrap{margin:0 auto;width:100%; background-color:#fff9f2;}.p{background:#d80c18;border-bottom:1px solid #fff;line-height:25px;color:#fff;}.p a{color:#666}.p a:hover{color:#f00}.h3{text-align:center;line-height:25px;border-bottom:1px solid #ef8043;margin:5px;font-weight:normal;color:#ef8043}.info{text-align:center}</style></head><body onload=\"init();\"><div id='wrap' style=\"padding:0px;margin:0px;\"><!--p class=\"p\">事业介绍</p--><h3 class=\"h3\">"+title+"</h3><!--p class=\"info\">datetime2<span style=\"cursor:pointer\" onclick=\"doFont('wrap','13pt');\">[标准]</span>&nbsp;&nbsp;<span style=\"cursor:pointer\" onclick=\"doFont('wrap','10.5pt');\">[小]</span--></p><p><span id=\"start\">"+con+"</span><span id=\"end\" style=\"display:none\"></span><br/></p><p style=\"margin:5px 0px 10px 0px;\"><span id=\"page\" style=\"float:left;\"></span><!--span id=\"back\" style=\"margin-top:-3px;float:right;color:#ea5504;background:url(../../../images/return.png);width:85px;height:25px;line-height:25px;font-wieght:bold;;font-size:12pt;padding-left:8px;\" onclick=\"parent.viewcon();\">返回上一级</span></p><br/><p class=\"p p2\">三生财智宝</p--></div></body><script>function init(){viewPage(document.getElementById('page'),'"+filename+"',"+curpageno+",'"+total_filenames+"',\"false\");var a_obj=document.getElementsByTagName(\"A\");var a_len = a_obj.length;for(var k=0;k<a_len;k++){if(a_obj[k].href.indexOf(\"192.103.137.34\")!=-1){if(getCookie(\"vpdn\")==\"0\" || getCookie(\"vpdn\")==undefined || getCookie(\"vpdn\")==null){a_obj[k].href=\"javascript:void(0);\";a_obj[k].target=\"_self\";a_obj[k].onclick=function(){alert(\"此功能需开通VPDN业务后使用！\");}}else{var verifyurl = getCookie(\"verifyurl\");var gourl = a_obj[k].href;if(gourl==\"\" || gourl==undefined || gourl==null){a_obj[k].href=\"javascript:void(0);\";a_obj[k].onclick=function(){alert(\"跳转地址有误,请联系管理员\");};return;};var telno=getCookie(\"telno\")==null?\"15000000000\":getCookie(\"telno\");var tmp_str = verifyurl+\"&userMobileNo=\"+telno+\"&redirect=\"+gourl;a_obj[k].href=tmp_str;}}}}</script></html>";
		total_desc="<html  xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\" /><meta http-equiv=\"Cache-Control\" content=\"no-cache\"/><META HTTP-EQUIV=\"pragma\" CONTENT=\"no-cache\"><META HTTP-EQUIV=\"expires\" CONTENT=\"0\"><meta id=\"viewport\" name=\"viewport\" content=\"width=320; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\" /><title>三生财智宝</title><script src=\"../../../scripts/common.js\"  charset=\"utf-8\"></script><script src=\"../../../scripts/pagination.js\"></script><style type=\"text/css\">body{margin:0;padding:0;color:#333;background-color:#fff9f2;font-size:13pt;}*{line-height:22px;}p,ul,dl,dt,dd,h1,h2,h3,form{margin:0;padding:0px 0px 0px 10px}img{border:0}img,input{vertical-align:middle}li{list-style:none}a{color:#039;text-decoration:underline}a:hover{color:#f00;text-decoration:none}p,dl,li,div{padding:0 0px 0px 10px}#wrap{margin:0 auto;width:100%; background-color:#fff9f2;}.p{background:#d80c18;border-bottom:1px solid #fff;line-height:25px;color:#fff;}.p a{color:#666}.p a:hover{color:#f00}.h3{text-align:center;line-height:25px;border-bottom:1px solid #ef8043;margin:5px;font-weight:normal;color:#ef8043}.info{text-align:center}</style></head><body onload=\"init();\"><div id='wrap' style=\"padding:0px;margin:0px;\"><!--p class=\"p\">事业介绍</p--><h3 class=\"h3\">"+title+"</h3><!--p class=\"info\">datetime2<span style=\"cursor:pointer\" onclick=\"doFont('wrap','13pt');\">[标准]</span>&nbsp;&nbsp;<span style=\"cursor:pointer\" onclick=\"doFont('wrap','10.5pt');\">[小]</span--></p><p><span id='span_total'>"+total+"</span><br/></p><p style=\"margin:5px 0px 10px 0px;\"><span id=\"page\" style=\"float:left;\"></span><span id=\"back\" style=\"margin-top:-3px;float:right;color:#ea5504;background:url(../../../images/return.png);width:85px;height:25px;line-height:25px;font-wieght:bold;;font-size:12pt;padding-left:8px;dispaly:none;\" onclick=\"parent.viewcon();\">返回上一级</span></p><br/><!--p class=\"p p2\">三生财智宝</p--></div></body><script>function init(){var container = document.getElementById('span_total');var total_len=_value[\"pos\"][1];for(var i=_value[\"pos\"][0];i<total_len;i++){document.getElementById(\"total\"+i).style.display='block';}}</script></html>";
		dirname =dirname+filepro;
	    dir2 = new File(dirname);
	    dir2.mkdirs();
    	
    	long written = 0L;
        OutputStream fileOut = null;
        java.io.OutputStreamWriter osw = null;
        java.io.OutputStreamWriter osw2 = null;
        String[]fns =  filename.split(",");
        String fn = fns[curpageno];
    	url = filepro+fn;
    	File _name = new File(dir2,fn);
        try
        {  
        	_name.createNewFile();
        	fileOut = new BufferedOutputStream(new FileOutputStream(_name));
            if(filename != null)
            {
                File file;
                if(dir2.isDirectory()){
                    file = new File(dir2, fn);
                    System.out.println("name=="+fn);
                }else
                    file = dir2;
                osw = new java.io.OutputStreamWriter(fileOut);
                osw.write(content_desc);
                osw.flush();
                if(curpageno==0){
	                if(total_filenames!=null){
	                	File total_name = new File(dir2,total_filenames);
	                	total_name.createNewFile();
	                	BufferedOutputStream totalOut = new BufferedOutputStream(new FileOutputStream(total_name));
	                	 osw2 = new java.io.OutputStreamWriter(totalOut);
	                	osw2.write(total_desc);
	                	osw2.flush();
	                }
                }
            }
        }catch(Exception e){
        	System.out.println(e);
        }
        finally
        {
        	try{
            if(fileOut != null)
                fileOut.close();
            if(osw!=null)
            	osw.close();
            if(osw2!=null)
            	osw2.close();
        	}catch(Exception e){
        		System.out.println(e);
        	}
        }
    	return url;
    	
    }
	
	//读取所有信息
	public String getprotype(String title,int index,int length){
		 String rtn = "";
			ResultSet rs = null;
			String _title = title;
			try{
				rs = proNewsDao.getprotype(_title,index,length);
				JSONArray all = new JSONArray();
				JSONObject jsonobj = new JSONObject(); 
				JSONObject totalobj = new JSONObject(); 
				while(rs.next()){
					JSONArray jsonarray = new JSONArray();
					String id = (String)rs.getString(1);
					String titl = (String)rs.getString(2);
					String con = (String)rs.getString(3);
					String outurl = (String)rs.getString(4);
					String datetime = (String)rs.getString(5);
					String count = (String)rs.getString(6);
					jsonarray.put(id);
					jsonarray.put(titl);
					jsonarray.put(con);
					jsonarray.put(outurl);
					jsonarray.put(datetime);
					jsonarray.put(count);
					all.put(jsonarray);
				}
				jsonobj.put("rs",all);
				StringBuilder sb = new StringBuilder();
				rtn = sb.append(jsonobj).toString(); 
			}catch(Exception e){
				try{
				rs.close();
				}catch(Exception ex){
					System.out.println(ex);
				}
				System.out.println(e);
			}finally{
				try{
					rs.close();
				}catch(Exception ex){
					System.out.println(ex);
				}
			}
			return rtn;
	}
	
	//删除信息
	public String deleteproType(String id){//
		String rtn = "0";
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			rtn = proNewsDao.deleteproType(id);
			rtn = serviceCountDao.deleteInfo("workIntroduce");
			jsonRtn.put("flag",rtn);
		}catch(Exception e){
			rtn = "0";
			System.out.println(e);
		}
		rtn = sb.append(jsonRtn).toString();
		return rtn;
	}
	
	//添加
	public  JSONObject saveProduct(String title,String url,String outurl,String datetime1)
	{
		String rtn = "0";
		JSONObject rs = new JSONObject();
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			ProNews proNews = new ProNews();
			proNews.setTitle(title);
			proNews.setConUri(url);
			proNews.setOutUri(outurl);
			proNews.setDates(datetime1);
			rtn = proNewsDao.saveInfo(proNews);
			rtn = serviceCountDao.updateInfo("workIntroduce");
		}catch (Exception e)
	    {
	    	rtn = "0";
	        try{
	        	rs.put("flag",false);
	    	}catch(Exception ex){
	    		System.out.println(ex);
	    	}
	        return rs;
	    }
	    if(rtn.equals("1")){
	    	try{
	    		rs.put("flag",true);
	    	}catch(Exception e){
	    		System.out.println(e);
	    	}
	    	return rs;
	    }else{
	    	try{
	        	rs.put("flag",false);
	    	}catch(Exception ex){
	    		System.out.println(ex);
	    	}
	    	return rs;
	    }
		
	}
	
//修改
	public  JSONObject alterProduct(String title,String url,String outurl,String datetime1,String id)
	{
		String rtn = "0";
		JSONObject rs = new JSONObject();
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			ProNews proNews =(ProNews) proNewsDao.getProNewsId(Integer.valueOf(id));
			proNews.setTitle(title);
			proNews.setConUri(url);
			proNews.setOutUri(outurl);
			//proNews.setDates(datetime1);
			rtn = proNewsDao.updateInfo(proNews);
		}catch (Exception e)
	    {
	    	rtn = "0";
	        try{
	        	rs.put("flag",false);
	    	}catch(Exception ex){
	    		System.out.println(ex);
	    	}
	        return rs;
	    }
	    if(rtn.equals("1")){
	    	try{
	    		rs.put("flag",true);
	    	}catch(Exception e){
	    		System.out.println(e);
	    	}
	    	return rs;
	    }else{
	    	try{
	        	rs.put("flag",false);
	    	}catch(Exception ex){
	    		System.out.println(ex);
	    	}
	    	return rs;
	    }
	}
	
}
