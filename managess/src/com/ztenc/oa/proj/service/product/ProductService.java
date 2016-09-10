package com.ztenc.oa.proj.service.product;

import java.util.List;

import com.ztenc.oa.proj.json.JSONObject;


public interface ProductService {
	
	//查找栏目信息
	public String getProductList(String title,String typeno,int index,int lenghth);
	public List getGroups();
	public List getTypes();
	public List getProductInfo(String id);
	public List getProductPermission(String id);
	public String saveContentToFile(String name,String con,String dirname,String filename,int curpageno,String total,String total_filenames,String filepro,String dates);
	public String getContentFromFile(String dirname,String url);
	public String deleteFromFile(String dirname,String url);
	public String deleteFromImages(String dirname,String url);
	//删除栏目信息
	public String deleteProduct(String id);
	//添加栏目
	public  JSONObject saveProduct(String name, String proAddress,
			String typeno, String permission, String con,
			String introduceurl, String experturl, String useurl, String level,
			String code,String price,String dvalue,String pvvalue,String standard);
	//修改
	public JSONObject alterProduct(String name, String proAddress,
			String typeno, String permission, String con,
			String introduceurl, String experturl, String useurl, String level,
			String code,String price,String dvalue,String pvvalue,String id,String standard);
}
