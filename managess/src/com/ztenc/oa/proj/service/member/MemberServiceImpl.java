package com.ztenc.oa.proj.service.member;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.WebApplicationContext;

import com.lowagie.text.rtf.RtfWriter2;
import com.ztenc.oa.proj.bean.Member;
import com.ztenc.oa.proj.bean.Member2group;
import com.ztenc.oa.proj.dao.member.*;
import com.ztenc.oa.proj.json.JSONArray;
import com.ztenc.oa.proj.json.JSONObject;
import com.ztenc.oa.proj.util.pagination.PcurrentDataDaoService;
import com.ztenc.oa.proj.util.CUtil;
import com.ztenc.oa.proj.util.CXML;
import com.ztenc.oa.proj.util.enumColorType;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.util.HSSFColor;
import org.codehaus.xfire.client.Client;

public class MemberServiceImpl implements MemberService {
	
	MemberDao memberdao;
	
	public void setMemberDao(MemberDao memberdao) {
		this.memberdao = memberdao;
	}
	
	public List getGroup() {
		
		String rtn = "";
		List rs = null;
		List list = null;
		list = memberdao.getGroup();
		return list;
		/*
		 * try{ rs = memberdao.getGroup(); JSONArray all = new JSONArray(); JSONObject jsonobj = new JSONObject(); JSONObject totalobj = new JSONObject(); while(rs.next()){ JSONArray jsonarray = new
		 * JSONArray(); String memberno = (String)rs.getString(1); String membername = (String)rs.getString(2); String telno = (String)rs.getString(3); String machineid = (String)rs.getString(4);
		 * String code = (String)rs.getString(5); String memo = (String)rs.getString(6); String groupname = rs.getString(11); int total = rs.getInt(7); jsonarray.put(memberno);
		 * jsonarray.put(membername); jsonarray.put(telno); jsonarray.put(machineid); jsonarray.put(code); jsonarray.put(memo); jsonarray.put(total); jsonarray.put(groupname); all.put(jsonarray); }
		 * jsonobj.put("rs",all); StringBuilder sb = new StringBuilder(); rtn = sb.append(jsonobj).toString(); }catch(Exception e){ try{ rs.close(); }catch(Exception ex){ System.out.println(ex); }
		 * System.out.println(e); }finally{ try{ rs.close(); }catch(Exception ex){ System.out.println(ex); } } return rtn;
		 */

	}
	
