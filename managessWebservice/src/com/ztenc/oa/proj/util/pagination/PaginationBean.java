package com.ztenc.oa.proj.util.pagination;


/**
 * <p>文件名称: 分页BEAN</p>
 * <p>文件描述: 分页JAVABEAN</p>
 * <p>版权所有: 版权所有(C)2007-2009</p>
 * <p>公    司: 中兴软件技术(南昌)有限公司</p>
 * <p>内容摘要: 用于存储要显示页面的分页状态</p>
 * <p>其他说明: // 其它内容的说明</p>
 * <p>完成日期：2007-7-19</p>
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
public class PaginationBean { 
    /** 代表所要显示（首页，上页，下页，末页）的状态 */
    String condition;
    /** 转向上一段时，上段页码 */
    String upid;
    /** 转向下一段时，下段页码 */
    String downid;
    /** 转向最后一页时，最后页页码 */
    String lastid;
    /** 当前页显示的图片未满时，未满行所需填补的空格子数 */
    int linkstartnum;
    /** 导航链接的结束页码 */
    int linkendnum;
    /**当前第几页*/
    int pageid=1;
    /**每页显示的页面个数*/
    int eachpagenum=15;
    /**总记录数*/
    int total = 0;
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getEachpagenum() {
		return eachpagenum;
	}

	public void setEachpagenum(int eachpagenum) {
		this.eachpagenum = eachpagenum;
	}

	
	/**
	  * 获得显示分页状态
	  * @return 返回状态信息
	  */
	public String getCondition() {
		return condition;
	}
	
	/**
	  * 设置显示分页状态。
	  * @param condition 分页状态
	  * @return 无
	  */
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	/**
	  * 取得的下一页页码 
	  * @return 返回标识
	  */
	public String getDownid() {
		return downid;
	}
	
	/**
	  * 设置下一页要显示页码
	  * @param downid 所要显示页码
	  * @return 空
	  */
	public void setDownid(String downid) {
		this.downid = downid;
	}
	
	/**
	  * 取得的最后一页页码
	  * @return 返回页码
	  */
	public String getLastid() {
		return lastid;
	}
	
	/**
	  * 设置最后一页页码
	  * @param lastid 页码
	  * @return 空
	  */
	public void setLastid(String lastid) {
		this.lastid = lastid;
	}
	
	
	/**
	  * 取得的上一页页码 
	  * @return 返回页码
	  */
	public String getUpid() {
		return upid;
	}
	
	/**
	  * 设置上一页要显示页页码
	  * @param upid 页码
	  * @return 空
	  */
	public void setUpid(String upid) {
		this.upid = upid;
	}

	public int getLinkendnum() {
		return linkendnum;
	}

	public void setLinkendnum(int linkendnum) {
		this.linkendnum = linkendnum;
	}

	public int getLinkstartnum() {
		return linkstartnum;
	}

	public void setLinkstartnum(int linkstartnum) {
		this.linkstartnum = linkstartnum;
	}

	public int getPageid() {
		return pageid;
	}

	public void setPageid(int pageid) {
		this.pageid = pageid;
	}
}
     