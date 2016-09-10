package com.ztenc.oa.proj.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServelet extends HttpServlet{

	public  TestServelet(){}
	
	public void doPost(HttpServletRequest req,HttpServletResponse resp) throws IOException,ServletException{
		resp.setCharacterEncoding("GB2312");
		
		String user = req.getParameter("user");
		System.out.println("接收到的参数"+user);
		PrintWriter out = resp.getWriter();
		out.print("<?xml version="+"1.0" +"encoding="+"gb2312"+"?>");
		String s = "<ANS VER="+"1.0"+"><LCSCLIENTID>100</LCSCLIENTID>" +
				"<ORID>8615301052564</ORID><LIA> <RESULT>0</RESULT>" +
				"<POSINFOS><POSINFO><MSID>13510002000</MSID><MSIDTYPE>0</MSIDTYPE>" +
				"<POSITIONRESULT>2</POSITIONRESULT><LOCALTIME>20090605082525</LOCALTIME>" +
				"<LATITUDETYPE>1</LATITUDETYPE><LATITUDE>40.00288</LATITUDE><LONGITUDETYPE>0</LONGITUDETYPE>" +
				"<LONGITUDE>116.24157</LONGITUDE><ALTITUDE>55</ALTITUDE><RADUIS>50</RADUIS><POSOUR>18</POSOUR>" +
				"</POSINFO></POSINFOS></LIA></ANS>";
		out.print(s);
		out.close();
	}
	
	public static void main(String args[]){
		TestServelet t = new TestServelet();
		try {
			t.doPost(null, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
} 