	public String MemberSearchByKey(int index, int length, String membername, String telno, String vpdn) {
		
		String rtn = "";
		ResultSet rs = null;
		
		try {
			rs = memberdao.memberSearchByKey(index, length, membername, telno, vpdn);
			JSONArray all = new JSONArray();
			JSONObject jsonobj = new JSONObject();
			JSONObject totalobj = new JSONObject();
			while (rs.next()) {
				JSONArray jsonarray = new JSONArray();
				String memberno = (String) rs.getString(1);
				String _membername = (String) rs.getString(2);
				String _telno = (String) rs.getString(3);
				String useracc = (String) rs.getString(8);
				String _imsi = (String) rs.getString(7);
				// String memo = (String)rs.getString(6);
				String groupid = rs.getString(17);
				String groupname = rs.getString(18);
				String _vpdn = (String) rs.getString(10);
				String enabledate = (String) rs.getString(11);
				String disabledate = (String) rs.getString(12);
				String logindate = (String) rs.getString(6);
				int total = rs.getInt(14);
				jsonarray.put(memberno);
				jsonarray.put(_membername);
				jsonarray.put(useracc);
				jsonarray.put(_imsi);
				// jsonarray.put(memo);
				jsonarray.put(total);
				jsonarray.put(groupname);
				jsonarray.put(groupid);
				jsonarray.put(_vpdn);
				jsonarray.put(_telno);
				jsonarray.put(enabledate);
				jsonarray.put(disabledate);
				jsonarray.put(logindate);
				all.put(jsonarray);
			}
			jsonobj.put("rs", all);
			StringBuilder sb = new StringBuilder();
			rtn = sb.append(jsonobj).toString();
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
	
	// 会员信息读取，index为搜索开始位置，length为每页显示的长度
	public String MemberSearch(int index, int length) {
		String rtn = "";
		ResultSet rs = null;
		
		try {
			rs = memberdao.memberSearch(index, length);
			JSONArray all = new JSONArray();
			JSONObject jsonobj = new JSONObject();
			JSONObject totalobj = new JSONObject();
			while (rs.next()) {
				JSONArray jsonarray = new JSONArray();
				String memberno = (String) rs.getString(1);
				String membername = (String) rs.getString(2);
				String telno = (String) rs.getString(3);
				String useracc = (String) rs.getString(8);
				String imsi = (String) rs.getString(7);
				// String memo = (String)rs.getString(6);
				String groupid = rs.getString(17);
				String groupname = rs.getString(18);
				String vpdn = (String) rs.getString(10);
				String enabledate = (String) rs.getString(11);
				String disabledate = (String) rs.getString(12);
				String logindate = (String) rs.getString(6);
				int total = rs.getInt(14);
				jsonarray.put(memberno);
				jsonarray.put(membername);
				jsonarray.put(useracc);
				jsonarray.put(imsi);
				// jsonarray.put(memo);
				jsonarray.put(total);
				jsonarray.put(groupname);
				jsonarray.put(groupid);
				jsonarray.put(vpdn);
				jsonarray.put(telno);
				jsonarray.put(enabledate);
				jsonarray.put(disabledate);
				jsonarray.put(logindate);
				all.put(jsonarray);
			}
			jsonobj.put("rs", all);
			StringBuilder sb = new StringBuilder();
			rtn = sb.append(jsonobj).toString();
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
	
	public String deleteMember(String no) {
		String rtn = "0";
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try {
			rtn = memberdao.deleteMember(no);
			rtn = "1";
			jsonRtn.put("flag", rtn);
		} catch (Exception e) {
			rtn = "0";
			System.out.println(e);
		}
		rtn = sb.append(jsonRtn).toString();
		return rtn;
	}
	
	public String modifyMember(String memberno, String membername, String telno, String imsi, String groupno, String vpdn, String useracc) {
		String rtn = "0";
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		String old_vpdn = memberdao.getVPDN(memberno).getVpdn();
		try {
			SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd" + " " + "HH:mm:ss");
			Calendar p_cal = Calendar.getInstance();
			java.util.Date p_date = p_cal.getTime();
			String datetime = tempDate.format(p_date);
			Date old_enabledate = memberdao.getVPDN(memberno).getEnableDate();
			Member2group mg = new Member2group();
			mg.setGroupno(groupno);
			mg.setMemberno(memberno);
			Member member = memberdao.getMemberId(memberno);
			member.setMemberno(memberno);
			// member.setPassword(password);
			member.setImeinum(imsi);
			member.setUserAcc(useracc);
			member.setTelno(telno);
			member.setVpdn(vpdn);
			// member.setMemo(memo);
			// member.setMembername(membername);
			// member.setLoginDate(p_date);
			if (!vpdn.equals(old_vpdn)) {
				if (old_vpdn.equals("0")) {
					member.setEnableDate(p_date);
					member.setDisableDate(null);
				} else {
					
					member.setEnableDate(old_enabledate);
					member.setDisableDate(p_date);
				}
			}
			rtn = memberdao.updateInfo(mg);
			rtn = memberdao.updateInfo(member);
			jsonRtn.put("flag", rtn);
			System.out.println("====" + rtn);
		} catch (DataAccessException date) {
			rtn = "0";
			try {
				jsonRtn.put("flag", rtn);
			} catch (Exception e) {
				System.out.println(e);
			}
			System.out.println(date);
		} catch (Exception e) {
			System.out.println(e);
		}
		String content = "";
		if (!vpdn.equals(old_vpdn)) {
			if (vpdn.equals("1")) {
				content = "欢迎您使用三生财智宝视频包月服务，现服务已开通，每月将收取￥30元包月费，您可使用2G视频包月流量。请重新登陆财智宝。";
			} else {
				content = "您好，您的视频服务已关闭！";
			}
			//modify by Tim 20110907 if (send(telno, content))
			try {
				if (false) {
					jsonRtn.put("is_send", true);
					System.out.println("send successfull");
				} else {
					jsonRtn.put("is_send", false);
					System.out.println("send failure");
				}
			} catch (Exception e) {
				try {
					jsonRtn.put("is_send", false);
					System.out.println("send failure");
				} catch (Exception ex) {
					System.out.println("it's wrong");
				}
				System.out.println(e);
			}
		}
		rtn = sb.append(jsonRtn).toString();
		return rtn;
	}
	
	private boolean send(String telno, String content) {
		boolean res = false;
		try {
			Client ser = new Client(new URL("http://192.168.0.245:8069/sms/service/send.ws?wsdl"));
			Object[] o = ser.invoke("send", new String[] { telno, content });
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
	
	private boolean sendAll(JSONArray jsonarr) {
		boolean res = false;
		try {
			Client ser = new Client(new URL("http://192.168.0.245:8069/sms/service/send.ws?wsdl"));
			Object[] o = ser.invoke("sendAll", new String[] { jsonarr.toString(), "" });
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
	
	public String addMember(String username, String telno, String imsi, String groupno, String vpdn) {
		String rtn = "0";
		JSONObject jsonRtn = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try {
			SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd" + " " + "HH:mm:ss");
			Calendar p_cal = Calendar.getInstance();
			java.util.Date p_date = p_cal.getTime();
			String datetime = tempDate.format(p_date);
			String memberno = CUtil.createBillNo(8);
			String code = CUtil.createBillNo(16);
			Member member = new Member();
			Member2group mg = new Member2group();
			mg.setGroupno(groupno);
			mg.setMemberno(memberno);
			member.setMemberno(memberno);
			member.setImeinum(imsi);
			member.setUserAcc(telno);
			member.setCode(code);
			member.setMembername(username);
			member.setLoginDate(p_date);
			member.setVpdn(vpdn);
			rtn = memberdao.saveInfo(member);
			rtn = memberdao.saveInfo(mg);
			jsonRtn.put("flag", rtn);
			System.out.println("====" + rtn);
		} catch (DataAccessException date) {
			rtn = "0";
			try {
				jsonRtn.put("flag", rtn);
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		rtn = sb.append(jsonRtn).toString();
		return rtn;
	}
	
	public JSONObject readexcel(HttpServletRequest request, java.io.InputStream is) {
		// OutputStream out=response.getOutputStream();
		// System.out.println(filename);
		
		HSSFRow row = null;
		HSSFCell cell = null;
		String insuranceId = "";
		String insured = "";
		String phoneNum = "";
		String lpNum = "";
		String brand = "";
		String rtn = "0";
		int bad_num = 0;
		StringBuffer bad_arr = new StringBuffer();
		JSONArray jsonarr = new JSONArray();
		JSONArray smsarr = new JSONArray();
		JSONObject smsobj = null;
		JSONObject rs = new JSONObject();
		JSONObject obj = null;
		List s = null;
		String location = null;
		try {
			
			POIFSFileSystem fs = new POIFSFileSystem(is);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			System.out.println("wb.getNumberOfSheets()" + wb.getNumberOfSheets());
			Pattern patt = Pattern.compile("^\\s|\\s$", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
			for (int k = 0; k < 1; k++) {
				
				HSSFSheet sheet = wb.getSheetAt(k);
				// int rows = sheet.getPhysicalNumberOfRows();
				int rows = sheet.getLastRowNum();
				cell.getCellType();
				System.out.println("rows" + rows);
				for (int r = 0; r < rows; r++) {
					obj = new JSONObject();
					row = sheet.getRow(r + 1);
					cell = row.getCell((short) 0);
					if (row != null) {
						if ((row.getCell((short) 0) == null || row.getCell((short) 0).getStringCellValue().equals(""))
								&& (row.getCell((short) 1) == null || row.getCell((short) 1).getStringCellValue().equals(""))
								&& (row.getCell((short) 2) == null || row.getCell((short) 2).getStringCellValue().equals(""))
								&& (row.getCell((short) 3) == null || row.getCell((short) 3).getStringCellValue().equals("")))
							break;
						cell = row.getCell((short) 0);
						String memberno = null;
						String telno = null;
						String vpdn = null;
						String date = null;
						cell = row.getCell((short) 0);
						if (cell != null) {
							if (cell.getCellType() == 0) {
								memberno = String.valueOf(cell.getNumericCellValue());
							} else if (cell.getCellType() == 1 && !String.valueOf(cell.getStringCellValue()).equals("")) {
								memberno = String.valueOf(cell.getStringCellValue());
							}
						} else {
							bad_num++;
							obj.put("memberno", k);
							obj.put("linenum", r);
							// bad_arr.append("{k:"+k+",r:"+r+"};");
							jsonarr.put(obj);
							continue;
						}
						cell = row.getCell((short) 1);
						if (cell != null) {
							if (cell.getCellType() == 0) {
								telno = String.valueOf(cell.getNumericCellValue());
							} else if (cell.getCellType() == 1 && !String.valueOf(cell.getStringCellValue()).equals("")) {
								telno = String.valueOf(cell.getStringCellValue());
							}
						} else {
							bad_num++;
							obj.put("telno", k);
							obj.put("linenum", r);
							// bad_arr.append("{k:"+k+",r:"+r+"};");
							jsonarr.put(obj);
							continue;
						}
						
						cell = row.getCell((short) 2);
						if (cell != null) {
							if (cell.getCellType() == 0) {
								vpdn = String.valueOf(cell.getNumericCellValue());
							} else if (cell.getCellType() == 1 && !String.valueOf(cell.getStringCellValue()).equals("")) {
								vpdn = String.valueOf(cell.getStringCellValue());
							}
							System.out.println("vpdn==" + vpdn);
						} else {
							bad_num++;
							obj.put("vpdn", k);
							obj.put("linenum", r);
							// bad_arr.append("{k:"+k+",r:"+r+"};");
							jsonarr.put(obj);
							continue;
						}
						
						cell = row.getCell((short) 3);
						if (cell != null) {
							if (cell.getCellType() == 0) {
								date = String.valueOf(cell.getNumericCellValue());
							} else if (cell.getCellType() == 1 && !String.valueOf(cell.getStringCellValue()).equals("")) {
								date = String.valueOf(cell.getStringCellValue());
							}
							System.out.println("date==" + date);
						}
						
						ApplicationContext context = (ApplicationContext) request.getSession().getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
						MemberDao clientService = (MemberDao) context.getBean("memberDao");
						Member mem = clientService.memberSearchByAcc(memberno);
						if (mem != null) {
							System.out.println("member is exist");
							SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd");
							Date datetime = null;
							String old_vpdn = mem.getVpdn();
							if (!vpdn.equals(old_vpdn)) {
								String content = "";
								
								if (date != null) {
									datetime = tempDate.parse(date);
								} else {
									Calendar p_cal = Calendar.getInstance();
									datetime = p_cal.getTime();
								}
								if (old_vpdn.equals("0")) {
									
									mem.setEnableDate(datetime);
									mem.setDisableDate(null);
								} else {
									mem.setDisableDate(datetime);
								}
								vpdn = patt.matcher(vpdn).replaceAll("");
								System.out.println("vpdn in member===" + vpdn);
								System.out.println("vpdn length==" + vpdn.length());
								if (vpdn.equals("1")) {
									System.out.println("it's enabled");
									content = "欢迎您使用三生财智宝视频包月服务，现服务已开通，每月将收取￥30元包月费，您可使用2G视频包月流量。请重新登陆财智宝。";
								} else {
									System.out.println("it's disabled");
									content = "您好，您的视频服务已关闭！";
								}
								if (telno != null) {
									smsobj = new JSONObject();
									smsobj.put("telno", telno);
									smsobj.put("content", content);
									smsarr.put(smsobj);
								}
							}
							mem.setVpdn(vpdn);
							mem.setTelno(telno);
						} else {
							bad_num++;
							obj.put("pageno", k);
							obj.put("linenum", r);
							obj.put("err_type", "account");
							jsonarr.put(obj);
							continue;
						}
						rtn = clientService.updateInfo(mem);
						mem = null;
						rtn = "1";
					}
					
				}
			}
			sendAll(smsarr);
			
		} catch (Exception e) {
			rtn = "0";
			e.getMessage();
			e.printStackTrace();
			
			try {
				rs.put("flag", false);
				rs.put("bad", jsonarr);
			} catch (Exception ex) {
				System.out.println(ex);
			}
			return rs;
			
		}
		if (rtn.equals("1")) {
			try {
				rs.put("flag", true);
				rs.put("bad", jsonarr);
			} catch (Exception e) {
				System.out.println(e);
			}
			return rs;
		} else {
			
			try {
				rs.put("flag", false);
				rs.put("bad", jsonarr);
			} catch (Exception ex) {
				System.out.println(ex);
			}
			return rs;
		}
	}
	
	public boolean getExcel(OutputStream output, HttpServletRequest request, JSONObject obj) {
		
		boolean flag = false;
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet1 = wb.createSheet("sheet1");
			/*
			 * CXML cxml = new CXML(); cxml.forXML(fis); fis.close(); String value = cxml.getNodeText("/hyh/web-app/title"); if(!value.equals("")){ String color =
			 * cxml.getAttributeByXPath("/hyh/web-app/title", "color"); String size = cxml.getAttributeByXPath("/hyh/web-app/title", "font-size"); HSSFRow row=sheet1.createRow((short)0); HSSFCell
			 * cell=row.createCell((short)3); HSSFFont font = wb.createFont(); HSSFCellStyle hs = wb.createCellStyle(); enumColorType colorenum = enumColorType.getEnum(Integer.valueOf(color));
			 * System.out.println("colorenum=="+colorenum.Index); font.setColor(colorenum.Index); font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); font.setFontHeightInPoints(Short.parseShort(size));
			 * hs.setFont(font); //hs.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION); cell.setCellStyle(hs); cell.setEncoding((short)HSSFCell.ENCODING_UTF_16); cell.setCellValue(value); }
			 */
			HSSFRow row = sheet1.createRow((short) 0);
			HSSFCell cell = row.createCell((short) 0);
			// HSSFFont font_header = wb.createFont();
			// font_header.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			// font_header.setFontHeightInPoints((short)16);
			// font_header.setColor(enumColorType.BLUE.Index);
			// HSSFCellStyle hs_header = wb.createCellStyle();
			// hs_header.setFont(font_header);
			// cell.setCellStyle(hs_header);
			cell.setEncoding((short) HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("会员帐号");
			cell = row.createCell((short) 1);
			// cell.setCellStyle(hs_header);
			cell.setEncoding((short) HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("手机号");
			
			cell = row.createCell((short) 2);
			// cell.setCellStyle(hs_header);
			cell.setEncoding((short) HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("VPDN");
			
			cell = row.createCell((short) 3);
			// cell.setCellStyle(hs_header);
			cell.setEncoding((short) HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("处理时间");
			ApplicationContext context = (ApplicationContext) request.getSession().getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			MemberDao clientService = (MemberDao) context.getBean("memberDao");
			ResultSet list = clientService.memberSearchByExpert(obj.getString("useracc"), obj.getString("telno"), obj.getString("vpdn"));
			int i = 1;
			if (list != null) {
				while (list.next()) {
					row = sheet1.createRow((short) i);
					// HSSFFont font = wb.createFont();
					HSSFCellStyle hs = wb.createCellStyle();
					// font.setColor(HSSFFont.COLOR_RED);
					// font.setBoldweight((short)10);
					// hs.setFont(font);
					hs.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));// 字符串型
					cell.setCellStyle(hs);
					cell = row.createCell((short) 0);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(list.getString(1));
					
					cell.setCellStyle(hs);
					cell = row.createCell((short) 1);
					cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(list.getString(2));
					
					cell.setCellStyle(hs);
					cell = row.createCell((short) 2);
					cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(list.getString(3));
					
					cell.setCellStyle(hs);
					cell = row.createCell((short) 3);
					cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					if (list.getString(3).equals("0")) {
						cell.setCellValue(list.getString(5));
					} else {
						cell.setCellValue(list.getString(4));
					}
					
					i++;
				}
				// FileOutputStream fileout=new FileOutputStream("workbook.xls");
				
				// output.flush();
				wb.write(output);
				output.close();
				flag = true;
				;
			} else {
				return false;
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			flag = false;
		} finally {
			try {
				flag = true;
			} catch (Exception e) {
				flag = false;
			}
		}
		return flag;
	}
}
