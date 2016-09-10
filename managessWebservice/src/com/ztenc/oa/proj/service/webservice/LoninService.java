package com.ztenc.oa.proj.service.webservice;

public interface LoninService {

	public String MobileLogin(String serialNum, String IMEINum, String userNo,
			String pwd);
	
	public String hasCodeLogin(String serialNum, String IMEINum, String userNo,String code);
	public boolean testVisit();
	public String verify(String account);
	public String doVpdn(String account,String vpdn,String telno);
	
	public String getCount();
	public String getItemCount(String value);
	public String getAdvertisment();
}
