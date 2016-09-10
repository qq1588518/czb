package com.ztenc.oa.proj.util.pagination;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>文件名称: 题目名称</p>
 * <p>文件描述: 本类描述</p>
 * <p>版权所有: 版权所有(C)2007-2009</p>
 * <p>公    司: 中兴软件技术(南昌)有限公司</p>
 * <p>内容摘要: // 分页</p>
 * <p>其他说明: // 其它内容的说明</p>
 * <p>完成日期：2007-9-24</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author  曹杰
 */
public class PaginationServiceImpl implements PaginationService{
	/*不使用IOC注入注释下列代码 将第64行代码取消注释*/
	/*----------------IOC注入------------------------*/
	PaginationBOI paginationBOI;
	 //提供IOC注入
	public void setPaginationBOI(PaginationBOI paginationBOI) {
		this.paginationBOI = paginationBOI;
	}
	/*-----------------IOC注入------------------------*/
	
	/**
	  * 通过HttpServletRequest参数和PcurrentDataDaoService,将要显示的记录信息返回，并将
	  * 分页显示的状态信息保存到request返回中。
	  * @param HttpServletRequest       request请求
	  * @param PcurrentDataDaoService   获得记录信息操作类 
	  * @param Object o 查询条件
	  * @return Object[] 返回所要显示的数据记录
	  */
	public Object[] service(HttpServletRequest request,PcurrentDataDaoService vardao,Object o){
        //每页显示的记录数
		int eachpagenum =15;
		//起始记录id
        int index = 0;
        //所要显示页的第几页
        int pageid = 1;
        //总记录
        int size = 0;
		//获得eachpagenum数据
		if( null == request.getParameter("eachpagenum")||"".equals(request.getParameter("eachpagenum"))){
			eachpagenum = 2;
		}
		else{
		    eachpagenum = Integer.valueOf(request.getParameter("eachpagenum")).intValue();
		}
		if( null == request.getParameter("pageid")||"".equals(request.getParameter("pageid"))){
			index = 0;
		}
		//如果有参数的情况
		else{
		    pageid = Integer.valueOf(request.getParameter("pageid")).intValue();
		    index = (pageid - 1) * eachpagenum;
		}
		//获得所要显示的记录
		Object[] infos = vardao.getInfo(index,eachpagenum,o);
		//总记录数
		Object count = vardao.getInfo(0,-1,o)[0];
		if(count.getClass().isAssignableFrom(Long.class)){
			size = (int)(((Long)count).longValue()); 
		}else{
			size = ((Integer)count).intValue(); 
		}
//		创建BO类
//		PaginationBOImpl bo=new PaginationBOImpl();--不提供IOC注入，使用此行
		//IOC注入
		//获得前台所要显示的数据
		paginationBOI.Pagination(pageid,eachpagenum,index,size,request);
		return infos;
	}
	
   
}
