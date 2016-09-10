package com.ztenc.oa.proj.web.webservice;

import com.ztenc.oa.proj.service.webservice.GetVersionService;

public class GetVersionInterfaceImpl implements GetVersionInterface{
	
	GetVersionService getVersionService;

	public void setGetVersionService(GetVersionService getVersionService) {
		this.getVersionService = getVersionService;
	}
	
	public String getVersionNO(){
		return getVersionService.getVersion();
	}
}
