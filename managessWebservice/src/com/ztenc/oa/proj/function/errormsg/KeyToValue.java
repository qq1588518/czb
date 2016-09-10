package com.ztenc.oa.proj.function.errormsg;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class KeyToValue {
	

	public  static String getValue(String key){
		Properties p = new Properties();
		try {
			p.loadFromXML(KeyToValue.class.getResourceAsStream("..\\..\\springConfig\\errormsg.xml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p.getProperty(key);
	}
	
	public static void main(String args[]){
		System.out.println(KeyToValue.getValue("aaa"));
	}
}
