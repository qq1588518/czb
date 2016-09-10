package com.ztenc.oa.proj.doExcel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import basic.base.test.FilePart;
import basic.base.test.MultipartParser;
import basic.base.test.ParamPart;
import basic.base.test.Part;

import com.ztenc.oa.proj.dao.member.MemberDao;
import com.ztenc.oa.proj.json.JSONArray;
import com.ztenc.oa.proj.json.JSONObject;

public class baseExcel extends abstractExcel {
	
	HSSFRow row = null;
	HSSFCell cell = null;
	POIFSFileSystem system = null;
	HSSFWorkbook book = null;
	int book_page = -1;                     //excel页数
	int maxSize = 0;						//最大上传的字节大小
	int mode = 0;
	int titlerows = 1;                      //默认的标题行数为1行
	String class_name = "";
	JSONArray cell_type = new JSONArray();     //存储定义excel表列类型
	JSONArray cell_name = new JSONArray();	   //存储定义excel表列名
	JSONArray cell_prop = new JSONArray();
	HttpServletResponse response = null;
	HttpServletRequest request = null;
	public baseExcel(int maxsize){
		maxSize = maxsize;
	}
	
	//默认从request流中读取字节
	public void init(HttpServletResponse response,HttpServletRequest request){
		try{
			this.response = response;
			this.request = request;
	        system = new POIFSFileSystem(getInputStream());
			book = new HSSFWorkbook(system);
			book_page = book.getNumberOfSheets();
			
		}catch(IOException e){
			try{
				if(e.getMessage().indexOf("Posted content length of")!=-1){
					if(response!=null){
			        	response.getWriter().print("<script language=javascript>alert('文件大小超过"+maxSize+"，上传失败！');history.go(-1)</script>");
			        	response.getWriter().flush();
			        	response.getWriter().close();
					}
		        }
			}catch(Exception ex){
				
			}
		}
		
	}
	
	//指定当前的inputstream流读取
	public void init(InputStream is,HttpServletResponse response,HttpServletRequest request){
		
		try{
			system = new POIFSFileSystem(is);
			book = new HSSFWorkbook(system);
			book_page = book.getNumberOfSheets();
			this.response = response;
			this.request = request;
		}catch(Exception e){
			
		}
		
	}
	
//	public void setClassName(String classname){
//		this.class_name = classname;
//	}
	
//	public void setImportMode(int mode){
//		
//		this.mode = mode;
//	}
	
	//设置excel表列类型与名称及属性
	public void setCellTypeAndName(int index,int type,String name,int prop){
		try{
			
			cell_type.put(index,type);
			cell_name.put(index,name);
			cell_prop.put(index,prop);
		}catch(Exception e){
			
		}
	}
	
	//设置最大字节数
	public void setMaxSize(int maxsize){
		this.maxSize = maxsize;
	}
	
	//设置excel标题的行数
	public void setTitleRowNum(int linenum){
		this.titlerows = linenum;
	}
	
