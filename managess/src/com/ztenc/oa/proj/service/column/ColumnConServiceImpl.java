package com.ztenc.oa.proj.service.column;

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

import com.ztenc.oa.proj.bean.CataGroup;
import com.ztenc.oa.proj.bean.Catagory;
import com.ztenc.oa.proj.bean.CatagoryCon;
import com.ztenc.oa.proj.bean.CatatoryConGroup;
import com.ztenc.oa.proj.bean.ProAnswer;
import com.ztenc.oa.proj.dao.ServiceCount.ServiceCountDao;
import com.ztenc.oa.proj.dao.column.ColumnConDao;
import com.ztenc.oa.proj.json.JSONArray;
import com.ztenc.oa.proj.json.JSONObject;
import com.ztenc.oa.proj.util.CUtil;
import com.ztenc.oa.proj.util.pagination.PcurrentDataDaoService;

public class ColumnConServiceImpl implements ColumnConService {
	
	ColumnConDao columnConDao;
	
	public void setColumnConDao(ColumnConDao columnConDao) {
		this.columnConDao = columnConDao;
	}
	
	ServiceCountDao serviceCountDao;
	
	public void setServiceCountDao(ServiceCountDao serviceCountDao) {
		this.serviceCountDao = serviceCountDao;
	}
	
	// 得到栏目列表
	public List getpro(String proid) {
		List list = null;
		list = columnConDao.getpro(proid);
		return list;
	}
	
	// 得到权限组信息
	public List getGroups() {
		List list = null;
		list = columnConDao.getGroups();
		return list;
	}
	
	// 修改时得到栏目内容
	public List getlistCatagorycon(String id) {
		List list = null;
		list = columnConDao.getlistCatagorycon(id);
		System.out.println("listcon == " + list.size());
		return list;
	}
	
