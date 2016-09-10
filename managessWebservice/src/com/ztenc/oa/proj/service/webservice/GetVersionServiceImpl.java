package com.ztenc.oa.proj.service.webservice;

public class GetVersionServiceImpl implements GetVersionService{

	String versionNo;

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	
	public String getVersion(){
		return versionNo;
	}
}