	//读取excel表数据,并回调读取后的操作
	public void readExcel(){
		
		try{
			JSONObject err_obj = null;
			JSONArray jsonarr = new JSONArray();
			JSONObject obj = new JSONObject();
			JSONArray all_params = new JSONArray();
			JSONArray params = new JSONArray();
			JSONObject param = new JSONObject();
			JSONObject rs = new JSONObject();
			Pattern  patt = Pattern.compile("^\\s|\\s$",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
			StringBuffer sb = new StringBuffer();
			ArrayList ll = new ArrayList();
			HashMap hm = new HashMap();
			int sb_start = 0;
			int sb_point = 0;
			if(book!=null){
				for(int k=0;k<book_page;k++){
					HSSFSheet sheet = book.getSheetAt(k);
					int rows=sheet.getLastRowNum();
					params = new JSONArray();
					for (int r = 0; r < rows; r++)
	                {
						row   = sheet.getRow(r+titlerows);
						if (row != null) {
							int len = cell_type.length();
							for(int l = 0;l<len;l++){
								
								cell = row.getCell((short)l);
								System.out.println("cell_type.getInt(l)=="+cell_type.getInt(l));
	                  			//System.out.println("cell_name.getString(l)=="+cell_name.getString(l)+"=="+cell.getStringCellValue());
								if(cell==null) System.out.println("it's null");
								if(cell!=null){
									System.out.println("cell.getCellType()=="+cell.getCellType());
									if(cell.getCellType()==1){
										System.out.println("value=="+cell.getStringCellValue());
										
										if(cell_prop.getInt(l)==NOT_EMPTY && patt.matcher(cell.getStringCellValue()).replaceAll("").equals("")){
										System.out.println("it's wrong!");
										err_obj = new JSONObject();
			                  			err_obj.put("pageno", k);
			                  			err_obj.put("linenum",r);
			                  			err_obj.put("err_info",ERR_TYPE );
			                  			jsonarr.put(err_obj);
			                  			System.out.println("sb_start=="+sb_start);
			                  			System.out.println("sb_point=="+sb_point);
			                  			sb.delete(sb_start, sb_point);
			                  			sb_point = sb_start;
			                  			System.out.println("delete sb=="+sb.toString());
			                  			break;
										}
			                  		}else if(cell.getCellType()==3){
			                  			if(cell_prop.getInt(l)==NOT_EMPTY){
			                  				System.out.println("it's wrong22!");
											err_obj = new JSONObject();
				                  			err_obj.put("pageno", k);
				                  			err_obj.put("linenum",r);
				                  			err_obj.put("err_info",ERR_TYPE );
				                  			jsonarr.put(err_obj);
				                  			System.out.println("sb_start=="+sb_start);
				                  			System.out.println("sb_point=="+sb_point);
				                  			sb.delete(sb_start, sb_point);
				                  			sb_point = sb_start;
				                  			System.out.println("delete sb=="+sb.toString());
				                  			break;
			                  			}
			                  		}
		                  		  if(cell.getCellType()==cell_type.getInt(l)){
		                  			  
		                  			//sb_start = sb.length();
		                  			  switch(cell_type.getInt(l)){
//			                  			  case 0:param.put(cell_name.getString(l), cell.getNumericCellValue()); break;
//			                  			  case 1:param.put(cell_name.getString(l),cell.getStringCellValue()); break;
//			                  			  case 2:param.put(cell_name.getString(l),cell.getDateCellValue()); break;
//			                  			  case 3:param.put(cell_name.getString(l),cell.getBooleanCellValue()); break;
		                  			  case 0:  String tmp = cell_name.getString(l)+":\""+cell.getNumericCellValue()+"\",";sb.append(cell_name.getString(l)+":\""+cell.getNumericCellValue()+"\",");sb_point+=tmp.length(); break;
		                  			  case 1:String tmp2 = cell_name.getString(l)+":\""+cell.getStringCellValue()+"\",";sb.append(cell_name.getString(l)+":\""+cell.getStringCellValue()+"\",");sb_point+=tmp2.length(); break;
		                  			  case 2:String tmp3 = cell_name.getString(l)+":\""+cell.getDateCellValue()+"\",";sb.append(cell_name.getString(l)+":\""+cell.getDateCellValue()+"\",");sb_point+=tmp3.length(); break;
		                  			  case 3:String tmp4 = cell_name.getString(l)+":\""+cell.getBooleanCellValue()+"\",";sb.append(cell_name.getString(l)+":\""+cell.getBooleanCellValue()+"\",");sb_point+=tmp4.length(); break;
		                  			  }
		                  		  }else{
		                  			  System.out.println("nnd");
		                  			  err_obj = new JSONObject();
		                  			  err_obj.put("pageno", k);
		                  			  err_obj.put("linenum",r);
		                  			  err_obj.put("err_info",ERR_TYPE );
		                  			  jsonarr.put(err_obj);
		                  			  System.out.println("sb_start=="+sb_start);
		                  			  System.out.println("sb_point=="+sb_point);
		                  			  sb.delete(sb_start, sb_point);
		                  			  sb_point = sb_start;
		                  			  System.out.println("delete sb=="+sb.toString());
		                  			  break;
		                  		  }
		                  		 
			                  	}else{
			                  		  System.out.println("ddd");
			                  		  err_obj = new JSONObject();
			                  		  err_obj.put("pageno",k);
			                  		  err_obj.put("linenum", r);
			                  		  err_obj.put("err_info",ERR_CELL);
			                  		  jsonarr.put(err_obj);
			                  		System.out.println("sb_start=="+sb_start);
		                  			System.out.println("sb_point=="+sb_point);
		                  			sb.delete(sb_start, sb_point);
		                  			sb_point = sb_start;
		                  			System.out.println("delete sb=="+sb.toString());
			                  		  break;
			                  	}
								
							}
							System.out.println("sb=="+sb.toString());
							System.out.println("start =="+sb_start);
							System.out.println("point =="+sb_point);
							
							if(sb_point-sb_start>0){
							  String warn_str = ",pageno:"+k+",linenum:"+r;
							  int len_warn = warn_str.length();
							  sb.insert(sb_start, "{");
	                  		  sb.insert(sb.length()-1,warn_str+"}");
	                  		  sb_point +=3+len_warn;
							}
	                  		  sb_start = sb.length();
	                  		  System.out.println("sb=="+sb.toString());
							//sb_start = sb.length();
							//param = new JSONObject(sb.toString());
							//params.put(param);
						}
	                    
	                }
					//all_params.put(params);
				}
				System.out.println("sb=="+sb.toString());
				sb.setLength(sb.length()-1);
				sb.insert(0, "[");
				sb.insert(sb.length(), "]");
				System.out.println("in finally sb=="+sb.toString());
				all_params = new JSONArray(sb.toString());
				System.out.println("sb=="+sb.toString());
				obj.put("data", all_params);
				obj.put("warn", jsonarr);
				Object[] o_param = new Object[1]; 
				System.out.println("all_params=="+all_params.toString());
				o_param[0]=obj;
				this.invoke("baseExcel","excute",o_param);
				System.out.println("obj in baseExcel======="+obj.toString()); 
				afterRead(obj);
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	//读取完excel数据之后对excel文档的提示信息的操作,例如可能的上传错误信息等.
	public String afterRead(JSONObject obj){
		
		String info = "";
		boolean flag = false;
		try{
			
        	if(response!=null){
        		JSONArray arr = obj.getJSONArray("warn");
            	String tmp_str = "有"+arr.length()+"条数据未上传成功，请检查您要上传数据的格式是否正确并且确认财智宝有此会员帐号。";
            	if(arr.length()==0){
            
    	        	info = "<script language=javascript>alert('上传成功！');history.go(-1)</script>";
    	        }else{
    	        	
    	        	String tmp_line = "";
    	        	String tmp_pn = "";
    	        	int t = 0;
    	        	for(int i=0;i<arr.length();i++){
    	        	
    	        		String pageno = arr.getJSONObject(i).getString("pageno");
    	        		String linenum = arr.getJSONObject(i).getString("linenum");
    	        			if(t == 0){
    	        				tmp_line =(Integer.valueOf(linenum)+titlerows+1)+"行,";
    	        				tmp_str += "在第"+(Integer.valueOf(pageno)+1)+"张表中"+tmp_line;
    	        				System.out.println("pageno="+pageno);
    	        				tmp_pn = pageno;
    	        				System.out.println("tmp_pn="+tmp_pn);
    	        				t=1;
    	        			}else{
    	        				if(tmp_pn.equals(pageno)){
    	        					tmp_line =(Integer.valueOf(linenum)+titlerows+1)+"行,";
    	        					System.out.println("aaaa");
    	        					tmp_str +=tmp_line;
    	        				}else{
    	        					tmp_line =(Integer.valueOf(linenum)+titlerows+1)+"行,";
    	        					tmp_str += "在第"+(Integer.valueOf(pageno)+1)+"张表中"+tmp_line;
    	        					System.out.println("pageno1="+pageno);
    	        					tmp_pn = pageno;
    	        					System.out.println("tmp_pn1="+tmp_pn);
    	        				}
    	        			}
    					
    	        	}
    	        	tmp_str = tmp_str.substring(0,tmp_str.length()-1);
    	        	info = "<script language=javascript>alert('"+tmp_str+"');history.go(-1)</script>";
    	        	response.getWriter().print(info);
    	        	response.getWriter().flush();
    	        	response.getWriter().close();
                	Object[] o_param = new Object[1];
                	o_param[0] = info;
                	this.invoke("baseExcel","afterExcute",o_param);
    	        }
        	}else{
        		
        	}
        	
		}catch(Exception e){
			System.out.println(e);
		}finally{
			try{
				if(response!=null){
					response.getWriter().flush();
		        	response.getWriter().close();
				}
			}catch(Exception e){
				
			}
		}
		return info;
	}
	
	//从数据源中读取数据并导出为excel文档
	public void writeExcel(String className,JSONObject params){
		try{ 
			  HSSFWorkbook wb=new HSSFWorkbook();
			  Object[] o_param = new Object[3]; 
			  o_param[0] = wb;
			  o_param[1] = className;
			  o_param[2] = params;
			  
			  this.invoke("baseExcel","getExcel",o_param);
			  
		}catch(Exception e){
			System.out.println("e==="+e);
		}
	}
	
	
	
	//一个默认的回调函数
	public Object invoke(String class_name,String method_name,Object[] param) throws Exception {
		
        Object rtn=null;
        System.out.println("this.getClass().getName()=="+this.getClass().getName());
        Class cls=Class.forName(this.getClass().getName());
        Object obj=(Object)cls.newInstance();
        int len=0;
        if(param!=null){
            len=param.length;
        }
        Class [] ar_class=null;
        if(len>0){
            ar_class=new Class[len];
        }
        for(int loop=0;loop<len;loop++){
            ar_class[loop]=param[loop].getClass();
        }

        java.lang.reflect.Method md=cls.getMethod(method_name,ar_class);
        md.setAccessible(true);
        rtn=md.invoke(this, param);
        return rtn;
        
    }
	//获得request字节流对象
	public InputStream getInputStream() throws IOException{
		
		int size = maxSize * 1024 * 1024;
        String content_type="";
        InputStream file=null;
        byte[] in_byte=null;
        //CDBContainer cDBContainer = new CDBContainer();        
        /** 上传部分 先取出part判断是文件还是属性 */
        Part part = null;
        HashMap paramMap=new HashMap();
        MultipartParser mrequest = new MultipartParser(request, maxSize);
        mrequest.setEncoding("GBK");
        InputStream fileparts=null;
        while ((part = mrequest.readNextPart()) != null){
            if(part.isParam()){
                ParamPart paramPart = (ParamPart) part;
                paramMap.put(paramPart.getName(), paramPart.getStringValue());
            }
            if (part.isFile()) {
                /** 转化为 filePart*/
                FilePart filepart = (FilePart) part;
                content_type=filepart.getContentType();
                file=filepart.getInputStream();
                fileparts = file;
                in_byte=new byte[maxSize];
                int len = file.read(in_byte);
                System.out.println("len=="+len);
            }
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(in_byte);
		return bais;
	}

	//默认的excute方法,子类应该实现自己的方法.
	public void excute(JSONObject param){
		System.out.println("请在子类中实现自己的excute方法");
	}
	//默认的getExcel方法,子类应该实现自己的方法
	public boolean getExcel(HSSFWorkbook wb,String className,JSONObject param){
		System.out.println("请在子类中实现自己的getExcel方法");
		return true;
	}
	
	//默认的afterExcute方法,子类应该实现自己的方法
	
	public void afterExcute(String str){
		System.out.println("请在子类中实现自己的gafterExcute方法");
	}
	
}