	public String getContentFromFile(String dirname, String url) {
		File dir2;
		String pic = "";// 隐藏域内的图片地址
		String proid = "";
		String subProid = "";
		int permission = 0;
		String proAddress = "";
		String fileName = "";
		String tmp_str = "";
		// SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd" + " " + "HH:mm:ss");
		SimpleDateFormat tempDate1 = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		Calendar p_cal = Calendar.getInstance();
		java.util.Date p_date = p_cal.getTime();
		// String datetime = tempDate.format(p_date);
		String datetime1 = tempDate1.format(p_date);
		// String content_desc =
		// "<table cellpadding='0' cellspacing='0' width='100%' height='100%' BGCOLOR='#f0f7f9'><tr height='80px'><td align='center' valign='center' width='100%' height='80px'><span style='font-size:20px;FONT-FAMILY:宋体; mso-ascii-font-family:Times New Roman; mso-hansi-font-family:Times New Roman'><b>"+title+"</b></span></td></tr><tr height='60px'><td align='center' valign='center' width='100%' height='60px'><a href=''>http://www.cmfx56.cn</a>&nbsp;&nbsp;"+datetime1+"&nbsp;&nbsp;中国移动物流网</td></tr><tr><td align='left' valign='top' width='100%' height='100%'>"
		// + con + "</td></tr></table>";
		String filepro = "upload/catagory/images/";
		dirname = dirname + url;
		
		long written = 0L;
		java.io.InputStream fileIn = null;
		java.io.InputStreamReader isr = null;
		String name = datetime1 + ".html";
		name = name.replaceAll("-|:", "");
		url = dirname;
		File _name = new File(url);
		BufferedReader br = null;
		try {
			// ByteArrayInputStream bais = new ByteArrayInputStream(con.getBytes());
			
			fileIn = new BufferedInputStream(new FileInputStream(_name));
			if (_name.isFile()) {
				File file;
				isr = new java.io.InputStreamReader(fileIn);
				br = new BufferedReader(isr);
				
				String hhh = "";
				tmp_str = "";
				while ((hhh = br.readLine()) != null) {
					tmp_str += hhh;
				}
				System.out.println("hhhhhh====" + tmp_str);
				/*
				 * byte buf[] = new byte[8192]; int i; System.out.println("bais=="+ new String(buf)); while((i = bais.read(buf)) != -1) { osw.write(i); //out.write(buf, 0, i); //size += i;
				 * 
				 * }
				 */
				// System.out.println("bais222=="+ new String(buf));
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (fileIn != null)
					fileIn.close();
				if (br != null)
					br.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return tmp_str;
		
	}
	
	public String deleteFromFile(String dirname, String url) {
		String rtn = "0";
		String filepro = "upload/catagory/file/";
		dirname = dirname + filepro;
		File dir2 = new File(dirname);
		File _name = new File(dir2, url);
		try {
			_name.delete();
			rtn = "1";
		} catch (Exception e) {
			System.out.println(e);
			rtn = "0";
		}
		return rtn;
	}
	
	public String deleteFromImages(String dirname, String url) {
		String rtn = "0";
		String filepro = "upload/catagory/images/";
		dirname = dirname + filepro;
		File dir2 = new File(dirname);
		File _name = new File(dir2, url);
		try {
			_name.delete();
			rtn = "1";
		} catch (Exception e) {
			System.out.println(e);
			rtn = "0";
		}
		return rtn;
	}
	
	public String saveContentToFile(String title, String con, String dirname, String filename, int curpageno, String total, String total_filenames, String filepro, String dates) {
		String url = "";
		String saveDirectory1 = "";
		File dir2;
		SimpleDateFormat tempDate2 = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
		Calendar p_cal = Calendar.getInstance();
		java.util.Date p_date = p_cal.getTime();
		String datetime2 = tempDate2.format(p_date);
		String content_desc = "";
		String total_desc = "";
		// modify by Tim 20110919
		content_desc = "<html  xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\" /><meta http-equiv=\"Cache-Control\" content=\"no-cache\"/><META HTTP-EQUIV=\"pragma\" CONTENT=\"no-cache\"><META HTTP-EQUIV=\"expires\" CONTENT=\"0\"><meta id=\"viewport\" name=\"viewport\" content=\"width=320; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\" /><title>三生财智宝</title><script src=\"../../../scripts/common.js\"  charset=\"utf-8\"></script><script src=\"../../../scripts/pagination.js\"  charset=\"utf-8\"></script><style type=\"text/css\">body{margin:0;padding:0;color:#333;background-color:#fff9f2;font-size:13pt;}*{line-height:22px;}p,ul,dl,dt,dd,h1,h2,h3,form{margin:0;padding:0px 0px 0px 10px}img{border:0}img,input{vertical-align:middle}li{list-style:none}a{color:#039;text-decoration:underline}a:hover{color:#f00;text-decoration:none}p,dl,li,div{padding:0 0px 0px 10px}#wrap{margin:0 auto;width:100%; background-color:#fff9f2;}.p{background:#d80c18;border-bottom:1px solid #fff;line-height:25px;color:#fff;}.p a{color:#666}.p a:hover{color:#f00}.h3{text-align:center;line-height:25px;border-bottom:1px solid #ef8043;margin:5px;font-weight:normal;color:#ef8043}.info{text-align:center}</style></head><body onload=\"init();\"><div id='wrap' style=\"padding:0px;margin:0px;\"><!--p class=\"p\">移动资讯</p--><h3 class=\"h3\">"
				+ title
				+ "</h3><!--p class=\"info\">datetime2<span style=\"cursor:pointer\" onclick=\"doFont('wrap','13pt');\">[标准]</span>&nbsp;&nbsp;<span style=\"cursor:pointer\" onclick=\"doFont('wrap','10.5pt');\">[小]</span--></p><p><span id=\"start\">"
				+ con
				+ "</span><span id=\"end\" style=\"display:none\"></span><br/></p><p style=\"margin:5px 0px 10px 0px;\"><span id=\"page\" style=\"float:left;\"></span><!--span id=\"back\" style=\"margin-top:-3px;float:right;color:#ea5504;background:url(../../../images/return.png);width:85px;height:25px;line-height:25px;font-wieght:bold;;font-size:12pt;padding-left:8px;\" onclick=\"parent.viewcon();\">返回上一级</span></p><br/><p class=\"p p2\">三生财智宝</p--></div></body><script>function init(){viewPage(document.getElementById('page'),'"
				+ filename
				+ "',"
				+ curpageno
				+ ",'"
				+ total_filenames
				+ "',\"false\");var a_obj=document.getElementsByTagName(\"A\");var a_len = a_obj.length;for(var k=0;k<a_len;k++){if(a_obj[k].href.indexOf(\"192.103.137.34\")!=-1){if(getCookie(\"vpdn\")==\"0\" || getCookie(\"vpdn\")==undefined || getCookie(\"vpdn\")==null){a_obj[k].href=\"javascript:void(0);\";a_obj[k].target=\"_self\";a_obj[k].onclick=function(){alert(\"此功能需开通VPDN业务后使用！\");}}else{var verifyurl = getCookie(\"verifyurl\");var gourl = a_obj[k].href;if(gourl==\"\" || gourl==undefined || gourl==null){a_obj[k].href=\"javascript:void(0);\";a_obj[k].onclick=function(){alert(\"跳转地址有误,请联系管理员\");};return;};var telno=getCookie(\"telno\")==null?\"15000000000\":getCookie(\"telno\");var tmp_str = verifyurl+\"&userMobileNo=\"+telno+\"&redirect=\"+gourl;a_obj[k].href=tmp_str;}}}}</script></html>";
		total_desc = "<html  xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\" /><meta http-equiv=\"Cache-Control\" content=\"no-cache\"/><META HTTP-EQUIV=\"pragma\" CONTENT=\"no-cache\"><META HTTP-EQUIV=\"expires\" CONTENT=\"0\"><meta id=\"viewport\" name=\"viewport\" content=\"width=320; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\" /><title>三生财智宝</title><script src=\"../../../scripts/common.js\"  charset=\"utf-8\"></script><script src=\"../../../scripts/pagination.js\"></script><style type=\"text/css\">body{margin:0;padding:0;color:#333;background-color:#fff9f2;font-size:13pt;}*{line-height:22px;}p,ul,dl,dt,dd,h1,h2,h3,form{margin:0;padding:0px 0px 0px 10px}img{border:0}img,input{vertical-align:middle}li{list-style:none}a{color:#039;text-decoration:underline}a:hover{color:#f00;text-decoration:none}p,dl,li,div{padding:0 0px 0px 10px}#wrap{margin:0 auto;width:100%; background-color:#fff9f2;}.p{background:#d80c18;border-bottom:1px solid #fff;line-height:25px;color:#fff;}.p a{color:#666}.p a:hover{color:#f00}.h3{text-align:center;line-height:25px;border-bottom:1px solid #ef8043;margin:5px;font-weight:normal;color:#ef8043}.info{text-align:center}</style></head><body onload=\"init();\"><div id='wrap' style=\"padding:0px;margin:0px;\"><p class=\"p\">移动资讯</p><h3 class=\"h3\">"
				+ title
				+ "</h3><!--p class=\"info\">datetime2<span style=\"cursor:pointer\" onclick=\"doFont('wrap','13pt');\">[标准]</span>&nbsp;&nbsp;<span style=\"cursor:pointer\" onclick=\"doFont('wrap','10.5pt');\">[小]</span--></p><p><span id='span_total'>"
				+ total
				+ "</span><br/></p><p style=\"margin:5px 0px 10px 0px;\"><span id=\"page\" style=\"float:left;\"></span><span id=\"back\" style=\"margin-top:-3px;float:right;color:#ea5504;background:url(../../../images/return.png);width:85px;height:25px;line-height:25px;font-wieght:bold;;font-size:12pt;padding-left:8px;dispaly:none;\" onclick=\"parent.viewcon();\">返回上一级</span></p><br/><p class=\"p p2\">三生财智宝</p></div></body><script>function init(){var container = document.getElementById('span_total');var total_len=_value[\"pos\"][1];for(var i=_value[\"pos\"][0];i<total_len;i++){document.getElementById(\"total\"+i).style.display='block';}}</script></html>";
		
		dirname = dirname + filepro;
		dir2 = new File(dirname);
		dir2.mkdirs();
		
		long written = 0L;
		OutputStream fileOut = null;
		java.io.OutputStreamWriter osw = null;
		java.io.OutputStreamWriter osw2 = null;
		// String name = datetime1+".html";
		// name = name.replaceAll("-|:", "");
		String[] fns = filename.split(",");
		String fn = fns[curpageno];
		url = filepro + fn;
		File _name = new File(dir2, fn);
		try {
			// ByteArrayInputStream bais = new ByteArrayInputStream(con.getBytes());
			
			_name.createNewFile();
			fileOut = new BufferedOutputStream(new FileOutputStream(_name));
			if (filename != null) {
				File file;
				if (dir2.isDirectory()) {
					file = new File(dir2, fn);
					System.out.println("name==" + fn);
				} else
					file = dir2;
				osw = new java.io.OutputStreamWriter(fileOut);
				osw.write(content_desc);
				osw.flush();
				if (curpageno == 0) {
					if (total_filenames != null) {
						File total_name = new File(dir2, total_filenames);
						total_name.createNewFile();
						BufferedOutputStream totalOut = new BufferedOutputStream(new FileOutputStream(total_name));
						osw2 = new java.io.OutputStreamWriter(totalOut);
						osw2.write(total_desc);
						osw2.flush();
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (fileOut != null)
					fileOut.close();
				if (osw != null)
					osw.close();
				if (osw2 != null)
					osw2.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return url;
		
	}
	
	public String saveContentToFileAddPic(String title, String con, String dirname, String filename, int curpageno, String total, String total_filenames, String filepro, String dates, String[] picp) {
		String url = "";
		String saveDirectory1 = "";
		
		System.out.println("sdsadsadsddssa====================================" + picp.length);
		int len = picp.length - 1;
		File dir2;
		SimpleDateFormat tempDate2 = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
		Calendar p_cal = Calendar.getInstance();
		java.util.Date p_date = p_cal.getTime();
		String datetime2 = tempDate2.format(p_date);
		String content_desc = "";
		String total_desc = "";
		content_desc = "<html  xmlns=\"http://www.w3.org/1999/xhtml\"><head>"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\" /><meta http-equiv=\"Cache-Control\" content=\"no-cache\"/>"
				+ "<META HTTP-EQUIV=\"pragma\" CONTENT=\"no-cache\"><META HTTP-EQUIV=\"expires\" CONTENT=\"0\"><meta id=\"viewport\" name=\"viewport\" content=\"width=320; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\" /><title>三生财智宝</title>"
				+ "<script src=\"../../../scripts/common.js\"  charset=\"utf-8\"></script>"
				+ "<script src=\"../../../scripts/pagination.js\"  charset=\"utf-8\">"
				+ "</script><style type=\"text/css\">"
				+ "body{margin:0;padding:0;color:#333;background-color:#fff9f2;font-size:13pt;}.n1{width:100%;padding:0px;overflow:hidden; zoom:1;border-top:5px solid #999;border-bottom:5px solid #999;}.n1 ul{float:left;text-align:center;overflow:hidden;}.n1 li{float:left;width:33%; text-align:center; margin:0 auto;}*{line-height:22px;}p,ul,dl,dt,dd,h1,h2,h3,form{margin:0;padding:0px 0px 0px 10px}img{border:0}img,input{vertical-align:middle}li{list-style:none}a{color:#039;text-decoration:underline}a:hover{color:#f00;text-decoration:none}p,dl,li,div{padding:0 0px 0px 0px}#wrap{margin:0 auto;width:100%; background-color:#fff9f2;}.p{background:#d80c18;border-bottom:1px solid #fff;line-height:25px;color:#fff;}.p a{color:#666}.p a:hover{color:#f00}.h3{text-align:center;line-height:25px;border-bottom:1px solid #ef8043;margin:5px;font-weight:normal;color:#ef8043}.info{text-align:center}.num1{background-color:#999999;width:100%;height:15px;padding-top:1px;}.num1 li{float:left; width:13px;display:block; color:#000; list-style-type:none;margin-left:4px;}.bigon {BACKGROUND: url(../../../images/jd_09.gif); WIDTH: 15px; COLOR: #fff; LINE-HEIGHT: 15px; HEIGHT: 15px; display:block; margin-left:4px;}.bigoff {BACKGROUND: url(../../../images/jd_07.gif); WIDTH: 15px; COLOR: #3f4040; LINE-HEIGHT: 15px; HEIGHT: 15px;display:block; margin-left:4px}"
				+ "</style></head><body onload=\"init();\"><div id='wrap' style=\"padding:0px;margin:0px;\"><!--p class=\"p\">移动资讯</p-->";
		// "<div id=\"fc\" style=\"margin:0 auto;width:100%;text-align:center;height:90px;background-color:#fff; overflow:hidden;padding-top:6px;padding-bottom:6px;\">";
		
		// int pos2 = 0;
		// int line = ((len%4)!=0)?(len/4+1):(len/4);//第几行显示
		// System.out.println("pos:"+len+",pos%4:"+len%4);
		// System.out.println("line=========================="+line);
		// int count = ((list.size()%4)!=0)?(list.size()/4+1):(list.size()/4);//共有多少行
		String _str = "";
		String _str2 = "";
		String tmp_str = "";
		int pos2 = 0;
		if (len > 3) {
			_str = "<table class=\"n1\"><tr> <td style=\"width:10%;margin:30px 0px -30px 0px;text-align:left;\"  onclick=\"leftmove();\"><img id=\"left_pic\" src=\"../../../images/left1.jpg\" style=\"cursor:pointer;\"></td><td style=\"width:80%;\" id=\"headinfo\"><table width=\"100%\"><tr>";
			_str2 = "</tr></table></td><td style=\"width:10%;margin:30px 0px -30px 0px;text-align:right;\" onclick=\"rightmove();\"><img id=\"right_pic\" src=\"../../../images/right.jpg\" style=\"cursor:pointer;\"></td></tr></table> ";
		} else {
			_str = "<table class=\"n1\"><tr> <td style=\"width:10%;margin:30px 0px -30px 0px;text-align:left;\"  onclick=\"leftmove();\"><img id=\"left_pic\" src=\"../../../images/left1.jpg\" style=\"cursor:pointer;\"></td><td style=\"width:80%;\" id=\"headinfo\"><table width=\"100%\"><tr>";
			_str2 = "</tr></table></td><td style=\"width:10%;margin:30px 0px -30px 0px;text-align:right;\" onclick=\"rightmove();\"><img id=\"right_pic\" src=\"../../../images/right1.jpg\" style=\"cursor:pointer;\"></td></tr></table> ";
		}
		// modify by Tim 20110915 ssczb -> managess
		for (int i = 1; i < len + 1; i++) {
			if (pos2 < 3) {
				tmp_str += "<td style=\"text-align:center;display:block\" id=\"item" + pos2 + "\">";
				tmp_str += "<img src=\"../../../../ssczb/" + picp[i] + "\" width=\"80\" height=\"100\" style='border:1px solid #999;' alt=\"\" />";
				tmp_str += "</td>";
			} else {
				tmp_str += "<td style=\"text-align:center;display:none\" id=\"item" + pos2 + "\">";
				tmp_str += "<img src=\"../../../../ssczb/" + picp[i] + "\" width=\"80\" height=\"100\"  style='border:1px solid #999;' alt=\"\" />";
				tmp_str += "</td>";
			}
			pos2++;
		}
		
		content_desc = content_desc + _str + tmp_str + _str2;
		
		// for(int i=1;i < len+1;i++){
		// System.out.println(len+ "len======================================="+picp[i]);
		// if(i==1){
		// content_desc = content_desc+ "<div valign=\"top\" style=\"display:block;\" onclick=Mea("+i+");><img src=\"../../../../ssczb/"+picp[i]+"\"  width=\"110\" height=\"90\" border=\"0\"/></div>";
		// }else if(i==len){
		// content_desc = content_desc+ "<div valign=\"top\" style=\"display:none;\" onclick=Mea(0);><img src=\"../../../../ssczb/"+picp[i]+"\"  width=\"110\" height=\"90\" border=\"0\"/></div>";
		// }else{
		// content_desc = content_desc+ "<div valign=\"top\" style=\"display:none;\" onclick=Mea("+i+");><img src=\"../../../../ssczb/"+picp[i]+"\"  width=\"110\" height=\"90\" border=\"0\"/></div>";
		// }
		// //content_desc = content_desc+ "<td valign=\"top\"><img src=\"../../../../ssczb/"+picp[i]+"\"  width=\"110\" height=\"90\" border=\"0\" /></td>";
		// }
		// content_desc = content_desc+ "</div><div id=\"num\" class=\"num1\"><ul>";
		// for(int i=0;i < len;i++){
		// //System.out.println(len+ "len======================================="+picp[i]);
		// if(i==0){
		// content_desc = content_desc+ "<li class='bigon' style=\"CURSOR: pointer\" onmouseover=Mea("+i+"); align=middle width=15>"+(i+1)+"</li>";
		// }else{
		// content_desc = content_desc+ "<li class='bigoff' style=\"CURSOR: pointer\" onmouseover=Mea("+i+"); align=middle width=15>"+(i+1)+"</li>";
		// }
		// //content_desc = content_desc+ "<td valign=\"top\"><img src=\"../../../../ssczb/"+picp[i]+"\"  width=\"110\" height=\"90\" border=\"0\" /></td>";
		// }
		// content_desc = content_desc+ "</ul></div>";
		content_desc = content_desc
				+ "<h3 class=\"h3\">"
				+ title
				+ "</h3><!--p class=\"info\">datetime2<span style=\"cursor:pointer\" onclick=\"doFont('wrap','13pt');\">[标准]</span>&nbsp;&nbsp;"
				+ "<span style=\"cursor:pointer\" onclick=\"doFont('wrap','10.5pt');\">[小]</span--></p><p>"
				+ "<span id=\"start\">"
				+ con
				+ "</span><span id=\"end\" style=\"display:none\"></span><br/></p><p style=\"margin:5px 0px 10px 0px;\">"
				+ "<!--span id=\"page\" style=\"float:left;\"></span><span id=\"back\" style=\"margin-top:-3px;float:right;color:#ea5504;background:url(../../../images/return.png);width:85px;height:25px;line-height:25px;font-wieght:bold;;font-size:12pt;padding-left:8px;\" onclick=\"parent.viewcon();\">"
				+ "返回上一级</span></p><br/><p class=\"p p2\">三生财智宝</p--></div></body>"
				+ "<script>"
				+ "var bbb=0;"
				+ "function rightmove(num){"
				+ "var headinfo = document.getElementById(\"headinfo\");"
				+ "	var li_len= headinfo.getElementsByTagName(\"td\").length;"
				+ "	if(li_len < 4){"
				+ "		document.getElementById(\"left_pic\").src =\"../../../images/left1.jpg\";"
				+ "	}else{"
				+ "		document.getElementById(\"left_pic\").src =\"../../../images/left.jpg\";"
				+ "		var j = 0;"
				+ "		for(var k=0;k<li_len;k++){"
				+ "			var item = document.getElementById('item'+k);"
				+ "			if(item.style.display=='block'){"
				+ "				bbb=k;"
				+ "			}"
				+ "		}"
				+ "		for(var i=0;i<li_len;i++){"
				+ "			var item = document.getElementById('item'+i);"
				+ "			if(item.style.display=='block' && bbb+1 <li_len){"
				+ "				item.style.display = \"none\";"
				+ "			}else{"
				+ "				if(i < li_len && j<3 && i>bbb){"
				+ "					item.style.display='block';"
				+ "					j++;"
				+ "				}"
				+ "			}"
				+ "		}"
				+ "		if(bbb+3 < li_len){"
				+ "			document.getElementById(\"right_pic\").src =\"../../../images/right.jpg\";"
				+ "		}else{"
				+ "			document.getElementById(\"right_pic\").src =\"../../../images/right1.jpg\";"
				+ "		}"
				+ "	}"
				+ "}"
				+

				"function leftmove(){"
				+ "	var headinfo = document.getElementById(\"headinfo\");"
				+ "	var li_len= headinfo.getElementsByTagName(\"td\").length;"
				+ "	for(var i=0;i<li_len;i++){"
				+ "		var item = document.getElementById('item'+i);"
				+ "		if(item.style.display=='block'&& i>=3){"
				+ "			item.style.display = \"none\";"
				+ "		}else{"
				+ "			if(i < li_len && i<3){"
				+ "				item.style.display='block';"
				+ "				aaa=0;"
				+ "			} "
				+ "		}"
				+ "	}"
				+ "	document.getElementById(\"left_pic\").src =\"../../../images/left1.jpg\";"
				+ "	if(li_len>3){"
				+ "		document.getElementById(\"right_pic\").src =\"../../../images/right.jpg\";"
				+ "	}else{"
				+ "		document.getElementById(\"right_pic\").src =\"../../../images/right1.jpg\";"
				+ "	}"
				+ "}"
				+ "var n=0;var showNum = document.getElementById(\"num\");var obj = document.getElementById(\"fc\");Mea(0);function Mea(value){setBg(value);plays(value);}function setBg(value){for(var i=0;i<"
				+ len
				+ ";i++){ if(value==i){showNum.getElementsByTagName(\"li\")[i].className='bigon';}else{	showNum.getElementsByTagName(\"li\")[i].className='bigoff';}  }}function plays(value){try{with (fc){filters[0].Apply();for(i=0;i<"
				+ len
				+ ";i++)i==value?obj.children[i].style.display=\"block\":obj.children[i].style.display=\"none\"; filters[0].play(); }}catch(e){var divlist = document.getElementById(\"fc\").getElementsByTagName(\"div\");for(i=0;i<"
				+ len
				+ ";i++){i==value?divlist[i].style.display=\"block\":divlist[i].style.display=\"none\";}}}"
				+ "function init(){viewPage(document.getElementById('page'),'"
				+ filename
				+ "',"
				+ curpageno
				+ ",'"
				+ total_filenames
				+ "',\"false\");var a_obj=document.getElementsByTagName(\"A\");var a_len = a_obj.length;"
				+ "for(var k=0;k<a_len;k++){if(a_obj[k].href.indexOf(\"192.103.137.34\")!=-1){if(getCookie(\"vpdn\")==\"0\" || getCookie(\"vpdn\")==undefined || getCookie(\"vpdn\")==null){a_obj[k].href=\"javascript:void(0);\";a_obj[k].target=\"_self\";a_obj[k].onclick=function(){alert(\"此业务尚未开通，请注册开通\");}}"
				+ "else{var verifyurl = getCookie(\"verifyurl\");var gourl = a_obj[k].href;if(gourl==\"\" || gourl==undefined || gourl==null){a_obj[k].href=\"javascript:void(0);\";a_obj[k].onclick=function(){alert(\"跳转地址有误,请联系管理员\");};return;};var telno=getCookie(\"telno\")==null?\"15000000000\":getCookie(\"telno\");var tmp_str = verifyurl+\"&userMobileNo=\"+telno+\"&redirect=\"+gourl;a_obj[k].href=tmp_str;}}}} "
				+ " var speed=20;document.getElementById(\"demo2\").innerHTML=document.getElementById(\"demo1\").innerHTML;function Marquee(){if(document.getElementById(\"demo2\").offsetWidth-document.getElementById(\"demo\").scrollLeft<=0)document.getElementById(\"demo\").scrollLeft-=document.getElementById(\"demo1\").offsetWidth;else{document.getElementById(\"demo\").scrollLeft++;}}var MyMar=setInterval(Marquee,speed);document.getElementById(\"demo\").onmouseover=function() {clearInterval(MyMar)};document.getElementById(\"demo\").onmouseout=function() {MyMar=setInterval(Marquee,speed)};</script></html>";
		total_desc = "<html  xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\" /><meta http-equiv=\"Cache-Control\" content=\"no-cache\"/><META HTTP-EQUIV=\"pragma\" CONTENT=\"no-cache\"><META HTTP-EQUIV=\"expires\" CONTENT=\"0\"><meta id=\"viewport\" name=\"viewport\" content=\"width=320; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\" /><title>三生财智宝</title><script src=\"../../../scripts/common.js\"  charset=\"utf-8\"></script><script src=\"../../../scripts/pagination.js\"></script><style type=\"text/css\">body{margin:0;padding:0;color:#333;background-color:#fff9f2;font-size:13pt;}*{line-height:22px;}p,ul,dl,dt,dd,h1,h2,h3,form{margin:0;padding:0px 0px 0px 10px}img{border:0}img,input{vertical-align:middle}li{list-style:none}a{color:#039;text-decoration:underline}a:hover{color:#f00;text-decoration:none}p,dl,li,div{padding:0 0px 0px 10px}#wrap{margin:0 auto;width:100%; background-color:#fff9f2;}.p{background:#d80c18;border-bottom:1px solid #fff;line-height:25px;color:#fff;}.p a{color:#666}.p a:hover{color:#f00}.h3{text-align:center;line-height:25px;border-bottom:1px solid #ef8043;margin:5px;font-weight:normal;color:#ef8043}.info{text-align:center}</style></head><body onload=\"init();\"><div id='wrap' style=\"padding:0px;margin:0px;\"><!--p class=\"p\">移动资讯1</p--><h3 class=\"h3\">"
				+ title
				+ "</h3><!--p class=\"info\">datetime2<span style=\"cursor:pointer\" onclick=\"doFont('wrap','13pt');\">[标准]</span>&nbsp;&nbsp;<span style=\"cursor:pointer\" onclick=\"doFont('wrap','10.5pt');\">[小]</span--></p><p><span id='span_total'>"
				+ total
				+ "</span><br/></p><p style=\"margin:5px 0px 10px 0px;\"><span id=\"page\" style=\"float:left;\"></span><span id=\"back\" style=\"margin-top:-3px;float:right;color:#ea5504;background:url(../../../images/return.png);width:85px;height:25px;line-height:25px;font-wieght:bold;;font-size:12pt;padding-left:8px;dispaly:none;\" onclick=\"parent.viewcon();\">返回上一级</span></p><br/><p class=\"p p2\">三生财智宝</p></div></body><script>function init(){var container = document.getElementById('span_total');var total_len=_value[\"pos\"][1];for(var i=_value[\"pos\"][0];i<total_len;i++){document.getElementById(\"total\"+i).style.display='block';}}</script></html>";
		
		dirname = dirname + filepro;
		dir2 = new File(dirname);
		dir2.mkdirs();
		
		long written = 0L;
		OutputStream fileOut = null;
		java.io.OutputStreamWriter osw = null;
		java.io.OutputStreamWriter osw2 = null;
		// String name = datetime1+".html";
		// name = name.replaceAll("-|:", "");
		String[] fns = filename.split(",");
		String fn = fns[curpageno];
		url = filepro + fn;
		File _name = new File(dir2, fn);
		try {
			// ByteArrayInputStream bais = new ByteArrayInputStream(con.getBytes());
			
			_name.createNewFile();
			fileOut = new BufferedOutputStream(new FileOutputStream(_name));
			if (filename != null) {
				File file;
				if (dir2.isDirectory()) {
					file = new File(dir2, fn);
					System.out.println("name==" + fn);
				} else
					file = dir2;
				osw = new java.io.OutputStreamWriter(fileOut);
				osw.write(content_desc);
				osw.flush();
				if (curpageno == 0) {
					if (total_filenames != null) {
						File total_name = new File(dir2, total_filenames);
						total_name.createNewFile();
						BufferedOutputStream totalOut = new BufferedOutputStream(new FileOutputStream(total_name));
						osw2 = new java.io.OutputStreamWriter(totalOut);
						osw2.write(total_desc);
						osw2.flush();
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (fileOut != null)
					fileOut.close();
				if (osw != null)
					osw.close();
				if (osw2 != null)
					osw2.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return url;
		
	}
	
	// 修改时得到栏目内容
	public List getCatgroupcon(String id) {
		List list = null;
		list = columnConDao.getCatgroupcon(id);
		return list;
	}
	
	public String listPro(String id) {
		String rtn = "";
		List list = null;
		try {
			list = columnConDao.listPro(id);
			JSONArray all = new JSONArray();
			JSONObject jsonobj = new JSONObject();
			JSONObject totalobj = new JSONObject();
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Catagory catagory = (Catagory) iter.next();
				String proId = catagory.getProId();
				String proName = catagory.getProName();
				JSONArray jsonarray = new JSONArray();
				jsonarray.put(proId);
				jsonarray.put(proName);
				all.put(jsonarray);
			}
			jsonobj.put("rs", all);
			StringBuilder sb = new StringBuilder();
			rtn = sb.append(jsonobj).toString();
		} catch (Exception e) {
			try {
			} catch (Exception ex) {
				System.out.println(ex);
			}
			System.out.println(e);
		} finally {
			try {
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}
		return rtn;
	}
	
	// 读取所有栏目信息
	public String getprotype(String title, String sub_protype, int index, int length) {
		String rtn = "";
		ResultSet rs = null;
		String _title = title;
		try {
			rs = columnConDao.getprotype(_title, sub_protype, index, length);
			JSONArray all = new JSONArray();
			JSONObject jsonobj = new JSONObject();
			JSONObject totalobj = new JSONObject();
			while (rs.next()) {
				JSONArray jsonarray = new JSONArray();
				String id = (String) rs.getString(1);
				String titl = (String) rs.getString(2);
				String con = (String) rs.getString(3);
				String pic = (String) rs.getString(4);
				String datetime = (String) rs.getString(5);
				String catConId = (String) rs.getString(7);
				String groupId = (String) rs.getString(8);
				String cata_id = (String) rs.getString(9);
				String sub_cata_id = (String) rs.getString(10);
				String groupName = (String) rs.getString(12);
				String proName = (String) rs.getString(15);
				String count = (String) rs.getString(20);
				jsonarray.put(id);
				jsonarray.put(titl);
				jsonarray.put(con);
				jsonarray.put(pic);
				jsonarray.put(datetime);
				jsonarray.put(catConId);
				jsonarray.put(groupId);
				jsonarray.put(cata_id);
				jsonarray.put(sub_cata_id);
				jsonarray.put(groupName);
				jsonarray.put(proName);
				jsonarray.put(count);
				all.put(jsonarray);
			}
			jsonobj.put("rs", all);
			StringBuilder sb = new StringBuilder();
			rtn = sb.append(jsonobj).toString();
			System.out.println("444444444444444444444444444444444444444444444444444444444=====" + rtn);
		} catch (Exception e) {
			try {
				rs.close();
			} catch (Exception ex) {
				System.out.println(ex);
			}
			System.out.println(e);
		} finally {
			try {
				rs.close();
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}
		return rtn;
	}
	
	// 删除栏目信息
	public String deleteproType(String id) {
		String rtn = "0";
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		String typeno = null;
		try {
			List list = columnConDao.getCatgroupcon(id);
			for (int i = 0; i < list.size(); i++) {
				CatatoryConGroup pro = (CatatoryConGroup) list.get(i);
				typeno = pro.getSubCatagoryId();
			}
			System.out.println("typeno:" + typeno);
			rtn = columnConDao.deleteproType(id);
			if (typeno.equals("2QTDNJ1R")) {
				rtn = serviceCountDao.deleteInfo("getNews");
			} else if (typeno.equals("E2BIQM5U")) {
				rtn = serviceCountDao.deleteInfo("getHotSale");
			} else if (typeno.equals("OYFYMIPL")) {
				rtn = serviceCountDao.deleteInfo("getProductNews");
			} else if (typeno.equals("S2JIC2HY")) {
				rtn = serviceCountDao.deleteInfo("getRencentNews");
			}
			
			rtn = "1";
			jsonRtn.put("flag", rtn);
		} catch (Exception e) {
			rtn = "0";
			System.out.println(e);
		}
		rtn = sb.append(jsonRtn).toString();
		return rtn;
	}
	
	// 栏目添加
	public JSONObject saveProduct(String title, String proAddress, String proid, String subProid, int permission, String con, String datetime1, String id, String conpic) {
		String rtn = "0";
		JSONObject rs = new JSONObject();
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try {
			// String saveDirectory1 = saveDirectory;
			// String datetime1 = datetime;
			CatagoryCon catagoryCon = new CatagoryCon();
			catagoryCon.setId(id);
			catagoryCon.setTitle(title);
			catagoryCon.setCon(con);
			catagoryCon.setPic(proAddress);
			catagoryCon.setDates(datetime1);
			catagoryCon.setConpic(conpic);
			rtn = columnConDao.saveInfo(catagoryCon);
			
			CatatoryConGroup catatoryConGroup = new CatatoryConGroup();
			catatoryConGroup.setId(id);
			catatoryConGroup.setGroupid(permission);
			catatoryConGroup.setCatagoryId(proid);
			catatoryConGroup.setSubCatagoryId(subProid);
			rtn = columnConDao.saveInfo(catatoryConGroup);
			
			if (subProid.equals("2QTDNJ1R")) {
				rtn = serviceCountDao.updateInfo("getNews");
			} else if (subProid.equals("E2BIQM5U")) {
				rtn = serviceCountDao.updateInfo("getHotSale");
			} else if (subProid.equals("OYFYMIPL")) {
				rtn = serviceCountDao.updateInfo("getProductNews");
			} else if (subProid.equals("S2JIC2HY")) {
				rtn = serviceCountDao.updateInfo("getRencentNews");
			}
		} catch (Exception e) {
			rtn = "0";
			try {
				rs.put("flag", false);
			} catch (Exception ex) {
				System.out.println(ex);
			}
			return rs;
		}
		if (rtn.equals("1")) {
			try {
				rs.put("flag", true);
			} catch (Exception e) {
				System.out.println(e);
			}
			return rs;
		} else {
			try {
				rs.put("flag", false);
			} catch (Exception ex) {
				System.out.println(ex);
			}
			return rs;
		}
		
	}
	
	// 栏目修改
	public JSONObject alterProduct(String title, String proAddress, String proid, String subProid, int permission, String con, String datetime1, String id, String conpic) {
		String rtn = "0";
		JSONObject rs = new JSONObject();
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try {
			// String saveDirectory1 = saveDirectory;
			// String datetime1 = datetime;
			CatagoryCon catagoryCon = (CatagoryCon) columnConDao.getCatagoryConId(id);
			catagoryCon.setTitle(title);
			catagoryCon.setCon(con);
			catagoryCon.setPic(proAddress);
			catagoryCon.setConpic(conpic);
			// catagoryCon.setDates(datetime1);
			rtn = columnConDao.updateInfo(catagoryCon);
			
			// CataGroup cataGroup =(CataGroup) columnConDao.getCataGroupId(id);
			// cataGroup.setGroupid(Integer.valueOf(permission));
			CatatoryConGroup catatoryConGroup = new CatatoryConGroup();
			catatoryConGroup.setId(id);
			catatoryConGroup.setGroupid(permission);
			catatoryConGroup.setCatagoryId(proid);
			catatoryConGroup.setSubCatagoryId(subProid);
			rtn = columnConDao.updateInfo(catatoryConGroup);
		} catch (Exception e) {
			rtn = "0";
			try {
				rs.put("flag", false);
			} catch (Exception ex) {
				System.out.println(ex);
			}
			return rs;
		}
		if (rtn.equals("1")) {
			try {
				rs.put("flag", true);
			} catch (Exception e) {
				System.out.println(e);
			}
			return rs;
		} else {
			try {
				rs.put("flag", false);
			} catch (Exception ex) {
				System.out.println(ex);
			}
			return rs;
		}
	}
	
}
