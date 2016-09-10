package com.ztenc.oa.proj.util.pagination;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>文件名称: 题目名称</p>
 * <p>文件描述: 本类描述</p>
 * <p>版权所有: 版权所有(C)2007-2009</p>
 * <p>公    司: 中兴软件技术(南昌)有限公司</p>
 * <p>内容摘要:  分页BO接口</p>
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
public interface PaginationBOI {
	
	/**
	  * 函数的目的/功能: 通过指定内容,个数等，指定当前显示的状态。由前台根据状态决定分页的格式
      * @param pageid      第几页
      * @param eachpagenum 每页显示的信息数量
      * @param index       预留
      * @param total       总的记录条数
      * @param request     请求对象
    */
	public void Pagination(int pageid,int eachpagenum,int index,
            int total,HttpServletRequest request);
}
