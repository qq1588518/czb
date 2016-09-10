package com.ztenc.oa.proj.web.webservice;

public class SumWebserviceImpl implements SumWebservice{

	public String sumAandB(String a,String b){
		System.out.println("a:"+a);
		System.out.println("b:"+b);
		if(a.equals("nba001")&& b.equals("dfgdfg")){
			return "0";
		}else{
			return "1";
		}
	}
}
