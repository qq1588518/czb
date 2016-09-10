package com.ztenc.oa.proj.dao.login;

import java.sql.ResultSet;
import java.util.List;

public interface LoginDao {

	public ResultSet getUser(String Name,String password);
}
