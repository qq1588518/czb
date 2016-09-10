package com.ztenc.oa.proj.util.pagination;

/**
 * <p>文件名称: 题目名称</p>
 * <p>文件描述: 本类描述</p>
 * <p>版权所有: 版权所有(C)2007-2009</p>
 * <p>公    司: 中兴软件技术(南昌)有限公司</p>
 * <p>内容摘要: //分页显示接口，用于显示获得所要显示的内容/p>
 * <p>其他说明: // 其它内容的说明</p>
 * <p>完成日期：2007-9-24</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：
 *    版 本 号：
 *    修 改 人：
 *    修改内容：PcurrentDataDaoService
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author 曹杰
 */
public interface PcurrentDataDaoService {
	
	/**
	  * 获得相应的数据信息
	  * @param  index 数据的开始下标
	  * @param  length 数据长度
	  * @param  condition 条件
	  * @param  o 查询条件
	  * @return 分页显示内容信息
	  */
	public Object[] getInfo(int index,int length,Object o);
}
