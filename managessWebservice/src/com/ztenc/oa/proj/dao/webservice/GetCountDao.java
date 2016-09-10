package com.ztenc.oa.proj.dao.webservice;

import java.util.List;

import com.ztenc.oa.proj.bean.Member;
import com.ztenc.oa.proj.bean.Member2group;

public interface GetCountDao {

	public List getCount();
	
	public List getCount(String value);
}
