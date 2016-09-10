package com.ztenc.oa.proj.util.pagination;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>文件名称: 题目名称</p>
 * <p>文件描述: 本类描述</p>
 * <p>版权所有: 版权所有(C)2007-2009</p>
 * <p>公    司: 中兴软件技术(南昌)有限公司</p>
 * <p>内容摘要: 分页功能接口</p>
 * <p>其他说明: // 其它内容的说明</p>
 * <p>完成日期：2007-9-25</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author  new
 */
public interface PaginationService {
	
	/**
	  * 通过HttpServletRequest参数和PcurrentDataDaoService,将要显示的记录信息返回，并将
	  * 分页显示的状态信息保存到request返回中。
	  * @param HttpServletRequest       request请求
	  * @param PcurrentDataDaoService   获得记录信息操作类 
	  * @param Object o 查询条件
	  * @return Object[] 返回所要显示的数据记录
	  */
	public Object[] service(HttpServletRequest request,PcurrentDataDaoService vardao,Object o);
}
