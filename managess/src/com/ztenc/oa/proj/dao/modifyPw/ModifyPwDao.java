package com.ztenc.oa.proj.dao.modifyPw;

import java.sql.ResultSet;
import java.util.List;

public interface ModifyPwDao {
	public String checkPassword(String userName, String password);
	public boolean updatePw(String verifypwd, String userName);
}
