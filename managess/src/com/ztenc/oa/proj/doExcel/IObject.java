package com.ztenc.oa.proj.doExcel;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztenc.oa.proj.json.JSONObject;

public interface IObject {
	
	public final static int data_number=0;
	
	public final static int data_text = 1;
	
	public final static int data_date = 2;
	
	public final static int data_bool = 3;

	public final static int ERR_CELL = 1; 
	
	public final static int ERR_TYPE = 2;
	
	public final static int isKey = 0;
	
	public final static int NOT_EMPTY=1;
	
	public final static int ALLOW_EMPTY=2;
	
	public void init(HttpServletResponse response,HttpServletRequest request);
	
	public void init(InputStream is,HttpServletResponse response,HttpServletRequest request);
	
	public void readExcel();
	
	public void writeExcel(String className,JSONObject params);
	
}
