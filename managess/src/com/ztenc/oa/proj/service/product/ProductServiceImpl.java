package com.ztenc.oa.proj.service.product;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.ztenc.oa.proj.bean.CatagoryCon;
import com.ztenc.oa.proj.bean.CatatoryConGroup;
import com.ztenc.oa.proj.bean.ProAnswer;
import com.ztenc.oa.proj.bean.Product;
import com.ztenc.oa.proj.bean.Product2group;
import com.ztenc.oa.proj.dao.ServiceCount.ServiceCountDao;
import com.ztenc.oa.proj.dao.column.ColumnConDao;
import com.ztenc.oa.proj.dao.product.ProductDao;
import com.ztenc.oa.proj.json.JSONArray;
import com.ztenc.oa.proj.json.JSONObject;
import com.ztenc.oa.proj.util.CUtil;

public class ProductServiceImpl implements ProductService {
	
	ProductDao productDao;
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
	
	ServiceCountDao serviceCountDao;
	public void setServiceCountDao(ServiceCountDao serviceCountDao) {
		this.serviceCountDao = serviceCountDao;
	}
	
	public JSONObject alterProduct(String name, String proAddress,
			String typeno, String permission, String con,
			String introduceurl, String experturl, String useurl, String level,
			String code,String price,String dvalue,String pvvalue,String id,String standard) {
		String rtn = "0";
		JSONObject rs = new JSONObject();
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			//String saveDirectory1 = saveDirectory;
			//String datetime1 = datetime;
			SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd" + " " + "HH:mm:ss");
			Calendar p_cal = Calendar.getInstance();
	        java.util.Date p_date = p_cal.getTime();
			String datetime = tempDate.format(p_date);
			Product product =(Product) productDao.getProdctId(id);
			product.setProductname(name);
			product.setImageurl(proAddress);
			product.setTypeno(Integer.valueOf(typeno));
			product.setContent(con);
			//product.setCreatedate(p_date);
			product.setCode(code);
			product.setPrice(price);
			product.setDvalue(dvalue);
			product.setPvvalue(pvvalue);
			product.setIntroduceurl(introduceurl);
			product.setExperturl(experturl);
			product.setUseurl(useurl);
			product.setStandard(standard);
			product.setLevel(Integer.valueOf(level));
			rtn = productDao.updateInfo(product);
			
			//CataGroup cataGroup =(CataGroup) columnConDao.getCataGroupId(id);
			//cataGroup.setGroupid(Integer.valueOf(permission));
			Product2group pg = new Product2group();
			pg.setGroupno(permission);
			pg.setProductno(id);
			rtn = productDao.updateInfo(pg);
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

	public String deleteFromFile(String dirname, String url) {
		String rtn = "0";
		String filepro ="upload/product/file/";
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
	
	public String deleteFromImages(String dirname, String url) {
		String rtn = "0";
		String filepro ="upload/product/images/";
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

	public String deleteProduct(String id) {
		String rtn = "0";
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		String typeno = null;
		try{
			List list = productDao.getProductInfo(id);
			for(int i=0;i<list.size();i++){
				Product pro = (Product)list.get(i);
				typeno = String.valueOf(pro.getTypeno());
			}
			System.out.println("typeno:"+typeno);
			rtn = productDao.deleteProduct(id);
			if(typeno.equals("1")){
				rtn = serviceCountDao.deleteInfo("goodFood");
			}else if(typeno.equals("2")){
				rtn = serviceCountDao.deleteInfo("selfProtect");
			}else if(typeno.equals("3")){
				rtn = serviceCountDao.deleteInfo("dressProduct");
			}else if(typeno.equals("4")){
				rtn = serviceCountDao.deleteInfo("otherProduct");
			}
			rtn = "1";
			jsonRtn.put("flag",rtn);
		}catch(Exception e){
			rtn = "0";
			System.out.println(e);
		}
		rtn = sb.append(jsonRtn).toString();
		return rtn;
	}

	public String getContentFromFile(String dirname, String url) {
		File dir2;
	    String pic ="";//隐藏域内的图片地址
	    String proid="";
	    String subProid="";
	    int permission = 0 ;
	    String proAddress="";
	    String fileName="";
	    String tmp_str = "";
//	    SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd" + " " + "HH:mm:ss");
		SimpleDateFormat tempDate1 = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
			Calendar p_cal = Calendar.getInstance();
	        java.util.Date p_date = p_cal.getTime();
//			String datetime = tempDate.format(p_date);
			String datetime1 = tempDate1.format(p_date);
			//String content_desc = "<table cellpadding='0' cellspacing='0' width='100%' height='100%' BGCOLOR='#f0f7f9'><tr height='80px'><td align='center' valign='center' width='100%' height='80px'><span style='font-size:20px;FONT-FAMILY:宋体; mso-ascii-font-family:Times New Roman; mso-hansi-font-family:Times New Roman'><b>"+title+"</b></span></td></tr><tr height='60px'><td align='center' valign='center' width='100%' height='60px'><a href=''>http://www.cmfx56.cn</a>&nbsp;&nbsp;"+datetime1+"&nbsp;&nbsp;中国移动物流网</td></tr><tr><td align='left' valign='top' width='100%' height='100%'>" + con + "</td></tr></table>";
			String filepro ="upload/images/";
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
    	//ByteArrayInputStream bais = new ByteArrayInputStream(con.getBytes());
    	
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
            System.out.println("hhhhhh===="+tmp_str);
            /*byte buf[] = new byte[8192];
            int i;
            System.out.println("bais=="+ new String(buf));
            while((i = bais.read(buf)) != -1) 
            {
            	osw.write(i);
            	//out.write(buf, 0, i);
                //size += i;
            	
            }*/
            //System.out.println("bais222=="+ new String(buf));
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

	public List getGroups() {
		List list = null;
		list = productDao.getGroups();
		return list;
	}

	public List getProductInfo(String id) {
		List list = null;
		list = productDao.getProductInfo(id);
		System.out.println("listcon == "+list.size());
		return list;
	}

	public String getProductList(String name,String typeno,int index, int length) {
		String rtn = "";
		ResultSet rs = null;
		try{
			rs = productDao.getProductList(name,typeno,index,length);
			JSONArray all = new JSONArray();
			JSONObject jsonobj = new JSONObject(); 
			JSONObject totalobj = new JSONObject(); 
			while(rs.next()){
				JSONArray jsonarray = new JSONArray();
				String id = (String)rs.getString(1);
				String _name = (String)rs.getString(2);
				String imageurl = (String)rs.getString(3);
				String con = (String)rs.getString(4);
				String type = (String)rs.getString(5);
				String code = (String)rs.getString(6);
				String price = (String)rs.getString(7);
				String dvvalue = (String)rs.getString(8);
				String pvvalue = (String)rs.getString(9);
				String introduceurl = (String)rs.getString(10);
				String experturl = (String)rs.getString(11);
				String useurl = (String)rs.getString(12);
				String level = (String)rs.getString(13);
				String standard = (String)rs.getString(14);
				String groupid = (String)rs.getString(18);
				String groupname = (String)rs.getString(19);
				String typename = (String)rs.getString(22);
				String remark = (String)rs.getString(24);
				String count = (String)rs.getString(26);
				jsonarray.put(id);
				jsonarray.put(_name);
				jsonarray.put(imageurl);
				jsonarray.put(con);
				jsonarray.put(type);
				jsonarray.put(code);
				jsonarray.put(price);
				jsonarray.put(dvvalue);
				jsonarray.put(pvvalue);
				jsonarray.put(introduceurl);
				jsonarray.put(experturl);
				jsonarray.put(useurl);
				jsonarray.put(level);
				jsonarray.put(groupid);
				jsonarray.put(groupname);
				jsonarray.put(count);
				jsonarray.put(typename);
				jsonarray.put(standard);
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
	public List getTypes(){
		List list = null;
		list = productDao.getTypes();
		return list;
	}
	public List getProductPermission(String id) {
		List list = null;
		list = productDao.getProductPermission(id);
		return list;
	}

	public String saveContentToFile(String name, String con, String dirname,
			String filename, int curpageno, String total,
			String total_filenames, String filepro,String dates) {
			String url = "";
	    	String saveDirectory1="";
		    File dir2;
		    SimpleDateFormat tempDate2 = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
			Calendar p_cal = Calendar.getInstance();
		    java.util.Date p_date = p_cal.getTime();
			String datetime2 = tempDate2.format(p_date);
			String content_desc = "";
			String total_desc = "";
			JSONObject content_obj = null;
			//modify by Tim 20110919 ssczb -> managess
			try{
	            content_obj = new JSONObject(name);
	            content_desc = "<html  xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\" /><meta http-equiv=\"Cache-Control\" content=\"no-cache\"/><META HTTP-EQUIV=\"pragma\" CONTENT=\"no-cache\"><META HTTP-EQUIV=\"expires\" CONTENT=\"0\"><meta id=\"viewport\" name=\"viewport\" content=\"width=320; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\" /><title>三生财智宝</title><script src=\"../../../scripts/common.js\"  charset=\"utf-8\"></script><script src=\"../../../scripts/pagination.js\"  charset=\"utf-8\"></script>";
	            content_desc = content_desc + "<style type=\"text/css\">body{margin:0;padding:0;color:#333;font-size:13pt;}*{line-height:22px;margin:0px;padding:0px;}img{border:0}img,input{vertical-align:middle}li{list-style:none}a{color:#039;text-decoration:underline}a:hover{color:#f00;text-decoration:none}#wrap{margin:0 auto;width:100%; }.p{background:#d80c18;border-bottom:1px solid #fff;line-height:25px;color:#fff;}.con{margin-left:10px;margin-top:10px;}</style></head>";
	            content_desc = content_desc + "<body onload=\"init();\" ><div id='wrap'><!--p class=\"p\">产品介绍</p-->";
	            content_desc = content_desc + "<div id=\"titles\">";
	            content_desc = content_desc + "<ul>";
	            content_desc = content_desc + "\t<li style=\"margin-top:10px;text-align:center;font-size:15pt;color:#ea5504;font-weight:bold;\"><span>" + content_obj.getString("name") + "</span></li>";
	            content_desc = content_desc + "<li>";
	            content_desc = content_desc + "<div style=\"float:left;padding:10px;padding-right:0px;color:#f39800;font-weight:bold;\">";
	            content_desc = content_desc + "\t<ul>";
	            content_desc = content_desc + "\t\t<li style=\"margin-bottom:10px;margin-right:10px;margin-top:10px;float:left;\"><img src=\"../../../../ssczb/" + content_obj.getString("imgurl") + "\"  width=\"113px\" ></li>";
	            content_desc = content_desc + "\t\t<li style=\"float:left;width:185px;\">";
	            content_desc = content_desc + "\t\t\t<span><span style=\"color:#ea5504;\">编号:</span>" + content_obj.getString("code") + "</span></br>  ";
	            content_desc = content_desc + "\t\t\t<span><span style=\"color:#ea5504;\">价格:</span>￥" + content_obj.getString("price") + "</span></br>  ";
	            content_desc = content_desc + "\t\t\t<span><span style=\"color:#ea5504;\">PV值:</span>" + content_obj.getString("pvalue") + "</span></br>";
	            content_desc = content_desc + "\t\t\t<span><span style=\"color:#ea5504;\">D 值:</span>" + content_obj.getString("dvvalue") + "</span></br>";
	            content_desc = content_desc + "\t\t\t<span><span style=\"color:#ea5504;\">规格:</span>" + content_obj.getString("standard") + "</span> ";
	            content_desc = content_desc + "\t\t</li>";
	            content_desc = content_desc + "\t</ul>";
	            content_desc = content_desc + "</div>";
	            content_desc = content_desc + "</li>";
	            content_desc = content_desc + "<li style=\"clear:both;\">";
	            content_desc = content_desc + "\t<div>";
	            if (!content_obj.getString("introduceurl").equals("")) {
	                content_desc = content_desc + "\t\t<span style=\"margin-left:9px;\"><a href=\"" + content_obj.getString("introduceurl") + "\" target=\"_blank\"><img src=\"../../../../ssczb/images/product.jpg\" /></a></span>";
	            }
	            if (!content_obj.getString("experturl").equals("")) {
	                content_desc = content_desc + "\t\t<span style=\"margin-left:9px;\"><a href=\"" + content_obj.getString("experturl") + "\" target=\"_blank\"><img src=\"../../../../ssczb/images/expert.jpg\" /></a></span>";
	            }
	            if (!content_obj.getString("useurl").equals("")) {
	                content_desc = content_desc + "\t\t<span style=\"margin-left:9px;\"><a href=\"" + content_obj.getString("useurl") + "\" target=\"_blank\"><img src=\"../../../../ssczb/images/share.jpg\" /></a></span>";
	            }
	            content_desc = content_desc + "\t</div>";
	            content_desc = content_desc + "</li>";
	            content_desc = content_desc + "</ul>";
	            content_desc = content_desc + "</div>";
	            
	            content_desc = content_desc + "<div class=\"con\"><span id=\"start\">" + con + "</span><span id=\"end\" style=\"display:none\"></span><br/></div><p style=\"margin:5px 0px 10px 0px;\"><span id=\"page\" style=\"float:left;\"></span></p><br/><!--p class=\"p p2\">三生财智宝</p--></div></body><script>function init(){viewPage(document.getElementById('page'),'" + filename + "'," + curpageno + ",'" + total_filenames + "',\"false\");var a_obj=document.getElementsByTagName(\"A\");var a_len = a_obj.length;for(var k=0;k<a_len;k++){if(a_obj[k].href.indexOf(\"192.103.137.34\")!=-1){if(getCookie(\"vpdn\")==\"0\" || getCookie(\"vpdn\")==undefined || getCookie(\"vpdn\")==null){a_obj[k].href=\"javascript:void(0);\";a_obj[k].target=\"_self\";a_obj[k].onclick=function(){alert(\"此功能需开通VPDN业务后使用！\");}}else{var verifyurl = getCookie(\"verifyurl\");var gourl = a_obj[k].href;if(gourl==\"\" || gourl==undefined || gourl==null){a_obj[k].href=\"javascript:void(0);\";a_obj[k].onclick=function(){alert(\"跳转地址有误,请联系管理员\");};return;};var telno=getCookie(\"telno\")==null?\"15000000000\":getCookie(\"telno\");var tmp_str = verifyurl+\"&userMobileNo=\"+telno+\"&redirect=\"+gourl;a_obj[k].href=tmp_str;}}}}</script></html>";
	            total_desc = "<html  xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\" /><meta http-equiv=\"Cache-Control\" content=\"no-cache\"/><META HTTP-EQUIV=\"pragma\" CONTENT=\"no-cache\"><META HTTP-EQUIV=\"expires\" CONTENT=\"0\"><meta id=\"viewport\" name=\"viewport\" content=\"width=320; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\" /><title>三生财智宝</title><script src=\"../../../scripts/common.js\"  charset=\"utf-8\"></script><script src=\"../../../scripts/pagination.js\"></script><style type=\"text/css\">body{margin:0;padding:0;color:#333;font-size:13pt;}*{line-height:22px;}p,ul,dl,dt,dd,h1,h2,h3,form{margin:0;padding:0px 0px 0px 10px}img{border:0}img,input{vertical-align:middle}li{list-style:none}a{color:#039;text-decoration:underline}a:hover{color:#f00;text-decoration:none}p,dl,div{padding:0 0px 0px 10px}#wrap{margin:0 auto;width:100%; }.p{background:#d80c18;border-bottom:1px solid #fff;line-height:25px;color:#fff;}.p a{color:#666}.p a:hover{color:#f00}.h3{text-align:center;line-height:25px;border-bottom:1px solid #ef8043;margin:0 5px;font-weight:normal;color:#ef8043}.info{text-align:center}</style></head><body onload=\"init();\"><div id='wrap' style=\"margin:0px;padding:0px;\"><p class=\"p\"  style=\"margin:0px;padding:0px;\">产品介绍</p><h3 class=\"h3\">" + name + "</h3><!--p class=\"info\">datetime2<span style=\"cursor:pointer\" onclick=\"doFont('wrap','13pt');\">[标准]</span>&nbsp;&nbsp;<span style=\"cursor:pointer\" onclick=\"doFont('wrap','10.5pt');\">[小]</span--></p><p><span id='span_total'>" + total + "</span><br/></p><p style=\"margin:5px 0px 10px 0px;\"><span id=\"page\" style=\"float:left;\"></span><!--span id=\"back\" style=\"margin-top:-3px;float:right;color:#ea5504;background:url(../../../images/return.png);width:85px;height:25px;line-height:25px;font-wieght:bold;;font-size:12pt;padding-left:8px;dispaly:none;\" onclick=\"parent.viewcon();\" onclick=\"parent.viewcon();\">返回上一级</span--></p><br/><p class=\"p p2\">三生财智宝</p></div></body><script> function init(){var container = document.getElementById('span_total');var total_len=_value[\"pos\"][1];for(var i=_value[\"pos\"][0];i<total_len;i++){document.getElementById(\"total\"+i).style.display='block';}}</script></html>";
	        }catch(Exception e){
				System.out.println(e);
			}
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

	public JSONObject saveProduct(String name, String proAddress,
			String typeno, String permission, String con,
			String introduceurl, String experturl, String useurl, String level,
			String code,String price,String dvalue,String pvvalue,String standard) {
		String rtn = "0";
		JSONObject rs = new JSONObject();
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd" + " " + "HH:mm:ss");
			Calendar p_cal = Calendar.getInstance();
	        java.util.Date p_date = p_cal.getTime();
			String datetime = tempDate.format(p_date);
			Product product = new Product();
			String productno = CUtil.createBillNo(8);
			product.setProductno(productno);
			product.setProductname(name);
			product.setImageurl(proAddress);
			product.setTypeno(Integer.valueOf(typeno));
			product.setContent(con);
			product.setCreatedate(p_date);
			product.setCode(code);
			product.setPrice(price);
			product.setDvalue(dvalue);
			product.setPvvalue(pvvalue);
			product.setIntroduceurl(introduceurl);
			product.setExperturl(experturl);
			product.setUseurl(useurl);
			product.setLevel(0);
			product.setStandard(standard);
			rtn = productDao.saveInfo(product);
			Product2group pg = new Product2group();
			pg.setGroupno(permission);
			pg.setProductno(productno);
			rtn = productDao.saveInfo(pg);
			if(typeno.equals("1")){
				rtn = serviceCountDao.updateInfo("goodFood");
			}else if(typeno.equals("2")){
				rtn = serviceCountDao.updateInfo("selfProtect");
			}else if(typeno.equals("3")){
				rtn = serviceCountDao.updateInfo("dressProduct");
			}else if(typeno.equals("4")){
				rtn = serviceCountDao.updateInfo("otherProduct");
			}
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
