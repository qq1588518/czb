package com.ztenc.oa.proj.web.member;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Session;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import basic.base.test.FilePart;
import basic.base.test.MultipartParser;
import basic.base.test.ParamPart;
import basic.base.test.Part;

import com.ztenc.oa.proj.doExcel.IExcel;
import com.ztenc.oa.proj.json.JSONArray;
import com.ztenc.oa.proj.json.JSONObject;
import com.ztenc.oa.proj.service.member.*;
import com.ztenc.oa.proj.util.pagination.PaginationService;
import com.ztenc.oa.proj.util.pagination.PcurrentDataDaoService;

public class MemberController extends MultiActionController {
	
	private MemberService memberService;
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
	String result = "";
	
	public ModelAndView getUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		Map model = new HashMap();
		return new ModelAndView("member/membermanage", model);
	}
	
	// 留言板信息读取
	public ModelAndView memberSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		int index = Integer.valueOf(request.getParameter("index"));
		int length = Integer.valueOf(request.getParameter("length"));
		
		String searchvalue = request.getParameter("searchvalue");
		if (searchvalue != null) {
			JSONObject jsonobj = new JSONObject(searchvalue);
			if (!jsonobj.has("membername")) {
				jsonobj.put("membername", "");
			}
			if (!jsonobj.has("telno")) {
				jsonobj.put("telno", "");
			}
			if (!jsonobj.has("vpdn")) {
				jsonobj.put("vpdn", "");
			}
			result = memberService.MemberSearchByKey(index, length, jsonobj.getString("membername"), jsonobj.getString("telno"), jsonobj.getString("vpdn"));
		} else {
			result = memberService.MemberSearch(index, length);
		}
		
		response.getWriter().print(result);
		response.getWriter().close();
		return null;
	}
	
	// 留言板信息删除
	public ModelAndView deleteMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String info = request.getParameter("fbid");
		result = memberService.deleteMember(info);
		response.getWriter().print(result);
		response.getWriter().close();
		return null;
	}
	
	// 修改留言板信息回复
	public ModelAndView modifyMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		Map model = new HashMap();
		String obj = new String(request.getParameter("obj").getBytes("iso8859-1"), "utf-8");
		if (!obj.equals("null")) {
			String[] object = obj.split(",");
			String memberno = object[0];
			String membername = object[1];
			String userAcc = object[2];
			String imsi = object[3];
			System.out.println("imsi:" + imsi);
			String groupno = object[6];
			String vpdn = object[7];
			String telno = object[8];
			model.put("memberno", memberno);
			model.put("membername", membername);
			model.put("userAcc", userAcc);
			model.put("groupno", groupno);
			model.put("vpdn", vpdn);
			model.put("telno", telno);
			model.put("imsi", imsi);
		}
		List list = memberService.getGroup();
		model.put("group", list);
		return new ModelAndView("member/modifyMember", model);
	}
	
	public ModelAndView addMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		Map model = new HashMap();
		List list = memberService.getGroup();
		model.put("group", list);
		return new ModelAndView("member/addMember", model);
	}
	
	public ModelAndView saveMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String memberno = request.getParameter("memberno");
		String membername = request.getParameter("membername");
		String imsi = request.getParameter("imsi");
		System.out.println("manchineid:" + imsi);
		String memo = request.getParameter("memo");
		String groupno = request.getParameter("group");
		String vpdn = request.getParameter("vpdn");
		String telno = request.getParameter("telno");
		String useracc = request.getParameter("useracc");
		System.out.println("memo:" + memo);
		String password = request.getParameter("password");
		if (memberno != null) {
			result = memberService.modifyMember(memberno, membername, telno, imsi, groupno, vpdn, useracc);
		} else {
			result = memberService.addMember(membername, telno, imsi, groupno, vpdn);
		}
		response.getWriter().print(result);
		response.getWriter().close();
		return null;
	}
	
	// public ModelAndView importMember(HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	//
	// response.setContentType("text/html;charset=utf-8");
	// request.setCharacterEncoding("utf-8");
	// Map model = new HashMap();
	// String update_success_flag="false";
	//
	// try{
	// int maxSize = 2 * 1024 * 1024;
	// String content_type="";
	// InputStream file=null;
	// byte[] in_byte=null;
	// //CDBContainer cDBContainer = new CDBContainer();
	// /** 上传部分 先取出part判断是文件还是属性 */
	// Part part = null;
	// HashMap paramMap=new HashMap();
	// MultipartParser mrequest = new MultipartParser(request, maxSize);
	// mrequest.setEncoding("GBK");
	// InputStream fileparts=null;
	// while ((part = mrequest.readNextPart()) != null){
	// if(part.isParam()){
	// ParamPart paramPart = (ParamPart) part;
	// paramMap.put(paramPart.getName(), paramPart.getStringValue());
	// }
	// if (part.isFile()) {
	// /** 转化为 filePart*/
	// FilePart filepart = (FilePart) part;
	// content_type=filepart.getContentType();
	// file=filepart.getInputStream();
	// fileparts = file;
	// in_byte=new byte[maxSize];
	// int len = file.read(in_byte);
	// System.out.println("len=="+len);
	// }
	// }
	// ByteArrayInputStream bais = new ByteArrayInputStream(in_byte);
	// MemberServiceImpl ie=new MemberServiceImpl();
	// JSONObject obj=ie.readexcel(request,bais);
	// JSONArray arr = obj.getJSONArray("bad");
	// String tmp_str = "有"+arr.length()+"条数据未上传成功，请检查您要上传数据的格式是否正确并且确认财智宝有此会员帐号。";
	// if(arr.length()==0){
	//
	// response.getWriter().print("<script language=javascript>alert('上传成功！');history.go(-1)</script>");
	// update_success_flag="true";
	// }else{
	//
	// String tmp_line = "";
	// String tmp_pn = "";
	// int t = 0;
	// for(int i=0;i<arr.length();i++){
	//
	// String pageno = arr.getJSONObject(i).getString("pageno");
	// String linenum = arr.getJSONObject(i).getString("linenum");
	// if(t == 0){
	// tmp_line =(Integer.valueOf(linenum)+2)+"行,";
	// tmp_str += "在第"+(Integer.valueOf(pageno)+1)+"张表中"+tmp_line;
	// System.out.println("pageno="+pageno);
	// tmp_pn = pageno;
	// System.out.println("tmp_pn="+tmp_pn);
	// t=1;
	// }else{
	// if(tmp_pn.equals(pageno)){
	// tmp_line =(Integer.valueOf(linenum)+2)+"行,";
	// System.out.println("aaaa");
	// tmp_str +=tmp_line;
	// }else{
	// tmp_line =(Integer.valueOf(linenum)+2)+"行,";
	// tmp_str += "在第"+(Integer.valueOf(pageno)+1)+"张表中"+tmp_line;
	// System.out.println("pageno1="+pageno);
	// tmp_pn = pageno;
	// System.out.println("tmp_pn1="+tmp_pn);
	// }
	// }
	//
	// }
	// tmp_str = tmp_str.substring(0,tmp_str.length()-1);
	// }
	// response.getWriter().print("<script language=javascript>alert('"+tmp_str+"');history.go(-1)</script>");
	// update_success_flag="true";
	//
	// }catch(IOException e){
	//
	// if(e.getMessage().indexOf("Posted content length of")!=-1){
	// response.getWriter().print("<script language=javascript>alert('文件大小超过5M，上传失败！');history.go(-1)</script>");
	// update_success_flag="flase";
	// response.getWriter().flush();
	// response.getWriter().close();
	// }
	// }finally{
	//
	// }
	//
	//
	//
	//
	//
	// return null;
	// }
	
	public ModelAndView importMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		Map model = new HashMap();
		String update_success_flag = "false";
		
		try {
			IExcel excel = new IExcel(2 * 1024 * 1024);
			excel.setTitleRowNum(2);
			excel.init(response, request);
			excel.setCellTypeAndName(0, IExcel.data_text, "account", IExcel.NOT_EMPTY);
			excel.setCellTypeAndName(1, IExcel.data_text, "telno", IExcel.NOT_EMPTY);
			excel.setCellTypeAndName(2, IExcel.data_text, "vpdn", IExcel.NOT_EMPTY);
			excel.setCellTypeAndName(3, IExcel.data_text, "date", IExcel.ALLOW_EMPTY);
			excel.readExcel();
		} catch (Exception e) {
			
			System.out.println(e);
		} finally {
			
		}
		return null;
	}
	
	public ModelAndView exportMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "attachment;filename=test123.xls");// 指定下载的文件名
		response.setContentType("application/vnd.ms-excel;charset=utf-8;");
		OutputStream os = response.getOutputStream();
		String useracc = request.getParameter("useracc");
		String telno = request.getParameter("telno");
		String vpdn = request.getParameter("vpdn");
		JSONObject obj = new JSONObject();
		obj.put("useracc", useracc);
		obj.put("telno", telno);
		obj.put("vpdn", vpdn);
		IExcel excel = new IExcel();
		excel.init(response, request);
		excel.writeExcel("memberDao", obj);
		// memberService.getExcel(os, request, obj);
		return null;
	}
}
