package com.ztenc.oa.proj.doExcel;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztenc.oa.proj.json.JSONObject;

public class abstractExcel implements IObject {
	
	public void init(HttpServletResponse response,HttpServletRequest request){
		
	}
	
	public void init(InputStream is, HttpServletResponse response,
			HttpServletRequest request) {
		// TODO Auto-generated method stub

	}
	
	public void writeExcel(String className,JSONObject params){
		
		
	}
	
	public void readExcel() {
		// TODO Auto-generated method stub

	}

}
