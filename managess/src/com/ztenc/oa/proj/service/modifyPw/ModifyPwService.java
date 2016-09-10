package com.ztenc.oa.proj.service.modifyPw;

import java.util.List;

public interface ModifyPwService {
	public  String checkPassword(String username,String oldPassword);
	public int modifyPassword(String verifypassword,String username);
}
