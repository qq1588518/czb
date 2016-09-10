package com.ztenc.oa.proj.service.login;

import java.io.FileInputStream;
import java.sql.Array;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.ztenc.oa.proj.util.SafePwd;
import org.springframework.dao.DataAccessException;

import com.ztenc.oa.proj.json.*;
import com.sun.xml.bind.v2.runtime.reflect.ListIterator;
import com.ztenc.oa.proj.dao.login.LoginDao;

public class LoginServiceImpl implements LoginService {

	private LoginDao loginDao;
	private SafePwd safePwd;
	
	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}
	
	public void setSafePwd(SafePwd safePwd) {
		this.safePwd = safePwd;
	}
	
	public String login(String username,String password){
		
		String rtn = "";
		String newpassword = safePwd.converPwd(password);
		ResultSet rs = loginDao.getUser(username, newpassword);
		try{
			while(rs.next()){
				rtn = rs.getString(1);
				System.out.println("name="+ rtn);
			}
			
		}catch(Exception e){
			System.out.println(e);
		}
		return rtn;
	}
	public boolean  setVPDNAddr(String path,JSONArray value) {
		//String pageKey = request.getHeader("referer");

		Properties settings = new Properties();
		String countFilePath = path;
		boolean flag = false;
		try {
			FileInputStream fis = new java.io.FileInputStream(countFilePath);
			settings.load(fis);
			fis.close();
		} catch (Exception e) { 

		}
		String count = "0";
		try {
			java.io.FileOutputStream fos = new java.io.FileOutputStream(countFilePath);
			System.out.println("value=="+value.get(0));
			settings.put("verifyurl",value.get(0));
			//settings.put("gourl",value.get(1));
			settings.store(fos, "the page is accessed");
			fos.close();
			flag = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return flag;
	}
}
