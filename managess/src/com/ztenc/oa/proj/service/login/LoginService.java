package com.ztenc.oa.proj.service.login;

import java.util.List;

import com.ztenc.oa.proj.json.JSONArray;

public interface LoginService {

	public String login(String username,String password);
	public boolean  setVPDNAddr(String path,JSONArray value);
}
