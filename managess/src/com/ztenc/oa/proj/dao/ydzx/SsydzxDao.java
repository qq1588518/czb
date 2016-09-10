package com.ztenc.oa.proj.dao.ydzx;

import java.sql.ResultSet;
import java.util.List;

public interface SsydzxDao {
	public List getAll(String id);
	public List getItem(String id,String index,String length);
	
}
