package com.ztenc.oa.proj.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import com.ztenc.oa.proj.util.CXML;;
public class Test {

	public static void main(String args[]){
		String conpic=",upload/catagory/images/20110216163428NPK5XK.jpg,upload/catagory/images/20110216163428P514A7.jpg";
		
		String[] ups=conpic.split(",");
		for(int i=1;i<ups.length;i++)
			{System.out.println("ups["+i+"]==="+ups[i] );}
		
		
		System.out.println("ups::::"+ups.length);
	}
}
