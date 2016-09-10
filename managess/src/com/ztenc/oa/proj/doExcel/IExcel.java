package com.ztenc.oa.proj.doExcel;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.ztenc.oa.proj.bean.Member;
import com.ztenc.oa.proj.dao.member.MemberDao;
import com.ztenc.oa.proj.json.JSONArray;
import com.ztenc.oa.proj.json.JSONObject;

public class IExcel extends baseExcel {
	
	public static void main(String[] args){
		try{
			byte[] aa="我们".getBytes("utf-8");
			InputStream is = new FileInputStream("C:/test123.xls");
			IExcel excel = new IExcel();
			HttpServletResponse response = null;
			HttpServletRequest request = null;
			excel.init(is,response,request);
			excel.setCellTypeAndName(0, IExcel.data_text, "account",IExcel.NOT_EMPTY);
			excel.setCellTypeAndName(1,IExcel.data_text,"telno",IExcel.NOT_EMPTY);
			excel.setCellTypeAndName(2,IExcel.data_text,"vpdn",IExcel.NOT_EMPTY);
			excel.setCellTypeAndName(3,IExcel.data_text,"date",IExcel.ALLOW_EMPTY);
			excel.readExcel();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public IExcel(){
		super(0);
	}
	
	public IExcel(int maxsize){
		super(maxsize);
	}
	
	public void excute(JSONObject obj){
		
		System.out.println("获得的excel数据=="+obj.toString());
		try{
			
			JSONArray data = obj.getJSONArray("data");
			JSONArray warn = obj.getJSONArray("warn");
			int len = data.length();
			if(this.request.getSession().getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE)!=null){
				System.out.println("context is not null");
			}else{
				System.out.println("context is null");
			}
			ApplicationContext context =(ApplicationContext)this.request.getSession().getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			MemberDao clientService = (MemberDao) context.getBean("memberDao");
			for(int i=0;i<len;i++){
				String telno = data.getJSONObject(i).getString("telno");
				String vpdn = data.getJSONObject(i).getString("vpdn");
				String account = data.getJSONObject(i).getString("account");
				String date = data.getJSONObject(i).getString("date");
				String pageno = data.getJSONObject(i).getString("pageno");
				String linenum = data.getJSONObject(i).getString("linenum");
				Member mem = clientService.memberSearchByAcc(account);
				
				if(mem!=null){
						System.out.println("member is exist");
						SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd");
						Date datetime = null;
						String old_vpdn = mem.getVpdn();
						if(!vpdn.equals(old_vpdn)){
							String content = "";
							
							
							if(date!=null){
								datetime = tempDate.parse(date);
							}else{
								Calendar p_cal = Calendar.getInstance();
								datetime = p_cal.getTime();
							}
							if(old_vpdn.equals("0")){
								
								mem.setEnableDate(datetime);
								mem.setDisableDate(null);
							}else{
								mem.setDisableDate(datetime);
							}
						}
						mem.setVpdn(vpdn);
						mem.setTelno(telno);
						clientService.updateInfo(mem);
				}else{
					JSONObject warn_obj = new JSONObject();
					warn_obj.put("pageno",pageno);
					warn_obj.put("linenum", linenum);
					warn_obj.put("err_type", "account");
					warn.put(warn_obj);
				}
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	
	public void afterExcute(String str){
		System.out.println("kkk in IExcel=="+str);
	}
	
	
	
	public ResultSet getRSList(String className,JSONObject param){
		ResultSet list = null;
		try{
			ApplicationContext context =(ApplicationContext)request.getSession().getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			MemberDao clientService = (MemberDao) context.getBean(className);
			list = clientService.memberSearchByExpert(param.getString("useracc"),param.getString("telno"),param.getString("vpdn"));
		}catch(Exception e){
			System.out.println(e);
		}
		return list;
	}
	
//	public boolean getExcel(HSSFWorkbook wb,String className,JSONObject param){
//		boolean flag = false;
//		try{
//		  
//		  HSSFSheet sheet1=wb.createSheet("sheet1");
//		  HSSFRow row=sheet1.createRow((short)0);
//		  HSSFCell cell=row.createCell((short)0);
//		  //HSSFFont font_header = wb.createFont();
//		  //font_header.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//		  //font_header.setFontHeightInPoints((short)16);
//		  //font_header.setColor(enumColorType.BLUE.Index);
//		  //HSSFCellStyle hs_header = wb.createCellStyle();
//		  //hs_header.setFont(font_header);
//		  //cell.setCellStyle(hs_header);
//		  cell.setEncoding((short)HSSFCell.ENCODING_UTF_16);
//		  cell.setCellValue("会员帐号");
//		  cell=row.createCell((short)1);
//		  //cell.setCellStyle(hs_header);
//		  cell.setEncoding((short)HSSFCell.ENCODING_UTF_16);
//		  cell.setCellValue("手机号");
//		  
//		  cell = row.createCell((short)2);
//		  //cell.setCellStyle(hs_header);
//		  cell.setEncoding((short)HSSFCell.ENCODING_UTF_16);
//		  cell.setCellValue("VPDN");
//		  
//		  cell=row.createCell((short)3);
//		  //cell.setCellStyle(hs_header);
//		  cell.setEncoding((short)HSSFCell.ENCODING_UTF_16);
//		  cell.setCellValue("处理时间");
//		  int i=1;
//		  //ResultSet list = getRSList(className,param);
//		  ApplicationContext context =(ApplicationContext)request.getSession().getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
//			MemberDao clientService = (MemberDao) context.getBean(className);
//			ResultSet list = clientService.memberSearchByExpert(param.getString("useracc"),param.getString("telno"),param.getString("vpdn"));
//		  if(list!=null){
//			while(((ResultSet)list).next()){
//				row=sheet1.createRow((short)i);
//				
//				//HSSFFont font = wb.createFont();
//				HSSFCellStyle hs = wb.createCellStyle();
//				//font.setColor(HSSFFont.COLOR_RED);
//				//font.setBoldweight((short)10);
//				//hs.setFont(font);
//				hs.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));//字符串型
//				cell.setCellStyle(hs);
//				cell = row.createCell((short)0);
//				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
//				cell.setCellValue(((ResultSet)list).getString(1));
//				
//				cell.setCellStyle(hs);
//				cell = row.createCell((short)1);
//				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
//				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//				cell.setCellValue(((ResultSet)list).getString(2));
//				
//				
//				cell.setCellStyle(hs);
//				cell = row.createCell((short)2);
//				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
//				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//				cell.setCellValue(((ResultSet)list).getString(3));
//				
//				cell.setCellStyle(hs);
//				cell=row.createCell((short)3);
//				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
//				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//				if(((ResultSet)list).getString(3).equals("0")){
//					cell.setCellValue(((ResultSet)list).getString(5));
//				}else{
//					cell.setCellValue(((ResultSet)list).getString(4));
//				}
//				i++;
//			}
//		  //FileOutputStream fileout=new FileOutputStream("workbook.xls");
//		         //output.flush(); 
//			if(response!=null){
//				OutputStream os = response.getOutputStream();
//				if(os!=null){
//					wb.write(os); 
//			         response.getWriter().close();
//			         flag = true;
//				}else{
//					flag = false;
//				}
//			}else{
//				flag = false;
//			}
//			
//		         
//		  }else{
//			  return false;
//		  }
//		}catch(Exception e){
//			System.out.println(e);
//		}
//		return flag;
//	}
	
	public   boolean   getExcel(HSSFWorkbook wb,String className,JSONObject param) 
	 {
		
	  boolean flag = false;
	  try{ 
	  HSSFSheet sheet1=wb.createSheet("sheet1");
	  /*CXML cxml = new CXML();
	  cxml.forXML(fis);
	  fis.close();
	  String value = cxml.getNodeText("/hyh/web-app/title");
	  if(!value.equals("")){
		  String color = cxml.getAttributeByXPath("/hyh/web-app/title", "color");
		  String size = cxml.getAttributeByXPath("/hyh/web-app/title", "font-size");
		  HSSFRow row=sheet1.createRow((short)0);
		  HSSFCell cell=row.createCell((short)3);
		  HSSFFont font = wb.createFont();
		  HSSFCellStyle hs = wb.createCellStyle();
		  enumColorType colorenum = enumColorType.getEnum(Integer.valueOf(color));
		  System.out.println("colorenum=="+colorenum.Index);
		  font.setColor(colorenum.Index);
		  font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		  font.setFontHeightInPoints(Short.parseShort(size));
		  hs.setFont(font);
		  //hs.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
		  cell.setCellStyle(hs);
		  cell.setEncoding((short)HSSFCell.ENCODING_UTF_16);
		  cell.setCellValue(value);
	  }*/
	  HSSFRow row=sheet1.createRow((short)0);
	  HSSFCell cell=row.createCell((short)0);
	  //HSSFFont font_header = wb.createFont();
	  //font_header.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	  //font_header.setFontHeightInPoints((short)16);
	  //font_header.setColor(enumColorType.BLUE.Index);
	  //HSSFCellStyle hs_header = wb.createCellStyle();
	  //hs_header.setFont(font_header);
	  //cell.setCellStyle(hs_header);
	  cell.setEncoding((short)HSSFCell.ENCODING_UTF_16);
	  cell.setCellValue("会员帐号");
	  cell=row.createCell((short)1);
	  //cell.setCellStyle(hs_header);
	  cell.setEncoding((short)HSSFCell.ENCODING_UTF_16);
	  cell.setCellValue("手机号");
	  
	  cell = row.createCell((short)2);
	  //cell.setCellStyle(hs_header);
	  cell.setEncoding((short)HSSFCell.ENCODING_UTF_16);
	  cell.setCellValue("VPDN");
	  
	  cell=row.createCell((short)3);
	  //cell.setCellStyle(hs_header);
	  cell.setEncoding((short)HSSFCell.ENCODING_UTF_16);
	  cell.setCellValue("处理时间");
	  ApplicationContext context =(ApplicationContext)request.getSession().getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
	  MemberDao clientService = (MemberDao) context.getBean(className);
	  ResultSet list = clientService.memberSearchByExpert(param.getString("useracc"),param.getString("telno"),param.getString("vpdn"));
		int i=1;
		if(list!=null){
		while(list.next()){
			row=sheet1.createRow((short)i);
			//HSSFFont font = wb.createFont();
			HSSFCellStyle hs = wb.createCellStyle();
			//font.setColor(HSSFFont.COLOR_RED);
			//font.setBoldweight((short)10);
			//hs.setFont(font);
			hs.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));//字符串型
			cell.setCellStyle(hs);
			cell = row.createCell((short)0);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(list.getString(1));
			
			cell.setCellStyle(hs);
			cell = row.createCell((short)1);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(list.getString(2));
			
			
			cell.setCellStyle(hs);
			cell = row.createCell((short)2);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(list.getString(3));
			
			cell.setCellStyle(hs);
			cell=row.createCell((short)3);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			if(list.getString(3).equals("0")){
				cell.setCellValue(list.getString(5));
			}else{
				cell.setCellValue(list.getString(4));
			}
			
			i++;
		}
	  //FileOutputStream fileout=new FileOutputStream("workbook.xls");
	  
	         //output.flush(); 
	         wb.write(response.getOutputStream()); 
	         response.getOutputStream().close();
	         flag = true;;
	  }else{
		  return false;
	  }
	    }catch   (Exception   e)   { 
	    	
	         e.printStackTrace(); 
	         flag = false;
	    }finally{
	    	try{
	    		flag = true;
	    	}catch(Exception e){
	    		flag = false;
	    	}
	    }
	    return flag;
	 }
}
